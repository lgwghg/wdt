<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-cn" xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>wofinder后台管理系统-登录</title>
<%@include file="common/common.jsp" %>
<script type="text/javascript" src="${ctx }/resources/wofinder/js/common/base.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/customer/index/login.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		
		$('#backgroundSlider').backgroundSlider({
			direction: 'slide', //场景动画类型：【in】:拉近;【out】:拉远;【slide】:幻灯片
	        speed: 5000
		});
		
		//错误提示信息
		if ("${error}" != "") {
	    	layer.alert('${error}', {icon : 5,time : 0});
		}
		
		//页面进行跳转到login
		if (window.location.href.indexOf("/login") == -1) {
		    if($("#userId").val() == null || $("#userId").val() == "")
		    {
		        top.location.href = "login";
		    }else
		    {
		        top.location.href = "admin";
		    }
		}
		
	});
</script>
</head>
<body class="login-layout">
<input id="userId" type="hidden" value="<c:if test="${not empty sessionScope.userSessionId}">${sessionScope.userSessionId }</c:if>"/>
	<div class="main-container">
		<div class="main-content">
			<div class="row">
				<div class="col-sm-10 col-sm-offset-1">
					<div class="login-container">
						<div class="center">
							<h1>
								<img src="${ctx }/resources/images/logo.png"/>
							</h1>
						</div>

						<div class="space-6"></div>

						<div class="position-relative">
							<div id="login-box"
								class="login-box visible widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header blue lighter bigger">
											<i class="ace-icon fa fa-coffee blue"></i> 请输入帐号信息
										</h4>

										<div class="space-6"></div>

										<form id="loginform" name="loginform"
											action="${ctx }/login"
											method="post">
											<fieldset>
												<label class="block clearfix"> 
												<span class="block input-icon input-icon-right">
													<input type="hidden" name="returnurl" value="feed">
													<input name="email" id="email" type="hidden" />
													<input name="mobile" id="mobile" type="hidden" />
													<input name="loginName" id="loginName" class="form-control" placeholder="邮箱或手机号登录" /> 
													<i class="ace-icon fa fa-user"></i>
												</span>
												</label> 
												<label class="block clearfix"> 
												<span class="block input-icon input-icon-right"> 
													<input name="password" id="password" type="password" class="form-control" placeholder="密码" />
													<i class="ace-icon fa fa-lock"></i>
												</span>
												</label>
												<label class="block clearfix"> 
													<input id="captcha" name="captcha" type="text" class="form-control" placeholder="验证码" style="width:60%;float:left;"/>
												<img id="kaptchaImage" src="${ctx }/captcha" style="cursor:pointer; margin-left:10px;" title="点击更换"/>  
												</label>

												<div class="space"></div>

												<div class="clearfix">
												<input type="hidden" id="rememberMe" name="rememberMe" value="false"/>
													<label class="inline"> <input id="rememberMeCheckBox" type="checkbox"
														class="ace" /> <span class="lbl"> 记住我</span>
													</label>

													<button onclick="login();" type="button" 
														class="width-35 pull-right btn btn-sm btn-primary">
														<i class="ace-icon fa fa-key"></i> <span
															class="bigger-110">登录</span>
													</button>
												</div>

												<div class="space-4"></div>
											</fieldset>
										</form>
									</div>
									<!-- /.widget-main -->

								</div>
								<!-- /.widget-body -->
							</div>
							<!-- /.login-box -->
						</div>
						<!-- /.position-relative -->
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	
	<!-- background -->
	<div id="backgroundSlider">
		<img src="${ctx }/resources/images/background/1.jpg" name="1"/>
		<img src="${ctx }/resources/images/background/2.jpg" name="2"/>
		<img src="${ctx }/resources/images/background/3.jpg" name="3"/>
		<img src="${ctx }/resources/images/background/4.jpg" name="4"/>
		<img src="${ctx }/resources/images/background/5.jpg" name="5"/>
		<img src="${ctx }/resources/images/background/6.jpg" name="6"/>
	</div>
	<!-- / #backgroundSlider -->

</body>
</html>
