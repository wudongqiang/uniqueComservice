package com.unique.patient.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.util.StringUtil;
import com.unique.patient.dao.UserDoctorRelationDao;
import com.unique.patient.po.UserDoctorRelation;
import com.unique.reg.po.Staff;

/**
 * 医患关系业务实现类
 * <br/>创建人 wdq
 * <br/>创建时间 上午9:40:18
 * @author Administrator
 * @date 2015年8月18日
 * @description
 */
@Service("userDoctorRelationService")
public class UserDoctorRelationServiceImpl implements UserDoctorRelationService{

	@Resource
	private UserDoctorRelationDao userDoctorRelationDao;
	
	@HttpMethod
	@Override
	public List<UserDoctorRelation> getUserdocDoctorRelations(@NotNull String staffUserId, @NotNull String type,String userName,
			Long startRow, Long rows) {
		
		if(StringUtil.isEmpty(userName)){
			if(startRow == null) startRow = 0L;
			if(rows == null) rows = 20L;
		}
		String sortCondition = null;
		if("1".equals(type)){
			//获取最近的，按创建时间 降序排序方式
			sortCondition=UserDoctorRelation.COLUMN_CREATE_TIME+" desc";
		}else{
			//获取全部，按用户名称升序排序
			sortCondition=UserDoctorRelation.COLUMN_USER_NAME;
		}
		return userDoctorRelationDao.getUserdocDoctorRelations(staffUserId,sortCondition,userName,startRow,rows);
	}

	
	@HttpMethod
	@Override
	public List<Staff> getStaffInUserDoctorRelationByUserId(@NotNull String userId, Long startRow, Long rows) {
		Long endRow = null;
		if(startRow!=null && rows!=null ) {
			endRow = startRow+rows;
		}
		return userDoctorRelationDao.getStaffInUserDoctorRelationByUserId(userId,startRow,endRow);
	}


	@HttpMethod
	@Override
	public Map<String,Object> getCanChooseStaffDoctorRelation(@NotNull String userId,
			String orgId,String orgGroupCode, Long startRow, Long rows) {
		if(startRow==null)startRow=0L;
		if(rows==null)rows=20L;
		Map<String,Object> result = new HashMap<String,Object>(2);
		result.put("staffs",userDoctorRelationDao.getCanChooseStaffDoctorRelation(userId,orgId,orgGroupCode,startRow,startRow+rows));
		result.put("count",userDoctorRelationDao.getCanChooseStaffDoctorRelationCount(userId,orgId,orgGroupCode));
		return result;
	}

}
