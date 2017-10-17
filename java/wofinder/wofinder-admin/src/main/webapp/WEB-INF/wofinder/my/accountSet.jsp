<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--账号设置-->
<div class="account-number-setting" id="container">
    <div class="account-number-setting-inside">
        <div class="one">
            <div class="form-group label-floating">
                <label class="control-label">手机号码</label>
                <input type="tel" class="form-control" value="${mobile }" readonly>
                <span class="help-block"></span>
            </div>
            <c:if test="${monthEnough eq 0 }">
            	<button class="btn" disabled="true" type="button">一个月修改一次</button>
            </c:if>
            <c:if test="${monthEnough eq 1}">
            	<button class="btn" type="button">确认修改帐号，下一步</button>
            </c:if>
            
        </div>
        <div class="two hide">
            <div class="top-title">
                <i class="iconfont icon-wf_zuo_qiehuan"></i>
                <div>
                    <span class="one"></span>
                    <span class="two">验证之前的手机</span>
                </div>
            </div>
            <div class="form-group label-floating form-group-one">
                <label class="control-label">手机号码</label>
                <input type="tel" class="form-control" value="${mobile }" readonly id="oldMobile">
            </div>
            <div class="main">
	            <div class="yanzheng" id="gtcaptcha">
				</div>
			</div>
            <div class="form-group label-floating form-group-two">
                <label class="control-label">请输入收到的验证码</label>
                <input type="text" class="form-control" id="oldCaptcha">
                <span class="help-block" id="oldCaptchaError"></span>
            </div>
            <button class="btn">下一步</button>
            <p class="number">
                1/2
            </p>
        </div>
        <div class="three hide">
            <div class="top-title">
                <i class="iconfont icon-wf_zuo_qiehuan"></i>
                <div>
                    <span class="one"></span>
                    <span class="two">请输入您的新手机号码</span>
                </div>
            </div>
            <div class="form-group label-floating form-group-one">
                <label class="control-label">请输入新手机号码</label>
                <input type="tel" class="form-control" id="mobile">
                <span class="help-block" id="mobileError"></span>
            </div>
            <div class="main">
            <div class="yanzheng" id="newgtcaptcha">
			</div>
			</div>
            <div class="form-group label-floating form-group-two">
                <label class="control-label">请输入收到的验证码</label>
                <input type="text" class="form-control" id="captcha">
                <span class="help-block" id="captchaError"></span>
            </div>
            <button class="btn" id="saveAccountMobile">确认</button>
            <p class="number">
                2/2
            </p>
        </div>
    </div>
</div>