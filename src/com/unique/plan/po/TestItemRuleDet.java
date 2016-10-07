package com.unique.plan.po;

import java.math.BigDecimal;


/**
 * 健康检测计划明细<br/>
 * 创建人 azq<br/>
 * 创建时间 2015年10月17日 下午12:57:32<br/>
 * @author Administrator
 * @date 2015年10月17日
 * @description
 */
public class TestItemRuleDet {
	public String getReferenceRuleDetId() {
		return referenceRuleDetId;
	}
	public void setReferenceRuleDetId(String referenceRuleDetId) {
		this.referenceRuleDetId = referenceRuleDetId;
	}
	public BigDecimal getKpiId() {
		return kpiId;
	}
	public void setKpiId(BigDecimal kpiId) {
		this.kpiId = kpiId;
	}
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
	public String getDoctorAlertCon() {
		return doctorAlertCon;
	}
	public void setDoctorAlertCon(String doctorAlertCon) {
		this.doctorAlertCon = doctorAlertCon;
	}
	public String getUserAlertResult() {
		return userAlertResult;
	}
	public void setUserAlertResult(String userAlertResult) {
		this.userAlertResult = userAlertResult;
	}
	public String getUserHealthySuggest() {
		return userHealthySuggest;
	}
	public void setUserHealthySuggest(String userHealthySuggest) {
		this.userHealthySuggest = userHealthySuggest;
	}
	private String referenceRuleDetId;
	
	private BigDecimal kpiId;
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
	private String doctorAlertCon;
	private String userAlertResult;
	private String userHealthySuggest;
	
	
}

