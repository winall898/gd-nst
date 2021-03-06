package cn.gdeng.nst.api.dto.order;

import java.io.Serializable;
import java.util.Date;

/**
 * 运单信息DTO
 * 
 * @author huangjianhua 2016年7月30日 上午10:33:45
 * @version 1.0
 */
public class OrderInfoDTO implements Serializable {

	private static final long serialVersionUID = -3835524742582056890L;

	private Integer id;
	/** 当前登陆用户ID */
	private Integer memberId;
	/** 货运订单号 */
	private String orderNo;
	/** 关联货源id */
	private Integer sourceId;
	/** 货主memberId */
	private Integer carId;
	/** 货主memberId */
	private Integer shipperMemberId;
	/** 司机memberId */
	private Integer driverMemberId;
	/** 确认订单时间 */
	private String confirmOrderTime;
	/** 确认收货时间 */
	private String confirmGoodsTime;
	/** 取消订单时间 */
	private String cancelOrderTime;
	/** 1 订单确认 2 确认收货 3 已取消 */
	private Byte orderStatus;
	/** 货主是否删除(0:未删除;1:已删除) */
	private Byte shipper_isDeleted;
	/** 司机是否删除(0:未删除;1:已删除) */
	private Byte driver_isDeleted;

	private String createUserId;

	private Date createTime;

	private String updateUserId;

	private Date updateTime;
	/** 预订单ID */
	private Integer orderBeforeId;
	/** 预付款状态(6+1) 0 未支付 1 已支付' */
	private Byte prePayStatus;

	/** 是否超时确认收货 */
	private String isHandleTimeout;
	/**
	 * 物流状态: 1待验货 2已发货 3车主已送达 4验货不通过 5 拒绝收货
	 */
	private Byte transStatus;

	public Integer getOrderBeforeId() {
		return orderBeforeId;
	}

	public void setOrderBeforeId(Integer orderBeforeId) {
		this.orderBeforeId = orderBeforeId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getShipperMemberId() {
		return shipperMemberId;
	}

	public void setShipperMemberId(Integer shipperMemberId) {
		this.shipperMemberId = shipperMemberId;
	}

	public Integer getDriverMemberId() {
		return driverMemberId;
	}

	public void setDriverMemberId(Integer driverMemberId) {
		this.driverMemberId = driverMemberId;
	}

	public String getConfirmOrderTime() {
		return confirmOrderTime;
	}

	public void setConfirmOrderTime(String confirmOrderTime) {
		this.confirmOrderTime = confirmOrderTime;
	}

	public String getConfirmGoodsTime() {
		return confirmGoodsTime;
	}

	public void setConfirmGoodsTime(String confirmGoodsTime) {
		this.confirmGoodsTime = confirmGoodsTime;
	}

	public String getCancelOrderTime() {
		return cancelOrderTime;
	}

	public void setCancelOrderTime(String cancelOrderTime) {
		this.cancelOrderTime = cancelOrderTime;
	}

	public Byte getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Byte orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Byte getShipper_isDeleted() {
		return shipper_isDeleted;
	}

	public void setShipper_isDeleted(Byte shipper_isDeleted) {
		this.shipper_isDeleted = shipper_isDeleted;
	}

	public Byte getDriver_isDeleted() {
		return driver_isDeleted;
	}

	public void setDriver_isDeleted(Byte driver_isDeleted) {
		this.driver_isDeleted = driver_isDeleted;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getIsHandleTimeout() {
		return isHandleTimeout;
	}

	public void setIsHandleTimeout(String isHandleTimeout) {
		this.isHandleTimeout = isHandleTimeout;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public Byte getPrePayStatus() {
		return prePayStatus;
	}

	public void setPrePayStatus(Byte prePayStatus) {
		this.prePayStatus = prePayStatus;
	}

	public Byte getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(Byte transStatus) {
		this.transStatus = transStatus;
	}

}
