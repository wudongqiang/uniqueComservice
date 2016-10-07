package com.unique.user.po;

import java.math.BigDecimal;
import java.util.Date;

public class AmsUserLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AMS_USER_LOG.USER_LOG_ID
     *
     * @mbggenerated
     */
    private BigDecimal userLogId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AMS_USER_LOG.USER_ID
     *
     * @mbggenerated
     */
    private BigDecimal userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AMS_USER_LOG.USER_NAME
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AMS_USER_LOG.LOGON_TIME
     *
     * @mbggenerated
     */
    private Date logonTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AMS_USER_LOG.LOGOUT_TIME
     *
     * @mbggenerated
     */
    private Date logoutTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AMS_USER_LOG.VISIT_IP
     *
     * @mbggenerated
     */
    private String visitIp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AMS_USER_LOG.LOGON_WAY
     *
     * @mbggenerated
     */
    private BigDecimal logonWay;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AMS_USER_LOG.USER_LOG_ID
     *
     * @return the value of AMS_USER_LOG.USER_LOG_ID
     *
     * @mbggenerated
     */
    public BigDecimal getUserLogId() {
        return userLogId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AMS_USER_LOG.USER_LOG_ID
     *
     * @param userLogId the value for AMS_USER_LOG.USER_LOG_ID
     *
     * @mbggenerated
     */
    public void setUserLogId(BigDecimal userLogId) {
        this.userLogId = userLogId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AMS_USER_LOG.USER_ID
     *
     * @return the value of AMS_USER_LOG.USER_ID
     *
     * @mbggenerated
     */
    public BigDecimal getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AMS_USER_LOG.USER_ID
     *
     * @param userId the value for AMS_USER_LOG.USER_ID
     *
     * @mbggenerated
     */
    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AMS_USER_LOG.USER_NAME
     *
     * @return the value of AMS_USER_LOG.USER_NAME
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AMS_USER_LOG.USER_NAME
     *
     * @param userName the value for AMS_USER_LOG.USER_NAME
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AMS_USER_LOG.LOGON_TIME
     *
     * @return the value of AMS_USER_LOG.LOGON_TIME
     *
     * @mbggenerated
     */
    public Date getLogonTime() {
        return logonTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AMS_USER_LOG.LOGON_TIME
     *
     * @param logonTime the value for AMS_USER_LOG.LOGON_TIME
     *
     * @mbggenerated
     */
    public void setLogonTime(Date logonTime) {
        this.logonTime = logonTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AMS_USER_LOG.LOGOUT_TIME
     *
     * @return the value of AMS_USER_LOG.LOGOUT_TIME
     *
     * @mbggenerated
     */
    public Date getLogoutTime() {
        return logoutTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AMS_USER_LOG.LOGOUT_TIME
     *
     * @param logoutTime the value for AMS_USER_LOG.LOGOUT_TIME
     *
     * @mbggenerated
     */
    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AMS_USER_LOG.VISIT_IP
     *
     * @return the value of AMS_USER_LOG.VISIT_IP
     *
     * @mbggenerated
     */
    public String getVisitIp() {
        return visitIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AMS_USER_LOG.VISIT_IP
     *
     * @param visitIp the value for AMS_USER_LOG.VISIT_IP
     *
     * @mbggenerated
     */
    public void setVisitIp(String visitIp) {
        this.visitIp = visitIp == null ? null : visitIp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AMS_USER_LOG.LOGON_WAY
     *
     * @return the value of AMS_USER_LOG.LOGON_WAY
     *
     * @mbggenerated
     */
    public BigDecimal getLogonWay() {
        return logonWay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AMS_USER_LOG.LOGON_WAY
     *
     * @param logonWay the value for AMS_USER_LOG.LOGON_WAY
     *
     * @mbggenerated
     */
    public void setLogonWay(BigDecimal logonWay) {
        this.logonWay = logonWay;
    }
}