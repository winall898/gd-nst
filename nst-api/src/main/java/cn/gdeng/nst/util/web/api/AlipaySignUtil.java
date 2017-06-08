package cn.gdeng.nst.util.web.api;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import cn.gdeng.nst.enums.KeyTypeEnum;
import cn.gdeng.nst.util.web.api.AlipayConfigUtil.AlipayConfig;

/**
 * 阿里签名工具类
 * @author sss
 *
 */
public class AlipaySignUtil {
	
	
	public static String buildUrl(AlipayConfig cf,Map<String, String> temp) throws BizException {
		Map<String, String> sParaTemp = buildRequestPara(temp,cf);
		StringBuffer sb = new StringBuffer();
		sb.append(cf.getGateWay());
		sb.append("_input_charset="+cf.getInput_charset());
		Iterator<Entry<String, String>> it = sParaTemp.entrySet().iterator();
		try {
			while(it.hasNext()){
				Entry<String, String> entry = it.next();
				sb.append("&").append(entry.getKey()).append("=");
				String value = entry.getValue();//对value进行编码
				sb.append(URLEncoder.encode(value,"utf-8"));
			}
		} catch (Exception e) {
			throw new BizException(2007, "URL地址编码错误");
		}
		String url = sb.toString();
		return url;

	}

	 /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
	private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp,AlipayConfig config) {
        
    	//获取signType
    	String signType= config.getKeyType();
    	//除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp);
        //生成签名结果
        String prestr = createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if(StringUtils.equals(signType, KeyTypeEnum.MD5.getWay()) ) {
        	mysign = MD5.sign(prestr, config.getKey(), sParaTemp.get("_input_charset"));
        }

        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", signType);

        return sPara;
    }

    /** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }
    
    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
}
