<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<title>wofinder后台管理系统</title>
<meta name="robots" content="noindex,nofollow">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta http-equiv="X-UA-Compatible" content="E=edge;chrome=1" />
<meta name="keywords" content="wofinder后台管理系统" />
<meta name="description" content="wofinder后台管理系统" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<link href="${ctx }/resources/images/wofinder.ico" rel="icon"/>
<link href="${ctx }/resources/images/wofinder.ico" type="image/x-icon" rel="bookmark"/>   
<link href="${ctx }/resources/images/wofinder.ico" type="image/x-icon" rel="shortcut icon"/>  

<link rel="stylesheet" href="${ctx}/resources/js/bootstrap/bootstrap.min.css"/>
<link rel="stylesheet" href="${ctx}/resources/fonts/fontawesome/font-awesome.min.css"/>
<link rel="stylesheet" href="${ctx}/resources/fonts/opensans/ace-fonts.min.css"/>
<link rel="stylesheet" href="${ctx}/resources/css/ace/ace.min.css"/>
<link rel="stylesheet" href="${ctx}/resources/css/customer/webside.min.css"/>
<!--[if lte IE 9]>
	<link rel="stylesheet" href="${ctx}/resources/css/ace/ace-part2.min.css" />
	<link rel="stylesheet" href="${ctx}/resources/css/ace/ace-ie.min.css" />
<![endif]-->

<!-- JQuery script -->
<!-- 非IE浏览器不会识别IE的条件注释，所以这里判断非IE需要如下写法：参照下面jquery-2.1.4.min.js引入的方式 -->
<!--[if !IE]><!-->
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery-2.1.4.min.js"></script>
<!--<![endif]-->
<!--[if IE]>
	<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery-1.11.3.min.js"></script>
<![endif]-->
<!-- basic scripts -->
<script type="text/javascript">
	if ('ontouchstart' in document.documentElement)document.write("<script src='${ctx}/resources/js/jquery/jquery.mobile.custom.min.js'>" + "<"+"script>");
</script>

<!--[if lt IE 9]>
	<script type="text/javascript" src="${ctx }/resources/js/ie/html5shiv.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/ie/respond.min.js"></script>
<![endif]-->
<!--[if lt IE 8]>
    <script src="${ctx}/resources/js/ie/json2.js"></script>
<![endif]-->
<!--[if lte IE 8]>
	<script type="text/javascript" src="${ctx}/resources/js/ie/excanvas.min.js"></script>
<![endif]-->

<script type="text/javascript" src="${ctx}/resources/js/jqueryui/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqueryui/jquery.ui.touch-punch.min.js"></script>

<script type="text/javascript" src="${ctx}/resources/js/layer-v2.3/layer.js"></script>
<!-- 背景图变化 -->
<link rel="stylesheet" href="${ctx }/resources/js/backgroundSlider/backgroundSlider.css" />
<script type="text/javascript" src="${ctx }/resources/js/backgroundSlider/backgroundSlider.min.js"></script>
<!-- 表格 -->
<link rel="stylesheet" type="text/css" href="${ctx}/resources/js/dlshouwen.grid.v1.2.1/dlshouwen.grid.min.css" />
<script type="text/javascript" src="${ctx}/resources/js/dlshouwen.grid.v1.2.1/dlshouwen.grid.treegrid.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/dlshouwen.grid.v1.2.1/i18n/zh-cn.js"></script>
<!-- 校验 -->
<script type="text/javascript" src="${ctx}/resources/js/jquery-validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery-validation/localization/messages_zh.js"></script>
<!-- 滚动条 -->
<script type="text/javascript" src="${ctx}/resources/js/nicescroll/jquery.nicescroll.min.js"></script>

<%-- <script type="text/javascript" src="${ctx }/resources/js/bootstrap/bootstrap.min.js"></script> --%>
<link rel="stylesheet" href="${ctx }/resources/js/bootstrap-switch/css/bootstrap3/bootstrap-switch.min.css"/>
<script type="text/javascript" src="${ctx }/resources/js/bootstrap-switch/js/bootstrap-switch.min.js"></script>
<!-- javascript tools -->
<script type="text/javascript" src="${ctx }/resources/js/underscore/underscore-min.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/purl/purl.min.js"></script>
<!-- 元素动画 -->
<script  type="text/javascript" src="${ctx }/resources/js/scrollreveal/scrollreveal.min.js"></script>

<!-- 复选框美化 -->
<link rel="stylesheet" href="${ ctx}/resources/js/select2/select2.min.css" />
<script type="text/javascript" src="${ ctx}/resources/js/select2/select2.min.js"></script>
<script type="text/javascript" src="${ ctx}/resources/js/select2/zh-CN.js"></script>

<script type="text/javascript" src="${ctx}/resources/js/customer/index/index.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/common/dateformat.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common/wofinder.css" />
<script type="text/javascript" src="${ctx}/resources/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" type="text/javascript">
	var sys = sys || {};
	sys.rootPath = "${ctx}";
	sys.pageNum = 10;
	sys.pageNumForModal = 5;
	sys.gridStyle = "Bootstrap";
	window.scrollreveal = ScrollReveal();
</script>
<!-- // 解决shiro登陆超时ajax请求跳转   -->
<script type="text/javascript">
	$.ajaxSetup({
	    complete:function(XMLHttpRequest,textStatus)
	    {
	 		var status = XMLHttpRequest.getResponseHeader('session_status');  
	    	
	 		if(status == 'timeout')
	    	{  
	   			var top = getTopWinow();  
	            var yes = confirm('由于您长时间没有操作, session已过期, 请重新登录.');  
	            
	            if (yes)
	            {  
	            	top.location.href = '/login.html';              
	            }  
	        }  
	    	
	    }
	});
	
	/** 
	 * 在页面中任何嵌套层次的窗口中获取顶层窗口 
	 * @return 当前页面的顶层窗口对象 
	 */  
	function getTopWinow()
	{  
		var p = window;  
	    
	    while(p != p.parent)
	    {  
	        p = p.parent;  
	    }
	    
	    return p;  
	}  
</script>