$(function() {
	$("#left_pwdEditBtn").click(function() {
		showPwdEdit();
	});
	
});

function showPwdEdit() {
	$.ajax( {
		url : _path + "/my/showPwdEdit",
		type : "POST", 
		dataType : "html",
		success : function(result) {
			$("#centerMain").html(result);
			
		    $(".szpassword i").click(function(){
		        if($(this).hasClass("icon-wf_yanjingkai")){
		            $(this).addClass("icon-wf_yanjingbi").removeClass("icon-wf_yanjingkai");
		            $(this).parent().find("input").attr("type", "password");
		        }else{
		            $(this).addClass("icon-wf_yanjingkai").removeClass("icon-wf_yanjingbi");
		            $(this).parent().find("input").attr("type", "text");
		        }
		    });
		    
		    $("#pawset").click(function() {
		    	setPassword();
		    });
		},error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
	});
}

function checkPassword(passwordStr) {
	var password = $("#" + passwordStr).val();
    if (password == "") {
    	$("#" +passwordStr+ "Error").html("请输入新密码");
    	$("#" +passwordStr+ "Error").parent('.form-group').addClass("has-error");
    	$("#" +passwordStr+ "Error").show();
        return false;
    } else if (password.length < 6 || password.length > 18) {
    	$("#" +passwordStr+ "Error").html("密码长度在6-18个字符之间");
    	$("#" +passwordStr+ "Error").parent('.form-group').addClass("has-error");
    	$("#" +passwordStr+ "Error").show();
        return false;
    } else {
    	$("#" +passwordStr+ "Error").html("");
    	$("#" +passwordStr+ "Error").parent('.form-group').removeClass("has-error");
    	$("#" +passwordStr+ "Error").hide();
    	return true; 
    }
}
var passwordFlag = true;
function setPassword() {
	var oldPassword = $("#oldPassword").val();
	var newPassword = $("#newPassword").val();
	if (!checkPassword("oldPassword") || !checkPassword("newPassword")) {
		return;
	}
	if (passwordFlag) {
		passwordFlag = false;
		$.ajax({
			type : "POST",
			url :  _path + "/my/editPwd",
			data : {
				oldPassword : oldPassword,
				newPassword : newPassword
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
					//layer.alert('新密码设置成功', {title:"",icon: 6,shift : 6,time : 3000,end:function(){login();}});	//lwh:设置成功后跳转到登录页面
					layer.alert('新密码设置成功', {title:"",icon: 6,shift : 6,time : 3000});
					$("#oldPassword").val('');
					$("#newPassword").val('');
				} else {
					layer.alert(result.message, {title:"",icon: 5,time : 3000});
				}
				passwordFlag = true;
			},
			error : function(errorMsg) {
				layer.alert('新密码设置失败', {title:"",icon: 5,time : 3000});
				passwordFlag = true;
			}
		});
	}
}

