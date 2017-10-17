$(function() {
	loadVideoValue();
	//合集展开收缩添加标签输入框
    $(".search-content-left .compilations .label-x div.add").on("click",function(){
        $(this).addClass("hide").siblings(".form-group ").removeClass("hide").css("width","24px").animate({
            "width":"200px"
        },388);
        $("#videoValueName").focus();
    });
    $(".search-content-left .compilations .label-x div.form-group i").on("click",function(){
        var _this = $(this);
        _this.parent().animate({
            "width":"24px"
        },388,function(){
            _this.parent().addClass("hide").siblings(".add ").removeClass("hide");
        })
    });
    
    
    // 回车保存新增标签
    $('#videoValueName').bind("keydown", function(event) {
		if(event.keyCode==13) {
	        addVideoValue();
	    }
	});
});
function initLebalEvent() {
	$(".search-content-left .compilations .label-x").off("mouseenter,mouseleave", ".label-x-inside:not('.add')");
	
	//鼠标滑过显示操作
	//鼠标滑过显示操作
    var timerLabelX;
    var timerLabelX2;
    $(".search-content-left .compilations .label-x").on("mouseenter", ".label-x-inside:not('.add')", function () {
        clearTimeout(timerLabelX2);
        var _this = $(this);
        var tagName = _this.find(".label-x-inside-content").text();
        var videoValueId = _this.find(".label-x-inside-content").attr('videoValueId');
        var createId = _this.find(".label-x-inside-content").attr('createId'); 
        //var informStatus = _this.find(".label-x-inside-content").attr('informStatus');
        var informStatus = $("#tag_" + videoValueId + " .label-x-inside-content").attr("informStatus")
        $("#labelSetting .team-one .top-title").html(tagName);
        if(_this.find("#labelSetting").length != 1){
            $("#labelSetting .team-one").removeClass("hide");
            $("#labelSetting").attr("videoValueId", videoValueId);
            // 删除按钮处理
            if (userId) {
            	if (userId != createId) {
            		$("#labelSetting .team-one .delete").hide();
            	} else {
            		$("#labelSetting .team-one .delete").show();
            	}
            } else {
            	$("#labelSetting .team-one .delete").show();
            }
            
            // 举报按钮处理
            if (informStatus) {
    			if (informStatus == 1) {
    				$("#labelSetting .team-one .informBtn").html("已举报");
    				$("#labelSetting .team-one .informBtn").removeClass("report");
    			}else if (informStatus == 2) {
    				$("#labelSetting .team-one .informBtn").html("举报已处理");
    				$("#labelSetting .team-one .informBtn").removeClass("report");
    			} else {
    				$("#labelSetting .team-one .informBtn").html("举报");
    				$("#labelSetting .team-one .informBtn").addClass("report");
    			}
            } else {
            	$("#labelSetting .team-one .informBtn").html("举报");
            	$("#labelSetting .team-one .informBtn").addClass("report");
            }
            /*$(".search-content-left .compilations .label-x .label-x-inside").on("click","#labelSetting .team-one .top .delete-report .report",function(){
            	// 举报标签
                $(this).parents(".team-one").addClass("hide").siblings(".team-two").removeClass("hide");
                $(this).parents(".team-one").siblings(".team-two").find(".yes").click(function() {
                	var videoValueId = $("#labelSetting").attr("videoValueId");
                	var informReason = $(this).parents(".team-two").find("input:radio[name='informReason']:checked").val();
                	informVideoValue(videoValueId, informReason);
                });
            });*/
            
            $("#labelSetting .team-two,#labelSetting .team-three").addClass("hide");
        }
        if(_this.find("#labelSetting").length == 1 && _this.find("#labelSetting").hasClass("hide") != true){
            return false;
        }
        timerLabelX = setTimeout(function () {
            _this.append($("#labelSetting"));
            $("#labelSetting").removeClass("hide").css("opacity","0").animate({
                "opacity":"1"
            },288)
            queryUpVideoValue(videoValueId, _this);
        }, 288);
        
        
    }).on("mouseleave", ".label-x-inside:not('.add')", function () {
        clearTimeout(timerLabelX);
        timerLabelX2 = setTimeout(function () {
            $("#labelSetting").animate({
                "opacity":"0"
            },288,function(){
                $("#labelSetting").addClass("hide");
            })
        }, 288);
    });
    
    $(".search-content-left .compilations .label-x .label-x-inside").off("click","#labelSetting .team-one .top .delete-report .delete")
    .off("click","#labelSetting .team-one .top .delete-report .report")
    .off("click","#labelSetting .team-three .bottom button.no")
    .off("click","#labelSetting .team-two .top .icon-x")
    .off("click","#labelSetting .team-one .bottom .icon-wf_dianzan");
    //点击举报
    $(".search-content-left .compilations .label-x .label-x-inside").on("click","#labelSetting .team-one .top .delete-report .delete",function(){
        // 删除标签
    	$(this).parents(".team-one").addClass("hide").siblings(".team-three").removeClass("hide");
    	$(this).parents(".team-one").siblings(".team-three").find(".yes").off("click");
        $(this).parents(".team-one").siblings(".team-three").find(".yes").click(function() {
        	var videoValueId = $("#labelSetting").attr("videoValueId");
        	deleteVideoValue(videoValueId);
        });
    }).on("click","#labelSetting .team-one .top .delete-report .report",function(){
    	// 举报标签
        $(this).parents(".team-one").addClass("hide").siblings(".team-two").removeClass("hide");
        $(this).parents(".team-one").siblings(".team-two").find(".yes").off("click");
        $(this).parents(".team-one").siblings(".team-two").find(".yes").click(function() {
        	var videoValueId = $("#labelSetting").attr("videoValueId");
        	var informReason = $(this).parents(".team-two").find("input:radio[name='informReason']:checked").val();
        	informVideoValue(videoValueId, informReason);
        });
    }).on("click","#labelSetting .team-three .bottom button.no",function(){
        $(this).parents(".team-three").addClass("hide").siblings(".team-one").removeClass("hide");
    }).on("click","#labelSetting .team-two .top .icon-x",function(){
        $(this).parents(".team-two").addClass("hide").siblings(".team-one").removeClass("hide");
    }).on("click","#labelSetting .team-one .bottom .icon-wf_dianzan",function(){
    	// 点赞标签
    	var videoValueId = $("#labelSetting").attr("videoValueId");
    	upVideoValue(videoValueId);
    	
    });
}
/**
 * 异步加载视频标签
 */
function loadVideoValue() {
	var videoId = $("#videoId").val();
	$.ajax({
		type : 'POST',
		url : _path + '/videoValue',
		data : {"videoId": videoId},
		dataType : "json",
		beforeSend : function() {
		},
		success : function(data) {
			var videoValueList = data.videoValueList;
			var tagContent = "";
			if (videoValueList && videoValueList.length > 0) {
				for (var i=0; i< videoValueList.length; i++) {
					var videoValue = videoValueList[i];
					var videoValueId = videoValue.id;
					var value = videoValue.value;
					var valueName = value.name;
					var upNum = videoValue.upNum;
					var createId = '';
					
					if (videoValue.createUser) {
						createId = videoValue.createUser.id;
					}
					var informStatus = videoValue.informStatus;
					
					tagContent += '<div class="label-x-inside" id="tag_' + videoValueId + '">';
					tagContent +=     '<i class="iconfont icon-wf_biaoqiantou"></i>';
					tagContent +=     '<span class="label-x-inside-content" upNum="' + upNum + '" videoValueId="' + videoValueId + '" createId = "' + createId + '" informStatus="' + informStatus + '">' + valueName + '</span>';
					tagContent += '</div>';
				}
			}
			
			$(".label-x").prepend(tagContent);
			$.material.init();
			initLebalEvent();
		}
	});
}



/**
 * 查询标签，点赞数量和用户是否点赞
 * @param videoValueId
 * @returns
 */
function queryUpVideoValue(videoValueId, _this) {
	if (!user) {
		var upNum = _this.find(".label-x-inside-content").attr('upNum');
	    $("#labelSetting .team-one .bottom .two .first").html(upNum);
		$("#labelSetting .team-one .bottom .icon-wf_dianzan").removeClass("red");
		return;
	}
	$.ajax({
		type : 'POST',
		url : _path + '/videoValue/u/queryUp',
		data : {"videoValueId" : videoValueId},
		dataType : "json",
		beforeSend : function() {
		},
		success : function(data) {
			$("#labelSetting .team-one .bottom .two .first").html(data.upNum);
			if (data.isUp == 1) {
				$("#labelSetting .team-one .bottom .icon-wf_dianzan").addClass("red");
			} else {
				$("#labelSetting .team-one .bottom .icon-wf_dianzan").removeClass("red");
			}
		}
	});
}
/**
 * 添加标签
 * @returns
 */
function addVideoValue() {
	if (!user) {
		var videoShortId = $("#videoShortId").val();
		var url = _path + '/video/' + videoShortId;
		location.href = _path + "/login?returnUrl=" + encodeURI(url);
		return;
	}
	var videoId = $("#videoId").val();
	var videoValueName = $.trim($("#videoValueName").val());
	if (videoValueName.length < 2 || videoValueName.length > 20) {
		layer.alert("标签长度应在2-20个字符之间", {title:"",icon: 5,time: 2000});
		return;
	}
	if (!/^[a-zA-Z0-9_\u4e00-\u9fa5]+$/.test(videoValueName)) {
		layer.alert("标签只能字母、数字或汉子组成", {title:"",icon: 5,time: 2000});
		return;
	}
	$.ajax({
		type : 'POST',
		url : _path + '/videoValue/u/add',
		data : {"videoId" : videoId, "tagName" : videoValueName},
		dataType : "json",
		beforeSend : function() {
		},
		success : function(data) {
			if (data.success) {
				$("#videoValueName").val('');
				// 标签放页面
				var tagContent = "";
				tagContent += '<div class="label-x-inside" id="tag_' + data.videoValueId + '">';
				tagContent +=     '<i class="iconfont icon-wf_biaoqiantou"></i>';
				tagContent +=     '<span class="label-x-inside-content" upNum="0" videoValueId="' + data.videoValueId + '" createId = "' + data.createId + '" informStatus="0">' + videoValueName + '</span>';
				tagContent += '</div>';
				$(".label-x .add").before(tagContent);
				initLebalEvent();
				// 点击关闭输入框
				$(".search-content-left .compilations .label-x div.form-group i").click();
			} else {
				layer.alert(data.message, {title:"",icon: 5,time: 2000});
			}
		}
	});
}

/**
 * 删除标签
 * @param videoValueId
 * @returns
 */
function deleteVideoValue(videoValueId) {
	if (!user) {
		var videoShortId = $("#videoShortId").val();
		var url = _path + '/video/' + videoShortId;
		location.href = _path + "/login?returnUrl=" + encodeURI(url);
		return;
	}
	$.ajax({
		type : 'POST',
		url : _path + '/videoValue/u/delete',
		data : {"videoValueId" : videoValueId},
		dataType : "json",
		beforeSend : function() {
		},
		success : function(data) {
			if (data.success) {
				$("body").append($("#labelSetting").addClass("hide"))
				$("#tag_" + videoValueId).remove();
			} else {
				layer.alert(data.message, {title:"",icon: 5,time: 2000});
			}
		}
	});
}

/**
 * 点赞或取消点赞
 * @param videoValueId
 * @returns
 */
function upVideoValue(videoValueId) {
	if (!user) {
		var videoShortId = $("#videoShortId").val();
		var url = _path + '/video/' + videoShortId;
		location.href = _path + "/login?returnUrl=" + encodeURI(url);
		return;
	}
	$.ajax({
		type : 'POST',
		url : _path + '/videoValue/u/up',
		data : {"videoValueId" : videoValueId},
		dataType : "json",
		beforeSend : function() {
		},
		success : function(data) {
			if (data.success) {
				$("#labelSetting .team-one .bottom .two .first").html(data.upNum);
				var _this = $("#labelSetting .team-one .bottom .icon-wf_dianzan");
		        if(_this.hasClass("red") != true){
		            _this.siblings(".two").find(".second").removeClass("hide").css("opacity","0").animate({
		                "opacity":"1",
		                "bottom": "27px"
		            },288,function(){
		                _this.siblings(".two").find(".second").css("bottom","19px").addClass("hide");
		            })
		        }
		        _this.toggleClass("red");
			} else {
				layer.alert(data.message, {title:"",icon: 5,time: 2000});
			}
		}
	});
}

function informVideoValue(videoValueId, informReason) {
	if (user) {
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
					//layer.msg("举报已提交");
					$("#tag_" + videoValueId + " .label-x-inside-content").attr("informStatus", 1);
					$("#labelSetting .team-one .informBtn").html("已举报");
    				$("#labelSetting .team-one .informBtn").removeClass("report");
					$("#labelSetting .team-two .top .icon-x").click();
					
				} else {
					layer.alert(data.message, {title:"",icon: 5,time: 2000});
				}
			}
		});
	} else {
		var shortId = $("#videoShortId").val();
		var url = _path + '/video/' + shortId;
		location.href = _path + "/login?returnUrl=" + encodeURI(url);
	}
}