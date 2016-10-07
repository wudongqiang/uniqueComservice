package com.unique.patient.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.unique.core.util.StringUtil;
import com.unique.patient.po.UserDoctorRelation;
import com.unique.reg.po.Staff;

/**
 * 医患关系数据实现类
 * <br/>创建人 wdq
 * <br/>创建时间 上午9:40:18
 * @author Administrator
 * @date 2015年8月18日
 * @description
 */
@Repository("userDoctorRelationDao")
public class UserDoctorRelationDaoImpl implements UserDoctorRelationDao{

	@Resource
	private SqlSession sqlSession;

	@Override
	public List<UserDoctorRelation> getUserdocDoctorRelations(String staffUserId,
			String sortCondition,String userName, Long startRow, Long rows) {
		Map<String,Object> params = new HashMap<String,Object>(5);
		if(startRow!=null && rows!=null){
			params.put("startRow", startRow);
			params.put("endRow", rows+startRow);
		}
		params.put("staffUserId", staffUserId);
		params.put("sortCondition", sortCondition);
		if(!StringUtil.isEmpty(userName)){
			params.put("userName", userName);
		}
		return sqlSession.selectList("getUserdocDoctorRelations", params);
	}
 
	@Override
	public List<Staff> getStaffInUserDoctorRelationByUserId(String userId,
			Long startRow, Long endRow) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("startRow", startRow);
		params.put("endRow", endRow);
		return sqlSession.selectList("getStaffInUserDoctorRelationByUserId", params);
	}
	
	@Override
	public List<Staff> getCanChooseStaffDoctorRelation(String userId, String orgId,String orgGroupCode, Long startRow, Long endRow) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("orgId", orgId);
		params.put("orgGroupCode", orgGroupCode);
		params.put("startRow", startRow);
		params.put("endRow", endRow);
		return sqlSession.selectList("getCanChooseStaffDoctorRelation", params);
	}
	
	@Override
	public Long getCanChooseStaffDoctorRelationCount(String userId, String orgId,String orgGroupCode) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("orgId", orgId);
		params.put("orgGroupCode", orgGroupCode);
		return sqlSession.selectOne("getCanChooseStaffDoctorRelationCount", params);
	}
	
	@Override
	public long getIsUserDoctorelation(String staffId, String userId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("staffId", staffId);
		params.put("userId", userId);
		return sqlSession.selectOne("getIsUserDoctorelation", params);
	}
	 
	@Override
	public int addUserDoctorRelation(UserDoctorRelation udr) {
		return sqlSession.insert("addUserDoctorRelation", udr);
	}
	
	@Override
	public int getUserdocDoctorRelationsCountByStaffId(String staffId) {
		return sqlSession.selectOne("getUserdocDoctorRelationsCountByStaffId", staffId);
	}
}
