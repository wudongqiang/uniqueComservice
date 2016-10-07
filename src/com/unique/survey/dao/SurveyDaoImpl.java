package com.unique.survey.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.unique.alipay.util.UtilDate;
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

/**
 * 用户管理持久层
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年8月17日上午11:55:49
 * 修改日期:2015年8月17日上午11:55:49
 */
@Repository("surveryDao")
public class SurveyDaoImpl implements SurveyDao{
	@Resource
	private SqlSessionTemplate sqlSession;
	
	/**
	 * 根据模板ID查找组
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月19日上午11:01:58
	 * 修改日期:2015年8月19日上午11:01:58
	 * @param moudleId
	 * @return
	 */
	public List<SurveyQusGroup> getGroupByMoudle(String moudleId){
		return sqlSession.selectList("getGroupByMoudle",moudleId);
	}
	
	/**
	 * 根据组ID获取随访项
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月19日下午2:29:26
	 * 修改日期:2015年8月19日下午2:29:26
	 * @param groupId
	 * @return
	 */
	public List<SurveyItem> getSurveyItem(String groupId){
		return sqlSession.selectList("getSurveyItem",groupId);
	}
	
	/**
	 * 根据项目ID获取选项列表
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月19日下午2:57:01
	 * 修改日期:2015年8月19日下午2:57:01
	 * @param itemId
	 * @return
	 */
	public List<SurveyMoudleSubitem> getSubItemByItem(String itemId){
		return sqlSession.selectList("getSubItemByItem",itemId);
	}
	
	/**
	 * 根据ID获取模板
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月19日下午3:53:54
	 * 修改日期:2015年8月19日下午3:53:54
	 * @param moudleId
	 * @return
	 */
	public SurveyMoudle getMoudleById(String moudleId){
		return sqlSession.selectOne("getMoudleById",moudleId);
	}
	
	public SurveyMoudle getMoudleByRecordId(String recordId){
		return sqlSession.selectOne("getMoudleByRecordId",recordId);
	}
	
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
	public List<SurveyRecord> getSurveyRecordByPage(String staffUserId,String userId,String userName,Long startRow,Long endRow){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("staffUserId", staffUserId);
		param.put("userId", userId);
		param.put("userName", userName);
		param.put("startRow", startRow);
		param.put("endRow", endRow);
		return sqlSession.selectList("getSurveyRecordByPage",param);
	}
	
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
	public long getSurveyRecordByPageCount(String staffUserId,String userId,String userName){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("staffUserId", staffUserId);
		param.put("userId", userId);
		param.put("userName", userName);
		return sqlSession.selectOne("getSurveyRecordByPage_count",param);
	}

	@Override
	public List<SurveySchduleDto> getSurveySchduleByStaffId(String staffId,String userId, String date,
			String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("staffId", staffId);
		param.put("userId", userId);
		if("list".equals(type)){
			param.put("month", date.substring(0, date.lastIndexOf("-")));
//			param.put("date", date);
		}else{
			param.put("searchDate", date);
		}
		param.put("sysdate", UtilDate.getCurrentDate());
		
		return sqlSession.selectList("getSurveySchduleByStaffId", param);
	}

	@Override
	public List<SurveyMoudleDto> searchSurveyMoudles(String orgId) {
		Map<String, Object> param = new HashMap<String, Object>(1);
		param.put("orgId", orgId);
		return sqlSession.selectList("searchSurveyMoudles",param);
	}
	
	/**
	 * 添加随访结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月24日下午3:21:40
	 * 修改日期:2015年8月24日下午3:21:40
	 * @param surveyResult
	 * @return
	 */
	public int addSurveyResult(SurveyResult surveyResult){
		return sqlSession.insert("addSurveyResult",surveyResult);
	}
	
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
	public SurveyTask getNextSurvey(String userId,String modleId,Date thisTime){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("modleId", modleId);
		param.put("thisTime", thisTime);
		return sqlSession.selectOne("getNextSurvey",param);
	}
	
	/**
	 * 添加随访记录
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月18日下午4:36:04
	 * 修改日期:2015年8月18日下午4:36:04
	 * @param record
	 * @return
	 */
	public int addSurveyRecord(SurveyRecord  record){
		return sqlSession.insert("addSurveyRecord",record);
	}
	
	/**
	 * 根据中间表查询KPI
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月27日下午3:01:31
	 * 修改日期:2015年8月27日下午3:01:31
	 * @param groupId
	 * @return
	 */
	public SurveyKpi getKpiByGroupVsKpi(String groupId){
		return sqlSession.selectOne("getKpiByGroupVsKpi",groupId);
	}
	
	/**
	 * 根据ID获取subitem
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月27日下午3:50:06
	 * 修改日期:2015年8月27日下午3:50:06
	 * @param subItemId
	 * @return
	 */
	public SurveyMoudleSubitem getSubItemById(String subItemId){
		return sqlSession.selectOne("getSubItemById",subItemId);
	}
	
	/**
	 * 根据随访记录ID查询积分规则
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月27日下午4:06:51
	 * 修改日期:2015年8月27日下午4:06:51
	 * @param recordId
	 * @return
	 */
	public List<ScoreRule> getScoreRuleByRecord(String recordId){
		return sqlSession.selectList("getScoreRuleByRecord",recordId);
	}
	
	/**
	 * 根据ID获取随访记录
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月28日上午9:45:08
	 * 修改日期:2015年8月28日上午9:45:08
	 * @param recordId
	 * @return
	 */
	public SurveyRecord getSurveyRecordById(String recordId){
		return sqlSession.selectOne("getSurveyRecordById",recordId);
	}
	
	/**
	 * 更新随访记录
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月28日上午9:51:06
	 * 修改日期:2015年8月28日上午9:51:06
	 * @param record
	 * @return
	 */
	public int updateSurveyRecord(SurveyRecord record){
		return sqlSession.update("updateSurveyRecord",record);
	}
	
	@Override
	public List<String> getSurveyScheduleDates(String staffId, String monthStr) {
		Map<String, Object> param = new HashMap<String, Object>(2);
		param.put("staffId", staffId);
		param.put("monthStr", monthStr);
		return sqlSession.selectList("getSurveyScheduleDates", param);
	}
	
	/**
	 * 添加随访结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月31日上午9:51:48
	 * 修改日期:2015年8月31日上午9:51:48
	 * @param eval
	 * @return
	 */
	public int addSurveyEval(SurveyEvaluate eval){
		return sqlSession.insert("addSurveyEval",eval);
	}
	
	/**
	 * 根据得分获取评价规则
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月31日上午10:12:23
	 * 修改日期:2015年8月31日上午10:12:23
	 * @param value
	 * @return
	 */
	public EvaluateRule getEvalRule(Double value,String scoreRuleId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("value", value.toString());
		param.put("scoreRuleId", scoreRuleId);
		return sqlSession.selectOne("getEvalRule",param);
	}
	
	public List<SurveyResult> getAnswerByKpi(String kpiId,String surveyId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("kpiId", kpiId);
		param.put("surveyId", surveyId);
		return sqlSession.selectList("getAnswerByKpi",param);
	}
	public List<SurveyResult> getAnswerBySurveyItemId(String itemId,String surveyId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("itemId", itemId);
		param.put("surveyId", surveyId);
		return sqlSession.selectList("getAnswerBySurveyItemId",param);
	}
	
	public List<AlarmRule> getAlarmRuleByIdAndType(String qId,String qType){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("qId", qId);
		param.put("qType", qType);
		return sqlSession.selectList("getAlarmRuleByIdAndType",param);
	}
	
	/**
	 * 添加预警计划
	 */
	public int addAlarmTask(AlarmTask task){
		return sqlSession.insert("addAlarmTask",task);
	}
	
	/**
	 * 根据随访ID获取评价结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月1日下午2:45:00
	 * 修改日期:2015年9月1日下午2:45:00
	 * @param recordId
	 * @return
	 */
	public List<EvaluateRule> getEvalsByRecord(String recordId){
		return sqlSession.selectList("getEvalsByRecord",recordId);
	}
	
	@Override
	public List<SurveySchdule> searchSurveySchdule(String userId, String staffId,Long startRow,Long endRow) {
		Map<String, Object> param = new HashMap<String, Object>(4);
		param.put("userId", userId);
		param.put("staffId", staffId);
		param.put("startRow", startRow);
		param.put("endRow", endRow);
		return sqlSession.selectList("searchSurveySchdule", param);
	}
	
	@Override
	public List<HealthyTestPlanDto> searchHealthTestSchdule(String userId, String staffId,Long startRow,Long endRow) {
		Map<String, Object> param = new HashMap<String, Object>(4);
		param.put("userId", userId);
		param.put("staffId", staffId);
		param.put("startRow", startRow);
		param.put("endRow", endRow);
		return sqlSession.selectList("searchHealthTestSchdule", param);
	}
	 
	@Override
	public Long searchHealthTestSchduleCount(String userId, String staffId) {
		Map<String, Object> param = new HashMap<String, Object>(2);
		param.put("userId", userId);
		param.put("staffId", staffId);
		return sqlSession.selectOne("searchHealthTestSchduleCount", param);
	}
	
	public SurveyKpi getKpiByCode(String code){
		return sqlSession.selectOne("getKpiByCode",code);
	}
	public SurveyKpi getKpiById(String kpiId){
		return sqlSession.selectOne("getKpiById",kpiId);
	}
	
	@Override
	public Long searchSurveySchduleCount(String userId, String staffId) {
		Map<String, Object> param = new HashMap<String, Object>(2);
		param.put("userId", userId);
		param.put("staffId", staffId);
		return sqlSession.selectOne("searchSurveySchduleCount", param);
	}
	
	@Override
	public int updateSurveyResultFeedfack(String[] resultIds, String docFeedback) {
		Map<String, Object> param = new HashMap<String, Object>(2);
		param.put("resultIds", resultIds);
		param.put("docFeedback", docFeedback);
		return sqlSession.update("updateSurveyResultFeedfack", param);
	}
	
	@Override
	public String getSurveyMoudleItemTitle(String resultId) {
		return sqlSession.selectOne("getSurveyMoudleItemTitle", resultId);
	}
}
