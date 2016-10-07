/**
 * 2015年9月21日
 */
package com.unique.his.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;
import com.unique.alipay.util.UtilDate;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.util.FileUtil;
import com.unique.core.util.HttpUtil;
import com.unique.core.util.RemotUtils;
import com.unique.core.util.StringUtil;
import com.unique.order.dao.OrderDao;
import com.unique.order.po.McOrder;
import com.unique.order.po.McOrderDet;
import com.unique.order.po.McProduct;
import com.unique.order.po.SourceType;
import com.unique.order.service.OrderService;
import com.unique.org.dao.OrgDao;
import com.unique.reg.po.Org;
import com.unique.system.service.SystemService;
import com.unique.user.dao.UserDao;
import com.unique.user.po.AmsUser;

/**
 * <br/>
 * 缴费服务接口实现类
 * 创建人 wdq<br/>
 * 创建时间 2015年9月21日 下午2:11:38<br/>
 * @author Administrator
 * @date 2015年9月21日
 * @description 
 */
@Service("costService")
public class CostServiceImpl implements CostService{
	@Resource
	private OrgDao orgDao;
	@Resource
	private UserDao userDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private OrderService orderService;
	@Resource
	private SystemService systemService;
	public static Logger log = Logger.getLogger("pay");
	///微信推送地址
	private static String WX_PUSH = FileUtil.readProperties("comservice.properties", "wxpush");
	/**
	 * 门诊未缴费费用列表查询接口
	 */
	@HttpMethod
	@Override
	public Object getClinicUnpaidByUserList(@NotNull String orgId, @NotNull String cardNo,@NotNull String userName, Long startRow,
			Long rows) {
		Org org = orgDao.getOrgInfo(orgId);
		if(startRow==null) startRow= 0L;
		if(rows==null) rows = 10L;
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("searchType", "4");
		params.put("cardNo", cardNo);
		params.put("patientName", userName);
		if(!(startRow==null || rows==null)){
			params.put("startRow", startRow);
			params.put("rows", rows);
		}
		String jsonStr = RemotUtils.getRemotData(org.getHospitalUrl(), CLINIC_UNPAID_BY_USER_LIST, params);
		JSONObject obj = JSONObject.fromObject(jsonStr);
		JSONObject resultObj =  obj.getJSONObject("result");
		JSONArray arr =  (JSONArray) resultObj.get("costList");
		//诊间缴费产品
		McProduct product = orderDao.getProductByCode("ZJ_TREAT", orgId);
		for(int i=0;arr!=null&&product!=null&&i<arr.size();i++){
			((JSONObject)arr.get(i)).put("mcProductId", product.getMcProductId());
		}
		resultObj.put("costList", arr);
		obj.put("result",resultObj);
		return obj;
	}

	@HttpMethod
	@Override
	public String getClinicUnpaidByApplyNoList(@NotNull String orgId,String applyNo,@NotNull String patientId) {
		Org org = orgDao.getOrgInfo(orgId);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("applyNo", applyNo);
		params.put("patientId", StringUtil.isEmpty(patientId)?"":patientId);
		return RemotUtils.getRemotData(org.getHospitalUrl(), CLINIC_UNPAID_BY_APPLY_NO_LIST, params);
	}
 
	@Override
	public boolean addClinicPay( String orgId,String payWay,
			 String applyNo,  String flowNo,String patientId) {
		Org org = orgDao.getOrgInfo(orgId);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("payWay", payWay);
		params.put("applyNo", applyNo);
		params.put("flowNo", flowNo);
		params.put("patientId", patientId);
		
		//his返回结果
		log.info(JSONObject.fromObject(params));
		String jsonResult = "{}";
		try{
			jsonResult = RemotUtils.getRemotData(org.getHospitalUrl(), ADD_CLINIC_PAY, params);
		}catch(Exception e){
			Logger elog = Logger.getLogger("exception");
			elog.info(ExceptionUtils.getStackTrace(e));
		}
		log.info("诊间缴费医院返回");
		log.info(jsonResult);
		JSONObject costResult = JSONObject.fromObject(jsonResult);
		String retCode = "-1";
		
		if(costResult.containsKey("result"))
			costResult = (JSONObject) costResult.get("result");
		
		if(costResult.containsKey("retCode"))
			retCode = costResult.getString("retCode");
		
		if("0".equals(retCode)){
			return true;
		}else{
			return false;
		}
	}

	@HttpMethod
	@Override
	public String getClinicPayByUserList(@NotNull String orgId,@NotNull String cardNo,@NotNull String userName, Long startRow, Long rows) {
		if(startRow==null) startRow= 0L;
		if(rows==null) rows = 10L;
		Org org = orgDao.getOrgInfo(orgId);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("searchType", "4");
		params.put("cardNo", cardNo);
		params.put("patientName", userName);
		if(!(startRow==null || rows==null)){
			params.put("startRow", startRow);
			params.put("rows", rows);
		}
		
		return RemotUtils.getRemotData(org.getHospitalUrl(), CLINIC_PAY_BY_USER_LIST, params);
	}

	@HttpMethod
	@Override
	public String getClinicPayByApplyNoList(@NotNull String orgId,@NotNull String applyNo,String patientId) {
		Org org = orgDao.getOrgInfo(orgId);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("applyNo", applyNo);
		params.put("patientId", StringUtil.isEmpty(patientId)?"":patientId);
		return RemotUtils.getRemotData(org.getHospitalUrl(), CLINIC_PAY_BY_APPLY_NO_LIST, params);
	}
	
	/**
	 * 诊间缴费成功
	 */
	@Transactional
	public void paySuccess(McOrderDet orderDet ,String payWayCode,String payNo,McOrder order,String orderPayId){
		log.info("支付成功");
		//更新状态为支付成功
		
		/************走医院接口************/
		String paywayCode = "A5";
		if(payWayCode.equals("ZFB")){
			paywayCode = "A0";
		}else if(payWayCode.equals("YHK")){
			paywayCode = "A1";
		}else if(payWayCode.equals("WXZF")){
			paywayCode = "A2";
		}else if(payWayCode.equals("XYK")){
			paywayCode = "A3";
		}else if(payWayCode.equals("YJJ")){
			paywayCode = "A4";
		}
		boolean result = addClinicPay(orderDet.getOrgId().toString(), paywayCode, 
				orderDet.getProductToId(), order.getOrderId().toString(),orderDet.getHisUserId());
		
		log.info("addClinicPay:" + result);
		if(result){
			orderDao.updateOrderPayStatus(payNo, "3",orderPayId);
			
			order.setOrderStatus("5");
			orderService.updateOrder(order);
			//推送消息
			systemService.sendOrderMsg(order, orderDet, 1);
		}else{
			//医院端接口调用失败--退费
			//
			orderDao.updateOrderPayStatus(payNo, "4",orderPayId);
			
			order.setOrderStatus("6");
			orderService.updateOrder(order);
			//退费流程
			int unOrderResultCode = -1;
			try{
				HashMap unOrderResult = orderService.unOrder(order.getOrderId().toString(), order.getUserId().toString(),
						"医院端接口调用失败", order.getUserId().toString(), "site",false);
				if(unOrderResult.containsKey("retCode")){
					unOrderResultCode = (Integer) unOrderResult.get("retCode");
				}
			}catch(RuntimeException e){
		        // 在后台中输出错误异常异常信息，通过log4j输出。  
		        Logger elog = Logger.getLogger("exception");  
		        elog.info("**************************************************************");
		        elog.info("退订单过程中出现错误");
		        elog.info("Exception class: " + e.getClass().getName());  
		        elog.info("ex.getMessage():" + e.getMessage());
		        String exceptionStr = ExceptionUtils.getFullStackTrace(e);
		        elog.info(exceptionStr);
		        elog.info("**************************************************************");  
			}
//			orderService.refund(payWayCode, order.getOrderNo());
		}
	}
	
	@Override
	@HttpMethod
	public String getBillOrderList(@NotNull String orgId, String flowNoApp, String startTime, String endTime,
			String receiptNo,
			String caseType,
			BigDecimal money,
			String operationType,
			String payType, 
			Long startRow,Long rows) {
		Org org = orgDao.getOrgInfo(orgId);
		Map<String,Object> params = new HashMap<String,Object>();
		if(!(startRow==null || rows==null)){
			params.put("startRow", startRow);
			params.put("rows", rows);
		}
		
//		params.put("flowNoHis", ""); //HIS流水号
		if(!StringUtil.isEmpty(flowNoApp)){
			params.put("flowNoApp", flowNoApp); //APP流水号 -- 订单Id
		}
		if(!StringUtil.isEmpty(startTime)){
			params.put("startTime", startTime+" 00:00:00"); //开始时间
		}
		if(!StringUtil.isEmpty(endTime)){
			params.put("endTime", endTime+" 23:59:59"); //结束时间
		}
		
		if(!StringUtil.isEmpty(receiptNo)){
			params.put("receiptNo",receiptNo); //收据号
		}
		if(!StringUtil.isEmpty(caseType)){
			params.put("caseType", caseType); //门诊/住院 0 门诊 1住院
		}
		if(money!=null){
			params.put("money", money==null?"":money); //金额 
		}
		if(!StringUtil.isEmpty(operationType)){
			params.put("operationType", operationType); //操作类型1 交费 2退费 3结算结存 4结算取消 5作废
		}
		if(!StringUtil.isEmpty(payType)){
			params.put("payType", payType); //支付方式	A0 支付宝 		A1 银行卡		A2 微信		A3 信用卡		A4  APP预交金		A5 其他
		}
//		params.put("operation", ""); //操作员
		String jsonStr = RemotUtils.getRemotData(org.getHospitalUrl(), BILL_ORDER_LIST, params);
		JSONObject obj = JSONObject.fromObject(jsonStr);
		JSONArray arr =  (JSONArray) obj.getJSONObject("result").get("billOrderList");
		//查询订单
		McOrder mcOrder = null;
		JSONObject jsonObj = null;
		for(int i=0;arr!=null&&i<arr.size();i++){
			jsonObj = (JSONObject)arr.get(i);
			mcOrder = orderDao.getOrderByOrderNo(jsonObj.get("flowNoApp")+"");
			if(mcOrder!=null){
				jsonObj.put("orderId", mcOrder.getOrderId().toString());
				jsonObj.put("orderNo", mcOrder.getOrderNo());
				jsonObj.put("orderName", mcOrder.getOrderName());
				jsonObj.put("payWayName", mcOrder.getPayWayName());
				jsonObj.put("createTime", UtilDate.getDateToStr(mcOrder.getCreateTime(), UtilDate.simple));
				jsonObj.put("orderMoney", mcOrder.getOrderMoney().toString());
				jsonObj.put("orderStatusName", mcOrder.getOrderStatusName());
				jsonObj.put("orderStatus", mcOrder.getOrderStatus());
			}else{
				jsonObj.put("orderId", "");
				jsonObj.put("orderNo", "");
				jsonObj.put("orderName", "");
				jsonObj.put("payWayName", "");
				jsonObj.put("createTime","");
				jsonObj.put("orderMoney", "");
				jsonObj.put("orderStatusName", "");
				jsonObj.put("orderStatus", "");
			}
		}
		obj.put("billOrderList",arr);
		return obj.toString();
	}
}
