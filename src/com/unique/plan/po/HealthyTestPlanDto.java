/**
 * 2015年10月17日
 */
package com.unique.plan.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年10月17日 下午3:16:03<br/>
 * @author Administrator
 * @date 2015年10月17日
 * @description 
 */
public class HealthyTestPlanDto {
	
	private BigDecimal planId;
	private String staffName;	//医生名称
	private Date endTime;	//计划开始日期
	private Date firstTime;	//计划结束日期
	private String testName;	//检查项目
	private String status;		//状态
	private String periodTime;	//时段
	private String periodType;		//周期值类型1周2日3月
	private String periodValue;		//频率
	
	public BigDecimal getPlanId() {
		return planId;
	}
	public void setPlanId(BigDecimal planId) {
		this.planId = planId;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	 
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getFirstTime() {
		return firstTime;
	}
	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPeriodTime() {
		return periodTime;
	}
	public void setPeriodTime(String periodTime) {
		this.periodTime = periodTime;
	}
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	public String getPeriodValue() {
		return periodValue;
	}
	public void setPeriodValue(String periodValue) {
		this.periodValue = periodValue;
	}
}
