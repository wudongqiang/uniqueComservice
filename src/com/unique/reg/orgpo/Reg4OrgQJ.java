package com.unique.reg.orgpo;

import java.math.BigDecimal;
/**
 * 医院方挂号记录对象(綦江医院专用)
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年9月18日上午10:36:55
 * 修改日期:2015年9月18日上午10:36:55
 */
public class Reg4OrgQJ {
	private BigDecimal regId;
	private BigDecimal orgId;
	private String dutyUuid;
	private String periodUniqueId;
	private String userName;
	private String idCard;
	private String userSex;
	private String userPhone;
	private String userAddr;
	private String userBirth;
	private BigDecimal regNumber;
	private String regUniqueId;
	private String createTime;
	private BigDecimal userId;
	
	public BigDecimal getRegId() {
		return regId;
	}
	public void setRegId(BigDecimal regId) {
		this.regId = regId;
	}
	public BigDecimal getOrgId() {
		return orgId;
	}
	public void setOrgId(BigDecimal orgId) {
		this.orgId = orgId;
	}
	public String getDutyUuid() {
		return dutyUuid;
	}
	public void setDutyUuid(String dutyUuid) {
		this.dutyUuid = dutyUuid;
	}
	public String getPeriodUniqueId() {
		return periodUniqueId;
	}
	public void setPeriodUniqueId(String periodUniqueId) {
		this.periodUniqueId = periodUniqueId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserAddr() {
		return userAddr;
	}
	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}
	public String getUserBirth() {
		return userBirth;
	}
	public void setUserBirth(String userBirth) {
		this.userBirth = userBirth;
	}
	public BigDecimal getRegNumber() {
		return regNumber;
	}
	public void setRegNumber(BigDecimal regNumber) {
		this.regNumber = regNumber;
	}
	public String getRegUniqueId() {
		return regUniqueId;
	}
	public void setRegUniqueId(String regUniqueId) {
		this.regUniqueId = regUniqueId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public BigDecimal getUserId() {
		return userId;
	}
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
}
