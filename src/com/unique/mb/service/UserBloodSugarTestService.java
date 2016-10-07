/**
 * 2015年10月8日
 */
package com.unique.mb.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.mb.po.TestResult;
import com.unique.mb.po.TestResultMain;
import com.unique.mb.po.UserBloodSugarTest;
import com.unique.mb.po.UserBloodSugarTestByTime;
import com.unique.mb.po.UserTestTime;

/**
 * 用户血糖测试记录 业务接口<br/>
 * 创建人 azq<br/>
 * @date 2015年10月8日
 * @description 
 */
public interface UserBloodSugarTestService {
	/**
	 * （血糖） 查询用户检测数据，得到当前时间的数据 
	 * <br/>创建人azq
	 * <br/>创建时间 2015年8月26日 上午10:54:48
	 * @param userId
	 * @return
	 */
	public List<UserBloodSugarTest> getUserBloodSugarTestDays(String userId);
	
	/**
	 * （血糖） 查询用户检测数据，得到周或月的数据 
	 * <br/>创建人 azq
	 * <br/>创建时间 2015年8月26日 上午10:54:56
	 * @param userId
	 * @param type 区分查询周数据和月数据 weeks:表示周数据  month:表示月数据
	 * @return
	 */
	public HashMap<String, Object> getUserBloodSugarTestWeeksAndMonth(String userId,String type);
	
	/**
	 * 按照时间分割类型查询测试记录
	 * @param type1 1 日，2 周，3 月
	 * @param time 最后一天的时间 yyyy-mm-dd
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getUserBloodSugarTestByTime(String periodId,@NotNull Integer type,String userId,String time);
	/**
	 * 分页
	 */
	public List<UserBloodSugarTest> getTestPage(String userId,String periodId, Long startRow,
			Long rows);
	
	public HashMap<String, Object> getTestPageCount(String userId,String periodId);
	/**
	 * 血糖数据上传
	 */
	public Map<String, Object> addUserBloodSugar(String userid, String time, String periodId, 
			String bloodglucose, String Inputmode,String sourceId,
			String deviceCode,String isTwo);
	/**
	 * 血糖查询
	 */
	public List<UserTestTime> queryUserBloodSugartById(String kpiCode);
	/**
	 * 用户记录上传
	 */
	/**
	 * 血糖数据上传
	 */
//	public Map<String, Object> addUserResultTest(@NotNull String time,@NotNull String periodId,@NotNull String bloodglucose,@NotNull String Inputmode);
	
	/**
	 * 计算测量结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月21日下午3:26:40
	 * 修改日期:2015年10月21日下午3:26:40
	 * @param userId
	 * @param firstValue
	 * @param firstKpiCode
	 * @param secondValue
	 * @param secondKpiCode
	 * @param testTimeId
	 * @param type 测量类型（1:血压,2:血糖,3血脂,4心电）
	 * @return
	 */
	public TestResultMain getResult(String userId,
			String firstValue,
			String firstKpiCode,//父
			String secondValue,
			String secondKpiCode,
			String pkpiCode,
			String testTimeId,
			String Inputmode,
			String isTwo,
			BigDecimal testId,
			BigDecimal testResultId);
	
	/**
	 * 按周获取血糖数据
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月26日上午10:03:51
	 * 修改日期:2015年11月26日上午10:03:51
	 * @param userId
	 * @param nowdate 本周任意一天,格式：yyyy-MM-dd,不传默认为本周
	 * @return
	 */
	public HashMap<String, Object> getBloodSugarByWeek(String userId,String nowdate);
	
	public void subHyperResult(Map<String, Object> map);
	public void subXTResult(Map<String, Object> map);
}
