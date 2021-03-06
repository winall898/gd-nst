package cn.gdeng.nst.api.vo.order;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 运单PageVo
 * @author huangjianhua  2016年8月5日  下午4:27:46
 * @version 1.0
 */
public class OrderInfoPageVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1634553417960799133L;
	/** 运单ID */
	private Integer id;
	/** 货主memberId */
	private Integer shipperMemberId;
	/** 货主手机 */
	private String shipperMobile;
	/** 货主姓名 */
	private String shipperName;
	/** 司机memberId */
	private Integer driverMemberId;
	/**司机姓名*/
	private String driverName;
	/**	司机手机	*/
	private String driverMobile;
	/** 货物类型 */
	private Integer goodsType;
	/**	货源ID	*/
	private Integer sourceId;
	/**订单号		*/
	private String orderNo;
    /** 运单状态      */
    private Integer orderStatus;
    /** 出发地   */
    private String s_detail;
	private String s_detailed_address;
	/**目的地    */
	private String e_detail;
	private String e_detailed_address;
    /**接单时间		*/
    private Date createTime;
    /**确认订单时间		*/
    private Date confirmOrderTime;
    /**确认收货时间			*/
    private Date confirmGoodsTime;
    /**支付状态		*/
    private Integer payStatus;
    /**
     * 出发地的全地址，包括省市区和详细地
     */
    private String srcFullAddr;
    /**
     * 目的地的全地址，包括省市区和详细地
     */
    private String desFullAddr;
    /**关联的预订单id
     * 
     */
    private Integer orderBeforeId;
    /**物流公司会员id 	*/
    private String logisticsMemberId;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getShipperMemberId() {
		return shipperMemberId;
	}
	public void setShipperMemberId(Integer shipperMemberId) {
		this.shipperMemberId = shipperMemberId;
	}
	public String getShipperMobile() {
		return shipperMobile;
	}
	public void setShipperMobile(String shipperMobile) {
		this.shipperMobile = shipperMobile;
	}
	public String getShipperName() {
		return shipperName;
	}
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}
	public Integer getDriverMemberId() {
		return driverMemberId;
	}
	public void setDriverMemberId(Integer driverMemberId) {
		this.driverMemberId = driverMemberId;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverMobile() {
		return driverMobile;
	}
	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}
	public Integer getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getS_detail() {
		return s_detail;
	}
	public void setS_detail(String s_detail) {
		this.s_detail = s_detail;
	}
	public String getS_detailed_address() {
		return s_detailed_address;
	}
	public void setS_detailed_address(String s_detailed_address) {
		this.s_detailed_address = s_detailed_address;
	}
	public String getE_detail() {
		return e_detail;
	}
	public void setE_detail(String e_detail) {
		this.e_detail = e_detail;
	}
	public String getE_detailed_address() {
		return e_detailed_address;
	}
	public void setE_detailed_address(String e_detailed_address) {
		this.e_detailed_address = e_detailed_address;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getConfirmOrderTime() {
		return confirmOrderTime;
	}
	public void setConfirmOrderTime(Date confirmOrderTime) {
		this.confirmOrderTime = confirmOrderTime;
	}
	public Date getConfirmGoodsTime() {
		return confirmGoodsTime;
	}
	public void setConfirmGoodsTime(Date confirmGoodsTime) {
		this.confirmGoodsTime = confirmGoodsTime;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public String getSrcFullAddr() {
		return srcFullAddr;
	}
	public void setSrcFullAddr(String srcFullAddr) {
		this.srcFullAddr = srcFullAddr;
	}
	public String getDesFullAddr() {
		return desFullAddr;
	}
	public void setDesFullAddr(String desFullAddr) {
		this.desFullAddr = desFullAddr;
	}
	public Integer getOrderBeforeId() {
		return orderBeforeId;
	}
	public void setOrderBeforeId(Integer orderBeforeId) {
		this.orderBeforeId = orderBeforeId;
	}
	public String getLogisticsMemberId() {
		return logisticsMemberId;
	}
	public void setLogisticsMemberId(String logisticsMemberId) {
		this.logisticsMemberId = logisticsMemberId;
	}
    
}
