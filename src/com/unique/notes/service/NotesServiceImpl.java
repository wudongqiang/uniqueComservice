package com.unique.notes.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.util.RegexUtil;
import com.unique.notes.dao.NotesDao;
import com.unique.notes.po.McNotes;
import com.unique.notes.po.NotesType;
import com.unique.user.po.McFb;

/**
 * 
 * 创建人 wdq
 * 修改人 
 * 创建时间 上午10:12:04
 * 修改时间 2015年8月17日
 * @author Administrator
 * @date 2015年8月17日
 * @description 消息内容管理服务  
 */
@Service("notesService")
public class NotesServiceImpl implements NotesService{
	
	@Resource
	private NotesDao notesDao;

	@HttpMethod
	@Override
	public Map<String,Object> getMcNotesRecentlyByOrgId(@NotNull String orgId) {
		Map<String,Object> map = new HashMap<String,Object>(1);
		List<McNotes> mcNotesList = notesDao.getMcNotesPageByOrgId(orgId, 0L, 3L);
		for(McNotes mcNotes : mcNotesList){
			mcNotes.setNotesText(RegexUtil.htmlRemoveTag(mcNotes.getNotesText()));
		}
		map.put("list", mcNotesList);
		return map;
	}

	@HttpMethod
	@Override
	public Map<String,Object> getMcNotesPageByOrgId(@NotNull String orgId, Long startRow, Long rows) {
		if(startRow==null) startRow = 0L;
		if(rows==null) rows = 20L;
		
		List<McNotes> mcNotesList = notesDao.getMcNotesPageByOrgId(orgId, startRow, rows);
		for(McNotes mcNotes : mcNotesList){
			mcNotes.setNotesText(RegexUtil.htmlRemoveTag(mcNotes.getNotesText()));
		}
		
		Map<String,Object> map = new HashMap<String,Object>(2);
		map.put("list", mcNotesList);
		map.put("count",notesDao.getMcNotesCountByOrgId(orgId));
		return map;
	}

	@HttpMethod
	@Override
	public Map<String,Object> getMcNotesCountByOrgId(@NotNull String orgId) {
		Map<String,Object> map = new HashMap<String,Object>(1);
		map.put("count",notesDao.getMcNotesCountByOrgId(orgId));
		return map;
	}

	@HttpMethod
	@Override
	@Transactional
	public McNotes getMcNotesInfo(String notesId) {
		//更新浏览量
		notesDao.updateViewCount(notesId);
		return notesDao.getMcNotesInfo(notesId);
	}

	
	/**
	 * 获取所有文章类型
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月11日下午2:09:41
	 * 修改日期:2015年9月11日下午2:09:41
	 * @return
	 */
	@HttpMethod
	public List<NotesType> getNotesTypes(){
		return notesDao.getNotesTypes();
	}
	
	/**
	 * 根据栏目类型ID获取文章并分页
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月11日下午2:26:44
	 * 修改日期:2015年9月11日下午2:26:44
	 * @param orgId
	 * @param notesTypeId
	 * @param isRcom 推荐 Y
	 * @param startRow
	 * @param rows
	 * @return
	 */
	@HttpMethod
	public Map<String,Object> getMcNotesPageByType(@NotNull String orgId,String notesTypeId,String isRcom, Long startRow, Long rows) {
		if(startRow==null) startRow = 0L;
		if(rows==null) rows = 20L;
		Map<String,Object> map = new HashMap<String,Object>();

		List<McNotes> mcNotesList = notesDao.getMcNotesPageByType(orgId,notesTypeId,isRcom, startRow, rows);
		for(McNotes mcNotes : mcNotesList){
			mcNotes.setNotesText(RegexUtil.htmlRemoveTag(mcNotes.getNotesText()));
		}
		map.put("list", mcNotesList);
		map.put("count",notesDao.getMcNotesPageByTypeCount(orgId, notesTypeId,isRcom));
		return map;
	}
	
	@HttpMethod
	public McFb getMcFBDetailById(@NotNull String fbId){
		return notesDao.getMcFBDetailById(fbId);
	} 
	
	@HttpMethod
	@Override
	public Map<String, Object> getUserHealthMcNotes(String userId,Long startRow,Long rows) {
		if(startRow==null) startRow = 0L;
		if(rows==null) rows = 20L;
		Map<String,Object> map = new HashMap<String,Object>();

		List<McNotes> mcNotesList = notesDao.getUserHealthMcNotes(userId, startRow, rows);
		for(McNotes mcNotes : mcNotesList){
			mcNotes.setNotesText(RegexUtil.htmlRemoveTag(mcNotes.getNotesText()));
		}
		map.put("list", mcNotesList);
		map.put("count",notesDao.getUserHealthMcNotesCount(userId));
		return map;
	}
}
