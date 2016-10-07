package com.unique.notes.dao;

import java.util.List;

import com.unique.notes.po.McNotes;
import com.unique.notes.po.NotesType;
import com.unique.user.po.McFb;

/**
 * 
 * 创建人 wdq
 * 创建时间 上午10:34:38
 * @author Administrator
 * @date 2015年8月17日
 * @description
 */
public interface NotesDao {

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
	public List<McNotes> getMcNotesPageByOrgId(String orgId, Long startRow, Long rows);
	
	/**
	 * 获取医院公告总数
	 * 创建人 wdq
	 * 创建时间 上午10:24:32
	 * 修改时间 2015年8月17日
	 * @param orgId
	 * @return
	 */
	public long getMcNotesCountByOrgId(String orgId);

	/**
	 * 获取公告详情
	 * <br/>创建人 wdq
	 * <br/>创建时间 下午5:00:26
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
	 * 根据栏目类型获取文章列表
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月11日下午2:24:04
	 * 修改日期:2015年9月11日下午2:24:04
	 * @param orgId
	 * @param notesTypeId
	 * @param isRcom 是否推荐 Y
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public List<McNotes> getMcNotesPageByType(String orgId,String notesTypeId,String isRcom, Long startRow, Long rows);
	
	/**
	 * 根据栏目类型获取文章列表(总数)
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月11日下午2:24:04
	 * 修改日期:2015年9月11日下午2:24:04
	 * @param orgId
	 * @param notesTypeId
	 * @param isRcom 是否推荐 Y
	 * @return
	 */
	public long getMcNotesPageByTypeCount(String orgId,String notesTypeId,String isRcom);

	/**
	 * 通过匹配tilte查询疾病相关科普文章<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月9日 下午5:02:38<br/>
	 * @param title
	 * @return
	 */
	public List<McNotes> getMcNotesLikeTitles(String title);
	
	/**
	 * 通过匹配tilte查询疾病相关知识问答<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月9日 下午5:02:38<br/>
	 * @param titles
	 * @return
	 */
	public List<McFb> getMcFBLikeTitles(String []titles);
	
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
	 * 更新浏览量<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月20日 下午4:56:13<br/>
	 * @param notesId
	 */
	public int updateViewCount(String notesId);
	
	/**
	 * 获取用户服务中的健康宣教<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月24日 下午1:51:44<br/>
	 * @param userId
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public List<McNotes> getUserHealthMcNotes(String userId,Long startRow,Long rows);
	
	/**
	 * 获取用户服务中的健康宣教总数<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月24日 下午1:51:44<br/>
	 * @param userId
	 * @return
	 */
	public long getUserHealthMcNotesCount(String userId);
}
