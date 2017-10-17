<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="personal-center-main-left">
    <p class="title">个人中心</p>
    <ul class="list">
        <li class="list-one checked" id="left_welcomeBtn">
            <span class="first"></span>
            <i class="iconfont icon-wf_touxiang"></i>
            <span class="left-label">欢迎</span>
        </li>
        <li class="list-one" id="loginAndSecurityBtn">
            <span class="first"></span>
            <i class="iconfont icon-wf_shezhi-copy"></i>
            <span class="left-label">登陆和安全</span>
        </li>
        <%-- <li class="list-two">
            <span class="first"></span>
            <span class="left-label">绑定WoDotA</span>
        </li> --%>
        <li class="list-two" id="deviceBtn">
            <span class="first"></span>
            <span class="left-label">设备活动和通知</span>
        </li>
        <li class="list-one" id="personalBtn">
            <span class="first"></span>
            <i class="iconfont icon-wf_pinglun1"></i>
            <span class="left-label">个人信息和隐私</span>
        </li>
        <li class="list-two" id="messageBtn">
            <span class="first"></span>
            <span class="left-label">消息</span>
            <span class="new-message hide" id="left_unreadMsgNum"></span>
        </li>
        <%-- <li class="list-two">
            <span class="first"></span>
            <span class="left-label">浏览记录</span>
        </li> --%>
        <li class="list-two" id="videoGradeBtn">
            <span class="first"></span>
            <span class="left-label">点评记录</span>
        </li>
        <li class="list-two" id="left_integralTaskBtn">
            <span class="first"></span>
            <span class="left-label">积分与任务</span>
        </li>
        <li class="list-two" id="left_browseSetBtn">
            <span class="first"></span>
            <span class="left-label">浏览配置</span>
        </li>
        <li class="list-one" id="left_accountSetBtn">
            <span class="first"></span>
            <i class="iconfont icon-wf_mima1"></i>
            <span class="left-label">账号设置</span>
        </li>
        <li class="list-two" id="left_pwdEditBtn">
            <span class="first"></span>
            <span class="left-label">密码修改</span>
        </li>
        <li class="list-two" id="left_safeQuestionBtn">
            <span class="first"></span>
            <span class="left-label">安全问题</span>
        </li>
        <li class="list-one" id="sysHelpBtn">
            <span class="first"></span>
            <i class="iconfont icon-help_aibaba_test"></i>
            <span class="left-label">帮助和反馈</span>
        </li>
        <a href="/logout">
        <li class="list-one tcdl" >
            <span class="first"></span>
            <i class="iconfont icon-wf_tuichu"></i>
            <span class="left-label">退出登录</span>
        </li>
        </a>
    </ul>
</div>
