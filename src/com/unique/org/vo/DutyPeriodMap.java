package com.unique.org.vo;

import java.math.BigDecimal;

public class DutyPeriodMap {

	private BigDecimal dutyPeriodId;
	private BigDecimal dutyId;
	private String startTime;
	private String endTime;
	private BigDecimal regNum;
	private String regTime;
	private String periodUniqueId;
	private BigDecimal remains;
	
	public BigDecimal getDutyPeriodId() {
		return dutyPeriodId;
	}
	public void setDutyPeriodId(BigDecimal dutyPeriodId) {
		this.dutyPeriodId = dutyPeriodId;
	}
	public BigDecimal getDutyId() {
		return dutyId;
	}
	public void setDutyId(BigDecimal dutyId) {
		this.dutyId = dutyId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public BigDecimal getRegNum() {
		return regNum;
	}
	public void setRegNum(BigDecimal regNum) {
		this.regNum = regNum;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public String getPeriodUniqueId() {
		return periodUniqueId;
	}
	public void setPeriodUniqueId(String periodUniqueId) {
		this.periodUniqueId = periodUniqueId;
	}
	public BigDecimal getRemains() {
		return remains;
	}
	public void setRemains(BigDecimal remains) {
		this.remains = remains;
	}
	
	
}
