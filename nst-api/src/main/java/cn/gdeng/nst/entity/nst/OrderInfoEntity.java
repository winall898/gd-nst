package cn.gdeng.nst.entity.nst;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * 运单信息Entity
 * 
 * @author huangjianhua  2016年7月30日  上午9:33:45
 * @version 1.0
 */
@Entity(name = "order_info")
public class OrderInfoEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7633687315237740594L;

	/**
    *主键id
    */
    private Integer id;

    /**
    *货运订单号
    */
    private String orderNo;

    /**
    *关联货源id
    */
    private Integer sourceId;

    /**
    *关联车辆Id
    */
    private Integer carId;

    /**
    *预订单id
    */
    private Integer orderBeforeId;

    /**
    *货主memberId
    */
    private Integer shipperMemberId;

    /**
    *司机memberId
    */
    private Integer driverMemberId;

    /**
    *确认订单时间
    */
    private Date confirmOrderTime;

    /**
    *确认收货时间
    */
    private Date confirmGoodsTime;

    /**
    *取消订单时间(干掉)
    */
    private Date cancelOrderTime;

    /**
    *1 订单确认 2 确认收货 3 已取消 (干掉)
    */
    private Byte orderStatus;

    /**
    *运输费
    */
    private Double transportAmt;

    /**
    *支付状态 1待付款 2已付款 3已关闭 4已退款
    */
    private Short payStatus;

    /**
    *货主是否删除(0:未删除;1:已删除)
    */
    private Byte shipper_isDeleted;

    /**
    *司机是否删除(0:未删除;1:已删除)
    */
    private Byte driver_isDeleted;

    /**
    *创建人员ID
    */
    private String createUserId;

    /**
    *创建时间
    */
    private Date createTime;

    /**
    *修改人员ID
    */
    private String updateUserId;

    /**
    *修改时间
    */
    private Date updateTime;
    
    /**物流状态(冗余): 
     * 0 待支付预付款(待确认) 1待验货(已确认) 2已发货(已确认) 3车主已送达(已确认) 4已支付尾款(已完成) 5已关闭(见closeReason)
     * 
     */
    private Byte transStatus;
    
    /**
    *运单关闭原因 1 农商友取消采购单 2 验货不通过 3 验货超时(3天) 4 预付款支付超时 5 退预付款(拒绝收货)
    */
    private Byte closeReason;
    
    /**
    *预付款支付状态(6+1) 0 未支付 1 已支付
    */
    private Byte prePayStatus;

    @Id
    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "orderNo")
    public String getOrderNo(){

        return this.orderNo;
    }
    public void setOrderNo(String orderNo){

        this.orderNo = orderNo;
    }
    @Column(name = "sourceId")
    public Integer getSourceId(){

        return this.sourceId;
    }
    public void setSourceId(Integer sourceId){

        this.sourceId = sourceId;
    }
    @Column(name = "carId")
    public Integer getCarId(){

        return this.carId;
    }
    public void setCarId(Integer carId){

        this.carId = carId;
    }
    @Column(name = "orderBeforeId")
    public Integer getOrderBeforeId(){

        return this.orderBeforeId;
    }
    public void setOrderBeforeId(Integer orderBeforeId){

        this.orderBeforeId = orderBeforeId;
    }
    @Column(name = "shipperMemberId")
    public Integer getShipperMemberId(){

        return this.shipperMemberId;
    }
    public void setShipperMemberId(Integer shipperMemberId){

        this.shipperMemberId = shipperMemberId;
    }
    @Column(name = "driverMemberId")
    public Integer getDriverMemberId(){

        return this.driverMemberId;
    }
    public void setDriverMemberId(Integer driverMemberId){

        this.driverMemberId = driverMemberId;
    }
    @Column(name = "confirmOrderTime")
    public Date getConfirmOrderTime(){

        return this.confirmOrderTime;
    }
    public void setConfirmOrderTime(Date confirmOrderTime){

        this.confirmOrderTime = confirmOrderTime;
    }
    @Column(name = "confirmGoodsTime")
    public Date getConfirmGoodsTime(){

        return this.confirmGoodsTime;
    }
    public void setConfirmGoodsTime(Date confirmGoodsTime){

        this.confirmGoodsTime = confirmGoodsTime;
    }
    @Column(name = "cancelOrderTime")
    public Date getCancelOrderTime(){

        return this.cancelOrderTime;
    }
    public void setCancelOrderTime(Date cancelOrderTime){

        this.cancelOrderTime = cancelOrderTime;
    }
    @Column(name = "orderStatus")
    public Byte getOrderStatus(){

        return this.orderStatus;
    }
    public void setOrderStatus(Byte orderStatus){

        this.orderStatus = orderStatus;
    }
    @Column(name = "transportAmt")
    public Double getTransportAmt(){

        return this.transportAmt;
    }
    public void setTransportAmt(Double transportAmt){

        this.transportAmt = transportAmt;
    }
    @Column(name = "payStatus")
    public Short getPayStatus(){

        return this.payStatus;
    }
    public void setPayStatus(Short payStatus){

        this.payStatus = payStatus;
    }
    @Column(name = "shipper_isDeleted")
    public Byte getShipper_isDeleted(){

        return this.shipper_isDeleted;
    }
    public void setShipper_isDeleted(Byte shipper_isDeleted){

        this.shipper_isDeleted = shipper_isDeleted;
    }
    @Column(name = "driver_isDeleted")
    public Byte getDriver_isDeleted(){

        return this.driver_isDeleted;
    }
    public void setDriver_isDeleted(Byte driver_isDeleted){

        this.driver_isDeleted = driver_isDeleted;
    }
    @Column(name = "createUserId")
    public String getCreateUserId(){

        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){

        this.createUserId = createUserId;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){

        this.updateUserId = updateUserId;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
    @Column(name = "transStatus")
	public Byte getTransStatus() {
		return transStatus;
	}
	public void setTransStatus(Byte transStatus) {
		this.transStatus = transStatus;
	}
	@Column(name = "closeReason")
	public Byte getCloseReason() {
		return closeReason;
	}
	public void setCloseReason(Byte closeReason) {
		this.closeReason = closeReason;
	}
	@Column(name = "prePayStatus")
	public Byte getPrePayStatus() {
		return prePayStatus;
	}
	public void setPrePayStatus(Byte prePayStatus) {
		this.prePayStatus = prePayStatus;
	}
}