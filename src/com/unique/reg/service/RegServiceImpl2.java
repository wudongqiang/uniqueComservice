package com.unique.reg.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;
import com.unique.alipay.util.AlipaySubmit;
import com.unique.alipay.util.AlipayUtil;
import com.unique.alipay.util.UtilDate;
import com.unique.core.annotation.CollectionClass;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.annotation.ParamNoNull;
import com.unique.core.util.DesUtil;
import com.unique.core.util.FileUtil;
import com.unique.core.util.HttpUtil;
import com.unique.core.util.IdCardUtil;
import com.unique.core.util.JsonUtil;
import com.unique.core.util.RedisKeys;
import com.unique.core.util.RemotUtils;
import com.unique.core.util.StringUtil;
import com.unique.core.util.UUIDUtil;
import com.unique.order.dao.OrderDao;
import com.unique.order.po.McOrder;
import com.unique.order.po.McOrderDet;
import com.unique.order.po.McProduct;
import com.unique.order.po.OrgPatient;
import com.unique.order.po.ProductPayInfo;
import com.unique.order.po.ProductRealPrice;
import com.unique.order.service.OrderService;
import com.unique.org.dao.OrgDao;
import com.unique.org.dao.StaffDao;
import com.unique.org.po.StaffType;
import com.unique.reg.dao.RegDao;
import com.unique.reg.orgpo.Reg4Org;
import com.unique.reg.orgpo.Reg4OrgQJ;
import com.unique.reg.orgpo.User4Org;
import com.unique.reg.orgpo.User4OrgQJ;
import com.unique.reg.po.BillDetail;
import com.unique.reg.po.BillSrcType;
import com.unique.reg.po.Billing;
import com.unique.reg.po.DutyPeriod;
import com.unique.reg.po.McDuty;
import com.unique.reg.po.McReg;
import com.unique.reg.po.Org;
import com.unique.reg.po.PayWay;
import com.unique.reg.po.ProductPrice;
import com.unique.reg.po.ProductPriceType;
import com.unique.reg.po.RegWay;
import com.unique.reg.po.Staff;
import com.unique.reg.vo.ReservationDetails;
import com.unique.reg.vo.patient.McRegInfo;
import com.unique.reg.vo.patient.PatientInfo;
import com.unique.reg.vo.patient.ScheduleDutyInfo;
import com.unique.survey.dao.SurveyDao;
import com.unique.survey.vo.SurveySchduleDto;
import com.unique.system.dao.SystemDao;
import com.unique.system.service.SystemService;
import com.unique.user.dao.UserDao;
import com.unique.user.po.AmsUser;
import com.unique.user.po.McCard;
import com.unique.user.po.UserFriend;
import com.unique.user.po.UserSchedule;
import com.unique.user.service.UserScheduleService;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

//@Service("regService")
public class RegServiceImpl2 implements RegService {
	@Resource
	private RegDao regDao;
	@Resource
	private OrgDao orgDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private UserDao userDao;
	@Resource
	private StaffDao staffDao;
	@Resource
	private SurveyDao surveyDao;

	@Resource(name = "userScheduleService")
	private UserScheduleService userScheduleService;

	@Resource
	private OrderService orderService;
	@Resource
	private SystemService systemService;
	@Resource
	private SystemDao systemDao;
	public static Logger log = Logger.getLogger("reg");
	@Resource
	private StringRedisTemplate redisTemplate;

	// /挂号记录留存redis的天数
	private static int REG_LIVE_DAYS = Integer.parseInt(FileUtil
			.readProperties("comservice.properties", "regLiveDays"));
	// /微信推送地址
	private static String WX_PUSH = FileUtil.readProperties(
			"comservice.properties", "wxpush");

	/**
	 * 挂号
	 */
	@HttpMethod
	public HashMap<String, Object> reg(@ParamNoNull String dutyId,
			String dutyPeriodId, @ParamNoNull String userId,
			@ParamNoNull String friendId, @ParamNoNull String regWayCode,
			String sourceCode, String hisUserId, String cardTypeId,Integer isBund) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		// 获取用户
		AmsUser user = userDao.getUserById(userId);
		UserFriend userFriend = userDao.getFriendUserByFriendId(friendId);
		if (user == null) {
			result.put("retCode", 3);
			result.put("err_message", "该用户已被删除。");
			return result;
		}
		if (user.getIsActive() != null && user.getIsActive().intValue() == 2) {
			result.put("retCode", 14);
			result.put("err_message", "该用户已冻结。");
			return result;
		}
		if (userFriend == null) {
			result.put("retCode", 13);
			result.put("err_message", "就诊者不存在");
			return result;
		}
		String idCard = userFriend.getUserIdcard();
		if (StringUtil.isEmpty(idCard)) {
			result.put("retCode", 4);
			result.put("err_message", "身份证不存在。");
			return result;
		}

		// int regRepeat = regDao.checkRegForRepeat(idCard,dutyId);
		// if(regRepeat>0){
		// result.put("retCode", 5);
		// result.put("err_message", "该身份证已经预约过该号，不能重复预约。");
		// return result;
		// }

		McDuty duty = regDao.getCanUserDutyById(dutyId);
		if (duty == null) {
			result.put("retCode", 11);
			result.put("err_message", "该排班无效");
			return result;
		}

		Org org = regDao.getOrgById(duty.getOrgId().toString());
		Staff staff = regDao.getStaffById(duty.getStaffId().toString());
		// 是否分时段
		String hasFsd = org.getHasPeriod();
		if (StringUtil.isEmpty(dutyPeriodId)
				&& ("1".equals(hasFsd) || "2".equals(hasFsd))) {
			result.put("retCode", 6);
			result.put("err_message", "缺少分时段ID");
			return result;
		}
		// 挂号次数查询
		// int regTimes = regDao.checkRegForRegTimes(idCard);
		// if(regTimes>=3){
		// result.put("retCode", 7);
		// result.put("err_message", "你今日的预约次数已超限制。");
		// return result;
		// }
		// 黑名单限制
		int blackCount = regDao.isUserBlack(duty.getOrgId().toString(), userId,
				"GH");
		if (blackCount > 0) {
			result.put("retCode", 8);
			result.put("err_message", "你已经被" + org.getOrgShortName() + "设为黑名单。");
			return result;
		}

		// 儿童医院限制
		if ("Y".equalsIgnoreCase(org.getChildHospital())
				&& new IdCardUtil(idCard).isChild()) {
			result.put("retCode", 9);
			result.put("err_message", "您输入的身份证已超过18岁！婴儿或小孩的身份证号您可查看其户口本！");
			return result;
		}
		List<McProduct> products = orderDao.getProductByStaffTypeId(duty
				.getStaffTypeId().toString());
		/******************* 计算费用列表 *********************/
		double d_price = 0;// 代扣费用
		double site_price = 0;// 不代扣费用，即现场支付
		ProductRealPrice rPrice = orderService.getPriceByStaffType(duty
				.getStaffTypeId().toString(), org);
		d_price = rPrice.getDiscountPriceD();
		site_price = rPrice.getDiscountPriceSite();

		/********************* 创建挂号记录 ***********************/
		// 占号
		int subRegInt = subReg(dutyId, dutyPeriodId);

		try {
			if (subRegInt <= 0) {
				result.put("retCode", 12);
				// result.put("err_message", "占号失败");
				result.put("err_message", "挂号失败");
				return result;
			}

			McReg reg = new McReg();
			reg.setCreateOperator(user.getUserId());
			reg.setCreateOperatorName(user.getUserName());
			reg.setCreateTime(new Date());
			reg.setDepId(duty.getDepId());
			reg.setDepName(duty.getDepName());
			reg.setDsb("1");
			reg.setDutyId(new BigDecimal(dutyId));

			reg.setRegUniqueId(UUIDUtil.genRegUniqueId());
			RegWay regway = regDao.getRegWayByCode(regWayCode);
			if (regway == null) {
				result.put("retCode", 10);
				result.put("err_message", "支付方式代码不存在！");
				return result;
			}
			reg.setRegWayId(regway.getRegWayId());
			reg.setRegWayName(regway.getRegWayName());
			// reg.setFirstvisitindicctor(new BigDecimal(1));
			reg.setIdCard(idCard);
			reg.setAmount(getProductPrice(duty.getStaffTypeId().toString()));
			reg.setRegNumber(new BigDecimal(0));
			reg.setOrderTime(duty.getDutyDate());

			// 分时段
			if ("1".equals(hasFsd) || "2".equals(hasFsd)) {
				DutyPeriod dutyPeriod = regDao.getDutyPeriodById(dutyPeriodId);
				reg.setDutyPeriodId(dutyPeriod.getDutyPeriodId());
				reg.setPeriodEndTime(dutyPeriod.getEndTime());
				reg.setPeriodStartTime(dutyPeriod.getStartTime());
				reg.setPeriodRegTime(dutyPeriod.getRegTime());
				reg.setPeriodUniqueId(dutyPeriod.getPeriodUniqueId());
			}
			reg.setPeriodId(duty.getPeriodId());
			reg.setPeriodName(duty.getPeriodName());

			if (d_price == 0) {
				// 不需要扣费
				reg.setIsUsed("0");
				reg.setStatus("7");
			} else {
				// 挂号失败E,预约未付费U,可取0,已取1,已退号2,过期3,爽约4,退号失败R，挂号中W
				// 状态：0未付费1扣费成功2扣费中3扣费失败4退费成功5退费中6退费失败7免支付
				// 是否走接口：1不走接口，2走及时接口，3异步接口
				if (org.getHasRemoteReg() == null
						|| "1".equals(org.getHasRemoteReg())) {
					reg.setIsUsed("U");
					reg.setStatus("0");

					// 设置挂号序号
					int regNum = regDao.getRegNum(dutyId);
					reg.setRegNumber(new BigDecimal(regNum));
				} else if (org.getHasRemoteReg() != null
						&& "2".equals(org.getHasRemoteReg())) {
					// 支付完后走同步接口
					reg.setIsUsed("U");
					reg.setStatus("0");
				} else if (org.getHasRemoteReg() != null
						&& "3".equals(org.getHasRemoteReg())) {
					// 支付完后走异步接口
					reg.setIsUsed("U");
					reg.setStatus("0");
				}
			}

			// 状态：0未付费1扣费成功2扣费中3扣费失败4退费成功5退费中6退费失败7免支付
			reg.setOperator(user.getUserId());
			reg.setOperatorName(user.getUserName());
			reg.setOpTime(new Date());
			reg.setOrgId(org.getOrgId());
			reg.setOrgName(org.getOrgName());
			reg.setStaffId(staff.getStaffId());
			reg.setStaffName(staff.getStaffName());
			// reg.setUserId(userFriend.getFriendId());
			reg.setFriendId(userFriend.getFriendId());
			reg.setUserId(user.getUserId());
			reg.setUserAddr(userFriend.getUserAddr());
			reg.setUserBirth(userFriend.getUserBirth());
			reg.setUserName(userFriend.getUserName());
			reg.setUserPhone(userFriend.getMobile());
			reg.setUserSex(userFriend.getUserSex());
			reg.setPickupAddr(org.getPickupAddr());
			reg.setHisUserId(hisUserId);
			// 添加挂号记录
			regDao.addMcReg(reg);
			log.info("创建挂号记录：" + reg.getRegId().longValue() + " 排班ID：" + dutyId
					+ " 分时段ID：" + dutyPeriodId);

			// 将挂号记录放入REDIS
			updateRegInRedis(reg.getRegId().toString(), reg);
			redisTemplate.opsForHash().put(RedisKeys.REG_LIST_HISTORY_LIST,
					reg.getRegId().toString(), reg.getRegId().toString());

			List<ProductPayInfo> productMaps = new ArrayList<ProductPayInfo>();
			for (McProduct p : products) {
				ProductPayInfo pMap = new ProductPayInfo();
				pMap.setProductId(p.getMcProductId().toString());
				pMap.setCount(1);
				pMap.setToId(reg.getRegId().toString());
				productMaps.add(pMap);
			}

			OrgPatient orgPatient = new OrgPatient();
			List<McCard> cards = userDao.getCardByFriendId(friendId);
			for (McCard card : cards) {
				if (!StringUtil.isEmpty(cardTypeId)
						&& card.getCardTypeId().toString().equals(cardTypeId)) {
					orgPatient.setCardNo(card.getCardNo());
					orgPatient.setCardType(card.getCardTypeName());
					break;
				}
			}
			if (StringUtil.isEmpty(cardTypeId)) {
				orgPatient.setCardNo(idCard);
				orgPatient.setIdCard(idCard);
				orgPatient.setCardType("身份证");
			}

			orgPatient.setHisUserId(hisUserId);
			orgPatient.setSex(userFriend.getUserSex());
			orgPatient.setUserName(userFriend.getUserName());
			orgPatient.setUserPhone(userFriend.getMobile());

			HashMap<String, Object> orderResult = orderService.placeOrder("GH",
					userId, productMaps, duty.getStaffTypeId().toString(),
					sourceCode, friendId, orgPatient, hisUserId);
			if (orderResult.get("retCode") != null
					&& Integer.parseInt((String) orderResult.get("retCode")) > 0) {
				throw new RuntimeException("下订单失败");
			}
			// 获得可用支付方式集合
			List<PayWay> payWays = regDao.getOnPayWay();
			// 挂号成功,等待支付或免支付
			result.put("retCode", 1);
			result.put("staffTypeName", duty.getStaffTypeName());
			reg.setOrderId(new BigDecimal(orderResult.get("orderId").toString()));
			result.put("reg", reg);
			result.put("orderId", orderResult.get("orderId"));
			result.put("orderNo", orderResult.get("orderNo"));
			result.put("d_price", d_price);// 代扣费用
			result.put("site_price", site_price);// 现场支付
			result.put("recoupWay", products.get(0).getRecoupWay());
			result.put("payWays", payWays);
			// 传递应付费给手机端
			result.put("payablePrice", result.get("d_price"));
			if (d_price == 0) {
				// 不需要扣费
				orderService.paySuccess("FREE", null, null,
						orderResult.get("orderNo").toString(),
						orderResult.get("orderId").toString());
				result.put("message", "挂号成功，请等待医院确认。");
			} else {
				result.put("message", "挂号成功，请支付");
			}
		} catch (Exception e) {
			// 未知异常
			result.put("retCode", 10);
			result.put("err_message", "未知异常");
			e.printStackTrace();

			// 放号
			releaseReg(dutyId, dutyPeriodId);
		}
		return result;
	}

	/**
	 * 获取级别应付款
	 * 
	 * @param staffTypeId
	 * @return
	 */
	// public double getPayablePrice(String staffTypeId,Org org){
	// // List<ProductPrice> psList =
	// regDao.getPriceListByStaffType(staffTypeId);
	// List<McProduct> products= orderDao.getProductByStaffTypeId(staffTypeId);
	// double d_price = 0;//代扣费用
	// double site_price = 0;//不代扣费用，即现场支付
	// for(McProduct p : products){
	// List<ProductPrice> psList =
	// orderDao.getProductPricesByProductId(p.getMcProductId().toString(),staffTypeId);
	// for(ProductPrice price : psList){
	// double percent = price.getPercent().doubleValue();
	// if(percent==0)percent=1;
	// if("Y".equals(p.getRecoupWay())){
	// //代扣费
	// d_price += price.getPriceAmount().doubleValue() * percent;
	// }else{
	// //不代扣费
	// site_price += price.getPriceAmount().doubleValue() * percent;
	// }
	// }
	// }
	// return d_price;
	// }

	/**
	 * 获取商品费用
	 * 
	 * @param staffTypeId
	 * @param org
	 * @return
	 */
	private double getProductPrice(String staffTypeId) {
		List<ProductPrice> psList = regDao.getPriceListByStaffType(staffTypeId);
		double price = 0;// 代扣费用
		for (ProductPrice ps : psList) {
			price += ps.getPriceAmount().doubleValue();
		}
		return price;
	}

	/**
	 * 支付选择 创建人:余宁 修改人:余宁 创建日期:2015年9月28日上午11:20:41 修改日期:2015年9月28日上午11:20:41
	 * 
	 * @param orderId
	 *            订单ID
	 * @param payWayCode
	 *            支付方式编码
	 * @param bankNo
	 *            银行卡号
	 * @param payType
	 *            支付类型，W微信，空表示标准支付方式
	 * @param returnUrl
	 *            返回地址
	 * @param showUrl
	 *            商品展示地址
	 * @return
	 */
	@HttpMethod
	@Transactional
	public HashMap<String, Object> choosePayWay(@NotNull String orderId,
			@NotNull String payWayCode, String bankNo, String payType,
			String returnUrl, String showUrl) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		PayWay payWay = orderDao.getPayWayByCode(payWayCode);
		McOrder order = orderDao.getOrderById(orderId);

		if (order.getOrderStatus().equals("5")) {
			// 支付交易成功
			result.put("retCode", 4);
			result.put("retMessage", "请不要重复支付");
			return result;
		}
		// 创建支付记录
		String orderPayId = orderService.addPayLog(payWay.getPayWayId()
				.toString(), orderId, null, bankNo);
		String sendOrderNo = order.getOrderNo() + "-" + orderPayId;

		if (payWayCode.equals("ZFB")) {
			if (payType == null || !payType.equals("W")) {
				String alipayInfo = AlipayUtil.getUtil().buildPayInfo("预约挂号",
						"宇佑预约挂号费用", order.getOrderMoney().toString(),
						sendOrderNo);
				result.put("alipayInfo", alipayInfo);
			} else {
				if (StringUtil.isEmpty(returnUrl)
						|| StringUtil.isEmpty(showUrl)) {
					result.put("retCode", 3);
					result.put("retMessage", "returnUrl或showUrl为空");
				} else {
					String paramMap = AlipaySubmit.buildRequest(
							"预约挂号", "宇佑预约挂号费用", order.getOrderMoney()
									.toString(), sendOrderNo, returnUrl,
							showUrl);
					result.put("alipayInfo", paramMap);
				}
			}
		}

		result.put("orderNo", order.getOrderNo());
		return result;
	}

	/**
	 * 退号流程 创建人:余宁 修改人:余宁 创建日期:2015年10月9日上午9:19:29 修改日期:2015年10月9日上午9:19:29
	 * 
	 * @param regId
	 * @param opUserId
	 * @param backReason
	 * @param userId
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> unReg(@ParamNoNull String regId,
			@ParamNoNull String opUserId, @ParamNoNull String backReason,
			@ParamNoNull String userId) {
		McOrder order = regDao.getOrderByRegId(regId);
		HashMap<String, Object> resultMap = orderService.unOrder(order
				.getOrderId().toString(), opUserId, backReason, userId, null,
				null);
		return resultMap;
	}

	/**
	 * 添加账单
	 */
	@Transactional
	private void addBilling(List<ProductPrice> psList, String billTypeCode,
			AmsUser user, String srcId, String RecoupWay) {
		Billing bill = new Billing();
		BillSrcType type = regDao.getBillTypeByCode(billTypeCode);
		bill.setBillTypeId(type.getBillTypeId());
		bill.setCreateTime(new Date());
		bill.setOperator(user.getUserId());
		bill.setOperatorName(user.getUserName());
		bill.setOpTime(new Date());
		bill.setSrcId(new BigDecimal(srcId));
		bill.setStatus("R");
		regDao.addBilling(bill);
		for (ProductPrice price : psList) {
			// 账单明细
			BillDetail billDetail = new BillDetail();
			billDetail.setBillingId(bill.getBillingId());
			billDetail.setCreateTime(new Date());
			billDetail.setFeeAmount(new BigDecimal(1));
			billDetail.setFeeMoney(price.getPriceAmount());
			billDetail.setOperateOrgCode("YYXX");
			billDetail.setOperateType("1");
			// 非代扣
			if (RecoupWay.equals("2")) {
				billDetail.setOperateType("");
				billDetail.setOperateOrgCode("");
			}
			billDetail.setOperator(user.getUserId());
			billDetail.setOpTime(new Date());
			billDetail.setProductprice(price.getPriceId());
			billDetail.setStatus("R");
			regDao.addBillDetail(billDetail);
		}
	}

	/**
	 * 占号接口
	 * 
	 * @param dutyId
	 *            排班ID
	 * @param dutyPeriodId
	 *            分时段ID，可为空
	 * @return 1成功，0失败
	 */
	@Transactional
	public int subReg(String dutyId, String dutyPeriodId) {
		if (!StringUtil.isEmpty(dutyPeriodId)) {
			int result = regDao.modDutyPeriod("W", dutyPeriodId, "C");
			if (result == 1) {
				result = regDao.subRegRemain(dutyId);
				if (result == 1) {
					log.info("占号成功，排班：" + dutyId + " 序号：" + dutyPeriodId);
					return 1;
				} else {
					// 占号失败释放分时段
					regDao.modDutyPeriod("C", dutyPeriodId, "W");
					log.info("占号失败，排班：" + dutyId + " 序号：" + dutyPeriodId);
					return 0;
				}
			} else {
				log.info("占分时段失败，排班：" + dutyId + " 序号：" + dutyPeriodId);
				return 0;
			}
		} else {
			int result = regDao.subRegRemain(dutyId);
			if (result == 1) {
				log.info("占号成功，排班：" + dutyId);
				return 1;
			} else {
				// 占号失败释放分时段
				regDao.modDutyPeriod("C", dutyPeriodId, "W");
				log.info("占号失败，排班：" + dutyId);
				return 0;
			}
		}
	}

	/**
	 * 放号
	 */
	@Transactional
	public int releaseReg(String dutyId, String dutyPeriodId) {
		if (!StringUtil.isEmpty(dutyPeriodId)) {
			int result = regDao.modDutyPeriod("C", dutyPeriodId, "W");
			if (result == 1) {
				result = regDao.addRegRemain(dutyId);
				if (result == 1) {
					log.info("放号成功，排班：" + dutyId + " 序号：" + dutyPeriodId);
					return 1;
				} else {
					// 占号失败释放分时段
					regDao.modDutyPeriod("W", dutyPeriodId, "C");
					log.info("放号失败，排班：" + dutyId + " 序号：" + dutyPeriodId);
					return 0;
				}
			} else {
				log.info("放分时段失败，排班：" + dutyId + " 序号：" + dutyPeriodId);
				return 0;
			}
		} else {
			int result = regDao.addRegRemain(dutyId);
			if (result == 1) {
				log.info("放号成功，排班：" + dutyId);
				return 1;
			} else {
				// 占号失败释放分时段
				return 0;
			}
		}
	}

	/**
	 * 检测挂号记录状态
	 * 
	 * @param regId
	 *            挂号记录ID
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> checkRegStatus(@NotNull String regId) {

		HashMap<String, Object> result = new HashMap<String, Object>();
		// 查出挂号记录
		String regJson = redisTemplate.opsForValue().get(
				RedisKeys.REG_LIST_HISTORY + regId);
		String unregJson = redisTemplate.opsForValue().get(
				RedisKeys.UNREG_LIST_HISTORY + regId);
		if (regJson == null) {
			regJson = unregJson;
		}
		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss" }));
		McReg reg = (McReg) JSONObject.toBean(
				JSONObject.fromObject(regJson, JsonUtil.getConfig()),
				McReg.class);

		// 状态：0未付费1扣费成功2扣费中3扣费失败4退费成功5退费中6退费失败7免支付
		// 挂号失败E,预约未付费U,可取0,已取1,已退号2,过期3,爽约4,退号失败R，挂号中W
		if (("U".equalsIgnoreCase(reg.getIsUsed()) && "0".equalsIgnoreCase(reg
				.getStatus()))) {
			result.put("regCode", 1);
			result.put("reg_message", "下单成功等待支付");
		} else if ("W".equalsIgnoreCase(reg.getIsUsed())
				&& ("1".equalsIgnoreCase(reg.getStatus()) || "7"
						.equalsIgnoreCase(reg.getStatus()))) {
			result.put("regCode", 2);
			result.put("reg_message", "支付成功，等待医院确认");
		} else if ("3".equalsIgnoreCase(reg.getIsUsed())
				&& "3".equalsIgnoreCase(reg.getStatus())) {
			result.put("regCode", 3);
			result.put("reg_message", "未支付已过期");
		} else if ("E".equalsIgnoreCase(reg.getIsUsed())
				&& "5".equalsIgnoreCase(reg.getStatus())) {
			result.put("regCode", 4);
			result.put("reg_message", "挂号失败，退费中");
		} else if ("E".equalsIgnoreCase(reg.getIsUsed())
				&& "4".equalsIgnoreCase(reg.getStatus())) {
			result.put("regCode", 5);
			result.put("reg_message", "挂号失败，已退款");
		} else if ("E".equalsIgnoreCase(reg.getIsUsed())
				&& "7".equalsIgnoreCase(reg.getStatus())) {
			result.put("regCode", 6);
			result.put("reg_message", "挂号失败，免费号");
		} else if ("0".equalsIgnoreCase(reg.getIsUsed())) {
			result.put("regCode", 7);
			result.put("reg_message", "待就诊");
		} else if ("1".equalsIgnoreCase(reg.getIsUsed())) {
			result.put("regCode", 8);
			result.put("reg_message", "已取号");
		} else if ("2".equalsIgnoreCase(reg.getIsUsed())
				&& "5".equalsIgnoreCase(reg.getStatus())) {
			result.put("regCode", 9);
			result.put("reg_message", "已退号，退款中");
		} else if ("2".equalsIgnoreCase(reg.getIsUsed())
				&& "4".equalsIgnoreCase(reg.getStatus())) {
			result.put("regCode", 10);
			result.put("reg_message", "已退号，已退款");
		} else if ("2".equalsIgnoreCase(reg.getIsUsed())
				&& "7".equalsIgnoreCase(reg.getStatus())) {
			result.put("regCode", 11);
			result.put("reg_message", "已退号，免费号");
		} else if ("4".equalsIgnoreCase(reg.getIsUsed())) {
			result.put("regCode", 12);
			result.put("reg_message", "爽约");
		} else {
			result.put("regCode", 13);
			result.put("reg_message", "意外的状态:is_used:" + reg.getIsUsed()
					+ " status:" + reg.getStatus());
			System.out.println("is_used:" + reg.getIsUsed() + " status:"
					+ reg.getStatus());
		}
		return result;
	}

	@Transactional
	public void updateReg(McReg reg) {
		regDao.updateRegById(reg);

		// 更新挂号记录从REDIS
		updateRegInRedis(reg.getRegId().toString(), reg);
	}

	@Transactional
	public void updateUNReg(McReg reg) {
		regDao.updateRegById(reg);

		// 更新挂号记录从REDIS
		updateUNRegInRedis(reg.getRegId().toString(), reg);
	}

	public void updateRegInRedis(String regId, McReg reg) {
		// 更新REDIS
		redisTemplate.delete(RedisKeys.REG_LIST_HISTORY + regId);
		redisTemplate.opsForValue().set(RedisKeys.REG_LIST_HISTORY + regId,
				JSONObject.fromObject(reg, JsonUtil.getConfig()).toString());

		redisTemplate.expire(RedisKeys.REG_LIST_HISTORY + regId, REG_LIVE_DAYS,
				TimeUnit.DAYS);
	}

	public void updateUNRegInRedis(String regId, McReg reg) {
		// 更新REDIS
		if (redisTemplate.hasKey(RedisKeys.REG_LIST_HISTORY + regId)) {
			redisTemplate.opsForValue()
					.set(RedisKeys.REG_LIST_HISTORY + regId,
							JSONObject.fromObject(reg, JsonUtil.getConfig())
									.toString());
		}

		redisTemplate.delete(RedisKeys.UNREG_LIST_HISTORY + regId);
		redisTemplate.opsForValue().set(RedisKeys.UNREG_LIST_HISTORY + regId,
				JSONObject.fromObject(reg, JsonUtil.getConfig()).toString());

		redisTemplate.expire(RedisKeys.UNREG_LIST_HISTORY + regId,
				REG_LIVE_DAYS, TimeUnit.DAYS);
	}

	/**
	 * 支付成功 创建人:余宁 修改人:余宁 创建日期:2015年8月13日上午9:35:13 修改日期:2015年8月13日上午9:35:13
	 * 
	 * @param regId
	 * @param payWayCode
	 * @param payNo
	 */
	@Transactional
	public void regSuccess(String regId, String payWayCode, String payNo,
			McOrder order, String orderPayId) {
		log.info("挂号成功");
		McReg reg = regDao.getRegById(regId);
		PayWay payway = orderDao.getPayWayByCode(payWayCode);
		AmsUser user = userDao.getUserById(reg.getUserId().toString());

		reg.setPayWayId(payway.getPayWayId());
		reg.setPayWayName(payway.getPayWayName());
		reg.setPayNum(payNo);
		// 更新状态为支付成功

		log.info("更新挂号方式：" + payway.getPayWayName());
		reg.setStatus("1");
		reg.setIsUsed("W");
		updateReg(reg);
		// 更新REDIS
		updateRegInRedis(regId, reg);

		/************ 走医院接口 ************/
		Org org = regDao.getOrgById(reg.getOrgId().toString());
		UserFriend friend = userDao.getFriendUserByFriendId(reg.getFriendId()
				.toString());
		McDuty duty = regDao.getDutyById(reg.getDutyId().toString());

		String hisRet = "";

		if (org.getHasRemoteReg() == null
				|| "".equals(org.getHasRemoteReg().trim())
				|| "1".equals(org.getHasRemoteReg())) {
			// 不走接口
			log.info("不走接口:" + regId);

			reg.setIsUsed("0");
			updateReg(reg);
			orderDao.updateOrderPayStatus(payNo, "3", orderPayId);

			// 订单成功
			order.setOrderStatus("5");
			orderService.updateOrder(order);

		} else if ("2".equals(org.getHasRemoteReg())) {
			// 实时接口
			log.info("实时接口:" + regId);

			// 调接口
			// 走同步接口
			HashMap<String, Object> syResult = null;
			try {
				syResult = synchronizedToOrg_Reg(friend, user,
						org.getHospitalUrl(), reg, duty,null);
				hisRet = syResult.get("retCode").toString();
			} catch (Exception e) {
				log.info("同步医院接口未知异常");
				syResult = new HashMap<String, Object>();
			}
			log.info("同步接口返回数据:" + JSONObject.fromObject(syResult).toString());
			if (syResult.containsKey("visitNo")) {
				reg.setAppointCode((String) syResult.get("visitNo"));
			}
			if (syResult.containsKey("patientId")) {
				reg.setHisUserId((String) syResult.get("patientId"));
			}

			log.info("判断是否退号");
			log.info(syResult.get("retCode").toString());
			if (syResult.containsKey("retCode")
					&& (syResult.get("retCode").toString().equals("0") || syResult
							.get("retCode").toString().equals("0000"))) {
				reg.setIsUsed("0");

				// 订单成功
				order.setOrderStatus("5");
				orderService.updateOrder(order);

			} else {
				log.info("开始退号:" + reg.getRegId());
				// 医院端接口调用失败
				reg.setIsUsed("E");
				reg.setStatus("5");
				updateReg(reg);

				order.setOrderStatus("6");
				orderService.updateOrder(order);
				// 退费流程
				int unOrderResultCode = -1;
				try {
					HashMap unOrderResult = orderService.unOrder(order
							.getOrderId().toString(), user.getUserId()
							.toString(), "医院端接口调用失败", user.getUserId()
							.toString(), "site", false);
					log.info("退号结果:" + reg.getRegId() + ",JSON:"
							+ JSONObject.fromObject(unOrderResult));

					if (unOrderResult.containsKey("retCode")) {
						unOrderResultCode = (Integer) unOrderResult
								.get("retCode");
					}
				} catch (RuntimeException e) {
					// 在后台中输出错误异常异常信息，通过log4j输出。
					Logger elog = Logger.getLogger("exception");
					elog.info("**************************************************************");
					elog.info("退订单过程中出现错误");
					elog.info("Exception class: " + e.getClass().getName());
					elog.info("ex.getMessage():" + e.getMessage());
					String exceptionStr = ExceptionUtils.getFullStackTrace(e);
					elog.info(exceptionStr);
					elog.info("**************************************************************");
				}
				return;
			}
			updateReg(reg);
		} else if ("3".equals(org.getHasRemoteReg())) {
			log.info("异步接口:" + regId);

			// 异步接口
			reg.setIsUsed("0");
			updateReg(reg);

			// 订单成功
			order.setOrderStatus("5");
			orderService.updateOrder(order);
		}

		// 发送推送消息以及短信
		if (!hisRet.equals("0000")) {
			McOrderDet orderDet = regDao.getOrderDetByRegId(reg.getRegId()
					.toString());
			systemService.sendOrderMsg(order, orderDet, 1);
		}

	}

	/**
	 * 查询挂号记录分页
	 */
	@HttpMethod
	public List<McReg> regList(String userId, Long startRow, Long rows,
			String[] status, String[] isUsed) {
		Long endRows = null;
		if (startRow != null && rows != null) {
			endRows = startRow + rows;
		}
		List<McReg> result = regDao.regList(userId, startRow, endRows, status,
				isUsed);
		return result;
	}

	/**
	 * 查询挂号记录分页（总数）
	 */
	@HttpMethod
	public HashMap<String, Object> regListCount(String userId, String[] status,
			String[] isUsed) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("count", regDao.regListCount(userId, status, isUsed));
		return result;
	}

	private int daysBetween(Date early, Date late) {
		java.util.Calendar calst = java.util.Calendar.getInstance();
		java.util.Calendar caled = java.util.Calendar.getInstance();
		calst.setTime(early);
		caled.setTime(late);
		// 设置时间为0时
		calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calst.set(java.util.Calendar.MINUTE, 0);
		calst.set(java.util.Calendar.SECOND, 0);
		caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
		caled.set(java.util.Calendar.MINUTE, 0);
		caled.set(java.util.Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
				.getTime().getTime() / 1000)) / 3600 / 24;

		return days;
	}

	/**
	 * 获取医生当月排班
	 * 
	 * @param staffId
	 * @param monthStr
	 * @return
	 */
	@HttpMethod
	public List<McDuty> getDutyByMonth(@NotNull String staffId, String monthStr) {
		return regDao.getDutyByMonth(staffId, monthStr);
	}

	/**
	 * 根据日期和医生获取挂号列表
	 * 
	 * @param staffId
	 * @param date
	 * @param period
	 * @return
	 */
	@HttpMethod
	public List<McReg> getRegByDutyDate(String staffId, String date,
			String period) {
		return regDao.getRegByDutyDate(staffId, date, period);
	}

	/**
	 * 添加医生级别
	 * 
	 * @param staffType
	 * @param prices
	 *            {price:金额，code:编码，name:名称}
	 */
	@HttpMethod
	@Transactional
	public HashMap<String, Object> addStaffType(
			@NotNull StaffType staffType,
			@NotNull String userTypeId,
			@CollectionClass(value = ProductPriceType.class) List<ProductPriceType> prices) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		McProduct p = orderDao.getGhProductByOrg(staffType.getOrgId()
				.toString());
		StaffType st = staffDao
				.getStaffTypeByCode(staffType.getStaffTypeCode());
		if (st != null) {
			result.put("retCode", 4);
			result.put("retMessage", "医生级别已存在");
			return result;
		}
		int i = regDao.addStaffType(staffType);
		if (i <= 0) {
			result.put("retCode", 3);
			result.put("retMessage", "添加医生级别失败");
			return result;
		}

		if (prices != null && prices.size() > 0) {
			for (ProductPriceType pricet : prices) {
				String typeCode = pricet.getTypeCode();
				ProductPriceType priceType = regDao.getProductPriceTypeByCode(
						typeCode, staffType.getOrgId().toString());
				if (priceType == null) {
					regDao.addProductPriceType(priceType);
				}
				priceType.setPrice(pricet.getPrice());

				ProductPrice pp = new ProductPrice();
				pp.setTypeId(priceType.getTypeId());
				pp.setProductCharacterId(new BigDecimal(1));
				pp.setMcProductId(p.getMcProductId());
				pp.setUserTypeId(new BigDecimal(userTypeId));
				pp.setPriceAmount(pricet.getPrice());
				pp.setPercent(new BigDecimal(0));
				pp.setStartDate(new Date());
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.YEAR, 20);
				pp.setEndDate(cal.getTime());
				pp.setOperator(new BigDecimal(-1));
				pp.setOpTime(new Date());
				pp.setStaffTypeId(staffType.getStaffTypeId());
				pp.setOrgId(staffType.getOrgId());

				regDao.addProductPrice(pp);
			}
		}

		return result;
	}

	/**
	 * 添加院方用户信息
	 * 
	 * @param u
	 */
	public void addUser4Org(AmsUser u) {
		User4OrgQJ userOrg = new User4OrgQJ();
		userOrg.setBirth(u.getBirth());
		userOrg.setHomeAddr(u.getHomeAddr());
		userOrg.setIdCard(u.getIdCard());
		userOrg.setMobile(u.getMobile());
		userOrg.setSex(u.getSex().toString());
		userOrg.setUserId(u.getUserId().toString());
		userOrg.setUserName(u.getUserName());
	}

	@HttpMethod
	@Override
	public List<PatientInfo> getFriendByStaffId(@NotNull String staffId,
			String userName, Long startRow, Long rows) {
		if (startRow == null)
			startRow = 0L;
		if (rows == null)
			rows = 20L;
		if (startRow != null && rows != null) {
			rows = startRow + rows;
		}
		return regDao.getFriendByStaffId(staffId, userName, startRow, rows);
	}

	@HttpMethod
	@Override
	public Map<String, Object> getMcRegInfos(@NotNull String staffId,
			String date) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 1.获取当前时间的挂号总数
		long mcRegCount = regDao.getCountMcReg(staffId, date);// 挂号总数

		// 计算当前挂号数
		result.put("regCount", mcRegCount);
		// 2.获取查询 挂号信息集 McRegInfo
		List<McRegInfo> infos = regDao.getMcRegInfos(staffId, date);
		result.put("retData", infos);

		// 计算 1 已就诊(hasBeenTreated)， 4是爽约(hooky)
		int hasBeenTreated = 0, hooky = 0;
		// IS_USED '0':'待就医' '1':'已就诊' '2':'已退号' 3(其他):'已爽约'
		for (McRegInfo info : infos) {
			if ("1".equals(info.getIsUsed())) {
				hasBeenTreated++;
			} else if ("3".equals(info.getIsUsed())) {
				hooky++;
			}
		}
		result.put("hasBeenTreated", hasBeenTreated);
		result.put("hooky", hooky);
		return result;
	}

	@HttpMethod
	@Override
	public Map<String, Object> searchMcRegs(@NotNull String staffId,
			String monthStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		String currentDate = null;
		if (StringUtil.isEmpty(monthStr)) {
			monthStr = UtilDate.getMonth(); // 获取当前年月
			currentDate = UtilDate.getCurrentDate();// 获取当前时间
		} else {
			currentDate = monthStr + "-01";
		}
		// 这里如果是传入的当前月分则需要显示当前月份的当前时间
		if (monthStr.equals(UtilDate.getMonth())) {
			currentDate = UtilDate.getCurrentDate();// 获取当前时间
		}

		// 查询出日期 排班 情况
		map.put("mcRegDates", regDao.searchMcRegs(staffId, monthStr));
		// 根据当前时间 查询挂号信息集
		map.put("mcRegs", this.getMcRegInfos(staffId, currentDate));
		return map;
	}

	@HttpMethod
	@Override
	public Map<String, Object> getDutyByDateAndStaffId(@NotNull String staffId,
			String date, String type, String queryType) {
		if (StringUtil.isEmpty(date)) {
			date = UtilDate.getCurrentDate(); // UtilDate.getMonth()+"-01"
		}
		if (StringUtil.isEmpty(type)) {
			type = "list";// type 类型标示日期查询方式：list列表查询，detailed详细查询
		}

		List<ScheduleDutyInfo> infos = null;// 1查询出当前排班日程
		List<UserSchedule> schedules = null;// 2查询自定义日程
		List<SurveySchduleDto> surveySchdules = null;// 查询随访计划
		if (StringUtil.isEmpty(queryType)) {
			// 1查询出当前排班日程
			infos = regDao.getDutyByDateAndStaffId(staffId, date, type);

		} else {
			/* 3查询随访计划-重医计划 修改时间2015-08-24 增加随访计划查询 */
			surveySchdules = surveyDao.getSurveySchduleByStaffId(staffId, null,
					date, type);
		}
		// 2查询自定义日程
		Staff staff = staffDao.getStaffById(staffId);
		if (staff != null && staff.getUserId() != null) {
			schedules = userScheduleService.getUserScheduleByUserIdAndDate(
					staff.getUserId().toString(), date, type);
		}

		// 封装数据
		// 得到排班日期
		Set<String> dutyDates = new TreeSet<String>();
		// 日期合并
		if (infos != null) {
			for (ScheduleDutyInfo info : infos) {
				// 封装 日期合并
				dutyDates.add(UtilDate.getDateToStr(info.getDutyDate(),
						UtilDate.dtDate));
			}
		}
		if (schedules != null) {
			for (UserSchedule usd : schedules) {
				// 封装 日期合并
				dutyDates.add(UtilDate.getDateToStr(usd.getBeginTime(),
						UtilDate.dtDate));
			}
		}
		if (surveySchdules != null) {
			for (SurveySchduleDto ssd : surveySchdules) {
				// 封装 日期合并
				dutyDates.add(UtilDate.getDateToStr(ssd.getDate(),
						UtilDate.dtDate));
			}
		}

		TreeMap<String, Object> retMap = new TreeMap<String, Object>();// 结果集返回
		List<Object> list = null;// 封装自定义日程列表
		for (String dateStr : dutyDates) {

			list = new ArrayList<Object>();
			// 排班日程
			if (infos != null) {
				for (ScheduleDutyInfo info : infos) {
					if (dateStr.equals((UtilDate.getDateToStr(
							info.getDutyDate(), UtilDate.dtDate)))) {
						list.add(info);
					}
				}
			}
			// 随访计划 -- 重医需求
			if (surveySchdules != null) {
				for (SurveySchduleDto ssd : surveySchdules) {
					if (dateStr.equals(UtilDate.getDateToStr(ssd.getDate(),
							UtilDate.dtDate))) {
						list.add(ssd);
					}
				}
			}

			// 自定义日程
			if (schedules != null) {
				for (UserSchedule us : schedules) {
					if (dateStr.equals(UtilDate.getDateToStr(us.getBeginTime(),
							UtilDate.dtDate))) {
						list.add(us);
					}
				}
			}

			retMap.put(dateStr, list);
		}
		return retMap;
	}

	@HttpMethod
	@Override
	public Map<String, Object> getScheduleDutys(@NotNull String staffId,
			String monthStr, String queryType) {
		Map<String, Object> map = new HashMap<String, Object>();
		String date = null; // yyyy-mm-dd
		if (StringUtil.isEmpty(monthStr)) {
			monthStr = UtilDate.getMonth();
			date = UtilDate.getCurrentDate();// 当前时间
		} else {
			date = monthStr.equals(UtilDate.getMonth()) ? UtilDate
					.getCurrentDate() : monthStr + "-01";
		}

		// 用于存放 有日程的日期信息
		Set<String> dutyDates = new TreeSet<String>();
		List<String> dates = null;
		// 为Null 则表示需要查询 排队 和 自定义日程
		if (StringUtil.isEmpty(queryType)) {
			// 获取医生 排班日程 日期集
			dates = regDao.getScheduleDutys(staffId, monthStr);
			for (String str : dates) {
				dutyDates.add(str);// 注意这里的日期格式是 yyyy-mm-dd
			}
		} else {
			// 不为Null 则表示需要查询随访
			// 获取随访日程 日期信息
			dates = surveyDao.getSurveyScheduleDates(staffId, monthStr);
			for (String str : dates) {
				dutyDates.add(str);// 注意这里的日期格式是 yyyy-mm-dd
			}
		}
		// 获取自定义日程 日期信息
		dates = userScheduleService.getScheduleDates(staffId, monthStr);
		for (String str : dates) {
			dutyDates.add(str);// 注意这里的日期格式是 yyyy-mm-dd
		}
		// 获取预警 日期信息
		Staff staff = staffDao.getStaffById(staffId);
		dates = systemDao.getWarnMsgDates(staff.getUserId().toString(), date);
		for (String str : dates) {
			dutyDates.add(str);// 注意这里的日期格式是 yyyy-mm-dd
		}
		// 日程 日历信息
		map.put("calendars", dutyDates);
		// 查询出当前月 中 当前时间的日程信息
		map.put("schedules", this.getDutyByDateAndStaffId(staffId, date,
				"detailed", queryType));

		return map;
	}

	/**
	 * 获取支付方式
	 * 
	 * @return
	 */
	@HttpMethod
	public List<PayWay> getPayways() {
		List<PayWay> payWays = regDao.getOnPayWay();
		return payWays;
	}

	/**
	 * 挂号医院同步接口
	 */
	public HashMap<String, Object> synchronizedToOrg_Reg(UserFriend userFriend,
			AmsUser user, String hospitalUrl, McReg reg, McDuty duty,Integer isBund) {
		Org org = regDao.getOrgById(reg.getOrgId().toString());
		if (org.getOrgCode() != null
				&& org.getOrgCode().equalsIgnoreCase("qjrmyy")) {
			// 綦江医院流程
			User4OrgQJ userOrg = new User4OrgQJ();
			userOrg.setBirth(userFriend.getUserBirth());
			userOrg.setHomeAddr(userFriend.getUserAddr());
			userOrg.setIdCard(userFriend.getUserIdcard());
			userOrg.setMobile(userFriend.getMobile());
			userOrg.setSex(userFriend.getUserSex());
			userOrg.setUserId(userFriend.getUserId().toString());
			userOrg.setUserName(userFriend.getUserName());

			Reg4OrgQJ reg4Org = new Reg4OrgQJ();
			reg4Org.setCreateTime(UtilDate.getDateToStr(reg.getCreateTime(),
					UtilDate.dtDate));
			reg4Org.setDutyUuid(duty.getUuid());
			reg4Org.setIdCard(userFriend.getUserIdcard());
			reg4Org.setOrgId(duty.getOrgId());
			reg4Org.setPeriodUniqueId(reg.getPeriodUniqueId());
			reg4Org.setRegNumber(reg.getRegNumber());
			reg4Org.setRegUniqueId(reg.getRegUniqueId());
			reg4Org.setUserAddr(userFriend.getUserAddr());
			reg4Org.setUserBirth(userFriend.getUserBirth());
			reg4Org.setUserId(userFriend.getFriendId());
			reg4Org.setUserName(userFriend.getUserName());
			reg4Org.setUserPhone(userFriend.getMobile());
			reg4Org.setUserSex(userFriend.getUserSex());

			HashMap<String, Object> result = new HashMap<String, Object>();
			try {
				String str = HttpUtil
						.sendPost(
								hospitalUrl,
								"{param:\""
										+ DesUtil.encrypt("{calltype:\"4\",\"serviceName\":\"AP0001\",\"transParams\":"
												+ JSONObject
														.fromObject(userOrg)
												+ "}") + "\"}");
				result = JsonUtil.toHashMap(str);
				if (result.get("retCode").toString().equals("0")) {
					try {
						// 添加成功
						str = HttpUtil
								.sendPost(
										hospitalUrl,
										"{param:\""
												+ DesUtil.encrypt("{calltype:\"4\",\"serviceName\":\"AP0002\",\"transParams\":"
														+ JSONObject
																.fromObject(reg4Org)
														+ "}") + "\"}");
					} catch (IOException e) {
						result.put("retCode", 5);
						result.put("retMessage", "医院端接口调用超时");
						return result;
					}
					result = JsonUtil.toHashMap(str);
					if (result.get("retCode").toString().equals("0")) {
						// 添加成功
						result.put("retCode", 0);
						result.put("retMessage", "医院通知成功");
					} else {
						result.put("retCode", 4);
						result.put("retMessage", "添加挂号记录到医院失败");
					}
				} else {
					result.put("retCode", 3);
					result.put("retMessage", "添加用户到医院失败");
				}

			} catch (IOException e) {
				result.put("retCode", 5);
				result.put("retMessage", "医院端接口调用超时");
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				result.put("retCode", 6);
				result.put("retMessage", "未知异常");
				return result;
			}
			return result;
		} else {
			// 标准医院流程
			User4Org userOrg = new User4Org();
			userOrg.setBirth(user.getBirth());
			userOrg.setHomeAddr(user.getHomeAddr());
			userOrg.setIdCard(user.getIdCard());
			userOrg.setPhone(user.getMobile());
			userOrg.setSex(user.getSex() == null ? null : user.getSex()
					.toString());
			userOrg.setUserId(user.getUserId().toString());
			userOrg.setUserName(user.getUserName());

			Reg4Org reg4Org = new Reg4Org();
			reg4Org.setAddress(userFriend.getUserAddr());
			reg4Org.setBirth(userFriend.getUserBirth());
			List<McCard> cards = userFriend.getCards();
			for (McCard c : cards) {
				if (c.getCardTypeName() != null
						&& c.getCardTypeName().equals("就诊卡")) {
					reg4Org.setCardCode(c.getCardNo());
				} else if (c.getCardTypeName() != null
						&& c.getCardTypeName().equals("医保卡")) {
					reg4Org.setSafetyCode(c.getCardNo());
				}
			}
			reg4Org.setDutyId(duty.getUuid());
			McOrderDet orderDet = regDao.getOrderDetByRegId(reg.getRegId()
					.toString());
			reg4Org.setFlowNo(orderDet.getOrderId().toString());
			reg4Org.setFriendId(userFriend.getFriendId().toString());
			reg4Org.setIdCard(userFriend.getUserIdcard());
			reg4Org.setNation(null);
			reg4Org.setNativeHeath(null);
			PayWay payWay = orderDao
					.getPayWayById(reg.getPayWayId().toString());
			// A0 支付宝 A1 银行卡 A2 微信 A3 信用卡 A4 APP预交金 A5 其他
			if (payWay.getPayWayCode().equals("ZFB")) {
				reg4Org.setPayWay("A0");
			} else if (payWay.getPayWayCode().equals("YHK")) {
				reg4Org.setPayWay("A1");
			} else if (payWay.getPayWayCode().equals("WXZF")) {
				reg4Org.setPayWay("A2");
			} else if (payWay.getPayWayCode().equals("XYK")) {
				reg4Org.setPayWay("A3");
			} else if (payWay.getPayWayCode().equals("YJJ")) {
				reg4Org.setPayWay("A4");
			} else {
				reg4Org.setPayWay("A5");
			}
			reg4Org.setPhone(userFriend.getMobile());
			reg4Org.setSex(userFriend.getUserSex());
			reg4Org.setTypeName("自费");
			// reg4Org.setUnitName(unitName);
			reg4Org.setUserId(user.getUserId().toString());
			reg4Org.setUserName(userFriend.getUserName());
			reg4Org.setUuid(reg.getRegUniqueId());
			reg4Org.setPatientId(reg.getHisUserId());
			HashMap<String, Object> result = new HashMap<String, Object>();

			try {
				HashMap<String, Object> gparam = new HashMap<String, Object>();
				// gparam.put("userId", user.getUserId());
				gparam.put("userID", "zskq2015");
				gparam.put("calltype", "4");
				gparam.put("deviceToken", "zskq2015-YUYOU");
				gparam.put("transParams", userOrg);
				String str = HttpUtil.sendPost(
						hospitalUrl + "module=userService&method=addUser",
						"{param:\""
								+ DesUtil.encrypt(JSONObject.fromObject(gparam)
										.toString()) + "\"}");
				log.info("添加用户医院返回数据");
				log.info(str);
				result = JsonUtil.toHashMap(str);
				if (result.get("retCode").toString().equals("0")) {
					try {
						gparam.put("transParams", reg4Org);
						// 添加成功
						str = HttpUtil
								.sendPost(
										hospitalUrl
												+ "module=regService&method=addRegistration",
										"{param:\""
												+ DesUtil.encrypt(JSONObject
														.fromObject(gparam)
														.toString()) + "\"}");
						log.info("医院返回数据：");
						log.info(result);
					} catch (IOException e) {
						e.printStackTrace();
						result.put("retCode", 5);
						result.put("retMessage", "医院端接口调用超时");
						return result;
					}
					result.clear();
					result = JsonUtil.toHashMap(str);

					if ((result.get("retCode").toString().equals("0") || result
							.get("retCode").toString().equals("0000"))
							&& result.containsKey("result")) {
						String resultDec = DesUtil.decrypt(result.get("result")
								.toString());
						log.info(resultDec);
						HashMap<String, Object> resultMap = JsonUtil
								.toHashMap(resultDec);
						JSONObject obj = (JSONObject) resultMap.get("result");
						log.info("===================");
						log.info(obj.containsKey("retCode"));
						log.info("retCode=====" + obj.get("retCode").toString());

						String retCode = obj.get("retCode").toString();
						if (obj.containsKey("retCode")
								&& (obj.get("retCode").toString().equals("0") || obj
										.get("retCode").toString()
										.equals("0000"))) {
							log.info(result);

							obj = (JSONObject) obj.get("registration");
							result.put("patientId", obj.get("patientId"));
							result.put("visitNo", obj.get("visitNo"));

							// // 添加成功
							if (retCode.equals("0000")) {
								result.put("retCode", "0000");
							} else {
								result.put("retCode", "0");
							}

							result.put("retMessage", "医院通知成功");
							log.info(result);
						} else {
							result.put("retCode", 4);
							result.put("retMessage", "添加挂号记录到医院失败");
						}
					} else {
						result.put("retCode", 4);
						result.put("retMessage", "添加挂号记录到医院失败");
					}
				} else {
					result.put("retCode", 3);
					result.put("retMessage", "添加用户到医院失败");
				}

			} catch (IOException e) {

				result.put("retCode", 5);
				result.put("retMessage", "医院端接口调用超时");
				log.info("挂号retCode=5的异常：" + e.getMessage());
				e.printStackTrace();
				return result;
			} catch (Exception e) {

				result.put("retCode", 6);
				result.put("retMessage", "未知异常");
				log.info("挂号retCode=6的异常：" + e.getMessage());
				e.printStackTrace();
				return result;
			}
			return result;
		}
	}

	// 走异步接口
	public HashMap<String, Object> asynchronousToOrg_Reg(UserFriend user,
			String orgCode) {

		return null;
	}

	/**
	 * 挂号医院同步接口
	 */
	public HashMap<String, Object> synchronizedToOrg_UnReg(String hospitalUrl,
			AmsUser user, Date cancelTime, McReg reg) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Org org = regDao.getOrgById(reg.getOrgId().toString());

		if (org.getOrgCode() != null
				&& org.getOrgCode().equalsIgnoreCase("qjrmyy")) {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("username", user.getUserName());
			param.put("cancelTime",
					UtilDate.getDateToStr(cancelTime, UtilDate.simple));
			param.put("regUniqueId", reg.getRegUniqueId());
			try {
				String str = HttpUtil
						.sendPost(
								hospitalUrl,
								"{param:\""
										+ DesUtil.encrypt("{calltype:\"4\",\"serviceName\":\"AP0003\",\"transParams\":"
												+ JSONObject.fromObject(param)
												+ "}") + "\"}");
				result = JsonUtil.toHashMap(str);
				if (result.get("retCode").toString().equals("0")) {
					// 退号成功
					result.put("retCode", 0);
					result.put("retMessage", "退号成功");
				} else {
					result.put("retCode", 3);
					result.put("retMessage", "添加用户到医院失败");
				}

			} catch (IOException e) {
				result.put("retCode", 5);
				result.put("retMessage", "医院端接口调用失败");
				log.info("退号retCode=5的异常：" + e.getMessage());
				e.printStackTrace();
				return result;
			} catch (Exception e) {
				result.put("retCode", 6);
				result.put("retMessage", "未知异常");
				log.info("退号retCode=6的异常：" + e.getMessage());
				e.printStackTrace();
				return result;
			}
		} else {
			HashMap<String, Object> gparam = new HashMap<String, Object>();
			gparam.put("userID", "zskq2015");
			gparam.put("calltype", "4");
			gparam.put("deviceToken", "zskq2015-YUYOU");

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("uuid", reg.getRegUniqueId());
			McOrder order = regDao.getOrderByRegId(reg.getRegId().toString());
			param.put("flowNo", "TH" + order.getOrderId());
			gparam.put("transParams", param);
			try {
				String str = HttpUtil.sendPost(
						hospitalUrl
								+ "module=regService&method=backRegistration",
						"{param:\""
								+ DesUtil.encrypt(JSONObject.fromObject(gparam)
										.toString()) + "\"}");
				result = JsonUtil.toHashMap(str);
				if (result.get("retCode").toString().equals("0")) {
					// 退号成功
					result.put("retCode", 0);
					result.put("retMessage", "退号成功");
				} else {
					result.put("retCode", 3);
					result.put("retMessage", "添加用户到医院失败");
				}

			} catch (IOException e) {

				result.put("retCode", 5);
				result.put("retMessage", "医院端接口调用失败");
				log.info("退号retCode=5的异常：" + e.getMessage());
				e.printStackTrace();
				return result;
			} catch (Exception e) {

				result.put("retCode", 6);
				result.put("retMessage", "未知异常");
				log.info("退号retCode=6的异常：" + e.getMessage());
				e.printStackTrace();
				return result;
			}
		}

		return result;
	}

	@HttpMethod
	@Override
	public McReg getMcRegById(@NotNull String regId, String orderStatus) {
		McReg mcReg = regDao.getMcRegById(regId, orderStatus);
		Staff staff = staffDao.getStaffInfo(mcReg.getStaffId().toString());
		if (staff != null && !StringUtil.isEmpty(staff.getStaffIcon())) {
			mcReg.setStaffIcon(staff.getStaffIcon());
		}
		return mcReg;
	}

	@HttpMethod
	@Override
	public McReg getMcRegSuccessById(@NotNull String regId) {
		return regDao.getMcRegById(regId, "5");
	}

	@HttpMethod
	@Override
	public McReg getRegByPayNo(@NotNull String payNo) {
		return regDao.getRegByPayNo(payNo);
	}

	@HttpMethod
	@Override
	public Map<String, Object> myReservation(@NotNull String userId,
			@NotNull String type, Long startRow, Long rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("type", type);
		// 预约记录需要分页
		if ("1".equals(type)) {
			if (startRow == null)
				startRow = 0L;
			if (rows == null)
				rows = 10L;
			map.put("startRow", startRow);
			map.put("endRow", startRow + rows);
		}
		result.put("reservations", regDao.myReservation(map));
		result.put("count", regDao.myReservationCount(map));

		return result;
	}

	@HttpMethod
	@Override
	public ReservationDetails reservationDetails(@NotNull String regId) {
		return regDao.reservationDetails(regId);
	}
	
	@HttpMethod
	@Override
	public String checkRegistration(@ParamNoNull String orgId,String friendId){
//		Org org = orgDao.getOrgInfo(orgId);
//		
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("idCard", idCard);
//		params.put("userName",userName);
//		params.put("cardCode",cardCode);
//		params.put("safetyCode",safetyCode);
//		
//		String result = RemotUtils.getRemotData(org.getHospitalUrl(), "module=regService&method=checkRegistration", params);
//		
//		log.info("挂号前验证是否之前有患者ID:"+result);
		
//		return result;
		return null;
	}
}
