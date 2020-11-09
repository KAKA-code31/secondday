package com.honestpeak.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: LogCustom
 * @Description: 类简介自定义解析注解(用于增加日志可读性)
 * @author Jeabev
 * @date 2016年9月13日 上午11:24:19
 */
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogCustom {
	/** 操作说明 */
	String desc() default "";
}
