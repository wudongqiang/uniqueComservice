/**
 * 2015年10月8日
 */
package com.unique.mb.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.util.StringUtil;
import com.unique.mb.dao.UserBloodSugarTestDao;
import com.unique.mb.po.TestResult;
import com.unique.mb.po.TestResultDet;
import com.unique.mb.po.TestResultMain;
import com.unique.mb.po.UserBloodSugarTest;
import com.unique.mb.po.UserBloodSugarTestByTime;
import com.unique.mb.po.UserHeartRateTest;
import com.unique.mb.po.UserTestResult;
import com.unique.mb.po.UserTestTime;
import com.unique.order.dao.OrderDao;
import com.unique.order.po.SourceType;
import com.unique.patient.dao.UserDoctorRelationDao;
import com.unique.plan.dao.HealthyTestPlanDao;
import com.unique.plan.dao.HealthyTestPlanDetRuleDao;
import com.unique.plan.po.HealthyTestPlan;
import com.unique.plan.po.HealthyTestPlanDetRule;
import com.unique.plan.po.TestItemRuleDet;
import com.unique.reg.po.Staff;
import com.unique.survey.dao.SurveyDao;
import com.unique.survey.po.SurveyKpi;
import com.unique.user.dao.UserDao;
import com.unique.user.po.AmsUser;
import com.unique.user.po.UserDevice;
import com.unique.user.webservice.WebUserService;

/**
 * 用户血糖测试记录 业务接口实现类<br/>
 * @author Administrator
 * @date 2015年10月8日
 * @description 
 */
@Service("userBloodSugarTestService")
public class UserBloodSugarTestServiceImpl implements UserBloodSugarTestService{
	@Resource(name="userBloodSugarTestDao")
	UserBloodSugarTestDao bs;
	@Resource
	HealthyTestPlanDetRuleDao ht;
	@Resource
	HealthyTestPlanDao healthyTestPlanDao;
	@Resource
	private WebUserService webUserService;
	@Resource
	private UserDao userDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private SurveyDao surveyDao;
	@Resource
	private UserDoctorRelationDao userDoctorRelationDao;
	/**
	 * 1.	血糖检测时段列表接口
	 */
	@HttpMethod
	@Override
	public List<UserTestTime> queryUserBloodSugartById(@NotNull String kpiCode){
		List<UserTestTime> ubs = bs.selectUserBloodSugartTest(kpiCode);
		return ubs;
	}
	/**
	 * 2.	血糖检测数据提交接口
	 */
	@HttpMethod
	@Override
	@Transactional
	public Map<String, Object> addUserBloodSugar(////////start
			@NotNull String userId,String time,
			@NotNull String periodId,
			@NotNull String bloodglucose,
			@NotNull String Inputmode,
			String sourceCode,
			String deviceCode,
			String isTwo){
		SurveyKpi kpi = surveyDao.getKpiByCode("XT");
		HashMap<String, Object> result = new HashMap<String, Object>();
		//写入血糖表
		UserBloodSugarTest ubs = new UserBloodSugarTest();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			if(time==null){
				date = new Date();
			}else{
				date = sdf.parse(time);
			}
			ubs.setTestTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		BigDecimal per=new BigDecimal(periodId);
		BigDecimal blo=new BigDecimal(bloodglucose);
		AmsUser user = userDao.getUserById(userId);
		ubs.setUserId(user.getUserId());
		ubs.setUserName(user.getUserName());
		ubs.setTestValue(blo);
		ubs.setStatus("C");
		ubs.setTestTimeId(per);
		ubs.setInputMode(Inputmode);
		if(deviceCode!=null){
			UserDevice device = userDao.getUserDeviceByCode(deviceCode);
			ubs.setUserDeviceCode(deviceCode);
			ubs.setUserDeviceId(device.getUserDeviceId());
		}
		if(sourceCode!=null){
			SourceType stype = orderDao.getSourceByCode(sourceCode);
			ubs.setSourceId(stype.getSourceId());
		}
		//写入血糖表, 写入监测记录日志
		bs.addUserBloodSugarTest(ubs);
		
		TestResult tresult = new TestResult();
		tresult.setTestId(ubs.getTestId());
		tresult.setTestType("2");
		bs.addTestResult(tresult);
		TestResultMain testResult = getResult(userId,blo.toString(),"XT_SUB",null,null,"XT",per.toString(),Inputmode,isTwo,ubs.getTestId(),tresult.getTestResultId());
		
		UserTestResult tResult = new UserTestResult();
		tResult.setInputMode(Inputmode);
		tResult.setEqupId(kpi.getEqupId());
		tResult.setResultLift(null);
		tResult.setSourceId(ubs.getTestId());
		tResult.setStatus("C");
		tResult.setTestItemReference(null);
		tResult.setTestItemUnit(kpi.getUnit());
		tResult.setTestItmeName(kpi.getKpiTitle());
		tResult.setTestTime(ubs.getTestTime());
		tResult.setUserDeviceCode(deviceCode);
		tResult.setUserDeviceId(ubs.getUserDeviceId());
		tResult.setUserId(user.getUserId());
		tResult.setUserName(user.getUserName());
		if(testResult!=null){
			//有测量结果
			tResult.setTestResult(testResult.getUserAlertResult());
			
			ubs.setTestResult(tResult.getTestResult());
			result.put("testResult", testResult);
			bs.updateUserBloodSugarTest(ubs);
		}
		bs.addUserResultTest(tResult);
		return result;
	}
	
	/**
	 * 计算测量结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月21日下午3:26:40
	 * 修改日期:2015年10月21日下午3:26:40
	 * @param userId
	 * @param firstValue
	 * @param firstKpiCode
	 * @param secondValue
	 * @param secondKpiCode
	 * @param testTimeId
	 * @param type 测量类型（1:血压,2:血糖,3血脂,4心电）
	 * @return
	 */
	public TestResultMain getResult(String userId,
			String firstValue,
			String firstKpiCode,//父
			String secondValue,
			String secondKpiCode,
			String pkpiCode,
			String testTimeId,
			String Inputmode,
			String isTwo,
			BigDecimal testId,
			BigDecimal testResultId){
		
		AmsUser user = userDao.getUserById(userId);
		
		List<TestResultMain> results = new ArrayList<TestResultMain>();
		List<HealthyTestPlanDetRule> rules = null;
 		if(testTimeId!=null){
			 rules = healthyTestPlanDao.getTestPlanListByUser(userId, pkpiCode, testTimeId);
		}
		
		if(rules==null || rules.size() ==0){
			List<TestItemRuleDet> ruleDets = healthyTestPlanDao.getTestRuleListByUser(testTimeId,pkpiCode);
			for(TestItemRuleDet ruleDet : ruleDets){
				TestResultMain main = new TestResultMain();
				try {
					BeanUtils.copyProperties(main, ruleDet);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				results.add(main);
			}
		}else{
			for(HealthyTestPlanDetRule rule : rules){ 
				TestResultMain main = new TestResultMain();
				try {
					BeanUtils.copyProperties(main, rule);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				results.add(main);
			}
		}
		
		//计算预警
		TestResultMain chooseResult = null;
		for(TestResultMain result : results){
			SurveyKpi  onekpi = null;SurveyKpi  twokpi = null;
			
			if(result.getOneKpiId()!=null){
				  onekpi = surveyDao.getKpiById(result.getOneKpiId().toString());
			}
			if(result.getTwoKpiId()!=null){
				  twokpi = surveyDao.getKpiById(result.getTwoKpiId().toString());
			}
			String formula = "";
			if(result.getOneBeginValue()!=null || result.getOneEndValue()!=null){
				String tempValue = "";
				if(onekpi!=null && onekpi.getKpiCode().equals(firstKpiCode)){
					tempValue = firstValue;
				}
				if(onekpi!=null && onekpi.getKpiCode().equals(secondKpiCode)){
					tempValue = secondValue;
				}
				 formula +="(";
				 if(result.getOneBeginValue()!=null){
					 formula += result.getOneBeginValue() + result.getOneBeginSymbol() +  tempValue;
				 }
				 if(result.getOneEndValue()!=null){
					 if(result.getOneBeginValue()!=null){
						 formula +=  " && ";
					 }
					 formula += tempValue + result.getOneEndSymbol() +  result.getOneEndValue();
				 }
				 formula += ")";
			}
			if(result.getTwoBeginValue() != null || result.getTwoEndValue() !=null){
				String tempValue = "";
				if(twokpi!=null && twokpi.getKpiCode().equals(firstKpiCode)){
					tempValue = firstValue;
				}
				if(twokpi!=null && twokpi.getKpiCode().equals(secondKpiCode)){
					tempValue = secondValue;
				}
				if(result.getOneBeginValue()!=null || result.getOneEndValue() !=null){
					formula += result.getOneTwoRelation();
				}
				formula +=  "(";
				 if(result.getTwoBeginValue()!=null){
					 formula += result.getTwoBeginValue() + result.getTwoBeginSymbol() +  tempValue;
				 }
				 if(result.getTwoEndValue()!=null){
					 if(result.getTwoBeginValue()!=null){
						 formula +=  " && ";
					 }
					 formula += tempValue + result.getTwoEndSymbol() +  result.getTwoEndValue();
				 }
				formula +=  ")";
			}
			
			ScriptEngineManager factory = new ScriptEngineManager();
			ScriptEngine engine = factory.getEngineByName ("JavaScript");
			if(StringUtil.isEmpty(formula))continue;
			try {
				System.out.println("-----公式-----");
				System.out.println(formula);
				Object a = engine.eval(formula);
				if((Boolean)a){
					chooseResult = result;
					break;
				}
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
		
		
		
		if(chooseResult!=null){
			/******************添加到结果*****************/
			TestResultDet tresultDet = new TestResultDet();
			tresultDet.setAlertGrade(chooseResult.getAlertGrade());
			tresultDet.setDoctorAlertCon(chooseResult.getDoctorAlertCon());
			tresultDet.setTestResultId(testResultId);
			tresultDet.setUserHealthySuggest(chooseResult.getUserHealthySuggest());
			tresultDet.setUserAlertResult(chooseResult.getUserAlertResult());
			SurveyKpi firstkpi = surveyDao.getKpiByCode(firstKpiCode);
			tresultDet.setKpiId(firstkpi.getKpiId());
			
			/******************************************/
			
			List<Staff> staffs = userDoctorRelationDao.getStaffInUserDoctorRelationByUserId(userId, null, null);
			Map<String, Object> extra = new HashMap<String, Object>();
			if(chooseResult.getAlertGrade().intValue() == 3){//不正常
					/////////////////////第一次(Inputmode!=null&&!Inputmode.equals("1"))&&(
				 if(isTwo==null||isTwo.equals("(null)")||isTwo.equals("")){
					 if(pkpiCode.equalsIgnoreCase("XY") || pkpiCode.equalsIgnoreCase("XL")){
						 tresultDet.setIsAlert("N");
						 chooseResult.setIsTwo("1");
					 }else{
						 tresultDet.setIsAlert("Y");
					 }
				 }else{//第二次
					 tresultDet.setIsAlert("Y");
					 
					//三级预警
					String alertCon = chooseResult.getDoctorAlertCon();
					for(Staff s : staffs){
						//添加一个医生ID
						extra.put("reStaffId", s.getUserId().toString());
						extra.put("reUserId", userId);
						
						String alertConSend=alertCon.replaceAll("\\$\\{username}", user.getUserName())
							.replaceAll("\\$\\{staffName}", s.getStaffName())
							.replaceAll("\\$\\{firstValue}", firstValue)
							.replaceAll("\\$\\{secondValue}", secondValue);
						webUserService.sendMsg("-3",alertConSend, JSONObject.fromObject(extra).toString(), new String[]{s.getUserId().toString()});
					}
					chooseResult.setIsTwo(null);
				 }
			}
			
			//添加
			bs.addTestResultDet(tresultDet);
		}
		
		return chooseResult;
	}
	
	public TestResultMain getResultOnly(String userId,
			String firstValue,
			String firstKpiCode,//父
			String secondValue,
			String secondKpiCode,
			String pkpiCode,
			String testTimeId,
			String Inputmode,
			BigDecimal testResultId){
		List<TestResultMain> results = new ArrayList<TestResultMain>();
		List<HealthyTestPlanDetRule> rules = null;
 		if(testTimeId!=null){
			 rules = healthyTestPlanDao.getTestPlanListByUser(userId, pkpiCode, testTimeId);
		}
		
		if(rules==null || rules.size() ==0){
			List<TestItemRuleDet> ruleDets = healthyTestPlanDao.getTestRuleListByUser(testTimeId,pkpiCode);
			for(TestItemRuleDet ruleDet : ruleDets){
				TestResultMain main = new TestResultMain();
				try {
					BeanUtils.copyProperties(main, ruleDet);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				results.add(main);
			}
		}else{
			for(HealthyTestPlanDetRule rule : rules){ 
				TestResultMain main = new TestResultMain();
				try {
					BeanUtils.copyProperties(main, rule);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				results.add(main);
			}
		}
		
		//计算预警
		TestResultMain chooseResult = null;
		for(TestResultMain result : results){
			SurveyKpi  onekpi = null;SurveyKpi  twokpi = null;
			
			if(result.getOneKpiId()!=null){
				  onekpi = surveyDao.getKpiById(result.getOneKpiId().toString());
			}
			if(result.getTwoKpiId()!=null){
				  twokpi = surveyDao.getKpiById(result.getTwoKpiId().toString());
			}
			String formula = "";
			if(result.getOneBeginValue()!=null || result.getOneEndValue()!=null){
				String tempValue = "";
				if(onekpi!=null && onekpi.getKpiCode().equals(firstKpiCode)){
					tempValue = firstValue;
				}
				if(onekpi!=null && onekpi.getKpiCode().equals(secondKpiCode)){
					tempValue = secondValue;
				}
				 formula +="(";
				 if(result.getOneBeginValue()!=null){
					 formula += result.getOneBeginValue() + result.getOneBeginSymbol() +  tempValue;
				 }
				 if(result.getOneEndValue()!=null){
					 if(result.getOneBeginValue()!=null){
						 formula +=  " && ";
					 }
					 formula += tempValue + result.getOneEndSymbol() +  result.getOneEndValue();
				 }
				 formula += ")";
			}
			if(result.getTwoBeginValue() != null || result.getTwoEndValue() !=null){
				String tempValue = "";
				if(twokpi!=null && twokpi.getKpiCode().equals(firstKpiCode)){
					tempValue = firstValue;
				}
				if(twokpi!=null && twokpi.getKpiCode().equals(secondKpiCode)){
					tempValue = secondValue;
				}
				if(result.getOneBeginValue()!=null || result.getOneEndValue() !=null){
					formula += result.getOneTwoRelation();
				}
				formula +=  "(";
				 if(result.getTwoBeginValue()!=null){
					 formula += result.getTwoBeginValue() + result.getTwoBeginSymbol() +  tempValue;
				 }
				 if(result.getTwoEndValue()!=null){
					 if(result.getTwoBeginValue()!=null){
						 formula +=  " && ";
					 }
					 formula += tempValue + result.getTwoEndSymbol() +  result.getTwoEndValue();
				 }
				formula +=  ")";
			}
			
			ScriptEngineManager factory = new ScriptEngineManager();
			ScriptEngine engine = factory.getEngineByName ("JavaScript");
			if(StringUtil.isEmpty(formula))continue;
			try {
				System.out.println("-----公式-----");
				System.out.println(formula);
				Object a = engine.eval(formula);
				if((Boolean)a){
					chooseResult = result;
					break;
				}
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
		
		if(chooseResult!=null){
			AmsUser user = userDao.getUserById(userId);
			/******************添加到结果*****************/
			TestResultDet tresultDet = new TestResultDet();
			tresultDet.setAlertGrade(chooseResult.getAlertGrade());
			
			String doctorAlertCon = null;
			if(!StringUtil.isEmpty(chooseResult.getDoctorAlertCon()) && user!=null){
				doctorAlertCon = chooseResult.getDoctorAlertCon().replaceAll("\\$\\{username}", user.getUserName())
//						.replaceAll("\\$\\{staffName}", s.getStaffName())
						.replaceAll("\\$\\{firstValue}", firstValue)
						.replaceAll("\\$\\{secondValue}", secondValue);
			}
			tresultDet.setDoctorAlertCon(doctorAlertCon);
			tresultDet.setTestResultId(testResultId);
			tresultDet.setUserHealthySuggest(chooseResult.getUserHealthySuggest());
			
			String userAlertCon = null;
			if(!StringUtil.isEmpty(chooseResult.getUserAlertResult()) && user!=null){
			userAlertCon = chooseResult.getUserAlertResult().replaceAll("\\$\\{username}", user.getUserName())
//					.replaceAll("\\$\\{staffName}", s.getStaffName())
					.replaceAll("\\$\\{firstValue}", firstValue)
					.replaceAll("\\$\\{secondValue}", secondValue);
			}
			tresultDet.setUserAlertResult(userAlertCon);
			SurveyKpi firstkpi = surveyDao.getKpiByCode(firstKpiCode);
			tresultDet.setKpiId(firstkpi.getKpiId());
			bs.addTestResultDet(tresultDet);
			/******************************************/
		}
		return chooseResult;
	}
	
	
	/**
	 * 3.	按照日、周、月获取血糖历史纪录
	 */
	@HttpMethod
	@Override
	public List<UserBloodSugarTest> getUserBloodSugarTestDays(@NotNull String userId) {
		return bs.getUserBloodSugarTestDays(userId);
	}
	
	@HttpMethod
	@Override
	public HashMap<String, Object> getUserBloodSugarTestWeeksAndMonth(
			@NotNull String userId, @NotNull String type) {
		
		HashMap<String, Object> result = new HashMap<String, Object>(2);
		//参数值必须是weeks 或 month
		if(!("weeks".equals(type) || "month".equals(type))){
			result.put("retCode", 1);
			result.put("data","type参数值必须是weeks或 month");
		}else{
			result.put("retCode", 0);
			result.put("data", bs.getUserBloodSugarTestWeeksAndMonth(userId,type));
		}
		return result;
	}
	
	/**
	 * 按照时间分割类型查询测试记录
	 * @param type 1 日，2 周，3 月, 4 日(平均值方式)，5 周(平均值方式)，6 月(平均值方式)
	 * @param time 最后一天的时间 yyyy-mm-dd
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getUserBloodSugarTestByTime(String periodId,@NotNull Integer type,@NotNull String userId,String datetime){
		Date nowTime = null;
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(StringUtil.isEmpty(datetime)){
			nowTime = new Date();
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				nowTime = sdf.parse(datetime);
			} catch (ParseException e) {
				nowTime = null;
			}
		}
		List<UserBloodSugarTestByTime> tests = null;
		if(type>3){
			tests=  bs.getUserBloodSugarTestByTimeAvg(type-3, nowTime,userId,periodId);
		}else{
			tests=  bs.getUserBloodSugarTestByTime(type, nowTime,userId,periodId);
		}
		if(tests==null){
			tests = new ArrayList<UserBloodSugarTestByTime>();
		}
		result.put("bloodSugar", tests);
		return result;
	}
	
	/**
	 * 4.	分页获取血糖历史纪录
	 * 查询测试记录分页
	 */
	@HttpMethod
	@Override
	public List<UserBloodSugarTest> getTestPage(@NotNull String userId,String periodId,Long startRow,Long rows){
		Long endRows= null;
		if(startRow!=null && rows!=null){
			endRows = startRow + rows;
		}
		List<UserBloodSugarTest> result = bs.getTestPage(userId,periodId, startRow, endRows);
		for(UserBloodSugarTest test : result){
			test.setTestValueStr(test.getTestValue().toString());
		}
		System.out.println(JSONArray.fromObject(result));
		return result;
	}
	/**
	 * 查询测试记录分页（总数）
	 */
	@HttpMethod
	@Override
	public HashMap<String, Object> getTestPageCount(@NotNull String userId,String periodId){
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("count", bs.getTestPageCount(userId,periodId));
		return result;
	}
	
	/**
	 * 按周获取血糖数据
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月26日上午10:03:51
	 * 修改日期:2015年11月26日上午10:03:51
	 * @param userId
	 * @param nowdate 本周任意一天,格式：yyyy-MM-dd,不传默认为本周
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getBloodSugarByWeek(@NotNull String userId,String nowdate){
		HashMap<String, Object> result=new HashMap<String, Object>();
		//获取时间段
		List<UserTestTime> testTime = bs.selectTestTime("XT");
		
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
		
		List<Map<String, Object>> list = bs.getBloodSugarByWeek(userId, sdf.format(cal.getTime()));
		result.put("testTime", testTime);
		result.put("list", list);
		return result;
	}
	
	@Transactional
	public void subHyperResult(Map<String, Object> map){
//		TestResult tresult = new TestResult();
//		tresult.setTestId(new BigDecimal(map.get("TEST_ID").toString()));
//		tresult.setTestType("1");
//		bs.addTestResult(tresult);
//		
//		TestResultMain testResult = getResultOnly(map.get("USER_ID").toString(),
//				map.get("HIGH_TENSION").toString(),"SSY",map.get("LOW_TENSION").toString(),"SZY",
//				"XY",null,map.get("INPUT_MODE")==null?null:map.get("INPUT_MODE").toString(),tresult.getTestResultId());
//		
		TestResultMain testResult = null;
		UserHeartRateTest rateTest = new UserHeartRateTest();
		rateTest.setDeviceToken(map.get("DEVICE_TOKEN")==null?null:map.get("DEVICE_TOKEN").toString());
		rateTest.setInputMode(map.get("INPUT_MODE")==null?null:map.get("INPUT_MODE").toString());
		rateTest.setIsAlarm(map.get("IS_ALARM")==null?null:map.get("IS_ALARM").toString());
		rateTest.setRate(new BigDecimal(map.get("HEART_RATE").toString()));
		rateTest.setSourceId(map.get("SOURCE_ID")==null?null:new BigDecimal(map.get("SOURCE_ID").toString()));
		rateTest.setStatus("C");
		rateTest.setTestTime(map.get("TEST_TIME")==null?null:(Date)map.get("TEST_TIME"));
		rateTest.setTestTimeId(map.get("TEST_TIME_ID")==null?null:new BigDecimal(map.get("TEST_TIME_ID").toString()));
		rateTest.setTestType("1");
		rateTest.setUserDeviceCode(map.get("DEVICE_CODE")==null?null:map.get("DEVICE_CODE").toString());
		rateTest.setUserDeviceId(map.get("DEVICE_ID")==null?null:new BigDecimal(map.get("DEVICE_ID").toString()));
		rateTest.setUserId(map.get("USER_ID")==null?null:new BigDecimal(map.get("USER_ID").toString()));
		rateTest.setUserName(map.get("USER_NAME")==null?null:map.get("USER_NAME").toString());
		rateTest.setTestSourceId(new BigDecimal(map.get("TEST_ID").toString()));
		bs.addHeartRateTest(rateTest);
		
		TestResult tresult2 = new TestResult();
		tresult2.setTestId(rateTest.getTestId());
		tresult2.setTestType("5");
		bs.addTestResult(tresult2);
		
		testResult = getResultOnly(map.get("USER_ID").toString(),
				map.get("HEART_RATE").toString(),"XL_SUB",null,null,
				"XL",null,map.get("INPUT_MODE")==null?null:map.get("INPUT_MODE").toString(),tresult2.getTestResultId());
	}
	
	@Transactional
	public void subXTResult(Map<String, Object> map){
		TestResult tresult = new TestResult();
		tresult.setTestId(new BigDecimal(map.get("TEST_ID").toString()));
		tresult.setTestType("2");
		bs.addTestResult(tresult);
		
		TestResultMain testResult = getResultOnly(map.get("USER_ID").toString(),
				map.get("TEST_VALUE").toString(),"XT_SUB",null,null,
				"XT",map.get("TEST_TIME_ID")==null?null:map.get("TEST_TIME_ID").toString(),
						map.get("INPUT_MODE")==null?null:map.get("INPUT_MODE").toString(),tresult.getTestResultId());
	}
	
	@HttpMethod
	public void buildNewResult(){
		//血压 
//		List<Map<String, Object>> hypers = bs.getNoTestResultRecord("user_hypertension_test");
//		
//		for(Map<String, Object> map : hypers){
//			subHyperResult(map);
//		}

		//血糖
		List<Map<String, Object>> sugars = bs.getNoTestResultRecord("user_blood_sugar_test");
		
		for(Map<String, Object> map : sugars){
			subXTResult(map);
		}
	}
}
