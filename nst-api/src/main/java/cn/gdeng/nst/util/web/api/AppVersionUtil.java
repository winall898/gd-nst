package cn.gdeng.nst.util.web.api;

import cn.gdeng.nst.api.dto.member.AppInfoDto;

/**
 * 版本判断工具类，用于接口兼容
 * @author zhangnf 20161228
 *
 */
public class AppVersionUtil {
    /** 当前登录APP类型：1 货主 2 车主 3 物流公司 */
    private static final String DEVICE_APP_1 = "1";
    /** 当前登录APP类型：1 货主 2 车主 3 物流公司 */
    private static final String DEVICE_APP_2 = "2";
    /** 当前登录APP类型：1 货主 2 车主 3 物流公司 */
    private static final String DEVICE_APP_3 = "3";
	/** 设备类型 0 android 1 ios **/
	private static final String DEVICE_TYPE_ANDROID = "0";
	/** 设备类型 0 android 1 ios **/
	private static final String DEVICE_TYPE_IOS = "1";
	/** Android版本号 位数均4位，不足补0*/
	private static final long APP_VERSION_ANDROID_1050 = 1050;//1.0.5
	/** IOS版本号 位数均4位，不足补0*/
	private static final long APP_VERSION_IOS_1050 = 1050;//1.0.5
	/** Android版本号 位数均4位，不足补0*/
    private static final long APP_VERSION_ANDROID_1060 = 1060;//1.0.6
    /** IOS版本号 位数均4位，不足补0*/
    private static final long APP_VERSION_IOS_1060 = 1060;//1.0.6
	
	/**
     * 微信支付老版本兼容检查
     * 老版本APP关闭微信支付选项
     *  true  开启 ,false 关闭
     * @param dto
     * @return
     */
    public static boolean isWebChat(AppInfoDto dto){
        if(null == dto
                || null == dto.getDeviceType()){
            return false;
        }
        long version = versionToLong(dto.getAppVersion());
        if(dto.getDeviceType().equals(DEVICE_TYPE_IOS)
                && version >= APP_VERSION_IOS_1060){
            return true;
        }else if(dto.getDeviceApp().equals(DEVICE_APP_1)
                && version >= APP_VERSION_ANDROID_1050){
        return true;
        }else if(dto.getDeviceType().equals(DEVICE_TYPE_ANDROID)
                && version >= APP_VERSION_ANDROID_1060){
            return true;
        }else{
            return false;
        }
    }
    
	/**
	 * 平台配送（6+1）版本验证
	 *  true  平台配送 ,false 非平台配送
	 * @param dto
	 * @return
	 */
	public static boolean isPlatform(AppInfoDto dto){
		if(null == dto
				|| null == dto.getDeviceType()){
			return false;
		}
		long version = versionToLong(dto.getAppVersion());
		if(dto.getDeviceType().equals(DEVICE_TYPE_IOS)
				&& version >= APP_VERSION_IOS_1050){
			return true;
		}else if(dto.getDeviceType().equals(DEVICE_TYPE_ANDROID)
				&& version >= APP_VERSION_ANDROID_1050){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 版本字符串转long
	 * @param version
	 * @return
	 */
	private static long versionToLong(String version){
		if(null == version
				|| "".equals(version)
					|| "".trim().equals(version)){
			return -1;
		}
		long result = -1;
		try {
			//补充版本号长度到位
			StringBuilder versionStr = new StringBuilder(version.replace(".",""));
			for(int i = 0; i < (5-versionStr.toString().length());i++){
				versionStr.append("0");
			}
			result = Long.parseLong(versionStr.toString());
		} catch (Exception e) {
			result = -2;
		}
		return result;
	}
	public static void main(String[] args) {
		AppInfoDto dto = new AppInfoDto();
		dto.setAppVersion("1.1");
		dto.setDeviceApp("1");
		dto.setDeviceType("0");
		dto.setMemberId("123456");
		System.out.println(AppVersionUtil.isPlatform(dto));
	}

}
