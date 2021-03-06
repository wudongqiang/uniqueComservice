package com.unique.plan.po;

import java.math.BigDecimal;
import java.util.Date;

public class HealthyTestPlan {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HEALTHY_TEST_PLAN.PLAN_ID
     *
     * @mbggenerated
     */
    private BigDecimal planId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HEALTHY_TEST_PLAN.ORG_ID
     *
     * @mbggenerated
     */
    private BigDecimal orgId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HEALTHY_TEST_PLAN.DEP_ID
     *
     * @mbggenerated
     */
    private BigDecimal depId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HEALTHY_TEST_PLAN.STAFF_ID
     *
     * @mbggenerated
     */
    private BigDecimal staffId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HEALTHY_TEST_PLAN.USER_ID
     *
     * @mbggenerated
     */
    private BigDecimal userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HEALTHY_TEST_PLAN.PLAN_BEGIN_TIME
     *
     * @mbggenerated
     */
    private Date planBeginTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HEALTHY_TEST_PLAN.PLAN_END_TIME
     *
     * @mbggenerated
     */
    private Date planEndTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HEALTHY_TEST_PLAN.PERIOD_VALUE
     *
     * @mbggenerated
     */
    private BigDecimal periodValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HEALTHY_TEST_PLAN.PERIOD_TYPE
     *
     * @mbggenerated
     */
    private BigDecimal periodType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HEALTHY_TEST_PLAN.CREATE_TIME
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HEALTHY_TEST_PLAN.OP_TIME
     *
     * @mbggenerated
     */
    private Date opTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HEALTHY_TEST_PLAN.OPERATOR
     *
     * @mbggenerated
     */
    private BigDecimal operator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HEALTHY_TEST_PLAN.STATUS
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HEALTHY_TEST_PLAN.HEALTHY_ID
     *
     * @mbggenerated
     */
    private BigDecimal healthyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HEALTHY_TEST_PLAN.TEST_TEMPLATE_ID
     *
     * @mbggenerated
     */
    private BigDecimal testTemplateId;
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	private String userName;
    private String staffName;
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HEALTHY_TEST_PLAN.PLAN_ID
     *
     * @return the value of HEALTHY_TEST_PLAN.PLAN_ID
     *
     * @mbggenerated
     */
    public BigDecimal getPlanId() {
        return planId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HEALTHY_TEST_PLAN.PLAN_ID
     *
     * @param planId the value for HEALTHY_TEST_PLAN.PLAN_ID
     *
     * @mbggenerated
     */
    public void setPlanId(BigDecimal planId) {
        this.planId = planId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HEALTHY_TEST_PLAN.ORG_ID
     *
     * @return the value of HEALTHY_TEST_PLAN.ORG_ID
     *
     * @mbggenerated
     */
    public BigDecimal getOrgId() {
        return orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HEALTHY_TEST_PLAN.ORG_ID
     *
     * @param orgId the value for HEALTHY_TEST_PLAN.ORG_ID
     *
     * @mbggenerated
     */
    public void setOrgId(BigDecimal orgId) {
        this.orgId = orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HEALTHY_TEST_PLAN.DEP_ID
     *
     * @return the value of HEALTHY_TEST_PLAN.DEP_ID
     *
     * @mbggenerated
     */
    public BigDecimal getDepId() {
        return depId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HEALTHY_TEST_PLAN.DEP_ID
     *
     * @param depId the value for HEALTHY_TEST_PLAN.DEP_ID
     *
     * @mbggenerated
     */
    public void setDepId(BigDecimal depId) {
        this.depId = depId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HEALTHY_TEST_PLAN.STAFF_ID
     *
     * @return the value of HEALTHY_TEST_PLAN.STAFF_ID
     *
     * @mbggenerated
     */
    public BigDecimal getStaffId() {
        return staffId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HEALTHY_TEST_PLAN.STAFF_ID
     *
     * @param staffId the value for HEALTHY_TEST_PLAN.STAFF_ID
     *
     * @mbggenerated
     */
    public void setStaffId(BigDecimal staffId) {
        this.staffId = staffId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HEALTHY_TEST_PLAN.USER_ID
     *
     * @return the value of HEALTHY_TEST_PLAN.USER_ID
     *
     * @mbggenerated
     */
    public BigDecimal getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HEALTHY_TEST_PLAN.USER_ID
     *
     * @param userId the value for HEALTHY_TEST_PLAN.USER_ID
     *
     * @mbggenerated
     */
    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HEALTHY_TEST_PLAN.PLAN_BEGIN_TIME
     *
     * @return the value of HEALTHY_TEST_PLAN.PLAN_BEGIN_TIME
     *
     * @mbggenerated
     */
    public Date getPlanBeginTime() {
        return planBeginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HEALTHY_TEST_PLAN.PLAN_BEGIN_TIME
     *
     * @param planBeginTime the value for HEALTHY_TEST_PLAN.PLAN_BEGIN_TIME
     *
     * @mbggenerated
     */
    public void setPlanBeginTime(Date planBeginTime) {
        this.planBeginTime = planBeginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HEALTHY_TEST_PLAN.PLAN_END_TIME
     *
     * @return the value of HEALTHY_TEST_PLAN.PLAN_END_TIME
     *
     * @mbggenerated
     */
    public Date getPlanEndTime() {
        return planEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HEALTHY_TEST_PLAN.PLAN_END_TIME
     *
     * @param planEndTime the value for HEALTHY_TEST_PLAN.PLAN_END_TIME
     *
     * @mbggenerated
     */
    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HEALTHY_TEST_PLAN.PERIOD_VALUE
     *
     * @return the value of HEALTHY_TEST_PLAN.PERIOD_VALUE
     *
     * @mbggenerated
     */
    public BigDecimal getPeriodValue() {
        return periodValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HEALTHY_TEST_PLAN.PERIOD_VALUE
     *
     * @param periodValue the value for HEALTHY_TEST_PLAN.PERIOD_VALUE
     *
     * @mbggenerated
     */
    public void setPeriodValue(BigDecimal periodValue) {
        this.periodValue = periodValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HEALTHY_TEST_PLAN.PERIOD_TYPE
     *
     * @return the value of HEALTHY_TEST_PLAN.PERIOD_TYPE
     *
     * @mbggenerated
     */
    public BigDecimal getPeriodType() {
        return periodType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HEALTHY_TEST_PLAN.PERIOD_TYPE
     *
     * @param periodType the value for HEALTHY_TEST_PLAN.PERIOD_TYPE
     *
     * @mbggenerated
     */
    public void setPeriodType(BigDecimal periodType) {
        this.periodType = periodType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HEALTHY_TEST_PLAN.CREATE_TIME
     *
     * @return the value of HEALTHY_TEST_PLAN.CREATE_TIME
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HEALTHY_TEST_PLAN.CREATE_TIME
     *
     * @param createTime the value for HEALTHY_TEST_PLAN.CREATE_TIME
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HEALTHY_TEST_PLAN.OP_TIME
     *
     * @return the value of HEALTHY_TEST_PLAN.OP_TIME
     *
     * @mbggenerated
     */
    public Date getOpTime() {
        return opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HEALTHY_TEST_PLAN.OP_TIME
     *
     * @param opTime the value for HEALTHY_TEST_PLAN.OP_TIME
     *
     * @mbggenerated
     */
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HEALTHY_TEST_PLAN.OPERATOR
     *
     * @return the value of HEALTHY_TEST_PLAN.OPERATOR
     *
     * @mbggenerated
     */
    public BigDecimal getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HEALTHY_TEST_PLAN.OPERATOR
     *
     * @param operator the value for HEALTHY_TEST_PLAN.OPERATOR
     *
     * @mbggenerated
     */
    public void setOperator(BigDecimal operator) {
        this.operator = operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HEALTHY_TEST_PLAN.STATUS
     *
     * @return the value of HEALTHY_TEST_PLAN.STATUS
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HEALTHY_TEST_PLAN.STATUS
     *
     * @param status the value for HEALTHY_TEST_PLAN.STATUS
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HEALTHY_TEST_PLAN.HEALTHY_ID
     *
     * @return the value of HEALTHY_TEST_PLAN.HEALTHY_ID
     *
     * @mbggenerated
     */
    public BigDecimal getHealthyId() {
        return healthyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HEALTHY_TEST_PLAN.HEALTHY_ID
     *
     * @param healthyId the value for HEALTHY_TEST_PLAN.HEALTHY_ID
     *
     * @mbggenerated
     */
    public void setHealthyId(BigDecimal healthyId) {
        this.healthyId = healthyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HEALTHY_TEST_PLAN.TEST_TEMPLATE_ID
     *
     * @return the value of HEALTHY_TEST_PLAN.TEST_TEMPLATE_ID
     *
     * @mbggenerated
     */
    public BigDecimal getTestTemplateId() {
        return testTemplateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HEALTHY_TEST_PLAN.TEST_TEMPLATE_ID
     *
     * @param testTemplateId the value for HEALTHY_TEST_PLAN.TEST_TEMPLATE_ID
     *
     * @mbggenerated
     */
    public void setTestTemplateId(BigDecimal testTemplateId) {
        this.testTemplateId = testTemplateId;
    }
}