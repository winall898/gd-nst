package cn.gdeng.nst.entity.nst;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "member_car")
public class MemberCarEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8557956175236452348L;

	private Integer id;














    private Byte isCarriage;
    @Id
    public Integer getId(){

    }
    public void setId(Integer id){

    }
    @Column(name = "memberId")
    public Integer getMemberId(){

    }
    public void setMemberId(Integer memberId){

    }
    @Column(name = "carNumber")
    public String getCarNumber(){

    }
    public void setCarNumber(String carNumber){

    }
    @Column(name = "carType")
    public Byte getCarType(){

    }
    public void setCarType(Byte carType){

    }
    @Column(name = "`load`")
    public Double getLoad(){

    }
    public void setLoad(Double load){

    }
    @Column(name = "carLength")
    public Double getCarLength(){

    }
    public void setCarLength(Double carLength){

    }
    @Column(name = "vehicleUrl")
    public String getVehicleUrl(){

    }
    public void setVehicleUrl(String vehicleUrl){

    }
    @Column(name = "carHeadUrl")
    public String getCarHeadUrl(){

    }
    public void setCarHeadUrl(String carHeadUrl){

    }
    @Column(name = "carRearUrl")
    public String getCarRearUrl(){

    }
    public void setCarRearUrl(String carRearUrl){

    }
    public Byte getIsDeleted(){

    }
    public void setIsDeleted(Byte isDeleted){

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
    @Column(name = "isCarriage")
	public Byte getIsCarriage() {
		return isCarriage;
	}
	public void setIsCarriage(Byte isCarriage) {
		this.isCarriage = isCarriage;
	}
    
    
}