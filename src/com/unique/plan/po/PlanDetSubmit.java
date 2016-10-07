/**
 *
 */
package com.unique.plan.po;

import java.math.BigDecimal;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年11月13日下午1:40:46
 * 修改日期:2015年11月13日下午1:40:46
 */
public class PlanDetSubmit {
	public BigDecimal getOneBeginValue() {
		return oneBeginValue;
	}

	public void setOneBeginValue(BigDecimal oneBeginValue) {
		this.oneBeginValue = oneBeginValue;
	}

	public String getOneBeginSymbol() {
		return oneBeginSymbol;
	}

	public void setOneBeginSymbol(String oneBeginSymbol) {
		this.oneBeginSymbol = oneBeginSymbol;
	}

	public BigDecimal getOneKpiId() {
		return oneKpiId;
	}

	public void setOneKpiId(BigDecimal oneKpiId) {
		this.oneKpiId = oneKpiId;
	}

	public String getOneEndSymbol() {
		return oneEndSymbol;
	}

	public void setOneEndSymbol(String oneEndSymbol) {
		this.oneEndSymbol = oneEndSymbol;
	}

	public BigDecimal getOneEndValue() {
		return oneEndValue;
	}

	public void setOneEndValue(BigDecimal oneEndValue) {
		this.oneEndValue = oneEndValue;
	}

	public String getOneTwoRelation() {
		return oneTwoRelation;
	}

	public void setOneTwoRelation(String oneTwoRelation) {
		this.oneTwoRelation = oneTwoRelation;
	}

	public BigDecimal getTwoBeginValue() {
		return twoBeginValue;
	}

	public void setTwoBeginValue(BigDecimal twoBeginValue) {
		this.twoBeginValue = twoBeginValue;
	}

	public String getTwoBeginSymbol() {
		return twoBeginSymbol;
	}

	public void setTwoBeginSymbol(String twoBeginSymbol) {
		this.twoBeginSymbol = twoBeginSymbol;
	}

	public BigDecimal getTwoKpiId() {
		return twoKpiId;
	}

	public void setTwoKpiId(BigDecimal twoKpiId) {
		this.twoKpiId = twoKpiId;
	}

	public String getTwoEndSymbol() {
		return twoEndSymbol;
	}

	public void setTwoEndSymbol(String twoEndSymbol) {
		this.twoEndSymbol = twoEndSymbol;
	}

	public BigDecimal getTwoEndValue() {
		return twoEndValue;
	}

	public void setTwoEndValue(BigDecimal twoEndValue) {
		this.twoEndValue = twoEndValue;
	}

	public BigDecimal getAlertGrade() {
		return alertGrade;
	}

	public void setAlertGrade(BigDecimal alertGrade) {
		this.alertGrade = alertGrade;
	}
	public BigDecimal getTestTimeId() {
		return testTimeId;
	}

	public void setTestTimeId(BigDecimal testTimeId) {
		this.testTimeId = testTimeId;
	}

	public BigDecimal getPeriodType() {
		return periodType;
	}

	public void setPeriodType(BigDecimal periodType) {
		this.periodType = periodType;
	}

	public BigDecimal getPeriodValue() {
		return periodValue;
	}

	public void setPeriodValue(BigDecimal periodValue) {
		this.periodValue = periodValue;
	}
	public BigDecimal getTemplateDetId() {
		return templateDetId;
	}

	public void setTemplateDetId(BigDecimal templateDetId) {
		this.templateDetId = templateDetId;
	}

	public BigDecimal getTemplateDetRuleId() {
		return templateDetRuleId;
	}

	public void setTemplateDetRuleId(BigDecimal templateDetRuleId) {
		this.templateDetRuleId = templateDetRuleId;
	}
	
	private BigDecimal testTimeId; ///时段ID
	private BigDecimal periodType; ///周期类型 1周2日3月
	private BigDecimal periodValue; ///周期值
	
	private BigDecimal oneBeginValue;
	private String oneBeginSymbol;
	private BigDecimal oneKpiId;
	private String oneEndSymbol;
	private BigDecimal oneEndValue;
	
	private String oneTwoRelation;
	
	private BigDecimal twoBeginValue;
	private String twoBeginSymbol;
	private BigDecimal twoKpiId;
	private String twoEndSymbol;
	private BigDecimal twoEndValue;
	
	private BigDecimal alertGrade;
	private BigDecimal templateDetId;
	private BigDecimal templateDetRuleId;
}
