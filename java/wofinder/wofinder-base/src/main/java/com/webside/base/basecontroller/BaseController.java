package com.webside.base.basecontroller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * 
 * @ClassName: BaseController
 * @Description: controller基类,目前功能比较简单
 * @author gaogang
 * @date 2016年7月12日 下午3:02:14
 *
 */
public abstract class BaseController {
	
	public Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	protected HttpSession session;
	
	protected String staticPrefix = "/resources/wofinder";

	public String getStaticPrefix() {
		return staticPrefix;
	}

	public void setStaticPrefix(String staticPrefix) {
		this.staticPrefix = staticPrefix;
	}
}