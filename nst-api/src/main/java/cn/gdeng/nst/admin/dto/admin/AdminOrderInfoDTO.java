package cn.gdeng.nst.admin.dto.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.gdeng.nst.entity.nst.OrderInfoEntity;
import cn.gdeng.nst.entity.nst.SourceExamineEntity;
import cn.gdeng.nst.enums.CarTypeEnum;
import cn.gdeng.nst.enums.GoodsTypeEnum;
import cn.gdeng.nst.enums.OrderInfoStatusEnum;
import cn.gdeng.nst.enums.OrderInfoTypeEnum;
import cn.gdeng.nst.enums.PayStatusEnum;
import cn.gdeng.nst.enums.SendGoodsTypeEnum;

public class AdminOrderInfoDTO extends OrderInfoEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5296537635747641707L;

	/**货主姓名（发布人姓名）*/
	private String shipperName;
	
	/**货主手机（发布人手机）*/
	private String shipperMobile;
	
	/**车主姓名*/
	private String driverName;
	
	/**车主手机*/
	private String driverMobile;
	
	private String logisticName;
	
	private String logisticMobile;
	
	/**货运订单类型:1 干线 2 同城*/
	private Integer sourceType;
	
	private String sDetail;
	
	private String eDetail;
	
	private String sDetailedAddress;
	
	private String eDetailedAddress;
	
	private Date sendDate;
	
	private Byte carType;
	
	private Double carLength;
	
	private Byte sendGoodsType;
	
	/**意向运费*/
	private Double freight;
	
	private String remark;
	
	private Byte goodsType;
	
	/**货物类型：101 蔬菜水果,102 干调粮油,103 活鲜水产,104 食品饮料,105 冷冻商品,106 化肥种子,107 农资农具,108 日用品,109 建材设备,110 其他商品*/
	private String goodsName;
	
	private Double totalWeight;
	
	private Integer totalSize;
	
	/**支付信息*/
	private AdminOrderPayDetailDTO orderPayDetail;
	
	private Double payMoney;
	
	/**车主接单时间*/
	private Date acceptTime;
	
	/**验货信息*/
	private SourceExamineEntity sourceExamineEntity;

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

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverMobile() {
		return driverMobile;
	}

	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}

	public String getLogisticName() {
		return logisticName;
	}

	public void setLogisticName(String logisticName) {
		this.logisticName = logisticName;
	}

	public String getLogisticMobile() {
		return logisticMobile;
	}

	public void setLogisticMobile(String logisticMobile) {
		this.logisticMobile = logisticMobile;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getSDetail() {
		return sDetail;
	}

	public void setSDetail(String sDetail) {
		this.sDetail = sDetail;
	}

	public String getEDetail() {
		return eDetail;
	}

	public void setEDetail(String eDetail) {
		this.eDetail = eDetail;
	}

	public String getSDetailedAddress() {
		return sDetailedAddress;
	}

	public void setSDetailedAddress(String sDetailedAddress) {
		this.sDetailedAddress = sDetailedAddress;
	}

	public String getEDetailedAddress() {
		return eDetailedAddress;
	}

	public void setEDetailedAddress(String eDetailedAddress) {
		this.eDetailedAddress = eDetailedAddress;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Byte getCarType() {
		return carType;
	}

	public void setCarType(Byte carType) {
		this.carType = carType;
	}

	public Double getCarLength() {
		return carLength;
	}

	public void setCarLength(Double carLength) {
		this.carLength = carLength;
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

	public Byte getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Byte goodsType) {
		this.goodsType = goodsType;
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

	public String getSourceTypeStr(){
		return OrderInfoTypeEnum.getNameByCode(getSourceType());
	}

	public String getCarTypeStr(){
		return CarTypeEnum.getNameByCode(getCarType());
	}
	
	public String getSendGoodsTypeStr(){
		return SendGoodsTypeEnum.getNameByCode(getSendGoodsType());
	}
	
	public String getGoodsTypeStr(){
		return GoodsTypeEnum.getNameByCode(getGoodsType());
	}
	
	public String getOrderStatusStr(){
		return OrderInfoStatusEnum.getNameByCode(getOrderStatus());
	}
	
	public String getSDetailStr(){
		String sDetail = getSDetail();
		if(sDetail != null){
			String[] detailArray = sDetail.split("/");
			StringBuilder sb = new StringBuilder();
			for(String val : detailArray){
				sb.append(val).append(" ");
			}
			return sb.toString();
		}
		return null;
	}
	
	public String getEDetailStr(){
		String eDetail = getEDetail();
		if(eDetail != null){
			String[] detailArray = eDetail.split("/");
			StringBuilder sb = new StringBuilder();
			for(String val : detailArray){
				sb.append(val).append(" ");
			}
			return sb.toString();
		}
		return null;
	}
	
	public String getCreateTimeStr(){
		if(getCreateTime() != null){
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sf.format(getCreateTime());
		}
		return "";
	}
	public String getPayStatusStr(){
		return PayStatusEnum.getNameByCode(getPayStatus());
	}
	public AdminOrderPayDetailDTO getOrderPayDetail() {
		return orderPayDetail;
	}

	public void setOrderPayDetail(AdminOrderPayDetailDTO orderPayDetail) {
		this.orderPayDetail = orderPayDetail;
	}

	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public SourceExamineEntity getSourceExamineEntity() {
		return sourceExamineEntity;
	}

	public void setSourceExamineEntity(SourceExamineEntity sourceExamineEntity) {
		this.sourceExamineEntity = sourceExamineEntity;
	}
}
