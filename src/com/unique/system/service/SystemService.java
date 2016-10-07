package com.unique.system.service;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;

import com.sun.istack.internal.NotNull;
import com.unique.order.po.McOrder;
import com.unique.order.po.McOrderDet;
import com.unique.system.po.UserClientType;
import com.unique.system.po.VersionConfig;

public interface SystemService {
	public VersionConfig getVersion(String typeId);
	
	public HashMap<String , Object> getAdContents(String code);
	
	public HashMap<String, Object> pushMsg(String[] uctIds,
			String msgContent,
			Map<String, String> extra) throws APIConnectionException, APIRequestException;
	
	public int addOrUpdateUserClient(UserClientType userClient);
	
	/**
	 * 添加IM发送的消息
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月13日下午2:50:04
	 * 修改日期:2015年8月13日下午2:50:04
	 * @param msgContent	消息内容
	 * @param contentType 消息类型 1 文字，2 图片，3 语音， 4 回访
	 * @param url	附件URL
	 * @param fromUser 发送者ID
	 * @param toUser 接收者ID
	 * @param sendTime 发送时间
	 */
	public void addIMMsgInfo(String msgContent,
			@NotNull String contentType,String url,
			String fromUser,String toUser,String sendTime);
	
	/**
	 * 获取警告消息列表
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月15日上午11:14:55
	 * 修改日期:2015年10月15日上午11:14:55
	 * @param userId
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public HashMap<String, Object> getWarnMsg(@NotNull String userId,String dateStr, Long startRow, Long rows);
	
	/**
	 * 根据订单发送推送消息
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月4日下午3:40:24
	 * 修改日期:2015年11月4日下午3:40:24
	 * @param order
	 * @param orderDet
	 * @param type
	 */
	public void sendOrderMsg(McOrder order,McOrderDet orderDet,int type);

	/**
	 * 获取警告消息列表
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月15日上午11:14:55
	 * 修改日期:2015年10月15日上午11:14:55
	 * @param staffId
	 * @param dataStr  yyyy-mm-dd
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public HashMap<String, Object> getEarlyWarns(@NotNull String staffId,String dateStr, Long startRow, Long rows);
}

