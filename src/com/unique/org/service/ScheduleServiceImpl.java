package com.unique.org.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;
import com.unique.alipay.util.UtilDate;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.annotation.ParamNoNull;
import com.unique.core.util.FileUtil;
import com.unique.core.util.StringUtil;
import com.unique.org.dao.DepDao;
import com.unique.org.dao.OrgDao;
import com.unique.org.dao.StaffDao;
import com.unique.org.po.Dep;
import com.unique.org.po.StaffType;
import com.unique.org.vo.DutyPeriodMap;
import com.unique.org.vo.ScheduleResultMap;
import com.unique.org.vo.StaffDuty;
import com.unique.org.vo.StaffDutyPeriod;
import com.unique.org.vo.bydate.DoctorVo;
import com.unique.org.vo.bydate.ScheduleVo;
import com.unique.org.vo.bydoctor.DoctorSchedule;
import com.unique.org.vo.bydoctor.ScheduleInfo;
import com.unique.org.vo.period.PeriodInfo;
import com.unique.reg.dao.RegDao;
import com.unique.reg.po.DutyPeriod;
import com.unique.reg.po.McDuty;
import com.unique.reg.po.Org;
import com.unique.reg.po.Staff;

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {
	@Resource
	private StaffDao staffDao;
	@Resource
	private RegDao regDao;
	@Resource
	private OrgDao orgDao;
	@Resource
	private DepDao depDao;

	// /图片服务器
	private static String PIC_SERVER = FileUtil.readProperties(
			"comservice.properties", "picServerOut");

	public static void main(String[] args) {
		// a>b
		System.out.println("08:02:00".compareTo("08:01:00") > 0);
//		String periodName = "08:00:00-10:00:00";
//		periodName = periodName.substring(periodName.lastIndexOf("-")+1);
//		System.out.println(periodName);
	}

	/**
	 * 过滤时间 比如 下午不能挂上午的号
	 * 
	 * @param queryList
	 * @return
	 */
	private List<ScheduleResultMap> filterDutyDate(
			List<ScheduleResultMap> queryList) {
		// 这种情况不处理
		if (queryList == null || queryList.isEmpty())
			return queryList;

		List<ScheduleResultMap> tempMap = new ArrayList<ScheduleResultMap>();
		// 当前时间的 年-月-日
		String currentDate = UtilDate.getDateToStr(new Date(), UtilDate.dtDate);

		String currentTime = UtilDate.getDateToStr(new Date(), "HH:mm:ss");//UtilDate.dtHm);//当前时间的 时分秒  如 12:00
		String currentTimeName = "";

		/*************** 上午挂下午 *******************************/
		// if(currentTime.compareTo("08:00")>=0 &&
		// "12:00".compareTo(currentTime)>=0){
		// currentTimeName = "上午"; //上午
		// }else{
		// currentTimeName = "下午"; //下午
		// }
		/*************** 上午挂下午 *******************************/

		for (ScheduleResultMap result : queryList) {
			String periodName = result.getPeriodName();
			//String periodId = result.getPeriodId();// 1 2 （上午） 3 4 （下午）
			periodName = StringUtil.isEmpty(periodName) ? "" : periodName.trim(); //08:00:00-10:00:00
			
			//获取时段最后一个时间
			periodName = periodName.substring(periodName.lastIndexOf("-")+1);
			
			// 是当天的才比较
			if (currentDate.equals(result.getDutyDate())) {
				if(currentTime.compareTo(periodName)>0){
					continue;
				}
			}
			
			//剩余号数为0的过滤
			if(result.getRegNumRemain()!=null && result.getRegNumRemain().intValue()==0){
				continue;
			}
			
//			// 是当天的才比较
//			if (currentDate.equals(result.getDutyDate())) {
//
//				if (("1".equals(periodId) || "2".equals(periodId))
//						&& "上午".equals(currentTimeName)) {
//					continue;
//				} else if (("1".equals(periodId) || "2".equals(periodId))
//						&& "下午".equals(currentTimeName)) {
//					continue;
//				} else if (("3".equals(periodId) || "4".equals(periodId))
//						&& "下午".equals(currentTimeName)) {
//					continue;
//				}
//			}

			tempMap.add(result);
		}

		return tempMap;
		/*
		 * //当前时间 的时分 格式 String currentTime = UtilDate.getDateToStr(new Date(),
		 * UtilDate.dtHm); //当前时间的 年-月-日 String currentDate =
		 * UtilDate.getDateToStr(new Date(), UtilDate.dtDate); String
		 * currentTimeName = ""; if(currentTime.compareTo("08:00")>=0 &&
		 * "10:00".compareTo(currentTime)>=0){ //上午 08:00 - 10:00
		 * currentTimeName = "08:00-10:00";//"上午"; }else
		 * if(currentTime.compareTo("10:00")>=0 &&
		 * "12:00".compareTo(currentTime)>=0){ //上午 10:00 - 12:00
		 * currentTimeName = "10:00-12:00"; }else
		 * if(currentTime.compareTo("14:00")>=0 &&
		 * "16:00".compareTo(currentTime)>=0){ //上午 10:00 - 12:00
		 * currentTimeName = "10:00-12:00"; }else{ //晚上 currentTimeName = "晚上";
		 * }
		 * 
		 * for(ScheduleResultMap result : queryList){ String periodName =
		 * result.getPeriodName(); String periodId = result.getPeriodId();// 1
		 * 08:00-10:00, 2 10:00-12:00, 3 periodName =
		 * StringUtil.isEmpty(periodName)?"":periodName.trim(); //是当天的才比较
		 * if(currentDate.equals(result.getDutyDate())){ //现在是下午或者晚上则 忽略当前排班
		 * if("上午".equals(periodName)&&( ("下午").equals(currentTimeName) ||
		 * ("晚上").equals(currentTimeName) )){ continue; }else
		 * if("下午".equals(periodName)&&("晚上").equals(currentTimeName)){ //现在是晚上则
		 * 忽略当前排班 continue; }else
		 * if("白天".equals(periodName)&&("晚上").equals(currentTimeName)){
		 * continue; } } tempMap.add(result); }
		 */
	}

	/**
	 * 科室下医生的排班 按时间查询
	 */
	@Override
	@HttpMethod
	public List<ScheduleVo> scheduleByDate(@ParamNoNull String depId) {
		List<ScheduleResultMap> queryList = staffDao
				.getMcDutyScheduleByDate(depId);
		// 过滤时间
		queryList = filterDutyDate(queryList);

		LinkedList<ScheduleVo> result = new LinkedList<ScheduleVo>();
		// List<ScheduleResultMap> queryList 组装为 List<ScheduleVo> list
		if (null != queryList && !queryList.isEmpty()) {
			int size = queryList.size();
			String dutyDate = "";
			String periodName = "";
			for (int i = 0; i < size; i++) {
				ScheduleResultMap oneOfList = queryList.get(i);

				if (!dutyDate.equals(oneOfList.getDutyDate())
						|| !periodName.equals(oneOfList.getPeriodName())) {
					dutyDate = oneOfList.getDutyDate();
					periodName = oneOfList.getPeriodName();
					// 第一层为时间排班的信息
					ScheduleVo sv = new ScheduleVo();
					sv.setDutyDate(oneOfList.getDutyDate());
					sv.setPeriodName(oneOfList.getPeriodName());
					LinkedList<DoctorVo> doctorList = new LinkedList<DoctorVo>();
					sv.setDoctorList(doctorList);
					result.addLast(sv);
				}
				ScheduleVo vo = result.getLast();
				LinkedList<DoctorVo> doctorList = vo.getDoctorList();
				// 第二层为医生相关信息
				DoctorVo doctor = new DoctorVo();
				doctor.setDutyId(oneOfList.getDutyId());
				doctor.setUuid(oneOfList.getUuid());
				doctor.setAmount(oneOfList.getAmount());
				doctor.setDepId(oneOfList.getDepId());
				doctor.setGootAt(oneOfList.getGootAt());
				doctor.setOrgId(oneOfList.getOrgId());
				doctor.setRegNum(oneOfList.getRegNum());
				doctor.setRegNumRemain(oneOfList.getRegNumRemain());
				doctor.setStaffLabelName(oneOfList.getStaffLabelName());
				doctor.setStaffName(oneOfList.getStaffName());
				doctor.setStaffId(oneOfList.getStaffId());
				if (!StringUtil.isEmpty(oneOfList.getStaffIcon())) {
					doctor.setStaffIcon(PIC_SERVER + oneOfList.getStaffIcon());
				}
				doctorList.addLast(doctor);
			}
		}
		return result;
	}

	/**
	 * 科室下医生的排班 按医生查询
	 */
	@Override
	@HttpMethod
	public List<DoctorSchedule> scheduleByDoctor(@ParamNoNull String depId) {
		List<ScheduleResultMap> queryList = staffDao
				.getMcDutyScheduleByDoctor(depId);
		// 过滤时间
		queryList = filterDutyDate(queryList);

		LinkedList<DoctorSchedule> result = new LinkedList<DoctorSchedule>();
		if (null != queryList && !queryList.isEmpty()) {
			int size = queryList.size();
			BigDecimal staffIdInit = new BigDecimal(0);
			for (int i = 0; i < size; i++) {
				ScheduleResultMap oneOfList = queryList.get(i);
				BigDecimal staffIdBigDecimal = oneOfList.getStaffId();
				if (!staffIdInit.equals(staffIdBigDecimal)) {
					staffIdInit = staffIdBigDecimal;
					DoctorSchedule ds = new DoctorSchedule();
					LinkedList<ScheduleInfo> scheduleInfoList = new LinkedList<ScheduleInfo>();
					ds.setScheduleInfoList(scheduleInfoList);
					// ds.setAmount(oneOfList.getAmount());
					ds.setDepId(oneOfList.getDepId());
					ds.setGootAt(oneOfList.getGootAt());
					ds.setOrgId(oneOfList.getOrgId());
					ds.setStaffId(oneOfList.getStaffId());
					ds.setStaffLabelName(oneOfList.getStaffLabelName());
					ds.setStaffName(oneOfList.getStaffName());
					result.addLast(ds);
				}
				DoctorSchedule ds = result.getLast();
				if (!StringUtil.isEmpty(oneOfList.getStaffIcon())) {
					ds.setStaffIcon(PIC_SERVER + oneOfList.getStaffIcon());
				}
				LinkedList<ScheduleInfo> scheduleInfoList = ds
						.getScheduleInfoList();
				ScheduleInfo sInfo = new ScheduleInfo();
				sInfo.setDutyDate(oneOfList.getDutyDate());
				sInfo.setDutyId(oneOfList.getDutyId());
				sInfo.setUuid(oneOfList.getUuid());
				sInfo.setPeriodName(oneOfList.getPeriodName());
				sInfo.setRegNum(oneOfList.getRegNum());
				sInfo.setRegNumRemain(oneOfList.getRegNumRemain());
				sInfo.setAmount(oneOfList.getAmount());

				scheduleInfoList.addLast(sInfo);

			}
		}
		return result;
	}

	/**
	 * 获取分时段 hasPeriod 机构是否有分时段 0无 1分时段 2分时点
	 */
	@Override
	@HttpMethod
	public List<PeriodInfo> getDutyPeriod(@ParamNoNull String hasPeriod,
			@ParamNoNull String dutyId) {
		LinkedList<PeriodInfo> result = new LinkedList<PeriodInfo>();
		if ("1".equals(hasPeriod) || "2".equals(hasPeriod)) {
			List<DutyPeriodMap> queryList = staffDao.getDutyPeriod(dutyId);
			if (null != queryList && !queryList.isEmpty()) {
				String startTimeInit = "";
				for (int i = 0; i < queryList.size(); i++) {
					DutyPeriodMap oneOfList = queryList.get(i);
					String st = oneOfList.getStartTime();
					if (!startTimeInit.equals(st)) {
						startTimeInit = st;
						PeriodInfo info = new PeriodInfo();
						List<BigDecimal> periodIdList = new ArrayList<BigDecimal>();
						info.setDutyPeriodIdList(periodIdList);
						result.addLast(info);
					}
					PeriodInfo info = result.getLast();
					info.setDutyId(oneOfList.getDutyId());
					info.setEndTime(oneOfList.getEndTime());
					info.setRegNum(oneOfList.getRegNum());
					info.setRegTime(oneOfList.getRegTime());
					info.setRemains(oneOfList.getRemains());
					info.setStartTime(oneOfList.getStartTime());
					// 一个时段下有多个号源
					info.getDutyPeriodIdList().add(oneOfList.getDutyPeriodId());
				}
			}
		}
		return result;
	}

	/**
	 * 添加排班
	 * 
	 * @param mcDuty
	 * @param uuid
	 * @param orgCode
	 * @param depCode
	 * @param staffCode
	 * @param staffTypeCode
	 * @return
	 */
	@HttpMethod
	@Transactional
	public HashMap<String, Object> addDuty(McDuty mcDuty, String uuid,
			String orgCode, String depCode, String staffCode,
			String staffTypeCode) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		McDuty duty = regDao.getDutyByUUID(uuid);
		if (duty != null) {
			result.put("err_message", "排班已存在");
			result.put("retCode", 3);
			return result;
		}
		Org org = orgDao.getOrgByCode(orgCode);
		if (org == null) {
			result.put("err_message", "机构不存在");
			result.put("retCode", 4);
			return result;
		}
		Dep dep = depDao.getDepInfoByCode(depCode);
		if (dep == null) {
			result.put("err_message", "科室不存在");
			result.put("retCode", 5);
			return result;
		}
		Staff staff = staffDao.getStaffByCode(staffCode);
		if (staff == null) {
			result.put("err_message", "医生不存在");
			result.put("retCode", 6);
			return result;
		}
		StaffType staffType = staffDao.getStaffTypeByCode(staffTypeCode);
		if (staffType == null) {
			result.put("err_message", "医生级别不存在");
			result.put("retCode", 7);
			return result;
		}
		duty = new McDuty();
		duty.setUuid(uuid);
		duty.setOrgId(org.getOrgId());
		duty.setOrgName(org.getOrgName());
		duty.setDepId(dep.getDepId());
		duty.setDepName(dep.getDepName());
		duty.setStaffId(staff.getStaffId());
		duty.setStaffName(staff.getStaffName());
		duty.setStaffTypeId(staffType.getStaffTypeId());
		duty.setStaffTypeName(staffType.getStaffTypeName());
		int i = regDao.addDuty(duty);
		if (i <= 0) {
			result.put("err_message", "未知错误");
			result.put("retCode", 8);
			return result;
		}
		return result;
	}

	@HttpMethod
	@Transactional
	public HashMap<String, Object> addPeriod(@NotNull DutyPeriod period,
			@NotNull String dutyUuid) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		McDuty duty = regDao.getDutyByUUID(dutyUuid);
		if (duty == null) {
			result.put("err_message", "未找到排班");
			result.put("retCode", 3);
			return result;
		}
		period.setDutyId(duty.getDutyId());
		int i = regDao.addDutyPeriod(period);
		if (i <= 0) {
			result.put("err_message", "添加失败");
			result.put("retCode", 4);
			return result;
		}
		return result;
	}

	@HttpMethod
	@Override
	public List<StaffDuty> getStaffMcDutyByDepId(@NotNull String depId) {
		return staffDao.getStaffMcDutyByDepId(depId);
	}

	@HttpMethod
	@Override
	public List<StaffDutyPeriod> getStaffMcDutyRecentlyByStaffId(
			@NotNull String staffId, String type) {
		// 查询医生近7天的排班信息
		return staffDao.getStaffMcDutyRecentlyByStaffId(staffId, type);
	}

	@HttpMethod
	@Override
	public List<StaffDuty> getStaffMcDutyByDepName(@NotNull String depName,
			@NotNull String orgId) {
		// 通过depName 查询出 depId
		Dep dep = depDao.getDepByDepName(depName, orgId);
		return dep == null ? new ArrayList<StaffDuty>() : this
				.getStaffMcDutyByDepId(dep.getDepId().toString());
	}
}
