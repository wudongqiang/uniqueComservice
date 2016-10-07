/**
 * 2015年10月8日
 */
package com.unique.mb.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.unique.mb.po.TestResult;
import com.unique.mb.po.TestResultDet;
import com.unique.mb.po.UserBloodSugarTest;
import com.unique.mb.po.UserBloodSugarTestByTime;
import com.unique.mb.po.UserBloodSugarTestDto;
import com.unique.mb.po.UserHeartRateTest;
import com.unique.mb.po.UserTestResult;
import com.unique.mb.po.UserTestTime;

/**
 * 用户血糖测试记录 数据访问接口实现类<br/>
 * 创建人 azq<br/>
 * 创建时间 2015年10月8日 上午9:37:34<br/>
 * @author Administrator
 * @date 2015年10月8日
 * @description 
 */
@Repository("userBloodSugarTestDao")
public class UserBloodSugarTestDaoImpl implements UserBloodSugarTestDao{
	
	@Resource
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<UserBloodSugarTest> getUserBloodSugarTestDays(String userId) {
		return sqlSession.selectList("getUserBloodSugarTestDays",userId);
	}

	@Override
	public List<UserBloodSugarTestDto> getUserBloodSugarTestWeeksAndMonth(
			String userId, String type) {
		Map<String, Object> param = new HashMap<String, Object>(2);
		param.put("userId", userId);
		param.put("type", type);
		return sqlSession.selectList("getUserBloodSugarTestWeeksAndMonth",param);
	}
	
	/**
	 * 按照时间分割类型查询测试记录
	 * @param type 1 日，2 周，3 月
	 * @param time 最后一天的时间
	 * @return
	 */
	public List<UserBloodSugarTestByTime> getUserBloodSugarTestByTime(Integer type,Date time,String userId,String testTimeId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("time", time);
		param.put("testTimeId", testTimeId);
		switch (type) {
		case 1: //天
			return sqlSession.selectList("getUserBloodSugarTestByDay",param);
		case 2: //周
			param.put("type", "week");
			return sqlSession.selectList("getUserBloodSugarTestByWeekAndMonth",param);
		case 3: //月
			param.put("type", "month");
			return sqlSession.selectList("getUserBloodSugarTestByWeekAndMonth",param);
		default:
			break;
		}
		return null;
	}
	
	
	/**
	 * 按照时间分割类型查询测试记录(平均方式)
	 * @param type 1 日，2 周，3 月
	 * @param time 最后一天的时间
	 * @return
	 */
	public List<UserBloodSugarTestByTime> getUserBloodSugarTestByTimeAvg(Integer type,Date time,String userId,String testTimeId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("time", time);
		param.put("testTimeId", testTimeId);
		switch (type) {
		case 1: //天
			return sqlSession.selectList("getUserBloodSugarTestByDayAvg",param);
		case 2: //周
			param.put("type", "week");
			return sqlSession.selectList("getUserBloodSugarTestByWeekAndMonthAvg",param);
		case 3: //月
			param.put("type", "month");
			return sqlSession.selectList("getUserBloodSugarTestByWeekAndMonthAvg",param);
		default:
			break;
		}
		return null;
	}
	
	
	public List<UserBloodSugarTest> getTestPage(String userId,String periodId,Long startRow,Long endRow){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("periodId", periodId);
		param.put("startRow", startRow);
		param.put("endRow", endRow);
		return sqlSession.selectList("getSugarPage",param);
	}
	
	public long getTestPageCount(String userId,String periodId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		return sqlSession.selectOne("getSugarPageCount",param);
	}

	@Override
	public int addUserBloodSugarTest(UserBloodSugarTest userBloodSugarTest) {
		return sqlSession.insert("addUserBloodSugarTest", userBloodSugarTest);
	}
	@Override
	public List<UserTestTime> selectUserBloodSugartTest(String kpiCode) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("kpiCode", kpiCode);
		return sqlSession.selectList("queryUserBloodSugarTestById",param);
	}
	@Override
	public int addUserResultTest(UserTestResult u) {
		return sqlSession.insert("addUserTestResult", u);
	}
	@Override
	public List<UserTestTime> selectTestTime(String kpiCode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kpiCode", kpiCode);
		return sqlSession.selectList("queryTestTimeByKpi",map);
	}
	
	public void updateUserBloodSugarTest(UserBloodSugarTest test){
		sqlSession.update("updateUserBloodSugarTest",test);
	}
	
	public int addTestResultDet(TestResultDet det){
		return sqlSession.insert("addTestResultDet",det);
	}
	
	public int addTestResult(TestResult result){
		return sqlSession.insert("addTestResult",result);
	}
	
	public int addHeartRateTest(UserHeartRateTest rateTest){
		return sqlSession.insert("addHeartRateTest",rateTest);
	}
	
	/**
	 * 根据心率ID查询心率实体
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月23日下午2:44:11
	 * 修改日期:2015年11月23日下午2:44:11
	 * @param testId
	 * @return
	 */
	public UserHeartRateTest getUserHeartRateTest(String testId){
		UserHeartRateTest heartRate = sqlSession.selectOne("getUserHeartRateTest",testId);
		return heartRate;
	}
	
	/**
	 * 根据测试ID和测试类型查询结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月23日下午3:11:00
	 * 修改日期:2015年11月23日下午3:11:00
	 * @param testId	
	 * @param type 1:血压,2:血糖,3血脂,4心电5心率
	 * @return
	 */
	public TestResult getTestResultInfo(String testId,String type){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("testId", testId);
		map.put("type", type);
		TestResult testResult = sqlSession.selectOne("getTestResult",map);
		List<TestResultDet> resultDets = sqlSession.selectList("getTestResultDet",testResult.getTestResultId().toString());
		testResult.setTestResultDets(resultDets);
		return testResult;
	}
	
	/**
	 * 按周获取血糖数据
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月26日上午10:03:51
	 * 修改日期:2015年11月26日上午10:03:51
	 * @param userId
	 * @param nowdate
	 * @return
	 */
	public List<Map<String, Object>> getBloodSugarByWeek(String userId,String nowdate){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("nowdate", nowdate);
		return sqlSession.selectList("getBloodSugarByWeek",param);
	} 
	
	public List<Map<String, Object>> getNoTestResultRecord(String tableName){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tableName", tableName);
		if(tableName.equalsIgnoreCase("user_hypertension_test")){
			param.put("type", "1");
		}else{
			param.put("type", "2");
		}
		return sqlSession.selectList("getNoTestResultRecord",param);
	}
}
