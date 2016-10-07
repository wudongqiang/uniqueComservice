/**
 *
 */
package com.unique.reg.webservice;

import java.util.HashMap;

/**
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年11月6日上午11:03:33
 * 修改日期:2015年11月6日上午11:03:33
 */
public interface WebRegService {
	public HashMap<String,Object> unReg(
			 String regId,
			 String opUserId,
			 String backReason,
			 String userId);
}
