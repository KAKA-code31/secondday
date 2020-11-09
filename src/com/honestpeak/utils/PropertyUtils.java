/*
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 */

package com.honestpeak.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @ClassName: PropertyUtils
 * @Description: 属性工具类,获取系统目录
 * @author Jeabev
 * @date 2016年8月23日 下午5:47:02
 */
public class PropertyUtils extends PropertyPlaceholderConfigurer {

	public static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

	private static Map<String, String> propertyMap;

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		propertyMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			propertyMap.put(keyStr, value);
		}
	}

	public static String getValue(String name) {
		String value = propertyMap.get(name);
		if (StringUtils.isBlank(value)) {
			return "";
		} else {
			return value;
		}
	}

	/**
	 * 不带/
	 * @return
	 */
	public static String getRoot() {
		String rootKey = "reportcheck.root";
		String cmsRoot = System.getProperty(rootKey);
		//Enumeration<?> enu = System.getProperties().propertyNames();
		if(cmsRoot.endsWith(java.io.File.separatorChar+"")){
			cmsRoot = cmsRoot.substring(0, cmsRoot.length()-1);
		}
		logger.info(cmsRoot);
		return cmsRoot;
	}
}
