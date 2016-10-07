/**
 * 2015年9月16日
 */
package com.unique.user.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 * <br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年9月16日 上午10:27:36<br/>
 * @author Administrator
 * @date 2015年9月16日
 * @description 
 */
@WebService(name = "patientRegistryCheckQueryService", targetNamespace = "http://checkPatientRegistry.v1.collect.archives.health.yuyou.com")
@SOAPBinding(style = Style.RPC)
public interface WebCreateHealthDocService {

	/**
	 * 新建健康档案<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年9月16日 上午10:29:13<br/>
	 * @param xml
	 * @return
	 */
	public String checkPatientRegistry(@WebParam(name="xml") String xml);
}
