package com.webside.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.webside.seo.service.SeoConfigService;
import com.webside.util.SpringBeanUtil;

public class ContextListener implements ServletContextListener {
	public static final Logger logger = Logger.getLogger(ContextListener.class);
	private ServletContext context = null;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		this.context = servletContextEvent.getServletContext();
		// 初始化seoConfiMap初始化完成
		SeoConfigService seoConfigService = SpringBeanUtil.getBean(SeoConfigService.class);
		context.setAttribute("seoConfigMap", seoConfigService.queryList());
		logger.info("seoConfiMap初始化完成");
		logger.debug("ContextListener contextInitialized .....");
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		logger.debug("ContextListener contextDestroyed .....");
		this.context = null;
	}

	public ServletContext getContext() {
		return this.context;
	}
}