package com.unique.plan.dao;

import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

/**
 * 
 * <br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年10月17日 下午1:05:54<br/>
 * @author Administrator
 * @date 2015年10月17日
 * @description
 */
@Repository("healthyTestPlanDetDao")
public class HealthyTestPlanDetDaoImpl implements HealthyTestPlanDetDao{
	@Resource
	private SqlSessionTemplate sqlSession;

	@Override
	public String getTestItemValue(Map<String,Object> params){
		return sqlSession.selectOne("getTestItemValue", params);
	}
	
}
