$(function() {
	var handler1 = function (captchaObj) {
        captchaObj.onSuccess(function () {
        	if (!checkMobile()) {//手机号填写不正确
            	captchaObj.reset(); // 调用该接口进行重置
            	$("#submitMobile").removeClass("nextone").addClass("unablenext");
            } else {
            	$("#submitMobile").removeClass("unablenext").addClass("nextone");
            	var mobile = $("#mobile").val();
            	genMobileCaptcha(mobile, captchaObj);
            }
        	$("#mobile").on("change", function() {
        		captchaObj.reset(); // 调用该接口进行重置
        		$("#submitMobile").removeClass("nextone").addClass("unablenext");
        	});
        	// 提交手机
        	$(".forget .nextone").on("click",function(e){
        		var result = captchaObj.getValidate();
        		if (checkMobile() && checkCaptcha(1)) {
        			if (!result) {
                        $("#captchaError").show();
                        setTimeout(function () {
                            $("#captchaError").hide();
                        }, 2000);
                        e.preventDefault();
                        
                    } else {
                    	$(this).closest(".forget").hide().next(".setpassword").show();
                    	$(".setpassword input").focus();
                    }
        		}
        	});
        });
        // 将验证码加到id为captcha的元素里，同时会有三个input的值用于表单提交
        captchaObj.appendTo("#gtcaptcha");
        captchaObj.onReady(function () {
            $("#wait1").hide();
        });
        // 更多接口参考：http://www.geetest.com/install/sections/idx-client-sdk.html
    };
    $.ajax({
        url: "/gtcaptcha?t=" + (new Date()).getTime(), // 加随机数防止缓存
        type: "get",
        dataType: "json",
        success: function (data) {
            // 调用 initGeetest 初始化参数
            // 参数1：配置参数
            // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它调用相应的接口
            initGeetest({
                gt: data.gt,
                challenge: data.challenge,
                new_captcha: data.new_captcha, // 用于宕机时表示是新验证码的宕机
                offline: !data.success, // 表示用户后台检测极验服务器是否宕机，一般不需要关注
                product: "float", // 产品形式，包括：float，popup
                width: "100%"
                // 更多配置参数请参见：http://www.geetest.com/install/sections/idx-client-sdk.html#config
            }, handler1);
        }
    });
    
    
    
    
    ///////////////////
    $(".setpassword .nextone").on("click",function(){
    	if (checkPassword()) {
    		setPassword();
    	}
	}); 
    
    $('.forget').find("input").bind("keydown", function(event) {
		if(event.keyCode==13) {
			$(".forget .nextone").click();
	    }
	});
    
    $('.setpassword').find("input").bind("keydown", function(event) {
		if(event.keyCode==13) {
			$(".setpassword .nextone").click();
	    }
	});
    
    $('.feedback').find("input").bind("keydown", function(event) {
		if(event.keyCode==13) {
			$(".feedback .nextone").click();
	    }
	});
});
$(function() {
	$('#forgetPasswordForm').find("input").bind("keydown", function(event) {
		if(event.keyCode==13) {
			setPassword();
	    }
	});
	
	$("#password").blur(function() {
		checkPassword();
	});
	$("#mobile").blur(function() {
		checkMobile();
	});
	$("#captcha").blur(function() {
		checkCaptcha(0);
	});
	
});

// 验证手机号
function checkMobile() {
	var mobile = $("#mobile").val();
    if (mobile == "") {
    	$("#mobileError").html("手机号不能为空");
    	$("#mobileError").parent('.form-group').addClass("has-error");
    	$("#mobileError").show();
 		//$("#mobile").focus();
        return false;
    } else {
        if(!/^1\d{10}$/.test(mobile)) {
        	$("#mobileError").html("请输入正确的手机号");
        	$("#mobileError").parent('.form-group').addClass("has-error");
        	$("#mobileError").show();
     		//$("#mobile").focus();
            return false;
        } else {
            var flag = true;
            $.ajax({
                type : "POST",
                url : _path + "/user/withoutAuth/validateUserMobile",
                async : false,
                data : {
                    "mobile" : mobile
                },
                dataType : "json",
                beforeSend:function(){
    			},
                success : function(resultdata) {
                    if (resultdata && resultdata.success) {
                    	$("#mobileError").html("不存在该手机号的用户");
                    	$("#mobileError").parent('.form-group').addClass("has-error");
                    	$("#mobileError").show();
                 		//$("#mobile").focus();
                        flag = false;
                    }else{
                    	flag = true;
                    }
                },
                error : function(errorMsg) {
                	layer.alert('服务器未响应,请稍后再试', {title:"",icon: 5,shift : 6,time : 3000});
             		//$("#mobile").focus();
                    flag = false;
                }
            });
            if(!flag)
            {	
            	//$("#submitCheckMobile").attr("disabled","true");
                return flag;   
            } else {
            	$("#mobileError").html("");
            	$("#mobileError").parent('.form-group').removeClass("has-error");
            	$("#mobileError").hide();
            	return true; 
            }
        }
    }
}

function checkCaptcha(isClear) {
	var captcha = $("#captcha").val();
	var mobile = $("#mobile").val();
	if (!checkMobile()) {
		return "mobileError";
	}
	if (captcha == null || captcha == "") {
		$("#captchaError").html("动态码不能为空");
		$("#captchaError").parent('.form-group').addClass("has-error");
		$("#captchaError").show();
		//$("#submitCheckMobile").attr("disabled","true");
		return false;
	}
	var flag = true;
	$.ajax({
        type : "POST",
        url : _path + "/withoutAuth/checkCaptcha",
        async : false,
        data : {
            "captcha" : captcha,
            "mobile" : mobile,
            "isClear" : isClear
        },
        beforeSend:function(){
        	//$("#submitCheckMobile").attr("disabled","true");
		},
        success : function(resultdata) {
        	if (resultdata == 0) {
        		$("#captchaError").html("动态码错误");
        		$("#captchaError").parent('.form-group').addClass("has-error");
        		$("#captchaError").show();
        		flag = false;
            } else {
            	$("#captchaError").html("");
            	$("#captchaError").parent('.form-group').removeClass("has-error");
            	$("#captchaError").hide();
            	return true;
            }
        },
        error : function(errorMsg) {
        	return false;
        }
    });
	if(!flag)
    {	
        return flag;   
    } else {
    	//$("#submitCheckMobile").removeAttr("disabled");
    	return true; 
    }
}

function checkPassword() {
	var password = $("#password").val();
    if (password == "") {
    	$("#passwordError").html("请输入密码");
    	$("#passwordError").parent('.form-group').addClass("has-error");
    	$("#passwordError").show();
        return false;
    } else if (password.length < 6 || password.length > 18) {
    	$("#passwordError").html("密码长度在6-18个字符之间");
    	$("#passwordError").parent('.form-group').addClass("has-error");
    	$("#passwordError").show();
        return false;
    } else {
    	$("#passwordError").html("");
    	$("#passwordError").parent('.form-group').removeClass("has-error");
    	$("#passwordError").hide();
    	return true; 
    }
}

function setPassword() {
	var mobile = $("#mobile").val();
	var password = $("#password").val();
	$.ajax({
        type : "POST",
        url :  _path + "/fp/password",
        data : {
        	password : password,
        	mobile : mobile
        },
        dataType : "json",
        beforeSend:function(){
        	$("#pawset").attr("disabled","true");
        	$("#pawset").html("提交中...");
		},
		complete: function () {	
			$("#pawset").removeAttr("disabled");
			$("#pawset").html("确&nbsp;定");
	    },
        success : function(result) {
             if (result.state) {
            	 $("#userId").val(result.userId);
            	 $(".setpassword .nextone").closest(".setpassword").hide().next(".feedback").show();
            	 //layer.alert('新密码设置成功', {title:"",icon: 6,shift : 6,time : 3000,end:function(){login();}});	//lwh:设置成功后跳转到登录页面
             } else {
            	 $("#passwordError").html(result.message);
            	 $("#passwordError").parent('.form-group').addClass("has-error");
             }
        },
        error : function(errorMsg) {
        	layer.alert('新密码设置失败', {title:"",icon: 5,time : 3000});
        }
    });
}

/*
 * 保存忘记密码的原因
 */
function saveForgetReason() {
	var userId = $("#userId").val();
	var reasonType = $("input[name='reasonType']:checked").val();
	var forgetReason = "";
	if (!reasonType) {
		window.location.href = "/index";
	} else {
		if (reasonType == 1) {
			forgetReason = "太久没登录，忘记密码了";
		} else if (reasonType == 2) {
			forgetReason = "最近修改了密码，没记住";
		} else if (reasonType == 0) {
			forgetReason = $("#forgetReason").val();
		}
		$.ajax({
			type : "POST",
			url :  _path + "/fp/feedback",
			data : {
				type : 0,
				userId : userId,
				feedback : forgetReason
			},
			dataType : "json",
			beforeSend:function(){
				/*$("#pawset").attr("disabled","true");
        	$("#pawset").html("提交中...");*/
			},
			complete: function () {	
				/*$("#pawset").removeAttr("disabled");
			$("#pawset").html("确&nbsp;定");*/
			},
			success : function(result) {
				if (result && result > 0) {
					window.location.href = "/login";
				} else {
					$("#passwordError").html(result.message);
					$("#passwordError").parent('.form-group').addClass("has-error");
				}
			},
			error : function(errorMsg) {
				
			}
		});
	}
	
}