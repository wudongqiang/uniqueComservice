/**
 *
 */
package com.unique.mb.service;

import java.util.HashMap;
import java.util.List;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.mb.po.UserWeightTest;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年12月1日下午1:44:40
 * 修改日期:2015年12月1日下午1:44:40
 */
public interface UserWeightTestService {
	/**
	 * 分页获取体重数据
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年12月3日下午2:27:16
	 * 修改日期:2015年12月3日下午2:27:16
	 * @param userId
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public HashMap<String, Object> getWeightTestPage( String userId,Long startRow,Long rows);
	/**
	 * 按周获取体重数据
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年12月3日下午2:33:44
	 * 修改日期:2015年12月3日下午2:33:44
	 * @param userId
	 * @param nowdate
	 * @return
	 */
	public HashMap<String, Object> getWeightTestByWeek(String userId,String nowdate,Integer type);
	
	/**
	 * 判断用户是否有服务
	 * @param userId
	 * @param scode
	 * @return
	 */
	public Integer checkService(String userId,String scode);
}
