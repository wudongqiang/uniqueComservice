/**
 *
 */
package com.tencent;

import javax.transaction.Transactional;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年10月7日上午9:22:27
 * 修改日期:2015年10月7日上午9:22:27
 */
public interface WXPayService {
	/**
	 * 创建支付订单信息(这里并不需要提交，只需要交给前端去提交)
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月29日下午2:50:05
	 * 修改日期:2015年9月29日下午2:50:05
	 * @param productName 商品名称
	 * @param price 金额
	 * @param orderNo 订单号
	 * @param tradeType 支付类型
	 * @param openId 公众号
	 * @param wxProductId 微信商品ID
	 * @return
	 */
	public String buildPayInfo(String productName,String price,String orderNo,String tradeType,String openId,String wxProductId,String ip);
	
	/**
	 * 微信支付，订单退费接口
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月7日上午9:32:23
	 * 修改日期:2015年10月7日上午9:32:23
	 * @param transactionID
	 * @param orderNo
	 * @param outRefundNo
	 */
	public void refund(String orderNo);
	
	public Object buildPayInfoJS(String productName,String price,String orderNo,String tradeType,String openId,String wxProductId,String ip);
}
