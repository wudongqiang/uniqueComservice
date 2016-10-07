package com.unique.patient.po;

import java.util.Date;

/**
 * 医患关系申请对象<br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年10月13日 上午11:09:01<br/>
 * @author Administrator
 * @date 2015年10月13日
 * @description
 */
public class UserDoctorRelationApply implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias	
	public static final String TABLE_SQL_NAME = "USER_DOCTOR_RELATION_APPLY";
	
	public static final String ALIAS_APPLY_ID = "申请ID";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_USER_NAME = "申请用户姓名";
	public static final String ALIAS_STAFF_USER_ID = "医生的用户ID";
	public static final String ALIAS_STAFF_NAME = "医生名称";
	public static final String ALIAS_APPLY_TIME = "申请时间";
	public static final String ALIAS_STATUS = "状态(1申请中2已同意3已拒绝)";
	public static final String ALIAS_AGREE_TIME = "同意时间";
	public static final String ALIAS_AGREE_USER_ID = "同意人ID";
	public static final String ALIAS_REFUSE_TIME = "拒绝时间";
	public static final String ALIAS_REFUSE_REASON = "拒绝原因";
	public static final String ALIAS_REFUSE_USER_ID = "拒绝人ID";
	
	//the column is a primary key	
	public static final String PROP_KEY = "applyId";
		
	//fields START
	public static String PROP_REF="UserDoctorRelationApply";
	public static String PROP_REF_APPLY_ID = "applyId";
	
	public static String PROP_REF_USER_ID = "userId";
	
	public static String PROP_REF_USER_NAME = "userName";
	
	public static String PROP_REF_STAFF_USER_ID = "staffUserId";
	
	public static String PROP_REF_STAFF_NAME = "staffName";
	
	public static String PROP_REF_APPLY_TIME_BEGIN = "applyTimeBegin";
	public static String PROP_REF_APPLY_TIME_END = "applyTimeEnd";
	
	public static String PROP_REF_STATUS = "status";
	
	public static String PROP_REF_AGREE_TIME_BEGIN = "agreeTimeBegin";
	public static String PROP_REF_AGREE_TIME_END = "agreeTimeEnd";
	
	public static String PROP_REF_AGREE_USER_ID = "agreeUserId";
	
	public static String PROP_REF_REFUSE_TIME_BEGIN = "refuseTimeBegin";
	public static String PROP_REF_REFUSE_TIME_END = "refuseTimeEnd";
	
	public static String PROP_REF_REFUSE_REASON = "refuseReason";
	
	public static String PROP_REF_REFUSE_USER_ID = "refuseUserId";
	
	//fields END
	
	//columns sql name START	
	public static String PROP_COLUMN_APPLY_ID = "APPLY_ID";
	public static String PROP_COLUMN_USER_ID = "USER_ID";
	public static String PROP_COLUMN_USER_NAME = "USER_NAME";
	public static String PROP_COLUMN_STAFF_USER_ID = "STAFF_USER_ID";
	public static String PROP_COLUMN_STAFF_NAME = "STAFF_NAME";
	public static String PROP_COLUMN_APPLY_TIME = "APPLY_TIME";
	public static String PROP_COLUMN_STATUS = "STATUS";
	public static String PROP_COLUMN_AGREE_TIME = "AGREE_TIME";
	public static String PROP_COLUMN_AGREE_USER_ID = "AGREE_USER_ID";
	public static String PROP_COLUMN_REFUSE_TIME = "REFUSE_TIME";
	public static String PROP_COLUMN_REFUSE_REASON = "REFUSE_REASON";
	public static String PROP_COLUMN_REFUSE_USER_ID = "REFUSE_USER_ID";
	//columns sql name END
		
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//
	private java.math.BigDecimal applyId;
	//
	private java.math.BigDecimal userId;
	//@Length(max=32)
	private java.lang.String userName;
	//@NotNull 
	private java.math.BigDecimal staffUserId;
	//@Length(max=32)
	private java.lang.String staffName;
	//@NotNull 
	private Date applyTime;
	//
	private java.math.BigDecimal status;
	//
	private Date agreeTime;
	//
	private java.math.BigDecimal agreeUserId;
	//
	private Date refuseTime;
	//@Length(max=256)
	private java.lang.String refuseReason;
	//
	private java.math.BigDecimal refuseUserId;
	
	private String applyCon;//申请附加消息
	private String applyWay;//申请方式1用户申请医生2医生申请用户
	
	//columns END
	
	public UserDoctorRelationApply(){
	}

	public UserDoctorRelationApply(
		java.math.BigDecimal applyId
	){
		this.applyId = applyId;
	}

	public String getApplyWay() {
		return applyWay;
	}

	public void setApplyWay(String applyWay) {
		this.applyWay = applyWay;
	}

	public String getApplyCon() {
		return applyCon;
	}

	public void setApplyCon(String applyCon) {
		this.applyCon = applyCon;
	}

	public void setApplyId(java.math.BigDecimal value) {
		this.applyId = value;
	}
	
	public java.math.BigDecimal getApplyId() {
		return this.applyId;
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
	public void setStaffUserId(java.math.BigDecimal value) {
		this.staffUserId = value;
	}
	
	public java.math.BigDecimal getStaffUserId() {
		return this.staffUserId;
	}
	public void setStaffName(java.lang.String value) {
		this.staffName = value;
	}
	
	public java.lang.String getStaffName() {
		return this.staffName;
	}
	
	public void setApplyTime(Date value) {
		this.applyTime = value;
	}
	
	public Date getApplyTime() {
		return this.applyTime;
	}
	public void setStatus(java.math.BigDecimal value) {
		this.status = value;
	}
	
	public java.math.BigDecimal getStatus() {
		return this.status;
	}
	
	public void setAgreeTime(Date value) {
		this.agreeTime = value;
	}
	
	public Date getAgreeTime() {
		return this.agreeTime;
	}
	public void setAgreeUserId(java.math.BigDecimal value) {
		this.agreeUserId = value;
	}
	
	public java.math.BigDecimal getAgreeUserId() {
		return this.agreeUserId;
	}
	
	public void setRefuseTime(Date value) {
		this.refuseTime = value;
	}
	
	public Date getRefuseTime() {
		return this.refuseTime;
	}
	public void setRefuseReason(java.lang.String value) {
		this.refuseReason = value;
	}
	
	public java.lang.String getRefuseReason() {
		return this.refuseReason;
	}
	public void setRefuseUserId(java.math.BigDecimal value) {
		this.refuseUserId = value;
	}
	
	public java.math.BigDecimal getRefuseUserId() {
		return this.refuseUserId;
	}
}

