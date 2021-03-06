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
	 */    private String name;

	/**
	 * 规则启用/禁用
	 */
    private Byte onOff;
    
    /**
	 * 规则启用时间
	 */    private Date onTime;
    
    /**
	 * 规则禁用时间
	 */    private Date offTime;

	/**
	 * 货源类别 1 干线 2 同城
	 */    private Integer sourceType;

	/**
	 * 是否删除(0:未删除;1:已删除)
	 */    private Byte isDeleted;

	/**
	 * 创建人员ID
	 */    private String createUserId;

	/**
	 * 创建时间
	 */    private Date createTime;

	/**
	 * 修改人员ID
	 */    private String updateUserId;

	/**
	 * 修改时间
	 */    private Date updateTime;
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

    @Id    @Column(name = "id")
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    @Column(name = "name")
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
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
        return this.sourceType;
    }
    public void setSourceType(Integer sourceType){
        this.sourceType = sourceType;
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
