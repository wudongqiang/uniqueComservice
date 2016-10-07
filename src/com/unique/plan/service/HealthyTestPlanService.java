package com.unique.plan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.plan.po.HealthyTestPlanDetRule;
import com.unique.plan.po.HealthyTestPlanTask;
import com.unique.plan.po.HealthyTestPlanTemplate;
import com.unique.plan.po.HealthyTestPlanTemplateDet;
import com.unique.plan.po.TestItemRuleDet;

/**
 * <br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年10月17日 下午1:02:40<br/>
 * @author Administrator
 * @date 2015年10月17日
 * @description
 */
public interface HealthyTestPlanService {
	/**
	 * 获取该机构下面的所有检测模板
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月12日下午2:36:03
	 * 修改日期:2015年11月12日下午2:36:03
	 * @param orgId
	 * @return
	 */
	public List<HealthyTestPlanTemplate> getTemplateByOrgId( String orgId);
	/**
	 * 根据模板ID查询模板明细
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月12日下午2:47:44
	 * 修改日期:2015年11月12日下午2:47:44
	 * @param templateId
	 * @return
	 */
	public List<HealthyTestPlanTemplateDet> getTemplateDetByTemplateId( String templateId);
	
	/**
	 * 获取检测计划分页
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月16日下午2:54:14
	 * 修改日期:2015年11月16日下午2:54:14
	 * @param userId
	 * @param staffId
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public HashMap<String, Object> getTestPlanByPage(String userId,String staffId,Long startRow,Long rows);
}
