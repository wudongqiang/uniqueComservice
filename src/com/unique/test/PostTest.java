package com.unique.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.unique.core.util.DesUtil;

public class PostTest {
	public static void main(String[] args) throws Exception {
		String result = sendPost(
				"http://113.204.115.34:9994/appComservice/base.do?do=httpInterface&module=regService&method=checkRegistration",
				"{\"DateOfBirth\":\"19820615\",\"Wearing\":1,\"UID\":\"E80EA0E8374413267E59A71F45FE804D\",\"MT\":1,\"CreateTime\":\"20151030095139186\",\"Sex\":1,\"msgsn\":1447142406759,\"Height\":172.0,\"Weight\":56.0}");

		System.out.println(result);
		
		System.out.println(DesUtil.decrypt("FE8E315ACA1512D756F61C720549DC7FC5EF54DFF26B68B95E985D39CCF3AEC221C75C6C83BBA25222F736817D82412A8127355A7581E9F7DF053022B2882F523D94F57429676F12"));
	}

	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        try {  
            URL serverUrl = new URL(url);  
            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();  
            conn.setConnectTimeout(20000);  
            conn.setRequestMethod("POST");  
            conn.addRequestProperty("Referer","http://221.179.9.XX:8080/bpss/index.jsp#");  
            conn.addRequestProperty("Accept", "*/*");  
            conn.addRequestProperty("Accept-Language", "zh-cn");  
            conn.addRequestProperty("Content-type", "text/plain");  
            conn.addRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");  
            //conn.addRequestProperty("Cookie", "JSESSIONID=60769A616C7132CB8BD8023AC05D214D;");  
            conn.setDoOutput(true);  
            conn.connect();  
            conn.getOutputStream().write(param.getBytes());  
             InputStream ins =  conn.getInputStream();    
             String charset = "utf-8";   
             InputStreamReader inr = new InputStreamReader(ins, charset);    
             BufferedReader br = new BufferedReader(inr);    
             String line = "";    
             StringBuffer sb = new StringBuffer();     
             do{  
                 sb.append(line);    
                 line = br.readLine();   
             }while(line != null);  
             return sb.toString();    
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
            return null;  
        } catch (ProtocolException e) {  
            e.printStackTrace();  
            return null;  
        } catch (IOException e) {  
            e.printStackTrace();  
            return null;  
        }  
    }    
}
