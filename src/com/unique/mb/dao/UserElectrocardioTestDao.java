/**
 *
 */
package com.unique.mb.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unique.mb.po.UserElectrocardioResult;
import com.unique.mb.po.UserElectrocardioTest;
import com.unique.mb.po.UserElectrocardioTestDet;
import com.unique.mb.po.UserHeartRateTest;
import com.unique.mb.po.UserTestByTime;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年11月18日上午9:49:23
 * 修改日期:2015年11月18日上午9:49:23
 */
public interface UserElectrocardioTestDao {
	public int addElectrocardioTest(UserElectrocardioTest userElectrocardioTest);
	
	/**
	 * 添加心电明细
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月18日上午9:55:02
	 * 修改日期:2015年11月18日上午9:55:02
	 * @param userElectrocardioTestDet
	 * @return
	 */
	public int addElectrocardioTestDet(UserElectrocardioTestDet userElectrocardioTestDet);
	
	/**
	 * 添加心电结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月18日上午9:55:55
	 * 修改日期:2015年11月18日上午9:55:55
	 * @param userElectrocardioResult
	 * @return
	 */
	public int addElectrocardioResult(UserElectrocardioResult userElectrocardioResult);
	
	/**
	 * 根据测试ID获取心电监测详情和结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月24日上午10:53:20
	 * 修改日期:2015年11月24日上午10:53:20
	 * @param testId
	 * @return
	 */
	public UserElectrocardioTest getElectrocardioTestById(String testId);
	
	public List<UserHeartRateTest> getHeartRatePage(String userId,Long startRow,Long endRow);
  
	public long getHeartRatePageCount(String userId);
	
	public List<UserTestByTime> getUserHeartRateTestByTime(Integer type,Date time,String userId,String testTimeId);
	
	public UserElectrocardioTestDet getElectrocardioTestDetByDetId(String testDetId);
}
