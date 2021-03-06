package cn.gdeng.nst.enums;

/**
 * 消息返回code msg定义
 * @author zhangnf
 *错误码定义成5位数
 *10000定义为成功消息
 *20000定义为失败消息
 *30000定义为登录相关消息
 *50000定义成服务器异常消息
 *
 */
public class MsgCons {
   
	public static final Integer C_10000 = 10000;
	public static final String M_10000 = "成功";
	
	
	public static final Integer C_20000 = 20000;
	public static final String M_20000 = "失败";
	
	public static final Integer C_30000 = 30000;
	public static final String M_30000 = "登录超时";
	
	public static final Integer C_50000 = 50000;
	public static final String M_50000 = "服务异常";
	
	public static final Integer C_20001=20001;
	public static final String M_20001="参数错误";
	
	public static final Integer C_20002=20002;
	public static final String M_20002="ID不能为空";
	
	public static final Integer C10000= -10000;
	public static final String M10000 = "该用户已禁用";
	
	/*******    21000-21999  会员公共定义       ******/
	public static final Integer C_21000=21000;
	public static final String M_21000="获取验证码失败";
	
	public static final Integer C_21001=21001;
	public static final String M_21001="验证码校验失败";
	
	public static final Integer C_21002=21002;
	public static final String M_21002="登录失败";
	
	public static final Integer C_21003=21003;
	public static final String M_21003="业务类型选择失败";
	
	public static final Integer C_21004=21004;
	public static final String M_21004="修改手机号码失败";
	
	public static final Integer C_21005=21005;
	public static final String M_21005="修改联系人失败";
	
	public static final Integer C_21006=21006;
	public static final String M_21006="修改会员状态失败";
	
	public static final Integer C_21007=21007;
	public static final String M_21007="重置密码失败";

	
	public static final Integer C_21008=21008;
	public static final String M_21008="请选择你的物流业务类型";
	
	public static final Integer C_21009=21009;
	public static final String M_21009="真实姓名不能为空";
	
	public static final Integer C_21010=21010;
    public static final String M_21010="身份证号码不能为空";
    
    public static final Integer C_21011=21011;
    public static final String M_21011="身份证正面照不能为空";
    
    public static final Integer C_21012=21012;
    public static final String M_21012="身份证反面照不能为空";
    
    public static final Integer C_21013=21013;
    public static final String M_21013="公司名称不能为空";
    
    public static final Integer C_21014=21014;
    public static final String M_21014="公司名称不能超过20个字";
    
    public static final Integer C_21015=21015;
    public static final String M_21015="请输入正确的身份证号";
    
    public static final Integer C_21016=21016;
    public static final String M_21016="请登录后访问";
    
    public static final Integer C_21017=21017;
    public static final String M_21017="当前用户没通过认证";
    
    
    public static final Integer C_21018=21018;
    public static final String M_21018="会员信息有误，请重新登录";
    
    
    public static final Integer C_21019=21019;
    public static final String M_21019="您还未完善联系人信息";
    

    
    public static final Integer C_21021=21021;
    public static final String M_21021="用户不存在";
    
    public static final Integer C_21022=21022;
    public static final String M_21022="认证来源不能为空";
    
    public static final Integer C_21023=21023;
    public static final String M_21023="该账户还未在平台注册";

    public static final Integer C_21024=21024;
    public static final String M_21024="该账户还未添加车辆信息，添加后才可管理";
    
    public static final Integer C_21025=21025;
    public static final String M_21025="司机有未完成的订单，暂不能删除";
    
    public static final Integer C_21026=21026;
    public static final String M_21026="您已添加过该用户";
    
    public static final Integer C_21027=21027;
    public static final String M_21027="该车辆为承运车辆，不允许删除!";
    
    public static final Integer C_21028=21028;
    public static final String M_21028="请选择承运车辆!";
    
	/*******    22000-22009  定义与出发地相关提示        ******/
	public static final  Integer C_22000=22000;
	public static final String M_22000="请选择出发地";
	
	public static final  Integer C_22001=22001;
	public static final String M_22001="请选择出发地省会";
	
	public static final  Integer C_22002=22002;
	public static final String M_22002="请选择出发地城市";
	
	public static final  Integer C_22003=22003;
	public static final String M_22003="请选择出发地区域";
	
	
	
	/*******    22010-22019  定义与目的地相关提示        ******/
	public static final  Integer C_22010=22010;
	public static final String M_22010="请选择目的地";
	
	public static final  Integer C_22011=22011;
	public static final String M_22011="请选择目的地省会";
	
	public static final  Integer C_22012=22012;
	public static final String M_22012="请选择目的地城市";
	
	public static final  Integer C_22013=22013;
	public static final String M_22013="请选择目的地区域";
	
	
	
	
	public static final Integer C_22021=22021;
	public static final String M_22021="您已添加5条线路，请删除或变更线路";

	public static final Integer C_22022=22022;
	public static final String M_22022="您已经添加过此线路";

	public static final Integer C_22023=22023;
	public static final String M_22023="您已添加10条常跑线路，请删除或变更线路";
	
	public static final Integer C_22024=22024;
	public static final String M_22024="您还没有添加车辆信息，不能发布线路";
	
	public static final Integer C_22025=22025;
	public static final String M_22025="您有发布线路,不能将车辆全部删除";
    
	
	public static final Integer C_22026=22026;
	public static final String M_22026="该车牌号已存在";

	public static final Integer C_22027=22027;
	public static final String M_22027="您已添加过相同线路";
	
	public static final Integer C_22028=22028;
	public static final String M_22028="您还没有添加车辆信息";
	
	   
    public static final Integer C_22029=22029;
	public static final String M_22029="该车辆有未完成的订单,不能删除";
	
	/****** 22100-22199 定义为物流分配相关提示******/
	public static final Integer C_22100 = 22100;
	public static final String M_22100 = "没有对应的物流规则";
	
    public static final Integer C_22101 = 22101;
	public static final String M_22101 = "没有开启收货模式";
	
	public static final Integer C_22102 = 22102;
	public static final String M_22102 = "查询货源分配历史错误！";
	
	
	/*******    23000-23999  定义与订单的相关提示        ******/
	
	/**拒绝车主接单失败*/
	public static final Integer C_23000 = 23000;
	public static final String M_23000 = "拒绝失败，请刷新后再尝试!";
	/** 车主接单失败 */
	public static final Integer C_23001= 23001;
	public static final String M_23001 = "接单失败，请刷新后再尝试!";
	
	/**信息订单号生成错误		*/
	public static final Integer C_23002= 23002;
	public static final String M_23002 = "生成信息订单号错误，请刷新后再尝试!";
	
	/**货运订单号生成错误		*/
	public static final Integer C_23003= 23003;
	public static final String M_23003 = "生成货运订单号错误，请刷新后再尝试!";
	
	/**查询信息订单error		*/
	public static final Integer C_23004= 23004;
	public static final String M_23004 = "查询信息订单错误!";
	
	/**查询预订单error		*/
	public static final Integer C_23005= 23005;
	public static final String M_23005 = "查询预订单错误!";
	
	/**接单数量超额		*/
	public static final Integer C_23006= 23006;
	public static final String M_23006 = "您接单已超过规定限额，暂时不能接单了";
	
	/**查询货源订单error		*/
	public static final Integer C_23007= 23007;
	public static final String M_23007 = "查询货源订单错误!";
	
	public static final Integer C_23008= 23008;
	public static final String M_23008 = "更新预订单状态错误!";
	
	public static final Integer C_23009= 23009;
	public static final String M_23009 = "此订单已被接受，无法再次接受";
	
	public static final Integer C_23010= 23010;
	public static final String M_23010 = "取消订单失败，请刷新后重试";
	

	
	public static final Integer C_23013= 23013;
	public static final String M_23013 = "取消订单失败，请刷新";

	
	public static final Integer C_23032= 23032;
	public static final String M_23032 = "取消订单失败，请刷新";
	public static final Integer C_23014 = 23014;
	public static final String M_23014 = "创建运单失败！";
	
	public static final Integer C_23033= 23033;
	public static final String M_23033 = "预付款的订单编号不能为空";
	
	public static final Integer C_23034= 23033;
	public static final String M_23034 = "订单编号没有对应的订单信息";
	
	public static final Integer C_23015 = 23015;
	public static final String M_23015 = "司机已删除车辆！";
	
	public static final Integer C_23016 = 23016;
	public static final String M_23016 = "您还未添加司机信息，请先添加!";

	public static final Integer C_23017= 23017;
	public static final String M_23017 = "验货不通过时，请填写验货说明";
	public static final Integer C_23018= 23018;
	public static final String M_23018 = "验货说明长度请控制在200字内";
	public static final Integer C_23019= 23019;
	public static final String M_23019 = "该订单已验货";
	
	public static final Integer C_23020= 23020;
	public static final String M_23020 = "不存在任何的预订单编号";

	public static final Integer C_23021= 23021;
	public static final String M_23021 = "未支付预付款";
	/** 车主 接单 相关操作提示  start  */
	public static final Integer C_23011= 23011;
	public static final String M_23011 = "车主已取消接单，请重新刷新数据！";
	
	public static final Integer C_23012= 23012;
	public static final String M_23012 = "订单有误，请重新刷新数据！";
	
	public static final Integer C_24017 = 24017;
	public static final String M_24017 = "接单失败";
	
	
	public static final Integer C_24018 = 24018;
	public static final String M_24018 = "货源已被他人处理，无法操作";
	
	public static final Integer C_24019 = 24019;
	public static final String M_24019 = "车辆载重不能为空";
	
	public static final Integer C_24020 = 24020;
	public static final String M_24020 = "货源不存在";
	
	public static final Integer C_24021= 24021;
	public static final String M_24021 = "货源已过期";
	
	public static final Integer C_24022 = 24022;
	public static final String M_24022 = "货主信息异常，无法接单";
	
	public static final Integer C_24023 = 24023;
	public static final String M_24023 = "货源已被接单，无法再接单";
	
	public static final Integer C_24029 = 24029;
	public static final String M_24029 = "暂时无法接单，请刷新后重试";
	
	public static final Integer C_24035= 24035;
	public static final String M_24035 = "请重新刷新数据！";
	
	public static final Integer C_24036 = 24036;
	public static final String M_24036 = "生成订单失败";
	
	public static final Integer C_24037 = 24037;
	public static final String M_24037 = "分配的货不存在";
	
	
	public static final Integer C_24038 = 24038;
	public static final String M_24038 = "确认失败，请刷新数据";
	
	public static final Integer C_24039 = 24039;
	public static final String M_24039 = "运单不存在";
	
	
	/** 车主 接单 相关操作提示  end  */
	
	/*******    24000-24999  定义与货源相关提示        ******/
	public static final Integer C_24000 = 24000;
	public static final String M_24000 = "货物类型不能为空";
	
	public static final Integer C_24001 = 24001;
	public static final String M_24001 = "货物重量超出范围";
	

	
	public static final Integer C_24002 = 24002;
	public static final String M_24002 = "货物体积超出范围";


	public static final Integer C_24003 = 24003;
	public static final String M_24003 = "意向运费超出范围";

	
	public static final Integer C_24004 = 24004;
	public static final String M_24004 = "发货方式不能为空";
	
	public static final Integer C_24005 = 24005;
	public static final String M_24005 = "货物名称不能超过50个字符";
	
	public static final Integer C_24006 = 24006;
	public static final String M_24006 = "给车主留言不能超过50个字符";
	
	public static final Integer C_24007 = 24007;
	public static final String M_24007 = "装货时间小于当前时间";
	
	public static final Integer C_24008 = 24008;
	public static final String M_24008 = "刷新失败,请重新读取数据后再尝试!";
	
	public static final Integer C_24009 = 24009;
	public static final String M_24009 = "更新装货时间失败,请刷新后再尝试!";
	
	public static final Integer C_24010 = 24010;
	public static final String M_24010 = "获取货源失败";
	
	public static final Integer C_24011 = 24011;
	public static final String M_24011 = "接受失败,请刷新后再尝试!";
	
	public static final Integer C_24012 = 24012;
	public static final String M_24012 = "拒绝失败,请刷新后再尝试!";

	public static final Integer C_24013 = 24013;
	public static final String M_24013 = "体积和重量必填一个";
	

	public static final Integer C_24014 = 24014;
	public static final String M_24014 = "更新过期货源失败,查询记录数和更新记录数不一致!";
	
	public static final Integer C_24015 = 24015;
	public static final String M_24015 = "车长不能为空";
	
	public static final Integer C_24016 = 24016;
	public static final String M_24016 = "车辆类型不能为空";
	
	
	
	
	
	public static final Integer C_24030 = 24030;
	public static final String M_24030 = "货源已被删除";
	
	public static final Integer C_24024 = 24024;
	public static final String M_24024 = "省份名称不正确";
	
	public static final Integer C_24025 = 24025;
	public static final String M_24025 = "城市名称不正确";
	
	public static final Integer C_24026 = 24026;
	public static final String M_24026 = "区(县)名称不正确";
	
	
	public static final Integer C_24027= 24027;
	public static final String M_24027 = "您已确认收货，请勿重复操作";
	
	public static final Integer C_24028= 24028;
	public static final String M_24028 = "确认收货失败，请稍后重试";
	
	
	
	public static final Integer C_24031 = 24031;
	public static final String M_24031 = "仅已发布和已过期的货源才能刷新";
	
	public static final Integer C_24032 = 24032;
	public static final String M_24032 = "仅已发布和已过期的货源才能修改装货时间";
	
	public static final Integer C_24033 = 24033;
	public static final String M_24033 = "货源不存在";
	
	public static final Integer C_24034 = 24034;
	public static final String M_24034 = "支付预付款过期更新失败,查询记录数和更新记录数不一致!";
	
	/*******    25000-25999  支付        ******/
	public static final Integer C_25000 = 25000;
	public static final String M_25000 = "验证失败";
	
	/**消息推送**/
	public static final Integer C_26000 = 26000;
	public static final String M_26000 = "会员ID不能为空";
	
	public static final Integer C_26001 = 26001;
	public static final String M_26001 = "会员不存在";
	
	public static final Integer C_26002 = 26002;
	public static final String M_26002 = "deviceType、deviceTokens不存在";
	
	public static final Integer C_26003 = 26003;
	public static final String M_26003 = "未知设备类型";
	
	public static final Integer C_26004 = 26004;
	public static final String M_26004 = "车辆行驶证、车头照、车尾照三者必填一项";
	
	public static final Integer C_26005 = 26005;
	public static final String M_26005 = "通知";
	
	public static final Integer C_26006 = 26006;
	public static final String M_26006 = "android消息推送失败";
	
	public static final Integer C_26007 = 26007;
	public static final String M_26007 = "IOS消息推送失败";
	
	
	/** 货源规则分配 **/
	public static final Integer C_27000 = 27000;
	public static final String M_27000 = "货源不存在";
	
	public static final Integer C_27001 = 27001;
	public static final String M_27001 = "货源已被分配";
	
	public static final Integer C_27002 = 27001;
	public static final String M_27002 = "货源直接分配给指定物流公司失败";
	
	public static final Integer C_27003 = 27003;
	public static final String M_27003 = "数据推送至队列30分钟倒计时成功";
	
	public static final Integer C_27004 = 27004;
	public static final String M_27004 = "数据推送至队列30分钟倒计时失败";
	
	public static final Integer C_27005 = 27005;
	public static final String M_27005 = "货源分配失败";
	
	public static final Integer C_27006 = 27006;
	public static final String M_27006 = "货源分配失败通知农商友失败";
	
	
	/******  MQ推送给APP消息内容 ******/
    public static final String M_28000 = "有车主接单，请您查看订单";
    public static final String M_28001 = "货主已接受您的运单";
    public static final String M_28002 = "货主已确认收货";
    public static final String M_28003 = "货主已支付运费，请查收";
    public static final String M_28004 = "车主已支付信息费，请查收";
    public static final String M_28005 = "物流公司已承接待指派车主";
    public static final String M_28006 = "您的货物将有车主接单，请您查看订单";
    public static final String M_28007 = "您的证件审核不通过，请您修改";
    public static final String M_28008 = "恭喜您已通过农速通审核";
    public static final String M_28009 = "物流公司已接受您的订单";
    public static final String M_28010 = "您有新的货源分配,请查收";
    public static final String M_28011 = "您有一条新的平台配送货源,请在15分钟内指派承运司机";
    public static final String M_28012 = "预付款已支付，待司机验货并承运";
    public static final String M_28013 = "预付款已支付，请在3天内验货并承运货物";
    public static final String M_28014 = "采购商已支付尾款";
    public static final String M_28015 = "您有一条承运信息,请查收";
    public static final String M_28016 = "您有一条货源信息已失效";
    public static final String M_28017 = "司机验货已通过，开始运载货物";
    public static final String M_28018 = "司机验货不通过，运单已关闭";
    public static final String M_28019 = "司机已送达货物，请查收";
    public static final String M_28020 = "承运司机3天内未提交验货结果，运单已关闭";
    public static final String M_28021 = "采购方3天未支付预付款，订单关闭";
    public static final String M_28022 = "农速通确认订单送达";
    public static final String M_28023 = "农速通车主验货超时3天"; 
    public static final String M_28024 = "系统已经删除了你的车辆，车牌号：%s";
    
    /** 短信提醒**/
    public static final String M_28101 = "【谷登科技】验证码：%s，您正在进行手机客户端注册操作，如非本人操作请忽略";
    public static final String M_28102 = "【谷登科技】验证码：%s，请及时输入以完成新密码设置";
    public static final String M_28103 = "【谷登科技】验证码：%s，请及时输入以完成新支付密码设置";
    public static final String M_28104 = "【谷登科技】验证码：%s，您正在进行农速通修改绑定手机操作，如非本人操作请忽略";
    
    
    /******  运营后台错误29000-29999 ******/
    public static final Integer C_29001 = 29001;
    public static final String M_29001 = "当前上架广告已经超过8条，不能执行本操作";

	public static final Integer C_29002 = 29002;
	public static final String M_29002 = "广告名称已存在，不能执行本操作";
	
	public static final Integer C_29003 = 29003;
	public static final String M_29003 = "上架广告中排序已存在，请重新修改排序";
	
	public static final Integer C_29004 = 29004;
	public static final String M_29004 = "当前时间小于广告开始时间，不能上架";
	
	public static final Integer C_29005 = 29005;
	public static final String M_29005 = "导出结果集无任何数据，请重新修改查询条件..";
	
	public static final Integer C_29006 = 29006;
	public static final String M_29006 = "导出结果集超过上限（5万条），请重新修改查询条件..";
	
	public static final Integer C_29007 = 29007;
	public static final String M_29007 = "广告信息城市为空";
	
	 /******  版本控制30001-31000 ******/
	public static final Integer C_30001 = 30001;
	public static final String M_30001= "已是最新版本,无需升级";
	
	
	public static final String MSG_SOURCE_DRIVER="司机接单";
	public static final String MSG_SOURCE_PLATFORM="平台分配,司机接单";
	
	/****** 图片上传 ********/
	public static final Integer C_2000 = 2000;
	public static final String M_2000 = "图片对象为空";
	
	public static final Integer C_2001 = 2001;
	public static final String M_2001 = "上传文件大小超过最大限制20M";
	
	public static final String M_2002 = "文件上传失败！";
}
