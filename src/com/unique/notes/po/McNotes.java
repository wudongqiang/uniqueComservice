package com.unique.notes.po;

import java.math.BigDecimal;
import java.util.Date;

import com.unique.core.util.FileUtil;
import com.unique.core.util.StringUtil;


/**
 * 
 * 创建人 wdq
 * 修改人 
 * 创建时间 上午10:16:48
 * 修改时间 2015年8月17日
 * @author Administrator
 * @date 2015年8月17日
 * @description 公告管理
 */
public class McNotes {

	private BigDecimal notesId ;		//公告id。
	private BigDecimal notesTypeId;		//公告类型id。
	private BigDecimal orgId;			//机构id
	private String notesText;			//公告内容。
	private BigDecimal editor;			//编辑者。
	private Date editDate;				//编辑日期。
	private String editorName;			//编辑者名称
	private BigDecimal publisher;		//发布人。
	private Date publishDate;			//发布日期。
	private String publisherName;		//发布人姓名
	private String status;				//状态（c:新增，u:编辑,s:关闭,p:发布）。
	private BigDecimal operator;		//操作者。
	private Date opTime;				//操作时间。
	private String operatorName;		//操作者姓名
	
	private String notesTitle;			//文章标题			
	private String notesPic;			//封面图片
	private String notesSrc;			//文章来源
	private String viewCount;			//浏览量
	private BigDecimal contentTypeId;	//内容类型id
	private String isRecommend;			//是否推荐Y是N否
	
	private String image;//图片
	
	private String notesTypeName;
	
    //图片服务器
  	private static final String PIC_SERVER = FileUtil.readProperties("comservice.properties", "picServerOut");
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		if(!StringUtil.isEmpty(image)){
			this.image = PIC_SERVER+image;
		}
	}
	public String getNotesTypeName() {
		return notesTypeName;
	}
	public void setNotesTypeName(String notesTypeName) {
		this.notesTypeName = notesTypeName;
	}
	public static String getPicServer() {
		return PIC_SERVER;
	}
	public BigDecimal getNotesId() {
		return notesId;
	}
	public void setNotesId(BigDecimal notesId) {
		this.notesId = notesId;
	}
	public BigDecimal getNotesTypeId() {
		return notesTypeId;
	}
	public void setNotesTypeId(BigDecimal notesTypeId) {
		this.notesTypeId = notesTypeId;
	}
	public BigDecimal getOrgId() {
		return orgId;
	}
	public void setOrgId(BigDecimal orgId) {
		this.orgId = orgId;
	}
	public String getNotesText() {
		return notesText;
	}
	public void setNotesText(String notesText) {
		this.notesText = notesText;
	}
	public BigDecimal getEditor() {
		return editor;
	}
	public void setEditor(BigDecimal editor) {
		this.editor = editor;
	}
	public Date getEditDate() {
		return editDate;
	}
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}
	public String getEditorName() {
		return editorName;
	}
	public void setEditorName(String editorName) {
		this.editorName = editorName;
	}
	public BigDecimal getPublisher() {
		return publisher;
	}
	public void setPublisher(BigDecimal publisher) {
		this.publisher = publisher;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getOperator() {
		return operator;
	}
	public void setOperator(BigDecimal operator) {
		this.operator = operator;
	}
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getNotesTitle() {
		return notesTitle;
	}
	public void setNotesTitle(String notesTitle) {
		this.notesTitle = notesTitle;
	}
	public String getNotesPic() {
		return notesPic;
	}
	public void setNotesPic(String notesPic) {
		if(!StringUtil.isEmpty(notesPic)){
			this.notesPic = PIC_SERVER+notesPic;
		}
	}
	public String getNotesSrc() {
		return notesSrc;
	}
	public void setNotesSrc(String notesSrc) {
		this.notesSrc = notesSrc;
	}
	public String getViewCount() {
		return viewCount;
	}
	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}
	public BigDecimal getContentTypeId() {
		return contentTypeId;
	}
	public void setContentTypeId(BigDecimal contentTypeId) {
		this.contentTypeId = contentTypeId;
	}
	public String getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
}
