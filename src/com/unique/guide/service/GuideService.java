package com.unique.guide.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.unique.guide.po.JbBodyPart;
import com.unique.guide.po.JbIllness;
import com.unique.guide.po.JbSymptom;
import com.unique.guide.po.StandardDep;

public interface GuideService {
	public List<JbBodyPart> getBodys();
	
	public List<JbSymptom> getSymptom(String sex,String bodyId,String syId);
	
	public List<JbIllness> getIllnessesByRelation(String sex,String zsyId,String[] syIds,String age);
	
	public HashMap<String, Object> getIllness(String illId);
	
	public List<StandardDep> getStandards(String illId,String illDep);
	
	public List<JbSymptom> getSymptomsByBs(String zsyId,String bpId,String sex);
	
	/**
	 * 添加有用与报错信息
	 * @param sex
	 * @param zsyId
	 * @param syIds
	 * @param age
	 * @param isGood
	 * @param reason
	 * @return
	 */
	@Transactional
	public void addUsefulAndError(String userId,String sex,String zsyId,String[] syIds,String age,String isGood,String reason,String deviceToken);
	
	/**
	 * 根据id查询
	 * @param standardId
	 * @return
	 */
	public StandardDep getStandardDepById(String standardId);
	
	/**
	 * 获取疾病分页显示
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月11日下午3:02:46
	 * 修改日期:2015年9月11日下午3:02:46
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public Map<String,Object> getIllnessByPage(Long startRow, Long rows);
}
