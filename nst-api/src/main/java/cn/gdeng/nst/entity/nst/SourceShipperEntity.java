package cn.gdeng.nst.entity.nst;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "source_shipper")
public class SourceShipperEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5519420560635690579L;

	private Integer id;

    private Integer memberId;

    private Integer sourceType;

    private Integer sProvinceId;

    private Integer sCityId;

    private Integer sAreaId;

    private String sDetail;

    private String sDetailedAddress;

    private Double sLng;

    private Double sLat;

    private Integer eProvinceId;

    private Integer eCityId;

    private Integer eAreaId;

    private String eDetail;

    private String eDetailedAddress;

    private Double eLng;

    private Double eLat;

    private Date sendDate;

    private Byte goodsType;

    private Double totalWeight;

    private Integer totalSize;

    private String goodsName;

    private Byte carType;

    private Byte sendGoodsType;

    private Double carLength;

    private Double freight;
    
    private String remark;

    private Double mileage;

    private Byte clients;

    private Integer assignMemberId;

    private Byte sourceStatus = 1;

    private Byte isDeleted = 0;

    private String createUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;
    
    private Date realCreateTime;
    
    private Byte nstRule;
    
    private Integer version;
    
    /**
     * 0 非平台配送  1 平台配送。
     */
    private Byte platform;
    
    /**当前货主名称
     * 
     */
    private String shipperName;
    /**当前货主电话
     * 
     */
    private String shipperMobile;
    
    /**货源类型*/
    private Byte sourceGoodsType;

    @Id
    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "memberId")
    public Integer getMemberId(){

        return this.memberId;
    }
    public void setMemberId(Integer memberId){

        this.memberId = memberId;
    }
    @Column(name = "sourceType")
    public Integer getSourceType(){

        return this.sourceType;
    }
    public void setSourceType(Integer sourceType){

        this.sourceType = sourceType;
    }
    @Column(name = "s_provinceId")
    public Integer getSProvinceId(){

        return this.sProvinceId;
    }
    public void setSProvinceId(Integer sProvinceId){

        this.sProvinceId = sProvinceId;
    }
    @Column(name = "s_cityId")
    public Integer getSCityId(){

        return this.sCityId;
    }
    public void setSCityId(Integer sCityId){

        this.sCityId = sCityId;
    }
    @Column(name = "s_areaId")
    public Integer getSAreaId(){

        return this.sAreaId;
    }
    public void setSAreaId(Integer sAreaId){

        this.sAreaId = sAreaId;
    }
    @Column(name = "s_detail")
    public String getSDetail(){

        return this.sDetail;
    }
    public void setSDetail(String sDetail){

        this.sDetail = sDetail;
    }
    @Column(name = "s_detailed_address")
    public String getSDetailedAddress(){

        return this.sDetailedAddress;
    }
    public void setSDetailedAddress(String sDetailedAddress){

        this.sDetailedAddress = sDetailedAddress;
    }
    @Column(name = "s_lng")
    public Double getSLng(){

        return this.sLng;
    }
    public void setSLng(Double sLng){

        this.sLng = sLng;
    }
    @Column(name = "s_lat")
    public Double getSLat(){

        return this.sLat;
    }
    public void setSLat(Double sLat){

        this.sLat = sLat;
    }
    @Column(name = "e_provinceId")
    public Integer getEProvinceId(){

        return this.eProvinceId;
    }
    public void setEProvinceId(Integer eProvinceId){

        this.eProvinceId = eProvinceId;
    }
    @Column(name = "e_cityId")
    public Integer getECityId(){

        return this.eCityId;
    }
    public void setECityId(Integer eCityId){

        this.eCityId = eCityId;
    }
    @Column(name = "e_areaId")
    public Integer getEAreaId(){

        return this.eAreaId;
    }
    public void setEAreaId(Integer eAreaId){

        this.eAreaId = eAreaId;
    }
    @Column(name = "e_detail")
    public String getEDetail(){

        return this.eDetail;
    }
    public void setEDetail(String eDetail){

        this.eDetail = eDetail;
    }
    @Column(name = "e_detailed_address")
    public String getEDetailedAddress(){

        return this.eDetailedAddress;
    }
    public void setEDetailedAddress(String eDetailedAddress){

        this.eDetailedAddress = eDetailedAddress;
    }
    @Column(name = "e_lng")
    public Double getELng(){

        return this.eLng;
    }
    public void setELng(Double eLng){

        this.eLng = eLng;
    }
    @Column(name = "e_lat")
    public Double getELat(){

        return this.eLat;
    }
    public void setELat(Double eLat){

        this.eLat = eLat;
    }
    @Column(name = "sendDate")
    public Date getSendDate(){

        return this.sendDate;
    }
    public void setSendDate(Date sendDate){

        this.sendDate = sendDate;
    }
    @Column(name = "goodsType")
    public Byte getGoodsType(){

        return this.goodsType;
    }
    public void setGoodsType(Byte goodsType){

        this.goodsType = goodsType;
    }
    @Column(name = "totalWeight")
    public Double getTotalWeight(){

        return this.totalWeight;
    }
    public void setTotalWeight(Double totalWeight){

        this.totalWeight = totalWeight;
    }
    @Column(name = "totalSize")
    public Integer getTotalSize(){

        return this.totalSize;
    }
    public void setTotalSize(Integer totalSize){

        this.totalSize = totalSize;
    }
    @Column(name = "goodsName")
    public String getGoodsName(){

        return this.goodsName;
    }
    public void setGoodsName(String goodsName){

        this.goodsName = goodsName;
    }
    @Column(name = "carType")
    public Byte getCarType(){

        return this.carType;
    }
    public void setCarType(Byte carType){

        this.carType = carType;
    }
    @Column(name = "sendGoodsType")
    public Byte getSendGoodsType(){

        return this.sendGoodsType;
    }
    public void setSendGoodsType(Byte sendGoodsType){

        this.sendGoodsType = sendGoodsType;
    }
    @Column(name = "carLength")
    public Double getCarLength(){

        return this.carLength;
    }
    public void setCarLength(Double carLength){

        this.carLength = carLength;
    }
    @Column(name = "freight")
    public Double getFreight() {
    	
		return freight;
	}
	public void setFreight(Double freight) {
		
		this.freight = freight;
	}
	@Column(name = "remark")
    public String getRemark(){

        return this.remark;
    }
    public void setRemark(String remark){

        this.remark = remark;
    }
    @Column(name = "mileage")
    public Double getMileage(){

        return this.mileage;
    }
    public void setMileage(Double mileage){

        this.mileage = mileage;
    }
    @Column(name = "clients")
    public Byte getClients(){

        return this.clients;
    }
    public void setClients(Byte clients){

        this.clients = clients;
    }
    @Column(name = "assignMemberId")
    public Integer getAssignMemberId(){

        return this.assignMemberId;
    }
    public void setAssignMemberId(Integer assignMemberId){

        this.assignMemberId = assignMemberId;
    }
	@Column(name = "sourceStatus")
    public Byte getSourceStatus(){

        return this.sourceStatus;
    }
    public void setSourceStatus(Byte sourceStatus){

        this.sourceStatus = sourceStatus;
    }
    @Column(name = "isDeleted")
    public Byte getIsDeleted(){

        return this.isDeleted;
    }
    public void setIsDeleted(Byte isDeleted){

        this.isDeleted = isDeleted;
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
    @Column(name = "realCreateTime")
    public Date getRealCreateTime() {
		return realCreateTime;
	}
	public void setRealCreateTime(Date realCreateTime) {
		this.realCreateTime = realCreateTime;
	}
	@Column(name = "nstRule")
	public Byte getNstRule() {
		return nstRule;
	}
	public void setNstRule(Byte nstRule) {
		this.nstRule = nstRule;
	}
	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
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
	@Column(name = "platform")
	public Byte getPlatform() {
		return platform;
	}
	public void setPlatform(Byte platform) {
		this.platform = platform;
	}
	@Column(name = "sourceGoodsType")
	public Byte getSourceGoodsType() {
		return sourceGoodsType;
	}
	public void setSourceGoodsType(Byte sourceGoodsType) {
		this.sourceGoodsType = sourceGoodsType;
	}
	
    
}


