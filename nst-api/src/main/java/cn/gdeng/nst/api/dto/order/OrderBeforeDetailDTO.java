package cn.gdeng.nst.api.dto.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.gdeng.nst.enums.CarTypeEnum;
import cn.gdeng.nst.enums.GoodsTypeEnum;
import cn.gdeng.nst.enums.MemberCerStatusEnum;
import cn.gdeng.nst.enums.NstRuleEnum;
import cn.gdeng.nst.enums.OrderAgentStatusEnum;
import cn.gdeng.nst.enums.OrderBeforeEnum;
import cn.gdeng.nst.enums.OrderInfoOrderStatusEnum;
import cn.gdeng.nst.enums.OrderInfoTransCloseReasonEnum;
import cn.gdeng.nst.enums.PayStatusEnum;
import cn.gdeng.nst.enums.SendGoodsTypeEnum;
import cn.gdeng.nst.enums.SourceExamineStatusEnum;
import cn.gdeng.nst.enums.SourceStatusEnum;
import cn.gdeng.nst.enums.TransStatusEnum;
import cn.gdeng.nst.util.web.api.ParamProcessUtil;

/**
 * @author DJB
 * @version 创建时间：2016年12月6日 下午12:16:49 订单管理--订单详情 专用 请勿套用
 */

public class OrderBeforeDetailDTO implements Serializable {

	/**
	 * cn.gdeng.nst.api.dto.source
	 */
	private static final long serialVersionUID = 7111202077111644623L;
	// 当前货主名片--显示名片

	/** 当前货主姓名 */
	private String iconUrl;
	/** 当前货主姓名 */
	private String userName;
	/** 当前货主ID */
	private Integer memberId;
	/** 当前货主手机 */
	private String mobile;
	/** 当前货主个人认证信息 */
	private Byte cerPersonalStatus;

	// 原始货主信息
	/*** 货主id */
	private Integer oriShipperMemberId;
	/*** 货主名称 */
	private String oriShipperName;
	/*** 货主电话 */
	private String oriShipperMobile;
	/** 货主状态 */
	private Byte oriShipperCerStatus;
	/** 货主头像 */
	private String oriShipperIconUrl;

	// 商家信息
	/*** 商家会员id */
	private Integer merchantMemberId;
	/*** 商家名称 */
	private String merchantName;
	/*** 商家电话 */
	private String merchantMobile;
	/** 商铺名称 */
	private String merchantTitle;
	/** 商铺地址 */
	private String merchantAddress;
	/** 商铺 商标 */
	private String merchantLogoUrl;

	/** 货源ID */
	private Integer sourceId;
	/** 出发地 */
	private String s_detail;
	/** 发货地详细地址 */
	private String s_detailed_address;
	/** 目的地 */
	private String e_detail;
	/** 收货地详细地址 */
	private String e_detailed_address;
	/** 1 干线 2 同城 */
	private Byte sourceType;
	/** 里程 */
	private BigDecimal mileage;
	/** 装车时间 */
	private String sendDate;
	/** 车长 */
	private BigDecimal carLength;
	/** 车辆类型 */
	private Byte carType;
	/** 货物类型 */
	private Byte goodsType;
	/** 发货方式 */
	private Byte sendGoodsType;
	/** 货物名称 */
	private String goodsName;
	/** 总重量 */
	private BigDecimal totalWeight;
	/** 总体积 */
	private BigDecimal totalSize;
	/** 货主留言 */
	private String remark;
	/** 意向运费 */
	private BigDecimal freight;

	/*** 货源状态 */
	private Byte sourceStatus;
	/*** 当前货主最原始名称 */
	private String shipperName;
	/*** 当前货主最原始手机号 */
	private String shipperMobile;

	/*** 货源关闭原因 */
	private Byte closeReason;

	private Date createTime;

	private Byte nstRule;

	/** 0 非平台配送 1 平台配送 **/
	private Byte platform;

	/**
	 * 出发地的全地址，包括省市区和详细地
	 */
	private String srcFullAddr;

	/**
	 * 目的地的全地址，包括省市区和详细地
	 */
	private String desFullAddr;

	private Byte orderBeforeStatus; // 预运单状态

	/*** 预运单ID **/
	private Integer orderBeforeId;

	private Integer carId;// 车辆id

	/** 接单司机车牌 */
	private String driverCarNumber;
	/** 接单司机车类型 */
	private Byte driverCarType;

	/** 接单司机车载重 */
	private BigDecimal driverCarLoad;
	/** 接单司机车长 */
	private BigDecimal driverCarLength;
	/** 司机真实姓名 */
	private String driverRealName;
	/** 司机认证状态 */
	private Byte driverCerStatus;

	/** 司机电话 */
	private String driverMobile;
	/** 司机头像 */
	private String driverIconUrl;

	/** 物流公司名称 */
	private String companyName;
	/** 信息订单号 */
	private String orderAgentNo;
	/** 信息订单状态 */
	private Byte orderAgentStatus;

	/** 信息生成时间 */
	private Date agentConfirmTime;

	/** 信息-支付金额 */
	private BigDecimal orderAgentPayMoney;
	/** 信息-付款状态 */
	private Byte orderAgentPayStatus;

	/** 信息-支付时间 */
	private Date orderAgentPayTime;
	/** 信息-平台支付流水 */
	private String orderAgentPlatformPayWater;
	/** 信息-付款方 */
	private String orderAgentPayName;

	/** 信息订单id */
	private Integer orderAgentId;

	/** 货运订单号 */
	private String orderInfoNo;
	/** 订单状态 */
	private Byte orderInfoStatus;

	/** 预付款支付状态(6+1) 0 未支付 1 已支付 **/
	private Byte prePayStatus;

	/** 货运确认订单时间 */
	private Date confirmGoodsInfoTime;

	/** 货运-信息费 */
	private BigDecimal orderInfoPayMoney;
	/** 货运-付款状态 */
	private Byte orderInfoPayStatus;

	/** 货运-支付时间 */
	private Date orderInfoPayTime;
	/** 货运-平台支付流水 */
	private String orderInfoPlatformPayWater;
	/** 货运-付款方 */
	private String orderInfoPayName;

	/** ----- 信息费相关字段---- */
	/** 收款方用户ID */
	private Integer payeeUserId;
	/** 收款方账号 */
	private String payeeMobile;
	/** 收款方姓名 */
	private String payeeName;
	/** 信息确认订单时间 */
	private Date agentLogisticTime;
	/** 货运生成时间 */
	private Date confirmOrderInfoTime;

	/** 物流状态 */
	private Byte transStatus;

	/** 验货状态 */
	private Byte inspectionStatus;

	/** 物流运费 */
	private String logisticsFreight;

	/*** 操作状态 **/
	private Byte operationStatus;
	/**** 操作状态中文解释 ****/
	private String operationStr;
	/**** 操作状态提示语 ****/
	private String markedWords;

	public Byte getOperationStatus() {
		return operationStatus;
	}

	public String getOperationStr() {
		return operationStr;
	}

	public String getMarkedWords() {
		return markedWords;
	}

	public Byte getPrePayStatus() {
		return prePayStatus;
	}

	public void setPrePayStatus(Byte prePayStatus) {
		this.prePayStatus = prePayStatus;
	}

	public String getLogisticsFreight() {
		if ( logisticsFreight == null) {
			return "面议";
		}
		return logisticsFreight;
	}

	public void setLogisticsFreight(String logisticsFreight) {
		this.logisticsFreight = logisticsFreight;
	}

	public String getUserName() {
		return (String) ParamProcessUtil.resultFieldByNstRole(this.userName, getOriShipperName(), getShipperName(), getNstRule());
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getMemberId() {
		return (Integer) ParamProcessUtil.resultFieldByNstRole(this.memberId, getOriShipperMemberId(), null, getNstRule());

	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getMobile() {
		return (String) ParamProcessUtil.resultFieldByNstRole(this.mobile, getOriShipperMobile(), getShipperMobile(), getNstRule());
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Byte getCerPersonalStatus() {
		return (Byte) ParamProcessUtil.resultFieldByNstRole(this.cerPersonalStatus, getOriShipperCerStatus(), null, getNstRule());
	}

	public void setCerPersonalStatus(Byte cerPersonalStatus) {
		this.cerPersonalStatus = cerPersonalStatus;
	}

	public String getCerPersonal() {
		return MemberCerStatusEnum.getNameByCode(getCerPersonalStatus());
	}

	public String getIconUrl() {
		return (String) ParamProcessUtil.resultFieldByNstRole(this.iconUrl, getOriShipperIconUrl(), null, getNstRule());
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
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

	public Integer getMerchantMemberId() {
		return merchantMemberId;
	}

	public void setMerchantMemberId(Integer merchantMemberId) {
		this.merchantMemberId = merchantMemberId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantMobile() {
		return merchantMobile;
	}

	public void setMerchantMobile(String merchantMobile) {
		this.merchantMobile = merchantMobile;
	}

	public String getMerchantTitle() {
		return merchantTitle;
	}

	public void setMerchantTitle(String merchantTitle) {
		this.merchantTitle = merchantTitle;
	}

	public String getMerchantAddress() {
		return merchantAddress;
	}

	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}

	public String getMerchantLogoUrl() {
		return merchantLogoUrl;
	}

	public void setMerchantLogoUrl(String merchantLogoUrl) {
		this.merchantLogoUrl = merchantLogoUrl;
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
		if ( StringUtils.isNotBlank(s_detail)) {
			this.s_detail = ParamProcessUtil.ridProvinceAndSpliceAddr(s_detail, "/");

		} else {
			this.s_detail = s_detail;
		}

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
		if ( StringUtils.isNotBlank(e_detail)) {
			this.e_detail = ParamProcessUtil.ridProvinceAndSpliceAddr(e_detail, "/");

		} else {
			this.e_detail = e_detail;
		}

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

	public String getSendDate() {
		return ParamProcessUtil.stringDatePattern(sendDate, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm");
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public Byte getSendGoodsType() {
		return sendGoodsType;
	}

	public void setSendGoodsType(Byte sendGoodsType) {
		this.sendGoodsType = sendGoodsType;
	}

	public String getSendGoodsTypeStr() {
		return SendGoodsTypeEnum.getNameByCode(getSendGoodsType());
	}

	public String getGoodsName() {
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

	public Byte getNstRule() {
		return nstRule;
	}

	public void setNstRule(Byte nstRule) {
		this.nstRule = nstRule;
	}

	public Byte getPlatform() {
		return platform;
	}

	public void setPlatform(Byte platform) {
		this.platform = platform;
	}

	public String getNstRuleStr() {
		return NstRuleEnum.getNameByCode(getNstRule());
	}

	public BigDecimal getCarLength() {
		return carLength;
	}

	public void setCarLength(BigDecimal carLength) {
		this.carLength = carLength;
	}

	public Byte getCarType() {
		return carType;
	}

	public void setCarType(Byte carType) {
		this.carType = carType;
	}

	public String getCarTypeStr() {
		return CarTypeEnum.getNameByCode(getCarType());
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public Byte getSourceStatus() {
		return sourceStatus;
	}

	public void setSourceStatus(Byte sourceStatus) {
		this.sourceStatus = sourceStatus;
	}

	public String getSourceStatusStr() {
		return SourceStatusEnum.getNameByCode(getSourceStatus());
	}

	public Integer getOriShipperMemberId() {
		return oriShipperMemberId;
	}

	public void setOriShipperMemberId(Integer oriShipperMemberId) {
		this.oriShipperMemberId = oriShipperMemberId;
	}

	public String getOriShipperName() {
		return oriShipperName;
	}

	public void setOriShipperName(String oriShipperName) {
		this.oriShipperName = oriShipperName;
	}

	public String getOriShipperMobile() {
		return oriShipperMobile;
	}

	public void setOriShipperMobile(String oriShipperMobile) {
		this.oriShipperMobile = oriShipperMobile;
	}

	public Byte getOriShipperCerStatus() {
		return oriShipperCerStatus;
	}

	public void setOriShipperCerStatus(Byte oriShipperCerStatus) {
		this.oriShipperCerStatus = oriShipperCerStatus;
	}

	public String getOriShipperIconUrl() {
		return oriShipperIconUrl;
	}

	public void setOriShipperIconUrl(String oriShipperIconUrl) {
		this.oriShipperIconUrl = oriShipperIconUrl;
	}

	public Byte getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Byte goodsType) {
		this.goodsType = goodsType;
	}

	public String getGoodsTypeStr() {
		return GoodsTypeEnum.getNameByCode(getGoodsType());
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

	public Integer getOrderBeforeId() {
		return orderBeforeId;
	}

	public void setOrderBeforeId(Integer orderBeforeId) {
		this.orderBeforeId = orderBeforeId;
	}

	public String getDriverCarNumber() {
		return driverCarNumber;
	}

	public void setDriverCarNumber(String driverCarNumber) {
		this.driverCarNumber = driverCarNumber;
	}

	public Byte getDriverCarType() {
		return driverCarType;
	}

	public void setDriverCarType(Byte driverCarType) {
		this.driverCarType = driverCarType;
	}

	public String getDriverCarTypeStr() {
		return CarTypeEnum.getNameByCode(getDriverCarType());
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

	public Byte getOrderAgentStatus() {
		return orderAgentStatus;
	}

	public void setOrderAgentStatus(Byte orderAgentStatus) {
		this.orderAgentStatus = orderAgentStatus;
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

	public String getOrderInfoNo() {
		return orderInfoNo;
	}

	public void setOrderInfoNo(String orderInfoNo) {
		this.orderInfoNo = orderInfoNo;
	}

	public Byte getOrderInfoStatus() {
		return orderInfoStatus;
	}

	public void setOrderInfoStatus(Byte orderInfoStatus) {
		this.orderInfoStatus = orderInfoStatus;
	}

	public Date getConfirmOrderInfoTime() {
		return confirmOrderInfoTime;
	}

	public void setConfirmOrderInfoTime(Date confirmOrderInfoTime) {
		this.confirmOrderInfoTime = confirmOrderInfoTime;
	}

	public Date getConfirmGoodsInfoTime() {
		return confirmGoodsInfoTime;
	}

	public void setConfirmGoodsInfoTime(Date confirmGoodsInfoTime) {
		this.confirmGoodsInfoTime = confirmGoodsInfoTime;
	}

	public BigDecimal getOrderAgentPayMoney() {
		return orderAgentPayMoney;
	}

	public void setOrderAgentPayMoney(BigDecimal orderAgentPayMoney) {
		this.orderAgentPayMoney = orderAgentPayMoney;
	}

	public Byte getOrderAgentPayStatus() {
		return orderAgentPayStatus;
	}

	public void setOrderAgentPayStatus(Byte orderAgentPayStatus) {
		this.orderAgentPayStatus = orderAgentPayStatus;
	}

	public Date getOrderAgentPayTime() {
		return orderAgentPayTime;
	}

	public void setOrderAgentPayTime(Date orderAgentPayTime) {
		this.orderAgentPayTime = orderAgentPayTime;
	}

	public String getOrderAgentPlatformPayWater() {
		return orderAgentPlatformPayWater;
	}

	public void setOrderAgentPlatformPayWater(String orderAgentPlatformPayWater) {
		this.orderAgentPlatformPayWater = orderAgentPlatformPayWater;
	}

	public String getOrderAgentPayName() {
		return orderAgentPayName;
	}

	public void setOrderAgentPayName(String orderAgentPayName) {
		this.orderAgentPayName = orderAgentPayName;
	}

	public BigDecimal getOrderInfoPayMoney() {
		return orderInfoPayMoney;
	}

	public void setOrderInfoPayMoney(BigDecimal orderInfoPayMoney) {
		this.orderInfoPayMoney = orderInfoPayMoney;
	}

	public Byte getOrderInfoPayStatus() {
		return orderInfoPayStatus;
	}

	public void setOrderInfoPayStatus(Byte orderInfoPayStatus) {
		this.orderInfoPayStatus = orderInfoPayStatus;
	}

	public Date getOrderInfoPayTime() {
		return orderInfoPayTime;
	}

	public void setOrderInfoPayTime(Date orderInfoPayTime) {
		this.orderInfoPayTime = orderInfoPayTime;
	}

	public String getOrderInfoPlatformPayWater() {
		return orderInfoPlatformPayWater;
	}

	public void setOrderInfoPlatformPayWater(String orderInfoPlatformPayWater) {
		this.orderInfoPlatformPayWater = orderInfoPlatformPayWater;
	}

	public String getOrderInfoPayName() {
		return orderInfoPayName;
	}

	public void setOrderInfoPayName(String orderInfoPayName) {
		this.orderInfoPayName = orderInfoPayName;
	}

	public String getDriverRealName() {
		return driverRealName;
	}

	public void setDriverRealName(String driverRealName) {
		this.driverRealName = driverRealName;
	}

	public Byte getDriverCerStatus() {
		return driverCerStatus;
	}

	public void setDriverCerStatus(Byte driverCerStatus) {
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

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public Byte getOrderBeforeStatus() {
		return orderBeforeStatus;
	}

	public void setOrderBeforeStatus(Byte orderBeforeStatus) {
		this.orderBeforeStatus = orderBeforeStatus;
	}

	public String getOrderBeforeStatusStr() {
		return OrderBeforeEnum.getNameByCode(getOrderBeforeStatus());
	}

	public String getDriverCerStatusStr() {
		return MemberCerStatusEnum.getNameByCode(getDriverCerStatus());
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrderAgentStatusStr() {
		return OrderAgentStatusEnum.getNameByCode(getOrderAgentStatus());
	}

	public String getOrderAgentPayStatusStr() {
		if ( getOrderAgentPayStatus() == null) {
			return null;
		}
		return PayStatusEnum.getNameByCode((short) getOrderAgentPayStatus().intValue());
	}

	public String getOrderInfoStatusStr() {
		return OrderInfoOrderStatusEnum.getNameByCode(getOrderInfoStatus());
	}

	public String getOrderInfoPayStatusStr() {
		if ( getOrderInfoPayStatus() == null) {
			return null;
		}
		return PayStatusEnum.getNameByCode((short) getOrderInfoPayStatus().intValue());
	}

	public Integer getPayeeUserId() {
		return payeeUserId;
	}

	public void setPayeeUserId(Integer payeeUserId) {
		this.payeeUserId = payeeUserId;
	}

	public String getPayeeMobile() {
		return payeeMobile;
	}

	public void setPayeeMobile(String payeeMobile) {
		this.payeeMobile = payeeMobile;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public Integer getOrderAgentId() {
		return orderAgentId;
	}

	public void setOrderAgentId(Integer orderAgentId) {
		this.orderAgentId = orderAgentId;
	}

	public Byte getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(Byte transStatus) {
		this.transStatus = transStatus;
	}

	public String getTransStatusStr() {
		return TransStatusEnum.getNameByCode(getTransStatus());
	}

	public Byte getInspectionStatus() {
		return inspectionStatus;
	}

	public void setInspectionStatus(Byte inspectionStatus) {
		this.inspectionStatus = inspectionStatus;
	}

	/** 验货状态中文解释 */
	public String getInspectionStatusStr() {
		return SourceExamineStatusEnum.getNameByCode(getInspectionStatus());
	}

	public Byte getSourceType() {
		return sourceType;
	}

	public void setSourceType(Byte sourceType) {
		this.sourceType = sourceType;
	}

	public Byte getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(Byte closeReason) {
		this.closeReason = closeReason;
	}

	public String getCloseReasonStr() {
		return OrderInfoTransCloseReasonEnum.getNameByCode(getCloseReason());
	}

	// 货物来源
	public String getGoodsSource() {
		Byte nByte = getNstRule();
		if ( nByte != null) {
			if ( nByte.compareTo((byte) 1) == 0) {
				return null;
			} else if ( nByte.compareTo((byte) 5) == 0) {
				return "平台分配";
			} else {
				return "物流公司推荐";
			}
		}
		return null;
	}

	public Boolean getInitialOperation() {
		Map<String, Object> resutlMap = ParamProcessUtil.getInitialOperation(nstRule, platform, sourceStatus, orderBeforeStatus, orderAgentPayStatus, orderInfoStatus, transStatus, prePayStatus, closeReason);
		this.operationStatus = (Byte) resutlMap.get("operationStatus");
		this.operationStr = (String) resutlMap.get("operationStr");
		this.markedWords = (String) resutlMap.get("markedWords");
		return true;

	}

}