package com.unique.org.service;

import java.util.HashMap;
import java.util.List;

import com.sun.istack.internal.NotNull;
import com.unique.org.po.Dep;
import com.unique.org.po.DepKey;

public interface DepService {
	List<Dep> get1stLevel(String orgId);
	List<Dep> get2ndLevel(String orgId, String parentId);
	
	/**
	 * 获取有排班的科室
	 * @param orgId
	 * @param parentId
	 * @return
	 */
	List<Dep> getDutyDep(String orgId, String parentId);
	
	public Dep getDepInfo(String depId);
	public HashMap<String, Object> addDep(Dep dep, String parentCode);
	
	/**
	 * 获取医院的重点科室 --- 最多8个 
	 * <br/>创建人 wdq 
	 * <br/>创建时间 下午2:20:43
	 * @param orgId
	 * @return
	 */
	public List<Dep> getAkeyDep(String orgId);
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
	public HashMap<String, Object> getDepsByKeyName(@NotNull String depTypeId,@NotNull String keyWord);
	
	/** 
	 * 通过类型查询就诊部门排班
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月10日 下午3:44:47
	 * @param depTypeId 上级科室id（一级科室id）
	 * @return
	 */
	List<Dep> getDepDutyByDepTypeId(String depTypeId);
	/**
	 * 获取关键字
	 * <br/>创建人 wdq
	 * <br/>创建时间 2015年9月11日 上午10:27:54
	 * @return
	 */
	public List<DepKey> getDepKeyByDepId(String depId);
}
