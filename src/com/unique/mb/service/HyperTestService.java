package com.unique.mb.service;

import java.util.HashMap;
import java.util.List;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.annotation.ParamNoNull;
import com.unique.mb.po.UserHypertensionTest;
import com.unique.mb.po.UserTestTime;

public interface HyperTestService {
	//血压预警
	public HashMap<String, Object>  addUserHypertension(String userId,@NotNull String highTension,@NotNull String lowTension,@NotNull String periodId);
	
	/**
	 * 添加测试结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月21日下午6:41:03
	 * 修改日期:2015年10月21日下午6:41:03
	 * @param userId
	 * @param sourceCode
	 * @param highTension
	 * @param lowTension
	 * @param heartRate
	 * @param inputMode
	 * @param deviceCode
	 * @param deviceToken
	 * @param testTime
	 * @param periodId
	 * @param Inputmode
	 * @return
	 */
	public HashMap<String, Object> addTest(
			@ParamNoNull String userId,
			@ParamNoNull String inputMode,
			String sourceCode,
			@ParamNoNull Double highTension,
			@ParamNoNull Double lowTension,
			@ParamNoNull Double heartRate,
			String deviceCode,
			String deviceToken,
			String testTime,
			String periodId,
			String isTwo
			);
	
	public List<UserHypertensionTest> getTestPage(String userId, Long startRow,
			Long rows);
	
	public HashMap<String, Object> getTestPageCount(String userId);

	/**
	 * （高血压,心率） 查询用户检测数据，得到当前时间的数据 
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年8月26日 上午10:54:48
	 * @param userId
	 * @return
	 */
	public HashMap<String, Object> getUserHypertensionTestDays(String userId);
	
	/**
	 * （高血压,心率） 查询用户检测数据，得到周或月的数据 
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年8月26日 上午10:54:56
	 * @param userId
	 * @param type 区分查询周数据和月数据 weeks:表示周数据  month:表示月数据
	 * @return
	 */
	public HashMap<String, Object> getUserHypertensionTestWeeksAndMonth(String userId,String type);
	
	/**
	 * 按照时间分割类型查询测试记录
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月2日上午11:39:47
	 * 修改日期:2015年9月2日上午11:39:47
	 * @param type1 1 日，2 周，3 月
	 * @param time 最后一天的时间 yyyy-mm-dd
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getUserHypertensionTestByTime(@NotNull Integer type,String userId,String time);
	
	/**
	 * 根据时间段KPI获取对应的检测列表
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月20日上午10:40:35
	 * 修改日期:2015年10月20日上午10:40:35
	 * @param type 1 日，2 周，3 月
	 * @param kpiCode XT:血糖，SSY:搜索压，SZY:舒张压
	 * @param userId 用户ID
	 * @param time 开始时间:yyyy-MM-dd
	 * @return
	 */
	public HashMap<String, Object>  getTestByTime(@NotNull Integer type,@NotNull String kpiCode,@NotNull String userId,String time,String testTimeId);
	
	/**
	 * 根据KPI获取对应的展示时间段
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月20日上午10:52:34
	 * 修改日期:2015年10月20日上午10:52:34
	 * @param kpiCode
	 * @return
	 */
	@HttpMethod
	public List<UserTestTime> getTestTimeByKpi(String kpiCode);
	
	/**
	 * 获取血压检测详情
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月23日下午4:32:23
	 * 修改日期:2015年11月23日下午4:32:23
	 * @param testId
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getHyperTensionTestById(String testId);
	
	/**
	 * 预警处理<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月24日 下午3:55:24<br/>
	 * @param testResultDetId
	 * @param doWay
	 * @return
	 */
	public HashMap<String,Object> handlerEarlyWarn(String testResultDetId,String doWay);
	
	/**
	 * 按周获取血压数据
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月26日上午10:03:51
	 * 修改日期:2015年11月26日上午10:03:51
	 * @param userId
	 * @param nowdate 本周任意一天,格式：yyyy-MM-dd,不传默认为本周
	 * @return
	 */
	public HashMap<String, Object> getHypertensionByWeek(String userId,String nowdate);
}
