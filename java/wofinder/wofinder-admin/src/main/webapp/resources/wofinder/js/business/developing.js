$(function() {
	var handler1 = function (captchaObj) {
        captchaObj.onSuccess(function () {
        	var feedBack = $.trim($("#feedBack").val());
        	if (feedBack == null || feedBack == '') {//手机号填写不正确
            	captchaObj.reset(); // 调用该接口进行重置
            	$("#feedbackBtn").addAttr("disabled", true);
            } else {
            	$("#feedbackBtn").removeAttr("disabled");
            }
        	
        	$("#feedbackBtn").click(function() {
        		saveFeedBack();
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
});

function saveFeedBack() {
	var feedBack = $.trim($("#feedBack").val());
	if (feedBack == null || feedBack == '') {//手机号填写不正确
    	$("#feedbackBtn").attr("disabled", true);
    	return false;
    }
	$.ajax({
		type : "POST",
		url :  _path + "/fp/feedback",
		data : {
			type : 1,
			feedback : feedBack
		},
		dataType : "json",
		beforeSend:function(){
			$("#feedbackBtn").attr("disabled","true");
			$("#feedbackBtn").html("提交中...");
		},
		complete: function () {	
			$("#feedbackBtn").removeAttr("disabled");
		$("#feedbackBtn").html("提交成功");
		},
		success : function(result) {
			if (result && result > 0) {
				window.location.href = "/index";
			}
			
		},
		error : function(errorMsg) {
			
		}
	});
	
}