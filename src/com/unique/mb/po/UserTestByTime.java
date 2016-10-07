package com.unique.mb.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 测试实体
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年9月2日上午11:26:31
 * 修改日期:2015年9月2日上午11:26:31
 */
public class UserTestByTime {
	public String getD() {
		return d;
	}
	public void setD(String d) {
		this.d = d;
	}
	public BigDecimal getH() {
		return h;
	}
	public void setH(BigDecimal h) {
		this.h = h;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	private String d;///天
	private BigDecimal h;///小时
	private BigDecimal value;///值
	private Date dValue;
	public Date getdValue() {
		return dValue;
	}
	public void setdValue(Date dValue) {
		this.dValue = dValue;
	}
}