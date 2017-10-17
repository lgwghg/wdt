 $(function(){
    	var nickName = cookie_nick_name;
    	//layer.alert(nickName, {title:"",icon: 5,time : 0});
    	if(nickName!=""&&nickName!=null){
    		var loginName = cookie_login_name;
    		var userPhoto = cookie_user_photo;
    		if(userPhoto==""||userPhoto==null){
    			userPhoto = _path + "/resources/wofinder/images/default.png";
    		}else{
    			if (userPhoto.indexOf('.') >= 0) {
    				var ph = userPhoto.split('.');
    				userPhoto = ph[0] + "_70." + ph[1];
    			}
    			userPhoto = _path + userPhoto;
    		}
    		
    		var mobileReg = /^1[3|4|5|7|8]\d{9}$/;
            if(mobileReg.test(loginName)) {
            	$("#mobile").val(loginName);
            } else {
            	$("#email").val(loginName);
            }
            $("#nickName_login").html(""+nickName+"");
            $("#login_name").html(loginName);
    		$("#userPhoto").attr('src',userPhoto);
    		$("#historyLog").show();
    		$("#loginNameDiv").hide();
    	} else {
    		$("#historyLog").hide();
    		$("#loginNameDiv").show();
    	}
    	
    	$("#notMe").click(function() {
    		$("#historyLog").hide();
   		 	$("#loginNameDiv").show();
   		 	$("#mobile").val('');
   		 	$("#email").val('');
    	});
    });
    
$(document).ready(function() {
    //$("body").css("background","#16a08");
    /*
    //背景粒子效果
    $('.main-container').particleground({
        dotColor : '#5cbdaa',
        lineColor : '#5cbdaa'
    });
    $('.main-content').css({
        'margin-top' : -($('.main-content').height())
    });
    */
    //刷新验证码
    /*$('#kaptchaImage').click(function() {//生成验证码
        $(this).hide().attr('src', 'captcha.html?' + new Date().getTime()).fadeIn();
    });*/

    //登录、注册、找回密码切换
    /*$(document).on('click', '.toolbar a[data-target]', function(e) {
        e.preventDefault();
        var target = $(this).data('target');
        //隐藏其他dom
        $('.widget-box.visible').removeClass('visible');
        //显示目标dom
        $(target).addClass('visible');
    });*/
    
    //注册回车键事件
//    document.onkeypress = function(e){
//    var ev = document.all ? window.event : e;
//        if(ev.keyCode==13) {
//            login();
//        }
//    };
//  
	// 打开登录输入框
	$('#loginform').find("input").bind("keydown", function(event) {
		if(event.keyCode==13) {
	        login();
	    }
	});
    $('#loginform').bind('submit', function()
   	{
        ajaxSubmit(this, function(data)
        {
            if(data.success)//帐号验证成功
            {
            	if(data.url.trim() != "")
            	{
            		//getTopWinow()
            		window.location.href = _path + data.url.trim();
            	}
            	else
            	{
            		window.location.href = _path + "/index";
            	}
            }
            else
            {
            	var info = "未知错误,请稍后再试!";
            	
            	if(data.message != "" && data.message != undefined)
            	{
            		info = data.message;
            		if ($("#loginName").val() != "") {
            			$("#email").val('');
            			$("#mobile").val('');
            		}
            	}
            	
            	layer.alert(info, {title:"",icon: 5,time : 3000});
            }
        });
        
        return false;
    });
    
});


//登录
function login() {
    if($("#loginName").val() == "") {
    	if ($("#email").val() == "" && $("#mobile").val() == "") {
    		$("#loginNameError").html("请输入账户邮箱或手机号");
    		$("#loginNameError").parent(".form-group").addClass("has-error");
    		$("#loginNameError").show();
    		$("#loginName").focus();
    		return;
    	}
    } else {// 通过输入账号登录
    	if($("#email").val()=='' && $("#mobile").val()==''){
    		var emailReg = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
            var mobileReg = /^1[3|4|5|7|8]\d{9}$/;
            if(!emailReg.test($("#loginName").val()) && !mobileReg.test($("#loginName").val()))
            {
                $("#loginNameError").html("请输入正确的邮箱或手机号");
                $("#loginNameError").parent(".form-group").addClass("has-error");
                $("#loginNameError").show();
                $("#loginName").focus();
                return false;
            } else if (emailReg.test($("#loginName").val()) && !mobileReg.test($("#loginName").val())) {
            	// 用邮箱登录
            	$("#email").val($("#loginName").val());
            	$("#mobile").val('');
            } else if (!emailReg.test($("#loginName").val()) && mobileReg.test($("#loginName").val())) {
            	// 用手机号登录
            	$("#mobile").val($("#loginName").val());
            	$("#email").val('');
            }
            //验证登录用户是否已经绑定第三方，已经绑定别的账号，不允许再绑定
            /*var mobile = $("#mobile").val();
            var email = $("#email").val();
            var thirdType = $("#thirdType").val();
            var flag = false;
            if (thirdType != null && $("#thirdKey").val() != null) {
            	$.ajax({
            		type : "POST",
            		url : _path + "/user/withoutAuth/validateUserBindThird",
            		data : {
            			"mobile" : mobile,
            			"email" : email,
            			"thirdType" : thirdType
            		},
            		dataType : "json",
            		async : false,
            		success : function(resultdata) {
            			if (resultdata != null && resultdata != "0") {
            				$("#loginNameError").html(resultdata);
            				$("#loginNameError").parent(".form-group").addClass("has-error");
            				$("#loginNameError").show();
            				flag = false;
            			} else {
            				flag = true;
            			}
            		},
            		error : function(errorMsg) {
            			$("#loginNameError").html("服务器未响应,请稍后再试");
            			$("#loginNameError").parent(".form-group").addClass("has-error");
            			$("#loginNameError").show();
            			//$("#nickName").focus();
            			flag = false;
            		}
            	});
            	if (!flag) {
            		return false;
            	}
            }*/
    	}
    }
    $("#loginNameError").html("");
    $("#loginNameError").parent(".form-group").removeClass("has-error");
    $("#loginNameError").hide();
    if($("#password").val() == "") {
    	$("#passwordError").html("请输入密码");
    	$("#passwordError").parent(".form-group").addClass("has-error");
    	$("#passwordError").show();
        $("#password").focus();
         return;
    }
    $("#passwordError").html("");
    $("#passwordError").parent(".form-group").removeClass("has-error");
    $("#passwordError").hide();
    /*if($("#captcha").val() == "")
    {
        $("#captcha").focus();
        layer.alert('请输入验证码', {title:"",icon: 5,time : 0});
         return false;
    }*/
    /*if($("#rememberMeCheckBox").is(':checked')) {
        $("#rememberMe").val(true);
    }*/
    $("#loginform").submit();
}

