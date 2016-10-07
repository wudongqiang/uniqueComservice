package com.unique.core.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * JSON帮助类
 * @author Angel
 *
 */
public class JsonUtil {
	/***
	 * 返回前台JSON对象
	 * @param response
	 * @param obj
	 * @return true发送成功，false发送失败
	 */
	public static boolean sendJsonObject(HttpServletResponse response,Object obj){
		response.setContentType("text/json;charset=utf-8");
		//清空缓存
		response.setDateHeader("expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("pragma", "no-cache");
		
		//转换为JSON字符串
		String responseString = "";
		if(obj instanceof Collection){
			responseString = JSONArray.fromObject(obj,JsonUtil.getConfig()).toString();
		}else{
			responseString = JSONObject.fromObject(obj,JsonUtil.getConfig()).toString();
		}
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(responseString);
			out.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("加密失败!");
			e.printStackTrace();
		}finally{
			out.close();
		}
		return false;
	}
	
	private static JsonConfig jsonConfig = null;
	public static JsonConfig getConfig(){
		if(jsonConfig==null){
			jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,
					new JsonDateValueProcessor());
			
			//bigdecimal
			jsonConfig.registerJsonValueProcessor(BigDecimal.class, new JsonValueProcessor() {
				@Override
				public Object processObjectValue(String key, Object value, JsonConfig arg2) {
					//如果vlaue为null，就返回"",不为空就返回他的值，
					if (value == null) {
						return "";
					}
					return value;
				}
				
				@Override
				public Object processArrayValue(Object value, JsonConfig arg1) {
					// TODO Auto-generated method stub
					return value;
				}
			});
		}
		return jsonConfig;
	}
	
	/***
	 * 将对象序列化为JSON文本
	 * 
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object) {
		if (object instanceof Collection) {
			return JSONArray.fromObject(object, getConfig()).toString();
		} else {
			return JSONObject.fromObject(object, getConfig()).toString();
		}
	}
	
	/** 
    * 将json格式的字符串解析成Map对象 <li> 
    * json格式：{"name":"admin","retries":"3fff","testname" 
    * :"ddd","testretries":"fffffffff"} 
    */  
   public static HashMap<String, Object> toHashMap(Object object) {  
       HashMap<String, Object> data = new HashMap<String, Object>();  
       // 将json字符串转换成jsonObject  
       JSONObject jsonObject = JSONObject.fromObject(object);  
       Iterator it = jsonObject.keys();  
       // 遍历jsonObject数据，添加到Map对象  
       while (it.hasNext())  
       {  
           String key = String.valueOf(it.next());  
           Object value = jsonObject.get(key);  
           data.put(key, value);  
       }  
       return data;  
   }
   
   /** 
    * 获取key 对于的value值
    * <br/>创建人 wdq
    * <br/>创建时间 2015年9月6日 上午10:22:00
    * @param jsonStr
    * @param key
    * @return
    */
   public static String getJsonValueByKey(String jsonStr,String key) {
	   JSONObject jobj = JSONObject.fromObject(jsonStr == null ? "{}": jsonStr); 
	   return jobj.getString(key);
   }
   
}
