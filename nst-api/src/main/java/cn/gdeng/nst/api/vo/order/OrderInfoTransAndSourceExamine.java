package cn.gdeng.nst.api.vo.order;

import java.util.Date;
import java.util.List;

import cn.gdeng.nst.api.dto.order.OrderInfoTransDTO;
import cn.gdeng.nst.api.dto.order.SourceExamineDTO;
import cn.gdeng.nst.enums.CarTypeEnum;

/**
 * 运单-物流信息 
 * @author kwang
 *
 * datetime:2016年12月5日 下午12:14:45
 */
public class OrderInfoTransAndSourceExamine  implements java.io.Serializable{
	
	private static final long serialVersionUID = -4631878591307283262L;
	private List<OrderInfoTransDTO>  orderInfoTransList;
	private SourceExamineDTO  sourceExamineDTO;
	private String orderNo;
	//货物重量
	private String  totalWeight;
	//车量载重
	private String  load;
	//车辆类型  
	private Byte  carType;
	//车辆类型名称
	private String  carTypeVar;
	//货主名
	private String shipperName;
	//货主手机
	private String shipperMobile;
	//目的地 详情
	private String e_detail;
	//目的地
	private String e_detailed_address;
	//头像
	private String iconUrl;
	//司机名
	private String driverName;
	//司机手机
	private String driverMobile;
	//物流公司手机
	private String companMobile;
	//物流公司名
	private String companyName;
	//车牌号
	private String carNumber;
	
	
	public Byte getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Byte orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getConfirmOrderTime() {
		return confirmOrderTime;
	}
	public void setConfirmOrderTime(Date confirmOrderTime) {
		this.confirmOrderTime = confirmOrderTime;
	}
	public Date getConfirmGoodsTime() {
		return confirmGoodsTime;
	}
	public void setConfirmGoodsTime(Date confirmGoodsTime) {
		this.confirmGoodsTime = confirmGoodsTime;
	}
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	private Byte orderStatus;
	private Date confirmOrderTime;
    private Date confirmGoodsTime;
    /**
     * orderStatus为3时为关闭时间
     */
    private Date closeTime;
	
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public List<OrderInfoTransDTO> getOrderInfoTransList() {
		return orderInfoTransList;
	}
	public void setOrderInfoTransList(List<OrderInfoTransDTO> orderInfoTransList) {
		this.orderInfoTransList = orderInfoTransList;
	}
	public SourceExamineDTO getSourceExamineDTO() {
		return sourceExamineDTO;
	}
	public void setSourceExamineDTO(SourceExamineDTO sourceExamineDTO) {
		this.sourceExamineDTO = sourceExamineDTO;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	public String getE_detail() {
		if(e_detail != null){
			String[] detailArray = e_detail.split("/");
			StringBuilder sb = new StringBuilder();
			for(String val : detailArray){
				sb.append(val).append(" ");
			}
			return sb.toString();
		}
		return "";
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
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getLoad() {
		return load;
	}
	public void setLoad(String load) {
		this.load = load;
	}
	public String getDriverMobile() {
		return driverMobile;
	}
	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}
	public String getCompanMobile() {
		return companMobile;
	}
	public void setCompanMobile(String companMobile) {
		this.companMobile = companMobile;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}
	public Byte getCarType() {
		return carType;
	}
	public void setCarType(Byte carType) {
		this.carType = carType;
	}
	public String getCarTypeVar() {
		return CarTypeEnum.getNameByCode(getCarType());
	}
	public void setCarTypeVar(String carTypeVar) {
		this.carTypeVar = carTypeVar;
	}
	
	
}
