package com.unique.survey.po;

import java.math.BigDecimal;
import java.util.Date;

public class SurveyKpi {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_KPI.KPI_ID
     *
     * @mbggenerated
     */
    private BigDecimal kpiId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_KPI.EQUP_ID
     *
     * @mbggenerated
     */
    private BigDecimal equpId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_KPI.KPI_TITLE
     *
     * @mbggenerated
     */
    private String kpiTitle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_KPI.INPUT_TYPE
     *
     * @mbggenerated
     */
    private BigDecimal inputType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_KPI.UNIT
     *
     * @mbggenerated
     */
    private String unit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_KPI.MAX_VALUE
     *
     * @mbggenerated
     */
    private String maxValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_KPI.MIN_VALUE
     *
     * @mbggenerated
     */
    private String minValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_KPI.STATUS
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_KPI.CREATE_TIME
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_KPI.OP_TIME
     *
     * @mbggenerated
     */
    private Date opTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_KPI.OPERATOR
     *
     * @mbggenerated
     */
    private BigDecimal operator;
    
    public BigDecimal getOrgId() {
		return orgId;
	}

	public void setOrgId(BigDecimal orgId) {
		this.orgId = orgId;
	}

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public BigDecimal getParentId() {
		return parentId;
	}

	public void setParentId(BigDecimal parentId) {
		this.parentId = parentId;
	}

	private BigDecimal orgId;
    private String kpiCode;
    private BigDecimal parentId;
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_KPI.KPI_ID
     *
     * @return the value of SURVEY_KPI.KPI_ID
     *
     * @mbggenerated
     */
    public BigDecimal getKpiId() {
        return kpiId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_KPI.KPI_ID
     *
     * @param kpiId the value for SURVEY_KPI.KPI_ID
     *
     * @mbggenerated
     */
    public void setKpiId(BigDecimal kpiId) {
        this.kpiId = kpiId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_KPI.EQUP_ID
     *
     * @return the value of SURVEY_KPI.EQUP_ID
     *
     * @mbggenerated
     */
    public BigDecimal getEqupId() {
        return equpId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_KPI.EQUP_ID
     *
     * @param equpId the value for SURVEY_KPI.EQUP_ID
     *
     * @mbggenerated
     */
    public void setEqupId(BigDecimal equpId) {
        this.equpId = equpId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_KPI.KPI_TITLE
     *
     * @return the value of SURVEY_KPI.KPI_TITLE
     *
     * @mbggenerated
     */
    public String getKpiTitle() {
        return kpiTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_KPI.KPI_TITLE
     *
     * @param kpiTitle the value for SURVEY_KPI.KPI_TITLE
     *
     * @mbggenerated
     */
    public void setKpiTitle(String kpiTitle) {
        this.kpiTitle = kpiTitle == null ? null : kpiTitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_KPI.INPUT_TYPE
     *
     * @return the value of SURVEY_KPI.INPUT_TYPE
     *
     * @mbggenerated
     */
    public BigDecimal getInputType() {
        return inputType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_KPI.INPUT_TYPE
     *
     * @param inputType the value for SURVEY_KPI.INPUT_TYPE
     *
     * @mbggenerated
     */
    public void setInputType(BigDecimal inputType) {
        this.inputType = inputType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_KPI.UNIT
     *
     * @return the value of SURVEY_KPI.UNIT
     *
     * @mbggenerated
     */
    public String getUnit() {
        return unit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_KPI.UNIT
     *
     * @param unit the value for SURVEY_KPI.UNIT
     *
     * @mbggenerated
     */
    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_KPI.MAX_VALUE
     *
     * @return the value of SURVEY_KPI.MAX_VALUE
     *
     * @mbggenerated
     */
    public String getMaxValue() {
        return maxValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_KPI.MAX_VALUE
     *
     * @param maxValue the value for SURVEY_KPI.MAX_VALUE
     *
     * @mbggenerated
     */
    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue == null ? null : maxValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_KPI.MIN_VALUE
     *
     * @return the value of SURVEY_KPI.MIN_VALUE
     *
     * @mbggenerated
     */
    public String getMinValue() {
        return minValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_KPI.MIN_VALUE
     *
     * @param minValue the value for SURVEY_KPI.MIN_VALUE
     *
     * @mbggenerated
     */
    public void setMinValue(String minValue) {
        this.minValue = minValue == null ? null : minValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_KPI.STATUS
     *
     * @return the value of SURVEY_KPI.STATUS
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_KPI.STATUS
     *
     * @param status the value for SURVEY_KPI.STATUS
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_KPI.CREATE_TIME
     *
     * @return the value of SURVEY_KPI.CREATE_TIME
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_KPI.CREATE_TIME
     *
     * @param createTime the value for SURVEY_KPI.CREATE_TIME
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_KPI.OP_TIME
     *
     * @return the value of SURVEY_KPI.OP_TIME
     *
     * @mbggenerated
     */
    public Date getOpTime() {
        return opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_KPI.OP_TIME
     *
     * @param opTime the value for SURVEY_KPI.OP_TIME
     *
     * @mbggenerated
     */
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_KPI.OPERATOR
     *
     * @return the value of SURVEY_KPI.OPERATOR
     *
     * @mbggenerated
     */
    public BigDecimal getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_KPI.OPERATOR
     *
     * @param operator the value for SURVEY_KPI.OPERATOR
     *
     * @mbggenerated
     */
    public void setOperator(BigDecimal operator) {
        this.operator = operator;
    }
}