package com.unique.mb.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class UserElectrocardioTest {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.TEST_ID
     *
     * @mbggenerated
     */
    private BigDecimal testId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.USER_ID
     *
     * @mbggenerated
     */
    private BigDecimal userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.SOURCE_ID
     *
     * @mbggenerated
     */
    private BigDecimal sourceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.USER_DEVICE_ID
     *
     * @mbggenerated
     */
    private BigDecimal userDeviceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.TEST_TIME
     *
     * @mbggenerated
     */
    private Date testTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.USER_NAME
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.TEST_RESULT
     *
     * @mbggenerated
     */
    private String testResult;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.INPUT_MODE
     *
     * @mbggenerated
     */
    private String inputMode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.USER_DEVICE_CODE
     *
     * @mbggenerated
     */
    private String userDeviceCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.DEVICE_TOKEN
     *
     * @mbggenerated
     */
    private String deviceToken;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.STATUS
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.TEST_TIME_ID
     *
     * @mbggenerated
     */
    private BigDecimal testTimeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.HR
     *
     * @mbggenerated
     */
    private BigDecimal hr;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.QRS
     *
     * @mbggenerated
     */
    private BigDecimal qrs;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.PR
     *
     * @mbggenerated
     */
    private BigDecimal pr;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.QT
     *
     * @mbggenerated
     */
    private BigDecimal qt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.QTC
     *
     * @mbggenerated
     */
    private BigDecimal qtc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.P_AXIS
     *
     * @mbggenerated
     */
    private BigDecimal pAxis;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.QRS_AXIS
     *
     * @mbggenerated
     */
    private BigDecimal qrsAxis;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.T_AXIS
     *
     * @mbggenerated
     */
    private BigDecimal tAxis;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.RV5
     *
     * @mbggenerated
     */
    private BigDecimal rv5;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.SV1
     *
     * @mbggenerated
     */
    private BigDecimal sv1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.RV5_AND_SV1
     *
     * @mbggenerated
     */
    private BigDecimal rv5AndSv1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.PWIDTH
     *
     * @mbggenerated
     */
    private BigDecimal pwidth;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST.RR
     *
     * @mbggenerated
     */
    private BigDecimal rr;

    
	private List<UserElectrocardioResult> eleResults;
    private List<UserElectrocardioTestDet> testDets;
    
    public List<UserElectrocardioResult> getEleResults() {
		return eleResults;
	}

	public void setEleResults(List<UserElectrocardioResult> eleResults) {
		this.eleResults = eleResults;
	}

	public List<UserElectrocardioTestDet> getTestDets() {
		return testDets;
	}

	public void setTestDets(List<UserElectrocardioTestDet> testDets) {
		this.testDets = testDets;
	}
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.TEST_ID
     *
     * @return the value of USER_ELECTROCARDIO_TEST.TEST_ID
     *
     * @mbggenerated
     */
    public BigDecimal getTestId() {
        return testId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.TEST_ID
     *
     * @param testId the value for USER_ELECTROCARDIO_TEST.TEST_ID
     *
     * @mbggenerated
     */
    public void setTestId(BigDecimal testId) {
        this.testId = testId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.USER_ID
     *
     * @return the value of USER_ELECTROCARDIO_TEST.USER_ID
     *
     * @mbggenerated
     */
    public BigDecimal getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.USER_ID
     *
     * @param userId the value for USER_ELECTROCARDIO_TEST.USER_ID
     *
     * @mbggenerated
     */
    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.SOURCE_ID
     *
     * @return the value of USER_ELECTROCARDIO_TEST.SOURCE_ID
     *
     * @mbggenerated
     */
    public BigDecimal getSourceId() {
        return sourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.SOURCE_ID
     *
     * @param sourceId the value for USER_ELECTROCARDIO_TEST.SOURCE_ID
     *
     * @mbggenerated
     */
    public void setSourceId(BigDecimal sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.USER_DEVICE_ID
     *
     * @return the value of USER_ELECTROCARDIO_TEST.USER_DEVICE_ID
     *
     * @mbggenerated
     */
    public BigDecimal getUserDeviceId() {
        return userDeviceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.USER_DEVICE_ID
     *
     * @param userDeviceId the value for USER_ELECTROCARDIO_TEST.USER_DEVICE_ID
     *
     * @mbggenerated
     */
    public void setUserDeviceId(BigDecimal userDeviceId) {
        this.userDeviceId = userDeviceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.TEST_TIME
     *
     * @return the value of USER_ELECTROCARDIO_TEST.TEST_TIME
     *
     * @mbggenerated
     */
    public Date getTestTime() {
        return testTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.TEST_TIME
     *
     * @param testTime the value for USER_ELECTROCARDIO_TEST.TEST_TIME
     *
     * @mbggenerated
     */
    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.USER_NAME
     *
     * @return the value of USER_ELECTROCARDIO_TEST.USER_NAME
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.USER_NAME
     *
     * @param userName the value for USER_ELECTROCARDIO_TEST.USER_NAME
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.TEST_RESULT
     *
     * @return the value of USER_ELECTROCARDIO_TEST.TEST_RESULT
     *
     * @mbggenerated
     */
    public String getTestResult() {
        return testResult;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.TEST_RESULT
     *
     * @param testResult the value for USER_ELECTROCARDIO_TEST.TEST_RESULT
     *
     * @mbggenerated
     */
    public void setTestResult(String testResult) {
        this.testResult = testResult == null ? null : testResult.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.INPUT_MODE
     *
     * @return the value of USER_ELECTROCARDIO_TEST.INPUT_MODE
     *
     * @mbggenerated
     */
    public String getInputMode() {
        return inputMode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.INPUT_MODE
     *
     * @param inputMode the value for USER_ELECTROCARDIO_TEST.INPUT_MODE
     *
     * @mbggenerated
     */
    public void setInputMode(String inputMode) {
        this.inputMode = inputMode == null ? null : inputMode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.USER_DEVICE_CODE
     *
     * @return the value of USER_ELECTROCARDIO_TEST.USER_DEVICE_CODE
     *
     * @mbggenerated
     */
    public String getUserDeviceCode() {
        return userDeviceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.USER_DEVICE_CODE
     *
     * @param userDeviceCode the value for USER_ELECTROCARDIO_TEST.USER_DEVICE_CODE
     *
     * @mbggenerated
     */
    public void setUserDeviceCode(String userDeviceCode) {
        this.userDeviceCode = userDeviceCode == null ? null : userDeviceCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.DEVICE_TOKEN
     *
     * @return the value of USER_ELECTROCARDIO_TEST.DEVICE_TOKEN
     *
     * @mbggenerated
     */
    public String getDeviceToken() {
        return deviceToken;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.DEVICE_TOKEN
     *
     * @param deviceToken the value for USER_ELECTROCARDIO_TEST.DEVICE_TOKEN
     *
     * @mbggenerated
     */
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken == null ? null : deviceToken.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.STATUS
     *
     * @return the value of USER_ELECTROCARDIO_TEST.STATUS
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.STATUS
     *
     * @param status the value for USER_ELECTROCARDIO_TEST.STATUS
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.TEST_TIME_ID
     *
     * @return the value of USER_ELECTROCARDIO_TEST.TEST_TIME_ID
     *
     * @mbggenerated
     */
    public BigDecimal getTestTimeId() {
        return testTimeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.TEST_TIME_ID
     *
     * @param testTimeId the value for USER_ELECTROCARDIO_TEST.TEST_TIME_ID
     *
     * @mbggenerated
     */
    public void setTestTimeId(BigDecimal testTimeId) {
        this.testTimeId = testTimeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.HR
     *
     * @return the value of USER_ELECTROCARDIO_TEST.HR
     *
     * @mbggenerated
     */
    public BigDecimal getHr() {
        return hr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.HR
     *
     * @param hr the value for USER_ELECTROCARDIO_TEST.HR
     *
     * @mbggenerated
     */
    public void setHr(BigDecimal hr) {
        this.hr = hr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.QRS
     *
     * @return the value of USER_ELECTROCARDIO_TEST.QRS
     *
     * @mbggenerated
     */
    public BigDecimal getQrs() {
        return qrs;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.QRS
     *
     * @param qrs the value for USER_ELECTROCARDIO_TEST.QRS
     *
     * @mbggenerated
     */
    public void setQrs(BigDecimal qrs) {
        this.qrs = qrs;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.PR
     *
     * @return the value of USER_ELECTROCARDIO_TEST.PR
     *
     * @mbggenerated
     */
    public BigDecimal getPr() {
        return pr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.PR
     *
     * @param pr the value for USER_ELECTROCARDIO_TEST.PR
     *
     * @mbggenerated
     */
    public void setPr(BigDecimal pr) {
        this.pr = pr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.QT
     *
     * @return the value of USER_ELECTROCARDIO_TEST.QT
     *
     * @mbggenerated
     */
    public BigDecimal getQt() {
        return qt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.QT
     *
     * @param qt the value for USER_ELECTROCARDIO_TEST.QT
     *
     * @mbggenerated
     */
    public void setQt(BigDecimal qt) {
        this.qt = qt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.QTC
     *
     * @return the value of USER_ELECTROCARDIO_TEST.QTC
     *
     * @mbggenerated
     */
    public BigDecimal getQtc() {
        return qtc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.QTC
     *
     * @param qtc the value for USER_ELECTROCARDIO_TEST.QTC
     *
     * @mbggenerated
     */
    public void setQtc(BigDecimal qtc) {
        this.qtc = qtc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.P_AXIS
     *
     * @return the value of USER_ELECTROCARDIO_TEST.P_AXIS
     *
     * @mbggenerated
     */
    public BigDecimal getpAxis() {
        return pAxis;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.P_AXIS
     *
     * @param pAxis the value for USER_ELECTROCARDIO_TEST.P_AXIS
     *
     * @mbggenerated
     */
    public void setpAxis(BigDecimal pAxis) {
        this.pAxis = pAxis;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.QRS_AXIS
     *
     * @return the value of USER_ELECTROCARDIO_TEST.QRS_AXIS
     *
     * @mbggenerated
     */
    public BigDecimal getQrsAxis() {
        return qrsAxis;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.QRS_AXIS
     *
     * @param qrsAxis the value for USER_ELECTROCARDIO_TEST.QRS_AXIS
     *
     * @mbggenerated
     */
    public void setQrsAxis(BigDecimal qrsAxis) {
        this.qrsAxis = qrsAxis;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.T_AXIS
     *
     * @return the value of USER_ELECTROCARDIO_TEST.T_AXIS
     *
     * @mbggenerated
     */
    public BigDecimal gettAxis() {
        return tAxis;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.T_AXIS
     *
     * @param tAxis the value for USER_ELECTROCARDIO_TEST.T_AXIS
     *
     * @mbggenerated
     */
    public void settAxis(BigDecimal tAxis) {
        this.tAxis = tAxis;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.RV5
     *
     * @return the value of USER_ELECTROCARDIO_TEST.RV5
     *
     * @mbggenerated
     */
    public BigDecimal getRv5() {
        return rv5;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.RV5
     *
     * @param rv5 the value for USER_ELECTROCARDIO_TEST.RV5
     *
     * @mbggenerated
     */
    public void setRv5(BigDecimal rv5) {
        this.rv5 = rv5;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.SV1
     *
     * @return the value of USER_ELECTROCARDIO_TEST.SV1
     *
     * @mbggenerated
     */
    public BigDecimal getSv1() {
        return sv1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.SV1
     *
     * @param sv1 the value for USER_ELECTROCARDIO_TEST.SV1
     *
     * @mbggenerated
     */
    public void setSv1(BigDecimal sv1) {
        this.sv1 = sv1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.RV5_AND_SV1
     *
     * @return the value of USER_ELECTROCARDIO_TEST.RV5_AND_SV1
     *
     * @mbggenerated
     */
    public BigDecimal getRv5AndSv1() {
        return rv5AndSv1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.RV5_AND_SV1
     *
     * @param rv5AndSv1 the value for USER_ELECTROCARDIO_TEST.RV5_AND_SV1
     *
     * @mbggenerated
     */
    public void setRv5AndSv1(BigDecimal rv5AndSv1) {
        this.rv5AndSv1 = rv5AndSv1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.PWIDTH
     *
     * @return the value of USER_ELECTROCARDIO_TEST.PWIDTH
     *
     * @mbggenerated
     */
    public BigDecimal getPwidth() {
        return pwidth;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.PWIDTH
     *
     * @param pwidth the value for USER_ELECTROCARDIO_TEST.PWIDTH
     *
     * @mbggenerated
     */
    public void setPwidth(BigDecimal pwidth) {
        this.pwidth = pwidth;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST.RR
     *
     * @return the value of USER_ELECTROCARDIO_TEST.RR
     *
     * @mbggenerated
     */
    public BigDecimal getRr() {
        return rr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST.RR
     *
     * @param rr the value for USER_ELECTROCARDIO_TEST.RR
     *
     * @mbggenerated
     */
    public void setRr(BigDecimal rr) {
        this.rr = rr;
    }
}