//分页信息
var pageMap = {};

$(function(){
	var videoList = $("input[id^='video_']");  
	for(var i = 0;i < videoList.length;i++){
		var videoId = $(videoList[i]).val();
		pageMap[videoId] = {pageSize:10,startRecord:0,nowPage:1,recordCount:-1,pageCount:-1,lastPage:false};
		//加载评论列表
		loadCommentList(videoId,true);
	}
	
	//评论初始化控件
	comment_init();
    
    //开启评论监听
    commentSocket();
});

//评论初始化控件
function comment_init(){
	
	//评论框
	if($("#userPhoto_65").val() == "") {// 用户没有上传头像
		var str = getPinY($("#userPhoto_65").val());
		if(str != "" && str != undefined) {
			$("span[id^=myZM_]").each(function(){
				$(this).text(str);
				$(this).removeClass("hide");
		  	});
			$("img[id^=myPhoto65_]").each(function(){
				$(this).addClass("hide");
		  	});
		} else {
			$("img[id^=myPhoto65_]").each(function(){
				$(this).attr("src",_staticPrefix +"/images/mr-header.png");
		  	});
			$("span[id^=myZM_]").each(function(){
				$(this).addClass("hide");
		  	});
		}
	}
		
	// 滑轮
	$(".comment-yes").mCustomScrollbar({
        callbacks:{onTotalScroll:function(){
        	var videoId = $(this).parents("li[id^='video_li_']").find("input[id^='video_']").val();
        	if(!pageMap[videoId].lastPage) {
        		$(this).find(".load-more").removeClass("hide");
        		$(this).mCustomScrollbar("scrollTo","bottom");
        		loadCommentList(videoId,false);
        	}
        }}
    });
	
	//enter组合发送
	$('.comment-fb').unbind("keydown").on('keydown',function (e){  
		e = e||event;
	     if (e.keyCode == 13)  {  
	    	 e.preventDefault();
	    	 addComment($(this));
	    }  
	});
	
	//点击回车图标发送
	$('.icon-wf_huiche').unbind("click").on('click',function (e){  
		addComment($(this));
	});
	
	//鼠标经过显示点赞
    $(".comment-yes .comment-inside").unbind("mouseenter").on("mouseenter","li .comment-content",function(){
        $(this).find(".right .top .time").addClass("hide");
        $(this).find(".right .top .praise").removeClass("hide");
    }).on("mouseleave","li .comment-content",function(){
        $(this).find(".right .top .time").removeClass("hide");
        $(this).find(".right .top .praise").addClass("hide");
    }).on("click","li .comment-content",function(){//回复
    	var videoId = $(this).parents("li[id^='video_li_']").find("input[id^='video_']").val();
    	$(this).parents(".comment-yes").siblings(".comment-fb-outside").find(".hf-x").removeClass("hide").find(".name").html($(this).find(".name").html());
    	$("#comment-content_"+videoId).attr("commentId",$(this).find(".comment_id").val());
    	$("#comment-content_"+videoId).attr("commentNickName", $(this).find(".name").html());
    	$(this).parents(".comment-yes").siblings(".comment-fb-outside").find("input").focus();
    }).on("click","li .comment-content .right .top .praise",function(e){//点赞
    	var a = $(this);
        var num = a.siblings(".comment_num").val();
        var timer = 1500;
        if(a.hasClass("praise-yes") != true){// 未点赞
            a.siblings(".add").animate({
                "opacity":"1"
            },888,function(){
                a.siblings(".add").animate({
                    "top":"-20px",
                    "opacity":"0"
                },888)
            });
            num = Number(num) + 1;
        } else {
        	num = Number(num) - 1;
        	timer = 0;
        }
        a.siblings(".comment_num").val(num);
        setTimeout(function() {
        	num = a.siblings(".comment_num").val();
        	if(num == 0) {
	        	a.siblings(".praise-number").text("");
	        } else {
	        	a.siblings(".praise-number").text("赞"+getCountStr(num));
	        }
        }, timer);
        a.toggleClass("praise-yes");
        e.stopPropagation();
    })
}

function commentCss() {
    //取消回复评论
    $(".search-content-left .comment-fb-outside .hf-x").on("click",function(){
    	var videoId = $(this).parents("li[id^='video_li_']").find("input[id^='video_']").val();
        $(this).addClass("hide");
        $("#comment-content_"+videoId).attr("commentId","");
        $("#comment-content_"+videoId).attr("commentNickName", "");
    })
}

//评论列表
function loadCommentList(videoId,first){
	//分页
	var gridPager = {};
	gridPager.pageSize = pageMap[videoId].pageSize;
	gridPager.startRecord = pageMap[videoId].startRecord;
	gridPager.nowPage = pageMap[videoId].nowPage;
	gridPager.recordCount = pageMap[videoId].recordCount;
	gridPager.pageCount = pageMap[videoId].pageCount;

	var dataJson = {};
	dataJson.videoId = videoId;
	
	gridPager.parameters = dataJson;
	
	var url = _path + "/videoComment/searchList";
	
	ajaxMethod(url, {"gridPager":JSON.stringify(gridPager)}, "post", true, function(backData) {
		if (backData.isSuccess) {
			if(backData.exhibitDatas) {
				var contentyes = "";
				var length = backData.exhibitDatas.length;
				for(var i=0; i<length; i++) {
					var videoComment = backData.exhibitDatas[i];
					var id = videoComment.id;
					var commentId = videoComment.commentId;
					var commentContent = videoComment.commentContent;
					var commentUserName = videoComment.commentUserName;
					var commentCreatetime = videoComment.commentCreatetime;
					var station = videoComment.station.value;
					var stationClass = videoComment.station.description;
					var likeNum = videoComment.likeNum;
					var likeStatus = videoComment.likeStatus;
					var photo = '';
					var parentNickName = '';
					if(!judge(commentUserName) && commentUserName!='') {// 评论人
						commentUserName = videoComment.createUser.nickName;
					}
					if(!judge(commentCreatetime)) {// 评论时间
						commentCreatetime = videoComment.createTime;
					}
					if(videoComment.createUser) {
						photo = videoComment.createUser.photo_40;
					}
					if(videoComment.commentParent) {
						parentNickName = videoComment.commentParent.commentUserName;
					}
					contentyes += getCommentYesDiv(id , commentId, commentContent, commentUserName, commentCreatetime, station, stationClass,likeNum,likeStatus, parentNickName,photo);
				}
				var contentno = "";
				var length = backData.exhibitDatas.length;
				for(var i=0; i<length && i < 1; i++) {
					var videoComment = backData.exhibitDatas[i];
					var nickName = videoComment.commentUserName;
					var station = videoComment.station.value;
					var stationClass = videoComment.station.description;
					var commentContent = videoComment.commentContent;
					var photo = '';
					if(!judge(nickName)) {// 评论人
						nickName = videoComment.createUser.nickName;
					}
					if(videoComment.createUser) {
						photo = videoComment.createUser.photo_40;
					}
					contentno += getCommentNoDiv(commentContent, nickName, photo ,station ,stationClass);
				}
				
				var pageCount = backData.pageCount;
				if(pageCount <= pageMap[videoId].nowPage) {// 最后一页
					$("#loadMore_"+videoId).addClass("hide")
					pageMap[videoId].lastPage = true;
					if(length <= 0) {
						contentyes += '<li class="no-comment">没有新的评论~</li>';
					} else if(!first || length <= pageMap[videoId].pageSize){
						contentyes += '<li class="load-more">没有更多评论</li>';
					}
				} else if(first) {
					contentyes += '<div id="loadMore_'+videoId+'" class="load-more"><img src="'+_staticPrefix+'/images/load-more.gif">正在加载...</div>';
				}
				if(first){
					if(contentno==''){
						contentno += '<div class="comment-fb-outside">';
						contentno += 	'<div class="header images-x images-x-content">';
						contentno += 		'<img id="myPhoto65_no_'+videoId+'" src="'+$("#userPhoto_65").val()+'">';
						contentno += 		'<span id="myZM_no_'+videoId+'" class="hide">Z</span>';
						contentno += 	'</div>';
						contentno += 	'<div class="hf-x hide">';
						contentno += 		'<span>回复：</span>';
						contentno += 		'<span class="name"></span>';
						contentno += 	'</div>';
						contentno += 	'<input type="text" id="comment-content_'+videoId+'" class="comment-fb" placeholder="添加评论...">';
						contentno += 	'<i class="iconfont icon-wf_huiche iconfont-x"></i>';
						contentno += '</div>';
						$("#video_li_"+videoId+" .comment-no").html(contentno);	
					}else{
						$("#video_li_"+videoId+" .comment-no .comment-inside").html(contentno);	
					}
					$("#video_li_"+videoId+" .comment-yes .comment-inside").html(contentyes);
				} else {
					$("#video_li_"+videoId+" .comment-yes .comment-inside").append(contentyes);
					$("#loadMore_"+videoId).appendTo($("#video_li_"+videoId+" .comment-yes .comment-inside"));
				}
				
				$("#firstComment").addClass("hide");
				$("#secondComment").removeClass("hide");
				commentCss();
			} else {
				$("#video_li_"+videoId+" .comment-yes .comment-inside").html('<li class="no-comment">没有新的评论~</li>');
			}
			// 下一页
			pageMap[videoId].nowPage = backData.nowPage+1;
			comment_init();
		} else {
			$("#video_li_"+videoId+" .comment-yes .comment-inside").html('<li class="no-comment">没有新的评论~</li>');
		}
	});
}

var canSubmit = true;
//发布评论
function addComment(element) {
	if(userId == "" || userId == undefined) {// 登录用户
		window.location.href = _path+"/login?returnUrl="+encodeURIComponent(window.location.href);
		return;
	}
	if(!canSubmit) {
		layer.alert("评论太快，请稍后再试~", {title:"",icon: 6,time: 1000});
		return;
	}
	
	var videoId = $(element).parents("li[id^='video_li_']").find("input[id^='video_']").val();
	var c_content = $("#comment-content_"+videoId).val();
	if(trim(c_content)==''){
		layer.alert("请先填写内容再发表评论", {title:"",icon: 5,time: 1000});
		return false;
	}
	if(strLength(c_content)>480){
		layer.alert("评论内容不能超过240字", {title:"",icon: 5,time: 1000});
		return false;
	}
	var pId = $("#comment-content_"+videoId).attr("commentId");
	var parentNickName = $("#comment-content_"+videoId).attr("commentNickName");
	var dataJson = {};
	dataJson.commentContent = c_content;
	dataJson["video.id"] = videoId;
	dataJson["commentParent.commentId"] = pId;
	dataJson["commentParent.commentUserName"] = parentNickName;
	
	var url = _path + "/videoComment/add";
	ajaxMethod(url, dataJson, "post", true, function(backData) {
		if (backData.success) {
			canSubmit = false;
			setTimeout(function() {
				canSubmit = true;
			}, 5000);
			
			$("#comment-content_"+videoId).val("");
			layer.msg("评论成功，您的评论已直达首页~", {title:"",icon: 6,time: 1000});
			commentCss();
		} else {
			layer.alert(backData.message, {title:"",icon: 5,time: 1000});
		}
	});
}

//获取展开评论
function getCommentYesDiv(id,commentId, commentContent, commentUserName, commentCreatetime, station, stationClass,likeNum,likeStatus, parentNickName,photo) {
	var content = '';
	content += '<li><div class="comment-content comment-one">';
	content += '<div class="header images-x images-x-content">';
	var zm = getPinY(commentUserName);
	if(station != null && "0" != station) {// 不是本站
		if(zm == "") {
			zm = "A";
		}
		content += '<span class="no_header no_header-'+zm+'"><i class="iconfont '+stationClass+'"></i></span>';
	} else if(judge(photo)) {// 有个人头像
		content += '<img src="'+photo+'">';
	} else {
		if(zm == "") {
			content += '<img id="myPhoto65" src="'+_staticPrefix+'/images/mr-header.png">';
		} else {
			content += '<span class="no_header no_header-'+zm+'">'+zm+'</span>';
		}
	}
	content += '</div>';
	content += '<div class="right">';
	content += '<div class="top"><input type="hidden" class="comment_num" value="'+likeNum+'"/>';
	content += '<input type="hidden" class="comment_id" value="'+commentId+'"/>';
	content += '<input type="hidden" id="likeStatus_'+id+'" value="'+likeStatus+'"/>';
	content += '<span class="name">'+commentUserName+'</span>';
	content += '<span class="praise-number" style="color:#DB4437;">'+(likeNum>0?('赞'+getCountStr(likeNum)):'')+'</span>';
	content += '<span class="add">+1</span>';
	content += '<span class="time">'+getDifToNow(commentCreatetime)+'</span>';
	var praise = "";
	if(likeStatus == 1) {
		praise = "praise-yes";
	}
	content += '<div class="praise hide '+praise+'" id="praise_'+id+'" onclick="addLike(\''+id+'\');event.stopPropagation();">';
	content += '<i class="iconfont icon-wf_dianzan"></i>';
	content += '</div>';
	content += '</div>';
	if(judge(parentNickName)) {// 回复XXX
		content += '<div class="bottom"><span>回复</span><span class="hf">'+parentNickName+'</span>：'+commentContent+'</div>';
	} else {
		content += '<div class="bottom">'+commentContent+'</div>';
	}
	content += '</div>';
	content += '</div></li>';
	
	return content;
}

function addLike(commentId) {
	if(userId == "" || userId == undefined) {// 登录用户
		window.location.href = _path+"/login?returnUrl="+encodeURIComponent(window.location.href);
		return;
	}
	var status = $("#likeStatus_"+commentId).val();
	if(status == null || status == undefined) {
		status = 1;
	} else {
		status = status == 1?0:1;
	}
	var url = _path + "/videoComment/addOrUpdateLike";
	ajaxMethod(url, {"commentId":commentId, "status":status}, "post", true, function(backData) {
		if(backData.success) {
			var a = $("#praise_"+commentId);
	        var num = a.siblings(".comment_num").val();
	        var timer = 1500;
	        if(a.hasClass("praise-yes") != true){// 未点赞
	            a.siblings(".add").animate({
	                "opacity":"1"
	            },888,function(){
	                a.siblings(".add").animate({
	                    "top":"-20px",
	                    "opacity":"0"
	                },888)
	            });
	            num = Number(num) + 1;
	        } else {
	        	num = Number(num) - 1;
	        	timer = 0;
	        }
	        a.siblings(".comment_num").val(num);
	        setTimeout(function() {
	        	num = a.siblings(".comment_num").val();
	        	if(num == 0) {
		        	a.siblings(".praise-number").text("");
		        } else {
		        	a.siblings(".praise-number").text("赞"+getCountStr(num));
		        }
	        }, timer);
	        
	        a.toggleClass("praise-yes");
	        $("#likeStatus_"+commentId).val(status);
		}
	});
}

//获取未展开评论
function getCommentNoDiv(commentContent,commentUserName,photo,station,stationClass) {
	var content = '';
	content += '<li><div class="header images-x images-x-content">';
	var zm = getPinY(commentUserName);
	if(station != null && "0" != station) {// 不是本站
		if(zm == "") {
			zm = "A";
		}
		content += '<span class="no_header no_header-'+zm+'"><i class="iconfont '+stationClass+'"></i></span>';
	} else if(judge(photo)) {// 有个人头像
		content += '<img src="'+photo+'">';
	} else {
		if(zm == "") {
			content += '<img id="myPhoto65" src="'+_staticPrefix+'/images/mr-header.png">';
		} else {
			content += '<span class="no_header no_header-'+zm+'">'+zm+'</span>';
		}
	}
	content += '<div class="images-x-hover"></div></div>';
	content += '<span class="name">'+commentUserName+'：</span>';
	content += '<span class="content">'+commentContent+'</span>';
    content += '</li>';
	
	return content;
}

function getPinY(str) {
	if(str == "" || str == undefined) {
		return "";
	}
	var py = makePy(str);
	py = py[0].substring(0,1);
	return py.toUpperCase();
}

//评论监听
function commentSocket() {
	socket = io.connect(_socketAddress);
	socket.on("comment",function(data) {
		var comment = JSON.parse(data);
		var videoId = comment.videoId; // 视频ID
		if(document.getElementById("video_li_"+videoId)) {
			var id = comment.id;
			var commentId = comment.commentId;
			var commentContent = comment.commentContent;
			var commentCreatetime = comment.commentCreatetime;
			var parentNickName = comment.parentUserName;
			var commentUserName = comment.commentUserName;
			var photo = comment.avatar;
			var station = comment.station;
			var stationClass = comment.stationClass;
			var likeNum = 0;
			var likeStatus = 0;
			var contentNo = getCommentNoDiv(commentContent,commentUserName,photo,station,stationClass);
			var contentyes = getCommentYesDiv(id , commentId, commentContent, commentUserName, commentCreatetime, station, stationClass,likeNum,likeStatus, parentNickName,photo);
			if(!document.getElementById("comment-no-ul_"+videoId)) {
				$("#video_li_"+videoId+" .comment-no").html('<ul class="comment-inside" id="comment-no-ul_'+videoId+'"></ul>');
			}
			$("#video_li_"+videoId+" .comment-no .comment-inside").append(contentNo);
			$("#video_li_"+videoId+" .comment-yes .comment-inside").prepend(contentyes);
			$("#video_li_"+videoId+" .comment-yes").mCustomScrollbar("scrollTo","top");
			autoScroll($("#comment-no-ul_"+videoId));
		}
	});
}
