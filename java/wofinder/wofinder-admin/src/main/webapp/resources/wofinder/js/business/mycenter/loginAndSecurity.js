$(function() {
	$("#loginAndSecurityBtn").click(function() {
		loadLoginAndSecurityList();
	});
	$("#deviceBtn").click(function() {
		loadLoginDeviceList();
	});
});
function loadLoginAndSecurityList() {
	//调用后台   
	$.ajax( {
		url : _path + "/my/loginAndSecurity",
		type : "POST", 
		dataType : "json",
		success : function(result) {
			if(result && result.loginInfoList){
				var loginInfoList = result.loginInfoList;
				var loginInfoLength = loginInfoList.length;
				var loginInfoContent = "";
				if (loginInfoLength > 0) {
					loginInfoContent += '<div class="safety module" id="safety">';
					loginInfoContent +=		'<div class="list">';
					loginInfoContent +=			'<div class="list-header">';
					loginInfoContent +=			 	'<span class="one">登录IP</span>';
					loginInfoContent +=			    '<span class="two">地址</span>';
					loginInfoContent +=			    '<span class="three">时间</span>';
					loginInfoContent +=			'</div>';
					loginInfoContent +=			'<ul class="list-content">';
					for (var i = 0; i < loginInfoLength; i++) {
						loginInfo = loginInfoList[i];
						var province = loginInfo.province;
						var city = loginInfo.city;
						if (province == '0') {
							province = '';
						}
						if (city == '0') {
							city = '';
						}
						loginInfoContent +=            '<li>';
						loginInfoContent +=                '<span class="one">' + loginInfo.loginIp + '</span>';
						loginInfoContent +=                '<span class="two">' + province + city +'</span>';
						loginInfoContent +=                '<span class="three">' + loginInfo.loginTime + '</span>';
						loginInfoContent +=                '<div class="four">';
						loginInfoContent +=                    '<span>不是我</span>';
						loginInfoContent +=                '</div>';
						loginInfoContent +=            '</li>';
					}
					loginInfoContent +=			'</ul>';
					loginInfoContent +=		'</div>';
					loginInfoContent += '</div>';
				}
				$("#centerMain").html(loginInfoContent);
				
				$(".list-content .four").click(function() {
					layer.confirm('不是你登录？', {icon : 0, title : "提示",
						  btn: ['取消','去修改密码'] //按钮
						}, function(index){
							layer.close(index);
						}, function(){
						  $("#left_pwdEditBtn").click();
						});
				});
			}
		},error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
	});
}

function loadLoginDeviceList() {
	//调用后台   
	$.ajax( {
		url : _path + "/my/loginAndSecurity",
		type : "POST", 
		dataType : "json",
		success : function(result) {
			if(result && result.loginInfoList){
				var loginInfoList = result.loginInfoList;
				var loginInfoLength = loginInfoList.length;
				var loginInfoContent = "";
				if (loginInfoLength > 0) {
					loginInfoContent += '<div class="content-sb">';
					for (var i = 0; i < loginInfoLength; i++) {
						loginInfo = loginInfoList[i];
						var deviceClass = "window";
						if (loginInfo.device == "Windows") {
							deviceClass = "window";
						} else if (loginInfo.device == "iPhone") {
							deviceClass = "phone";
						}  else if (loginInfo.device == "iPad") {
							deviceClass = "pad";
						}
						var loginDate = new Date(loginInfo.loginTime);
						var loginTime = '';
						if (loginDate) {
							loginTime = loginDate.getTime();
						}
						var device = loginInfo.device;
						if (device == null) {
							device = '';
						}
						if (i == 0) {
							loginInfoContent +=     '<div class="content-item"><div class="iconwrap"><span class="' + deviceClass + '"></span><span class="text">' + device + '</span></div><span class="description active">' + '当前使用设备' + '</span></div>';
						} else {
							loginInfoContent +=     '<div class="content-item"><div class="iconwrap"><span class="' + deviceClass + '"></span><span class="text">' + device + '</span></div><span class="description">' + getDifToNow(loginTime) + '</span></div>';
						}
					}
					loginInfoContent += '</div>';
				}
				$("#centerMain").html(loginInfoContent);
			}
		},error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
	});
}