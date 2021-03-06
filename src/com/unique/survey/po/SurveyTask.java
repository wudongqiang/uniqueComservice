package com.unique.survey.po;

import java.math.BigDecimal;
import java.util.Date;

public class SurveyTask {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_TASK.SURVEY_TASK_ID
     *
     * @mbggenerated
     */
    private BigDecimal surveyTaskId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_TASK.SURVEY_MOUDLE_ID
     *
     * @mbggenerated
     */
    private BigDecimal surveyMoudleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_TASK.USER_ID
     *
     * @mbggenerated
     */
    private BigDecimal userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_TASK.SURVEY_SCHDULE_ID
     *
     * @mbggenerated
     */
    private BigDecimal surveySchduleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_TASK.EXC_TIME
     *
     * @mbggenerated
     */
    private Date excTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_TASK.EXC_STATUS
     *
     * @mbggenerated
     */
    private String excStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_TASK.CREATE_TIME
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_TASK.OP_TIME
     *
     * @mbggenerated
     */
    private Date opTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_TASK.OPERATOR
     *
     * @mbggenerated
     */
    private BigDecimal operator;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_TASK.SURVEY_TASK_ID
     *
     * @return the value of SURVEY_TASK.SURVEY_TASK_ID
     *
     * @mbggenerated
     */
    public BigDecimal getSurveyTaskId() {
        return surveyTaskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_TASK.SURVEY_TASK_ID
     *
     * @param surveyTaskId the value for SURVEY_TASK.SURVEY_TASK_ID
     *
     * @mbggenerated
     */
    public void setSurveyTaskId(BigDecimal surveyTaskId) {
        this.surveyTaskId = surveyTaskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_TASK.SURVEY_MOUDLE_ID
     *
     * @return the value of SURVEY_TASK.SURVEY_MOUDLE_ID
     *
     * @mbggenerated
     */
    public BigDecimal getSurveyMoudleId() {
        return surveyMoudleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_TASK.SURVEY_MOUDLE_ID
     *
     * @param surveyMoudleId the value for SURVEY_TASK.SURVEY_MOUDLE_ID
     *
     * @mbggenerated
     */
    public void setSurveyMoudleId(BigDecimal surveyMoudleId) {
        this.surveyMoudleId = surveyMoudleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_TASK.USER_ID
     *
     * @return the value of SURVEY_TASK.USER_ID
     *
     * @mbggenerated
     */
    public BigDecimal getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_TASK.USER_ID
     *
     * @param userId the value for SURVEY_TASK.USER_ID
     *
     * @mbggenerated
     */
    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_TASK.SURVEY_SCHDULE_ID
     *
     * @return the value of SURVEY_TASK.SURVEY_SCHDULE_ID
     *
     * @mbggenerated
     */
    public BigDecimal getSurveySchduleId() {
        return surveySchduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_TASK.SURVEY_SCHDULE_ID
     *
     * @param surveySchduleId the value for SURVEY_TASK.SURVEY_SCHDULE_ID
     *
     * @mbggenerated
     */
    public void setSurveySchduleId(BigDecimal surveySchduleId) {
        this.surveySchduleId = surveySchduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_TASK.EXC_TIME
     *
     * @return the value of SURVEY_TASK.EXC_TIME
     *
     * @mbggenerated
     */
    public Date getExcTime() {
        return excTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_TASK.EXC_TIME
     *
     * @param excTime the value for SURVEY_TASK.EXC_TIME
     *
     * @mbggenerated
     */
    public void setExcTime(Date excTime) {
        this.excTime = excTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_TASK.EXC_STATUS
     *
     * @return the value of SURVEY_TASK.EXC_STATUS
     *
     * @mbggenerated
     */
    public String getExcStatus() {
        return excStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_TASK.EXC_STATUS
     *
     * @param excStatus the value for SURVEY_TASK.EXC_STATUS
     *
     * @mbggenerated
     */
    public void setExcStatus(String excStatus) {
        this.excStatus = excStatus == null ? null : excStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_TASK.CREATE_TIME
     *
     * @return the value of SURVEY_TASK.CREATE_TIME
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_TASK.CREATE_TIME
     *
     * @param createTime the value for SURVEY_TASK.CREATE_TIME
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_TASK.OP_TIME
     *
     * @return the value of SURVEY_TASK.OP_TIME
     *
     * @mbggenerated
     */
    public Date getOpTime() {
        return opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_TASK.OP_TIME
     *
     * @param opTime the value for SURVEY_TASK.OP_TIME
     *
     * @mbggenerated
     */
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_TASK.OPERATOR
     *
     * @return the value of SURVEY_TASK.OPERATOR
     *
     * @mbggenerated
     */
    public BigDecimal getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_TASK.OPERATOR
     *
     * @param operator the value for SURVEY_TASK.OPERATOR
     *
     * @mbggenerated
     */
    public void setOperator(BigDecimal operator) {
        this.operator = operator;
    }
}