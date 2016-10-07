/**
 * 2015年10月8日
 */
package com.unique.mb.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.unique.mb.po.TestResult;
import com.unique.mb.po.TestResultDet;
import com.unique.mb.po.UserBloodSugarTest;
import com.unique.mb.po.UserBloodSugarTestByTime;
import com.unique.mb.po.UserBloodSugarTestDto;
import com.unique.mb.po.UserHeartRateTest;
import com.unique.mb.po.UserTestResult;
import com.unique.mb.po.UserTestTime;

/**
 * 用户血糖测试记录 数据访问接口<br/>
 * 创建人 azq<br/>
 * 创建时间 2015年10月8日 上午9:37:34<br/>
 * @author Administrator
 * @date 2015年10月8日
 * @description 
 */
public interface UserBloodSugarTestDao {
	/**
	 * （血糖） 查询用户检测数据，得到当前时间的数据 
	 * @param userId
	 * @return
	 */
	public List<UserBloodSugarTest> getUserBloodSugarTestDays(String userId);
	
	/**
	 * （血糖） 查询用户检测数据，得到周或月的数据 
	 * @param userId
	 * @param type 区分查询周数据和月数据 weeks:表示周数据  month:表示月数据
	 * @return
	 */
	public List<UserBloodSugarTestDto> getUserBloodSugarTestWeeksAndMonth(String userId,String type);
	
	/**
	 * 按照时间分割类型查询测试记录
	 * @param type 1 日，2 周，3 月
	 * @param time 最后一天的时间
	 * @return
	 */
	public List<UserBloodSugarTestByTime> getUserBloodSugarTestByTime(Integer type,Date time,String userId,String testTimeId);
	
	/**
	 * 按照时间分割类型查询测试记录(平均方式)
	 * @param type 1 日，2 周，3 月
	 * @param time 最后一天的时间
	 * @return
	 */
	public List<UserBloodSugarTestByTime> getUserBloodSugarTestByTimeAvg(Integer type,Date time,String userId,String testTimeId);
	/**
	 * 
	 * 创建人:敖资权
	 * 分页获取血糖历史记录
	 * 创建日期:2015年10月14日上午9:33:23
	 * 修改日期:2015年10月14日上午9:33:23
	 * @param periodId
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public List<UserBloodSugarTest> getTestPage(String userId,String periodId,Long startRow,Long endRow);
	public long getTestPageCount(String userId,String periodId);
	/**
	 * 添加用户血糖测试记录<br/>
	 * 创建人 azq<br/>
	 * 创建时间 2015年10月8日 上午9:42:34<br/>
	 * @param userBloodSugarTest
	 * @return
	 */
	public int addUserBloodSugarTest(UserBloodSugarTest userBloodSugarTest);
	/**
	 * 查询用户血糖记录<br/>
	 * 创建人 azq<br/>
	 * @param userBloodSugarTest
	 * @return
	 */
	public List<UserTestTime> selectUserBloodSugartTest(String kpiCode);
	public List<UserTestTime> selectTestTime(String kpiCode);
	/**
	 * 查询用户测试记录<br/>
	 * 创建人 azq<br/>
	 * @param userBloodSugarTest
	 * @return
	 */
	public int addUserResultTest(UserTestResult u);
	
	public void updateUserBloodSugarTest(UserBloodSugarTest test);
	
	public int addTestResultDet(TestResultDet det);
	
	public int addTestResult(TestResult result);
	
	public int addHeartRateTest(UserHeartRateTest rateTest);
	
	/**
	 * 根据心率ID查询心率实体
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月23日下午2:44:11
	 * 修改日期:2015年11月23日下午2:44:11
	 * @param testId
	 * @return
	 */
	public UserHeartRateTest getUserHeartRateTest(String testId);
	
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
	public TestResult getTestResultInfo(String testId,String type);
	
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
	public List<Map<String, Object>> getBloodSugarByWeek(String userId,String nowdate);
	
	public List<Map<String, Object>> getNoTestResultRecord(String tableName);
}
