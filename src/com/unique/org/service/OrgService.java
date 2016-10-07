package com.unique.org.service;

import java.util.List;
import java.util.Map;

import com.sun.istack.internal.NotNull;
import com.unique.org.po.OrgFloor;
import com.unique.reg.po.Org;

public interface OrgService {
	List<Org> getAllOrgs(String useCode);
	
	public Org getOrgInfo(String orgId);
	
	/**
	 * 获得医院的楼层导航
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年9月7日下午3:40:03
	 * 修改日期:2015年9月7日下午3:40:03
	 * @param orgId
	 * @return
	 */
	public List<OrgFloor> getOrgFloorByOrgId(@NotNull String orgId);
	
	/**
	 * 
	 * 获得医院地图坐标<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年9月22日 上午10:02:22<br/>
	 * @param orgId
	 * @return
	 */
	public Map<String,Object> getOrgMapPositionByOrgId(@NotNull String orgId);
	
	/**
	 * 通过机构分组代码获取其下所有的机构<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年11月2日 上午10:51:08<br/>
	 * @param orgGroupCode 机构分组代码
	 * @return
	 */
	public List<Org> getOrgByOrgGroupCode(@NotNull String orgGroupCode);
}
