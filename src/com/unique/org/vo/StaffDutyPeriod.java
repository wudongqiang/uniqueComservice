/**
 * 2015年9月9日
 */
package com.unique.org.vo;

import java.math.BigDecimal;

/** 
 * 医生排班时段-某医生近7天的排班时段信息
 * <br/>创建人 wdq
 * <br/>创建时间 2015年9月9日 下午12:02:23
 * @author Administrator
 * @date 2015年9月9日
 * @description 
 */
public class StaffDutyPeriod {

	private BigDecimal dutyId;	 	//排班id
//	private String periodTime;		//时段
	private String dutyDate;		//排班时间
	private String regNumRemain;	//剩余号数
	private String periodName;		//时段名称
	private BigDecimal periodId;	//时段id
	
	private BigDecimal amount;		//价格
	private String staffLableName;	//号别
	
	public String getPeriodName() {
		return periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	public String getDutyDate() {
		return dutyDate;
	}

	public void setDutyDate(String dutyDate) {
		this.dutyDate = dutyDate;
	}

	public BigDecimal getDutyId() {
		return dutyId;
	}

	public void setDutyId(BigDecimal dutyId) {
		this.dutyId = dutyId;
	}

//	public String getPeriodTime() {
//		return periodTime;
//	}
//
//	public void setPeriodTime(String periodTime) {
//		this.periodTime = periodTime;
//	}

	public String getRegNumRemain() {
		return regNumRemain;
	}

	public void setRegNumRemain(String regNumRemain) {
		this.regNumRemain = regNumRemain;
	}

	public BigDecimal getPeriodId() {
		return periodId;
	}

	public void setPeriodId(BigDecimal periodId) {
		this.periodId = periodId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStaffLableName() {
		return staffLableName;
	}

	public void setStaffLableName(String staffLableName) {
		this.staffLableName = staffLableName;
	}
	
}