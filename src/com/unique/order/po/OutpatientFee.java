/**
 *
 */
package com.unique.order.po;

import java.math.BigDecimal;

/**
 * 门诊费用
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年9月23日上午11:16:58
 * 修改日期:2015年9月23日上午11:16:58
 */
public class OutpatientFee {

	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getAllCost() {
		return allCost;
	}
	public void setAllCost(String allCost) {
		this.allCost = allCost;
	}
	public String getSelfCost() {
		return selfCost;
	}
	public void setSelfCost(String selfCost) {
		this.selfCost = selfCost;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	public String getVisitNo() {
		return visitNo;
	}
	public void setVisitNo(String visitNo) {
		this.visitNo = visitNo;
	}
	private String itemName; ///项目名称
	private String applyNo;	///申请单号
	private String allCost;	///总费用
	private String selfCost;	///病人自付费用
	private String flag;	///缴费标志  ’N’未缴费
	private String patientId;	///病人Id
	private String applyTime;	///申请时间
	private String costType;	///费用类型  1药费，2诊疗
	private String visitNo;	///门诊挂号号

}
