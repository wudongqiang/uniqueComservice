package com.unique.core.util;
/**
 * 用于管理Redis的键
 * @author Angel
 *
 */
public class RedisKeys {
	///接口调用日志
	public static final String COM_LOG = "zjk:comservice:log";
	///移动设备ID列表
	public static final String IOS_DEVICE_LIST  = "zjk:ios:deviceList";
	///IOS设备信息（token）
	public static final String IOS_DEVICE  = "zjk:ios:device:";
	///随机码列表
	public static final String RANDOM_NUM_INFOS  = "zjk:randomnum:";
	///接口实例列表
	public static final String COMSERVICE_BEANS  = "zjk:comservice:beans:";
	///用户最近的挂号记录ID集合
	public static final String REG_LIST_HISTORY_LIST  = "zjk:reg:regHistoryList";
	///用户最近的挂号记录
	public static final String REG_LIST_HISTORY  = "zjk:reg:regHistory:";
	
	///用户最近的退号记录ID集合
	public static final String UNREG_LIST_HISTORY_LIST  = "zjk:unreg:regHistoryList";
	///用户最近的退号记录
	public static final String UNREG_LIST_HISTORY  = "zjk:unreg:regHistory:";
	
	///用户最近的订单记录
	public static final String ORDER_LIST_HISTORY  = "zjk:reg:orderHistory:";
	///用户最近的订单记录
	public static final String ORDER_LIST_HISTORY_LIST  = "zjk:reg:orderHistoryList";
	
	///用户最近的订单记录
	public static final String UNORDER_LIST_HISTORY  = "zjk:unorder:orderHistory:";
	///用户最近的订单记录
	public static final String UNORDER_LIST_HISTORY_LIST  = "zjk:unorder:orderHistoryList";
	///用户推送编号
	public static final String USER_PUSH_CODE_USER  = "zjk:user:push_user:";
	public static final String USER_PUSH_CODE_DEVICE = "zjk:user:push_device:";
	///IM消息临时存储
	public static final String IM_MSG_INFO = "zjk:im:msg";
}
