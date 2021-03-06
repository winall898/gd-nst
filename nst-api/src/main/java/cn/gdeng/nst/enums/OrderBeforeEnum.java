package cn.gdeng.nst.enums;

public enum OrderBeforeEnum {
	/**司机已接单*/
	RECEIVEORDER((byte)1, "接单"),
	/**已生成订单（物流公司接受）*/
	GENERATEORDER((byte)2, "接受"),
	/**已完成*/
	COMPLETEORDER((byte)3, "接受"),
	/**已关闭(货主或者信息部拒绝 )*/
	CLOSEORDER((byte)4, "拒绝"),
	/**已超时*/
	TIMEOUT((byte)5, "超时"),
	/**司机已取消*/
	CANCEL((byte)6, "取消"),
	/**已关闭(平台配送)*/
	CLOSED((byte)7, "已关闭");
	
	private Byte code;
	
	private String name;

	private OrderBeforeEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}

	public Byte getCode() {
		return code;
	}

	public void setCode(Byte code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static String getNameByCode(Byte code){
		OrderBeforeEnum[] values = OrderBeforeEnum.values();
		for(OrderBeforeEnum val : values){
			if(val.getCode().equals(code)){
				return val.getName();
			}
		}
		return null;
	}
	
}
