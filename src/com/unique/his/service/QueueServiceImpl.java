/**
 * 2015年9月14日
 */
package com.unique.his.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.util.RemotUtils;
import com.unique.org.dao.OrgDao;
import com.unique.reg.po.Org;

/**
 * 排班查询业务实现类<br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年9月14日 上午10:02:51<br/>
 * @author Administrator
 * @date 2015年9月14日
 * @description 
 */
@Service("queueService")
public class QueueServiceImpl implements QueueService{
	@Resource
	private OrgDao orgDao;
	
	@Override
	@HttpMethod
	public String getQueueByUserList(@NotNull String orgId,@NotNull String cardNo,@NotNull String userName, Long startRow, Long rows) {
		if(startRow==null) startRow= 0L;
		if(rows==null) rows = 10L;
		Org org = orgDao.getOrgInfo(orgId);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("searchType", "4");
		params.put("cardNo", cardNo);
		params.put("patientName", userName);
		if(startRow!=null&&rows!=null){
			params.put("startRow", startRow);
			params.put("rows", rows);
		}
		
		return RemotUtils.getRemotData(org.getHospitalUrl(), QUEUE_BY_USER_LIST, params);
	}

	@HttpMethod
	@Override
	public String getQueueByOrgList(@NotNull String orgId, Long startRow, Long rows) {
		if(startRow==null) startRow= 0L;
		if(rows==null) rows = 10L;
		Org org = orgDao.getOrgInfo(orgId);
		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("orgCode", org.getOrgCode());
		params.put("orgCode", orgId);
//		params.put("depCode", "");
//		params.put("doctor", "");
		if(startRow!=null&&rows!=null){
			params.put("startRow", startRow);
			params.put("rows", rows);
		}
		
		return RemotUtils.getRemotData(org.getHospitalUrl(), QUEUE_BY_ORG_LIST, params);
	}
}
