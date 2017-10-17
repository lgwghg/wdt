<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@page import="com.webside.common.GlobalConstant"%>
<%
	if (GlobalConstant.ENV.equals(GlobalConstant.EnvConstant.PRD)) {
		//生产环境
		request.setAttribute("socketAddress", GlobalConstant.SocketAddressConstant.PRD);
	} else if(GlobalConstant.ENV.equals(GlobalConstant.EnvConstant.TEST)) {
		//测试环境
		request.setAttribute("socketAddress", GlobalConstant.SocketAddressConstant.TEST);
	} else {
		//开发环境
		request.setAttribute("socketAddress", GlobalConstant.SocketAddressConstant.DEV);
	}
%>
<script type="text/javascript" src="${staticPrefix }/js/socket.io-1.4.5.js?v=${version }"></script>
<script>
var _socketAddress = '${socketAddress}';
var socket = null;
</script>