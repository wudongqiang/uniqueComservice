package com.unique.survey.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 医生端 封装简单的随访计划 信息  
 * <br/>创建人 wdq
 * <br/>创建时间 2015年8月21日 下午4:52:08
 * @author Administrator
 * @date 2015年8月21日
 * @description
 */
public class SurveySchduleDto {

	private Date date;				//随访时间
	private BigDecimal userId;			//用户Id
	private String userName;		//用户名称
	private BigDecimal surveryMoudleId;	//随访模板Id
	private String statusStr;		//随访状态0未填写1已填写2已删除	
	private String surveyId;		//随访id
	private String surveyTaskId;		//任务id
	private String surveyMoudleName; 	//随访模板名称
	
	public String getSurveyMoudleName() {
		return surveyMoudleName;
	}
	public void setSurveyMoudleName(String surveyMoudleName) {
		this.surveyMoudleName = surveyMoudleName;
	}
	public String getSurveyTaskId() {
		return surveyTaskId;
	}
	public void setSurveyTaskId(String surveyTaskId) {
		this.surveyTaskId = surveyTaskId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BigDecimal getUserId() {
		return userId;
	}
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	public BigDecimal getSurveryMoudleId() {
		return surveryMoudleId;
	}
	public void setSurveryMoudleId(BigDecimal surveryMoudleId) {
		this.surveryMoudleId = surveryMoudleId;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
}
