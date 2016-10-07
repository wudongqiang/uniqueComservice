/**
 * 2015年9月14日
 */
package com.unique.his.service;



/**
 * 排队查询接口<br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年9月14日 上午10:02:51<br/>
 * @author Administrator
 * @date 2015年9月14日
 * @description 
 */
public interface QueueService {
	
	
	/**病人排队查询接口*/
	public final static String QUEUE_BY_USER_LIST = "module=queueService&method=getQueueByUserList";

	/**医院科室排排队查询接口*/
	public final static String QUEUE_BY_ORG_LIST = "module=queueService&method=getQueueByOrgList";
	
	
	/**
	 * 病人排队查询接口 - （分页可能有多个挂号的排队）<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年9月14日 上午10:08:22<br/>
	 * @param cardNo 查询号码-患者ID
	 * @param userName 患者姓名 
	 * @param startRow 开始行
	 * @param rows 没有显示行数
	 */
	public String getQueueByUserList( String orgId,String cardNo,String userName,Long startRow, Long rows);
	
	/**
	 * 医院科室排队查询接口<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年9月14日 上午10:44:21<br/>
	 * @param orgCode 机构代码
	 * @param startRow	开始行
	 * @param rows 行数
	 * @return
	 */
	public String getQueueByOrgList(String orgId,Long startRow, Long rows);
}
