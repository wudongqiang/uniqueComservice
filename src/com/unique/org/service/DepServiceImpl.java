package com.unique.org.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.unique.core.annotation.HttpMethod;
import com.unique.core.annotation.ParamNoNull;
import com.unique.core.util.StringUtil;
import com.unique.org.dao.DepDao;
import com.unique.org.po.Dep;
import com.unique.org.po.DepKey;

@Service("depService")
public class DepServiceImpl implements DepService{

	@Resource
	private DepDao depDao;
	
	/**
	 * 医院一级科室列表
	 */
	@Override
	@HttpMethod
	public List<Dep> get1stLevel(@ParamNoNull String orgId) {
		// TODO Auto-generated method stub
		return depDao.get1stLevel(new String[]{orgId});
	}

	/**
	 * 医院二级科室列表
	 */
	@Override
	@HttpMethod
	public List<Dep> get2ndLevel(@ParamNoNull String orgId, @ParamNoNull String parentId) {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
		params.put("orgId", orgId);
		params.put("parentId", parentId);
		return depDao.get2ndLevel(params);
	}
	
	@HttpMethod
	@Override
	public List<Dep> getDutyDep(@ParamNoNull String orgId, @ParamNoNull String parentId) {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
		params.put("orgId", orgId);
		params.put("parentId", parentId);
		return depDao.getDutyDep(params);
	}
	
	@HttpMethod
	public Dep getDepInfo(@ParamNoNull String depId) {
		return depDao.getDepInfo(depId);
	}

	@HttpMethod
	@Transactional
	public HashMap<String, Object> addDep(Dep dep, String parentCode){
		HashMap<String, Object> result = new HashMap<String, Object>();
		Dep depChild = depDao.getDepInfoByCode(dep.getDepCode());
		if(depChild!=null){
			result.put("retCode", 4);
			result.put("retMessage", "科室已存在！");
		}else{
			if(!StringUtil.isEmpty(parentCode)){
				Dep parent = depDao.getDepInfoByCode(parentCode);
				dep.setParentId(parent.getParentId());
			}
			if(StringUtil.isEmpty(dep.getIsRecommend())){
				dep.setIsRecommend("N");
			}
			int i = depDao.addDep(dep);
			if(i>0){
				result.put("retCode", 1);
				result.put("retMessage", "添加成功");
			}else{
				result.put("retCode", 3);
				result.put("retMessage", "添加失败");
			}
		}
		return result;
	}

	@HttpMethod
	@Override
	public List<Dep> getAkeyDep(String orgId) {
		return depDao.getAkeyDep(orgId);
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
	@HttpMethod
	@Override
	public HashMap<String, Object> getDepsByKeyName(@ParamNoNull String depTypeId,@ParamNoNull String keyWord){
		HashMap<String, Object> param = new HashMap<String, Object>();
		List<Dep> deps = depDao.getDepsByKeyName(depTypeId, keyWord);
		param.put("deps", deps);
		return param;
	}
	
	@HttpMethod
	@Override
	public List<Dep> getDepDutyByDepTypeId(@ParamNoNull String depTypeId){
		return depDao.getDepDutyByDepTypeId(depTypeId);
	}
	
	@HttpMethod
	@Override
	public List<DepKey> getDepKeyByDepId(@ParamNoNull String depId){
		return depDao.getDepKeyByDepId(depId);
	}
}
