package com.unique.org.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.unique.org.po.Dep;
import com.unique.org.po.DepKey;

@Repository("depDao")
public class DepDaoImpl implements DepDao{

	@Resource
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<Dep> get1stLevel(String[] orgIds) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orgIds", orgIds);
		return sqlSession.selectList("get1stLevlDep", param);
	}

	@Override
	public List<Dep> get2ndLevel(Map<String, String> params) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("get2ndLevlDep", params);
	}
	public Dep getDepInfo(String depId) {
		return sqlSession.selectOne("getDepInfo",depId);
	}
	public Dep getDepInfoByCode(String depCode) {
		return sqlSession.selectOne("getDepInfoByCode",depCode);
	}
	
	public int addDep(Dep dep){
		return sqlSession.insert("addDep",dep);
	}

	@Override
	public List<Dep> getAkeyDep(String orgId) {
		return sqlSession.selectList("getAkeyDep", orgId);
	}
	
	/**
	 * 根据疾病关键字查询科室列表
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月11日上午10:21:58
	 * 修改日期:2015年9月11日上午10:21:58
	 * @param depTypeId
	 * @param keyWord
	 * @return
	 */
	public List<Dep> getDepsByKeyName(String depTypeId,String keyWord){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("depTypeId", depTypeId);
		param.put("keyWord", keyWord);
		return sqlSession.selectList("getDepsByKeyName",param);
	}

	@Override
	public List<Dep> getDepDutyByDepTypeId(String depTypeId) {
		return sqlSession.selectList("getDepDutyByDepTypeId",depTypeId);
	}
	
	@Override
	public List<DepKey> getDepKeyByDepId(String depId){
		return sqlSession.selectList("getDepKeyByDepId",depId);
	}

	@Override
	public Dep getDepByDepName(String depName,String orgId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orgId", orgId);
		param.put("depName", depName);
		return sqlSession.selectOne("getDepByDepName",param);
	}
	
	@Override
	public List<Dep> getDutyDep(Map<String, String> params) {
		return sqlSession.selectList("getDutyDep", params);
	}
}
