/**
 *
 */
package com.unique.order.po;

/**
 * 商品购买信息实体
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年9月17日上午11:31:04
 * 修改日期:2015年9月17日上午11:31:04
 */
public class ProductPayInfo {
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	private double price;
	private String productId; ///商品类别ID
	private long count; ///购买数量
	private String toId; ///业务ID
}
