/**
 *
 */
package com.unique.mb.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年11月18日下午1:35:30
 * 修改日期:2015年11月18日下午1:35:30
 */
public interface UserBloodLipidTestService {
	/**
	 * 添加血脂数据
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月18日下午3:22:55
	 * 修改日期:2015年11月18日下午3:22:55
	 * @param userId
	 * @param time
	 * @param periodId
	 * @param inputMode
	 * @param sourceCode
	 * @param deviceCode
	 * @param deviceToken
	 * @param isTwo
	 * @param TG
	 * @param TC
	 * @param HDL_C
	 * @param LDL_C
	 * @return
	 */
	@HttpMethod
	@Transactional
	public Map<String, Object> addElectrocardioLipidTest(
			@NotNull String userId,
			String time,
			@NotNull String inputMode,
			String sourceCode,
			String deviceCode,
			String deviceToken,
			String isTwo,
			@NotNull Double TG,
			@NotNull Double TC,
			@NotNull Double HDL_C,
			@NotNull Double LDL_C
			);
	
	/**
	 * 获取血脂分页
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月18日下午4:22:16
	 * 修改日期:2015年11月18日下午4:22:16
	 * @param userId
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public HashMap<String, Object> getBloodLipidByPage(String userId,Long startRow,Long rows);
	
	/**
	 * 按照时间分割类型查询血脂
	 * @param type 1 日，2 周，3 月
	 * @param time 最后一天的时间 yyyy-mm-dd
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getBloodLipidTestByTime(@NotNull Integer type,
			@NotNull String userId,
			String time,String testTimeId);
}
