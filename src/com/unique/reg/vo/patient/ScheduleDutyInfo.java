package com.unique.reg.vo.patient;

import java.util.Date;

/**
 * 用户日程vo对象
 * @author Administrator
 *
 */
public class ScheduleDutyInfo {

	private String dutyStatus; //排班情况 1 未排班 2 有排班
	private String periodName; //时段名称 上午、下午、晚上
	private Date dutyDate;	//排班时间
	private String regCount;   //预约数
	private String regNum;  //最大挂号数（总号数）
	private String periodId; //时段id
	
	public String getPeriodId() {
		return periodId;
	}
	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}
	public String getRegNum() {
		return regNum;
	}
	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}
	public String getDutyStatus() {
		return dutyStatus;
	}
	public void setDutyStatus(String dutyStatus) {
		this.dutyStatus = dutyStatus;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public Date getDutyDate() {
		return dutyDate;
	}
	public void setDutyDate(Date dutyDate) {
		this.dutyDate = dutyDate;
	}
	public String getRegCount() {
		return regCount;
	}
	public void setRegCount(String regCount) {
		this.regCount = regCount;
	}
}
