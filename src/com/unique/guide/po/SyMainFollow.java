package com.unique.guide.po;


public class SyMainFollow {
	 
	private java.math.BigDecimal syRelatioId;
	private java.math.BigDecimal zsyId;
	private java.lang.String zsyCode;
	private java.lang.String zsyName;
	private java.math.BigDecimal fsyId;
	private java.lang.String fsyName;
	private java.util.Date opTime;
	private java.math.BigDecimal operator;
	private java.lang.String operatorName;
	private java.lang.String fsyCode;

	public SyMainFollow(){
	}

	public SyMainFollow(
		java.math.BigDecimal syRelatioId
	){
		this.syRelatioId = syRelatioId;
	}

	public void setSyRelatioId(java.math.BigDecimal value) {
		this.syRelatioId = value;
	}
	
	public java.math.BigDecimal getSyRelatioId() {
		return this.syRelatioId;
	}
	public void setZsyId(java.math.BigDecimal value) {
		this.zsyId = value;
	}
	
	public java.math.BigDecimal getZsyId() {
		return this.zsyId;
	}
	public void setZsyCode(java.lang.String value) {
		this.zsyCode = value;
	}
	
	public java.lang.String getZsyCode() {
		return this.zsyCode;
	}
	public void setZsyName(java.lang.String value) {
		this.zsyName = value;
	}
	
	public java.lang.String getZsyName() {
		return this.zsyName;
	}
	public void setFsyId(java.math.BigDecimal value) {
		this.fsyId = value;
	}
	
	public java.math.BigDecimal getFsyId() {
		return this.fsyId;
	}
	public void setFsyName(java.lang.String value) {
		this.fsyName = value;
	}
	
	public java.lang.String getFsyName() {
		return this.fsyName;
	}
	 
	public void setOpTime(java.util.Date value) {
		this.opTime = value;
	}
	
	public java.util.Date getOpTime() {
		return this.opTime;
	}
	public void setOperator(java.math.BigDecimal value) {
		this.operator = value;
	}
	
	public java.math.BigDecimal getOperator() {
		return this.operator;
	}
	public void setOperatorName(java.lang.String value) {
		this.operatorName = value;
	}
	
	public java.lang.String getOperatorName() {
		return this.operatorName;
	}
	public void setFsyCode(java.lang.String value) {
		this.fsyCode = value;
	}
	
	public java.lang.String getFsyCode() {
		return this.fsyCode;
	}

}

