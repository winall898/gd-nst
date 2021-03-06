package cn.gdeng.nst.web.controller.order;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.gdeng.nst.api.server.order.OrderBeforeService;
import cn.gdeng.nst.entity.nst.OrderBeforeEntity;
import cn.gdeng.nst.enums.MsgCons;
import cn.gdeng.nst.util.web.api.ApiPage;
import cn.gdeng.nst.util.web.api.ApiResult;
import cn.gdeng.nst.util.web.api.BizException;
import cn.gdeng.nst.web.controller.base.BaseController;
import cn.gdeng.nst.web.util.DataCheckUtils;

@Controller
@RequestMapping("v1/orderBefore")
public class OrderBeforeController extends BaseController {

	@Reference
	private OrderBeforeService orderBeforeService;

	/**
	 * 新增预运单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("createOrderBefore")
	@ResponseBody
	public Object createOrderBefore(HttpServletRequest request) throws Exception {
		OrderBeforeEntity dto = getDecodeDto(request,
				OrderBeforeEntity.class);
		checkCreateBeforeData(dto);
		return orderBeforeService.createOrderBefore(dto);
	}

	/**
	 * 取消预运单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("cancleOrderBefore")
	@ResponseBody
	public Object cancleOrderBefore(HttpServletRequest request) throws Exception {
		Map<String, Object> map = getDecodeMap(request);
		DataCheckUtils.assertIsNonNull(MsgCons.C_20001,
				MsgCons.M_20001,
				map.get("orderBeforeId"),
				map.get("sourceId"),
				map.get("driverMemberId"));
		return orderBeforeService.cancleOrderBefore(map);
	}

	/**
	 * 
	 * 查询预运单详情
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryOrderBefore")
	@ResponseBody
	public Object queryOrderBefore(HttpServletRequest request) throws Exception {
		Map<String, Object> map = getDecodeMap(request);
		DataCheckUtils.assertIsNonNull(MsgCons.C_20001,
				MsgCons.M_20001,
				map.get("orderBeforeId"));
		return orderBeforeService.queryOrderBefore(map);
	}

	/**
	 * 分页查询预运单
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryOrderBeforePage")
	@ResponseBody
	public Object queryOrderBeforePage(HttpServletRequest request) throws Exception {
		ApiPage page = getPageInfoEncript(request);
		DataCheckUtils.assertIsNonNull(MsgCons.C_20001,
				MsgCons.M_20001,
				page.getParaMap().get("sourceStatus"),
				page.getParaMap().get("driverMemberId"));
		orderBeforeService.checkoutMember(page.getParaMap().get("driverMemberId").toString());
		return orderBeforeService.queryOrderBeforePage(page);
	}

	/**
	 * 拒接预订单，即拒绝司机的接单。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("rejectOrderBefore")
	@ResponseBody
	public Object rejectOrderBefore(HttpServletRequest request) throws Exception {
		Map<String, String> resParamMap = getDecodeMapStr(request);
		Map<String, Object> serParamMap = new HashMap<String, Object>(resParamMap);
		// 关闭预订单
		serParamMap.put("orderBeforeStatus",
				4);
		// 货源状态重新变为已发布
		serParamMap.put("sourceShipperStatus",
				1);
		// 数据校验
		checkDataBeforeRejectOrder(serParamMap);
		return orderBeforeService.rejectOrderBefore(serParamMap);
	}

	/**
	 * 预订单拒绝之前的数据检测。
	 * 
	 * @param paraMap
	 * @param api
	 * @throws BizException
	 *         如果校验不通过，抛出此异常。
	 */
	private void checkDataBeforeRejectOrder(Map<String, Object> paraMap) throws BizException {
		DataCheckUtils.assertIsNonNull(MsgCons.C_20001,
				MsgCons.M_20001,
				paraMap.get("orderBeforeStatus"),
				paraMap.get("orderBeforeId"),
				paraMap.get("sourceShipperStatus"),
				paraMap.get("version"),
				paraMap.get("updateUserId"));

	}

	/**
	 * 检查数据
	 * 
	 * @param dto
	 * @throws BizException
	 */
	private void checkCreateBeforeData(OrderBeforeEntity dto) throws BizException {
		DataCheckUtils.assertIsNonNull(MsgCons.C_20001,
				MsgCons.M_20001,
				dto.getSourceId(),
				dto.getCarId(),
				dto.getDriverMemberId());

	}
	/**
	 * 
	 * @Description:查询接单数量，判断是否还可以再接单 参数货源ID 判断是同城还是干线 干线已达5单，同城已达10单 提示 接单超过数量限定的
	 * @param request
	 * @return
	 * @throws Exception
	 *
	 */
	@RequestMapping("queryOrderQuantity")
	@ResponseBody
	public ApiResult<Boolean> queryOrderQuantity(HttpServletRequest request) throws Exception {
		Map<String, Object> paramMap = getDecodeMap(request);
		DataCheckUtils.assertIsNonNull(MsgCons.C_20001,
				MsgCons.M_20001,
				paramMap.get("driverMemberId"),
				paramMap.get("id"));
		return orderBeforeService.queryOrderQuantity(paramMap);
	}

}
