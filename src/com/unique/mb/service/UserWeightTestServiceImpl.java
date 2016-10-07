/**
 *
 */
package com.unique.mb.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.annotation.Resources;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.CollectionClass;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.util.StringUtil;
import com.unique.mb.dao.UserBloodSugarTestDao;
import com.unique.mb.dao.UserWeightTestDao;
import com.unique.mb.po.TestResult;
import com.unique.mb.po.TestResultDet;
import com.unique.mb.po.TestResultMain;
import com.unique.mb.po.UserElectrocardioResult;
import com.unique.mb.po.UserElectrocardioTest;
import com.unique.mb.po.UserElectrocardioTestDet;
import com.unique.mb.po.UserHeartRateTest;
import com.unique.mb.po.UserTestResult;
import com.unique.mb.po.UserTestTime;
import com.unique.mb.po.UserWeightTest;
import com.unique.order.dao.OrderDao;
import com.unique.order.po.SourceType;
import com.unique.survey.dao.SurveyDao;
import com.unique.survey.po.SurveyKpi;
import com.unique.user.dao.UserDao;
import com.unique.user.po.AmsUser;
import com.unique.user.po.UserDevice;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年12月1日下午1:44:40
 * 修改日期:2015年12月1日下午1:44:40
 */
@Service("userWeightTestService")
public class UserWeightTestServiceImpl implements UserWeightTestService{
	@Resource
	private UserWeightTestDao userWeightTestDao;
	@Resource
	private SurveyDao surveyDao;
	@Resource
	private UserDao userDao;
	@Resource
	private OrderDao orderDao;
	@Resource(name="userBloodSugarTestDao")
	private UserBloodSugarTestDao bs;
	@Resource
	private UserBloodSugarTestService userBloodSugarTestService;
	
	/**
	 * 添加用户体重
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年12月1日下午2:37:02
	 * 修改日期:2015年12月1日下午2:37:02
	 * @param userId
	 * @param time
	 * @param inputMode
	 * @param sourceCode
	 * @param deviceCode
	 * @param deviceToken
	 * @param isTwo
	 * @param height
	 * @param weight
	 * @return
	 */
	@HttpMethod
	@Transactional
	public Map<String, Object> addUserWeightTest(
			@NotNull String userId,
			String time,
			@NotNull String inputMode,
			String sourceCode,
			String deviceCode,
			String deviceToken,
			String isTwo,
			Double height,
			@NotNull Double weight
			){
		SurveyKpi kpi = surveyDao.getKpiByCode("TZ");
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		AmsUser user = userDao.getUserById(userId);
		if(user==null){
			result.put("retCode", 3);
			result.put("retMessage", "没有找到该患者");
			return result;
		}
		UserWeightTest test = new UserWeightTest();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			if(time==null || time.length()!=19){
				date = new Date();
			}else{
				date = sdf.parse(time);
			}
			test.setTestTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		test.setDeviceToken(deviceToken);
		test.setInputMode(inputMode);
		if(sourceCode!=null){
			SourceType stype = orderDao.getSourceByCode(sourceCode);
			test.setSourceId(stype.getSourceId());
		}
		test.setStatus("C");
		if(deviceCode!=null){
			UserDevice device = userDao.getUserDeviceByCode(deviceCode);
			test.setUserDeviceCode(deviceCode);
			test.setUserDeviceId(device.getUserDeviceId());
		}
		test.setUserWeight(new BigDecimal(weight));
		if(height!=null){
			test.setUserHeight(new BigDecimal(height));
		}else{
			test.setUserHeight(user.getUserHeight());
		}
		test.setUserId(user.getUserId());
		test.setUserName(user.getUserName());
		
		if(test.getUserWeight()!=null && test.getUserHeight() !=null){
			double bmi = test.getUserWeight().doubleValue() / 
					(test.getUserHeight().doubleValue() * test.getUserHeight().doubleValue());
			test.setUserBmi(new BigDecimal(bmi));
		}
		userWeightTestDao.addUserWeightTest(test);
		
		//更新用户身高体重信息
		user.setUserHeight(test.getUserHeight());
		user.setUserWeight(test.getUserWeight());
		userDao.updateUserInfo(user);
		
		TestResultMain testResult = null;
		if(test.getUserBmi()!=null){
			TestResult tresult = new TestResult();
			tresult.setTestId(test.getTestId());
			tresult.setTestType("6");
			bs.addTestResult(tresult);
			//BMI
			testResult = userBloodSugarTestService.getResult(userId,test.getUserBmi().toString(),"TZ_BMI",null,null, "TZ",null,inputMode,isTwo,test.getTestId(),tresult.getTestResultId());
		}

		UserTestResult tResult = new UserTestResult();
		tResult.setInputMode(inputMode);
		tResult.setEqupId(kpi.getEqupId());
		tResult.setResultLift(null);
		tResult.setSourceId(test.getTestId());
		tResult.setStatus("C");
		tResult.setTestItemReference(null);
		tResult.setTestItemUnit(kpi.getUnit());
		tResult.setTestItmeName(kpi.getKpiTitle());
		
		tResult.setTestTime(test.getTestTime());
		tResult.setUserDeviceCode(deviceCode);
		tResult.setUserDeviceId(test.getUserDeviceId());
		tResult.setUserId(user.getUserId());
		tResult.setUserName(user.getUserName());
		if(testResult!=null){
			test.setTestResult(testResult.getUserAlertResult());
			result.put("testResult", testResult);
			//有测量结果
			tResult.setTestResult(testResult.getUserAlertResult());
		}
		bs.addUserResultTest(tResult);
		result.put("bmi", test.getUserBmi());
		result.put("retCode", 0);
		result.put("retMessage", "添加成功");
		return result;
	}
	
	/**
	 * 分页获取体重数据
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年12月3日下午2:27:16
	 * 修改日期:2015年12月3日下午2:27:16
	 * @param userId
	 * @param startRow
	 * @param rows
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getWeightTestPage(@NotNull String userId,Long startRow,Long rows){
		HashMap<String, Object> result = new HashMap<String, Object>();
		Long endRows= null;
		if(startRow!=null && rows!=null){
			endRows = startRow + rows;
		}
		
		List<UserWeightTest> list = userWeightTestDao.getWeightByPage(userId, startRow, endRows);
		long count = userWeightTestDao.getWeightByPageCount(userId);
		result.put("list", list);
		result.put("count", count);
		return result;
	}
	
	/**
	 * 按周获取体重数据
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年12月3日下午2:33:44
	 * 修改日期:2015年12月3日下午2:33:44
	 * @param userId
	 * @param nowdate
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getWeightTestByWeek(@NotNull String userId,String nowdate,Integer type){
		HashMap<String, Object> result=new HashMap<String, Object>();
		//获取时间段
		List<UserTestTime> testTime = bs.selectTestTime("TZ");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			if(!StringUtil.isEmpty(nowdate)){
				cal.setTime(sdf.parse(nowdate));
			}else{
				cal.setTime(new Date());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int weekNum = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DAY_OF_WEEK, 8-weekNum);
		
		if(type==null)type=2;
		List<Map<String, Object>> list =userWeightTestDao.getWeightByWeek(userId, sdf.format(cal.getTime()),type);
		result.put("testTime", testTime);
		result.put("list", list);
		return result;
	}
	
	/**
	 * 判断用户是否有对应的服务
	 * @param userId
	 * @param scode GXYFW 高血压服务,TNBFW 糖尿病服务,YQTIZZ 孕期体重增长
	 * @return
	 */
	@HttpMethod
	public Integer checkService(@NotNull String userId,@NotNull String scode){
		return userWeightTestDao.checkService(userId, scode);
	}
	
	/**
	 * 获取BMI孕周报表
	 * @param userId
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getBmiReportByPregnancy(@NotNull String userId){
		HashMap<String, Object> result=new HashMap<String, Object>();
		
		//末次月经BMI和时间
		Map<String, Object> endMenstruation = userWeightTestDao.getEndMenstruation(userId);
		Date emnstruation = (Date) endMenstruation.get("t");
		BigDecimal bmi = (BigDecimal) endMenstruation.get("bmi"); 
		
		List<Map<String, Object>> list = userWeightTestDao.getBmiReportByPregnancy(userId,emnstruation);
		result.put("list", list);
		result.put("bmi", bmi);
		if(bmi.doubleValue()<18.5){
			result.put("type", "瘦妈妈");
		}else if(bmi.doubleValue()>=30){
			result.put("type", "超胖妈妈");
		}else if(bmi.doubleValue()>=18.5 && bmi.doubleValue()<=24.9){
			result.put("type", "标准妈妈");
		}else if(bmi.doubleValue()>=25 && bmi.doubleValue()<=29.9){
			result.put("type", "微胖妈妈");
		}
		return result;
	}
}
