/**
 *
 */
package com.unique.survey.po;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年9月24日上午10:40:42
 * 修改日期:2015年9月24日上午10:40:42
 */
public class SurveyRecordExt extends SurveyRecord{
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	private String depName;
	private String orgName;
	private String staffTypeName;
	public String getStaffTypeName() {
		return staffTypeName;
	}
	public void setStaffTypeName(String staffTypeName) {
		this.staffTypeName = staffTypeName;
	}
}
