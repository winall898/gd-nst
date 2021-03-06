package cn.gdeng.nst.enums;

/**运单-物流信息 物流状态枚举
 * 
 * @author wjguo
 *
 * datetime:2016年12月5日 上午11:42:34
 */
public enum OrderInfoTransStatusEnum {

	WAIT_EXAMINED_GOODS((byte)1, "待验货"),
	SENT((byte)2, "已发货"),
	GOOD_ARRIVED((byte)3, "车主已送达"),
	EXAMINED_NOT_PASS((byte)4, "验货不通过"),
	REJECTED_TAK_GOODS((byte)5, "拒绝收货");
	
	private Byte code;
	
	private String name;

	private OrderInfoTransStatusEnum(Byte code, String name) {
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
	
	/**根据code码获取name属性
	 * @param code
	 * @return 如果找不到对应的枚举，返回null
	 */
	public static String getNameByCode(Byte code){
		OrderInfoTransStatusEnum en = getByCode(code);
		return en != null ? en.getName() : null;
	}
	
	/**根据code码获取枚举
	 * @param code
	 * @return 如果找不到对应的枚举，返回null
	 */
	public static OrderInfoTransStatusEnum getByCode(Byte code){
		OrderInfoTransStatusEnum[] values = OrderInfoTransStatusEnum.values();
		for(OrderInfoTransStatusEnum val : values){
			if(val.getCode().equals(code)){
				return val;
			}
		}
		return null;
	}
}
