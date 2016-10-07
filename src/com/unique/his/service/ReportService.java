package com.unique.his.service;



/**
 * 报告查询业务接口类
 * <br/>创建人 wdq
 * <br/>创建时间 2015年9月8日 下午2:47:30
 * @author Administrator
 * @date 2015年9月8日
 * @description
 */
public interface ReportService {
	
	
	/**报告单查询*/
	public final static String REPORT_LIST = "module=reportService&method=getReportList";

	/**患者检验报告详情查询接口*/
	public final static String LIS_REPORT_BY_APPLY_NO = "module=reportService&method=getLisReportByApplyNo";
	
	/**患者检查报告详情查询接口*/
	public final static String PACS_REPORT_BY_APPLY_NO = "module=reportService&method=getPacsReportByApplyNo";
	
	/**
	 * 报告单查询
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月8日 下午2:58:20
	 * @param cardId  就诊id
	 * @param userName	用户姓名
	 * @param type	类型，1表示当月，2表示所有
	 * @param startRow  开始行 默认0
	 * @param rows	行数 默认5
	 */
	public String getReportList(String orgId,String cardNo, String userName,String type, Long startRow,Long rows);
	
	
	/**
	 * 患者检查报告详情查询接口
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月8日 下午3:17:29
	 * @param applyNo 单据号
	 */
	public String getPacsReportByApplyNo(String orgId,String applyNo,Long startRow,Long rows);
	
	/**
	 * 患者检验报告详情查询接口
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月8日 下午3:17:29
	 * @param applyNo 单据号
	 */
	public String getLisReportByApplyNo(String orgId,String applyNo,Long startRow,Long rows);
	
	
}
