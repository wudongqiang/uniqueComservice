/**
 *
 */
package com.unique.mb.service;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.mb.po.UserElectrocardioTestDet;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年11月18日上午10:04:49
 * 修改日期:2015年11月18日上午10:04:49
 */
public interface UserElectrocardioTestService {
	/**
	 * 心电添加数据接口
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月20日上午10:22:59
	 * 修改日期:2015年11月20日上午10:22:59
	 * @param userId
	 * @param time
	 * @param periodId
	 * @param inputMode
	 * @param sourceCode
	 * @param deviceCode
	 * @param deviceToken
	 * @param isTwo
	 * @param HR
	 * @param QRS
	 * @param PR
	 * @param QT
	 * @param QTC
	 * @param P_AXIS
	 * @param QRS_AXIS
	 * @param T_AXIS
	 * @param RV5
	 * @param SV1
	 * @param RV5_AND_SV1
	 * @param PWidth
	 * @param RR
	 * @param dets
	 * @param results
	 * @return
	 */
	@HttpMethod
	@Transactional
	public Map<String, Object> addElectrocardioTest(
			String userId,
			String time,
			String inputMode,
			String sourceCode,
			String deviceCode,
			String deviceToken,
			String isTwo,
			 Integer HR, Integer QRS, Integer PR, Integer QT, Integer QTC,
			 Integer P_AXIS,  Integer QRS_AXIS, Integer T_AXIS, Double RV5,
			 Double SV1, Double RV5_AND_SV1, Double PWidth, Integer RR,
			 List<UserElectrocardioTestDet> dets,
			 List<String> results
			);
	
	/**
	 * 根据心率检测ID获取心率详情和结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月23日下午3:22:42
	 * 修改日期:2015年11月23日下午3:22:42
	 * @param testId
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getHeartInfo(@NotNull String testId);
	
	/**
	 * 获取血压检测详情
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月23日下午4:32:23
	 * 修改日期:2015年11月23日下午4:32:23
	 * @param testId
	 * @return
	 */
	public HashMap<String, Object> getUserElectrocardioTestById(String testId);
	
	/**
	 * 获取心率数据分页
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月24日上午11:33:31
	 * 修改日期:2015年11月24日上午11:33:31
	 * @param userId
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public HashMap<String, Object> getHeartRatePage(@NotNull String userId,Long startRow,Long rows);
	
	/**
	 * 按照时间分割类型查询心率
	 * @param type 1 日，2 周，3 月
	 * @param time 最后一天的时间 yyyy-mm-dd
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getHeartRateTestByTime(@NotNull Integer type,
			@NotNull String userId,
			String time,String testTimeId);
	
	/**
	 * 根据检测明细ID绘制图片
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年12月2日下午1:41:26
	 * 修改日期:2015年12月2日下午1:41:26
	 * @param testDetId
	 * @return
	 */
	public BufferedImage getElectrocardioImg(String testDetId);
}
