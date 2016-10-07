package com.unique.user.po;

/**
 * 
 * 用户微信绑定<br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年10月16日 上午10:31:36<br/>
 * @author Administrator
 * @date 2015年10月16日
 * @description
 */
public class UserWechat {
	
	//alias	
	public static final String TABLE_SQL_NAME = "USER_WECHAT";
	
	public static final String ALIAS_BIND_ID = "绑定ID";
	public static final String ALIAS_CLIENT_TYPE_ID = "项目ID";
	public static final String ALIAS_ORG_ID = "机构ID";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_BIND_VALUE = "绑定的值";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	
	//the column is a primary key	
	public static final String PROP_KEY = "bindId";
	
		
	//fields START
	public static String PROP_REF="UserWechat";
	public static String PROP_REF_BIND_ID = "bindId";
	
	public static String PROP_REF_CLIENT_TYPE_ID = "clientTypeId";
	
	public static String PROP_REF_ORG_ID = "orgId";
	
	public static String PROP_REF_USER_ID = "userId";
	
	public static String PROP_REF_BIND_VALUE = "bindValue";
	
	public static String PROP_REF_CREATE_TIME_BEGIN = "createTimeBegin";
	public static String PROP_REF_CREATE_TIME_END = "createTimeEnd";
	
	//fields END
	
	//columns sql name START	
	public static String PROP_COLUMN_BIND_ID = "BIND_ID";
	public static String PROP_COLUMN_CLIENT_TYPE_ID = "CLIENT_TYPE_ID";
	public static String PROP_COLUMN_ORG_ID = "ORG_ID";
	public static String PROP_COLUMN_USER_ID = "USER_ID";
	public static String PROP_COLUMN_BIND_VALUE = "BIND_VALUE";
	public static String PROP_COLUMN_CREATE_TIME = "CREATE_TIME";
	//columns sql name END
		
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//
	private java.math.BigDecimal bindId;
	//
	private java.math.BigDecimal clientTypeId;
	//
	private java.math.BigDecimal orgId;
	//
	private java.math.BigDecimal userId;
	//@Length(max=64)
	private java.lang.String bindValue;
	//
	private java.util.Date createTime;
	//columns END

	public UserWechat(){
	}

	public UserWechat(
		java.math.BigDecimal bindId
	){
		this.bindId = bindId;
	}

	public void setBindId(java.math.BigDecimal value) {
		this.bindId = value;
	}
	
	public java.math.BigDecimal getBindId() {
		return this.bindId;
	}
	public void setClientTypeId(java.math.BigDecimal value) {
		this.clientTypeId = value;
	}
	
	public java.math.BigDecimal getClientTypeId() {
		return this.clientTypeId;
	}
	public void setOrgId(java.math.BigDecimal value) {
		this.orgId = value;
	}
	
	public java.math.BigDecimal getOrgId() {
		return this.orgId;
	}
	public void setUserId(java.math.BigDecimal value) {
		this.userId = value;
	}
	
	public java.math.BigDecimal getUserId() {
		return this.userId;
	}
	public void setBindValue(java.lang.String value) {
		this.bindValue = value;
	}
	
	public java.lang.String getBindValue() {
		return this.bindValue;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
}

