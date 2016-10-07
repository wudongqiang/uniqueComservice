package com.unique.org.service;

import java.util.HashMap;
import java.util.List;

import com.unique.org.po.Dep;
import com.unique.org.vo.StaffDuty;
import com.unique.org.vo.StaffDutyPeriod;
import com.unique.org.vo.bydate.ScheduleVo;
import com.unique.org.vo.bydoctor.DoctorSchedule;
import com.unique.org.vo.period.PeriodInfo;
import com.unique.reg.po.DutyPeriod;


public interface ScheduleService {
	
	public List<ScheduleVo> scheduleByDate(String depId);
	public List<DoctorSchedule> scheduleByDoctor(String depId);
	
	public List<PeriodInfo> getDutyPeriod(String hasPeriod, String dutyId);
	
	public HashMap<String, Object> addPeriod( DutyPeriod period, String dutyUuid);
	
	/**
	 * 通过部门id获取部门下所有的医生的排班 
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月9日 下午12:37:32
	 * @param depId
	 */
	List<StaffDuty> getStaffMcDutyByDepId(String depId);
	/**
	 * 获取医生近7天的排班信息
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月9日 下午2:53:31
	 * @param staffId
	 * @param type 1表示全部，0或null表示 一部分，默认0
	 * @return
	 */
	List<StaffDutyPeriod> getStaffMcDutyRecentlyByStaffId(String staffId,
			String type);
	
	/**
	 * 通过科室名称查询科室下的医生排班
	 * <br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月30日 上午10:37:19<br/>
	 * @param depName
	 * @return
	 */
	List<StaffDuty> getStaffMcDutyByDepName(String depName,String orgId);
	
}
