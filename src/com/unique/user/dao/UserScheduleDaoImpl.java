package com.unique.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.unique.user.po.UserSchedule;

/**
 * 用户管理持久层
 * @author Angel
 *
 */
@Repository("userScheduleDao")
public class UserScheduleDaoImpl implements UserScheduleDao{
	
	@Resource
	private SqlSessionTemplate sqlSession;

	@Override
	public int addUserSchedule(UserSchedule userSchedule) {
		return sqlSession.insert("addUserSchedule",userSchedule);
	}

	@Override
	public List<UserSchedule> getUserScheduleByUserIdAndDate(String userId,String date, String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		if("list".equals(type)){
			param.put("month", date.substring(0, date.lastIndexOf("-")));
//			param.put("date", date);
		}else{
			param.put("searchDate", date);
		}
		return sqlSession.selectList("getUserScheduleByUserIdAndDate", param);
	}

	@Override
	public UserSchedule getUsersScheduleById(String scheduleId) {
		return sqlSession.selectOne("getUsersScheduleById", scheduleId);
	}

	@Override
	public int updateUserScheduleParams(UserSchedule userSchedule) {
		return sqlSession.update("updateUserScheduleParams", userSchedule);
	}

	@Override
	public int deleteByScheduleId(String scheduleId) {
		return sqlSession.update("deleteByScheduleId", scheduleId);
	}

	@Override
	public List<String> getScheduleDates(String staffId, String monthStr) {
		Map<String, Object> param = new HashMap<String, Object>(2);
		param.put("staffId", staffId);
		param.put("monthStr", monthStr);
		return sqlSession.selectList("getScheduleDates", param);
	}
	 
}
