package com.unique.mb.po;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * 用户血糖测试记录<br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年10月8日 上午9:16:55<br/>
 * @author Administrator
 * @date 2015年10月8日
 * @description
 */
public class UserBloodSugarTest implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias	
	public static final String TABLE_SQL_NAME = "USER_BLOOD_SUGAR_TEST";
	
	public static final String ALIAS_TEST_ID = "检测记录ID";
	public static final String ALIAS_USER_DEVICE_ID = "用户设备ID";
	public static final String ALIAS_SOURCE_ID = "来源ID";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_TEST_TIME = "检测时间";
	public static final String ALIAS_USER_NAME = "检查用户名称";
	public static final String ALIAS_TEST_VALUE = "血糖值";
	public static final String ALIAS_TEST_RESULT = "检测结果";
	public static final String ALIAS_INPUT_MODE = "录入类型(1手动2检测)";
	public static final String ALIAS_USER_DEVICE_CODE = "设备编号";
	public static final String ALIAS_ALERT_TIME = "提醒时间";
	public static final String ALIAS_STATUS = "状态(C正常R停用";
	public static final String ALIAS_TEST_TIME_ID = "监测项时段ID";
	//the column is a primary key	
	public static final String PROP_KEY = "testId";
	
		
	//fields START
	public static String PROP_REF="UserBloodSugarTest";
	public static String PROP_REF_TEST_ID = "testId";
	
	public static String PROP_REF_USER_DEVICE_ID = "userDeviceId";
	
	public static String PROP_REF_SOURCE_ID = "sourceId";
	
	public static String PROP_REF_USER_ID = "userId";
	
	public static String PROP_REF_TEST_TIME_BEGIN = "testTimeBegin";
	public static String PROP_REF_TEST_TIME_END = "testTimeEnd";
	
	public static String PROP_REF_USER_NAME = "userName";
	
	public static String PROP_REF_TEST_VALUE = "testValue";
	
	public static String PROP_REF_TEST_RESULT = "testResult";
	
	public static String PROP_REF_INPUT_MODE = "inputMode";
	
	public static String PROP_REF_USER_DEVICE_CODE = "userDeviceCode";
	
	public static String PROP_REF_ALERT_TIME_BEGIN = "alertTimeBegin";
	public static String PROP_REF_ALERT_TIME_END = "alertTimeEnd";
	
	public static String PROP_REF_STATUS = "status";
	public static String PROP_REF_TEST_TIME_ID = "testTimeId";
	
	//fields END
	
	//columns sql name START	
	public static String PROP_COLUMN_TEST_ID = "TEST_ID";
	public static String PROP_COLUMN_USER_DEVICE_ID = "USER_DEVICE_ID";
	public static String PROP_COLUMN_SOURCE_ID = "SOURCE_ID";
	public static String PROP_COLUMN_USER_ID = "USER_ID";
	public static String PROP_COLUMN_TEST_TIME = "TEST_TIME";
	public static String PROP_COLUMN_USER_NAME = "USER_NAME";
	public static String PROP_COLUMN_TEST_VALUE = "TEST_VALUE";
	public static String PROP_COLUMN_TEST_RESULT = "TEST_RESULT";
	public static String PROP_COLUMN_INPUT_MODE = "INPUT_MODE";
	public static String PROP_COLUMN_USER_DEVICE_CODE = "USER_DEVICE_CODE";
	public static String PROP_COLUMN_ALERT_TIME = "ALERT_TIME";
	public static String PROP_COLUMN_STATUS = "STATUS";
	public static String PROP_COLUMN_TEST_TIME_ID = "TEST_TIME_ID";
	//columns sql name END
		
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//
	private java.math.BigDecimal testId;
	//
	private java.math.BigDecimal userDeviceId;
	//
	private java.math.BigDecimal sourceId;
	//
	private java.math.BigDecimal userId;
	//
	private java.util.Date testTime;
	//@Length(max=32)
	private java.lang.String userName;
	//
	private java.math.BigDecimal testValue;
	
	private String testValueStr;
	public String getTestValueStr() {
		return testValueStr;
	}

	public void setTestValueStr(String testValueStr) {
		this.testValueStr = testValueStr;
	}

	//@Length(max=256)
	private java.lang.String testResult;
	//@Length(max=1)
	private java.lang.String inputMode;
	//@Length(max=64)
	private java.lang.String userDeviceCode;
	//
	private java.util.Date alertTime;
	//
	private String status;
	//columns END
	private java.math.BigDecimal testTimeId;

	public UserBloodSugarTest(){
	}

	public UserBloodSugarTest(
		java.math.BigDecimal testId
	){
		this.testId = testId;
	}

	public void setTestId(java.math.BigDecimal value) {
		this.testId = value;
	}
	
	public java.math.BigDecimal getTestId() {
		return this.testId;
	}
	public void setUserDeviceId(java.math.BigDecimal value) {
		this.userDeviceId = value;
	}
	
	public java.math.BigDecimal getUserDeviceId() {
		return this.userDeviceId;
	}
	public void setSourceId(java.math.BigDecimal value) {
		this.sourceId = value;
	}
	
	public java.math.BigDecimal getSourceId() {
		return this.sourceId;
	}
	public void setUserId(java.math.BigDecimal value) {
		this.userId = value;
	}
	
	public java.math.BigDecimal getUserId() {
		return this.userId;
	}
	
	public void setTestTime(java.util.Date value) {
		this.testTime = value;
	}
	
	public java.util.Date getTestTime() {
		return this.testTime;
	}
	public void setUserName(java.lang.String value) {
		this.userName = value;
	}
	
	public java.lang.String getUserName() {
		return this.userName;
	}
	public void setTestValue(java.math.BigDecimal value) {
		this.testValue = value;
	}
	
	public java.math.BigDecimal getTestValue() {
		return this.testValue;
	}
	public void setTestResult(java.lang.String value) {
		this.testResult = value;
	}
	
	public java.lang.String getTestResult() {
		return this.testResult;
	}
	public void setInputMode(java.lang.String value) {
		this.inputMode = value;
	}
	
	public java.lang.String getInputMode() {
		return this.inputMode;
	}
	public void setUserDeviceCode(java.lang.String value) {
		this.userDeviceCode = value;
	}
	
	public java.lang.String getUserDeviceCode() {
		return this.userDeviceCode;
	}
	
	public void setAlertTime(java.util.Date value) {
		this.alertTime = value;
	}
	
	public java.util.Date getAlertTime() {
		return this.alertTime;
	}
	public void setStatus(String value) {
		this.status = value;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setTestTimeId(java.math.BigDecimal value) {
		this.testTimeId = value;
	}
	
	public java.math.BigDecimal getTestTimeId() {
		return this.testTimeId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("TestId",getTestId())
			.append("UserDeviceId",getUserDeviceId())
			.append("SourceId",getSourceId())
			.append("UserId",getUserId())
			.append("TestTime",getTestTime())
			.append("UserName",getUserName())
			.append("TestValue",getTestValue())
			.append("TestResult",getTestResult())
			.append("InputMode",getInputMode())
			.append("UserDeviceCode",getUserDeviceCode())
			.append("AlertTime",getAlertTime())
			.append("Status",getStatus())
			.append("TestTimeId",getTestTimeId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getTestId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserBloodSugarTest == false) return false;
		if(this == obj) return true;
		UserBloodSugarTest other = (UserBloodSugarTest)obj;
		return new EqualsBuilder()
			.append(getTestId(),other.getTestId())
			.isEquals();
	}
}

