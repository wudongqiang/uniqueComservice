package com.unique.health.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.util.JsonUtil;
import com.unique.core.util.QueryUtils;
import com.unique.core.util.StringUtil;
import com.unique.core.util.XmlUtil;
import com.unique.user.po.AmsUser;
import com.unique.user.service.UserService;
import com.unique.user.webservice.WebCreateHealthDocService;
import com.unique.user.webservice.WebHealthService;
import com.unique.user.webservice.WebPatDocBrowsService;
import com.unique.user.webservice.WebRetrieveDocumentService;

/**
 * 健康档案管理 
 * <br/>创建人 wdq
 * <br/>创建时间 2015年9月2日 上午10:48:19
 * @author Administrator
 * @date 2015年9月2日
 * @description
 */
@Service("healthService")
public class HealthServiceImpl implements HealthService {

	private final Logger log = Logger.getLogger("health");
	
	@Resource
	private UserService userService;
	@Resource
	private WebHealthService webHealthService;
	@Resource
	private WebPatDocBrowsService webPatDocBrowsService;
	@Resource
	private WebRetrieveDocumentService webRetrieveDocumentService;
	@Resource
	private WebCreateHealthDocService webCreateHealthDocService;
	
	/** 自定义异常返回数据*/
	private final static String EXCEPTION_RET_MESSAGE = "{'status':{'value':'-1','retMsg':'查询失败，请检查！'},'rows':[]}";
	private final static String DOCUMENT_RESPONSE = "DocumentResponse";
	private final static String DOCUMENT = "Document";
	 
	@Override
	@HttpMethod
	public String getUserHealthRecordBaseInfo(@NotNull String userId) {
		
		try {
			Map<String, Object> params = new HashMap<String,Object>();
			//1.获取用户基本信息 
			AmsUser user = userService.getUserById(userId);
			
			if(user==null||StringUtil.isEmpty(user.getHealtyRecordId())){
				// 用户身份证不存在
				return "{'PatientPHRQueryResponse':{{'status':{'value':'-1','retMsg':'用户或健康档案编号不存在'},'rows':[]}}}";
			}
			//2.封装查询信息
//			params.put("Paperworklist.paperworkno", user.getIdCard());
			params.put("Patient.mpi", user.getHealtyRecordId());
			String queryXml = QueryUtils.getQueryXml(2,"PatientPHRQuery",params);
			//3.发送数据并得到返回数据
			String resultXml = webHealthService.patientPHRQuery(queryXml);
			 
//			String jsonStr = XMlUtils.xml2JSON(resultXml);
			String jsonStr = XmlUtil.xmlToJson(resultXml);
			log.info("获取用户的健康档案基本信息:resultJson="+jsonStr);
			return jsonStr;
		} catch (Exception e) {
			log.error("获取用户的健康档案基本信息", e);
			return EXCEPTION_RET_MESSAGE;
		}
	}
 
 
	@HttpMethod
	@Override
	public String getUserHealthHistorys(String userId,String type) {
		try {
			Map<String, Object> params = new HashMap<String,Object>();
			//1.获取用户基本信息 
			AmsUser user = userService.getUserById(userId);
			
			if(user==null||StringUtil.isEmpty(user.getHealtyRecordId())){
				return "{'status':{'value':'-1','retMsg':'用户或健康档案编号（mpi）不存在'},'rows':[]}";
			}
			//2.封装查询信息
			params.put("Patient.mpi", user.getHealtyRecordId());
			String queryXml = QueryUtils.getQueryXml(null,"PatDocBrowsIndxQuery", params);
			//3发送请求
			//3.发送数据并得到返回数据
			String resultXml = webPatDocBrowsService.patDocBrowsIndxQuery(queryXml);
			//4解析
//			String jsonStr = XMlUtils.xml2JSON(resultXml);
			String jsonStr = XmlUtil.xmlToJson(resultXml);
			
		 	//解析分类组装
			List<Object> rows = new ArrayList<Object>(); //重写结果rows
			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			 
			JSONObject jsonObjectRes = jsonObject.getJSONObject("PatDocBrowsIndxQueryResponse");
			
			JSONArray jsonArray = jsonObjectRes.getJSONArray("rows");
			
			JSONObject ob = null;
			String visitType = null; //默认门诊 查询
			if(type!=null){
				if("1".equals(type)){
					visitType = "门诊";
				}else if("2".equals(type)){
					visitType = "住院";
				}else if("3".equals(type)){
					visitType = "体检";
				}
			}
			for(int i=0;jsonArray!=null&&i<jsonArray.size();i++){
				ob = (JSONObject)jsonArray.get(i);
				
				//visitType	就诊类型-门诊、住院、体检
				if(visitType==null){
					rows.add(jsonArray.get(i));
				}else if(visitType.equals(ob.getJSONObject("row").get("visitType"))){
					//门诊
					rows.add(jsonArray.get(i));
				} 
			}
		 
			jsonObjectRes.put("rows", rows);
			jsonObject.put("PatDocBrowsIndxQueryResponse", jsonObjectRes);
			
			return jsonObject.toString();
		} catch (Exception e) {
			log.error("获取用户的健康史集", e);
			return EXCEPTION_RET_MESSAGE;
		}
	}

	@HttpMethod
	@Override
	public String getUserHealthHistoryDetails(@NotNull String docuRegID, String storageID) {
		try {
			Map<String, Object> params = new HashMap<String,Object>();
			//1.封装查询信息
			params.put("Docureg.docuRegID", docuRegID);
			params.put("Docustorage.storageID", storageID);
			String queryXml = QueryUtils.getQueryXml(null,"RetrieveDocumentSetRequest", params);
			
			//2发送请求
			String resultXml = webRetrieveDocumentService.retrieveDocumentSetRequest(queryXml);
			//3解析返回值
//			String jsonStr = XMlUtils.xml2JSON(resultXml);
			String jsonStr = XmlUtil.xmlToJson(resultXml);
			log.info("获取用户的健康史详情:resultJson=" + jsonStr);
			if(jsonStr.indexOf(DOCUMENT_RESPONSE)>=0){
				String documents = JsonUtil.getJsonValueByKey(JsonUtil.getJsonValueByKey(jsonStr, "RetrieveDocumentSetResponseXML"), DOCUMENT_RESPONSE);
				if(documents.indexOf(DOCUMENT)>=0){
					documents = JsonUtil.getJsonValueByKey(documents,DOCUMENT);
	//				jsonStr = XMlUtils.xml2JSON(documents);
					jsonStr = XmlUtil.xmlToJson(documents);
				}else{
					jsonStr = EXCEPTION_RET_MESSAGE;
				}
			}else{
				jsonStr = EXCEPTION_RET_MESSAGE;
			}
			log.info("获取用户的健康史详情解析document:JsonStr="+ jsonStr);
//			//4解密Document 节点内容
//			try {
//				documents = DesUtil.decrypt(documents);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			//5.解析数据返回
			return jsonStr;
		} catch (Exception e) {
			log.error("获取用户的健康史详情", e);
			return EXCEPTION_RET_MESSAGE;
		}
		
	}
	 

	@HttpMethod
	@Override
	public String createHealthDocment(@NotNull String userName,@NotNull String idCard,@NotNull String phoneNumber){

		try {
			Map<String, Object> params = new HashMap<String,Object>();
			//1.封装查询信息
			params.put("Patient.name", userName);
			params.put("Patient.telephoneno", phoneNumber);
			params.put("Paperworklist.paperworkno", idCard);
			String queryXml = QueryUtils.getQueryXml(2,"PatientRegistryCheckQuery", params);
			String result = webCreateHealthDocService.checkPatientRegistry(queryXml);
			String jsonStr = XmlUtil.xmlToJson(result);
			
			jsonStr = JsonUtil.getJsonValueByKey(jsonStr, "PatientRegistryCheckQueryResponse");
			System.out.println(jsonStr);
			int rt = Integer.parseInt(JsonUtil.getJsonValueByKey(jsonStr, "value"));
			String mpi = null;
			//status 0：表示新创建 1：表示查询结果 2：表示创建失败
			if(rt>=0){
				rt = (rt==2) ? 0 : 1;  
			}else{
				rt = 2;//失败
			}
			if(jsonStr.contains("rows")){
				mpi = JsonUtil.getJsonValueByKey(JsonUtil.getJsonValueByKey(jsonStr, "rows"),"注册MPI");
			}else {
				mpi = "0";
			}
			return "{'status':'"+rt+"','mpi':'"+mpi+"'}";
		} catch (Exception e) {
			log.error("新建档案", e);
		}
		return "0";
	}
}
