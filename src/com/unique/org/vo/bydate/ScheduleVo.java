package com.unique.org.vo.bydate;

import java.util.LinkedList;

public class ScheduleVo {
	
	private String dutyDate;
	private String periodName;
	
	private LinkedList<DoctorVo> doctorList;


	public String getDutyDate() {
		return dutyDate;
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

	public LinkedList<DoctorVo> getDoctorList() {
		return doctorList;
	}

	public void setDoctorList(LinkedList<DoctorVo> doctorList) {
		this.doctorList = doctorList;
	}
	
	
}
