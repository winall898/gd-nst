package cn.gdeng.nst.api.vo.member;

import java.io.Serializable;

import cn.gdeng.nst.enums.CarTypeEnum;
/**
 * 车辆详情VO
 * @author KWANG
 *
 */
public class MemberCarDetailVo implements Serializable{
	private static final long serialVersionUID = -1L;
	//ID
	private Integer id;
    //车牌号
	private String carNumber;
    //车量类型
	private Byte carType;
	//车量类型
	private String carTypeStr;
    //载重
	private Double load;
    //车长
	private Double carLength;
	 //车身图
	private String vehicleUrl;
    //车头图
	private String carHeadUrl;
	 //车尾图
	private String carRearUrl;
	//是否承运车辆
	private Byte isCarriage;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public Byte getCarType() {
		return carType;
	}

	public void setCarType(Byte carType) {
		this.carType = carType;
	}

	public Double getLoad() {
		return load;
	}

	public void setLoad(Double load) {
		this.load = load;
	}

	public Double getCarLength() {
		return carLength;
	}

	public void setCarLength(Double carLength) {
		this.carLength = carLength;
	}

	public String getVehicleUrl() {
		return vehicleUrl;
	}

	public void setVehicleUrl(String vehicleUrl) {
		this.vehicleUrl = vehicleUrl;
	}

	public String getCarHeadUrl() {
		return carHeadUrl;
	}

	public void setCarHeadUrl(String carHeadUrl) {
		this.carHeadUrl = carHeadUrl;
	}

	public String getCarRearUrl() {
		return carRearUrl;
	}

	public void setCarRearUrl(String carRearUrl) {
		this.carRearUrl = carRearUrl;
	}
	
	public String getCarTypeStr() {
		return CarTypeEnum.getNameByCode(getCarType());
	}

	public void setCarTypeStr(String carTypeStr) {
		this.carTypeStr = carTypeStr;
	}

	public Byte getIsCarriage() {
		return isCarriage;
	}

	public void setIsCarriage(Byte isCarriage) {
		this.isCarriage = isCarriage;
	}
	
}
