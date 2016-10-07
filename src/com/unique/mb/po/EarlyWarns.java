/**
 * 2015年11月25日
 */
package com.unique.mb.po;

import java.util.Date;

/**
 * 预警提醒<br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年11月25日 下午4:26:50<br/>
 * 
 * @author Administrator
 * @date 2015年11月25日
 * @description
 */
public class EarlyWarns {

	private String userName; // 用户名称
	private String userId; // 用户id
	private int status; // 状态 0 未处理 1已处理
	private String testResultDetId;// 测试结果详细Id
	private String earlyName; // 'Xx预警'
	private String result; // 结果
	private Date testTime; // 测试时间

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTestResultDetId() {
		return testResultDetId;
	}

	public void setTestResultDetId(String testResultDetId) {
		this.testResultDetId = testResultDetId;
	}

	public String getEarlyName() {
		return earlyName;
	}

	public void setEarlyName(String earlyName) {
		this.earlyName = earlyName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getTestTime() {
		return testTime;
	}

	public void setTestTime(Date testTime) {
		this.testTime = testTime;
	}
}
