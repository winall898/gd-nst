package cn.gdeng.nst.admin.dto.admin;

import java.util.Date;


public class MemberCerDTO implements java.io.Serializable{

    private static final long serialVersionUID = 2716266534091840151L;
    
    private Integer id;

    private Integer memberId;
    
    private String account;

    private String realName;

    private String companyName;
    
    private String mobile;
    
    private String iconUrl;

    private Byte userType;
    
    private Byte certificFrom;

    private String idCard;

    private String idCardUrl_z;

    private String idCardUrl_f;

    private String doorUrl;

    private String visitingCardUrl;

    private String bzlUrl;
    
    private String groupCardUrl;
    
    private Integer carId;
    
    /**车辆是否被注册认证：0否*/
    private Integer carAuth;
    
    private String carNumber;
    
    private String carHeadUrl;
    
    private String carRearUrl;
    
    private String vehicleUrl;

    private Date passTime;

    private Byte cerStatus;

    private String createUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Byte getUserType() {
		return userType;
	}

	public void setUserType(Byte userType) {
		this.userType = userType;
	}

	public Byte getCertificFrom() {
		return certificFrom;
	}

	public void setCertificFrom(Byte certificFrom) {
		this.certificFrom = certificFrom;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getIdCardUrl_z() {
		return idCardUrl_z;
	}

	public void setIdCardUrl_z(String idCardUrl_z) {
		this.idCardUrl_z = idCardUrl_z;
	}

	public String getIdCardUrl_f() {
		return idCardUrl_f;
	}

	public void setIdCardUrl_f(String idCardUrl_f) {
		this.idCardUrl_f = idCardUrl_f;
	}

	public String getDoorUrl() {
		return doorUrl;
	}

	public void setDoorUrl(String doorUrl) {
		this.doorUrl = doorUrl;
	}

	public String getVisitingCardUrl() {
		return visitingCardUrl;
	}

	public void setVisitingCardUrl(String visitingCardUrl) {
		this.visitingCardUrl = visitingCardUrl;
	}

	public String getBzlUrl() {
		return bzlUrl;
	}

	public void setBzlUrl(String bzlUrl) {
		this.bzlUrl = bzlUrl;
	}

	public String getGroupCardUrl() {
		return groupCardUrl;
	}

	public void setGroupCardUrl(String groupCardUrl) {
		this.groupCardUrl = groupCardUrl;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public Integer getCarAuth() {
		return carAuth;
	}

	public void setCarAuth(Integer carAuth) {
		this.carAuth = carAuth;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getCarHeadUrl() {
		return carHeadUrl;
	}

	public void setCarHeadUrl(String carHeadUrl) {
		this.carHeadUrl = carHeadUrl;
	}

	public String getCarRearUrl() {
		return carRearUrl;
	}

	public void setCarRearUrl(String carRearUrl) {
		this.carRearUrl = carRearUrl;
	}

	public String getVehicleUrl() {
		return vehicleUrl;
	}

	public void setVehicleUrl(String vehicleUrl) {
		this.vehicleUrl = vehicleUrl;
	}

	public Date getPassTime() {
		return passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}

	public Byte getCerStatus() {
		return cerStatus;
	}

	public void setCerStatus(Byte cerStatus) {
		this.cerStatus = cerStatus;
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
}
