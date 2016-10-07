/**
 * 2015年9月22日
 */
package com.unique.reg.vo;

import java.math.BigDecimal;

import com.unique.core.util.FileUtil;
import com.unique.core.util.StringUtil;

/**
 * 预约详情<br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年9月22日 上午10:56:11<br/>
 * @author Administrator
 * @date 2015年9月22日
 * @description 
 */
public class ReservationDetails {

	private String orgName;			//医院名称
	private BigDecimal orgId;		//医院Id
	private String depName;			//科室名称
	private String staffName;		//医生姓名
	private String orderTime;		//就诊时间
	private String patientName;		//就诊人
	private String sex;				//性别
	private String telPhone;		//电话号码
	private String idCard;			//身份证
	private String cardType;		//卡类型
	private String cardNo;			//卡号
	
	private BigDecimal regId;		//挂号id
	private String isUsed;			//E挂号失败,U预约未付费,0可取,1已取,2已退号,3过期,4爽约,R退号失败，W挂号中
	
	private String reservationNo;	//预约码
	private String periodName;		//时段名称
	private BigDecimal periodId;	//时段id
	
	private String imageUrl;		//头像
	private String hisUserId;		

	//图片服务器
  	private final static String PIC_SERVER = FileUtil.readProperties("comservice.properties", "picServerOut");
	
	public String getHisUserId() {
		return hisUserId;
	}
	public void setHisUserId(String hisUserId) {
		this.hisUserId = hisUserId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		if(!StringUtil.isEmpty(imageUrl)){
			this.imageUrl = PIC_SERVER+imageUrl;
		}
	}

	public String getPeriodName() {
		return periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	public BigDecimal getPeriodId() {
		return periodId;
	}

	public void setPeriodId(BigDecimal periodId) {
		this.periodId = periodId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public BigDecimal getOrgId() {
		return orgId;
	}

	public void setOrgId(BigDecimal orgId) {
		this.orgId = orgId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public BigDecimal getRegId() {
		return regId;
	}

	public void setRegId(BigDecimal regId) {
		this.regId = regId;
	}

	public String getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	public String getReservationNo() {
		return reservationNo;
	}

	public void setReservationNo(String reservationNo) {
		this.reservationNo = reservationNo;
	}
}
