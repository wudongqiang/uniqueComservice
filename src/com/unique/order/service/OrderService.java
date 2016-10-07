package com.unique.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.order.po.McOrder;
import com.unique.order.po.McOrderBackDet;
import com.unique.order.po.OrgPatient;
import com.unique.order.po.ProductPayInfo;
import com.unique.order.po.ProductRealPrice;
import com.unique.reg.po.Org;

public interface OrderService {
	
	public HashMap<String, Object> placeOrder(String orderTypeCode,String userId,
			List<ProductPayInfo> productChooses,
			String staffTypeId,String sourceCode,String friendId,OrgPatient orgPatient,String hisUserId);
	
	/**
	 * 关闭订单
	 */
	public void closeOrder(String orderId);
	
	/**
	 * 支付日志记录
	 * @param regWayId 支付方式ID
	 * @param orderId 订单ID
	 */
	public String addPayLog(String payWayId,String orderId,String payAcct,String payBank);
	
	/**
	 * 添加账单并创建分成比例
	 * @param order
	 * @param payNo
	 * @param payWayId
	 * @param realMoney
	 */
	public void addBill(McOrder order,String payNo,String payWayId);
	
	/**
	 * 支付成功
	 */
	public void paySuccess(String payWayCode,String payBank,String payNo,String orderNo,String orderId);
	
	public HashMap<String, Object> addUnOrderInfo(String orderTypeCode,String userId,String opUserId,List<McOrderBackDet> orderDets,String backReason,String orderId);
	
	public List<McOrder> orderList(String userId,Long startRow,Long rows,String[] status);

	public HashMap<String, Object> orderListCount(String userId,String[] status);
	
	public HashMap<String,Object> unOrder( String orderId,
			 String opUserId,
			 String backReason,
			 String userId,
			 String refundType,
			 Boolean doHospital);
	
	/**
	 * 根据类型获取产品列表
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月10日下午2:53:22
	 * 修改日期:2015年9月10日下午2:53:22
	 * @param typeAtt 0 业务商品 1 销售商品
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public HashMap<String,Object> getProductsList(String typeAtt,Long startRow,Long rows);
	
	/**
	 * 根据挂号级别获取价格
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月10日下午4:18:46
	 * 修改日期:2015年9月10日下午4:18:46
	 * @param staffTypeId
	 * @param org
	 * @return
	 */
	public ProductRealPrice getPriceByStaffType(String staffTypeId,Org org);
	
	/**
	 * 根据产品ID获取各种价格
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月10日下午4:41:41
	 * 修改日期:2015年9月10日下午4:41:41
	 * @param productId
	 * @return
	 */
	public ProductRealPrice getPriceByProductId(String productId);
	
	/**
	 * 购买商品接口
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月22日下午2:45:50
	 * 修改日期:2015年9月22日下午2:45:50
	 * @param orderTypeCode 订单类别编码:挂号：GH，VIP卡：VIP_CARD，诊间缴费：ZJ_PAY
	 * @param userId 下单用户ID
	 * @param productChooses 选择产品集合
	 * @param staffTypeId 挂号级别ID（挂号专用）
	 * @param sourceCode 来源编码
	 * @return {orderId:订单ID,d_price:在线付费费用,site_price:现场付费费用,price_ori:原始金额,orderNo:订单编号,payWays:[支付方式对象]}
	 */
	public HashMap<String, Object> buy(String orderTypeCode,
			String userId,
			List<ProductPayInfo> productChooses,
			String staffTypeId,String sourceCode,String friendId,OrgPatient orgPatient,String hisUserId);
	
	public void updateOrder(McOrder order);

	/**
	 * 选择支付方式预先生成订单信息
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月29日下午2:08:16
	 * 修改日期:2015年9月29日下午2:08:16
	 * @param orderId 订单ID
	 * @param payWayCode 支付方式编码
	 * @param bankNo 银行卡号
	 * @param payType 支付类型 默认传空，W 表示微信调用支付宝支付（微信）
	 * @param returnUrl 支付接口回调地址（微信）
	 * @param showUrl 订单展示地址（微信）
	 * @param tradeType 微信支付用：JSAPI、NATIVE、APP(微信支付)
	 * @param openId 公众号ID(微信支付)
	 * @param wxProductId 微信商品ID(微信支付)
	 * @return
	 */
	public HashMap<String, Object> usePayWay(
			String orderId,
			String payWayCode,
			String bankNo,
			String payType, 
			String returnUrl,
			String showUrl,
			String tradeType, ///微信支付用：JSAPI、NATIVE、APP
			String openId,		///公众号ID
			String wxProductId, ///微信商品ID
			String ip ///支付终端IP
			);
	
	/**
	 * 通过订单Id获取订单详情<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年9月28日 下午3:22:30<br/>
	 * @param orderId
	 * @return
	 */
	public Map<String,Object> getOrderDetailsByOrderId(String orderId);

	/**
	 * 获取商品详情<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年9月29日 下午4:50:11<br/>
	 * @param productId
	 * @return
	 */
	public Map<String, Object> getMcProduct(String productId);
	
	/**
	 * 订单退费成功
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月8日上午10:11:33
	 * 修改日期:2015年10月8日上午10:11:33
	 */
	public void orderRefundSuccess(String orderNo);
	
	/**
	 * 检测订单状态
	 * @param regId 挂号记录ID
	 * @return orderStatus:1待支付，2待支付已过期，3支付成功交易中，4支付成功交易失败，5支付成功交易成功，6退款中，7已退款,8交易取消
	 */
	@HttpMethod
	public HashMap<String, Object> checkOrderStatus(@NotNull String orderId);

	/**
	 * 订单退费失败
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月8日上午10:11:33
	 * 修改日期:2015年10月8日上午10:11:33
	 */
	public void orderRefundFail(String orderNo);
	
	public void refund(String payWayCode,String orderNo);
	
}
