/**
 *
 */
package com.unique.plan.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.CollectionClass;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.util.StringUtil;
import com.unique.org.dao.StaffDao;
import com.unique.plan.dao.HealthyTestPlanDao;
import com.unique.plan.po.HealthyPlan;
import com.unique.plan.po.HealthyTestPlan;
import com.unique.plan.po.HealthyTestPlanDet;
import com.unique.plan.po.HealthyTestPlanDetRule;
import com.unique.plan.po.HealthyTestPlanTask;
import com.unique.plan.po.HealthyTestPlanTemplate;
import com.unique.plan.po.HealthyTestPlanTemplateDet;
import com.unique.plan.po.PlanDetSubmit;
import com.unique.plan.po.PlanSubmit;
import com.unique.plan.po.TestPlanTemplateDetRule;
import com.unique.reg.po.Staff;
import com.unique.survey.dao.SurveyDao;
import com.unique.survey.po.SurveyKpi;
import com.unique.user.dao.UserDao;
import com.unique.user.po.AmsUser;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年11月12日下午2:34:51
 * 修改日期:2015年11月12日下午2:34:51
 */
@Service("healthyTestPlanService")
public class HealthyTestPlanServiceImpl implements HealthyTestPlanService {
	@Resource
	private HealthyTestPlanDao healthyTestPlanDao;
	@Resource
	private UserDao userDao;
	@Resource
	private StaffDao staffDao;
	@Resource
	private SurveyDao surveryDao;
	/**
	 * 获取该机构下面的所有检测模板
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月12日下午2:36:03
	 * 修改日期:2015年11月12日下午2:36:03
	 * @param orgId
	 * @return
	 */
	@HttpMethod
	public List<HealthyTestPlanTemplate> getTemplateByOrgId(@NotNull String orgId){
		return healthyTestPlanDao.getTemplateByOrgId(orgId);
	}
	
	/**
	 * 根据模板ID查询模板明细
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月12日下午2:47:44
	 * 修改日期:2015年11月12日下午2:47:44
	 * @param templateId
	 * @return
	 */
	@HttpMethod
	public List<HealthyTestPlanTemplateDet> getTemplateDetByTemplateId(@NotNull String templateId){
		return healthyTestPlanDao.getTemplateDetByTemplateId(templateId);
	}
	
	/**
	 * 提交制定的检测计划
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月13日下午2:17:27
	 * 修改日期:2015年11月13日下午2:17:27
	 * @param userId 患者ID
	 * @param staffId 医生ID
	 * @param planInfo 计划对象
	 * @param planDetInfos 计划明细集合
	 * @return
	 */
	@HttpMethod
	@Transactional
	public HashMap<String,Object> subTestPlan(
			@NotNull String userId,
			@NotNull String staffId,
			@NotNull String operatorUserId,
			@NotNull PlanSubmit planInfo,
			@NotNull @CollectionClass(PlanDetSubmit.class) List<PlanDetSubmit> planDetInfos){
		AmsUser user= userDao.getUserById(userId);
		Staff staff = staffDao.getStaffById(staffId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		HashMap<String,Object> result = new HashMap<String,Object>();
		
		HealthyPlan plan = new HealthyPlan();
		
		/************************* 添加健康计划 *******************************/
		if(StringUtil.isEmpty(planInfo.getBeginTime())){
			result.put("retCode", 3);
			result.put("retMessage", "缺少参数:BeginTime");
			return result;
		}
		if(StringUtil.isEmpty(planInfo.getEndTime())){
			result.put("retCode", 3);
			result.put("retMessage", "缺少参数:EndTime");
			return result;
		}
		try {
			plan.setBeginTime(sdf.parse(planInfo.getBeginTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		plan.setCreateTime(now);
		plan.setDepId(staff.getDepId());
		try {
			plan.setBeginTime(sdf.parse(planInfo.getBeginTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		plan.setHealthyName(null);
		plan.setOpeator(new BigDecimal(operatorUserId));
		plan.setOpTime(now);
		plan.setOrgId(staff.getOrgId());
		plan.setStaffId(new BigDecimal(staffId));
		plan.setStatus("C");
		plan.setUserId(new BigDecimal(userId));
		plan.setUserName(user.getUserName());
		healthyTestPlanDao.addHealthyPlan(plan);
		
		HealthyTestPlan testPlan = new HealthyTestPlan();
		testPlan.setCreateTime(now);
		testPlan.setDepId(staff.getDepId());
		testPlan.setHealthyId(plan.getHealthyId());
		testPlan.setOperator(new BigDecimal(operatorUserId));
		testPlan.setOpTime(now);
		testPlan.setOrgId(staff.getOrgId());
		testPlan.setPlanBeginTime(plan.getBeginTime());
		testPlan.setPlanEndTime(plan.getEndTime());
		testPlan.setStaffId(staff.getStaffId());
		testPlan.setStatus("C");
		testPlan.setTestTemplateId(planInfo.getTemplateId());
		testPlan.setUserId(user.getUserId());
		healthyTestPlanDao.addHealthyTestPlan(testPlan);

		/***
		 * 添加检测计划项
		 */
		HashMap<String, Object> detMap = new HashMap<String, Object>();
		for(PlanDetSubmit planDetInfo : planDetInfos){
			HealthyTestPlanTemplateDet templateDet= healthyTestPlanDao.getTemplateDetByTemplateDetId(planDetInfo.getTemplateDetId().toString());
			String key = templateDet.getKpiId() + "_" + templateDet.getTestTimeId();
			if(!detMap.containsKey(key)){
				//不存在数据
				
				HealthyTestPlanDet planDet = new HealthyTestPlanDet();
				planDet.setAlertCon(templateDet.getAlertCon());
				planDet.setAlertRuleId(templateDet.getAlertRuleId());
				planDet.setAlertTime(templateDet.getAlertTime());
				planDet.setKpiId(templateDet.getKpiId());
				planDet.setPeriodType(templateDet.getPeriodType());
				planDet.setPeriodValue(templateDet.getPeriodValue());
				planDet.setPlanBeginTime(plan.getBeginTime());
				planDet.setPlanEndTime(plan.getEndTime());
				planDet.setPlanId(testPlan.getPlanId());
				planDet.setTestBeginTime(templateDet.getTestBeginTime());
				planDet.setTestEndTime(templateDet.getTestEndTime());
				planDet.setTestTimeId(templateDet.getTestTimeId());
				healthyTestPlanDao.addHealthyTestPlanDet(planDet);
				
				/********** 任务 ***********/
				HealthyTestPlanTask  task = new HealthyTestPlanTask();
				task.setCreateTime(now);
				task.setExcStatus("1");
				SurveyKpi kpi = surveryDao.getKpiById(planDet.getKpiId().toString());
				task.setKpiCode(kpi.getKpiCode());
				task.setKpiId(kpi.getKpiId());
				task.setKpiTitle(kpi.getKpiTitle());
				task.setOperator(new BigDecimal(operatorUserId));
				task.setOpTime(now);
				task.setPlanDetId(planDet.getPlanDetId());
				task.setPlanId(testPlan.getPlanId());
				task.setUnit(kpi.getUnit());
				task.setUserId(user.getUserId());
				healthyTestPlanDao.addHealthyTestPlanTask(task);
				
				detMap.put(key, planDet);
			}
		}
		
		for(PlanDetSubmit planDetInfo : planDetInfos){
			HealthyTestPlanTemplateDet templateDet= healthyTestPlanDao.getTemplateDetByTemplateDetId(planDetInfo.getTemplateDetId().toString());
			TestPlanTemplateDetRule templateDetRule = healthyTestPlanDao.getTemplateDetRuleById(planDetInfo.getTemplateDetRuleId().toString());
			String key = templateDet.getKpiId() + "_" + templateDet.getTestTimeId();
			HealthyTestPlanDet planDet = (HealthyTestPlanDet) detMap.get(key);
			
			HealthyTestPlanDetRule detRule = new HealthyTestPlanDetRule();
			detRule.setAlertGrade(planDetInfo.getAlertGrade());
			detRule.setDoctorAlertCon(templateDet.getAlertCon());
			detRule.setKpiId(templateDet.getKpiId());
			detRule.setOneBeginSymbol(planDetInfo.getOneBeginSymbol());
			detRule.setOneBeginValue(planDetInfo.getOneBeginValue());
			detRule.setOneEndSymbol(planDetInfo.getOneEndSymbol());
			detRule.setOneEndValue(planDetInfo.getOneEndValue());
			detRule.setOneKpiId(planDetInfo.getOneKpiId());
			detRule.setOneTwoRelation(planDetInfo.getOneTwoRelation());
			detRule.setPlanDetId(planDet.getPlanDetId());
			detRule.setPlanId(testPlan.getPlanId());
			detRule.setTwoBeginSymbol(planDetInfo.getTwoBeginSymbol());
			detRule.setTwoBeginValue(planDetInfo.getTwoBeginValue());
			detRule.setTwoEndSymbol(planDetInfo.getTwoEndSymbol());
			detRule.setTwoEndValue(planDetInfo.getTwoEndValue());
			detRule.setTwoKpiId(planDetInfo.getTwoKpiId());
			detRule.setUserAlertResult(templateDetRule.getUserAlertResult());
			detRule.setUserHealthySuggest(templateDetRule.getUserHealthySuggest());
			detRule.setUserId(new BigDecimal(userId));
			
			healthyTestPlanDao.addHealthyTestPlanDetRule(detRule);
		}
		
		result.put("retCode", 0);
		result.put("retMessage", "添加成功");
		return result;
	}
	
	/**
	 * 停止检测计划
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月16日下午12:00:35
	 * 修改日期:2015年11月16日下午12:00:35
	 * @param operatorUserId 操作者ID 
	 * @param planId 计划ID
	 */
	@HttpMethod
	@Transactional
	public void stopTestPlan(@NotNull String operatorUserId,@NotNull String planId){
		healthyTestPlanDao.stopTestPlanTask(planId);
		healthyTestPlanDao.stopTestPlan(operatorUserId, planId);
	}
	
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
	@HttpMethod
	public HashMap<String, Object> getTestPlanByPage(String userId,String staffId,Long startRow,Long rows){
		HashMap<String, Object> result = new HashMap<String, Object>();
		Long endRow = null;
		if(rows!=null && startRow!=null){
			endRow= startRow + rows;
		}
		
		List<HealthyTestPlan> ls = healthyTestPlanDao.getTestPlanByPage(userId, staffId, startRow, endRow);
		long count = healthyTestPlanDao.getTestPlanByPageCount(userId, staffId);
		result.put("list", ls);
		result.put("count", count);
		return result;
	}
}
