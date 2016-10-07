package com.unique.test;

import org.junit.Test;

import com.unique.core.util.DesUtil;

import net.sf.json.JSONObject;

public class UserServiceTest {

	private static String url = "http://113.204.115.34:9994/appComservice/base.do?do=httpInterface&";
    @Test
    public void staffLogin() throws Exception {
        String result = PostTest.sendPost("http://113.204.115.34:9994/appComservice/base.do?do=httpInterface&module=regService&method=checkRegistration",
                "{param:\"" +
                        DesUtil.encrypt("{\"calltype\":\"3\",\"friendId\":\"443\",\"orgId\":\"6\"}") +
                        "\"}");
        String re = (String) JSONObject.fromObject(result).get("result");
        System.out.println(DesUtil.decrypt(re));
    }
    
    @Test
    public void orderHisList() throws Exception {
    	String result = PostTest.sendPost(url+"module=orderService&method=orderHisList",
    			"{param:\"" +
    					DesUtil.encrypt("{\"calltype\":\"3\",\"userId\":\"607\"}") +
    			"\"}");
    	String re = (String) JSONObject.fromObject(result).get("result");
    	System.out.println(DesUtil.decrypt(re));
    }
    @Test
    public void myReservation() throws Exception {
    	String result = PostTest.sendPost(url+"module=regService&method=myReservation",
    			"{param:\"" +
    					DesUtil.encrypt("{\"calltype\":\"3\",\"userId\":\"607\",\"type\":\"1\"}") +
    			"\"}");
    	String re = (String) JSONObject.fromObject(result).get("result");
    	System.out.println(DesUtil.decrypt(re));
    }
}
