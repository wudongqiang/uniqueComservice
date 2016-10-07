package com.unique.plan.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.unique.core.util.StringUtil;
import com.unique.plan.po.HealthyPlan;
import com.unique.plan.po.HealthyTestPlan;
import com.unique.plan.po.HealthyTestPlanDet;
import com.unique.plan.po.HealthyTestPlanDetRule;
import com.unique.plan.po.HealthyTestPlanTask;
import com.unique.plan.po.HealthyTestPlanTemplate;
import com.unique.plan.po.HealthyTestPlanTemplateDet;
import com.unique.plan.po.TestItemRuleDet;
import com.unique.plan.po.TestPlanTemplateDetRule;

/**
 * 健康检查计划明细业务<br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年10月17日 下午1:07:08<br/>
 * @author Administrator
 * @date 2015年10月17日
 * @description
 */
@Repository("healthyTestPlanDao")
public class HealthyTestPlanDaoImpl implements HealthyTestPlanDao{
	@Resource
	private SqlSessionTemplate sqlSession;

	@Override
	public List<HealthyTestPlanTask> getHealthyTestPlanTaskByTime(String date,String userId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("date", date);
		params.put("userId", userId);
		return sqlSession.selectList("getHealthyTestPlanTaskByTime", params);
	}

	public List<HealthyTestPlanDetRule> getTestPlanListByUser(String userId,String kpiCode,String testTimeId){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("kpiCode", kpiCode);
		params.put("testTimeId", testTimeId);
		return sqlSession.selectList("getTestPlanListByUser",params);
	}
	
	public List<TestItemRuleDet> getTestRuleListByUser(String testTimeId,String kpiCode){
		Map<String,Object> params = new HashMap<String,Object>();
		String testName = null;
		if(!StringUtil.isEmpty(testTimeId)){
			testName = getTestTimeNameById(testTimeId);
		}
		params.put("kpiCode", kpiCode);
		params.put("testTimeName", testName);
		return sqlSession.selectList("getTestRuleListByUser",params);
	}
	
	public String getTestTimeNameById(String id){
		return sqlSession.selectOne("getTestTimeNameById",id);
	}
	
	public String getKpiCodeByChild(String kpiCode){
		return sqlSession.selectOne("getKpiCodeByChild",kpiCode);
	}
	
	/**
	 * 查询该医院下面的所有模板
	 */
	public List<HealthyTestPlanTemplate> getTemplateByOrgId(String orgId){
		return sqlSession.selectList("getTemplateByOrgId",orgId);
	}
	
	/**
	 * 根据模板ID查询模板明细
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月12日下午2:45:24
	 * 修改日期:2015年11月12日下午2:45:24
	 * @param TemplateId
	 * @return
	 */
	public List<HealthyTestPlanTemplateDet> getTemplateDetByTemplateId(String TemplateId){
		List<HealthyTestPlanTemplateDet> dets = sqlSession.selectList("getTemplateDetByTemplateId",TemplateId);
		for(HealthyTestPlanTemplateDet det : dets){
			List<TestPlanTemplateDetRule> rules = sqlSession.selectList("getTestPlanTemplateByDetId",det.getTempleteDetId().toString());
			det.setTestPlanTemplateDetRules(rules);
		}
		return dets;
	}
	
	/**
	 * 根据模板明细ID获取检测模板明细
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月13日下午3:10:50
	 * 修改日期:2015年11月13日下午3:10:50
	 * @param templateDetId
	 * @return
	 */
	public HealthyTestPlanTemplateDet getTemplateDetByTemplateDetId(String templateDetId){
		return sqlSession.selectOne("getTemplateDetByTemplateDetId",templateDetId);
	}
	
	/**
	 * 添加健康计划
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月13日下午2:42:05
	 * 修改日期:2015年11月13日下午2:42:05
	 * @param plan
	 * @return
	 */
	public int addHealthyPlan(HealthyPlan plan){
		return sqlSession.insert("addHealthyPlan",plan);
	}
	
	/**
	 * 添加检测计划
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月13日下午2:55:50
	 * 修改日期:2015年11月13日下午2:55:50
	 * @param testPlan
	 * @return
	 */
	public int addHealthyTestPlan(HealthyTestPlan testPlan){
		return sqlSession.insert("addHealthyTestPlan",testPlan);
	}
	
	/**
	 * 根据ID查询计划模板明细规则
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月16日上午9:56:09
	 * 修改日期:2015年11月16日上午9:56:09
	 * @param id
	 * @return
	 */
	public TestPlanTemplateDetRule getTemplateDetRuleById(String id){
		return sqlSession.selectOne("getTemplateDetRuleById",id);
	}
	
	
	public int addHealthyTestPlanDetRule(HealthyTestPlanDetRule detRule){
		return sqlSession.insert("addHealthyTestPlanDetRule",detRule);
	}
	
	public int addHealthyTestPlanDet(HealthyTestPlanDet planDet){
		return sqlSession.insert("addHealthyTestPlanDet",planDet);
	}
	
	public HealthyTestPlanTemplate getTemplateById(String templateId){
		return sqlSession.selectOne("getTemplateById",templateId);
	}
	
	public int addHealthyTestPlanTask(HealthyTestPlanTask task){
		return sqlSession.insert("addHealthyTestPlanTask",task);
	}
	
	public int stopTestPlanTask(String planId){
		return sqlSession.update("stopTestPlanTask",planId);
	}
	
	public int stopTestPlan(String opUserId,String planId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("opId", opUserId);
		param.put("panId", planId);
		return sqlSession.update("stopTestPlan",param);
	}
	
	public List<HealthyTestPlan> getTestPlanByPage(String userId,String staffId,Long startRow,Long endRow){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("staffId", staffId);
		param.put("startRow", startRow);
		param.put("endRow", endRow);
		return sqlSession.selectList("getTestPlanByPage",param);
	}
	
	public long getTestPlanByPageCount(String userId,String staffId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("staffId", staffId);
		return sqlSession.selectOne("getTestPlanByPageCount",param);
	}
}
