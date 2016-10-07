/**
 *
 */
package com.unique.mb.service;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.CollectionClass;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.util.EcgViewUtil;
import com.unique.core.util.StringUtil;
import com.unique.mb.dao.HyperTestDao;
import com.unique.mb.dao.UserBloodSugarTestDao;
import com.unique.mb.dao.UserElectrocardioTestDao;
import com.unique.mb.po.TestResult;
import com.unique.mb.po.TestResultDet;
import com.unique.mb.po.TestResultMain;
import com.unique.mb.po.UserElectrocardioResult;
import com.unique.mb.po.UserElectrocardioTest;
import com.unique.mb.po.UserElectrocardioTestDet;
import com.unique.mb.po.UserHeartRateTest;
import com.unique.mb.po.UserHypertensionTest;
import com.unique.mb.po.UserTestByTime;
import com.unique.mb.po.UserTestResult;
import com.unique.mb.po.UserTestTime;
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
 * 创建日期:2015年11月18日上午10:04:56
 * 修改日期:2015年11月18日上午10:04:56
 */
@Service("userElectrocardioTestService")
public class UserElectrocardioTestServiceImpl implements UserElectrocardioTestService{
	@Resource
	private UserElectrocardioTestDao userElectrocardioTestDao;
	@Resource
	private SurveyDao surveyDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private UserDao userDao;
	@Resource
	private HyperTestDao hyperTestDao;
	@Resource
	private UserBloodSugarTestDao userBloodSugarTestDao;
	@Resource
	private UserBloodSugarTestService userBloodSugarTestService;
	@Resource(name="userBloodSugarTestDao")
	UserBloodSugarTestDao bs;

	
	/**
	 * 心电添加数据接口
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月20日上午10:22:59
	 * 修改日期:2015年11月20日上午10:22:59
	 * @param userId
	 * @param time
	 * @param periodId
	 * @param inputMode
	 * @param sourceCode
	 * @param deviceCode
	 * @param deviceToken
	 * @param isTwo
	 * @param HR
	 * @param QRS
	 * @param PR
	 * @param QT
	 * @param QTC
	 * @param P_AXIS
	 * @param QRS_AXIS
	 * @param T_AXIS
	 * @param RV5
	 * @param SV1
	 * @param RV5_AND_SV1
	 * @param PWidth
	 * @param RR
	 * @param dets
	 * @param results
	 * @return
	 */
	@HttpMethod
	@Transactional
	public Map<String, Object> addElectrocardioTest(
			@NotNull String userId,
			String time,
			@NotNull String inputMode,
			String sourceCode,
			String deviceCode,
			String deviceToken,
			String isTwo,
			@NotNull Integer HR,@NotNull Integer QRS, Integer PR, Integer QT, Integer QTC,
			 Integer P_AXIS,  Integer QRS_AXIS, Integer T_AXIS, Double RV5,
			 Double SV1, Double RV5_AND_SV1, Double PWidth,Integer RR,
			@NotNull @CollectionClass(UserElectrocardioTestDet.class) List<UserElectrocardioTestDet> dets,
			@NotNull List<String> results
			){
		SurveyKpi kpi = surveyDao.getKpiByCode("XD");
		System.out.println(JSONArray.fromObject(dets));
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		AmsUser user = userDao.getUserById(userId);
		if(user==null){
			result.put("retCode", 3);
			result.put("retMessage", "没有找到该患者");
			return result;
		}
		UserElectrocardioTest test = new UserElectrocardioTest();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			if(time==null){
				date = new Date();
			}else{
				date = sdf.parse(time);
			}
			test.setTestTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		test.setDeviceToken(deviceToken);
		test.setHr(HR==null?null:new BigDecimal(HR));
		test.setInputMode(inputMode);
		test.setpAxis(P_AXIS==null?null:new BigDecimal(P_AXIS));
		test.setPr(PR==null?null:new BigDecimal(PR));
		test.setPwidth(PWidth==null?null:new BigDecimal(PWidth));
		test.setQrs(QRS==null?null:new BigDecimal(QRS));
		test.setQt(QT==null?null:new BigDecimal(QT));
		test.setQtc(QTC==null?null:new BigDecimal(QTC));
		test.setRr(RR==null?null:new BigDecimal(RR));
		test.setRv5(RV5==null?null:new BigDecimal(RV5));
		test.setRv5AndSv1(RV5_AND_SV1==null?null:new BigDecimal(RV5_AND_SV1));
		if(sourceCode!=null){
			SourceType stype = orderDao.getSourceByCode(sourceCode);
			test.setSourceId(stype.getSourceId());
		}
		test.setStatus("C");
		test.setSv1(SV1==null?null:new BigDecimal(SV1));
		test.settAxis(T_AXIS==null?null:new BigDecimal(T_AXIS));
		if(deviceCode!=null){
			UserDevice device = userDao.getUserDeviceByCode(deviceCode);
			test.setUserDeviceCode(deviceCode);
			test.setUserDeviceId(device.getUserDeviceId());
		}
		test.setUserId(user.getUserId());
		test.setUserName(user.getUserName());
		userElectrocardioTestDao.addElectrocardioTest(test);
		
		/***************添加单独心率数据*****************/
		UserHeartRateTest rateTest = new UserHeartRateTest();
		rateTest.setDeviceToken(deviceToken);
		rateTest.setInputMode(inputMode);
		rateTest.setIsAlarm("N");
		rateTest.setRate(test.getHr());
		rateTest.setSourceId(test.getSourceId());
		rateTest.setStatus("C");
		rateTest.setTestTime(test.getTestTime());
		rateTest.setTestTimeId(test.getTestTimeId());
		if(dets.size()==12){
			//12导
			rateTest.setTestType("3");
		}else{
			//单导
			rateTest.setTestType("2");
		}
		rateTest.setUserDeviceCode(test.getUserDeviceCode());
		rateTest.setUserDeviceId(test.getUserDeviceId());
		rateTest.setUserId(test.getUserId());
		rateTest.setUserName(test.getUserName());
		rateTest.setTestSourceId(test.getTestId());
		userBloodSugarTestDao.addHeartRateTest(rateTest);
		
		/******************添加心电明细********************/
		for(UserElectrocardioTestDet det : dets){
			det.setTestId(test.getTestId());
			userElectrocardioTestDao.addElectrocardioTestDet(det);
		}
		
		/*******************添加心电结果***********************/
		for(String r : results){
			UserElectrocardioResult elecResult = new UserElectrocardioResult();
			elecResult.setContext(r);
			elecResult.setTestId(test.getTestId());
			userElectrocardioTestDao.addElectrocardioResult(elecResult);
		}
		
		/**
		 * 添加结果表数据
		 */
		TestResult xltestResult = new TestResult();
		xltestResult.setTestId(rateTest.getTestId());
		xltestResult.setTestType("5");
		userBloodSugarTestDao.addTestResult(xltestResult);

		
		/******************** 心率结果 ************************/
		if(test.getHr()!=null){
			TestResultMain resultMain = userBloodSugarTestService.getResult(userId, 
					test.getHr().toString(),"XL_SUB", null, null, "XL",
					null, inputMode, isTwo, test.getTestId(), xltestResult.getTestResultId());
			result.put("xlResult", resultMain);
		}

		/**
		 * 添加结果表数据
		 */
		TestResult testResult = new TestResult();
		testResult.setTestId(test.getTestId());
		testResult.setTestType("4");
		userBloodSugarTestDao.addTestResult(testResult);
		
		for(String r : results){
			TestResultDet resultDet = new TestResultDet();
			resultDet.setDoctorAlertCon(r);
			resultDet.setTestResultId(testResult.getTestResultId());
			resultDet.setUserAlertResult(r);
			userBloodSugarTestDao.addTestResultDet(resultDet);
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
		bs.addUserResultTest(tResult);
		
		result.put("retCode", 0);
		result.put("retMessage", "添加成功");
		return result;
	}
	
	/**
	 * 根据心率检测ID获取心率详情和结果
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月23日下午3:22:42
	 * 修改日期:2015年11月23日下午3:22:42
	 * @param testId
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getHeartInfo(@NotNull String testId){
		HashMap<String, Object> result = new HashMap<String, Object>();
		//获得心率实体
		UserHeartRateTest heartRate = userBloodSugarTestDao.getUserHeartRateTest(testId);
		int type = Integer.parseInt(heartRate.getTestType());
		TestResult tresult = userBloodSugarTestDao.getTestResultInfo(heartRate.getTestId().toString(), "5");
		TestResult otherResult = null;
		switch (type) {
		case 1:
			//血压计
			otherResult = userBloodSugarTestDao.getTestResultInfo(heartRate.getTestId().toString(), "1");
			UserHypertensionTest userHyper = hyperTestDao.getHypertensionInfo(heartRate.getTestSourceId().toString());
			result.put("xy", userHyper);
			break;
		case 2:
			//单导
			otherResult = userBloodSugarTestDao.getTestResultInfo(heartRate.getTestId().toString(), "4");
			break;
		case 3:
			//12导
			otherResult = userBloodSugarTestDao.getTestResultInfo(heartRate.getTestId().toString(), "4");
			break;
		default:
			break;
		}
		
		result.put("heartRate", heartRate);
		result.put("heartResult", tresult);
		result.put("otherResult", otherResult);
		return result;
	}
	
	/**
	 * 获取心电检测详情
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月23日下午4:32:23
	 * 修改日期:2015年11月23日下午4:32:23
	 * @param testId
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getUserElectrocardioTestById(String testId){
		HashMap<String, Object> result = new HashMap<String, Object>();
		UserElectrocardioTest test = userElectrocardioTestDao.getElectrocardioTestById(testId);
		TestResult tresult = userBloodSugarTestDao.getTestResultInfo(test.getTestId().toString(), "4");
		result.put("record", test);
		result.put("testResult", tresult);
		return result;
	}
	
	/**
	 * 获取心率数据分页
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月24日上午11:33:31
	 * 修改日期:2015年11月24日上午11:33:31
	 * @param userId
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getHeartRatePage(@NotNull String userId,Long startRow,Long rows){
		HashMap<String, Object> result = new HashMap<String, Object>();
		Long endRows= null;
		if(startRow!=null && rows!=null){
			endRows = startRow + rows;
		}
		
		List<UserHeartRateTest> list = userElectrocardioTestDao.getHeartRatePage(userId, startRow, endRows);
		long count = userElectrocardioTestDao.getHeartRatePageCount(userId);
		result.put("list", list);
		result.put("count", count);
		return result;
	}
	
	
	/**
	 * 按照时间分割类型查询心率
	 * @param type 1 日，2 周，3 月
	 * @param time 最后一天的时间 yyyy-mm-dd
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getHeartRateTestByTime(@NotNull Integer type,
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
		String timeKpiCode = "XL";
		
		List<UserTestTime> testTime = bs.selectTestTime(timeKpiCode);
		List<UserTestByTime> ls = null;
		if(type==1){
			//按日划分要稍微特殊点
			ls= userElectrocardioTestDao.getUserHeartRateTestByTime(type, nowTime, userId,null);
			result.put("ls", ls);
		}else{
			if(testTimeId==null){
				for(UserTestTime testt : testTime){
						//血压心率
					ls= userElectrocardioTestDao.getUserHeartRateTestByTime(type, nowTime, userId,testt.getTestTimeId().toString());
					testt.setTestTimes(ls);
				}
				result.put("times", testTime);
			}else{
				ls= userElectrocardioTestDao.getUserHeartRateTestByTime(type, nowTime, userId,testTimeId);
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
	 * 根据检测明细ID绘制图片
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年12月2日下午1:41:26
	 * 修改日期:2015年12月2日下午1:41:26
	 * @param testDetId
	 * @return
	 */
	public BufferedImage getElectrocardioImg(String testDetId){
		UserElectrocardioTestDet det = userElectrocardioTestDao.getElectrocardioTestDetByDetId(testDetId);
		if(!StringUtil.isEmpty(det.getBitmap())){
			String[] ecgs = det.getBitmap().split(",");
			short[] ecgShorts = new short[ecgs.length];
			for(int i=0;i< ecgs.length;i++){
				ecgShorts[i] = Short.parseShort(ecgs[i]);
			}
			
	    	EcgViewUtil util = new EcgViewUtil();
	    	long l1 = (400L * 1000L * ecgShorts.length);
	    	long l2 = 40 * 40 * det.getRate();
	    	util.init((int)(l1/ l2), 400,det.getRate());
	    	util.updatePaint(0,ecgShorts);
	    	return util.img;
		}
		return null;
	}
}
