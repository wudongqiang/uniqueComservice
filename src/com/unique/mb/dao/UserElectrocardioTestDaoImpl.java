/**
 *
 */
package com.unique.mb.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.unique.mb.po.UserElectrocardioResult;
import com.unique.mb.po.UserElectrocardioTest;
import com.unique.mb.po.UserElectrocardioTestDet;
import com.unique.mb.po.UserHeartRateTest;
import com.unique.mb.po.UserTestByTime;
import com.unique.mb.po.UserTestTime;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年11月18日上午9:49:23
 * 修改日期:2015年11月18日上午9:49:23
 */
@Repository("userElectrocardioTestDao")
public class UserElectrocardioTestDaoImpl implements UserElectrocardioTestDao{
	@Resource
	private SqlSessionTemplate sqlSession;
	
	/**
	 * 添加心电监测
	 */
	public int addElectrocardioTest(UserElectrocardioTest userElectrocardioTest) {
		return sqlSession.insert("addElectrocardioTest", userElectrocardioTest);
	}
	
	/**
	 * 添加心电明细
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月18日上午9:55:02
	 * 修改日期:2015年11月18日上午9:55:02
	 * @param userElectrocardioTestDet
	 * @return
	 */
	public int addElectrocardioTestDet(UserElectrocardioTestDet userElectrocardioTestDet) {
		return sqlSession.insert("addElectrocardioTestDet", userElectrocardioTestDet);
	}
	
	/**
	 * 添加心电结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月18日上午9:55:55
	 * 修改日期:2015年11月18日上午9:55:55
	 * @param userElectrocardioResult
	 * @return
	 */
	public int addElectrocardioResult(UserElectrocardioResult userElectrocardioResult) {
		return sqlSession.insert("addElectrocardioResult", userElectrocardioResult);
	}
	
	/**
	 * 根据测试ID获取心电监测详情和结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月24日上午10:53:20
	 * 修改日期:2015年11月24日上午10:53:20
	 * @param testId
	 * @return
	 */
	public UserElectrocardioTest getElectrocardioTestById(String testId) {
		UserElectrocardioTest testRecord = sqlSession.selectOne("getElectrocardioTestById", testId);
		List<UserElectrocardioResult> results = sqlSession.selectList("getElectrocardioTestResultById",testId);
		List<UserElectrocardioTestDet> testDets = sqlSession.selectList("getElectrocardioTestDetById",testId);
		testRecord.setEleResults(results);
		testRecord.setTestDets(testDets);
		return testRecord;
	}
	
	
	public List<UserHeartRateTest> getHeartRatePage(String userId,Long startRow,Long endRow){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("startRow", startRow);
		param.put("endRow", endRow);
		return sqlSession.selectList("getHeartRatePage",param);
	}
  
	public long getHeartRatePageCount(String userId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		return sqlSession.selectOne("getHeartRatePageCount",param);
	}
	
	public List<UserTestByTime> getUserHeartRateTestByTime(Integer type,Date time,String userId,String testTimeId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");
		param.put("nowdate", sdf.format(time));
		param.put("type", type);
		
		if(testTimeId!=null){
			UserTestTime testTime = sqlSession.selectOne("getTestTimeById", testTimeId);
			param.put("testTimeId", testTimeId);
			param.put("beginTime", sdf2.format(testTime.getTestBeginTime()));
			param.put("endTime", sdf2.format(testTime.getTestEndTime()));
		}
		switch (type) {
		case 1: //天
			return sqlSession.selectList("getUserHeartRateByTestDay",param);
		case 2: //周
			return sqlSession.selectList("getUserHeartRateByTestWeekAndMonth",param);
		case 3: //月
			return sqlSession.selectList("getUserHeartRateByTestWeekAndMonth",param);
		default:
			break;
		}
		return null;
	}
	
	public UserElectrocardioTestDet getElectrocardioTestDetByDetId(String testDetId){
		return sqlSession.selectOne("getElectrocardioTestDetByDetId",testDetId);
	}
}
