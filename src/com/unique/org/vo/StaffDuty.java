/**
 * 2015年9月9日
 */
package com.unique.org.vo;


/** 
 * 科室下医生排班基本信息，科室下近7天的所有医生信息
 * <br/>创建人 wdq
 * <br/>创建时间 2015年9月9日 下午12:02:23
 * @author Administrator
 * @date 2015年9月9日
 * @description 
 */
public class StaffDuty extends StaffBaseInfo {

 
	private String regNumRemain;	//剩余号数
	
	public String getRegNumRemain() {
		return regNumRemain;
	}
	public void setRegNumRemain(String regNumRemain) {
		this.regNumRemain = regNumRemain;
	}
	
}