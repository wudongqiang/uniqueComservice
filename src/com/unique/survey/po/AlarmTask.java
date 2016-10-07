package com.unique.survey.po;

import java.math.BigDecimal;
import java.util.Date;

public class AlarmTask {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALARM_TASK.AT_ID
     *
     * @mbggenerated
     */
    private BigDecimal atId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALARM_TASK.ALARM_RULE_ID
     *
     * @mbggenerated
     */
    private BigDecimal alarmRuleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALARM_TASK.STATS
     *
     * @mbggenerated
     */
    private String stats;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALARM_TASK.CREATE_TIME
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALARM_TASK.SEND_TIME
     *
     * @mbggenerated
     */
    private Date sendTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALARM_TASK.STAFF_ID
     *
     * @mbggenerated
     */
    private BigDecimal staffId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALARM_TASK.SURVEY_ID
     *
     * @mbggenerated
     */
    private BigDecimal surveyId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALARM_TASK.AT_ID
     *
     * @return the value of ALARM_TASK.AT_ID
     *
     * @mbggenerated
     */
    public BigDecimal getAtId() {
        return atId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALARM_TASK.AT_ID
     *
     * @param atId the value for ALARM_TASK.AT_ID
     *
     * @mbggenerated
     */
    public void setAtId(BigDecimal atId) {
        this.atId = atId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALARM_TASK.ALARM_RULE_ID
     *
     * @return the value of ALARM_TASK.ALARM_RULE_ID
     *
     * @mbggenerated
     */
    public BigDecimal getAlarmRuleId() {
        return alarmRuleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALARM_TASK.ALARM_RULE_ID
     *
     * @param alarmRuleId the value for ALARM_TASK.ALARM_RULE_ID
     *
     * @mbggenerated
     */
    public void setAlarmRuleId(BigDecimal alarmRuleId) {
        this.alarmRuleId = alarmRuleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALARM_TASK.STATS
     *
     * @return the value of ALARM_TASK.STATS
     *
     * @mbggenerated
     */
    public String getStats() {
        return stats;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALARM_TASK.STATS
     *
     * @param stats the value for ALARM_TASK.STATS
     *
     * @mbggenerated
     */
    public void setStats(String stats) {
        this.stats = stats == null ? null : stats.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALARM_TASK.CREATE_TIME
     *
     * @return the value of ALARM_TASK.CREATE_TIME
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALARM_TASK.CREATE_TIME
     *
     * @param createTime the value for ALARM_TASK.CREATE_TIME
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALARM_TASK.SEND_TIME
     *
     * @return the value of ALARM_TASK.SEND_TIME
     *
     * @mbggenerated
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALARM_TASK.SEND_TIME
     *
     * @param sendTime the value for ALARM_TASK.SEND_TIME
     *
     * @mbggenerated
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALARM_TASK.STAFF_ID
     *
     * @return the value of ALARM_TASK.STAFF_ID
     *
     * @mbggenerated
     */
    public BigDecimal getStaffId() {
        return staffId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALARM_TASK.STAFF_ID
     *
     * @param staffId the value for ALARM_TASK.STAFF_ID
     *
     * @mbggenerated
     */
    public void setStaffId(BigDecimal staffId) {
        this.staffId = staffId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALARM_TASK.SURVEY_ID
     *
     * @return the value of ALARM_TASK.SURVEY_ID
     *
     * @mbggenerated
     */
    public BigDecimal getSurveyId() {
        return surveyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALARM_TASK.SURVEY_ID
     *
     * @param surveyId the value for ALARM_TASK.SURVEY_ID
     *
     * @mbggenerated
     */
    public void setSurveyId(BigDecimal surveyId) {
        this.surveyId = surveyId;
    }
}