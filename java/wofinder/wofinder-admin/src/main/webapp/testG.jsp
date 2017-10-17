<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
response.setHeader("Access-Control-Allow-Origin", "*");
response.setHeader("Access-Control-Allow-Methods","GET,POST");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'testG.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="/resources/wofinder/js/jquery-3.2.1.min.js"></script>
	<script src='https://recaptcha.net/recaptcha/api.js'></script>
    <script type="text/javascript">
	  var verifyCallback = function(response) {
        $.ajax({
        	type : 'POST',
        	url : '/ssl/recaptcha',//https://recaptcha.net/recaptcha/api/siteverify
        	data : {
        		"resp" : response
        	},
        	success :function(data) {
        		alert(data.success);
        	}
        });
      };
	</script>

  </head>
  
  <body>
    <div class="g-recaptcha" id="g-recaptcha"
							data-sitekey="6LfWACoUAAAAAHjldo2N1Vu4y10iKb5jTG0TAVgn"
							data-callback="verifyCallback" >
							</div>
  </body>
</html>
