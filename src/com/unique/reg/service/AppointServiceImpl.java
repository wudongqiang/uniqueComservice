/**
 * 2015年11月24日
 */
package com.unique.reg.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;
import com.unique.alipay.util.UtilDate;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.util.FileUtil;
import com.unique.org.dao.StaffDao;
import com.unique.reg.dao.AppointDao;
import com.unique.reg.po.McAppoint;
import com.unique.reg.po.Staff;
import com.unique.system.dao.SystemDao;
import com.unique.user.dao.UserDao;
import com.unique.user.po.AmsUser;

/**
 * 约诊服务接口<br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年11月24日 下午2:43:37<br/>
 * @author Administrator
 * @date 2015年11月24日
 * @description 
 */
@Service("appointService")
public class AppointServiceImpl implements AppointService{

	public static Logger log = Logger.getLogger("appoint");
	///图片服务器
  	private final static String PIC_SERVER = FileUtil.readProperties("comservice.properties", "picServerOut");
	@Resource
	private AppointDao appointDao;
	@Resource
	private UserDao userDao;
	@Resource
	private StaffDao staffDao;
	@Resource
	private SystemDao systemDao;
	
	@HttpMethod
	@Override
	@Transactional
	public Map<String, Object> appointment(@NotNull String staffId,@NotNull String userId,@NotNull String appointDate,
			@NotNull String desc, String[] imgUrls) {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			
			//判断同意一人同一时间不能重复添加
			if(appointDao.checkIsUniqueAppoint(staffId,userId,appointDate)>0){
				result.put("retCode", -1);
				result.put("retMessage", "不能重复预约,请修改时间或者医生！");
				return result;
			}
			
			// 新增一条记录
			McAppoint appoint = new McAppoint();
			appoint.setStatus(new BigDecimal(1));
			appoint.setAppointTime(UtilDate.getStrToDate(appointDate, UtilDate.dtDate));
			appoint.setSymptomDesc(desc); 
			
			Staff staff = staffDao.getStaffById(staffId);
			AmsUser user = userDao.getUserById(userId);
			appoint.setStaffId(staff.getStaffId());
			appoint.setStaffName(staff.getStaffName());
			appoint.setUserId(user.getUserId());
			appoint.setUserName(user.getUserName());
			appoint.setOrgId(staff.getOrgId());
			
			//保存数据
			appointDao.addMcAppoint(appoint);
			
			//保存图片
			if(imgUrls!=null && imgUrls.length>0){
				//查询图片所属类型
				String typeUseId = systemDao.getCmsImgTypeIdByTypeCode("appoint_id");
				for(String url : imgUrls){
					result.clear();
					result.put("typeUseId", typeUseId);
					result.put("imgFormat", url.substring(url.lastIndexOf(".")));
					result.put("hlinkTo", url.replace(PIC_SERVER, "/"));
					result.put("operator", user.getUserId());
					result.put("operatorName", user.getUserName());
					result.put("appointId", appoint.getAppointId());
					systemDao.addCmsImgLibForAppoint(result);
				}
			}
			
			result.clear();
			result.put("retCode", 0);
			result.put("retMessage", "操作成功!");
		} catch (Exception e) {
			result.clear();
			log.error("约诊失败", e);
			result.put("retCode", -1);
			result.put("retMessage", "操作失败!");
		}
		return result;
	}

	@HttpMethod
	@Override
	public Map<String, Object> getMcAppoints(String staffId, String userId,String status,
			Long startRow, Long rows) {
		if(startRow==null) startRow = 0L;
		if(rows==null) rows = 20L;
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("list", appointDao.getMcAppoints(staffId, userId,status, startRow, rows));
		result.put("count", appointDao.getMcAppointsCount(staffId, userId,status));
		return result;
	}
	 
	@HttpMethod
	@Override
	public McAppoint getMcAppointById(@NotNull String appointId) {
		return appointDao.getMcAppointById(appointId);
	}
}
