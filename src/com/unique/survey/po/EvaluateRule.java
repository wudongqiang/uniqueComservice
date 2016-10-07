package com.unique.survey.po;

import java.math.BigDecimal;
import java.util.Date;

public class EvaluateRule {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATE_RULE.EVALUATE_RULE_ID
     *
     * @mbggenerated
     */
    private BigDecimal evaluateRuleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATE_RULE.SCORE_RULE_ID
     *
     * @mbggenerated
     */
    private BigDecimal scoreRuleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATE_RULE.EVALUATE_RULE_TITLE
     *
     * @mbggenerated
     */
    private String evaluateRuleTitle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATE_RULE.MAX_VALUE
     *
     * @mbggenerated
     */
    private BigDecimal maxValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATE_RULE.MIN_VALUE
     *
     * @mbggenerated
     */
    private BigDecimal minValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATE_RULE.CREATE_TIME
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATE_RULE.OP_TIME
     *
     * @mbggenerated
     */
    private Date opTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATE_RULE.OPERATOR
     *
     * @mbggenerated
     */
    private BigDecimal operator;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATE_RULE.EVALUATE_RULE_ID
     *
     * @return the value of EVALUATE_RULE.EVALUATE_RULE_ID
     *
     * @mbggenerated
     */
    public BigDecimal getEvaluateRuleId() {
        return evaluateRuleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATE_RULE.EVALUATE_RULE_ID
     *
     * @param evaluateRuleId the value for EVALUATE_RULE.EVALUATE_RULE_ID
     *
     * @mbggenerated
     */
    public void setEvaluateRuleId(BigDecimal evaluateRuleId) {
        this.evaluateRuleId = evaluateRuleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATE_RULE.SCORE_RULE_ID
     *
     * @return the value of EVALUATE_RULE.SCORE_RULE_ID
     *
     * @mbggenerated
     */
    public BigDecimal getScoreRuleId() {
        return scoreRuleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATE_RULE.SCORE_RULE_ID
     *
     * @param scoreRuleId the value for EVALUATE_RULE.SCORE_RULE_ID
     *
     * @mbggenerated
     */
    public void setScoreRuleId(BigDecimal scoreRuleId) {
        this.scoreRuleId = scoreRuleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATE_RULE.EVALUATE_RULE_TITLE
     *
     * @return the value of EVALUATE_RULE.EVALUATE_RULE_TITLE
     *
     * @mbggenerated
     */
    public String getEvaluateRuleTitle() {
        return evaluateRuleTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATE_RULE.EVALUATE_RULE_TITLE
     *
     * @param evaluateRuleTitle the value for EVALUATE_RULE.EVALUATE_RULE_TITLE
     *
     * @mbggenerated
     */
    public void setEvaluateRuleTitle(String evaluateRuleTitle) {
        this.evaluateRuleTitle = evaluateRuleTitle == null ? null : evaluateRuleTitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATE_RULE.MAX_VALUE
     *
     * @return the value of EVALUATE_RULE.MAX_VALUE
     *
     * @mbggenerated
     */
    public BigDecimal getMaxValue() {
        return maxValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATE_RULE.MAX_VALUE
     *
     * @param maxValue the value for EVALUATE_RULE.MAX_VALUE
     *
     * @mbggenerated
     */
    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATE_RULE.MIN_VALUE
     *
     * @return the value of EVALUATE_RULE.MIN_VALUE
     *
     * @mbggenerated
     */
    public BigDecimal getMinValue() {
        return minValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATE_RULE.MIN_VALUE
     *
     * @param minValue the value for EVALUATE_RULE.MIN_VALUE
     *
     * @mbggenerated
     */
    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATE_RULE.CREATE_TIME
     *
     * @return the value of EVALUATE_RULE.CREATE_TIME
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATE_RULE.CREATE_TIME
     *
     * @param createTime the value for EVALUATE_RULE.CREATE_TIME
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATE_RULE.OP_TIME
     *
     * @return the value of EVALUATE_RULE.OP_TIME
     *
     * @mbggenerated
     */
    public Date getOpTime() {
        return opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATE_RULE.OP_TIME
     *
     * @param opTime the value for EVALUATE_RULE.OP_TIME
     *
     * @mbggenerated
     */
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATE_RULE.OPERATOR
     *
     * @return the value of EVALUATE_RULE.OPERATOR
     *
     * @mbggenerated
     */
    public BigDecimal getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATE_RULE.OPERATOR
     *
     * @param operator the value for EVALUATE_RULE.OPERATOR
     *
     * @mbggenerated
     */
    public void setOperator(BigDecimal operator) {
        this.operator = operator;
    }
}