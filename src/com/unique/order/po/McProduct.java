package com.unique.order.po;

import java.math.BigDecimal;
import java.util.Date;

import com.unique.core.util.FileUtil;
import com.unique.core.util.StringUtil;

public class McProduct {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_PRODUCT.MC_PRODUCT_ID
     *
     * @mbggenerated
     */
    private BigDecimal mcProductId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_PRODUCT.ORG_ID
     *
     * @mbggenerated
     */
    private BigDecimal orgId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_PRODUCT.UNIT_ID
     *
     * @mbggenerated
     */
    private BigDecimal unitId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_PRODUCT.ORG_ORG_ID
     *
     * @mbggenerated
     */
    private BigDecimal orgOrgId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_PRODUCT.PRODUCT_NAME
     *
     * @mbggenerated
     */
    private String productName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_PRODUCT.PRODUCT_CODE
     *
     * @mbggenerated
     */
    private String productCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_PRODUCT.DESCRIPTION
     *
     * @mbggenerated
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_PRODUCT.OPERATOR
     *
     * @mbggenerated
     */
    private String operator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_PRODUCT.OP_TIME
     *
     * @mbggenerated
     */
    private Date opTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_PRODUCT.OPERATOR_NAME
     *
     * @mbggenerated
     */
    private String operatorName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_PRODUCT.STATUS
     *
     * @mbggenerated
     */
    private String status;

    private String recoupWay;


	private BigDecimal productNum;
    private String subHead;
    private String buyNotice;
    private String process;
    
    private ProductRealPrice productRealPrice;
    
    private String productImage;
    private String productTypeName;
    
    private String orgName;
    
    public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public static String getPicServer() {
		return PIC_SERVER;
	}

	///图片服务器
  	private final static String PIC_SERVER = FileUtil.readProperties("comservice.properties", "picServerOut");
    
    public String getProductImage() {
		return productImage;
	}
 
	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public void setProductImage(String productImage) {
		if(!StringUtil.isEmpty(productImage)){
			this.productImage = PIC_SERVER+productImage;
		}
	}

	public ProductRealPrice getProductRealPrice() {
		return productRealPrice;
	}

	public void setProductRealPrice(ProductRealPrice productRealPrice) {
		this.productRealPrice = productRealPrice;
	}

	public BigDecimal getProductNum() {
		return productNum;
	}

	public void setProductNum(BigDecimal productNum) {
		this.productNum = productNum;
	}

	public String getSubHead() {
		return subHead;
	}

	public void setSubHead(String subHead) {
		this.subHead = subHead;
	}

	public String getBuyNotice() {
		return buyNotice;
	}

	public void setBuyNotice(String buyNotice) {
		this.buyNotice = buyNotice;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}
    public String getRecoupWay() {
		return recoupWay;
	}

	public void setRecoupWay(String recoupWay) {
		this.recoupWay = recoupWay;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_PRODUCT.MC_PRODUCT_ID
     *
     * @return the value of MC_PRODUCT.MC_PRODUCT_ID
     *
     * @mbggenerated
     */
    public BigDecimal getMcProductId() {
        return mcProductId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_PRODUCT.MC_PRODUCT_ID
     *
     * @param mcProductId the value for MC_PRODUCT.MC_PRODUCT_ID
     *
     * @mbggenerated
     */
    public void setMcProductId(BigDecimal mcProductId) {
        this.mcProductId = mcProductId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_PRODUCT.ORG_ID
     *
     * @return the value of MC_PRODUCT.ORG_ID
     *
     * @mbggenerated
     */
    public BigDecimal getOrgId() {
        return orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_PRODUCT.ORG_ID
     *
     * @param orgId the value for MC_PRODUCT.ORG_ID
     *
     * @mbggenerated
     */
    public void setOrgId(BigDecimal orgId) {
        this.orgId = orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_PRODUCT.UNIT_ID
     *
     * @return the value of MC_PRODUCT.UNIT_ID
     *
     * @mbggenerated
     */
    public BigDecimal getUnitId() {
        return unitId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_PRODUCT.UNIT_ID
     *
     * @param unitId the value for MC_PRODUCT.UNIT_ID
     *
     * @mbggenerated
     */
    public void setUnitId(BigDecimal unitId) {
        this.unitId = unitId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_PRODUCT.ORG_ORG_ID
     *
     * @return the value of MC_PRODUCT.ORG_ORG_ID
     *
     * @mbggenerated
     */
    public BigDecimal getOrgOrgId() {
        return orgOrgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_PRODUCT.ORG_ORG_ID
     *
     * @param orgOrgId the value for MC_PRODUCT.ORG_ORG_ID
     *
     * @mbggenerated
     */
    public void setOrgOrgId(BigDecimal orgOrgId) {
        this.orgOrgId = orgOrgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_PRODUCT.PRODUCT_NAME
     *
     * @return the value of MC_PRODUCT.PRODUCT_NAME
     *
     * @mbggenerated
     */
    public String getProductName() {
        return productName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_PRODUCT.PRODUCT_NAME
     *
     * @param productName the value for MC_PRODUCT.PRODUCT_NAME
     *
     * @mbggenerated
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_PRODUCT.PRODUCT_CODE
     *
     * @return the value of MC_PRODUCT.PRODUCT_CODE
     *
     * @mbggenerated
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_PRODUCT.PRODUCT_CODE
     *
     * @param productCode the value for MC_PRODUCT.PRODUCT_CODE
     *
     * @mbggenerated
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_PRODUCT.DESCRIPTION
     *
     * @return the value of MC_PRODUCT.DESCRIPTION
     *
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_PRODUCT.DESCRIPTION
     *
     * @param description the value for MC_PRODUCT.DESCRIPTION
     *
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_PRODUCT.OPERATOR
     *
     * @return the value of MC_PRODUCT.OPERATOR
     *
     * @mbggenerated
     */
    public String getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_PRODUCT.OPERATOR
     *
     * @param operator the value for MC_PRODUCT.OPERATOR
     *
     * @mbggenerated
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_PRODUCT.OP_TIME
     *
     * @return the value of MC_PRODUCT.OP_TIME
     *
     * @mbggenerated
     */
    public Date getOpTime() {
        return opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_PRODUCT.OP_TIME
     *
     * @param opTime the value for MC_PRODUCT.OP_TIME
     *
     * @mbggenerated
     */
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_PRODUCT.OPERATOR_NAME
     *
     * @return the value of MC_PRODUCT.OPERATOR_NAME
     *
     * @mbggenerated
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_PRODUCT.OPERATOR_NAME
     *
     * @param operatorName the value for MC_PRODUCT.OPERATOR_NAME
     *
     * @mbggenerated
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_PRODUCT.STATUS
     *
     * @return the value of MC_PRODUCT.STATUS
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_PRODUCT.STATUS
     *
     * @param status the value for MC_PRODUCT.STATUS
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}