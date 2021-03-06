package cn.gdeng.nst.server.order.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

import cn.gdeng.nst.api.dto.order.OrderBasicDTO;
import cn.gdeng.nst.api.dto.order.OrderBeforeBasicDTO;
import cn.gdeng.nst.api.dto.order.OrderBeforeDTO;
import cn.gdeng.nst.api.dto.order.OrderBeforeDetailDTO;
import cn.gdeng.nst.api.dto.task.PushMsgDto;
import cn.gdeng.nst.api.dto.task.TaskDto;
import cn.gdeng.nst.api.server.order.OrderBeforeService;
import cn.gdeng.nst.dao.BaseDao;
import cn.gdeng.nst.entity.nst.MemberInfoEntity;
import cn.gdeng.nst.entity.nst.MqError;
import cn.gdeng.nst.entity.nst.OrderAgentEntity;
import cn.gdeng.nst.entity.nst.OrderBeforeEntity;
import cn.gdeng.nst.entity.nst.OrderInfoEntity;
import cn.gdeng.nst.entity.nst.OrderInfoTransEntity;
import cn.gdeng.nst.entity.nst.SourceLogEntity;
import cn.gdeng.nst.entity.nst.SourceShipperEntity;
import cn.gdeng.nst.enums.MemberInfoStatusEnum;
import cn.gdeng.nst.enums.MqConstants;
import cn.gdeng.nst.enums.MsgCons;
import cn.gdeng.nst.enums.NstRuleEnum;
import cn.gdeng.nst.enums.OperateEnum;
import cn.gdeng.nst.enums.OrderAgentStatusEnum;
import cn.gdeng.nst.enums.OrderBeforeEnum;
import cn.gdeng.nst.enums.OrderInfoOrderStatusEnum;
import cn.gdeng.nst.enums.OrderInfoTransCloseReasonEnum;
import cn.gdeng.nst.enums.OrderInfoTransStatusEnum;
import cn.gdeng.nst.enums.PushConstants;
import cn.gdeng.nst.enums.SourceStatusEnum;
import cn.gdeng.nst.enums.SourceTypeEnum;
import cn.gdeng.nst.util.admin.web.DateUtil;
import cn.gdeng.nst.util.server.AddrUtils;
import cn.gdeng.nst.util.server.InterfaceUtil;
import cn.gdeng.nst.util.server.JacksonUtil;
import cn.gdeng.nst.util.web.api.ApiPage;
import cn.gdeng.nst.util.web.api.ApiResult;
import cn.gdeng.nst.util.web.api.BizException;
import cn.gdeng.nst.util.web.api.Constant;
import cn.gdeng.nst.util.web.api.Des3;
import cn.gdeng.nst.util.web.api.GSONUtils;
import cn.gdeng.nst.util.web.api.GdProperties;
import cn.gdeng.nst.util.web.api.ObjectResult;
import cn.gdeng.nst.util.web.api.ParamProcessUtil;
import cn.gdeng.nst.util.web.api.SerializeUtil;

@Service
public class OrderBeforeServiceImpl implements OrderBeforeService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private InterfaceUtil interfaceUtil;

	@Autowired
	private GdProperties gdProperties;

	@Resource
	private BaseDao<?> baseDao;
	@Resource
	private ProducerBean taskProducer;

	@Resource
	private ProducerBean msgPushProducer;

	@Resource
	private ProducerBean countProducer;

	/**
	 * 
	 * @Description: 新增订单
	 * @param beforeDTO
	 *            必须字段 sourceId
	 * @return
	 * @throws BizException
	 *
	 */
	@Override
	@Transactional
	public ApiResult<Integer> createOrderBefore(OrderBeforeEntity beforeDTO) throws BizException {
		ApiResult<Integer> apiResult = new ApiResult<Integer>();

		// step1判断与货源相关信息
		SourceShipperEntity entity = this.updateSourceShipperStatus(beforeDTO.getSourceId(), SourceStatusEnum.RELEASED.getCode(), SourceStatusEnum.STAY_FOR_CONFIRM.getCode(), MsgCons.C_24029, MsgCons.M_24029);

		// step2查询相关信息
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", beforeDTO.getDriverMemberId());
		// 查询车主信息
		MemberInfoEntity memberInfoEntity = baseDao.queryForObject("OrderBefore.queryMemberById", map, MemberInfoEntity.class);
		if ( memberInfoEntity == null) {
			throw new BizException(MsgCons.C_21018, MsgCons.M_21018);
		} else if ( memberInfoEntity.getStatus().compareTo(MemberInfoStatusEnum.ENABLE.getCode()) != 0) {
			throw new BizException(MsgCons.C10000, MsgCons.M10000);
		}

		// 查询货主信息
		MemberInfoEntity sourceMemberInfo = null, originalSourceMemberInfo = null;
		Map<String, Object> memberMap = new HashMap<>();
		memberMap.put("memberId", entity.getMemberId());
		// 查询原始货主信息
		originalSourceMemberInfo = baseDao.queryForObject("OrderBefore.queryMemberById", memberMap, MemberInfoEntity.class);
		if ( originalSourceMemberInfo == null) {
			throw new BizException(MsgCons.C_24022, MsgCons.M_24022);
		}
		// 统一接单时间
		Date createDate = new Date();
		// step2 新增预运单记录
		beforeDTO.setCreateTime(createDate);
		beforeDTO = this.saveOrderBeforeEntity(beforeDTO, memberInfoEntity, entity);

		// step3 生产信息订单 消息推送
		// 货主直发
		if ( entity.getNstRule().compareTo(NstRuleEnum.SELF.getCode()) == 0) {
			// 推送消息给货主
			msgPushToDriver(beforeDTO.getSourceId(), originalSourceMemberInfo.getId(), MsgCons.M_28000, PushConstants.MSG_TYPE_3);

		}
		// 货物流公司直发
		if ( entity.getNstRule().compareTo(NstRuleEnum.SELFLOGST.getCode()) == 0) {
			isDistribution(beforeDTO, entity, originalSourceMemberInfo);
			// 推送消息给物流公司
			msgPushToDriver(beforeDTO.getId(), originalSourceMemberInfo.getId(), MsgCons.M_28000, PushConstants.MSG_TYPE_0);
		}
		if ( entity.getNstRule().compareTo(NstRuleEnum.DISTRIBUTED.getCode()) == 0) {
			// 分配过
			memberMap.put("memberId", entity.getAssignMemberId());
			sourceMemberInfo = baseDao.queryForObject("OrderBefore.queryMemberById", memberMap, MemberInfoEntity.class);
			if ( sourceMemberInfo == null) {
				throw new BizException(MsgCons.C_24022, MsgCons.M_24022);
			}

			isDistribution(beforeDTO, entity, sourceMemberInfo);
			// 推送消息给货主
			msgPushToDriver(beforeDTO.getSourceId(), originalSourceMemberInfo.getId(), MsgCons.M_28000, PushConstants.MSG_TYPE_3);
			// 推送消息给物流公司
			msgPushToDriver(beforeDTO.getId(), sourceMemberInfo.getId(), MsgCons.M_28000, PushConstants.MSG_TYPE_0);

		}
		// ====保存订单日志====
		String logDesc = OperateEnum.APPLYFREIGHTGOODS.getName();
		saveLog(beforeDTO.getSourceId(), logDesc, beforeDTO.getDriverMemberId().toString());

		// step 3 发送定时任务MQ
		this.sendDriverReceiveOrderMsg(beforeDTO.getId(), beforeDTO.getDriverMemberId());
		return apiResult;
	}

	/**
	 * 
	 * @Description: 取消订单 货源只有在待确认的状态，司机才能取消，其他情况司机没有取消按钮
	 * @param map
	 * @return
	 * @throws BizException
	 *
	 */
	@Override
	@Transactional
	public ApiResult<Integer> cancleOrderBefore(Map<String, Object> map) throws BizException {
		ApiResult<Integer> apiResult = new ApiResult<Integer>();
		// 查询预定单
		map.put("id", map.get("orderBeforeId"));
		OrderBeforeEntity entity = baseDao.queryForObject("OrderBefore.queryOrderBeforeById", map, OrderBeforeEntity.class);
		if ( entity == null) {
			throw new BizException(MsgCons.C_23010, MsgCons.M_23010);
		}
		// step3 修改货源状态为"已发布"
		SourceShipperEntity sourceShipperEntity = this.updateSourceShipperStatus(entity.getSourceId(), SourceStatusEnum.STAY_FOR_CONFIRM.getCode(), SourceStatusEnum.RELEASED.getCode(), MsgCons.C_23010, MsgCons.M_23010);

		map.put("sourceId", sourceShipperEntity.getId());
		// step1 司机取消订单 :driverMemberId :id :sourceStatus :sourceId
		map.put("sourceStatus", OrderBeforeEnum.CANCEL.getCode().intValue());
		map.put("version", sourceShipperEntity.getVersion());
		if ( baseDao.execute("OrderBefore.update", map) != 1) {
			// 订单异常
			throw new BizException(MsgCons.C_23010, MsgCons.M_23010);
		}
		// step2 货源被分配给物流公司 修改信息订单状态"已关闭"
		if ( sourceShipperEntity.getNstRule().compareTo( NstRuleEnum.DISTRIBUTED.getCode()) == 0
					|| sourceShipperEntity.getNstRule().compareTo(NstRuleEnum.SELFLOGST.getCode()) == 0) {
			Map<String, Object> mapAgent = new HashMap<>();
			mapAgent.put("sourceId", entity.getSourceId());
			mapAgent.put("orderStatus", OrderAgentStatusEnum.COMPLETED.getCode());
			mapAgent.put("orderBeforeId", entity.getId());
			mapAgent.put("updateUserId", entity.getDriverMemberId());
			mapAgent.put("payStatus", (short) 3);
			mapAgent.put("logisticTime", ParamProcessUtil.getNewDate());
			if ( baseDao.execute("OrderAgent.update", mapAgent) != 1) {
				throw new BizException(MsgCons.C_24021, MsgCons.M_24021);
			}
		}

		// ====保存订单日志====
		String logDesc = OperateEnum.CANCELFREIGHTGOODS.getName();
		saveLog(entity.getSourceId(), logDesc, entity.getDriverMemberId().toString());
		return apiResult;
	}

	/**
	 *
	 * 
	 * @Description: 车主查询订单详情
	 * @param map
	 * @return
	 * @throws BizException
	 *
	 */
	@Override
	public ApiResult<OrderBeforeDetailDTO> queryOrderBefore(Map<String, Object> map) throws BizException {
		ApiResult<OrderBeforeDetailDTO> apiResult = new ApiResult<>();
		// step1 查询订单 订单ID
		// OrderBeforeEntity beforeEntity=this.queryOrderBeforeById(map);
		Map<String, Object> orderBeforeMap = new HashMap<>();
		orderBeforeMap.put("id", map.get("orderBeforeId"));
		OrderBeforeEntity beforeEntity = baseDao.queryForObject("OrderBefore.queryOrderBeforeById", orderBeforeMap, OrderBeforeEntity.class);
		if ( beforeEntity == null) {
			throw new BizException(MsgCons.C_23005, MsgCons.M_23005);
		}
		OrderBeforeDetailDTO vo = null;
		map.put("sourceId", beforeEntity.getSourceId());
		vo = baseDao.queryForObject("OrderBefore.querySourceByIds", map, OrderBeforeDetailDTO.class);
		if ( vo == null) {
			throw new BizException(MsgCons.C_24030, MsgCons.M_24030);
		}
		// 生成全地址
		try {
			AddrUtils.generalFullAddrAndSet(vo);
		}
		catch (Exception e) {
			logger.error("", e);
			throw new BizException(MsgCons.C_20000, MsgCons.M_20000);
		}

		apiResult.setResult(vo);
		return apiResult;
	}

	/**
	 * 
	 * @Description: 分页查询订单
	 * @param page
	 * @return
	 * @throws BizException
	 *
	 */
	@Override
	public ApiResult<ApiPage> queryOrderBeforePage(ApiPage page) throws BizException {
		ApiResult<ApiPage> apiResult = new ApiResult<ApiPage>();
		Map<String, Object> map = page.getParaMap();
		int total = baseDao.queryForObject("OrderBefore.getTotalNew", map, Integer.class);
		// 获取结果集
		if ( total < 1) {
			page.setRecordCount(0);
			return apiResult.setResult(page);
		}
		List<OrderBeforeBasicDTO> list = baseDao.queryForList("OrderBefore.queryByConditionPageNew", map, OrderBeforeBasicDTO.class);
		page.setRecordList(list).setRecordCount(total);
		// 将分页封装到返回结果
		apiResult.setResult(page);
		return apiResult;
	}

	@Override
	public ApiResult<ApiPage> queryOrderBeforePageV2(ApiPage page) throws BizException {
		ApiResult<ApiPage> apiResult = new ApiResult<ApiPage>();
		Map<String, Object> map = page.getParaMap();
		int total = baseDao.queryForObject("OrderBefore.getTotalNewV2", map, Integer.class);
		// 获取结果集
		if ( total < 1) {
			page.setRecordCount(0);
			return apiResult.setResult(page);
		}
		List<OrderBeforeBasicDTO> list = baseDao.queryForList("OrderBefore.queryByConditionPageNewV2", map, OrderBeforeBasicDTO.class);
		page.setRecordList(list).setRecordCount(total);
		// 将分页封装到返回结果
		apiResult.setResult(page);
		return apiResult;
	}



	@Override
	@Transactional
	public ApiResult<Map<String, Object>> rejectOrderBefore(Map<String, Object> param) throws BizException {
		// 判断司机是否已经取消订单
		OrderBeforeEntity obParamEntity = new OrderBeforeEntity();
		obParamEntity.setId(Integer.valueOf(param.get("orderBeforeId").toString()));
		OrderBeforeEntity obResultEntity = baseDao.find(OrderBeforeEntity.class, obParamEntity);
		if ( obResultEntity == null) {
			throw new BizException(MsgCons.C_23005, MsgCons.M_23005);
		}
		if ( obResultEntity.getSourceStatus().equals(OrderBeforeEnum.CANCEL.getCode())) {
			throw new BizException(MsgCons.C_23011, MsgCons.M_23011);
		}

		// 更新货源状态
		int ssRecord = baseDao.execute("SourceShipper.updateSourceShipperStatusById", param);
		if ( ssRecord == 0) {
			throw new BizException(MsgCons.C_23000, MsgCons.M_23000);
		}

		// 更新预订单状态
		int obRecord = baseDao.execute("OrderBefore.updateOrderBeforeStatusById", param);
		if ( obRecord == 0) {
			throw new BizException(MsgCons.C_23000, MsgCons.M_23000);
		}

		// ====保存货源日志====
		String logDesc = OperateEnum.GOODSOWNERREFUSE.getName();
		// 如果是被分配的，则显示为物流公司操作。
		SourceShipperEntity souShipEnt = new SourceShipperEntity();
		souShipEnt.setId(Integer.valueOf(param.get("sourceShipperId").toString()));
		SourceShipperEntity resultEntity = baseDao.find(SourceShipperEntity.class, souShipEnt);
		if ( resultEntity.getAssignMemberId() != null 
					&& resultEntity.getNstRule().compareTo(NstRuleEnum.SELF.getCode()) != 0) {
			logDesc = OperateEnum.LOGISTICSREFUSE.getName();
			// 更新信息订单状态。ps：只有分配给物流公司的货源才有信息订单。
			updateOrderAgentStatusForReject(param);
		}
		;
		saveLog(souShipEnt.getId(), logDesc, param.get("updateUserId").toString());

		// 返回操作结果
		ApiResult<Map<String, Object>> apiResult = new ApiResult<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("record", obRecord);
		logger.debug("rejectOrderBefore执行成功！");
		return apiResult.setResult(resultMap);
	}



	/**
	 * 
	 * @Description: 判断司机是否符合接单要求
	 * @param param
	 *            driverMemberId 司机ID id:货源ID
	 * @return
	 * @throws BizException
	 *
	 */
	@Override
	public ApiResult<Boolean> queryOrderQuantity(Map<String, Object> param) throws BizException {
		Map<String, Object> memberMap = new HashMap<>();
		memberMap.put("memberId", param.get("driverMemberId"));
		// 查询司机信息
		// 判断实际联系人是否有
		MemberInfoEntity memberInfoEntity = baseDao.queryForObject("OrderBefore.queryMemberById", memberMap, MemberInfoEntity.class);
		if ( memberInfoEntity == null || !ParamProcessUtil.stringIsEmpty(memberInfoEntity.getUserName())) {
			throw new BizException(MsgCons.C_21019, MsgCons.M_21019);
		} else if ( memberInfoEntity.getStatus().compareTo(MemberInfoStatusEnum.ENABLE.getCode()) != 0) {
			throw new BizException(MsgCons.C10000, MsgCons.M10000);
		}
		// 判断司机是否有车
		int total = baseDao.queryForObject("OrderBefore.getMemberCarNumber", memberMap, Integer.class);
		if ( total <= 0) {
			throw new BizException(MsgCons.C_22028, MsgCons.M_22028);
		}
		param.put("sourceStatus", 2);
		param.put("beforeSourceStatus", 1);
		// ：driverMemberId ：sourceStatus
		List<OrderBeforeDTO> orderBeforeDTOs = baseDao.queryForList("OrderBefore.queryOrderQuantity", param, OrderBeforeDTO.class);
		if ( orderBeforeDTOs != null && orderBeforeDTOs.size() >= 15) {
			throw new BizException(MsgCons.C_23006, MsgCons.M_23006);
		}
		if ( orderBeforeDTOs == null || orderBeforeDTOs.isEmpty() || orderBeforeDTOs.size() < 4) {
			return new ApiResult<Boolean>().setResult(true);
		}
		// same：同城接单总数     different：干线接单总数
		int same = 0, different = 0;
		// 根据货源查询 ：id
		SourceShipperEntity sourceShipperEntity = baseDao.queryForObject("OrderBefore.querySourceShipper", param, SourceShipperEntity.class);
		if ( sourceShipperEntity == null || sourceShipperEntity.getSourceStatus().compareTo(SourceStatusEnum.RELEASED.getCode()) != 0) {
			throw new BizException(MsgCons.C_24021, MsgCons.M_24021);
		}

		if ( sourceShipperEntity.getSourceType().compareTo(SourceTypeEnum.TRUNK.getCode()) == 0) {
			different++;
		} else {
			same++;
		}
		for (OrderBeforeDTO orderBeforeDTO : orderBeforeDTOs) {
			if ( orderBeforeDTO.getSourceType().compareTo((byte)SourceTypeEnum.TRUNK.getCode().intValue()) == 0) {
				different++;
			} else {
				same++;
			}
		}
		if ( same >= 11 || different >= 6) {
			throw new BizException(MsgCons.C_23006, MsgCons.M_23006);
		}
		return new ApiResult<Boolean>().setResult(true);
	}

	@Override
	public void checkoutMember(String memberId) throws BizException {
		Map<String, Object> param = new HashMap<>();
		param.put("memberId", memberId);
		// TODO Auto-generated method stub
		MemberInfoEntity memberInfoEntity = baseDao.queryForObject("OrderBefore.queryMemberById", param, MemberInfoEntity.class);
		if ( memberInfoEntity == null) {
			throw new BizException(MsgCons.C_21021, MsgCons.M_21021);
		} else if ( memberInfoEntity.getStatus().compareTo((byte) 1) != 0) {
			throw new BizException(MsgCons.C10000, MsgCons.M10000);
		}
	}

	// 确认送达
	@Override
	@Transactional
	public ApiResult<Boolean> deliveryConfirmation(Map<String, Object> param) throws BizException {
		Integer memberId = Integer.parseInt(param.get("driverMemberId").toString());
		// 判断货源状态
		Map<String, Object> orderBeforeMap = new HashMap<>();
		orderBeforeMap.put("id", param.get("orderBeforeId"));
		OrderBeforeEntity orderBeforeEntity = baseDao.queryForObject("OrderBefore.queryOrderBeforeById", orderBeforeMap, OrderBeforeEntity.class);
		if ( orderBeforeEntity == null) {
			throw new BizException(MsgCons.C_24039, MsgCons.M_24039);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("id", orderBeforeEntity.getSourceId());
		SourceShipperEntity sourceShipperEntity = baseDao.queryForObject("OrderBefore.querySourceShipper", map, SourceShipperEntity.class);
		if ( sourceShipperEntity == null) {
			throw new BizException(MsgCons.C_24033, MsgCons.M_24033);
		}

		OrderInfoEntity orderInfoEntity = baseDao.queryForObject("OrderBefore.queryOrderInfo", param, OrderInfoEntity.class);
		if ( orderInfoEntity == null) {
			throw new BizException(MsgCons.C_24039, MsgCons.M_24039);
		}
		if ( orderInfoEntity.getTransStatus() == null 
					|| orderInfoEntity.getTransStatus().compareTo(OrderInfoTransStatusEnum.SENT.getCode()) != 0) {
			// 不是发货状态，不可以点击为送达
			throw new BizException(MsgCons.C_24038, MsgCons.M_24038);
		}

		Map<String, Object> orderInfoMap = new HashMap<>();
		orderInfoMap.put("id", orderInfoEntity.getId());
		orderInfoMap.put("transStatus", OrderInfoTransStatusEnum.GOOD_ARRIVED.getCode());
		orderInfoMap.put("driverMemberId", memberId);
		// 更新orderInfo
		int orderInfoRecord = baseDao.execute("OrderBefore.updateOrderInfo", orderInfoMap);
		if ( orderInfoRecord == 0) {
			throw new BizException(MsgCons.C_24038, MsgCons.M_24038);
		}
		// 添加物流信息
		OrderInfoTransEntity orderInfoTransEntity = new OrderInfoTransEntity();
		orderInfoTransEntity.setCreateTime(new Date());
		orderInfoTransEntity.setCreateUserId(memberId.toString());
		orderInfoTransEntity.setIsDeleted((byte) 0);
		orderInfoTransEntity.setOperateTime(new Date());
		orderInfoTransEntity.setOrderInfoId(orderInfoEntity.getId());
		orderInfoTransEntity.setOrderNo(orderInfoEntity.getOrderNo());
		orderInfoTransEntity.setSourceId(orderInfoEntity.getSourceId());
		orderInfoTransEntity.setTransStatus(OrderInfoTransStatusEnum.GOOD_ARRIVED.getCode());
		Long id = baseDao.persist(orderInfoTransEntity, Long.class);
		if ( id == null) {
			throw new BizException(MsgCons.C_24038, MsgCons.M_24038);
		}
		// 友盟通知
		// 推送消息给物流公司
		msgPushToDriver(orderBeforeEntity.getId(), sourceShipperEntity.getAssignMemberId(), MsgCons.M_28019, PushConstants.MSG_TYPE_5);

		// 通知谷登平台SENDMAP
		Map<String, Object> sendMap = new HashMap<String, Object>();
		sendMap.put("orderNo", orderBeforeEntity.getSourceId());
		sendMap.put("userId", memberId);
		sendMap.put("type", "5");
		informOrderCenter(sendMap, 2, memberId, MsgCons.M_28022);

		return new ApiResult<Boolean>().setResult(true);
	}

	/**
	 * 车主 验货超时 3 天
	 */
	@Override
	public void examineCargoTimeOut() {
		// 查询
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		now.set(Calendar.DATE, now.get(Calendar.DATE) - 3);
		// now.set(Calendar.MINUTE, now.get(Calendar.MINUTE)-30);
		Date date = now.getTime();

		Map<String, Object> map = new HashMap<>();
		map.put("updateTime", date);
		List<OrderBasicDTO> orderBasicDTOs = baseDao.queryForList("OrderBefore.queryExamineCargoTimeOut", map, OrderBasicDTO.class);
		updateExamineCargoTimeOut(orderBasicDTOs);
	}

	// 通知订单中心
	private boolean informOrderCenter(Map<String, Object> sendMap, Integer type, Integer memberId, String remark) {
		String result = null;
		ObjectResult objectResult = null;
		try {
			result = interfaceUtil.sendSourceExaminePassStatus(des3Map(sendMap));
			result = Des3.decode(result);
			// 将解密后的结果 装换objectResult
			objectResult = JacksonUtil.str2Obj(result, ObjectResult.class);
			if ( objectResult.getStatusCode() == 0) {
				// 成功
				logger.info("informOrderCenter 通知成功");
				return true;
			}

		}
		catch (Exception e) {
			logger.error("informOrderCenter 通知调用失败   出现异常" + e.toString());
		}
		// 失败
		logger.error("informOrderCenter 通知失败  未进入异常");
		// 存入数据库
		insertMqError(type, sendMap, memberId, remark);
		return false;
	}



	/**
	 * 
	 * @Description: 存入错误表
	 * @param type
	 *            1 APP推送 2 定时任务 验货通知 3 系统同步 确认送达
	 * @param content
	 * @param memberId
	 * @param remark
	 *
	 */
	private void insertMqError(Integer type, Object content, Integer memberId, String remark) {
		MqError mqError = new MqError();
		mqError.setMemberId(memberId);
		mqError.setContent(GSONUtils.toJson(content, false));
		mqError.setRemark(remark);
		switch (type) {
		case 1:// APP推送
			mqError.setBizType(MqConstants.BIZ_TYPE_1);
			mqError.setTopic(MqConstants.TOPIC_PUSH);
			mqError.setCreateUserId(1);
			break;
		case 2:// 定时任务 验货通知
			mqError.setBizType(MqConstants.BIZ_TYPE_2);
			mqError.setTopic(MqConstants.TOPIC_TASK);
			mqError.setHttpUrl(gdProperties.getsourceExaminePassStatusUrl());
			break;
		case 3:// 系统同步 确认送达
			mqError.setBizType(MqConstants.BIZ_TYPE_2);
			mqError.setTopic(MqConstants.TOPIC_MEMBER_INFO);
			mqError.setHttpUrl(gdProperties.getsourceExaminePassStatusUrl());
			break;
		default:
			return;
		}
		baseDao.execute("MqError.insert", mqError);
	}

	// 更新验货超时相关表
	private void updateExamineCargoTimeOut(List<OrderBasicDTO> orderBasicDTOs) {
		for (OrderBasicDTO orderBasicDTO : orderBasicDTOs) {
			Map<String, Object> orderInfoMap = new HashMap<>();
			orderInfoMap.put("id", orderBasicDTO.getOrderInfoId());
			orderInfoMap.put("orderStatus", OrderInfoOrderStatusEnum.CLOSED.getCode());
			orderInfoMap.put("closeReason", OrderInfoTransCloseReasonEnum.EXAMINE_GOODS_OVERDUE.getCode());
			if ( baseDao.execute("OrderBefore.updateOrderInfo", orderInfoMap) != 1) {
				logger.info(" OrderBefore.updateOrderInfo ----  " + orderBasicDTO.getOrderInfoId());
				continue;
			}
			// 友盟通知
			try {
				// 给物流公司 已关闭标签页
				msgPushToDriver(orderBasicDTO.getOrderBeforeId(), orderBasicDTO.getCompanyMemberId(), MsgCons.M_28020, PushConstants.MSG_TYPE_8);
				// 给车主
				msgPushToDriver(orderBasicDTO.getOrderBeforeId(), orderBasicDTO.getDriverMemberId(), MsgCons.M_28020, PushConstants.MSG_TYPE_0);

			}
			catch (BizException e) {
				logger.info("updateExamineCargoTimeOut ----  " + e.getMsg());
			}
			// 通知其他地方
			// 通知谷登平台SENDMAP
			Map<String, Object> sendMap = new HashMap<String, Object>();
			sendMap.put("orderNo", orderBasicDTO.getSourceId());
			sendMap.put("cancelReason", "车主 验货超时 3 天");
			sendMap.put("userId", "0");
			sendMap.put("type", "4");
			informOrderCenter(sendMap, 2, null, MsgCons.M_28023);
		}
	}

	private Map<String, Object> des3Map(Map<String, Object> map) {
		String json = GSONUtils.getGson().toJson(map);
		Map<String, Object> temp = new HashMap<String, Object>();
		try {
			temp.put("param", Des3.encode(json));
		}
		catch (Exception e) {
			return map;
		}
		return temp;
	}

	/**
	 * 
	 * @Description://友盟消息推送
	 * @param bizId
	 * @param memberId
	 * @param msgCons
	 * @param msgType
	 * @throws BizException
	 *
	 */
	private void msgPushToDriver(Integer bizId, Integer memberId, String msgCons, Integer msgType) throws BizException {
		PushMsgDto dto = new PushMsgDto();
		dto.setBizId(bizId);
		dto.setContent(msgCons);
		dto.setMemberId(memberId);
		dto.setMsgType(msgType);

		Message msg = new Message(msgPushProducer.getProperties().getProperty(MqConstants.TOPIC), MqConstants.TAG, SerializeUtil.serialize(dto));
		msg.setKey(dto.getMemberId().toString());
		try {
			logger.info(msgCons, new Object[] { DateFormatUtils.format(new Date(), DateUtil.DATE_FORMAT_DATETIME), JSON.toJSONString(msg) });
			countProducer.send(msg);
		}
		catch (Exception e) {
			logger.error(msgCons + "MQ error", e);
			// 异常数据存入表 mq_error
			this.insertMqError(1, dto, memberId, "友盟消息推送");

		}
	}

	/**
	 * 保存货源日志。
	 * 
	 * @param sourceId
	 * @param desc
	 * @param createUserId
	 * @return
	 */
	private int saveLog(Integer sourceId, String desc, String createUserId) {
		SourceLogEntity entity = new SourceLogEntity();
		entity.setSourceId(sourceId);
		entity.setDescription(desc);
		entity.setCreateUserId(createUserId);
		Long id = baseDao.persist(entity, Long.class);
		logger.debug("保存货源日志成功，日志内容：" + desc);
		return id.intValue();
	}

	/**
	 * 添加orderBefore
	 * 
	 * @Description:
	 * @param orderBeforeEntity
	 * @param driverMember
	 * @param shipperEntity
	 * @return
	 * @throws BizException
	 *
	 */
	private OrderBeforeEntity saveOrderBeforeEntity(OrderBeforeEntity orderBeforeEntity, MemberInfoEntity driverMember, SourceShipperEntity shipperEntity) throws BizException {
		orderBeforeEntity.setSourceStatus(OrderBeforeEnum.RECEIVEORDER.getCode());
		orderBeforeEntity.setCreateUserId(driverMember.getId().toString());
		orderBeforeEntity.setShipper_isDeleted(Constant.TABLE_NOT_DELETE);
		orderBeforeEntity.setDriver_isDeleted(Constant.TABLE_NOT_DELETE);
		orderBeforeEntity.setShipperMemberId(shipperEntity.getMemberId());
		orderBeforeEntity.setShipperName(shipperEntity.getShipperName());
		orderBeforeEntity.setShipperMobile(shipperEntity.getShipperMobile());
		orderBeforeEntity.setDriverMobile(driverMember.getMobile());
		orderBeforeEntity.setDriverName(driverMember.getUserName());
		orderBeforeEntity.setUpdateUserId(driverMember.getId().toString());
		orderBeforeEntity.setUpdateTime(new Date());
		Long id = baseDao.persist(orderBeforeEntity, Long.class);
		if ( id == null) {
			throw new BizException(MsgCons.C_24017, MsgCons.M_24017);
		}
		orderBeforeEntity.setId(id.intValue());
		return orderBeforeEntity;
	}

	/**
	 * 
	 * @Description: 更新货源状态 将货源老状态更新为新状态
	 * @param entity
	 *            货源实体
	 * @param oldStatus
	 *            老状态
	 * @param newStatus
	 *            新状态
	 * @throws BizException
	 *
	 */
	private SourceShipperEntity updateSourceShipperStatus(Integer sourceId, Byte oldStatus, Byte newStatus, Integer errorCode, String errorMsg) throws BizException {
		Map<String, Object> map = new HashMap<>();
		map.put("id", sourceId);
		SourceShipperEntity entity = baseDao.queryForObject("OrderBefore.querySourceShipper", map, SourceShipperEntity.class);
		// 判断货源是否能够操作 分配中不可操作
		if ( entity == null) {
			throw new BizException(MsgCons.C_24030, MsgCons.M_24030);
		}
		if ( entity.getSourceStatus().compareTo(oldStatus) != 0) {
			if ( newStatus.compareTo(SourceStatusEnum.RELEASED.getCode()) == 0) {
				// 取消
				throw new BizException(MsgCons.C_23013, MsgCons.M_23013);
			} else if ( newStatus.compareTo(SourceStatusEnum.STAY_FOR_CONFIRM.getCode()) == 0) {
				// 接收
				throw new BizException(MsgCons.C_24023, MsgCons.M_24023);
			} else {
				throw new BizException(errorCode, errorMsg);
			}

		}
		if ( entity.getNstRule().compareTo(NstRuleEnum.DISTRIBUTING.getCode()) == 0) {
			// 分配中，不可操作
			throw new BizException(errorCode, errorMsg);
		}
		Map<String, Object> sourceEntityMap = new HashMap<>();
		sourceEntityMap.put("id", entity.getId());
		sourceEntityMap.put("sourceStatus", newStatus);
		sourceEntityMap.put("version", entity.getVersion());
		if ( baseDao.execute("OrderBefore.updateSourceShipperStatus", sourceEntityMap) != 1) {
			throw new BizException(errorCode, errorMsg);
		}
		entity.setSourceStatus(newStatus);
		return entity;

	}

	/**
	 * 判断货源是否分配给了物流公司
	 * 
	 * @param sourceId
	 * @return
	 */
	private void isDistribution(OrderBeforeEntity beforeDTO, SourceShipperEntity entity, MemberInfoEntity sourceMemberInfo) throws BizException {
		// step2 货源属于物流公司 创建信息订单
		if ( entity.getNstRule().compareTo(NstRuleEnum.DISTRIBUTED.getCode()) == 0 
					|| entity.getNstRule().compareTo(NstRuleEnum.SELFLOGST.getCode()) == 0) {
			OrderAgentEntity orderAgentEntity = new OrderAgentEntity();
			orderAgentEntity.setDriverMemberId(beforeDTO.getDriverMemberId());
			orderAgentEntity.setDriverMobile(beforeDTO.getDriverMobile());
			orderAgentEntity.setDriverName(beforeDTO.getDriverName());
			// orderAgentEntity.setLogisticTime(new Date());
			orderAgentEntity.setLogisticMemberId(sourceMemberInfo.getId());
			orderAgentEntity.setLogisticMobile(sourceMemberInfo.getMobile());
			orderAgentEntity.setLogisticName(sourceMemberInfo.getUserName());
			orderAgentEntity.setOrderBeforeId(beforeDTO.getId());
			orderAgentEntity.setOrderNo(generateOrderNo(entity));
			orderAgentEntity.setConfirmTime(beforeDTO.getCreateTime());
			orderAgentEntity.setOrderStatus(OrderAgentStatusEnum.WAITCONFIRM.getCode());
			orderAgentEntity.setSourceId(entity.getId());
			orderAgentEntity.setCreateUserId(beforeDTO.getDriverMemberId().toString());
			orderAgentEntity.setCreateTime(beforeDTO.getCreateTime());
			orderAgentEntity.setPayStatus((short) 1);
			baseDao.persist(orderAgentEntity, Long.class);
		}

	}

	/**
	 * 司机接单 发送定时(10分钟后触发)MQ信息
	 * 
	 * @param id
	 *            预订单主键ID
	 * @throws BizException
	 */
	private void sendDriverReceiveOrderMsg(Integer id, Integer memberId) throws BizException {
		TaskDto taskDto = new TaskDto();
		taskDto.setBizId(id);
		taskDto.setTaskType(MqConstants.TASK_TYPE_0);
		Message msg = new Message(taskProducer.getProperties().getProperty(MqConstants.TOPIC), MqConstants.TAG, SerializeUtil.serialize(taskDto));
		msg.setKey(id.toString());
		// 定时消息(10分钟)
		long time = System.currentTimeMillis() + 10 * 60 * 1000;
		msg.setStartDeliverTime(time);
		try {
			logger.info("司机接单发送定时MQ 发送时间:{},message:{}", new Object[] { DateFormatUtils.format(new Date(), DateUtil.DATE_FORMAT_DATETIME), JSON.toJSONString(msg) });
			taskProducer.send(msg);
		}
		catch (Exception e) {
			logger.error("司机接单发送定时MQ error", e);
			// 存入数据库
			insertMqError(1, msg, memberId, "司机接单发送定时MQ");
		}
	}

	/**
	 * 生成订单号
	 * 
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	private String generateOrderNo(SourceShipperEntity entity) throws BizException {
		Integer sequence = baseDao.queryForObject("OrderAgent.getNstOrderNo", null, Integer.class);
		if ( sequence == null) {
			throw new BizException(MsgCons.C_23002, MsgCons.M_23002);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(entity.getSProvinceId().toString().substring(0, 2)); // 出发地省会Id
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		sb.append(sf.format(new Date())); // 年月日
		// 六位的自增序列
		String seq = String.valueOf(sequence);
		for (int i = 0; i < 6 - seq.length(); i++) {
			sb.append("0");
		}
		sb.append(seq);
		return sb.toString();
	}

	/**
	 * 更新信息订单表的状态为拒绝状态。更新失败，抛出BizException异常。
	 * 
	 * @param param
	 * @throws BizException
	 */
	private void updateOrderAgentStatusForReject(Map<String, Object> param) throws BizException {
		param.put("sourceId", param.get("sourceShipperId"));
		param.put("orderStatus", OrderAgentStatusEnum.COMPANY_CANCEL.getCode());
		int record = baseDao.execute("OrderAgent.update", param);
		if ( record == 0) {
			throw new BizException(MsgCons.C_23000, MsgCons.M_23000);
		}
	}
}
