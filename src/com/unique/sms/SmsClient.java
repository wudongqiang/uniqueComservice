package com.unique.sms;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;

import com.unique.core.util.FileUtil;

public class SmsClient {
	private static final Logger log = Logger.getLogger("comservice");
	
	private static final String serverUrlString = FileUtil.readProperties(
			"comservice.properties", "sms.server.url");
	private static final String account = FileUtil.readProperties(
			"comservice.properties", "sms.account");
	private static final String password = FileUtil.readProperties(
			"comservice.properties", "sms.password");
	private static final String sign = FileUtil.readProperties(
			"comservice.properties", "sms.sign");
	public static final String subcode = ""; // 子号码，可为空
	public static final String msgid = ""; // 短信id，查询短信状态报告时需要，可为空
	public static final String sendtime = ""; // 定时发送时间

	public static final void sendMsg(String content, String phone) {
		log.info(phone+ "," + content);
		
		doGet(content, phone);

	}

	private static final String getMsgXml(String content, String phone) {
		StringBuffer xmlBuf = new StringBuffer();
		xmlBuf.append( "<?xml version=\"1.0\" encoding=\"UTF-8\"?><message><account>")
				.append(account)
				.append("</account><password>")
				.append(password)
				.append("</password><msgid></msgid><phones></phones><content></content><sign></sign><subcode></subcode><sendtime></sendtime></message>");
		
		String xml = xmlBuf.toString();
		xml.replace("<phones></phones>", "<phones>" + phone + "</phones>");
		xml.replace("<content></content>", "<content>" + content + "</content>");
		return xml;
	}

	private static final String doGet(String content, String phone) {
		String param = getMsgXml(content, phone);
		String response = null;
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(serverUrlString);
		method.setRequestHeader("Content-Type", "text/html;charset=gbk");
		try {
			method.setQueryString(URIUtil.encodeQuery(param, "GBK"));
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				response = method.getResponseBodyAsString();
			}
		} catch (URIException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			method.releaseConnection();
		}
		return response;
	}

}
