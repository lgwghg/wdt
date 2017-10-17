<%@page import="com.webside.user.service.UserCacheService"%>
<%@page import="com.webside.config.service.ConfigService"%>
<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@page import="com.webside.util.CookieUtil"%>
<%@page import="com.webside.common.GlobalConstant"%>
<%@page import="com.webside.util.StringUtils"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.webside.util.SpringBeanUtil"%>
<%@page import="com.webside.shiro.ShiroAuthenticationManager"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setAttribute("path", request.getContextPath());
	request.setAttribute("ctx", request.getContextPath());
	request.setAttribute("staticPrefix",  request.getContextPath()+"/resources/wofinder");
	request.setAttribute("user",  ShiroAuthenticationManager.getUserEntity());
	if (ShiroAuthenticationManager.getUserId() != null && ShiroAuthenticationManager.getUserId() != "") {
		request.setAttribute("userId",  ShiroAuthenticationManager.getUserId());
		UserCacheService userCacheService = SpringBeanUtil.getBean(UserCacheService.class);
		request.setAttribute("user",  userCacheService.getUserEntityByUserId(ShiroAuthenticationManager.getUserId()));
	}
	
	//获取当前域名地址
	StringBuffer url = request.getRequestURL();
	String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
	
	if(StringUtils.isNotEmpty(tempContextUrl))
	{
		//判断是否是真实域名
		if(tempContextUrl.indexOf("wofinder") > -1 && tempContextUrl.split(":").length == 3)
		{
			request.setAttribute("ip", tempContextUrl.substring(0, tempContextUrl.lastIndexOf(":")) + "/");
		}
		else
		{
			request.setAttribute("ip", tempContextUrl);
		}
	}
	else
	{
		ConfigService configService = SpringBeanUtil.getBean(ConfigService.class);
		String domainUrl = configService.findConfigValueByKey("domain_url");
		
		if(StringUtils.isNotEmpty(domainUrl))
		{
			JSONObject domainUrlJson = new JSONObject(domainUrl);
			request.setAttribute("ip",  domainUrlJson.get("wofinder"));
		}
	}
	//版本
	request.setAttribute("version", GlobalConstant.VERSION);
	
	//用户cookies
	String cookie_nick_name = CookieUtil.findCookieByName(request, GlobalConstant.COOKIE_NICK_NAME);
	if(StringUtils.isNotBlank(cookie_nick_name)){
		cookie_nick_name = URLDecoder.decode(cookie_nick_name,"UTF-8");
		request.setAttribute(GlobalConstant.COOKIE_NICK_NAME, cookie_nick_name);
	}
	String cookie_user_photo = CookieUtil.findCookieByName(request, GlobalConstant.COOKIE_USER_PHOTO);
	if(StringUtils.isNotBlank(cookie_user_photo)){
		cookie_user_photo = URLDecoder.decode(cookie_user_photo,"UTF-8");
		request.setAttribute(GlobalConstant.COOKIE_USER_PHOTO, cookie_user_photo);
	}
	String cookie_login_name = CookieUtil.findCookieByName(request, GlobalConstant.COOKIE_LOGIN_NAME);
	if(StringUtils.isNotBlank(cookie_login_name)){
		cookie_login_name = URLDecoder.decode(cookie_login_name,"UTF-8");
		request.setAttribute(GlobalConstant.COOKIE_LOGIN_NAME, cookie_login_name);
	}
%>

<meta http-equiv="X-UA-Compatible" content="edge"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<script language="JavaScript">
	var _path = '${path}';
	var _staticPrefix = '${staticPrefix}';
	var user = '${user}';		
	var userId = '${userId}';		
	var ip = '${ip}';
	var _version = '${version}';
	var cookie_nick_name = '${cookie_nick_name}';
	var cookie_login_name = '${cookie_login_name}';
	var cookie_user_photo = '${cookie_user_photo}';
	
	function ajaxMethod(url, dataJson, type, sync, callBack) {
		if(url == "" || url == undefined) {// 链接
			layer.alert("url 不能为空", {title:" ",icon: 5,time: 2000});
			return;
		}
		
		if(dataJson == "" || dataJson == undefined) {// 数据
			dataJson = {};
		}
		if(type == "" || type == undefined) {// post/get 请求
			type = "post";
		}
		var _sync = false;
		if(sync == "true" || sync) {// 同步
			_sync = true;
		}
		
		$.ajax({
			url: url,
			data: dataJson,
			type: type,
			async: _sync,
			dataType : "json",
			success : function(data) {
				if(callBack) {
					callBack(data);
				}
			}
		});
	}
</script>

<link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_297657_ckk1gad8b5u3di.css?v=${version }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/bootstrap.min.css?v=${version }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/bootstrap-material-design.css?v=${version }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/ripples.min.css?v=${version }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/mCustomScrollbar.css?v=${version }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/main.css?v=${version }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/common.css?v=${version }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/homepage.css?v=${version }">
<link rel="shortcut icon" href="${staticPrefix }/images/favicon.ico" type="image/x-icon" />

<script type="text/javascript" src="${staticPrefix }/js/jquery-3.2.1.min.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/bootstrap.min.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/material.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/ripples.min.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/mCustomScrollbar.concat.min.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/common.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/common/base.js?v=${version }"></script>
<script type="text/javascript" src="${ctx }/resources/js/layer-v2.3/layer.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/business/search/common.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/business/userSign.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix}/js/business/mycenter/browseSet.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/referrer-killer.js?v=${version }"></script>