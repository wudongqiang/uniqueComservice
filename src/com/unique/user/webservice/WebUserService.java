/**
 *
 */
package com.unique.user.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年8月25日上午11:31:14
 * 修改日期:2015年8月25日上午11:31:14
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface WebUserService {
	/**
	 * 平台发送消息方法
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月18日上午10:00:11
	 * 修改日期:2015年8月18日上午10:00:11
	 * @param fromUserId 发送者ID
	 * @param content 发送内容
	 * @param toUserIds 接收者ID
	 * @return
	 */
	@WebMethod
	public boolean sendMsg(String fromUserId,String content,String extra,String[] toUserIds);
	
	/**
	 * 反馈消息
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月1日下午12:47:34
	 * 修改日期:2015年9月1日下午12:47:34
	 * @param fromUserId
	 * @param content
	 * @param extra
	 * @param toUserId
	 * @return
	 */
	public boolean sendNtfMessage(
			String fromUserId,
			String content,
			String extra,
			String toUserId);
}
