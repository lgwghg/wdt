package com.webside.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.webside.common.GlobalConstant;
import com.webside.exception.ServiceException;
import com.webside.frame.annotation.Token;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.shiro.filter.ShiroUtils;

/*****
 * @date 2016-09-28 10:23:55
 * @author sunhw
 * @desc 拦截器，防止form表单数据重复提交。
 */
public class TokenInterceptor extends HandlerInterceptorAdapter 
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception 
	{
		if (handler instanceof HandlerMethod)
		{
			//获取调用的方法
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			
			//获取令牌的注解
			Token annotation = method.getAnnotation(Token.class);
			
			//判断方法是否有令牌注解
			if (annotation != null) 
			{
				//判断是否需要验证令牌
				boolean needSaveSession = annotation.save();
				
				//需要验证令牌
				if (needSaveSession)
				{
					// 将令牌存入shiro 登录用户的session
					ShiroAuthenticationManager.setSessionAttribute(GlobalConstant.USER_TOKEN, UUID.randomUUID().toString());
				}
				
				//是否删除令牌
				boolean needRemoveSession = annotation.remove();
				
				//需要删除令牌
				if (needRemoveSession)
				{
					//验证表单的令牌值 跟会话的令牌是否相等
					if (isRepeatSubmit(request))
					{
						if (ShiroUtils.isAjax(request))// ajax请求
						{
							Map<String,Object> resultMap = new HashMap<String, Object>();
							resultMap.put("status", "611");
							resultMap.put("message", "重复提交表单!");//重复提交表单!
							
							response.setHeader("session_status", "token");
							response.sendError(611, "重复提交表单!");  
							ShiroUtils.writeJson(response, resultMap);
							
							return Boolean.FALSE ;
						}
						
						throw new ServiceException("重复提交表单!");
					}
					
					//删除会话令牌值
					ShiroAuthenticationManager.delSessionAttribute(GlobalConstant.USER_TOKEN);
				}
			}
			
			return true;
		}
		else 
		{
			return super.preHandle(request, response, handler);
		}
	}

	/****
	 * 判断token 是否存在，判断是否重复提交表单
	 * @param request
	 * @return
	 */
	private boolean isRepeatSubmit(HttpServletRequest request) 
	{
		//获取会话中的令牌值
		String serverToken = (String) ShiroAuthenticationManager.getSessionAttribute(GlobalConstant.USER_TOKEN);
		
		//判断令牌是否存在
		if (serverToken == null) 
		{
			return true;
		}
		
		//获取请求中的令牌
		String clinetToken = request.getParameter(GlobalConstant.USER_TOKEN);
		if (clinetToken == null)
		{
			return true;
		}
		
		//判断令牌是否相等
		if (!serverToken.equals(clinetToken))
		{
			return true;
		}
		
		return false;
	}
}