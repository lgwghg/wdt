package com.webside.shiro;

import java.util.ArrayList;
import java.util.List;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.webside.rights.model.UserSessionEntity;
import com.webside.rights.service.UserSessionService;
import com.webside.user.model.UserEntity;
import com.webside.util.SpringContextUtil;
import com.webside.util.StringUtils;

/**
 * 
 * @ClassName: ShiroManager
 * @Description: shiro 认证信息操作工具类
 * @author gaogang
 * @date 2016年7月12日 下午4:31:10
 *
 */
public class ShiroAuthenticationManager {
	/**
	 * 用户权限管理
	 */
	public static final MyDBRealm myDBRealm = SpringContextUtil.getBean("myDBRealm", MyDBRealm.class);
	/**
	 * 用户session管理
	 */
	public static final UserSessionService userSessionService = SpringContextUtil.getBean("userSessionService", UserSessionService.class);

	/**
	 * 获取shiro的session
	 * 
	 * @return
	 */
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	/**
	 * 获取shiro Subject
	 * 
	 * @return
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取用户
	 * 
	 * @return
	 */
	public static UserEntity getUserEntity() {
		return (UserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * 获取用户id
	 * 
	 * @return
	 */
	public static String getUserId() {
		return getUserEntity() == null ? null : getUserEntity().getId();
	}
	
	/**
	 * 获取用户邮箱
	 * 
	 * @return
	 */
	public static String getUserEmail() {
		return getUserEntity() == null ? null : getUserEntity().getEmail();
	}
	/**
	 * 获取用户手机号
	 * @return
	 */
	public static String getUserMobile() {
		return getUserEntity() == null ? null : getUserEntity().getMobile();
	}
	
	/**
	 * 获取用户昵称
	 * @return
	 */
	public static String getUserNickName() {
		return getUserEntity() == null ? null : getUserEntity().getNickName();
	}

	/**
	 * 把值放入到当前登录用户的Session里
	 * 
	 * @param key
	 * @param value
	 */
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	/**
	 * 从当前登录用户的Session里取值
	 * 
	 * @param key
	 * @return
	 */
	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	/**
	 * 从当前登录用户的Session里删除属性
	 * 
	 * @param key
	 * @return
	 */
	public static Object delSessionAttribute(Object key) {
		return getSession().removeAttribute(key);
	}
	
	/**
	 * 判断是否登录
	 * 
	 * @return
	 */
	public static boolean isLogin() {
		return null != SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * 退出登录
	 */
	public static void logout() {
		SecurityUtils.getSubject().logout();
	}

	/**
	 * 获取验证码，获取后删除
	 * 
	 * @return
	 */
	public static String getKaptcha(String key) {
		String kaptcha = StringUtils.toString(getSessionAttribute(key));
		getSession().removeAttribute(key);
		return kaptcha;
	}
	
	
	/**
	 * 
	 * @Description	清空当前用户权限信息
	 * </br>目的：为了在判断权限的时候，系统会再次调用 <code>doGetAuthorizationInfo(...)  </code>方法加载权限信息。
	 *
	 * @author wjggwm
	 * @data 2017年1月5日 下午6:09:55
	 */
	public static void clearUserAuth(){
		myDBRealm.clearCachedAuthorizationInfo();
	}
	
	
	/**
	 * 
	 * @Description 根据UserIds清空权限信息
	 * @param userIds	用户ids
	 *
	 * @author wjggwm
	 * @data 2017年1月5日 下午6:07:45
	 */
	public static void clearUserAuthByUserId(String...userIds){
		
		if(null == userIds || userIds.length == 0)	return ;
		List<SimplePrincipalCollection> result = userSessionService.getSimplePrincipalCollectionByUserId(userIds);
		
		for (SimplePrincipalCollection simplePrincipalCollection : result) {
			myDBRealm.clearCachedAuthorizationInfo(simplePrincipalCollection);
		}
	}


	/**
	 * 
	 * @Description 根据UserIds清空权限信息
	 * @param userIds	用户ids
	 *
	 * @author wjggwm
	 * @data 2017年1月5日 下午6:08:01
	 */
	public static void clearUserAuthByUserId(List<String> userIds) {
		if(null == userIds || userIds.size() == 0){
			return ;
		}
		clearUserAuthByUserId(userIds.toArray(new String[0]));
	}
	
	/**
	 * 
	 * @Description 清空所有用户权限信息
	 *
	 * @author wjggwm
	 * @data 2017年1月5日 下午6:08:01
	 */
	public static void clearAllUserAuth() {
		List<UserSessionEntity> list = userSessionService.getAllUser();
		List<String> userIds = new ArrayList<String>();
		/*list.forEach(user -> {
			userIds.add(user.getId());
		});*/
		for (UserSessionEntity user : list) {
			userIds.add(user.getId());
		}
		clearUserAuthByUserId(userIds.toArray(new String[0]));
	}
	

}
