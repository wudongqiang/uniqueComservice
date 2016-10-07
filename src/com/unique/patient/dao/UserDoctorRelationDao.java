package com.unique.patient.dao;

import java.util.List;

import com.unique.patient.po.UserDoctorRelation;
import com.unique.reg.po.Staff;

/**
 * 医患关系 数据接口
 * <br/>创建人 wdq
 * <br/>创建时间 上午9:39:55
 * @author Administrator
 * @date 2015年8月18日
 * @description
 */
public interface UserDoctorRelationDao {

	/**
	 * 查询我的患者
	 * <br/>创建人 wdq
	 * <br/>创建时间 上午10:19:20
	 * @param staffUserId
	 * @param sortCondition 排序条件
	 * @param userName
	 * @param startRow  
	 * @param rows
	 * @return
	 */
	List<UserDoctorRelation> getUserdocDoctorRelations(String staffUserId,
			String sortCondition,String userName, Long startRow, Long rows);

	/**
	 * 通过用户id查询医生信息<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月9日 上午9:47:58<br/>
	 * @param userId
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	List<Staff> getStaffInUserDoctorRelationByUserId(String userId,
			Long startRow, Long endRow);
	
	/**
	 * 获取患者还可以选择添加的医生到医患关系<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月12日 下午5:10:31<br/>
	 * @param userId
	 * @param orgId
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	List<Staff> getCanChooseStaffDoctorRelation(String userId,String orgId,String orgGroupCode,Long startRow, Long endRow);

	/**
	 * 获取患者还可以选择添加的医生到医患关系总数<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月13日 上午10:24:33<br/>
	 * @param userId
	 * @param orgId
	 * @return
	 */
	Long getCanChooseStaffDoctorRelationCount(String userId, String orgId,String orgGroupCode);

	/**
	 * 获取医生和用户是否存在用户关系<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月13日 上午10:57:02<br/>
	 * @param staffId
	 * @param userId
	 * @return
	 */
	long getIsUserDoctorelation(String staffId, String userId);

	/**
	 * 添加医患关系<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月14日 下午5:13:34<br/>
	 * @param udr
	 */
	int addUserDoctorRelation(UserDoctorRelation udr);

	/**
	 * 查询医生 目前 的医患关系数量<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月15日 上午11:28:10<br/>
	 * @param staffId
	 * @return
	 */
	int getUserdocDoctorRelationsCountByStaffId(String staffId);

}
