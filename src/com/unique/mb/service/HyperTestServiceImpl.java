package com.unique.mb.service;

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
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.annotation.ParamNoNull;
import com.unique.core.util.StringUtil;
import com.unique.mb.dao.HyperTestDao;
import com.unique.mb.dao.UserBloodSugarTestDao;
import com.unique.mb.po.TestResult;
import com.unique.mb.po.TestResultMain;
import com.unique.mb.po.UserBloodSugarTest;
import com.unique.mb.po.UserBloodSugarTestByTime;
import com.unique.mb.po.UserBloodSugarTestDto;
import com.unique.mb.po.UserHeartRateTest;
import com.unique.mb.po.UserHypertensionTest;
import com.unique.mb.po.UserHypertensionTestByTime;
import com.unique.mb.po.UserHypertensionTestDto;
import com.unique.mb.po.UserTestByTime;
import com.unique.mb.po.UserTestResult;
import com.unique.mb.po.UserTestTime;
import com.unique.order.dao.OrderDao;
import com.unique.order.po.SourceType;
import com.unique.plan.dao.HealthyTestPlanDetRuleDao;
import com.unique.plan.po.HealthyTestPlanDetRule;
import com.unique.plan.po.TestItemRuleDet;
import com.unique.survey.dao.SurveyDao;
import com.unique.survey.po.SurveyKpi;
import com.unique.user.dao.UserDao;
import com.unique.user.po.AmsUser;
import com.unique.user.po.UserDevice;
import com.unique.user.webservice.WebUserService;

@Service("hyperTestService")
public class HyperTestServiceImpl implements HyperTestService{
	@Resource
	private HyperTestDao hyperTestDao;
	@Resource(name="userBloodSugarTestDao")
	UserBloodSugarTestDao bs;
	@Resource(name="healthyTestPlanDetRuleDao")
	HealthyTestPlanDetRuleDao ht;
	@Resource
	private UserDao userDao;
	@Resource
	private WebUserService webUserService;
	@Resource
	private SurveyDao surveyDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private UserBloodSugarTestService userBloodSugarTestService;
	@Resource
	UserBloodSugarTestDao userBloodSugarTestDao;
	/**
	 * 血压预警
	 * 创建人:敖资权
	 * 修改人:敖资权
	 * 创建日期:2015年10月19日下午4:44:37
	 * 修改日期:2015年10月19日下午4:44:37
	 * @param type
	 * @param userId
	 * @param time
	 * @return
	 */
	@HttpMethod
	@Transactional
	public HashMap<String, Object>  addUserHypertension(String userId,
			@NotNull String highTension,
			@NotNull String lowTension,@NotNull String periodId){
		HashMap<String, Object> result = new HashMap<String, Object>();
		String[] user=new String[]{userId};
		List<HealthyTestPlanDetRule> selectHypertensionUser = ht.selectHypertensionUser(userId,highTension,lowTension,periodId);
		//小于1=没有用户数据： 直接进入公共方式
		if(selectHypertensionUser.size()<1){
		List<TestItemRuleDet> selectHypertensionCommon = ht.selectHypertensionCommon(highTension,lowTension,periodId);
		if(selectHypertensionCommon.size()==2){
			//两条数据链接方式不同(一条为“和”，一条为“或”)，取“或”
			if(!selectHypertensionCommon.get(0).getOneTwoRelation().equals(selectHypertensionCommon.get(0).getOneTwoRelation())){
				for(TestItemRuleDet k:selectHypertensionCommon){
					if("或".equals(k.getOneTwoRelation())){
						result.put("conclusion",k.getUserAlertResult());
						result.put("level", k.getAlertGrade());
						webUserService.sendMsg("-3", k.getUserAlertResult(), "json", user);
						}
					}
				}else{
					// 两条数据都为“或” or 都为“和” 直接取一条
					result.put("conclusion",selectHypertensionCommon.get(0).getUserAlertResult());
					result.put("level", selectHypertensionCommon.get(0).getAlertGrade());
					webUserService.sendMsg("-3", selectHypertensionCommon.get(0).getUserAlertResult(), "json", user);
				}
		}else{
			//只有一条数据的情况直接获取预警
				result.put("conclusion",selectHypertensionCommon.get(0).getUserAlertResult());
				result.put("level", selectHypertensionCommon.get(0).getAlertGrade());
				webUserService.sendMsg("-3", selectHypertensionCommon.get(0).getUserAlertResult(), "json", user);
			}
	}else{
	//进入：个人方式
		if(selectHypertensionUser.size()==2){
			//两条数据链接方式不同，取“或”
			if(!selectHypertensionUser.get(0).getOneTwoRelation().equals(selectHypertensionUser.get(0).getOneTwoRelation())){
				for(HealthyTestPlanDetRule k:selectHypertensionUser){
					if("或".equals(k.getOneTwoRelation())){
						result.put("conclusion",k.getUserAlertResult());
						result.put("level", k.getAlertGrade());
						webUserService.sendMsg("-3", k.getUserAlertResult(), "json", user);
						}
					}
				}else{
					// 两条数据都为“或” or 都为“和” 直接去一条
					result.put("conclusion",selectHypertensionUser.get(0).getUserAlertResult());
					result.put("level", selectHypertensionUser.get(0).getAlertGrade());
					webUserService.sendMsg("-3", selectHypertensionUser.get(0).getUserAlertResult(), "json", user);
				}
		}else{
			//只有一条数据的情况直接获取预警
				result.put("conclusion",selectHypertensionUser.get(0).getUserAlertResult());
				result.put("level", selectHypertensionUser.get(0).getAlertGrade());
				webUserService.sendMsg("-3", selectHypertensionUser.get(0).getUserAlertResult(), "json", user);
			}
		}
		return result;
	}
	
	/**
	 * 查询测试记录分页
	 */
	@HttpMethod
	@Override
	public List<UserHypertensionTest> getTestPage(@NotNull String userId,Long startRow,Long rows){
		Long endRows= null;
		if(startRow!=null && rows!=null){
			endRows = startRow + rows;
		}
		List<UserHypertensionTest> result = hyperTestDao.getTestPage(userId, startRow, endRows);
		return result;
	}
	/**
	 * 查询测试记录分页（总数）
	 */
	@HttpMethod
	@Override
	public HashMap<String, Object> getTestPageCount(@NotNull String userId){
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("count", hyperTestDao.getTestPageCount(userId));
		return result;
	}
	
	/**
	 * 高血压检测
	 * @param userId 
	 * @param sourceId 来源ID
	 * @param deviceId 设备ID
	 * @param highTension 高血压
	 * @param lowTension 低血压
	 * @param heartRate 心率
	 * @param testResult 测试结果
	 * @param inputMode 输入类型 1手工2检测
	 * @param deviceCode 检测设备编号
	 * @param deviceToken 手机TOKEN
	 */
	@HttpMethod
	@Transactional
	@Override
	public HashMap<String, Object> addTest(
			@ParamNoNull String userId,
			@ParamNoNull String inputMode,
			String sourceCode,
			@ParamNoNull Double highTension,
			@ParamNoNull Double lowTension,
			@ParamNoNull Double heartRate,
			String deviceCode,
			String deviceToken,
			String testTime,
			String periodId,
			String isTwo
			){
		
		SurveyKpi kpi = surveyDao.getKpiByCode("XY");
		SurveyKpi xlKpi = surveyDao.getKpiByCode("XL");
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		AmsUser user = userDao.getUserById(userId);
		
		UserHypertensionTest test = new UserHypertensionTest();
		test.setDeviceToken(deviceToken);
		test.setHeartRate(heartRate==null?null:new BigDecimal(heartRate));
		test.setHighTension(highTension==null?null:new BigDecimal(highTension));
		test.setInputMode(inputMode);
		test.setLowTension(lowTension==null?null:new BigDecimal(lowTension));
		if(deviceCode!=null){
			UserDevice device = userDao.getUserDeviceByCode(deviceCode);
			test.setUserDeviceCode(device.getUserDeviceCode());
			test.setUserDeviceId(device.getUserDeviceId());
		}
		if(sourceCode!=null){
			SourceType stype = orderDao.getSourceByCode(sourceCode);
//			if(stype!=null){
				test.setSourceId(stype.getSourceId());
//			}
		}
		test.setStatus("C");
		Date testTimeDat = null;
		if(testTime==null){
			testTimeDat = new Date();
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {
				testTimeDat = sdf.parse(testTime);
			} catch (ParseException e) {
				testTimeDat = new Date();
			}
		}
		
		if(periodId==null){
			//根据测试时间获取时间段ID
			periodId = hyperTestDao.getTestTimeIdByTime(testTimeDat, kpi.getKpiCode(),userId);
		}
		if(periodId==null){
//			resultMap.put("retCode", 3);
//			resultMap.put("retMessage", "该时间段未找到");
//			return resultMap;
		}else{
			test.setTestTimeId(new BigDecimal(periodId));
		}
		test.setTestTime(testTimeDat);
		test.setUserId(new BigDecimal(userId));
		test.setUserName(user.getUserName());
		
		hyperTestDao.addTest(test);
		
		TestResult tresult = new TestResult();
		tresult.setTestId(test.getTestId());
		tresult.setTestType("1");
		bs.addTestResult(tresult);
		//血压
		TestResultMain testResult = userBloodSugarTestService.getResult(userId,highTension.toString(),"SSY",lowTension.toString(),"SZY", "XY",periodId,inputMode,isTwo,test.getTestId(),tresult.getTestResultId());
		if(testResult!=null){
			//有测量结果
			resultMap.put("testResult", testResult);
			UserTestResult tResult = new UserTestResult();
			tResult.setInputMode(inputMode);
			tResult.setEqupId(kpi.getEqupId());
			tResult.setResultLift(null);
			tResult.setSourceId(test.getTestId());
			tResult.setStatus("C");
			tResult.setTestItemReference(null);
			tResult.setTestItemUnit(kpi.getUnit());
			tResult.setTestItmeName(kpi.getKpiTitle());
			tResult.setTestResult(testResult.getUserAlertResult());
			tResult.setTestTime(test.getTestTime());
			tResult.setUserDeviceCode(deviceCode);
			tResult.setUserDeviceId(test.getUserDeviceId());
			tResult.setUserId(user.getUserId());
			tResult.setUserName(user.getUserName());
			bs.addUserResultTest(tResult);
			
			test.setTestResult(tResult.getTestResult());
			hyperTestDao.updateUserHypertensionTestById(test);
		}
		
		
		UserHeartRateTest rateTest = new UserHeartRateTest();
		rateTest.setDeviceToken(deviceToken);
		rateTest.setInputMode(inputMode);
		rateTest.setIsAlarm(test.getIsAlarm());
		rateTest.setRate(test.getHeartRate());
		rateTest.setSourceId(test.getSourceId());
		rateTest.setStatus("C");
		rateTest.setTestTime(test.getTestTime());
		rateTest.setTestTimeId(test.getTestTimeId());
		rateTest.setTestType("1");
		rateTest.setUserDeviceCode(test.getUserDeviceCode());
		rateTest.setUserDeviceId(test.getUserDeviceId());
		rateTest.setUserId(test.getUserId());
		rateTest.setUserName(test.getUserName());
		rateTest.setTestSourceId(test.getTestId());
		userBloodSugarTestDao.addHeartRateTest(rateTest);
		//心率
		TestResult tresultxl = new TestResult();
		tresultxl.setTestId(rateTest.getTestId());
		tresultxl.setTestType("5");
		bs.addTestResult(tresultxl);
		TestResultMain xlResult = userBloodSugarTestService.getResult(userId,heartRate.toString(),"XL_SUB",null,null, "XL",periodId,inputMode,isTwo,test.getTestId(),tresultxl.getTestResultId());
		
		
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
		if(xlResult!=null){
			//有测量结果
			tResult.setTestResult(xlResult.getUserAlertResult());
			bs.addUserResultTest(tResult);
		}
//		UserTestResult result = new UserTestResult();
//		EqupType equpType = hyperTestDao.getEqIdByTableName("USER_HYPERTENSION_TEST");
//		if(equpType!=null){
//			result.setEqupId(equpType.getEqupId());
//		}
//		result.setUserDeviceId(deviceId==null?null:new BigDecimal(deviceId));
//		result.setSourceId(test.getTestId());
//		result.setTestTime(testTimeDat);
//		result.setUserName(user.getUserName());
//		result.setTestItmeName(equpType.getEqupName());
//		result.setStatus("C");
//		result.setUserId(user.getUserId());
//		hyperTestDao.addTestResult(result);
		return resultMap;
	}
	
	
	@HttpMethod
	@Override
	public HashMap<String, Object> getUserHypertensionTestDays(@NotNull String userId) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<UserBloodSugarTest> userBloodSugarTestDays = bs.getUserBloodSugarTestDays(userId);
		List<UserHypertensionTest> userHypertensionTestDays = hyperTestDao.getUserHypertensionTestDays(userId);
		result.put("bloodSugar", userBloodSugarTestDays);
		result.put("hypertension", userHypertensionTestDays);
		return result;
	}
	
	@HttpMethod
	@Override
	public HashMap<String, Object> getUserHypertensionTestWeeksAndMonth(
			@NotNull String userId, @NotNull String type) {
		
		HashMap<String, Object> result = new HashMap<String, Object>(2);
		HashMap<String, Object> bh = new HashMap<String, Object>();
		//参数值必须是weeks 或 month
		if(!("weeks".equals(type) || "month".equals(type))){
			result.put("retCode", 1);
			result.put("data","type参数值必须是weeks或 month");
		}else{
			result.put("retCode", 0);
			List<UserBloodSugarTestDto> userBloodSugarTestWeeksAndMonth = bs.getUserBloodSugarTestWeeksAndMonth(userId, type);
			List<UserHypertensionTestDto> userHypertensionTestWeeksAndMonth = hyperTestDao.getUserHypertensionTestWeeksAndMonth(userId,type);
			bh.put("bloodSugar", userBloodSugarTestWeeksAndMonth);
			bh.put("hypertension", userHypertensionTestWeeksAndMonth);
			result.put("data",bh);
		}
		return result;
	}
	
	/**
	 * 按照时间分割类型查询测试记录
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月2日上午11:39:47
	 * 修改日期:2015年9月2日上午11:39:47
	 * @param type 1 日，2 周，3 月, 4 日(平均值方式)，5 周(平均值方式)，6 月(平均值方式)
	 * @param time 最后一天的时间 yyyy-mm-dd
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object>  getUserHypertensionTestByTime(@NotNull Integer type,@NotNull String userId,String time){
		Date nowTime = null;
		HashMap<String, Object> result = new HashMap<String, Object>();
		String[] user=new String[]{userId};
		if(StringUtil.isEmpty(time)){
			nowTime = new Date();
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				nowTime = sdf.parse(time);
			} catch (ParseException e) {
				nowTime = null;
			}
		}
		List<UserHypertensionTestByTime> tests = null;
		List<UserBloodSugarTestByTime> tests2 = null;
		if(type>3){
			tests  = hyperTestDao.getUserHypertensionTestByTimeAvg(type-3, nowTime,userId);
			tests2 = bs.getUserBloodSugarTestByTimeAvg(type-3, nowTime, userId,"");
		}else{
			tests  =  hyperTestDao.getUserHypertensionTestByTime(type, nowTime,userId);
			tests2 =  bs.getUserBloodSugarTestByTime(type, nowTime, userId,"");
		}
		if(tests==null){
			tests = new ArrayList<UserHypertensionTestByTime>();
		}
		if(tests2==null){
			tests2 = new ArrayList<UserBloodSugarTestByTime>();
		}
		result.put("hypertension",tests);
		result.put("bloodSugar", tests2);
		result.put("timeList", bs.selectTestTime(null));
		return result;
	}
	
	/**
	 * 根据时间段KPI获取对应的检测列表
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月20日上午10:40:35
	 * 修改日期:2015年10月20日上午10:40:35
	 * @param type 1 日，2 周，3 月
	 * @param kpiCode XT:血糖，SSY:搜索压，SZY:舒张压
	 * @param userId 用户ID
	 * @param time 开始时间:yyyy-MM-dd
	 * @param testTimeId 时间段ID
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object>  getTestByTime(@NotNull Integer type,
			@NotNull String kpiCode,
			@NotNull String userId,
			String time,String testTimeId){
		Date nowTime = null;
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(StringUtil.isEmpty(time)){
			nowTime = new Date();
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				nowTime = sdf.parse(time);
			} catch (ParseException e) {
				nowTime = null;
			}
		}
		
		
		String timeKpiCode = "";
		if(kpiCode.equals("XT")){
			timeKpiCode = "XT";
		}else{
			timeKpiCode = "XY";
		}
		
		List<UserTestTime> testTime = bs.selectTestTime(timeKpiCode);
		List<UserTestByTime> ls = null;
		if(type==1){
			//按日划分要稍微特殊点
			
			if("XT".equalsIgnoreCase(kpiCode)){
				//血糖,时间段
				ls= hyperTestDao.getUserTestXTByTime(type, nowTime, userId, kpiCode,null,testTime);
				result.put("times", testTime);
			}else{
				//血压心率
				ls= hyperTestDao.getUserTestXLXYByTime(type, nowTime, userId, kpiCode,null);
			}
			result.put("ls", ls);
		}else{
			if(testTimeId==null){
				for(UserTestTime testt : testTime){
					if("XT".equalsIgnoreCase(kpiCode)){
						//血糖
						ls= hyperTestDao.getUserTestXTByTime(type, nowTime, userId, kpiCode,testt.getTestTimeId().toString(),null);
					}else{
						//血压心率
						ls= hyperTestDao.getUserTestXLXYByTime(type, nowTime, userId, kpiCode,testt.getTestTimeId().toString());
					}
					testt.setTestTimes(ls);
				}
				result.put("times", testTime);
			}else{
				ls= hyperTestDao.getUserTestXTByTime(type, nowTime, userId, kpiCode,testTimeId,null);
				for(UserTestTime testt : testTime){
					if(testt.getTestTimeId().toString().equals(testTimeId)){
						testt.setTestTimes(ls);
						result.put("times", testt);
						break;
					}
				}
				
			}
		}
		return result;
	}
	
	/**
	 * 根据KPI获取对应的展示时间段
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月20日上午10:52:34
	 * 修改日期:2015年10月20日上午10:52:34
	 * @param kpiCode
	 * @return
	 */
	@HttpMethod
	public List<UserTestTime> getTestTimeByKpi(String kpiCode){
		return bs.selectTestTime(kpiCode);
	}
	
	/**
	 * 获取血压检测详情
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月23日下午4:32:23
	 * 修改日期:2015年11月23日下午4:32:23
	 * @param testId
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getHyperTensionTestById(String testId){
		HashMap<String, Object> result = new HashMap<String, Object>();
		UserHypertensionTest test = hyperTestDao.getHypertensionInfo(testId);
		TestResult tresult = userBloodSugarTestDao.getTestResultInfo(test.getTestId().toString(), "1");
		result.put("record", test);
		result.put("testResult", tresult);
		return result;
	}
	
	@HttpMethod
	@Override
	@Transactional
	public HashMap<String, Object> handlerEarlyWarn(@NotNull String testResultDetId,
			@NotNull String doWay) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {

			hyperTestDao.handlerEarlyWarn(testResultDetId,doWay);
			
			result.put("retCode", 0);
			result.put("retMessage", "处理成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("retCode", -1);
			result.put("retMessage", "发生异常，处理失败！");
		}
		return result;
	}
	
	/**
	 * 按周获取血压数据
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月26日上午10:03:51
	 * 修改日期:2015年11月26日上午10:03:51
	 * @param userId
	 * @param nowdate 本周任意一天,格式：yyyy-MM-dd,不传默认为本周
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getHypertensionByWeek(@NotNull String userId,String nowdate){
		HashMap<String, Object> result=new HashMap<String, Object>();
		//获取时间段
		List<UserTestTime> testTime = bs.selectTestTime("XY");
		
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
		
		List<Map<String, Object>> list = hyperTestDao.getHypertensionByWeek(userId, sdf.format(cal.getTime()));
		result.put("testTime", testTime);
		result.put("list", list);
		return result;
	}
}
