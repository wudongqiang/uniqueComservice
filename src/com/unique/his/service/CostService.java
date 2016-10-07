/**
 * 2015年9月21日
 */
package com.unique.his.service;

import java.math.BigDecimal;

import com.unique.order.po.McOrder;
import com.unique.order.po.McOrderDet;


/**
 * <br/>
 * 缴费服务接口
 * 创建人 wdq<br/>
 * 创建时间 2015年9月21日 下午2:11:38<br/>
 * @author Administrator
 * @date 2015年9月21日
 * @description 
 */
public interface CostService {

	
	/** 门诊未缴费费用列表查询接口 */
	public final static String CLINIC_UNPAID_BY_USER_LIST = "module=costService&method=getClinicUnpaidByUserList";
	/** 门诊未缴费费用单个列表中的明细查询接口 */
	public final static String CLINIC_UNPAID_BY_APPLY_NO_LIST = "module=costService&method=getClinicUnpaidByApplyNoList";
	
	/** 门诊费用缴纳接口 */
	public final static String ADD_CLINIC_PAY = "module=costService&method=addClinicPay";
	
	/**门诊已缴费结果费用查询接口 */
	public final static String CLINIC_PAY_BY_USER_LIST = "module=costService&method=getClinicPayByUserList";
	/** 门诊已缴费费用单个列表中的明细查询接口 */
	public final static String CLINIC_PAY_BY_APPLY_NO_LIST = "module=costService&method=getClinicPayByApplyNoList";

	/** 对账查询接口 */
	public final static String BILL_ORDER_LIST = "module=billService&method=getBillOrderList";


	/**
	 * 
	 * 门诊未缴费费用列表查询接口 <br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年9月21日 下午2:19:59<br/>
	 * @param cardNo
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public Object getClinicUnpaidByUserList(String orgId,String cardNo,String userName,Long startRow,Long rows);
	
	/**
	 * 门诊未缴费费用单个列表中的明细查询接口<br/>.
	 * 创建人 wdq<br/>
	 * 创建时间 2015年9月21日 下午2:25:45<br/>
	 * @param applyNo
	 * @return
	 */
	public String getClinicUnpaidByApplyNoList(String orgId,String applyNo,String patientId);
	
	/**
	 * 门诊费用缴纳接口<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年9月21日 下午4:37:11<br/>
	 * @param orgCode 分院代码
	 * @param patientId 病人Id
	 * @param payWay 支付方式代码（平台提供对应支付代码）以下为HIS代码 A0 支付宝 A1 银行卡 A2 微信 A3 信用卡 A4  APP预交金 A5 其他
	 * @param applyNo 费用申请单号
	 * @param costType 费用类型（1药费 2 诊疗）
	 * @param payCost 缴费金额
	 * @param flowNo 交易流水号，订单号
	 * @return
	 */
	public boolean addClinicPay(String orgId,String payWay,
			 String applyNo,  String flowNo,String patientId);
	
	/**
	 * 门诊已缴费结果费用查询接口<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年9月21日 下午4:33:13<br/>
	 * @param cardNo 查询号码
	 * @param startRow 开始行
	 * @param rows 行数
	 * @return
	 */
	public String getClinicPayByUserList(String orgId,String cardNo,String userName,Long startRow,Long rows);	 

	/**
	 * 门诊已缴费费用单个列表中的明细查询接口<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年9月21日 下午4:35:04<br/>
	 * @param applyNo 申请单号
	 * @return
	 */
	public String getClinicPayByApplyNoList(String orgId,String applyNo,String patientId);
	
	public void paySuccess(McOrderDet orderDet ,String payWayCode,String payNo,McOrder order,String orderPayId);
	
	/**
	 * 对账查询<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月10日 下午12:38:21<br/>
	 * @param orgId 机构Id
	 * @param flowNoApp app流水号 对应 订单Id
	 * @param startTime 开始时间   格式 yyyy-mm-dd
	 * @param endTime   结束时间   格式 yyyy-mm-dd
	 * @param receiptNo 收据号
	 * @param caseType 门诊/住院 0 门诊 1住院
	 * @param money 金额
	 * @param operationType 操作类型 1 交费 2退费 3结算结存 4结算取消 5作废
	 * @param payType 支付方式 A0 支付宝 A1 银行卡 A2 微信 A3 信用卡   A4 APP预交金 A5 其他
	 * @param startRow  开始行
	 * @param rows		每页行数
	 * @return
	 */
	public String getBillOrderList(String orgId,String flowNoApp, String startTime, String endTime,
			String receiptNo,
			String caseType,
			BigDecimal money,
			String operationType,
			String payType, 
			Long startRow,Long rows);
}
