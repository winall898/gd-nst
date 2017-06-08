package cn.gdeng.nst.entity.nst;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 物流公司找车线路表
 * @author DJB
 *
 */
@Entity(name = "find_car_line")
public class FindCarLineEntity  implements java.io.Serializable{

	private static final long serialVersionUID = 5257837964590379345L;

	
	private Integer id;//主键
















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
    @Column(name = "s_provinceId")
    public Integer getS_provinceId(){

    }
    public void setS_provinceId(Integer s_provinceId){

    }
    @Column(name = "s_cityId")
    public Integer getS_cityId(){

    }
    public void setS_cityId(Integer s_cityId){

    }
    @Column(name = "s_areaId")
    public Integer getS_areaId(){

    }
    public void setS_areaId(Integer s_areaId){

    }
    @Column(name = "s_detail")
    public String getS_detail(){

    }
    public void setS_detail(String s_detail){

    }
    @Column(name = "e_provinceId")
    public Integer getE_provinceId(){

    }
    public void setE_provinceId(Integer e_provinceId){

    }
    @Column(name = "e_cityId")
    public Integer getE_cityId(){

    }
    public void setE_cityId(Integer e_cityId){

    }
    @Column(name = "e_areaId")
    public Integer getE_areaId(){

    }
    public void setE_areaId(Integer e_areaId){

    }
    @Column(name = "e_detail")
    public String getE_detail(){

    }
    public void setE_detail(String e_detail){

    }
    @Column(name = "isSelect")
    public Byte getIsSelect(){

    }
    public void setIsSelect(Byte isSelect){

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
    @Column(name = "isDeleted")
    public Byte getIsDeleted(){

    }
    public void setIsDeleted(Byte isDeleted){

    }
}