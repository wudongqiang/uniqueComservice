/**
 *
 */
package com.unique.survey.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.transaction.Transactional;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;
import com.unique.alipay.util.UtilDate;
import com.unique.core.annotation.CollectionClass;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.util.IdCardUtil;
import com.unique.core.util.StringUtil;
import com.unique.org.dao.StaffDao;
import com.unique.plan.dao.HealthyTestPlanDao;
import com.unique.plan.dao.HealthyTestPlanDetDao;
import com.unique.plan.po.HealthyTestPlanTask;
import com.unique.reg.po.Staff;
import com.unique.survey.dao.SurveyDao;
import com.unique.survey.po.AlarmRule;
import com.unique.survey.po.AlarmTask;
import com.unique.survey.po.EvaluateRule;
import com.unique.survey.po.ScoreRule;
import com.unique.survey.po.SurveyAnswer;
import com.unique.survey.po.SurveyEvaluate;
import com.unique.survey.po.SurveyItem;
import com.unique.survey.po.SurveyKpi;
import com.unique.survey.po.SurveyMoudle;
import com.unique.survey.po.SurveyMoudleSubitem;
import com.unique.survey.po.SurveyQusGroup;
import com.unique.survey.po.SurveyRecord;
import com.unique.survey.po.SurveyRecordExt;
import com.unique.survey.po.SurveyResult;
import com.unique.survey.po.SurveyTask;
import com.unique.survey.vo.ReplyFeedback;
import com.unique.survey.vo.SurveyMoudleDto;
import com.unique.user.dao.UserDao;
import com.unique.user.po.AmsUser;
import com.unique.user.webservice.WebUserService;

/**
 * 随访业务层
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年8月18日下午3:12:03
 * 修改日期:2015年8月18日下午3:12:03
 */
@Service("surveryService")
public class SurveyServiceImpl implements SurveyService {
	@Resource
	private SurveyDao surveyDao;
	@Resource
	private WebUserService webUserService;
	@Resource
	private UserDao userDao;
	@Resource
	private StaffDao staffDao;
	@Resource
	private HealthyTestPlanDao healthyTestPlanDao;
	@Resource
	private HealthyTestPlanDetDao healthyTestPlanDetDao;
	
	public static Logger log = Logger.getLogger("exception");
	/**
	 * 根据随访记录ID获取随访问卷
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月19日上午11:03:20
	 * 修改日期:2015年8月19日上午11:03:20
	 * @return
	 */
	@HttpMethod
	public Map<String, Object> getSurveyPaperByMoudleId(@NotNull String recordId){
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		SurveyMoudle moudle = surveyDao.getMoudleByRecordId(recordId);
		
		//获取组列表
		List<SurveyQusGroup> groups = surveyDao.getGroupByMoudle(moudle.getSurveyMoudleId().toString());
		for(SurveyQusGroup group : groups){
			List<SurveyItem> items = surveyDao.getSurveyItem(group.getSurveyGroupId().toString());
			for(SurveyItem item : items){
				if(item.getType().equals("1")){
					//非测试项
					
					//设置选项
					List<SurveyMoudleSubitem> subItems = surveyDao.getSubItemByItem(item.getId().toString());
					if(subItems!=null && subItems.size() > 0){
						item.setMoudleSubitems(subItems);
					}
				}
			}
			group.setSurveyItems(items);
		}
		
		result.put("moudleInfo", moudle);
		result.put("moudleGroups", groups);
		return result;
	}
	
	/**
	 * 根据随访ID获取随访结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月19日上午11:03:20
	 * 修改日期:2015年8月19日上午11:03:20
	 * @return
	 */
	@HttpMethod
	public Map<String, Object> getSurveyRecordInfo(@NotNull String recordId){
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		SurveyMoudle moudle = surveyDao.getMoudleByRecordId(recordId);
		SurveyRecord record =  surveyDao.getSurveyRecordById(recordId);
		SurveyRecordExt recordExt = new SurveyRecordExt();
		try {
			BeanUtils.copyProperties(recordExt, record);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		Staff staff =staffDao.getStaffInfo(record.getStaffId().toString());
		recordExt.setDepName(staff.getDepName());
		recordExt.setOrgName(staff.getOrgName());
		recordExt.setStaffTypeName(staff.getStaffTypeName());
		//获取组列表
		List<SurveyQusGroup> groups = surveyDao.getGroupByMoudle(moudle.getSurveyMoudleId().toString());
		for(SurveyQusGroup group : groups){
			List<SurveyItem> items = surveyDao.getSurveyItem(group.getSurveyGroupId().toString());
			for(SurveyItem item : items){
				if(item.getType().equals("1")){
					//非测试项
					List<SurveyResult> results = surveyDao.getAnswerBySurveyItemId(item.getId().toString(),recordId);
					item.setResults(results);
				}else{
					//测试项
					List<SurveyResult> results = surveyDao.getAnswerByKpi(item.getId().toString(),recordId);
					item.setResults(results);
				}
			}
			group.setSurveyItems(items);
		}
		
		List<EvaluateRule> evals =  surveyDao.getEvalsByRecord(recordId);
		result.put("moudleInfo", moudle);
		result.put("moudleGroups", groups);
		result.put("record",recordExt);
		result.put("evaluates", evals);
		return result;
	}
	
	/**
	 * 用户端获取随访记录分页
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月24日上午9:28:51
	 * 修改日期:2015年8月24日上午9:28:51
	 * @param staffUserId
	 * @param userId
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	@HttpMethod
	public Map<String, Object> getSurveyRecordByPage(String staffUserId,String userId,String userName, Long startRow,Long rows){
		Map<String, Object> result = new HashMap<String, Object>();
		Long endRows= null;
		if(startRow!=null && rows!=null){
			endRows = startRow + rows;
		}
		List<SurveyRecord> records = surveyDao.getSurveyRecordByPage(staffUserId, userId,userName,startRow, endRows);
		long count = surveyDao.getSurveyRecordByPageCount(staffUserId, userId,userName);
		result.put("items", records);
		result.put("count", count);
		return result;
	}
	
	@HttpMethod
	@Override
	public List<SurveyMoudleDto> searchSurveyMoudles(@NotNull String orgId){
		return surveyDao.searchSurveyMoudles(orgId);
	}
	
	/**
	 * 医生端推送随访到用户端
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月27日上午10:09:25
	 * 修改日期:2015年8月27日上午10:09:25
	 * @param staffUserId
	 * @param userIds
	 * @param moudleIds
	 */
	@HttpMethod
	public void pushSurveys(@NotNull String staffUserId,@NotNull String[] userIds,@NotNull String[] moudleIds){
		//添加随访记录
		Date nowTime = new Date();
		for(String userId : userIds){
			for(String moudleId : moudleIds){
				AmsUser user = userDao.getUserById(userId);
				Staff staff = staffDao.getStaffByUserId(staffUserId);
				//获取下次随访计划
				SurveyTask nextTask = surveyDao.getNextSurvey(user.getUserId().toString(), moudleId, nowTime);
				SurveyMoudle moudle = surveyDao.getMoudleById(moudleId);
				
				//创建随访记录
				SurveyRecord record = new SurveyRecord();
				IdCardUtil idcardUtil = new IdCardUtil(user.getIdCard());
				record.setAge(new BigDecimal(idcardUtil.getAgeByIdCard()));
				record.setBirth(idcardUtil.getBirthday());
				record.setCreateTime(new Date());
				record.setDepId(staff.getDepId());
				if(nextTask!=null){
					record.setNextSruveyDate(nextTask.getExcTime());
				}
				record.setOperator(new BigDecimal("-1"));
				record.setOpTime(new Date());
				record.setOrgId(staff.getOrgId());
				record.setSruveyDate(nowTime);
				record.setStaffId(staff.getStaffId());
				record.setStaffName(staff.getStaffName());
				record.setStatus("0");
				record.setSurveyMoudleId(new BigDecimal(moudleId));
				record.setUserId(user.getUserId());
				record.setUserName(user.getUserName());
				
				surveyDao.addSurveyRecord(record);
				
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("moudleId", moudleId);
				param.put("type", "survey");
				param.put("recordId", record.getSurveyId());
				String msg = "${username}，您好！今天是你的随访时间，${staffname}医生发了一份[${surveyname}表]，请尽快填写，方便医生为您做相关评估。";
				msg = msg.replaceAll("\\$\\{username}", user.getUserName())
					.replaceAll("\\$\\{staffname}", staff.getStaffName())
					.replaceAll("\\$\\{surveyname}", moudle.getSurveyMoudleName());
				webUserService.sendMsg(staffUserId, msg, JSONObject.fromObject(param).toString(), new String[]{userId});
				
				//发送成功消息
				webUserService.sendNtfMessage(userId, "随访发送成功", "", staffUserId);
			}
		}
	}
	
	/**
	 * 提交随访答案
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月27日上午11:50:33
	 * 修改日期:2015年8月27日上午11:50:33
	 * @param userId 答卷人ID
	 * @param recordId 随访记录ID
	 * @param answers 回答问题集合
	 */
	@HttpMethod
	@Transactional
	public HashMap<String, Object> submitAnswer(@NotNull String userId,@NotNull String recordId,@CollectionClass(value=SurveyAnswer.class) List<SurveyAnswer> answers){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Date nowTime = new Date();
		AmsUser user  = userDao.getUserById(userId);
		
		//获取随访记录
		SurveyRecord record = surveyDao.getSurveyRecordById(recordId);
		if(record.getStatus()!=null && record.getStatus().equals("1")){
			//已填写
			resultMap.put("retCode", 3);
			resultMap.put("retMessage", "该随访已填写");
			return resultMap;
		}else if(record.getStatus()!=null && record.getStatus().equals("2")){
			//已填写
			resultMap.put("retCode", 4);
			resultMap.put("retMessage", "该随访已删除");
			return resultMap;
		}
		
		Staff staff =staffDao.getStaffById(record.getStaffId().toString());
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName ("JavaScript");
		
		int i=0;
		//保存回答结果
		if(answers!=null){
			for(SurveyAnswer answer : answers){
				i++;
				String surveyItemType = answer.getSurveyItemType();
				BigDecimal id = answer.getId();
				
				SurveyResult result =  new SurveyResult();
				result.setCreateTime(nowTime);
				result.setOperator(user.getUserId());
				result.setOpTime(nowTime);
				result.setSurveyId(new BigDecimal(recordId));
				if(answer.getType().equals("1")){
					//普通项
					//题目
					result.setSurveyItemId(id);
					///1单选 2多选 3填空 4有无型
					if("1".equals(surveyItemType)){
						//题值
						result.setSubItemId(answer.getNumValue());
						//获取答案子项
						SurveyMoudleSubitem subItem = surveyDao.getSubItemById(answer.getNumValue().toString());
						
						//获取题目得分
						int score = 0;
						if(!StringUtil.isEmpty(subItem.getSubItemValue())){
							score = Integer.parseInt(subItem.getSubItemValue());
						}
						//把得分加入脚本引擎等待计算
						engine.put("Q" + i, score);
						
						//预警设置
						List<AlarmRule> alarms = surveyDao.getAlarmRuleByIdAndType(id.toString(),"2");
						for(AlarmRule alarm : alarms){
							//测试
							ScriptEngineManager factory2 = new ScriptEngineManager();
							ScriptEngine alarmEngine = factory2.getEngineByName ("JavaScript");
							
							Boolean doAlarm = false;
							try {
								doAlarm = (Boolean) alarmEngine.eval(score + alarm.getValRalation() + alarm.getLimitValue());
							} catch (ScriptException e) {
								e.printStackTrace();
							}
							
							if(doAlarm){
								//执行预警
								AlarmTask task = new AlarmTask();
								task.setAlarmRuleId(alarm.getAlarmRuleId());
								task.setCreateTime(new Date());
								task.setSendTime(new Date());
								task.setStaffId(record.getStaffId());
								task.setStats("1");
								task.setSurveyId(record.getSurveyId());
								
								surveyDao.addAlarmTask(task);
							}
						}
						
					}else if("2".equals(surveyItemType)){
						//多选要特殊一些
						//题值
						if(StringUtil.isEmpty(answer.getStrValue())){
							result.setSubItemId(answer.getNumValue());
						}else{
							//附加答案
							result.setStrValue(answer.getStrValue());
						}
						engine.put("Q" + i, 0);
					}else if("3".equals(surveyItemType)){
						result.setStrValue(answer.getStrValue());
						engine.put("Q" + i, 0);
					}else if("4".equals(surveyItemType)){
						//0无，1有
						result.setNumValue(answer.getNumValue());
						result.setStrValue(answer.getStrValue());
						engine.put("Q" + i, 0);
					}
				}else{
					//测试项
					SurveyKpi kpi = surveyDao.getKpiByGroupVsKpi(id.toString());
					result.setKpiId(kpi.getKpiId());
					result.setMoudleKpiId(id);
					result.setNumValue(answer.getNumValue());
					
					engine.put("Q" + i, 0);
				}
				//保存随访结果
				surveyDao.addSurveyResult(result);
			}
		}
		//获得积分规则
		List<ScoreRule> scoreRules= surveyDao.getScoreRuleByRecord(recordId);
		
		int j=0;
		if(scoreRules!=null){
			for(ScoreRule rule : scoreRules){
				//简单验证下公式
				if(StringUtil.isEmpty(rule.getFormula())) continue;
				
				//计算得分
				try {
					Object allScore = engine.eval(rule.getFormula());
					if(j==0){
						//设置总分
						record.setResult(new BigDecimal(allScore.toString()));
					}
					
					//添加评价结果
					EvaluateRule evalRule = surveyDao.getEvalRule(new BigDecimal(allScore.toString()).doubleValue(),rule.getScoreRuleId().toString());
					SurveyEvaluate eval = new SurveyEvaluate();
					eval.setEvaluateRuleId(evalRule.getEvaluateRuleId());
					eval.setSurveyId(record.getSurveyId());
					surveyDao.addSurveyEval(eval);
					
					//把结果告知用户
					webUserService.sendMsg(staff.getUserId().toString(), evalRule.getEvaluateRuleTitle(), "", new String[]{userId});
					
					
					j++;
				} catch (ScriptException e) {
					e.printStackTrace();
				}
			}
		}
		//更新随访记录为已读状态
		record.setStatus("1");
		record.setFillTime(nowTime);
		surveyDao.updateSurveyRecord(record);
		return resultMap;
	}
 
	@HttpMethod
	@Override
	public Map<String,Object> searchSurveySchdule(String userId,String staffId,Long startRow,Long rows) {
		Long endRow = null;
		if(startRow !=null && rows!=null){
			endRow = startRow+rows;
		}
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put("surveySchdules", surveyDao.searchSurveySchdule(userId, staffId,startRow,endRow));
		result.put("count", surveyDao.searchSurveySchduleCount(userId, staffId));
		return result;
	}
	
	@HttpMethod
	@Override
	public Map<String, Object> getToDateSchdule(String userId) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		//获取今日随访计划
		result.put("surveySchdule", surveyDao.getSurveySchduleByStaffId(null, userId, UtilDate.getCurrentDate(), "details"));
		//获取今日监测计划 -- 监测任务
		List<HealthyTestPlanTask> hts = healthyTestPlanDao.getHealthyTestPlanTaskByTime(UtilDate.getCurrentDate(),userId);//"2015-10-07"
		//收缩压 SSY  血压 XY  血糖 XT
		Map<String,Object> params = new HashMap<String,Object>(5);
		for(HealthyTestPlanTask ht : hts){
			params.clear();
			params.put("planDetId",ht.getPlanDetId().toString());
			params.put("kpiId", ht.getKpiId().toString());
			params.put("planId", ht.getPlanId().toString());
			params.put("userId", userId);
			//查询监测项值
			if("XY".equals(ht.getKpiCode())){
				//查询血压--USER_HYPERTENSION_TEST
				params.put("columus", "floor(avg(t.low_tension))||'/'||floor(avg(t.high_tension))");
				params.put("tableName", "USER_HYPERTENSION_TEST t");
			}else if("XL".equals(ht.getKpiCode())){
				//查询心率--USER_HYPERTENSION_TEST
				params.put("columus", "floor(avg(t.heart_rate))");//心率
				params.put("tableName", "USER_HYPERTENSION_TEST t");
			}else if("XT".equals(ht.getKpiCode())){
				//查询血糖--USER_BLOOD_SUGAR_TEST
				params.put("columus", "round(avg(t.test_value),2)");
				params.put("tableName", "USER_BLOOD_SUGAR_TEST t");
			}else if("SSY".equals(ht.getKpiCode())){
				//收缩压--USER_HYPERTENSION_TEST  --高压
				params.put("columus", "floor(avg(t.high_tension))");
				params.put("tableName", "USER_HYPERTENSION_TEST t");
			}else if("SZY".equals(ht.getKpiCode())){
				//舒张压--USER_HYPERTENSION_TEST  --低压
				params.put("columus", "floor(avg(t.low_tension))");
				params.put("tableName", "USER_HYPERTENSION_TEST t");
			}
			//
			if(params.size()==6){
				ht.setTestValue(healthyTestPlanDetDao.getTestItemValue(params));
			}
		}
		result.put("healthyTestPlan",hts);
		return result;
	}
	
	@HttpMethod
	@Override
	public Map<String,Object> searchHealthTestSchdule(String userId, String staffId,Long startRow,Long rows) {
		Long endRow = null;
		if(startRow !=null && rows!=null){
			endRow = startRow+rows;
		}
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put("healthTestPlans", surveyDao.searchHealthTestSchdule(userId, staffId,startRow,endRow));
		result.put("count", surveyDao.searchHealthTestSchduleCount(userId, staffId));
		return result;
	}
	
	@HttpMethod
	@Override
	@Transactional
	public Map<String,Object> updateReplySurveyResult(
			 @NotNull 
			 @CollectionClass(ReplyFeedback.class)
			 List<ReplyFeedback> feedbacks,String userId,
			String staffId,String url){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(feedbacks==null || feedbacks.size() <= 0){
				result.put("retCode", 0);
				result.put("retMessage", "没有回复内容");
				return result;
			}
			for(ReplyFeedback fb : feedbacks){
				surveyDao.updateSurveyResultFeedfack(fb.getResultIds(),StringUtil.isEmpty(fb.getDocFeedback())?"":fb.getDocFeedback());
			}
			
			result.put("retCode", 0);
			result.put("retMessage", "操作成功");
			
			//发送IM  发送给用户
			if(!StringUtil.isEmpty(userId)){
			
				Staff staff = staffDao.getStaffById(staffId);
				AmsUser user = userDao.getUserById(userId);
				String recordId = url.substring(url.indexOf("recordId")+9,url.indexOf("&"));
				log.info("recordId:"+recordId+" url:"+url);
				SurveyRecord record = surveyDao.getSurveyRecordById(recordId);
				SurveyMoudle moudle = surveyDao.getMoudleById(record.getSurveyMoudleId().toString());
				
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("moudleId", moudle.getSurveyMoudleId().toString());
				param.put("type", "surveyfilled");
				param.put("recordId", record.getSurveyId());
				String msg = user.getUserName()+"，您好！"+staff.getStaffName()+" 医生对你的["+moudle.getSurveyMoudleName()+"表]做出了回复，请注意查看。";
				webUserService.sendMsg(staff.getUserId().toString(), msg, JSONObject.fromObject(param).toString(), new String[]{userId});
			}
			
		} catch (Exception e) {
			result.put("retCode", -1);
			result.put("retMessage", "未知异常");
		}
		return result;
	}
}
