$(function() {
	// 弹幕信息开关
	if (user) {
		ajaxMethod("/my/switch/1","","post",false,function(data){
			if (data && data.switchValue == 1) {
				// 接收评论
				receiveComment();
			}
		});
	}else{
		// 接收评论
		receiveComment();
	}
	
	var width = screen.width;
	
	var url = _path + "/videoRecommend/searchList";
	ajaxMethod(url,"","post",true,function(data){
		if(data.success) {
			var list = data.list;
			var content = '';
			if(list!=null && list.length>0){
				var videoIndex = 0;
				var videoRecommend;
				//每页最多显示多少个视频
				var jMax = 3;
				if(width<1200){
					if(width>860){
						jMax = 2;
					}else{
						jMax = 1;
					}
				}
				//计算有几页
				var count = parseInt(list.length / jMax);
				count += list.length % jMax > 0 ? 1 : 0;
				
				content += '<div class="swiper-container swiper-no-swiping">';
				content += 	'<div class="swiper-wrapper">';
				for(var i = 1;i <= count;i++){
					if(i == count){
						jMax = list.length % jMax > 0 ? list.length % jMax : jMax;
					}
					content += 		'<div class="swiper-slide">';
					for(var j = 1;j <= jMax;j++){
						videoRecommend = list[videoIndex];
						videoIndex++;
						content += 			'<div class="recommend-video-details images-x">';
						content += 				'<a target="_blank" href="'+_path+'/video/'+videoRecommend.id+'" class="view1 images-x-content">';
						content += ReferrerKiller.imageHtml(videoRecommend.cover,_path+'/video/'+videoRecommend.id);
						content += 				'</a>';
						content += 				'<div class="view2">';
						content += 					'<p class="video-title">';
						content += 						'<span class="one"></span>';
						content += 						'<strong class="two-outside"><a target="_blank" href="'+_path+'/video/'+videoRecommend.id+'" class="two" title="'+videoRecommend.title+'">'+videoRecommend.title+'</a></strong>';
						content += 					'</p>';
						content += 					'<p class="video-content">'+videoRecommend.introduction+'</p>';
						content += 					'<div class="video-icon">';
						content += 						'<strong>';
						content += 							'<a target="_blank" class="left btn">';
						content += 								'<i class="iconfont icon-wf_bofang" title="播放"></i>';
						content += 								'<span class="two">'+getCountStr(videoRecommend.viewCount)+'</span>';
						content += 							'</a>';
						content += 						'</strong>';
						content += 					'</div>';
						content += 				'</div>';
						content += 				'<div class="recommend-video-details-div">';
						content += 					'<span class="one">评论:</span>';
						content += 					'<strong><a target="_blank" class="btn" href="'+_path+'/video/'+videoRecommend.id+'?gto=comment">'+getCountStr(videoRecommend.videoCommentCount)+'</a></strong>';
						content += 				'</div>';
						content += 			'</div>';
					}
					content += 		'</div>';
				}
				content += 	'</div>';
				content += '</div>';
				content += '<div class="swiper-button-prev"></div>';
				content += '<div class="swiper-button-next"></div>';
			}
			$("#recommend-video").html(content);
			//swiper
			var swiper = new Swiper('.swiper-container', {
                pagination: '.swiper-pagination',
                paginationClickable: true,
                nextButton: '.swiper-button-next',
                prevButton: '.swiper-button-prev',
                noSwiping : true,
                parallax: true,
                speed: 600,
            });
		}
	});
});

//接收评论
function receiveComment(){
	socket = io.connect(_socketAddress);
	socket.on("comment",function(result)
   	{
		var message = JSON.parse(result);
		var content = '';
		content += '<div class="dm-content" id="dm_'+message.id+'">';
		content += 	'<strong class="dm-href-outside">';
		content += 		'<a target="_helf" href="'+_path+'/video/'+message.videoId+'" class="dm-href">';
		content += 			'<img src="'+message.avatar+'" class="dm-c-header">';
		content +=			'<span class="dm-c-ct">'+message.commentUserName+'：'+message.commentContent+'</span>';
		content += 		'</a>';
		content += 	'</strong>';
		content += 	'<input type="hidden" id="likeStatus_'+message.id+'" value="0"/>';
		content += 	'<i class="iconfont icon-wf_dianzan"  title="点赞" onclick="addLike(this,\''+message.id+'\')"></i>';
		content += '</div>';
		$("#homepage-main-dm").append(content);
		
        dmgd($("#dm_"+message.id));
        
        updateStatus($("#dm_"+message.id));
	});
}

//附加事件
function updateStatus(a){
	var timer1;
    //弹幕点赞
    $(a).mouseenter(function(){//鼠标移入停止弹幕滚动
        var a = $(this);
        timer1 = setTimeout(function(){
            a.stop(true);
        },100)
    }).mouseleave(function(){//鼠标移出弹幕继续滚动
        clearTimeout(timer1);
        dmgdNew($(this));
    })
}

//弹幕滚动
function dmgd(a){
    //获取屏幕的宽度
    var w = window.innerWidth;
    a.each(function(){
        var b = $(this).width()+20;//获取每条弹幕的长度并+20
        var sd = parseInt(Math.random()*(18000-8500+1)+8500);//设置随机滚动速度
        $(this).css({
            "right":"-"+b+"px",
            "width":$(this).width() + 6 + "px",
            "top":Math.random()*(115-0+1)+0
        });//让弹幕隐藏在屏幕之外
        $(this).animate({//滚动
            "right":w+500+"px"
        },sd,function(){
            $(this).remove();
        });
    })
}

//鼠标移出弹幕继续滚动
function dmgdNew(a){
    //获取屏幕的宽度
    var w = window.innerWidth;
    var b = a.width()+20;//获取每条弹幕的长度并+20
    var r = parseInt(a.css("right"));//当前的right
    var rb = r+b;//已经滚动了的距离
    var c = w+500+b//总距离
    var d = c-rb//剩余距离
    var e = c/d;
    var sd = parseInt(Math.random()*(18000/e-8500/e+1)+8500/e);//设置随机滚动速度
    a.animate({//滚动
        "right":w+500+"px"
    },sd,function(){
        $(this).remove();
    });
}

//点赞
function addLike(obj,commentId) {
	if(userId == "" || userId == undefined) {// 登录用户
		window.open(_path+'/login');
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
			$(obj).parent().toggleClass("ydz");
	        $("#likeStatus_"+commentId).val(status);
		}
	});
}