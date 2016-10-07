/**
 *
 */
package com.unique.reg.webservice;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.springframework.stereotype.Service;

import com.unique.core.annotation.ParamNoNull;
import com.unique.reg.service.RegService;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年11月6日上午11:03:33
 * 修改日期:2015年11月6日上午11:03:33
 */
@WebService
@SOAPBinding(style = Style.RPC)
@Service("webRegService")
public class WebRegServiceImpl implements WebRegService{
	@Resource
	private RegService regService;
	
	/**
	 * 退号流程
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年11月6日上午11:07:14
	 * 修改日期:2015年11月6日上午11:07:14
	 * @param regId
	 * @param opUserId
	 * @param backReason
	 * @param userId
	 * @return
	 */
	@WebMethod
	public HashMap<String,Object> unReg(
			 String regId,
			 String opUserId,
			 String backReason,
			 String userId){
		return regService.unReg(regId, opUserId, backReason, userId);
	}
}
