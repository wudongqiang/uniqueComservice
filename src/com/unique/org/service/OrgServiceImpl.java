package com.unique.org.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.util.StringUtil;
import com.unique.org.dao.OrgDao;
import com.unique.org.po.OrgFloor;
import com.unique.reg.po.Org;

@Service("orgService")
public class OrgServiceImpl implements OrgService{

	@Resource
	private OrgDao orgDao;
	
	/**
	 * 医院列表接口,默认查询首页图片
	 */
	@Override
	@HttpMethod
	public List<Org> getAllOrgs(String useCode) {
		return orgDao.getAllOrgs(StringUtil.isEmpty(useCode)?"sytp":useCode);
	}

	@HttpMethod
	public Org getOrgInfo(@NotNull String orgId) {
		return orgDao.getOrgInfo(orgId);
	}
	
	/**
	 * 获得医院的楼层导航
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月7日下午3:40:03
	 * 修改日期:2015年9月7日下午3:40:03
	 * @param orgId
	 * @return
	 */
	@HttpMethod
	public List<OrgFloor> getOrgFloorByOrgId(@NotNull String orgId){
		return orgDao.getOrgFloorByOrgId(orgId);
	}
	
	@HttpMethod
	public Map<String,Object> getOrgMapPositionByOrgId(@NotNull String orgId){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("position", orgDao.getOrgMapPositionByOrgId(orgId));
		return result;
	}
	
	@HttpMethod
	@Override
	public List<Org> getOrgByOrgGroupCode(@NotNull String orgGroupCode) {
		return orgDao.getOrgByOrgGroupCode(orgGroupCode);
	}
}
