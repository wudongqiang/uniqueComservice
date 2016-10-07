package com.unique.org.dao;

import java.util.List;
import java.util.Map;

import com.unique.org.po.Dep;
import com.unique.org.po.DepKey;

public interface DepDao {
	List<Dep> get1stLevel(String[] orgIds);
	List<Dep> get2ndLevel(Map<String, String> params);
	
	public Dep getDepInfo(String depId);
	
	public Dep getDepInfoByCode(String depCode);
	
	public int addDep(Dep dep);
	
	/**
	 * 获取医院的重点科室 --- 最多8个 
	 * <br/>创建人 wdq
	 * <br/>创建时间 下午2:30:41
	 * @param orgId
	 * @return
	 */
	List<Dep> getAkeyDep(String orgId);
	
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
	public List<Dep> getDepsByKeyName(String depTypeId,String keyWord);

	/** 
	 * 通过类型查询就诊部门排班
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月10日 下午3:44:47
	 * @param depTypeId 一级科室id 
	 * @return
	 */
	List<Dep> getDepDutyByDepTypeId(String depTypeId);
	
	/**
	 * 获取关键字 
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月11日 上午10:29:18
	 * @return
	 */
	List<DepKey> getDepKeyByDepId(String depId);
	
	/**
	 * 通过depName查询科室<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月30日 上午10:40:42<br/>
	 * @param depName
	 * @param orgId
	 * @return
	 */
	Dep getDepByDepName(String depName,String orgId);
	
	List<Dep> getDutyDep(Map<String, String> params);
}
