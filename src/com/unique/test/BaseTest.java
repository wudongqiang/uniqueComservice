package com.unique.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tencent.common.Configure;
import com.unique.core.util.JsonUtil;
import com.unique.g2pay.core.PayCreator;
import com.unique.g2pay.core.util.JSONUtil;
import com.unique.g2pay.core.vo.BaseReturnObject;
import com.unique.g2pay.core.vo.ClientType;
import com.unique.g2pay.core.vo.PayType;
import com.unique.g2pay.wx.vo.WxConfig;
import com.unique.g2pay.wx.vo.WxPrePayOrderParam;

import net.sf.json.JSONObject;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:context/*spring.xml"})
public class BaseTest {

	protected void showResult(Object obj){
		System.out.println(JSONObject.fromObject(obj, JsonUtil.getConfig()));
	}
	static String  txt = "{\"p12\":\"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\",\"muchId\":\"1357677102\",\"pwd\":\"341806\",\"appId\":\"wx7c99812958878129\",\"key\":\"kqyy2397asdfasd0as72ralsg823gnas\"}";
	public static void main(String[] args) {
		/************************************APP微信支付*****************************************/
		Configure config = Configure.getInstance(txt, "WXZF_APP");
		WxConfig wxCfg = new WxConfig();
		wxCfg.setAppID(config.getAppid());
		wxCfg.setCertbytes(config.getCertbytes());
		wxCfg.setKey(config.getKey());
		wxCfg.setMchID(config.getMchid());
		wxCfg.setSubMchID(config.getSubMchid());
		
		WxPrePayOrderParam param = new WxPrePayOrderParam();
		param.setIp("127.0.0.1");
		param.setBody("test");
		param.setName("test");
		param.setNotify_url("www.baidu.com");
		param.setOut_trade_no("23129234110012931");
		param.setTotal_fee(1);
		//param.setOpenid(openId);
		param.setTrade_type("APP");
		
		BaseReturnObject returnObject = null;
		try {
			returnObject = PayCreator.initPay(wxCfg, PayType.WX, null).buildPayOrder(param, ClientType.APP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("payReq", returnObject.getReturn_object());
		resultMap.put("return_code", returnObject.getReturn_code());
		resultMap.put("return_msg", returnObject.getReturn_msg());
		System.out.println("========weixin-APP======");
		System.out.println("微信APP:"+JSONUtil.toJSONString(resultMap) );
	}
}
