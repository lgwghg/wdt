$(function() {
	if (userId) {
		var myDate = new Date();
		$("#signCalender").html(myDate.getDate());
		isSign();
	}
	$("#signButton").click(function() {
		var buttonText = $.trim($(this).text());
		if (buttonText == '签到') {
			userSign();
		} else if (buttonText == '个人中心') {
			window.location.href = "/my";
		}
	});
});
// 验证用户今天是否已经签到
function isSign() {
	$.ajax({
		type : "POST",
		url : _path + "/my/isSign",
		dataType : "json",
		success : function(data) {
			if (data >= 0) { // 已经签到
				$("#signButton").html("个人中心");
				$("#signButton").addClass("yqd");
				//$("#signButton").attr("disabled", true);
			    $("#signIntegral").html("签到+" + data);
			}else{
				$("#signButton").html("签到");
				$("#signButton").removeClass("yqd");
				//$("#signButton").removeAttr("disabled");
				$("#signIntegral").html("签到");
			}
		},
		complete : function() {
			
		}
	});
}
// 用户签到
function userSign() {
	$.ajax({
		type : "POST",
		url : _path + "/my/sign",
		dataType : "json",
		success : function(data) {
			if (data > 0) {
				$("#signButton").html("个人中心");
				$("#signButton").addClass("yqd");
				//$("#signButton").attr("disabled", true);
				$("#signIntegral").html("签到+" + 5);
			} else if (data == -1) {
			}
		},
		complete:function(XMLHttpRequest,textStatus) {
	 		/*var status = XMLHttpRequest.getResponseHeader("session_status");  
	 		if(status == "timeout") {
	        } */ 
	    }
	});
}