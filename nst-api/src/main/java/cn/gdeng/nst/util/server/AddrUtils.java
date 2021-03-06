package cn.gdeng.nst.util.server;

import org.apache.commons.lang3.StringUtils;

/**地址工具类
 * @author wjguo
 * datetime 2016年8月11日 下午9:04:20  
 *
 */
public final class AddrUtils {
	
	/**剔除地址的省份并重新拼接地址
	 * @param addr  地址
	 * @param regex 分割符
	 * @return 返回拼接的地址，如果参数地址或分割符为空，返回空字符串""
	 */
	public static String ridProvinceAndSpliceAddr(String addr, String regex) {
		if (addr == null  || regex == null) {
			return ""; 
		}
		
		//如果没有包含对应的分隔符，直接返回原字符串。
		if (!addr.contains(regex)) {
			return addr;
		}
		
		StringBuilder sb = new StringBuilder();
		String[] split = addr.split(regex);
		for (int i = 0 ,len = split.length; i < len; i++) {
			//去除掉省份。
			if (i == 0) {
				continue;
			}
			sb.append(split[i]);
			
		}
		return sb.toString();
	
	}
	
	/**生成详细的全地址，并set到参数的元素中。<br/>
	 * NOTE:如果地址包含省份，去除该省份。
	 * @param list
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	public static void generalFullAddrAndSet(Object obj) throws Exception {
		if (obj == null) {
			return ;
		}
		//=====设置起始地=====
		String srcFullAddr = "";
		String sDetailVal = (String) ReflectionUtils.invokeMethod(obj, "getS_detail");
		if (StringUtils.isNotBlank(sDetailVal)) {
			srcFullAddr = AddrUtils.ridProvinceAndSpliceAddr(sDetailVal, "/");
			
		}
		String sDetailAddrVal = (String) ReflectionUtils.invokeMethod(obj, "getS_detailed_address");
		if(StringUtils.isNotBlank(sDetailAddrVal)) {
			srcFullAddr += sDetailAddrVal;
		}
		//set到对象属性中
		ReflectionUtils.invokeMethod(obj, "setSrcFullAddr", srcFullAddr);

		//=====设置目的地====
		String  desFullAddr = "";
		String eDetailVal = (String) ReflectionUtils.invokeMethod(obj, "getE_detail");
		if (StringUtils.isNotBlank(eDetailVal)) {
			desFullAddr = AddrUtils.ridProvinceAndSpliceAddr(eDetailVal, "/");
		}
		String eDetailAddrVal = (String) ReflectionUtils.invokeMethod(obj, "getE_detailed_address");
		if(StringUtils.isNotBlank(eDetailAddrVal)) {
			desFullAddr += eDetailAddrVal;
		}
		//set到对象属性中
		ReflectionUtils.invokeMethod(obj, "setDesFullAddr", desFullAddr);
	}
}
