package com.unique.reg.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unique.order.po.McOrder;
import com.unique.org.po.StaffType;
import com.unique.reg.po.McDuty;
import com.unique.reg.po.McReg;
import com.unique.reg.po.PayWay;
import com.unique.reg.po.ProductPriceType;
import com.unique.reg.vo.ReservationDetails;
import com.unique.reg.vo.patient.PatientInfo;
import com.unique.user.po.AmsUser;
import com.unique.user.po.UserFriend;

public interface RegService {
	
	/**
	 * 挂号
	 * @param dutyId  排班ID
	 * @param dutyPeriodId 排班时段ID
	 * @param userId 用户ID
	 * @param firendId 就诊人ID
	 * @param regWayCode 挂号方式
	 * @param sourceCode 来源code
	 * @param hisUserId his用户ID
	 * @param cardTypeId 卡类型ID
	 * @param isBund 	0不绑定 1 绑定
	 * @return
	 */
	public HashMap<String, Object> reg(String dutyId, String dutyPeriodId,
			String userId, String firendId, String regWayCode,
			String sourceCode,String hisUserId,String cardTypeId,Integer isBund);
	
	public int subReg(String dutyId,String dutyPeriodId);
	
	public void regSuccess(String regId,String payWayCode,String payNo,McOrder order,String orderPayId);
	
	public List<McReg> regList(String userId,Long startRow,Long rows,String[] status,String[] isUsed);
	
	public McReg getMcRegById(String regId,String orderStatus);
	/**
	 * 
	 * 获取挂号支付成功的挂号记录<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月12日 下午12:52:00<br/>
	 * @param regId
	 * @return
	 */
	public McReg getMcRegSuccessById(String regId);
	
	public HashMap<String, Object> regListCount(String userId,String[] status,String[] isUsed);
	
	/**
	 * 获取医生当月排班
	 * @param staffId
	 * @param monthStr
	 * @return
	 */
	public List<McDuty> getDutyByMonth( String staffId, String monthStr);
	
	/**
	 * 根据日期和医生获取挂号列表
	 * @param staffId
	 * @param date
	 * @param period
	 * @return
	 */
	public List<McReg> getRegByDutyDate(String staffId,String date,String period);
	
	public HashMap<String, Object> addStaffType( StaffType staffType, String userTypeId, List<ProductPriceType> prices);
	
	/**
	 * 获取当前医生的所有患者 -分页
	 * @param staffId
	 * @param userName
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public List<PatientInfo> getFriendByStaffId(String staffId,String userName,Long startRow ,Long rows);

	/**
	 * 挂号查询（具体日期）
	 * @param staffId
	 * @param date 如2015-05-08
	 * @return
	 */
	Map<String,Object> getMcRegInfos(String staffId, String date);

	/**
	 * 医生端 主页跳转到挂号查询页面数据
	 * @param staffId
	 * @param monthStr 如 2015-02
	 */
	Map<String,Object> searchMcRegs(String staffId, String monthStr);

	/**
	 * 通过staffId 和 日期 获取日程列表信息
	 * @param staffId
	 * @param date 查询开始时间，不填写默认当前日期，也可查询具体日期的日程信息 yyyy-mm-dd
	 * @param type 类型标示日期查询方式：list列表查询，detailed详细查询，默认list
	 * @param queryType 查询类型，如 重医只显示 随访
	 * @return
	 */
	Map<String,Object> getDutyByDateAndStaffId(String staffId, String date, String type,String queryType);

	/**
	 * 日程排班查询 日历信息
	 * @param staffId
	 * @param monthStr
	 * @param queryType 查询类型，如 重医只显示 随访
	 * @return
	 */
	Map<String,Object> getScheduleDutys(String staffId, String monthStr,String queryType);
	
	public void updateReg(McReg reg);
	
	public List<PayWay> getPayways();
	
	/**
	 * 挂号医院同步接口
	 */	
	public HashMap<String, Object> synchronizedToOrg_Reg(UserFriend userFriend,AmsUser user,String hospitalUrl,McReg reg,McDuty duty,Integer isBund);
	
	//走异步接口
	public HashMap<String, Object> asynchronousToOrg_Reg(UserFriend user,String orgCode);
	
	/**
	 * 挂号医院同步接口
	 */
	public HashMap<String, Object> synchronizedToOrg_UnReg(String ipAddr,AmsUser user,Date cancelTime,McReg reg);

	/**
	 * 通过订单流水号获取
	 * @param payNo
	 * @return
	 */
	McReg getRegByPayNo(String payNo);

	/**
	 * 我的预约查询 -口腔
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月10日 下午4:19:13
	 * @param userId 用户id
	 * @param type  0:查询待就医，1:查询预约记录
	 * @param startRow
	 * @param rows
	 * @return
	 */
	Map<String,Object> myReservation(String userId, String type,
			Long startRow, Long rows);
	/**
	 * 预约详情<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年9月22日 上午11:44:19<br/>
	 * @param regId
	 * @return
	 */
	ReservationDetails reservationDetails(String regId);
	
	public void updateRegInRedis(String regId,McReg reg);
	
	public void updateUNRegInRedis(String regId,McReg reg);
	
	public void updateUNReg(McReg reg);

	/**
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月17日下午2:12:27
	 * 修改日期:2015年10月17日下午2:12:27
	 * @param string
	 * @param string2
	 */
	public int releaseReg(String string, String string2);
	
	/**
	 * 退号
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月6日上午11:06:57
	 * 修改日期:2015年11月6日上午11:06:57
	 * @param regId
	 * @param opUserId
	 * @param backReason
	 * @param userId
	 * @return
	 */
	public HashMap<String,Object> unReg(
			 String regId,
			 String opUserId,
			 String backReason,
			 String userId);

	/**
	 * 挂号前验证是否之前有患者ID（有就诊记录）
	 * @创建时间 2016年6月22日
	 * @param orgId 机构id 比传
	 * @param friendId 就诊人id
	 * @return
	 */
	String checkRegistration(String orgId, String friendId);
	
}
