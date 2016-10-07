/**
 *
 */
package com.unique.mb.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.unique.mb.po.UserWeightTest;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年11月18日上午9:49:23
 * 修改日期:2015年11月18日上午9:49:23
 */
@Repository("userWeightTestDao")
public class UserWeightTestDaoImpl implements UserWeightTestDao{
	@Resource
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int addUserWeightTest(UserWeightTest userWeightTest) {
		return sqlSession.insert("addUserWeightTest", userWeightTest);
	}
	
	public List<UserWeightTest> getWeightByPage(String userId,Long startRow,Long endRow){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("startRow", startRow);
		param.put("endRow", endRow);
		return sqlSession.selectList("getWeightByPage",param);
	}
  
	public long getWeightByPageCount(String userId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		return sqlSession.selectOne("getWeightByPageCount",param);
	}
	
	public List<Map<String, Object>> getWeightByWeek(String userId,String nowdate,int type){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("nowdate", nowdate);
		param.put("type", type);
		return sqlSession.selectList("getWeightByWeek",param);
	} 
	
	/**
	 * 判断用户是否有服务
	 * @param userId
	 * @param scode
	 * @return
	 */
	public int checkService(String userId,String scode){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("scode", scode);
		return sqlSession.selectOne("checkService",param);
	}
	
	/**
	 * 获取末次月经时间和末次BMI
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getEndMenstruation(String userId){
		return sqlSession.selectOne("getEndMenstruation",userId);
	}
	
	/**
	 * 获取孕产周的BMI报表
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getBmiReportByPregnancy(String userId,Date endTime){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("endTime", endTime);
		return sqlSession.selectList("getBmiReportByPregnancy",param);
	}
	
}
