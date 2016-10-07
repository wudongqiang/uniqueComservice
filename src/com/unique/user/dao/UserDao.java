package com.unique.user.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.unique.reg.po.Staff;
import com.unique.user.po.AmsUser;
import com.unique.user.po.AmsUserLog;
import com.unique.user.po.CardType;
import com.unique.user.po.McCard;
import com.unique.user.po.McFb;
import com.unique.user.po.McFbType;
import com.unique.user.po.McMsg;
import com.unique.user.po.MsgType;
import com.unique.user.po.UserCallLog;
import com.unique.user.po.UserDevice;
import com.unique.user.po.UserFriend;
import com.unique.user.po.UserSchedule;

public interface UserDao {
	public int addUser(AmsUser user);
	public int addUserFriend(UserFriend userf);
	
	/**
	 * <br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月4日 下午4:08:48<br/>
	 * @param logonAcct
	 * @param pwd
	 * @param typeCode
	 * @param type 登陆类型，0：单一登陆，1：混合登陆
	 * @return
	 */
	public AmsUser getUserByPassword(String logonAcct,String pwd,String typeCode,String type);
	
	public void updateUserInfo(AmsUser user);
	
	public AmsUser getUserById(String uid);
	
	public UserFriend getFriendUserBySelf(String uid);
	public UserFriend getFriendUserByFriendId(String uid);
	
	public void updateUserFriend(UserFriend uf);
	
	public void setFriendNoDefault(String userId);
	public void setFriendDefault(String userId);
	
	public void delFriend(String friendId);
	
	public List<UserFriend> getFriendUsersByUid(String friendId);
	
	public int checkUser(String mobile);
	public AmsUser checkUserByIdCard(String idCard);
	
	public int log(UserCallLog log);
	
	public void logs(List<UserCallLog> logs);
	
	/**
	 * 添加意见反馈
	 * @param mcFb
	 */
	public int addMsg(McFb mcFb);
	
	/**
	 * 通过code获取mcFbType
	 * @param code
	 * @return
	 */
	public McFbType getMcFbTypeByCode(String code);
	
	/**
	 *  通过code获取msgType
	 * @param code
	 * @return
	 */
	public MsgType getMsgTypeByCode(String code);
	
	public List<McFb> getLxMessageByPage(String userId,long startRow,long rows,String code);
	
	public long getLxMessageByPageCount(String userId,String code);
	
	/**
	 * 记录登陆日志
	 * @param userLog
	 */
	public void addLoginLog(AmsUserLog userLog);
	
	public AmsUserLog getLastLogonTime(String userId);
	
	public void updateUserLogById(AmsUserLog userLog );
	 
	/**
	 * 获取患者用户消息-分页
	 * @param userId
	 * @param msgTypeCode
	 * @param startRow
	 * @param rows
	 * @param publishUserId 2015-07-24 11:10 增加参数publishUserId 即发布用户在（医生端）获取患者消息时此值为医生ID
	 * @return
	 */
	public List<McMsg> getUserMsgByPage(String userId,String publishUserId,long startRow,long rows);
	
	/**
	 * 获取消息-分页
	 * @param userId
	 * @param msgTypeCode
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public List<McMsg> getMsgByPage(String userId,String msgTypeCode[],long startRow,long rows);
	
	public long getMsgByPageCount(String userId,String msgTypeCode[]);
	
	public McMsg getMsgDetById(String msgId);
	
	public void setMsgRead(String msgId);
	
	public long getWdMsgCount(String userId,String[] msgTypeCode);
	
	public int addTsMsg(McMsg msg);
	
	public Staff getStaffInfoByUserId(String userId);
	
	/**
	 * 获取日程信息集 分页
	 * @param userId
	 * @param startRow
	 * @param rows
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<UserSchedule> getScheduleResultMapByPage(String userId,long startRow,long rows,String startTime,String endTime);
	
	public long getScheduleResultMapByPageCount(String userId,String startTime,String endTime);

	public AmsUser getUserByMobile(String mobile);
	
	/**
	 * 通过Id和密码检查用户的登陆密码
	 * @param userId
	 * @param logonPwd
	 * @return
	 */
	public long checkUserPassword(String userId,String logonPwd);
	
	/**
	 * 根据就诊人ID获取卡片集合
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月7日下午2:33:55
	 * 修改日期:2015年9月7日下午2:33:55
	 * @param friendId
	 * @return
	 */
	public List<McCard> getCardByFriendId(String friendId);
	
	/**
	 * 添加就诊卡
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月8日上午9:31:30
	 * 修改日期:2015年9月8日上午9:31:30
	 * @param card
	 * @return
	 */
	public int addMcCard(McCard card);
	
	/**
	 * 删除卡片集合
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月8日上午9:47:32
	 * 修改日期:2015年9月8日上午9:47:32
	 * @param ids
	 */
	public void delCards(Set<BigDecimal> ids);
	
	/**
	 * 更新用户卡片
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月8日上午9:52:24
	 * 修改日期:2015年9月8日上午9:52:24
	 * @param card
	 */
	public void updateMcCardById(McCard card);
	
	/**
	 * 获取卡片类型集合
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月8日上午10:28:51
	 * 修改日期:2015年9月8日上午10:28:51
	 * @return
	 */
	public List<CardType> getCardTypes();
	
	/**
	 * 
	 * 通过微信Id获取用户<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月16日 上午11:23:13<br/>
	 * @param weChatId
	 * @return
	 */
	public AmsUser getUserByWeChatId(String weChatId,String calltype);
	/**
	 * <br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月17日 上午10:26:31<br/>
	 * @param mcFb
	 */
	public int addMcFb(McFb mcFb);
	
	/**
	 * 根据设备编号获取设备类型
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月21日下午2:33:57
	 * 修改日期:2015年10月21日下午2:33:57
	 * @param code
	 * @return
	 */
	public UserDevice getUserDeviceByCode(String code);
	
	/**
	 * 验证身份证是否存在当前用户中<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月24日 下午2:11:04<br/>
	 * @param idCard
	 * @param userId
	 * @return
	 */
	public int userFirendIsValidIdCard(String idCard, String userId);
	
	/**
	 * 验证就诊人卡类型是否唯一<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月10日 下午3:15:51<br/>
	 * @param userId
	 * @param cardTypeId
	 * @param cardNo
	 * @return
	 */
	public int userFirendCheckCardUniqu(String userId,String cardTypeId,String cardNo,String idCard,String friendId);
	
	/**
	 * 健康文档记录<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月2日 下午3:49:13<br/>
	 * @param userId
	 * @param startRow
	 * @param rows
	 * @param code
	 * @return
	 */
	public List<McFb> getJkwdMessageByPage(String userId, Long startRow, Long rows,
			String code);
	/**
	 * 健康文档记录总数<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月2日 下午3:51:38<br/>
	 * @param userId
	 * @param code
	 * @return
	 */
	public int getJkwdMessageByPageCount(String userId, String code);
	
	/**
	 * 登陆账号验证<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月4日 上午10:50:40<br/>
	 * @param logonAcct
	 * @param type 登陆类型 0：单一登陆方式 1：混合登陆方式
	 * @return
	 */
	public int getUserByLoginAccout(String logonAcct,String type);
}
