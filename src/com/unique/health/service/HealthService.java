package com.unique.health.service;


public interface HealthService {

	/**
	 * 获取用户的健康档案基本信息
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月2日 上午10:50:03\
	 * @param userId
	 * @return
	 */
	public String getUserHealthRecordBaseInfo(String userId);
	
	
	/**
	 * 获取用户的健康史集
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月2日 下午4:09:31
	 * @param userId
	 * @param type 	就诊类型-1门诊、2住院、3体检
	 * @return
	 */
	public String getUserHealthHistorys(String userId,String type);
	
	/**
	 * 获取用户的健康史详情
	 * <docuRegID>文档注册号</docuRegID>
	 * <storageID>文档存储号</storageID> 获取为一个文档
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月2日 下午4:11:11
	 * @param docuRegId  文档注册号
	 * @param storageId  文档存储号
	 * @return
	 */
	public String getUserHealthHistoryDetails(String docuRegId, String storageId);


	/**
	 * 新建档案<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年9月11日 下午5:26:55<br/>
	 * @param userName
	 * @param idCard
	 * @param phoneNumber
	 * @return
	 */
	String createHealthDocment(String userName, String idCard,
			String phoneNumber);
}
