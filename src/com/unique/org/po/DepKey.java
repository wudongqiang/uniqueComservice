package com.unique.org.po;


/**
* <p>com.csair.adp.system.database.resources.DepKey</p>
*
* @author changwu.liao email:changwu.liao(a)gmail.com
* @version 1.0
* @since 1.0
* @Modified by add modified description
* @purpose add this file description here
*/
public class DepKey {
	
	//alias	
	public static final String TABLE_SQL_NAME = "DEP_KEY";
	
	public static final String ALIAS_KEY_ID = "部门关键ID";
	public static final String ALIAS_DEP_ID = "部门ID";
	public static final String ALIAS_ORG_ID = "机构ID";
	public static final String ALIAS_KEY_NAME = "关键字名称";
	public static final String ALIAS_STATUS = "状态(C正常R停用)";
	public static final String ALIAS_OPERATOR = "操作员";
	public static final String ALIAS_OP_TIME = "操作时间";
	public static final String ALIAS_OPERATOR_NAME = "操作员名称";
	
	
	//the column is a primary key	
	public static final String PROP_KEY = "keyId";
	
	
		
	//fields START
	public static String PROP_REF="DepKey";
	public static String PROP_REF_KEY_ID = "keyId";
	
	public static String PROP_REF_DEP_ID = "depId";
	
	public static String PROP_REF_ORG_ID = "orgId";
	
	public static String PROP_REF_KEY_NAME = "keyName";
	
	public static String PROP_REF_STATUS = "status";
	
	public static String PROP_REF_OPERATOR = "operator";
	
	public static String PROP_REF_OP_TIME_BEGIN = "opTimeBegin";
	public static String PROP_REF_OP_TIME_END = "opTimeEnd";
	
	public static String PROP_REF_OPERATOR_NAME = "operatorName";
	
	//fields END
	
	//columns sql name START	
	public static String PROP_COLUMN_KEY_ID = "KEY_ID";
	public static String PROP_COLUMN_DEP_ID = "DEP_ID";
	public static String PROP_COLUMN_ORG_ID = "ORG_ID";
	public static String PROP_COLUMN_KEY_NAME = "KEY_NAME";
	public static String PROP_COLUMN_STATUS = "STATUS";
	public static String PROP_COLUMN_OPERATOR = "OPERATOR";
	public static String PROP_COLUMN_OP_TIME = "OP_TIME";
	public static String PROP_COLUMN_OPERATOR_NAME = "OPERATOR_NAME";
	//columns sql name END
		
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//
	private java.math.BigDecimal keyId;
	//
	private java.math.BigDecimal depId;
	//
	private java.math.BigDecimal orgId;
	//@NotBlank @Length(max=64)
	private java.lang.String keyName;
	//@Length(max=1)
	private java.lang.String status;
	//
	private java.math.BigDecimal operator;
	//
	private java.sql.Timestamp opTime;
	//@Length(max=32)
	private java.lang.String operatorName;
	//columns END

	public DepKey(){
	}

	public DepKey(
		java.math.BigDecimal keyId
	){
		this.keyId = keyId;
	}

	public void setKeyId(java.math.BigDecimal value) {
		this.keyId = value;
	}
	
	public java.math.BigDecimal getKeyId() {
		return this.keyId;
	}
	public void setDepId(java.math.BigDecimal value) {
		this.depId = value;
	}
	
	public java.math.BigDecimal getDepId() {
		return this.depId;
	}
	public void setOrgId(java.math.BigDecimal value) {
		this.orgId = value;
	}
	
	public java.math.BigDecimal getOrgId() {
		return this.orgId;
	}
	public void setKeyName(java.lang.String value) {
		this.keyName = value;
	}
	
	public java.lang.String getKeyName() {
		return this.keyName;
	}
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
	public java.lang.String getStatus() {
		return this.status;
	}
	public void setOperator(java.math.BigDecimal value) {
		this.operator = value;
	}
	
	public java.math.BigDecimal getOperator() {
		return this.operator;
	}
 
	public void setOpTime(java.sql.Timestamp value) {
		this.opTime = value;
	}
	
	public java.sql.Timestamp getOpTime() {
		return this.opTime;
	}
	public void setOperatorName(java.lang.String value) {
		this.operatorName = value;
	}
	
	public java.lang.String getOperatorName() {
		return this.operatorName;
	}
}

