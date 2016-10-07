package com.unique.plan.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年10月19日 下午4:03:04<br/>
 * @author Administrator
 * @date 2015年10月19日
 * @description 
 */
public class HealthyTestPlanTask {
	
	private BigDecimal planTaskId;		//监测任务id
	private BigDecimal userId;			//用户id
	private BigDecimal kpiId;			//项目id
	private BigDecimal planId;			//监测计划id
	private Date excTime;				//执行时间
	private String excStatus;			//执行状态(1 未执行,2 已执行,3 作废)
	private Date createTime;			//创建日期
	private Date opTime;				//操作日期
	private BigDecimal operator;		//操作人
	private BigDecimal planDetId;		//检测计划明细id
	
	private String KpiTitle;
	private String kpiCode;
	private String unit;	//单位
	
	private String testValue;//测试值
	
	public String getTestValue() {
		return testValue;
	}

	public void setTestValue(String testValue) {
		this.testValue = testValue;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public BigDecimal getPlanTaskId() {
		return planTaskId;
	}

	public void setPlanTaskId(BigDecimal planTaskId) {
		this.planTaskId = planTaskId;
	}

	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public BigDecimal getKpiId() {
		return kpiId;
	}

	public void setKpiId(BigDecimal kpiId) {
		this.kpiId = kpiId;
	}

	public BigDecimal getPlanId() {
		return planId;
	}

	public void setPlanId(BigDecimal planId) {
		this.planId = planId;
	}

	public Date getExcTime() {
		return excTime;
	}

	public void setExcTime(Date excTime) {
		this.excTime = excTime;
	}

	public String getExcStatus() {
		return excStatus;
	}

	public void setExcStatus(String excStatus) {
		this.excStatus = excStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public BigDecimal getOperator() {
		return operator;
	}

	public void setOperator(BigDecimal operator) {
		this.operator = operator;
	}

	public BigDecimal getPlanDetId() {
		return planDetId;
	}

	public void setPlanDetId(BigDecimal planDetId) {
		this.planDetId = planDetId;
	}

	public String getKpiTitle() {
		return KpiTitle;
	}

	public void setKpiTitle(String kpiTitle) {
		KpiTitle = kpiTitle;
	}
	 
}
