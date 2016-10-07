package com.unique.notes.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.unique.notes.po.McNotes;
import com.unique.notes.po.NotesType;
import com.unique.user.po.McFb;

/**
 * 
 * 创建人 wdq
 * 创建时间 上午10:34:45
 * @author Administrator
 * @date 2015年8月17日
 * @description
 */
@Repository("notesDao")
public class NotesDaoImpl implements NotesDao{

	@Resource
	private SqlSession sqlSession;

	@Override
	public List<McNotes> getMcNotesPageByOrgId(String orgId, Long startRow, Long rows) {
		Map<String,Object> params = new HashMap<String,Object>(3);
		params.put("startRow", startRow);
		params.put("endRow", rows+startRow);
		params.put("orgId", orgId);
		return sqlSession.selectList("getMcNotesPageByOrgId", params);
	}

	@Override
	public long getMcNotesCountByOrgId(String orgId) {
		return sqlSession.selectOne("getMcNotesCountByOrgId", orgId);
	}

	@Override
	public McNotes getMcNotesInfo(String notesId) {
		return sqlSession.selectOne("getMcNotesInfo", notesId);
	}
	
	
	/**
	 * 获取所有文章类型
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月11日下午2:09:41
	 * 修改日期:2015年9月11日下午2:09:41
	 * @return
	 */
	public List<NotesType> getNotesTypes(){
		return sqlSession.selectList("getNotesTypes");
	}
	
	
	/**
	 * 根据栏目类型获取文章列表
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月11日下午2:24:04
	 * 修改日期:2015年9月11日下午2:24:04
	 * @param orgId
	 * @param notesTypeId
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public List<McNotes> getMcNotesPageByType(String orgId,String notesTypeId,String isRcom, Long startRow, Long rows) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startRow", startRow);
		params.put("endRow", rows+startRow);
		params.put("orgId", orgId);
		params.put("notesTypeId", notesTypeId);
		params.put("isRcom", isRcom);
		return sqlSession.selectList("getMcNotesPageByType", params);
	}
	
	/**
	 * 根据栏目类型获取文章列表(总数)
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月11日下午2:24:04
	 * 修改日期:2015年9月11日下午2:24:04
	 * @param orgId
	 * @param notesTypeId
	 * @return
	 */
	public long getMcNotesPageByTypeCount(String orgId,String notesTypeId,String isRcom) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orgId", orgId);
		params.put("notesTypeId", notesTypeId);
		params.put("isRcom", isRcom);
		return sqlSession.selectOne("getMcNotesPageByType_Count", params);
	}

	 
	@Override
	public List<McNotes> getMcNotesLikeTitles(String title) {
		return sqlSession.selectList("getMcNotesLikeTitles", title);
	}
	
	public List<McFb> getMcFBLikeTitles(String []titles){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("titles", titles);
		return sqlSession.selectList("getMcFBLikeTitles", params);
	}
	
	public McFb getMcFBDetailById(String fbId){
		return sqlSession.selectOne("getMcFBDetailById", fbId);
	}
	
	@Override
	public int updateViewCount(String notesId) {
		return sqlSession.update("updateViewCount", notesId);
	}
	
	@Override
	public List<McNotes> getUserHealthMcNotes(String userId,Long startRow,Long rows ) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("startRow", startRow);
		params.put("endRow", rows+startRow);
		return sqlSession.selectList("getUserHealthMcNotes", params);
	}
	
	@Override
	public long getUserHealthMcNotesCount(String userId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		return sqlSession.selectOne("getUserHealthMcNotesCount", params);
	}
}
