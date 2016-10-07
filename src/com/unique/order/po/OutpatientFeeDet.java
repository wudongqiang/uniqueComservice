/**
 *
 */
package com.unique.order.po;

/**
 * 门诊缴费明细
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年9月23日上午11:22:52
 * 修改日期:2015年9月23日上午11:22:52
 */
public class OutpatientFeeDet {
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
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
	private String applyNo;	///申请单号
	private String itemName;	///项目名称
	private String unit;	////单位
	private String quantity;	///数量
	private String allCost;	///总费用
	private String selfCost;	///病人自付费用
}
