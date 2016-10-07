package com.unique.org.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.annotation.ParamNoNull;
import com.unique.core.util.StringUtil;
import com.unique.org.dao.DepDao;
import com.unique.org.dao.OrgDao;
import com.unique.org.dao.StaffDao;
import com.unique.org.po.Dep;
import com.unique.org.po.StaffType;
import com.unique.org.po.UserFavor;
import com.unique.patient.dao.UserDoctorRelationDao;
import com.unique.reg.dao.RegDao;
import com.unique.reg.po.Org;
import com.unique.reg.po.Staff;

@Service("staffService")
public class StaffServiceImpl implements StaffService{
	
	@Resource
	private StaffDao staffDao;
	@Resource
	private DepDao depDao;
	@Resource
	private OrgDao orgDao;
	@Resource
	private RegDao regDao;
	@Resource
	private UserDoctorRelationDao userDoctorRelationDao;
	/**
	 * 最近预约过的医生
	 */
	@Override
	@HttpMethod
	public List<Staff> recentStaff(@ParamNoNull String userId) {
		return staffDao.recentStaff(userId);
	}

	/**
	 * 科室下医生列表
	 */
	@Override
	@HttpMethod
	public List<Staff> getStaffByDep(@ParamNoNull String depId) {
		return staffDao.getStaffByDep(depId);
	}
	
	/**
	 * 科室下医生列表 - 分页
	 */
	@Override
	@HttpMethod
	public Map<String,Object> getStaffByDepPage(@ParamNoNull String depId,Long startRow,Long rows) {
		Map<String,Object> result = new HashMap<String,Object>();
		if(startRow == null) startRow = 0L;
		if(rows == null) rows =10L;
		result.put("count", staffDao.getStaffByDepPageCount(depId));
		result.put("list", staffDao.getStaffByDepPage(depId,startRow,rows));
		return result;
	}
	
	/**
	 * 科室下的医生列表（包含可预约数）
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月11日上午11:23:16
	 * 修改日期:2015年9月11日上午11:23:16
	 * @param depId
	 * @return
	 */
	@Override
	@HttpMethod
	public List<Staff> getStaffByDepHasDutys(@ParamNoNull String depId) {
		List<Staff> staffs = staffDao.getStaffByDep(depId);
		for(Staff s : staffs){
			long num = regDao.getCanRegNums(s.getStaffId().toString());
			s.setCanRegNum(num);
		}
		return staffs;
	}

	/**
	 * 医生详情
	 */
	@Override
	@HttpMethod
	public Staff staffDetail(@NotNull String staffId,String userId) {
		Staff staff = staffDao.getStaffInfo(staffId);
		staff.setFans(staffDao.getStaffFans(staffId));
		//需要查询 用户和医生是否存在 医患关系
		if(!StringUtil.isEmpty(userId)){
			long count = userDoctorRelationDao.getIsUserDoctorelation(staffId,userId);
			staff.setIsUserDoctorReation(count>0?1:0);
		}
		return staff;
	}
	 
	@HttpMethod
	@Override
	public HashMap<String, Object>  getFavorByStaffIdAndUserId(@NotNull String staffId, @NotNull String userId) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("isFavor", staffDao.getFavorByStaffIdAndUserId(staffId, userId));
		return result;
	}
	 
	
	/**
	 * 用户收藏的医生列表
	 */
	@Override
	@HttpMethod
	public List<Staff> favoriteList(@ParamNoNull String userId, Long startRow, Long rows) {
		if(startRow==null || startRow < 0) startRow = 0L;
		if(rows==null || rows < 0) rows = 10L;
		List<Staff> list = staffDao.getFavorList(userId,startRow,rows);
		//得到医生近7天的被预约的总数 -口腔
		for(Staff staff:list){
			staff.setBeReservedRegNum(regDao.getBeReservedRegNums(staff.getStaffId().toString()));
		}
		return list;
	}
	
	/**
	 * 获取总数
	 */
	@HttpMethod
	@Override
	public HashMap<String, Object> favoriteListCount(String userId){
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("count", staffDao.getFavorListCount(userId));
		return result;
	}
	
	/**
	 * 关注（收藏）医生
	 */
	@Override
	@HttpMethod
	public void favorOneStaff(@ParamNoNull String userId, @ParamNoNull String staffId) {
		UserFavor ufQuery = new UserFavor();
		ufQuery.setUserId(new BigDecimal(userId));
		ufQuery.setFavorType("1");
		ufQuery.setFavorId(new BigDecimal(staffId));
		
		UserFavor ufResult = staffDao.getOneFavor(ufQuery);
		if (ufResult == null) {
			ufResult = new UserFavor();
			ufResult.setCreateTime(new Date());
			ufResult.setFavorId(new BigDecimal(staffId));
			ufResult.setFavorType("1");
			ufResult.setStatus("C");
			ufResult.setUserId(new BigDecimal(userId));
			staffDao.addFavor(ufResult);
		} else {
			ufResult.setStatus("C");
			staffDao.updateFavorStatus(ufResult);
		}
		
		
	}
	
	/**
	 * 取消收藏
	 */
	@Override
	@HttpMethod
	public void cancelFavorStaff(@ParamNoNull String userId, @ParamNoNull String staffId) {
		UserFavor ufQuery = new UserFavor();
		ufQuery.setUserId(new BigDecimal(userId));
		ufQuery.setFavorType("1");
		ufQuery.setFavorId(new BigDecimal(staffId));
		UserFavor ufResult = staffDao.getOneFavor(ufQuery);
		
		ufResult.setStatus("R");
		staffDao.updateFavorStatus(ufResult);
	}
	
	
	/**
	 * 添加医生
	 * @param staff
	 * @param depCode
	 * @param staffTypeCode
	 * @return
	 */
	@HttpMethod
	@Transactional
	public HashMap<String, Object> addStaff(@NotNull Staff staff,@NotNull String depCode,@NotNull String staffTypeCode){
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(StringUtil.isEmpty(staff.getStaffUniqueId())){
			result.put("retCode", 3);
			result.put("retMessage", "医生唯一编码不能为空");
			return result;
		}
		Staff s = staffDao.getStaffByUnicodeID(staff.getStaffUniqueId());
		if(s!=null){
			result.put("retCode", 4);
			result.put("retMessage", "医生已存在");
			return result;
		}
		Dep dep = depDao.getDepInfoByCode(depCode);
		StaffType staffType = staffDao.getStaffTypeByCode(staffTypeCode);
		if(staffType!=null){
			staff.setStaffTypeId(staffType.getStaffTypeId());
			staff.setStaffCode(staffType.getStaffTypeCode());
			staff.setStaffTypeName(staffType.getStaffTypeName());
		}
		staff.setDepId(dep.getDepId());
		staff.setOrgId(dep.getOrgId());
		int i = staffDao.addStaff(staff);
		if(i<=0){
			result.put("retCode", 6);
			result.put("retMessage", "未知异常");
			return result;
		}
		return result;
	}
	
	
	/**
	 * 添加医生
	 * @param staff
	 * @param depCode
	 * @param staffTypeCode
	 * @return
	 */
	@HttpMethod
	@Transactional
	@Override
	public HashMap<String, Object> modStaffSimpleInfo(@NotNull String staffId,
			String orgCode,	String depCode, String staffTypeCode,
			String gootAt,String staffDesc){
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
				
			if(StringUtil.isEmpty(staffId)){
				result.put("retCode", 1);
				result.put("retMessage", "医生id不能为空");
				return result;
			}
			Staff staff = new Staff();
			staff.setStaffId(new BigDecimal(staffId));
			staff.setGootAt(gootAt);
			staff.setStaffDesc(staffDesc);
			if(!StringUtil.isEmpty(orgCode)){
				//记录机构信息
				Org org = orgDao.getOrgByCode(orgCode);
				if(org!=null){
					staff.setOrgId(org.getOrgId());	
					staff.setOrgName(org.getOrgName());
				}
			}
			if(!StringUtil.isEmpty(depCode)){
				//记录科室信息
				Dep dep = depDao.getDepInfoByCode(depCode);
				if(dep!=null){
					staff.setDepId(dep.getDepId());
					staff.setDepName(dep.getDepName());
				}
			}
			if(!StringUtil.isEmpty(staffTypeCode)){
				//记录医生级别信息
				StaffType staffType = staffDao.getStaffTypeByCode(staffTypeCode);
				if(staffType!=null){
					staff.setStaffTypeId(staffType.getStaffTypeId());
					staff.setStaffCode(staffType.getStaffTypeCode());
					staff.setStaffTypeName(staffType.getStaffTypeName());
				}
			}
			
	//		staff.setDepId(dep.getDepId());
	//		staff.setOrgId(dep.getOrgId());
			
			int i = staffDao.modStaffSimpleInfo(staff);
			if(i > 0){
				result.put("retCode", 0);
				result.put("retMessage", "修改成功！");
			}else{
				result.put("retCode", 2);
				result.put("retMessage", "修改失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("retCode", 3);
			result.put("retMessage", "未知异常");
		}
		
		return result;
	}
	
	@HttpMethod
	public Staff getStaffInfo(@NotNull String staffId){
		Staff staff = staffDao.getStaffInfo(staffId);
		staff.setFans(staffDao.getStaffFans(staffId));
		return staff;
	}

	@HttpMethod
	@Override
	public List<Staff> getMailListPage(@NotNull String orgId, Long startRow, Long rows , String staffName) {
//		if(startRow==null || startRow < 0 )startRow = 0l;
//		if(rows==null || rows < 0 )rows = 20l; //默认20条
		return staffDao.getMailListPage(orgId, startRow, rows, staffName);
	}

	@HttpMethod
	@Override
	public Map<String,Object> getMailListPageCount(String orgId, String staffName){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("count", staffDao.getMailListPageCount(orgId, staffName));
		return result;
	}
	
	/**
	 * 获取粉丝数量
	 * @param staffId
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getStaffFans(@NotNull String staffId){
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("count", staffDao.getStaffFans(staffId));
		return result;
	}

	@HttpMethod
	@Override
	public List<Staff> getFamousDoctor(String orgId) {
		List<Staff> staffs = staffDao.getFamousDoctor(orgId);
		return staffs;
	}

	@HttpMethod
	@Override
	public Map<String, Object> searchStaff(@NotNull String orgId, String staffName) {
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("list", staffDao.searchStaff(orgId,staffName));
		result.put("count", staffDao.searchStaffCount(orgId,staffName));
		return result;
	}
	
	@HttpMethod
	@Override
	public List<Staff> getSameDepReCommendStaff(String staffId) {
		return staffDao.getSameDepReCommendStaff(staffId);
	}
}
