package cn.gdeng.nst.server.source.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

import cn.gdeng.nst.api.dto.task.PushMsgDto;
import cn.gdeng.nst.api.server.source.CarOwnerSourceService;
import cn.gdeng.nst.dao.BaseDao;
import cn.gdeng.nst.entity.nst.MemberCarEntity;
import cn.gdeng.nst.entity.nst.MemberInfoEntity;
import cn.gdeng.nst.entity.nst.MqError;
import cn.gdeng.nst.entity.nst.OrderBeforeEntity;
import cn.gdeng.nst.entity.nst.OrderInfoEntity;
import cn.gdeng.nst.entity.nst.SourceAssignHisEntity;
import cn.gdeng.nst.entity.nst.SourceShipperEntity;
import cn.gdeng.nst.enums.CarLengthEnum;
import cn.gdeng.nst.enums.CarTypeEnum;
import cn.gdeng.nst.enums.GoodsTypeEnum;
import cn.gdeng.nst.enums.MqConstants;
import cn.gdeng.nst.enums.MsgCons;
import cn.gdeng.nst.enums.PushConstants;
import cn.gdeng.nst.enums.SendGoodsTypeEnum;
import cn.gdeng.nst.util.admin.web.DateUtil;
import cn.gdeng.nst.util.server.AddrUtils;
import cn.gdeng.nst.util.web.api.ApiPage;
import cn.gdeng.nst.util.web.api.ApiResult;
import cn.gdeng.nst.util.web.api.BizException;
import cn.gdeng.nst.util.web.api.GSONUtils;
import cn.gdeng.nst.util.web.api.SerializeUtil;

@Service
public class CarOwnerSourceServiceImpl implements CarOwnerSourceService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private ProducerBean msgPushProducer;
	@Resource
	private BaseDao<?> baseDao;
	

	@Override
	public ApiResult<ApiPage> queryPage(ApiPage page) throws BizException {
		ApiResult<ApiPage> apiResult = new ApiResult<ApiPage>();
		long total = 0;
		List<Map<String,Object>> list = null;
		String sqlMap_Count = "countBySourceAssignHis";
		String sqlMap_Query = "queryBySourceAssignHis";
		Object statusKey = page.getParaMap().get("status");
		if(statusKey != null && "2".equals((String)statusKey)){
			sqlMap_Count = "countByOrderBefore";
			sqlMap_Query = "queryByOrderBefore";
		}
		total = baseDao.queryForObject("CarOwnerSource." + sqlMap_Count, page.getParaMap(), Long.class);
		if (total > 0) {
			list= baseDao.queryForList("CarOwnerSource." + sqlMap_Query, page.getParaMap());		
			try {				
				generalFullAddrAndSetList(list);//生成详细的全地址
			} catch (Exception e) {
				logger.error("CarOwnerSourceServiceImpl类queryPage方法生成详细地址异常>>>>>>>>>>>>>>>>", e);
				throw new BizException(MsgCons.C_20000, MsgCons.M_20000);
			}			
		} else {
			list = Collections.emptyList();
		}
		page.setRecordList(list).setRecordCount(total);
		return apiResult.setResult(page);
	}
	
	/**
	 * 生成详细的全地址，并set到list的元素中。
	 * NOTE:如果地址包含省份，去除该省份。
	 */
	private void generalFullAddrAndSetList(List<Map<String,Object>> list) throws Exception {
		for (Map<String,Object> map : list) {
			generalFullAddrAndSet(map);			
		}
	}
	
	/**
	 * 生成详细的全地址，并set到参数的元素中。
	 * NOTE:如果地址包含省份，去除该省份。
	 */
	private void generalFullAddrAndSet(Map<String,Object> map) throws Exception {
		if (map == null) {
			return;
		}
		//货主要求的装货车型
		if(map.containsKey("carType") && map.get("carType") != null){
			Integer carType = (Integer) map.get("carType");
			map.put("carType", CarTypeEnum.getNameByCode(carType.byteValue()));
		}
		//发货方式
		if(map.containsKey("sendGoodsType") && map.get("sendGoodsType") != null){
			Integer sendGoodsType = (Integer) map.get("sendGoodsType");
			map.put("sendGoodsType", SendGoodsTypeEnum.getNameByCode(sendGoodsType.byteValue()));
		}
		//货物类型
		if(map.containsKey("goodsType") && map.get("goodsType") != null){
			Integer goodsType = (Integer) map.get("goodsType");
			map.put("goodsType", GoodsTypeEnum.getNameByCode(goodsType.byteValue()));
		}
		//车主车型
		if(map.containsKey("driverCarType") && map.get("driverCarType") != null){
			Integer driverCarType = (Integer) map.get("driverCarType");
			map.put("driverCarType", CarTypeEnum.getNameByCode(driverCarType.byteValue()));
		}
		//货主要求的装货车长
		if(map.containsKey("carLength") && map.get("carLength") != null){
			BigDecimal carLength = (BigDecimal) map.get("carLength");
			String carLengthStr = CarLengthEnum.getNameByCode(carLength.doubleValue());
			map.put("carLength", StringUtils.isNotEmpty(carLengthStr) ? carLengthStr : carLength);
		}
		//司机车长
		if(map.containsKey("driverCarLength") && map.get("driverCarLength") != null){
			BigDecimal driverCarLength = (BigDecimal) map.get("driverCarLength");
			String driverCarLengStr = CarLengthEnum.getNameByCode(driverCarLength.doubleValue());
			map.put("driverCarLength", StringUtils.isNotEmpty(driverCarLengStr) ? driverCarLengStr : driverCarLength);
		}
		//货品名称
		if(map.containsKey("goodsName") && map.get("goodsName") != null){
			String goodsName =map.get("goodsName").toString();
			int length=goodsName.length();
			if (length>10) {
				map.put("goodsName",(goodsName.substring(0, 10)+"..."));
			}
			
		}
		
		//=====设置起始地=====
		String srcFullAddr = "";
		String sDetailVal = map.get("sourceSDetail") !=null ? (String) map.get("sourceSDetail") : "";
		if (StringUtils.isNotBlank(sDetailVal)) {
			srcFullAddr = AddrUtils.ridProvinceAndSpliceAddr(sDetailVal, "/");
			map.put("sourceSDetail",sDetailVal.replaceAll("/", ""));
		}
		String sDetailAddrVal = map.get("sourceSDetailAddr") !=null ? (String) map.get("sourceSDetailAddr") : "";
		if(StringUtils.isNotBlank(sDetailAddrVal)) {
			srcFullAddr += sDetailAddrVal;
		}
		map.put("srcFullAddr", srcFullAddr.replaceAll("/", ""));
		//=====设置目的地====
		String desFullAddr = "";
		String eDetailVal = map.get("sourceEDetail") !=null ? (String) map.get("sourceEDetail") : "";
		if (StringUtils.isNotBlank(eDetailVal)) {
			desFullAddr = AddrUtils.ridProvinceAndSpliceAddr(eDetailVal, "/");
			map.put("sourceEDetail",eDetailVal.replaceAll("/", ""));
		}
		String eDetailAddrVal = map.get("sourceEDetailAddr") !=null ? (String)map.get("sourceEDetailAddr") : "";
		if(StringUtils.isNotBlank(eDetailAddrVal)) {
			desFullAddr += eDetailAddrVal;
		}
		map.put("desFullAddr", desFullAddr.replaceAll("/", ""));
	}

	@Override
	public ApiResult<?> detail(Map<String, Object> reqParam) throws BizException {
		String sqlMap = "queryDetailBySourceAssignHis";
		Object statusKey = reqParam.get("status");
		if(statusKey != null && "2".equals((String)statusKey)){
			sqlMap = "queryDetailByOrderBefore";
		}		
		Map<String,Object> map = baseDao.queryForMap("CarOwnerSource." + sqlMap, reqParam);
		try {
			generalFullAddrAndSet(map);
		} catch (Exception e) {
			logger.error("CarOwnerSourceServiceImpl类detail方法生成详细地址异常>>>>>>>>>>>>>>>>", e);
			throw new BizException(MsgCons.C_20000, MsgCons.M_20000);
		}
		ApiResult<Map<String,Object>> apiResult = new ApiResult<Map<String,Object>>();
		return apiResult.setResult(map);
	}

	@Override
	@Transactional
	public ApiResult<?> accept(Map<String, Object> paramMap) throws BizException {	
		        Integer sourceShipperId = Integer.valueOf(paramMap.get("sourceShipperId").toString());//货源ID
		        //2017.2.17新需求   分配的货源接单判断是否有设置承运车辆
		        MemberCarEntity carEntity=baseDao.queryForObject("CarOwnerSource.queryCarrierCar", paramMap,MemberCarEntity.class);
		        if(carEntity==null){
		        	throw new BizException(MsgCons.C_21028, MsgCons.M_21028);
		        }
		        String updateUserId = paramMap.get("updateUserId").toString();//车主会员ID
		        //=====查询货源=====
		        SourceShipperEntity shipperEntity = querySourceShipperById(sourceShipperId);
		        if (shipperEntity == null) {
					throw new BizException(MsgCons.C_24010, MsgCons.M_24010);
				}
		        //校验分给我的货
		        paramMap.put("sourceId", sourceShipperId);
		        paramMap.put("assignMemberId", updateUserId);
		        checkSourceAssignHis(paramMap);
		        //=====查询车主信息===
				Map<String, Object> map =new HashMap<String, Object>();
				map.put("memberId", updateUserId);
				//查询车主信息
				MemberInfoEntity memberInfoEntity=baseDao.queryForObject("CarOwnerSource.queryMemberById", map, MemberInfoEntity.class);
				if (memberInfoEntity==null) {
					throw new BizException(MsgCons.C_21018, MsgCons.M_21018);
				}else if (memberInfoEntity.getStatus().compareTo((byte)1)!=0) {
					throw new BizException(MsgCons.C10000, MsgCons.M10000);
				}
				Integer shipperMemberId = shipperEntity.getMemberId();
				Double transportAmt = shipperEntity.getFreight();
				//生成预运单
		        OrderBeforeEntity orderBeforeEntity = new OrderBeforeEntity();
		        orderBeforeEntity.setSourceId(sourceShipperId);
		        orderBeforeEntity.setCarId(carEntity.getId());			   							
				orderBeforeEntity.setShipperMemberId(shipperMemberId);
				orderBeforeEntity.setShipperName(shipperEntity.getShipperName());
				orderBeforeEntity.setShipperMobile(shipperEntity.getShipperMobile());
				orderBeforeEntity.setDriverMobile(memberInfoEntity.getMobile());
				orderBeforeEntity.setDriverMemberId(Integer.valueOf(updateUserId));
				orderBeforeEntity.setDriverName(memberInfoEntity.getUserName());
				orderBeforeEntity.setSourceStatus((byte) 2);	
				orderBeforeEntity.setShipper_isDeleted((byte) 0);
				orderBeforeEntity.setDriver_isDeleted((byte) 0);
				orderBeforeEntity.setCreateUserId(updateUserId);
				orderBeforeEntity.setCreateTime(new Date());
				orderBeforeEntity.setUpdateTime(new Date());
				orderBeforeEntity.setUpdateUserId(updateUserId);
				Long id=baseDao.persist(orderBeforeEntity,Long.class);
			   	if (id==null) {
			   		throw new BizException(MsgCons.C_24017, MsgCons.M_24017);
				}
		        Integer orderBeforeId = id.intValue();
		        //生成运单
		        String orderNo = generateOrderNo(shipperEntity);	
		        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
		        orderInfoEntity.setOrderNo(orderNo);
		        orderInfoEntity.setSourceId(sourceShipperId);
		        orderInfoEntity.setCarId(carEntity.getId());
		        orderInfoEntity.setOrderBeforeId(orderBeforeId);	
		        orderInfoEntity.setShipperMemberId(shipperMemberId);
		        orderInfoEntity.setDriverMemberId(Integer.valueOf(updateUserId));
		        orderInfoEntity.setConfirmOrderTime(new Date());
		        orderInfoEntity.setOrderStatus((byte) 1);
		        orderInfoEntity.setPayStatus((short) 1);
		        orderInfoEntity.setTransportAmt(transportAmt);
		        orderInfoEntity.setShipper_isDeleted((byte) 0);
		        orderInfoEntity.setDriver_isDeleted((byte) 0);
		        orderInfoEntity.setCreateUserId(updateUserId);
		        orderInfoEntity.setCreateTime(new Date());
		        orderInfoEntity.setUpdateTime(new Date());
		        orderInfoEntity.setUpdateUserId(updateUserId);
		        /**6+1 需求 新增物流状态"已发货"(冗余以前的业务)			*/
		        orderInfoEntity.setTransStatus((byte)2);
		        id = baseDao.persist(orderInfoEntity,Long.class);
		    	if (id==null) {
			   		throw new BizException(MsgCons.C_24036, MsgCons.M_24036);
				}
				//===修改货源状态===
				paramMap.put("nstRule", 5);//设置货源为代发规则		
				paramMap.put("sourceStatus", 3);//设置货源状态为已接受
				paramMap.put("assignStatus", 2);//设置为接受分配	
				paramMap.put("updateTime", new Date());
				//===更新货源和分配历史表状态===
			    baseDao.execute("CarOwnerSource.updateNstRuleById", paramMap);				
				baseDao.execute("CarOwnerSource.updateStatusById", paramMap);							
				msgPushToDriver(sourceShipperId,shipperEntity.getMemberId(),MsgCons.M_28000,PushConstants.MSG_TYPE_0);//推送消息给货主			        		
				ApiResult<String> apiResult = new ApiResult<String>();		
                return apiResult;
	}
	
	@Override
	public ApiResult<?> checkSourceAssignHis(Map<String, Object> reqParam) throws BizException {
		SourceAssignHisEntity entity = baseDao.queryForObject("CarOwnerSource.findBySourceId", reqParam,SourceAssignHisEntity.class);
		if(entity == null){
			throw new BizException(MsgCons.C_24037, MsgCons.M_24037);
		}else if(entity.getStatus() != 1){
			throw new BizException(MsgCons.C_20000, "操作失败，请刷新后再试！");
		}
		return new ApiResult();
	}
	
	/**
	 * 生成运单订单号
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	private String generateOrderNo(SourceShipperEntity entity) throws BizException{
		Integer sequence = baseDao.queryForObject("CarOwnerSource.getNstOrderNo", null, Integer.class);
		if(sequence == null){
			throw new BizException(MsgCons.C_23003, MsgCons.M_23003);
		}
		StringBuilder sb = new StringBuilder();
		//1.出发地省会Id
		sb.append(entity.getSProvinceId().toString().substring(0, 2)); 
		
		//2.年月日
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		sb.append(sf.format(new Date()));
		
		//3.目的地省会Id
		sb.append(entity.getEProvinceId().toString().substring(0, 2)); 
		
		//4.六位的自增序列
		String seq = String.valueOf(sequence);
		for(int i = 0; i < 6 - seq.length(); i++){
				sb.append("0");
		}
		sb.append(seq);		
		return sb.toString();
	}

	private SourceShipperEntity querySourceShipperById(Integer sourceId)throws BizException{
		Map<String, Integer> map=new HashMap<>();
		map.put("id",  sourceId);
		SourceShipperEntity entity=baseDao.queryForObject("CarOwnerSource.querySourceShipper", map, SourceShipperEntity.class);
		if(entity==null){
			logger.info("查询货源异常:sourceId={}",sourceId);
			throw new BizException(MsgCons.C_24010, MsgCons.M_24010);
		}
		return entity;
	}
	
	private void msgPushToDriver(Integer bizId, Integer memberId,String msgCons,Integer msgType)throws BizException {
		PushMsgDto dto=new PushMsgDto();
		dto.setBizId(bizId);
		dto.setContent(msgCons);
		dto.setMemberId(memberId);
		dto.setMsgType(msgType);		
		Message msg = new Message(msgPushProducer.getProperties().getProperty(MqConstants.TOPIC),MqConstants.TAG,SerializeUtil.serialize(dto));
		msg.setKey(dto.getMemberId().toString());
		try {
			msgPushProducer.send(msg);
			logger.info(msgCons,new Object[]{DateFormatUtils.format(new Date(), DateUtil.DATE_FORMAT_DATETIME),JSON.toJSONString(msg)});
		} catch (Exception e) {
			logger.error(msgCons+"MQ error",e);
			this.insertMqError(dto,memberId);
		}
	}
	
	/**
	 * 异常数据存入表 mq_error
	 * @param dto
	 */
	private void insertMqError(Object dto,Integer memberId){
		MqError mqError = new MqError();
		mqError.setBizType(MqConstants.BIZ_TYPE_1);
		mqError.setTopic(MqConstants.TOPIC_PUSH);
		mqError.setMemberId(memberId);
		mqError.setContent(GSONUtils.toJson(dto,false));
		mqError.setCreateUserId(1);
		baseDao.execute("MqError.insert", mqError);
	}

}
