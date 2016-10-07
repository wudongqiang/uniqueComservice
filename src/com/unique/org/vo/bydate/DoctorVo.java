package com.unique.org.vo.bydate;

import java.math.BigDecimal;

public class DoctorVo {
	
	private BigDecimal staffId;
	private BigDecimal dutyId;
	private String uuid;
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
	public BigDecimal getStaffId() {
		return staffId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	public BigDecimal getDutyId() {
		return dutyId;
	}
	public void setDutyId(BigDecimal dutyId) {
		this.dutyId = dutyId;
	}
	
}
