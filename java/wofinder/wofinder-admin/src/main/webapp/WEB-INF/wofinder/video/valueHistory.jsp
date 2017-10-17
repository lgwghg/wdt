<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../include/common.jsp"/>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="edge"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<title>标签修改记录</title>
	<link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_7f82nhukauqpk3xr.css">
    <link rel="stylesheet" href="${staticPrefix }/css/titlecollection.css?v=${version }" type="text/css">
    <script type="text/javascript" src="${path }/resources/js/common/dateformat.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/business/videovalue/videoValueLog.js?v=${version }"></script>
</head>
<body>
<!--顶部-->
<jsp:include page="../include/header.jsp"/>
<div class="main fix">
	<input type="hidden" value="${videoId }" id="videoId"/>
	<div class="wrap">
        <div class="header">
    		<h2>《${videoTitle }》标签修改记录</h2>
    		<div class="list-header">
    			<div class="lh-item"><span>用户</span></div>
    			<div class="lh-item"><span>时间</span></div>
    			<div class="lh-item"><span>操作</span></div>
    		</div>
        </div>
		<div class="list-body">
            
            
		</div>
	</div>
</div>
<!--底部-->
<jsp:include page="../include/footer.jsp"/>
<%-- <div class="jubaokuang">
	<div class="header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="iconfont icon-wf_guanbi"></i></button>
		<h3>请选择举报理由</h3>
	</div>	
	<div class="form-group">
	  <div class="radio">
	    <label>
	      <input type="radio" name="sample1" value="option1" checked="">
	      内容不相关
	    </label>
	  </div>
	  <div class="radio">
	    <label>
	      <input type="radio" name="sample1" value="option1">
	      敏感信息
	    </label>
	  </div>
	  <div class="radio">
	    <label>
	      <input type="radio" name="sample1" value="option1">
	      恶意攻击
	    </label>
	  </div>
	  <div class="radio">
	    <label>
	      <input type="radio" name="sample1" value="option1">
	      剧透内容
	    </label>
	  </div>
	</div>
</div>	 --%>
</body>
<script>
 $.material.init();
</script>
</html>