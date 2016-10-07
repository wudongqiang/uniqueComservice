package com.unique.org.vo.period;

import java.math.BigDecimal;
import java.util.List;

public class PeriodInfo {
	
	private List<BigDecimal> dutyPeriodIdList;
	private BigDecimal dutyId;
	private String startTime;
	private String endTime;
	private BigDecimal regNum;
	private String regTime;
	private BigDecimal remains;
	
	public List<BigDecimal> getDutyPeriodIdList() {
		return dutyPeriodIdList;
	}
	public void setDutyPeriodIdList(List<BigDecimal> dutyPeriodIdList) {
		this.dutyPeriodIdList = dutyPeriodIdList;
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
	public BigDecimal getRemains() {
		return remains;
	}
	public void setRemains(BigDecimal remains) {
		this.remains = remains;
	}
	
	
}
