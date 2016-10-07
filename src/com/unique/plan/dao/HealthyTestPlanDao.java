package com.unique.plan.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * <br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年10月17日 下午1:02:40<br/>
 * @author Administrator
 * @date 2015年10月17日
 * @description
 */
public interface HealthyTestPlanDao {

	/**
	 * 获取监测计划任务<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月20日 上午9:25:39<br/>
	 * @param date
	 * @param userId
	 * @return
	 */
	public List<HealthyTestPlanTask> getHealthyTestPlanTaskByTime(String date,String userId);

	public List<HealthyTestPlanDetRule> getTestPlanListByUser(String userId,String kpiCode,String testTimeId);
	
	public List<TestItemRuleDet> getTestRuleListByUser(String testTimeId,String kpiCode);
	
	public String getTestTimeNameById(String id);
	
	public String getKpiCodeByChild(String kpiCode);
	
	public List<HealthyTestPlanTemplate> getTemplateByOrgId(String orgId);
	
	/**
	 * 根据模板ID查询模板明细
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月12日下午2:45:24
	 * 修改日期:2015年11月12日下午2:45:24
	 * @param TemplateId
	 * @return
	 */
	public List<HealthyTestPlanTemplateDet> getTemplateDetByTemplateId(String TemplateId);
	
	/**
	 * 添加健康计划
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月13日下午2:42:05
	 * 修改日期:2015年11月13日下午2:42:05
	 * @param plan
	 * @return
	 */
	public int addHealthyPlan(HealthyPlan plan);
	
	/**
	 * 添加检测计划
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月13日下午2:55:50
	 * 修改日期:2015年11月13日下午2:55:50
	 * @param testPlan
	 * @return
	 */
	public int addHealthyTestPlan(HealthyTestPlan testPlan);
	
	/**
	 * 根据模板明细ID获取检测模板明细
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月13日下午3:10:50
	 * 修改日期:2015年11月13日下午3:10:50
	 * @param templateDetId
	 * @return
	 */
	public HealthyTestPlanTemplateDet getTemplateDetByTemplateDetId(String templateDetId);
	
	/**
	 * 根据ID查询计划模板明细规则
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月16日上午9:56:09
	 * 修改日期:2015年11月16日上午9:56:09
	 * @param id
	 * @return
	 */
	public TestPlanTemplateDetRule getTemplateDetRuleById(String id);
	
	public int addHealthyTestPlanDetRule(HealthyTestPlanDetRule detRule);
	
	public int addHealthyTestPlanDet(HealthyTestPlanDet planDet);
	
	public HealthyTestPlanTemplate getTemplateById(String templateId);
	
	public int addHealthyTestPlanTask(HealthyTestPlanTask task);

	public int stopTestPlanTask(String planId);
	
	public int stopTestPlan(String opUserId,String planId);
	
	public List<HealthyTestPlan> getTestPlanByPage(String userId,String staffId,Long startRow,Long endRow);
	
	public long getTestPlanByPageCount(String userId,String staffId);
}
