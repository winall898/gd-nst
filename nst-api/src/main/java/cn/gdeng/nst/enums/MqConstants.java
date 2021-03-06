package cn.gdeng.nst.enums;
/**
 * 队列常量
 * @author zhangnf
 *
 */
public class MqConstants {

	/** Topic **/
	public static final String TOPIC = "Topic";
	/** TagA **/
	public static final String TAG = "TagA";
	
	/** 0 会员同步 1 货源分配 2 会员统计 3 支付流水 4 定时任务 5 APP消息推送**/
	public static final Integer TOPIC_MEMBER_INFO = 0;
	/** 0 会员同步 1 货源分配 2 会员统计 3 支付流水 4 定时任务 5 APP消息推送 **/
	public static final Integer TOPIC_SOURCE_SHIPPER = 1;
	/** 0 会员同步 1 货源分配 2 会员统计 3 支付流水 4 定时任务 5 APP消息推送 **/
	public static final Integer TOPIC_ORDER_INFO = 2;
	/** 0 会员同步 1 货源分配 2 会员统计 3 支付流水 4 定时任务 5 APP消息推送 **/
	public static final Integer TOPIC_PAY_DETAIL = 3;
	/** 0 会员同步 1 货源分配 2 会员统计 3 支付流水 4 定时任务 5 APP消息推送 **/
	public static final Integer TOPIC_TASK = 4;
	/** 0 会员同步 1 货源分配 2 会员统计 3 支付流水 4 定时任务 5 APP消息推送 **/
	public static final Integer TOPIC_PUSH = 5;
	
	/** 收发类型 0 消费者 1 生产者 **/
	public static final Integer BIZ_TYPE_0 = 0;
	/** 收发类型0 消费者 1 生产者 **/
	public static final Integer BIZ_TYPE_1 = 1;
	/** 收发类型0 消费者 1 生产者 2接口 **/
	public static final Integer BIZ_TYPE_2 = 2;
	
	/** 定时消息类型 0 订单接受按钮 (10分钟) 1 货源分配(30分钟)  **/
	public static final int TASK_TYPE_0 = 0;
	/** 定时消息类型 0 订单接受按钮 (10分钟) 1 货源分配(30分钟)  **/
	public static final int TASK_TYPE_1 = 1;
}
