<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="include/common.jsp" />
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>忘记密码${seoConfigMap['1'].title }</title>
	<meta name="keywords" content="${seoConfigMap['1'].keywords }">
    <meta name="description" content="${seoConfigMap['1'].description }">
    <link rel="stylesheet" href="${staticPrefix }/css/forgetpassword.css?v=${version }" type="text/css">
    <script src="${staticPrefix}/js/common/gt.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix}/js/business/login/forgetPassword.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix}/js/common/captcha.js?v=${version }" type="text/javascript"></script>
</head>
<body style="background-color: #ffffff;">
<div class="main fix">
	<div class="wrap">
		<div class="backgroundwrap">
			<canvas id="Mycanvas"></canvas>
		</div>
		<div class="header">
			<h1 class="logowrap"></h1>
		</div>
		<div class="content">
			<div class="forget">
				<div class="title">
					<h3 class="logotitle">扎心了，老铁！密码怎么能忘记。来，快把他找回来。</h3>
				</div>
				<div class="form-group label-floating phonenum">
					<label for="mobile" class="control-label">请输入手机号码</label>
					<input type="tel" class="form-control" id="mobile">
					<span class="help-block" id="mobileError"></span>
				</div>
				<div class="yanzheng" id="gtcaptcha"></div>
				<div class="form-group label-floating phonenum">
				   <label for="captcha" class="control-label">请输入手机验证码</label>
				   <input type="text" class="form-control" id="captcha" onkeyup="checkCaptcha()">
				   <span class="help-block error" id="captchaError"></span>
				</div>
				<p><a title="确认" href="javascript:;" class="btn unablenext" id="submitMobile">确认</a></p>
				<p>想起来了，<strong><a href="/login">去登录</a></strong></p>
			</div>
			<div class="setpassword">
				<div class="title">
					<h3>请输入新密码</h3>
				</div>
				<div class="form-group label-floating szpassword">
				   <label for="password" class="control-label">设置密码</label>
				   <input type="password" class="form-control" id="password" >
				   <i class="iconfont icon-wf_yanjingbi"></i>
				   <span class="help-block error" id="passwordError"></span>
				</div>
				<p><a title="确定" href="javascript:;" class="btn nextone" id="pawset">确定</a></p>
			</div>
			<div class="feedback">
			<div class="title">
				<h3>恭喜您，密码找回成功！</h3>
				</div>
				<div class="selecttitle">回来就好，想想是怎么迷失的？</div>
				<input type="hidden" id="userId"/>
				<div class="radio">
				  <label>
				    <input type="radio" name="reasonType" value="1" > 
				    太久没登录，忘记密码了 
				  </label>
				</div>
				<div class="radio">
				  <label>
				    <input type="radio" name="reasonType" value="2" > 
				    最近修改了密码，没记住 
				  </label>
				</div>
				<div class="radio">
				  <label>
				    <input type="radio" name="reasonType" value="0" > 
				    一句话才能形容我此刻的心情：
				  </label>
				</div>
				<div class="form-group label-floating" style="margin-top: 23px;">
				    <label for="feedback" class="control-label">此刻想说的话</label>
				    <textarea class="form-control" id="feedback" maxlength="64"></textarea>
				    <p class="zg"><span class="sy" id="count">0</span>/64</p>
				</div>
				<p><a title="确定" href="javascript:;" class="btn nextone" onClick="saveForgetReason()">确定</a></p>
			</div>
		</div>
	</div>
</div>
</body>
<script type="text/javascript" src="${staticPrefix}/js/bgcanvas.js?v=${version }"></script>
<script>
	$.material.init();
	$(function(){
    	var falg;
    	$(".szpassword i").click(function(){
    		if(falg){
    			$(this).addClass("icon-wf_yanjingbi").removeClass("icon-wf_yanjingkai");
    			$("#password").attr("type", "password");
    			falg = false;
    		}else{
    			$(this).addClass("icon-wf_yanjingkai").removeClass("icon-wf_yanjingbi");
    			$("#password").attr("type", "text");
    			falg = true;
    		}
    	});
	});
	/*字数限制*/  
    $("#feedback").on("input propertychange", function() {  
        var $this = $(this),  
            _val = $this.val(),  
            count = "";  
        if (_val.length > 64) {  
            $this.val(_val.substring(0, 64));  
        }  
        count = $this.val().length;  
        $("#count").text(count);  
    });  
</script>
</html>