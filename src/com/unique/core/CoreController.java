package com.unique.core;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.unique.core.annotation.CollectionClass;
import com.unique.core.annotation.ParamRegex;
import com.unique.core.util.DesUtil;
import com.unique.core.util.FileUtil;
import com.unique.core.util.JsonUtil;
import com.unique.core.util.StringUtil;
import com.unique.mb.service.UserElectrocardioTestService;
import com.unique.org.po.StaffType;
import com.unique.reg.service.RegService;
import com.unique.system.service.SystemService;
import com.unique.user.service.BaseService;

/**
 * 核心控制器
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年8月13日上午9:33:39
 * 修改日期:2015年8月13日上午9:33:39
 */
@Controller("/base.do")
public class CoreController {
	private Logger log = Logger.getLogger("comservice");
	///智能设备日志
	private Logger deviceLog = Logger.getLogger("device");
	private static String PIC_SERVER_OUT = FileUtil.readProperties("comservice.properties", "picServerOut");
	///图片服务器
	private static String PIC_SERVER = FileUtil.readProperties("comservice.properties", "picServer");
	
	@Resource
	private BaseService baseService;
	
	@Resource
	private RegService regService;
	
	@Resource
	private SystemService systemService;
	
	@Resource
	private UserElectrocardioTestService userElectrocardioTestService;
	
	/**
	 * 智能设备厂商提交数据
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月6日上午10:28:13
	 * 修改日期:2015年11月6日上午10:28:13
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="method=postData")
	public String postData(HttpServletRequest request,HttpServletResponse response){
		String ctype = request.getParameter("ctype");
		deviceLog.info("========= ctype:" + ctype + "========");
		if("37".equals(ctype)){
			try {
				String result = receivePost37(request);
				deviceLog.info(result);
			} catch (IOException e) {
				Logger deviceLog = Logger.getLogger("exception");
				deviceLog.info(ExceptionUtils.getStackTrace(e));
			}
		}
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print("SUCCESS");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
		return null;
	}
	
	/**
	 * 绘制心电图根据测试明细ID
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年12月2日下午1:47:59
	 * 修改日期:2015年12月2日下午1:47:59
	 * @param request
	 * @param response
	 * @param testDetId
	 * @return
	 */
	@RequestMapping(params="method=drawEcg")
	public String drawEcg(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(required=true)String testDetId){
		BufferedImage img = userElectrocardioTestService.getElectrocardioImg(testDetId);
		
		response.setContentType("image/jpeg;charset=utf-8");
		
		if(img!=null){
			OutputStream out=null;
			try {
				out = response.getOutputStream();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	    	try {
	    		out = response.getOutputStream();
				ImageIO.write(img, "jpg",out);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 37度厂商提交数据
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月6日上午10:30:08
	 * 修改日期:2015年11月6日上午10:30:08
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static String receivePost37(HttpServletRequest request) throws IOException, UnsupportedEncodingException {
        // 读取请求内容
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
            sb.append(line);
        }

        // 将资料解码
        String reqBody = sb.toString();
        return reqBody;
    }
	
	
	/**
	 * 通用的HTTP调用接口方法
	 * @param request 
	 * @param response
	 * @param module 被调用BEAN名称
	 * @param method 被调用方法名
	 * @return
	 */
	@RequestMapping(params="do=httpInterface")
	public String httpInterface(HttpServletRequest request,HttpServletResponse response,
				@RequestParam(required=true)String module,
				@RequestParam(required=true)String method){
		
		log.info("类：" + module + "方法：" + method);
		Map<String,Object> HttpMethodBean = (Map<String, Object>) request.getSession().getServletContext().getAttribute("HttpMethodBean");
		//从流中获取JSON参数
		StringBuffer sb = new StringBuffer();
		StringBuffer exception = new StringBuffer();
		try {
			InputStream is = request.getInputStream();
			int temp = -1;
			while((temp = is.read())!=-1){
				sb.append((char)temp);
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		String paramJson = sb.toString().trim();
		HashMap<String, Object> result = new HashMap<String, Object>();
		Object bean = BeanListener.getApplicationContext().getBean(module);
		//反射方法
		try {
			Map<String, Object> MethodMaps = (Map<String, Object>)((Map<String, Object>)HttpMethodBean.get(module)).get(method);
			if(MethodMaps==null)MethodMaps = new HashMap<String, Object>();
			if(!MethodMaps.containsKey("paramNames")){
				//没有找到该方法
				exception.append("没有找到该方法;");
			}else{
				String[] paramNames = (String[]) MethodMaps.get("paramNames");
		        Method methodInstance = (Method)MethodMaps.get("instance");
		        Method oldMethod = (Method)MethodMaps.get("oldMethod");
		        //获取方法参数类型
		        Class[] parameterTypes = methodInstance.getParameterTypes();
		        //权限
		        int[]  authos = (int[]) MethodMaps.get("autho");
		        
		        Object[] paramObjects = null;
		        JSONObject paramJsonObj = null;
		        if(paramNames.length >0){
		        	paramObjects = new Object[paramNames.length];
		        }
				if(paramJson==null || "".equals(paramJson)){
				}else{
					paramJsonObj = JSONObject.fromObject(paramJson,JsonUtil.getConfig());
					String EncString = (String) paramJsonObj.get("param");
					paramJsonObj = JSONObject.fromObject(DesUtil.decrypt(EncString),JsonUtil.getConfig());
					log.info(paramJsonObj);
					//公共参数提取
					String callType = (String) paramJsonObj.get("calltype");
					String userId = paramJsonObj.has("operatorUserId")?paramJsonObj.getString("operatorUserId"):null;
					String version = paramJsonObj.has("version")?paramJsonObj.getString("version"):null;
					String deviceToken = paramJsonObj.has("deviceToken")?paramJsonObj.getString("deviceToken"):null;
					if(callType==null){
						exception.append("callType不能为空;");
	
					}else{
						baseService.interfaceLog(module, method,Integer.parseInt(callType),userId, version, deviceToken);
						//检查是否具有权限
						boolean hasAutho = false;
						for(int autho : authos){
							if(autho==0 || autho==Integer.parseInt(callType)){
								hasAutho = true;
								break;
							}
						}
						if(hasAutho){
							paramObjects = new Object[paramNames.length];
				            for (int i = 0; i < paramNames.length; i++) {
				            	Object paramObj = paramJsonObj.get(paramNames[i]);
				            	String objClass = parameterTypes[i].getName();
				            	if(Collection.class.isAssignableFrom(parameterTypes[i])){
					            	//为反射泛型做准备
				            		Annotation[] annos = oldMethod.getParameterAnnotations()[i];
				            		CollectionClass ann =null;
				            		for(Annotation a : annos){
				            			if(a.annotationType().getName().equals("com.unique.core.annotation.CollectionClass")){
				            				ann = (CollectionClass)a;
				            				break;
				            			}
				            		}
				            		Class fx = String.class;
				            		if(ann!=null){
				            			fx = ann.value();
				            		}
				            		
				            		//参数是一个集合
				            		if(paramObj==null){
				            			paramObjects[i] = null; 
				            		}else{
				            			paramObjects[i] = JSONArray.toCollection((JSONArray) paramObj,fx);
				            		}
				            	}else if(paramObj instanceof JSONArray){
					            	//为反射泛型做准备
				            		Annotation[] annos = oldMethod.getParameterAnnotations()[i];
				            		CollectionClass ann =null;
				            		for(Annotation a : annos){
				            			if(a.annotationType().getName().equals("com.unique.core.annotation.CollectionClass")){
				            				ann = (CollectionClass)a;
				            				break;
				            			}
				            		}
				            		Class fx = String.class;
				            		if(ann!=null){
				            			fx = ann.value();
				            		}
				            		
				            		//参数是一个数组
				            		paramObjects[i] = JSONArray.toArray((JSONArray)paramObj,fx);
				            	}else if(paramObj instanceof JSONObject){
				            		//参数是一个复杂对象
				            		paramObjects[i] = JSONObject.toBean((JSONObject)paramObj,Class.forName(objClass));
				            	}else{
				            		if(parameterTypes[i].getName().equals("java.lang.Long") && paramObj!=null){
				            			paramObjects[i] = Long.parseLong(paramObj.toString());
				            		}else if(parameterTypes[i].getName().equals("java.lang.Short") && paramObj!=null){
				            			paramObjects[i] = Short.parseShort(paramObj.toString());
				            		}else if(parameterTypes[i].getName().equals("java.lang.Byte") && paramObj!=null){
				            			paramObjects[i] =Byte.parseByte(paramObj.toString());
				            		}else if(parameterTypes[i].getName().equals("java.lang.Double") && paramObj!=null){
				            			paramObjects[i] = Double.parseDouble(paramObj.toString());
				            		}else if(parameterTypes[i].getName().equals("java.lang.Float") && paramObj!=null){
				            			paramObjects[i] = Float.parseFloat(paramObj.toString());
				            		}else{
				            			if("null".equals(paramObj+"")){
				            				paramObjects[i] = null;
				            			}else{
				            				paramObjects[i] = paramObj;
				            			}
				            		}
				            	}
				            }
						}else{
							exception.append("你无权访问该接口。");
							result.put("retCode", -1);
						}
					}
				}
				if(exception.length() > 0){
					result.put("retMessage", "调用失败");
					result.put("retCode", -1);
				}else{
					//判断参数是否可为空
//					Annotation[][] annos = methodInstance.getParameterAnnotations();
					Annotation[][] annos = oldMethod.getParameterAnnotations();
					int i=0;
					for(Annotation[] paramAnnos : annos){
						int length = paramAnnos.length;
		                if(length == 0){
		                	//如果为0，则表示没有为该参数添加注释
		                }else{
		                	for(Annotation noNullParam : paramAnnos){
		                		if(noNullParam.annotationType().getName().equals("com.unique.core.annotation.ParamNoNull") && paramObjects[i]==null){
		                			exception.append(paramNames[i].replaceAll("\"", "'") + "不能为空;");
		                		}
		                		
		                		if(noNullParam.annotationType().getName().equals("com.unique.core.annotation.ParamRegex") && paramJsonObj!=null){
		                			//验证正则表达式
		                			String regex = ((ParamRegex)noNullParam).regex();
		                			Object paramValue = paramJsonObj.get(paramNames[i]);
		                			if(!StringUtil.isEmpty(regex) && !StringUtil.isEmpty((String)paramValue)){
		                				//是否符合正则表达式
		                				boolean isReg = Pattern.matches(regex, paramValue.toString());
		                				if(!isReg){
		                					exception.append(paramNames[i].replaceAll("\"", "'") + "参数值不合法;");
		                				}
		                			}
		                			
		                		}
		                	}
		                }
		                i++;
					}
					if(exception.length() > 0){
						result.put("retMessage", "调用失败");
						result.put("retCode", -1);
					}else{
						Type returnType = methodInstance.getReturnType();
						Object methodResult = methodInstance.invoke(bean, paramObjects);
						result.put("retCode", 0);
						if(returnType.toString().equals("void")){
							result.put("retCode", 1);
						}else{
							if(methodResult!=null){
								if(methodResult instanceof Map){
									//取实际方法的返回结果枚举
									Integer retCode = (Integer) ((Map<String,Object>)methodResult).get("retCode");
//									result.putAll((Map<String,Object>)methodResult);
									if(retCode!=null)result.put("retCode", retCode);
								}

								if(methodResult.getClass().isPrimitive()){
									result.put("result", DesUtil.encrypt(""+ methodResult));
								}else{
									//结果转为JSON
									String resultStr = "";
									if (null != methodResult) {
										if (methodResult instanceof Collection || methodResult instanceof Object[]) {  
											resultStr = JSONArray.fromObject(methodResult,JsonUtil.getConfig()).toString();  
								        } else if(methodResult instanceof String){
								        	resultStr =(String) methodResult;
										} else {
								        	resultStr = JSONObject.fromObject(methodResult,JsonUtil.getConfig()).toString();
										}
									}
									if(!"".equals(resultStr)){
										result.put("result", DesUtil.encrypt(resultStr));
									}
								}
						
							}
						}
						
						result.put("retMessage", "调用成功");
					}
				}
			}
		}catch (Exception e) {
			
	        // 在后台中输出错误异常异常信息，通过log4j输出。  
	        Logger elog = Logger.getLogger("exception");  
	        elog.info("**************************************************************");
	        elog.info("类：" + module + "方法：" + method);
	        elog.info("Exception class: " + e.getClass().getName());  
	        elog.info("ex.getMessage():" + e.getMessage());  
	        String exceptionStr = ExceptionUtils.getFullStackTrace(e);
	        elog.info(exceptionStr);
	        elog.info("**************************************************************");  
			
			result.put("retCode", 2);
			result.put("retMessage", e.getMessage());
			
			e.printStackTrace();
		}
		if(exception.length() > 0){
			result.put("exception", exception.toString());
		}
		JsonUtil.sendJsonObject(response, result);
		return null;
	}
	
	
	
	
	/**
	 * 提取JSON对象到对象数组,方便反射
	 * @param obj
	 * @return
	 */
	private Object[] getParamtersByJson(JSONObject obj,Method method){
		if(obj==null){
			return null;
		}else{
			for(Class type : method.getParameterTypes()){
				
			}
			Object[] objs = new Object[obj.keySet().size()];
			int i=0;
			for(String key : (Set<String>)obj.keySet()){
				objs[i++] = obj.get(key);
			}
			return objs;
		}
	}
	
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="method=uploadPic")
	public String uploadPic(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(required=true) String type){
		String savePath = new File(PIC_SERVER, type).getPath();
		Map<String,Object> result  = doUploadInvoke(savePath, type,request);
        JsonUtil.sendJsonObject(response, result);
        return null;
	}
	
	/**
	 * 上传文件
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月13日下午3:38:47
	 * 修改日期:2015年8月13日下午3:38:47
	 * @param request
	 * @param response
	 * @param type
	 * @return
	 */
	@RequestMapping(params="method=uploadFile")
	public String uploadFile(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(required=true) String type){
		String savePath = new File(PIC_SERVER, type).getPath();
		Map<String,Object> result  = doUploadInvoke(savePath, type,request);
        JsonUtil.sendJsonObject(response, result);
        return null;
	}
	
	
	/**
	 * 执行上传文件功能
	 * @param savePath 本地保存路径
	 * @param request http request
	 * @return
	 */
	private Map<String,Object> doUploadInvoke(String savePath,String dirName,HttpServletRequest request){
		HashMap<String, Object> result= new HashMap<String, Object>();
		//检测目录是否存在
		File ckfile = new File(savePath);
        if (!ckfile.exists()) {
        	ckfile.mkdirs();
        }
        
        Enumeration e = request.getHeaderNames();
        DiskFileItemFactory fac = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fac);
        upload.setHeaderEncoding("utf-8");
        List fileList = null;
        Map<String,List<FileItem>> param = null;
        //检测上传文件和传递参数
        try {
            fileList = upload.parseRequest(request);
            param = upload.parseParameterMap(request);
        } catch (FileUploadException ex) {
        	//上传异常
        	ex.printStackTrace();
        	result.put("type", "FileUploadException");
            return result;
        }
        result.putAll(param);
        
        Iterator<FileItem> it = fileList.iterator();
        String name = "";
        String extName = "";
        
        //遍历所有文件
        while (it.hasNext()) {
            FileItem item = it.next();
            if (!item.isFormField()) {
                name = item.getName();
                long size = item.getSize();
                String type = item.getContentType();
                System.out.println("上传图片格式：" + type);
                
                if (name == null || name.trim().equals("")) {
                    continue;
                }
                //扩展名格式：  
                if (!StringUtil.isEmpty(type)) {
                	if(type.toLowerCase().endsWith("jpeg")){
                		extName =".jpg";
                	}else if(type.toLowerCase().endsWith("png")){
                		extName =".png";
                	}else if(type.toLowerCase().endsWith("gif")){
                		extName =".gif";
                	}else if(type.toLowerCase().endsWith("bmp")){
                		extName =".bmp";
                	}else{
                		extName =".dat";
                	}
                }
                File file = null;
                do {
                    //生成文件名：
                    name = "COM_" + UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                    file = new File(savePath , name + extName);
                } while (file.exists());
                
                File saveFile = new File(savePath , name + extName);
                try {
                    item.write(saveFile);
                    result.put("name", new File(dirName,name + extName).getPath().replaceAll("\\\\", "/"));
                    result.put("serverPath",PIC_SERVER_OUT);
                    result.put("type", "success");
                } catch (Exception e1) {
                    e1.printStackTrace();
                    result.put("type", "save err");
                }
            }
        }
		return result;
	}
	
	public static void main(String[] args) {
		String resultStr = JSONObject.fromObject("ZJK_35F60429E5AE452B859FA5A4A831041C",JsonUtil.getConfig()).toString();
		System.out.println(resultStr);
	}
}
