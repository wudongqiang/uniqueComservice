package com.unique.his.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sun.istack.internal.NotNull;
import com.unique.alipay.util.UtilDate;
import com.unique.core.annotation.HttpMethod;
import com.unique.core.util.RemotUtils;
import com.unique.org.dao.OrgDao;
import com.unique.reg.po.Org;

/**
 *  报告查询业务实现类
 * <br/>创建人 wdq
 * <br/>创建时间 2015年9月8日 下午2:46:53
 * @author Administrator
 * @date 2015年9月8日
 * @description
 */
@Service("reportService")
public class ReportServiceImpl implements ReportService {
	@Resource
	private OrgDao orgDao;
	@HttpMethod
	@Override
	public String getReportList(@NotNull String orgId,@NotNull String cardNo, @NotNull String userName,@NotNull String type, Long startRow, Long rows) {
		if(null == startRow) startRow = 0L;
		if(null == rows) rows = 10L;
		Org org = orgDao.getOrgInfo(orgId);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("searchType", "4");
		params.put("cardNo", cardNo);
		params.put("patientName", userName);
		if("1".equals(type)){
			params.put("reportTime", UtilDate.getMonth());
		}
		if(startRow!=null&&rows!=null){
			params.put("startRow", startRow);
			params.put("rows", rows);
		}
		return RemotUtils.getRemotData(org.getHospitalUrl(), REPORT_LIST, params);
	}

 
	@HttpMethod
	@Override
	public String getPacsReportByApplyNo(@NotNull String orgId,@NotNull String applyNo,Long startRow,Long rows) {
		if(null == startRow) startRow = 0L;
		if(null == rows) rows = 10L;
		Org org = orgDao.getOrgInfo(orgId);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("applyNo", applyNo);
		if(startRow!=null&&rows!=null){
			params.put("startRow", startRow);
			params.put("rows", rows);
		}
		return RemotUtils.getRemotData(org.getHospitalUrl(), PACS_REPORT_BY_APPLY_NO, params);
	}

	@HttpMethod
	@Override
	public String getLisReportByApplyNo(@NotNull String orgId,@NotNull String applyNo,Long startRow,Long rows) {
		if(null == startRow) startRow = 0L;
		if(null == rows) rows = 10L;
		Org org = orgDao.getOrgInfo(orgId);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("applyNo", applyNo);
		if(startRow!=null&&rows!=null){
			params.put("startRow", startRow);
			params.put("rows", rows);
		}
		
		return RemotUtils.getRemotData(org.getHospitalUrl(), LIS_REPORT_BY_APPLY_NO, params);
	}

}
