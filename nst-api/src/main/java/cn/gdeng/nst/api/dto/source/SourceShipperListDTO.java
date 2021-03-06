package cn.gdeng.nst.api.dto.source;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import cn.gdeng.nst.enums.CarLengthEnum;
import cn.gdeng.nst.enums.CarTypeEnum;
import cn.gdeng.nst.enums.GoodsTypeEnum;

/**货源列表DTO，保存货源起始收发地和接单司机等信息。
 * @author wjguo
 * datetime 2016年8月8日 下午2:06:48  
 *
 */
public class SourceShipperListDTO implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6512506637693451491L;

	/**
	 * 数据库id
	 */
	private Integer id;

    /**
     * 关联的会员id
     */
    private Integer memberId;
    
    /**出发地 省份+城市+区域（/  作为分隔符）
     * 
     */
    private String s_detail;
    
    /**
     * 发货地详细地址(补充详情地址)
     */
    private String s_detailed_address;
    
    /**
     * 目的地 省份+城市+区域（/  作为分隔符）
     */
    private String e_detail;
    
    /**
     * 收货地详细地址(补充详情地址)
     */
    private String e_detailed_address;
    
    /**
     * 装货时间
     */
    private Date sendDate;
    
    /**
     * 出发地的全地址，包括市、区和详细地
     */
    private String srcFullAddr;
    
    /**
     * 目的地的全地址，包括市、区和详细地
     */
    private String desFullAddr;
    
    /**货物状态名称，如：已分配、已发布、待确认、已过期
     * 
     */
    private String goodsStatusName;
    
    /**货源状态码。1表示分配中，2表示待确认，3已发布，4已接受，5已过期
     * 
     */
    private String goodsStatusCode;
    
    /**当前货源是否可处理，例如接受和拒绝司机的接单,删除货源等。
     * 
     */
    private Boolean isCanDispose;
    
    /**司机手机号码
     * 
     */
    private String driverMobile;
    /**
     * 司机接单时间
     */
    private Date driverAcceptDate;
    /**
     * 分配的物流公司手机号码
     */
    private String assignMemberMobile;
    
    /**数据版本号
     * 
     */
    private Integer version;
    
    /**预订单id
     * 
     */
    private Integer orderBeforeId;
    
    /**服务器当前时间
     * 
     */
    private Date currentDate;
    /**货源创建时间,即发布时间。
     * 
     */
    private Date createTime;

  //PS:IOS 用于刷新货源新增下列字段  
    /**
     * 货物类型
     */
    private Byte goodsType;
    /**
     * 货物名称
     */
    private String goodsName;
    /**重量
     * 
     */
    private Double totalWeight;

    /**体积
     * 
     */
    private Integer totalSize;
    /**货源所需车型
     * 
     */
    private Byte carType;
    /**货源所需车长
     * 
     */
    private Double carLength;
    /**发货方式
     * 
     */
    private Byte sendGoodsType;
    /**意向运费
     * 
     */
    private Double freight;
    /**
     * 货主留言
     */
    private String remark;
    /**
     * 出发地经度
     */
    private BigDecimal slng;
    /**
     * 出发地纬度
     */
    private BigDecimal slat;
    /**
     * 目的地经度
     */
    private BigDecimal elng;
    /**
     * 目的地纬度
     */
    private BigDecimal elat;
    /**
     * 出发地省会
     */
    private String sprovinceName;
    /**
     * 出发地城市
     */
    private String scityName;
    /**
     * 出发地区域
     */
    private String sareaName;
    /**
     * 目的地省会
     */
    private String eprovinceName;
    /**
     * 目的地城市	
     */
    private String ecityName;
    /**
     * 目的地区域
     */
    private String eareaName;
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

	public String getS_detail() {
		if(StringUtils.isNotBlank(this.s_detail)){
			String[] saddress=this.s_detail.split("/");
			this.setSprovinceName(saddress[0]);
			this.setScityName(saddress[1]);
			if(saddress.length==3){
				this.setSareaName(saddress[2]);
			}
		}
		return s_detail;
	}

	public void setS_detail(String s_detail) {
		this.s_detail = s_detail;
	}

	public String getS_detailed_address() {
		return s_detailed_address;
	}

	public void setS_detailed_address(String s_detailed_address) {
		this.s_detailed_address = s_detailed_address;
	}

	public String getE_detail() {
		if(StringUtils.isNotBlank(this.e_detail)){
			String[] eaddress=this.e_detail.split("/");
			this.setEprovinceName(eaddress[0]);
			this.setEcityName(eaddress[1]);
			if(eaddress.length==3){
				this.setEareaName(eaddress[2]);
			}
		}
		return e_detail;
	}

	public void setE_detail(String e_detail) {
		this.e_detail = e_detail;
	}

	public String getE_detailed_address() {
		return e_detailed_address;
	}

	public void setE_detailed_address(String e_detailed_address) {
		this.e_detailed_address = e_detailed_address;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getSrcFullAddr() {
		return srcFullAddr;
	}

	public void setSrcFullAddr(String srcFullAddr) {
		this.srcFullAddr = srcFullAddr;
	}

	public String getDesFullAddr() {
		return desFullAddr;
	}

	public void setDesFullAddr(String desFullAddr) {
		this.desFullAddr = desFullAddr;
	}

	public String getGoodsStatusName() {
		return goodsStatusName;
	}

	public void setGoodsStatusName(String goodsStatusName) {
		this.goodsStatusName = goodsStatusName;
	}

	public Boolean getIsCanDispose() {
		return isCanDispose;
	}

	public void setIsCanDispose(Boolean isCanDispose) {
		this.isCanDispose = isCanDispose;
	}

	public String getDriverMobile() {
		return driverMobile;
	}

	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}

	public Date getDriverAcceptDate() {
		return driverAcceptDate;
	}

	public void setDriverAcceptDate(Date driverAcceptDate) {
		this.driverAcceptDate = driverAcceptDate;
	}

	public String getAssignMemberMobile() {
		return assignMemberMobile;
	}

	public void setAssignMemberMobile(String assignMemberMobile) {
		this.assignMemberMobile = assignMemberMobile;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getOrderBeforeId() {
		return orderBeforeId;
	}

	public void setOrderBeforeId(Integer orderBeforeId) {
		this.orderBeforeId = orderBeforeId;
	}

	public String getGoodsStatusCode() {
		return goodsStatusCode;
	}

	public void setGoodsStatusCode(String goodsStatusCode) {
		this.goodsStatusCode = goodsStatusCode;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Byte getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Byte goodsType) {
		this.goodsType = goodsType;
	}

	public String getGoodsTypeName() {
		return GoodsTypeEnum.getNameByCode(getGoodsType());
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}

	public Byte getCarType() {
		return carType;
	}

	public void setCarType(Byte carType) {
		this.carType = carType;
	}

	public String getCarTypeName() {
		return CarTypeEnum.getNameByCode(getCarType());
	}

	public Double getCarLength() {
		return carLength;
	}

	public void setCarLength(Double carLength) {
		this.carLength = carLength;
	}

	public String getCarLengthName() {
		return CarLengthEnum.getNameByCode(getCarLength());
	}

	public Byte getSendGoodsType() {
		return sendGoodsType;
	}

	public void setSendGoodsType(Byte sendGoodsType) {
		this.sendGoodsType = sendGoodsType;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getSlng() {
		return slng;
	}

	public void setSlng(BigDecimal slng) {
		this.slng = slng;
	}

	public BigDecimal getSlat() {
		return slat;
	}

	public void setSlat(BigDecimal slat) {
		this.slat = slat;
	}

	public BigDecimal getElng() {
		return elng;
	}

	public void setElng(BigDecimal elng) {
		this.elng = elng;
	}

	public BigDecimal getElat() {
		return elat;
	}

	public void setElat(BigDecimal elat) {
		this.elat = elat;
	}

	public String getSprovinceName() {
		return sprovinceName;
	}

	public void setSprovinceName(String sprovinceName) {
		this.sprovinceName = sprovinceName;
	}

	public String getScityName() {
		return scityName;
	}

	public void setScityName(String scityName) {
		this.scityName = scityName;
	}

	public String getSareaName() {
		return sareaName;
	}

	public void setSareaName(String sareaName) {
		this.sareaName = sareaName;
	}

	public String getEprovinceName() {
		return eprovinceName;
	}

	public void setEprovinceName(String eprovinceName) {
		this.eprovinceName = eprovinceName;
	}

	public String getEcityName() {
		return ecityName;
	}

	public void setEcityName(String ecityName) {
		this.ecityName = ecityName;
	}

	public String getEareaName() {
		return eareaName;
	}

	public void setEareaName(String eareaName) {
		this.eareaName = eareaName;
	}
	
	
}
