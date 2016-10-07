package com.unique.reg.vo.patient;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  挂号查询信息
 * @author Administrator
 *
 */
public class McRegInfo {

	private String friendId;	//患者ID
	private String userName;	//用户名字
	private String userSex;		//用户性别
	private int userAge;		//用户年龄
	private String isUsed;		//挂号记录状态
	private BigDecimal regNumber;//就诊序号
	private String periodName;	//时段名称
	private Date periodStartTime;//开始时间
	private Date periodEndTime;	//结束时间
	private int sortRank; 		//排序
	private String status; 		//排序
	private String orderTime; 		//就诊时间
	
	
	public String getFriendId() {
		return friendId;
	}
	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public int getUserAge() {
		return userAge;
	}
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	public String getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}
	public BigDecimal getRegNumber() {
		return regNumber;
	}
	public void setRegNumber(BigDecimal regNumber) {
		this.regNumber = regNumber;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public Date getPeriodStartTime() {
		return periodStartTime;
	}
	public void setPeriodStartTime(Date periodStartTime) {
		this.periodStartTime = periodStartTime;
	}
	public Date getPeriodEndTime() {
		return periodEndTime;
	}
	public void setPeriodEndTime(Date periodEndTime) {
		this.periodEndTime = periodEndTime;
	}
	public int getSortRank() {
		return sortRank;
	}
	public void setSortRank(int sortRank) {
		this.sortRank = sortRank;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
}
