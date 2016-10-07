/**
 *
 */
package com.tencent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tencent.business.RefundBusiness;
import com.tencent.common.Configure;
import com.tencent.common.RandomStringGenerator;
import com.tencent.common.Util;
import com.tencent.protocol.pay_protocol.BeforePayReqData;
import com.tencent.protocol.pay_protocol.BeforePayResData;
import com.tencent.protocol.pay_protocol.PayReqDate;
import com.tencent.protocol.pay_protocol.PayReqDateJSMain;
import com.tencent.protocol.refund_protocol.RefundReqData;
import com.tencent.protocol.refund_protocol.RefundResData;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.unique.core.BeanListener;
import com.unique.core.util.FileUtil;
import com.unique.core.util.StringUtil;
import com.unique.order.dao.OrderDao;
import com.unique.order.po.McOrder;
import com.unique.order.service.OrderService;


/**
 * 微信支付工具类
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年9月28日上午11:23:37
 * 修改日期:2015年9月28日上午11:23:37
 */
@Service("wxPayUtilService")
public class WXPayServiceImpl implements WXPayService{
//	private static String key = FileUtil.readProperties("wxpay.properties", "key");
//	private static String appID = FileUtil.readProperties("wxpay.properties", "appID");
//	private static String mchID = FileUtil.readProperties("wxpay.properties", "mchID");
	private static String payTimeOut = FileUtil.readProperties("wxpay.properties", "payTimeOut");
	private static String opId = FileUtil.readProperties("wxpay.properties", "opId");
	
	public static Logger log = Logger.getLogger("pay");
	private static SimpleDateFormat orderTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	@Resource
	private OrderDao orderDao;
	@Resource
	private OrderService orderService;
	
//	private static WXPayServiceImpl instance = new WXPayServiceImpl();
//	public static WXPayServiceImpl getUtil(){
//		return instance;
//	}
	
	private void initCert(){
		String certPath = new File(BeanListener.BASE_PATH,"apiclient_cert.p12").getPath();
		Configure.setCertLocalPath(certPath);
	}
	
	/**
	 * 创建支付订单信息(这里并不需要提交，只需要交给前端去提交)
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月29日下午2:50:05
	 * 修改日期:2015年9月29日下午2:50:05
	 * @param productName 商品名称
	 * @param price 金额
	 * @param orderNo 订单号
	 * @param tradeType 支付类型
	 * @param openId 公众号
	 * @param wxProductId 微信商品ID
	 * @return
	 */
	public String buildPayInfo(String productName,String price,String orderNo,String tradeType,String openId,String wxProductId,String ip){
		initCert();
		
		Date orderTime = new Date();
		int fee =(int) (Double.parseDouble(price) * 100);
		Calendar cal = Calendar.getInstance();
		cal.setTime(orderTime);
		cal.add(Calendar.SECOND, Integer.parseInt(payTimeOut));
		PayReqDate param =  new PayReqDate(null,productName,null,orderNo,fee,ip,orderTimeFormat.format(orderTime),
				orderTimeFormat.format(cal.getTime()),null, 
				FileUtil.readProperties("comservice.properties", "comservicePath") + "wxnotify.do",
				tradeType,openId,wxProductId
				);
        //解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = xStreamForRequestPostData.toXML(param);
		
		return postDataXML;
	}
	
	public Object buildPayInfoJS(String productName,String price,String orderNo,String tradeType,String openId,String wxProductId,String ip){
		initCert();
		
		Date orderTime = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(orderTime);
		cal.add(Calendar.SECOND, Integer.parseInt(payTimeOut));
		int fee =(int) (Double.parseDouble(price) * 100);
		
		String nonceStr = RandomStringGenerator.getRandomStringByLength(32);
		String notifyUrl= FileUtil.readProperties("comservice.properties", "comservicePath") + "wxnotify.do";

		BeforePayReqData data = new BeforePayReqData(null,productName,null,null,null,null,nonceStr,notifyUrl,
				openId,orderNo,null,ip,orderTimeFormat.format(cal.getTime()),orderTimeFormat.format(orderTime),fee);
		String buildResult = null;
		BeforePayResData payResData = null;
		try {
			buildResult = WXPay.buildPayOrderService(data);
			payResData = (BeforePayResData) Util.getObjectFromXML(buildResult, BeforePayResData.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(payResData!=null && payResData.getReturn_code().equals("SUCCESS")){
			nonceStr = RandomStringGenerator.getRandomStringByLength(32);
			String nowTime = orderTimeFormat.format(new Date());
			PayReqDateJSMain main = new PayReqDateJSMain(Configure.getAppid(), nonceStr, payResData.getPrepay_id(), nowTime,"MD5",tradeType);
			
			if("APP".equals(tradeType)){
				result.put("appid", Configure.getAppid());
				result.put("partnerid", Configure.getMchid());
				result.put("prepayid", payResData.getPrepay_id());
				result.put("package", "Sign=WXPay");
				result.put("noncestr", nonceStr);
				result.put("timestamp", nowTime);
				result.put("sign", main.getSign());
				
			}else{
				//微信公众号支付
				result.put("appId", Configure.getAppid());
				result.put("timeStamp",nowTime);
				result.put("nonceStr", nonceStr);
				result.put("package", "prepay_id=" + payResData.getPrepay_id());
				result.put("signType", "MD5");
				result.put("paySign", main.getSign());
			}
			log.info("支付回传："+result);
			System.out.println(JSONObject.fromObject(result));
		}else{
			
		}
		return result;
	}
	
	
	/**
	 * 微信支付，订单退费接口
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月7日上午9:32:23
	 * 修改日期:2015年10月7日上午9:32:23
	 * @param transactionID
	 * @param orderNo
	 * @param outRefundNo
	 */
	public void refund(String orderNo){
		initCert();
		
		log.info("开始退费");
		log.info("orderNo:" +orderNo );
		String trueOrderNo = orderNo;
		if(!StringUtil.isEmpty(trueOrderNo) && trueOrderNo.indexOf("-")>0){
			trueOrderNo = orderNo.split("-")[0];
		}
		McOrder order = orderDao.getOrderByOrderNo(trueOrderNo);
		RefundReqData param = new RefundReqData(order.getPayNo(),orderNo,null,"BK_" + orderNo,
				(int) (order.getOrderMoney().doubleValue() * 100),
				(int) (order.getOrderMoney().doubleValue() * 100)-(int) (order.getPoundage().doubleValue() * 100),
				opId,"CNY");
		
		log.info(JSONObject.fromObject(param.toMap()));
		try {
			WXPay.doRefundBusiness(param, new RefundBusiness.ResultListener() {
				@Override
				public void onRefundSuccess(RefundResData refundResData) {
					log.info("退款成功");
					String orderNo = refundResData.getOut_trade_no();
					String trueOrderNo = orderNo;
					if(!StringUtil.isEmpty(trueOrderNo) && trueOrderNo.indexOf("-")>0){
						trueOrderNo = orderNo.split("-")[0];
					}
					log.info("退款orderNo:" + orderNo);
					//订单退款成功
					orderService.orderRefundSuccess(trueOrderNo);
				}
				
				//退费失败
				@Override
				public void onRefundFail(RefundResData refundResData) {
					log.info("退款失败");
					String orderNo = refundResData.getOut_trade_no();
					String trueOrderNo = orderNo;
					if(!StringUtil.isEmpty(trueOrderNo) && trueOrderNo.indexOf("-")>0){
						trueOrderNo = orderNo.split("-")[0];
					}
					if(!StringUtil.isEmpty(orderNo)){
						orderService.orderRefundFail(trueOrderNo);
					}
				}
				
				//支付请求API返回的数据签名验证失败，有可能数据被篡改了
				@Override
				public void onFailBySignInvalid(RefundResData refundResData) {
					log.info("退款失败,签名错误");
					String orderNo = refundResData.getOut_trade_no();
					String trueOrderNo = orderNo;
					if(!StringUtil.isEmpty(trueOrderNo) && trueOrderNo.indexOf("-")>0){
						trueOrderNo = orderNo.split("-")[0];
					}
					if(!StringUtil.isEmpty(orderNo)){
						orderService.orderRefundFail(trueOrderNo);
					}
				}
				
				//API返回ReturnCode为FAIL，支付API系统返回失败，请检测Post给API的数据是否规范合法
				@Override
				public void onFailByReturnCodeFail(RefundResData refundResData) {
					log.info("退款失败,签名错误");
					String orderNo = refundResData.getOut_trade_no();
					String trueOrderNo = orderNo;
					if(!StringUtil.isEmpty(trueOrderNo) && trueOrderNo.indexOf("-")>0){
						trueOrderNo = orderNo.split("-")[0];
					}
					if(!StringUtil.isEmpty(orderNo)){
						orderService.orderRefundFail(trueOrderNo);
					}
				}
				
				//API返回ReturnCode不合法，支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问
				@Override
				public void onFailByReturnCodeError(RefundResData refundResData) {
					log.info("退款失败,ReturnCode不合法");
					String orderNo = refundResData.getOut_trade_no();
					String trueOrderNo = orderNo;
					if(!StringUtil.isEmpty(trueOrderNo) && trueOrderNo.indexOf("-")>0){
						trueOrderNo = orderNo.split("-")[0];
					}
					if(!StringUtil.isEmpty(orderNo)){
						orderService.orderRefundFail(trueOrderNo);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
