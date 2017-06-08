package cn.gdeng.nst.entity.nst;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 分配规则信息表
 * @author wujiang
 */
@Entity(name = "rule_info")
public class RuleInfoEntity  implements java.io.Serializable{

	private static final long serialVersionUID = -1883496371293297909L;

	private Integer id;

	/**
	 * 规则名称
	 */

	/**
	 * 规则启用/禁用
	 */
    private Byte onOff;
    
    /**
	 * 规则启用时间
	 */
    
    /**
	 * 规则禁用时间
	 */

	/**
	 * 货源类别 1 干线 2 同城
	 */

	/**
	 * 是否删除(0:未删除;1:已删除)
	 */

	/**
	 * 创建人员ID
	 */

	/**
	 * 创建时间
	 */

	/**
	 * 修改人员ID
	 */

	/**
	 * 修改时间
	 */
    /**
     * 货源所在地-省会Id
     */
    private Integer provinceId;

    /**
     * 货源所在地-城市Id
     */
    private Integer cityId;
    
    /**
     * 货源所在地-区域Id
     */
    private Integer areaId;

    @Id
    public Integer getId(){

    }
    public void setId(Integer id){

    }
    @Column(name = "name")
    public String getName(){

    }
    public void setName(String name){

    }
    @Column(name = "onOff")
    public Byte getOnOff() {
		return onOff;
	}
	public void setOnOff(Byte onOff) {
		this.onOff = onOff;
	}
	@Column(name = "onTime")
	public Date getOnTime() {
		return onTime;
	}
	public void setOnTime(Date onTime) {
		this.onTime = onTime;
	}
	@Column(name = "offTime")
	public Date getOffTime() {
		return offTime;
	}
	public void setOffTime(Date offTime) {
		this.offTime = offTime;
	}
    @Column(name = "sourceType")
    public Integer getSourceType(){

    }
    public void setSourceType(Integer sourceType){

    }
    @Column(name = "isDeleted")
    public Byte getIsDeleted(){

    }
    public void setIsDeleted(Byte isDeleted){

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
    @Column(name = "provinceId")
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	 @Column(name = "cityId")
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	 @Column(name = "areaId")
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	
}