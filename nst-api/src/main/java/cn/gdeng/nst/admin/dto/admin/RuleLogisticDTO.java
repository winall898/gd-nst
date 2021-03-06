package cn.gdeng.nst.admin.dto.admin;

import org.apache.commons.lang3.StringUtils;

import cn.gdeng.nst.entity.nst.RuleLogisticEntity;
import cn.gdeng.nst.util.server.DateUtil;

public class RuleLogisticDTO extends RuleLogisticEntity{

	private static final long serialVersionUID = 6807392704709899589L;
	/**
	 * 会员类型
	 */
	private String memberTypeStr;
	/**
	 * 电话
	 */
	private String mobile;
	/**
	 * 姓名
	 */
	private String userName;
	public String getMemberTypeStr() {
		return memberTypeStr;
	}
	public void setMemberTypeStr(String memberTypeStr) {
		this.memberTypeStr = memberTypeStr;
	}
	public String getMobile() {
		if(StringUtils.isBlank(mobile)){
			return "";
		}
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUserName() {
		if(StringUtils.isBlank(userName)){
			return "";
		}
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getStartTimeStr(){
		return DateUtil.getDate(getStartTime(), "yyyy-MM-dd");
	}
	
	public String getEndTimeStr(){
		return DateUtil.getDate(getEndTime(), "yyyy-MM-dd");
	}
}
