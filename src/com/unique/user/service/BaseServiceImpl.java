package com.unique.user.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.unique.core.annotation.HttpMethod;
import com.unique.core.annotation.ParamNoNull;
import com.unique.core.util.FileUtil;
import com.unique.core.util.JsonUtil;
import com.unique.core.util.RedisKeys;
import com.unique.user.dao.UserDao;
import com.unique.user.po.AmsUser;
import com.unique.user.po.UserCallLog;

/**
 * 基础接口
 * @author Angel
 *
 */
@Service("baseService")
public class BaseServiceImpl implements BaseService{
	@Resource
	private StringRedisTemplate redisTemplate;
	
	@Resource
	private UserDao userDao;
	private long logBuffer = Long.parseLong(FileUtil.readProperties("comservice.properties", "logBuffer"));
	/**
	 * 注册设备
	 * @param deviceToken 设备ID
	 * @param userId 用户ID（非必填）
	 */
	@HttpMethod
	@Override
	public void regDevice(@ParamNoNull String deviceToken, String userId) {
		HashMap<String, Object> deviceInfo = new HashMap<String,Object>();
		deviceInfo.put("deviceToken", deviceToken);
		deviceInfo.put("userId", userId);
		String deviceJson = JSONObject.fromObject(deviceInfo).toString();
		String old = redisTemplate.opsForValue().get(RedisKeys.IOS_DEVICE + deviceToken);
		if(old!=null){
			redisTemplate.opsForValue().set(RedisKeys.IOS_DEVICE + deviceToken, deviceToken);
		}else{
			redisTemplate.opsForList().rightPush(RedisKeys.IOS_DEVICE_LIST, deviceJson);
			redisTemplate.opsForValue().set(RedisKeys.IOS_DEVICE + deviceToken, deviceJson);
		}
	}
	
	/**
	 * 记录接口日志
	 */
	public void interfaceLog(String module,String method,int souce,String userId,String version, String deviceToken) {
		UserCallLog userLog = new UserCallLog();
		userLog.setCallMethod(method);
		userLog.setCallModule(module);
		userLog.setCallTime(new Date());
		userLog.setDeviceToken(deviceToken);
//		userLog.setMarks(marks);
		userLog.setSourceId(new BigDecimal(souce));
		if(userId!=null){
			AmsUser u = userDao.getUserById(userId);
			if(null!=u){
				userLog.setUserId(u.getUserId());
				userLog.setUserName(u.getUserName());
			}
		}

		String logJson = JSONObject.fromObject(userLog).toString();
		redisTemplate.opsForList().rightPush(RedisKeys.COM_LOG, logJson);
		
		if(redisTemplate.opsForList().size(RedisKeys.COM_LOG) >= logBuffer){
			
			List<UserCallLog> logs = new ArrayList<UserCallLog>();
			//缓存清空并转移数据库表
			while(redisTemplate.opsForList().size(RedisKeys.COM_LOG)==0){
				String logJsonOld = redisTemplate.opsForList().leftPop(RedisKeys.COM_LOG);
				JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss"}) );
				UserCallLog log = (UserCallLog) JSONObject.toBean(JSONObject.fromObject(logJsonOld,JsonUtil.getConfig()), UserCallLog.class);
				logs.add(log);
			}
			userDao.logs(logs);
		}
	}
}
