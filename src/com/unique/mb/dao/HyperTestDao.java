package com.unique.mb.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.unique.mb.po.EqupType;
import com.unique.mb.po.UserHypertensionTest;
import com.unique.mb.po.UserHypertensionTestByTime;
import com.unique.mb.po.UserHypertensionTestDto;
import com.unique.mb.po.UserTestByTime;
import com.unique.mb.po.UserTestResult;
import com.unique.mb.po.UserTestTime;

public interface HyperTestDao {
	public List<UserHypertensionTest> getTestPage(String userId,Long startRow,Long endRow);
	
	public long getTestPageCount(String userId);
	
	public int addTest(UserHypertensionTest test);
	
	
	/**
	 * （高血压,心率） 查询用户检测数据，得到当前时间的数据 
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年8月26日 上午10:54:48
	 * @param userId
	 * @return
	 */
	public List<UserHypertensionTest> getUserHypertensionTestDays(String userId);
	
	/**
	 * （高血压,心率） 查询用户检测数据，得到周或月的数据 
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年8月26日 上午10:54:56
	 * @param userId
	 * @param type 区分查询周数据和月数据 weeks:表示周数据  month:表示月数据
	 * @return
	 */
	public List<UserHypertensionTestDto> getUserHypertensionTestWeeksAndMonth(String userId,String type);
	
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
	public List<UserHypertensionTestByTime> getUserHypertensionTestByTime(Integer type,Date time,String userId);
	
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
	public List<UserHypertensionTestByTime> getUserHypertensionTestByTimeAvg(Integer type,Date time,String userId);
	
	/**
	 * 添加测试结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月21日上午9:42:31
	 * 修改日期:2015年9月21日上午9:42:31
	 * @param result
	 * @return
	 */
	public int addTestResult(UserTestResult result);
	
	/**
	 * 获取设备EQID
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月21日上午9:48:52
	 * 修改日期:2015年9月21日上午9:48:52
	 * @param tableName
	 * @return
	 */
	public EqupType getEqIdByTableName(String tableName);
	
	public List<UserTestByTime> getUserTestXLXYByTime(Integer type,Date time,String userId,String kpiCode,String testTimeId);
	
	public List<UserTestByTime> getUserTestXTByTime(Integer type,Date time,String userId,String kpiCode,String testTimeId,List<UserTestTime> testTime);
	
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
	public String getTestTimeIdByTime(Date date,String kpiCode,String userId);
	
	
	public void updateUserHypertensionTestById(UserHypertensionTest test);
	
	/**
	 * 根据ID获取血压测量结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月23日下午2:50:25
	 * 修改日期:2015年11月23日下午2:50:25
	 * @param testId
	 * @return
	 */
	public UserHypertensionTest getHypertensionInfo(String testId);

	/**
	 * 处理预警<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月24日 下午3:58:11<br/>
	 * @param testResultDetId
	 * @param doWay
	 */
	public int handlerEarlyWarn(String testResultDetId, String doWay);
	
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
	public List<Map<String, Object>> getHypertensionByWeek(String userId,String nowdate);
}
