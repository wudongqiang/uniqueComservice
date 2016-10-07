package com.unique.notes.service;

import java.util.List;
import java.util.Map;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.notes.po.McNotes;
import com.unique.notes.po.NotesType;
import com.unique.user.po.McFb;

/**
 * 
 * 创建人 wdq
 * 修改人 
 * 创建时间 上午10:13:30
 * 修改时间 2015年8月17日
 * @author Administrator
 * @date 2015年8月17日
 * @description
 */
public interface NotesService {

	/**
	 * 获取医院公告 -最近的3条
	 * 创建人 wdq
	 * 创建时间 上午10:18:13
	 * 修改时间 2015年8月17日
	 * @return
	 */
	public Map<String,Object> getMcNotesRecentlyByOrgId(String orgId);
	
	/**
	 * 获取医院公告分页
	 * 创建人 wdq
	 * 创建时间 上午10:21:35
	 * 修改时间 2015年8月17日
	 * @param orgId 医院id （组织id）
	 * @param startRow 开始行
	 * @param rows 行数-（一页显示多少行）
	 * @return
	 */
	public Map<String,Object> getMcNotesPageByOrgId(String orgId, Long startRow, Long rows);
	
	/**
	 * 获取医院公告总数
	 * 创建人 wdq
	 * 创建时间 上午10:24:32
	 * 修改时间 2015年8月17日
	 * @param orgId
	 * @return
	 */
	public Map<String,Object> getMcNotesCountByOrgId(String orgId);
	
	/**
	 * 获取公告详情
	 * <br/>创建人 wdq
	 * <br/>创建时间 下午4:59:43
	 * @param notesId
	 * @return
	 */
	public McNotes getMcNotesInfo(String notesId);
	
	/**
	 * 获取所有文章类型
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月11日下午2:09:41
	 * 修改日期:2015年9月11日下午2:09:41
	 * @return
	 */
	public List<NotesType> getNotesTypes();
	
	/**
	 * 根据栏目类型ID获取文章并分页
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月11日下午2:26:44
	 * 修改日期:2015年9月11日下午2:26:44
	 * @param orgId
	 * @param notesTypeId
	 * @param startRow
	 * @param rows
	 * @return
	 */
	@HttpMethod
	public Map<String,Object> getMcNotesPageByType(@NotNull String orgId,@NotNull String notesTypeId,String isRcom, Long startRow, Long rows);
	
	/**
	 * 
	 * 获取问答详细<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月11日 上午11:24:10<br/>
	 * @param fbId
	 * @return
	 */
	public McFb getMcFBDetailById(String fbId);
	
	/**
	 * 获取用户服务中的健康宣教<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月24日 下午1:51:44<br/>
	 * @param userId
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public Map<String,Object> getUserHealthMcNotes(String userId,Long startRow,Long rows);
}
