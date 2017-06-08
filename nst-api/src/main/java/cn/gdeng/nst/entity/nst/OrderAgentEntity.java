package cn.gdeng.nst.entity.nst;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * 信息订单Entity
 * 
 * @author huangjianhua  2016年7月30日  上午9:32:41
 * @version 1.0
 */
@Entity(name = "order_agent")
public class OrderAgentEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3447614431077161798L;
	
	private Integer id;
    /**  信息费订单号     */
    /** 货源id      */
    /** 关联的预运单ID      */
    private Integer orderBeforeId;
    
    /** 物流公司memberId      */
    /** 司机memberId      */
    /** 接单时间      */
    /** 物流公司确认时间      */
    /** 1 已确认 2 已完成      */
    /** 1：已支付 2：支付失败*/
    private Short payStatus;
    
    private Double infoAmt;
    



    
    private String logisticName;
    private String logisticMobile;
    
    private String driverName;
    
    private String driverMobile;
    
    
    
    

    @Id
    public Integer getId(){

    }
    public void setId(Integer id){

    }
    @Column(name = "orderNo")
    public String getOrderNo(){

    }
    
    @Column(name = "orderBeforeId")
    public Integer getOrderBeforeId() {
		return orderBeforeId;
	}
	public void setOrderBeforeId(Integer orderBeforeId) {
		this.orderBeforeId = orderBeforeId;
	}

	public void setOrderNo(String orderNo){

    }
    @Column(name = "sourceId")
    public Integer getSourceId(){

    }
    public void setSourceId(Integer sourceId){

    }
    @Column(name = "logisticMemberId")
    public Integer getLogisticMemberId(){

    }
    public void setLogisticMemberId(Integer logisticMemberId){

    }
    @Column(name = "driverMemberId")
    public Integer getDriverMemberId(){

    }
    public void setDriverMemberId(Integer driverMemberId){

    }
    @Column(name = "confirmTime")
    public Date getConfirmTime(){

    }
    public void setConfirmTime(Date confirmTime){

    }
    @Column(name = "logisticTime")
    public Date getLogisticTime(){

    }
    public void setLogisticTime(Date logisticTime){

    }
    @Column(name = "orderStatus")
    public Byte getOrderStatus(){

    }
    public void setOrderStatus(Byte orderStatus){

    }
    @Column(name = "payStatus")
    public Short getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Short payStatus) {
		this.payStatus = payStatus;
	}
	@Column(name = "InfoAmt")
	public Double getInfoAmt() {
		return infoAmt;
	}
	public void setInfoAmt(Double infoAmt) {
		this.infoAmt = infoAmt;
	}
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
    
    
    @Column(name = "logisticName")
	public String getLogisticName() {
		return logisticName;
	}
	public void setLogisticName(String logisticName) {
		this.logisticName = logisticName;
	}
	
	@Column(name = "logisticMobile")
	public String getLogisticMobile() {
		return logisticMobile;
	}
	public void setLogisticMobile(String logisticMobile) {
		this.logisticMobile = logisticMobile;
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
    
    
    
    
    
}