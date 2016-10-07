package com.unique.mb.po;

import java.math.BigDecimal;

/**
 * 测试实体
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年9月2日上午11:26:31
 * 修改日期:2015年9月2日上午11:26:31
 */
public class UserHypertensionTestByTime {
	private String h; ///小时
	private String d;///天
	private BigDecimal maxHighTension;///最大高压
	private BigDecimal minHighTension;///最小高压
	private BigDecimal maxLowTension;///最大低压
	private BigDecimal minLowTension;///最小低压
	private BigDecimal maxHeartRate;///最大心率
	private BigDecimal minHeartRate;///最小心率
	public String getH() {
		return h;
	}
	public void setH(String h) {
		this.h = h;
	}
	public String getD() {
		return d;
	}
	public void setD(String d) {
		this.d = d;
	}
	public BigDecimal getMaxHighTension() {
		return maxHighTension;
	}
	public void setMaxHighTension(BigDecimal maxHighTension) {
		this.maxHighTension = maxHighTension;
	}
	public BigDecimal getMinHighTension() {
		return minHighTension;
	}
	public void setMinHighTension(BigDecimal minHighTension) {
		this.minHighTension = minHighTension;
	}
	public BigDecimal getMaxLowTension() {
		return maxLowTension;
	}
	public void setMaxLowTension(BigDecimal maxLowTension) {
		this.maxLowTension = maxLowTension;
	}
	public BigDecimal getMinLowTension() {
		return minLowTension;
	}
	public void setMinLowTension(BigDecimal minLowTension) {
		this.minLowTension = minLowTension;
	}
	public BigDecimal getMaxHeartRate() {
		return maxHeartRate;
	}
	public void setMaxHeartRate(BigDecimal maxHeartRate) {
		this.maxHeartRate = maxHeartRate;
	}
	public BigDecimal getMinHeartRate() {
		return minHeartRate;
	}
	public void setMinHeartRate(BigDecimal minHeartRate) {
		this.minHeartRate = minHeartRate;
	}
}