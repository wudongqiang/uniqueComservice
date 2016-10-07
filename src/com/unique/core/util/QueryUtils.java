package com.unique.core.util;

import java.util.Map;
import java.util.Map.Entry;


/**
 * 查询条件封装工具类
 * <br/>创建人 wdq
 * <br/>创建时间 2015年9月2日 上午11:21:13
 * @author Administrator
 * @date 2015年9月2日
 * @description
 */
public final class QueryUtils {

	
	/**
	 * 获取查询xml （传入信息格式）
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月2日 上午11:20:14
	 * @param maxRecords 最大显示记录条数  如果为null 表示不限
	 * @param queryId  DetailsQuery查询Id
	 * @param params  以key作为semanticsText的属性值，value作为parameter条件值
	 * @return
	 */
	public final static String getQueryXml(Integer maxRecords,String queryId,Map<String,Object> params){
		StringBuffer sb = new StringBuffer();
		sb.append("<DetailsQuery id=\""+queryId+"\">");
		if(!(maxRecords==null||maxRecords<=0)){
			sb.append("<initialQuantity>"+maxRecords+"</initialQuantity>");
		}
		sb.append("<QueryByParameterPayload>");
		for(Entry<String, Object> entry: params.entrySet()){
			sb.append("<Parameter semanticsText=\""+entry.getKey()+"\">");
			sb.append(entry.getValue());
			sb.append("</Parameter>");
		}
		sb.append("</QueryByParameterPayload>");
		sb.append("</DetailsQuery>");
		
		return sb.toString();
	}
	
	public enum CmsImageLib{
		IMG_GUID,//图片GUID
		STAFF_ID,//员工ID
		USER_ID,//用户ID
		DEP_ID,//部门ID
		ORG_ID,//机构ID
		PRODUCT_ID//产品ID
	}
}


