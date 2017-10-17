var current = 0; //当前页
var totalPages = 0; //总页数
var reslutCount = 0; //符合条件的结果数量
var wd; //搜索关键字
var count = 1; //计数器

$(function() {
	//搜索关键字
	wd = $("#wd").val();
	//当前页
	current = $("#current").val();

	//加载视频列表
	loadList();

	// 初始化热门视频
	initHotVideo();

	drawNext();

})

//初始化视频预加载
function initVideoLoad(size) {
	var content = '';
	for (var i = 0; i < size; i++) {
		content += '<li class="video video-yjz">';
		content += '    <!--标题-->';
		content += '    <div class="title-x title-x-yjz">';
		content += '       <span class="one"></span>';
		content += '        <h3 class="two">';
		content += '            <strong>';
		content += '                <a href="javascript:;"></a>';
		content += '            </strong>';
		content += '        </h3>';
		content += '    </div>';
		content += '    <!--视频封面和介绍-->';
		content += '    <div class="unfold-before">';
		content += '       <div class="images-x images-x-content">';
		content += '       </div>';
		content += '      <div class="two">';
		content += '           <p>';
		content += '          </p>';
		content += '        </div>';
		content += '   </div>';
		content += '   <!--切换视频来源-->';
		content += '  <div class="video-source">';
		content += '      <div class="left" title="播放量">';
		content += '      </div>';
		content += '      <div class="middle">';
		content += '      </div>';
		content += '      <div class="right">';
		content += '      </div>';
		content += '    </div>';
		content += '   <!--评论未展开-->';
		content += '   <div class="comment-no">';
		content += '      <ul class="comment-inside">';
		content += '          <li>';
		content += '             <div class="header images-x images-x-content">';
		content += '            </div>';
		content += '             <span class="name"></span>';
		content += '            <span class="content"></span>';
		content += '        </li>';
		content += '     </ul>';
		content += '  </div>';
		content += '</li>';
	}
	$(".search-result-main-content .search-content-left .page-turning").before(content);
	$(".search-content-left li.video:last").after($("#loadMore"));
}

//公用初始化方法
function search_init() {
	//点击播放视频
	$(".search-content-left .video").unbind("click").on("click", ".unfold-before", function() {
		$(".search-content-left .video .unfold-before").removeClass("hide");
		$(".search-content-left .video .unfold-after-x").addClass("hide");
		$(this).addClass("hide");
		$(this).siblings(".unfold-after-x").removeClass("hide");
	})

	//切换视频来源
	$(".search-content-left .video .video-source .middle .video-source-content ul li").unbind("click").on("click", "i", function() {
		$(this).addClass("checked").siblings("i").removeClass("checked");
	})

	//切换视频来源箭头显示与否
	$(".search-content-left .video .video-source .middle .video-source-content ul li").each(function() {
		if ($(this).find("i").length <= 7) {
			$(this).parents(".video-source-content").siblings(".icon-wf_zuo_qiehuan,.icon-wf_you_qiehuan").addClass("hide");
		}
	})
	$(".search-content-left .video .video-source .middle .icon-wf_zuo_qiehuan").unbind("click").on("click", function() {
		var _this = $(this);
		var fi = _this.siblings(".video-source-content").find("ul li i:first-child");
		var al = parseInt(fi.css("margin-left"));
		if (al != 0 && _this.hasClass("no-click") != true) {
			_this.addClass("no-click");
			_this.siblings(".icon-wf_you_qiehuan").addClass("no-click");
			fi.animate({
				"margin-left" : al + 54 + "px"
			}, 288, function() {
				_this.removeClass("no-click");
				_this.siblings(".icon-wf_you_qiehuan").removeClass("no-click");
			});
		}
	})
	$(".search-content-left .video .video-source .middle .icon-wf_you_qiehuan").unbind("click").on("click", function() {
		var _this = $(this);
		var fi = _this.siblings(".video-source-content").find("ul li i:first-child");
		var al = parseInt(fi.css("margin-left"));
		var tl = $(this).siblings(".video-source-content").find("ul li i").length - 7;
		if (al != -(tl * 54) && _this.hasClass("no-click") != true) {
			_this.addClass("no-click");
			_this.siblings(".icon-wf_you_qiehuan").addClass("no-click");
			fi.animate({
				"margin-left" : al - 54 + "px"
			}, 288, function() {
				_this.removeClass("no-click");
				_this.siblings(".icon-wf_you_qiehuan").removeClass("no-click");
			});
		}
	})

	//打星
	var d;
	var e;
	$(".grade-number-x .two div").mouseenter(function() {
		d = $(this).parent().siblings("ul").attr("class");
		e = $(this).parents(".grade-number-x").siblings(".grade-number").html();
		$(this).parent().siblings("ul").attr("class", "" + $(this).attr("class") + "");
		/*$(this).parents(".grade-number-x").siblings(".grade-number").html($(this).index() + 1 +"分");*/
		if ($(this).index() == 0 || $(this).index() == 1) {
			$(this).parent().siblings(".three").css("left", "-2px").html($(this).index() + 1);
		} else if ($(this).index() == 2 || $(this).index() == 3) {
			$(this).parent().siblings(".three").css("left", "19px").html($(this).index() + 1);
		} else if ($(this).index() == 4 || $(this).index() == 5) {
			$(this).parent().siblings(".three").css("left", "40px").html($(this).index() + 1);
		} else if ($(this).index() == 6 || $(this).index() == 7) {
			$(this).parent().siblings(".three").css("left", "61px").html($(this).index() + 1);
		} else if ($(this).index() == 8 || $(this).index() == 9) {
			$(this).parent().siblings(".three").css("left", "82px").html($(this).index() + 1);
		}
	}).mouseleave(function() {
		$(this).parent().siblings("ul").attr("class", "" + d + "");
	/*$(this).parents(".grade-number-x").siblings(".grade-number").html(e);*/
	}).on("click", function() {
		$(this).parent().siblings("ul").attr("class", "" + $(this).attr("class") + "");
		d = $(this).attr("class");
		e = $(this).index() + 1 + "分";
	})
}

//搜索结果初始化方法
function searchVideo_init() {
	//图片响应式
	var a = $(".search-content-left .introduce .bottom .left li div img");
	a.each(function() {
		var b = $(this).width();
		var c = $(this).height();
		if (b > c) {
			$(this).css({
				"margin-left" : (-(90 / c * b / 2)) + "px",
				"height" : "90px",
				"left" : "50%"
			});
		} else {
			$(this).css({
				"margin-top" : (-(90 / b * c / 2)) + "px",
				"width" : "90px",
				"top" : "50%"
			});
		}
	})

	// 视频播放量
	var viewCount = $("span[id^=viewCount_]");
	for (var i = 0; i < viewCount.length; i++) {
		if (!$(viewCount[i]).hasClass("init")) {
			$(viewCount[i]).addClass("init").html(getCountStr($(viewCount[i]).html()));
		}
	}
	// 评论量
	var videoCommentCount = $("a[id^=videoCommentCount_]");
	for (var i = 0; i < videoCommentCount.length; i++) {
		if (!$(videoCommentCount[i]).hasClass("init")) {
			$(videoCommentCount[i]).addClass("init").html(getCountStr($(videoCommentCount[i]).html()));
		}
	}
	// 视频时长
	var duration = $("span[id^=duration_]");
	for (var i = 0; i < duration.length; i++) {
		if (!$(duration[i]).hasClass("init")) {
			$(duration[i]).addClass("init").html(secToTime($(duration[i]).html()));
		}
	}
	// 用户视频评分样式
	var scoreClass = $("ul[id^=scoreClass_]");
	var score = $("input[id^=score_]");
	for (var i = 0; i < scoreClass.length; i++) {
		if (!$(scoreClass[i]).hasClass("init")) {
			$(scoreClass[i]).addClass("init").addClass(getScoreClass($(score[i]).val()));
		}
	}

	//切换提交评分
	$(".grade-number-x .two div").unbind("click").on("click", function() {
		addOrUpdateGrade($(this));
	});

	//评论展开收起
	$(".search-content-left").unbind("click").on("click", ".video .comment-no", function() {
		if ($(this).parent().hasClass("video-yjz") != true) {
			var a = $(this);
			//定位
			$('html,body').animate({
				scrollTop : a.parent().offset().top - 60
			}, 500);
			a.addClass("hide").siblings(".comment-x").css({
				"height" : "0",
				"display" : "block"
			}).removeClass("hide").animate({
				"height" : "566px"
			}, 500);
		}
	}).on("click", ".no-find .form-group-x .subtract", function() { //无搜索结果时删除URL
		if ($(".no-find input[name='submitUrlList']").length > 1) {
			$(this).parent().remove();
		}
	}).on("click", ".no-find .add-outside .add", function() { //无搜索结果时添加URL
		if ($(".no-find input[name='submitUrlList']").length < 4) {
			$(this).parent().before('<div class="form-group label-floating form-group-x">' +
				'<input type="text" name="submitUrlList" class="form-control text_input"  placeholder="http://" onblur="checkSubmitUrl(this)">' +
				'<span class="help-block">请输入链接地址</span>' +
				'<span class="subtract images-x images-x-content">-</span>' +
				'</div>');
		}
	})

	//收起评论
	$(".search-content-left .video .share").on("click", function() {
		var a = $(this);
		if (a.hasClass("no-click") != true) {
			a.addClass("no-click").parent().animate({
				"height" : "65px"
			}, 500, function() {
				a.removeClass("no-click").parent().addClass("hide").siblings(".comment-no").removeClass("hide");
			})
		}
	}).on("click", ".two,.three a", function(e) { //点赞
		e.stopPropagation();
	})

	$(".search-content-left").on("click", ".comment-no .comment-fb-outside", function(e) {
		e.stopPropagation();
	})
}

//视频合集搜索结果初始化方法
function searchAlbum_init() {
	$(".compilations .episode-details .episode-details-menu .two .icon-wf_zuo_qiehuan").on("click", function() {
		var _this = $(this);
		var fi = _this.parent().siblings(".one-outside").find("li:first-child");
		var al = parseInt(fi.css("margin-left"));
		if (al != 0 && _this.hasClass("no-click") != true) {
			_this.addClass("no-click");
			_this.siblings(".icon-wf_you_qiehuan").addClass("no-click");
			fi.animate({
				"margin-left" : al + 80 + "px"
			}, 288, function() {
				if (parseInt(fi.css("margin-left")) != 0) {
					_this.removeClass("no-click");
				}
				_this.siblings(".icon-wf_you_qiehuan").removeClass("no-click");
			});
		}
	})
	$(".compilations .episode-details .episode-details-menu .two .icon-wf_you_qiehuan").on("click", function() {
		var _this = $(this);
		var fi = _this.parent().siblings(".one-outside").find("li:first-child");
		var al = parseInt(fi.css("margin-left"));
		var tl = $(this).parent().siblings(".one-outside").find("li").length - 7;
		if (al != -(tl * 80) && _this.hasClass("no-click") != true) {
			_this.addClass("no-click");
			_this.siblings(".icon-wf_zuo_qiehuan").addClass("no-click");
			fi.animate({
				"margin-left" : al - 80 + "px"
			}, 288, function() {
				if (al != -((tl - 1) * 80)) {
					_this.removeClass("no-click");
				}
				_this.siblings(".icon-wf_zuo_qiehuan").removeClass("no-click");
			});
		}
	})
}
/**
 * 初始化搜索页右侧的热门视频
 */
function initHotVideo() {
	var url = _path + "/s/hotVideo";
	$.ajax({
		url : url,
		async : true,
		type : "POST",
		dataType : "json",
		success : function(data) {
			if (data.isSuccess) {
				var list = data.rightVideoList;
				var content = '';
				var video;
				if (list != null && list.length > 0) {
					content += '<div class="title-x">';
					content += '<span class="one"></span>';
					content += '<h3 class="two">热门视频</h3>';
					content += '</div>';
					content += '<div class="team-two">';
					for (var i = 0; i < list.length; i++) {
						video = list[i];
						content += '<div class="team-two-inside">';
						content += '<a href="' + _path + '/video/' + video.shortId + '" target="_helf" class="images-x images-x-content">';
						content += ReferrerKiller.imageHtml(video.cover, _path + '/video/' + video.shortId);
						content += '<span class="time" id="duration_' + video.id + '">' + video.duration + '</span>';
						content += '<span class="pf">' + video.score + '分</span>';
						content += '</a>';
						content += '<a href="' + _path + '/video/' + video.shortId + '" target="_helf" class="title">' + video.title + '</a>';
						content += '</div>';
					}
					content += '</div>';
					$(".search-content-right").html(content);
					search_init();
				}
			}
		}
	});
}
//ajax加载列表数据
function loadList() {
	initVideoLoad(5);
	var url = _path + "/s";
	var dataJson = {
		wd : wd,
		current : parseInt(current)
	};
	ajaxMethod(url, dataJson, "post", true, function(data) {
		if (data.isSuccess) {
			current = data.current;
			totalPages = data.totalPages;
			reslutCount = count;
			var list = data.videoVoList;
			var videoAlbumList = data.videoAlbumVoList;
			var content = '';
			if (videoAlbumList != null && videoAlbumList.length > 0) {
				content = '';
				var videoAlbum;
				var sonVideoAlbumList;
				var videoList;
				var video;
				var firstVideoArray = new Array();
				for (var i = 0; i < videoAlbumList.length; i++) {
					videoAlbum = videoAlbumList[i];
					sonVideoAlbumList = videoAlbum.videoAlbumList;
					if(sonVideoAlbumList!=null && sonVideoAlbumList.length > 0){
						videoList = sonVideoAlbumList[0].videoList;
					}else{
						videoList = videoAlbum.videoList;
					}
					if(videoList==null || videoList.length < 1){
						continue;
					}
					video = videoList[0];
					firstVideoArray[i] = {
						videoId : video.id,
						videoAlbumId : videoAlbum.id
					};

					content += '<li class="video compilations" id="videoAlbum_li_' + videoAlbum.id + '">';
					content += '<!-- 隐藏域 -->';
					content += '<input type="hidden" value="' + videoAlbum.id + '" id="videoAlbum_' + videoAlbum.id + '">';
					content += '    <!--标题-->';
					content += '    <div class="title-x">';
					content += '        <span class="one"></span>';
					content += '        <h3 class="two">';
					content += '            <strong>';
					if (sonVideoAlbumList != null && sonVideoAlbumList.length > 0) {
						content += '                <a id="albumName_' + videoAlbum.id + '" target="_blank" href="' + _path + '/video/' + video.shortId + '">' + videoAlbum.searchName + '<font style=\"color:#db4437\">' + sonVideoAlbumList[0].name + '</font></a>';
						content += '            	<input type="hidden" id="sonAlbumNameValue_' + videoAlbum.id + '" value=\'<font style="color:#db4437">' + sonVideoAlbumList[0].name + '</font>\'>';
					} else {
						content += '                <a id="albumName_' + videoAlbum.id + '" target="_blank" href="' + _path + '/video/' + video.shortId + '">' + videoAlbum.searchName + '</a>';
						content += '            	<input type="hidden" id="sonAlbumNameValue_' + videoAlbum.id + '" value="">';
					}
					content += '            		<input type="hidden" id="albumNameValue_' + videoAlbum.id + '" value="' + videoAlbum.searchName + '">';

					content += '            </strong>';
					content += '        </h3>';
					content += '    </div>';
					content += '    <!--视频封面和介绍-->';
					content += '    <div class="unfold-before">';
					content += '        <div class="images-x images-x-content">';
					if (sonVideoAlbumList != null && sonVideoAlbumList.length > 0) {
						if (sonVideoAlbumList[0].cover == null || sonVideoAlbumList[0].cover == "") {
							content += '		<img src="' + _staticPrefix + '/images/fengmiantu.png">';
						} else {
							content += ReferrerKiller.imageHtml(sonVideoAlbumList[0].cover);
						}
					}else{
						if (videoAlbum.cover == null || videoAlbum.cover == "") {
							content += '		<img src="' + _staticPrefix + '/images/fengmiantu.png">';
						} else {
							content += ReferrerKiller.imageHtml(videoAlbum.cover);
						}
					}
					content += '            <i class="iconfont icon-wf_bofang"></i>';
					content += '        </div>';
					content += '        <div class="two">';
					content += '            <div class="first">';
					content += '                <span class="first-one">更新至</span>';
					content += '                <span class="first-two">第' + getChineseByNum(Number(video.albumIndex)) + '期</span>';
					if (videoAlbum.updateRemarks != null && videoAlbum.updateRemarks != '') {
						content += '            <span class="first-three">网络更新时间：' + videoAlbum.updateRemarks + '</span>';
					}
					content += '            </div>';
					if (videoAlbum.author != null && videoAlbum.author != '') {
						content += '        <div class="second">作者：<span>' + videoAlbum.author + '</span></div>';
					}
					if (videoAlbum.introduction != null && videoAlbum.introduction != '') {
						content += '        <p>' + videoAlbum.introduction + '</p>';
					}
					content += '            <div class="thirdly" id="albumValue_' + videoAlbum.id + '">';
					content += '            </div>';
					content += '        </div>';
					content += '    </div>';
					content += '    <!--视频点击后播放-->';
					content += '    <div class="unfold-after unfold-after-x hide" id="aVideoDiv_' + videoAlbum.id + '">';
					//一开始无视频站点数据，加载完集合后，视频异步加载
					content += '   </div>';
					content += '    <!--切换视频来源-->';
					content += '    <div class="video-source">';
					content += '        <div class="left" title="播放量">';
					content += '            <i class="iconfont icon-wf_bofang"></i>';
					content += '            <span class="two" id="aViewCount_' + videoAlbum.id + '">' + video.viewCount + '</span>';
					content += '        </div>';
					content += '        <div class="middle">';
					content += '            <i class="iconfont icon-wf_zuo_qiehuan"></i>';
					content += '            <div class="video-source-content">';
					content += '               <ul>';
					content += '                    <li id="albumHidLi_' + videoAlbum.id + '">';
					content += '                    </li>';
					content += '                </ul>';
					content += '            </div>';
					content += '            <i class="iconfont icon-wf_you_qiehuan"></i>';
					content += '        </div>';
					content += '        <div class="right">';
					content += '            <span class="grade-number" id="aScore_' + videoAlbum.id + '">' + video.score + '分</span>';
					content += '        </div>';
					content += '    </div>';
					content += '    <!--视频点击后简介模块-->';
					content += '    <div class="grade unfold-after-x hide">';
					content += '        <div class="grade-x">';
					content += '            <div class="left">';
					content += '                <div class="one"></div>';
					content += '                <div class="two" title="" id="abIntroduction_' + videoAlbum.id + '">';
					content += '                </div>';
					content += '            </div>';
					content += '            <div class="right">';
					content += '            	<input type="hidden" value="' + video.myScore + '" id="abScore_' + videoAlbum.id + '">';
					content += '                <div class="grade-number-x">';
					content += '                    <ul id="abScoreClass_' + videoAlbum.id + '">';
					content += '                        <li class="item1">';
					content += '                            <i class="iconfont icon-wf_weipingfen"></i>';
					content += '                            <i class="iconfont icon-wf_pingfen1"></i>';
					content += '                            <i class="iconfont icon-wf_pingfen"></i>';
					content += '                        </li>';
					content += '                        <li class="item2">';
					content += '                            <i class="iconfont icon-wf_weipingfen"></i>';
					content += '                            <i class="iconfont icon-wf_pingfen1"></i>';
					content += '                            <i class="iconfont icon-wf_pingfen"></i>';
					content += '                        </li>';
					content += '                        <li class="item3">';
					content += '                            <i class="iconfont icon-wf_weipingfen"></i>';
					content += '                            <i class="iconfont icon-wf_pingfen1"></i>';
					content += '                            <i class="iconfont icon-wf_pingfen"></i>';
					content += '                        </li>';
					content += '                        <li class="item4">';
					content += '                            <i class="iconfont icon-wf_weipingfen"></i>';
					content += '                            <i class="iconfont icon-wf_pingfen1"></i>';
					content += '                            <i class="iconfont icon-wf_pingfen"></i>';
					content += '                        </li>';
					content += '                        <li class="item5">';
					content += '                            <i class="iconfont icon-wf_weipingfen"></i>';
					content += '                            <i class="iconfont icon-wf_pingfen1"></i>';
					content += '                            <i class="iconfont icon-wf_pingfen"></i>';
					content += '                        </li>';
					content += '                    </ul>';
					content += '                    <div class="two" albumId="' + videoAlbum.id + '">';
					content += '                        <div class="item1-one"></div>';
					content += '                        <div class="item1-two"></div>';
					content += '                        <div class="item2-one"></div>';
					content += '                        <div class="item2-two"></div>';
					content += '                        <div class="item3-one"></div>';
					content += '                        <div class="item3-two"></div>';
					content += '                        <div class="item4-one"></div>';
					content += '                        <div class="item4-two"></div>';
					content += '                        <div class="item5-one"></div>';
					content += '                        <div class="item5-two"></div>';
					content += '                    </div>';
					content += '                    <span class="three"></span>';
					content += '                </div>';
					content += '            </div>';
					content += '            <div class="author">';
					content += '                <span>作者:</span>';
					content += '                <span id="abUpName_' + videoAlbum.id + '" title=""></span>';
					content += '            </div>';
					content += '        </div>';
					content += '        <div class="label-x" id="abVideoValue_' + videoAlbum.id + '">';
					content += '        </div>';
					content += '    </div>';
					content += '    <div class="episode-details">';
					if(sonVideoAlbumList!=null && sonVideoAlbumList.length > 0){
						content += '        <div class="episode-details-menu">';
						content += '            <div class="one-outside">';
						content += '                <ul class="one">';
						for (var j = 0; j < sonVideoAlbumList.length; j++) {
							if (j == 0) {
								content += '			<li class="checked" pid="' + videoAlbum.id + '" albumId="' + sonVideoAlbumList[j].id + '">' + sonVideoAlbumList[j].name + '</li>';
							} else {
								content += '			<li pid="' + videoAlbum.id + '" albumId="' + sonVideoAlbumList[j].id + '">' + sonVideoAlbumList[j].name + '</li>';
							}
						}
						content += '                </ul>';
						content += '            </div>';
						content += '            <div class="two">';
						content += '                <i class="iconfont icon-wf_zuo_qiehuan"></i>';
						content += '                <i class="iconfont icon-wf_you_qiehuan"></i>';
						content += '            </div>';
						content += '        </div>';
					}
					content += '        <div class="episode-details-bottom">';
					content += '            <ul class="videos" id="albumVideos_' + videoAlbum.id + '">';
					content += mosaicVideoList(videoList);
					content += '           </ul>';
					content += '           <a id="abSeeMore_' + videoAlbum.id + '" target="_blank" href="' + _path + '/video/' + video.shortId + '" class="to-details">查看更多合集视频</a>';
					content += '       </div>';
					content += '   </div>';
					content += '</li>';
				}
				//追加视频合集列表
				$(".search-result-main-content .search-content-left").append(content);
				//加载各个合集的第一个视频数据
				for (var t = 0; t < firstVideoArray.length; t++) {
					getStationListByVideoId(firstVideoArray[t].videoId, firstVideoArray[t].videoAlbumId);
				}
				//加载各个合集的标签
				for (var t = 0; t < videoAlbumList.length; t++) {
					getAlbumValueList(videoAlbumList[t].id);
				}
				//切换期数
				$(".compilations .episode-details .episode-details-bottom .videos").on("click", " li", function() {
					$(".compilations .episode-details .episode-details-bottom .videos li").removeClass("checked");
					$(this).addClass("checked");
					$(".search-content-left .video .unfold-before").removeClass("hide");
					$(".search-content-left .video .unfold-after-x").addClass("hide");
					//定位
					$('html,body').animate({
						scrollTop : $(this).parents(".video").offset().top - 60
					}, 500);
					$(this).parents(".episode-details").siblings(".unfold-before").addClass("hide");
					$(this).parents(".episode-details").siblings(".unfold-after-x").removeClass("hide");

					var videoAlbumId = $(this).parents("li[id^='videoAlbum_li_']").find("input[id^='videoAlbum_']").val();
					var videoId = $(this).attr("videoId");
					getStationListByVideoId(videoId, videoAlbumId);
				})
				//切换季
				$(".compilations .episode-details .episode-details-menu .one").on("click", " li", function() {
					$(".compilations .episode-details .episode-details-menu .one li").removeClass("checked");
					$(this).addClass("checked");
					getVideoListByAlbumId($(this));
				})
				//季 翻页
				$(".compilations .episode-details .episode-details-menu .one").each(function() {
					if ($(this).find("li").length <= 7 /* && document.documentElement.clientWidth>480*/ ) {
						$(this).siblings(".two").addClass("hide");
					}
				})
			}
			if (list != null && list.length > 0) {
				content = '';
				var video;
				var videoStationList;
				var videoStation;
				var videoList = new Array();
				for (var i = 0; i < list.length; i++) {
					video = list[i];
					videoStationList = video.videoStationList;
					videoList[i] = video.id;
					content += '<li class="video" id="video_li_' + video.id + '">';
					content += '<!-- 隐藏域 -->';
					content += '<input type="hidden" value="' + video.id + '" id="video_' + video.id + '">';
					content += '<!--标题-->';
					content += '<div class="title-x">';
					content += '    <span class="one"></span>';
					content += '    <h3 class="two">';
					content += '        <strong>';
					content += '            <a target="_blank" href="' + _path + '/video/' + video.shortId + '">' + video.searchTitle + '</a>';
					content += '        </strong>';
					content += '    </h3>';
					content += '</div>';
					content += '<!--视频封面和介绍-->';
					content += '<div class="unfold-before">';
					content += '    <div class="images-x images-x-content">';
					content += ReferrerKiller.imageHtml(video.cover);
					content += '    </div>';
					content += '    <div class="two">';
					content += '    	<div><span>作者：</span><span id="upName_two_' + video.id + '">' + video.videoStationList[0].upName + '</span></div>';
					content += '        <p id="introduction_two_' + video.id + '">' + video.videoStationList[0].introduction + '</p>';
					content += '    </div>';
					content += '</div>';
					content += '<!--视频点击后播放-->';
					content += '<div class="unfold-after unfold-after-x hide" id="videoDiv_' + video.id + '">';
					if (video.videoStationList[0].station == '1' || video.videoStationList[0].station == '2') {
						content += '<iframe src="' + video.videoStationList[0].flashUrl + '" width="672" height="536" align="middle"></iframe>';
					} else {
						content += '	<embed width="672" height="536" allownetworking="all" allowscriptaccess="always" ';
						content += '		src="' + video.videoStationList[0].flashUrl + '" quality="high" bgcolor="#000" wmode="window"';
						content += '		allowfullscreen="true" allowFullScreenInteractive="true" type="application/x-shockwave-flash"';
						content += '		pluginspage="//www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash">';
					}
					content += '   <span class="time" id="duration_' + video.id + '">' + video.duration + '</span>';
					content += '</div>';
					content += '<!--切换视频来源-->';
					content += '<div class="video-source">';
					content += '    <div class="left" title="播放量">';
					content += '        <i class="iconfont icon-wf_bofang"></i>';
					content += '        <span class="two" id="viewCount_' + video.id + '">' + video.viewCount + '</span>';
					content += '    </div>';
					content += '    <div class="middle">';
					content += '        <i class="iconfont icon-wf_zuo_qiehuan"></i>';
					content += '       <div class="video-source-content">';
					content += '            <ul>';
					content += '               <li>';
					for (var j = 0; j < videoStationList.length; j++) {
						videoStation = videoStationList[j];
						if (j == 0) {
							content += '<i class="iconfont ' + videoStation.stationClass + ' checked" value="' + videoStation.id + '" onclick="changeFlash(this,\'' + video.id + '\');"></i>';
						} else {
							content += '<i class="iconfont ' + videoStation.stationClass + '" value="' + videoStation.id + '" onclick="changeFlash(this,\'' + video.id + '\');"></i>';
						}
						content += '<input type="hidden" id="flashUrl_' + videoStation.id + '" value="' + videoStation.flashUrl + '"/>';
						content += '<input type="hidden" id="url_' + videoStation.id + '" value="' + videoStation.url + '"/>';
						content += '<input type="hidden" id="introduction_' + videoStation.id + '" value="' + videoStation.introduction + '"/>';
						content += '<input type="hidden" id="upName_' + videoStation.id + '" value="' + videoStation.upName + '"/>';
						content += '<input type="hidden" id="station_' + videoStation.id + '" value="' + videoStation.station + '"/>';
					}
					content += '                </li>';
					content += '            </ul>';
					content += '        </div>';
					content += '         <i class="iconfont icon-wf_you_qiehuan"></i>';
					content += '    </div>';
					content += '    <div class="right">';
					content += '       <span class="grade-number">' + video.score + '分</span>';
					content += '       <span>评论：</span>';
					content += '       <strong><a target="_helf" href="' + _path + '/video/' + video.shortId + '?gto=comment" id="videoCommentCount_' + video.id + '">' + video.videoCommentCount + '</a></strong>';
					content += '    </div>';
					content += '</div>';
					content += ' <!--视频点击后简介模块-->';
					content += '<div class="grade unfold-after-x hide">';
					content += '    <div class="grade-x">';
					content += '        <div class="left">';
					content += '        	<div class="one"></div>';
					content += '        	<div class="two" title="' + video.videoStationList[0].introduction + '" id="introduction_' + video.id + '">';
					content += '        	    ' + video.videoStationList[0].introduction + '';
					content += '            </div>';
					content += '        </div>';
					content += '        <div class="right">';
					content += '            <input type="hidden" value="' + video.myScore + '" id="score_' + video.id + '">';
					content += '            <div class="grade-number-x">';
					content += '                <ul id="scoreClass_' + video.id + '">';
					content += '                    <li class="item1">';
					content += '                        <i class="iconfont icon-wf_weipingfen"></i>';
					content += '                        <i class="iconfont icon-wf_pingfen1"></i>';
					content += '                        <i class="iconfont icon-wf_pingfen"></i>';
					content += '                    </li>';
					content += '                    <li class="item2">';
					content += '                        <i class="iconfont icon-wf_weipingfen"></i>';
					content += '                        <i class="iconfont icon-wf_pingfen1"></i>';
					content += '                        <i class="iconfont icon-wf_pingfen"></i>';
					content += '                    </li>';
					content += '                    <li class="item3">';
					content += '                        <i class="iconfont icon-wf_weipingfen"></i>';
					content += '                        <i class="iconfont icon-wf_pingfen1"></i>';
					content += '                        <i class="iconfont icon-wf_pingfen"></i>';
					content += '                    </li>';
					content += '                    <li class="item4">';
					content += '                        <i class="iconfont icon-wf_weipingfen"></i>';
					content += '                        <i class="iconfont icon-wf_pingfen1"></i>';
					content += '                        <i class="iconfont icon-wf_pingfen"></i>';
					content += '                    </li>';
					content += '                    <li class="item5">';
					content += '                        <i class="iconfont icon-wf_weipingfen"></i>';
					content += '                        <i class="iconfont icon-wf_pingfen1"></i>';
					content += '                        <i class="iconfont icon-wf_pingfen"></i>';
					content += '                    </li>';
					content += '                </ul>';
					content += '                <div class="two">';
					content += '                    <div class="item1-one"></div>';
					content += '                    <div class="item1-two"></div>';
					content += '                    <div class="item2-one"></div>';
					content += '                    <div class="item2-two"></div>';
					content += '                    <div class="item3-one"></div>';
					content += '                    <div class="item3-two"></div>';
					content += '                    <div class="item4-one"></div>';
					content += '                    <div class="item4-two"></div>';
					content += '                    <div class="item5-one"></div>';
					content += '                    <div class="item5-two"></div>';
					content += '                </div>';
					content += '                <span class="three"></span>';
					content += '            </div>';
					content += '        </div>';
					content += '        <div class="author">';
					content += '            <span>作者:</span>';
					content += '            <span id="upName_' + video.id + '" title="' + video.videoStationList[0].upName + '">' + video.videoStationList[0].upName + '</span>';
					content += '        </div>';
					content += '    </div>';
					content += '</div>';
					content += '<!--评论未展开-->';
					content += '<div class="comment-no">';
					content += '    <ul class="comment-inside" id="comment-no-ul_' + video.id + '">';
					content += '    </ul>';
					content += '</div>';
					content += '<div class="comment-x hide">';
					content += '    <!--评论展开后分享模块-->';
					content += '    <div class="share">';
					content += '        <span class="one">分享：</span>';
					content += '        <div class="two">';
					content += '            <i class="iconfont iconfont-x icon-qq" onclick="share(\'1\',\'' + ip + 'video/' + video.shortId + '\',\'' + video.title + '\',\'' + video.videoStationList[0].introduction + '\',\'' + video.cover + '\')"></i>';
					content += '             <!--<i class="iconfont iconfont-x icon-weixin"></i> -->';
					content += '            <i class="iconfont iconfont-x icon-weibo" onclick="share(\'2\',\'' + ip + 'video/' + video.shortId + '\',\'' + video.title + '\',\'' + video.videoStationList[0].introduction + '\',\'' + video.cover + '\')"></i>';
					content += '        </div>';
					content += '        <strong class="three">';
					content += '            <a id="url_' + video.id + '" target="_helf" href="' + video.videoStationList[0].url + '">视频来源</a>';
					content += '        </strong>';
					content += '    </div>';
					content += '    <!--评论已展开-->';
					content += '    <div class="comment-yes">';
					content += '        <ul class="comment-inside">';
					content += '        </ul>';
					content += '    </div>';
					content += '    <div class="comment-fb-outside">';
					content += '	    <div class="header images-x images-x-content">';
					content += '	        <img id="myPhoto65_' + video.id + '" src="' + $("#userPhoto_65").val() + '">';
					content += '	        <span id="myZM_' + video.id + '" class="hide">Z</span>';
					content += '	    </div>';
					content += '	    <div class="hf-x hide">';
					content += '	        <span>回复：</span>';
					content += '	        <span class="name"></span>';
					content += '	    </div>';
					content += '	    <input type="text" id="comment-content_' + video.id + '" class="comment-fb" placeholder="添加评论...">';
					content += '	    <i class="iconfont icon-wf_huiche iconfont-x"></i>';
					content += '	</div>';
					content += '</div>';
					content += '</li>';
				}
				//移除预加载列表
				$(".search-result-main-content .search-content-left .video-yjz").remove();
				//追加视频列表
				$(".search-result-main-content .search-content-left").append(content);

				if (count < 3 && totalPages > 1) {
					//加载更多样式
					$(".search-content-left li.video:last").after($("#loadMore"));
					$("#loadMore").removeClass("hide");
				} else {
					$("#loadMore").remove();
				}

				//搜索结果视频初始化控件
				searchVideo_init();

				//加载评论
				for (var i = 0; i < videoList.length; i++) {
					var videoId = videoList[i];
					pageMap[videoId] = {
						pageSize : 10,
						startRecord : 0,
						nowPage : 1,
						recordCount : -1,
						pageCount : -1,
						lastPage : false
					};
					//加载评论列表
					loadCommentList(videoId, true);
				}
				//初始化分页插件
				initPage(data.startPage, data.endPage);
				//移除没有找到模块
				$("li.no-find").remove();
			}
			if ((videoAlbumList != null && videoAlbumList.length > 0) || (list != null && list.length > 0)) {
				//搜索结果公用初始化控件
				search_init();
			} else {
				//移除预加载列表
				$(".search-result-main-content .search-content-left .video-yjz").remove();
				//极验
				if (parseInt(totalPages) < 1) {
					$("li.no-find").removeClass("hide");
					$("#loadMore").remove();
					//初始化极验
					initGtCaptcha();
				}
			}
		}
	});
}

//查询视频合集标签列表
function getAlbumValueList(albumId) {
	var url = _path + "/videoAlbum/getValueList";
	var dataJson = {
		albumId : albumId
	};
	ajaxMethod(url, dataJson, "post", true, function(data) {
		if (data.success) {
			var list = data.data;
			var content = '';
			for (var i = 0; i < list.length; i++) {
				content += '                <div>';
				content += '                    <i class="iconfont icon-wf_biaoqiantou"></i>';
				content += '                    <span>' + list[i].name + '</span>';
				content += '                </div>';
			}
			$("#albumValue_" + albumId).html(content);
		}
	})
}

//查询视频标签列表
function getVideoValueList(albumId, videoId) {
	var url = _path + "/videoValue";
	var dataJson = {
		videoId : videoId
	};
	ajaxMethod(url, dataJson, "post", true, function(data) {
		var videoValueList = data.videoValueList;
		var content = "";
		for (var i = 0; i < videoValueList.length; i++) {
			content += '        <div class="label-x-inside">';
			content += '           <i class="iconfont icon-wf_biaoqiantou"></i>';
			content += '           <span class="label-x-inside-content">' + videoValueList[i].value.name + '</span>';
			content += '        </div>';
		}
		content += '            <div class="fix" style="clear: both;display: block"></div>';
		$("#abVideoValue_" + albumId).html(content);
	})
}

//拼接站点切换
function mosaicStation(video, videoAlbumId) {
	var content = '';
	var videoStation;
	for (var k = 0; k < video.videoStationList.length; k++) {
		videoStation = video.videoStationList[k];
		if (k == 0) {
			content += '			<i class="iconfont ' + videoStation.stationClass + ' checked" value="' + videoStation.id + '" onclick="changeAlbumFlash(this,\'' + video.id + '\',\'' + videoAlbumId + '\');"></i>';
		} else {
			content += '			<i class="iconfont ' + videoStation.stationClass + '" value="' + videoStation.id + '" onclick="changeAlbumFlash(this,\'' + video.id + '\',\'' + videoAlbumId + '\');"></i>';
		}
		content += '				<input type="hidden" id="aFlashUrl_' + video.id + '_' + videoStation.id + '" value="' + videoStation.flashUrl + '"/>';
		content += '				<input type="hidden" id="aUrl_' + video.id + '_' + videoStation.id + '" value="' + videoStation.url + '"/>';
		content += '				<input type="hidden" id="aIntroduction_' + video.id + '_' + videoStation.id + '" value="' + videoStation.introduction + '"/>';
		content += '				<input type="hidden" id="aUpName_' + video.id + '_' + videoStation.id + '" value="' + videoStation.upName + '"/>';
		content += '				<input type="hidden" id="aStation_' + video.id + '_' + videoStation.id + '" value="' + videoStation.station + '"/>';
	}
	return content;
}

//拼接视频列表
function mosaicVideoList(videoList) {
	var content = '';
	var video;
	for (var j = 0; j < videoList.length; j++) {
		video = videoList[j];
		if (j == 0) {
			content += '            <li class="checked" videoId="' + video.id + '">';
		} else {
			content += '            <li videoId="' + video.id + '">';
		}
		content += '                    <div class="top images-x images-x-content">';
		content += ReferrerKiller.imageHtml(video.cover);
		content += '                        <p class="two">';
		content += '                            <i class="iconfont icon-wf_bofang"></i>';
		content += '                            <span class="qi">第' + getChineseByNum(Number(video.albumIndex)) + '期</span>';
		content += '                        </p>';
		if (j == 0) {
			content += '                        <span class="new">最新</span>';
		}
		content += '                    </div>';
		content += '                    <p class="bottom" title="' + video.title + '">' + video.title + '</p>';
		content += '                </li>';
	}
	return content;
}

//拼接播放器
function mosaicVideoPlay(videoStation, duration, videoAlbumId) {
	var content = '';
	if (videoStation.station == '1' || videoStation.station == '2') {
		content += '	<iframe src="' + videoStation.flashUrl + '" width="672" height="536" align="middle"></iframe>';
	} else {
		content += '	<embed width="672" height="536" allownetworking="all" allowscriptaccess="always" ';
		content += '		src="' + videoStation.flashUrl + '" quality="high" bgcolor="#000" wmode="window"';
		content += '		allowfullscreen="true" allowFullScreenInteractive="true" type="application/x-shockwave-flash"';
		content += '		pluginspage="//www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash">';
	}
	content += '       <span class="time" id="aDuration_' + videoAlbumId + '">' + secToTime(duration) + '</span>';
	return content;
}

//切换合集视频，加载站点列表
function getStationListByVideoId(videoId, videoAlbumId) {
	var url = _path + "/video/getVideoStationList";
	var dataJson = {
		videoId : videoId
	};
	ajaxMethod(url, dataJson, "post", true, function(data) {
		if (data.success) {

			//替换隐藏元素
			var video = data.data;
			var content = mosaicStation(video, videoAlbumId);
			$("#albumHidLi_" + videoAlbumId).html(content);

			// 替换视频播放器内容
			content = mosaicVideoPlay(video.videoStationList[0], video.duration, videoAlbumId);
			$("#aVideoDiv_" + videoAlbumId).html(content);

			//修改页面信息
			updatePageInfo(videoAlbumId, video);

			//搜索结果视频合集初始化控件
			searchAlbum_init();

			//修改视频标签
			getVideoValueList(videoAlbumId, videoId);
		}
	})
}

//切换合集期数，加载视频列表
function getVideoListByAlbumId(album) {
	var pid = $(album).attr("pid");
	var albumId = $(album).attr("albumId");
	var url = _path + "/video/getAlbumVideoList";
	var dataJson = {
		albumId : albumId
	};
	ajaxMethod(url, dataJson, "post", true, function(data) {
		if (data.success) {
			var content = '';
			var videoList = data.list;
			content = mosaicVideoList(videoList);
			$("#albumVideos_" + pid).html(content);

			var video = videoList[0];
			getStationListByVideoId(video.id, pid);

			$("#sonAlbumNameValue_" + pid).val('<font style="color:#db4437">' + $(album).html() + '</font>');
			//合集名称a标签
			$("#albumName_" + pid).html($("#albumNameValue_" + pid).val() + $("#sonAlbumNameValue_" + pid).val());
		}
	})
}

//修改页面信息，切换期数调用
function updatePageInfo(pid, video) {
	//播放量
	$("#aViewCount_" + pid).html(getCountStr(video.viewCount));
	//评分
	$("#aScore_" + pid).text(video.score + '分');
	//视频点击后
	//简介
	$("#abIntroduction_" + pid).text(video.videoStationList[0].introduction).attr("title", video.videoStationList[0].introduction);
	//我的评分
	$("#abScore_" + pid).val(video.myScore);
	// 用户视频评分样式
	$("#abScoreClass_" + pid).attr("class", "").addClass(getScoreClass(video.myScore));
	//作者
	$("#abUpName_" + pid).text(video.videoStationList[0].upName).attr("title", video.videoStationList[0].upName);
	//查看更多a标签路径
	$("#abSeeMore_" + pid).attr("href", _path + "/video/" + video.shortId);
	//合集名称a标签
	$("#albumName_" + pid).attr("href", _path + "/video/" + video.shortId).html($("#albumNameValue_" + pid).val() + $("#sonAlbumNameValue_" + pid).val() +'<font style="color:#db4437">：</font>'+ video.title);
}

//分页模块
function initPage(startPage, endPage) {
	if (totalPages > 1) {
		var content = '';
		for (var i = startPage; i <= endPage; i++) {
			if (current == i) {
				content += '<li class="checked">';
				content += '<span class="one">o</span>';
				content += '<span class="two">' + i + '</span>';
				content += '</li>';
			} else {
				content += '<li onclick="search(' + i + ')">';
				content += '<span class="one">o</span>';
				content += '<span class="two">' + i + '</span>';
				content += '</li>';
			}
		}
		$("#middle-page").html(content);
		if (parseInt(current) == 1) {
			$("#left div.fy").addClass("hide");
		} else {
			$("#left div.fy").removeClass("hide").attr("onclick", "search(" + (parseInt(current) - 1) + ")");
		}
		if (parseInt(current) == parseInt(totalPages)) {
			$("#right div.fy").addClass("hide");
		} else {
			$("#right div.fy").removeClass("hide").attr("onclick", "search(" + (parseInt(current) + 1) + ")");
		}
		$(".search-content-left li.video:last").after($(".page-turning"));
		$("#loadMore").after($(".page-turning"));
		$(".page-turning").removeClass("hide");
	} else {
		$(".page-turning").remove();
	}
}

//初始化极验
function initGtCaptcha() {
	var handler1 = function(captchaObj) {
		captchaObj.onSuccess(function() {
			$("#submitKeywordBtn").removeAttr("disabled");

			$("#submitKeywordBtn").click(function() {
				submitKeyword();
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

//滚动加载通知数据
$(document).ready(function() {
	$(window).unbind("scroll");
	$(window).scroll(function() {
		var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
		if ($(document).height() <= totalheight) {
			if (parseInt(current) < parseInt(totalPages)) {
				if (count < 3) {
					count++;
					current++;
					loadList();
				} else {
					//alert("已经三次了");
				}
			} else {
				//暂无更多数据
			}
		}
	});
});

//切换站点
function changeFlash(obj, videoId) {
	if (!$(obj).hasClass("checked")) {
		var stationId = $(obj).attr("value");
		var station = $("#station_" + stationId).val();
		var flashUrl = $("#flashUrl_" + stationId).val();
		var url = $("#url_" + stationId).val();
		var introduction = $("#introduction_" + stationId).val();
		var upName = $("#upName_" + stationId).val();
		// 切换视频
		$("#videoDiv_" + videoId + " embed").remove();
		$("#videoDiv_" + videoId + " iframe").remove();
		if (station == 1 || station == 2) { // AcFun bilibili
			var str = '<iframe src="' + flashUrl + '" width="672" height="536" align="middle"></iframe>';
			$("#videoDiv_" + videoId).prepend(str);
		} else {
			var str = '<embed width="672" height="536" allownetworking="all" allowscriptaccess="always"';
			str += 'src="' + flashUrl + '" quality="high" bgcolor="#000" wmode="window"';
			str += 'allowfullscreen="true" allowFullScreenInteractive="true" type="application/x-shockwave-flash"';
			str += 'pluginspage="//www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash">';
			$("#videoDiv_" + videoId).prepend(str);
		}

		$("#url_" + videoId).attr("href", url);
		$("#introduction_" + videoId).html(introduction);
		$("#introduction_" + videoId).attr("title", introduction);
		$("#upName_" + videoId).html(upName);
		$("#upName_two_" + videoId).html(upName);
		$("#introduction_two_" + videoId).html(introduction);
		// 切换站点
		$(obj).addClass("checked").siblings("i").removeClass("checked");
	}
}

//切换合集中视频的站点
function changeAlbumFlash(obj, videoId, videoAlbumId) {
	if (!$(obj).hasClass("checked")) {
		console.log(1);
		var stationId = $(obj).attr("value");
		var station = $("#aStation_" + videoId + "_" + stationId).val();
		var flashUrl = $("#aFlashUrl_" + videoId + "_" + stationId).val();
		var url = $("#aUrl_" + videoId + "_" + stationId).val();
		var introduction = $("#aIntroduction_" + videoId + "_" + stationId).val();
		var upName = $("#aUpName_" + videoId + "_" + stationId).val();
		// 切换视频
		$("#aVideoDiv_" + videoAlbumId + " embed").remove();
		$("#aVideoDiv_" + videoAlbumId + " iframe").remove();
		if (station == 1 || station == 2) { // AcFun bilibili
			var str = '<iframe src="' + flashUrl + '" width="672" height="536" align="middle"></iframe>';
			$("#aVideoDiv_" + videoAlbumId).prepend(str);
		} else {
			var str = '<embed width="672" height="536" allownetworking="all" allowscriptaccess="always"';
			str += 'src="' + flashUrl + '" quality="high" bgcolor="#000" wmode="window"';
			str += 'allowfullscreen="true" allowFullScreenInteractive="true" type="application/x-shockwave-flash"';
			str += 'pluginspage="//www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash">';
			$("#aVideoDiv_" + videoAlbumId).prepend(str);
		}
		//简介
		$("#abIntroduction_" + videoAlbumId).text(introduction).attr("title", introduction);
		//作者
		$("#abUpName_" + videoAlbumId).text(upName).attr("title", upName);
		// 切换站点
		$(obj).addClass("checked").siblings("i").removeClass("checked");
	}
}

//检验关键字
function checkSubmitKeyword() {
	var keyword = $("#sub_keyword");
	if (trim(keyword.val()) == '') {
		$(keyword).next().html("关键词不能为空");
		$(keyword).next().addClass("error");
		$(keyword).next().parent(".form-group").addClass("has-error");
		$(keyword).next().show();
		return false;
	} else {
		$(keyword).next().html("");
		$(keyword).next().removeClass("error");
		$(keyword).next().parent(".form-group").removeClass("has-error");
		$(keyword).next().hide();
	}
	return true;
}

//检验关键字url链接
function checkSubmitUrl(obj) {
	var submitUrl = $(obj);
	if (trim(submitUrl.val()) == '') {
		$(submitUrl).next().html("关键词的url不能为空");
		$(submitUrl).next().addClass("error");
		$(submitUrl).next().parent(".form-group").addClass("has-error");
		$(submitUrl).next().show();
		return false;
	} else if (!isUrl(trim(submitUrl.val()))) {
		$(submitUrl).next().html("请正确填写关键词的url");
		$(submitUrl).next().addClass("error");
		$(submitUrl).next().parent(".form-group").addClass("has-error");
		$(submitUrl).next().show();
		return false;
	} else {
		$(submitUrl).next().html("");
		$(submitUrl).next().removeClass("error");
		$(submitUrl).next().parent(".form-group").removeClass("has-error");
		$(submitUrl).next().hide();
	}
	return true;
}

//提交关键词
function submitKeyword() {
	var token = $("#token").val();

	var keyword = $("#sub_keyword");
	if (!checkSubmitKeyword()) {
		$(keyword).focus();
		return false;
	}

	var submitUrlList = $(".no-find input[name='submitUrlList']");
	var submitUrlArray = [];
	for (var i = 0; i < submitUrlList.length; i++) {
		var submitUrl = $(submitUrlList[i]);
		if (!checkSubmitUrl(submitUrl)) {
			$(submitUrl).focus();
			return false;
		}
		submitUrlArray.push(trim(submitUrl.val()));
	}
	var url = _path + "/submitKeyword/add";
	var dataJson = {
		token : token,
		keyword : trim(keyword.val()),
		submitUrls : submitUrlArray
	};
	ajaxMethod(url, dataJson, "post", false, function(data) {
		if (data.success) {
			layer.msg(data.message, {
				title : "",
				icon : 1
			});
			window.location.href = _path + "/index";
		} else {
			layer.msg(data.message, {
				title : "",
				icon : 2
			});
		}
	});
}

//打分
function addOrUpdateGrade(obj) {
	if (userId == "" || userId == undefined) { // 登录用户
		window.location.href = _path + "/login?returnUrl=" + encodeURIComponent(window.location.href);
		return;
	}
	var videoId = $(obj).parents("li[id^='video_li_']").find("input[id^='video_']").val();
	var score = getScoreNum($("#scoreClass_" + videoId).attr("class"));
	if (videoId == undefined) {
		var videoAlbumId = $(obj).parents("li[id^='videoAlbum_li_']").find("input[id^='videoAlbum_']").val();
		videoId = $("#albumVideos_" + videoAlbumId).children("li[class='checked']").attr("videoId");
		score = getScoreNum($("#abScoreClass_" + videoAlbumId).attr("class"));
	}

	var url = _path + "/video/addOrUpdateGrade";
	ajaxMethod(url, {
		"videoId" : videoId,
		"score" : score
	}, "post", true, function(backData) {
		if (backData.success) {
			layer.alert(backData.message, {
				title : "",
				icon : 6,
				time : 1000
			});
		} else {
			layer.alert(backData.message, {
				title : "",
				icon : 5,
				time : 1000
			});
		}
	});
}