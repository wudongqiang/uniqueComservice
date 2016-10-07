package com.unique.system.service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sun.istack.internal.NotNull;
import com.unique.alipay.util.UtilDate;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.util.FileUtil;
import com.unique.core.util.HttpUtil;
import com.unique.core.util.JsonUtil;
import com.unique.core.util.RedisKeys;
import com.unique.core.util.StringUtil;
import com.unique.order.dao.OrderDao;
import com.unique.order.po.McOrder;
import com.unique.order.po.McOrderDet;
import com.unique.order.po.McOrderType;
import com.unique.order.po.ProductType;
import com.unique.order.po.SourceType;
import com.unique.org.dao.OrgDao;
import com.unique.reg.dao.RegDao;
import com.unique.reg.po.McReg;
import com.unique.reg.po.Org;
import com.unique.system.dao.SystemDao;
import com.unique.system.po.ImMsgInfo;
import com.unique.system.po.UserClientType;
import com.unique.system.po.VersionConfig;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

@Service("systemService")
public class SystemServiceImpl implements SystemService {
	public static Logger log = Logger.getLogger("comservice");
	public static Logger orderLog = Logger.getLogger("order");
	@Resource
	private SystemDao systemDao;
	@Resource
	private RegDao regDao;

	@Resource
	private OrderDao orderDao;
	@Resource
	private OrgDao orgDao;

	public static final JSONObject PUSH_KEY = JSONObject.fromObject(FileUtil
			.readProperties("comservice.properties", "pushkey"));
	// /图片服务器
	private static String picServerOut = FileUtil.readProperties(
			"comservice.properties", "picServerOut");

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");
	@Resource
	private StringRedisTemplate redisTemplate;

	// /微信推送地址
	private static String WX_PUSH = FileUtil.readProperties(
			"comservice.properties", "wxpush");
	private String sms = FileUtil
			.readProperties("comservice.properties", "sms");

	/**
	 * 获取最新版本号
	 * 
	 * @param typeId
	 * @return
	 */
	@HttpMethod
	public VersionConfig getVersion(@NotNull String typeId) {
		VersionConfig v = systemDao.getVersion(typeId);
		String spliter = "";
		if (v != null) {
			if (!picServerOut.endsWith("/") && !v.getVerAddr().startsWith("/")) {
				spliter = "/";
			}
			if (!v.getVerAddr().startsWith("https://")) {
				v.setVerAddr(picServerOut + spliter + v.getVerAddr());
			}
		}
		return v;
	}

	/**
	 * 根据位置编码获取广告内容
	 * 
	 * @param code
	 *            位置编码
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getAdContents(String code) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("ad", systemDao.getAdByPosition(code));
		result.put("serverpath", picServerOut);
		return result;
	}

	/**
	 * 发送推送消息
	 * 
	 * @param deviceTokens
	 *            要发送用户设备列表
	 * @param system
	 *            :0 IOS;1 android;2 IOS and Android
	 * @param projectCode
	 *            ZJK 找健康用户端；ZJK_DOCTOR 找健康医生端
	 * @param isAll
	 *            是否发送所有用户
	 * @param msgTypeCode
	 * @param msgContent
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	@HttpMethod
	public HashMap<String, Object> pushMsg(String[] uctIds, String msgContent,
			Map<String, String> extra) throws APIConnectionException,
			APIRequestException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		if (uctIds != null && uctIds.length > 0) {
			List<UserClientType> clientTypes = systemDao
					.getClientTypeByUcts(uctIds);
			// 确认推送系统类型
			String pCode = clientTypes.get(0).getProjectCode();
			log.info(pCode);
			// 确认系统类别
			String ClientTypeCode = clientTypes.get(0).getClientTypeCode();

			// 取出推送唯一码
			List<String> pushUUIDs = new ArrayList<String>();
			for (UserClientType clientType : clientTypes) {
				if (clientType.getPushCode() != null) {
					// 找到推送KEY
					pushUUIDs.add(clientType.getPushCode());
				}
			}

			PushResult pr = null;
			String[] uuidArray = new String[pushUUIDs.size()];
			for (int i = 0; i < pushUUIDs.size(); i++) {
				uuidArray[i] = (String) pushUUIDs.get(i);
			}

			JSONObject obj = (JSONObject) PUSH_KEY.get(pCode);
			if (obj == null) {
				result.put("retCode", 3);
				result.put("retMessage", "改产品不支持推送");
				return null;
			}
			JPushClient jpush = new JPushClient(obj.getString("secret"),
					obj.getString("appkey"));
			log.info("phoneType:" + ClientTypeCode + ",pCode:" + pCode);
			for (String uuid : uuidArray) {
				log.info("pushCode:" + uuid);
			}
			log.info("push end");
			try {
				if (ClientTypeCode.toString().indexOf("IOS") >= 0) {
					pr = jpush.sendIosNotificationWithAlias(msgContent, extra,
							uuidArray);
				} else {
					pr = jpush.sendAndroidNotificationWithAlias(null,
							msgContent, extra, uuidArray);
				}
			} catch (Exception e) {
				result.put("retMessage", "部分推送失败或全部失败");
			}

			if (pr != null) {
				result.put("type", pr.isResultOK());
				result.put("频率次数", pr.getRateLimitQuota());
				result.put("可用频率", pr.getRateLimitRemaining());
				result.put("重置时间", pr.getRateLimitReset());
			}
		} else {
			result.put("retCode", 3);
			result.put("retMessage", "没有可用推送设备");
		}
		return result;
	}

	@Transactional
	public int addOrUpdateUserClient(UserClientType userClient) {
		UserClientType oldUserClient = systemDao.getPushCode(userClient
				.getDeviceToken(), userClient.getClientTypeId().toString());
		if (oldUserClient == null) {
			return systemDao.addUserClient(userClient);
		} else {
			userClient.setUctId(oldUserClient.getUctId());
			return systemDao.updateUserClient(userClient);
		}
	}

	/**
	 * 添加IM发送的消息 创建人:余宁 修改人:余宁 创建日期:2015年8月13日下午2:50:04 修改日期:2015年8月13日下午2:50:04
	 * 
	 * @param msgContent
	 *            消息内容
	 * @param contentType
	 *            消息类型 1 文字，2 图片，3 语音， 4 回访
	 * @param url
	 *            附件URL
	 * @param fromUser
	 *            发送者ID
	 * @param toUser
	 *            接收者ID
	 * @param sendTime
	 *            发送时间
	 */
	@HttpMethod
	public void addIMMsgInfo(String msgContent, @NotNull String contentType,
			String url, String fromUser, String toUser, String sendTime) {
		// 封装消息对象,存redis
		ImMsgInfo info = new ImMsgInfo();
		info.setContent(msgContent);
		info.setContentType(contentType);
		if (!StringUtil.isEmpty(fromUser)) {
			info.setFromUser(new BigDecimal(fromUser));
		}
		if (!StringUtil.isEmpty(toUser)) {
			info.setToUser(new BigDecimal(toUser));
		}
		if (sendTime == null) {
			info.setSendTime(new Date());
		} else {
			try {
				info.setSendTime(sdf.parse(sendTime));
			} catch (ParseException e) {
				info.setSendTime(new Date());
			}
		}
		info.setStats("1");
		info.setUrl(url);

		// 加入redis
		redisTemplate.opsForList().rightPush(RedisKeys.IM_MSG_INFO,
				JSONObject.fromObject(info).toString());
	}

	/**
	 * 定时计划，添加IM消息到数据库 创建人:余宁 修改人:余宁 创建日期:2015年8月13日下午3:26:57
	 * 修改日期:2015年8月13日下午3:26:57
	 */
	@Transactional
	public void addImMsgFromRedis() {
		Logger.getRootLogger().info("[IM数据转存开始]");
		// 取出消息
		List<ImMsgInfo> infos = new ArrayList<ImMsgInfo>();
		// 缓存清空并转移数据库表
		while (redisTemplate.opsForList().size(RedisKeys.IM_MSG_INFO) == 0) {
			String logJsonOld = redisTemplate.opsForList().leftPop(
					RedisKeys.IM_MSG_INFO);
			JSONUtils.getMorpherRegistry().registerMorpher(
					new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss" }));
			ImMsgInfo info = (ImMsgInfo) JSONObject.toBean(
					JSONObject.fromObject(logJsonOld, JsonUtil.getConfig()),
					ImMsgInfo.class);
			infos.add(info);
		}
		systemDao.addImMsgs(infos);
	}

	/**
	 * 获取警告消息列表 创建人:余宁 修改人:余宁 创建日期:2015年10月15日上午11:14:55
	 * 修改日期:2015年10月15日上午11:14:55
	 * 
	 * @param userId
	 * @param startRow
	 * @param rows
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getWarnMsg(@NotNull String userId,
			String dateStr, Long startRow, Long rows) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		if (StringUtil.isEmpty(dateStr)) {
			dateStr = UtilDate.getCurrentDate(); // 默认当前时间
		}

		long count = systemDao.getWarnMsgCount(userId, dateStr);
		List<ImMsgInfo> msgs = systemDao.getWarnMsg(userId, dateStr, startRow,
				rows);
		result.put("count", count);
		result.put("list", msgs);
		return result;
	}

	/**
	 * 推送订单消息 创建人:余宁 修改人:余宁 创建日期:2015年11月4日下午3:27:38 修改日期:2015年11月4日下午3:27:38
	 * 
	 * @param order
	 *            订单
	 * @param orderDet
	 *            订单明细
	 * @param type
	 *            1下单，2退单
	 */
	public void sendOrderMsg(McOrder order, McOrderDet orderDet, int type) {
		orderLog.info("进入消息推送环节...type=" + type);
		McReg reg = getRegByOrder(order.getOrderId().toString());

		SourceType sourceType = null;
		if (order.getSourceId() != null) {
			sourceType = orderDao.getSourceById(order.getSourceId().toString());
		}

		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> msg = new HashMap<String, Object>();
		if (type == 1) {
			orderLog.info("进入消息推送环节...下单");
			/********************* 下单成功 ***********************/
			ProductType ptype = orderDao.getProductTypeById(orderDet
					.getProductTypeId().toString());
			Org org = orgDao.getOrgInfo(orderDet.getOrgId().toString());
			// 挂号
			orderLog.info(ptype.getCode() + "===="
					+ (reg == null ? "不是挂号" : reg.getIsUsed()));
			if (reg != null && reg.getIsUsed() != null
					&& reg.getIsUsed().equals("0")) {
				msg.put("objectValue", reg.getAppointCode());
				msg.put("cardType", orderDet.getCardType());
				msg.put("hisUserId", reg.getHisUserId());// 患者id
				msg.put("icCardId",
						reg.getIcCardId() == null ? "" : reg.getIcCardId());// 就诊卡号
				msg.put("callNo",
						reg.getCallNo() == null ? "" : reg.getCallNo());// 排队序号

				msg.put("orgName", reg.getOrgName());
				msg.put("depName", reg.getDepName());
				msg.put("staffName", reg.getStaffName());
				msg.put("contactPhone", org.getContactPhone());
				msg.put("orgAddr", org.getOrgAddr());
				msg.put("userName", reg.getUserName());
				msg.put("userSex", reg.getUserSex());
				msg.put("userPhone", reg.getUserPhone());
				// msg.put("orderTime", new
				// SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(reg.getOrderTime()));
				msg.put("orderTime",
						new SimpleDateFormat("yyyy-MM-dd").format(reg
								.getOrderTime()) + " " + reg.getPeriodName());
				msg.put("regNumber", reg.getRegNumber());
				msg.put("amount", reg.getAmount());
				msg.put("remark", "请准时就诊。");
				msg.put("orderNo", order.getOrderNo());
				msg.put("orderId", order.getOrderId());

				param.put("userId", reg.getUserId());
				param.put("type", "reg");
				param.put("msg", msg);

				sendMessage(param);

			} else {
				// 其他的方式，微信需要发送推送
				msg.put("objectValue", orderDet.getPickCode());
				msg.put("cardType", orderDet.getCardType());
				msg.put("amount", order.getOrderMoney());

				msg.put("hisUserId", reg.getHisUserId());// 患者id
				// msg.put("icCardId",reg.getIcCardId());//就诊卡号

				msg.put("orgName", org.getOrgName());
				msg.put("contactPhone", org.getContactPhone());
				msg.put("orgAddr", org.getOrgAddr());
				msg.put("userName", orderDet.getUserName());
				msg.put("userSex", orderDet.getSex());
				msg.put("orderTime",
						new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
								.format(order.getCreateTime()));
				msg.put("remark", orderDet.getProductName() + "购买成功");
				msg.put("orderNo", order.getOrderNo());
				msg.put("orderId", order.getOrderId());

				param.put("userId", order.getUserId());
				if (ptype.getCode().equalsIgnoreCase("HYK")) {
					param.put("type", "VIP_CARD");
				} else if (ptype.getCode().toUpperCase().equals("ZJJF")) {
					param.put("type", "ZJ_PAY");
				}
				param.put("msg", msg);
			}
		} else if (type == 2) {
			/********************* 退单成功 ***********************/

			if (reg != null) {
				// 如果状态为可取号
				Org org = orgDao.getOrgInfo(reg.getOrgId().toString());
				List<McOrderDet> orderDets = orderDao
						.getOrderDetByOrderId(order.getOrderId().toString());
				// 微信需要发送推送
				msg.put("msgType", "regUnOrder");
				msg.put("objectValue", reg.getAppointCode());
				msg.put("cardType", orderDets.get(0).getCardType());
				msg.put("orgName", reg.getOrgName());
				msg.put("depName", reg.getDepName());
				msg.put("staffName", reg.getStaffName());
				msg.put("contactPhone", org.getContactPhone());
				msg.put("orgAddr", org.getOrgAddr());
				msg.put("userName", reg.getUserName());
				msg.put("userSex", reg.getUserSex());
				msg.put("orderTime",
						new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(reg
								.getOrderTime()));
				msg.put("regNumber", reg.getRegNumber());
				msg.put("amount", reg.getAmount());
				msg.put("remark", "您的退号已成功");
				msg.put("orderNo", order.getOrderNo());
				msg.put("orderId", order.getOrderId());

				param.put("cardType", reg.getCardTypeName());
				param.put("userId", reg.getUserId());
				param.put("type", "unOrder");
				param.put("msg", msg);
			} else {
				// 非挂号订单
				Org org = orgDao.getOrgInfo(orderDet.getOrgId().toString());
				ProductType ptype = orderDao.getProductTypeById(orderDet
						.getProductTypeId().toString());

				if (ptype.getCode().toUpperCase().equals("HYK")) {
					msg.put("msgType", "vipCardUnOrder");
				} else if (ptype.getCode().toUpperCase().equals("ZJJF")) {
					msg.put("msgType", "zjPayUnOrder");
				}
				msg.put("objectValue", orderDet.getPickCode());
				msg.put("cardType", orderDet.getCardType());
				msg.put("amount", order.getOrderMoney());

				msg.put("orgName", org.getOrgName());
				msg.put("contactPhone", org.getContactPhone());
				msg.put("orgAddr", org.getOrgAddr());
				msg.put("userName", orderDet.getUserName());
				msg.put("userSex", orderDet.getSex());
				msg.put("orderTime",
						new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
								.format(order.getCreateTime()));
				msg.put("remark", "退单成功");
				msg.put("orderNo", order.getOrderNo());
				msg.put("orderId", order.getOrderId());

				param.put("userId", order.getUserId());
				param.put("type", "unOrder");
				param.put("msg", msg);
			}
		}

		/*********************
		 * 微信推送
		 * ***********************/
		if (sourceType != null
				&& "WX".equalsIgnoreCase(sourceType.getSourceCode())) {
			orderLog.info(JSONObject.fromObject(param).toString());
			HttpUtil.sendPost(WX_PUSH, JSONObject.fromObject(param).toString());
		}
	}

	/**
	 * 发送短信
	 * 
	 * @param param
	 */
	@SuppressWarnings("unchecked")
	private void sendMessage(Map<String, Object> param) {
		try {
			/*
			 * 【掌上口腔】预约,{userName} {sexStr}
			 * {cardType}预约挂号成功,门诊挂号号:{objectValue},
			 * 医院:{orgName},科室:#{depName}{staffName},时间：{orderTime},
			 * 请携带{cardType}原件和就诊ID前往医院收费处取号
			 */
			String content = "{userName} 预约挂号成功,门诊号:{objectValue},"
					+ "患者ID:{hisUserId},就诊卡号:{icCardId},排队序号:{callNo},"
					+ "{orgName}/{depName}/{staffName},时间：{orderTime},"
					+ "请携带{cardType}原件到分诊处分诊.(注:如果您的就诊卡号为:S100***,请到收费处租卡取号)";
			Map<String, Object> msg = (Map<String, Object>) param.get("msg");
			content = content
					.replaceAll("\\{userName}", msg.get("userName").toString())
					// .replaceAll("\\{sexStr}",
					// "1".equals(msg.get("userSex").toString())?"先生":"女士")
					.replaceAll("\\{cardType}", msg.get("cardType").toString())
					.replaceAll("\\{objectValue}",
							msg.get("objectValue").toString())
					.replaceAll("\\{hisUserId}",
							msg.get("hisUserId").toString())
					.replaceAll("\\{icCardId}", msg.get("icCardId").toString())
					.replaceAll("\\{callNo}", msg.get("callNo").toString())
					.replaceAll("\\{orgName}", msg.get("orgName").toString())
					.replaceAll("\\{depName}", msg.get("depName").toString())
					.replaceAll("\\{orderTime}",
							msg.get("orderTime").toString());

			if (StringUtils.isEmpty(msg.get("staffName"))) {
				content = content.replaceAll("\\{staffName}", ", 医生:"
						+ msg.get("staffName").toString());
			} else {
				content = content.replaceAll("\\{staffName}", "");
			}
			log.info("短信内容：" + content);

			log.info("就诊人电话：" + msg.get("userPhone"));
			if (!StringUtils.isEmpty(msg.get("userPhone"))) {
				String sendUrl = sms.replaceAll("\\{phone\\}",
						msg.get("userPhone").toString()).replaceAll(
						"\\{content\\}", URLEncoder.encode(content, "utf-8"));
				log.info("短信发送内容：" + sendUrl);
				String res = HttpUtil.sendPost(sendUrl, "");
				log.info("短信发送结果：" + res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据订单获取挂号记录 创建人:余宁 修改人:余宁 创建日期:2015年10月17日上午11:42:18
	 * 修改日期:2015年10月17日上午11:42:18
	 * 
	 * @param orderId
	 * @return
	 */
	private McReg getRegByOrder(String orderId) {
		try {
			McOrder order = orderDao.getOrderById(orderId);
			List<McOrderDet> orderDets = orderDao.getOrderDetByOrderId(orderId);
			McOrderType orderType = orderDao.getOrderTypeById(order
					.getOrderTypeId().toString());
			if ("GH".equals(orderType.getOrderCode())) {
				// 挂号
				String regId = orderDets.get(0).getProductToId();
				return regDao.getRegById(regId);
			} else {
				return null;
			}
		} catch (Exception e) {
			orderLog.info("根据订单获取挂号记录失败:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@HttpMethod
	@Override
	public HashMap<String, Object> getEarlyWarns(String staffId,
			String dateStr, Long startRow, Long rows) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		if (startRow == null)
			startRow = 0L;
		if (rows == null)
			rows = 20L;
		result.put("list",
				systemDao.getEarlyWarns(staffId, dateStr, startRow, rows));
		result.put("count", systemDao.getEarlyWarnsCount(staffId, dateStr));
		return result;
	}
}
