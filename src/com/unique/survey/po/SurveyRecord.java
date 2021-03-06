package com.unique.survey.po;

import java.math.BigDecimal;
import java.util.Date;

import com.unique.core.util.FileUtil;
import com.unique.core.util.StringUtil;

public class SurveyRecord {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_RECORD.SURVEY_ID
     *
     * @mbggenerated
     */
    private BigDecimal surveyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_RECORD.ORG_ID
     *
     * @mbggenerated
     */
    private BigDecimal orgId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_RECORD.USER_ID
     *
     * @mbggenerated
     */
    private BigDecimal userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_RECORD.STAFF_ID
     *
     * @mbggenerated
     */
    private BigDecimal staffId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_RECORD.DEP_ID
     *
     * @mbggenerated
     */
    private BigDecimal depId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_RECORD.SURVEY_MOUDLE_ID
     *
     * @mbggenerated
     */
    private BigDecimal surveyMoudleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_RECORD.USER_NAME
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_RECORD.AGE
     *
     * @mbggenerated
     */
    private BigDecimal age;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_RECORD.BIRTH
     *
     * @mbggenerated
     */
    private Date birth;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_RECORD.STAFF_NAME
     *
     * @mbggenerated
     */
    private String staffName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_RECORD.STATUS
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_RECORD.SRUVEY_DATE
     *
     * @mbggenerated
     */
    private Date sruveyDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_RECORD.NEXT_SRUVEY_DATE
     *
     * @mbggenerated
     */
    private Date nextSruveyDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_RECORD.REMARK
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_RECORD.CREATE_TIME
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_RECORD.OP_TIME
     *
     * @mbggenerated
     */
    private Date opTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_RECORD.OPERATOR
     *
     * @mbggenerated
     */
    private BigDecimal operator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SURVEY_RECORD.RESULT
     *
     * @mbggenerated
     */
    private BigDecimal result;

    private Date fillTime;
    
    ///图片服务器
  	private final static String PIC_SERVER = FileUtil.readProperties("comservice.properties", "picServerOut");
  	
    private String imgUrl;//患者头像
    private String sex;
    
    public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		if(!StringUtil.isEmpty(imgUrl)){
			this.imgUrl = PIC_SERVER + imgUrl;
		}
	}

	public String getSurveyMoudleName() {
		return SurveyMoudleName;
	}

	public void setSurveyMoudleName(String surveyMoudleName) {
		SurveyMoudleName = surveyMoudleName;
	}

	public String getIllName() {
		return illName;
	}

	public void setIllName(String illName) {
		this.illName = illName;
	}

	private String SurveyMoudleName;
    private String illName;
    
    public Date getFillTime() {
		return fillTime;
	}

	public void setFillTime(Date fillTime) {
		this.fillTime = fillTime;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_RECORD.SURVEY_ID
     *
     * @return the value of SURVEY_RECORD.SURVEY_ID
     *
     * @mbggenerated
     */
    public BigDecimal getSurveyId() {
        return surveyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_RECORD.SURVEY_ID
     *
     * @param surveyId the value for SURVEY_RECORD.SURVEY_ID
     *
     * @mbggenerated
     */
    public void setSurveyId(BigDecimal surveyId) {
        this.surveyId = surveyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_RECORD.ORG_ID
     *
     * @return the value of SURVEY_RECORD.ORG_ID
     *
     * @mbggenerated
     */
    public BigDecimal getOrgId() {
        return orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_RECORD.ORG_ID
     *
     * @param orgId the value for SURVEY_RECORD.ORG_ID
     *
     * @mbggenerated
     */
    public void setOrgId(BigDecimal orgId) {
        this.orgId = orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_RECORD.USER_ID
     *
     * @return the value of SURVEY_RECORD.USER_ID
     *
     * @mbggenerated
     */
    public BigDecimal getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_RECORD.USER_ID
     *
     * @param userId the value for SURVEY_RECORD.USER_ID
     *
     * @mbggenerated
     */
    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_RECORD.STAFF_ID
     *
     * @return the value of SURVEY_RECORD.STAFF_ID
     *
     * @mbggenerated
     */
    public BigDecimal getStaffId() {
        return staffId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_RECORD.STAFF_ID
     *
     * @param staffId the value for SURVEY_RECORD.STAFF_ID
     *
     * @mbggenerated
     */
    public void setStaffId(BigDecimal staffId) {
        this.staffId = staffId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_RECORD.DEP_ID
     *
     * @return the value of SURVEY_RECORD.DEP_ID
     *
     * @mbggenerated
     */
    public BigDecimal getDepId() {
        return depId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_RECORD.DEP_ID
     *
     * @param depId the value for SURVEY_RECORD.DEP_ID
     *
     * @mbggenerated
     */
    public void setDepId(BigDecimal depId) {
        this.depId = depId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_RECORD.SURVEY_MOUDLE_ID
     *
     * @return the value of SURVEY_RECORD.SURVEY_MOUDLE_ID
     *
     * @mbggenerated
     */
    public BigDecimal getSurveyMoudleId() {
        return surveyMoudleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_RECORD.SURVEY_MOUDLE_ID
     *
     * @param surveyMoudleId the value for SURVEY_RECORD.SURVEY_MOUDLE_ID
     *
     * @mbggenerated
     */
    public void setSurveyMoudleId(BigDecimal surveyMoudleId) {
        this.surveyMoudleId = surveyMoudleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_RECORD.USER_NAME
     *
     * @return the value of SURVEY_RECORD.USER_NAME
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_RECORD.USER_NAME
     *
     * @param userName the value for SURVEY_RECORD.USER_NAME
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_RECORD.AGE
     *
     * @return the value of SURVEY_RECORD.AGE
     *
     * @mbggenerated
     */
    public BigDecimal getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_RECORD.AGE
     *
     * @param age the value for SURVEY_RECORD.AGE
     *
     * @mbggenerated
     */
    public void setAge(BigDecimal age) {
        this.age = age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_RECORD.BIRTH
     *
     * @return the value of SURVEY_RECORD.BIRTH
     *
     * @mbggenerated
     */
    public Date getBirth() {
        return birth;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_RECORD.BIRTH
     *
     * @param birth the value for SURVEY_RECORD.BIRTH
     *
     * @mbggenerated
     */
    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_RECORD.STAFF_NAME
     *
     * @return the value of SURVEY_RECORD.STAFF_NAME
     *
     * @mbggenerated
     */
    public String getStaffName() {
        return staffName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_RECORD.STAFF_NAME
     *
     * @param staffName the value for SURVEY_RECORD.STAFF_NAME
     *
     * @mbggenerated
     */
    public void setStaffName(String staffName) {
        this.staffName = staffName == null ? null : staffName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_RECORD.STATUS
     *
     * @return the value of SURVEY_RECORD.STATUS
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_RECORD.STATUS
     *
     * @param status the value for SURVEY_RECORD.STATUS
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_RECORD.SRUVEY_DATE
     *
     * @return the value of SURVEY_RECORD.SRUVEY_DATE
     *
     * @mbggenerated
     */
    public Date getSruveyDate() {
        return sruveyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_RECORD.SRUVEY_DATE
     *
     * @param sruveyDate the value for SURVEY_RECORD.SRUVEY_DATE
     *
     * @mbggenerated
     */
    public void setSruveyDate(Date sruveyDate) {
        this.sruveyDate = sruveyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_RECORD.NEXT_SRUVEY_DATE
     *
     * @return the value of SURVEY_RECORD.NEXT_SRUVEY_DATE
     *
     * @mbggenerated
     */
    public Date getNextSruveyDate() {
        return nextSruveyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_RECORD.NEXT_SRUVEY_DATE
     *
     * @param nextSruveyDate the value for SURVEY_RECORD.NEXT_SRUVEY_DATE
     *
     * @mbggenerated
     */
    public void setNextSruveyDate(Date nextSruveyDate) {
        this.nextSruveyDate = nextSruveyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_RECORD.REMARK
     *
     * @return the value of SURVEY_RECORD.REMARK
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_RECORD.REMARK
     *
     * @param remark the value for SURVEY_RECORD.REMARK
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_RECORD.CREATE_TIME
     *
     * @return the value of SURVEY_RECORD.CREATE_TIME
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_RECORD.CREATE_TIME
     *
     * @param createTime the value for SURVEY_RECORD.CREATE_TIME
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_RECORD.OP_TIME
     *
     * @return the value of SURVEY_RECORD.OP_TIME
     *
     * @mbggenerated
     */
    public Date getOpTime() {
        return opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_RECORD.OP_TIME
     *
     * @param opTime the value for SURVEY_RECORD.OP_TIME
     *
     * @mbggenerated
     */
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_RECORD.OPERATOR
     *
     * @return the value of SURVEY_RECORD.OPERATOR
     *
     * @mbggenerated
     */
    public BigDecimal getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_RECORD.OPERATOR
     *
     * @param operator the value for SURVEY_RECORD.OPERATOR
     *
     * @mbggenerated
     */
    public void setOperator(BigDecimal operator) {
        this.operator = operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SURVEY_RECORD.RESULT
     *
     * @return the value of SURVEY_RECORD.RESULT
     *
     * @mbggenerated
     */
    public BigDecimal getResult() {
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SURVEY_RECORD.RESULT
     *
     * @param result the value for SURVEY_RECORD.RESULT
     *
     * @mbggenerated
     */
    public void setResult(BigDecimal result) {
        this.result = result;
    }
}