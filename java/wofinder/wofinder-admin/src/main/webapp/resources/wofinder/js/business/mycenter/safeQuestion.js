$(function() {
	$("#left_safeQuestionBtn").click(function() {
		showSafeQuestion();
	});
});

function showSafeQuestion() {
	$.ajax({
		url : _path + "/my/showSafeQuestion",
		type : "POST",
		dataType : "html",
		success : function(result) {
			$("#centerMain").html(result);
			loadSafeQuestion();
			setJY();
		},
		error : function(errorMsg) {

		},
		complete : function(errorMsg) {

		}
	});
}

/**
 * 
 */
function loadSafeQuestion() {
	var url = _path + "/my/showQuestion.html";

	ajaxMethod(url, {}, "post", true, function(backData) {
		if (backData) {
			var content;
			var question = backData.question;
			var data = backData.questionList;
			if (question) {// 已经设置过密保
				$(".first").hide();
				$(".second").show();
				$("#id").val(question.id);
				$("#answer").val(question.answer);
			}
			if (data) {
				var dict;
				for (var i = 0; i < data.length; i++) {
					dict = data[i];
					if (question) {
						if (question.question == dict.value) {
							$("#questionId2").val(dict.label);
						}
					}
					content += '<option value="' + dict.value + '">' + dict.label + '</option>';
				}
			}

			$("#questionId").html(content);
			$("#questionId3").html(content);
		}
	});
}

function editQuestion(type) {
	var id = $("#id").val();
	var question = $("#questionId").val();
	var answer = $("#answer").val();
	if(type == "update") {
		question = $("#questionId3").val();
		answer = $("#answer3").val();
	}
	if (question == "" || question == undefined) {
		layer.alert("请选择密保问题~", {title : " ", icon : 5, time : 1500 });
		return;
	}
	if (answer == "" || answer == undefined) {
		layer.alert("请设置答案~", {title : " ",icon : 5,time : 1500});
		return;
	}

	var url = _path + "/my/editQuestion.html";
	var dataJson = {};
	dataJson.id = id;
	dataJson.question = question;
	dataJson.answer = answer;

	ajaxMethod(url, dataJson, "post", true, function(backData) {
		if (backData.success) {
			if (id != "" && id != undefined) {
				layer.alert("修改成功", {title : " ",icon : 6,time : 1500});
			} else {
				layer.alert("新增成功", {title : " ",icon : 6,time : 1500});
			}
			showSafeQuestion();
		} else {
			if (id != "" && id != undefined) {
				layer.alert("修改失败", {title : " ",icon : 5,time : 1500});
			} else {
				layer.alert("新增失败", {title : " ",icon : 5,time : 1500});
			}
		}
	});
}

function checkToSet() {
	var answer2 = $("#answer2").val();
	if (answer2 == "" || answer2 == undefined) {
		layer.alert("请输入答案~", {title : " ",icon : 5,time : 1500});
		return;
	}
	var answer = $("#answer").val();
	if ($.trim(answer) != $.trim(answer2)) {
		layer.alert("答案错误，请确认后重新输入~", {title : " ",icon : 5,time : 1500});
		$("#answer").val();
		return;
	}
	$(".second").hide();
	$(".third").show();
}

function gotoSecond() {
	$(".second").show();
	$(".third").hide();
}

function gotoThird() {
	$(".third").show();
	$(".fourth").hide();
}

function setJY() {
	var handler1 = function(captchaObj) {
		captchaObj.onSuccess(function() {
			var mobile = $("#mobile").val();
			genMobileCaptcha(mobile, captchaObj);
			$("#mobile").on("change", function() {
				captchaObj.reset(); // 调用该接口进行重置
			});
		});
		// 将验证码加到id为captcha的元素里，同时会有三个input的值用于表单提交
		captchaObj.appendTo("#gtcaptcha");
		captchaObj.onReady(function() {
			$("#wait1").hide();
		});
		// 更多接口参考：http://www.geetest.com/install/sections/idx-client-sdk.html
	};
	$.ajax({
		url : "/gtcaptcha?t=" + (new Date()).getTime(), // 加随机数防止缓存
		type : "get",
		dataType : "json",
		success : function(data) {
			// 调用 initGeetest 初始化参数
			// 参数1：配置参数
			// 参数2：回调，回调的第一个参数验证码对象，之后可以使用它调用相应的接口
			initGeetest({
				gt : data.gt,
				challenge : data.challenge,
				new_captcha : data.new_captcha, // 用于宕机时表示是新验证码的宕机
				offline : !data.success, // 表示用户后台检测极验服务器是否宕机，一般不需要关注
				product : "float", // 产品形式，包括：float，popup
				width : "100%"
			// 更多配置参数请参见：http://www.geetest.com/install/sections/idx-client-sdk.html#config
			}, handler1);
		}
	});
}

function checkCaptcha(isClear) {
	var captcha = $("#captcha").val();
	var mobile = $("#mobile").val();
	if (captcha == null || captcha == "") {
		$("#captchaError").html("手机动态码不能为空");
		$("#captchaError").parent(".form-group").addClass("has-error");
		$("#captchaError").show();
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
        dataType: "JSON",
        async : false,
        success : function(resultdata) {
        	if (resultdata.success) {
        		$("#captchaError").html("");
        		$("#captchaError").parent(".form-group").removeClass("has-error");
        		$("#captchaError").hide();
        		flag = true;
            } else {
            	$("#captchaError").html("验证码错误");
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

function checkSure() {
	if(checkCaptcha(1)) {
		$(".third").hide();
		$(".fourth").show();
	}
}

