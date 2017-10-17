package com.webside.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @ClassName: CookieUtil
 * @Description: Cookie工具,拷贝自www.sojson.com
 * @author gaogang
 * @date 2016年7月12日 下午4:16:51
 *
 */
public class CookieUtil {
	
	private static Logger logger = Logger.getLogger(CookieUtil.class);
	
	
	public static void addCookie(HttpServletResponse response, String name,
			String value, int maxAge){
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
	    
	    //依次取得cookie中的名称、值、最大生存时间、路径、域和是否为安全协议信息
	    String cookieName = cookie.getName();
	    String cookieValue = cookie.getValue();
	    String path = cookie.getPath();
	    String domain = cookie.getDomain();
	    boolean isSecure = cookie.getSecure();
	    
        StringBuffer strBufferCookie = new StringBuffer();
        strBufferCookie.append(cookieName + "=" + cookieValue +  ";");
        
        if(maxAge >= 0){
            strBufferCookie.append("Max-Age=" + maxAge + ";");
        }
        
        if(StringUtils.isNotBlank(domain)){
        	strBufferCookie.append("domain=" + domain + ";");
        }
        
        if(StringUtils.isNotBlank(path)){
        	strBufferCookie.append("path=" + path + ";");
        }
        
        if(isSecure){
        	strBufferCookie.append("secure;HTTPOnly;");
        }else{
        	strBufferCookie.append("HTTPOnly;");
        }
        
        response.addHeader("Set-Cookie",strBufferCookie.toString());
	}
	
	public static void addCookie(HttpServletResponse response,Cookie cookie, int maxAge){
		cookie.setPath("/");
	    
	    //依次取得cookie中的名称、值、最大生存时间、路径、域和是否为安全协议信息
	    String cookieName = cookie.getName();
	    String cookieValue = cookie.getValue();
	    String path = cookie.getPath();
	    String domain = cookie.getDomain();
	    boolean isSecure = cookie.getSecure();
	    
        StringBuffer strBufferCookie = new StringBuffer();
        strBufferCookie.append(cookieName + "=" + cookieValue +  ";");
        
        if(maxAge >= 0){
            strBufferCookie.append("Max-Age=" + maxAge + ";");
        }
        
        if(StringUtils.isNotBlank(domain)){
        	strBufferCookie.append("domain=" + domain + ";");
        }
        
        if(StringUtils.isNotBlank(path)){
        	strBufferCookie.append("path=" + path + ";");
        }
        
    	if(isSecure){
        	strBufferCookie.append("secure;HTTPOnly;");
        }
        
        response.addHeader("Set-Cookie",strBufferCookie.toString());
	}

	public static boolean deleteAllCookie(HttpServletRequest request,
			HttpServletResponse response){
		boolean bool = false;
		Cookie[] cookies = request.getCookies();
		if(null == cookies || cookies.length == 0) return bool;
		try {
			for (int i = 0; i < cookies.length; i++) {
				String cookieName = cookies[i].getName();
				StringBuffer strBufferCookie = new StringBuffer();
		        strBufferCookie.append(cookieName + "=;");
		        strBufferCookie.append("Max-Age=" + 0 + ";");
		        strBufferCookie.append("path=" + "/" + ";");
		        strBufferCookie.append("HTTPOnly;");
		        response.addHeader("Set-Cookie",strBufferCookie.toString());
				bool = true;
			}
		} catch (Exception ex) {
			logger.error("清空Cookies发生异常！", ex);
		}
		return bool;
	}

	/**
	 * 清空Cookie操作 clearCookie
	 * 
	 * @param request
	 * @param response
	 * @return boolean
	 * @author JIANG FEI Jun 19, 2014 10:12:17 AM
	 */
	public static boolean clearCookie(HttpServletRequest request,
			HttpServletResponse response, String name) {
		boolean bool = false;
		try {
			String cookieName = name;
			StringBuffer strBufferCookie = new StringBuffer();
	        strBufferCookie.append(cookieName + "=;");
	        strBufferCookie.append("Max-Age=" + 0 + ";");
	        strBufferCookie.append("path=" + "/" + ";");
	        strBufferCookie.append("HTTPOnly;");
	        response.addHeader("Set-Cookie",strBufferCookie.toString());
	        bool = true;
		} catch (Exception ex) {
			logger.error("清空Cookies发生异常！", ex);
		}
		return bool;
	}

	/**
	 * 清空Cookie操作 clearCookie
	 * 
	 * @param request
	 * @param response
	 * @return boolean
	 * @author JIANG FEI Jun 19, 2014 10:12:17 AM
	 */
	public static boolean clearCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String domain) {
		boolean bool = false;
		Cookie[] cookies = request.getCookies();
		if(null == cookies || cookies.length == 0) return bool;
		try {
			String cookieName = name;
			StringBuffer strBufferCookie = new StringBuffer();
	        strBufferCookie.append(cookieName + "=;");
	        strBufferCookie.append("Max-Age=" + 0 + ";");
	        strBufferCookie.append("domain=" + domain + ";");
	        strBufferCookie.append("path=" + "/" + ";");
	        strBufferCookie.append("HTTPOnly;");
	        response.addHeader("Set-Cookie",strBufferCookie.toString());
	        bool = true;
		} catch (Exception ex) {
			logger.error("清空Cookies发生异常！", ex);
		}
		return bool;
	}

	/**
	 * 获取指定cookies的值 findCookieByName
	 * 
	 * @param request
	 * @param name
	 * @return String
	 */
	public static String findCookieByName(HttpServletRequest request,
			String name) {
		Cookie[] cookies = request.getCookies();
		if(null == cookies || cookies.length == 0) return null;
		String string = null;
		try {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				String cname = cookie.getName();
				if (!StringUtils.isBlank(cname) && cname.equals(name)) {
					string = cookie.getValue();
				}

			}
		} catch (Exception ex) {
			logger.error("获取Cookies发生异常！", ex);
		}
		return string;
	}

}
