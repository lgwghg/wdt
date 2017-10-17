$(function() {
	$("#sysHelpBtn").click(function() {
		showHelpInfo("");
	});
});

function showHelpInfo(id) {
	var dataJson = {};
	$.ajax({
		url : _path + "/help",
		type : "POST", 
		dataType : "html",
		success : function(result) {
			$("#centerMain").html(result);
			loadHelpInfo();
			if(id != "" && id != undefined) {
				loadHelpDetail(id);
			}
			// 上一篇
			$("#prveHelp").click(function() {
				nowCount = nowCount - 1;
				clickHelp();
			});
			// 下一篇
			$("#nextHelp").click(function() {
				nowCount = nowCount + 1;
				clickHelp();
			});
		}
	});
}

var nowCount = 0;
var totalCount = 0;
function loadHelpInfo() {
	var options = {};
	options.url = _path + "/help/list";
	options.pageSize = "10";
	var parameters = {};
	parameters.status = "1";
	options.parameters = parameters;

	$.pagination("helpPagination", options, function(data, total) {
		var content = '';
		if(data.length > 0) {
			var entity ;
			for(var i = 0; i<data.length; i++) {
				entity = data[i];
				content += '<li id="'+entity.id+'"><i class="iconfont icon-wf_bangzhu"></i><span class="text">'+entity.title+'</span><span class="time">' + getDifToNow(entity.addTime) + '</span></li>';
			}
		} else {
			content = '<p style="text-align: center; height: 50px; line-height: 27px; padding: 12px;">暂无数据</p>';
		}
		$(".help-list").html(content);
		totalCount = total;
		
		$(".help-list").on("click","li",function() {
	        // 选中的行数，从1开始
	        var count = $(this).index() + 1;
	        var nowPage = options.nowPage;
	        var pageSize = options.pageSize;
	        nowCount = (nowPage - 1) * pageSize + count;
	        loadHelpDetail($(this).attr("id"));
	    });
	    $(document).on("click",".tuibtn",function(){
	        $(this).parents(".details").hide();
	        $(".content-help").show();
	    });
	})
}

function loadHelpDetail(id) {
	var url = _path + "/help/findById";
	var dataJson = {"id":id};
	ajaxMethod(url, dataJson, "post", true, function(backData) {
		if(backData.isSuccess) {
			var entity = backData.data;
			$(".details-header").html('<h3>'+entity.title+'</h3>');
			$(".details-body").html(entity.content);
			$(".content-help").hide();
	        $(".details").show();
		}
	});
}

function clickHelp() {
	if(nowCount <= 0) {
		layer.alert("已经是第一篇啦~", {title:"",icon: 5,time : 1500});
		nowCount = 1;
		return;
	} else if(nowCount > totalCount) {
		layer.alert("已经是最后一篇啦~", {title:"",icon: 5,time : 1500});
		nowCount = totalCount;
		return;
	}
	var url = _path + "/help/list";
	var options = {};
	options.url = _path + "/help/list";
	options.pageSize = "1";
	options.nowPage = nowCount;
	var parameters = {};
	parameters.status = "1";
	options.parameters = parameters;
	
	ajaxMethod(url, {"gridPager":JSON.stringify(options)}, "post", true, function(backData) {
		if(backData.isSuccess) {
			var entity = backData.list[0];
			$(".details-header").html('<h3>'+entity.title+'</h3>');
			$(".details-body").html(entity.content);
		}
	});
}


