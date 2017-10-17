$(function() {
	loadValueLog();
	$(window).unbind("scroll");
	$(window).scroll(function () {
		var totalheight = parseFloat($(window).height())+parseFloat($(window).scrollTop());
		if($(document).height()<= totalheight){
			if (parseInt(options.nowPage) < parseInt(options.pageCount)) {
				options.nowPage = parseInt(options.nowPage) + 1;
				loadValueLog();
			}else{
				//暂无更多数据
			}
		}
	});
});

var options = {};
options.url = _path + '/videoValue/logData';
options.pageSize = "10";
options.nowPage = "1";
function loadValueLog() {
	var videoId = $("#videoId").val();
	var parameters = {};
	parameters.videoId = videoId;
	options.parameters = parameters;
	$.ajax({
		type : 'POST',
		url : _path + '/videoValue/logData',
		data : {"gridPager": JSON.stringify(options)},
		dataType : "json",
		beforeSend : function() {
			var loadContent = "";
			loadContent += '<div class="lb-load">';
			loadContent +=	 '<img src="' + _staticPrefix + '/images/loadding-02.gif" alt="">';
			loadContent += '</div>';
			$(".list-body").append(loadContent);
		},
		success : function(data) {
			options.nowPage = data.nowPage;
			options.pageCount = data.pageCount;
			
			var valueLogList = data.valueLogList;
			var content = "";
			if (valueLogList && valueLogList.length > 0) {
				for (var i = 0; i < valueLogList.length; i++) {
					var valueLog = valueLogList[i];
					var logUser = valueLog.createUser;
					var operateType = valueLog.operateType;
					var operate = "";
					if (operateType == 0) {
						operate = "添加了";
					} else if (operateType == 4) {
						operate = "删除了";
					}
					content += '<div class="lb-item">';
					content += 	'<div class="avatar">';
					content += 		'<img src="' + logUser.photo_40 + '" alt="">';
					content += 	'</div>';
					content += 	'<div class="up">';
					//content += 		'<span class="upz">UP主</span>';
					content += 		'<span class="upname">' + logUser.nickName + '</span>';
					content += 	'</div>';
					content += 	'<div class="shijian">';
					content += 		'<span>' + getFormatDateByLong(valueLog.createTime, 'yyyy-MM-dd hh:mm') + '</span>';
					content += 	'</div>';
					content += 	'<div class="caozuo">';
					content += 		'<span class="act">' + operate + '</span>';
					content += 		'<span class="acttitle">' + valueLog.valueName + '</span>';
					content += 	'</div>';
					content += 	'<div class="jubao">';
					if (valueLog.informStatus == 0) {
						content += 			'<a href="javascript:;" class="informValue" ot="' + operateType + '" vvId = "' + valueLog.videoValueId+ '" id="inform_' + valueLog.videoValueId+ '">举报</a>';
					} else if (valueLog.informStatus == 1) {
						content += 			'<a href="javascript:;" class="avisited">已举报</a>';
					} else if (valueLog.informStatus == 2) {
						content += 			'<a href="javascript:;" class="avisited">举报已处理</a>';
					}
					content += 	'</div>';
					content += '</div>';
				} 
			} else {
				content += '<div class="nonum">';
				content +=    '<div class="chaoxi">';
				content +=        '<img src="' + _staticPrefix + '/images/chaoxi.png" alt="">';
				content +=   '</div>';
				content +=    '<p>暂无数据</p>';
				content += '</div>';
			}
			$(".list-body").find(".lb-load").remove();
			$(".list-body").append(content);
			
			initEvent();
		}
	});
}

function initEvent() {
	$(".jubao .informValue").off("click");
	var flag =true;
	// 举报点击
	$(".jubao .informValue").one("click",function(){
		if (!user) {
			var videoId = $("#videoId").val();
			var url = _path + '/videoValue/log?videoId=' + videoId;
			location.href = _path + "/login?returnUrl=" + encodeURI(url);
			return;
		}
		
		var btnTop = $(this).offset().top;
		var _this = $(this);
		var operateType = _this.attr("ot");
		var vvId = _this.attr("vvId");
		var tanContent = '<div class="jubaokuang">'+'<div class="header">'+
		'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="iconfont icon-wf_guanbi"></i></button>'+
		'<h3>请选择举报理由</h3></div>'+
		'<div class="form-group">';
			if (operateType == 0) {
				tanContent += '<div class="radio"><label><input type="radio" name="informReason" value="0" checked="">内容不相关</label></div>'+
				'<div class="radio"><label><input type="radio" name="informReason" value="1">敏感信息</label></div>'+
				'<div class="radio"><label><input type="radio" name="informReason" value="2">恶意攻击</label></div>'+
				'<div class="radio"><label><input type="radio" name="informReason" value="3">剧透内容</label></div>';
			} else if (operateType == 4) {
				tanContent += '<div class="radio"><label><input type="radio" name="informReason" value="4" checked="">恶意删除</label></div>';
			}
		tanContent += '</div>'+
		'<div class="btn-group">'+
		'<button class="btn confirm" type="button" ot="' + operateType + '" vvId="' + vvId + '">确认</button></div></div>';
		_this.parent().append(tanContent);
        $.material.init();
        
        if(btnTop  > $(window).height()*3/4){
            _this.parent().find(".jubaokuang").addClass("jubaokuangbottom");
        }
        
        // 确认按钮
        _this.parent().find(".confirm").click(function() {
        	var confirm = $(this);
        	var informReason = confirm.closest(".jubaokuang").find("input:radio[name='informReason']:checked").val();
        	informVideoValue(vvId, informReason);
        });
        
        _this.parent().find(".close").off("click");
        _this.parent().on("click",".close",function(){
	        $(this).closest(".jubaokuang").hide();
	        $(this).closest(".jubao").find(".informValue").removeClass("avisited");
	        flag = true;
	    });
    });
	$(".jubao .informValue").on("click",function(){
		if (!user) {
			var videoId = $("#videoId").val();
			var url = _path + '/videoValue/log?videoId=' + videoId;
			location.href = _path + "/login?returnUrl=" + encodeURI(url);
		}
        $(this).closest(".lb-item").siblings().find(".jubaokuang").hide();
        if(flag){
            $(this).parent().find(".jubaokuang").show(); 
            $(this).addClass("avisited");
            flag = false;
        }else{
            $(this).parent().find(".jubaokuang").hide();
            $(this).removeClass("avisited");
            flag = true;
        }
        
    }); 
}
function informVideoValue(videoValueId, informReason) {
	if (user) {
		if (!informReason) {
			layer.alert("请选择举报原因", {title:"",icon: 5,time: 2000});
			return;
		}
		$.ajax({
			type : 'POST',
			url : _path + '/videoValue/u/inform',
			data : {
				videoValueId : videoValueId,
				informReason : informReason
			},
			dataType : "json",
			success : function(data) {
				if (data.success) {
					$("#inform_" + videoValueId).parent(".jubao").find(".close").click();
					$("#inform_" + videoValueId).html("已举报").addClass("avisited").removeClass("informValue");
					$("#inform_" + videoValueId).off("click");
				} else {
					layer.alert(data.message, {title:"",icon: 5,time: 2000});
				}
			}
		});
	} else {
		var videoId = $("#videoId").val();
		var url = _path + '/videoValue/log?videoId=' + videoId;
		location.href = _path + "/login?returnUrl=" + encodeURI(url);
	}
}