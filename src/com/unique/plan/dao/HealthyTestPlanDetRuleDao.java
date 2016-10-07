package com.unique.plan.dao;

import java.util.List;

import com.unique.plan.po.HealthyTestPlanDetRule;
import com.unique.plan.po.TestItemRuleDet;



/**
 * <br/>
 * 创建人 azq<br/>
 * 创建时间 2015年10月17日 下午1:05:09<br/>
 * @author Administrator
 * @date 2015年10月17日
 * @description
 */
public interface HealthyTestPlanDetRuleDao {
	/**
	 * 查询用户预警
	 * 创建人:敖资权
	 * 修改人:敖资权
	 * 创建日期:2015年10月17日下午3:28:31
	 * 修改日期:2015年10月17日下午3:28:31
	 * @return
	 */
	public List<HealthyTestPlanDetRule> selectUser(String userId,String bloodglucose,String periodId);
	public List<HealthyTestPlanDetRule> selectHypertensionUser(String userId,String highTension,String lowTension,String periodId);
	/**
	 * 查询用户预警（通用版本没有USERID）
	 * 创建人:敖资权
	 * 修改人:敖资权
	 * 创建日期:2015年10月17日下午3:28:51
	 * 修改日期:2015年10月17日下午3:28:51
	 * @return
	 */
	public List<TestItemRuleDet> selectCommon(String bloodglucose,String periodId);
	public List<TestItemRuleDet> selectHypertensionCommon(String highTension,String lowTension,String periodId);
}