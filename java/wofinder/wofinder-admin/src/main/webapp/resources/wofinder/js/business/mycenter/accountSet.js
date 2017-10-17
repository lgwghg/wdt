$(function() {
	$("#left_accountSetBtn").click(function() {
		showAccountSet();
	});
});

function showAccountSet() {
	$.ajax( {
		url : _path + "/my/showAccountSet",
		type : "POST", 
		dataType : "html",
		success : function(result) {
			$("#centerMain").html(result);
			oldMobileCaptcha();
			//账号设置页面
	        $(".account-number-setting-inside .one").on("click",".btn",function(){
	            $(this).parent().addClass("hide").siblings(".two").removeClass("hide");
	        })
	        $(".account-number-setting-inside .two").on("click",".top-title .iconfont",function(){
	        	$("#oldCaptcha").val("");
	            $(this).parents(".two").addClass("hide").siblings(".one").removeClass("hide");
	        })
	        $(".account-number-setting-inside .three").on("click",".top-title .iconfont",function(){
	        	$("#oldCaptcha").val("");
	        	$("#newCaptcha").val("");
	            $(this).parents(".three").addClass("hide").siblings(".two").removeClass("hide");
	        });
	        
	        
		},error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
	});
}


function oldMobileCaptcha() {
	var handler1 = function (captchaObj) {
		captchaObj.onSuccess(function () {
			var mobile = $("#oldMobile").val();
			genMobileCaptcha(mobile, captchaObj);
			// 提交手机
			$(".account-number-setting-inside .two").on("click",".btn",function(){
				var result = captchaObj.getValidate();
				if (checkAccountCaptcha(mobile, "oldCaptcha" ,1)) {
					if (result) {
						if ($.trim($("#newgtcaptcha").html()) == '') {
							newMobileCaptcha();
						}
						$(this).parent().addClass("hide").siblings(".three").removeClass("hide");
					} else {
						$("#oldCaptchaError").show();
					}
				}
	        }).on("click",".top-title .iconfont",function(){
	        	captchaObj.reset();
	        	$("#oldCaptcha").val("");
	            $(this).parents(".two").addClass("hide").siblings(".one").removeClass("hide");
	        })
	        $(".account-number-setting-inside .three").on("click",".top-title .iconfont",function(){
	        	captchaObj.reset();
	        	$("#oldCaptcha").val("");
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
}

function newMobileCaptcha() {
	var handler2 = function (captchaObj) {
        captchaObj.onSuccess(function () {
        	var mobile = $("#mobile").val();
        	if (!checkAccountMobile()) {//手机号填写不正确
            	captchaObj.reset(); // 调用该接口进行重置
            	$("#submitMobile").removeClass("nextone").addClass("unablenext");
            } else {
            	$("#submitMobile").removeClass("unablenext").addClass("nextone");
            	genMobileCaptcha(mobile, captchaObj);
            }
        	$("#mobile").on("change", function() {
        		captchaObj.reset(); // 调用该接口进行重置
        	});
        	$(".account-number-setting-inside .three").on("click",".top-title .iconfont",function(){
	        	captchaObj.reset();
	        	$("#newCaptcha").val("");
	        });
        	// 提交手机
        	$("#saveAccountMobile").on("click",function(e){
        		var result = captchaObj.getValidate();
        		if (checkAccountMobile() && checkAccountCaptcha(mobile, "captcha", 1)) {
        			if (!result) {
                        $("#captchaError").show();
                        setTimeout(function () {
                            $("#captchaError").hide();
                        }, 2000);
                        e.preventDefault();
                        
                    } else {
                    	//$(this).closest(".one").hide().next(".two").show();
                    	editMobileAccount();
                    }
        		}
        	});
        });
        // 将验证码加到id为captcha的元素里，同时会有三个input的值用于表单提交
        captchaObj.appendTo("#newgtcaptcha");
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
            }, handler2);
        }
    });
}

function checkAccountCaptcha(mobile,captchaId,isClear) {
	var captcha = $("#" + captchaId).val();
	if (captcha == null || captcha == "") {
		$("#" + captchaId + "Error").html("手机动态码不能为空");
		$("#" + captchaId + "Error").parent(".form-group").addClass("has-error");
		$("#" + captchaId + "Error").show();
		return false;
	}
	if (captchaId != 'oldCaptcha') {
		if (!checkAccountMobile()) {
			return false;
		}
		
	}
	var flag = true;
	$.ajax({
        type : "POST",
        url : _path + "/withoutAuth/checkCaptcha",
        data : {
            "captcha" : captcha,
            "mobile" : mobile,
            "isClear" : isClear
        },
        async : false,
        success : function(resultdata) {
        	if (resultdata == 0) {
        		$("#" + captchaId + "Error").html("手机动态码错误");
        		$("#" + captchaId + "Error").parent(".form-group").addClass("has-error");
        		$("#" + captchaId + "Error").show();
         		flag = false;
            } else {
            	$("#" + captchaId + "Error").html("");
            	$("#" + captchaId + "Error").parent(".form-group").removeClass("has-error");
            	$("#" + captchaId + "Error").hide();
            	flag = true;
            }
        },
        error : function(errorMsg) {
        	flag = false;
        }
    });
	return flag;
}

//验证手机号
function checkAccountMobile() {
	var mobile = $("#mobile").val();
    if (mobile == "") {
    	$("#mobileError").html("手机号不能为空");
    	$("#mobileError").parent(".form-group").addClass("has-error");
    	$("#mobileError").show();
 		//$("#mobile").focus();
        return false;
    } else {
        if(!/^1\d{10}$/.test(mobile)) {
        	$("#mobileError").html("请输入正确的手机号");
        	$("#mobileError").parent(".form-group").addClass("has-error");
        	$("#mobileError").show();
     		//$("#mobile").focus();
            return false;
        } else {
            var flag = true;
            $.ajax({
                type : "POST",
                url : _path + "/user/withoutAuth/validateUserMobile",
                data : {
                    "mobile" : mobile
                },
                dataType : "json",
                async : false,
                beforeSend:function(){
                	
    			},
                success : function(resultdata) {
                    if (!resultdata.success) {
                        $("#mobileError").html(resultdata.message);
                        $("#mobileError").parent(".form-group").addClass("has-error");
                        $("#mobileError").show();
                 		//$("#mobile").focus();
                        flag = false;
                    } 
                },
                error : function(errorMsg) {
                    $("#mobileError").html("服务器未响应,请稍后再试");
                    $("#mobileError").parent(".form-group").addClass("has-error");
                    $("#mobileError").show();
             		//$("#mobile").focus();
                    flag = false;
                }
            });
            if(!flag)
            {
                return flag;   
            } else {
            	$("#mobileError").html("");
            	$("#mobileError").parent(".form-group").removeClass("has-error");
            	$("#mobileError").hide();
            	return true; 
            }
        }
    }
}
var mobileFlag = true;
function editMobileAccount() {
	var oldMobile = $("#oldMobile").val();
	var mobile = $("#mobile").val();
	if (mobileFlag) {
		mobileFlag = false;
		$.ajax({
	        type : "POST",
	        url : _path + "/my/editMobileAccount",
	        data : {
	            "mobile" : mobile,
	            oldMobile : oldMobile
	        },
	        dataType : "json",
	        beforeSend:function(){
	        	
			},
	        success : function(resultdata) {
	            if (resultdata.success) {
	            	layer.msg(resultdata.message, {title:"",icon: 6,time: 2000});
	            	showAccountSet();
	            } else {
	            	layer.alert(resultdata.message, {title:"",icon: 5,time: 2000});
	            }
	            mobileFlag = true;
	        },
	        error : function(errorMsg) {
	            mobileFlag = true;
	        }
	    });
	}
    
}