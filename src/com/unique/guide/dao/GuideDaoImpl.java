package com.unique.guide.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.unique.guide.po.JbBodyPart;
import com.unique.guide.po.JbIllness;
import com.unique.guide.po.JbSymptom;
import com.unique.guide.po.StandardDep;
import com.unique.guide.po.SyMainFollow;
import com.unique.guide.po.SyRelationResSts;
import com.unique.notes.po.McNotes;
import com.unique.org.po.Dep;
import com.unique.reg.po.Org;

@Repository("guideDao")
public class GuideDaoImpl implements GuideDao {
	@Resource
	private SqlSession sqlSession;

	public List<JbBodyPart> getBodys(){
		return sqlSession.selectList("getBodys");
	}
	
	/**
	 * 获取症状
	 * @param sex
	 * @param bodyId
	 * @return
	 */
	public List<JbSymptom> getSymptom(String sex,String bodyId,String syId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sex", sex);
		map.put("bodyId", bodyId);
		map.put("syId", syId);
		return sqlSession.selectList("getSymptoms",map);
	}

	public JbIllness getIllnessById(String illId){
		return sqlSession.selectOne("getIllnessById",illId);
	}
	
	public List<JbIllness> getIllnessesByRelation(String zsyId,String syIds,String syId,String sex,String age){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("zsyId", zsyId);
		map.put("syIds", syIds);
		map.put("sex", sex);
		map.put("age", age);
		return sqlSession.selectList("getIllnessesByRelation",map);
	}
	
	
	/**
	 * 根据疾病查找机构
	 * @param illId
	 * @return
	 */
	public List<Org> getDepByIll(String[] illId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("illIds", illId);
		return sqlSession.selectList("getDepByIll",map);
	}
	
	/**
	 * 推荐科室by疾病
	 * @param illId
	 * @return
	 */
	public List<Dep> getRecDepByIll(String[] illId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("illIds", illId);
		return sqlSession.selectList("getRecDepByIll",map);
	}
	
	/**
	 * 获取标准科室列表
	 * @param illId
	 * @return
	 */
	public List<StandardDep> getStandardDeps(String illId,String illDep){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("illId", illId);
		map.put("illDep", illDep);
		return sqlSession.selectList("getStandardDeps",map);
	}
	
	public List<JbSymptom> getSymptomsByBs(String zsyId,String bpId,String sex){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("zsyId", zsyId);
		map.put("bpId", bpId);
		map.put("sex", sex);
		return sqlSession.selectList("getSymptomsByBs",map);
	}
	
	public List<JbSymptom> getSymptoms4Lucence(Date lastTime,String[] ids){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lastTime", lastTime);
		param.put("ids", ids);
		return sqlSession.selectList("getSymptoms4Lucence",param);
	}
	
	public List<JbIllness> getIllness4Lucence(Date lastTime,String[] ids){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lastTime", lastTime);
		param.put("ids", ids);
		return sqlSession.selectList("getIllness4Lucence",param);
	}
	
	public List<JbIllness> getIllnessByStandard(String standId){
		return sqlSession.selectList("getIllnessByStandard",standId);
	}

	public List<JbSymptom> getBsSyByZzz(String[] syIds){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("syIds", syIds);
		return sqlSession.selectList("getBsSyByZzz",param);
	}
	
	/**
	 * 获取关系ID集合
	 * @param zsyId
	 * @param syIds
	 * @param syId
	 * @param sex
	 * @param age
	 * @return
	 */
	public List<String> getSyRelations(String zsyId,String syIds,String syId,String sex,String age){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("zsyId", zsyId);
		map.put("syIds", syIds);
		map.put("sex", sex);
		map.put("age", age);
		return sqlSession.selectList("getSyRelations",map);
	}
	
	public int addSyRelation(SyRelationResSts relation){
		return sqlSession.insert("addSyRelation",relation);
	}

	@Override
	public StandardDep getStandardDepById(String standardId) {
		return sqlSession.selectOne("getStandardDepById",standardId);
	}
	
	public List<McNotes> getIllnessByPage(Long startRow, Long rows) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startRow", startRow);
		params.put("endRow", rows+startRow);
		return sqlSession.selectList("getIllnessByPage", params);
	}
	
	public long getIllnessByPageCount() {
		return sqlSession.selectOne("getIllnessByPageCount");
	}
	
	@Override
	public List<SyMainFollow> getSyMainFollowName(String[] zsyIds,
			String[] fsyIds) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("zsyIds", zsyIds);
		params.put("fsyIds",fsyIds);
		return sqlSession.selectList("getSyMainFollowName", params);
	}
}
