package com.unique.patient.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.unique.patient.po.UserDoctorRelationApply;


/**
 * 医患关系申请 数据访问实现类<br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年10月13日 上午11:09:01<br/>
 * @author Administrator
 * @date 2015年10月13日
 * @description
 */
@Repository("userDoctorRelationApplyDao")
public class UserDoctorRelationApplyDaoImpl implements UserDoctorRelationApplyDao{
	@Resource
	private SqlSession sqlSession;
	
	@Override
	public int addUserDoctorRelationApply(UserDoctorRelationApply m) {
		return sqlSession.insert("addUserDoctorRelationApply", m);
	}
	
	@Override
	public List<UserDoctorRelationApply> getUserDoctorRelationApplyByCondition(UserDoctorRelationApply m){
		return sqlSession.selectList("getUserDoctorRelationApplyByCondition", m);
	}
	
	@Override
	public int updateUserDoctorRelationApply(UserDoctorRelationApply m) {
		return sqlSession.update("updateUserDoctorRelationApply", m);
	}
	
	@Override
	public UserDoctorRelationApply getUserDoctorRelationApplyById(String applyId) {
		return sqlSession.selectOne("getUserDoctorRelationApplyById", applyId);
	}
}