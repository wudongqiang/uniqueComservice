/**
 * 2015年11月24日
 */
package com.unique.reg.dao;

import java.util.List;

import com.unique.reg.po.McAppoint;

/**
 * 约诊服务接口<br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年11月24日 下午2:43:37<br/>
 * @author Administrator
 * @date 2015年11月24日
 * @description 
 */
public interface AppointDao {
	/**
	 * 添加约诊<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月24日 上午10:48:07<br/>
	 * @param appoint
	 * @return
	 */
	public int addMcAppoint(McAppoint appoint);
	
	/**
	 * 我的约诊 分页<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月24日 下午2:45:06<br/>
	 * @param staffId
	 * @param userId
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public List<McAppoint> getMcAppoints(String staffId,String userId,String status,Long startRow,Long rows);
	
	/**
	 * 我的约诊总数<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月24日 下午2:45:06<br/>
	 * @param staffId
	 * @param userId
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public long getMcAppointsCount(String staffId,String userId,String status);
	
	/**
	 * 获取约诊详情<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月24日 下午3:15:15<br/>
	 * @param appointId
	 * @return
	 */
	public McAppoint getMcAppointById(String appointId);

	/**
	 * 检查同一时间统一人不能重复申请
	 * @param staffId
	 * @param userId
	 * @param appointDate
	 * @return
	 */
	public long checkIsUniqueAppoint(String staffId, String userId,
			String appointDate);
}
