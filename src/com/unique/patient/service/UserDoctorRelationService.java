package com.unique.patient.service;

import java.util.List;
import java.util.Map;

import com.unique.patient.po.UserDoctorRelation;
import com.unique.reg.po.Staff;

/**
 * 医患关系 业务接口
 * <br/>创建人 wdq
 * <br/>创建时间 上午9:39:55
 * @author Administrator
 * @date 2015年8月18日
 * @description
 */
public interface UserDoctorRelationService {

	/**
	 * 获取我的患者 通过医生Id   -- 分页
	 * <br/>创建人 wdq
	 * <br/>创建时间 上午10:05:56
	 * @param staffUserId  医生用户Id
	 * @param startRow 开始行  默认0
	 * @param rows     每页显示行数 默认20
	 * @param type	       查询类型（1：最近    2：全部） 
	 * @param userName 名字查询 
	 * @return
	 */
	public List<UserDoctorRelation> getUserdocDoctorRelations(String staffUserId, String type,String userName, Long startRow, Long rows);
	
	/**
	 * 通过用户id查询医生信息<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月9日 上午9:45:06<br/>
	 * @param userId
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public List<Staff> getStaffInUserDoctorRelationByUserId(String userId,Long startRow, Long rows);
	
	/**
	 * 获取患者还可以选择添加的医生到医患关系<br/>
	 * 其中参数orgId 和 orgGroupCode 二选一 或都不传入则表示查询所有医院<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月12日 下午5:10:31<br/>
	 * @param userId
	 * @param orgId 医院id 查询具体某一医院医生
	 * @param orgGroupCode 根据分组code查询其下的医院医生
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public Map<String,Object> getCanChooseStaffDoctorRelation(String userId,String orgId,String orgGroupCode,Long startRow, Long endRow);

}
