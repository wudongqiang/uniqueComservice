package com.unique.system.po;

import java.math.BigDecimal;

import javax.print.DocFlavor.STRING;

public class UserClientType {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_CLIENT_TYPE.UCT_ID
     *
     * @mbggenerated
     */
    private BigDecimal uctId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_CLIENT_TYPE.USER_ID
     *
     * @mbggenerated
     */
    private BigDecimal userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_CLIENT_TYPE.CLIENT_TYPE_ID
     *
     * @mbggenerated
     */
    private BigDecimal clientTypeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_CLIENT_TYPE.DEVICE_TOKEN
     *
     * @mbggenerated
     */
    private String deviceToken;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_CLIENT_TYPE.VERSION_NO
     *
     * @mbggenerated
     */
    private String versionNo;
    
    private String projectCode;
    
    private String clientTypeCode;
    private String pushCode;
    
	public String getPushCode() {
		return pushCode;
	}

	public void setPushCode(String pushCode) {
		this.pushCode = pushCode;
	}

	public String getClientTypeCode() {
		return clientTypeCode;
	}

	public void setClientTypeCode(String clientTypeCode) {
		this.clientTypeCode = clientTypeCode;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_CLIENT_TYPE.UCT_ID
     *
     * @return the value of USER_CLIENT_TYPE.UCT_ID
     *
     * @mbggenerated
     */
    public BigDecimal getUctId() {
        return uctId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_CLIENT_TYPE.UCT_ID
     *
     * @param uctId the value for USER_CLIENT_TYPE.UCT_ID
     *
     * @mbggenerated
     */
    public void setUctId(BigDecimal uctId) {
        this.uctId = uctId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_CLIENT_TYPE.USER_ID
     *
     * @return the value of USER_CLIENT_TYPE.USER_ID
     *
     * @mbggenerated
     */
    public BigDecimal getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_CLIENT_TYPE.USER_ID
     *
     * @param userId the value for USER_CLIENT_TYPE.USER_ID
     *
     * @mbggenerated
     */
    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_CLIENT_TYPE.CLIENT_TYPE_ID
     *
     * @return the value of USER_CLIENT_TYPE.CLIENT_TYPE_ID
     *
     * @mbggenerated
     */
    public BigDecimal getClientTypeId() {
        return clientTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_CLIENT_TYPE.CLIENT_TYPE_ID
     *
     * @param clientTypeId the value for USER_CLIENT_TYPE.CLIENT_TYPE_ID
     *
     * @mbggenerated
     */
    public void setClientTypeId(BigDecimal clientTypeId) {
        this.clientTypeId = clientTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_CLIENT_TYPE.DEVICE_TOKEN
     *
     * @return the value of USER_CLIENT_TYPE.DEVICE_TOKEN
     *
     * @mbggenerated
     */
    public String getDeviceToken() {
        return deviceToken;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_CLIENT_TYPE.DEVICE_TOKEN
     *
     * @param deviceToken the value for USER_CLIENT_TYPE.DEVICE_TOKEN
     *
     * @mbggenerated
     */
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken == null ? null : deviceToken.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_CLIENT_TYPE.VERSION_NO
     *
     * @return the value of USER_CLIENT_TYPE.VERSION_NO
     *
     * @mbggenerated
     */
    public String getVersionNo() {
        return versionNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_CLIENT_TYPE.VERSION_NO
     *
     * @param versionNo the value for USER_CLIENT_TYPE.VERSION_NO
     *
     * @mbggenerated
     */
    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo == null ? null : versionNo.trim();
    }
}