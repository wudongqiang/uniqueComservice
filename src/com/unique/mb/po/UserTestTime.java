package com.unique.mb.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class UserTestTime {
	private BigDecimal testTimeId;
	private BigDecimal kpiId;
	private Date testBeginTime;
	private Date testEndTime;
	private BigDecimal referenceBegin;
	private BigDecimal referenceEnd;
	private Date alertTime;
	private String alertCon;
	private Date opTime;
	private BigDecimal opeator;
	private String testTimeName;
	
	private List<UserTestByTime> testTimes;
	public List<UserTestByTime> getTestTimes() {
		return testTimes;
	}
	public void setTestTimes(List<UserTestByTime> testTimes) {
		this.testTimes = testTimes;
	}
	public BigDecimal getTestTimeId() {
		return testTimeId;
	}
	public void setTestTimeId(BigDecimal testTimeId) {
		this.testTimeId = testTimeId;
	}
	public BigDecimal getKpiId() {
		return kpiId;
	}
	public void setKpiId(BigDecimal kpiId) {
		this.kpiId = kpiId;
	}
	public Date getTestBeginTime() {
		return testBeginTime;
	}
	public void setTestBeginTime(Date testBeginTime) {
		this.testBeginTime = testBeginTime;
	}
	public Date getTestEndTime() {
		return testEndTime;
	}
	public void setTestEndTime(Date testEndTime) {
		this.testEndTime = testEndTime;
	}
	public BigDecimal getReferenceBegin() {
		return referenceBegin;
	}
	public void setReferenceBegin(BigDecimal referenceBegin) {
		this.referenceBegin = referenceBegin;
	}
	public BigDecimal getReferenceEnd() {
		return referenceEnd;
	}
	public void setReferenceEnd(BigDecimal referenceEnd) {
		this.referenceEnd = referenceEnd;
	}
	public Date getAlertTime() {
		return alertTime;
	}
	public void setAlertTime(Date alertTime) {
		this.alertTime = alertTime;
	}
	public String getAlertCon() {
		return alertCon;
	}
	public void setAlertCon(String alertCon) {
		this.alertCon = alertCon;
	}
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	public BigDecimal getOpeator() {
		return opeator;
	}
	public void setOpeator(BigDecimal opeator) {
		this.opeator = opeator;
	}
	public String getTestTimeName() {
		return testTimeName;
	}
	public void setTestTimeName(String testTimeName) {
		this.testTimeName = testTimeName;
	}
	
}
