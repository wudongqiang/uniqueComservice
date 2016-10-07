package com.unique.user.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import net.sf.json.JSONObject;

import org.dom4j.DocumentException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;
import com.unique.alipay.util.UtilDate;
import com.unique.core.annotation.CollectionClass;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.annotation.ParamNoNull;
import com.unique.core.annotation.ParamRegex;
import com.unique.core.util.FileUtil;
import com.unique.core.util.HttpUtil;
import com.unique.core.util.IdCardUtil;
import com.unique.core.util.IdcardValidator;
import com.unique.core.util.JsonUtil;
import com.unique.core.util.MD5Utils;
import com.unique.core.util.MyBeanUtils;
import com.unique.core.util.RandomNum;
import com.unique.core.util.RedisKeys;
import com.unique.core.util.RegexUtil;
import com.unique.core.util.RemotUtils;
import com.unique.core.util.StringUtil;
import com.unique.core.util.XmlUtil;
import com.unique.health.service.HealthService;
import com.unique.org.dao.OrgDao;
import com.unique.org.dao.StaffDao;
import com.unique.reg.po.Org;
import com.unique.reg.po.Staff;
import com.unique.system.dao.SystemDao;
import com.unique.system.po.UserClientType;
import com.unique.system.service.SystemService;
import com.unique.user.dao.UserDao;
import com.unique.user.dao.UserWechatDao;
import com.unique.user.po.AmsUser;
import com.unique.user.po.AmsUserLog;
import com.unique.user.po.CardType;
import com.unique.user.po.McCard;
import com.unique.user.po.McFb;
import com.unique.user.po.McMsg;
import com.unique.user.po.UserFriend;
import com.unique.user.po.UserSchedule;
import com.unique.user.po.UserWechat;

@Service("userService")
public class UserServiceImpl implements UserService{
	@Resource
	private StringRedisTemplate redisTemplate;
	
	@Resource
	private BaseService baseService;
	@Resource
	private UserDao userDao;
	@Resource
	private SystemDao systemDao;
	@Resource
	private StaffDao staffDao;
	@Resource
	private SystemService systemService;
	@Resource
	private UserWechatDao userWechatDao;
	@Resource
	private OrgDao orgDao;
	
	@Resource(name="healthService")
	private HealthService healthService;
	
	private String randomTime = FileUtil.readProperties("comservice.properties", "randomNumTime");
	private String sms = FileUtil.readProperties("comservice.properties", "sms");
	private String picServerOut = FileUtil.readProperties("comservice.properties", "picServerOut");
	private String welcomeTxt = FileUtil.readProperties("comservice.properties", "welcomeTxt");
	private String welcomeTilte = FileUtil.readProperties("comservice.properties", "welcomeTilte");
	SimpleDateFormat normalDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 绑定推送编号
	 * @param userId 用户ID
	 * @return 返回推送编号
	 */
	@HttpMethod
	public HashMap<String, Object> bindPushCode(String version,@ParamNoNull String deviceToken,String userId,@ParamNoNull String clientId){
		HashMap<String, Object> result =new HashMap<String, Object>();
		 
		UserClientType clientType = systemDao.getPushCode(deviceToken, clientId);
		if(clientType==null || StringUtil.isEmpty(clientType.getPushCode())){
			//生成推送编码
			String pushCode = clientId + "_" + UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
			
			UserClientType userClientType = new UserClientType();
			userClientType.setVersionNo(version);
			userClientType.setClientTypeId(new BigDecimal(clientId));
			if(userId!=null){
				userClientType.setUserId(new BigDecimal(userId));
			}
			userClientType.setDeviceToken(deviceToken);
			userClientType.setPushCode(pushCode);
			systemService.addOrUpdateUserClient(userClientType);
			
			result.put("tCode", 2);
			result.put("pushMessage", "编码不存在，并已添加");
			result.put("pushCode", pushCode);
		}else{
			//推送编码已存在
			UserClientType userClientType = new UserClientType();
			userClientType.setVersionNo(version);
			userClientType.setClientTypeId(new BigDecimal(clientId));
			if(userId!=null){
				userClientType.setUserId(new BigDecimal(userId));
			}
			userClientType.setDeviceToken(deviceToken);
			userClientType.setPushCode(clientType.getPushCode());
			systemService.addOrUpdateUserClient(userClientType);
			
			result.put("tCode", 1);
			result.put("pushMessage", "编码已存在");
			result.put("pushCode", clientType.getPushCode());
		}
		return result;
	}
	
	/**
	 * 获取验证码
	 * @param mobile 手机号
	 * @param intent 获取验证码的条目1: 用户注册2: 修改用户登录密码 3 仅验证
	 */
	@HttpMethod
	@Transactional(value=TxType.NOT_SUPPORTED)
	public HashMap<String, Object> getConfirmCode(
			@ParamRegex(regex=RegexUtil.MOBILE)
			@ParamNoNull String mobile,
			@ParamNoNull String intent){
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		if(intent.equals("1")){
			AmsUser u = userDao.getUserByMobile(mobile);
			if(u!=null){
				result.put("retCode", 3);
				result.put("retMessage", "该号码已注册");
				return result;
			}
		}
		
		String randomNums =  RandomNum.getRandomNum(4);
		HashMap<String, Object> randomNumInfo = new HashMap<String, Object>();
		randomNumInfo.put("mobile", mobile);
		randomNumInfo.put("intent", intent);
		randomNumInfo.put("randomNum", randomNums);
		long randomTimeL = Long.parseLong(randomTime);
		//发送短信
		String sendUrl = null;
		//SmsClient.sendMsg("您的验证码是:" + randomNums, mobile);
		try {
			sendUrl = sms.replaceAll("\\{phone\\}", mobile).replaceAll("\\{content\\}", URLEncoder.encode("您的验证码是:" + randomNums,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HttpUtil.sendPost(sendUrl,"");
		//加入redis 
		String  key = RedisKeys.RANDOM_NUM_INFOS + mobile + ":" + intent;
		if(redisTemplate.hasKey(key)){
			redisTemplate.delete(key);
		}
		redisTemplate.opsForValue().set(key, JSONObject.fromObject(randomNumInfo).toString());
		redisTemplate.expire(key, randomTimeL, TimeUnit.MINUTES);
		
		return result;
	}
	
	@HttpMethod
	@Override
	public Map<String,Object> checkUserMobile(@ParamRegex(regex=RegexUtil.MOBILE)
				@ParamNoNull String mobile ){
		Map<String, Object> result = new HashMap<String, Object>();
		AmsUser u =  userDao.getUserByMobile(mobile);
		if(u==null){
			result.put("retMessage", "手机号不存在，请先注册！");
			result.put("retCode", 1);
		}else{
			result.put("retMessage", "手机号存在");
			result.put("retCode", 0);
		}
		return result;
	}
	
	@HttpMethod
	@Override
	public Map<String,Object> checkUserPassword(@ParamNoNull String userId,@ParamNoNull String logonPwd){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			logonPwd = MD5Utils.getMD5String(logonPwd);//密码加密
			long ret = userDao.checkUserPassword(userId,logonPwd);
			if(ret > 0){
				result.put("retMessage", "密码输入正确！");
				result.put("retCode", 0);
			}else{
				result.put("retMessage", "密码输入错误！");
				result.put("retCode", 1);
			}
		} catch (Exception e) {
			result.put("retMessage", "未知异常！");
			result.put("retCode", 2);
		}
	
		return result;
		
	}
	
	@HttpMethod
	@Transactional
	@Override
	public HashMap<String, Object> changePwd(
			@ParamNoNull String userId,@ParamNoNull String logonPwd,
			@ParamNoNull String newPwd,	@ParamNoNull String confirmPwd){
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			//检查原密码
			if(!"0".equals(checkUserPassword(userId,MD5Utils.getMD5String(logonPwd)).get("retCode").toString())){
				result.put("retMessage", "原密码输入错误！");
				result.put("retCode", 0);
				return result;
			}
			//检查新密码和确认密码
			if(StringUtil.isEmpty(newPwd)||!newPwd.equals(confirmPwd)){
				result.put("retMessage", "新密码和确认密码不匹配，请检查！");
				result.put("retCode", 0);
				return result;
			}	
			//更新密码
			AmsUser user = new AmsUser();
			user.setUserId(BigDecimal.valueOf(Long.parseLong(userId)));
			newPwd = MD5Utils.getMD5String(newPwd);//加密
			user.setLogonPwd(newPwd);
			userDao.updateUserInfo(user);
			result.put("retMessage", "修改成功！");
			result.put("retCode", 0);	
		} catch (Exception e) {
			result.put("retMessage", "未知异常！");
			result.put("retCode", 1);	
		}
		
		return result;
	}
	
	/**
	 * 修改用户密码
	 * @param mobile 手机号
	 * @param ConfirmCode 随机码
	 * @param newPwd 新密码
	 * @return
	 */
	/* 原有密码修改方式
	 public HashMap<String, Object> changePwd(
			@ParamRegex(regex=RegexUtil.MOBILE)
			@ParamNoNull String mobile,@ParamNoNull String ConfirmCode,@ParamNoNull String newPwd){
		String randomNumStr = redisTemplate.opsForValue().get(RedisKeys.RANDOM_NUM_INFOS + mobile + ":" +2);
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(StringUtil.isEmpty(randomNumStr)){
			result.put("err_message", "您还没有获取验证码。");
			result.put("retCode", 3);
		}else{
			if(randomNumStr.equals(ConfirmCode)){
				//随机码匹配
				AmsUser user = userDao.getUserByPassword(mobile, null, null);
				user.setLogonPwd(newPwd);
				userDao.updateUserInfo(user);
				
				result.put("err_message", "修改成功");
			}else{
				result.put("err_message", "验证码错误。");
				result.put("retCode", 4);
			}
		}
		return result;
	}*/
	
	/**
	 * 注册
	 * @param mobile 手机号
	 * @param confirm 验证码
	 * @param password 密码
	 * @param deviceToken 
	 */
	@HttpMethod
	@Transactional
	public HashMap<String, Object>  register(
			@ParamRegex(regex=RegexUtil.MOBILE)
			@ParamNoNull String mobile,
			@ParamNoNull String confirm,
			@ParamNoNull String password,
			String calltype,
			String deviceToken,String type){
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		if(userDao.checkUser(mobile) > 0){
			result.put("err_message", "用户已存在!");
			result.put("retCode", 5);
		}else{
			addRegisterUser(mobile, confirm, "", "", password, calltype, deviceToken, type,result);
		}
		return result;
	}
	
	/**
	 * 增强注册方法
	 * @param mobile 手机号
	 * @param confirm 验证码
	 * @param password 密码
	 * @param userName 用户名
	 * @param idCard 身份证
	 * @param deviceToken 
	 */
	@HttpMethod
	public HashMap<String, Object> etRegister(
			@ParamRegex(regex=RegexUtil.MOBILE)
			@ParamNoNull String mobile,
			@ParamNoNull String confirm,
			@ParamNoNull String password,
			@ParamNoNull String userName,
			@ParamRegex(regex=RegexUtil.ID_CARD)
			@ParamNoNull String idCard,
			String calltype,
			String deviceToken,String type){
		HashMap<String, Object> result = new HashMap<String, Object>();
		//验证 身份证和手机号码
		if(userDao.checkUserByIdCard(idCard)!=null){
			result.put("err_message", "身份证已经存在！");
			result.put("retCode", 1);
			return result;
		}
		if(userDao.getUserByMobile(mobile)!=null){
			result.put("err_message", "手机号码已经存在！");
			result.put("retCode", 2);
			return result;
		}
		//添加数据到数据库
		addRegisterUser(mobile, confirm, userName, idCard, password, calltype, deviceToken, type,result);
		
		try {
			//新建健康档案
			String json = healthService.createHealthDocment(userName, idCard, mobile);
	//		"{'status':'"+rt+"','mpi':'"+mpi+"'}";
			if(json.contains("mpi")){
				json = JsonUtil.getJsonValueByKey(json, "mpi");
				if(json.length()>2){
					AmsUser user = (AmsUser)result.get("user");
					user.setHealtyRecordId(json);
					userDao.updateUserInfo(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Transactional
	private void addRegisterUser(String mobile,String confirm,String userName,
			String idCard,String password,String calltype,String deviceToken,String type,HashMap<String, Object> result){
		try {
			//验证身份证是否合法
			if (!StringUtil.isEmpty(idCard)&&!(new IdcardValidator().isValidatedAllIdcard(idCard))) {
				result.put("retCode", 1);
				result.put("retMessage", "输入的身份证不合法!");	
				return ;
			}
			String randomNumStr = redisTemplate.opsForValue().get(RedisKeys.RANDOM_NUM_INFOS + mobile + ":" + 1);
			if(randomNumStr==null){
				result.put("err_message", "您还没有申请注册用户码。");
				result.put("retCode", 3);
			}else{
				JSONObject randomNumObj = JSONObject.fromObject(randomNumStr);
				if(!randomNumObj.getString("randomNum").toString().equals(confirm)){
					//验证码不匹配
					result.put("err_message", "验证码不匹配。");
					result.put("retCode", 4);
				}else{
					AmsUser user = new AmsUser();
					user.setIsActive(new BigDecimal(1));
					user.setIsOnline("N");
					user.setLoginCount(new BigDecimal(0));
					user.setLogonAcct(mobile);
					user.setLogonPwd(MD5Utils.getMD5String(password)); //加密密码
					user.setNickName("kq".equals(type) ? mobile.substring(0,3) + "****" + mobile.substring(7, mobile.length()): mobile + "_P");

					user.setRegWay(new BigDecimal(calltype));
					user.setDeviceToken(deviceToken);
					user.setCreateTime(new Date());
					user.setOpTime(new Date());
					user.setStatus("C");
					user.setUserTypeId(new BigDecimal(1));
					user.setMobile(mobile);
					user.setUserName(userName);
					user.setIdCard(idCard);
					//填充身份证
					fillUser(idCard,user);
					
					if(userDao.addUser(user)>0){
						result.put("retCode", 0);
						result.put("user", user);
						
						//添加成功删除验证码
						redisTemplate.delete(RedisKeys.RANDOM_NUM_INFOS + mobile + ":" + 1);
						
						//添加消息
						McMsg msg = new McMsg();
						msg.setCreateTime(new Date());
						msg.setDeviceToken(deviceToken);
						msg.setDoStatus(new BigDecimal("1"));
						msg.setDoThing("nothing");
						msg.setMsgContent(welcomeTxt);
						msg.setMsgTitle(welcomeTilte);
						msg.setMsgTypeId(new BigDecimal("1"));
						msg.setPublishTime(new Date());
						msg.setPublishUser(new BigDecimal(-1));
						msg.setReviewUserName("系统");
						msg.setStatus("4");
						msg.setUserId(user.getUserId());
						msg.setUserName(user.getUserName());
						userDao.addTsMsg(msg);
						
						//判断是否添加就诊人
						if(!"kq".equals(type)){
							UserFriend uf = new UserFriend();
							uf.setUserId(user.getUserId());
							uf.setStatus("C");
							uf.setIsSelf("Y");
							uf.setIsDefault("Y");
							uf.setUserRelation("我");
							uf.setOperator(user.getUserId());
							uf.setOpTime(new Date());
							uf.setOperatorName(user.getUserName());
							uf.setMobile(mobile);
							uf.setFriendUserId(user.getUserId());
							
							userDao.addUserFriend(uf);
						}
					}else{
						result.put("retCode", -1);
						result.put("err_message", "注册失败！");
					}
				}
			}
		} catch (Exception e) {
			result.put("retCode", -1);
			result.put("err_message", "未知异常，注册失败！");
		}
	}
	
	
	/**
	 * 检测验证码
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月9日下午3:16:37
	 * 修改日期:2015年9月9日下午3:16:37
	 * @param mobile
	 * @param confirm
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object>  chkConfirm(
			@ParamRegex(regex=RegexUtil.MOBILE)
			@ParamNoNull String mobile,
			@ParamNoNull String confirm
			){
		HashMap<String, Object> result = new HashMap<String, Object>();
		String randomNumStr = redisTemplate.opsForValue().get(RedisKeys.RANDOM_NUM_INFOS + mobile + ":" + 3);
		
		if(!StringUtil.isEmpty(randomNumStr)){
			JSONObject randomNumObj = JSONObject.fromObject(randomNumStr);
			if(confirm.equals(randomNumObj.getString("randomNum"))){
				result.put("retCode", 1);
				result.put("retMessage", "验证通过");
				//添加成功删除验证码
				redisTemplate.delete(RedisKeys.RANDOM_NUM_INFOS + mobile + ":" + 3);
			}else{
				result.put("retCode", 2);
				result.put("retMessage", "验证失败");
			}
		}else{
			result.put("retCode", 2);
			result.put("retMessage", "验证失败");
		}
		return result;
	}
	
	/**
	 * 登陆方式，可以使用手机号码和身份证<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月4日 下午3:53:43<br/>
	 * @param logonAcct
	 * @param password
	 * @param deviceToken
	 * @param calltype
	 * @param weChatId
	 * @return
	 */
	@HttpMethod
	@Override
	public HashMap<String, Object> etLogin(
			@ParamRegex(regex=RegexUtil.MOBILE_AND_ID_CARED)
			@ParamNoNull String logonAcct,
			@ParamNoNull String password,
			String deviceToken,
			String calltype){
		 return loginHandle(password, logonAcct, deviceToken, calltype, null, "1");
	}
	
	/**
	 * 登陆接口 -- 单一登陆方式
	 * @param mobile
	 * @param password
	 * @param deviceToken
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object>  login(
//			@ParamRegex(regex=RegexUtil.MOBILE)
			@ParamRegex(regex=RegexUtil.MOBILE_AND_ID_CARED)
			@ParamNoNull String logonAcct,//mobile,
			@ParamNoNull String password,
			String deviceToken,
			String calltype,
			String weChatId){
		 return loginHandle(password, logonAcct, deviceToken, calltype, weChatId, "0");
	}
	
	/**
	 * 
	 * 登陆处理方法<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月4日 下午3:58:59<br/>
	 * @param result
	 * @param password
	 * @param logonAcct
	 * @param deviceToken
	 * @param calltype
	 * @param weChatId
	 * @param type 登陆方式 0：单一登陆方式，1：混合登陆方式（身份证和手机）
	 */
	@Transactional
	private HashMap<String, Object> loginHandle(String password,String logonAcct,
			String deviceToken, String calltype,String weChatId,String type){
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			//加密
			password =  MD5Utils.getMD5String(password);
			
			// 通过登陆检查 用户是否 已注册
			if(userDao.getUserByLoginAccout(logonAcct,type)<=0){
				result.put("err_message", "账号不存在，请先注册！");
				result.put("retCode", 3);
				return result;
			}
			
			AmsUser u = userDao.getUserByPassword(logonAcct, password,null,type);
			if(u==null){
				result.put("err_message", "用户名或密码错误。");
				result.put("retCode", 3);
				return result;
			}else{
				if("0".equals(u.getIsActive().toString())){
					result.put("err_message", "用户尚未激活！");
					result.put("retCode", 3);
					return result;
				}else if("2".equals(u.getIsActive().toString())){
					result.put("err_message", "用户被冻结！");
					result.put("retCode", 3);
					return result;
				}
			}
			//获取头像全路径
			if(!StringUtil.isEmpty(u.getImgUrl())){
				if(!u.getImgUrl().startsWith("/") && !picServerOut.endsWith("/")){
					u.setImgUrl(picServerOut + "/" + u.getImgUrl());
				}else{
					u.setImgUrl(picServerOut + u.getImgUrl());
				}
			}
			
			//更新用户在线状态
			AmsUser p = new AmsUser();
			p.setUserId(u.getUserId());
			p.setIsOnline("Y");
			//登陆次数+登录时间
			p.setLoginCount(u.getLoginCount()==null?new BigDecimal(1):u.getLoginCount().add(new BigDecimal(1)));
			p.setLastLoginTime(new Date());
			//更新deviceToken
			if(!StringUtil.isEmpty(deviceToken)){
				p.setDeviceToken(deviceToken);
			}
			userDao.updateUserInfo(p);
			
			//绑定微信id
			if(!StringUtil.isEmpty(weChatId)){
				bingWeChatId(calltype,u.getUserId(),u.getOrgId(),weChatId);
			}
			
			UserFriend uf = userDao.getFriendUserBySelf(u.getUserId().toString());
			result.put("user", u);
			result.put("friend", uf);
			
			//记录登陆日志
			AmsUserLog ulog = new AmsUserLog();
			ulog.setLogonTime(new Date());
			ulog.setLogonWay(new BigDecimal(calltype));
			ulog.setUserId(u.getUserId());
			ulog.setUserName(u.getUserName());
			userDao.addLoginLog(ulog);
			
			if(!StringUtil.isEmpty(deviceToken)){
				baseService.regDevice(deviceToken, u.getUserId().toString());
			}
		} catch (Exception e) {
			result.put("err_message", "未知异常，登陆失败！");
			result.put("retCode", -1);
		}
		return result;
	}
	
	/**
	 * 绑定微信Id<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月16日 上午10:48:30<br/>
	 * @param calltype 
	 * @param userId
	 * @param orgId
	 * @param weChatId
	 */
	private void bingWeChatId(String calltype, BigDecimal userId, BigDecimal orgId,String weChatId) {
		//每次登陆，删除之前绑定的关系，然后重新添加关系
		userWechatDao.deleteUserWechatByUserId(userId.toString(),calltype);
		// 检查是否绑定过
		UserWechat userWechat = new UserWechat();
		userWechat.setBindValue(weChatId);
		userWechat.setClientTypeId(new BigDecimal(calltype));
		userWechat.setCreateTime(new Date());
		userWechat.setUserId(userId);
		userWechat.setOrgId(orgId);
		userWechatDao.AddUserWechat(userWechat);
	}

	/**
	 * 医生登陆
	 * @param mobile
	 * @param password
	 * @param deviceToken
	 * @return
	 */
	@HttpMethod
	@Transactional
	public HashMap<String, Object>  staffLogin(
//			@ParamRegex(regex=RegexUtil.MOBILE_AND_ID_CARED)
			@ParamNoNull String logonAcct,
			@ParamNoNull String password,
			String deviceToken,
			String calltype){
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		//加密
		password =  MD5Utils.getMD5String(password);
		AmsUser u = userDao.getUserByPassword(logonAcct, password,"yyyh","0");
		if(u==null){
			result.put("err_message", "用户名或密码错误。");
			result.put("retCode", 3);
			return result;
		}
		
		//设置头像
		if(!StringUtil.isEmpty(u.getImgUrl())){
			u.setImgUrl(picServerOut+u.getImgUrl());
		}
		//更新用户在线状态
		AmsUser p = new AmsUser();
		p.setUserId(u.getUserId());
		p.setIsOnline("Y");
		//登陆次数+登录时间
		p.setLoginCount(u.getLoginCount()==null?new BigDecimal(1):u.getLoginCount().add(new BigDecimal(1)));
//		p.setLastLoginTime(new Date());
		userDao.updateUserInfo(p);
		
		result.put("user", u);
		//返回医生对象
		Staff staff = getStaffInfoByUserId(u.getUserId().toString());
		result.put("staff", staff);
		
		//记录登陆日志
		AmsUserLog ulog = new AmsUserLog();
		ulog.setLogonTime(new Date());
		ulog.setLogonWay(new BigDecimal(calltype));
		ulog.setUserId(u.getUserId());
		ulog.setUserName(u.getUserName());
		userDao.addLoginLog(ulog);
		if(deviceToken!=null){
			baseService.regDevice(deviceToken, u.getUserId().toString());
		}
		return result;
	}
	
	
	/**
	 * 用户登出
	 */
	@HttpMethod
	@Transactional
	public void  loginOut(@ParamNoNull String userId,String calltype){
		if(userDao.getUserById(userId) != null){
			AmsUser p = new AmsUser();
			p.setUserId(new BigDecimal(userId));
			p.setIsOnline("N");
			userDao.updateUserInfo(p);
			
			// 解绑 微信Id
			userWechatDao.deleteUserWechatByUserId(userId,calltype);
			
			//记录登出日志
			AmsUserLog ulog = userDao.getLastLogonTime(userId);
			if(ulog!=null){
				ulog.setLogoutTime(new Date());
				userDao.updateUserLogById(ulog);
			}else{
				AmsUser  u = userDao.getUserById(userId);
				ulog = new AmsUserLog();
				ulog.setLogonWay(new BigDecimal(calltype));
				ulog.setUserId(u.getUserId());
				ulog.setUserName(u.getUserName());
				userDao.addLoginLog(ulog);
			}

		}
	}
	

	/**
	 * 更新用户密码
	 * @param userId
	 * @param mobile
	 * @param confirm
	 * @param password
	 * @return
	 */
	@HttpMethod
	@Transactional
	public HashMap<String, Object> updateUserPwdNoConfirm(
			@ParamNoNull String userId,
			@ParamNoNull String password){
		HashMap<String, Object> result = new HashMap<String, Object>();
		AmsUser u =  new AmsUser();
		u.setUserId(new BigDecimal(userId));
		//加密
		password =  MD5Utils.getMD5String(password);
		u.setLogonPwd(password);
		userDao.updateUserInfo(u);
		//result.put("err_message", "更新成功");
		result.put("retCode", 1);
		return result;
	}
	
	/**
	 * 更新用户密码
	 * @param userId
	 * @param mobile
	 * @param confirm
	 * @param password
	 * @return
	 */
	@HttpMethod
	@Transactional
	public HashMap<String, Object> updateUserPwd(
			@ParamRegex(regex=RegexUtil.MOBILE)
			@ParamNoNull String mobile,
			@ParamNoNull String confirm,
			@ParamNoNull String password){
		HashMap<String, Object> result = new HashMap<String, Object>();
		String randomNumStr = redisTemplate.opsForValue().get(RedisKeys.RANDOM_NUM_INFOS + mobile + ":" + 2);
		if(randomNumStr==null){
			result.put("err_message", "您还没有申请注册用户码。");
			result.put("retCode", 3);
		}else{
			JSONObject randomNumObj = JSONObject.fromObject(randomNumStr);
			if(!randomNumObj.getString("randomNum").toString().equals(confirm)){
					//验证码不匹配
					result.put("err_message", "验证码不匹配。");
					result.put("retCode", 4);
			}else{
				AmsUser u =  userDao.getUserByMobile(mobile);
				//加密
				password =  MD5Utils.getMD5String(password);
				u.setLogonPwd(password);
				userDao.updateUserInfo(u);
				//result.put("err_message", "更新成功");
				result.put("retCode", 1);
			}
		}
		return result;
	}
	
	/**
	 * 更新用户手机号
	 * @param userId
	 * @param mobile
	 * @param confirm
	 * @param password
	 * @return
	 */
	@HttpMethod
	@Transactional
	public HashMap<String, Object> updateUserMobile(
			@ParamRegex(regex=RegexUtil.MOBILE)
			@ParamNoNull String mobile,
			@ParamNoNull String userId,
			@ParamNoNull String confirm){
		HashMap<String, Object> result = new HashMap<String, Object>();
		String randomNumStr = redisTemplate.opsForValue().get(RedisKeys.RANDOM_NUM_INFOS + mobile + ":" + 2);
		if(randomNumStr==null){
			result.put("err_message", "您还没有申请注册用户码。");
			result.put("retCode", 2);
		}else{
			JSONObject randomNumObj = JSONObject.fromObject(randomNumStr);
			if(!randomNumObj.getString("randomNum").toString().equals(confirm)){
				//验证码不匹配
				result.put("err_message", "验证码不匹配。");
				result.put("retCode", 3);
			}else{
				AmsUser u =userDao.getUserById(userId);
				u.setMobile(mobile);	//更改手机号
				u.setLogonAcct(mobile); //更改登陆手机
				userDao.updateUserInfo(u);
				result.put("err_message", "更新成功");
				result.put("retCode", 1);
				
				//更换 就诊者 是自己的电话
				UserFriend uf = userDao.getFriendUserBySelf(userId);
				if(uf!=null){
					uf.setMobile(mobile);
					userDao.updateUserFriend(uf);
				}
			}
		}
		return result;
	}
	
	@HttpMethod
	@Override
	@Transactional
	public HashMap<String,Object> modifyUserPwd(@NotNull String userId,@NotNull String oldPwd, @NotNull String newPwd){
		HashMap<String, Object> result = new HashMap<String, Object>();
		//检查原密码
		AmsUser user = userDao.getUserById(userId);
		if(user==null){
			result.put("message", "修改用户不存在，请检查！");
			result.put("retCode", 1);
			return result;
		}
		if(!user.getLogonPwd().equals(MD5Utils.getMD5String(oldPwd))){
			result.put("message", "原密码输入错误，请检查！");
			result.put("retCode", 2);
			return result;
		}
		user =  new AmsUser();
		//加密
		newPwd =  MD5Utils.getMD5String(newPwd);
		user.setUserId(new BigDecimal(userId));
		user.setLogonPwd(newPwd);
		//更新密码
		userDao.updateUserInfo(user);
		result.put("message", "更新成功");
		result.put("retCode", 0);
		return result;
	}
	
	/**
	 * 用户更新
	 * @param userId
	 * @param idCard
	 * @param userName
	 * @param nickName
	 * @param address
	 * @param imgUrl
	 * @param age
	 * @param sex
	 * @return
	 */
	@HttpMethod
	@Transactional
	public HashMap<String, Object>  updateUser(
			@ParamNoNull String userId,
			@ParamRegex(regex=RegexUtil.ID_CARD)
			 String idCard,
			 String userName,
			 String nickName,
			 String address,
			 String imgUrl,
			 String mobile,
			 String birth,
			 String sex,String type){
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		//验证身份证是否合法
		if(idCard!=null){
			if (!(new IdcardValidator().isValidatedAllIdcard(idCard))) {
				result.put("retCode", 1);
				result.put("retMessage", "输入的身份证不合法!");	
				return result;
			}
		}
		AmsUser param = new AmsUser();
		param.setUserId(new BigDecimal(userId));
		param.setIdCard(idCard);
		//填充身份证
		fillUser(idCard,param);
		//当身份证不为null时
		if(StringUtil.isEmpty(idCard)){
			if(!StringUtil.isEmpty(sex))
				param.setSex(new BigDecimal(sex));
			if(!StringUtil.isEmpty(birth)){
				param.setBirth(birth);
				param.setAge(IdCardUtil.getAgeByIdBirth(UtilDate.getStrToDate(birth, UtilDate.dtDate)));
			}
		}
		
		param.setUserName(userName);
		param.setNickName(nickName);
		param.setHomeAddr(address);
		param.setImgUrl(imgUrl);
		param.setMobile(mobile);
		
		userDao.updateUserInfo(param);
		AmsUser u = userDao.getUserById(userId);
		//设置头像全路径  picServerOut
		if(picServerOut.endsWith("/") || u.getImgUrl().startsWith("/")){
			u.setImgUrl(picServerOut + u.getImgUrl());
		}else{
			u.setImgUrl(picServerOut + "/" + u.getImgUrl());
		}
		UserFriend userFriend = userDao.getFriendUserBySelf(userId);
		UserFriend uf = null;
		
		if(userFriend==null){
			if(!"kq".equals(type)){
				//自己的就诊人是空就需要创建
				uf = new UserFriend();
				uf.setUserId(u.getUserId());
				uf.setStatus("C");
				uf.setIsSelf("Y");
				//uf.setIsDefault("Y");
				uf.setOperator(u.getUserId());
				uf.setOpTime(new Date());
				uf.setUserIdcard(idCard);
				//填充身份证信息
				fillFriend(idCard,uf);
				if(StringUtil.isEmpty(idCard)){
					if(!StringUtil.isEmpty(sex))
						uf.setUserSex(sex);
				}
				
				uf.setOperatorName(u.getUserName());
				uf.setFriendUserId(u.getUserId());
				uf.setUserAddr(address);
				uf.setUserRelation("我");
				uf.setMobile(mobile);
				userDao.addUserFriend(uf);
			}
		}else{
			userFriend.setStatus("U");
			userFriend.setOperator(u.getUserId());
			userFriend.setOpTime(new Date());
			userFriend.setUserIdcard(idCard);
			//填充身份证信息
			fillFriend(idCard,userFriend);
			if(StringUtil.isEmpty(idCard)){
				if(!StringUtil.isEmpty(sex))
					userFriend.setUserSex(sex);
			}
			userFriend.setUserName(userName);
			userFriend.setUserAddr(address);
			userFriend.setFriendUserId(u.getUserId());
			userFriend.setUserRelation("我");
			userFriend.setMobile(mobile);
			userDao.updateUserFriend(userFriend);
			uf = userDao.getFriendUserBySelf(userId);
		}
		
		result.put("user", u);
		result.put("friend", uf);
		return result;
	}
	
	/**
	 * 获得就诊人列表
	 * @param userId
	 * @return
	 */
	@HttpMethod
	@Transactional
	public HashMap<String, Object>  getFriends(@ParamNoNull String userId){
		HashMap<String, Object> result = new HashMap<String,Object>();
		List<UserFriend> friends = userDao.getFriendUsersByUid(userId);
		for(UserFriend f : friends){
			//查询就诊人的卡
			List<McCard> cards = userDao.getCardByFriendId(f.getFriendId().toString());
			f.setCards(cards);
		}
		result.put("friends", friends);
		return result;
	}
	
	/**
	 * 添加就诊人
	 * @param userId
	 * @param idCard
	 * @param mobile
	 * @param userName
	 * @param address
	 * @param relation
	 * @param isDefault
	 * @param operator
	 * @param cards 卡
	 * @return
	 */
	@HttpMethod
	@Transactional
	@Override
	public Map<String, Object>  addUserFriend(@ParamNoNull String userId,
			@ParamRegex(regex=RegexUtil.ID_CARD)
			@ParamNoNull String idCard,
			@ParamRegex(regex=RegexUtil.MOBILE)
			@ParamNoNull String mobile,
			@ParamNoNull String userName,
			 String address,
			 String relation,
			 @ParamNoNull String isDefault,
			 String operator,
			 @CollectionClass(value=McCard.class) List<McCard> cards,
			 String sex,String orgId){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(StringUtil.isEmpty(orgId)) orgId = "6";
			Map<String,Object>  vailResult = validateHisUserId(cards,userName,orgId);
			//表示验证有错误
			if(vailResult!=null&&vailResult.size()>0){
				return vailResult;
			}
			//验证身份证是否合法
			if (!(new IdcardValidator().isValidatedAllIdcard(idCard))) {
				result.put("retCode", 1);
				result.put("retMessage", "输入的身份证不合法!");	
				return result;
			}
			
			// 如果是 身份证+身份证类型 重复，如果是 医保卡+卡号+身份证 判断重复
			if(checkUserFriendCard(cards,userId,idCard,null)>0){
				result.put("retCode", 1);
				result.put("retMessage", "输入的就诊卡类型已经存在当前用户就诊人中!");	
				return result;
			}
//			验证身份证是否已存在库中
//			if(userDao.userFirendIsValidIdCard(idCard,userId)>0){
//				result.put("retCode", 1);
//				result.put("retMessage", "输入的身份证已经存在当前用户就诊人中!");	
//				return result;
//			}
			
			UserFriend uf = new UserFriend();
			uf.setUserIdcard(idCard);
			fillFriend(idCard,uf);
			if(isDefault.equalsIgnoreCase("y")){
				uf.setIsDefault("Y");
				userDao.setFriendNoDefault(userId);
			}else{
				uf.setIsDefault("N");
			}
			uf.setIsSelf("N");
			if(operator!=null)uf.setOperator(new BigDecimal(operator));
			uf.setOpTime(new Date());
			uf.setStatus("C");
			uf.setUserAddr(address);
			uf.setUserId(new BigDecimal(userId));
			uf.setUserName(userName);
			uf.setUserRelation(relation);
			uf.setUserSex(sex);
			uf.setMobile(mobile);
			userDao.addUserFriend(uf);
			if(cards!=null){
				AmsUser op = userDao.getUserById(userId);
				for(McCard card : cards){
					card.setFriendId(uf.getFriendId());
					card.setOperator(op.getUserId());
					card.setOperatorName(op.getUserName());
					card.setOpTime(new Date());
					card.setStatus("C");
					card.setUserId(op.getUserId());
					//添加用户卡片
					userDao.addMcCard(card);
				}
			}
			//返回该用户的所有就诊人
			List<UserFriend> friends = userDao.getFriendUsersByUid(userId);
			result.put("friends", friends);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("retCode", -1);
			result.put("retMessage", "未知异常!");
		}
		return result;
	}
	
	/**
	 * 验证就诊人是否重复添加就诊卡<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月10日 下午3:29:55<br/>
	 * @param cards
	 * @param userId
	 * @param idCard
	 * @param friendId 不为空，则不检查自己
	 * @return
	 */
	private int checkUserFriendCard(List<McCard> cards,String userId,String idCard,String friendId){
		// 如果是 身份证+身份证类型 重复
		// 如果是 医保卡+卡号+身份证 判断重复 
		int checkCount = 0; 
		if(cards!=null){
			String typeId = null;
			for(McCard card : cards){
				 typeId = card.getCardTypeId()+"";
				 if("0".equals(typeId)||"身份证".equals(card.getCardName())){//身份证
					 checkCount = userDao.userFirendCheckCardUniqu(userId, typeId, card.getCardNo(), idCard,friendId);
				 }else if("1".equals(typeId)||"就诊卡".equals(card.getCardName())){//就诊卡
					 checkCount = userDao.userFirendCheckCardUniqu(userId, typeId, card.getCardNo(), idCard,friendId);
				 }else if("2".equals(typeId)||"银行卡".equals(card.getCardName())){//银行卡
					 checkCount = userDao.userFirendCheckCardUniqu(userId, typeId, card.getCardNo(), idCard,friendId);
				 }else if("3".equals(typeId)||"医保卡".equals(card.getCardName())){//医保卡
					 checkCount = userDao.userFirendCheckCardUniqu(userId, typeId, card.getCardNo(), idCard,friendId);
				 }else if("4".equals(typeId)||"重庆健康卡".equals(card.getCardName())){//重庆健康卡
					 checkCount = userDao.userFirendCheckCardUniqu(userId, typeId, card.getCardNo(), idCard,friendId);
				 }else if("5".equals(typeId)||"微信".equals(card.getCardName())){//微信
					 checkCount = userDao.userFirendCheckCardUniqu(userId, typeId, card.getCardNo(), idCard,friendId);
				 }
				 //存在重复
				 if(checkCount>0) break;
			}
		}
		return checkCount;
	}
	
	private final static String VALIDATE_HISUSER_ID_MODULE = "module=userService&method=getUserByPatient";
	/**
	 * 验证hisUserId<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月23日 下午1:50:40<br/>
	 * @param cards
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> validateHisUserId(List<McCard> cards,
			String userName,String orgId) {
		Map<String,Object> params = new HashMap<String,Object>();
		JSONObject jsonObject =  null;
		//先判断 hisUserId
		if(cards!=null){
			Org org = orgDao.getOrgInfo(orgId);
			for(McCard card:cards){
				params.clear();
				if(!StringUtil.isEmpty(card.getHisUserId())){
					params.put("patientId", card.getHisUserId());
					params.put("patientName", userName);
					if("3".equals(card.getCardTypeId())){
						params.put("safeTyCode", card.getCardNo());
					}else if("0".equals(card.getCardTypeId())){
						params.put("idCard", card.getCardNo());
					}
					//请求到hisService 验证 hisUserId
					jsonObject = JSONObject.fromObject(RemotUtils.getRemotData(org.getHospitalUrl(), VALIDATE_HISUSER_ID_MODULE, params));
					if(!"0".equals(jsonObject.getJSONObject("result").get("retCode"))){
						//错误情况
						return (Map<String, Object>) JSONObject.toBean(jsonObject.getJSONObject("result"),Map.class);
					}
				}
			}
		}
		return null;
	}

	/**
	 * 更新就诊人信息
	 * @param userId
	 * @param friendId
	 * @param idCard
	 * @param userName
	 * @param address
	 * @param relation
	 * @param isDefault
	 * @param operator
	 * @param cards
	 * @param sex
	 * @return
	 */
	@HttpMethod
	@Transactional
	public Map<String, Object>  updateFriend(
			@ParamNoNull String userId,
			@ParamNoNull String friendId,
			@ParamRegex(regex=RegexUtil.ID_CARD)
			String idCard,
			String userName,
			String address,
			String relation,
			@ParamNoNull String isDefault,
			String operator,
			@ParamRegex(regex=RegexUtil.MOBILE)
			String mobile,
			@CollectionClass(value=McCard.class) List<McCard> cards,
			String sex,String orgId
			){
		Map<String, Object> result = new HashMap<String,Object>();
		try {
			//默认是机构6
			if(StringUtil.isEmpty(orgId)) orgId = "6";
			
			//验证hisUsreId
			Map<String, Object> validResult = validateHisUserId(cards, userName, orgId);
			if(validResult!=null&&validResult.size()>0){
				return validResult;
			}
			//验证身份证是否合法
			if (!(new IdcardValidator().isValidatedAllIdcard(idCard))) {
				result.put("retCode", 1);
				result.put("retMessage", "输入的身份证不合法!");	
				return result;
			}
			
			// 如果是 身份证+身份证类型 重复，如果是 医保卡+卡号+身份证 判断重复
			if(checkUserFriendCard(cards,userId,idCard,friendId)>0){
				result.put("retCode", 1);
				result.put("retMessage", "输入的就诊卡类型已经存在当前用户就诊人中!");	
				return result;
			}
			
//			//验证身份证是否已存在库中
//			if(!StringUtil.isEmpty(idCard) && !idCard.equals(uf.getUserIdcard()) && userDao.userFirendIsValidIdCard(idCard,userId)>0){
//				result.put("retCode", 1);
//				result.put("retMessage", "输入的身份证已经存在当前用户就诊人中!");	
//				return result;
//			}
			UserFriend uf = userDao.getFriendUserByFriendId(friendId);
			
			uf = new UserFriend();
			uf.setFriendId(new BigDecimal(friendId));
			uf.setUserId(new BigDecimal(userId));
			uf.setFriendUserId(new BigDecimal(userId));
			if(isDefault.equalsIgnoreCase("y")){
				//设置其他就诊人为非默认
				userDao.setFriendNoDefault(userId);
				uf.setIsDefault("Y");
			}else{
				uf.setIsDefault("N");
			}
			uf.setStatus("C");
			if(operator!=null)uf.setOperator(new BigDecimal(operator));
			uf.setOpTime(new Date());
			uf.setUserAddr(address);
			uf.setUserIdcard(idCard);
			uf.setUserName(userName);
			uf.setUserRelation(relation);
			uf.setUserSex(sex);
			uf.setMobile(mobile);
			userDao.updateUserFriend(uf);
			List<UserFriend> friends = userDao.getFriendUsersByUid(userId);
			result.put("friends", friends);
			
			List<McCard> oldCards= userDao.getCardByFriendId(friendId);
			Map<BigDecimal, McCard> delMap = new HashMap<BigDecimal, McCard>();
			if(oldCards!=null){
				for(McCard oldCard : oldCards){
					delMap.put(oldCard.getCardId(), oldCard);
				}
			}
			//判断删除的卡片
			if(cards!=null){
				AmsUser op = userDao.getUserById(userId);
				for(McCard card : cards){
					if(card.getCardId()!=null){
						/**
						 * 更新卡片信息
						 */
						card.setOperator(op.getUserId());
						card.setOperatorName(op.getUserName());
						userDao.updateMcCardById(card);
					}else{
						//添加卡片
						card.setFriendId(uf.getFriendId());
						card.setOperator(op.getUserId());
						card.setOperatorName(op.getUserName());
						card.setOpTime(new Date());
						card.setStatus("C");
						card.setUserId(op.getUserId());
						//添加用户卡片
						userDao.addMcCard(card);
					}
					
					//并从删除列表中去除
					if(card.getCardId()!=null && delMap.containsKey(card.getCardId())){
						delMap.remove(card.getCardId());
					}
				}
			}
			Set<BigDecimal> delIds = delMap.keySet();
			if(delIds!=null && delIds.size() > 0){
				//删除
				userDao.delCards(delIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("retCode", -1);
			result.put("retMessage", "未知异常!");
		}
		return result;
	}
	
	@HttpMethod
	@Transactional
	public HashMap<String, Object>  delFriend(@ParamNoNull String friendId,@ParamNoNull String userId){
		HashMap<String, Object> result = new HashMap<String,Object>();
		UserFriend uf = userDao.getFriendUserByFriendId(friendId);
		if(uf!=null&&uf.getIsDefault()!=null && uf.getIsDefault().equalsIgnoreCase("Y")){
			//设置自己为默认就诊人
			userDao.setFriendDefault(userId);
		}
		userDao.delFriend(friendId);
		List<UserFriend> friends = userDao.getFriendUsersByUid(userId);
		result.put("friends", friends);
		return result;
	}
	
	@HttpMethod
	@Override
	public HashMap<String,Object> getFriendUserByFriendId(@ParamNoNull String friendId){
		HashMap<String, Object> result = new HashMap<String,Object>();
		UserFriend userFriend = userDao.getFriendUserByFriendId(friendId);
		//查询就诊人的卡
		List<McCard> cards = userDao.getCardByFriendId(friendId);
		userFriend.setCards(cards);
		result.put("friendUser", userFriend);
		return result;
	}
	
	private boolean fillUser(String idCard,AmsUser user){
		if(!StringUtil.isEmpty(idCard) && user!=null){
			//根据身份证更新其他信息
			IdcardValidator validator = new IdcardValidator();
			boolean isIdCard = validator.is18Idcard(idCard);
			if(isIdCard){
				IdCardUtil util = new IdCardUtil(idCard);
				user.setBirth(util.getBirthday()==null?null:normalDateFormat.format(util.getBirthday()));
				if(util.getGender()!=null){
					if(util.getGender().equalsIgnoreCase("男")){
						user.setSex(new BigDecimal("1"));
					}else{
						user.setSex(new BigDecimal("2"));
					}
				}
				user.setAddrCity(util.getCity());
				user.setAddrPro(util.getProvince());
			}
			return isIdCard;
		}
		return true;
	}
	
	private boolean fillFriend(String idCard,UserFriend user){
		if(!StringUtil.isEmpty(idCard) && user!=null){
			//根据身份证更新其他信息
			IdcardValidator validator = new IdcardValidator();
			boolean isIdCard = validator.is18Idcard(idCard);
			if(isIdCard){
				IdCardUtil util = new IdCardUtil(idCard);
				user.setUserBirth(util.getBirthday()==null?null:normalDateFormat.format(util.getBirthday()));
				if(util.getGender()==null){
					if("男".equalsIgnoreCase(util.getGender())){
						user.setUserSex("1");
					}else{
						user.setUserSex("2");
					}
				}
			}
			return isIdCard;
		}
		return true;
	}
	
	/**
	 * 添加消息
	 * @param msg 消息内容
	 * @param title 消息标题
	 * @param userId 用户ID
	 * @param msgType 消息类型 YJFK:意见反馈，LXKF:离线客服 JKWD:健康知识问答
	 */
	@HttpMethod
	@Transactional
	public Map<String,Object> addMsg(@ParamNoNull String msg,
				String title,
				@ParamNoNull String userId,
				@ParamNoNull String msgType){
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			McFb fb = new McFb();
			AmsUser user = userDao.getUserById(userId);
			fb.setAdviserEmail(user.geteMail());
			fb.setAdviserName(user.getUserName());
			fb.setAdviserPhone(user.getMobile());
			fb.setFbContent(msg);
			fb.setFbTitle(title);
			fb.setFbTypeId(userDao.getMcFbTypeByCode(msgType).getFbTypeId());
			fb.setStatus("0");
			fb.setUserId(user.getUserId());
			if(userDao.addMsg(fb)>0){
				result.put("retCode", 0);
				result.put("retMessage", "操作成功");
			}else{
				result.put("retCode", 0);
				result.put("retMessage", "操作失败");
			}
		} catch (Exception e) {
			result.put("retCode", 1);
			result.put("retMessage", "未知异常!");
		}
		return result;
	}
	
	/**
	 * 获取离线消息（分页）
	 * @param userId 用户ID
	 * @param startRow 开始行
	 * @param rows 每页行数
	 * @param code LXKF：离线客服，JKWD：JKWD
	 * @return
	 */
	@HttpMethod
	public List<McFb> getLxMessageByPage(@ParamNoNull String userId,Long startRow,Long rows,String code){
		if(StringUtil.isEmpty(code)){
			code = "LXKF";
		}
		if(startRow==null)startRow = 0l;
		if(rows==null)rows = 10l;
		return userDao.getLxMessageByPage(userId, startRow, rows,code);
	}
	
	/**
	 * 获取离线消息（总条数）
	 * @param userId 用户ID
	 * @param code LXKF：离线客服，JKWD：JKWD
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getLxMessageByPageCount(@ParamNoNull String userId,String code){
		if(StringUtil.isEmpty(code)){
			code = "LXKF";
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("result",userDao.getLxMessageByPageCount(userId,code));
		return result;
	}
	
	/**
	 * 获取问答消息（分页）
	 * @param startRow 开始行
	 * @param rows 每页行数
	 * @param code LXKF：离线客服，JKWD：JKWD
	 * @return
	 */
	@HttpMethod
	public Map<String,Object> getJkwdMessageByPage(String userId,Long startRow,Long rows,String code){
		if(StringUtil.isEmpty(code)){
			code = "JKWD";
		}
		if(startRow==null)startRow = 0l;
		if(rows==null)rows = 10l;
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("mcfbs", userDao.getJkwdMessageByPage(userId, startRow, rows, code));
		result.put("count", userDao.getJkwdMessageByPageCount(userId,code));
		return result;
	}
	/**
	 * 获取消息接口
	 * @param userId
	 * @param msgTypeCode
	 * @param startRow
	 * @param rows
	 * @param publishUserId 2015-07-24 11:10 增加参数publishUserId 即发布用户在（医生端）获取患者消息时此值为医生ID
	 * @return
	 */
	@HttpMethod
	public List<McMsg> getMsgByPage(@ParamNoNull String userId,@ParamNoNull String[] msgTypeCode,Long startRow,Long rows){
		if(startRow==null)startRow = 0l;
		if(rows==null)rows = 10l;
		return userDao.getMsgByPage(userId, msgTypeCode, startRow, rows);
	}
	@HttpMethod
	public List<McMsg> getUserMsgByPage(@ParamNoNull String userId,@ParamNoNull String publishUserId,Long startRow,Long rows){
		if(startRow==null)startRow = 0l;
		if(rows==null)rows = 10l;
		return userDao.getUserMsgByPage(userId, publishUserId, startRow, rows); 
	}
	
	@HttpMethod
	public HashMap<String, Object> getMsgByPageCount(@ParamNoNull String userId,@ParamNoNull String[]  msgTypeCode){
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("count",userDao.getMsgByPageCount(userId, msgTypeCode));
		result.put("wdcount",userDao.getWdMsgCount(userId, msgTypeCode));
		return result;
	}
	
	@HttpMethod
	public McMsg getMsgDetById(@ParamNoNull String msgId){
		return userDao.getMsgDetById(msgId);
	}
	
	@HttpMethod
	@Transactional
	public void setMsgRead(@ParamNoNull String msgId){
		userDao.setMsgRead(msgId);
	}
	
	/**
	 * 医院用注册接口
	 * @param user
	 * @return
	 */
	@HttpMethod
	@Transactional
	public HashMap<String, Object> addUser(AmsUser user){
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		if(StringUtil.isEmpty(user.getUserName())){
			result.put("retCode", 3);
			result.put("retMessage", "用户姓名不能为空");
		}else if(StringUtil.isEmpty(user.getBirth())){
			result.put("retCode", 4);
			result.put("retMessage", "用户生日不能为空");
		}else if(user.getSex()==null){
			result.put("retCode", 5);
			result.put("retMessage", "用户性别不能为空");
		}else{
			AmsUser u = null;
			if(StringUtil.isEmpty(user.getIdCard())){
				//身份证为空
				userDao.addUser(user);
				u = user;
			}else{
				u = userDao.checkUserByIdCard(user.getIdCard());
				if(u!=null){
					MyBeanUtils.copyPropertiesIgnoreNull(user, u);
					userDao.updateUserInfo(u);
				}else{
					userDao.addUser(user);
					u = user;
				}
			}
			
			UserFriend uf = new UserFriend();
			uf.setUserId(u.getUserId());
			uf.setStatus("C");
			uf.setIsSelf("Y");
			uf.setIsDefault("Y");
			uf.setUserRelation("我");
			uf.setOperator(u.getUserId());
			uf.setOpTime(new Date());
			uf.setOperatorName(u.getUserName());
			uf.setFriendUserId(u.getUserId());
			int i = userDao.addUserFriend(uf);
			result.put("user", u);
			result.put("friend", uf);
			
			McMsg msg = new McMsg();
			msg.setCreateTime(new Date());
			msg.setDoStatus(new BigDecimal("1"));
			msg.setDoThing("nothing");
			msg.setMsgContent("如果您在使用过程中遇到任何问题，可以联系客服向我们反馈，客服电话：023-68611002");
			msg.setMsgTitle("欢迎使用“找健康”!");
			msg.setMsgTypeId(new BigDecimal("1"));
			msg.setPublishTime(new Date());
			msg.setPublishUser(new BigDecimal(-1));
			msg.setReviewUserName("系统");
			msg.setStatus("1");
			msg.setUserId(user.getUserId());
			msg.setUserName(user.getUserName());
			userDao.addTsMsg(msg);
		}
		return result;
	}
	
	@HttpMethod
	public Staff getStaffInfoByUserId(@NotNull String userId){
		Staff staff = userDao.getStaffInfoByUserId(userId);
		if(staff!=null){
			staff.setFans(staffDao.getStaffFans(staff.getStaffId().toString()));
		}
		return staff;
	}
	
	/**
	 * 获取用户日程（分页）
	 * @param userId 用户ID
	 * @param startRow 开始行
	 * @param rows 每页行数
	 * @param startTime 开始时间 yyyy-mm-dd
	 * @param endTime 结束时间 yyyy-mm-dd
	 * @return
	 */
	@HttpMethod
	public List<UserSchedule> getScheduleByPage(@ParamNoNull String userId,Long startRow,Long rows,String startTime,String endTime){
		if(startRow==null)startRow = 0l;
		if(rows==null)rows = 10l;
		return userDao.getScheduleResultMapByPage(userId, startRow, rows,startTime, endTime);
	}
	
	/**
	 * 获取用户日程（总条数）
	 * @param userId 用户ID
	 * @param startTime 开始时间 yyyy-mm-dd
	 * @param endTime 结束时间 yyyy-mm-dd
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getScheduleByPageCount(@ParamNoNull String userId,String startTime,String endTime){
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("result",userDao.getScheduleResultMapByPageCount(userId,startTime,endTime));
		return result;
	}

	@HttpMethod
	@Override
	public AmsUser getUserById(@ParamNoNull String userId) {
		AmsUser amsUser = userDao.getUserById(userId);
		if(amsUser!=null){
			if(!StringUtil.isEmpty(amsUser.getImgUrl())){
				if(picServerOut.endsWith("/") || amsUser.getImgUrl().startsWith("/")){
					amsUser.setImgUrl(picServerOut + amsUser.getImgUrl());
				}else{
					amsUser.setImgUrl(picServerOut + "/" + amsUser.getImgUrl());
				}
			}
			if(!StringUtil.isEmpty(amsUser.getIdCard())){
				IdCardUtil idCard =	new IdCardUtil(amsUser.getIdCard());
				amsUser.setAge(idCard.getAgeByIdCard());
			}else if(!StringUtil.isEmpty(amsUser.getBirth())){
				amsUser.setAge(IdCardUtil.getAgeByIdBirth(UtilDate.getStrToDate(amsUser.getBirth(), UtilDate.dtDate)));
			}
		}
		return amsUser;
	}
	
	
	@HttpMethod
	@Override
	public AmsUser getUserByFriendId(@ParamNoNull String friendId) {
		UserFriend uf = userDao.getFriendUserByFriendId(friendId);
		if(uf!=null){
		 return	this.getUserById(uf.getUserId().toString());
		}
		return null;
	}
	
	
	
	@HttpMethod
	@Override
	public Map<String, Object> staffPushToUserMsg(@ParamNoNull String content,String title,
			@ParamNoNull String friendId,@ParamNoNull String pubStaffId) {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			
			Staff staff = staffDao.getStaffById(pubStaffId);
			
			McMsg msg = new McMsg();
			msg.setMsgTitle(title);
			msg.setMsgContent(content);
			//这里保存就诊人 所属登陆用户Id
			msg.setUserId(userDao.getFriendUserByFriendId(friendId).getUserId()); //(new BigDecimal(userId));
			msg.setPublishTime(new Date());
			msg.setPublishUser(staff.getUserId()); //这里保存医生对于的UserId
			msg.setPublishUserName(staff.getStaffName());
			msg.setCreateTime(new Date());
			msg.setStatus("4");//默认4 未读状态    20150806 设置默认值
			msg.setMsgTypeId(userDao.getMsgTypeByCode("YZXX").getMsgTypeId());//医嘱消息
			//审核默认设置
			msg.setDoStatus(new BigDecimal(1));
			msg.setReviewUser(staff.getUserId());
			msg.setReviewUserName(staff.getStaffName());
			msg.setReviewTime(new Date());
			
			if(userDao.addTsMsg(msg)>0){
				result.put("retCode", 0);
				result.put("retMessage", "操作成功");
			}else{
				result.put("retCode", 0);
				result.put("retMessage", "操作失败");
			}
		} catch (Exception e) {
			result.put("retCode", 1);
			result.put("retMessage", "未知异常!");
		}
		return result;
	}
	
	
	/**
	 * 获取卡片类型集合
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月8日上午10:28:51
	 * 修改日期:2015年9月8日上午10:28:51
	 * @return
	 */
	@HttpMethod
	public List<CardType> getCardTypes(){
		return userDao.getCardTypes();
	}
	
	@HttpMethod
	@Override
	public HashMap<String, Object> getUserByWeChatId(@ParamNoNull String weChatId,String calltype) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		AmsUser u = userDao.getUserByWeChatId(weChatId,calltype);
		if(u==null){
			result.put("retCode", -1);
			result.put("err_message", "当前微信id为绑定用户，请登陆绑定！");
			return result;
		}
		//获取头像全路径
		if(!StringUtil.isEmpty(u.getImgUrl())){
			if(!u.getImgUrl().startsWith("/") && !picServerOut.endsWith("/")){
				u.setImgUrl(picServerOut + "/" + u.getImgUrl());
			}else{
				u.setImgUrl(picServerOut + u.getImgUrl());
			}
		}
		
		//更新用户在线状态
		AmsUser p = new AmsUser();
		p.setUserId(u.getUserId());
		p.setIsOnline("Y");
		//登陆次数+登录时间
		p.setLoginCount(u.getLoginCount()==null?new BigDecimal(1):u.getLoginCount().add(new BigDecimal(1)));
//				p.setLastLoginTime(new Date());
		 
		userDao.updateUserInfo(p);
		
		UserFriend uf = userDao.getFriendUserBySelf(u.getUserId().toString());
		result.put("user", u);
		result.put("friend", uf);
		result.put("retCode", 0);
		
		//记录登陆日志
		AmsUserLog ulog = new AmsUserLog();
		ulog.setLogonTime(new Date());
		ulog.setLogonWay(new BigDecimal(6));
		ulog.setUserId(u.getUserId());
		ulog.setUserName(u.getUserName());
		userDao.addLoginLog(ulog);
		return result;
	}
	
	@Override
	@HttpMethod
	@Transactional
	public HashMap<String, Object> addMcFb(String userId, @NotNull String typeCode,@NotNull String content,String title,String pId) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			McFb mcFb = new McFb();
			if(!StringUtil.isEmpty(userId)){
				//获取用户
				AmsUser user = userDao.getUserById(userId);
				mcFb.setCreateTime(new Date());
				mcFb.setUserId(user.getUserId());
				mcFb.setAdviserEmail(user.geteMail());
				mcFb.setAdviserName(user.getUserName());
				mcFb.setAdviserPhone(user.getMobile());
			}
			mcFb.setFbContent(content);
			if(!StringUtil.isEmpty(pId)) mcFb.setParentId(new BigDecimal(pId));
			if(!StringUtil.isEmpty(title)) {
				mcFb.setFbTitle(title);
			}else{
				if(content.length()>10){
					mcFb.setFbTitle(content.substring(0, 10)+"...");
				}else{
					mcFb.setFbTitle(content);
				}
			}
			mcFb.setFbTypeId(userDao.getMcFbTypeByCode(typeCode).getFbTypeId());//获取问答类型Id 
			mcFb.setStatus("0");
			mcFb.setCreateTime(new Date());
			userDao.addMcFb(mcFb);
			result.put("retCode", 0);
			result.put("retMessage", "添加问答成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("retCode", -1);
			result.put("retMessage", "添加问答失败！");
		}
		return result;
	}
	
	@HttpMethod
	@Override
	public String getWeChatIdByUserId(@NotNull String userId, String calltype) {
		UserWechat uw = userWechatDao.getUserWechatByUserId(userId, calltype);
		return uw==null?"":uw.getBindValue();
	}
}
