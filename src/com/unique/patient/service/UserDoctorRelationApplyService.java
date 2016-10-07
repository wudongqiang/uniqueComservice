package com.unique.patient.service;

import java.util.Map;

/**
 * 医患关系申请 业务接口<br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年10月13日 上午11:09:01<br/>
 * @author Administrator
 * @date 2015年10月13日
 * @description
 */
public interface UserDoctorRelationApplyService{
	
	/**
	 * 添加医患关系申请<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月13日 上午11:19:41<br/>
	 * @param staffUserId 医生用户id
	 * @param userId 用户id
	 * @return
	 */
	public Map<String,Object> addUserDoctorRelationApply(String staffUserId,String userId,String message);
	
	/**
	 * 更新申请状态<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月14日 下午4:47:37<br/>
	 * @param applyId
	 * @param status 2同意 3 拒绝
	 * @param reason 原因
	 * @return
	 */
	public Map<String,Object> updateUserDoctorRelationApplyStatus(String applyId,String status,String reason);
	
	/**
	 * 通过Id获取医患关系申请<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月14日 下午5:27:29<br/>
	 * @param applyId
	 * @return
	 */
	public Map<String,Object> getUserDoctorRelationApplyStatusByapplyId(String applyId);
	
	/**
	 * 
	 * 检查医患关系<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月16日 下午5:36:21<br/>
	 * @param staffUserId
	 * @param userId
	 * @return
	 */
	public Map<String,Object> getIsUserDoctorelation(String staffId,String userId);
}

