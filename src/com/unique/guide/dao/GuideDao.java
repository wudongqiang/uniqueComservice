package com.unique.guide.dao;

import java.util.Date;
import java.util.List;

import com.unique.guide.po.JbBodyPart;
import com.unique.guide.po.JbIllness;
import com.unique.guide.po.JbSymptom;
import com.unique.guide.po.StandardDep;
import com.unique.guide.po.SyMainFollow;
import com.unique.guide.po.SyRelationResSts;
import com.unique.notes.po.McNotes;
import com.unique.org.po.Dep;
import com.unique.reg.po.Org;


public interface GuideDao {
	public List<JbBodyPart> getBodys();
	
	public List<JbSymptom> getSymptom(String sex,String bodyId,String syId);

	public List<JbIllness> getIllnessesByRelation(String zsyId,String syIds,String syId,String sex,String age);
	
	public List<Org> getDepByIll(String[] illId);
	public List<Dep> getRecDepByIll(String[] illId);
	
	public List<StandardDep> getStandardDeps(String illId,String illDep);
	
	public List<JbSymptom> getSymptomsByBs(String zsyId,String bpId,String sex);
	
	public JbIllness getIllnessById(String illId);
	
	public List<JbSymptom> getSymptoms4Lucence(Date lastTime,String[] ids);
	
	public List<JbIllness> getIllness4Lucence(Date lastTime,String[] ids);
	
	public List<JbIllness> getIllnessByStandard(String standId);
	
	public List<JbSymptom> getBsSyByZzz(String[] syIds);
	
	/**
	 * 获取关系ID集合
	 * @param zsyId
	 * @param syIds
	 * @param syId
	 * @param sex
	 * @param age
	 * @return
	 */
	public List<String> getSyRelations(String zsyId,String syIds,String syId,String sex,String age);
	
	public int addSyRelation(SyRelationResSts relation);
	
	/**
	 * 根据id查询
	 * @param standardId
	 * @return
	 */
	public StandardDep getStandardDepById(String standardId);
	
	public List<McNotes> getIllnessByPage(Long startRow, Long rows);
	
	public long getIllnessByPageCount();
	 
	/**
	 * 
	 * 主症状与伴随症状关系查询<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月9日 下午4:48:09<br/>
	 * @param zsyIds 主症状数组
	 * @param fsyIds 伴随症状数组
	 * @return
	 */
	public List<SyMainFollow> getSyMainFollowName(String[] zsyIds,String[] fsyIds);
}
