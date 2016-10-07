package com.unique.user.po;

import java.math.BigDecimal;
import java.util.Date;

public class McCard {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_CARD.CARD_ID
     *
     * @mbggenerated
     */
    private BigDecimal cardId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_CARD.USER_ID
     *
     * @mbggenerated
     */
    private BigDecimal userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_CARD.ORG_ID
     *
     * @mbggenerated
     */
    private BigDecimal orgId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_CARD.CARD_TYPE_ID
     *
     * @mbggenerated
     */
    private BigDecimal cardTypeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_CARD.CARD_NAME
     *
     * @mbggenerated
     */
    private String cardName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_CARD.CARD_NO
     *
     * @mbggenerated
     */
    private String cardNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_CARD.STATUS
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_CARD.HIS_USER_ID
     *
     * @mbggenerated
     */
    private String hisUserId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_CARD.OPERATOR
     *
     * @mbggenerated
     */
    private BigDecimal operator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_CARD.OP_TIME
     *
     * @mbggenerated
     */
    private Date opTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_CARD.OPERATOR_NAME
     *
     * @mbggenerated
     */
    private String operatorName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_CARD.FRIEND_ID
     *
     * @mbggenerated
     */
    private BigDecimal friendId;

    private String cardTypeName;
    
    public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_CARD.CARD_ID
     *
     * @return the value of MC_CARD.CARD_ID
     *
     * @mbggenerated
     */
    public BigDecimal getCardId() {
        return cardId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_CARD.CARD_ID
     *
     * @param cardId the value for MC_CARD.CARD_ID
     *
     * @mbggenerated
     */
    public void setCardId(BigDecimal cardId) {
        this.cardId = cardId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_CARD.USER_ID
     *
     * @return the value of MC_CARD.USER_ID
     *
     * @mbggenerated
     */
    public BigDecimal getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_CARD.USER_ID
     *
     * @param userId the value for MC_CARD.USER_ID
     *
     * @mbggenerated
     */
    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_CARD.ORG_ID
     *
     * @return the value of MC_CARD.ORG_ID
     *
     * @mbggenerated
     */
    public BigDecimal getOrgId() {
        return orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_CARD.ORG_ID
     *
     * @param orgId the value for MC_CARD.ORG_ID
     *
     * @mbggenerated
     */
    public void setOrgId(BigDecimal orgId) {
        this.orgId = orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_CARD.CARD_TYPE_ID
     *
     * @return the value of MC_CARD.CARD_TYPE_ID
     *
     * @mbggenerated
     */
    public BigDecimal getCardTypeId() {
        return cardTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_CARD.CARD_TYPE_ID
     *
     * @param cardTypeId the value for MC_CARD.CARD_TYPE_ID
     *
     * @mbggenerated
     */
    public void setCardTypeId(BigDecimal cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_CARD.CARD_NAME
     *
     * @return the value of MC_CARD.CARD_NAME
     *
     * @mbggenerated
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_CARD.CARD_NAME
     *
     * @param cardName the value for MC_CARD.CARD_NAME
     *
     * @mbggenerated
     */
    public void setCardName(String cardName) {
        this.cardName = cardName == null ? null : cardName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_CARD.CARD_NO
     *
     * @return the value of MC_CARD.CARD_NO
     *
     * @mbggenerated
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_CARD.CARD_NO
     *
     * @param cardNo the value for MC_CARD.CARD_NO
     *
     * @mbggenerated
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_CARD.STATUS
     *
     * @return the value of MC_CARD.STATUS
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_CARD.STATUS
     *
     * @param status the value for MC_CARD.STATUS
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_CARD.HIS_USER_ID
     *
     * @return the value of MC_CARD.HIS_USER_ID
     *
     * @mbggenerated
     */
    public String getHisUserId() {
        return hisUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_CARD.HIS_USER_ID
     *
     * @param hisUserId the value for MC_CARD.HIS_USER_ID
     *
     * @mbggenerated
     */
    public void setHisUserId(String hisUserId) {
        this.hisUserId = hisUserId == null ? null : hisUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_CARD.OPERATOR
     *
     * @return the value of MC_CARD.OPERATOR
     *
     * @mbggenerated
     */
    public BigDecimal getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_CARD.OPERATOR
     *
     * @param operator the value for MC_CARD.OPERATOR
     *
     * @mbggenerated
     */
    public void setOperator(BigDecimal operator) {
        this.operator = operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_CARD.OP_TIME
     *
     * @return the value of MC_CARD.OP_TIME
     *
     * @mbggenerated
     */
    public Date getOpTime() {
        return opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_CARD.OP_TIME
     *
     * @param opTime the value for MC_CARD.OP_TIME
     *
     * @mbggenerated
     */
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_CARD.OPERATOR_NAME
     *
     * @return the value of MC_CARD.OPERATOR_NAME
     *
     * @mbggenerated
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_CARD.OPERATOR_NAME
     *
     * @param operatorName the value for MC_CARD.OPERATOR_NAME
     *
     * @mbggenerated
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_CARD.FRIEND_ID
     *
     * @return the value of MC_CARD.FRIEND_ID
     *
     * @mbggenerated
     */
    public BigDecimal getFriendId() {
        return friendId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_CARD.FRIEND_ID
     *
     * @param friendId the value for MC_CARD.FRIEND_ID
     *
     * @mbggenerated
     */
    public void setFriendId(BigDecimal friendId) {
        this.friendId = friendId;
    }
}