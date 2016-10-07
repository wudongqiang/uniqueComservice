package com.unique.org.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.unique.org.po.StaffType;
import com.unique.org.po.UserFavor;
import com.unique.org.vo.DutyPeriodMap;
import com.unique.org.vo.ScheduleResultMap;
import com.unique.org.vo.StaffBaseInfo;
import com.unique.org.vo.StaffDuty;
import com.unique.org.vo.StaffDutyPeriod;
import com.unique.reg.po.Staff;

@Repository("staffDao")
public class StaffDaoImpl implements StaffDao{
	@Resource
	private SqlSessionTemplate sqlSession;
	
	public List<Staff> recentStaff(String userId) {
		return this.sqlSession.selectList("getRecentStaff", userId);
	}
	
	@Override
	public List<Staff> getStaffByDep(String depId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("depId", depId);
		return this.sqlSession.selectList("getStaffByDep", params);
	}
	@Override
	public List<Staff> getStaffByDepPage(String depId,Long startRow,Long rows) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("depId", depId);
		params.put("startRow", startRow);
		params.put("endRow", startRow + rows);
		return this.sqlSession.selectList("getStaffByDep", params);
	}
	@Override
	public Long getStaffByDepPageCount(String depId) {
		return this.sqlSession.selectOne("getStaffByDepCount", depId);
	}
	
	public Staff getStaffById(String staffId) {
		return this.sqlSession.selectOne("getStaffDetailById", staffId);
	}
	public Long getFavorByStaffIdAndUserId(String staffId,String userId){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("staffId", staffId);
		params.put("userId", userId);
		return this.sqlSession.selectOne("getFavorByStaffIdAndUserId", params);
	}

	@Override
	public List<Staff> getFavorList(String userId,Long startRow,Long rows) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("startRow", startRow);
		params.put("endRow", startRow + rows);
		return this.sqlSession.selectList("getFavorList", params);
	}
	
	@Override
	public int getFavorListCount(String userId){
		return this.sqlSession.selectOne("getFavorListCount", userId);
	}
	
	@Override
	public UserFavor getOneFavor(UserFavor uf) {
		// TODO Auto-generated method stub
		return this.sqlSession.selectOne("getFavor", uf);
	}

	@Override
	public void addFavor(UserFavor uf) {
		this.sqlSession.insert("addFavor", uf);
		
	}

	@Override
	public void updateFavorStatus(UserFavor uf) {
		this.sqlSession.update("updateFavorStatus",uf);
	}

	@Override
	public List<ScheduleResultMap> getMcDutyScheduleByDate(String depId) {
		return this.sqlSession.selectList("getMcDutyScheduleByDate", depId);
	}

	@Override
	public List<ScheduleResultMap> getMcDutyScheduleByDoctor(String depId) {
		return this.sqlSession.selectList("getMcDutyScheduleByDoctor", depId);
	}

	@Override
	public List<DutyPeriodMap> getDutyPeriod(String dutyId) {
		return this.sqlSession.selectList("getDutyPeriod", dutyId);
	}
	
	public Staff getStaffByUnicodeID(String unicodeId){
		return sqlSession.selectOne("getStaffByUnicodeID",unicodeId);
	}
	public Staff getStaffByCode(String code){
		return sqlSession.selectOne("getStaffByCode",code);
	}
	
	public StaffType getStaffTypeByCode(String typeCode){
		return sqlSession.selectOne("getStaffTypeByCode",typeCode);
	}
	
	public int addStaff(Staff staff){
		return sqlSession.insert("addStaff",staff);
	}
	
	public Staff getStaffInfo(String staffId){
		return sqlSession.selectOne("getStaffInfo",staffId);
	}

	@Override
	public List<Staff> getMailListPage(String orgId,Long startRow,Long rows, String staffName) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orgId", orgId);
		if(startRow!=null && rows!=null){
			params.put("startRow", startRow);
			params.put("endRow", startRow + rows);
		}
		params.put("staffName", staffName);
		return sqlSession.selectList("getMailListPage",params);
	}
	
	@Override
	public long getMailListPageCount(String orgId, String staffName){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		params.put("staffName", staffName);
		return sqlSession.selectOne("getMailListPageCount",params);
	}
	
	public Long getStaffFans(String staffId){
		return sqlSession.selectOne("getStaffFans",staffId);
	}

	@Override
	public int modStaffSimpleInfo(Staff staff) {
		return sqlSession.update("modStaffSimpleInfo",staff);
	}

	@Override
	public List<Staff> getFamousDoctor(String orgId) {
		return sqlSession.selectList("getFamousDoctor",orgId);
	}
	
	public Staff getStaffByUserId(String userId){
		return sqlSession.selectOne("getStaffByUserId",userId);
	}

	@Override
	public List<StaffDuty> getStaffMcDutyByDepId(String depId) {
		return sqlSession.selectList("getStaffMcDutyByDepId",depId);
	}

	@Override
	public List<StaffDutyPeriod> getStaffMcDutyRecentlyByStaffId(
			String staffId, String type) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("staffId", staffId);
		params.put("type", type);
		return sqlSession.selectList("getStaffMcDutyRecentlyByStaffId",params);
	}
	 
	@Override
	public List<StaffBaseInfo> searchStaff(String orgId, String staffName) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orgId", orgId);
		params.put("staffName", staffName);
		return sqlSession.selectList("searchStaff",params);
	}
	
	@Override
	public Long searchStaffCount(String orgId, String staffName) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orgId", orgId);
		params.put("staffName", staffName);
		return sqlSession.selectOne("searchStaffCount",params);
	}
 
	@Override
	public List<Staff> getSameDepReCommendStaff(String staffId) {
		return sqlSession.selectList("getSameDepReCommendStaff",staffId);
	}
}
