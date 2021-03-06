package cn.gdeng.nst.server.source.mq.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.ProducerBean;

import cn.gdeng.nst.api.dto.member.MemberCountDTO;
import cn.gdeng.nst.api.dto.source.SourceShipperMqDto;
import cn.gdeng.nst.api.dto.task.PushMsgDto;
import cn.gdeng.nst.dao.BaseDao;
import cn.gdeng.nst.entity.nst.MqError;
import cn.gdeng.nst.entity.nst.SourceShipperEntity;
import cn.gdeng.nst.enums.MqConstants;
import cn.gdeng.nst.enums.MsgCons;
import cn.gdeng.nst.enums.PushConstants;
import cn.gdeng.nst.server.source.mq.GoodsProvidereMQServie;
import cn.gdeng.nst.util.admin.web.DateUtil;
import cn.gdeng.nst.util.web.api.BizException;
import cn.gdeng.nst.util.web.api.GSONUtils;
import cn.gdeng.nst.util.web.api.SerializeUtil;

//定义为Spring的服务类。
@Service("goodsProvidereMQServie")
public class GoodsProvidereServieMQImpl implements GoodsProvidereMQServie {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private BaseDao<?> baseDao;
	@Resource
	private ProducerBean sourceShipperProducer;
	@Resource
	private ProducerBean msgPushProducer;
	@Resource
	private ProducerBean countProducer;
	
	public void goodsAssignmentMQ(final Integer sourceShipperID) throws BizException {
		long start = System.currentTimeMillis();	        
		final SourceShipperMqDto dto = new SourceShipperMqDto();
		dto.setId(sourceShipperID);		
        Message msg = new Message(sourceShipperProducer.getProperties().getProperty(MqConstants.TOPIC),MqConstants.TAG,SerializeUtil.serialize(dto));
        msg.setKey(sourceShipperID.toString());
		try {
			//异步发送
			sourceShipperProducer.sendAsync(msg, new SendCallback(){
				@Override
				public void onSuccess(SendResult sendResult) {
					logger.info("provideMQSource推送成功，货源id为{}，mq消息为{} " , sourceShipperID, sendResult);
				}

				@Override
				public void onException(OnExceptionContext context) {
					logger.error("provideMQSource推送异常，货源id为{}，mq异常消息为{} ", sourceShipperID, context.getException());
					//异常数据存入表 mq_error
					insertMqError(dto, context.getException().getMessage());
					//修改货源为直发
					assignToDriver(dto.getId());
				}
			});
			
		} catch (Exception e) {
			logger.error("", e);
			//异常数据存入表 mq_error
			insertMqError(dto, e.getMessage());
			//修改货源为直发
			assignToDriver(dto.getId());
		}
		logger.info("provideMQSource time:"+(System.currentTimeMillis()-start)+"---------------------");
	}

	/**
	 * 异常数据存入表 mq_error
	 * @param dto
	 */
	private void insertMqError(SourceShipperMqDto dto, String remark){
		MqError mqError = new MqError();
		mqError.setBizType(MqConstants.BIZ_TYPE_1);
		mqError.setTopic(MqConstants.TOPIC_SOURCE_SHIPPER);
		mqError.setContent(GSONUtils.toJson(dto,false));
		mqError.setRemark(remark);
		baseDao.execute("MqError.insert", mqError);
	}
	
	
	/**根据货源id修改货源为直发
	 * @param sourceShipperID
	 */
	private void assignToDriver(Integer sourceShipperID) {
		SourceShipperEntity paramEntity = new SourceShipperEntity();
		paramEntity.setId(sourceShipperID);
		SourceShipperEntity resultEntity = baseDao.find(SourceShipperEntity.class, paramEntity);
		baseDao.execute("SourceShipper.assignToDriver", resultEntity);
	}
	
	public void msgPushToShipperForGoodsAssignmentAccepted(Integer sourceId, Integer memberId) throws BizException {
		PushMsgDto dto=new PushMsgDto();
		dto.setBizId(sourceId);
		dto.setContent(MsgCons.M_28005);
		dto.setMemberId(memberId);
		dto.setMsgType(PushConstants.MSG_TYPE_3);
		
		Message msg = new Message(msgPushProducer.getProperties().getProperty(MqConstants.TOPIC),MqConstants.TAG,SerializeUtil.serialize(dto));
		msg.setKey(dto.getMemberId().toString());
		try {
			logger.info("物流公司接受分配的货源，推送消息给货主MQ发送时间:{},message:{}",new Object[]{DateFormatUtils.format(new Date(), DateUtil.DATE_FORMAT_DATETIME),JSON.toJSONString(msg)});
			msgPushProducer.send(msg);
		} catch (Exception e) {
			logger.error("物流公司接受分配的货源，推送消息给货主MQ error", e);
			//异常数据存入表 mq_error
			this.insertMqError(dto,memberId);
		}
	}
	
	/**
	 * 异常数据存入表 mq_error
	 * @param dto
	 */
	private void insertMqError(PushMsgDto dto,Integer memberId){
		MqError mqError = new MqError();
		mqError.setBizType(MqConstants.BIZ_TYPE_1);
		mqError.setTopic(MqConstants.TOPIC_PUSH);
		mqError.setMemberId(memberId);
		mqError.setContent(GSONUtils.toJson(dto,false));
		baseDao.execute("MqError.insert", mqError);
	}

	/**
	 * @param  orderBeforeId  车主运单ID
	 * @param memberId 车主会员ID
	 */
	@Override
	public void msgPushToDriverForPlatformAssignmentAccepted(Integer orderBeforeId, Integer memberId) throws BizException {
		PushMsgDto dto=new PushMsgDto();
		dto.setBizId(orderBeforeId);
		dto.setContent(MsgCons.M_28015);
		dto.setMemberId(memberId);
		dto.setMsgType(PushConstants.MSG_TYPE_0);
		
		Message msg = new Message(msgPushProducer.getProperties().getProperty(MqConstants.TOPIC),MqConstants.TAG,SerializeUtil.serialize(dto));
		msg.setKey(orderBeforeId.toString());
		try {
			logger.info("物流公司接受平台配送的货源，推送消息给车主MQ发送时间:{},message:{}",new Object[]{DateFormatUtils.format(new Date(), DateUtil.DATE_FORMAT_DATETIME),JSON.toJSONString(msg)});
			msgPushProducer.send(msg);
		} catch (Exception e) {
			logger.error("物流公司接受平台配送的货源，推送消息给车主MQ error", e);
			//异常数据存入表 mq_error
			this.insertMqError(dto,memberId);
		}
		//推送车主 统计接单次数
		msgPushToDriverForPlatformOrderCount(memberId);
	}
	
	/**
	 * 发送MQ 车主统计接单数
	 * @param memberId 车主会员ID
	 */
	private void msgPushToDriverForPlatformOrderCount(Integer memberId){
		MemberCountDTO mqVo=new MemberCountDTO();
		mqVo.setMemberId(memberId);
		mqVo.setDriverOrderCount(1);
		Message msg = new Message(countProducer.getProperties().getProperty(MqConstants.TOPIC),MqConstants.TAG,SerializeUtil.serialize(mqVo));
		msg.setKey(mqVo.getMemberId().toString());
		try {
			logger.info("物流公司接受平台配送订单,推送车主统计接单次数MQ发送时间:{},message:{},memberId:{}",new Object[]{DateFormatUtils.format(new Date(), DateUtil.DATE_FORMAT_DATETIME),JSON.toJSONString(msg),memberId});
			countProducer.send(msg);
		} catch (Exception e) {
			logger.info("物流公司接受平台配送订单,推送车主统计接单次数MQ error",e);
			//异常数据存入表 mq_error
			PushMsgDto dto=new PushMsgDto();
			dto.setContent("物流公司接受平台配送订单,推送车主统计接单次数");
			dto.setMemberId(memberId);
			this.insertMqError(dto,memberId);
		}
	}
}
