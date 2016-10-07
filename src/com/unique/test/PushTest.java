package com.unique.test;

import com.unique.core.util.DesUtil;
import com.unique.core.util.HttpUtil;


public class PushTest {
//	public static void main(String[] args) {
//		JPushClient jpush = new JPushClient("b53d93ed0673d6a62434ea14", "f12ea951c3145ccd43900bd2");
//		try {
//			Map<String, String> extras = new HashMap<String, String>();
//			extras.put("test", "123");
////			PushResult p = jpush.sendIosNotificationWithAlias("adsfadsfadsf", extras, "ZJK_35F60429E5AE452B859FA5A4A831041C");
//			PushResult p = jpush.sendIosMessageWithAlias(null, "测试内容", "1_41915C1ED6D84B458D25D353C90D2E13");
//			if(p.isResultOK()){
//	            System.out.println("sendNotification by tag:**返回状态：" +
//                        p.getOriginalContent() +
//                        "  频率次数：" + p.getRateLimitQuota() +
//                        "  可用频率：" + p.getRateLimitRemaining() +
//                        "  重置时间：" + p.getRateLimitReset());
//			}
//			
//			
////			jpush.sendAndroidMessageWithAlias("测试", "测试内容", "1213");
//		} catch (APIConnectionException e) {
//			e.printStackTrace();
//		} catch (APIRequestException e) {
//			e.printStackTrace();
//		}
//
//	}
	
	public static void main(String[] args) throws Exception {
		HttpUtil.sendPost(
				"http://10.0.0.200/ams/",
				"{param:\""
						+ DesUtil.encrypt("{calltype:\"4\",\"serviceName\":\"AP0002\"}")
						+ "\"}");
	}
	
}
