package com.unique.order.dao;

import java.util.List;

import com.unique.order.po.McOrder;
import com.unique.order.po.McOrderBack;
import com.unique.order.po.McOrderBackDet;
import com.unique.order.po.McOrderBill;
import com.unique.order.po.McOrderBillDet;
import com.unique.order.po.McOrderDet;
import com.unique.order.po.McOrderDivide;
import com.unique.order.po.McOrderPay;
import com.unique.order.po.McOrderType;
import com.unique.order.po.McProduct;
import com.unique.order.po.McProductChannel;
import com.unique.order.po.ProductType;
import com.unique.order.po.SourceType;
import com.unique.reg.po.PayWay;
import com.unique.reg.po.ProductPrice;

public interface OrderDao {
	public McOrderType getOrderTypeByCode(String orderTypeCode);
	
	public McProduct getProductById(String productId);
	
	public int addOrder(McOrder order );
	
	/**
	 * 根据产品获取产品价格列表
	 * @param pid
	 * @return
	 */
	public List<ProductPrice> getProductPricesByProductId(String pid,String staffTypeId);
	
	public ProductType getProductTypeByProduct(String pid);
	
	public int addOrderPay(McOrderPay orderPay);
	
	public McOrder getOrderById(String orderId);
	
	public PayWay getPayWayById(String id );
	
	public void updateOrderById(McOrder order);
	
	public int addOrderDet(McOrderDet orderDet );
	
	public int addOrderBill(McOrderBill bill);
	
	public int addOrderBillDet(McOrderBillDet det);
	
	public List<McOrderDet> getOrderDetByOrderId(String orderId);
	
	public int addOrderDivide(McOrderDivide orderDivide);
	
	public List<McProduct> getProductsByParentId(String pid);
	
	public List<McProductChannel> getChannelInfoByProduct(String pid);
	
	public List<McProduct> getProductByStaffTypeId(String staffTypeId);
	
	public PayWay getPayWayByCode(String code);
	
	public void updateOrderPayStatus(String payNo,String status,String orderPayId);
	
	public McOrderPay getOrderPayByProductCodeAndId(String typeCode,String toId);
	
	public McOrderType getOrderTypeById(String typeId);
	
	public ProductType getProductTypeById(String pid);
	
	public int addOrderBack(McOrderBack orderBack);
	
	public int addOrderBackDet(McOrderBackDet orderBackDet);
	
	public List<McOrderBackDet> getOrderBackDetByOrderBack(String orderbackId);
	
	public McOrder getOrderByPayNo(String payNo);
	
	public List<McOrder> getOrderByPage(String userId,String[] status,Long startRow,Long endRow);
	
	public long getOrderByPageCount(String userId,String[] status);
	
	public McProduct getGhProductByOrg(String orgId);
	
	public McOrderBack getOrderBackByOrderNo(String orderNo);
	/**
	 * 查询是否具有支付账单
	 * 作者:余宁
	 * 创建日期:2015年8月7日
	 * 修改日期:2015年8月7日
	 * @param payNo
	 * @return
	 */
	public McOrderBill findBillByPayNum(String payNo);
	
	/**
	 * 根据产品类型获取产品列表
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月10日下午2:46:31
	 * 修改日期:2015年9月10日下午2:46:31
	 * @param typeAttr
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public List<McProduct> getProductsByType(String typeAttr,Long startRow,Long endRow);
	
	
	/**
	 * 根据产品类型获取产品列表(总数)
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月10日下午2:47:58
	 * 修改日期:2015年9月10日下午2:47:58
	 * @param typeAttr
	 * @return
	 */
	public long getProductsByTypeCount(String typeAttr);
	
	/**
	 * 获取来源对象
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月22日下午2:12:24
	 * 修改日期:2015年9月22日下午2:12:24
	 * @param code
	 * @return
	 */
	public SourceType getSourceByCode(String code);
	
	public SourceType getSourceById(String sourceId);
	
	/**
	 * 更新订单明细根据ID
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月22日下午3:03:50
	 * 修改日期:2015年9月22日下午3:03:50
	 * @param id
	 */
	public void updateOrderDetById(McOrderDet det);
	
	/**
	 * 改变商品库存
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月22日下午3:25:47
	 * 修改日期:2015年9月22日下午3:25:47
	 * @param productId 商品ID
	 * @param num 数量
	 * @return
	 */
	public int changeProductNum(String productId,int num);
	/**
	 * 根据产品编码获取产品
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月24日上午10:42:37
	 * 修改日期:2015年9月24日上午10:42:37
	 * @param code
	 * @param orgId
	 * @return
	 */
	public McProduct getProductByCode(String code,String orgId);
	
	public McOrder getOrderByOrderNo(String orderNo);
	
	/**
	 * 根据订单号查询支付账单
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月8日上午9:43:26
	 * 修改日期:2015年10月8日上午9:43:26
	 * @param orderNo
	 * @return
	 */
	public McOrderBill findBillByOrderNo(String orderNo);
	
	/**
	 * 
	 * 获取某商品被购买的数量<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年9月29日 下午4:55:12<br/>
	 * @param productId
	 * @return
	 */
	public Long getPayProductCount(String productId);
	
	/**
	 * 更新退款订单根据订单号
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月8日上午11:02:23
	 * 修改日期:2015年10月8日上午11:02:23
	 * @param orderBack
	 * @return
	 */
	public int updateOrderBackByBackId(McOrderBack orderBack);
	
	public McOrderBack getOrderBackSuccessByOrderNo(String orderNo);
	
	public List<McOrderPay> getOrderPayByOrderId(String orderId);
}
