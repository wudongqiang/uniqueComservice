package com.unique.reg.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

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

@Repository("regDao")
public class RegDaoImpl implements RegDao{
	@Resource
	private SqlSessionTemplate sqlSession;
	
	/**
	 * 释放号源
	 * @param dutyId 排班ID
	 * @return
	 */
	public int addRegRemain(String dutyId){
		try{
			int result = sqlSession.update("addRegRemain",dutyId);
			return result;
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * 占号
	 * @param dutyId 排班ID
	 * @return
	 */
	public int subRegRemain(String dutyId){
		try{
			String regNum = sqlSession.selectOne("getRegNum",dutyId);
			if(regNum==null){
				return 1;
			}
			int result = sqlSession.update("subRegRemain",dutyId);
			return result;
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * 修改分时段状态
	 * @param status 修改成的状态
	 * @param periodUniqueId 分时段ID
	 * @param preStatus 修改后的状态
	 * @return
	 */
	public int modDutyPeriod(String status,String dutyPeriodId,String preStatus){
		try{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("status", status);
			map.put("dutyPeriodId", dutyPeriodId);
			map.put("preStatus", preStatus);
			int result = sqlSession.update("modDutyPeriod",map);
			return result;
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * 检测重复预约
	 */
	public int checkRegForRepeat(String idCard,String dutyId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("idCard", idCard);
		param.put("dutyId", dutyId);
		int result = sqlSession.selectOne("checkRegForRepeat",param);
		return result;
	}
	
	public McDuty getDutyById(String dutyId){
		return sqlSession.selectOne("getDutyById",dutyId);
	}
	
	public McDuty getCanUserDutyById(String dutyId){
		return sqlSession.selectOne("getCanUserDutyById",dutyId);
	}
	
	public Org getOrgById(String orgId){
		return sqlSession.selectOne("getOrgById",orgId);
	}
	
	public Staff getStaffById(String staffId){
		return sqlSession.selectOne("getStaffById",staffId);
	}
	
	public int checkRegForRegTimes(String idCard){
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("idCard", idCard);
		return sqlSession.selectOne("checkRegForRegTimes",param);
	}
	
	public int isUserBlack(String orgId,String userId,String typeCode){
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("orgId", orgId);
		param.put("userId", userId);
		param.put("typeCode", typeCode);
		return sqlSession.selectOne("isUserBlack",param);
	}
	
	public List<ProductPrice> getPriceListByStaffType(String staffTypeId){
		return sqlSession.selectList("getPriceListByStaffType",staffTypeId);
	}
	
	/**
	 * 添加挂号记录
	 */
	public int addMcReg(McReg reg){
		return sqlSession.insert("addMcReg",reg);
	}
	
	public RegWay getRegWayByCode(String code){
		return sqlSession.selectOne("getRegWayByCode",code);
	}
	
	public DutyPeriod getDutyPeriodById(String pid){
		return sqlSession.selectOne("getDutyPeriodById",pid);
	}
	
	/**
	 * 获取账单类型
	 * @param code
	 * @return
	 */
	public BillSrcType getBillTypeByCode(String code){
		return sqlSession.selectOne("getBillTypeByCode",code);
	}
	
	public void addBilling(Billing billing){
		sqlSession.insert("addBilling",billing);
	}
	
	public void addBillDetail(BillDetail detail){
		sqlSession.insert("addBillDetail",detail);
	}
	
	public void updateRegById(McReg reg){
		sqlSession.update("updateRegById",reg);
	}
	
	public List<PayWay> getOnPayWay(){
		return sqlSession.selectList("getOnPayWay");
	}
	
	public McReg getRegById(String regId){
		return sqlSession.selectOne("getRegById",regId);
	}
	
	public List<McReg> regList(String userId,Long startRow,Long endRow,String[] status,String[] isUsed){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startRow", startRow);
		param.put("endRow", endRow);
		param.put("userId", userId);
		param.put("status", status);
		param.put("isUsed", isUsed);
		return sqlSession.selectList("regList",param);
	}
	
	public long regListCount(String userId,String[] status,String[] isUsed){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("status", status);
		param.put("isUsed", isUsed);
		return sqlSession.selectOne("regListCount",param);
	}
	
	public McOrder getOrderByRegId(String regId){
		return sqlSession.selectOne("getOrderByRegId",regId);
	}
	
	public McOrderDet getOrderDetByRegId(String regId){
		return sqlSession.selectOne("getOrderDetByRegId",regId);
	}
	
	public int updateDutyNo(String dutyId,long ver){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dutyId", dutyId);
		map.put("ver", ver);
		return sqlSession.update("updateDutyNo",map);
	}
	
	/**
	 * 获取序号
	 * @return
	 */
	@Transactional
	public int getRegNum(String dutyId){
		int currentNo = 0;
		while(currentNo==0){
			McDuty duty = getDutyById(dutyId);
			long version = duty.getCurretNoVer()==null?0:duty.getCurretNoVer().longValue();
			int result = updateDutyNo(dutyId, version);
			if(result>0){
				//更新成功
				currentNo = (duty.getCurretNo()==null?0:duty.getCurretNo().intValue()) + 1;
			}
		}
		return currentNo;
	}
	
	public List<Org> getOrgs4Lucence(Date lastTime,String[] ids){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lastTime", lastTime);
		param.put("ids", ids);
		return sqlSession.selectList("getOrgs4Lucence",param);
	}
	
	
	public List<Dep> getDep4Lucence(Date lastTime,String[] ids){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lastTime", lastTime);
		param.put("ids", ids);
		return sqlSession.selectList("getDep4Lucence",param);
	}
	
	
	public List<Staff> getStaff4Lucence(Date lastTime,String[] ids){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lastTime", lastTime);
		param.put("ids", ids);
		return sqlSession.selectList("getStaff4Lucence",param);
	}
	
	public List<McDuty> getDutyByMonth(String staffId,String monthStr){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("staffId", staffId);
		param.put("monthStr", monthStr);
		return sqlSession.selectList("getDutyByMonth",param);
	}
	
	
	public List<McReg> getRegByDutyDate(String staffId,String date,String period){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("staffId", staffId);
		param.put("date", date);
		param.put("period", period);
		return sqlSession.selectList("getRegByDutyDate",param);
	}
	
	public McDuty getDutyByUUID(String uuid){
		return sqlSession.selectOne("getDutyByUUID",uuid);
	}
	
	public int addDuty(McDuty duty){
		return sqlSession.insert("addDuty",duty);
	}
	
	public int addDutyPeriod(DutyPeriod period){
		return sqlSession.insert("addDutyPeriod",period);
	}
	
	public ProductPriceType getProductPriceTypeByCode(String code,String orgId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("code", code);
		param.put("orgId", orgId);
		return sqlSession.selectOne("getProductPriceTypeByCode",param);
	}
	
	public int addProductPriceType(ProductPriceType priceType){
		return sqlSession.insert("addProductPriceType",priceType);
	}
	public int addStaffType(StaffType type){
		int result = sqlSession.insert("addStaffType",type);
		return result;
	}
	
	public int addProductPrice(ProductPrice price){
		return sqlSession.insert("addProductPrice",price);
	}

	@Override
	public List<PatientInfo> getFriendByStaffId(String staffId,String userName,Long startRow ,Long endRow) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("staffId", staffId);
		param.put("userName",userName);
		param.put("startRow",startRow);
		param.put("endRow",endRow);
		return sqlSession.selectList("getFriendByStaffId", param);
	}

	@Override
	public List<McRegInfo> getMcRegInfos(String staffId, String date) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("staffId", staffId);
		param.put("date", date);
		return sqlSession.selectList("getMcRegInfos",param);
	}

	@Override
	public long getCountMcReg(String staffId, String date) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("staffId", staffId);
		param.put("date", date);
		return sqlSession.selectOne("getCountMcReg", param);
	}

	@Override
	public List<String> searchMcRegs(String staffId, String monthStr) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("staffId", staffId);
		param.put("monthStr", monthStr);
		return sqlSession.selectList("searchMcRegs", param);
	}

	@Override
	public List<ScheduleDutyInfo> getDutyByDateAndStaffId(String staffId, String date,String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("staffId", staffId);
		if("list".equals(type)){
			param.put("month", date.substring(0, date.lastIndexOf("-")));
//			param.put("date", date);
		}else{
			param.put("searchDate", date);
		}
		return sqlSession.selectList("getDutyByDateAndStaffId", param);
	}

	@Override
	public List<String> getScheduleDutys(String staffId, String monthStr) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("staffId", staffId);
		param.put("monthStr", monthStr);
		return sqlSession.selectList("getScheduleDutys", param);
	}

	@Override
	public McReg getMcRegById(String regId,String orderStauts) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("regId", regId);
		param.put("orderStauts", orderStauts);
		return sqlSession.selectOne("getMcRegById", param);
	}
	
	public RegWay getRegWayById(String regWayId){
		return sqlSession.selectOne("getRegWayById",regWayId);
	}

	@Override
	public McReg getRegByPayNo(String payNo) {
		return sqlSession.selectOne("getRegByPayNo", payNo);
	}
	
	@Override
	public List<MyReservation> myReservation(Map<String, Object> map){
		 return sqlSession.selectList("myReservation", map);
	}

	/**
	 * 根据医生ID获取可预约数
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月11日上午11:21:57
	 * 修改日期:2015年9月11日上午11:21:57
	 * @param staffId
	 * @return
	 */
	public long getCanRegNums(String staffId){
		return sqlSession.selectOne("getCanRegNums",staffId);
	}
	
	@Override
	public long getBeReservedRegNums(String staffId) {
		return sqlSession.selectOne("getBeReservedRegNums",staffId);
	}
	
	@Override
	public ReservationDetails reservationDetails(String regId) {
		return sqlSession.selectOne("reservationDetails", regId);
	}
 
	@Override
	public McReg getMcRegAndFriendInfoByRegId(String regId){
		return sqlSession.selectOne("getMcRegAndFriendInfoByRegId", regId);
	}
	
	@Override
	public Long myReservationCount(Map<String, Object> map) {
		return sqlSession.selectOne("myReservationCount", map);
	}
}
