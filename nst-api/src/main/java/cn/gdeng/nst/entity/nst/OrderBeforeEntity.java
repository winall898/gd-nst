package cn.gdeng.nst.entity.nst;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * 预运单表Entity
 * 
 * @author huangjianhua  2016年7月30日  上午9:31:23
 * @version 1.0
 */
@Entity(name = "order_before")
public class OrderBeforeEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 366920815967401001L;
	
	private Integer id;
    /**关联货源id   */
    /**接单车辆ID			*/
	private Integer carId;
    /**货主memberId     */
    /**司机memberId     */
    /** 1 已接单 2 已取消(司机) 3 已拒绝(货主) 4 已生成订单    */
 




    /**货主联系人 */
    private String shipperName;
    /**货主手机     */
    private String  shipperMobile;
    /**司机联系人     */
    private String driverName;
    /**司机手机    */
    private String driverMobile;
    /** 货主是否删除(0:未删除;1:已删除)     */
    private Byte shipper_isDeleted;
    /**  司机是否删除(0:未删除;1:已删除)    */
    private Byte driver_isDeleted;
    

    @Id
    public Integer getId(){

    }
    public void setId(Integer id){

    }
    @Column(name = "sourceId")
    public Integer getSourceId(){

    }
    public void setSourceId(Integer sourceId){

    }
    @Column(name = "shipperMemberId")
    public Integer getShipperMemberId(){

    }
    public void setShipperMemberId(Integer shipperMemberId){

    }
    @Column(name = "driverMemberId")
    public Integer getDriverMemberId(){

    }
    public void setDriverMemberId(Integer driverMemberId){

    }
    @Column(name = "sourceStatus")
    public Byte getSourceStatus(){

    }
    public void setSourceStatus(Byte sourceStatus){

    }
  /*  @Column(name = "isDeleted")
    public Byte getIsDeleted(){

    }
    public void setIsDeleted(Byte isDeleted){

    }*/
    @Column(name = "createUserId")
    public String getCreateUserId(){

    }
    public void setCreateUserId(String createUserId){

    }
    @Column(name = "createTime")
    public Date getCreateTime(){

    }
    public void setCreateTime(Date createTime){

    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

    }
    public void setUpdateUserId(String updateUserId){

    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

    }
    public void setUpdateTime(Date updateTime){

    }
    @Column(name = "carId")
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	
	 @Column(name = "shipperName")
	public String getShipperName() {
		return shipperName;
	}
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}
	
	 @Column(name = "shipperMobile")
	public String getShipperMobile() {
		return shipperMobile;
	}
	public void setShipperMobile(String shipperMobile) {
		this.shipperMobile = shipperMobile;
	}
	 @Column(name = "driverName")
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	 @Column(name = "driverMobile")
	public String getDriverMobile() {
		return driverMobile;
	}
	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}
	
	 @Column(name = "shipper_isDeleted")
	public Byte getShipper_isDeleted() {
		return shipper_isDeleted;
	}
	public void setShipper_isDeleted(Byte shipper_isDeleted) {
		this.shipper_isDeleted = shipper_isDeleted;
	}
	
	 @Column(name = "driver_isDeleted")
	public Byte getDriver_isDeleted() {
		return driver_isDeleted;
	}
	public void setDriver_isDeleted(Byte driver_isDeleted) {
		this.driver_isDeleted = driver_isDeleted;
	}
     
	
}