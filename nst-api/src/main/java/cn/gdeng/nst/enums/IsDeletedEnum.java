package cn.gdeng.nst.enums;

public enum IsDeletedEnum {

	NOT_DELETED((byte)0, "使用中"),
	DELETED((byte)1, "已删除");
	
	private Byte code;
	
	private String name;

	private IsDeletedEnum(Byte code, String name) {
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
		IsDeletedEnum[] values = IsDeletedEnum.values();
		for(IsDeletedEnum val : values){
			if(val.getCode().equals(code)){
				return val.getName();
			}
		}
		return null;
	}
}
