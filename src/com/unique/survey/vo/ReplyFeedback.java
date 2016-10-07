/**
 * 2015年11月18日
 */
package com.unique.survey.vo;

/**
 * 回复请求实体<br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年11月18日 下午4:08:44<br/>
 * @author Administrator
 * @date 2015年11月18日
 * @description 
 */
public class ReplyFeedback {

	private String[] resultIds;
	private String docFeedback;
	
	public String[] getResultIds() {
		return resultIds;
	}
	public void setResultIds(String[] resultIds) {
		this.resultIds = resultIds;
	}
	public String getDocFeedback() {
		return docFeedback;
	}
	public void setDocFeedback(String docFeedback) {
		this.docFeedback = docFeedback;
	}
}
