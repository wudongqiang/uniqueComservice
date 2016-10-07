package com.unique.mb.po;

import java.math.BigDecimal;

public class UserElectrocardioTestDet {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST_DET.TEST_DET_ID
     *
     * @mbggenerated
     */
    private BigDecimal testDetId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST_DET.TEST_ID
     *
     * @mbggenerated
     */
    private BigDecimal testId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST_DET.LEAD_NUM
     *
     * @mbggenerated
     */
    private BigDecimal leadNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_ELECTROCARDIO_TEST_DET.BITMAP
     *
     * @mbggenerated
     */
    private String bitmap;

    private Integer rate;
    public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST_DET.TEST_DET_ID
     *
     * @return the value of USER_ELECTROCARDIO_TEST_DET.TEST_DET_ID
     *
     * @mbggenerated
     */
    public BigDecimal getTestDetId() {
        return testDetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST_DET.TEST_DET_ID
     *
     * @param testDetId the value for USER_ELECTROCARDIO_TEST_DET.TEST_DET_ID
     *
     * @mbggenerated
     */
    public void setTestDetId(BigDecimal testDetId) {
        this.testDetId = testDetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST_DET.TEST_ID
     *
     * @return the value of USER_ELECTROCARDIO_TEST_DET.TEST_ID
     *
     * @mbggenerated
     */
    public BigDecimal getTestId() {
        return testId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST_DET.TEST_ID
     *
     * @param testId the value for USER_ELECTROCARDIO_TEST_DET.TEST_ID
     *
     * @mbggenerated
     */
    public void setTestId(BigDecimal testId) {
        this.testId = testId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST_DET.LEAD_NUM
     *
     * @return the value of USER_ELECTROCARDIO_TEST_DET.LEAD_NUM
     *
     * @mbggenerated
     */
    public BigDecimal getLeadNum() {
        return leadNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST_DET.LEAD_NUM
     *
     * @param leadNum the value for USER_ELECTROCARDIO_TEST_DET.LEAD_NUM
     *
     * @mbggenerated
     */
    public void setLeadNum(BigDecimal leadNum) {
        this.leadNum = leadNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_ELECTROCARDIO_TEST_DET.BITMAP
     *
     * @return the value of USER_ELECTROCARDIO_TEST_DET.BITMAP
     *
     * @mbggenerated
     */
    public String getBitmap() {
        return bitmap;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_ELECTROCARDIO_TEST_DET.BITMAP
     *
     * @param bitmap the value for USER_ELECTROCARDIO_TEST_DET.BITMAP
     *
     * @mbggenerated
     */
    public void setBitmap(String bitmap) {
        this.bitmap = bitmap == null ? null : bitmap.trim();
    }
}