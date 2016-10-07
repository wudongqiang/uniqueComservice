package com.unique.org.vo;

/**
 * 通讯录 对象 vo
 * @author Administrator
 *
 */
public class MailList {

	private String depName; //部门名称
	private String staffName; //医生姓名
	private String homeTel; //电话
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
	public String getHomeTel() {
		return homeTel;
	}
	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}
	
}
