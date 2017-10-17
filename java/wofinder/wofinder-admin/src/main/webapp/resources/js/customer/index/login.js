$(document).ready(function() {
    $("body").css("background","#16a08");
    
    //刷新验证码
    $('#kaptchaImage').click(function() {//生成验证码
        $(this).hide().attr('src', 'captcha?' + new Date().getTime()).fadeIn();
    });

    //登录、注册、找回密码切换
    $(document).on('click', '.toolbar a[data-target]', function(e) {
        e.preventDefault();
        var target = $(this).data('target');
        //隐藏其他dom
        $('.widget-box.visible').removeClass('visible');
        //显示目标dom
        $(target).addClass('visible');
    });
    
    //注册回车键事件
    document.onkeypress = function(e){
    var ev = document.all ? window.event : e;
        if(ev.keyCode==13) {
            login();
        }
    };
    
    $('#loginform').bind('submit', function()
   	{
        ajaxSubmit(this, function(data)
        {
            if(data.success)//帐号验证成功
            {
            	if(data.url.trim() != "" && data.url.indexOf(".") < 0)
            	{
            		location.href = sys.rootPath + data.url.trim();
            	}
            	else
            	{
                	location.href = sys.rootPath + "/admin";
            	}
            }
            else
            {
            	$("#kaptchaImage").hide().attr('src', 'captcha?' + new Date().getTime()).fadeIn();
            	$("#email").val('');
        		$("#mobile").val('');
            	layer.alert(data.message, {title:"",icon: 5,time : 3000});
            }
        });
        
        return false;
    });
});


//登录
function login() {
    if($("#loginName").val() == "")
    {
        layer.alert('请输入账户邮箱或手机号', {title:"",icon: 5,time : 0});
        $("#loginName").focus();
        return false;
    }else
    {
        var emailReg = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
        var mobileReg = /^1[3|4|5|7|8]\d{9}$/;
        if(!emailReg.test($("#loginName").val()) && !mobileReg.test($("#loginName").val()))
        {
            layer.alert('请输入正确的邮箱或手机号', {title:"",icon: 5,time : 0});
            $("#loginName").focus();
            return false;
        } else if (emailReg.test($("#loginName").val()) && !mobileReg.test($("#loginName").val())) {
        	// 用邮箱登录
        	$("#email").val($("#loginName").val());
        } else if (!emailReg.test($("#loginName").val()) && mobileReg.test($("#loginName").val())) {
        	// 用手机号登录
        	$("#mobile").val($("#loginName").val());
        }
    }
    if($("#password").val() == "")
    {
        layer.alert('请输入密码', {title:"",icon: 5,time : 0});
        $("#password").focus();
         return false;
    }
    if($("#captcha").val() == "")
    {
        $("#captcha").focus();
        layer.alert('请输入验证码', {title:"",icon: 5,time : 0});
         return false;
    }
    if($("#rememberMeCheckBox").is(':checked'))
    {
        $("#rememberMe").val(true);
    }
    $("#loginform").submit();
}