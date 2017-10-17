//分页信息
var pageSize_v = 10; //每页显示条数
var startRecord_v = 0;	//开始记录数
var nowPage_v = 1;  //当前页
var pageCount_v = -1;	//总页数
var recordCount_v =-1;	//总记录数

$(function() {
	//事件标签
	loadTaskValue();
	//相关事件
	getRelatedTask(true);
})

//分享 type 1、QQ 2、微博
function share(type, url, title){
	var videoId = $("#videoId").val();
	if("1" == type) {
		window.open('http://connect.qq.com/widget/shareqq/index.html?url='+url+'&title='+title);
	}else if("2" == type){
		window.open('http://service.weibo.com/share/share.php?appkey=&title='+title+'&url='+url);
	}
}

//跳转
function gotoRelated(id, isRefresh) {
	var url = _path + "/task/" + id;
	if("true" == isRefresh) {
		window.location.href = url;
	} else {
		window.open(url,id);
	}
}

/**
 * 事件点赞
 */
function addTaskLike(taskId,ico) {
	if(userId == "" || userId == undefined) {// 登录用户
		window.location.href = _path+"/login?returnUrl="+encodeURIComponent(window.location.href);
		return;
	}
	var status = $("#taskLikeStatus_"+taskId).val();
	if(status == null || status == undefined) {
		status = 1;
	} else {
		status = status == 1?0:1;
	}
	var url = _path + "/task/addOrUpdateLike";
	ajaxMethod(url, {"taskId":taskId, "status":status}, "post", true, function(backData) {
		if(backData.success) {
			$(ico).toggleClass("red");
			var likeCount = $("#likeCount_"+taskId).val();
			if(status == 1){
				$("#taskLikeCount_"+taskId+'_title').html(parseInt(likeCount)+1);
				$("#taskLikeCount_"+taskId).html(parseInt(likeCount)+1);
				$("#likeCount_"+taskId).val(parseInt(likeCount)+1);
			}else{
				$("#taskLikeCount_"+taskId+'_title').html(parseInt(likeCount)-1);
				$("#taskLikeCount_"+taskId).html(parseInt(likeCount)-1);
				$("#likeCount_"+taskId).val(parseInt(likeCount)-1);
			}
			$("#taskLikeStatus_"+taskId).val(status);
		}
	});
}

/**
 * 事件标签
 */
function loadTaskValue() {
	var taskId = $("#taskId").val();
	var url = _path + "/taskValue";
	ajaxMethod(url,{"taskId":taskId},"post",true,function(backData){
		var taskValueList = backData.taskValueList;
		var tagContent = "";
		if (taskValueList && taskValueList.length > 0) {
			for (var i=0; i< taskValueList.length; i++) {
				var taskValue = taskValueList[i];
				var valueName = taskValue.value.name;
				
				tagContent += '<div>';
				tagContent +=     '<i class="iconfont icon-wf_biaoqiantou"></i>';
				tagContent +=     '<span>'+valueName+'</span>';
				tagContent += '</div>';
			}
		}
		$("#taskTagList").html(tagContent);
	})
}

/**
 * 相关事件
 */
function getRelatedTask(first) {
	//分页
	var gridPager = {};
	gridPager.pageSize = pageSize_v;
	gridPager.startRecord = startRecord_v;
	gridPager.nowPage = nowPage_v;
	gridPager.recordCount = recordCount_v;
	gridPager.pageCount = pageCount_v;
	
	var dataJson = {};
	dataJson.id = $("#taskId").val();
	dataJson.upId = $("#upId").val();
	dataJson.startCount = startRecord_v;
	dataJson.pageSize = pageSize_v;
	
	gridPager.parameters = dataJson;
	
	var url = _path + "/task/getRelatedTask";
	ajaxMethod(url,{"gridPager":JSON.stringify(gridPager)},"post",true,function(backData){
		var content = '';
		if(backData.isSuccess) {
			setTimeout(function(){
				var data = backData.exhibitDatas;
				if(data && data.length > 0) {
					var length = data.length;
					var task;
					for(var i=0; i<length; i++) {
						task = data[i];
						content += '<li onclick="gotoRelated(\''+task.id+'\')">';
						content += '	<div class="top">';
						content += '		<i class="iconfont icon-wf_shijianshi"></i>';
						content += '		<span class="one" style="cursor:pointer;">'+task.title+'</span>';
						if(task.commentContent != null && task.commentContent!=""){
							content += '	<span class="two">&nbsp;一&nbsp;'+task.commentContent+'</span>';
						}
						content += '	</div>';
						content += '	<div class="bottom">';
						content += '		<div class="one">';
						if(task.likeStatus == 1){
							content += '		<i class="iconfont icon-zan red" onclick="addTaskLike(\''+task.id+'\',this)"></i>';
						}else{
							content += '		<i class="iconfont icon-zan" onclick="addTaskLike(\''+task.id+'\',this)"></i>';
						}
						content += '			<span class="num">'+task.likeCount+'</span>';
						content += '		</div>';
						content += '		<div class="two">';
						content += '			<span class="first">评论：</span>';
						content += '			<a href="javascript:;" class="num">'+task.commentCount+'</a>';
						content += '		</div>';
						content += '	</div>';
						content += '</li>';
					}
					$("#relatedTask").html(content);
					//相关事件点赞
				    $(".event-content").on("click","li .bottom .one .icon-zan",function(){
				        $(this).toggleClass("red");
				    })
				    startRecord_v = nowPage_v*pageSize_v;
					nowPage_v = backData.nowPage + 1;
				} else {
					$("#relatedTask").html('<li class="no-correlation">暂无相关事件推荐~</li>');
					startRecord_v = 0;
				}
			},200);
		} else {
			$("#relatedTask").html('<li class="no-correlation">暂无相关事件推荐~</li>');
			startRecord_v = 0;
		}
	});
}