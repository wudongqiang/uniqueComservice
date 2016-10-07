package com.unique.patient.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;
import com.unique.alipay.util.UtilDate;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.util.StringUtil;
import com.unique.org.dao.StaffDao;
import com.unique.patient.dao.UserDoctorRelationApplyDao;
import com.unique.patient.dao.UserDoctorRelationDao;
import com.unique.patient.po.UserDoctorRelation;
import com.unique.patient.po.UserDoctorRelationApply;
import com.unique.reg.po.Staff;
import com.unique.user.dao.UserDao;
import com.unique.user.po.AmsUser;
import com.unique.user.webservice.WebUserService;


/**
 * 医患关系申请 业务实现类<br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年10月13日 上午11:09:01<br/>
 * @author Administrator
 * @date 2015年10月13日
 * @description
 */
@Service("userDoctorRelationApplyService")
public class UserDoctorRelationApplyServiceImpl implements UserDoctorRelationApplyService{
	
	private final static Logger logger = Logger.getLogger(UserDoctorRelationApplyServiceImpl.class);
	
	@Resource
	private UserDoctorRelationApplyDao userDoctorRelationApplyDao;
	@Resource
	private UserDoctorRelationDao userDoctorRelationDao;
	@Resource
	private WebUserService webUserService;
	
	@Resource
	private StaffDao staffDao;
	
	@Resource
	private UserDao userDao;

	
	@HttpMethod
	@Transactional
	@Override
	public Map<String, Object> addUserDoctorRelationApply(@NotNull String staffUserId,@NotNull String userId,String message) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			
			//判断是否可以添加   状态(1申请中2已同意3已拒绝)
			UserDoctorRelationApply conditon = new UserDoctorRelationApply();
			conditon.setUserId(new BigDecimal(userId));
			conditon.setStaffUserId(new BigDecimal(staffUserId));
			//conditon.setStatus(new BigDecimal(1));
			//查询医生
			Staff staff = staffDao.getStaffByUserId(staffUserId);
			
			//判断是否可以申请
			List<UserDoctorRelationApply> udas = userDoctorRelationApplyDao.getUserDoctorRelationApplyByCondition(conditon);
			if(!udas.isEmpty()&&udas.size()>0){
				result.put("retCode", 2);
				result.put("retMessage", "对不起，申请关系已存在，不能重复申请！");
				return result;
			}
			
			//判断是否存在医患关系
			if(userDoctorRelationDao.getIsUserDoctorelation(staff.getStaffId().toString(), userId)>0){
				result.put("retCode", 3);
				result.put("retMessage", "对不起，您已经存在医患关系，不能重复申请建立关系！");
				return result;
			}
			
			//查询医生 目前 的医患关系数量 不能大于 医生所服务的最大数
			if(staff.getHealtyUserNumber().intValue()<=userDoctorRelationDao.getUserdocDoctorRelationsCountByStaffId(staff.getStaffId().toString())){
				result.put("retCode", 4);
				result.put("retMessage", "医生的服务量已超，不能在申请！");
				return result;
			}
			
			//查询用户
			AmsUser user = userDao.getUserById(userId);
			//创建医患关系申请对象
			UserDoctorRelationApply m = new UserDoctorRelationApply();
			m.setUserId(user.getUserId());
			m.setUserName(user.getUserName());
			m.setStaffUserId(staff.getUserId());
			m.setStaffName(staff.getStaffName());
			m.setApplyTime(new Date());
			m.setStatus(new BigDecimal(1));//状态(1申请中2已同意3已拒绝)
			m.setApplyCon(StringUtil.isEmpty(message)?"":message);
			m.setApplyWay("1");//申请方式1用户申请医生2医生申请用户
			
			userDoctorRelationApplyDao.addUserDoctorRelationApply(m);
			result.put("retCode", 0);
			result.put("retMessage", "医患关系申请成功！等待审核...");
			
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userName", user.getUserName());
			param.put("type", "friendApply");//好友申请
			param.put("applyTime", UtilDate.getDateToStr(new Date(), UtilDate.dtDate));//申请时间
			param.put("userId", user.getUserId());
			param.put("apply", m.getApplyId());//关系申请Id
			param.put("message", StringUtil.isEmpty(message)?"":message);
			
			String msg = staff.getStaffName()+" 您好！ 患者  " + user.getUserName()+ " 向您申请建立[医患关系] ，请您尽快审核！";
			
			//发送im 消息 此处改成使用系统消息发送
			if(webUserService.sendMsg("-4", msg, JSONObject.fromObject(param).toString(), new String[]{staffUserId})){
				//发送成功消息   -4  表示发送 系统消息 
				webUserService.sendNtfMessage("-4", "医患关系申请发送成功", "", userId);
			}else{
				//发送成功消息
				webUserService.sendNtfMessage("-4", "医患关系申请发送失败", "", userId);
			}
			
		} catch (Exception e) {
			logger.error("添加医患关系申请失败！", e);
			result.put("retCode", -1);
			result.put("retMessage", "医患关系申请失败！");
		}
		
		return result;
	}
	
	@Override
	@HttpMethod
	@Transactional
	public Map<String, Object> updateUserDoctorRelationApplyStatus(
			@NotNull String applyId,@NotNull String status,String reason) {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			//判断关系申请是否存在
			UserDoctorRelationApply uda = userDoctorRelationApplyDao.getUserDoctorRelationApplyById(applyId);
			
			if(uda==null){
				result.put("retCode", 4);
				result.put("message", "医患关系申请已不存在！请重新加载");
				result.put("retMessage", "医患关系申请已不存在！请重新加载");
				return result;
			}
			if(!("2".equals(status)||"3".equals(status))){
				result.put("retCode", 3);
				result.put("message", "状态传入错误。注（2同意3拒绝）");
				result.put("retMessage", "状态传入错误。注（2同意3拒绝）");
				return result;
			}
			
			//查询医生
			Staff staff = staffDao.getStaffByUserId(uda.getStaffUserId().toString());
			//查询用户 患者
			AmsUser user = userDao.getUserById(uda.getUserId().toString());
			
			//记录状态 1 表示 用户申请，4 表示 医生申请
			String stautsSrc = uda.getStatus().toString();
			//申请方式1用户申请医生2医生申请用户
			String applyWay = uda.getApplyWay();
			
			String message = null; //定义消息
			String toUserIds[] = null;//定义接收者
			
			//查询医患关系是否存在
			if(userDoctorRelationDao.getIsUserDoctorelation(staff.getStaffId().toString(), user.getUserId().toString())>0){
				result.put("retCode", 1);
				if("2".equals(status)){
					result.put("message", "关系已存在，无需重复添加！");
					result.put("retMessage", "关系已存在，无需重复添加！");
				}else{
					result.put("message", "关系已存在，无法拒绝，请重新加载！");
					result.put("retMessage", "关系已存在，无法拒绝，请重新加载！");
				}
				return result;
			}
			
			uda.setStatus(new BigDecimal(status));
			if("2".equals(status)){
				
				if("2".equals(stautsSrc)){
					result.put("retCode", 0);
					result.put("message", "医患关系已同意，不能再次同意");
					result.put("retMessage", "医患关系已同意，不能再次同意");
					webUserService.sendNtfMessage("-4", "医患关系已同意，不能再次同意", "", "1".equals(applyWay)?staff.getUserId().toString():user.getUserId().toString());
					return result;
				}
				
				if("3".equals(stautsSrc)){
					result.put("retCode", 2);
					result.put("message", "医患关系已拒绝，不能进行同意");
					result.put("retMessage", "医患关系已拒绝，不能进行同意");
					webUserService.sendNtfMessage("-4", "医患关系已拒绝，不能进行同意", "", "1".equals(applyWay)?staff.getUserId().toString():user.getUserId().toString());
					return result;
				}
				
				
				//查询医生 目前 的医患关系数量 不能大于 医生所服务的最大数
				if(staff.getHealtyUserNumber().intValue()<=userDoctorRelationDao.getUserdocDoctorRelationsCountByStaffId(staff.getStaffId().toString())){
					result.put("retCode", 5);
					result.put("message", "医生的服务量已超，不能在添加");
					result.put("retMessage", "医生的服务量已超，不能在添加");
					return result;
				}
				
				uda.setAgreeTime(new Date());//同意时间
				uda.setAgreeUserId("1".equals(applyWay)?staff.getUserId():user.getUserId());//同意人
				
				//同意  后 添加医患关系
				UserDoctorRelation udr = new UserDoctorRelation();
				udr.setCreateTime(new Date());
				udr.setDocUserId(staff.getUserId());
				udr.setStaffName(staff.getStaffName());
				udr.setStaffId(staff.getStaffId());
				udr.setUserId(user.getUserId());
				udr.setUserName(user.getUserName());
				userDoctorRelationDao.addUserDoctorRelation(udr);
				//更新状态
				userDoctorRelationApplyDao.updateUserDoctorRelationApply(uda);
				
				result.put("retCode", 0);
				result.put("message", "已生成医患关系");
				result.put("retMessage", "已生成医患关系");
				
				if("1".equals(stautsSrc)){
					message = "医生["+staff.getStaffName()+"]已同意并添加医患关系！";
					toUserIds = new String[]{user.getUserId().toString()};
				}else{
					message = "用户["+user.getUserName()+"]已同意并添加医患关系！";
					toUserIds = new String[]{staff.getUserId().toString()};
				}
				
				//发送im 消息
				if(webUserService.sendNtfMessage("-4", message, "{\"retCode\":\"0\",\"retMessage\":\"发送消息\"}", toUserIds[0])){
					//发送成功消息   -4  表示发送 系统消息 
					webUserService.sendNtfMessage("-4", "医患关系同意发送成功", "", "1".equals(applyWay)?staff.getUserId().toString():user.getUserId().toString());
				}else{
					//发送成功消息
					webUserService.sendNtfMessage("-4", "医患关系同意发送失败", "", "1".equals(applyWay)?staff.getUserId().toString():user.getUserId().toString());
				}
			}else{
				
				if("2".equals(stautsSrc)){
					result.put("retCode", 0);
					result.put("message", "医患关系已同意，不能进行拒绝");
					result.put("retMessage", "医患关系已同意，不能进行拒绝");
					webUserService.sendNtfMessage("-4", "医患关系已同意，不能进行拒绝", "", "1".equals(applyWay)?staff.getUserId().toString():user.getUserId().toString());
					return result;
				}
				
				if("3".equals(stautsSrc)){
					result.put("retCode", 2);
					result.put("message", "医患关系已拒绝，不能再次拒绝");
					result.put("retMessage", "医患关系已拒绝，不能再次拒绝");
					webUserService.sendNtfMessage("-4", "医患关系已拒绝，不能再次拒绝", "", "1".equals(applyWay)?staff.getUserId().toString():user.getUserId().toString());
					return result;
				}
				
				uda.setRefuseTime(new Date());//拒绝时间
				uda.setRefuseUserId("1".equals(applyWay)?staff.getUserId():user.getUserId());//拒绝人
				uda.setRefuseReason(StringUtil.isEmpty(reason)?"":reason);
				
				//更新状态
				userDoctorRelationApplyDao.updateUserDoctorRelationApply(uda);
				
				//拒绝
				result.put("retCode", 2);
				result.put("message", "已拒绝添加医患关系");
				result.put("retMessage", "已拒绝添加医患关系");
				
				if("1".equals(stautsSrc)){
					message = "医生["+staff.getStaffName()+"]拒绝你添加医患关系！";
					toUserIds = new String[]{user.getUserId().toString()};
				}else{
					message = "用户["+user.getUserName()+"]拒绝您添加医患关系！";
					toUserIds = new String[]{staff.getUserId().toString()};
				}
				
				//发送im 消息
				if(webUserService.sendNtfMessage("-4", message, "{\"type\":\"refuseReason\",\"message\":\""+(StringUtil.isEmpty(reason)?"":reason)+"\"}", toUserIds[0])){
					//发送成功消息   -4  表示发送 系统消息 
					webUserService.sendNtfMessage("-4", "医患关系拒绝发送成功", "", "1".equals(applyWay)?staff.getUserId().toString():user.getUserId().toString());
				}else{
					//发送成功消息
					webUserService.sendNtfMessage("-4", "医患关系拒绝发送失败", "", "1".equals(applyWay)?staff.getUserId().toString():user.getUserId().toString());
				}
			}
			
		} catch (Exception e) {
			logger.error("更改申请状态失败！", e);
			result.put("retCode", -1);
			result.put("message", "更改申请状态失败");
			result.put("retMessage", "更改申请状态失败");
		}
		return result;
	}
	 
	@HttpMethod
	@Override
	public Map<String,Object> getUserDoctorRelationApplyStatusByapplyId(@NotNull String applyId) {
		Map<String,Object> result = new HashMap<String,Object>(1);
		UserDoctorRelationApply uda = userDoctorRelationApplyDao.getUserDoctorRelationApplyById(applyId);
		result.put("status",uda==null?-1:uda.getStatus());
		return result;
	}
 
	@HttpMethod
	@Override
	public Map<String,Object> getIsUserDoctorelation(@NotNull String staffId,@NotNull String userId) {
		Map<String,Object> result = new HashMap<String,Object>(1);
		result.put("count", userDoctorRelationDao.getIsUserDoctorelation(staffId, userId));
		return result;
	}
	
}

