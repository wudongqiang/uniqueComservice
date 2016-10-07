/**
 *
 */
package com.unique.survey.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.survey.po.SurveyAnswer;
import com.unique.survey.vo.ReplyFeedback;
import com.unique.survey.vo.SurveyMoudleDto;

/**
 * 随访业务层
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年8月18日下午3:11:56
 * 修改日期:2015年8月18日下午3:11:56
 */
public interface SurveyService {
	/**
	 * 根据模块ID获取随访问卷
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月19日上午11:03:20
	 * 修改日期:2015年8月19日上午11:03:20
	 * @return
	 */
	public Map<String, Object> getSurveyPaperByMoudleId(@NotNull String moudleId);
	
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
	@HttpMethod
	public Map<String, Object> getSurveyRecordByPage(String staffUserId,String userId,String userName,Long startRow,Long rows);
	
	/**
	 * 根据随访ID获取随访结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月19日上午11:03:20
	 * 修改日期:2015年8月19日上午11:03:20
	 * @return
	 */
	@HttpMethod
	public Map<String, Object> getSurveyRecordInfo(@NotNull String recordId);
	
	/**
	 * 获取随访模板集
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年8月24日 上午11:48:49
	 * @param orgId
	 * @return
	 */
	public List<SurveyMoudleDto> searchSurveyMoudles(String orgId);
	
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
	public void pushSurveys(String staffUserId,String[] userIds,String[] moudleIds);
	
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
	public HashMap<String, Object> submitAnswer(@NotNull String userId,@NotNull String recordId,List<SurveyAnswer> answers);
	
	/**
	 * 获取随访计划<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月19日 上午11:45:34<br/>
	 * @param userId
	 * @param staffId
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public Map<String,Object> searchSurveySchdule(String userId,String staffId,Long startRow,Long rows);
	
	/**
	 * 获取今日计划（包含随访计划，监测计划）<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月19日 下午1:09:47<br/>
	 * @param userId
	 * @return
	 */
	public Map<String,Object> getToDateSchdule(String userId);
	
	/**
	 * 获取监测计划<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月19日 下午1:33:44<br/>
	 * @param userId
	 * @param staffId
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public Map<String,Object> searchHealthTestSchdule(String userId,String staffId,Long startRow,Long rows);

	/**
	 * 随访记录回复<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月17日 上午11:03:05<br/>
	 * @param resultId
	 * @param staffId
	 * @param userId
	 * @param docFeedback
	 * @return
	 */
	Map<String, Object> updateReplySurveyResult(List<ReplyFeedback> feedbacks,String userId,
			String staffId,String url);
	
	
}