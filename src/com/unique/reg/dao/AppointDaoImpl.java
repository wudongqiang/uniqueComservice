/**
 * 2015年11月24日
 */
package com.unique.reg.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.unique.core.annotation.HttpMethod;
import com.unique.reg.po.McAppoint;

/**
 * 约诊服务接口<br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年11月24日 下午2:43:37<br/>
 * @author Administrator
 * @date 2015年11月24日
 * @description 
 */
@Repository("appointDao")
public class AppointDaoImpl implements AppointDao{

	@Resource
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int addMcAppoint(McAppoint appoint) {
		return sqlSession.insert("addMcAppoint", appoint);
	}

	@HttpMethod
	@Override
	public List<McAppoint> getMcAppoints(String staffId, String userId,String status,
			Long startRow, Long rows) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("staffId", staffId);
		params.put("userId", userId);
		params.put("status", status);
		params.put("startRow", startRow);
		params.put("endRow", startRow+rows);
		return sqlSession.selectList("getMcAppoints", params);
	}
	
	@Override
	public long getMcAppointsCount(String staffId, String userId,String status) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("staffId", staffId);
		params.put("userId", userId);
		params.put("status", status);
		return sqlSession.selectOne("getMcAppointsCount", params);
	}
	
	@Override
	public McAppoint getMcAppointById(String appointId) {
		return sqlSession.selectOne("getMcAppointById", appointId);
	}

	@Override
	public long checkIsUniqueAppoint(String staffId, String userId,
			String appointDate) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("staffId", staffId);
		params.put("userId", userId);
		params.put("appointDate", appointDate);
		return sqlSession.selectOne("checkIsUniqueAppoint", params);
	}

}
