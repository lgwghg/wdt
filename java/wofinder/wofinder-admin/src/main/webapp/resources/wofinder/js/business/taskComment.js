//分页信息
var pageSize = 10; //每页显示条数
var startRecord = 0;	//开始记录数
var nowPage = 1;  //当前页
var pageCount = -1;	//总页数
var recordCount =-1;	//总记录数
var lastPage = false;//是否最后一页

$(function(){
	loadCommentList($("#taskId").val(),true);
	
	//开启监控
	commentSocket();
});

//评论列表
function loadCommentList(taskId,first){
	//分页
	var gridPager = {};
	gridPager.pageSize = pageSize;
	gridPager.startRecord = startRecord;
	gridPager.nowPage = nowPage;
	gridPager.recordCount = pageCount;
	gridPager.pageCount = recordCount;
	gridPager.lastPage = lastPage;
	
	var dataJson = {};
	dataJson.taskId = taskId;
	
	gridPager.parameters = dataJson;
	
	var url = _path + "/taskComment/searchList";
	
	ajaxMethod(url, {"gridPager":JSON.stringify(gridPager)}, "post", true, function(backData) {
		if (backData.isSuccess) {
			if(backData.exhibitDatas) {
				var contentyes = "";
				var length = backData.exhibitDatas.length;
				for(var i=0; i<length; i++) {
					var taskComment = backData.exhibitDatas[i];
					var id = taskComment.id;
					var taskId = taskComment.taskId;
					var content = taskComment.content;
					var userName = taskComment.userName;
					var createtime = taskComment.createtime;
					var likeNum = taskComment.likeNum;
					var likeStatus = taskComment.likeStatus;
					var photo = taskComment.avatar;
					var parentNickName = taskComment.parentUserName;
					contentyes += getCommentYesDiv(id , taskId, content, userName, createtime, likeNum,likeStatus, parentNickName,photo);
				}
				
				var pageCount = backData.pageCount;
				if(pageCount <= gridPager.nowPage) {// 最后一页
					$("#loadMore").addClass("hide")
					gridPager.lastPage = true;
					lastPage = true;
					if(length <= 0) {
						contentyes += '<li class="no-comment">没有新的评论~</li>';
					} else if(!first || length <= gridPager.pageSize){
						contentyes += '<li class="load-more">没有更多评论</li>';
					}
				} else if(first) {
					contentyes += '<div id="loadMore" class="load-more"><img src="'+_staticPrefix+'/images/load-more.gif">正在加载...</div>';
				}
				if(first){
					$("#task_li .comment-yes .comment-inside").html(contentyes);
				} else {
					$("#task_li .comment-yes .comment-inside").append(contentyes);
					$("#loadMore").appendTo($("#task_li .comment-yes .comment-inside"));
				}
				
				//$("#firstComment").addClass("hide");
				//$("#secondComment").removeClass("hide");
				commentCss();
			} else {
				$("#task_li .comment-yes .comment-inside").html('<li class="no-comment">没有新的评论~</li>');
			}
			// 下一页
			gridPager.nowPage = backData.nowPage+1;
			comment_init();
		} else {
			$("#task_li .comment-yes .comment-inside").html('<li class="no-comment">没有新的评论~</li>');
		}
	});
}

//获取展开评论
function getCommentYesDiv(id,taskId, Content,UserName, Createtime,likeNum,likeStatus, parentNickName,photo) {
	var content = '';
	content += '<li><div class="comment-content comment-one">';
	content += '<div class="header images-x images-x-content">';
	if(judge(photo)) {// 有个人头像
		content += '<img src="'+photo+'">';
	} else {
		content += '<img id="myPhoto65" src="'+_staticPrefix+'/images/mr-header.png">';
	}
	content += '</div>';
	content += '<div class="right">';
	content += '<div class="top"><input type="hidden" class="comment_num" value="'+likeNum+'"/>';
	content += '<input type="hidden" id="likeStatus_'+id+'" value="'+likeStatus+'"/>';
	content += '<span class="name">'+UserName+'</span>';
	content += '<span class="praise-number" style="color:#DB4437;">'+(likeNum>0?('赞'+getCountStr(likeNum)):'')+'</span>';
	content += '<span class="add">+1</span>';
	content += '<span class="time">'+getDifToNow(Createtime)+'</span>';
	var praise = "";
	if(likeStatus == 1) {
		praise = "praise-yes";
	}
	content += '<div class="praise hide '+praise+'" id="praise_'+id+'" onclick="addLike(\''+id+'\');event.stopPropagation();">';
	content += '<i class="iconfont icon-wf_dianzan"></i>';
	content += '</div>';
	content += '</div>';
	if(judge(parentNickName)) {// 回复XXX
		content += '<div class="bottom"><span>回复</span><span class="hf">'+parentNickName+'</span>：'+Content+'</div>';
	} else {
		content += '<div class="bottom">'+Content+'</div>';
	}
	content += '</div>';
	content += '</div></li>';
	
	return content;
}

//评论初始化控件
function comment_init(){
	
	//评论框
	if($("#userPhoto_65").val() == "") {// 用户没有上传头像
		$("#myPhoto65").attr("src",_staticPrefix +"/images/mr-header.png");
	}
		
	// 滑轮
	$(".comment-yes").mCustomScrollbar({
      callbacks:{onTotalScroll:function(){
      	var taskId = $("#taskId").val();
      	if(!lastPage) {
      		$(this).find(".load-more").removeClass("hide");
      		$(this).mCustomScrollbar("scrollTo","bottom");
      		loadCommentList(taskId,false);
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
  	var taskId = $("#taskId").val();
  	$(this).parents(".comment-yes").siblings(".comment-fb-outside").find(".hf-x").removeClass("hide").find(".name").html($(this).find(".name").html());
  	$("#comment-content").attr("commentId",$(this).find(".comment_id").val());
  	$("#comment-content").attr("commentNickName", $(this).find(".name").html());
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
    	var taskId = $("#taskId").val();
        $(this).addClass("hide");
        $("#comment-content").attr("commentId","");
        $("#comment-content").attr("commentNickName", "");
    })
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
	
	var taskId = $("#taskId").val();
	var content = $("#comment-content").val();
	if(trim(content)==''){
		layer.alert("请先填写内容再发表评论", {title:"",icon: 5,time: 1000});
		return false;
	}
	if(strLength(content)>480){
		layer.alert("评论内容不能超过240字", {title:"",icon: 5,time: 1000});
		return false;
	}
	var pId = $("#comment-content").attr("commentId");
	var parentNickName = $("#comment-content").attr("commentNickName");
	var dataJson = {};
	dataJson.content = content;
	dataJson["task.id"] = taskId;
	dataJson["parentComment.id"] = pId;
	dataJson["parentComment.createUser.nickName"] = parentNickName;
	
	var url = _path + "/taskComment/add";
	ajaxMethod(url, dataJson, "post", true, function(backData) {
		if (backData.success) {
			canSubmit = false;
			setTimeout(function() {
				canSubmit = true;
			}, 5000);
			
			$("#comment-content").val("");
			layer.msg("评论成功", {title:"",icon: 6,time: 1000});
			commentCss();
		} else {
			layer.alert(backData.message, {title:"",icon: 5,time: 1000});
		}
	});
}

//评论监听
function commentSocket() {
	socket = io.connect(_socketAddress);
	socket.on("taskComment",function(data) {
		var comment = JSON.parse(data);
		var taskId = comment.taskId; // 事件ID
		if($("#taskId").val()==taskId) {
			var id = comment.id;
			var Content = comment.Content;
			var Createtime = comment.Createtime;
			var parentNickName = comment.parentUserName;
			var UserName = comment.userName;
			var photo = comment.avatar;
			var likeNum = 0;
			var likeStatus = 0;
			var contentyes = getCommentYesDiv(id,taskId, Content,UserName, Createtime,likeNum,likeStatus, parentNickName,photo);
			$("#task_li .comment-yes .comment-inside").prepend(contentyes);
			$("#task_li .comment-yes").mCustomScrollbar("scrollTo","top");
			autoScroll($("#comment-no-ul"));
		}
	});
}

