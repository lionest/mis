package com.htlab.mis.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
/**
 * 获取bean辅助类
 *
 */
@Service
public class ApplicationContextHelper implements ApplicationContextAware {
	private static ApplicationContext ctx;

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		ApplicationContextHelper.ctx = ctx;
	}

	/**
	 * 通过bean name获取bean
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBean(Class<?> requiredType) {
		return ctx.getBean(requiredType);
	}
	
}
