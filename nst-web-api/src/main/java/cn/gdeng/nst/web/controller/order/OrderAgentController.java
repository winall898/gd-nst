package cn.gdeng.nst.web.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.gdeng.nst.api.dto.order.OrderAgentDTO;
import cn.gdeng.nst.api.server.order.OrderAgentService;
import cn.gdeng.nst.util.web.api.ApiPage;
import cn.gdeng.nst.util.web.api.AppVersionUtil;
import cn.gdeng.nst.web.controller.base.BaseController;
import cn.gdeng.nst.web.util.DataCheckUtils;

/**
 * 
 * 物流公司Controller
 * @author huangjianhua  2017年1月10日  上午11:10:07
 * @version 1.0
 */
@Controller
@RequestMapping("v1/orderAgent")
public class OrderAgentController extends BaseController {

	@Reference
	private OrderAgentService orderAgentService;
	
	/**
	 * 分页查询信息订单
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryOrderAgentPage")
	@ResponseBody
	public Object queryOrderAgentPage(HttpServletRequest request) throws Exception {
		ApiPage page = getPageInfoEncript(request);
		
		DataCheckUtils.assertIsNonNull("状态不能为null", page.getParaMap().get("orderStatus"));
		DataCheckUtils.assertIsNonNull("物流公司ID不能为null", page.getParaMap().get("logisticMemberId"));
		//是否平台配送货源  ps:兼容IOS老版本
		boolean result=AppVersionUtil.isPlatform(this.getAppInfo(request));
		page.getParaMap().put("isPlatform", result);
		return orderAgentService.queryOrderAgentPage(page);
	}
	
	/**
	 * 查询信息订单详情
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryOrderAgent")
	@ResponseBody
	public Object queryOrderAgent(HttpServletRequest request) throws Exception {
		OrderAgentDTO dto = getDecodeDto(request, OrderAgentDTO.class);
		DataCheckUtils.assertIsNonNull("ID不能为null",dto.getId());
		return orderAgentService.queryOrderAgent(dto);
	}
}
