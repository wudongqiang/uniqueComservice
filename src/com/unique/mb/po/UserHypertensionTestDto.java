package com.unique.mb.po;

import java.math.BigDecimal;

/**
 * 用户高血压检测dto
 * <br/>创建人 wdq
 * <br/>创建时间 2015年8月26日 上午9:42:45
 * @author Administrator
 * @date 2015年8月26日
 * @description
 */
public class UserHypertensionTestDto {

	private String date; 				//日期
	
	private BigDecimal maxHighTension;		//最大值高压 
	private BigDecimal minHighTension;		//最小值高压
	
    private BigDecimal maxLowTension;		//最大值低压
    private BigDecimal minLowTension;		//最小值低压
    
    private BigDecimal maxHeartRate;	//最大值心率
    private BigDecimal minHeartRate; 	//最小值心率
    
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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

