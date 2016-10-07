/**
 * 2015年9月10日
 */
package com.unique.reg.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <br/>
 * 创建人 wdq <br/>
 * 创建时间 2015年9月10日 下午4:25:54
 * 
 * @author Administrator
 * @date 2015年9月10日
 * @description
 */
public class MyReservation {
	
	private BigDecimal regId; 	// 挂号记录
	private String orgName; 	// 机构名称
	private String staffName; 	// 医生名称
	private String depName; 	// 科室名称
	private Date orderTime; 	// 就诊时间
	private String isUsed; 		// 挂号状态
	private String friedUserName; // 就诊人
	private String parentId; 		// 上级部门Id（区分普通和VIP）
	private BigDecimal periodId; 	//时段id
	private String periodName; 	//时段名称
	private String status; 		// 挂号支付状态

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getRegId() {
		return regId;
	}

	public void setRegId(BigDecimal regId) {
		this.regId = regId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	public String getFriedUserName() {
		return friedUserName;
	}

	public void setFriedUserName(String friedUserName) {
		this.friedUserName = friedUserName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public BigDecimal getPeriodId() {
		return periodId;
	}

	public void setPeriodId(BigDecimal periodId) {
		this.periodId = periodId;
	}

	public String getPeriodName() {
		return periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

}
