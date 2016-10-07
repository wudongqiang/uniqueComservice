package com.unique.test;

import org.springframework.stereotype.Service;

import com.unique.core.annotation.HttpMethod;
import com.unique.core.annotation.ParamNoNull;
import com.unique.sms.SmsClient;

@Service("testSmsService")
public class TestSms {
	/**
	 * 测试短信发送
	 * http://123.57.73.123:9999/uniqueComservice/base.do?do=httpInterface&module=testSmsService&method=send
	 * {param:\"" + DesUtil.encrypt("{\"phone\":\"13888888888\",\"content\":\"xxxxxxxxxxxxxxxx\"}") }");
	 */
	@HttpMethod
	public void send(@ParamNoNull String content, @ParamNoNull String phone) {
		SmsClient.sendMsg(content, phone);
	}
}
