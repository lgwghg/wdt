package com.webside.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.webside.common.GlobalConstant;
import com.webside.dict.model.DictEntity;
import com.webside.rights.model.RoleEntity;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.model.UserEntity;

public class BackendInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 无后台角色的用户不能进入后台
		UserEntity user = ShiroAuthenticationManager.getUserEntity();
		if (user == null || user.getRole() == null || user.getRole().getType() == null || !GlobalConstant.ROLE_TYPE_0.equals(user.getRole().getType().getValue()) || StringUtils.isBlank(user.getRole().getId())) {
			response.sendRedirect("/index");
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
