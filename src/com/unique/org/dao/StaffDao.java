package com.unique.org.dao;

import java.util.List;

import com.unique.org.po.StaffType;
import com.unique.org.po.UserFavor;
import com.unique.org.vo.DutyPeriodMap;
import com.unique.org.vo.ScheduleResultMap;
import com.unique.org.vo.StaffBaseInfo;
import com.unique.org.vo.StaffDuty;
import com.unique.org.vo.StaffDutyPeriod;
import com.unique.reg.po.Staff;

public interface StaffDao {
	
	List<Staff> recentStaff(String userId);
	List<Staff> getStaffByDep(String depId);
	List<Staff> getStaffByDepPage(String depId,Long startRow,Long rows);
	Long getStaffByDepPageCount(String depId);
	Staff getStaffById(String staffId);
	Long getFavorByStaffIdAndUserId(String staffId,String userId);
	
	/**
	 * 获取关注的医生 -分页
	 * @param userId
	 * @param startRow
	 * @param row
	 * @return
	 */
	List<Staff> getFavorList(String userId,Long startRow,Long row);
	/**
	 * 获取关注的医生总数
	 * @param userId
	 * @return
	 */
	int getFavorListCount(String userId);
	UserFavor getOneFavor(UserFavor uf);
	void addFavor(UserFavor uf);
	void updateFavorStatus(UserFavor uf);
	
	List<ScheduleResultMap> getMcDutyScheduleByDate(String depId);
	List<ScheduleResultMap> getMcDutyScheduleByDoctor(String depId);
	
	public Staff getStaffByUnicodeID(String unicodeId);
	public List<DutyPeriodMap> getDutyPeriod(String dutyId);
	public StaffType getStaffTypeByCode(String typeCode);
	public int addStaff(Staff staff);
	
	public Staff getStaffByCode(String code);
	
	public Staff getStaffInfo(String staffId);
	

	public Long getStaffFans(String staffId);
	
	/**
	 * 通过当前医生的机构Id（医院），获取当前所属机构下的医生通讯录   分页
	 * @param orgId
	 * @param startRow 开始行
	 * @param rows 行数
	 * @param staffName
	 * @return
	 */
	List<Staff> getMailListPage(String orgId, Long startRow, Long rows,String staffName);
	/**
	 * 通过当前医生的机构Id（医院），获取当前所属机构下的医生通讯录 总数
	 * @param orgId
	 * @param staffName
	 * @return
	 */
	long getMailListPageCount(String orgId, String staffName);
	
	/**
	 * 修改医生简单信息
	 * @param staff
	 * @return
	 */
	int modStaffSimpleInfo(Staff staff);
	
	/**
	 * 获取医院下的名医--限50个
	 * <br/>创建人 wdq
	 * <br/>创建时间 下午2:43:55
	 * @param orgId
	 * @return
	 */
	List<Staff> getFamousDoctor(String orgId);
	
	public Staff getStaffByUserId(String userId);

	/**
	 * 通过部门id获取部门下所有的医生的排班 
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月9日 下午12:37:32
	 * @param depId
	 */
	List<StaffDuty> getStaffMcDutyByDepId(String depId);
	/**
	 * 获取医生近7天的排班信息
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月9日 下午2:53:31
	 * @param staffId
	 * @param type 0表示查询部分 5条，1表示查询全部 ，默认不传表示查询部分
	 * @return
	 */
	List<StaffDutyPeriod> getStaffMcDutyRecentlyByStaffId(String staffId,
			String type);
	/**
	 * 重医-搜索医生
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月10日 上午11:48:29
	 * @param orgId
	 * @param staffName
	 * @return
	 */
	List<StaffBaseInfo> searchStaff(String orgId, String staffName);
	/**
	 * 重医-搜索医生总数
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月10日 上午11:48:29
	 * @param orgId
	 * @param staffName
	 * @return
	 */
	Long searchStaffCount(String orgId, String staffName);
	
	/**
	 * 查询相同科室下的推荐医生<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月17日 上午10:00:48<br/>
	 * @param staffId
	 * @return
	 */
	List<Staff> getSameDepReCommendStaff(String staffId);
}
