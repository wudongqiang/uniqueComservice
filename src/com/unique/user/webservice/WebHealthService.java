package com.unique.user.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 *  获取用户健康档案 webService 调用
 * <br/>创建人 wdq
 * <br/>创建时间 2015年9月8日 上午8:58:47
 * @author Administrator
 * @date 2015年9月8日
 * @description
 */
@WebService(name = "patientPHRQuery", targetNamespace = "http://patientPHRQuery.v1.collect.archives.health.yuyou.com")
@SOAPBinding(style = Style.RPC)
public interface WebHealthService {

	/**
	 * 用户健康记录基本信息
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月8日 上午8:58:42
	 * @param xml
	 * @return
	 */
	public String patientPHRQuery(@WebParam(name = "xml") String xml);
	
}
