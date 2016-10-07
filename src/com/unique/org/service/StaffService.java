package com.unique.org.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unique.reg.po.Staff;

public interface StaffService {
	List<Staff> recentStaff(String userId);
	List<Staff> getStaffByDep(String depId);
	Map<String,Object> getStaffByDepPage(String depId,Long startRows,Long rows);
	Staff staffDetail(String staffId,String userId);
	
	/**
	 * 获取某用户下的某医生是否被关注
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月8日 下午4:12:32
	 * @param staffId
	 * @param userId
	 * @return
	 */
	Map<String, Object> getFavorByStaffIdAndUserId(String staffId,String userId);
	 
	List<Staff> favoriteList(String userId, Long startRow, Long rows);
	HashMap<String, Object> favoriteListCount(String userId);
	void favorOneStaff(String userId, String staffId);
	void cancelFavorStaff(String userId, String staffId);
	public HashMap<String, Object> addStaff( Staff staff, String depCode, String staffTypeCode);
	
	public Staff getStaffInfo(String staffId);
	
	
	/**
	 * 通过当前医生的机构Id（医院），获取当前所属机构下的医生通讯录 分页
	 * @param orgId 
	 * @param startRow
	 * @param rows
	 * @param staffName
	 * @return
	 */
	public List<Staff> getMailListPage(String orgId, Long startRow, Long rows,String staffName);
	
	/**
	 * 通过当前医生的机构Id（医院），获取当前所属机构下的医生通讯录 总数
	 * @param orgId 
	 * @param staffName
	 * @return
	 */
	public Map<String,Object> getMailListPageCount(String orgId, String staffName);
	
	/**
	 * 修改医生简单的信息
	 * @param staffId 医生Id
	 * @param orgCode 机构代码
	 * @param depCode 部门代码
	 * @param staffTypeCode 级别代码
	 * @param goodAt  擅长
	 * @param staffDesc 简介
	 * @return
	 */
	HashMap<String, Object> modStaffSimpleInfo(String staffId, String orgCode,
			String depCode, String staffTypeCode, String goodAt, String staffDesc);
	
	/**
	 * 获取医院下的名医--限50个
	 * <br/>创建人 wdq
	 * <br/>创建时间 下午2:40:49
	 * @param orgId
	 * @return
	 */
	public List<Staff> getFamousDoctor(String orgId);
	
	/**
	 * 重医-搜索医生
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月10日 上午11:46:19
	 * @param orgId
	 * @param staffName
	 * @return
	 */
	public Map<String,Object> searchStaff(String orgId,String staffName);
	/**
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月11日上午11:23:51
	 * 修改日期:2015年9月11日上午11:23:51
	 * @param depId
	 * @return
	 */
	List<Staff> getStaffByDepHasDutys(String depId);
	
	/**
	 * 查询相同科室下的推荐医生<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月17日 上午10:00:48<br/>
	 * @param staffId
	 * @return
	 */
	public List<Staff> getSameDepReCommendStaff(String staffId);
	
}
