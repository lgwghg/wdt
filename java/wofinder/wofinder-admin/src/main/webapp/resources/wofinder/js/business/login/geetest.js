$(function() {
	var handler1 = function (captchaObj) {
        /*$("#submitMobile").click(function (e) {
            var result = captchaObj.getValidate();
            if (!result) {
                $("#captchaError").show();
                setTimeout(function () {
                    $("#captchaError").hide();
                }, 2000);
                e.preventDefault();
                
            }
        });*/
        captchaObj.onSuccess(function () {
        	var result = captchaObj.getValidate();
        	/*$.ajax({
                url: "/gtvalidate?t=" + (new Date()).getTime(), // 加随机数防止缓存
                type: "post",
                data:{
                	geetest_challenge: result.geetest_challenge,
                    geetest_validate: result.geetest_validate,
                    geetest_seccode: result.geetest_seccode
                },
                dataType: "json",
                success: function (data) {
                	// 根据服务端二次验证的结果进行跳转等操作
                    if (data.status === 'fail') {
                        alert('用户名或密码错误，请重新输入并完成验证');
                        captchaObj.reset(); // 调用该接口进行重置
                    }
                }
            });*/
        	if (!checkMobile()) {//手机号填写不正确
            	captchaObj.reset(); // 调用该接口进行重置
            	$("#submitMobile").removeClass("nextone").addClass("unablenext");
            } else {
            	$("#submitMobile").removeClass("unablenext").addClass("nextone");
            	var mobile = $("#rmobile").val();
            	genMobileCaptcha(mobile, captchaObj);
            }
        	$("#rmobile").on("change", function() {
        		captchaObj.reset(); // 调用该接口进行重置
        		$("#submitMobile").removeClass("nextone").addClass("unablenext");
        	});
        	// 提交手机
        	$(".one .nextone").on("click",function(e){
        		var result = captchaObj.getValidate();
        		if (checkMobile() && checkCaptcha(1)) {
        			if (!result) {
                        $("#captchaError").show();
                        setTimeout(function () {
                            $("#captchaError").hide();
                        }, 2000);
                        e.preventDefault();
                        
                    } else {
                    	$(this).closest(".one").hide().next(".two").show();
                    	$(".two input").focus();
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
});