package cn.gdeng.nst.enums;

public enum SourceStatusEnum2 {
	/**已发布	*/
	RELEASED((byte)1, "已发布"),
	/**待确认	*/
	STAY_FOR_CONFIRM((byte)2, "已发布"),
	/**已接受	*/
	ACCEPTED((byte)3, "已接单"),
	/**已过期	*/
	EXPIRE((byte)4, "已过期"),
	/**已关闭 */
	CLOSED((byte)5, "已关闭");

	private Byte code;
	
	private String name;

	private SourceStatusEnum2(Byte code, String name) {
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
		if(code == null) {
			return null;
		}
		SourceStatusEnum2[] values = SourceStatusEnum2.values();
		for(SourceStatusEnum2 val : values){
			if(val.getCode().equals(code)){
				return val.getName();
			}
		}
		return null;
	}
}
