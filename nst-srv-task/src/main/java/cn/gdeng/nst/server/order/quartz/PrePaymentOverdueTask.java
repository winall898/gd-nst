package cn.gdeng.nst.server.order.quartz;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.gdeng.nst.api.server.order.OrderInfoTransService;
import cn.gdeng.nst.util.web.api.ApiResult;

/** 支付预付款3天过期的task。
 * 
 * @author wjguo
 *
 * datetime:2016年12月6日 上午11:41:45
 */
public class PrePaymentOverdueTask {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Reference
	private OrderInfoTransService orderInfoTransService;
	/**
	 * 定时
	 */
	public void execute(){  
		logger.info("-----------PrePaymentOverdueTask begin---------");
		Long beginTime=System.currentTimeMillis();
		try {
			orderInfoTransService.scanPrePaymtenOverdueAndHandler();
		} catch (Exception e) {
			logger.error("PrePaymentOverdueTask execute failure", e);
		}
		
        Long consumedTime=System.currentTimeMillis() - beginTime;
        logger.info("-----------PrePaymentOverdueTask end, time consume:{}ms ---------", consumedTime);
    }  
}
