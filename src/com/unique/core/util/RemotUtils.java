/**
 * 2015年9月18日
 */
package com.unique.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * <br/>
 * 远程调用接口工具类
 * 创建人 wdq<br/>
 * 创建时间 2015年9月18日 下午2:18:06<br/>
 * @author Administrator
 * @date 2015年9月18日
 * @description 
 */
public final class RemotUtils {

	/**
	 * <br/>
	 * 得到远程数据
	 * 创建人 wdq<br/>
	 * 创建时间 2015年9月18日 下午2:29:25<br/>
	 * @param url
	 * @param module
	 * @param params
	 * @return
	 */
	public final static String getRemotData(String url,String module,Map<String,Object> params){
//		module=queueService&method=getQueueByUserList
    	String result = null;
		try {
			result = sendPost(url+ module,
					"{param:\""
							+ DesUtil
	    					.encrypt("{\"calltype\":\"4\",\"userID\":\"zskq2015\",\"deviceToken\":\"zskq2015-YUYOU\","
	    							+ "\"transParams\":"+JSONObject.fromObject(params).toString()+"}")  
	    					+ "\"}");
			System.out.println("远程his返回数据(加密)："+result);
			result = (String) JSONObject.fromObject(result).get("result");
			System.out.println("远程his返回数据(解密)："+result);
			result = DesUtil.decrypt(result);
		} catch (Exception e) {
			e.printStackTrace();
			return "{'result':{'retCode':'-1','retMessage':'未知异常！','list':[]}}";
		}
	    return result;
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
    private final static String sendPost(String url, String param) {
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
