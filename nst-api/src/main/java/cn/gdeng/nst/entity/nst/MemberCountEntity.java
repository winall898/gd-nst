package cn.gdeng.nst.entity.nst;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "member_count")
public class MemberCountEntity  implements java.io.Serializable{
    
    private static final long serialVersionUID = -7290098147420186948L;
    private Integer id;
    /**
    *会员id
    */
    private Integer memberId;

    *司机成功接单数
    */
    private Integer driverOrderCount=0;

    *司机总收益
    */
    private Double driverIcome=0.0;

    *物流公司总接单数
    */
    private Integer confirmSourceCount=0;

    *货源分配总数
    */
    private Integer AutoSourceCount=0;

    *创建人员ID
    */
    private String createUserId;

    *创建时间
    */
    private Date createTime;

    *修改人员ID
    */
    private String updateUserId;

    *修改时间
    */
    private Date updateTime;
    /**
     * 用户注册时间
     */
    private Date regTime;

    @Id
    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    
    public Integer getMemberId(){

    }
    public void setMemberId(Integer memberId){

    }
    @Column(name = "driverOrderCount")
    public Integer getDriverOrderCount(){

    }
    public void setDriverOrderCount(Integer driverOrderCount){

    }
    @Column(name = "driverIcome")
    public Double getDriverIcome(){

    }
    public void setDriverIcome(Double driverIcome){

    }
    @Column(name = "confirmSourceCount")
    public Integer getConfirmSourceCount(){

    }
    public void setConfirmSourceCount(Integer confirmSourceCount){

    }
    @Column(name = "AutoSourceCount")
    public Integer getAutoSourceCount(){

    }
    public void setAutoSourceCount(Integer AutoSourceCount){

    }
    @Column(name = "createUserId")
    public String getCreateUserId(){

    }
    public void setCreateUserId(String createUserId){

    }
 
    public Date getCreateTime(){

    }
    public void setCreateTime(Date createTime){

    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

    }
    public void setUpdateUserId(String updateUserId){

    }
    public Date getUpdateTime(){

    }
    public void setUpdateTime(Date updateTime){

    }
    @Column(name = "regTime")
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
}