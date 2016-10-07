package com.unique.org.vo.bydoctor;

import java.math.BigDecimal;


public class ScheduleInfo {
	
	private BigDecimal dutyId;
	private String Uuid;
	private String dutyDate;
	private String periodName;
	private BigDecimal regNum;
	private BigDecimal regNumRemain;
	private BigDecimal amount;
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getDutyId() {
		return dutyId;
	}
	public void setDutyId(BigDecimal dutyId) {
		this.dutyId = dutyId;
	}
	public String getDutyDate() {
		return dutyDate;
	}
	public String getUuid() {
		return Uuid;
	}
	public void setUuid(String uuid) {
		Uuid = uuid;
	}
	public void setDutyDate(String dutyDate) {
		this.dutyDate = dutyDate;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public BigDecimal getRegNum() {
		return regNum;
	}
	public void setRegNum(BigDecimal regNum) {
		this.regNum = regNum;
	}
	public BigDecimal getRegNumRemain() {
		return regNumRemain;
	}
	public void setRegNumRemain(BigDecimal regNumRemain) {
		this.regNumRemain = regNumRemain;
	}
	
	
}
