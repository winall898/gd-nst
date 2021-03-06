package cn.gdeng.nst.web.controller.pub;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.gdeng.nst.pub.service.AppVersionService;
import cn.gdeng.nst.pub.service.MemberPublicService;
import cn.gdeng.nst.util.web.api.ApiResult;
import cn.gdeng.nst.util.web.api.BizException;
import cn.gdeng.nst.web.controller.base.BaseController;
import cn.gdeng.nst.web.util.DataCheckUtils;

/**
 * 版本控制Controller
 * 
 * @author xiaojun
 *
 */
@Controller
@RequestMapping("v1/app")
public class AppVersionController extends BaseController {
	@Reference
	private AppVersionService appVersionService;
	@Reference
	private MemberPublicService memberPublicService;
	/**
	 * 版本更新检查
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("checkAppVesion")
	public Object checkAppVesion(HttpServletRequest request) throws Exception {
		Map<String, Object> paramMap=getDecodeMap(request);
		checkParam(paramMap);
		return appVersionService.checkAppVesion(paramMap);
	}
	
	/**
	 * 更新业务 检查字段 
	 * @param paramMap
	 * @throws BizException
	 */
	public void checkParam(Map<String, Object> paramMap) throws BizException{
		//用户id不能为空  
		DataCheckUtils.assertIsNonNull("版本号不能为空", paramMap.get("num"));
		//业务类型不能空
		DataCheckUtils.assertIsNonNull("类型不能为空", paramMap.get("type"));
		//业务类型不能空
		DataCheckUtils.assertIsNonNull("平台不能为空", paramMap.get("platform"));
	}
	
	/**
	 * app启动统计
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("appStart")
	public Object appStart(HttpServletRequest request) throws Exception {
		Map<String, Object> paramMap=getDecodeMap(request);
		appStartCheck(paramMap);
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("param", request.getParameter("param"));
		try {
			memberPublicService.appStart(param,paramMap.get("appVersion") ,getTokenByRequest(request));
		} catch (Exception e) {
			logger.error("",e);
		}
		return new ApiResult<Object>();
	}
	
	public void appStartCheck(Map<String, Object> paramMap) throws BizException{
		//DataCheckUtils.assertIsNonNull("会员不能为空", paramMap.get("memberId"));
		DataCheckUtils.assertIsNonNull("App版本不能为空", paramMap.get("appVersion"));
		DataCheckUtils.assertIsNonNull("是否登录不能为空", paramMap.get("isLogin"));
		DataCheckUtils.assertIsNonNull("app类型不能为空", paramMap.get("appType"));
	}
}
