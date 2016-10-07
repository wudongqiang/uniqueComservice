/**
 *
 */
package com.unique.plan.po;

import java.math.BigDecimal;
import java.util.List;

/**
 * 检测计划提交实体
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年11月13日下午1:38:19
 * 修改日期:2015年11月13日下午1:38:19
 */
public class PlanSubmit {
	public BigDecimal getTemplateId() {
		return templateId;
	}
	public void setTemplateId(BigDecimal templateId) {
		this.templateId = templateId;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	private BigDecimal templateId; ///模板ID
	private String beginTime; ///开始时间yyyy-MM-dd
	private String endTime;///结束时间yyyy-MM-dd
}
