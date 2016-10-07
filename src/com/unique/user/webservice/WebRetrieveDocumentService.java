package com.unique.user.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 共享文档内容查询
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年9月14日下午2:39:40
 * 修改日期:2015年9月14日下午2:39:40
 */
@WebService(name = "retrieveDocumentSetRequest", targetNamespace = "http://retrieveDocumentSetRequest.v1.collect.archives.health.yuyou.com")
public interface WebRetrieveDocumentService {

	/**
	 * 共享文档内容查询
	 * @param xml XML查询文档
	 * @return
	 */
	public String retrieveDocumentSetRequest(@WebParam(name = "xml") String xml);
}
