<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="include/common.jsp" />
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>登录注册${seoConfigMap['1'].title }</title>
	<meta name="keywords" content="登录注册${seoConfigMap['1'].keywords }">
    <meta name="description" content="登录注册${seoConfigMap['1'].keywords }">
	<link rel="stylesheet" href="${staticPrefix}/css/cropper.min.css?v=${version }" type="text/css">
    <link rel="stylesheet" href="${staticPrefix}/css/reterlogin.css?v=${version }" type="text/css">
    <script src="${staticPrefix}/js/common/gt.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix}/js/cropper.min.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix}/js/sitelogo.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix}/js/jquery.form.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix}/js/business/login/login.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix}/js/business/login/register.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix}/js/common/captcha.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix}/js/business/login/geetest.js?v=${version }" type="text/javascript"></script>
</head>
<body style="background-color: #ffffff;">
	<div class="main fix">
		<div class="wrap">
			<div class="backgroundwrap">
				<canvas id="Mycanvas"></canvas>
			</div>
			<a href="/" class="to-homepage"><img src="${staticPrefix}/images/w-b-b.png"></a>
			<div class="header">
				<h1 class="logowrap"></h1>
				<h3>上WoDotA找一下，看你想看的</h3>
			</div>
			<div class="content">
				<input type="hidden" id="isLogin"  value="${isLogin }" />
				<div class="tab">
					<div class="title tab-active" id="registerTitle">注册</div>	
					<div class="title" id="loginTitle">登录</div>
				</div>
				<div class="link-t register">
					<form id="registerForm" action="/register" method="POST">
					<div class="one">
						<div class="form-group label-floating phonenum">
						   <label for="rmobile" class="control-label">请输入手机号码</label>
						   <input type="tel" class="form-control" id="rmobile">
						   <span class="help-block error" id="mobileError"></span>
						</div>
						<div class="yanzheng" id="gtcaptcha">
						</div>
						<div class="form-group label-floating phonenum">
						   <label for="captcha" class="control-label">请输入手机验证码</label>
						   <input type="text" class="form-control" id="captcha" onkeyup="checkCaptcha()">
						   <span class="help-block error" id="captchaError"></span>
						</div>
						<p><a title="下一步" href="javascript:;" class="btn unablenext" id="submitMobile">下一步</a></p>
						<div class="textpwrap">
						<p>* 提交既表示你同意并遵循<strong><a href="#">XXX服务协议</a></strong></p>
						<p>已有账号?<strong><a href="/login">登录</a></strong></p>
						</div>
					</div>
					<div class="two">
						<div class="form-group label-floating szpassword">
						   <label for="rpassword" class="control-label">设置密码</label>
						   <input type="password" class="form-control" id="rpassword" >
						   <i class="iconfont icon-wf_yanjingbi"></i>
						   <span class="help-block error" id="rpasswordError"></span>
						</div>
						<p><a title="下一步" href="javascript:;" class="btn nextone" id="mobileRegisterButton">注册</a></p> 
					</div>
					<div class="three">
						<div class="form-group label-floating szpassword">
						   <label for="rnickName" class="control-label">设置昵称</label>
						   <input type="text" class="form-control" id="rnickName">
						   <span class="help-block error" id="nickNameError"></span>
						</div>
						<p><a title="下一步" href="javascript:;" class="btn nextone" id="nickNameBTN">下一步</a></p> 
					</div>
					<div class="six">
						<strong class="djsc"><a href="#" data-toggle="modal" data-target="#avatar-modal">点击上传头像</a></strong>
						<div class="avatarwrap">
								<div class="imgzhezhao">
									<div class="shch">
									<strong><a href="#" data-toggle="modal"
							data-target="#avatar-modal"><i class="iconfont icon-wf_shangchuan"></i>上传头像</a></strong>
									</div>
								</div>
								<img class="avatar" src="${staticPrefix}/images/default.png" alt="" width="100%" height="100%" id="user_pic">
						</div>
						<p><a title="完成" href="javascript:;" class="btn nextone">完成</a></p>
						<!-- <p class="passgo"><strong><a href="#">跳过</a></strong>，<strong><a href="#">继续</a></strong></p> -->
					</div>
					<div class="seven">
						<input type="hidden" id="nickName"/>
						<div class="ws-wrap">
							<div class="ws-item">
								<div class="line"></div>
								<span class="title">账号信息</span>
								<span class="circle">
									<span class="dot"></span>
								</span>
								<span class="score">+5</span>
							</div>
							<div class="ws-item" id="completeNick">
								<div class="line"></div>
								<span class="title">设置昵称</span>
								<span class="circle">
									<span class="dot"></span>
									<i class="iconfont icon-wf_cuowushi"></i>
								</span>
								<a href="/my#account"><span class="score ws-word">去完善</span></a>
							</div>
							<div class="ws-item" id="completePhoto">
								<div class="line"></div>
								<span class="title">上传头像</span>
								<span class="circle">
									<span class="dot"></span>
									<i class="iconfont icon-wf_cuowushi"></i>
								</span>
								<a href="/my#account"><span class="score ws-word">去完善</span></a>
							</div>
						</div>
						<p><a title="回到首页，玩转世界" href="/index" class="btn nextone">回到首页，玩转世界</a></p>
					</div>
					
					</form>
				</div>
				<div class="link-t login">
					<form id="loginform" action="/login" method="POST">
					<input type="hidden" name="returnUrl"  value="${returnUrl }" />
					<input type="hidden" id="mobile" name="mobile"/>
					<input type="hidden" id="email" name="email"/>
					<div class="form-group label-floating user" id="loginNameDiv">
					   <label for="loginName" class="control-label">请输入手机号码</label>
					   <input type="text" class="form-control" id="loginName">
					   <span class="help-block error" id="loginNameError"></span>
					</div>
					<div class="loginuser disappear" id="historyLog">
						<div class="userimgwrap">
							<img src="${staticPrefix}/images/default.png" alt="" width="100%" height="100%" id="userPhoto">
						</div>
						<div class="userinform"><span class="nikename" id="nickName_login"></span><span class="phone" id="login_name"></span></div>
						<a title="关闭" class="iconfont icon-wf_guanbi userclose" id="notMe"></a>
					</div>
					<div class="form-group label-floating password">
					   <label for="password" class="control-label">请输入密码</label>
					   <input type="password" class="form-control" id="password" name="password" />
					   <span class="help-block error" id="passwordError"></span>
					</div>
					<input id="rememberMe" name="rememberMe" value="false" type="hidden"/>
					<p class="forget"><strong><a title="忘记密码" href="/fp">忘记密码？</a></strong></p>
					<p><strong><a title="登录" href="javascript:login();" class="btn submit">登录</a></strong></p>
					</form>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="include/footer.jsp" />
	<!--picshanchuan-->
 	<div class="modal fade" id="avatar-modal" aria-hidden="true"
		aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
 	<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<form class="avatar-form" action="/avatar/upload"
					enctype="multipart/form-data" method="post" id="uploadImgForm">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="iconfont icon-wf_guanbi"></i></button>
						<h3 class="modal-title">图片选择</h3>
					</div>
					<div class="modal-body">
						<div class="avatar-body">
			              <div class="avatar-upload">
			                  <input class="avatar-src" name="avatar_src" type="hidden">
			                  <input class="avatar-data" name="avatar_data"  id="avatar_data" type="hidden">
			                  <label class="btn avatar-label" for="avatarInput">点击上传文件</label>
			                  <input type="file" id="avatarInput" multiple="" class="avatar-input" name="file"/>
			                </div>
							<div class="avatar">
								<div class="avatarone">
									<div class="avatar-wrapper"></div>
								</div>
								<div class="avatartwo">
									<div class="avatar-preview preview-lg"></div>
									<div class="avatar-preview preview-md"></div>
									<div class="avatar-preview preview-sm"></div>
								</div>
							</div>
							<div class="row avatar-btns">
								<div class="col-md-9">
									<div class="btn-group">
										<button class="btn"
											data-method="rotate" data-option="-90" type="button"
											title="Rotate -90 degrees">向左旋转</button>
									</div>
									<div class="btn-group">
										<button class="btn"
											data-method="rotate" data-option="90" type="button"
											title="Rotate 90 degrees">向右旋转</button>
									</div>
								</div>
								<div class="col-md-3">

								</div>
							</div>
							<div class="saveclose">
							<div class="btn-group">
								<a href="javascript:;" class="close" data-dismiss="modal">取消</a>						</div>	
								<div class="btn-group">
									<button class="btn btn-success btn-block avatar-save"
										type="button" onClick="uploadImg()">保存修改</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	 </div>
</body>
<script type="text/javascript" src="${staticPrefix}/js/bgcanvas.js?v=${version }"></script>
<script>
    $.material.init();
    $(function(){
    	$(".title").click(function(){
    		$(this).addClass("tab-active").siblings().removeClass("tab-active");
    		var index = $(".title").index(this);
    		$(".link-t").hide().eq(index).show();
    	});
    	var isLogin = $("#isLogin").val();
    	if (isLogin) {
    		$("#loginTitle").click();
    	}
    	$(".avatarwrap").hover(function(){
    		$(this).find(".imgzhezhao").toggle();
    	});
 		$(".loginuser").on("mouseenter",function(){
   			$(this).find(".userclose").show();
   		}).on("mouseleave",function(){
   			$(this).find(".userclose").hide();
   		}); 
    	$(".userclose").on("click",function(){
    		$(this).closest(".loginuser").hide().prev(".user").show();
    	});
    });

</script>
</html>
