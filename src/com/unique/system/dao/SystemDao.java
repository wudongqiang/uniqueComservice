package com.unique.system.dao;

import java.util.List;
import java.util.Map;

import com.unique.core.util.QueryUtils;
import com.unique.mb.po.EarlyWarns;
import com.unique.system.po.AdContent;
import com.unique.system.po.ImMsgInfo;
import com.unique.system.po.UserClientType;
import com.unique.system.po.VersionConfig;

public interface SystemDao {
	
	public VersionConfig getVersion(String typeId);
	
	public List<AdContent> getAdByPosition(String code);
	
	public int addUserClient(UserClientType userClient);
	
	public UserClientType getUserClientByToken(String deviceToken);
	
	public int updateUserClient(UserClientType userClient);
	
	public List<UserClientType> getClientTypeByUcts(String[] ucts);
	
	public int updateClientType(String pushCode,String clientTypeId,String deviceToken);

	public UserClientType getPushCode(String deviceToken,String clientId);
	
	/**
	 * 添加IM消息记录
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月13日下午2:42:20
	 * 修改日期:2015年8月13日下午2:42:20
	 * @param msginfo
	 * @return
	 */
	public int addImMsg(ImMsgInfo msginfo);
	
	/**
	 * 批量添加IM消息
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月13日下午3:26:19
	 * 修改日期:2015年8月13日下午3:26:19
	 * @param msginfos
	 */
	public void addImMsgs(List<ImMsgInfo> msginfos);
	
	/**
	 * 根据相关Id获取 hlink_to<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月13日 下午2:16:38<br/>
	 * @param filedName 属性字段
	 * @param filedValue 属性值
	 * @return
	 */
	public String getCmsImageByWhereId(QueryUtils.CmsImageLib filedName,String filedValue,String useCode);
	
	/**
	 * 获取警告消息列表
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月15日上午11:14:55
	 * 修改日期:2015年10月15日上午11:14:55
	 * @param userId
	 * @param dateStr
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public List<ImMsgInfo> getWarnMsg(String userId,String dateStr, Long startRow, Long rows);
	
	/**
	 * 通过图片类型code获取图片类型用途id type_use_id<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月24日 上午11:32:55<br/>
	 * @param typeCode
	 * @return
	 */
	public String getCmsImgTypeIdByTypeCode(String typeCode);
	
	/**
	 * 添加约诊图片<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月24日 上午11:37:34<br/>
	 * @param map
	 * @return
	 */
	public int addCmsImgLibForAppoint(Map<String,Object> map);
	
	/**
	 * 查询警告消息总数
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月15日上午11:16:44
	 * 修改日期:2015年10月15日上午11:16:44
	 * @param userId
	 * @return
	 */
	public long getWarnMsgCount(String userId,String dateStr);
	
	/**
	 * 获取预警日期<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月23日 下午12:03:59<br/>
	 * @param userId
	 * @param dateStr
	 * @return
	 */
	public List<String> getWarnMsgDates(String userId,String dateStr);

	/**
	 * 预警提醒 结果查询<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月25日 下午4:34:54<br/>
	 * @param staffId
	 * @param dateStr
	 * @param startRow
	 * @param rows
	 */
	public List<EarlyWarns> getEarlyWarns(String staffId,String dateStr, Long startRow, Long rows);

	/**
	 * 预警提醒 结果查询总数<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月25日 下午4:38:55<br/>
	 * @param userId
	 * @param dateStr
	 * @return
	 */
	public long getEarlyWarnsCount(String staffId,String dateStr);
}
