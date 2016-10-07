/**
 *
 */
package com.unique.mb.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.util.StringUtil;
import com.unique.mb.dao.UserBloodLipidTestDao;
import com.unique.mb.dao.UserBloodSugarTestDao;
import com.unique.mb.po.TestResult;
import com.unique.mb.po.TestResultMain;
import com.unique.mb.po.UserBloodLipidTest;
import com.unique.mb.po.UserTestByTime;
import com.unique.mb.po.UserTestByTimeBloodLipid;
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
 * 创建日期:2015年11月18日下午1:35:30
 * 修改日期:2015年11月18日下午1:35:30
 */
@Service("userBloodLipidTestService")
public class UserBloodLipidTestServiceImpl implements UserBloodLipidTestService {
	@Resource
	private UserBloodLipidTestDao userBloodLipidTestDao;
	@Resource
	private UserBloodSugarTestService userBloodSugarTestService;
	@Resource
	private SurveyDao surveyDao;
	@Resource
	private UserDao userDao;
	@Resource
	private OrderDao orderDao;
	@Resource(name="userBloodSugarTestDao")
	UserBloodSugarTestDao bs;
	
	/**
	 * 添加血脂数据
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月18日下午3:22:55
	 * 修改日期:2015年11月18日下午3:22:55
	 * @param userId
	 * @param time
	 * @param periodId
	 * @param inputMode
	 * @param sourceCode
	 * @param deviceCode
	 * @param deviceToken
	 * @param isTwo
	 * @param TG
	 * @param TC
	 * @param HDL_C
	 * @param LDL_C
	 * @return
	 */
	@HttpMethod
	@Transactional
	public Map<String, Object> addElectrocardioLipidTest(
			@NotNull String userId,
			String time,
			@NotNull String inputMode,
			String sourceCode,
			String deviceCode,
			String deviceToken,
			String isTwo,
			@NotNull Double TG,
			@NotNull Double TC,
			@NotNull Double HDL_C,
			@NotNull Double LDL_C
			){
		SurveyKpi kpi = surveyDao.getKpiByCode("XZ");
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		AmsUser user = userDao.getUserById(userId);
		UserBloodLipidTest test = new UserBloodLipidTest();
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
		test.setHdlC(new BigDecimal(HDL_C));
		test.setInputMode(inputMode);
		test.setLdlC(new BigDecimal(LDL_C));
		if(sourceCode!=null){
			SourceType stype = orderDao.getSourceByCode(sourceCode);
			test.setSourceId(stype.getSourceId());
		}
		test.setTg(new BigDecimal(TG));
		if(deviceCode!=null){
			UserDevice device = userDao.getUserDeviceByCode(deviceCode);
			test.setUserDeviceCode(deviceCode);
			test.setUserDeviceId(device.getUserDeviceId());
		}
		test.setUserId(user.getUserId());
		test.setUserName(user.getUserName());
		test.setTcDivisionHdlC(new BigDecimal(TC/HDL_C));
		//添加血脂检测结果
		userBloodLipidTestDao.addUserBloodLipidTest(test);
		
		TestResult tresult = new TestResult();
		tresult.setTestId(test.getTestId());
		tresult.setTestType("3");
		bs.addTestResult(tresult);
		/*****************TG*******************/
		TestResultMain tgTestResult = userBloodSugarTestService.getResult(userId,TC.toString(),"TG",null,null,"XZ",null,inputMode,isTwo,test.getTestId(),tresult.getTestResultId());
		/*****************TC*******************/
		TestResultMain tcTestResult = userBloodSugarTestService.getResult(userId,TC.toString(),"TC",null,null,"XZ",null,inputMode,isTwo,test.getTestId(),tresult.getTestResultId());
		/*****************HDL_C*******************/
		TestResultMain hdlCTestResult = userBloodSugarTestService.getResult(userId,TC.toString(),"HDL_C",null,null,"XZ",null,inputMode,isTwo,test.getTestId(),tresult.getTestResultId());
		/*****************LDL_C*******************/
		TestResultMain ldlCTestResult = userBloodSugarTestService.getResult(userId,TC.toString(),"LDL_C",null,null,"XZ",null,inputMode,isTwo,test.getTestId(),tresult.getTestResultId());
		
		//有测量结果
		result.put("tgTestResult", tgTestResult);
		result.put("tcTestResult", tcTestResult);
		result.put("hdlCTestResult", hdlCTestResult);
		result.put("ldlCTestResult", ldlCTestResult);
		
		
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
		return result;
	}
	
	/**
	 * 获取血脂分页
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月18日下午4:22:16
	 * 修改日期:2015年11月18日下午4:22:16
	 * @param userId
	 * @param startRow
	 * @param rows
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getBloodLipidByPage(String userId,Long startRow,Long rows){
		HashMap<String, Object> result = new HashMap<String, Object>();
		Long endRow = null;
		if(startRow!=null && rows!=null){
			endRow = startRow + rows;
		}
		
		List<UserBloodLipidTest> ls = userBloodLipidTestDao.getBloodLipidByPage(userId, startRow, endRow);
		long count = userBloodLipidTestDao.getBloodLipidByPageCount(userId);
		result.put("list", ls);
		result.put("count", count);
		return result;
	}
	
	
	/**
	 * 按照时间分割类型查询血脂
	 * @param type 1 日，2 周，3 月
	 * @param time 最后一天的时间 yyyy-mm-dd
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getBloodLipidTestByTime(@NotNull Integer type,
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
		List<UserTestByTimeBloodLipid> ls = null;
		ls = userBloodLipidTestDao.getBloodLipidTestByTime(type, nowTime, userId, null);
		result.put("list", ls);
		return result;
	}
}
