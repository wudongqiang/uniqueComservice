package com.unique.mb.po;

import java.math.BigDecimal;

/**
 * 测试实体
 * 创建人:azq
 */
public class UserBloodSugarTestByTime {
	private String h; ///小时
	private String d;///天
	private BigDecimal max_sugar_value;	//最大值血糖   max min都是同一个值
    private BigDecimal min_sugar_value; 	//最小值血糖   max min都是同一个值
    private BigDecimal testTimeId;
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
	
	public BigDecimal getMax_sugar_value() {
		return max_sugar_value;
	}
	public void setMax_sugar_value(BigDecimal max_sugar_value) {
		this.max_sugar_value = max_sugar_value;
	}
	public BigDecimal getMin_sugar_value() {
		return min_sugar_value;
	}
	public void setMin_sugar_value(BigDecimal min_sugar_value) {
		this.min_sugar_value = min_sugar_value;
	}
	public BigDecimal getTestTimeId() {
		return testTimeId;
	}
	public void setTestTimeId(BigDecimal testTimeId) {
		this.testTimeId = testTimeId;
	}
	
}