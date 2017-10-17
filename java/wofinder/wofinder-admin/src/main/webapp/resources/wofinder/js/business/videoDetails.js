//分页信息
var pageSize_v = 10; //每页显示条数
var startRecord_v = 0;	//开始记录数
var nowPage_v = 1;  //当前页
var pageCount_v = -1;	//总页数
var recordCount_v =-1;	//总记录数
var stationId;
var lastPage_v = false;
$(function() {
	drawNext();
	
	//定位到评论列表
	if($("#gto").val()=='comment'){
		window.location.hash = "#comment";
	}
	stationId = $("#stationId").val();
	// 视频播放量
	$("#viewCount").html(getCountStr($("#viewCount").html()));
	// 评论量
	$("#videoCommentCount").html(getCountStr($("#videoCommentCount").html()));
	
	//评论框
	if($("#userPhoto_65").val() == "") {// 用户没有上传头像
		var str = getPinY($("#userPhoto_65").val());
		if(str != "" && str != undefined) {
			$("#myZM").removeClass("hide");
			$("#myZM").text(str);
			$("#myPhoto65").addClass("hide");
		} else {
			$("#myPhoto65").attr("src",_staticPrefix +"/images/mr-header.png");
			$("#myZM").addClass("hide");
		}
	}
	
	// 其他站点视频
	getVideoStationList();
	// 视频合辑
	getVideoAlbumList();
	// 相关视频
	getRelatedVideo(true);
	
	//切换提交评分
	$(".grade-number-x .two div").on("click" ,function(){
    	addOrUpdateGrade($(this));
    });
	
//	setCss();
});

function getVideoStationList() {
	var videoId = $(".video").attr("value");
	var url = _path + "/video/getVideoStationList";
	ajaxMethod(url, {"videoId":videoId, "all": true}, "post", true, function(backData) {
		if(backData.success) {
			var data = backData.data;
			$("#viewCount").text(getCountStr(data.viewCount));
			$("#videoCommentCount").text(getCountStr(data.videoCommentCount));
			// 视频评分样式
			$("#score").val(data.myScore);
			$("#scoreClass").addClass(getScoreClass(data.myScore));
			var list = data.videoStationList;
			if(list) {
				var content = '';
				var length = list.length;
				for(var i=0; i<length; i++) {
					var videoStation = list[i];
					var id = videoStation.id;
					if(i == 0) {
						content += '<i class="iconfont '+videoStation.stationClass+' checked" value="'+id+'" onclick="changeFlash(this);"></i>';
					} else {
						content += '<i class="iconfont '+videoStation.stationClass+'" value="'+id+'" onclick="changeFlash(this);"></i>';
					}
					content += '<input type="hidden" id="flashUrl_'+id+'" value="'+videoStation.flashUrl+'"/>';
					content += '<input type="hidden" id="url_'+id+'" value="'+videoStation.url+'"/>';
					content += '<input type="hidden" id="introduction_'+id+'" value="'+videoStation.introduction+'"/>';
					content += '<input type="hidden" id="upName_'+id+'" value="'+videoStation.upName+'"/>';
					content += '<input type="hidden" id="station_'+id+'" value="'+videoStation.station+'"/>';
				}
				
				$("#firstVideo").addClass("hide");
				$("#secondVideo").removeClass("hide");
				$("#video_li_"+videoId).removeClass("video-yjz");
				$("#stationList_li").html(content);
			}
		} else {
			layer.alert(backData.message, {title:"",icon: 5,time: 2000});
		}
	});
}

function addOrUpdateGrade() {
	if(userId == "" || userId == undefined) {// 登录用户
		window.location.href = _path+"/login?returnUrl="+encodeURIComponent(window.location.href);
		return;
	}
	var flag = true;
	var videoId = $(".video").attr("value");
	var score = getScoreNum($("#scoreClass").attr("class"));
	var url = _path + "/video/addOrUpdateGrade";
	ajaxMethod(url, {"videoId":videoId, "score":score}, "post", true, function(backData) {
		flag = backData.success;
		if(backData.success) {
			layer.alert(backData.message, {title:"",icon: 6,time: 1000});
		} else {
			layer.alert(backData.message, {title:"",icon: 5,time: 2000});
		}
	});
	
	return flag;
}

//切换站点
function changeFlash(obj) {
	if(!$(obj).hasClass("checked")) {
		var id = $(obj).attr("value");
		stationId = id;
		var station = $("#station_"+id).val();
		var flashUrl = $("#flashUrl_"+id).val();
		var url = $("#url_"+id).val();
		var introduction = $("#introduction_"+id).val();
		var upName = $("#upName_"+id).val();
		// 切换视频
		$("#videoDiv embed").remove();
		$("#videoDiv iframe").remove();
		if(station == 1 || station == 2) {// AcFun bilibili
			var str = '<iframe src="'+flashUrl+'" width="672" height="536" align="middle"></iframe>';
			$("#videoDiv").prepend(str);
		} else {
			var str = '<embed width="672" height="536" allownetworking="all" allowscriptaccess="always"';
			str += 'src="'+flashUrl+'" quality="high" bgcolor="#000" wmode="window"';
			str += 'allowfullscreen="true" allowFullScreenInteractive="true" type="application/x-shockwave-flash"';
			str += 'pluginspage="//www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash">';
			$("#videoDiv").prepend(str);
		}
		$("#url").attr("href",url);
		$("#introduction").html(introduction);
		$("#introduction").attr("title",introduction);
		$("#upName").html(upName);
		$("#upName").attr("title", upName);
		// 切换站点
		$(obj).addClass("checked").siblings("i").removeClass("checked");
		
		getRelatedVideo();
	}
}

function getRelatedVideo(first) {
	//分页
	var gridPager = {};
	gridPager.pageSize = pageSize_v;
	gridPager.startRecord = startRecord_v;
	gridPager.nowPage = nowPage_v;
	gridPager.recordCount = recordCount_v;
	gridPager.pageCount = pageCount_v;
	
	var dataJson = {};
	dataJson.videoId = $(".video").attr("value");
	dataJson.category = $("#category").val();
	dataJson.orderBy = "t.v_view_count desc";
	dataJson.startCount = startRecord_v;
	dataJson.pageSize = pageSize_v;
	
	gridPager.parameters = dataJson;
	
	var url = _path + "/video/getRelatedVideo";
	ajaxMethod(url,{"gridPager":JSON.stringify(gridPager)},"post",true,function(backData){
		var content = '';
		if(backData.isSuccess) {
			setTimeout(function(){
				var data = backData.exhibitDatas;
				if(data && data.length > 0) {
					var length = data.length;
					var videoStation;
					for(var i=0; i<length; i++) {
						videoStation = data[i];
						
						content += '<li class="unfold-before" onclick="gotoRelated(\''+videoStation.shortId+'\')">';
						content += '<div class="images-x images-x-content">';
						content += '<span class="time">'+secToTime(videoStation.duration)+'</span>';
						content += ReferrerKiller.imageHtml(videoStation.cover);
						content += '</div>';
						content += '<div class="two">';
						content += '<p>'+videoStation.introduction+'</p>';
						content += '<span>'+videoStation.score+'分</span>';
						content += '</div>';
						content += '</li>';
					}
					
					$("#firstRelate").addClass("hide");
					$("#secondRelate").removeClass("hide");
					$("#relatedVideo").html(content);
				} else {
					$("#relatedVideo").html('<li class="no-correlation">暂无相关视频推荐~</li>');
				}
				
				startRecord_v = nowPage_v*pageSize_v;
				nowPage_v = backData.nowPage + 1;
			},200);
		} else {
			$("#relatedVideo").html('<li class="no-correlation">暂无相关视频推荐~</li>');
		}
		
	});
}

function gotoRelated(id, isRefresh) {
	var url = _path + "/video/" + id;
	if("true" == isRefresh) {
		window.location.href = url;
	} else {
		window.open(url,id);
	}
}

function getVideoAlbumList() {
	var albumId = $("#albumId").val();
	if(albumId == "" || albumId == undefined) {
		return;
	}
	var url = _path + "/videoAlbum/getVideoAlbumList";
	var dataJson = {"albumId":albumId, "all":true};
	ajaxMethod(url, dataJson, "post", true, function(backData){
		var content = '';
		if(backData.success) {
			var data = backData.data;
			if(data && data.length > 1) {
				var length = data.length;
				var videoAlbum;
				for(var i=0; i<length; i++) {
					videoAlbum = data[i];
					if(albumId == videoAlbum.id) {
						content += '<li value='+videoAlbum.id+' class="checked">第'+getChineseByNum(length-i)+'季</li>';
					} else {
						content += '<li value='+videoAlbum.id+'>第'+getChineseByNum(length-i)+'季</li>';
					}
				}
				
				if(length > 7) {
					$("#changeAlbum").css("display","block");
				}
				
				$(".episode-details-menu").css("display","block");
				$("#album_ul").html(content);
			}
		}
	});
	// 获取下属详情视频
	getAlbumVideoList(albumId);
}

var _albumId = "";
function getAlbumVideoList(albumId) {
	if(albumId == "" || albumId == undefined || albumId == _albumId) {
		return;
	}
	_albumId = albumId;
	var url = _path + "/video/getAlbumVideoList";
	var dataJson = {"albumId":albumId, "all":true};
	ajaxMethod(url, dataJson, "post", true, function(backData){
		var content = '';
		if(backData.success) {
			var data = backData.list;
			if(data && data.length > 0) {
				var length = data.length;
				var videoVo;
				var size;
				var videoId = $(".video").attr("value");
				for(var i=0; i<length; i++) {
					videoVo = data[i];
					
					if(videoId == videoVo.id ) {
						size = i + 1;
						content += '<li onclick="gotoRelated(\''+videoVo.shortId+'\',\'true\')" class="checked">';
					} else {
						content += '<li onclick="gotoRelated(\''+videoVo.shortId+'\',\'true\')">';
					}
					content += '<div class="top images-x images-x-content">';
					content += ReferrerKiller.imageHtml(videoVo.cover);
					content += '<p class="two">';
					content += '<i class="iconfont icon-wf_bofang"></i>';
					content += '<span class="qi">第'+getChineseByNum(length-i)+'期</span>';
					content += '</p>';
					content += '<div class="images-x-hover"></div>';
					content += '</div>';
					content += '<p class="bottom">'+videoVo.title+'</p>';
					content += '</li>';
				}
				
				if(length > 5) {
					$(".control-left").css("display","block");
					$(".control-right").css("display","block");
				}
				
				$("#video_ul").html(content);
				$(".episode-details-bottom").css("display","block");
				setAlbumLocation(length, size);
				setCss();
			}
		}
	});
}

function setAlbumLocation(total, size) {
	if(total > 5){
        if((total-2) > (total-size) && (total-size) > 2){
        	var px = (size-3) * (-137);
            $(".compilations .episode-details .episode-details-bottom .videos li:first-child").css("margin-left",px+"px");
        }else if((total-size) <= 2){
        	var px = (total-5) * (-137);
            $(".compilations .episode-details .episode-details-bottom .videos li:first-child").css("margin-left",px+"px");
        }
    } else {
        $(".compilations .episode-details .episode-details-bottom .videos li:first-child").css("margin-left","0px");
    }
}

function setIdToRedis(ids) {
	var url = _path + "/crawl/setIdToRedis";
	var dataJson = {"ids":ids};
	ajaxMethod(url, dataJson, "post", true, function(backData){
		if(backData.success) {
			layer.alert("更新缓存成功", {title:"",icon: 6,time: 1000});
			$("#videoRedis_btn").hide();
		}
	});
}

/***************************************** CSS样式 ****************************************************************************/

function setCss() {
    //切换季
    $(".compilations .episode-details .episode-details-menu .one").on("click", " li", function () {
        $(".compilations .episode-details .episode-details-menu .one li").removeClass("checked");
        $(this).addClass("checked");
        var albumId = $(this).attr("value");
        getAlbumVideoList(albumId);
    })
    //季 翻页
    $(".compilations .episode-details .episode-details-menu .one").each(function () {
        if ($(this).find("li").length <= 7/* && document.documentElement.clientWidth>480*/) {
            $(this).siblings(".two").addClass("hide");
        }
    })
    $(".compilations .episode-details .episode-details-menu .two .icon-wf_zuo_qiehuan").on("click", function () {
        var _this = $(this);
        var fi = _this.parent().siblings(".one-outside").find("li:first-child");
        var al = parseInt(fi.css("margin-left"));
        if (al != 0 && _this.hasClass("no-click") != true) {
            _this.addClass("no-click");
            _this.siblings(".icon-wf_you_qiehuan").addClass("no-click");
            fi.animate({
                "margin-left": al + 80 + "px"
            }, 288, function () {
                if (parseInt(fi.css("margin-left")) != 0) {
                    _this.removeClass("no-click");
                }
                _this.siblings(".icon-wf_you_qiehuan").removeClass("no-click");
            });
        }
    })
    $(".compilations .episode-details .episode-details-menu .two .icon-wf_you_qiehuan").on("click", function () {
        var _this = $(this);
        var fi = _this.parent().siblings(".one-outside").find("li:first-child");
        var al = parseInt(fi.css("margin-left"));
        var tl = $(this).parent().siblings(".one-outside").find("li").length - 7;
        if (al != -(tl * 80) && _this.hasClass("no-click") != true) {
            _this.addClass("no-click");
            _this.siblings(".icon-wf_zuo_qiehuan").addClass("no-click");
            fi.animate({
                "margin-left": al - 80 + "px"
            }, 288, function () {
                if (al != -((tl - 1) * 80)) {
                    _this.removeClass("no-click");
                }
                _this.siblings(".icon-wf_zuo_qiehuan").removeClass("no-click");
            });
        }
    })
    //期  翻页
    $(".compilations .episode-details .episode-details-bottom .control-left").on("click", function () {
        var _this = $(this);
        var fi = _this.siblings(".videos").find("li:first-child");
        var al = parseInt(fi.css("margin-left"));
        if (al != 0 && _this.hasClass("no-click") != true) {
            _this.addClass("no-click");
            _this.siblings(".control-right").addClass("no-click");
            fi.animate({
                "margin-left": al + 137 + "px"
            }, 288, function () {
                if (parseInt(fi.css("margin-left")) != 0) {
                    _this.removeClass("no-click");
                }
                _this.siblings(".control-right").removeClass("no-click");
            });
        }
    })
    $(".compilations .episode-details .episode-details-bottom .control-right").on("click", function () {
        var _this = $(this);
        var fi = _this.siblings(".videos").find("li:first-child");
        var al = parseInt(fi.css("margin-left"));
        var tl = $(this).siblings(".videos").find("li").length - 5;
        if (al != -(tl * 137) && _this.hasClass("no-click") != true) {
            _this.addClass("no-click");
            _this.siblings(".control-left").addClass("no-click");
            fi.animate({
                "margin-left": al - 137 + "px"
            }, 288, function () {
                if (al != -((tl - 1) * 137)) {
                    _this.removeClass("no-click");
                }
                _this.siblings(".control-left").removeClass("no-click");
            });
        }
    })
}
