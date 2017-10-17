<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="welcome module" id="welcome">
    <div class="header images-x images-x-content">
    	<c:if test="${not empty userInfo and not empty userInfo.getPhoto_133()}">
        	<img src="${userInfo.getPhoto_133() }">
    	</c:if>
    	<c:if test="${empty userInfo }">
        <span class="no_header no_header-D">D</span>
        </c:if>
    </div>
    <p class="name">
        欢迎您，<span class="re">${userInfo.nickName }</span>！
    </p>
    <p class="describe">您可以在这里集中对帐号进行设置，确保个人账户的安全。</p>
    <p class="IP">
        <span>您当前登录的IP是：</span>
        <span>${loginInfo.loginIp }</span>
        <c:if test="${not empty loginInfo.province && loginInfo.province != '0'}">
        <span>(${loginInfo.province } ${loginInfo.city } ${loginInfo.net })</span>
        </c:if>
        <%-- <span>(${loginInfo.province }${loginInfo.city })</span> --%>
    </p>
    <img src="/resources/wofinder/images/team.png" class="tb">
    <p class="last">我们致力于保护您的隐私和安全——WoDotA</p>
</div>
