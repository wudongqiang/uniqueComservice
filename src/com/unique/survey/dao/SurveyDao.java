package com.unique.survey.dao;

import java.util.Date;
import java.util.List;

import com.unique.plan.po.HealthyTestPlanDto;
import com.unique.survey.po.AlarmRule;
import com.unique.survey.po.AlarmTask;
import com.unique.survey.po.EvaluateRule;
import com.unique.survey.po.ScoreRule;
import com.unique.survey.po.SurveyEvaluate;
import com.unique.survey.po.SurveyItem;
import com.unique.survey.po.SurveyKpi;
import com.unique.survey.po.SurveyMoudle;
import com.unique.survey.po.SurveyMoudleSubitem;
import com.unique.survey.po.SurveyQusGroup;
import com.unique.survey.po.SurveyRecord;
import com.unique.survey.po.SurveyResult;
import com.unique.survey.po.SurveySchdule;
import com.unique.survey.po.SurveyTask;
import com.unique.survey.vo.SurveyMoudleDto;
import com.unique.survey.vo.SurveySchduleDto;


public interface SurveyDao {
	/**
	 * 根据模板ID查找组
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月19日上午11:01:58
	 * 修改日期:2015年8月19日上午11:01:58
	 * @param moudleId
	 * @return
	 */
	public List<SurveyQusGroup> getGroupByMoudle(String moudleId);
	
	/**
	 * 根据组ID获取随访项
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月19日下午2:29:26
	 * 修改日期:2015年8月19日下午2:29:26
	 * @param groupId
	 * @return
	 */
	public List<SurveyItem> getSurveyItem(String groupId);
	
	/**
	 * 根据项目ID获取选项列表
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月19日下午2:57:01
	 * 修改日期:2015年8月19日下午2:57:01
	 * @param itemId
	 * @return
	 */
	public List<SurveyMoudleSubitem> getSubItemByItem(String itemId);
	
	/**
	 * 根据ID获取模板
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月19日下午3:53:54
	 * 修改日期:2015年8月19日下午3:53:54
	 * @param moudleId
	 * @return
	 */
	public SurveyMoudle getMoudleById(String moudleId);
	
	/**
	 * 用户端获取随访记录分页
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月24日上午9:28:51
	 * 修改日期:2015年8月24日上午9:28:51
	 * @param staffId
	 * @param userId
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public List<SurveyRecord> getSurveyRecordByPage(String staffUserId,String userId,String userName,Long startRow,Long endRow);
	
	/**
	 * 用户端获取随访记录总数
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月24日上午9:29:53
	 * 修改日期:2015年8月24日上午9:29:53
	 * @param staffId
	 * @param userId
	 * @return
	 */
	public long getSurveyRecordByPageCount(String staffUserId,String userId,String userName);
	
	/**
	 * 医生端查询随访计划
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年8月21日 下午4:49:18
	 * @param staffId 医生Id
	 * @param userId 用户Id 用户端需要
	 * @param date 查询开始时间，不填写默认当前日期，也可查询具体日期的日程信息 yyyy-mm-dd
	 * @param type 类型标示日期查询方式：list列表查询，detailed详细查询，默认list
	 */
	public List<SurveySchduleDto> getSurveySchduleByStaffId(String staffId,String userId,String date, String type);

	/**
	 * 获取随访模板集 
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年8月24日 上午11:50:46
	 * @param orgId
	 * @return
	 */
	public List<SurveyMoudleDto> searchSurveyMoudles(String orgId);
	
	/**
	 * 添加随访结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月24日下午3:21:40
	 * 修改日期:2015年8月24日下午3:21:40
	 * @param surveyResult
	 * @return
	 */
	public int addSurveyResult(SurveyResult surveyResult);
	
	public SurveyMoudle getMoudleByRecordId(String recordId);
	
	/**
	 * 插叙下次随访计划
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月18日下午3:57:32
	 * 修改日期:2015年8月18日下午3:57:32
	 * @param userId 用户ID
	 * @param modleId 模板ID
	 * @param thisTime 这次随访时间
	 * @return
	 */
	public SurveyTask getNextSurvey(String userId,String modleId,Date thisTime);
	
	/**
	 * 添加随访记录
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月18日下午4:36:04
	 * 修改日期:2015年8月18日下午4:36:04
	 * @param record
	 * @return
	 */
	public int addSurveyRecord(SurveyRecord  record);
	
	/**
	 * 根据中间表查询KPI
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月27日下午3:01:31
	 * 修改日期:2015年8月27日下午3:01:31
	 * @param groupId
	 * @return
	 */
	public SurveyKpi getKpiByGroupVsKpi(String groupId);
	
	/**
	 * 根据ID获取subitem
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月27日下午3:50:06
	 * 修改日期:2015年8月27日下午3:50:06
	 * @param subItemId
	 * @return
	 */
	public SurveyMoudleSubitem getSubItemById(String subItemId);
	
	/**
	 * 根据随访记录ID查询积分规则
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月27日下午4:06:51
	 * 修改日期:2015年8月27日下午4:06:51
	 * @param recordId
	 * @return
	 */
	public List<ScoreRule> getScoreRuleByRecord(String recordId);
	
	/**
	 * 根据ID获取随访记录
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月28日上午9:45:08
	 * 修改日期:2015年8月28日上午9:45:08
	 * @param recordId
	 * @return
	 */
	public SurveyRecord getSurveyRecordById(String recordId);
	
	/**
	 * 更新随访记录
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月28日上午9:51:06
	 * 修改日期:2015年8月28日上午9:51:06
	 * @param record
	 * @return
	 */
	public int updateSurveyRecord(SurveyRecord record);
	
	/**
	 * 获取医生 随访日程 日期集
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年8月28日 上午9:40:56
	 * @param staffId
	 * @param monthStr  yyyy-mm-dd
	 * @return
	 */
	public List<String> getSurveyScheduleDates(String staffId, String monthStr);
	
	/**
	 * 添加随访结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月31日上午9:51:48
	 * 修改日期:2015年8月31日上午9:51:48
	 * @param eval
	 * @return
	 */
	public int addSurveyEval(SurveyEvaluate eval);
	
	/**
	 * 根据得分获取评价规则
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月31日上午10:12:23
	 * 修改日期:2015年8月31日上午10:12:23
	 * @param value
	 * @return
	 */
	public EvaluateRule getEvalRule(Double value,String scoreRuleId);
	
	public List<SurveyResult> getAnswerByKpi(String kpiId,String surveyId);
	
	public List<SurveyResult> getAnswerBySurveyItemId(String itemId,String surveyId);
	
	public List<AlarmRule> getAlarmRuleByIdAndType(String qId,String qType);

	public int addAlarmTask(AlarmTask task);
	
	/**
	 * 根据随访ID获取评价结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月1日下午2:45:00
	 * 修改日期:2015年9月1日下午2:45:00
	 * @param recordId
	 * @return
	 */
	public List<EvaluateRule> getEvalsByRecord(String recordId);
	
	/**
	 * 获取随访计划<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月19日 上午11:47:22<br/>
	 * @param userId
	 * @param staffId
	 * @return
	 */
	public List<SurveySchdule> searchSurveySchdule(String userId,String staffId,Long startRow,Long endRow);
	
	/**
	 * 获取随访计划总数<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月19日 上午11:47:22<br/>
	 * @param userId
	 * @param staffId
	 * @return
	 */
	public Long searchSurveySchduleCount(String userId,String staffId);
	
	/**
	 * 获取监测计划<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月19日 上午11:47:22<br/>
	 * @param userId
	 * @param staffId
	 * @return
	 */
	public List<HealthyTestPlanDto> searchHealthTestSchdule(String userId,String staffId,Long startRow,Long endRow);
	
	/**
	 * 获取监测计划总数<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月19日 上午11:47:22<br/>
	 * @param userId
	 * @param staffId
	 * @return
	 */
	public Long searchHealthTestSchduleCount(String userId,String staffId);
	
	public SurveyKpi getKpiByCode(String code);
	
	public SurveyKpi getKpiById(String kpiId);

	/**
	 * 更新随访结果回复<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月17日 上午11:10:12<br/>
	 * @param resultId
	 * @param docFeedback
	 */
	public int updateSurveyResultFeedfack(String[] resultId, String docFeedback);

	/**
	 * <br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月17日 上午11:41:15<br/>
	 * @param resultId
	 * @return
	 */
	public String getSurveyMoudleItemTitle(String resultId);
}
