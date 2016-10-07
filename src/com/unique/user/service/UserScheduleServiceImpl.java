package com.unique.user.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;
import com.unique.alipay.util.UtilDate;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.annotation.ParamNoNull;
import com.unique.core.annotation.ParamRegex;
import com.unique.core.util.RegexUtil;
import com.unique.user.dao.UserScheduleDao;
import com.unique.user.po.UserSchedule;


@Service("userScheduleService")
public class UserScheduleServiceImpl implements UserScheduleService {

	private Logger logger = Logger.getLogger("comservice");
	
	@Resource(name="userScheduleDao")
	private UserScheduleDao userScheduleDao;
	
	
	@HttpMethod
	@Override
	@Transactional
	public Map<String,Object> addUserScheduleParams(@NotNull String userId,
			@ParamRegex(regex=RegexUtil.TITLE)
			@ParamNoNull String scheduleTitle,
			@ParamRegex(regex=RegexUtil.CONTENT)
			String scheduleCon,
			@ParamNoNull String beginTime){
		
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			UserSchedule userSchedule = new UserSchedule();
			userSchedule.setUserId(BigDecimal.valueOf(Long.parseLong(userId)));
			userSchedule.setBeginTime(UtilDate.getStrToDate(beginTime, UtilDate.simple));
			userSchedule.setScheduleTitle(scheduleTitle);
			userSchedule.setScheduleCon(scheduleCon);
			
			int ret = userScheduleDao.addUserSchedule(userSchedule);
			if(ret>0){
				result.put("retCode", 0);
				result.put("retMessage", "保存成功");
			}else{
				result.put("retCode", 0);
				result.put("retMessage", "保存失败，请重试！");
			}
		} catch (Exception e) {
			logger.error("保存数据失败", e);
			result.put("retCode", 2);
			result.put("retMessage", "未知异常！");
		}
		return result;
	}
	
	@HttpMethod
	@Transactional
	@Override
	public Map<String, Object> updateUserScheduleParams(@NotNull String scheduleId,
			@ParamNoNull String scheduleTitle, String scheduleCon, @ParamNoNull String beginTime) {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			UserSchedule userSchedule = this.getUsersScheduleById(scheduleId);
			userSchedule.setScheduleTitle(scheduleTitle);
			userSchedule.setScheduleCon(scheduleCon);
			userSchedule.setBeginTime(UtilDate.getStrToDate(beginTime, UtilDate.simple));
			int ret = userScheduleDao.updateUserScheduleParams(userSchedule);
			if(ret>0){
				result.put("retCode", 0);
				result.put("retMessage", "修改成功");
			}else{
				result.put("retCode", 0);
				result.put("retMessage", "修改失败，请重试！");
			}
		} catch (Exception e) {
			logger.error("修改数据失败", e);
			result.put("retCode", 2);
			result.put("retMessage", "未知异常！");
		}
		return result;
	}
	
	@HttpMethod
	@Override
	public List<UserSchedule> getUserScheduleByUserIdAndDate(@NotNull String userId,String date, String type){
		return userScheduleDao.getUserScheduleByUserIdAndDate(userId,date,type);
	}

	@HttpMethod
	@Override
	public UserSchedule getUsersScheduleById(@NotNull String scheduleId) {
		return userScheduleDao.getUsersScheduleById(scheduleId);
	}
	
	
	@HttpMethod
	@Transactional
	@Override
	public Map<String,Object> deleteByScheduleId(@NotNull String scheduleId){
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			int ret = userScheduleDao.deleteByScheduleId(scheduleId);
			if(ret>0){
				result.put("retCode", 0);
				result.put("retMessage", "删除成功");
			}else{
				result.put("retCode", 0);
				result.put("retMessage", "删除失败，请重试！");
			}
		} catch (Exception e) {
			logger.error("修改数据失败", e);
			result.put("retCode", 2);
			result.put("retMessage", "未知异常！");
		}
		return result;
	}

	@Override
	public List<String> getScheduleDates(String staffId, String monthStr) {
		return userScheduleDao.getScheduleDates(staffId, monthStr);
	}
}
