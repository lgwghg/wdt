$(document).ready(function() {
	/*$('#registerForm').find("input").bind("keydown", function(event) {
		if(event.keyCode==13) {
			mobileRegister();
	    }
	});*/
    $("#rmobile").blur(function() {
    	/*if ($("#mobileError").text() !='') {
    		$("#mobileError").parent(".form-group").removeClass("hide").addClass("has-error")
    		$("#mobileError").show();
    	}*/
    	checkMobile();
    });
    $("#rpassword").blur(function() {
    	checkPassword();
    });
    $("#rnickName").blur(function() {
    	checkNickName();
    });
    $("#nickNameBTN").click(function() {
    	setNickName();
    });
    var falg;
	$(".szpassword i").click(function(){
		if(falg){
			$(this).addClass("icon-wf_yanjingbi").removeClass("icon-wf_yanjingkai");
			$("#rpassword").attr("type", "password");
			falg = false;
		}else{
			$(this).addClass("icon-wf_yanjingkai").removeClass("icon-wf_yanjingbi");
			$("#rpassword").attr("type", "text");
			falg = true;
		}
	});
	
	//注册
	$(".two .nextone").on("click",function(){
		mobileRegister();
	});
	// 完成注册
	$(".six .nextone").on("click",function(){
		var nickName = $("#nickName").val();
		$(this).closest(".content").prev(".header").find("h3").text('"' + nickName + '"恭喜您，已经在在WoDotA注册成功!');
		$(this).closest(".register").prev(".tab").hide();
		$(this).closest(".six").hide().next(".seven").show();
	});
	
	
	//注册回车事件
	// 账号回车
	$('.one').find("input").bind("keydown", function(event) {
		if(event.keyCode==13) {
			$(".one .nextone").click();
	    }
	});
	// 密码回车
	$('.two').find("input").bind("keydown", function(event) {
		if(event.keyCode==13) {
			$(".two .nextone").click();
	    }
	});
	// 昵称回车
	$('.three').find("input").bind("keydown", function(event) {
		if(event.keyCode==13) {
			$("#nickNameBTN").click();
	    }
	});
});

// 验证手机号
function checkMobile() {
	var mobile = $("#rmobile").val();
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
                        return flag;
                    } else {
                    	$("#mobileError").html("");
                    	$("#mobileError").parent(".form-group").removeClass("has-error");
                    	$("#mobileError").hide();
                    	return true; 
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

function checkPassword() {
	var password = $("#rpassword").val();
    if (password == "") {
    	$("#rpasswordError").html("请输入密码");
    	$("#rpasswordError").parent(".form-group").addClass("has-error");
    	$("#rpasswordError").show();
 		//$("#password").focus();
        return false;
    } else if (password.length < 6 || password.length > 18) {
    	$("#rpasswordError").parent(".form-group").addClass("has-error");
    	$("#rpasswordError").html("密码长度6-18个字符");
    	$("#rpasswordError").show();
 		//$("#password").focus();
        return false;
    } else {
    	$("#rpasswordError").html("");
    	$("#rpasswordError").parent(".form-group").removeClass("has-error");
    	$("#rpasswordError").hide();
    	return true; 
    }
}

var submit = true;
//注册
function mobileRegister() {
	var mobile = $("#rmobile").val();
	var password = $("#rpassword").val();
	var token = $("#token").val();
	if (submit) {
		submit = false;
		// 验证手机号
		if (!checkMobile()) {
			submit = true;
			$("#rmobile").focus();
			return false;
		}
		// 验证密码
		if (!checkPassword()) {
			submit = true;
			$("#rpassword").focus();
			return false;
		}
		$.ajax({
			type : "POST",
			url : _path + "/register",
			data : {
				"token" : token,
				"mobile" : mobile,
				"password" : password
			},
			dataType : "json",
			beforeSend:function(){
				$("#mobileRegisterButton").html("正在注册");
				$("#mobileRegisterButton").attr("disabled", true);
			},
			complete: function () {	
				$("#mobileRegisterButton").html("注册");
				$("#mobileRegisterButton").removeAttr("disabled");
		    },
			success : function(resultdata) {
				if (resultdata.success) {
					// 分享注册 成功页不一样。不需要登录
					/*var shareRegister = $("#shareRegister").val();
					if (typeof(shareRegister) != undefined && shareRegister != null && shareRegister != '' && shareRegister == '1') {
						window.location.href= _path + "/share/f/regSuccess";
					} else {
					}*/
					//layer.msg('注册成功', {title:"",icon: 6,time : 2000,end:function(){registerlogin(mobile, password);}});	//lwh:注册成功后跳转到登录页面
					$("#userId").val(resultdata.userId);
					$("#nickName").val(resultdata.nickName);
					$("#mobileRegisterButton").html("注册成功");
					$("#mobileRegisterButton").attr("disabled", true);
					registerlogin(mobile, password);
					$(".two .nextone").closest(".two").hide().next(".three").show();
					$(".three input").focus();
					return true;
				} else {
					layer.alert(resultdata.message, {title:"",icon: 5,time : 2000});
					return false;
				}
				submit = true;
			},
			error : function(jqXHR, textStatus, errorThrown) 
			{
				/*弹出jqXHR对象的信息*/
	            if(jqXHR.status = "611")
	            {
	            	layer.alert("重复提交表单!", {title:"",icon: 5,time : 2000});
	            }
	            else
	            {
	            	layer.alert(errorMsg, {title:"",icon: 5,time : 2000});
	            }
				submit = true;
				return false;
			}
		});
		
	}
}
function registerlogin(mobile, password) {
	$.ajax({
		type : "POST",
		url : _path+"/login",
		data : {
			"mobile" : mobile,
			"password" : password,
			"rememberMe" : false
		},
		success : function(data) {
			/*var _data = eval('(' + data + ')');
			if (_data.code == 1) {
				window.location.href = _path + "/index";
			} else {
				window.location.href = _path + "/login";
			}*/
		}
	});
}


function checkCaptcha(isClear) {
	var captcha = $("#captcha").val();
	var mobile = $("#rmobile").val();
	if (captcha == null || captcha == "") {
		$("#captchaError").html("手机动态码不能为空");
		$("#captchaError").parent(".form-group").addClass("has-error");
		$("#captchaError").show();
		return false;
	}
	if (captcha.length != 4) {
		$("#captchaError").html("手机动态码错误");
		$("#captchaError").parent(".form-group").addClass("has-error");
		$("#captchaError").show();
		return false;
	}
	if (!checkMobile()) {
		return false;
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
        dataType : "json",
        async : false,
        success : function(resultdata) {
        	if (resultdata.success) {
         		$("#captchaError").html("");
                $("#captchaError").parent(".form-group").removeClass("has-error");
                $("#captchaError").hide();
            	flag = true;
            } else {
            	$("#captchaError").html(resultdata.message);
                $("#captchaError").parent(".form-group").addClass("has-error");
                $("#captchaError").show();
         		flag = false;
            }
        },
        error : function(errorMsg) {
        	flag = false;
        }
    });
	return flag;
}

function isRead() {
	var isRead = document.getElementById("isRead").checked;
	if (isRead) {
		$("#mobileRegisterButton").removeAttr("disabled");
	} else {
		$("#mobileRegisterButton").attr("disabled","true");
	}
}

//上传头像
function uploadImg(){
	var avatar_data = $("#avatar_data").val();
	var avatarInput = $("#avatarInput").val();
	if(trim(avatar_data)=="" || trim(avatarInput)==""){
		layer.alert("请选择要上传的图片", {title:"",icon: 5,time : 3000});
	}else{
		$("#uploadImgForm").ajaxSubmit({
	        type : "POST",
	        url : _path + "/avatar/upload",
	        data : $("#uploadImgForm").serialize(),
	        success : function(result) {
	        	var data = eval("("+result+")");
	        	if(parseInt(data.code) == 1)//上传成功
	            {
	        		layer.msg("上传成功!", {title:"",icon: 6,time : 2000 ,end:function(){/*window.location.reload();*/}});
	        		$("#user_pic").attr("src",data.src);
	        		$(".close").click();
	        		var photoHtml = '<div class="line"></div>'+
	        			'<span class="title">上传头像</span><span class="circle">'+
							'<span class="dot"></span>'+
						'</span>'+
						'<span class="score">+5</span>';
	        		$("#completePhoto").html(photoHtml);
	            }
	            else
	            {
	            	var info = "未知错误,请稍后再试!";
	            	
	            	if(data.msg != "")
	            	{
	            		info = data.msg;
	            	}
	            	layer.alert(info, {title:"",icon: 5,time : 3000});
	            }
	        }
	    });
	}
}
function checkNickName() {
	// 验证昵称
	var nickName = $("#rnickName").val().replace(/\ +/g,"");
	$("#rnickName").val(nickName);
	if (nickName == "") {
		$("#nickNameError").html("昵称不能为空");
		$("#nickNameError").parent(".form-group").addClass("has-error");
		$("#nickNameError").show();
		//$("#nickName").focus();
		return false;
	} else if (!checkNickLength(nickName)) {
		$("#nickNameError").html("昵称2-6个字");
		$("#nickNameError").parent(".form-group").addClass("has-error");
		$("#nickNameError").show();
		//$("#nickName").focus();
		return false;
	} else if (!/^[a-zA-Z0-9_\u4e00-\u9fa5]+$/.test(nickName)) {
		$("#nickNameError").html("昵称只能字母、数字或汉字组成");
		$("#nickNameError").parent(".form-group").addClass("has-error");
		$("#nickNameError").show();
		//$("#nickName").focus();
		return false;
	} else {
		var flag = true;
        /*$.ajax({
            type : "POST",
            url : _path + "/user/withoutAuth/validateUserNick",
            data : {
                "nickName" : nickName
            },
            dataType : "json",
            success : function(resultdata) {
                if (!resultdata) {
                	$("#nickNameError").html("该昵称已注册");
                	$("#nickNameError").parent(".form-group").addClass("has-error");
                	$("#nickNameError").show();
            		//$("#nickName").focus();
                    flag = false;
                } 
            },
            error : function(errorMsg) {
                $("#nickNameError").html("服务器未响应,请稍后再试");
                $("#nickNameError").parent(".form-group").addClass("has-error");
                $("#nickNameError").show();
        		//$("#nickName").focus();
                flag = false;
            }
        });*/
        if(!flag)
        {
            return flag;   
        } else {
        	$("#nickNameError").html("");
        	$("#nickNameError").parent(".form-group").removeClass("has-error");
        	$("#nickNameError").hide();
        	return true; 
        }
	}
}
/**
 * 设置昵称
 */
function setNickName() {
	var nickName = $("#rnickName").val();
	if (nickName == null || nickName =='') {
		$(".three .nextone").closest(".three").hide().next(".six").show();
	}
	var id = $("#userId").val();
	if (checkNickName()) {
		$.ajax({
            type : "POST",
            url : _path + "/my/edit",
            data : {
                "nickName" : nickName
            },
            dataType : "json",
            success : function(resultdata) {
                if (resultdata.result > 0) {
                	$("#nickName").val(nickName);
                	var nickHtml = '<div class="line"></div>'+
	        			'<span class="title">设置昵称</span><span class="circle">'+
							'<span class="dot"></span>'+
						'</span>'+
						'<span class="score">+5</span>';
	        		$("#completeNick").html(nickHtml);
                	$(".three .nextone").closest(".three").hide().next(".six").show();
                } 
            },
            error : function(errorMsg) {
                flag = false;
            }
        });
	}
	
}
