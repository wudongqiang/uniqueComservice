package com.unique.org.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.unique.core.util.FileUtil;
import com.unique.org.po.OrgFloor;
import com.unique.reg.po.Org;


@Repository("orgDao")
public class OrgDaoImpl implements OrgDao{
	///图片服务器
	private static String picServerOut = FileUtil.readProperties("comservice.properties", "picServerOut");
	@Resource
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<Org> getAllOrgs(String useCode) {
		return sqlSession.selectList("org_getAllOrg",useCode);
	}

	public Org getOrgInfo(String orgId) {
		Org o = sqlSession.selectOne("getOrgInfo",orgId);
		List<OrgFloor> floors = getOrgFloorByOrgId(orgId);
		if(floors!=null && floors.size() > 0){
			for(OrgFloor f : floors){
				String spliter = "";
				if(!picServerOut.endsWith("/") && !f.getFloorPic().startsWith("/")){
					spliter = "/";
				}
				f.setFloorPic(picServerOut +spliter + f.getFloorPic());
			}
			o.setFloors(floors);
		}
		return o;
	}
	
	public Org getOrgByCode(String code){
		return sqlSession.selectOne("getOrgByCode",code);
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
	public List<OrgFloor> getOrgFloorByOrgId(String orgId){
		return sqlSession.selectList("getOrgFloorByOrgId",orgId);
	}
	
	@Override
	public String getOrgMapPositionByOrgId(String orgId){
		return sqlSession.selectOne("getOrgMapPositionByOrgId",orgId);
	}

	@Override
	public List<Org> getOrgByOrgGroupCode(String orgGroupCode) {
		return sqlSession.selectList("getOrgByOrgGroupCode", orgGroupCode);
	}
}
