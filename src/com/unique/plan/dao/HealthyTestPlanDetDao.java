package com.unique.plan.dao;



import java.util.Map;
/**
 * <br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年10月17日 下午1:05:09<br/>
 * @author Administrator
 * @date 2015年10月17日
 * @description
 */
public interface HealthyTestPlanDetDao {

	/**
	 * 获取测试项的值<br/>
	 * 创建人 wdq<br/>
	 * 创建时间 2015年10月20日 上午9:27:46<br/>
	 * @param params
	 * @return
	 */
	public String getTestItemValue(Map<String, Object> params);
	
}