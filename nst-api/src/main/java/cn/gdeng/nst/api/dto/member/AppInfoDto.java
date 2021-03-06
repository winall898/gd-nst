package cn.gdeng.nst.api.dto.member;

import java.io.Serializable;

/**
 * App公共参数Dto
 * @author zhangnf
 *
 */
public class AppInfoDto implements Serializable{

  private static final long serialVersionUID = 280908809069422695L;
  /**
	 * 会员号
	 */
	private String memberId;
	/**
	 * 设备类型 0 android 1 ios
	 */
	private String deviceType;
	/**
	 * 当前登录APP类型：1 货主 2 车主 3 物流公司
	 */
	private String deviceApp;
	/**
	 * app版本
	 */
	private String appVersion;
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceApp() {
		return deviceApp;
	}
	public void setDeviceApp(String deviceApp) {
		this.deviceApp = deviceApp;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	
}
