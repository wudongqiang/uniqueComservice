package com.unique.mb.po;

import java.math.BigDecimal;

/**
 * 用户高血压检测dto
 * <br/>创建人 azq
 * @date 2015年8月26日
 * @description
 */
public class UserBloodSugarTestDto {

	private String date; 				//日期
    
	private BigDecimal max_sugar_value;	//最大值血糖   max min都是同一个值
    private BigDecimal min_sugar_value; 	//最小值血糖   max min都是同一个值
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	

}

