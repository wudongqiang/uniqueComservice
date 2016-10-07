package com.unique.survey.po;

import java.math.BigDecimal;
import java.util.Date;

public class SurveyMoudleSubitem {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_MOUDLE_SUBITEM.SUB_ITEM_ID
     *
     * @mbggenerated
     */
    private BigDecimal subItemId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_MOUDLE_SUBITEM.SURVEY_ITEM_ID
     *
     * @mbggenerated
     */
    private BigDecimal surveyItemId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_MOUDLE_SUBITEM.SUB_ITEM_TITLE
     *
     * @mbggenerated
     */
    private String subItemTitle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_MOUDLE_SUBITEM.SUB_ITEM_VALUE
     *
     * @mbggenerated
     */
    private String subItemValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_MOUDLE_SUBITEM.CREATE_TIME
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_MOUDLE_SUBITEM.OP_TIME
     *
     * @mbggenerated
     */
    private Date opTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_MOUDLE_SUBITEM.OPERATOR
     *
     * @mbggenerated
     */
    private BigDecimal operator;

    private BigDecimal orderIndex;
    public BigDecimal getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(BigDecimal orderIndex) {
		this.orderIndex = orderIndex;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_MOUDLE_SUBITEM.SUB_ITEM_ID
     *
     * @return the value of SURVEY_MOUDLE_SUBITEM.SUB_ITEM_ID
     *
     * @mbggenerated
     */
    public BigDecimal getSubItemId() {
        return subItemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_MOUDLE_SUBITEM.SUB_ITEM_ID
     *
     * @param subItemId the value for SURVEY_MOUDLE_SUBITEM.SUB_ITEM_ID
     *
     * @mbggenerated
     */
    public void setSubItemId(BigDecimal subItemId) {
        this.subItemId = subItemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_MOUDLE_SUBITEM.SURVEY_ITEM_ID
     *
     * @return the value of SURVEY_MOUDLE_SUBITEM.SURVEY_ITEM_ID
     *
     * @mbggenerated
     */
    public BigDecimal getSurveyItemId() {
        return surveyItemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_MOUDLE_SUBITEM.SURVEY_ITEM_ID
     *
     * @param surveyItemId the value for SURVEY_MOUDLE_SUBITEM.SURVEY_ITEM_ID
     *
     * @mbggenerated
     */
    public void setSurveyItemId(BigDecimal surveyItemId) {
        this.surveyItemId = surveyItemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_MOUDLE_SUBITEM.SUB_ITEM_TITLE
     *
     * @return the value of SURVEY_MOUDLE_SUBITEM.SUB_ITEM_TITLE
     *
     * @mbggenerated
     */
    public String getSubItemTitle() {
        return subItemTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_MOUDLE_SUBITEM.SUB_ITEM_TITLE
     *
     * @param subItemTitle the value for SURVEY_MOUDLE_SUBITEM.SUB_ITEM_TITLE
     *
     * @mbggenerated
     */
    public void setSubItemTitle(String subItemTitle) {
        this.subItemTitle = subItemTitle == null ? null : subItemTitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_MOUDLE_SUBITEM.SUB_ITEM_VALUE
     *
     * @return the value of SURVEY_MOUDLE_SUBITEM.SUB_ITEM_VALUE
     *
     * @mbggenerated
     */
    public String getSubItemValue() {
        return subItemValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_MOUDLE_SUBITEM.SUB_ITEM_VALUE
     *
     * @param subItemValue the value for SURVEY_MOUDLE_SUBITEM.SUB_ITEM_VALUE
     *
     * @mbggenerated
     */
    public void setSubItemValue(String subItemValue) {
        this.subItemValue = subItemValue == null ? null : subItemValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_MOUDLE_SUBITEM.CREATE_TIME
     *
     * @return the value of SURVEY_MOUDLE_SUBITEM.CREATE_TIME
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_MOUDLE_SUBITEM.CREATE_TIME
     *
     * @param createTime the value for SURVEY_MOUDLE_SUBITEM.CREATE_TIME
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_MOUDLE_SUBITEM.OP_TIME
     *
     * @return the value of SURVEY_MOUDLE_SUBITEM.OP_TIME
     *
     * @mbggenerated
     */
    public Date getOpTime() {
        return opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_MOUDLE_SUBITEM.OP_TIME
     *
     * @param opTime the value for SURVEY_MOUDLE_SUBITEM.OP_TIME
     *
     * @mbggenerated
     */
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_MOUDLE_SUBITEM.OPERATOR
     *
     * @return the value of SURVEY_MOUDLE_SUBITEM.OPERATOR
     *
     * @mbggenerated
     */
    public BigDecimal getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_MOUDLE_SUBITEM.OPERATOR
     *
     * @param operator the value for SURVEY_MOUDLE_SUBITEM.OPERATOR
     *
     * @mbggenerated
     */
    public void setOperator(BigDecimal operator) {
        this.operator = operator;
    }
}