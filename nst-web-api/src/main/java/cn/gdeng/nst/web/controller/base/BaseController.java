package cn.gdeng.nst.web.controller.base;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.gdeng.nst.api.dto.member.AppInfoDto;
import cn.gdeng.nst.util.server.jodis.JodisTemplate;
import cn.gdeng.nst.util.web.api.ApiPage;
import cn.gdeng.nst.util.web.api.Des3Request;
import cn.gdeng.nst.util.web.api.GSONUtils;


public class BaseController {
	
	/**
	 * 定义记录日志信息
	 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected static String EXPORTING = "exporting";
	
	@Autowired
	private JodisTemplate jodisTemplate;
	
	/**
	 * @return the request
	 */
//	public HttpServletRequest getRequest() {
//		return httpRequest;
//	}

	/**
	 * @return the session
	 */
//	public HttpSession getSession() {
//		this.session = httpRequest.getSession();
//		return this.session;
//	}

	
	public void createZipHttpResponseHead(HttpServletResponse response,String fileName){
		response.setContentType("application/octet-stream;charset=UTF-8");
		try {
			fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setHeader("Content-Disposition", "attachment;filename="+fileName);
	}

	
	public void createTextHttpResponseHead(HttpServletResponse response){
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Content-Disposition", "");
	}	
	
	/**
	 * 解密数据
	 * @param request
	 * @param clazz
	 * @return 自定义对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected <T>T getDecodeDto(HttpServletRequest request,final Class<?> clazz) throws Exception{
		String param = request.getParameter("param");
		param = StringUtils.isBlank(param) ? "{}" : param;
		param = Des3Request.decode(param);
		logger.debug("request url -> :" + request.getRequestURI());
		//清除value值为空的数据
		Map<String, Object> mapObj = GSONUtils.fromJsonToMapObj(param);
		Iterator<Map.Entry<String, Object>> it = mapObj.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			if(null == entry.getValue()
					|| "".equals(entry.getValue())){
				it.remove();
			}
		}
		return (T) GSONUtils.fromJsonToObject(GSONUtils.getGson().toJson(mapObj), clazz);
	}
	/**
	 * 解密数据
	 * @param request
	 * @return Map
	 */
	protected Map<String, Object> getDecodeMap(HttpServletRequest request) throws Exception{
		String param = request.getParameter("param");
		param = StringUtils.isBlank(param) ? "{}" : param;
		param = Des3Request.decode(param);
		logger.debug("request url -> :" + request.getRequestURI());
		return GSONUtils.fromJsonToMapObj(param);
	}
	
	/**
	 * 解密数据。转换为泛型为字符串形式的map。<br/>
	 * ps：可以转换为字符串的泛型，可以防止整数变为小数的情况。
	 * @param request
	 * @return Map
	 */
	protected Map<String, String> getDecodeMapStr(HttpServletRequest request) throws Exception{
		String param = request.getParameter("param");
		param = StringUtils.isBlank(param) ? "{}" : param;
		param = Des3Request.decode(param);
		logger.debug("request url -> :" + request.getRequestURI());
		return GSONUtils.fromJsonToMapStr(param);
	}
	/**
	 * 获取解密 字符串
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected String getParamDecodeStr(HttpServletRequest request) throws Exception{
		String encryptParam = request.getParameter("param");
		encryptParam =  StringUtils.isBlank(encryptParam) ? "{}" : Des3Request.decode(encryptParam);
		logger.debug("request url -> :" + request.getRequestURI());
		logger.debug("request params -> :" + encryptParam);
		return StringUtils.isBlank(encryptParam) ? "{}" : encryptParam;
	}
	/**
	 * 拼装加密字符串到map中
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Object> getParamMap(HttpServletRequest request) throws Exception{
		String param =request.getParameter("param");
		Map<String, Object> map=new HashMap<>();
		map.put("param", param);
		return map;
	}
	/**
	 * 获取分页对象
	 * @param request
	 * @return
	 */
	protected ApiPage getPageInfoEncript(HttpServletRequest request)  throws Exception{
		Map<String, Object> paraMap = getDecodeMap(request);
		// 当前第几页
		String page = (String)paraMap.get("pageNum");
		// 每页显示的记录数
		String rows = (String)paraMap.get("pageSize");
		// 当前页
		int currentPage = Integer.parseInt((StringUtils.isBlank(page) || "0".equals(page)) ? "1" : page);
		// 每页显示条数
		int pageSize = Integer.parseInt((StringUtils.isBlank(rows) || "0".equals(rows)) ? "10" : rows);

		ApiPage pageDTO = new ApiPage(currentPage, pageSize);
		paraMap.put("startRow", pageDTO.getOffset());
		paraMap.put("endRow", pageDTO.getPageSize());
		pageDTO.setParaMap(paraMap);
		return pageDTO;
	}
	
	/**
	 * 获取会员App信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected AppInfoDto getAppInfo(HttpServletRequest request) throws Exception{
		String tokenJson = jodisTemplate.get(getTokenByRequest(request));
		return (AppInfoDto) GSONUtils.fromJsonToObject(tokenJson, AppInfoDto.class);
	}
	
	 
	 /**
     * 获取token
     * @param request
     * @return
     * @throws Exception
     */
    public String getTokenByRequest(HttpServletRequest request) throws Exception{
    	String encryptParam = request.getParameter("param");
    	String jsonStr=StringUtils.isBlank(encryptParam) ? "{}" : Des3Request.decode(encryptParam);
    	String resultStr=(String)GSONUtils.getJsonValueStr(jsonStr, "token");
    	return resultStr==null?"":resultStr;
    }
}
