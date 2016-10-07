package com.unique.guide.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.util.StringUtil;
import com.unique.guide.dao.GuideDao;
import com.unique.guide.po.JbBodyPart;
import com.unique.guide.po.JbIllness;
import com.unique.guide.po.JbSymptom;
import com.unique.guide.po.StandardDep;
import com.unique.guide.po.SyMainFollow;
import com.unique.guide.po.SyRelationResSts;
import com.unique.notes.dao.NotesDao;
import com.unique.notes.po.McNotes;
import com.unique.org.dao.DepDao;
import com.unique.org.po.Dep;
import com.unique.org.service.DepService;
import com.unique.org.service.OrgService;
import com.unique.reg.po.Org;
import com.unique.user.dao.UserDao;
import com.unique.user.po.AmsUser;
import com.unique.user.po.McFb;

@Service("guideService")
public class GuideServiceImpl implements GuideService{
	@Resource
	private GuideDao guideDao;
	@Resource
	private UserDao userDao;	
	@Resource
	private NotesDao notesDao;	
	@Resource
	private DepDao depDao;	
	@Resource
	private OrgService orgService;	
	 
	/**
	 * 获取部位列表
	 * @return
	 */
	@HttpMethod
	public List<JbBodyPart> getBodys(){
		return guideDao.getBodys();
	}
	
	/**
	 * 获取症状列表
	 * @param sex
	 * @param bodyId
	 * @return
	 */
	@HttpMethod
	public List<JbSymptom> getSymptom(String sex,String bodyId,String syId){
		return guideDao.getSymptom(sex, bodyId,syId);
	}
	
	/**
	 * 获取疾病集合
	 */
	@HttpMethod
	public List<JbIllness> getIllnessesByRelation(String sex,String zsyId,String[] syIds,String age){
		Long[] syIdsLong=null;
		StringBuffer syIdStr =null;
		if(syIds!=null && syIds.length > 0){
			syIdsLong = new Long[syIds.length + 1];
			int i=0;
			for(String syId : syIds){
				syIdsLong[i++] = Long.parseLong(syId);
			}
			syIdsLong[i++] = Long.parseLong(zsyId);
			//排序再组合成字符串
			Arrays.sort(syIdsLong);
			syIdStr = new StringBuffer();
			for(Long syId : syIdsLong){
				syIdStr.append(syId + ",");
			}
		}else{
			syIdStr = new StringBuffer(zsyId + ",");
		}

		return guideDao.getIllnessesByRelation(zsyId, syIdStr==null?null:syIdStr.toString(), zsyId, sex, age);
	}
	
	/**
	 * 针对口腔改造 获取疾病集合<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月9日 下午3:28:14<br/>
	 * @param syIds	伴随症状ID数组
	 * @param zsyId	主症状ID
	 * @param sex	性别
	 * @param age	年龄
	 * @return
	 */
	@HttpMethod
	public Map<String,Object> getIllnessesByRelationHis(String sex,String zsyId,String[] syIds,String age){
		Map<String,Object> result = new HashMap<String,Object>();
		
		//1. 获取疾病
		List<JbIllness> jbliIllneses = this.getIllnessesByRelation(sex, zsyId, syIds, age);
//				guideDao.getIllnessesByRelation(zsyId, syIdStr==null?null:syIdStr.toString(), zsyId, sex, age);
		//2. 知识问答XX  mc_noties  查询优先级  疾病关键词>主症状>伴随症状，推荐5条
		Set<String> queryStr = new HashSet<String>(); //条件组装 
		String[] queryIllIds = null; //条件组装 
		
//		List<McFb> mcFbs = new ArrayList<McFb>();
		List<McFb> mcFbsTemp = new ArrayList<McFb>();
		Set<McFb> mcFbs = new HashSet<McFb>(); //去除重复
		
		String[] arr =  null;
		if(!jbliIllneses.isEmpty()){
			queryIllIds = new String[jbliIllneses.size()];
			int i=0;
			for(JbIllness jb:jbliIllneses){
				 queryStr.add(jb.getIllName());
				 queryIllIds[i++]=jb.getIllId().toString();
				 
				 //ILL_TXT 为null添加状态
				jb.setIsShow(StringUtil.isEmpty(jb.getIllTxt())?0:1);
			 }
		}
		//查询第一次 疾病关键词
		arr = (String[])queryStr.toArray(new String[queryStr.size()]);
		if(arr!=null && arr.length>0){
			mcFbsTemp = notesDao.getMcFBLikeTitles(arr);//最多5条
			mcFbs.addAll(mcFbsTemp);
		}
		
		int mcFbsSize = mcFbs.size();
		
		if(mcFbsSize<5 && !StringUtil.isEmpty(zsyId)){
			//主症状 查询名称
			List<SyMainFollow> list = guideDao.getSyMainFollowName(new String[]{zsyId}, null);
			queryStr.clear();//清除之前的数据
			for(int i=0,size=list.size();i<size&&!list.isEmpty();i++){
				queryStr.add(list.get(i).getZsyName());//主症状名称
			}
			//查询第二次 主症状
			arr = (String[])queryStr.toArray(new String[queryStr.size()]);
			if(arr!=null && arr.length>0){
				mcFbsTemp = notesDao.getMcFBLikeTitles(arr);//最多5条
				if(mcFbsTemp.size()>0){
					mcFbs.addAll(mcFbsTemp);//加入集合
				}
			}
		}
		
		mcFbsSize = mcFbs.size();
		
		if(mcFbsSize<5 && null!=syIds && syIds.length > 0){
			//伴随症状  查询名称
			List<SyMainFollow> list = guideDao.getSyMainFollowName(null,syIds);
			queryStr.clear();//清除之前的数据
			for(int i=0,size=list.size();i<size&&!list.isEmpty();i++){
				queryStr.add(list.get(i).getFsyName());//伴随症状名称
			}
			//查询第三次 伴随症状
			arr = (String[])queryStr.toArray(new String[queryStr.size()]);
			if(arr!=null && arr.length>0){
				mcFbsTemp = notesDao.getMcFBLikeTitles(arr);//最多5条
				if(mcFbsTemp.size()>0){
					mcFbs.addAll(mcFbsTemp);//加入集合
				}
			}
		}
		mcFbsSize = mcFbs.size();
		//去重还原为List
		mcFbsTemp.clear();
		mcFbsTemp.addAll(mcFbs);
		if(mcFbsSize>5){
			mcFbsTemp = mcFbsTemp.subList(0, 5); //只取前五条
		}
		 
		//获取医院 得到门诊
		List<Org> orgs = orgService.getAllOrgs(null);
		String []oIds = new String[orgs.size()];
		int i=0;
		for(Org org:orgs){
			oIds[i++] = org.getOrgId().toString();
		}
		//获取所有的1集部门
		List<Dep> deps = null;
		deps = depDao.get1stLevel(oIds);
		for(Dep dep:deps){
			result.put(dep.getDepId().toString(), dep.getDepName());
		}
			
		//3. 获取推荐科室  （若无则推荐冉家坝医院和上清寺医院的“初检分诊中心”）
		deps.clear();
		if(queryIllIds!=null){
			deps = guideDao.getRecDepByIll(queryIllIds);
		}
		String depName = null;
		for(Dep dep:deps){
			if(dep.getParentId()!=null){
				depName = (String)result.get(dep.getParentId().toString()); 
				if(!StringUtil.isEmpty(depName)&&depName.indexOf("VIP")>=0){
					dep.setDepTypeId(new BigDecimal(-1));	
				}
			}
		}
		
		result.clear();
		result.put("jbliIllneses", jbliIllneses);
		result.put("recommendDeps", deps);//推荐科室
		result.put("fbs", mcFbsTemp);//知识问答
		
		return result;
	}
	
	/**
	 * 获取疾病详情
	 * @param illId
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getIllness(@NotNull String illId){
		HashMap<String, Object> result = new HashMap<String, Object>();
		JbIllness illness = guideDao.getIllnessById(illId);
		result.put("ill", illness);
		List<Org> orgs = guideDao.getDepByIll(new String[]{illId});
		result.put("orgs", orgs);
		List<StandardDep> deps = guideDao.getStandardDeps(illId,null);
		result.put("standardDeps", deps);
		return result;
	}
	
	/**
	 * 获取疾病详情 - 针对口腔
	 * @param illId
	 * @return
	 */
	@HttpMethod
	public HashMap<String, Object> getIllnessHis(@NotNull String illId){
		HashMap<String, Object> result = new HashMap<String, Object>();
		 
		//获取医院 得到门诊
		List<Org> orgs = orgService.getAllOrgs(null);
		String []oIds = new String[orgs.size()];
		int i=0;
		for(Org org:orgs){
			oIds[i++] = org.getOrgId().toString();
		}
		//获取所有的1集部门
		List<Dep> deps = null;
		deps = depDao.get1stLevel(oIds);
		for(Dep dep:deps){
			result.put(dep.getDepId().toString(), dep.getDepName());
		}
		deps.clear();
		//推荐科室
		deps = guideDao.getRecDepByIll(new String[]{illId});
		String depName = null;
		for(Dep dep:deps){
			if(dep.getParentId()!=null){
				depName = (String)result.get(dep.getParentId().toString()); 
				if(!StringUtil.isEmpty(depName)&&depName.indexOf("VIP")>=0){
					dep.setDepTypeId(new BigDecimal(-1));	
				}
			}
		}
		
		// 疾病详情
		JbIllness illness = guideDao.getIllnessById(illId);
		//科普文章
		//最多5条
		List<McNotes> mcNotes = notesDao.getMcNotesLikeTitles(illness.getIllName());
		
		result.clear();
		result.put("ill", illness);
		result.put("recommendDeps", deps);//推荐科室
		result.put("notes", mcNotes);//科普文章
		
		return result;
	}
	
	/**
	 * 获取标准科室列表
	 * @return
	 */
	@HttpMethod
	public List<StandardDep> getStandards(String illId,String illDep){
		return guideDao.getStandardDeps(illId,illDep);
	}
	
	/**
	 * 根据主症状查伴随症状
	 * @param zsyId
	 * @param bpId
	 * @param sex
	 * @return
	 */
	@HttpMethod
	public List<JbSymptom> getSymptomsByBs(String zsyId,String bpId,String sex){
		return guideDao.getSymptomsByBs(zsyId, bpId, sex);
	}
	
	/**
	 * 根据标准科室ID获取疾病
	 * @param standId
	 * @return
	 */
	@HttpMethod
	public List<JbIllness> getIllnessByStandard(String standId){
		return guideDao.getIllnessByStandard(standId);
	}
	

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
	@HttpMethod
	@Transactional
	public void addUsefulAndError(String userId,String sex,@NotNull String zsyId,String[] syIds,String age,String isGood,String reason,String deviceToken){
		Long[] syIdsLong=null;
		StringBuffer syIdStr =null;
		if(syIds!=null && syIds.length > 0){
			syIdsLong = new Long[syIds.length + 1];
			int i=0;
			for(String syId : syIds){
				syIdsLong[i++] = Long.parseLong(syId);
			}
			syIdsLong[i++] = Long.parseLong(zsyId);
			//排序再组合成字符串
			Arrays.sort(syIdsLong);
			syIdStr = new StringBuffer();
			for(Long syId : syIdsLong){
				syIdStr.append(syId + ",");
			}
		}else{
			syIdStr = new StringBuffer(zsyId + ",");
		}
		List<String> ids = guideDao.getSyRelations(zsyId, syIdStr==null?null:syIdStr.toString(), zsyId, sex, age);
		AmsUser user =  userDao.getUserById(userId);
		
		for(String relationId : ids){
			if(StringUtil.isEmpty(relationId))continue;
			SyRelationResSts sts = new SyRelationResSts();
			sts.setBadReason(reason);
			sts.setDeviceToken(deviceToken);
			sts.setIsGood(isGood);
			sts.setOpTime(new Date());
			sts.setRelationId(new BigDecimal(relationId));
			sts.setUserId(user.getUserId());
			sts.setUserName(user.getUserName());
			
			guideDao.addSyRelation(sts);
		}
	}

	@HttpMethod
	@Override
	public StandardDep getStandardDepById(String standardId) {
		return guideDao.getStandardDepById(standardId);
	}
	
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
	public Map<String,Object> getIllnessByPage(Long startRow, Long rows) {
		if(startRow==null) startRow = 0L;
		if(rows==null) rows = 20L;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", guideDao.getIllnessByPage(startRow, rows));
		map.put("count",guideDao.getIllnessByPageCount());
		return map;
	}
}
