package com.unique.order.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

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

@Repository("orderDao")
public class OrderDaoImpl implements OrderDao {
	@Resource
	private SqlSession sqlSession;
	
	public McOrderType getOrderTypeByCode(String orderTypeCode){
		return sqlSession.selectOne("getOrderTypeByCode",orderTypeCode);
	}
	
	
	public McProduct getProductById(String productId){
		return sqlSession.selectOne("getProductById",productId);
	}
	
	public int addOrder(McOrder order ){
		return sqlSession.insert("addOrder",order);
	}
	
	/**
	 * 根据产品获取产品价格列表
	 * @param pid
	 * @return
	 */
	public List<ProductPrice> getProductPricesByProductId(String pid,String staffTypeId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staffTypeId", staffTypeId);
		map.put("pid", pid);
		return sqlSession.selectList("getProductPricesByProductId",map);
	}
	
	public ProductType getProductTypeByProduct(String pid){
		return sqlSession.selectOne("getProductTypeByProduct",pid);
	}
	
	public int addOrderPay(McOrderPay orderPay){
//		int hasPayNo = sqlSession.selectOne("checkOrderPay",orderPay.getPayNo());
//		if(hasPayNo>0){
//			return 0;
//		}else{
			return sqlSession.insert("addOrderPay",orderPay);
//		}
	}
	
	public McOrder getOrderById(String orderId){
		return sqlSession.selectOne("getOrderById",orderId);
	}
	
	public PayWay getPayWayById(String id ){
		return sqlSession.selectOne("getPayWayById",id);
	}
	
	public void updateOrderById(McOrder order){
		sqlSession.update("updateOrderById",order);
	}
	
	public int addOrderBill(McOrderBill bill){
		return sqlSession.insert("addOrderBill",bill);
	}
	
	public int addOrderDet(McOrderDet orderDet ){
		return sqlSession.insert("addOrderDet",orderDet);
	}
	
	public int addOrderBillDet(McOrderBillDet det){
		return sqlSession.insert("addOrderBillDet",det);
	}
	
	public List<McOrderDet> getOrderDetByOrderId(String orderId){
		return sqlSession.selectList("getOrderDetByOrderId",orderId);
	}
	
	public int addOrderDivide(McOrderDivide orderDivide){
		return sqlSession.insert("addOrderDivide",orderDivide);
	}
	
	public List<McProduct> getProductsByParentId(String pid){
		return sqlSession.selectList("getProductsByParentId",pid);
	}
	
	public List<McProductChannel> getChannelInfoByProduct(String pid){
		return sqlSession.selectList("getChannelInfoByProduct",pid);
	}
	
	public List<McProduct> getProductByStaffTypeId(String staffTypeId){
		return sqlSession.selectList("getProductByStaffTypeId",staffTypeId);
	}
	
	public PayWay getPayWayByCode(String code){
		return sqlSession.selectOne("getPayWayByCode",code);
	}
	
	public void updateOrderPayStatus(String payNo,String status,String orderPayId){
		if(payNo!=null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("payNo", payNo);
			map.put("status", status);
			map.put("orderPayId", orderPayId);
			sqlSession.update("updateOrderPayStatus",map);
		}
	}
	
	public McOrderPay getOrderPayByProductCodeAndId(String typeCode,String toId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeCode", typeCode);
		param.put("toId", toId);
		return sqlSession.selectOne("getOrderPayByProductCodeAndId",param);
	}
	
	public McOrderType getOrderTypeById(String typeId){
		return sqlSession.selectOne("getOrderTypeById",typeId);
	}
	
	public ProductType getProductTypeById(String pid){
		return sqlSession.selectOne("getProductTypeById",pid);
	}
	
	public int addOrderBack(McOrderBack orderBack){
		return sqlSession.insert("addOrderBack",orderBack);
	}
	
	public int addOrderBackDet(McOrderBackDet orderBackDet){
		return sqlSession.insert("addOrderBackDet",orderBackDet);
	}
	
	public List<McOrderBackDet> getOrderBackDetByOrderBack(String orderbackId){
		return sqlSession.selectList("getOrderBackDetByOrderBack",orderbackId);
	}
	
	public McOrderBack getOrderBackByOrderNo(String orderNo){
		return sqlSession.selectOne("getOrderBackByOrderNo",orderNo);
	}
	
	public McOrderBack getOrderBackSuccessByOrderNo(String orderNo){
		return sqlSession.selectOne("getOrderBackSuccessByOrderNo",orderNo);
	}
	
	public McOrder getOrderByPayNo(String payNo){
		return sqlSession.selectOne("getOrderByPayNo",payNo);
	}
	
	public McOrder getOrderByOrderNo(String orderNo){
		return sqlSession.selectOne("getOrderByOrderNo",orderNo);
	}
	
	public List<McOrder> getOrderByPage(String userId,String[] status,Long startRow,Long endRow){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startRow", startRow);
		param.put("endRow", endRow);
		param.put("status", status);
		param.put("userId", userId);
		return sqlSession.selectList("getOrderByPage",param);
	}
	
	public long getOrderByPageCount(String userId,String[] status){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", status);
		param.put("userId", userId);
		return sqlSession.selectOne("getOrderByPageCount",param);
	}
	
	
	public McProduct getGhProductByOrg(String orgId){
		return sqlSession.selectOne("getGhProductByOrg",orgId);
	}
	
	/**
	 * 查询是否具有支付账单
	 * 作者:余宁
	 * 创建日期:2015年8月7日
	 * 修改日期:2015年8月7日
	 * @param payNo
	 * @return
	 */
	public McOrderBill findBillByPayNum(String payNo){
		return sqlSession.selectOne("findBillByPayNum",payNo);
	}
	
	/**
	 * 根据订单号查询支付账单
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月8日上午9:43:26
	 * 修改日期:2015年10月8日上午9:43:26
	 * @param orderNo
	 * @return
	 */
	public McOrderBill findBillByOrderNo(String orderNo){
		return sqlSession.selectOne("findBillByOrderNo",orderNo);
	}
	
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
	public List<McProduct> getProductsByType(String typeAttr,Long startRow,Long endRow){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startRow", startRow);
		param.put("endRow", endRow);
		param.put("typeAttr", typeAttr);
		return sqlSession.selectList("getProductsByType",param);
	}
	
	/**
	 * 根据产品类型获取产品列表(总数)
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月10日下午2:47:58
	 * 修改日期:2015年9月10日下午2:47:58
	 * @param typeAttr
	 * @return
	 */
	public long getProductsByTypeCount(String typeAttr){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeAttr", typeAttr);
		return sqlSession.selectOne("getProductsByType_Count",param);
	}
	
	/**
	 * 获取来源对象
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月22日下午2:12:24
	 * 修改日期:2015年9月22日下午2:12:24
	 * @param code
	 * @return
	 */
	public SourceType getSourceByCode(String code){
		return sqlSession.selectOne("getSourceByCode",code);
	}
	
	public SourceType getSourceById(String sourceId){
		return sqlSession.selectOne("getSourceById",sourceId);
	}
	/**
	 * 更新订单明细根据ID
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月22日下午3:03:50
	 * 修改日期:2015年9月22日下午3:03:50
	 * @param id
	 */
	public void updateOrderDetById(McOrderDet det){
		sqlSession.update("updateOrderDetById",det);
	}
	
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
	public int changeProductNum(String productId,int num){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("productId", productId);
		param.put("num", num);
		return sqlSession.update("changeProductNum",param);
	}
	
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
	public McProduct getProductByCode(String code,String orgId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("code", code);
		param.put("orgId", orgId);
		return sqlSession.selectOne("getProductByCode",param);
	}
	
	public Long getPayProductCount(String productId){
		return sqlSession.selectOne("getPayProductCount",productId);
	}
	
	public int updateOrderBackByBackId(McOrderBack orderBack){
		return sqlSession.update("updateOrderBackByBackId",orderBack);
	}
	
	public List<McOrderPay> getOrderPayByOrderId(String orderId){
		return sqlSession.selectList("getOrderPayByOrderId",orderId);
	}
}
