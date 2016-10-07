package com.unique.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpUtil {
    public static String sendPost(String url, String param) {
        try {  
            URL serverUrl = new URL(url);  
            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();  
            conn.setConnectTimeout(20000);  
            conn.setRequestMethod("POST");  
            conn.addRequestProperty("Accept", "*/*");  
            conn.addRequestProperty("Accept-Language", "zh-cn");  
            conn.addRequestProperty("Content-type", "text/plain");
            conn.addRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");  
            //conn.addRequestProperty("Cookie", "JSESSIONID=60769A616C7132CB8BD8023AC05D214D;");  
            conn.setDoOutput(true);  
            conn.connect();  
            conn.getOutputStream().write(param.getBytes("UTF-8"));  
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
    
    public static void main(String[] args) {
    	try {
			String result = HttpUtil.sendPost("http://123.56.166.35/testsms/TestSms1?phone=18623333386&content=" + URLEncoder.encode("你好","UTF-8"),"");
			System.out.println(result);
    	} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
