package cn.gdeng.nst.util.web.api;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gdeng.nst.enums.MsgCons;
import cn.gdeng.nst.enums.OperationStatusEnum;
import cn.gdeng.nst.util.server.ServiceException;

/**
 * @author DJB
 * @version 创建时间：2016年8月1日 下午3:14:34 参数处理
 */

public class ParamProcessUtil {

	/**
	 * 
	 * @Description: 判断字符串是否为空，不为空且不是空字符串返回true
	 * @param string
	 * @return
	 *
	 */
	public static boolean stringIsEmpty(String string) {
		if (string == null) {
			return false;
		}
		if (string.trim().equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @Description: 判断map是否为空，不为空返回true
	 * @param map
	 * @return
	 *
	 */
	public static boolean mapIsEmpty(Map<String, Object> map) {
		if (map == null) {
			return false;
		}
		if (map.isEmpty()) {
			return false;
		}
		return true;
	}

	// 验证map是否含有指定参数，且参数不能为空
	public static boolean mapKeyIsEmpty(Map<String, Object> map, String keyName) {
		if (mapIsEmpty(map) && stringIsEmpty(keyName)) {
			Object value = map.get(keyName);
			if (value != null && !value.toString().trim().equals("")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @Description: 取出结果并转换为 Integer类型
	 * @param map
	 * @param keyName
	 * @return
	 *
	 */
	public static Integer getMapParamValueToInteger(Map<String, Object> map, String keyName) {
		try {
			if (mapIsEmpty(map) && stringIsEmpty(keyName)) {
				Object value = map.get(keyName);
				if (value != null && !value.toString().trim().equals("")) {
					return Integer.parseInt(value.toString().trim());
				}
			}
		}
		catch (Exception e) {}
		return null;
	}
	public static Integer getMapParamValueToInteger(Map<String, Object> map, String keyName,String errorMsg) throws ServiceException {
		try {
			if (mapIsEmpty(map) && stringIsEmpty(keyName)) {
				Object value = map.get(keyName);
				if (value != null && !value.toString().trim().equals("")) {
					return Integer.parseInt(value.toString().trim());
				}
			}
		}
		catch (Exception e) {throw new ServiceException(MsgCons.C_20001, errorMsg);}
		return null;
	}

	/**
	 * 
	 * @Description: 取出结果并转换为 String类型
	 * @param map
	 * @param keyName
	 * @return
	 *
	 */
	public static String getMapParamValueToString(Map<String, Object> map, String keyName) {
		try {
			if (mapIsEmpty(map) && stringIsEmpty(keyName)) {
				Object value = map.get(keyName);
				if (value != null && !value.toString().trim().equals("")) {
					return value.toString().trim();
				}
			}
		}
		catch (Exception e) {}
		return null;
	}
	
	
	public static String getMapParamValueToString(Map<String, Object> map, String keyName,String errorMsg) throws ServiceException  {
		try {
			if (mapIsEmpty(map) && stringIsEmpty(keyName)) {
				Object value = map.get(keyName);
				if (value != null && !value.toString().trim().equals("")) {
					return value.toString().trim();
				}
			}
		}
		catch (Exception e) {throw new ServiceException(MsgCons.C_20001, errorMsg);}
		return null;
	}

	/**
	 * 
	 * @Description:取出指定key结果
	 * @param map
	 * @param keyName
	 * @return
	 *
	 */
	public static Object getMapParamValue(Map<String, Object> map, String keyName) {
		try {
			if (mapIsEmpty(map) && stringIsEmpty(keyName)) {
				Object value = map.get(keyName).toString().trim();
				if (value != null && !value.toString().equals("")) {
					return value;
				}
			}
		}
		catch (Exception e) {}
		return null;
	}

	/**
	 * 
	 * @Description:比较两个list是否相等 有序
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T extends Comparable<T>> boolean compareSort(List<T> a, List<T> b) {
		if (a.size() != b.size())
			return false;
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i)==null&&b.get(i)!=null) {
				return false;
			}
			if (a.get(i)!=null&&b.get(i)==null) {
				return false;
			}
			if (a.get(i)!=null&&b.get(i)!=null&&!a.get(i).equals(b.get(i))){
				return false;
			}
		}
		return true;

	}

	/**
	 * 
	 * @Description:比较两个list是否相等  无序
	 * @param a
	 * @param b
	 * @return
	 *
	 */
	public static <T extends Comparable<T>> boolean compareNotSort(List<T> a, List<T> b) {
		if (a.size() != b.size())
			return false;
		Collections.sort(a);
		Collections.sort(b);
		for (int i = 0; i < a.size(); i++) {
			if (!a.get(i).equals(b.get(i)))
				return false;
		}
		return true;

	}
	
	/*public static void main(String[] args) {
	    List<Integer> a = Arrays.asList(1,1,3,4);
	    List<Integer> b = Arrays.asList(1,1,3,4);
	    System.out.println(compareNotSort(a, b));
	}*/

	
	/**剔除地址的省份并重新拼接地址
	 * @param addr  地址
	 * @param regex 分割符
	 * @return 返回拼接的地址，如果参数地址或分割符为空，返回空字符串""
	 */
	public static String ridProvinceAndSpliceAddr(String addr, String regex) {
		if (addr == null  || regex == null) {
			return ""; 
		}
		StringBuilder sb = new StringBuilder();
		String[] split = addr.split(regex);
		for (int i = 0 ,len = split.length; i < len; i++) {
			//去除掉省份。
			if (i == 0 && split[i].endsWith("省")&&len-1!=i) {
				continue;
			}
			sb.append(split[i]);
			
		}
		return sb.toString();
	
	}
	
	/**  去掉地址中的 指定分隔符
	 * @param addr  地址
	 * @param regex 分割符
	 * @return 返回拼接的地址，如果参数地址或分割符为空，返回空字符串""
	 */
	public static String ridAndSpliceAddr(String addr, String regex) {
		if (addr == null  || regex == null) {
			return ""; 
		}
		StringBuilder sb = new StringBuilder();
		String[] split = addr.split(regex);
		for (int i = 0 ,len = split.length; i < len; i++) {
			
			sb.append(split[i]);
			
		}
		return sb.toString();
	
	}
	
	
	/**  
     * 将String型格式化,比如想要将2011-11-11格式化成2011年11月11日,就StringPattern("2011-11-11","yyyy-MM-dd","yyyy年MM月dd日"). 
     * @param date String 想要格式化的日期 
     * @param oldPattern String 想要格式化的日期的现有格式 
     * @param newPattern String 想要格式化成什么格式 
     * @return String  
     */   
    public static String stringDatePattern(String date, String oldPattern, String newPattern) {   
        if (date == null || oldPattern == null || newPattern == null)   
            return "";   
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern) ;        // 实例化模板对象    
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern) ;        // 实例化模板对象    
        Date d = null ;    
        try{    
            d = sdf1.parse(date) ;   // 将给定的字符串中的日期提取出来    
        }catch(Exception e){            // 如果提供的字符串格式有错误，则进行异常处理    
            e.printStackTrace() ;       // 打印异常信息    
        }    
        return sdf2.format(d);  
    } 

     public static String getNewDate(){
    	 SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ; 
    	 return sdf2.format(new Date());
     }
	
     public static String getNewDateAdd(Integer day){
    	 SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ; 
    	 Date date=new Date();
    	 Calendar cal = Calendar.getInstance();
    	 cal.setTime(date);
    	 cal.add(Calendar.DATE,day);
    	 Date date2=cal.getTime();
    	 return sdf2.format(date2);
     }
     
    /* public static void main(String[] args) {
    	 System.out.println(getNewDateAdd(-5));
	}*/
     
 	/**
 	 * 
 	 * @Description:
 	 * @param orgField 原始的
 	 * @param preField 要替换的
 	 * @param precedence 存在是 不判断规则
 	 * @param nstRole
 	 * @return
 	 *
 	 */
 	public  static Object resultFieldByNstRole(Object orgField,Object preField,Object precedence,Byte nstRole){
 		if (precedence!=null&&(compareToByte(nstRole, (byte)1)||compareToByte(nstRole, (byte)4))||compareToByte(nstRole, (byte)5)||compareToByte(nstRole, (byte)7)) {
 			return precedence;
 		}
 		if (nstRole==null) {
 			return orgField;
 		}
 		switch (nstRole) {
 		case 1:
 			return preField;
 		case 2:
 			return orgField;
 		case 4:
 			return preField;
 		case 5:
 			return preField;
 		case 6:
 			return orgField;
 		case 7:
 			return preField;
 		default:
 			return orgField;
 		}
 	}
 	
 	
 	public static Map<String, Object>  getInitialOperation(Byte nstRule,Byte platform, Byte sourceStatus,Byte orderBeforeStatus,
 			Byte orderAgentPayStatus,
 			Byte orderInfoStatus,Byte transStatus,Byte prePayStatus,Byte closeReason){
 		Map<String, Object> resultMap=new HashMap<>();
 		Byte operationStatus;
		if ( (compareToByte(nstRule,(byte)2)||compareToByte( nstRule,(byte)4))&&
				compareToByte( orderBeforeStatus,(byte)1)) {
			 operationStatus=1;
		}else if((compareToByte( nstRule,(byte)1)||compareToByte( nstRule,(byte)5)||compareToByte( nstRule,(byte)7))
				&&compareToByte( orderBeforeStatus,(byte)1)) {
			 operationStatus=2;
		}else if ((compareToByte( nstRule,(byte)2)||compareToByte( nstRule,(byte)4))
				&&compareToByte( orderBeforeStatus,(byte)2) 
				&&compareToByte( orderAgentPayStatus,(byte)1) ) {
			 operationStatus=3;
		}else if ((compareToByte( nstRule,(byte)2)||compareToByte( nstRule,(byte)4))
				&&compareToByte( orderBeforeStatus,(byte)2) 
				&&compareToByte( orderAgentPayStatus,(byte)2) ) {
			 operationStatus=4;
		}else if ((compareToByte( nstRule,(byte)2)||compareToByte( nstRule,(byte)4))
				&&compareToByte( orderBeforeStatus,(byte)4)  ) {
			 operationStatus=5;
	
		}else if ((compareToByte( nstRule,(byte)1)||compareToByte( nstRule,(byte)5)||compareToByte( nstRule,(byte)7))
				&&compareToByte( orderBeforeStatus,(byte)4)  ) {
			 operationStatus=6;

		}else if (compareToByte( orderBeforeStatus,(byte)5) ) {
			 operationStatus=7;

		}else if (compareToByte( orderBeforeStatus,(byte)6)  ) {
			 operationStatus=8;

		}else if (compareToByte( nstRule,(byte)6)&&compareToByte( platform,(byte)1)
				&&compareToByte( sourceStatus,(byte)3)&&compareToByte( orderBeforeStatus,(byte)2) 
				&&compareToByte( orderInfoStatus,(byte)1)&&compareToByte( prePayStatus,(byte)0)) {
			 operationStatus=16;

		}else if (compareToByte( nstRule,(byte)6)&&compareToByte( platform,(byte)1)
				&&compareToByte( orderInfoStatus,(byte)1)&&compareToByte( prePayStatus,(byte)1)&&compareToByte( transStatus,(byte)1)) {
			 operationStatus=9;

		}else if (compareToByte( nstRule,(byte)6)&&compareToByte( platform,(byte)1)
				&&compareToByte( orderInfoStatus,(byte)3)&&compareToByte( transStatus,(byte)4)) {
			 operationStatus=10;

		}else if (compareToByte( nstRule,(byte)6)&&compareToByte( platform,(byte)1)
				&&compareToByte( orderInfoStatus,(byte)1)&&compareToByte( transStatus,(byte)2)
				&&compareToByte( prePayStatus,(byte)1)) {
			 operationStatus=11;

		}else if (compareToByte( nstRule,(byte)6)&&compareToByte( platform,(byte)1)
				&&compareToByte( orderInfoStatus,(byte)3)&&compareToByte( transStatus,(byte)1)
				&&compareToByte( closeReason,(byte)3)) {
			 operationStatus=12;

		}else if (compareToByte( nstRule,(byte)6)&&compareToByte( platform,(byte)1)
				&&compareToByte( orderInfoStatus,(byte)1)&&compareToByte( transStatus,(byte)3)) {
			 operationStatus=13;

		}else if (compareToByte( nstRule,(byte)6)&&compareToByte( platform,(byte)1)
				&&compareToByte( orderInfoStatus,(byte)2)&&compareToByte( transStatus,(byte)3)) {
			 operationStatus=14;

		}else if (compareToByte( sourceStatus,(byte)4)) {
			 operationStatus=15;

		}else if (compareToByte( nstRule,(byte)6)&&compareToByte( platform,(byte)1)
				&&compareToByte( orderInfoStatus,(byte)3)&&compareToByte( closeReason,(byte)1)) {
			 operationStatus=19;

		}else if (compareToByte( nstRule,(byte)6)&&compareToByte( platform,(byte)1)
				&&compareToByte( orderInfoStatus,(byte)3)&&compareToByte( closeReason,(byte)4)) {
			 operationStatus=17;
	
		}else if (compareToByte( nstRule,(byte)6)&&compareToByte( platform,(byte)1)
				&&compareToByte( orderInfoStatus,(byte)3)&&compareToByte( transStatus,(byte)5)
				&&compareToByte( closeReason,(byte)5)) {
			 operationStatus=18;

		}else if ((compareToByte( nstRule,(byte)2)||compareToByte( nstRule,(byte)4)||compareToByte( nstRule,(byte)1)||compareToByte( nstRule,(byte)5)||compareToByte( nstRule,(byte)7))
				&&(compareToByte( orderBeforeStatus,(byte)3)||compareToByte( orderBeforeStatus,(byte)2))) {
			 operationStatus=20;

		}else  {
			 operationStatus=0;

		}
		resultMap.put("operationStatus", operationStatus);
		resultMap.put("operationStr", OperationStatusEnum.getOperationStrByStatus(operationStatus));
		resultMap.put("markedWords", OperationStatusEnum.getMarkedWordsByStatus(operationStatus));
		
		return resultMap;
	}

	

	public static boolean compareToByte(Byte param,Byte oldParam){
		if(param==null){
			return false;
		}
		if (param.compareTo(oldParam)==0) {
			return true;
		}
		return false;
		
	}
     
     
}
