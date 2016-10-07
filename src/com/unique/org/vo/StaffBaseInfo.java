/**
 * 2015年9月10日
 */
package com.unique.org.vo;

import java.math.BigDecimal;

import com.unique.core.util.FileUtil;
import com.unique.core.util.StringUtil;

/**
 * 医生基本信息
 * <br/>创建人 wdq
 * <br/>创建时间 2015年9月10日 上午11:46:50
 * @author Administrator
 * @date 2015年9月10日
 * @description 
 */
public class StaffBaseInfo {

	private BigDecimal staffId; 	//医生id
	private String staffName;		//医生名称
	private String depName;			//科室名称
	private String staffTypeName;	//医生级别
	private String gootAt;			//擅长
	private String staffIcon;		//医生图标
	
	/**图片服务器*/
	private final static String PIC_SERVER = FileUtil.readProperties("comservice.properties", "picServerOut");
	
	public BigDecimal getStaffId() {
		return staffId;
	}
	public void setStaffId(BigDecimal staffId) {
		this.staffId = staffId;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getStaffTypeName() {
		return staffTypeName;
	}
	public void setStaffTypeName(String staffTypeName) {
		this.staffTypeName = staffTypeName;
	}
	public String getStaffIcon() {
		return staffIcon;
	}
	public void setStaffIcon(String staffIcon) {
		if(!StringUtil.isEmpty(staffIcon)){
			this.staffIcon = PIC_SERVER + staffIcon;
		}
	}
	public String getGootAt() {
		return gootAt;
	}
	public void setGootAt(String gootAt) {
		this.gootAt = gootAt;
	}
	
}
