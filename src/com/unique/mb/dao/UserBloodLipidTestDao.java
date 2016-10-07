/**
 *
 */
package com.unique.mb.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unique.mb.po.UserBloodLipidTest;
import com.unique.mb.po.UserTestByTime;
import com.unique.mb.po.UserTestByTimeBloodLipid;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年11月18日下午1:35:30
 * 修改日期:2015年11月18日下午1:35:30
 */
public interface UserBloodLipidTestDao {
	public int addUserBloodLipidTest(UserBloodLipidTest test);
	
	/**
	 * 获取血脂分页
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月18日下午4:17:13
	 * 修改日期:2015年11月18日下午4:17:13
	 * @param userId
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public List<UserBloodLipidTest> getBloodLipidByPage(String userId,Long startRow,Long endRow);
	
	/**
	 * 获取血脂分页(总行数)
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月18日下午4:17:13
	 * 修改日期:2015年11月18日下午4:17:13
	 * @param userId
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public long getBloodLipidByPageCount(String userId);
	
	public List<UserTestByTimeBloodLipid> getBloodLipidTestByTime(Integer type,Date time,String userId,
			String testTimeId);
}
