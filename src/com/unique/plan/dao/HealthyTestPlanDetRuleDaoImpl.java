package com.unique.plan.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.unique.plan.po.HealthyTestPlanDetRule;
import com.unique.plan.po.TestItemRuleDet;

/**
 * 
 * 健康检查计划业务<br/>
 * 创建人azq<br/>
 * 创建时间 2015年10月17日 下午1:05:54<br/>
 * @author Administrator
 * @date 2015年10月17日
 * @description
 */
@Repository("healthyTestPlanDetRuleDao")
public class HealthyTestPlanDetRuleDaoImpl implements HealthyTestPlanDetRuleDao{
	@Resource
	private SqlSessionTemplate sqlSession;
	/**
	 * 查询用户预警
	 * 创建人:敖资权
	 * 修改人:敖资权
	 * 创建日期:2015年10月17日下午3:28:31
	 * 修改日期:2015年10月17日下午3:28:31
	 * @return
	 */
	public List<HealthyTestPlanDetRule> selectUser(String userId,String bloodglucose,String periodId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bloodglucose", bloodglucose);
		param.put("userId", userId);
		param.put("testTimeId", periodId);
		return sqlSession.selectList("selectLevel", param);
	}
	public List<HealthyTestPlanDetRule> selectHypertensionUser(String userId,String highTension,String lowTension,String periodId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lowTension", lowTension);
		param.put("highTension", highTension);
		param.put("userId", userId);
		param.put("testTimeId", periodId);
		return sqlSession.selectList("selectHypertensionLevel", param);
	}
	/**
	 * 查询用户预警（通用版本没有USERID）
	 * 创建人:敖资权
	 * 修改人:敖资权
	 * 创建日期:2015年10月17日下午3:28:51
	 * 修改日期:2015年10月17日下午3:28:51
	 * @return
	 */
	public List<TestItemRuleDet> selectCommon(String bloodglucose,String periodId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bloodglucose", bloodglucose);
		param.put("testTimeId", periodId);
		return sqlSession.selectList("selectCommonLevel", param);
	}
	public List<TestItemRuleDet> selectHypertensionCommon(String highTension,String lowTension,String periodId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lowTension", lowTension);
		param.put("highTension", highTension);
		param.put("testTimeId", periodId);
		return sqlSession.selectList("selectHypertensionCommonLevel", param);
	}
}
