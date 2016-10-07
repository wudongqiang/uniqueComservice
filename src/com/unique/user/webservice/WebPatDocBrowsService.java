package com.unique.user.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 *  获取健康史（文档） webService 调用
 * <br/>创建人 wdq
 * <br/>创建时间 2015年9月8日 上午8:58:47
 * @author Administrator
 * @date 2015年9月8日
 * @description
 */
@WebService(name = "patDocRequest", targetNamespace = "http://patDocBrowsIndxQuery.v1.collect.archives.health.yuyou.com")
@SOAPBinding(style = Style.RPC)
public interface WebPatDocBrowsService {
	
	/**
	 * 用户健康史记录
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月9日 上午10:52:27
	 * @param xml
	 * @return
	 */
	public String patDocBrowsIndxQuery(@WebParam(name = "xml") String xml);
	
}
