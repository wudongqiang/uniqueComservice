package com.unique.order.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sun.istack.internal.NotNull;
import com.tencent.WXPayService;
import com.tencent.common.Configure;
import com.unique.alipay.util.AlipaySubmit;
import com.unique.alipay.util.AlipayUtil;
import com.unique.alipay.util.UtilDate;
import com.unique.core.annotation.CollectionClass;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.annotation.ParamNoNull;
import com.unique.core.util.FileUtil;
import com.unique.core.util.JsonUtil;
import com.unique.core.util.QueryUtils;
import com.unique.core.util.RandomNum;
import com.unique.core.util.RedisKeys;
import com.unique.core.util.StringUtil;
import com.unique.g2pay.core.PayCreator;
import com.unique.g2pay.core.util.JSONUtil;
import com.unique.g2pay.core.vo.BaseReturnObject;
import com.unique.g2pay.core.vo.ClientType;
import com.unique.g2pay.core.vo.PayType;
import com.unique.g2pay.core.vo.RefundParam;
import com.unique.g2pay.wx.vo.WxConfig;
import com.unique.g2pay.wx.vo.WxPrePayOrderParam;
import com.unique.his.service.CostService;
import com.unique.order.dao.OrderDao;
//import com.unique.order.pay.alipay.util.AlipaySubmit;
//import com.unique.order.pay.alipay.config.AlipayConfig;
//import com.unique.order.pay.alipay.config.AlipayServiceEnvConstants;
//import com.unique.order.pay.kingbom.KUtil;
//import com.unique.order.pay.tencent.common.Configure;
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
import com.unique.order.po.OrgPatient;
import com.unique.order.po.ProductPayInfo;
import com.unique.order.po.ProductRealPrice;
import com.unique.order.po.ProductType;
import com.unique.order.po.SourceType;
import com.unique.org.dao.OrgDao;
import com.unique.reg.dao.RegDao;
import com.unique.reg.po.McReg;
import com.unique.reg.po.Org;
import com.unique.reg.po.PayWay;
import com.unique.reg.po.ProductPrice;
import com.unique.reg.service.RegService;
import com.unique.system.dao.SystemDao;
import com.unique.system.service.SystemService;
import com.unique.user.dao.UserDao;
import com.unique.user.po.AmsUser;
import com.unique.user.po.UserFriend;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
	@Resource
	private OrderDao orderDao;
	@Resource
	private UserDao userDao;
	@Resource
	private RegDao regDao;
	@Resource
	private OrgDao orgDao;
	@Resource
	private RegService regService;
	@Resource
	private StringRedisTemplate redisTemplate;
	@Resource
	private CostService costService;
	@Resource(name = "wxPayUtilService")
	private WXPayService wxPayService;

	@Resource
	private SystemDao systemDao;
	@Resource
	private SystemService systemService;

	private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat orderTimeFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	// /挂号记录留存redis的天数
	private static int REG_LIVE_DAYS = Integer.parseInt(FileUtil
			.readProperties("comservice.properties", "regLiveDays"));
	// /图片服务器
	private final static String PIC_SERVER = FileUtil.readProperties(
			"comservice.properties", "picServerOut");

	public static Logger log = Logger.getLogger("pay");

	public static Logger orderLog = Logger.getLogger("order");
	// /微信推送地址
	private static String WX_PUSH = FileUtil.readProperties(
			"comservice.properties", "wxpush");
	private static String comservicePath = FileUtil.readProperties(
			"comservice.properties", "comservicePath");

	/**
	 * 宇佑订单号
	 * 
	 * @param orderTypeCode
	 * @return
	 */
	public static String getOrderNo(String orderTypeCode) {
		StringBuffer orderNo = new StringBuffer(orderTypeCode.toUpperCase())
				.append(orderTimeFormat.format(new Date()));
		Random r = new Random();
		for (int i = 0; i < 5; i++) {
			int ascii = r.nextInt(35);
			if (ascii >= 26) {
				// 数字
				orderNo.append((char) (22 + ascii));
			} else {
				// 字母
				orderNo.append((char) (65 + ascii));
			}
		}
		return orderNo.toString();
		// String uuid = UUID.randomUUID().toString().replaceAll("-",
		// "").toUpperCase();
		// StringBuffer orderNo = new
		// StringBuffer(orderTypeCode.toUpperCase()).append(orderTimeFormat.format(new
		// Date()));
		// return orderNo.toString();
	}

	/**
	 * 购买商品接口 创建人:余宁 修改人:余宁 创建日期:2015年9月22日下午2:45:50 修改日期:2015年9月22日下午2:45:50
	 * 
	 * @param orderTypeCode
	 *            订单类别编码:挂号：GH，VIP卡：VIP_CARD，诊间缴费：ZJ_PAY
	 * @param userId
	 *            下单用户ID
	 * @param productChooses
	 *            选择产品集合
	 * @param staffTypeId
	 *            挂号级别ID（挂号专用）
	 * @param sourceCode
	 *            来源编码
	 * @return 
	 *         {orderId:订单ID,d_price:在线付费费用,site_price:现场付费费用,price_ori:原始金额,orderNo
	 *         :订单编号,payWays:[支付方式对象]}
	 */
	@HttpMethod
	@Transactional(propagation=Propagation.REQUIRED)
	public HashMap<String, Object> buy(
			@NotNull String orderTypeCode,
			@NotNull String userId,
			@NotNull @CollectionClass(value = ProductPayInfo.class) List<ProductPayInfo> productChooses,
			String staffTypeId, String sourceCode, String friendId,
			OrgPatient orgPatient, String hisUserId) {
		HashMap<String, Object> result = placeOrder(orderTypeCode, userId,
				productChooses, staffTypeId, sourceCode, friendId, orgPatient,
				hisUserId);
		// 获得可用支付方式集合
		List<PayWay> payWays = regDao.getOnPayWay();
		result.put("payWays", payWays);
		return result;
	}

	/**
	 * 下单
	 * 
	 * @param orderTypeCode
	 *            订单类别编码:挂号：GH，VIP卡：VIP_CARD，诊间缴费：ZJ_PAY
	 * @param userId
	 *            下单用户ID
	 * @param productChooses
	 *            选择产品集合
	 * @param staffTypeId
	 *            挂号级别ID（挂号专用）
	 * @param sourceCode
	 *            来源编码
	 * @return
	 */
	public HashMap<String, Object> placeOrder(
			String orderTypeCode,
			String userId,
			@CollectionClass(value = ProductPayInfo.class) List<ProductPayInfo> productChooses,
			String staffTypeId, String sourceCode, String friendId,
			OrgPatient orgPatient, String hisUserId) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		AmsUser user = userDao.getUserById(userId);
		McOrderType orderType = orderDao.getOrderTypeByCode(orderTypeCode);
		if (orderType == null) {
			result.put("retCode", 1);
			result.put("retMsg", "订单类型不正确！");
			return result;
		}
		// 生成订单唯一编号
		String orderNo = getOrderNo(orderTypeCode);
		result.put("orderNo", orderNo);

		McOrder order = new McOrder();

		// 平台扣费
		double d_price = 0.0;
		// 现场扣费
		double site_price = 0.0;
		// 原始金额
		double price_ori = 0.0;
		if ("ZJ_PAY".equals(orderTypeCode)) {
			// 诊间缴费
			for (ProductPayInfo productChooseInfo : productChooses) {
				d_price += productChooseInfo.getPrice();
				price_ori += productChooseInfo.getPrice();
			}
		} else {
			// 遍历订购商品计算出总金额,并判断商品类型，以确定是否需要扣除库存
			for (ProductPayInfo productChooseInfo : productChooses) {

				// 商品ID
				String productId = productChooseInfo.getProductId();
				// 商品单价集合
				McProduct p = orderDao.getProductById(productId);
				Org org = regDao.getOrgById(p.getOrgId().toString());
				ProductRealPrice realPrice = null;
				if ("GH".equals(orderTypeCode)) {
					realPrice = getPriceByStaffType(staffTypeId, org);
				} else {
					realPrice = getPriceByProductId(productId);
				}
				d_price += realPrice.getDiscountPriceD();
				site_price += realPrice.getDiscountPriceSite();
				price_ori += realPrice.getMarkPrice();
			}

			// 扣库存
			try {
				subProductNum(productChooses);
			} catch (RuntimeException e) {
				e.printStackTrace();
				result.put("retCode", 2);
				result.put("retMsg", "库存不足或购买数量不正确，无法购买");
				return result;
			}
		}

		if (sourceCode != null) {
			SourceType sourceType = orderDao.getSourceByCode(sourceCode);
			if (sourceType != null) {
				order.setSourceId(sourceType.getSourceId());
			}
		}

		order.setCreateTime(new Date());
		order.setOrderMoney(new BigDecimal(d_price));
		order.setOrderName(orderType.getOrderName());
		order.setOrderNo(orderNo);
		order.setOrderStatus("1");
		order.setOrderTypeId(orderType.getOrderTypeId());
		order.setUserId(user.getUserId());
		order.setUserName(user.getUserName());
		order.setOriginalMoney(new BigDecimal(price_ori));
		orderDao.addOrder(order);

		if (!orderTypeCode.equalsIgnoreCase("GH")) {
			// 增加订单信息到redis
			updateOrderInRedis(order);
			redisTemplate.opsForHash().put(RedisKeys.ORDER_LIST_HISTORY_LIST,
					order.getOrderId().toString(),
					order.getOrderId().toString());
		}

		result.put("orderId", order.getOrderId().toString());
		result.put("d_price", d_price);
		result.put("site_price", site_price);
		result.put("price_ori", price_ori);

		// 遍历订购商品
		for (ProductPayInfo productChooseInfo : productChooses) {
			// 商品ID
			String productId = productChooseInfo.getProductId();
			// 商品数量
			long chooseCount = productChooseInfo.getCount();
			// 业务ID：挂号记录就是挂号ID
			String toId = productChooseInfo.getToId();
			McProduct product = orderDao.getProductById(productId);
			Org org = regDao.getOrgById(product.getOrgId().toString());

			String typeCode = product.getProductCode();
			if (typeCode != null && typeCode.toUpperCase().indexOf("TC") >= 0) {
				// 套餐
				List<McProduct> productChilds = orderDao
						.getProductsByParentId(productId);
				for (McProduct productChild : productChilds) {
					buildOrderDet(productChild, order, org.getOrgName(),
							chooseCount, toId, staffTypeId, orderTypeCode,
							productChooseInfo.getPrice(), friendId, orgPatient,
							hisUserId);
				}
			} else {
				// 单个产品
				buildOrderDet(product, order, org.getOrgName(), chooseCount,
						toId, staffTypeId, orderTypeCode,
						productChooseInfo.getPrice(), friendId, orgPatient,
						hisUserId);
			}
		}

		if (!orderTypeCode.equalsIgnoreCase("GH")) {
			if (d_price == 0) {
				// 不需要扣费
				paySuccess("FREE", null, null, orderNo, order.getOrderId()
						.toString());
				result.put("message", "下订单成功，免支付。");
			} else {
				result.put("message", "下订单成功，请支付。");
			}
		}
		return result;
	}

	/**
	 * 退单
	 * 
	 * @param orderTypeCode
	 * @param userId
	 * @param orderDets
	 * @return orderNo:订单编号
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public HashMap<String, Object> addUnOrderInfo(String orderTypeCode,
			String userId, String opUserId, List<McOrderBackDet> orderDets,
			String backReason, String orderId) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		List<McOrderBackDet> unorders = new ArrayList<McOrderBackDet>();
		BigDecimal allPrice = new BigDecimal(0);
		// 循环退货订单并累加金额
		for (McOrderBackDet orderDet : orderDets) {
			McOrderBackDet unOrderDet = new McOrderBackDet();
			try {
				BeanUtils.copyProperties(unOrderDet, orderDet);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			BigDecimal price = orderDet.getBackMoney();
			allPrice.add(price);
			unOrderDet.setOrderProductId(null);
			unorders.add(unOrderDet);
		}

		McOrder order = orderDao.getOrderById(orderId);
		AmsUser user = userDao.getUserById(userId);
		// 主退款单
		McOrderBack orderBack = new McOrderBack();
		orderBack.setBackReason(backReason);
		orderBack.setCreateTime(new Date());
		orderBack.setOpUserId(new BigDecimal(opUserId));
		orderBack.setOrderId(new BigDecimal(orderId));
		orderBack.setOrderMoney(order.getOrderMoney());
		orderBack.setOrderName(order.getOrderName());
		orderBack.setOrderNo(order.getOrderNo());
		orderBack.setOrderStatus("1");
		orderBack.setPayNo(order.getPayNo());
		orderBack.setPayTime(order.getPayTime());
		orderBack.setPoundage(orderBack.getPoundage());
		orderBack.setUserId(new BigDecimal(userId));
		orderBack.setUserName(user.getUserName());
		orderDao.addOrderBack(orderBack);

		// 添加退款单明细
		for (McOrderBackDet backDet : unorders) {
			backDet.setBackOrderId(orderBack.getBackOrderId());
			orderDao.addOrderBackDet(backDet);
		}
		result.put("orderNo", order.getOrderNo());
		return result;
	}

	private void buildOrderDet(McProduct product, McOrder order,
			String orgName, long chooseCount, String toId, String staffTypeId,
			String orderTypeCode, double price, String friendId,
			OrgPatient orgPatient, String hisUserId) {
		// 产品价格获取
		ProductType pType = orderDao.getProductTypeByProduct(product
				.getMcProductId().toString());
		Org org = regDao.getOrgById(product.getOrgId().toString());

		// 平台扣费
		double d_price = 0.0;
		// 现场扣费
		double site_price = 0.0;
		// 原始金额
		double ori_price = 0.0;
		if ("ZJ_PAY".equals(orderTypeCode)) {
			// 诊间缴费
			d_price = price;
			ori_price = price;
		} else {
			ProductRealPrice rPrice = null;
			if ("GH".equals(orderTypeCode)) {
				rPrice = getPriceByStaffType(staffTypeId, org);
			} else {
				rPrice = getPriceByProductId(product.getMcProductId()
						.toString());
			}
			d_price = rPrice.getDiscountPriceD();
			site_price = rPrice.getDiscountPriceSite();
			ori_price = rPrice.getMarkPrice();
		}

		// 创建订单明细
		McOrderDet orderDet = new McOrderDet();
		orderDet.setMcProductId(product.getMcProductId());
		orderDet.setOrderId(order.getOrderId());
		orderDet.setOrgId(product.getOrgId());
		orderDet.setOrgName(orgName);
		orderDet.setProductName(product.getProductName());
		orderDet.setProductNum(new BigDecimal(chooseCount));
		orderDet.setOriginalPrice(new BigDecimal(ori_price));
		// 商品单价
		orderDet.setProductPrice(new BigDecimal(d_price));
		// 商品总价
		orderDet.setProductSum(new BigDecimal(d_price * chooseCount));
		orderDet.setProductToId(toId);
		orderDet.setProductTypeId(pType.getProductTypeId());

		orderDet.setStatus(new BigDecimal("1"));
		orderDet.setOperator(order.getUserId());
		orderDet.setOperatorName(order.getUserName());
		orderDet.setOpTime(new Date());
		orderDet.setHisUserId(hisUserId);
		if (!StringUtil.isEmpty(friendId)) {
			orderDet.setFriendId(new BigDecimal(friendId));
		}

		if (orgPatient == null) {
			UserFriend friend = userDao.getFriendUserByFriendId(friendId);
			orderDet.setUserName(friend.getUserName());
			orderDet.setUserPhone(friend.getMobile());
			orderDet.setSex(friend.getUserSex());
			orderDet.setCardType("身份证");
			orderDet.setIdCard(friend.getUserIdcard());
		} else {
			try {
				BeanUtils.copyProperties(orderDet, orgPatient);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		orderDao.addOrderDet(orderDet);
	}

	/**
	 * 关闭订单
	 */
	@Transactional
	public void closeOrder(String orderId) {
		McOrder order = orderDao.getOrderById(orderId);
		order.setOrderStatus("8");
		updateOrder(order);
	}

	/**
	 * 支付日志记录
	 * 
	 * @param regWayId
	 *            支付方式ID
	 * @param orderId
	 *            订单ID
	 */
	public String addPayLog(String payWayId, String orderId, String payAcct,
			String payBank) {
		McOrder order = orderDao.getOrderById(orderId);
		PayWay payWay = orderDao.getPayWayById(payWayId);
		// 创建支付记录
		McOrderPay orderPay = new McOrderPay();
		orderPay.setOrderId(new BigDecimal(orderId));
		orderPay.setPayMoney(order.getOrderMoney());
		orderPay.setPayStatus("1");
		orderPay.setPayWayId(new BigDecimal(payWayId));
		orderPay.setPoundage(payWay.getPoundage());
		orderPay.setPayAcct(payAcct);
		orderPay.setPayBank(payBank);
		orderPay.setPayTime(new Date());

		// 插入支付记录
		orderDao.addOrderPay(orderPay);
		return orderPay.getOrderPayId().toString();
	}

	/**
	 * 添加退货单并创建分成比例
	 * 
	 * @param order
	 * @param payNo
	 * @param payWayId
	 * @param realMoney
	 */
	public void addBackBill(McOrderBack order, String payNo, String payWayId,
			BigDecimal realMoney) {
		log.info("开始添加退款账单 payNo：" + payNo);
		// 账单主表
		McOrderBill bill = new McOrderBill();
		bill.setBillTime(new Date());
		bill.setBillType("2");
		bill.setOrderMoney(order.getOrderMoney());
		bill.setOrderTypeId(order.getOrderId());
		bill.setPayNo(payNo);
		bill.setPayWayId(new BigDecimal(payWayId));
		bill.setRealMoney(realMoney);
		bill.setSrcOrderId(order.getOrderId());
		bill.setSrcOrderNo(order.getOrderNo());
		bill.setStatus("C");
		bill.setUserId(order.getUserId());
		bill.setUserName(order.getUserName());

		orderDao.addOrderBill(bill);

		List<McOrderBackDet> orderBackDets = orderDao
				.getOrderBackDetByOrderBack(order.getBackOrderId().toString());
		for (McOrderBackDet orderDet : orderBackDets) {
			McOrderBillDet det = new McOrderBillDet();
			// 应收
			det.setAllMoney(orderDet.getBackMoney());
			// 实收
			det.setAllRealMoney(orderDet.getBackMoney());
			det.setBillMoney(orderDet.getProductPrice());
			det.setBillNum(orderDet.getBackNum());
			det.setCreateTime(new Date());
			det.setMcBillId(bill.getBillId());
			det.setRealMoney(orderDet.getProductPrice());
			det.setSrcBillId(orderDet.getMcProductId());
			det.setStatus("C");

			orderDao.addOrderBillDet(det);

			// 创建收入分成明细
			List<McProductChannel> channels = orderDao
					.getChannelInfoByProduct(orderDet.getMcProductId()
							.toString());
			if (channels != null && channels.size() > 0) {
				for (McProductChannel channel : channels) {
					McOrderDivide divide = new McOrderDivide();
					divide.setChannelId(channel.getChannelId());
					divide.setChannelName(channel.getChannelName());
					divide.setCreateTime(new Date());
					if ("1".equals(channel.getDivideWay())) {
						// 百分比
						divide.setDivideMoney(new BigDecimal(0
								- channel.getDivideValue().doubleValue()
								* det.getAllRealMoney().doubleValue()));
					} else {
						divide.setDivideMoney(new BigDecimal(0 - channel
								.getDivideValue().doubleValue()));
					}
					divide.setDivideValue(channel.getDivideValue());
					divide.setDivideWay(channel.getDivideWay());
					divide.setMcProductId(orderDet.getMcProductId());
					divide.setOrderProductId(orderDet.getOrderProductId());
					divide.setProductName(orderDet.getProductName());

					orderDao.addOrderDivide(divide);
				}
			}

		}
	}

	/**
	 * 添加账单并创建分成比例
	 * 
	 * @param order
	 * @param payNo
	 * @param payWayId
	 * @param realMoney
	 */
	public void addBill(McOrder order, String payNo, String payWayId) {
		// 账单主表
		McOrderBill bill = new McOrderBill();
		bill.setBillTime(new Date());
		bill.setBillType("1");
		bill.setOrderMoney(order.getOriginalMoney());
		bill.setOrderTypeId(order.getOrderId());
		bill.setPayNo(payNo);
		bill.setPayWayId(new BigDecimal(payWayId));
		bill.setRealMoney(order.getOrderMoney());
		bill.setSrcOrderId(order.getOrderId());
		bill.setSrcOrderNo(order.getOrderNo());
		bill.setStatus("C");
		bill.setUserId(order.getUserId());
		bill.setUserName(order.getUserName());

		orderDao.addOrderBill(bill);

		List<McOrderDet> orderDets = orderDao.getOrderDetByOrderId(order
				.getOrderId().toString());
		for (McOrderDet orderDet : orderDets) {

			log.info("轮询退单明细,orderId:" + orderDet.getOrderId());
			McOrderBillDet det = new McOrderBillDet();
			// 应收
			det.setAllMoney(orderDet.getProductSum());
			det.setBillMoney(orderDet.getProductPrice());
			det.setBillNum(orderDet.getProductNum());
			det.setCreateTime(new Date());
			det.setMcBillId(bill.getBillId());
			// 实收
			det.setRealMoney(orderDet.getProductPrice());
			det.setAllRealMoney(orderDet.getProductSum());
			det.setSrcBillId(orderDet.getMcProductId());
			det.setStatus("C");

			orderDao.addOrderBillDet(det);

			// 创建收入分成明细
			// List<McProductChannel> channels =
			// orderDao.getChannelInfoByProduct(orderDet.getMcProductId().toString());
			// if(channels!=null && channels.size() >0){
			// for(McProductChannel channel : channels){
			// McOrderDivide divide = new McOrderDivide();
			// divide.setChannelId(channel.getChannelId());
			// divide.setChannelName(channel.getChannelName());
			// divide.setCreateTime(new Date());
			// if("1".equals(channel.getDivideWay())){
			// //百分比
			// divide.setDivideMoney(channel.getDivideValue().multiply(det.getAllRealMoney()));
			// }else{
			// divide.setDivideMoney(channel.getDivideValue());
			// }
			// divide.setDivideValue(channel.getDivideValue());
			// divide.setDivideWay(channel.getDivideWay());
			// divide.setMcProductId(orderDet.getMcProductId());
			// divide.setOrderProductId(orderDet.getOrderProductId());
			// divide.setProductName(orderDet.getProductName());
			//
			// orderDao.addOrderDivide(divide);
			// }
			// }

		}
	}

	/**
	 * 支付成功
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void paySuccess(String payWayCode, String payBank, String payNo,
			String orderNo, String orderId) {
		String orderPayId = orderNo.split("-")[1];
		orderNo = orderNo.split("-")[0];

		McOrder order = null;
		boolean free = false; // /免支付
		PayWay payWay = null;
		if (payNo == null && payWayCode.equalsIgnoreCase("FREE")) {
			// 免支付
			order = orderDao.getOrderById(orderId);
			free = true;
		} else {
			log.info("支付成功。" + orderNo);
			order = orderDao.getOrderByOrderNo(orderNo);
		}
//		if (!StringUtil.isEmpty(payWayCode)) {
//			payWay = orderDao.getPayWayByCode(payWayCode);
//		} else {
//			payWay = orderDao.getPayWayById(order.getPayWayId().toString());
//			payWayCode = payWay.getPayWayCode();
//		}
		
		payWay = orderDao.getPayWayById(order.getPayWayId().toString());
		payWayCode = payWay.getPayWayCode();
		
		// 修改支付订单状态
		log.info("OrderStatus:" + order.getOrderStatus());
		log.info("查询支付方式");
		// 1待支付，2待支付已过期，3支付成功交易中，4支付成功交易失败，5支付成功交易成功，6退款中，7已退款,8交易取消
		if (order.getOrderStatus().equals("3")
				|| order.getOrderStatus().equals("4")
				|| order.getOrderStatus().equals("5")) {
			// 该订单已经被置为成功
			McOrderBill bill = orderDao.findBillByOrderNo(orderNo);
			if (bill == null) {
				// 如果账单不存在则单独创建账单

				// 增加账单记录
				addBill(order, payNo, payWay.getPayWayId().toString());
				if (payWay.getRefund() != null
						&& payWay.getRefund().equalsIgnoreCase("Y")) {
					// 可退费则退费操作
				}
			}
			return;
		}
		// 更改订单状态
		log.info("更改订单状态");
		order.setOrderStatus("3");
		order.setPayNo(payNo);
		order.setPayTime(new Date());
		order.setPayWayId(payWay.getPayWayId());
		order.setPoundage(payWay.getPoundage());
		updateOrder(order);

		// 增加账单记录
		addBill(order, payNo, payWay.getPayWayId().toString());

		List<McOrderDet> orderDets = orderDao.getOrderDetByOrderId(order
				.getOrderId().toString());
		log.info("循环订单明细");
		for (McOrderDet orderdet : orderDets) {
			ProductType ptype = orderDao.getProductTypeById(orderdet
					.getProductTypeId().toString());

			/**
			 * 走具体的业务流程
			 */
			if (ptype.getCode().equalsIgnoreCase("GH")) {
				log.info("挂号成功处理");
				// 挂号成功
				orderDao.updateOrderPayStatus(payNo, "3", orderPayId);

				/***************** 2016-06-23 修改挂号方式 为先到医院挂号 *******************************/
				regService.regSuccess(orderdet.getProductToId(), payWayCode,
						payNo, order, orderPayId);
				/***************** 2016-06-23 修改挂号方式 为先到医院挂号 *******************************/

			} else if (ptype.getCode().equalsIgnoreCase("ZJJF")) {
				log.info("诊间缴费成功处理");
				// 缴费成功
				costService.paySuccess(orderdet, payWayCode, payNo, order,
						orderPayId);
			} else if (ptype.getTypeAtt().equals("1")) {
				log.info("销售商品处理");
				// 销售商品
				order.setOrderStatus("5");

				// 取货验证码
				String randomNums = RandomNum.getRandomNum(8);
				orderdet.setPickCode(randomNums);
				orderDao.updateOrderDetById(orderdet);
				log.info("设置取货验证码");

				orderDao.updateOrderPayStatus(payNo, "3", orderPayId);
				updateOrder(order);

				// 发送推送消息
				systemService.sendOrderMsg(order, orderdet, 1);
			}
		}

	}

	/**
	 * 查询订单记录分页
	 */
	@HttpMethod
	public List<McOrder> orderList(@NotNull String userId, Long startRow,
			Long rows, String[] status) {
		Long endRows = null;
		if (startRow != null && rows != null) {
			endRows = startRow + rows;
		}
		List<McOrder> result = orderDao.getOrderByPage(userId, status,
				startRow, endRows);
		return result;
	}

	/**
	 * 查询订单记录分页（总数）
	 */
	@HttpMethod
	public HashMap<String, Object> orderListCount(@NotNull String userId,
			String[] status) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("count", orderDao.getOrderByPageCount(userId, status));
		return result;
	}

	/**
	 * 查询订单包含详细记录分页
	 */
	@HttpMethod
	public Map<String, Object> orderHisList(@NotNull String userId,
			Long startRow, Long rows, String[] status) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (startRow == null)
			startRow = 0L;
		if (rows == null)
			rows = 10L;
		List<McOrder> mcOrders = orderDao.getOrderByPage(userId, status,
				startRow, startRow + rows);
		// 查询出订单详细
		List<McOrderDet> detais = null;
		List<Map<String, Object>> dataList = null;
		Map<String, Object> map = null;
		McReg mcReg = null;
		McProduct mcProduct = null;
		for (McOrder order : mcOrders) {
			// 订单详情McOrderDet
			detais = orderDao.getOrderDetByOrderId(order.getOrderId()
					.toString());
			dataList = new ArrayList<Map<String, Object>>();
			for (McOrderDet det : detais) {
				map = new HashMap<String, Object>();
				map.put("productTypeCode", det.getProductTypeCode());
				// 如果是挂号产品类型
				if (ORDER_TYPE_GH.equals(det.getProductTypeCode())) {
					// 得到挂号数据
					mcReg = regDao.getMcRegById(det.getProductToId(), null);
					if (mcReg == null)
						continue;
					map.put("objectName_key", "医院");
					map.put("objectName_vlaue", mcReg.getOrgName());

					map.put("objectItem_key", "科室");
					map.put("objectItem_value", mcReg.getDepName());

					order.setOrderGhIsUsed(mcReg.getIsUsed());

					String img = systemDao.getCmsImageByWhereId(
							QueryUtils.CmsImageLib.ORG_ID, mcReg.getOrgId()
									.toString(), "logotp");
					if (!StringUtil.isEmpty(img)) {
						img = PIC_SERVER + img;
					}
					map.put("objectImage", img == null ? "" : img);
				} else {
					// 其他的产品
					mcProduct = orderDao.getProductById(det.getMcProductId()
							.toString());
					if (mcProduct == null)
						continue;

					order.setOrderGhIsUsed("");
					if ("ZJJF".equals(det.getProductTypeCode())) {
						// 诊间缴费 中所需的图片是医院图片
						String img = systemDao.getCmsImageByWhereId(
								QueryUtils.CmsImageLib.ORG_ID, det.getOrgId()
										.toString(), "logotp");
						if (!StringUtil.isEmpty(img)) {
							img = PIC_SERVER + img;
						}
						map.put("objectImage", img == null ? "" : img);

						map.put("objectName_key", "医院");
						map.put("objectName_vlaue", mcProduct.getOrgName());

						map.put("objectItem_key", "项目");
						map.put("objectItem_value", mcProduct.getProductName());
					} else {
						// 产品图片
						map.put("objectImage", mcProduct.getProductImage());

						map.put("objectName_key", "名称");
						map.put("objectName_vlaue", mcProduct.getProductName());

						map.put("objectItem_key", "类型");
						map.put("objectItem_value",
								mcProduct.getProductTypeName());
					}
				}
				dataList.add(map);
			}
			order.setDataList(dataList);
		}
		result.put("mcOrders", mcOrders);
		result.put("count", orderDao.getOrderByPageCount(userId, status));
		return result;
	}
	
	
	
//	public Map<String, Object> orderHisList(@NotNull String userId,
//			Long startRow, Long rows, String[] status) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		if (startRow == null)
//			startRow = 0L;
//		if (rows == null)
//			rows = 10L;
//		List<McOrder> mcOrders = orderDao.getOrderByPage(userId, status,
//				startRow, startRow + rows);
//		// 查询出订单详细
//		List<McOrderDet> detais = null;
//		List<Map<String, Object>> dataList = null;
//		Map<String, Object> map = null;
//		McReg mcReg = null;
//		McProduct mcProduct = null;
//		if(mcOrders!=null){
//			for (McOrder order : mcOrders) {
//				map = new HashMap<String, Object>();
//				if (ORDER_TYPE_GH.equals(order.getOrderTypeCode())) {
//
//				}
//
//			}
//		
//		}
//		
//		for (McOrder order : mcOrders) {
//			// 订单详情McOrderDet
//			detais = orderDao.getOrderDetByOrderId(order.getOrderId()
//					.toString());
//			dataList = new ArrayList<Map<String, Object>>();
//			for (McOrderDet det : detais) {
//				map = new HashMap<String, Object>();
//				map.put("productTypeCode", det.getProductTypeCode());
//				// 如果是挂号产品类型
//				if (ORDER_TYPE_GH.equals(det.getProductTypeCode())) {
//					// 得到挂号数据
//					mcReg = regDao.getMcRegById(det.getProductToId(), null);
//					if (mcReg == null)
//						continue;
//					map.put("objectName_key", "医院");
//					map.put("objectName_vlaue", mcReg.getOrgName());
//
//					map.put("objectItem_key", "科室");
//					map.put("objectItem_value", mcReg.getDepName());
//
//					order.setOrderGhIsUsed(mcReg.getIsUsed());
//
//					String img = systemDao.getCmsImageByWhereId(
//							QueryUtils.CmsImageLib.ORG_ID, mcReg.getOrgId()
//									.toString(), "logotp");
//					if (!StringUtil.isEmpty(img)) {
//						img = PIC_SERVER + img;
//					}
//					map.put("objectImage", img == null ? "" : img);
//				} else {
//					// 其他的产品
//					mcProduct = orderDao.getProductById(det.getMcProductId()
//							.toString());
//					if (mcProduct == null)
//						continue;
//
//					order.setOrderGhIsUsed("");
//					if ("ZJJF".equals(det.getProductTypeCode())) {
//						// 诊间缴费 中所需的图片是医院图片
//						String img = systemDao.getCmsImageByWhereId(
//								QueryUtils.CmsImageLib.ORG_ID, det.getOrgId()
//										.toString(), "logotp");
//						if (!StringUtil.isEmpty(img)) {
//							img = PIC_SERVER + img;
//						}
//						map.put("objectImage", img == null ? "" : img);
//
//						map.put("objectName_key", "医院");
//						map.put("objectName_vlaue", mcProduct.getOrgName());
//
//						map.put("objectItem_key", "项目");
//						map.put("objectItem_value", mcProduct.getProductName());
//					} else {
//						// 产品图片
//						map.put("objectImage", mcProduct.getProductImage());
//
//						map.put("objectName_key", "名称");
//						map.put("objectName_vlaue", mcProduct.getProductName());
//
//						map.put("objectItem_key", "类型");
//						map.put("objectItem_value",
//								mcProduct.getProductTypeName());
//					}
//				}
//				dataList.add(map);
//			}
//			order.setDataList(dataList);
//		}
//		result.put("mcOrders", mcOrders);
//		result.put("count", orderDao.getOrderByPageCount(userId, status));
//		return result;
//	}

	

	/**
	 * 退订单
	 * 
	 * @param orderId
	 *            订单号
	 * @param opUserId
	 *            操作者ID
	 * @param backReason
	 *            退货原因
	 * @param userId
	 *            消费者ID
	 * @param 退费类型
	 *            site:现场，置空为平台
	 * @return 8)
	 *         ->{"calltype":"6","userId":null,"backReason":"啊节快乐","opUserId":
	 *         null,"orderId":"376"}
	 */
	@HttpMethod
	public HashMap<String, Object> unOrder(@ParamNoNull String orderId,
			@ParamNoNull String opUserId, @ParamNoNull String backReason,
			@ParamNoNull String userId, String refundType, Boolean doHospital) {
		if (doHospital == null)
			doHospital = true;

		// 审核订单是否可退
		HashMap<String, Object> result = checkUnOrder(orderId);
		if (!(Boolean) result.get("canUnOrder")) {
			// 不能退
			return result;
		} else {
			result.put("retCode", 0);
		}
		log.info("=========开始退订单============");

		// 查询出必要的对象

		McOrder order = orderDao.getOrderById(orderId);
		McOrderType orderType = orderDao.getOrderTypeById(order.getOrderTypeId().toString());
		McReg reg = null;
		List<McOrderDet> orderdets = orderDao.getOrderDetByOrderId(orderId);
		// 往REDIS里面插入一条数据以便以后查询
		if (!orderType.getOrderCode().equalsIgnoreCase("GH")) {
			// 增加订单信息到redis
			updateUNOrderInRedis(order);
			redisTemplate.opsForHash().put(RedisKeys.UNORDER_LIST_HISTORY_LIST,
					order.getOrderId().toString(),
					order.getOrderId().toString());
		} else {
			// 读入该挂号记录到缓存
			reg = regDao.getRegById(orderdets.get(0).getProductToId());
			regService.updateUNRegInRedis(reg.getRegId().toString(), reg);
			redisTemplate.opsForHash().put(RedisKeys.UNREG_LIST_HISTORY_LIST,
					reg.getRegId().toString(), reg.getRegId().toString());
		}
		// 应退金额
		double payablePrice = 0.0;

		log.info("退订单类型:orderNo:" + order.getOrderNo());
		if (order.getOrderNo().startsWith("GH")) {
			// 挂号订单
			Org org = regDao.getOrgById(reg.getOrgId().toString());
			
			/******修改退号失败，staffTypeId不存在的问题 2016-09-11*******/
//			List<McProduct> regProducts = orderDao.getProductByStaffTypeId(reg.getStaffTypeId().toString());
//			ProductType ptype = orderDao.getProductTypeByProduct(regProducts.get(0).getMcProductId().toString());
			
			//这里貌似用不上（这个价格只是用来记录，不用到退费中）
//			ProductRealPrice realPrice = getPriceByStaffType(reg.getStaffTypeId().toString(), org);
//			payablePrice = realPrice.getDiscountPriceD();
			
			//add
			payablePrice = orderdets.get(0).getProductPrice()!=null?orderdets.get(0).getProductPrice().doubleValue():0.0f;
			ProductType ptype = orderDao.getProductTypeByProduct(orderdets.get(0).getMcProductId().toString());
			
			/******修改退号失败，staffTypeId不存在的问题 2016-09-11*******/

			McOrderBackDet odet = new McOrderBackDet();
			odet.setBackMoney(new BigDecimal(payablePrice));
			odet.setBackNum(new BigDecimal(1));
			//odet.setMcProductId(regProducts.get(0).getMcProductId());
			odet.setMcProductId(orderdets.get(0).getMcProductId());
			odet.setOrderId(order.getOrderId());
			odet.setOrderProductId(orderdets.get(0).getOrderProductId());
			//odet.setProductName(regProducts.get(0).getProductName());
			odet.setProductName(orderdets.get(0).getProductName());
			odet.setProductPrice(new BigDecimal(reg.getAmount()));
			odet.setProductTypeId(ptype.getProProductTypeId());

			List<McOrderBackDet> odets = new ArrayList<McOrderBackDet>();
			odets.add(odet);

			if (payablePrice == 0.0) {
				// 免费或者现场退号
				reg.setStatus("7");
				reg.setIsUsed("2");
			} else if (refundType != null
					&& "site".equalsIgnoreCase(refundType)) {
				// 现场退号
				reg.setStatus("4");
				reg.setIsUsed("2");
			} else {
				// 退费中
				reg.setStatus("5");
				reg.setIsUsed("R");
			}
			AmsUser opUser = userDao.getUserById(opUserId);
			reg.setBackOperator(opUser.getUserId());
			reg.setBackOperatorName(opUser.getUserName());
			reg.setBackOperatorType(new BigDecimal(4));
			reg.setBackReason(backReason);

			log.info("1判断退费是否走医院接口 orderId:" + order.getOrderId());
			if (org.getHasRemoteReg() == null
					|| "1".equals(org.getHasRemoteReg())
					|| (refundType != null && "site"
							.equalsIgnoreCase(refundType)) || !doHospital) {
				// 不走接口
				log.info("2没有走接口(退费退号)");
			} else if (org.getHasRemoteReg() != null
					&& "2".equals(org.getHasRemoteReg())) {
				// 及时接口
				HashMap<String, Object> orgResult = regService
						.synchronizedToOrg_UnReg(org.getHospitalUrl(), opUser,
								new Date(), reg);
				log.info("2退费退号医院及时接口返回数据:" + orgResult);
				if (orgResult.get("retCode").toString().equals("0")) {
					// 接口调用成功
					reg.setStatus("5");
				} else {
					reg.setIsUsed("R");
					regService.updateReg(reg);

					result.put("retCode", 6);
					result.put("err_message", "医院端退号失败");
					result.put("orgResult", orgResult);

					return result;
				}
			} else if (org.getHasRemoteReg() != null
					&& "3".equals(org.getHasRemoteReg())) {
				// 异步接口
				return result;
			}

			// 放号
			regService.releaseReg(reg.getDutyId().toString(), reg
					.getDutyPeriodId() != null ? reg.getDutyPeriodId()
					.toString() : null);
			// 更新挂号记录为失败状态
			regService.updateUNReg(reg);
			log.info("取消订单 orderId:" + order.getOrderId());
			// 取消订单
			addUnOrderInfo("GH", userId, opUserId, odets, backReason, order
					.getOrderId().toString());
		} else {

			List<McOrderBackDet> odets = new ArrayList<McOrderBackDet>();

			// 非挂号订单
			for (McOrderDet orderDet : orderdets) {
				ProductRealPrice realPrice = getPriceByProductId(orderDet
						.getMcProductId().toString());
				payablePrice += realPrice.getDiscountPriceD();

				// 更新为已退货
				orderDet.setStatus(new BigDecimal("3"));
				updateOrderDet(orderDet);

				McOrderBackDet odet = new McOrderBackDet();
				odet.setBackMoney(orderDet.getProductPrice());
				odet.setBackNum(orderDet.getProductNum());
				odet.setMcProductId(orderDet.getMcProductId());
				odet.setOrderId(order.getOrderId());
				odet.setOrderProductId(orderDet.getOrderProductId());
				odet.setProductName(orderDet.getProductName());
				odet.setProductPrice(orderDet.getOriginalPrice());
				odet.setProductTypeId(orderDet.getProductTypeId());

				odets.add(odet);
			}

			// 取消订单
			addUnOrderInfo("GH", userId, opUserId, odets, backReason, order
					.getOrderId().toString());
		}

		if (payablePrice == 0.0) {
			order.setOrderStatus("7");
		} else if (refundType != null && "site".equalsIgnoreCase(refundType)) {
			// order.setOrderStatus("7");
		} else {
			order.setOrderStatus("6");
		}
		updateUNOrder(order);

		log.info("开始退款，金额：" + payablePrice);
		/*************** 退款处理 *****************/
		// if(refundType!=null && "site".equalsIgnoreCase(refundType)){
		// log.info("只退单");
		// //退订单写账单
		// orderRefundSuccess(order.getOrderNo());
		// }else
		if (payablePrice == 0.0) {
			// 金额为0不需要退
			log.info("无需退款，也无需变更状态");
			orderRefundSuccess(order.getOrderNo());
		} else {
			log.info("需要退费");
			PayWay payWay = orderDao.getPayWayById(order.getPayWayId()
					.toString());
			if (payWay.getRefund() != null
					&& payWay.getRefund().equalsIgnoreCase("N")) {
				// 不支持退费，如支付宝
				orderRefundFail(order.getOrderNo());
			} else {
				refund(payWay.getPayWayCode(), order.getOrderNo());
			}
		}
		return result;
	}

	/**
	 * 退费方法 创建人:余宁 修改人:余宁 创建日期:2015年10月9日上午9:23:44 修改日期:2015年10月9日上午9:23:44
	 * 
	 * @param payWayCode
	 * @param orderNo
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void refund(String payWayCode, String orderNo) {
		McOrder order = orderDao.getOrderByOrderNo(orderNo);
		// 执行退费操作
		if (payWayCode.contains("WXZF")) {
			// 执行退费
			changeOrderPayStatus(order.getOrderId().toString(), 1);
			List<McOrderPay> orderPays = orderDao.getOrderPayByOrderId(order
					.getOrderId().toString());
			for (McOrderPay orderPay : orderPays) {
				// wxPayService.refund(orderNo + "-" +
				// orderPay.getOrderPayId().toString());
				this.wxRefund(orderNo + "-"
						+ orderPay.getOrderPayId().toString(), payWayCode);
			}
		} else {
			// 不可退费
			changeOrderPayStatus(order.getOrderId().toString(), 2);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	private void wxRefund(String orderNo, String payWayCode) {

		try {
			log.info("开始退费");
			log.info("orderNo:" + orderNo);
			String trueOrderNo = orderNo;
			if (!StringUtil.isEmpty(trueOrderNo)
					&& trueOrderNo.indexOf("-") > 0) {
				trueOrderNo = orderNo.split("-")[0];
			}
			McOrder order = orderDao.getOrderByOrderNo(trueOrderNo);

			PayWay payWay = orderDao.getPayWayByCode(payWayCode);

			// 微信退费 退回到个人微信账户
			Configure config = Configure.getInstance(payWay.getConfig(),
					payWayCode);
			WxConfig wxCfg = new WxConfig();
			wxCfg.setAppID(config.getAppid());
			wxCfg.setCertbytes(config.getCertbytes());
			wxCfg.setKey(config.getKey());
			wxCfg.setMchID(config.getMchid());
			wxCfg.setSubMchID(config.getSubMchid());
			// 退款参数
			RefundParam param = new RefundParam() {
			};
			param.setOut_refund_no("BK_" + orderNo);
			param.setOut_trade_no(orderNo);

			param.setRefund_fee((int) (order.getOrderMoney().doubleValue() * 100)
					- (int) (order.getPoundage().doubleValue() * 100));
			param.setTotal_fee((int) (order.getOrderMoney().doubleValue() * 100));

			BaseReturnObject refund = PayCreator.initPay(wxCfg, PayType.WX,
					null).refund(param, ClientType.WEB);

			log.info("退款结果：" + JSONObject.fromObject(refund).toString());
			System.out.println("退款结果："
					+ JSONObject.fromObject(refund).toString());

			// 签名失败，其实也是成功了的
			if ("SUCCESS".equals(refund.getReturn_code())
					|| "REFUND_SIGN_FAILD".equals(refund.getReturn_code())) {
				log.info("退款成功:" + orderNo);
				// 订单退款成功
				orderRefundSuccess(trueOrderNo);
			} else {
				log.info("退款失败");
				orderRefundFail(trueOrderNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新订单状态并更新redis
	 */
	public void updateOrder(McOrder order) {
		McOrderType typeCode = orderDao.getOrderTypeById(order.getOrderTypeId()
				.toString());
		if (!typeCode.getOrderCode().equalsIgnoreCase("GH")) {
			// 增加订单信息到redis
			updateOrderInRedis(order);
		}
		orderDao.updateOrderById(order);
	}

	public void updateOrderDet(McOrderDet orderDet) {
		orderDao.updateOrderDetById(orderDet);
	}

	/**
	 * 更新退单状态并更新redis
	 */
	public void updateUNOrder(McOrder order) {
		McOrderType typeCode = orderDao.getOrderTypeById(order.getOrderTypeId()
				.toString());
		if (!typeCode.getOrderCode().equalsIgnoreCase("GH")) {
			// 增加订单信息到redis
			updateUNOrderInRedis(order);
		}

		orderDao.updateOrderById(order);
		log.info("退款，更新订单状态:" + order.getOrderStatus());
	}

	/**
	 * 检查订单是否可退
	 * 
	 * @param orderId
	 *            订单ID
	 * @return
	 */
	public HashMap<String, Object> checkUnOrder(String orderId) {
		McOrder order = orderDao.getOrderById(orderId);
		McOrderType orderType = orderDao.getOrderTypeById(order
				.getOrderTypeId().toString());
		List<McOrderDet> orderdets = orderDao.getOrderDetByOrderId(orderId);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean result = false;
		if (order.getOrderStatus().equals("0")) {
			resultMap.put("retCode", 5);
			resultMap.put("retMessage", "已取货不能退");
			result = false;
			resultMap.put("canUnOrder", result);
			return resultMap;
		} else if (order.getOrderStatus().equals("5")
				|| order.getOrderStatus().equals("6")) {
			result = true;
		} else {
			resultMap.put("retCode", 6);
			resultMap.put("retMessage", "订单未成功不能退");
			result = false;
			resultMap.put("canUnOrder", result);
			return resultMap;
		}
		if (orderType.getOrderCode().equals("GH")) {
			// 挂号订单
			String regId = orderdets.get(0).getProductToId();
			McReg reg = regDao.getRegById(regId);
			if (reg.getIsUsed().equals("1")) {
				resultMap.put("retCode", 3);
				resultMap.put("retMessage", "已取号不能退");
				result = false;
			}

			if (!ymd.format(reg.getOrderTime()).equals(
					ymd.format(new Date().getTime()))
					&& (reg.getOrderTime().getTime() - new Date().getTime() > 0)) {
				// 非就诊当天
				result = true;
			} else {
				resultMap.put("retCode", 4);
				resultMap.put("retMessage", "就诊当天或以前的号不能退");
				result = false;
			}
		} else if (orderType.getOrderCode().equals("ZJ_PAY")) {
			resultMap.put("retCode", 7);
			resultMap.put("retMessage", "诊间缴费不能退");
			result = false;
		}
		resultMap.put("canUnOrder", result);
		return resultMap;
	}

	/**
	 * 根据类型获取产品列表 创建人:余宁 修改人:余宁 创建日期:2015年9月10日下午2:53:22
	 * 修改日期:2015年9月10日下午2:53:22
	 * 
	 * @param typeAtt
	 *            0 业务商品 1 销售商品
	 * @param startRow
	 * @param rows
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getProductsList(String typeAtt,
			Long startRow, Long rows) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Long endRows = null;
		if (startRow != null && rows != null) {
			endRows = startRow + rows;
		}

		List<McProduct> products = orderDao.getProductsByType(typeAtt,
				startRow, endRows);
		long count = orderDao.getProductsByTypeCount(typeAtt);
		// 设置产品价格
		for (McProduct p : products) {
			ProductRealPrice productRealPrice = getPriceByProductId(p
					.getMcProductId().toString());
			p.setProductRealPrice(productRealPrice);
		}
		result.put("list", products);
		result.put("count", count);
		return result;
	}

	@HttpMethod
	@Override
	public HashMap<String, Object> getMcProduct(String productId) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		McProduct product = orderDao.getProductById(productId);
		// 设置产品价格
		if (product != null) {
			ProductRealPrice productRealPrice = getPriceByProductId(product
					.getMcProductId().toString());
			product.setProductRealPrice(productRealPrice);
		}
		result.put("product", product);
		result.put("payProductCount", orderDao.getPayProductCount(productId));
		return result;
	}

	/**
	 * 根据挂号级别获取价格 创建人:余宁 修改人:余宁 创建日期:2015年9月10日下午4:18:46
	 * 修改日期:2015年9月10日下午4:18:46
	 * 
	 * @param staffTypeId
	 * @param org
	 * @return
	 */
	public ProductRealPrice getPriceByStaffType(String staffTypeId, Org org) {
		ProductRealPrice rp = new ProductRealPrice();

		List<McProduct> products = orderDao.getProductByStaffTypeId(staffTypeId);
		double d_price = 0;// 代扣费用
		double site_price = 0;// 不代扣费用，即现场支付
		double mark_price = 0; // /标价
		for (McProduct p : products) {
			List<ProductPrice> psList = orderDao.getProductPricesByProductId(p
					.getMcProductId().toString(), staffTypeId);
			for (ProductPrice price : psList) {
				if (price.getPriceAmount() == null)
					continue;
				double percent = price.getPercent().doubleValue();
				if (percent == 0)
					percent = 1;
				mark_price += price.getPriceAmount().doubleValue();

				if (price.getRebateType() != null
						&& price.getRebateType().equals("1")) {
					// 固定值
					if ("Y".equals(p.getRecoupWay())) {
						// 代扣费
						d_price += price.getRebateAmout().doubleValue();
					} else {
						// 不代扣费
						site_price += price.getRebateAmout().doubleValue();
					}
				} else if (price.getRebateType() != null
						&& price.getRebateType().equals("2")) {
					// 百分比
					if ("Y".equals(p.getRecoupWay())) {
						// 代扣费
						d_price += price.getPriceAmount().doubleValue()
								* percent;
					} else {
						// 不代扣费
						site_price += price.getPriceAmount().doubleValue()
								* percent;
					}
				} else {
					// 无折扣
					if ("Y".equals(p.getRecoupWay())) {
						// 代扣费
						d_price += price.getPriceAmount().doubleValue();
					} else {
						// 不代扣费
						site_price += price.getPriceAmount().doubleValue();
					}
				}

			}
		}

		rp.setDiscountPriceD(d_price);
		rp.setDiscountPriceSite(site_price);
		rp.setMarkPrice(mark_price);
		return rp;
	}

	/**
	 * 根据产品ID获取各种价格 创建人:余宁 修改人:余宁 创建日期:2015年9月10日下午4:41:41
	 * 修改日期:2015年9月10日下午4:41:41
	 * 
	 * @param productId
	 * @return
	 */
	public ProductRealPrice getPriceByProductId(String productId) {
		ProductRealPrice rp = new ProductRealPrice();
		McProduct product = orderDao.getProductById(productId);
		List<ProductPrice> psList = orderDao.getProductPricesByProductId(
				product.getMcProductId().toString(), null);
		double d_price = 0;// 代扣费用
		double site_price = 0;// 不代扣费用，即现场支付
		double mark_price = 0; // /标价
		for (ProductPrice price : psList) {
			if (price.getPriceAmount() == null)
				continue;
			double percent = price.getPercent() == null ? 0 : price
					.getPercent().doubleValue();
			if (percent == 0)
				percent = 1;
			mark_price += price.getPriceAmount().doubleValue();

			if (price.getRebateType() != null
					&& price.getRebateType().equals("1")) {
				// 固定值
				if ("Y".equals(product.getRecoupWay())) {
					// 代扣费
					d_price += price.getRebateAmout().doubleValue();
				} else {
					// 不代扣费
					site_price += price.getRebateAmout().doubleValue();
				}
			} else if (price.getRebateType() != null
					&& price.getRebateType().equals("2")) {
				// 百分比
				if ("Y".equals(product.getRecoupWay())) {
					// 代扣费
					d_price += price.getPriceAmount().doubleValue() * percent;
				} else {
					// 不代扣费
					site_price += price.getPriceAmount().doubleValue()
							* percent;
				}
			} else {
				// 无折扣
				if ("Y".equals(product.getRecoupWay())) {
					// 代扣费
					d_price += price.getPriceAmount().doubleValue();
				} else {
					// 不代扣费
					site_price += price.getPriceAmount().doubleValue();
				}
			}
		}
		rp.setDiscountPriceD(d_price);
		rp.setDiscountPriceSite(site_price);
		rp.setMarkPrice(mark_price);
		return rp;
	}

	/**
	 * 减商品库存 创建人:余宁 修改人:余宁 创建日期:2015年9月22日下午3:31:34 修改日期:2015年9月22日下午3:31:34
	 * 
	 * @param productIds
	 * @param num
	 * @return
	 */
	private void subProductNum(List<ProductPayInfo> productIds) {
		for (ProductPayInfo payInfo : productIds) {
			ProductType pType = orderDao.getProductTypeByProduct(payInfo
					.getProductId().toString());
			System.out.println(pType + " " + pType.getTypeAtt());
			if (pType.getTypeAtt() == null || !pType.getTypeAtt().equals("1")) {
				continue;
			}
			// 改变商品库存
			int result = orderDao.changeProductNum(payInfo.getProductId()
					.toString(), 0 - (int) payInfo.getCount());
			if (result != 1) {
				throw new RuntimeException("库存不足！");
			}
		}
	}

	/**
	 * 退商品库存 创建人:余宁 修改人:余宁 创建日期:2015年9月22日下午3:38:33 修改日期:2015年9月22日下午3:38:33
	 * 
	 * @param productIds
	 * @param num
	 */
	private void addProductNum(List<ProductPayInfo> productIds) {
		for (ProductPayInfo payInfo : productIds) {
			ProductType pType = orderDao.getProductTypeByProduct(payInfo
					.getProductId().toString());
			if (!pType.getTypeAtt().equals("1")) {
				continue;
			}
			// 改变商品库存
			int result = orderDao.changeProductNum(payInfo.getProductId()
					.toString(), (int) payInfo.getCount());
			if (result != 1) {
				throw new RuntimeException("库存不足！");
			}
		}
	}

	/**
	 * 更新订单信息到REDIS 创建人:余宁 修改人:余宁 创建日期:2015年9月22日下午4:04:36
	 * 修改日期:2015年9月22日下午4:04:36
	 * 
	 * @param orderId
	 * @param order
	 */
	private void updateOrderInRedis(McOrder order) {
		// 更新REDIS
		redisTemplate.delete(RedisKeys.ORDER_LIST_HISTORY
				+ order.getOrderId().toString());
		redisTemplate.opsForValue().set(
				RedisKeys.ORDER_LIST_HISTORY + order.getOrderId().toString(),
				JSONObject.fromObject(order, JsonUtil.getConfig()).toString());
		redisTemplate.expire(RedisKeys.ORDER_LIST_HISTORY
				+ order.getOrderId().toString(), REG_LIVE_DAYS, TimeUnit.DAYS);
	}

	private void updateUNOrderInRedis(McOrder order) {
		// 更新REDIS
		redisTemplate.delete(RedisKeys.UNORDER_LIST_HISTORY
				+ order.getOrderId().toString());
		redisTemplate.opsForValue().set(
				RedisKeys.UNORDER_LIST_HISTORY + order.getOrderId().toString(),
				JSONObject.fromObject(order, JsonUtil.getConfig()).toString());
		redisTemplate.expire(RedisKeys.UNORDER_LIST_HISTORY
				+ order.getOrderId().toString(), REG_LIVE_DAYS, TimeUnit.DAYS);
	}

	/**
	 * 选择支付方式预先生成订单信息 创建人:余宁 修改人:余宁 创建日期:2015年9月29日下午2:08:16
	 * 修改日期:2015年9月29日下午2:08:16
	 * 
	 * @param orderId
	 *            订单ID
	 * @param payWayCode
	 *            支付方式编码 WXZF、 WXZF_APP
	 * @param bankNo
	 *            银行卡号
	 * @param payType
	 *            支付类型 默认传空，W 表示微信调用支付宝支付（微信）
	 * @param returnUrl
	 *            支付接口回调地址（微信）
	 * @param showUrl
	 *            订单展示地址（微信）
	 * @param tradeType
	 *            微信支付用：JSAPI、NATIVE、APP(微信支付)
	 * @param openId
	 *            公众号ID(微信支付)
	 * @param wxProductId
	 *            微信商品ID(微信支付)
	 * @return
	 */
	@HttpMethod
	@Transactional(propagation=Propagation.REQUIRED)
	public HashMap<String, Object> usePayWay(@NotNull String orderId,
			@ParamNoNull String payWayCode, String bankNo, String payType,
			String returnUrl, String showUrl, String tradeType, // /微信支付用：JSAPI、NATIVE、APP
			String openId, // /公众号ID
			String wxProductId, // /微信商品ID
			String ip // /支付终端IP
	) {

		HashMap<String, Object> result = new HashMap<String, Object>();
		// 创建支付日志
		PayWay payWay = orderDao.getPayWayByCode(payWayCode);
		String orderPayId = addPayLog(payWay.getPayWayId().toString(), orderId,
				null, bankNo);
		McOrder order = orderDao.getOrderById(orderId);
		order.setPayWayId(payWay.getPayWayId());
		updateOrder(order);

		if (order.getOrderStatus().equals("5")) {
			// 支付交易成功
			result.put("retCode", 4);
			result.put("retMessage", "请不要重复支付");
			return result;
		}

		String sendOrderNo = order.getOrderNo() + "-" + orderPayId;
		log.info("-------usePayWay---------:" + sendOrderNo);
		/************************************ 支付宝 *****************************************/
		if (payWayCode.equals("ZFB")) {
			log.info("-------usePayWay---------:支付宝支付");
			if (payType == null || !payType.equals("W")) {
				String alipayInfo = AlipayUtil.getUtil().buildPayInfo("预约挂号",
						"宇佑预约挂号费用", order.getOrderMoney().toString(),
						sendOrderNo);
				result.put("alipayInfo", alipayInfo);
				log.info("=========调用支付宝jsonParam=========" + alipayInfo);
			} else {
				if (StringUtil.isEmpty(returnUrl)
						|| StringUtil.isEmpty(showUrl)) {
					result.put("retCode", 3);
					result.put("retMessage", "returnUrl或showUrl为空");
				} else {
					String paramMap = AlipaySubmit.buildRequest("预约挂号",
							"宇佑预约挂号费用", order.getOrderMoney().toString(),
							sendOrderNo, returnUrl, showUrl);
					result.put("alipayInfo", paramMap);
					log.info("=========调用支付宝jsonParam=========" + paramMap);
				}
			}
			/*
			 * ZfbConfig zfbCfg = new ZfbConfig();
			 * zfbCfg.setAli_public_key(AlipayConfig.ali_public_key);
			 * zfbCfg.setAli_public_key_wap(AlipayConfig.ali_public_key_wap); //
			 * zfbCfg.setAppId(alipayServiceEnvConstants.APP_ID);
			 * zfbCfg.setPartner(AlipayConfig.partner);
			 * zfbCfg.setPrivate_key(AlipayConfig.private_key);
			 * zfbCfg.setSELLER(AlipayConfig.SELLER);
			 * zfbCfg.setInput_charset(AlipayConfig.input_charset);
			 * zfbCfg.setSign_type(AlipayConfig.sign_type);
			 * 
			 * ZfbPrePayOrderParam param = new ZfbPrePayOrderParam();
			 * param.setBody(order.getOrderName());
			 * param.setName(order.getOrderName());
			 * param.setNotify_url(comservicePath + "notify.do?method=alipay");
			 * param.setOut_trade_no(sendOrderNo); param.setShowUrl(showUrl);
			 * param.setReturnUrl(returnUrl); param.setTotal_fee((int)
			 * (order.getOrderMoney().doubleValue() * 100));
			 * 
			 * if (payType == null || (!payType.equals("wap") &&
			 * !payType.equals("web"))) { // APP端 BaseReturnObject returnObject
			 * = null; try { returnObject = PayCreator .initPay(zfbCfg,
			 * PayType.ZFB, null).buildPayOrder( param, ClientType.APP); } catch
			 * (Exception e) { e.printStackTrace(); }
			 * 
			 * String alipayInfo = AlipayUtil.buildPayInfo(
			 * order.getOrderName(), order.getOrderName() + "费用",
			 * order.getOrderMoney().toString(), sendOrderNo);
			 * log.info("=========zfb========="); log.info(alipayInfo);
			 * log.info("=========zfb=========");
			 * log.info(returnObject.getReturn_object().toString());
			 * result.put("payUrl", returnObject.getReturn_object().toString());
			 * } else { // wap if (StringUtils.isEmpty(returnUrl) ||
			 * StringUtils.isEmpty(showUrl)) { result.put("retCode", 3);
			 * result.put("retMessage", "returnUrl或showUrl为空"); return result; }
			 * else {
			 * 
			 * BaseReturnObject returnObject = null; try { returnObject =
			 * PayCreator.initPay(zfbCfg, PayType.ZFB,
			 * null).buildPayOrder(param, ClientType.WAP); } catch (Exception e)
			 * { e.printStackTrace(); }
			 * 
			 * String paramMap = AlipaySubmit.buildRequest(
			 * order.getOrderName(), order.getOrderName() + "费用",
			 * order.getOrderMoney().toString(), sendOrderNo, returnUrl,
			 * showUrl); log.info("=========wx调用支付宝=========");
			 * log.info(JSONUtil.toJSONString(returnObject
			 * .getReturn_object())); //
			 * result.setJsonParam(JSONUtil.toJSONString
			 * (returnObject.getReturn_object()));
			 * log.info("=========wx调用支付宝jsonParam=========");
			 * log.info(paramMap); result.put("jsonParam", paramMap); } }
			 */
		} else if (payWayCode.equals("WXZF")) {
			log.info("-------usePayWay---------:微信支付");
			/************************************ 微信支付 *****************************************/
			Configure config = Configure.getInstance(payWay.getConfig(),
					payWayCode);
			WxConfig wxCfg = new WxConfig();
			wxCfg.setAppID(config.getAppid());
			wxCfg.setCertbytes(config.getCertbytes());
			wxCfg.setKey(config.getKey());
			wxCfg.setMchID(config.getMchid());
			wxCfg.setSubMchID(config.getSubMchid());

			// wap/web/mobile
			WxPrePayOrderParam param = new WxPrePayOrderParam();
			param.setIp(ip);
			param.setBody(order.getOrderName());
			param.setName(order.getOrderName());
			param.setNotify_url(comservicePath + "wxnotify.do");
			param.setOut_trade_no(sendOrderNo);
			param.setTotal_fee((int) (order.getOrderMoney().doubleValue() * 100));
			param.setOpenid(openId);
			param.setTrade_type("JSAPI");
			// 微信
			BaseReturnObject returnObject = null;
			try {
				returnObject = PayCreator.initPay(wxCfg, PayType.WX,
						payWay.getPayWayCode()).buildPayOrder(param,
						ClientType.WEB);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// result.setJsonParam(JSONUtil.toJSONString(returnObject.getReturn_object()));
			log.info("微信公众号:"
					+ JSONUtil.toJSONString(returnObject.getReturn_object()));
			result.put("jsonParam", returnObject.getReturn_object());

		} else if (payWayCode.equals("WXZF_APP")) {
			log.info("-------usePayWay---------:微信APP支付");
			/************************************ APP微信支付 *****************************************/
			Configure config = Configure.getInstance(payWay.getConfig(),
					payWayCode);
			WxConfig wxCfg = new WxConfig();
			wxCfg.setAppID(config.getAppid());
			wxCfg.setCertbytes(config.getCertbytes());
			wxCfg.setKey(config.getKey());
			wxCfg.setMchID(config.getMchid());
			wxCfg.setSubMchID(config.getSubMchid());

			WxPrePayOrderParam param = new WxPrePayOrderParam();
			param.setIp(ip);
			param.setBody(order.getOrderName());
			param.setName(order.getOrderName());
			param.setNotify_url(comservicePath + "wxnotify.do");
			param.setOut_trade_no(sendOrderNo);
			param.setTotal_fee((int) (order.getOrderMoney().doubleValue() * 100));
			param.setOpenid(openId);
			param.setTrade_type("APP");

			BaseReturnObject returnObject = null;
			try {
				returnObject = PayCreator.initPay(wxCfg, PayType.WX, null)
						.buildPayOrder(param, ClientType.APP);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("payReq", returnObject.getReturn_object());
			resultMap.put("return_code", returnObject.getReturn_code());
			resultMap.put("return_msg", returnObject.getReturn_msg());
			log.info("========weixin-APP======");
			log.info("微信APP:" + JSONUtil.toJSONString(resultMap));
			// result.setJsonParam(JSONUtil.toJSONString(resultMap) );
			result.put("jsonParam", resultMap);

		}
		result.put("orderNo", order.getOrderNo());
		return result;
	}

	// 支付1.0方式
	// @HttpMethod
	// @Transactional
	// public HashMap<String, Object> usePayWay(
	// @NotNull
	// String orderId,
	// @NotNull
	// String payWayCode,
	// String bankNo,
	// String payType,
	// String returnUrl,
	// String showUrl,
	//
	// String tradeType, ///微信支付用：JSAPI、NATIVE、APP
	// String openId, ///公众号ID
	// String wxProductId, ///微信商品ID
	// String ip ///支付终端IP
	// ){
	//
	// HashMap<String, Object> result = new HashMap<String, Object>();
	// PayWay payWay = orderDao.getPayWayByCode(payWayCode);
	// //创建支付日志
	// String orderPayId = addPayLog(payWay.getPayWayId().toString(), orderId,
	// null, bankNo);
	// McOrder order = orderDao.getOrderById(orderId);
	// order.setPayWayId(payWay.getPayWayId());
	// updateOrder(order);
	//
	// if(order.getOrderStatus().equals("5")){
	// //支付交易成功
	// result.put("retCode", 4);
	// result.put("retMessage", "请不要重复支付");
	// }
	//
	// String sendOrderNo = order.getOrderNo() + "-" + orderPayId;
	//
	// if(payWayCode.equals("ZFB")){
	// if(payType==null || !payType.equals("W")){
	// String alipayInfo = AlipayUtil.getUtil().buildPayInfo("预约挂号", "宇佑预约挂号费用",
	// order.getOrderMoney().toString(),sendOrderNo);
	// result.put("alipayInfo", alipayInfo);
	// }else{
	// if(StringUtil.isEmpty(returnUrl) || StringUtil.isEmpty(showUrl)){
	// result.put("retCode", 3);
	// result.put("retMessage", "returnUrl或showUrl为空");
	// }else{
	// Map<String,String> paramMap = AlipaySubmit.buildRequest("预约挂号",
	// "宇佑预约挂号费用", order.getOrderMoney().toString(), sendOrderNo, returnUrl,
	// showUrl);
	// result.put("alipayInfo", paramMap);
	// }
	// }
	// }else if(payWayCode.equals("WXZF")){
	// String res = wxPayService.buildPayInfo(order.getOrderName(),
	// order.getOrderMoney().toString(), sendOrderNo, tradeType, openId,
	// wxProductId,ip);
	// result.put("xmlParam", res);
	//
	// Object jsonres = wxPayService.buildPayInfoJS(order.getOrderName(),
	// order.getOrderMoney().toString(), sendOrderNo, tradeType, openId,
	// wxProductId, ip);
	// result.put("jsonParam", jsonres);
	// }
	// result.put("orderNo", order.getOrderNo());
	// return result;
	// }

	/** 挂号订单 */
	private final static String ORDER_TYPE_GH = "GH";

	@HttpMethod
	public Map<String, Object> getOrderDetailsByOrderId(String orderId) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 三种情况 1.挂号订单GH 2.诊间缴费ZJJF 3.VIP卡 HYK
		// 查询订单
		McOrder mcOrder = orderDao.getOrderById(orderId);
		// 订单详情McOrderDet
		List<McOrderDet> detais = orderDao.getOrderDetByOrderId(orderId);
		// 需要就诊人，性别，电话，身份证，类型，卡号
		UserFriend uf = null;// 关联就诊人
		// int i=0;

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		McReg mcReg = null;
		for (McOrderDet det : detais) {
			map = new HashMap<String, Object>();
			map.put("objectTypeCode", det.getProductTypeCode());
			map.put("objectPrice", det.getProductSum());
			map.put("objectToId", det.getProductToId());
			if (ORDER_TYPE_GH.equals(det.getProductTypeCode())) {
				// 挂号产品 与具体的挂号有记录有关，
				// 挂号订单GH 查询挂号记录表，其中包含就诊人 (需要就诊人，性别，电话，身份证，类型，卡号)
				mcReg = regDao.getMcRegAndFriendInfoByRegId(det
						.getProductToId());
				if (mcReg == null)
					continue;

				map.put("objectName_value", mcReg.getOrgName());
				map.put("objectItem_value", mcReg.getDepName());
				map.put("objectName_key", "医院");
				map.put("objectItem_key", "科室");
				map.put("objectStaffName", mcReg.getStaffName());
				// 医院的图片
				String img = systemDao.getCmsImageByWhereId(
						QueryUtils.CmsImageLib.ORG_ID, mcReg.getOrgId()
								.toString(), "logotp");
				if (!StringUtil.isEmpty(img)) {
					img = PIC_SERVER + img;
				}
				map.put("objectImage", img == null ? "" : img);
				map.put("objectTime", UtilDate.getDateToStr(
						mcReg.getOrderTime(), UtilDate.dtDate));
				map.put("objectPeriodName", mcReg.getPeriodName());
				map.put("objectAppointCode", mcReg.getAppointCode());// 门诊挂号号

				// 就诊人信息
				map.put("firendUser", mcReg.getUserName());
				map.put("firendSex", mcReg.getUserSex());
				map.put("firendPhone", mcReg.getUserPhone());
				map.put("firendIdCard", mcReg.getIdCard());
				map.put("firendCardType", mcReg.getCardTypeName());
				map.put("firendCardNo", mcReg.getCardNo());

			} else {
				/*
				 * //除了挂号以外 的其他产品 // 查询就诊人（ 此出只查询一次） 订单详情中包含了friendId 所以此处不用这中方法
				 * if(i==0){ i=1; uf =
				 * userDao.getFriendUserBySelf(mcOrder.getUserId().toString());
				 * }
				 */

				// 通过 md.getMcProductId() 获取产品
				McProduct mcp = orderDao.getProductById(det.getMcProductId()
						.toString());
				if (mcp == null)
					continue; // 可以可能为null
				map.put("objectTime", UtilDate.getDateToStr(
						mcOrder.getCreateTime(), UtilDate.dtDate));
				map.put("objectPeriodName", "");
				map.put("objectAppointCode", det.getPickCode());// 取号验证码
				// 诊间缴费
				if ("ZJJF".equals(det.getProductTypeCode())) {
					map.put("objectName_value", mcp.getOrgName());
					map.put("objectItem_value", mcp.getProductName());
					map.put("objectName_key", "医院");
					map.put("objectItem_key", "项目");
					// 医院的图片
					String img = systemDao.getCmsImageByWhereId(
							QueryUtils.CmsImageLib.ORG_ID, mcp.getOrgId()
									.toString(), "logotp");
					if (!StringUtil.isEmpty(img)) {
						img = PIC_SERVER + img;
					}
					map.put("objectImage", img == null ? "" : img);

					// 就诊人信息
					map.put("firendUser", det.getUserName());
					map.put("firendSex", det.getSex());
					map.put("firendPhone", det.getUserPhone());
					map.put("firendIdCard", det.getIdCard());
					map.put("firendCardType", det.getCardType());
					map.put("firendCardNo", det.getCardNo());
				} else {
					map.put("objectName_value", mcp.getProductName());
					map.put("objectItem_value", mcp.getProductTypeName());
					map.put("objectName_key", "名称");
					map.put("objectItem_key", "类型");
					map.put("objectImage", mcp.getProductImage());

					if (det.getFriendId() != null) {
						uf = userDao.getFriendUserByFriendId(det.getFriendId()
								.toString());
					}
					uf = uf == null ? new UserFriend() : uf;// 防止数据问题报错
					// 就诊人信息
					map.put("firendUser",
							uf.getUserName() == null ? "" : uf.getUserName());
					map.put("firendSex",
							uf.getUserSex() == null ? "" : uf.getUserSex());
					map.put("firendPhone",
							uf.getMobile() == null ? "" : uf.getMobile());
					map.put("firendIdCard", uf.getUserIdcard() == null ? ""
							: uf.getUserIdcard());
					if (uf.getCards() != null && uf.getCards().size() > 0) {
						map.put("firendCardType", uf.getCards().get(0)
								.getCardTypeName());
						map.put("firendCardNo", uf.getCards().get(0)
								.getCardNo());
					} else {
						map.put("firendCardType", "");
						map.put("firendCardNo", "");
					}
				}

			}

			dataList.add(map);
		}

		result.put("mcOrder", mcOrder);
		result.put("dataList", dataList);

		return result;
	}

	/**
	 * 订单退费成功 创建人:余宁 修改人:余宁 创建日期:2015年10月8日上午10:11:33 修改日期:2015年10月8日上午10:11:33
	 */
	public void orderRefundSuccess(String orderNo) {
		orderLog.info("开始退订单:" + orderNo);
		McOrder order = orderDao.getOrderByOrderNo(orderNo);
		log.info("orderStatus:" + order.getOrderStatus());

		order.setOrderStatus("7");
		updateUNOrder(order);

		McReg reg = getRegByOrder(order.getOrderId().toString());
		if (reg != null) {
			// 挂号订单
			reg.setStatus("4");
			reg.setIsUsed("2");
			regService.updateUNReg(reg);
			log.info("更新挂号记录状态:isUsed:" + 2 + ",status:" + 4);
		} else {
			// 非挂号订单
		}
		log.info("更新退款单");
		McOrderBack mcOrderBackSuccess = orderDao
				.getOrderBackSuccessByOrderNo(orderNo);
		if (mcOrderBackSuccess != null) {
			log.info("已经成功的退款记录");
			return;
		}
		McOrderBack mcOrderBack = orderDao.getOrderBackByOrderNo(orderNo);
		mcOrderBack.setOrderStatus("3");
		log.info("更新退款单:" + mcOrderBack.getBackOrderId());
		orderDao.updateOrderBackByBackId(mcOrderBack);
		addBackBill(mcOrderBack, order.getPayNo(), order.getPayWayId()
				.toString(), order.getOrderMoney());

		// 发送推送消息
		log.info("获取订单明细并推送退款消息：orderId" + order.getOrderId());
		List<McOrderDet> orderDets = orderDao.getOrderDetByOrderId(order
				.getOrderId().toString());
		systemService.sendOrderMsg(order, orderDets.get(0), 2);
	}

	/**
	 * 订单退费失败 创建人:余宁 修改人:余宁 创建日期:2015年10月8日上午10:11:33 修改日期:2015年10月8日上午10:11:33
	 */
	public void orderRefundFail(String orderNo) {
		// McOrder order = orderDao.getOrderByOrderNo(orderNo);
		McOrderBack mcOrderBack = orderDao.getOrderBackByOrderNo(orderNo);
		mcOrderBack.setOrderStatus("4");
		orderDao.updateOrderBackByBackId(mcOrderBack);
		// addBackBill(mcOrderBack, order.getPayNo(),
		// order.getPayWayId().toString(), order.getOrderMoney());
	}

	/**
	 * 检测订单状态
	 * 
	 * @param regId
	 *            挂号记录ID
	 * @return orderStatus:-1
	 *         订单暂未进入缓存,1待支付，2待支付已过期，3支付成功交易中，4支付成功交易失败，5支付成功交易成功，
	 *         6退款中，7已退款,8交易取消
	 */
	@HttpMethod
	public HashMap<String, Object> checkOrderStatus(@NotNull String orderId) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		// 查出挂号记录
		String orderJson = redisTemplate.opsForValue().get(
				RedisKeys.ORDER_LIST_HISTORY + orderId);
		String unorderJson = redisTemplate.opsForValue().get(
				RedisKeys.UNORDER_LIST_HISTORY + orderId);
		if (orderJson == null) {
			orderJson = unorderJson;
		}
		if (orderJson == null) {
			result.put("orderStatus", "-1");
		} else {
			JSONUtils.getMorpherRegistry().registerMorpher(
					new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss" }));
			McOrder order = (McOrder) JSONObject.toBean(
					JSONObject.fromObject(orderJson, JsonUtil.getConfig()),
					McOrder.class);

			result.put("orderStatus", order.getOrderStatus());
		}
		return result;
	}

	/**
	 * 根据订单获取挂号记录 创建人:余宁 修改人:余宁 创建日期:2015年10月17日上午11:42:18
	 * 修改日期:2015年10月17日上午11:42:18
	 * 
	 * @param orderId
	 * @return
	 */
	private McReg getRegByOrder(String orderId) {
		McOrder order = orderDao.getOrderById(orderId);
		List<McOrderDet> orderDets = orderDao.getOrderDetByOrderId(orderId);
		McOrderType orderType = orderDao.getOrderTypeById(order
				.getOrderTypeId().toString());
		if (orderType.getOrderCode().equals("GH")) {
			// 挂号
			String regId = orderDets.get(0).getProductToId();
			return regDao.getRegById(regId);
		} else {
			return null;
		}
	}

	/**
	 * 修改订单支付状态 创建人:余宁 修改人:余宁 创建日期:2015年10月17日上午11:28:08
	 * 修改日期:2015年10月17日上午11:28:08
	 * 
	 * @param orderId
	 * @param status
	 *            1 退费中，2 退费失败，3 已退费
	 */
	public void changeOrderPayStatus(String orderId, int status) {
		McOrder order = orderDao.getOrderById(orderId);
		if (order.getOrderStatus().equals("7")) {
			// 已经退过了
		} else {
			McReg reg = getRegByOrder(orderId);
			switch (status) {
			case 1:
				order.setOrderStatus("6");
				if (reg != null) {
					reg.setIsUsed("2");
					reg.setStatus("5");
				}
				break;
			case 2:
				order.setOrderStatus("9");
				if (reg != null) {
					reg.setIsUsed("2");
					reg.setStatus("6");
				}
				break;
			case 3:
				order.setOrderStatus("7");
				if (reg != null) {
					reg.setIsUsed("2");
					reg.setStatus("4");
				}
				break;
			default:
				break;
			}

			if (reg != null) {
				regDao.updateRegById(reg);
			}
			orderDao.updateOrderById(order);
		}
	}

}
