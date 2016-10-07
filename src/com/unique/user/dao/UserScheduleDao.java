package com.unique.user.dao;

import java.util.List;

import com.unique.user.po.UserSchedule;

public interface UserScheduleDao {

	/**
	 * 添加用户日程
	 * @param userSchedule
	 */
	public int addUserSchedule(UserSchedule userSchedule);

	/**
	 * 根据userId 和 日期 查询 用户日程信息
	 * @param userId
	 * @param date
	 * @param type 类型标示日期查询方式：list列表查询，detailed详细查询，默认list
	 * @return
	 */
	public List<UserSchedule> getUserScheduleByUserIdAndDate(String userId,String date,String type);
	
	/**
	 * 根据id查询 日程信息
	 * @param scheduleId
	 * @return
	 */
	public UserSchedule getUsersScheduleById(String scheduleId);

	/**
	 * 更新用户日程
	 * @param userSchedule
	 * @return
	 */
	public int updateUserScheduleParams(UserSchedule userSchedule);

	/**
	 * 删除日程信息（数据假删 ，更改状态 为R）
	 * @param scheduleId
	 * @return
	 */
	public int deleteByScheduleId(String scheduleId);

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
