package com.unique.user.service;

import java.util.List;
import java.util.Map;

import com.unique.user.po.UserSchedule;

public interface UserScheduleService {

	/** 
	 * 添加用户日程
	 * @param userId
	 * @param scheduleTitle
	 * @param scheduleCon
	 * @param beginTime
	 * @return
	 */
	public Map<String, Object> addUserScheduleParams(String userId, String scheduleTitle,
			String scheduleCon, String beginTime);

	/**
	 * 更新用户日程
	 * @param userId
	 * @param scheduleTitle
	 * @param scheduleCon
	 * @param beginTime
	 * @return
	 */
	public Map<String, Object> updateUserScheduleParams(String scheduleId, String scheduleTitle,
			String scheduleCon, String beginTime);
	
	/**
	 * 根据userId 和 日期 查询 用户日程信息
	 * @param userId
	 * @param date 查询开始时间，不填写默认当前日期，也可查询具体日期的日程信息 yyyy-mm-dd
	 * @param type 类型标示日期查询方式：list列表查询，detailed详细查询，默认list
	 * @return
	 */
	public List<UserSchedule> getUserScheduleByUserIdAndDate(String userId, String date, String type);
	
	/**
	 * 根据id查询 日程信息
	 * @param scheduleId
	 * @return
	 */
	public UserSchedule getUsersScheduleById(String scheduleId);

	/**
	 * 删除日程信息（数据假删 ，更改状态 为R）
	 * @param scheduleId
	 * @return
	 */
	public Map<String, Object> deleteByScheduleId(String scheduleId);

	/**
	 * 获取医生 某月的所有的自定义日程的 日期集
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年8月28日 上午9:35:42
	 * @param staffId
	 * @param monthStr yyyy-mm-dd
	 * @return
	 */
	public List<String> getScheduleDates(String staffId, String monthStr);
	
}
