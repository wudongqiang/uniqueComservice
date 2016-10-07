package com.unique.user.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

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

/**
 * 用户管理持久层
 * @author Angel
 *
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao{
	@Resource
	private SqlSessionTemplate sqlSession;
	
	public int addUser(AmsUser user){
		return sqlSession.insert("regUser",user);
	}
	
	public int addUserFriend(UserFriend userf){
		return sqlSession.insert("addUserFriend",userf);
	}
	
	public AmsUser getUserByPassword(String logonAcct,String pwd,String typeCode,String type){
		AmsUser param = new AmsUser();
		param.setLogonAcct(logonAcct);
		param.setLogonPwd(pwd);
		param.setTypeCode(typeCode);
		param.setA1(type); // 登陆方式 
		return sqlSession.selectOne("getUserByPassword",param);
	}
	
	public void updateUserInfo(AmsUser user){
		sqlSession.update("updateUserInfo",user);
	}
	
	public AmsUser getUserById(String uid){
		return sqlSession.selectOne("getUserById",uid);
	}
	
	public AmsUser getUserByMobile(String mobile){
		return  sqlSession.selectOne("getUserByMobile",mobile);
	}
	
	public UserFriend getFriendUserBySelf(String uid){
		return sqlSession.selectOne("getFriendUserBySelf",uid);
	}
	
	public UserFriend getFriendUserByFriendId(String uid){
		return sqlSession.selectOne("getFriendUserByFriendId",uid);
	}
	
	public void updateUserFriend(UserFriend uf){
		sqlSession.update("updateUserFriend",uf);
	}
	
	public void setFriendNoDefault(String userId){
		sqlSession.update("setFriendNoDefault",userId);
	}
	public void setFriendDefault(String userId){
		sqlSession.update("setFriendDefault",userId);
	}
	public void delFriend(String friendId){
		sqlSession.update("delFriend",friendId);
	}
	
	public List<UserFriend> getFriendUsersByUid(String friendId){
		return sqlSession.selectList("getFriendUsersByUid",friendId);
	}
	
	public int checkUser(String mobile){
		return sqlSession.selectOne("checkUser",mobile);
	}
	public AmsUser checkUserByIdCard(String idCard){
		return sqlSession.selectOne("checkUserByIdCard",idCard);
	}
	
	
	public int log(UserCallLog log){
		return sqlSession.insert("serviceLog",log);
	}
	
	/**
	 * 批量提交日志
	 * @param logs
	 */
	public void logs(List<UserCallLog> logs){
		SqlSession sess = sqlSession.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		for(UserCallLog log : logs){
			sess.insert("serviceLog",log);
		}
		sess.commit();
		sess.clearCache();
	}
	
	public McFbType getMcFbTypeByCode(String code){
		return sqlSession.selectOne("getMsgTypeByCode",code);
	}
	public MsgType getMsgTypeByCode(String typeCode){
		return sqlSession.selectOne("getMsgType",typeCode);
	}
	/**
	 * 添加意见反馈
	 * @param mcFb
	 */
	public int addMsg(McFb mcFb){
		return sqlSession.insert("addMsg",mcFb);
	}
	
	/**
	 * 获取离线消息分页
	 * @param userId
	 * @return
	 */
	public List<McFb> getLxMessageByPage(String userId,long startRow,long rows,String code){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("startRow", startRow);
		param.put("endRow", startRow + rows);
		param.put("code", code);
		return sqlSession.selectList("getLxMessageByPage",param);
	}
	
	public long getLxMessageByPageCount(String userId,String code){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("code", code);
		return sqlSession.selectOne("getLxMessageByPageCount",param);
	}
	
	/**
	 * 记录登陆日志
	 * @param userLog
	 */
	public void addLoginLog(AmsUserLog userLog){
		sqlSession.insert("addUserLog",userLog);
	}
	
	public AmsUserLog getLastLogonTime(String userId){
		return sqlSession.selectOne("getLastLoginLog",userId);
	}
	
	public void updateUserLogById(AmsUserLog userLog ){
		sqlSession.update("updateUserLogById",userLog);
	}
	
	public List<McMsg> getMsgByPage(String userId,String[] msgTypeCode,long startRow,long rows){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("startRow", startRow);
		param.put("endRow", startRow + rows);
		param.put("typeCode", msgTypeCode);
		return sqlSession.selectList("getMsgByPage",param);
	}
	
	public List<McMsg> getUserMsgByPage(String userId,String publishUserId,long startRow,long rows){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("publishUserId", publishUserId);
		param.put("startRow", startRow);
		param.put("endRow", startRow + rows);
		return sqlSession.selectList("getUserMsgByPage",param);
	}
	
	public long getMsgByPageCount(String userId,String[] msgTypeCode){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("typeCode", msgTypeCode);
		return sqlSession.selectOne("getMsgByPageCount",param);
	}
	
	public McMsg getMsgDetById(String msgId){
		return sqlSession.selectOne("getMegDet",msgId);
	}
	
	public void setMsgRead(String msgId){
		sqlSession.update("setMsgRead",msgId);
	}
	
	public long getWdMsgCount(String userId,String[] msgTypeCode){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("typeCode", msgTypeCode);
		return sqlSession.selectOne("getWdMsgCount",param);
	}
	
	public int addTsMsg(McMsg msg){
		return sqlSession.insert("addTsMsg",msg);
	}
 
	public Staff getStaffInfoByUserId(String userId){
		return sqlSession.selectOne("getStaffInfoByUserId",userId);
	}
	
	public List<UserSchedule> getScheduleResultMapByPage(String userId,long startRow,long rows,String startTime,String endTime){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("startTime",startTime);
		param.put("endTime", endTime);
		param.put("startRow", startRow);
		param.put("endRow", startRow + rows);
		return sqlSession.selectList("getScheduleResultMapByPage",param);
	}
	
	public long getScheduleResultMapByPageCount(String userId,String startTime,String endTime){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("startTime",startTime);
		param.put("endTime", endTime);
		return sqlSession.selectOne("getScheduleResultMapByPageCount",param);
	}

	@Override
	public long checkUserPassword(String userId, String logonPwd) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("logonPwd",logonPwd);
		return sqlSession.selectOne("checkUserPassword",param);
	}
 
	/**
	 * 根据就诊人ID获取卡片集合
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月7日下午2:33:55
	 * 修改日期:2015年9月7日下午2:33:55
	 * @param friendId
	 * @return
	 */
	public List<McCard> getCardByFriendId(String friendId){
		return sqlSession.selectList("getCardByFriendId",friendId);
	}
	
	/**
	 * 添加就诊卡
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月8日上午9:31:30
	 * 修改日期:2015年9月8日上午9:31:30
	 * @param card
	 * @return
	 */
	public int addMcCard(McCard card){
		return sqlSession.insert("addMcCard",card);
	}
	
	/**
	 * 删除卡片集合
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月8日上午9:47:32
	 * 修改日期:2015年9月8日上午9:47:32
	 * @param ids
	 */
	public void delCards(Set<BigDecimal> ids){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		sqlSession.delete("delCards",map);
	}
	
	/**
	 * 更新用户卡片
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月8日上午9:52:24
	 * 修改日期:2015年9月8日上午9:52:24
	 * @param card
	 */
	public void updateMcCardById(McCard card){
		card.setOpTime(new Date());
		card.setStatus("U");
		sqlSession.update("updateMcCardById",card);
	}
	
	/**
	 * 获取卡片类型集合
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月8日上午10:28:51
	 * 修改日期:2015年9月8日上午10:28:51
	 * @return
	 */
	public List<CardType> getCardTypes(){
		return sqlSession.selectList("getCardTypes");
	}
 
	@Override
	public AmsUser getUserByWeChatId(String weChatId,String calltype) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("weChatId", weChatId);
		map.put("calltype", calltype);
		return sqlSession.selectOne("getUserByWeChatId", map);
	}
	
	@Override
	public int addMcFb(McFb mcFb) {
		return sqlSession.insert("addMcFb",mcFb);
	}
	
	/**
	 * 根据设备编号获取设备类型
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月21日下午2:33:57
	 * 修改日期:2015年10月21日下午2:33:57
	 * @param code
	 * @return
	 */
	public UserDevice getUserDeviceByCode(String code){
		return sqlSession.selectOne("getUserDeviceByCode","code");
	}
 
	@Override
	public int userFirendIsValidIdCard(String idCard, String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("idCard", idCard);
		return sqlSession.selectOne("userFirendIsValidIdCard",map);
	}
	
	@Override
	public int userFirendCheckCardUniqu(String userId, String cardTypeId,
			String cardNo, String idCard,String friendId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("cardTypeId", cardTypeId);
		map.put("cardNo", cardNo);
		map.put("userIdCard", idCard);
		map.put("friendId", friendId);
		return sqlSession.selectOne("userFirendCheckCardUniqu",map);
	}
	
	@Override
	public List<McFb> getJkwdMessageByPage(String userId, Long startRow,
			Long rows, String code) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("startRow", startRow);
		param.put("endRow", startRow + rows);
		param.put("code", code);
		return sqlSession.selectList("getJkwdMessageByPage",param);
	}
 
	@Override
	public int getJkwdMessageByPageCount(String userId, String code) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("code", code);
		return sqlSession.selectOne("getJkwdMessageByPageCount",param);
	}
	
	@Override
	public int getUserByLoginAccout(String logonAcct,String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("logonAcct", logonAcct);
		param.put("type", type);
		return sqlSession.selectOne("getUserByLoginAccout",param);
	}
}
