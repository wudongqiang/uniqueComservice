/**
 * 2015年11月24日
 */
package com.unique.reg.service;

import java.util.Map;

import com.unique.reg.po.McAppoint;

/**
 * 约诊服务接口<br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年11月24日 下午2:43:37<br/>
 * @author Administrator
 * @date 2015年11月24日
 * @description 
 */
public interface AppointService {
	/**
	 * 添加约诊  <br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月20日 下午4:04:35<br/>
	 * @param staffId
	 * @param userId
	 * @param appointDate 格式 yyyy-mm-dd hh:mm:ss
	 * @param desc
	 * @param imgUrls
	 * @return
	 */
	public Map<String,Object> appointment(String staffId,String userId,String appointDate,String desc,String[] imgUrls);
	
	/**
	 * 我的约诊<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月24日 下午2:45:06<br/>
	 * @param staffId
	 * @param userId
	 * @param status
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public Map<String,Object> getMcAppoints(String staffId,String userId,String status,Long startRow,Long rows);
	
	/**
	 * 获取约诊详情<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月24日 下午3:15:15<br/>
	 * @param appointId
	 * @return
	 */
	public McAppoint getMcAppointById(String appointId);
}
