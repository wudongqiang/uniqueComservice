package com.unique.order.po;

import java.math.BigDecimal;

public class McOrderProduct {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_ORDER_PRODUCT.ORDER_PRODUCT_ID
     *
     * @mbggenerated
     */
    private BigDecimal orderProductId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_ORDER_PRODUCT.ORDER_ID
     *
     * @mbggenerated
     */
    private BigDecimal orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_ORDER_PRODUCT.MC_PRODUCT_ID
     *
     * @mbggenerated
     */
    private BigDecimal mcProductId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_ORDER_PRODUCT.PRODUCT_TYPE_ID
     *
     * @mbggenerated
     */
    private BigDecimal productTypeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_ORDER_PRODUCT.PRODUCT_NAME
     *
     * @mbggenerated
     */
    private String productName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_ORDER_PRODUCT.PRODUCT_PRICE
     *
     * @mbggenerated
     */
    private BigDecimal productPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_ORDER_PRODUCT.PRODUCT_NUM
     *
     * @mbggenerated
     */
    private BigDecimal productNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_ORDER_PRODUCT.PRODUCT_SUM
     *
     * @mbggenerated
     */
    private BigDecimal productSum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MC_ORDER_PRODUCT.PRODUCT_TO_ID
     *
     * @mbggenerated
     */
    private String productToId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_ORDER_PRODUCT.ORDER_PRODUCT_ID
     *
     * @return the value of MC_ORDER_PRODUCT.ORDER_PRODUCT_ID
     *
     * @mbggenerated
     */
    public BigDecimal getOrderProductId() {
        return orderProductId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_ORDER_PRODUCT.ORDER_PRODUCT_ID
     *
     * @param orderProductId the value for MC_ORDER_PRODUCT.ORDER_PRODUCT_ID
     *
     * @mbggenerated
     */
    public void setOrderProductId(BigDecimal orderProductId) {
        this.orderProductId = orderProductId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_ORDER_PRODUCT.ORDER_ID
     *
     * @return the value of MC_ORDER_PRODUCT.ORDER_ID
     *
     * @mbggenerated
     */
    public BigDecimal getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_ORDER_PRODUCT.ORDER_ID
     *
     * @param orderId the value for MC_ORDER_PRODUCT.ORDER_ID
     *
     * @mbggenerated
     */
    public void setOrderId(BigDecimal orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_ORDER_PRODUCT.MC_PRODUCT_ID
     *
     * @return the value of MC_ORDER_PRODUCT.MC_PRODUCT_ID
     *
     * @mbggenerated
     */
    public BigDecimal getMcProductId() {
        return mcProductId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_ORDER_PRODUCT.MC_PRODUCT_ID
     *
     * @param mcProductId the value for MC_ORDER_PRODUCT.MC_PRODUCT_ID
     *
     * @mbggenerated
     */
    public void setMcProductId(BigDecimal mcProductId) {
        this.mcProductId = mcProductId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_ORDER_PRODUCT.PRODUCT_TYPE_ID
     *
     * @return the value of MC_ORDER_PRODUCT.PRODUCT_TYPE_ID
     *
     * @mbggenerated
     */
    public BigDecimal getProductTypeId() {
        return productTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_ORDER_PRODUCT.PRODUCT_TYPE_ID
     *
     * @param productTypeId the value for MC_ORDER_PRODUCT.PRODUCT_TYPE_ID
     *
     * @mbggenerated
     */
    public void setProductTypeId(BigDecimal productTypeId) {
        this.productTypeId = productTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_ORDER_PRODUCT.PRODUCT_NAME
     *
     * @return the value of MC_ORDER_PRODUCT.PRODUCT_NAME
     *
     * @mbggenerated
     */
    public String getProductName() {
        return productName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_ORDER_PRODUCT.PRODUCT_NAME
     *
     * @param productName the value for MC_ORDER_PRODUCT.PRODUCT_NAME
     *
     * @mbggenerated
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_ORDER_PRODUCT.PRODUCT_PRICE
     *
     * @return the value of MC_ORDER_PRODUCT.PRODUCT_PRICE
     *
     * @mbggenerated
     */
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_ORDER_PRODUCT.PRODUCT_PRICE
     *
     * @param productPrice the value for MC_ORDER_PRODUCT.PRODUCT_PRICE
     *
     * @mbggenerated
     */
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_ORDER_PRODUCT.PRODUCT_NUM
     *
     * @return the value of MC_ORDER_PRODUCT.PRODUCT_NUM
     *
     * @mbggenerated
     */
    public BigDecimal getProductNum() {
        return productNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_ORDER_PRODUCT.PRODUCT_NUM
     *
     * @param productNum the value for MC_ORDER_PRODUCT.PRODUCT_NUM
     *
     * @mbggenerated
     */
    public void setProductNum(BigDecimal productNum) {
        this.productNum = productNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_ORDER_PRODUCT.PRODUCT_SUM
     *
     * @return the value of MC_ORDER_PRODUCT.PRODUCT_SUM
     *
     * @mbggenerated
     */
    public BigDecimal getProductSum() {
        return productSum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_ORDER_PRODUCT.PRODUCT_SUM
     *
     * @param productSum the value for MC_ORDER_PRODUCT.PRODUCT_SUM
     *
     * @mbggenerated
     */
    public void setProductSum(BigDecimal productSum) {
        this.productSum = productSum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MC_ORDER_PRODUCT.PRODUCT_TO_ID
     *
     * @return the value of MC_ORDER_PRODUCT.PRODUCT_TO_ID
     *
     * @mbggenerated
     */
    public String getProductToId() {
        return productToId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MC_ORDER_PRODUCT.PRODUCT_TO_ID
     *
     * @param productToId the value for MC_ORDER_PRODUCT.PRODUCT_TO_ID
     *
     * @mbggenerated
     */
    public void setProductToId(String productToId) {
        this.productToId = productToId == null ? null : productToId.trim();
    }
}