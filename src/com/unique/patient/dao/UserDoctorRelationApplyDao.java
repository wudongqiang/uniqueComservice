package com.unique.patient.dao;

import java.util.List;

import com.unique.patient.po.UserDoctorRelationApply;

/**
 * 医患关系申请 数据访问接口<br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年10月13日 上午11:09:01<br/>
 * @author Administrator
 * @date 2015年10月13日
 * @description
 */
public interface UserDoctorRelationApplyDao{
	/**
	 * 添加医患关系申请<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月13日 上午11:19:41<br/>
	 * @param m
	 * @return
	 */
	public int addUserDoctorRelationApply(UserDoctorRelationApply m);
	
	/**
	 * 查询申请关系<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月14日 下午3:41:43<br/>
	 * @param m
	 * @return
	 */
	public List<UserDoctorRelationApply> getUserDoctorRelationApplyByCondition(UserDoctorRelationApply m);

	/**
	 * 更新申请状态<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月14日 下午4:48:58<br/>
	 * @param m
	 */
	public int updateUserDoctorRelationApply(UserDoctorRelationApply m);

	/**
	 * 根据Id获取医患关系申请<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月15日 上午9:07:04<br/>
	 * @param applyId
	 * @return
	 */
	public UserDoctorRelationApply getUserDoctorRelationApplyById(String applyId);
}

