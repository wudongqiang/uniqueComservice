package com.unique.org.vo;

import java.math.BigDecimal;

public class ScheduleResultMap {
	
	private BigDecimal dutyId;
	private String uuid;
	private String dutyDate;  //排班时间  yyyy-mm-dd
	private String periodName; //时段名称
	private String periodId; //时段id
	private BigDecimal staffId;
	private BigDecimal depId;
	private BigDecimal orgId;
	private BigDecimal amount;
	private BigDecimal regNum;
	private BigDecimal regNumRemain;
	private String staffName;
	private String gootAt;
	private String staffLabelName;
	private String staffIcon;
	
	public String getStaffIcon() {
		return staffIcon;
	}
	public void setStaffIcon(String staffIcon) {
		this.staffIcon = staffIcon;
	}
	public BigDecimal getDutyId() {
		return dutyId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public void setDutyId(BigDecimal dutyId) {
		this.dutyId = dutyId;
	}
	public String getDutyDate() {
		return dutyDate;
	}
	public void setDutyDate(String dutyDate) {
		this.dutyDate = dutyDate;
	}
	public String getPeriodId() {
		return periodId;
	}
	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public BigDecimal getStaffId() {
		return staffId;
	}
	public void setStaffId(BigDecimal staffId) {
		this.staffId = staffId;
	}
	public BigDecimal getDepId() {
		return depId;
	}
	public void setDepId(BigDecimal depId) {
		this.depId = depId;
	}
	public BigDecimal getOrgId() {
		return orgId;
	}
	public void setOrgId(BigDecimal orgId) {
		this.orgId = orgId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getGootAt() {
		return gootAt;
	}
	public void setGootAt(String gootAt) {
		this.gootAt = gootAt;
	}
	public String getStaffLabelName() {
		return staffLabelName;
	}
	public void setStaffLabelName(String staffLabelName) {
		this.staffLabelName = staffLabelName;
	}
	
}
