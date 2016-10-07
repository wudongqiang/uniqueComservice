/**
 *
 */
package com.unique.mb.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.unique.mb.po.UserBloodLipidTest;
import com.unique.mb.po.UserBloodSugarTest;
import com.unique.mb.po.UserTestByTime;
import com.unique.mb.po.UserTestByTimeBloodLipid;
import com.unique.mb.po.UserTestTime;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年11月18日下午1:35:30
 * 修改日期:2015年11月18日下午1:35:30
 */
@Repository("userBloodLipidTestDao")
public class UserBloodLipidTestDaoImpl implements UserBloodLipidTestDao{
	@Resource
	private SqlSessionTemplate sqlSession;
	
	public int addUserBloodLipidTest(UserBloodLipidTest test){
		return sqlSession.insert("addUserBloodLipidTest",test);
	}
	
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
	public List<UserBloodLipidTest> getBloodLipidByPage(String userId,Long startRow,Long endRow){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("startRow", startRow);
		param.put("endRow", endRow);
		return sqlSession.selectList("getBloodLipidByPage",param);
	}
	
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
	public long getBloodLipidByPageCount(String userId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		return sqlSession.selectOne("getBloodLipidByPageCount",param);
	}
	
	
	public List<UserTestByTimeBloodLipid> getBloodLipidTestByTime(Integer type,Date time,String userId,
			String testTimeId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");
		param.put("nowdate", sdf.format(time));
		param.put("type", type);
		
		if(testTimeId!=null){
			UserTestTime testTime = sqlSession.selectOne("getTestTimeById", testTimeId);
			param.put("testTimeId", testTimeId);
			param.put("beginTime", sdf2.format(testTime.getTestBeginTime()));
			param.put("endTime", sdf2.format(testTime.getTestEndTime()));
		}
		switch (type) {
		case 1: //天
//			return sqlSession.selectList("getUserHeartRateByTestDay",param);
			break;
		case 2: //周
			return sqlSession.selectList("getBloodLipidByTestWeekAndMonth",param);
		case 3: //月
			return sqlSession.selectList("getBloodLipidByTestWeekAndMonth",param);
		default:
			break;
		}
		return null;
	}
}
