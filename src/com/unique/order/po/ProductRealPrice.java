/**
 *
 */
package com.unique.order.po;

/**
 * 商品实际价格业务实体
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年9月10日下午4:14:40
 * 修改日期:2015年9月10日下午4:14:40
 */
public class ProductRealPrice {
	private double discountPriceD;	///折扣价格_代扣费
	private double discountPriceSite;	///折扣价格_现场付费
	private double markPrice; ///标价
	
	public double getDiscountPriceD() {
		return discountPriceD;
	}
	public void setDiscountPriceD(double discountPriceD) {
		this.discountPriceD = discountPriceD;
	}
	public double getDiscountPriceSite() {
		return discountPriceSite;
	}
	public void setDiscountPriceSite(double discountPriceSite) {
		this.discountPriceSite = discountPriceSite;
	}
	public double getMarkPrice() {
		return markPrice;
	}
	public void setMarkPrice(double markPrice) {
		this.markPrice = markPrice;
	}
}
