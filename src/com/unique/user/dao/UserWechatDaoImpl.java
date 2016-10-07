package com.unique.user.dao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.unique.user.po.UserWechat;

/**
 * 
 * 微信绑定业务<br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年10月16日 上午10:51:31<br/>
 * @author Administrator
 * @date 2015年10月16日
 * @description
 */
@Repository("userWechatDao")
public class UserWechatDaoImpl implements UserWechatDao{
	@Resource
	private SqlSessionTemplate sqlSession;

	@Override
	public int AddUserWechat(UserWechat m) {
		return sqlSession.insert("AddUserWechat",m);
	}

	@Override
	public UserWechat getUserWechatByUserId(String userId,String calltype) {
		Map<String,Object> params = new HashMap<String,Object>(2);
		params.put("userId", userId);
		params.put("calltype", calltype);
		return sqlSession.selectOne("getUserWechatByUserId", params);
	}
	
	@Override
	public int deleteUserWechatByUserId(String userId,String calltype) {
		Map<String,Object> params = new HashMap<String,Object>(2);
		params.put("userId", userId);
		params.put("calltype", calltype);
		return sqlSession.delete("deleteUserWechatByUserId", params);
	}
	 
}
