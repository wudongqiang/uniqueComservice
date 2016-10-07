package com.unique.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.unique.core.util.QueryUtils;
import com.unique.mb.po.EarlyWarns;
import com.unique.system.po.AdContent;
import com.unique.system.po.ImMsgInfo;
import com.unique.system.po.UserClientType;
import com.unique.system.po.VersionConfig;

@Repository("systemDao")
public class SystemDaoImpl implements SystemDao{
	@Resource
	private SqlSessionTemplate sqlSession;
	
	public VersionConfig getVersion(String typeId){
		return sqlSession.selectOne("getVersion",typeId);
	}
	
	public List<AdContent> getAdByPosition(String code){
		return sqlSession.selectList("getAdByPosition",code);
	}
	
	public int addUserClient(UserClientType userClient){
		return sqlSession.insert("addUserClient",userClient);
	}
	
	public UserClientType getUserClientByToken(String deviceToken){
		return sqlSession.selectOne("getUserClientByToken",deviceToken);
	}
	
	public int updateUserClient(UserClientType userClient){
		return sqlSession.update("updateUserClient",userClient);
	}
	
	public List<UserClientType> getClientTypeByUcts(String[] ucts){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ucts", ucts);
		return sqlSession.selectList("getClientTypeByUcts",map);
	}
	
	public int updateClientType(String pushCode,String clientTypeId,String deviceToken){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pushCode", pushCode);
		map.put("clientTypeId", clientTypeId);
		map.put("deviceToken", deviceToken);
		return sqlSession.update("updateClientType",map);
	}
	
	public UserClientType getPushCode(String deviceToken,String clientId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("deviceToken", deviceToken);
		param.put("clientId", clientId);
		
		return sqlSession.selectOne("getPushCode",param);
	}
	
	/**
	 * 添加IM消息记录
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月13日下午2:42:20
	 * 修改日期:2015年8月13日下午2:42:20
	 * @param msginfo
	 * @return
	 */
	public int addImMsg(ImMsgInfo msginfo){
		return sqlSession.insert("addImMsg",msginfo);
	}
	
	/**
	 * 批量添加IM消息
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年8月13日下午3:26:19
	 * 修改日期:2015年8月13日下午3:26:19
	 * @param msginfos
	 * @return
	 */
	public void addImMsgs(List<ImMsgInfo> msginfos){
		SqlSession sess = sqlSession.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		for(ImMsgInfo msginfo : msginfos){
			sess.insert("addImMsg",msginfo);
		}
		sess.commit();
		sess.clearCache();
	}
	
	@Override
	public String getCmsImageByWhereId(QueryUtils.CmsImageLib filedName, String filedValue,String useCode) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("filedName", filedName);
		params.put("filedValue", filedValue);
		params.put("useCode", useCode);
		return sqlSession.selectOne("getCmsImageByWhereId",params);
	}
	
	/**
	 * 获取警告消息列表
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月15日上午11:14:55
	 * 修改日期:2015年10月15日上午11:14:55
	 * @param userId
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public List<ImMsgInfo> getWarnMsg(String userId,String dateStr, Long startRow, Long rows){
		Map<String,Object> params = new HashMap<String,Object>(3);
		params.put("startRow", startRow);
		params.put("endRow", rows+startRow);
		params.put("userId", userId);
		params.put("dateStr", dateStr);
		return sqlSession.selectList("getWarnMsg",params);
	}
	
	/**
	 * 查询警告消息总数
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年10月15日上午11:16:44
	 * 修改日期:2015年10月15日上午11:16:44
	 * @param userId
	 * @return
	 */
	public long getWarnMsgCount(String userId,String dateStr){
		Map<String,Object> params = new HashMap<String,Object>(3);
		params.put("userId", userId);
		params.put("dateStr", dateStr);
		return sqlSession.selectOne("getWarnMsgCount",params);
	}

	@Override
	public List<String> getWarnMsgDates(String userId, String dateStr) {
		Map<String,Object> params = new HashMap<String,Object>(3);
		params.put("userId", userId);
		params.put("dateStr", dateStr);
		return sqlSession.selectList("getWarnMsgDates",params);
	}
	
	@Override
	public String getCmsImgTypeIdByTypeCode(String typeCode) {
		return sqlSession.selectOne("getCmsImgTypeIdByTypeCode",typeCode);
	}

	@Override
	public int addCmsImgLibForAppoint(Map<String, Object> map) {
		return sqlSession.insert("addCmsImgLibForAppoint",map);
	}
	
	@Override
	public List<EarlyWarns> getEarlyWarns(String staffId,String dateStr, Long startRow,
			Long rows) {
		Map<String,Object> params = new HashMap<String,Object>(3);
		params.put("startRow", startRow);
		params.put("endRow", rows+startRow);
		params.put("dateStr", dateStr);
		params.put("staffId", staffId);
		return sqlSession.selectList("getEarlyWarns", params);
	}
	
	@Override
	public long getEarlyWarnsCount(String staffId,String dateStr) {
		Map<String,Object> params = new HashMap<String,Object>(3);
		params.put("staffId", staffId);
		params.put("dateStr", dateStr);
		return sqlSession.selectOne("getEarlyWarnsCount", params);
	}
}
