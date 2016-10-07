package com.unique.survey.vo;

import java.util.ArrayList;
import java.util.List;

import com.unique.survey.po.SurveyMoudle;

/**
 * 随访模板对象 按层次 <br/>
 * 创建人 wdq <br/>
 * 创建时间 2015年8月21日 下午4:52:08
 * 
 * @author Administrator
 * @date 2015年8月21日
 * @description
 */
public class SurveyMoudleDto {

	private String illName; // 疾病名称
	private List<SurveyMoudle> surveyMoudles = new ArrayList<SurveyMoudle>(); // 模板集

	public String getIllName() {
		return illName;
	}

	public void setIllName(String illName) {
		this.illName = illName;
	}

	public List<SurveyMoudle> getSurveyMoudles() {
		return surveyMoudles;
	}

	public void setSurveyMoudles(List<SurveyMoudle> surveyMoudles) {
		this.surveyMoudles = surveyMoudles;
	}

}
