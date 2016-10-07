package com.unique.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unique.core.annotation.HttpMethod;
import com.unique.core.annotation.ParamNoNull;
import com.unique.reg.po.Staff;
import com.unique.user.po.AmsUser;
import com.unique.user.po.CardType;
import com.unique.user.po.McCard;
import com.unique.user.po.McFb;
import com.unique.user.po.McMsg;

public interface UserService {
	public HashMap<String, Object> getConfirmCode(String mobile, String intent);

	public HashMap<String, Object> register(String mobile, String confirm,
			String password,String calltype, String deviceToken,String type);
	
	public HashMap<String, Object> etRegister(String mobile, String confirm,
			String password,String userName,String idCard,String calltype, String deviceToken,String type);

	/**
	 *  用户登陆
	 * @param logonAcct 手机 or 身份证
	 * @param password
	 * @param deviceToken
	 * @param calltype
	 * @param weChatId 微信Id（用于微信登陆） 
	 * @return
	 */
	public HashMap<String, Object> login(String logonAcct, String password,
			String deviceToken,String calltype,String weChatId);

	public void loginOut(String userId,String calltype);

	/**
	 * 重医-用户端修改密码
	 * <br/>创建人 wdq
	 * <br/>创建时间 上午11:26:55
	 * @param userId
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	public HashMap<String, Object> modifyUserPwd(String userId, String oldPwd,
			String newPwd);
	
	public HashMap<String, Object> updateUserPwd(String mobile,
			String confirm, String password);
	
	public HashMap<String, Object> updateUserPwdNoConfirm(
			 String userId,
			 String password);

	public HashMap<String, Object> updateUser(String userId, String idCard,
			String userName, String nickName, String address, String imgUrl,String mobile,String age,
			 String sex,String type);
	public HashMap<String, Object> updateUserMobile(String mobile,String userId,String confirm);
	public HashMap<String, Object> getFriends(String userId);

	public Map<String, Object> addUserFriend(String userId, String idCard,
			String mobile, String userName, String address, String relation,
			String isDefault, String operator,List<McCard> cards,String sex,String orgId);

	public Map<String, Object> updateFriend(String userId, String friendId,
			String idCard, String userName, String address, String relation,
			String isDefault, String operator,String mobile,List<McCard> cards,String sex,String orgId);

	public HashMap<String, Object> delFriend(String friendId, String userId);
	
	/**
	 * 添加消息
	 * @param msg 消息内容
	 * @param title 消息标题
	 * @param userId 用户ID
	 * @param msgType 消息类型 YJFK:意见反馈，LXKF:离线客服
	 */
	public Map<String,Object> addMsg(String msg,
				String title,
				String userId,
				String msgType);
	/**
	 * 获取离线消息（分页）
	 * @param userId 用户ID
	 * @param startRow 开始行
	 * @param rows 每页行数
	 * @return
	 */
	public List<McFb> getLxMessageByPage(@ParamNoNull String userId,Long startRow,Long rows,String code);
	
	public Map<String,Object> getJkwdMessageByPage(String userId,Long startRow,Long rows,String code);
	
	public HashMap<String, Object> getLxMessageByPageCount(String userId,String code);
	
	/**
	 * 获取消息 分页  （可获取系统消息、医院公告）
	 * @param userId
	 * @param msgTypeCode  user_push_msg 推送消息 XTXX 系统消息 YYGG医院公告 YZXX 医嘱消息
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public List<McMsg> getMsgByPage(@ParamNoNull String userId,@ParamNoNull String[] msgTypeCode,Long startRow,Long rows);
	
	/**
	 * 获取根据用户Id，医生用户Id获取用户消息- 分页
	 * @param userId
	 * @param msgTypeCode
	 * @param startRow
	 * @param rows
	 * @param publishUserId 即发布用户在（医生端）获取患者消息时此值为医生 user_Id
	 * @return
	 */
	public List<McMsg> getUserMsgByPage(@ParamNoNull String userId,@ParamNoNull String publishUserId,Long startRow,Long rows);
	
	public HashMap<String, Object> getMsgByPageCount(@ParamNoNull String userId,@ParamNoNull String[]  msgTypeCode);
	
	public McMsg getMsgDetById(String msgId);
	
	public void setMsgRead(String msgId);
	
	public HashMap<String, Object> bindPushCode(String version, String deviceToken,String userId, String clientId);
	
	public HashMap<String, Object> addUser(AmsUser user);
	

	public Staff getStaffInfoByUserId(String userId);

//	public HashMap<String, Object> changePwd(String mobile, String ConfirmCode, String newPwd);
	
	/**
	 * 修改密码
	 * @param userId 
	 * @param oldPaw 原密码
	 * @param newPwd 新密码
	 * @param confirmPwd 确认密码
	 * @return
	 */
	public HashMap<String, Object> changePwd(@ParamNoNull String userId,@ParamNoNull String logonPwd,
			@ParamNoNull String newPwd,	@ParamNoNull String confirmPwd);

	/**
	 * 检查用户密码
	 * @param userId
	 * @param logonPwd
	 * @return
	 */
	Map<String, Object> checkUserPassword(@ParamNoNull String userId,@ParamNoNull String logonPwd);

	/**
	 * 通过id获取user 用户
	 * @param userId
	 * @return
	 */
	public AmsUser getUserById(@ParamNoNull String userId);
	
	/**
	 * 医生发送消息给患者
	 * @param msg
	 * @param title
	 * @param userId
	 * @param pubStaffId
	 * @param msgType
	 * @return
	 */
	public Map<String,Object> staffPushToUserMsg(String msg,
			String title,
			String friendId,
			String pubStaffId);

	/**
	 * 根据就诊人Id查询就诊信息
	 * @param friendId
	 * @return
	 */
	HashMap<String,Object> getFriendUserByFriendId(String friendId);

	/**
	 * 通过就诊Id查询出对于的用户
	 * @param friendId
	 * @return
	 */
	AmsUser getUserByFriendId(String friendId);

	/**
	 * 获取卡片类型集合
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月8日上午10:28:51
	 * 修改日期:2015年9月8日上午10:28:51
	 * @return
	 */
	@HttpMethod
	public List<CardType> getCardTypes();
	
	/**
	 * 检测验证码
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月9日下午3:16:37
	 * 修改日期:2015年9月9日下午3:16:37
	 * @param mobile
	 * @param confirm
	 * @return
	 */
	public HashMap<String, Object>  chkConfirm(
			 String mobile,
			 String confirm
			);
	/**
	 * 
	 * 通过微信Id获取用户<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月16日 上午11:23:13<br/>
	 * @param weChatId
	 * @return
	 */
	public HashMap<String,Object> getUserByWeChatId(String weChatId,String calltype);
	
	/**
	 * 
	 * 问答添加<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月17日 上午10:13:07<br/>
	 * @param userId
	 * @param typeCode 离线客服:LXKF,投诉:TS_USER,咨询:ZX,BUG:BUG,意见:YJFK,评价:PJ,其他:TS_QT,健康知识问答:JKWD				
	 * @param content
	 * @param pId
	 * @return
	 */
	public HashMap<String,Object> addMcFb(String userId,String typeCode,String content,String title,String pId);
	
	/**
	 * 
	 * 通过用户Id获取微信Id<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月3日 下午2:14:23<br/>
	 * @param userId
	 * @param calltype
	 * @return
	 */
	public String getWeChatIdByUserId(String userId,String calltype);

	/**
	 * 验证手机号码<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月4日 上午11:19:01<br/>
	 * @param mobile
	 * @return
	 */
	Map<String, Object> checkUserMobile(String mobile);

	/**
	 * <br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月4日 下午4:19:26<br/>
	 * @param logonAcct
	 * @param password
	 * @param deviceToken
	 * @param calltype
	 * @return
	 */
	HashMap<String, Object> etLogin(String logonAcct, String password,
			String deviceToken, String calltype);
}
