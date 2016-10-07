package com.unique.reg.orgpo;

/**
 * 医院方挂号记录对象
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年9月18日上午10:36:55
 * 修改日期:2015年9月18日上午10:36:55
 */
public class Reg4Org {
	private String uuid;	///挂号流水号
	private String patientId;
	
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFriendId() {
		return friendId;
	}
	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}
	public String getDutyId() {
		return dutyId;
	}
	public void setDutyId(String dutyId) {
		this.dutyId = dutyId;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getSafetyCode() {
		return safetyCode;
	}
	public void setSafetyCode(String safetyCode) {
		this.safetyCode = safetyCode;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getNativeHeath() {
		return nativeHeath;
	}
	public void setNativeHeath(String nativeHeath) {
		this.nativeHeath = nativeHeath;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public String getFlowNo() {
		return flowNo;
	}
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	
	public Integer getIsBund() {
		return isBund;
	}
	public void setIsBund(Integer isBund) {
		this.isBund = isBund;
	}

	private String userId;	///用户ID，平台就诊用户ID	
	private String friendId;
	private String dutyId;	///排班ID	
	private String cardCode;	///病人就诊卡号	
	private String safetyCode;	///医保号	
	private String idCard;	///身份证号	
	private String userName;	///姓名	
	private String typeName;	///病人类别名称（如：医保、自费）不能为空 默认为自费	
	private String sex;	///性别（1男，2女，3未知，9不明）	
	private String unitName;	///单位名称	
	private String birth;	///出生日期	yyyy-MM-dd
	private String nativeHeath;	///出生地	
	private String nation;	///民族 （直接传中文名称。如：汉族、壮族；）	
	private String address;	///通讯地址	
	private String phone;	///联系电话	
	private String payWay;	///支付方式代码 A0 支付宝 A1 银行卡 A2 微信 A3 信用卡 A4  APP预交金 A5 其他	
	private String flowNo; ///	交易流水号（APP流水号，用于对账）, 平台订单号	
	private Integer isBund; //0 不绑定 1 绑定 

}
