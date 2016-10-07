package com.unique.reg.po;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* <p>com.unique.reg.po.McAppoint</p>
*
* @author wdq
* @version 1.0
* @since 1.0
* @purpose 约诊
*/
public class McAppoint implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias	
	public static final String TABLE_SQL_NAME = "MC_APPOINT";
	
	public static final String ALIAS_APPOINT_ID = "约诊ID";
	public static final String ALIAS_STAFF_ID = "员工ID";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_USER_NAME = "约诊用户名称";
	public static final String ALIAS_STAFF_NAME = "约诊医生名称";
	public static final String ALIAS_APPOINT_TIME = "期望就诊时间";
	public static final String ALIAS_SYMPTOM_DESC = "症状描述";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_AGREE_TIME = "同意时间";
	public static final String ALIAS_AGREE_USER_ID = "同意人用户ID";
	public static final String ALIAS_REFUSE_USER_ID = "拒绝人用户ID";
	public static final String ALIAS_REFUSE_TIME = "拒绝时间";
	public static final String ALIAS_REFUSE_CON = "拒绝理由";
	public static final String ALIAS_NOTICE_CON = "通知信息";
		
	//
	private java.math.BigDecimal appointId;
	//
	private java.math.BigDecimal staffId;
	private java.math.BigDecimal orgId;
	
	//诊疗方式(1电话约诊2线上约诊3上门约诊4医院约诊)
	private java.math.BigDecimal doWay;
	private java.lang.String doWayStr; //字符描述
	//
	private java.math.BigDecimal userId;
	//@Length(max=32)
	private java.lang.String userName;
	//@Length(max=32)
	private java.lang.String staffName;
	//@NotNull 
	private java.util.Date appointTime;
	//@Length(max=1024)
	private java.lang.String symptomDesc;
	//@NotNull 
	private java.util.Date createTime;
	//
	private java.math.BigDecimal status;
	//
	private java.util.Date agreeTime;
	//
	private java.math.BigDecimal agreeUserId;
	//
	private java.math.BigDecimal refuseUserId;
	//
	private java.util.Date refuseTime;
	//@Length(max=256)
	private java.lang.String refuseCon;
	//@Length(max=256)
	private java.lang.String noticeCon;
	//columns END
	
	private List<AppointImages> images = new ArrayList<AppointImages>(); 

	public List<AppointImages> getImages() {
		return images;
	}

	public void setImages(List<AppointImages> images) {
		this.images = images;
	}

	public McAppoint(){
	}

	public McAppoint(
		java.math.BigDecimal appointId
	){
		this.appointId = appointId;
	}

	public java.math.BigDecimal getDoWay() {
		return doWay;
	}

	public void setDoWay(java.math.BigDecimal doWay) {
		this.doWay = doWay;
	}

	public java.lang.String getDoWayStr() {
		//诊疗方式(1电话约诊2线上约诊3上门约诊4医院约诊)
		String doWayStr = "";
		if(getDoWay()!=null){
			switch (getDoWay().intValue()) {
			case 1:
				doWayStr  = "电话约诊";
				break;
			case 2:
				doWayStr  = "线上约诊";
				break;
			case 3:
				doWayStr  = "上门约诊";
				break;
			case 4:
				doWayStr  = "医院约诊";
				break;
			}
		}
		return doWayStr;
	}
	
	public java.math.BigDecimal getOrgId() {
		return orgId;
	}

	public void setOrgId(java.math.BigDecimal orgId) {
		this.orgId = orgId;
	}

	public void setAppointId(java.math.BigDecimal value) {
		this.appointId = value;
	}
	
	public java.math.BigDecimal getAppointId() {
		return this.appointId;
	}
	public void setStaffId(java.math.BigDecimal value) {
		this.staffId = value;
	}
	
	public java.math.BigDecimal getStaffId() {
		return this.staffId;
	}
	public void setUserId(java.math.BigDecimal value) {
		this.userId = value;
	}
	
	public java.math.BigDecimal getUserId() {
		return this.userId;
	}
	public void setUserName(java.lang.String value) {
		this.userName = value;
	}
	
	public java.lang.String getUserName() {
		return this.userName;
	}
	public void setStaffName(java.lang.String value) {
		this.staffName = value;
	}
	
	public java.lang.String getStaffName() {
		return this.staffName;
	}
	
	public void setAppointTime(java.util.Date value) {
		this.appointTime = value;
	}
	
	public java.util.Date getAppointTime() {
		return this.appointTime;
	}
	public void setSymptomDesc(java.lang.String value) {
		this.symptomDesc = value;
	}
	
	public java.lang.String getSymptomDesc() {
		return this.symptomDesc;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setStatus(java.math.BigDecimal value) {
		this.status = value;
	}
	
	public java.math.BigDecimal getStatus() {
		return this.status;
	}
	
	public void setAgreeTime(java.util.Date value) {
		this.agreeTime = value;
	}
	
	public java.util.Date getAgreeTime() {
		return this.agreeTime;
	}
	public void setAgreeUserId(java.math.BigDecimal value) {
		this.agreeUserId = value;
	}
	
	public java.math.BigDecimal getAgreeUserId() {
		return this.agreeUserId;
	}
	public void setRefuseUserId(java.math.BigDecimal value) {
		this.refuseUserId = value;
	}
	
	public java.math.BigDecimal getRefuseUserId() {
		return this.refuseUserId;
	}
	
	public void setRefuseTime(java.util.Date value) {
		this.refuseTime = value;
	}
	
	public java.util.Date getRefuseTime() {
		return this.refuseTime;
	}
	public void setRefuseCon(java.lang.String value) {
		this.refuseCon = value;
	}
	
	public java.lang.String getRefuseCon() {
		return this.refuseCon;
	}
	public void setNoticeCon(java.lang.String value) {
		this.noticeCon = value;
	}
	
	public java.lang.String getNoticeCon() {
		return this.noticeCon;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("AppointId",getAppointId())
			.append("StaffId",getStaffId())
			.append("UserId",getUserId())
			.append("UserName",getUserName())
			.append("StaffName",getStaffName())
			.append("AppointTime",getAppointTime())
			.append("SymptomDesc",getSymptomDesc())
			.append("CreateTime",getCreateTime())
			.append("Status",getStatus())
			.append("AgreeTime",getAgreeTime())
			.append("AgreeUserId",getAgreeUserId())
			.append("RefuseUserId",getRefuseUserId())
			.append("RefuseTime",getRefuseTime())
			.append("RefuseCon",getRefuseCon())
			.append("NoticeCon",getNoticeCon())
			.toString();
	}
}
