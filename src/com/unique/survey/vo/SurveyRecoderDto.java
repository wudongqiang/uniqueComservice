package com.unique.survey.vo;

import java.util.Date;

public class SurveyRecoderDto {
	
	private String surveyId;		//随访记录Id
	private Date  sruveyDate;		//随访时间
	private String surveyMoudleId;	//模板Id
	private int status; 			//0 未填写 1已填写 2已删除 3未到期
	
	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	public Date getSruveyDate() {
		return sruveyDate;
	}
	public void setSruveyDate(Date sruveyDate) {
		this.sruveyDate = sruveyDate;
	}
	public String getSurveyMoudleId() {
		return surveyMoudleId;
	}
	public void setSurveyMoudleId(String surveyMoudleId) {
		this.surveyMoudleId = surveyMoudleId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}