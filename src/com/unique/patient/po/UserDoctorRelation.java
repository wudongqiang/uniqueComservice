package com.unique.patient.po;

import java.math.BigDecimal;
import java.util.Date;

import com.unique.core.util.FileUtil;
import com.unique.core.util.StringUtil;

/**
 * 医生患者关系表 （用户与医生之间的关系，我的患者）
 * <br/>创建人 wdq
 * <br/>创建时间 下午3:22:46
 * @author Administrator
 * @date 2015年8月17日
 * @description
 */
public class UserDoctorRelation {

	private BigDecimal relatinId;		//	用户与医生关系id
	private BigDecimal userId;			//	用户id
	private String userName;			//	用户姓名
	private BigDecimal docUserId;		//	医生的用户id
	private BigDecimal staffId;			//	医生的员工id
	private String staffName;			//	医生名称
	private Date createTime;			//	创建时间
	
	public static final String COLUMN_CREATE_TIME = "U.CREATE_TIME";
	public static final String COLUMN_USER_NAME = "U.USER_NAME";
	
	///图片服务器
	private static final String PIC_SERVER = FileUtil.readProperties("comservice.properties", "picServerOut");
	
	//业务所需属性
	private int age;   					//年龄
	private String sex;					//性别
	private String imgUrl;				//用户头像
    
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		if(!StringUtil.isEmpty(imgUrl)){
			this.imgUrl = PIC_SERVER+imgUrl;
		}
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public BigDecimal getRelatinId() {
		return relatinId;
	}
	public void setRelatinId(BigDecimal relatinId) {
		this.relatinId = relatinId;
	}
	public BigDecimal getUserId() {
		return userId;
	}
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BigDecimal getDocUserId() {
		return docUserId;
	}
	public void setDocUserId(BigDecimal docUserId) {
		this.docUserId = docUserId;
	}
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
