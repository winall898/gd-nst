package cn.gdeng.nst.entity.nst;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "member_info")
public class MemberInfoEntity  implements java.io.Serializable{


	private Integer id;






    
    private Integer cerCompanyStatus;    









    
    private String deviceTokens;
    
    private Integer deviceType;
    
    private Integer deviceApp;
    
    private Byte appoint;
    
    private String account;
    
    @Id
    public Integer getId(){

    }
    public void setId(Integer id){

    }
   
    @Column(name = "mobile")
    public String getMobile(){

    }
    public void setMobile(String mobile){

    }
    @Column(name = "password")
    public String getPassword(){

    }
    public void setPassword(String password){

    }
    @Column(name = "appType")
    public Byte getAppType(){

    }
    public void setAppType(Byte appType){

    }
    @Column(name = "serviceType")
    public Byte getServiceType(){

    }
    public void setServiceType(Byte serviceType){

    }
    @Column(name = "userName")
    public String getUserName(){

    }
    public void setUserName(String userName){

    }
    @Column(name = "iconUrl")
    public String getIconUrl(){

    }
    public void setIconUrl(String iconUrl){

    }
    @Column(name = "latestLoginTime")
    public Date getLatestLoginTime(){

    }
    public void setLatestLoginTime(Date latestLoginTime){

    }
    @Column(name = "regetype")
    public Byte getRegetype(){

    }
    public void setRegetype(Byte regetype){

    }
   
    @Column(name = "status")
    public Byte getStatus(){

    }
    public void setStatus(Byte status){

    }
    @Column(name = "remark")
    public String getRemark(){

    }
    public void setRemark(String remark){

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
    @Column(name = "cerPersonalStatus")
	public Integer getCerPersonalStatus() {
		return cerPersonalStatus;
	}
   
	public void setCerPersonalStatus(Integer cerPersonalStatus) {
		this.cerPersonalStatus = cerPersonalStatus;
	}
	@Column(name = "cerCompanyStatus")
	public Integer getCerCompanyStatus() {
		return cerCompanyStatus;
	}
	
	public void setCerCompanyStatus(Integer cerCompanyStatus) {
		this.cerCompanyStatus = cerCompanyStatus;
	}
	@Column(name = "deviceTokens")
	public String getDeviceTokens() {
		return deviceTokens;
	}
	public void setDeviceTokens(String deviceTokens) {
		this.deviceTokens = deviceTokens;
	}
	@Column(name = "deviceType")
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	@Column(name = "appoint")
	public Byte getAppoint() {
		return appoint;
	}
	public void setAppoint(Byte appoint) {
		this.appoint = appoint;
	}
	@Column(name = "account")
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getDeviceApp() {
		return deviceApp;
	}
	public void setDeviceApp(Integer deviceApp) {
		this.deviceApp = deviceApp;
	}
    
     
}