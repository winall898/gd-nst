package cn.gdeng.nst.api.vo.order;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 信息订单PageVo
 * @author huangjianhua  2016年8月9日  下午2:27:46
 * @version 1.0
 */
public class OrderAgentPageVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1634553417960799133L;
	/**  信息单ID */
	private Integer id;
	 /**  货源订单号     */
    private String orderInfoNo;
    /**		创建时间	*/
    /** 1 待确认 2 已确认 3已关闭      */
    private Byte orderStatus;
    
    private String orderStatusStr;
    
    private Date createTime;
	/**	货源ID	*/
	private Integer sourceId;
	/** 出发地   */
	private String s_detail;
	private String s_detailed_address;
	/**目的地    */
	private String e_detail;
	private String e_detailed_address;
    /**	司机电话		*/
    private String driverMobile;
	/**	货主电话		*/
    private String mobile;
    /**	物流公司ID		*/
	private Integer assignMemberId;
	/**	规则ID			*/
	private Integer nstRule;
	/**	物流公司ID			*/
	private Integer logisticMemberId;
	/**	司机ID			*/
	private Integer driverMemberId;
	/**	货主ID			*/
	private Integer shipperMemberId;
	/** 信息订单号 */
	private String orderAgentNo;
    /**服务器当前时间
     * 
     */
    private Date currentDate;
    /**
     * 0 非平台配送 1 平台配送
     */
    private Integer platform;
	
    /**
     * 6+1  发布时间
     */
    private Date releaseTime;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderInfoNo() {
		return orderInfoNo;
	}
	public void setOrderInfoNo(String orderInfoNo) {
		this.orderInfoNo = orderInfoNo;
	}
	public Byte getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Byte orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	public String getS_detailed_address() {
		return s_detailed_address;
	}
	public void setS_detailed_address(String s_detailed_address) {
		this.s_detailed_address = s_detailed_address;
	}
	public String getE_detailed_address() {
		return e_detailed_address;
	}
	public void setE_detailed_address(String e_detailed_address) {
		this.e_detailed_address = e_detailed_address;
	}
	public String getDriverMobile() {
		return driverMobile;
	}
	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getAssignMemberId() {
		return assignMemberId;
	}
	public void setAssignMemberId(Integer assignMemberId) {
		this.assignMemberId = assignMemberId;
	}
	public Integer getNstRule() {
		return nstRule;
	}
	public void setNstRule(Integer nstRule) {
		this.nstRule = nstRule;
	}
	public Integer getLogisticMemberId() {
		return logisticMemberId;
	}
	public void setLogisticMemberId(Integer logisticMemberId) {
		this.logisticMemberId = logisticMemberId;
	}
	public Integer getDriverMemberId() {
		return driverMemberId;
	}
	public void setDriverMemberId(Integer driverMemberId) {
		this.driverMemberId = driverMemberId;
	}
	public Integer getShipperMemberId() {
		return shipperMemberId;
	}
	public void setShipperMemberId(Integer shipperMemberId) {
		this.shipperMemberId = shipperMemberId;
	}
	public String getS_detail() {
		return s_detail;
	}
	public void setS_detail(String s_detail) {
		this.s_detail = s_detail;
	}
	public String getE_detail() {
		return e_detail;
	}
	public void setE_detail(String e_detail) {
		this.e_detail = e_detail;
	}
	public String getOrderAgentNo() {
		return orderAgentNo;
	}
	public void setOrderAgentNo(String orderAgentNo) {
		this.orderAgentNo = orderAgentNo;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	public String getOrderStatusStr() {
		return orderStatusStr;
	}
	public void setOrderStatusStr(String orderStatusStr) {
		this.orderStatusStr = orderStatusStr;
	}
	public Integer getPlatform() {
		return platform;
	}
	public void setPlatform(Integer platform) {
		this.platform = platform;
	}
	public Date getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	
}
