/**
 *
 */
package com.unique.mb.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unique.mb.po.UserHeartRateTest;
import com.unique.mb.po.UserWeightTest;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年11月18日上午9:49:23
 * 修改日期:2015年11月18日上午9:49:23
 */
public interface UserWeightTestDao {
	public int addUserWeightTest(UserWeightTest userWeightTest);
	
	public List<UserWeightTest> getWeightByPage(String userId,Long startRow,Long endRow);
  
	public long getWeightByPageCount(String userId);
	
	public List<Map<String, Object>> getWeightByWeek(String userId,String nowdate,int type);
	
	/**
	 * 判断用户是否有服务
	 * @param userId
	 * @param scode
	 * @return
	 */
	public int checkService(String userId,String scode);
	
	/**
	 * 获取末次月经时间和末次BMI
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getEndMenstruation(String userId);
	
	/**
	 * 获取孕产周的BMI报表
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getBmiReportByPregnancy(String userId,Date endTime);
}
