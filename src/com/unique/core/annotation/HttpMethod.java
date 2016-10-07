package com.unique.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口权限控制
 * @author 余宁
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HttpMethod {
	///访问权限
	int[] autho() default 0;
	//调用者标示0-所有 1-Web，2-Android，3-iOS	Yes
}
