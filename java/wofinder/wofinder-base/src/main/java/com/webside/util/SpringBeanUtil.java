package com.webside.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanUtil implements ApplicationContextAware 
{
	private static ApplicationContext context = null;
	private static SpringBeanUtil springBeanUtil = null;

	public synchronized static SpringBeanUtil init() 
	{
		if (springBeanUtil == null) 
		{
			springBeanUtil = new SpringBeanUtil();
		}
		
		return springBeanUtil;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException
	{
		context = applicationContext;
	}

	public synchronized static Object getBean(String beanName) 
	{
		return context.getBean(beanName);
	}
	
	public synchronized static <T> T getBean(String name, Class<T> requiredType) throws BeansException 
	{
		return context.getBean(name, requiredType);
	}
	
	public synchronized static <T> T getBean(Class<T> requiredType) throws BeansException 
	{
		return context.getBean(requiredType);
	}
}
