package com.unique.mb.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.unique.mb.po.EqupType;
import com.unique.mb.po.UserHypertensionTest;
import com.unique.mb.po.UserHypertensionTestByTime;
import com.unique.mb.po.UserHypertensionTestDto;
import com.unique.mb.po.UserTestByTime;
import com.unique.mb.po.UserTestResult;
import com.unique.mb.po.UserTestTime;

@Repository("hyperTestDao")
public class HyperTestDaoImpl implements HyperTestDao{
	@Resource
	private SqlSessionTemplate sqlSession;
	
	public List<UserHypertensionTest> getTestPage(String userId,Long startRow,Long endRow){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("startRow", startRow);
		param.put("endRow", endRow);
		return sqlSession.selectList("getTestPage",param);
	}
	
	public long getTestPageCount(String userId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		return sqlSession.selectOne("getTestPageCount",param);
	}
	
	public int addTest(UserHypertensionTest test){
		return sqlSession.insert("addTest",test);
	}

	@Override
	public List<UserHypertensionTest> getUserHypertensionTestDays(String userId) {
		return sqlSession.selectList("getUserHypertensionTestDays",userId);
	}

	@Override
	public List<UserHypertensionTestDto> getUserHypertensionTestWeeksAndMonth(
			String userId, String type) {
		Map<String, Object> param = new HashMap<String, Object>(2);
		param.put("userId", userId);
		param.put("type", type);
		return sqlSession.selectList("getUserHypertensionTestWeeksAndMonth",param);
	}
	
	/**
	 * 按照时间分割类型查询测试记录
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月2日上午11:39:47
	 * 修改日期:2015年9月2日上午11:39:47
	 * @param type 1 日，2 周，3 月
	 * @param time 最后一天的时间
	 * @return
	 */
	public List<UserHypertensionTestByTime> getUserHypertensionTestByTime(Integer type,Date time,String userId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("time", time);
		switch (type) {
		case 1: //天
			return sqlSession.selectList("getUserHypertensionTestByDay",param);
		case 2: //周
			param.put("type", "week");
			return sqlSession.selectList("getUserHypertensionTestByWeekAndMonth",param);
		case 3: //月
			param.put("type", "month");
			return sqlSession.selectList("getUserHypertensionTestByWeekAndMonth",param);
		default:
			break;
		}
		return null;
	}
	
	
	/**
	 * 按照时间分割类型查询测试记录(平均方式)
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月2日上午11:39:47
	 * 修改日期:2015年9月2日上午11:39:47
	 * @param type 1 日，2 周，3 月
	 * @param time 最后一天的时间
	 * @return
	 */
	public List<UserHypertensionTestByTime> getUserHypertensionTestByTimeAvg(Integer type,Date time,String userId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("time", time);
		switch (type) {
		case 1: //天
			return sqlSession.selectList("getUserHypertensionTestByDayAvg",param);
		case 2: //周
			param.put("type", "week");
			return sqlSession.selectList("getUserHypertensionTestByWeekAndMonthAvg",param);
		case 3: //月
			param.put("type", "month");
			return sqlSession.selectList("getUserHypertensionTestByWeekAndMonthAvg",param);
		default:
			break;
		}
		return null;
	}
	
	
	/**
	 * 添加测试结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月21日上午9:42:31
	 * 修改日期:2015年9月21日上午9:42:31
	 * @param result
	 * @return
	 */
	public int addTestResult(UserTestResult result){
		return sqlSession.insert("addUserTestResult",result);
	}
	
	/**
	 * 获取设备EQID
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月21日上午9:48:52
	 * 修改日期:2015年9月21日上午9:48:52
	 * @param tableName
	 * @return
	 */
	public EqupType getEqIdByTableName(String tableName){
		return sqlSession.selectOne("getEqIdByTableName",tableName);
	}
	
	public List<UserTestByTime> getUserTestXLXYByTime(Integer type,Date time,String userId,String kpiCode,String testTimeId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");
		param.put("nowdate", sdf.format(time));
		param.put("type", type);
		param.put("kpiCode", kpiCode);
		
		if(testTimeId!=null){
			UserTestTime testTime = sqlSession.selectOne("getTestTimeById", testTimeId);
			param.put("testTimeId", testTimeId);
			param.put("beginTime", sdf2.format(testTime.getTestBeginTime()));
			param.put("endTime", sdf2.format(testTime.getTestEndTime()));
		}
		switch (type) {
		case 1: //天
			return sqlSession.selectList("getUserTestList_day",param);
		case 2: //周
			return sqlSession.selectList("getUserTestList_weekAndMonth",param);
		case 3: //月
			return sqlSession.selectList("getUserTestList_weekAndMonth",param);
		default:
			break;
		}
		return null;
	}
	
	public List<UserTestByTime> getUserTestXTByTime(Integer type,Date time,String userId,String kpiCode,String testTimeId,List<UserTestTime> testTimes){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");
		param.put("nowdate", sdf.format(time));
		param.put("type", type);
		param.put("kpiCode", kpiCode);
		param.put("testTimes", testTimes);
		
		if(testTimeId!=null){
			UserTestTime testTime = sqlSession.selectOne("getTestTimeById", testTimeId);
			param.put("testTimeId", testTimeId);
			param.put("beginTime", sdf2.format(testTime.getTestBeginTime()));
			param.put("endTime", sdf2.format(testTime.getTestEndTime()));
		}
		switch (type) {
		case 1: //天
			return sqlSession.selectList("getUserTestXTList_day",param);
		case 2: //周
			return sqlSession.selectList("getUserTestXTList_weekAndMonth",param);
		case 3: //月
			return sqlSession.selectList("getUserTestXTList_weekAndMonth",param);
		default:
			break;
		}
		return null;
	}
	
	/**
	 * 根据时间查询时间段ID
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月21日下午8:50:45
	 * 修改日期:2015年10月21日下午8:50:45
	 * @param date
	 * @param kpiCode
	 * @return
	 */
	public String getTestTimeIdByTime(Date date,String kpiCode,String userId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("time", date);
		param.put("kpiCode", kpiCode);
		param.put("userId", userId);
		return sqlSession.selectOne("getTestTimeIdByTime",param);
	}
	
	public void updateUserHypertensionTestById(UserHypertensionTest test){
		sqlSession.update("updateUserHypertensionTestById",test);
	}
	
	/**
	 * 根据ID获取血压测量结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月23日下午2:50:25
	 * 修改日期:2015年11月23日下午2:50:25
	 * @param testId
	 * @return
	 */
	public UserHypertensionTest getHypertensionInfo(String testId){
		return sqlSession.selectOne("getHypertensionInfo",testId);
	}
	
	@Override
	public int handlerEarlyWarn(String testResultDetId, String doWay) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("testResultDetId", testResultDetId);
		param.put("doWay", doWay);
		return sqlSession.update("handlerEarlyWarn",param);
	}
	
	/**
	 * 按周获取血压数据
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月26日上午10:03:51
	 * 修改日期:2015年11月26日上午10:03:51
	 * @param userId
	 * @param nowdate
	 * @return
	 */
	public List<Map<String, Object>> getHypertensionByWeek(String userId,String nowdate){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("nowdate", nowdate);
		return sqlSession.selectList("getHypertensionByWeek",param);
	}
}
