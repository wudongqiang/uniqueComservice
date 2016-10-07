package com.unique.user.po;

import java.math.BigDecimal;
import java.util.Date;

public class MsgType {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MSG_TYPE.MSG_TYPE_ID
     *
     * @mbggenerated
     */
    private BigDecimal msgTypeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MSG_TYPE.MSG_TYPE_CODE
     *
     * @mbggenerated
     */
    private String msgTypeCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MSG_TYPE.MSG_TYPE_NAME
     *
     * @mbggenerated
     */
    private String msgTypeName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MSG_TYPE.STATUS
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MSG_TYPE.OP_TIME
     *
     * @mbggenerated
     */
    private Date opTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MSG_TYPE.OPERATOR
     *
     * @mbggenerated
     */
    private BigDecimal operator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MSG_TYPE.OPERATOR_NAME
     *
     * @mbggenerated
     */
    private BigDecimal operatorName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MSG_TYPE.MSG_TYPE_ID
     *
     * @return the value of MSG_TYPE.MSG_TYPE_ID
     *
     * @mbggenerated
     */
    public BigDecimal getMsgTypeId() {
        return msgTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MSG_TYPE.MSG_TYPE_ID
     *
     * @param msgTypeId the value for MSG_TYPE.MSG_TYPE_ID
     *
     * @mbggenerated
     */
    public void setMsgTypeId(BigDecimal msgTypeId) {
        this.msgTypeId = msgTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MSG_TYPE.MSG_TYPE_CODE
     *
     * @return the value of MSG_TYPE.MSG_TYPE_CODE
     *
     * @mbggenerated
     */
    public String getMsgTypeCode() {
        return msgTypeCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MSG_TYPE.MSG_TYPE_CODE
     *
     * @param msgTypeCode the value for MSG_TYPE.MSG_TYPE_CODE
     *
     * @mbggenerated
     */
    public void setMsgTypeCode(String msgTypeCode) {
        this.msgTypeCode = msgTypeCode == null ? null : msgTypeCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MSG_TYPE.MSG_TYPE_NAME
     *
     * @return the value of MSG_TYPE.MSG_TYPE_NAME
     *
     * @mbggenerated
     */
    public String getMsgTypeName() {
        return msgTypeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MSG_TYPE.MSG_TYPE_NAME
     *
     * @param msgTypeName the value for MSG_TYPE.MSG_TYPE_NAME
     *
     * @mbggenerated
     */
    public void setMsgTypeName(String msgTypeName) {
        this.msgTypeName = msgTypeName == null ? null : msgTypeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MSG_TYPE.STATUS
     *
     * @return the value of MSG_TYPE.STATUS
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MSG_TYPE.STATUS
     *
     * @param status the value for MSG_TYPE.STATUS
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MSG_TYPE.OP_TIME
     *
     * @return the value of MSG_TYPE.OP_TIME
     *
     * @mbggenerated
     */
    public Date getOpTime() {
        return opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MSG_TYPE.OP_TIME
     *
     * @param opTime the value for MSG_TYPE.OP_TIME
     *
     * @mbggenerated
     */
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MSG_TYPE.OPERATOR
     *
     * @return the value of MSG_TYPE.OPERATOR
     *
     * @mbggenerated
     */
    public BigDecimal getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MSG_TYPE.OPERATOR
     *
     * @param operator the value for MSG_TYPE.OPERATOR
     *
     * @mbggenerated
     */
    public void setOperator(BigDecimal operator) {
        this.operator = operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MSG_TYPE.OPERATOR_NAME
     *
     * @return the value of MSG_TYPE.OPERATOR_NAME
     *
     * @mbggenerated
     */
    public BigDecimal getOperatorName() {
        return operatorName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MSG_TYPE.OPERATOR_NAME
     *
     * @param operatorName the value for MSG_TYPE.OPERATOR_NAME
     *
     * @mbggenerated
     */
    public void setOperatorName(BigDecimal operatorName) {
        this.operatorName = operatorName;
    }
}