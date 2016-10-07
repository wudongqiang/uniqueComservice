package com.unique.org.vo.bydoctor;

import java.math.BigDecimal;
import java.util.LinkedList;

public class DoctorSchedule {
	private BigDecimal orgId;
	private BigDecimal depId;
	private BigDecimal staffId;
	private String staffName;
	
	private String staffLabelName;
	
	private String gootAt;
	
	private LinkedList<ScheduleInfo> scheduleInfoList;
	private String staffIcon;

	public BigDecimal getOrgId() {
		return orgId;
	}

	public void setOrgId(BigDecimal orgId) {
		this.orgId = orgId;
	}

	public BigDecimal getDepId() {
		return depId;
	}

	public void setDepId(BigDecimal depId) {
		this.depId = depId;
	}

	public BigDecimal getStaffId() {
		return staffId;
	}

	public void setStaffId(BigDecimal staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}


	public String getStaffLabelName() {
		return staffLabelName;
	}

	public void setStaffLabelName(String staffLabelName) {
		this.staffLabelName = staffLabelName;
	}


	public String getGootAt() {
		return gootAt;
	}

	public void setGootAt(String gootAt) {
		this.gootAt = gootAt;
	}

	public LinkedList<ScheduleInfo> getScheduleInfoList() {
		return scheduleInfoList;
	}

	public void setScheduleInfoList(LinkedList<ScheduleInfo> scheduleInfoList) {
		this.scheduleInfoList = scheduleInfoList;
	}

	public void setStaffIcon(String staffIcon) {
		this.staffIcon = staffIcon;
	}
	
	public String getStaffIcon() {
		return staffIcon;
	}
	
	
	
}
