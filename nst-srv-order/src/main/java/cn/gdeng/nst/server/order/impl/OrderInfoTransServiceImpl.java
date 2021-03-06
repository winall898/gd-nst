package cn.gdeng.nst.server.order.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

import cn.gdeng.nst.api.dto.member.MemberCountDTO;
import cn.gdeng.nst.api.dto.order.OrderInfoDTO;
import cn.gdeng.nst.api.dto.order.OrderInfoTransDTO;
import cn.gdeng.nst.api.dto.order.SourceExamineDTO;
import cn.gdeng.nst.api.dto.task.PushMsgDto;
import cn.gdeng.nst.api.server.order.OrderInfoTransService;
import cn.gdeng.nst.api.vo.order.OrderInfoTransAndSourceExamine;
import cn.gdeng.nst.api.vo.order.OrderInfoTransVo;
import cn.gdeng.nst.dao.BaseDao;
import cn.gdeng.nst.entity.nst.MqError;
import cn.gdeng.nst.entity.nst.OrderBeforeEntity;
import cn.gdeng.nst.entity.nst.OrderInfoEntity;
import cn.gdeng.nst.entity.nst.OrderInfoTransEntity;
import cn.gdeng.nst.entity.nst.SourceShipperEntity;
import cn.gdeng.nst.enums.MqConstants;
import cn.gdeng.nst.enums.MsgCons;
import cn.gdeng.nst.enums.OrderBeforeEnum;
import cn.gdeng.nst.enums.OrderInfoPrePayStatus;
import cn.gdeng.nst.enums.OrderInfoStatusEnum;
import cn.gdeng.nst.enums.OrderInfoTransCloseReasonEnum;
import cn.gdeng.nst.enums.OrderInfoTransStatusEnum;
import cn.gdeng.nst.enums.PushConstants;
import cn.gdeng.nst.util.admin.web.DateUtil;
import cn.gdeng.nst.util.web.api.ApiResult;
import cn.gdeng.nst.util.web.api.BizException;
import cn.gdeng.nst.util.web.api.GSONUtils;
import cn.gdeng.nst.util.web.api.SerializeUtil;

/**
 *  运单-物流信息接口实现类
 * @author wjguo
 *
 * datetime:2016年12月5日 上午11:56:39
 */
@Service
public class OrderInfoTransServiceImpl implements OrderInfoTransService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private BaseDao<?> baseDao;
	/**
	 * 消息推送队列生产者
	 */
	@Resource
	private ProducerBean msgPushProducer;
	/**
	 * 数据统计队列生产者
	 */
	@Resource
	private ProducerBean countProducer;
	

	@Override
	@Transactional
	public ApiResult<Map<String, Object>> saveForPrePaymentSucc(String orderNo) throws BizException {
		OrderInfoDTO orderInfoDTO = getOrderInfoByOrderNo(orderNo);
		
		//===1.更新orderInfo订单信息表的支付预付款状态===   
		updateOrderInfoPrePayStatus(orderInfoDTO.getId(), OrderInfoPrePayStatus.PAID.getCode());
		//===2.给物流公司推送消息===
		pushMsgToLogisticsForPrePaymentSucc(orderInfoDTO);
		//===3.给车主推送消息===
		pushMsgToDriverForPrePaymentSucc(orderInfoDTO);
		
		//===4.组装返回结果=== ps:目前无需要返回的数据，返回空集合，备后面扩展。
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ApiResult<Map<String, Object>> apiResult = new ApiResult<Map<String,Object>>();
		apiResult.setResult(resultMap);
		return apiResult;
	}
	
	
	/** 根据订单编号获取订单信息
	 * @param orderNo
	 * @return
	 * @throws BizException 如果没有对应的订单信息，则抛出此异常。
	 */
	private OrderInfoDTO getOrderInfoByOrderNo(String orderNo) throws BizException {
		Map<String, Object> paramMap = new HashMap<String, Object>(1);
		paramMap.put("orderNo", orderNo);
		OrderInfoDTO orderInfoDTO = baseDao.queryForObject("OrderInfo.getByOrderNo", paramMap, OrderInfoDTO.class);
		if (orderInfoDTO == null) {
			throw new BizException(MsgCons.C_23034, MsgCons.M_23034);
		}
		return orderInfoDTO;
	}
	
	
	/** 更新订单信息的预付款状态
	 * @param id 订单id
	 * @param prePayStatus
	 */
	private void updateOrderInfoPrePayStatus(Integer id, Byte prePayStatus) {
		OrderInfoEntity orderInfoEntity  = new OrderInfoEntity();
		orderInfoEntity.setPrePayStatus(prePayStatus);
		orderInfoEntity.setId(id);
		orderInfoEntity.setUpdateTime(new Date());
		baseDao.dynamicMerge(orderInfoEntity);
	}
	
	
	/** 更新订单信息的多种状态
	 * @param id 订单id
	 * @param orderStatus 订单状态。如果不需要更新此状态，则传入null即可。
	 * @param transStatus 物流状态。如果不需要更新此状态，则传入null即可。
	 * @param closeReason 关闭原因。如果物流状态不属于关闭状态，则直接传入null即可。
	 */
	private void updateOrderInfoMultiStatus(Integer id, Byte orderStatus, Byte transStatus, Byte closeReason) {
		OrderInfoEntity orderInfoEntity  = new OrderInfoEntity();
		orderInfoEntity.setOrderStatus(orderStatus);
		orderInfoEntity.setTransStatus(transStatus);
		orderInfoEntity.setCloseReason(closeReason);
		orderInfoEntity.setId(id);
		orderInfoEntity.setUpdateTime(new Date());
		baseDao.dynamicMerge(orderInfoEntity);
	}
	
	/** 构造订单物流信息并保存
	 * @param OrderInfoDTO 订单信息DTO
	 * @param transStatus 物流状态
	 * @return  保存的物流信息id
	 */
	private Long constractTransAndSave(OrderInfoDTO orderInfoDTO, Byte transStatus) {
		OrderInfoTransEntity transEntity = constractTransBaseData(orderInfoDTO);
		transEntity.setTransStatus(transStatus);
		return  baseDao.persist(transEntity, Long.class);
	}
	
	/** 构造订单-物流信息的基本数据
	 * @param orderInfoDTO
	 * @return
	 */
	private OrderInfoTransEntity constractTransBaseData(OrderInfoDTO orderInfoDTO) {
		OrderInfoTransEntity transEntity = new OrderInfoTransEntity();
		transEntity.setOrderInfoId(orderInfoDTO.getId());
		transEntity.setOrderNo(orderInfoDTO.getOrderNo());
		transEntity.setSourceId(orderInfoDTO.getSourceId());
		transEntity.setIsDeleted((byte)0);
		transEntity.setOperateTime(new Date());
		transEntity.setCreateTime(new Date());
		transEntity.setUpdateTime(new Date());
		return transEntity;
	}
	
	
	/** 预付款支付成功后，推送消息给物流公司。
	 * @param OrderInfoDTO 订单信息DTO
	 * @throws BizException  
	 * 
	 */
	private void pushMsgToLogisticsForPrePaymentSucc(OrderInfoDTO orderInfoDTO) throws BizException {
		SourceShipperEntity shipperEntity = getSourceShipperById(orderInfoDTO.getSourceId());
		PushMsgDto dto=new PushMsgDto();
		dto.setBizId(orderInfoDTO.getOrderBeforeId());
		dto.setContent(MsgCons.M_28012);
		dto.setMemberId(shipperEntity.getAssignMemberId());
		dto.setMsgType(PushConstants.MSG_TYPE_5);
		pushMsgMQ(dto);
	}
	
	/** 根据id获取货源信息。
	 * @param sourceId
	 * @return
	 * @throws BizException 如果没有对应的订单信息，则抛出此异常。
	 */
	private SourceShipperEntity getSourceShipperById(Integer sourceId) throws BizException {
		SourceShipperEntity shipperParam = new SourceShipperEntity();
		shipperParam.setId(sourceId);
		SourceShipperEntity shipperResult = baseDao.find(SourceShipperEntity.class, shipperParam);
		if (shipperResult == null) {
			throw new BizException(MsgCons.C_24033, MsgCons.M_24033);
		}
		
		return shipperResult;
	}
	
	/** 预付款支付成功后，推送消息给车主。
	 * @param OrderInfoDTO 订单信息DTO
	 * 
	 */
	private void pushMsgToDriverForPrePaymentSucc(OrderInfoDTO orderInfoDTO) {
		PushMsgDto dto = new PushMsgDto();
		dto.setBizId(orderInfoDTO.getOrderBeforeId());
		dto.setContent(MsgCons.M_28013);
		dto.setMemberId(orderInfoDTO.getDriverMemberId());
		dto.setMsgType(PushConstants.MSG_TYPE_0);
		pushMsgMQ(dto);
	}
	
	private void pushMsgMQ(PushMsgDto dto) {
		Message msg = new Message(msgPushProducer.getProperties().getProperty(MqConstants.TOPIC),MqConstants.TAG,SerializeUtil.serialize(dto));
		msg.setKey(dto.getMemberId().toString());
		try {
			logger.info("MQ消息推送成功, 推送的数据为:{}", JSON.toJSONString(dto));
			msgPushProducer.send(msg);
		} catch (Exception e) {
			logger.error("MQ消息推送失败", e);
			//异常数据存入表 mq_error
			this.insertMqError(dto, dto.getBizId(), MqConstants.TOPIC_PUSH);
		}
	}
	
	
	/**
	 * 异常数据存入表 mq_error
	 * @param dto
	 */
	private void insertMqError(Object dto,Integer memberId,Integer topic){
		MqError mqError = new MqError();
		mqError.setBizType(MqConstants.BIZ_TYPE_1);
		mqError.setTopic(topic);
		mqError.setMemberId(memberId);
		mqError.setContent(GSONUtils.toJson(dto,false));
		mqError.setCreateUserId(1);
		baseDao.execute("MqError.insert", mqError);
	}



	@Override
	@Transactional
	public ApiResult<Map<String, Object>> repealPrePaymentSucc(String orderNo) throws BizException {
		OrderInfoDTO orderInfoDTO = getOrderInfoByOrderNo(orderNo);
		
		//===1.更新orderBefore预订单表的货源状态 为 已关闭===    暂时取消对预订单状态的更新。
		//updateSourceStatusForOrderBefore(orderInfoDTO.getOrderBeforeId(), OrderBeforeEnum.CLOSED.getCode());
		
		//===2.更新orderInfo订单信息表的订单状态和物流状态 为 已关闭=== 
		Byte orderStatus = OrderInfoStatusEnum.CLOSED.getCode();
		Byte transStatus = OrderInfoTransStatusEnum.REJECTED_TAK_GOODS.getCode();
		Byte transCloseReason = OrderInfoTransCloseReasonEnum.REPEAL_PRE_PAYMTEN.getCode();
		updateOrderInfoMultiStatus(orderInfoDTO.getId(), orderStatus, transStatus, transCloseReason);
		
		//===3.新增订单-物流信息表的记录。===
		Long transId = constractTransAndSave(orderInfoDTO, transStatus);
		
		//===4.组装返回结果===
		Map<String, Object> resultMap = new HashMap<String, Object>(1);
		resultMap.put("transId", transId);
		ApiResult<Map<String, Object>> apiResult = new ApiResult<Map<String,Object>>();
		apiResult.setResult(resultMap);
		return apiResult;
	}



	@Override
	@Transactional
	public ApiResult<Map<String, Object>> payFinalPaymentSuccHandler(String orderNo) throws BizException {
		OrderInfoDTO orderInfoDTO = getOrderInfoByOrderNo(orderNo);
		//===1.更新orderBefore预订单的运单状态===  ps:尾款支付后，订单就已经完成。
		updateSourceStatusForOrderBefore(orderInfoDTO.getOrderBeforeId(), OrderBeforeEnum.COMPLETEORDER.getCode());
		
		//===2.更新orderInfo订单信息的运单状态=== 
		updateOrderStatusForOrderInfo(orderInfoDTO.getId(), OrderInfoStatusEnum.GOODS_CONFIRM.getCode());
		
		//===3.支付尾款成功后发送mq信息===
		sendMQForFinalPaymentSucc(orderInfoDTO);
		
		//===4.组装返回结果=== ps:目前无需要返回的数据，返回空集合，备后面扩展。
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ApiResult<Map<String, Object>> apiResult = new ApiResult<Map<String,Object>>();
		apiResult.setResult(resultMap);
		return apiResult;
	}
	
	
	/** 更新预订单的货源状态
	 * @param id 预订单id
	 * @param sourceStatus 货源状态
	 */
	private void updateSourceStatusForOrderBefore(Integer id, Byte sourceStatus) {
		OrderBeforeEntity orderBeforeEntity  = new OrderBeforeEntity();
		orderBeforeEntity.setSourceStatus(sourceStatus);
		orderBeforeEntity.setId(id);
		orderBeforeEntity.setUpdateTime(new Date());
		baseDao.dynamicMerge(orderBeforeEntity);
	}
	
	
	/** 更新订单信息的订单状态
	 * @param id 订单id
	 * @param orderStatus 订单状态
	 */
	private void updateOrderStatusForOrderInfo(Integer id, Byte orderStatus) {
		OrderInfoEntity orderInfoEntity  = new OrderInfoEntity();
		orderInfoEntity.setOrderStatus(orderStatus);
		orderInfoEntity.setId(id);
		orderInfoEntity.setUpdateTime(new Date());
		orderInfoEntity.setConfirmGoodsTime(new Date());
		baseDao.dynamicMerge(orderInfoEntity);
	}
	
	/** 尾款支付成功后，推送消息给物流公司。
	 * @param OrderInfoDTO 订单信息DTO
	 * @throws BizException 
	 * 
	 */
	private void sendMQForFinalPaymentSucc(OrderInfoDTO orderInfoDTO) throws BizException {
		SourceShipperEntity shipperEntity = getSourceShipperById(orderInfoDTO.getSourceId());
		
		//推送消息给物流公司
		PushMsgDto dto=new PushMsgDto();
		dto.setBizId(orderInfoDTO.getId());
		dto.setContent(MsgCons.M_28014);
		dto.setMemberId(shipperEntity.getAssignMemberId());
		dto.setMsgType(PushConstants.MSG_TYPE_7);
		pushMsgMQ(dto);
		
		//统计物流公司接单数量
		sendLogisticsOrderReceivingCountMQ(shipperEntity.getAssignMemberId());
	}
    
	
	/**
	 * 物流信息 加验货信息
	 * @param orderNo
	 * @throws BizException 
	 */
	@Override
	public ApiResult<OrderInfoTransAndSourceExamine> queryOrderInfoTransAndSourceExamine(Map<String, Object>  map)
			throws BizException {
		OrderInfoTransAndSourceExamine vo=new OrderInfoTransAndSourceExamine();
		SourceExamineDTO dto=baseDao.queryForObject("SourceExamine.queryByCondition", map, SourceExamineDTO.class);
		vo=baseDao.queryForObject("OrderInfoTrans.ContactsInfo",map,OrderInfoTransAndSourceExamine.class);
		if(vo==null){
			throw new BizException(MsgCons.C_24039, MsgCons.M_24039);
		}
		List<OrderInfoTransDTO> list=baseDao.queryForList("OrderInfoTrans.queryByCondition",map,OrderInfoTransDTO.class);
		vo.setOrderInfoTransList(list);
		vo.setSourceExamineDTO(dto);
		ApiResult<OrderInfoTransAndSourceExamine> apiResult = new ApiResult<OrderInfoTransAndSourceExamine>(vo,MsgCons.C_10000,MsgCons.M_10000);
		return apiResult;
	}


	/**
	 * 物流信息
	 * @param orderNo
	 * @throws BizException 
	 */
	@Override
	public ApiResult<List<OrderInfoTransVo>> queryOrderInfoTrans(
			Map<String, Object>  map) throws BizException {
		List<OrderInfoTransVo> list=baseDao.queryForList("OrderInfoTrans.queryByCondition",map,OrderInfoTransVo.class);
		ApiResult<List<OrderInfoTransVo>>  apiResult = new ApiResult<List<OrderInfoTransVo>>(list,MsgCons.C_10000,MsgCons.M_10000);
		return apiResult;
	}



	@Override
	@Transactional
	public ApiResult<Map<String, Object>> scanPrePaymtenOverdueAndHandler() throws BizException {
		//查询支付预付款过期的订单信息
		List<OrderInfoDTO> orderInfos = baseDao.queryForList("OrderInfo.queryPrePaymtenOverdue", null, OrderInfoDTO.class);
		int size = orderInfos.size();
		
		List<String> orderNos = new ArrayList<String>(size);
		
		if (size > 0) {
			//支付超时处理
			handlePrePaymtenOverdue(orderInfos);
			
			//记录更新的订单信息编号
			for (OrderInfoDTO orderInfo : orderInfos) {
				orderNos.add(orderInfo.getOrderNo());
			}
		}
		
		//组装返回数据
		ApiResult<Map<String, Object>> apiReult = new ApiResult<Map<String,Object>>();
		Map<String, Object> resultMap= new HashMap<String, Object>(2);
		resultMap.put("record", size);
		resultMap.put("orderNos", orderNos);
		apiReult.setResult(resultMap);
		return apiReult;
	}
	
	
	/** 处理支付预付款超时
	 * @param orderInfos
	 * @throws BizException
	 */
	private void handlePrePaymtenOverdue(List<OrderInfoDTO> orderInfos) throws BizException {
		//===1.数据组装====
		int originalOrderInfoCouont = orderInfos.size();
		//存放需要更新的预订单Id集合
		List<Integer> orderBeforeIds = new ArrayList<Integer>(originalOrderInfoCouont);
		//存放需要更新的订单信息Id集合
		List<Integer> orderIds = new ArrayList<Integer>(originalOrderInfoCouont);
		for (OrderInfoDTO orderInfo : orderInfos) {
			orderBeforeIds.add(orderInfo.getOrderBeforeId());
			orderIds.add(orderInfo.getId());
		}
		
		
		//===2.更新预订单状态====  暂时取消对预订单状态的更新。
		//updateOrderBeforeSourceStatusByIds(orderBeforeIds, OrderBeforeEnum.CLOSED.getCode());
		
		//===3.更新订单信息状态====
		Byte orderStatus = OrderInfoStatusEnum.CLOSED.getCode();
		Byte closeReason = OrderInfoTransCloseReasonEnum.PRE_PAYMENT_OVERDUE.getCode();
		updateOrderInfoOrderStatusByIds(orderIds, orderStatus, closeReason);
		
		//===4.给车主推送消息
		pushMsgToDriverForPrePaymtenOverdue(orderInfos);
	}
	
	/**支付预付款超时，给车主推送消息。
	 * @param orderInfos 订单信息DTO集合
	 */
	private void pushMsgToDriverForPrePaymtenOverdue(List<OrderInfoDTO> orderInfos) {
		for (OrderInfoDTO orderInfoDTO : orderInfos) {
			PushMsgDto dto = new PushMsgDto();
			dto.setBizId(orderInfoDTO.getOrderBeforeId());
			dto.setContent(MsgCons.M_28021);
			dto.setMemberId(orderInfoDTO.getDriverMemberId());
			dto.setMsgType(PushConstants.MSG_TYPE_0);
			pushMsgMQ(dto);
		}
	}

	
	/** 根据id集合更新预订单货源状态
	 * @param ids id集合
	 * @param sourceStatus 更新的状态
	 * @return 影响的行数
	 * @throws BizException  如果更新的数量和id数量不一致，则抛出此异常。
	 */
	private int updateOrderBeforeSourceStatusByIds(List<Integer> ids, Byte sourceStatus) throws BizException {
		Map<String, Object> updateParam = new HashMap<String, Object>();
		updateParam.put("ids", ids);
		updateParam.put("updateUserId", "system");
		updateParam.put("sourceStatus", sourceStatus);
		int updateCount =  baseDao.execute("OrderBefore.updateSourceStatusByIds", updateParam);
		
		//如果更新数量的判断
		if (ids.size() != updateCount) {
			throw new BizException(MsgCons.C_24034, MsgCons.M_24014);
		}
		
		return updateCount;
	}
	
	
	/** 根据id集合更新订单信息多种状态
	 * @param ids id集合
	 * @param orderStatus 运单状态
	 * @param closeReason 运单已关闭时的原因。如果物流状态不是已关闭，则传null。
	 * @return 影响的行数
	 * @throws BizException  如果更新的数量和id数量不一致，则抛出此异常。
	 */
	private int updateOrderInfoOrderStatusByIds(List<Integer> ids, Byte orderStatus, Byte closeReason) throws BizException {
		Map<String, Object> updateParam = new HashMap<String, Object>();
		updateParam.put("ids", ids);
		updateParam.put("orderStatus", orderStatus);
		updateParam.put("closeReason", closeReason);
		updateParam.put("updateUserId", "system");
		int updateCount =  baseDao.execute("OrderInfo.updateOrderStatusByIds", updateParam);
		
		//如果更新数量的判断
		if (ids.size() != updateCount) {
			throw new BizException(MsgCons.C_24034, MsgCons.M_24014);
		}
		
		return updateCount;
	}
	
	/** 构造物流信息并批量保存
	 * @param orderInfos 订单信息集合
	 * @param transStatus 物流状态
	 * @param transCloseReason 物流状态为已关闭时的原因。如果物流状态不是已关闭，则传null。
	 */
	@SuppressWarnings("unchecked")
	private void constructTransAndBatchSave(List<OrderInfoDTO> orderInfos, Byte transStatus,
			Byte transCloseReason){
		int len = orderInfos.size();
		Map<String, Object>[] saveTransParams = new Map[len];
		Date operateTime = new Date();
		String createUserId = "system";
		Byte transReason = OrderInfoTransCloseReasonEnum.REPEAL_PRE_PAYMTEN.getCode();
		for (int i = 0 ; i < len; i++) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sourceId", orderInfos.get(i).getSourceId());
			param.put("orderInfoId", orderInfos.get(i).getId());
			param.put("orderNo", orderInfos.get(i).getOrderNo());
			param.put("transStatus", transStatus);
			param.put("closeReason", transReason);
			param.put("operateTime", operateTime);
			param.put("createUserId", createUserId);
			saveTransParams[i] = param;
		}
		baseDao.batchUpdate("OrderInfoTrans.basicInsert", saveTransParams);
	}


	@Override
	public ApiResult<Map<String, Object>> payPrePaymtenOverdue(String[] orderNos) throws BizException {
		//查询支付预付款过期的订单信息
		Map<String, Object> paramMap = new HashMap<String, Object>(1);
		paramMap.put("orderNos", orderNos);
		List<OrderInfoDTO> orderInfos = baseDao.queryForList("OrderInfo.queryByOrderNos", paramMap, OrderInfoDTO.class);
		int size = orderInfos.size();
		if (size > 0) {
			//支付超时处理
			handlePrePaymtenOverdue(orderInfos);
		}
		
		//组装返回数据
		ApiResult<Map<String, Object>> apiReult = new ApiResult<Map<String,Object>>();
		Map<String, Object> resultMap= new HashMap<String, Object>(1);
		resultMap.put("record", size);
		apiReult.setResult(resultMap);
		return apiReult;
	}
	
	/**
	 * 发送MQ 给物流公司统计接单次数
	 * @param memberId 物流公司的会员id
	 */
	private void sendLogisticsOrderReceivingCountMQ(Integer memberId)throws BizException {
		if(null == memberId){
			return;
		}
		MemberCountDTO mqVo=new MemberCountDTO();
		mqVo.setMemberId(memberId);
		mqVo.setConfirmSourceCount(1);
		Message msg = new Message(countProducer.getProperties().getProperty(MqConstants.TOPIC),MqConstants.TAG,SerializeUtil.serialize(mqVo));
		msg.setKey(mqVo.getMemberId().toString());
		try {
			logger.info("接受订单物流公司统计次数MQ发送时间:{},memberId:{},message:{}",new Object[]{DateFormatUtils.format(new Date(), DateUtil.DATE_FORMAT_DATETIME),memberId,JSON.toJSONString(msg)});
			countProducer.send(msg);
		} catch (Exception e) {
			logger.error("接受订单物流公司统计次数MQ error",e);
			//异常数据存入表 mq_error
			this.insertMqError(mqVo,memberId,MqConstants.TOPIC_ORDER_INFO);
		}
	}

}
