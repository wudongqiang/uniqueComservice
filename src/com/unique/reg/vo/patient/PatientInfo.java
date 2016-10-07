package com.unique.reg.vo.patient;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 患者信息
 * @author Administrator
 *
 */
public class PatientInfo {

	private BigDecimal friendId; //患者Id
	private String userName;	//患者名
	private String sex;			//患者性别
	private int age;			//患者年龄
	private Date orderTime;		//就诊时间
	private String depName;		//就诊部门- 科室
	
	public BigDecimal getFriendId() {
		return friendId;
	}
	public void setFriendId(BigDecimal friendId) {
		this.friendId = friendId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	
}
