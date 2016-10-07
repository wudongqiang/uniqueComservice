package com.unique.reg.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.unique.order.po.McOrder;
import com.unique.order.po.McOrderDet;
import com.unique.org.po.Dep;
import com.unique.org.po.StaffType;
import com.unique.reg.po.BillDetail;
import com.unique.reg.po.BillSrcType;
import com.unique.reg.po.Billing;
import com.unique.reg.po.DutyPeriod;
import com.unique.reg.po.McDuty;
import com.unique.reg.po.McReg;
import com.unique.reg.po.Org;
import com.unique.reg.po.PayWay;
import com.unique.reg.po.ProductPrice;
import com.unique.reg.po.ProductPriceType;
import com.unique.reg.po.RegWay;
import com.unique.reg.po.Staff;
import com.unique.reg.vo.MyReservation;
import com.unique.reg.vo.ReservationDetails;
import com.unique.reg.vo.patient.McRegInfo;
import com.unique.reg.vo.patient.PatientInfo;
import com.unique.reg.vo.patient.ScheduleDutyInfo;

public interface RegDao {
	/**
	 * 释放号源
	 * @param dutyId 排班ID
	 * @return
	 */
	public int addRegRemain(String dutyId);
	
	/**
	 * 占号
	 * @param dutyId 排班ID
	 * @return
	 */
	public int subRegRemain(String dutyId);
	
	/**
	 * 修改分时段状态
	 * @param status 修改成的状态
	 * @param periodUniqueId 分时段ID
	 * @param preStatus 修改后的状态
	 * @return
	 */
	public int modDutyPeriod(String status,String dutyPeriodId,String preStatus);
	
	public int checkRegForRepeat(String idCard,String dutyId);
	
	public McDuty getDutyById(String dutyId);
	
	public McDuty getCanUserDutyById(String dutyId);
	
	public Org getOrgById(String orgId);
	
	public Staff getStaffById(String staffId);
	
	public int checkRegForRegTimes(String idCard);
	
	public int isUserBlack(String orgId,String userId,String typeCode);
	
	public List<ProductPrice> getPriceListByStaffType(String staffTypeId);
	
	public int addMcReg(McReg reg);
	
	public RegWay getRegWayByCode(String code);
	
	public DutyPeriod getDutyPeriodById(String pid);
	
	/**
	 * 获取账单类型
	 * @param code
	 * @return
	 */
	public BillSrcType getBillTypeByCode(String code);
	
	public void addBilling(Billing billing);
	
	public void addBillDetail(BillDetail detail);
	
	public void updateRegById(McReg reg);
	
	public List<PayWay> getOnPayWay();
	
	public McReg getRegById(String regId);
	
	public List<McReg> regList(String userId,Long startRow,Long endRow,String[] status,String[] isUsed);
	public long regListCount(String userId,String[] status,String[] isUsed);
	
	public McOrder getOrderByRegId(String regId);
	
	public McOrderDet getOrderDetByRegId(String regId);
	
	public int updateDutyNo(String dutyId,long ver);
	
	public int getRegNum(String dutyId);
	
	public List<Org> getOrgs4Lucence(Date lastTime,String[] ids);
	
	public List<Dep> getDep4Lucence(Date lastTime,String[] ids);
	
	public List<Staff> getStaff4Lucence(Date lastTime,String[] ids);
	
	public List<McDuty> getDutyByMonth(String staffId,String monthStr);
	
	public List<McReg> getRegByDutyDate(String staffId,String date,String period);
	
	public McDuty getDutyByUUID(String uuid);
	
	public int addDuty(McDuty duty);
	
	public int addDutyPeriod(DutyPeriod period);
	
	public ProductPriceType getProductPriceTypeByCode(String code,String orgId);
	
	public int addProductPriceType(ProductPriceType priceType);
	
	public int addStaffType(StaffType type);
	
	public int addProductPrice(ProductPrice price);

	/**
	 * 获取当前医生的所有患者 - 分页
	 * @param staffId
	 * @param userName 
	 * @param startRow 
	 * @param endRow 
	 * @return
	 */
	public List<PatientInfo> getFriendByStaffId(String staffId, String userName,Long startRow ,Long endRow);

	/**
	 * 挂号查询
	 * @param staffId
	 * @param date
	 */
	public List<McRegInfo> getMcRegInfos(String staffId, String date);

	/**
	 * 获取当前挂号数
	 * @param staffId
	 * @param date
	 */
	public long getCountMcReg(String staffId, String date);

	/**
	 * 医生端 主页调整到挂号查询页面数据
	 * @param staffId
	 * @param monthStr
	 */
	public List<String> searchMcRegs(String staffId, String monthStr);

	/**
	 * 日程安排列表,查询日期之后的，并且是本月
	 * @param staffId
	 * @param date
	 * @param type 类型标示日期查询方式：list列表查询，detailed详细查询，默认list
	 * @return
	 */
	public List<ScheduleDutyInfo> getDutyByDateAndStaffId(String staffId, String date,String type);
	
	/**
	 * 日程排班查询 日历信息
	 * @param staffId
	 * @param monthStr
	 * @return
	 */
	public List<String> getScheduleDutys(String staffId, String monthStr);

	public McReg getMcRegById(String regId, String orderStatus);

	/**
	 * 通过订单流水号获取
	 * @param payNo
	 * @return
	 */
	public McReg getRegByPayNo(String payNo);

	
	public RegWay getRegWayById(String regWayId);

	/**
	 * 我的预约查询 -口腔
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月10日 下午4:19:13
	 * @param map 查询条件
	 * @return
	 */
	public List<MyReservation> myReservation(Map<String, Object> map);
	
	/**
	 * 根据医生ID获取可预约数
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月11日上午11:21:57
	 * 修改日期:2015年9月11日上午11:21:57
	 * @param staffId
	 * @return
	 */
	public long getCanRegNums(String staffId);
	
	/**
	 * 根据医生ID获取被预约数<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年9月14日 下午3:23:47<br/>
	 * @param staffId
	 * @return
	 */
	public long getBeReservedRegNums(String staffId);

	/**
	 * 预约详情<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年9月22日 上午11:45:42<br/>
	 * @param regId
	 * @return
	 */
	public ReservationDetails reservationDetails(String regId);
	
	/**
	 * 此处查询挂号记录并查询就诊人基本信息和卡类型信息 
	 * <br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年9月28日 下午4:45:20<br/>
	 * @param regId
	 * @return
	 */
	public McReg getMcRegAndFriendInfoByRegId(String regId);

	/**
	 * <br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月11日 下午1:25:47<br/>
	 * @param map
	 * @return
	 */
	public Long myReservationCount(Map<String, Object> map);
	
}
