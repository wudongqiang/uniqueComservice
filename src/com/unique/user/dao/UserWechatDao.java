package com.unique.user.dao;

import com.unique.user.po.UserWechat;


public interface UserWechatDao {
	/**
	 * 添加用户微信绑定<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月16日 上午10:52:28<br/>
	 * @param m
	 * @return
	 */
	 public int AddUserWechat(UserWechat m);
	 
	 /**
	  * 获取用户微信绑定关系<br/>
	  * 创建人 wdq<br/>
	  * 创建时间 2015年10月16日 上午10:54:46<br/>
	  * @param userId 用户Id
	  * @param weChatId 微信Id
	  * @return
	  */
	 public UserWechat getUserWechatByUserId(String userId,String calltype);
	 
	 /**
	  * 通过userId删除
	  * <br/>
	  * 创建人 wdq<br/>
	  * 创建时间 2015年10月22日 下午5:28:35<br/>
	  * @param userId
	  * @return
	  */
	 public int deleteUserWechatByUserId(String userId,String calltype);
	 
}
