/**
 *
 */
package com.unique.survey.po;

import java.math.BigDecimal;

/**
 * 用户填写随访后的答案
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年8月27日上午11:00:52
 * 修改日期:2015年8月27日上午11:00:52
 */
public class SurveyAnswer {
	private BigDecimal id; ///题目ID
	private String type; ///类型1 普通项，2 检测项
	private String surveyItemType; ///1单选 2多选 3填空 4有无型
	private BigDecimal numValue; ///数值结果
	private String strValue; ///文本结果
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSurveyItemType() {
		return surveyItemType;
	}
	public void setSurveyItemType(String surveyItemType) {
		this.surveyItemType = surveyItemType;
	}
	public BigDecimal getNumValue() {
		return numValue;
	}
	public void setNumValue(BigDecimal numValue) {
		this.numValue = numValue;
	}
	public String getStrValue() {
		return strValue;
	}
	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}
}
