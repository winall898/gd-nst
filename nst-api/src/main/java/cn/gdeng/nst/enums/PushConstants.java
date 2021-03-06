package cn.gdeng.nst.enums;
/**
 * 消息推送常量
 * @author zhangnf
 *
 触发客户端	触发条件	接收客户端	通知内容	着陆页
司机	点击接单	货主	有司机接单，请您查看订单	订单页面
司机	点击接单	物流公司	有司机接单，请您查看订单	订单页面
货主	点击接受	司机	货主已接受您的运单	订单页面
货主	点击确认收货	司机	货主已确认收货	订单页面
货主	支付成功	司机	货主已支付运费，请查收	订单页面
司机	支付成功	物流公司	司机已支付信息费，请查收	订单页面
物流公司	 点击 接受分配的货	货主	物流公司已承接待指派司机	订单页面
物流公司	点击分配的货接受司机的接受 	货主	您的货物将有司机接单，请您查看订单	订单页面
货主、司机、物流公司	运营后台 提交审核不通过原因	货主、司机、物流公司	您的证件审核不通过，请您修改	身份认证
货主、司机、物流公司	运营后台 审核通过	货主、司机、物流公司	恭喜您已通过农速通审核	首页
接单通知提示后,	不点击通知入口，直接进入原页面操作通知的提示		红点显示	
 */
public class PushConstants {

	/** 消息类型，用于跳转 0 订单详情页面 1身份认证详情查看页面  2 首页 **/
	public static final String MSG_TYPE = "direct";
	
	/** 消息类型，用于跳转 0 订单详情页面 1身份认证详情查看页面  2 首页 **/
	public static final Integer MSG_TYPE_0 = 0;
	
	/** 消息类型，用于跳转 0 订单详情页面 1身份认证详情查看页面  2 首页 **/
	public static final Integer MSG_TYPE_1 = 1;
	
	/** 消息类型，用于跳转 0 订单详情页面 1身份认证详情查看页面  2 首页 **/
	public static final Integer MSG_TYPE_2 = 2;
	
	/** 消息类型，用于跳转 3表示货主 我发的货 待确认 详情 **/
	public static final Integer MSG_TYPE_3 = 3;
	
	/** 消息类型，用于跳转 4表示分配,物流公司或者车主查看货源详情 **/
	public static final Integer MSG_TYPE_4 = 4;
	
	/** 消息类型，用于跳转 5表示物流公司 订单管理-已确认页签 **/
	public static final Integer MSG_TYPE_5 = 5;
	
	
	/** 消息类型，用于跳转 7表示物流公司  订单管理-已完成页签**/
	public static final Integer MSG_TYPE_7 = 7;
	
	/** 消息类型，用于跳转 8表示物流公司  订单管理-已关闭页签**/
	public static final Integer MSG_TYPE_8 = 8;
	
	/** 消息类型，用于跳转 9表示车主  车辆管理列表**/
	public static final Integer MSG_TYPE_9 = 9;
	
	/**业务主键ID 用于跳转查询**/
	public static final String BIZ_ID = "bizId";
	/**业务多参数 用于跳转查询**/
	public static final String BIZ_MAP = "bizMap";
	
	/** 设备类型 0 android 1 ios **/
	public static final int DEVICE_TYPE_0 = 0;
	/** 设备类型 0 android 1 ios **/
	public static final int DEVICE_TYPE_1 = 1;
	
	/**false测试、true生产模式**/
	public static final String PRODUCTION_MODE = "production_mode";
	/** 用户类型：1 货主 2 车主 3 物流公司,对应Key Secret**/
	public static final String APP_TYPE_ANDROID_KEY = "android_key_";
	public static final String APP_TYPE_ANDROID_SECRET = "android_secret_";
	public static final String APP_TYPE_IOS_KEY = "ios_key_";
	public static final String APP_TYPE_IOS_SECRET = "ios_secret_";
	
}
