/**
 *
 */
package com.unique.survey.po;

import java.math.BigDecimal;
import java.util.List;

/**
 * 随访项
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年8月19日下午2:23:27
 * 修改日期:2015年8月19日下午2:23:27
 */
public class SurveyItem {
	private BigDecimal id;
	private String itemName; ///项目名
	private String type; ///类型1 普通项，2 检测项
	private String equpCode; ///设备编码
	private String equpName; ///设备名称
	private int inputType; /// 输入类型1 手动输入 2 自动输入
	private String unit; ///单位
	private String surveyItemType; ///1单选 2多选 3填空 4有无型
	
	private List<SurveyMoudleSubitem> moudleSubitems; ///随访选项
	private List<SurveyResult> results; ///随访结果集
	
	public List<SurveyResult> getResults() {
		return results;
	}
	public void setResults(List<SurveyResult> results) {
		this.results = results;
	}
	public String getSurveyItemType() {
		return surveyItemType;
	}
	public void setSurveyItemType(String surveyItemType) {
		this.surveyItemType = surveyItemType;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public List<SurveyMoudleSubitem> getMoudleSubitems() {
		return moudleSubitems;
	}
	public void setMoudleSubitems(List<SurveyMoudleSubitem> moudleSubitems) {
		this.moudleSubitems = moudleSubitems;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEqupCode() {
		return equpCode;
	}
	public void setEqupCode(String equpCode) {
		this.equpCode = equpCode;
	}
	public String getEqupName() {
		return equpName;
	}
	public void setEqupName(String equpName) {
		this.equpName = equpName;
	}
	public int getInputType() {
		return inputType;
	}
	public void setInputType(int inputType) {
		this.inputType = inputType;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
