<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="safewrap" id="safeQuestion">
	<input type="hidden" id="id" />
		<div class="first">
			<div class="form-group label-floating">
				<label for="questionId" class="control-label">请选择提示问题</label>
				<select id="questionId" class="form-control">
		   		</select>
			</div>
			<div class="form-group label-floating">
				<label for="answer" class="control-label">设置答案</label>
				<input type="tel" class="form-control" id="answer">
			</div>
			<p><a title="确认" href="javascript:;" class="btn msure" onclick="editQuestion('save');">确认</a></p>			
		</div>
		<div class="second">
			<div class="form-group label-floating">
				<label for="i5" class="control-label">提示问题</label>
				<input type="tel" class="form-control" id="questionId2" readonly="readonly">
			</div>
			<div class="form-group label-floating">
				<label for="answer2" class="control-label">请输入答案</label>
				<input type="tel" class="form-control" id="answer2">
			</div>
			<p><a title="确认" href="javascript:;" class="btn msure" onclick="checkToSet();">验证，去重设</a></p>
		</div>
		<div class="third">
			<div class="sansi">
				<div class="title2">
					<i class="iconfont icon-wf_zuo_qiehuan" onclick="gotoSecond();"></i>
					<h3 class="logotitle">验证之前的手机</h3>
				</div>
				<div class="form-group label-floating phonenum">
					<label for="mobile" class="control-label">请输入手机号码</label>
					<input type="tel" class="form-control" id="mobile">
					<span class="help-block"><code id="help-block"></code></span>
				</div>
				<div class="main"><div class="yanzheng" id="gtcaptcha"></div></div>
				<div class="form-group label-floating phonenum">
					<label for="captcha" class="control-label">请输入收到的验证码</label>
					<input type="tel" class="form-control" id="captcha">
					<span class="help-block error" id="captchaError"></span>
				</div>
				<p><a title="确认" href="javascript:;" class="btn msure" onclick="checkSure();">确认</a></p>
			</div>
		</div>
		<div class="fourth">
			<div class="sansi">
				<div class="title">
					<i class="iconfont icon-wf_zuo_qiehuan" onclick="gotoThird();"></i>
					<h3 class="logotitle">重新设置安全问题</h3>
				</div>
				<div class="form-group label-floating">
					<label for="questionId3" class="control-label">请选择提示问题</label>
					<select id="questionId3" class="form-control">
			   		</select>
				</div>
				<div class="form-group label-floating">
					<label for="answer3" class="control-label">请输入答案</label>
					<input type="tel" class="form-control" id="answer3">
				</div>
				<p><a title="确认" href="javascript:;" class="btn msure" onclick="editQuestion('update')">确认</a></p>
			</div>
		</div>
	</div>