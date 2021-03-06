package cn.gdeng.nst.api.vo.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import cn.gdeng.nst.enums.CarTypeEnum;
import cn.gdeng.nst.enums.GoodsTypeEnum;
import cn.gdeng.nst.enums.NstRuleEnum2;
import cn.gdeng.nst.enums.OrderAgentStatusEnum;
import cn.gdeng.nst.enums.PayStatusEnum;
import cn.gdeng.nst.enums.SendGoodsTypeEnum;
import cn.gdeng.nst.enums.SourceStatusClientEnum;

/**
 * 
 * 信息订单PageVo
 * 
 * @author huangjianhua 2016年8月9日 下午2:27:46
 * @version 1.0
 */
public class OrderAgentVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1634553417960799133L;

	private Integer id;
	/** 接单司机车牌 */
	private String driverCarNumber;
	/** 接单司机车类型 */
	private Integer driverCarType;
	/** 接单司机车载重 */
	private BigDecimal driverCarLoad;
	/** 接单司机车长 */
	private BigDecimal driverCarLength;
	/** 司机真实姓名 */
	private String driverRealName;
	/** 司机认证状态 */
	private Integer driverCerStatus;
	/** 司机电话 */
	private String driverMobile;
	/** 司机头像 */
	private String driverIconUrl;
	/** 物流公司名称 */
	private String companyName;
	/** 信息订单号 */
	private String orderAgentNo;
	/** 货运订单号 */
	private String orderInfoNo;
	/**货源订单状态			*/
	private String orderStatusStr;
	/** 信息订单状态 */
	private Integer orderAgentStatus;
	/** 信息生成时间 */
	private Date agentConfirmTime;
	/** 信息确认订单时间 */
	private Date agentLogisticTime;
	/** 信息费 */
	private BigDecimal payMoney;
	/** 付款状态 */
	private String payStatus;
	/** 支付时间 */
	private Date payTime;
	/** 平台支付流水 */
	private String platformPayWater;
	/** 付款方 */
	private String payName;
	/** 货主真实姓名 */
	private String shipperRealName;
	/** 货主状态 */
	private Integer shipperCerStatus;
	/** 货主头像 */
	private String shipperIconUrl;
	/**接单状态 **/
	private Integer orderStatus;
	
	/**
	 * 货源ID
	 */
	private Integer sourceId;
	/** 出发地   */
	private String s_detail;
	/** 发货地详细地址   */
	private String s_detailed_address;
	/**目的地    */
	private String e_detail;
	/** 收货地详细地址   */
	private String e_detailed_address;
	 /**里程 	*/
    private BigDecimal mileage;
	 /** 装车时间            */
    private Date sendDate;
    /** 车长       */
    private BigDecimal carLength;
    /** 车辆类型       */
    private Integer carType;
    /** 货物类型     */
    private Integer goodsTypeId;
    /** 发货方式     */
    private Integer sendGoodsType;
    /** 货物名称       */
    private String goodsName;
    
    private BigDecimal totalWeight;
    
    private BigDecimal totalSize;
    /**货主留言     */
    private String remark;
    /**	意向运费	*/
    private BigDecimal freight;
    /*** 货源来源    */
    private String goodsSource;
    private Byte sourceStatus;//货源状态
    private String sourceStatusStr; //货源状态中文
    
    private Integer nstRule;
    
    /*** 当前货主名称 */
    private String shipperName;
    /*** 当前货主电话  */
    private String shipperMobile;
    /**
     * 出发地的全地址，包括省市区和详细地
     */
    private String srcFullAddr;
    
    /**
     * 目的地的全地址，包括省市区和详细地
     */
    private String desFullAddr;
    
    /**
     * 会员ID
     */
    private Integer memberId;
    /**
     * 联系人
     */
    private String userName;
    
    /**
     * 头像
     */
    private String iconUrl;
    /**
     * 账号
     */
    private String mobile;
    
    /**
     * 认证状态:0:未认证 1:认证中 2:认证通过 3:认证未通过
     */
    private Byte cerPersonalStatus;
    
    private String cerPersonal;
    
    /**服务器当前时间
     * 
     */
    private Date currentDate;
    
    /*** 信息来源    */
    private String msgSource;
    /**	物流状态:		*/
    private Integer transStatus;
    
    private Integer  platform;
    
	/**	1 干线 2 同城	*/
    private Integer sourceType;
    
    /**
     * 发布时间
     */
    private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDriverCarNumber() {
		return driverCarNumber;
	}

	public void setDriverCarNumber(String driverCarNumber) {
		this.driverCarNumber = driverCarNumber;
	}

	public Integer getDriverCarType() {
		return driverCarType;
	}

	public void setDriverCarType(Integer driverCarType) {
		this.driverCarType = driverCarType;
	}

	public String getDriverCarTypeStr() {
		return CarTypeEnum.getNameByCode(getDriverCarType()==null?null:getDriverCarType().byteValue());
	}

	public BigDecimal getDriverCarLoad() {
		return driverCarLoad;
	}

	public void setDriverCarLoad(BigDecimal driverCarLoad) {
		this.driverCarLoad = driverCarLoad;
	}

	public BigDecimal getDriverCarLength() {
		return driverCarLength;
	}

	public void setDriverCarLength(BigDecimal driverCarLength) {
		this.driverCarLength = driverCarLength;
	}

	public String getDriverRealName() {
		return driverRealName;
	}

	public void setDriverRealName(String driverRealName) {
		this.driverRealName = driverRealName;
	}

	public Integer getDriverCerStatus() {
		return driverCerStatus;
	}

	public void setDriverCerStatus(Integer driverCerStatus) {
		this.driverCerStatus = driverCerStatus;
	}

	public String getDriverMobile() {
		return driverMobile;
	}

	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}

	public String getDriverIconUrl() {
		return driverIconUrl;
	}

	public void setDriverIconUrl(String driverIconUrl) {
		this.driverIconUrl = driverIconUrl;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOrderAgentNo() {
		return orderAgentNo;
	}

	public void setOrderAgentNo(String orderAgentNo) {
		this.orderAgentNo = orderAgentNo;
	}

	public String getOrderInfoNo() {
		return orderInfoNo;
	}

	public void setOrderInfoNo(String orderInfoNo) {
		this.orderInfoNo = orderInfoNo;
	}

	public Integer getOrderAgentStatus() {
		return orderAgentStatus;
	}

	public void setOrderAgentStatus(Integer orderAgentStatus) {
		this.orderAgentStatus = orderAgentStatus;
	}

	public String getOrderAgentStatusStr() {
		return OrderAgentStatusEnum.getNameByCode(getOrderAgentStatus()==null?null:getOrderAgentStatus().byteValue());
	}

	public Date getAgentConfirmTime() {
		return agentConfirmTime;
	}

	public void setAgentConfirmTime(Date agentConfirmTime) {
		this.agentConfirmTime = agentConfirmTime;
	}

	public Date getAgentLogisticTime() {
		return agentLogisticTime;
	}

	public void setAgentLogisticTime(Date agentLogisticTime) {
		this.agentLogisticTime = agentLogisticTime;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayStatusStr() {
		return PayStatusEnum.getNameByCode(getPayStatus()==null?null:Short.valueOf(getPayStatus()));
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPlatformPayWater() {
		return platformPayWater;
	}

	public void setPlatformPayWater(String platformPayWater) {
		this.platformPayWater = platformPayWater;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getShipperRealName() {
		return shipperRealName;
	}

	public void setShipperRealName(String shipperRealName) {
		this.shipperRealName = shipperRealName;
	}

	public Integer getShipperCerStatus() {
		return shipperCerStatus;
	}

	public void setShipperCerStatus(Integer shipperCerStatus) {
		this.shipperCerStatus = shipperCerStatus;
	}

	public String getShipperIconUrl() {
		return shipperIconUrl;
	}

	public void setShipperIconUrl(String shipperIconUrl) {
		this.shipperIconUrl = shipperIconUrl;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public String getS_detail() {
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

	public BigDecimal getMileage() {
		return mileage;
	}

	public void setMileage(BigDecimal mileage) {
		this.mileage = mileage;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public BigDecimal getCarLength() {
		return carLength;
	}

	public void setCarLength(BigDecimal carLength) {
		this.carLength = carLength;
	}

	public Integer getCarType() {
		return carType;
	}

	public void setCarType(Integer carType) {
		this.carType = carType;
	}

	public String getCarTypeStr() {
		return CarTypeEnum.getNameByCode(getCarType()==null?null:getCarType().byteValue());
	}
	
	public Integer getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(Integer goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	public String getGoodsTypeStr() {
		return GoodsTypeEnum.getNameByCode(getGoodsTypeId()==null?null:getGoodsTypeId().byteValue());
	}

	public Integer getSendGoodsType() {
		return sendGoodsType;
	}

	public void setSendGoodsType(Integer sendGoodsType) {
		this.sendGoodsType = sendGoodsType;
	}

	public String getSendGoodsTypeStr() {
		return SendGoodsTypeEnum.getNameByCode(getSendGoodsType()==null?null:getSendGoodsType().byteValue());
	}

	public String getGoodsName() {
		//产品给出优化方案：货品名称显示10个字符，超过部分用省略号代替
		if(StringUtils.isNotBlank(this.goodsName)&&(this.goodsName.length()>10)){
			StringBuffer sb=new StringBuffer();
			sb.append(this.goodsName.substring(0, 11));
			sb.append("...");
			return sb.toString();
		}
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}

	public BigDecimal getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(BigDecimal totalSize) {
		this.totalSize = totalSize;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public String getGoodsSource() {
		return goodsSource;
	}

	public void setGoodsSource(String goodsSource) {
		this.goodsSource = goodsSource;
	}

	public Byte getSourceStatus() {
		return sourceStatus;
	}

	public void setSourceStatus(Byte sourceStatus) {
		this.sourceStatus = sourceStatus;
	}

	public String getSourceStatusStr() {
		return sourceStatusStr;
	}

	public void setSourceStatusStr(String sourceStatusStr) {
		this.sourceStatusStr = sourceStatusStr;
	}

	public Integer getNstRule() {
		return nstRule;
	}

	public void setNstRule(Integer nstRule) {
		this.nstRule = nstRule;
	}

	public String getNstRuleStr() {
		return NstRuleEnum2.getNameByCode(getNstRule().byteValue());
	}

	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	public String getShipperMobile() {
		return shipperMobile;
	}

	public void setShipperMobile(String shipperMobile) {
		this.shipperMobile = shipperMobile;
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

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Byte getCerPersonalStatus() {
		return cerPersonalStatus;
	}

	public void setCerPersonalStatus(Byte cerPersonalStatus) {
		this.cerPersonalStatus = cerPersonalStatus;
	}

	public String getCerPersonal() {
		return cerPersonal;
	}

	public void setCerPersonal(String cerPersonal) {
		this.cerPersonal = cerPersonal;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public String getMsgSource() {
		return msgSource;
	}

	public void setMsgSource(String msgSource) {
		this.msgSource = msgSource;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public Integer getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(Integer transStatus) {
		this.transStatus = transStatus;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public void setOrderStatusStr(String orderStatusStr) {
		this.orderStatusStr = orderStatusStr;
	}
	public String getOrderStatusStr() {
		//平台配送
		if(getPlatform()==1){
			return  orderStatusStr;
		}
		return SourceStatusClientEnum.getNameByCode(getOrderStatus()==null?null:getOrderStatus().byteValue());
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
