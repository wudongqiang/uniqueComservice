/**
 *
 */
package com.unique.order.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年10月17日下午4:39:35
 * 修改日期:2015年10月17日下午4:39:35
 */
public class OrgPatient {
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getHisUserId() {
		return hisUserId;
	}
	public void setHisUserId(String hisUserId) {
		this.hisUserId = hisUserId;
	}
    public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	private String userName;
    private String userPhone;
    private String idCard;
    private String hisUserId;
	private String sex;
    private String cardType;
    private String cardNo;
}
