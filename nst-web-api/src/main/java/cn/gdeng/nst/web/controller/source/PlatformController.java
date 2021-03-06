package cn.gdeng.nst.web.controller.source;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.gdeng.nst.api.server.source.PlatformService;
import cn.gdeng.nst.util.web.api.ApiResult;
import cn.gdeng.nst.web.controller.base.BaseController;

/**
 * 平台配送调用
 * 
 * @author xiaojun
 *
 */
@Controller
@RequestMapping("v1/platform/")
public class PlatformController extends BaseController {

	@Reference
	private PlatformService platformService;
	/**
	 * 货源关闭
	 */
	@ResponseBody
	@RequestMapping("goodsClose")
	public Object goodsClose(HttpServletRequest request) throws Exception {
		Map<String, Object> paramMap = getDecodeMap(request);
		ApiResult<?> apiResult = platformService.goodsClose(paramMap);
		return apiResult;
	}
}
