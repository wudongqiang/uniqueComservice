package com.unique.guide.po;

import java.math.BigDecimal;
import java.util.Date;

import com.unique.core.util.FileUtil;
import com.unique.core.util.StringUtil;

public class JbBodyPart {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JB_BODY_PART.BP_ID
     *
     * @mbggenerated
     */
    private BigDecimal bpId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JB_BODY_PART.PART_NAME
     *
     * @mbggenerated
     */
    private String partName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JB_BODY_PART.PART_CODE
     *
     * @mbggenerated
     */
    private String partCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JB_BODY_PART.PARENT_ID
     *
     * @mbggenerated
     */
    private BigDecimal parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JB_BODY_PART.PART_DESC
     *
     * @mbggenerated
     */
    private String partDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JB_BODY_PART.STATUS
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JB_BODY_PART.OP_TIME
     *
     * @mbggenerated
     */
    private Date opTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JB_BODY_PART.OPERATOR
     *
     * @mbggenerated
     */
    private BigDecimal operator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JB_BODY_PART.OPERATOR_NAME
     *
     * @mbggenerated
     */
    private String operatorName;
    
    ///图片服务器
  	private final static String PIC_SERVER = FileUtil.readProperties("comservice.properties", "picServerOut");
  	
    private String partPic;//部位图标

    public String getPartPic() {
		return partPic;
	}

	public void setPartPic(String partPic) {
		if(!StringUtil.isEmpty(partPic)){
			this.partPic = PIC_SERVER+partPic;
		}
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JB_BODY_PART.BP_ID
     *
     * @return the value of JB_BODY_PART.BP_ID
     *
     * @mbggenerated
     */
    public BigDecimal getBpId() {
        return bpId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JB_BODY_PART.BP_ID
     *
     * @param bpId the value for JB_BODY_PART.BP_ID
     *
     * @mbggenerated
     */
    public void setBpId(BigDecimal bpId) {
        this.bpId = bpId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JB_BODY_PART.PART_NAME
     *
     * @return the value of JB_BODY_PART.PART_NAME
     *
     * @mbggenerated
     */
    public String getPartName() {
        return partName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JB_BODY_PART.PART_NAME
     *
     * @param partName the value for JB_BODY_PART.PART_NAME
     *
     * @mbggenerated
     */
    public void setPartName(String partName) {
        this.partName = partName == null ? null : partName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JB_BODY_PART.PART_CODE
     *
     * @return the value of JB_BODY_PART.PART_CODE
     *
     * @mbggenerated
     */
    public String getPartCode() {
        return partCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JB_BODY_PART.PART_CODE
     *
     * @param partCode the value for JB_BODY_PART.PART_CODE
     *
     * @mbggenerated
     */
    public void setPartCode(String partCode) {
        this.partCode = partCode == null ? null : partCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JB_BODY_PART.PARENT_ID
     *
     * @return the value of JB_BODY_PART.PARENT_ID
     *
     * @mbggenerated
     */
    public BigDecimal getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JB_BODY_PART.PARENT_ID
     *
     * @param parentId the value for JB_BODY_PART.PARENT_ID
     *
     * @mbggenerated
     */
    public void setParentId(BigDecimal parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JB_BODY_PART.PART_DESC
     *
     * @return the value of JB_BODY_PART.PART_DESC
     *
     * @mbggenerated
     */
    public String getPartDesc() {
        return partDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JB_BODY_PART.PART_DESC
     *
     * @param partDesc the value for JB_BODY_PART.PART_DESC
     *
     * @mbggenerated
     */
    public void setPartDesc(String partDesc) {
        this.partDesc = partDesc == null ? null : partDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JB_BODY_PART.STATUS
     *
     * @return the value of JB_BODY_PART.STATUS
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JB_BODY_PART.STATUS
     *
     * @param status the value for JB_BODY_PART.STATUS
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JB_BODY_PART.OP_TIME
     *
     * @return the value of JB_BODY_PART.OP_TIME
     *
     * @mbggenerated
     */
    public Date getOpTime() {
        return opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JB_BODY_PART.OP_TIME
     *
     * @param opTime the value for JB_BODY_PART.OP_TIME
     *
     * @mbggenerated
     */
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JB_BODY_PART.OPERATOR
     *
     * @return the value of JB_BODY_PART.OPERATOR
     *
     * @mbggenerated
     */
    public BigDecimal getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JB_BODY_PART.OPERATOR
     *
     * @param operator the value for JB_BODY_PART.OPERATOR
     *
     * @mbggenerated
     */
    public void setOperator(BigDecimal operator) {
        this.operator = operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JB_BODY_PART.OPERATOR_NAME
     *
     * @return the value of JB_BODY_PART.OPERATOR_NAME
     *
     * @mbggenerated
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JB_BODY_PART.OPERATOR_NAME
     *
     * @param operatorName the value for JB_BODY_PART.OPERATOR_NAME
     *
     * @mbggenerated
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }
}