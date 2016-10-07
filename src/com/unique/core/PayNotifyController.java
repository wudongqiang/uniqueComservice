package com.unique.core;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.http.converter.ObjectToStringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

import com.tencent.business.ScanPayBusiness.ResultListener;
import com.tencent.common.Configure;
import com.tencent.common.Signature;
import com.tencent.common.Util;
import com.tencent.common.report.ReporterFactory;
import com.tencent.common.report.protocol.ReportReqData;
import com.tencent.common.report.service.ReportService;
import com.tencent.protocol.pay_protocol.ScanPayReqData;
import com.tencent.protocol.pay_protocol.ScanPayResData;
import com.unique.alipay.util.AlipayNotify;
import com.unique.core.util.HttpUtil;
import com.unique.order.service.OrderService;

/**
 * 支付回调通知
 * @author 余宁
 *
 */
@Controller("/notify.do")
public class PayNotifyController {
	@Resource
	private OrderService orderService;
	
	public static Logger log = Logger.getLogger("pay");
	
	@RequestMapping(params="method=findCert")
    public String findCert(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String basePath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/");
		File cert = new File(basePath,"apiclient_cert.p12");
		System.out.println(cert.exists());
		System.out.println(cert.getPath());
		
		return null;
	}
	
	/**
	 * 支付宝通知回调
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(params="method=alipay")
	public String alipay(HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("============ 支付宝回调收到 ==============");
		PrintWriter out = response.getWriter();
		response.setContentType("text/json;charset=utf-8");
		//清空缓存
		response.setDateHeader("expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("pragma", "no-cache");
		
		
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
//		log.info("requestParams:" + JSONObject.fromObject(requestParams));
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		 log.info("支付API返回的数据如下：\n" +params);
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号

		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//支付宝交易号

		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		log.info("============ 开始验证 ==============");
		if(AlipayNotify.verify(params)){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码

			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			log.info("trade_status:" + trade_status);
			log.info("out_trade_no:" + out_trade_no);
			if(trade_status.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
                try{
                	orderService.paySuccess("ZFB", null,trade_no, out_trade_no,null);
                }catch(Exception e){
        	        // 在后台中输出错误异常异常信息，通过log4j输出。  
        	        Logger elog = Logger.getLogger("exception");  
        	        elog.info("**************************************************************");
        	        elog.info("Exception class: " + e.getClass().getName());  
        	        elog.info("ex.getMessage():" + e.getMessage());  
        	        String exceptionStr = ExceptionUtils.getFullStackTrace(e);
        	        elog.info(exceptionStr);
        	        elog.info("**************************************************************");  
                }
				//注意：
				//该种交易状态只在两种情况下出现
				//1、开通了普通即时到账，买家付款成功后。
				//2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
			} else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
                try{
                	orderService.paySuccess("ZFB", null,trade_no, out_trade_no,null);
                }catch(Exception e){
        	        // 在后台中输出错误异常异常信息，通过log4j输出。  
        	        Logger elog = Logger.getLogger("exception");  
        	        elog.info("**************************************************************");
        	        elog.info("Exception class: " + e.getClass().getName());  
        	        elog.info("ex.getMessage():" + e.getMessage());  
        	        String exceptionStr = ExceptionUtils.getFullStackTrace(e);
        	        elog.info(exceptionStr);
        	        elog.info("**************************************************************");  
                }
				
				//注意：
				//该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
			}

			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
				
			out.println("success");	//请不要修改或删除

			//////////////////////////////////////////////////////////////////////////////////////////
		}else{//验证失败
			out.println("fail");
		}
		return null;
	}
	
	
	/**
	 * 微信支付通知回调
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月29日下午2:26:58
	 * 修改日期:2015年9月29日下午2:26:58
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	@RequestMapping(value="/wxnotify.do")
    public String wxpay(HttpServletRequest request,HttpServletResponse response) throws IOException, ParserConfigurationException, SAXException{
		log.info("============ 微信支付回调收到 ==============");
		PrintWriter out = response.getWriter();
		response.setContentType("text/json;charset=utf-8");
		//清空缓存
		response.setDateHeader("expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("pragma", "no-cache");
		
        //接受API返回
        StringBuffer payServiceResponseString = new StringBuffer();
        long costTimeStart = System.currentTimeMillis();
        String inputLine;
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				payServiceResponseString.append(inputLine);
			}
			request.getReader().close();
		} catch (Exception e) {
			e.printStackTrace();
		}

        log.info("支付API返回的数据如下：\n" + payServiceResponseString.toString());

        long costTimeEnd = System.currentTimeMillis();
        long totalTimeCost = costTimeEnd - costTimeStart;
        log.info("api请求总耗时：" + totalTimeCost + "ms");

        //将从API返回的XML数据映射到Java对象
        ScanPayResData scanPayResData = (ScanPayResData) Util.getObjectFromXML(payServiceResponseString.toString(), ScanPayResData.class);

        if (scanPayResData == null || scanPayResData.getReturn_code() == null) {
            log.error("【支付失败】支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问");
        }

        if (scanPayResData.getReturn_code().equals("FAIL")) {
            //注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
            log.error("【支付失败】支付API系统返回失败，请检测Post给API的数据是否规范合法");
        } else {
            log.info("支付API系统成功返回数据");
            //--------------------------------------------------------------------
            //收到API的返回数据的时候得先验证一下数据有没有被第三方篡改，确保安全
            //--------------------------------------------------------------------
            if (!Signature.checkIsSignValidFromResponseString(payServiceResponseString.toString())) {
                log.error("【支付失败】支付请求API返回的数据签名验证失败，有可能数据被篡改了");
                return null;
            }

            //获取错误码
            String errorCode = scanPayResData.getErr_code();
            //获取错误描述
            String errorCodeDes = scanPayResData.getErr_code_des();

            if (scanPayResData.getResult_code().equals("SUCCESS")) {

                //--------------------------------------------------------------------
                //1)直接扣款成功
                //--------------------------------------------------------------------

                log.info("【一次性支付成功】");
                final String out_trade_no = scanPayResData.getOut_trade_no();
                final String rransaction_id = scanPayResData.getTransaction_id();
                try{
                	new Thread(new Runnable() {
						@Override
						public void run() {
							orderService.paySuccess("WXZF", null,rransaction_id ,out_trade_no,null);
						}
					}).start();
                }catch(Exception e){
        	        // 在后台中输出错误异常异常信息，通过log4j输出。  
        	        Logger elog = Logger.getLogger("exception");  
        	        elog.info("**************************************************************");
        	        elog.info("Exception class: " + e.getClass().getName());  
        	        elog.info("ex.getMessage():" + e.getMessage());  
        	        String exceptionStr = ExceptionUtils.getFullStackTrace(e);
        	        elog.info(exceptionStr);
        	        elog.info("**************************************************************");  
                }
                
                out.print("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
                out.flush();
                out.close();
            }else{

                //出现业务错误
                log.info("业务返回失败");
                log.info("err_code:" + errorCode);
                log.info("err_code_des:" + errorCodeDes);

                //业务错误时错误码有好几种，商户重点提示以下几种
                if (errorCode.equals("AUTHCODEEXPIRE") || errorCode.equals("AUTH_CODE_INVALID") || errorCode.equals("NOTENOUGH")) {

                    //--------------------------------------------------------------------
                    //2)扣款明确失败
                    //--------------------------------------------------------------------

                    //对于扣款明确失败的情况直接走撤销逻辑
//                    doReverseLoop(outTradeNo,resultListener);

                    //以下几种情况建议明确提示用户，指导接下来的工作
                    if (errorCode.equals("AUTHCODEEXPIRE")) {
                        //表示用户用来支付的二维码已经过期，提示收银员重新扫一下用户微信“刷卡”里面的二维码
                        log.warn("【支付扣款明确失败】原因是：" + errorCodeDes);
                    } else if (errorCode.equals("AUTH_CODE_INVALID")) {
                        //授权码无效，提示用户刷新一维码/二维码，之后重新扫码支付
                        log.warn("【支付扣款明确失败】原因是：" + errorCodeDes);
                    } else if (errorCode.equals("NOTENOUGH")) {
                        //提示用户余额不足，换其他卡支付或是用现金支付
                        log.warn("【支付扣款明确失败】原因是：" + errorCodeDes);
                    }
                } else if (errorCode.equals("USERPAYING")) {
                	log.warn("需要输入密码");
                    //--------------------------------------------------------------------
                    //3)需要输入密码
                    //--------------------------------------------------------------------

                    //表示有可能单次消费超过300元，或是免输密码消费次数已经超过当天的最大限制，这个时候提示用户输入密码，商户自己隔一段时间去查单，查询一定次数，看用户是否已经输入了密码
//                    if (doPayQueryLoop(payQueryLoopInvokedCount, outTradeNo,resultListener)) {
//                        log.i("【需要用户输入密码、查询到支付成功】");
//                        resultListener.onSuccess(scanPayResData,transactionID);
//                    } else {
//                        log.i("【需要用户输入密码、在一定时间内没有查询到支付成功、走撤销流程】");
//                        doReverseLoop(outTradeNo,resultListener);
//                        resultListener.onFail(scanPayResData);
//                    }
                } else {
                	log.warn("扣款未知失败");
                    //--------------------------------------------------------------------
                    //4)扣款未知失败
                    //--------------------------------------------------------------------

//                    if (doPayQueryLoop(payQueryLoopInvokedCount, outTradeNo,resultListener)) {
//                        log.i("【支付扣款未知失败、查询到支付成功】");
//                        resultListener.onSuccess(scanPayResData,transactionID);
//                    } else {
//                        log.i("【支付扣款未知失败、在一定时间内没有查询到支付成功、走撤销流程】");
//                        doReverseLoop(outTradeNo,resultListener);
//                        resultListener.onFail(scanPayResData);
//                    }
                }
            }
        }
		return null;
    }
	
	public static void main(String[] args) {
		String xml = "<xml><appid><![CDATA[wxd97756427937e00e]]></appid><bank_type><![CDATA[CMB_DEBIT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1273384901]]></mch_id><nonce_str><![CDATA[0f3598gtqmbmwq9f2ax7gb0njnxmaat6]]></nonce_str><openid><![CDATA[oogvZtymgc1_1ZHfIWMTnPy7OEPg]]></openid><out_trade_no><![CDATA[ZJ_PAY20151109103657K2RXO]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[889C927FBCE5729FBD35DDE53FEE3098]]></sign><time_end><![CDATA[20151109103717]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1009360865201511091517336485]]></transaction_id></xml>";
		HttpUtil.sendPost("http://127.0.0.1:8080/uniqueComservice/wxnotify.do", xml);
	}
}
