$(function() {
	initUpTag();//初始化标签
	initUpRelation();//初始化相关人物
	initUpSecondLevel();//初始化人物二级信息
	var first = true;
	var pageNum = 1;
	loadRelationVideoList(first);//初始化相关视频
});

function initUpTag() {
	var upId = $("#upId").val();
	$.ajax({
        type : "POST",
        url :  _path + "/p/t/" + upId,
        dataType : "json",
        async : true,
        success : function(result) {
        	if (result && result.upValueList) {
        		var upValueList = result.upValueList;
        		var upValueLength = upValueList.length;
        		if (upValueLength > 0) {
        			var upValueContent = "";
        			for (var i = 0; i < upValueLength; i++) {
        				var upValue = upValueList[i];
        				upValueContent += '<li><i class="iconfont icon-wf_biaoqiantou"></i><span>' + upValue.value.name + '</span></li>';
        			}
        			$("#upTag").html(upValueContent);
        			$("#upTag").parent().removeClass("hide");
        		}
        		
        	}
        },
        error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
    });
}

function initUpSecondLevel() {
	var upId = $("#upId").val();
	$.ajax({
        type : "POST",
        url :  _path + "/p/l/" + upId,
        dataType : "json",
        async : true,
        success : function(result) {
        	if (result && result.upSecondLevelList) {
        		var upSecondLevelList = result.upSecondLevelList;
        		var levelLength = upSecondLevelList.length;
        		var levelContent = "";
        		for (var i = 0; i < levelLength; i++) {
        			var upSecondLevel = upSecondLevelList[i];
        			levelContent += '<div class="gameachievements">';
        			//levelContent += 	'<a href="#" class="iconfont icon-wf_bianji"></a>';
        			levelContent += 	'<div class="title">';
        			levelContent += 		'<h3>' + upSecondLevel.title + '</h3>';
        			levelContent += 	'</div>';
        			levelContent += 	'<div class="content">';
        			levelContent += 		'<div>';
        			levelContent += 			upSecondLevel.content;
        			levelContent += 		'</div>';
        			if (upSecondLevel.mainPhoto != null && upSecondLevel.mainPhoto != '') {
        				levelContent += 		'<div class="teamwrap upSecondLevelPhoto" upLevelId="' + upSecondLevel.id + '" data-toggle="modal" data-target="#myModal">';
        				levelContent += 			'<div class="team">';
        				levelContent += 				'<img src="' + upSecondLevel.mainPhoto + '" alt="" width="100%" height="100%">';
        				levelContent += 			'</div>';
        				levelContent += 		'</div>';
        				
        				appendBodyPhoto(upSecondLevel.upPhotoList, "levelPhoto_" + upSecondLevel.id);
        			}
        			levelContent += 	'</div>';
        			levelContent += '</div>';
        		}
        		$("#upSecondLevelDIV").html(levelContent);
        		$(".upSecondLevelPhoto").click(function() {
        			var secondLevelId = $(this).attr("upLevelId");
        			$(".modal-body").html($("#levelPhoto_" + secondLevelId).html());
        		});
        		$('#myModal').on('shown.bs.modal',
        			    function() {
        			        swiper();
        			    })
        	}
        },
        error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
    });
}

function appendBodyPhoto(upPhotoList, id) {
	if (upPhotoList && upPhotoList.length > 0) {
		var content = '<div class="hide" id="' + id + '">';
		var photoLength = upPhotoList.length;
		content += '<div class="swiper-container gallery-left">';
		content +=     '<div class="swiper-wrapper">';
		for (var i=0; i<photoLength; i++) {
			var upPhoto = upPhotoList[i];
			content +=         '<div class="swiper-slide"><img src="' + upPhoto.photo + '" alt="" ></div>';
		}
		content +=     '</div>';
		content += '</div>';
		content += '<div class="swiper-button-next"><i class="iconfont icon-wf_you_qiehuan"></i></div>';
		content += '<div class="swiper-button-prev"><i class="iconfont icon-wf_zuo_qiehuan"></i></div>';
		content += '<div class="haha">';
		content +=     '<div class="swiper-container gallery-thumbs">';
		content +=         '<div class="swiper-wrapper">';
		for (var i=0; i<photoLength; i++) {
			var upPhoto = upPhotoList[i];
			content +=         '<div class="swiper-slide"><img src="' + upPhoto.photo + '" alt="" width="100%" height="100%"></div>';
		}
		content +=         '</div>';
		content += 	'</div>';
		content += 	'<div class="button-bottom"><i class="iconfont icon-wf_yincangxianshi1"></i></div>';
		content += 	'<div class="button-top"><i class="iconfont icon-wf_yincangxianshi"></i></div>';
		content += '</div>';
		
		content += '</div>';
		$("body").append(content);
		
	}
}


function initUpRelation() {
	var upId = $("#upId").val();
	$.ajax({
        type : "POST",
        url :  _path + "/p/r/" + upId,
        dataType : "json",
        async : true,
        success : function(result) {
        	if (result && result.upRelationList) {
        		var upRelationList = result.upRelationList;
        		var relationLength = upRelationList.length;
        		if (relationLength > 0) {
        			$("#upRelationDiv").removeClass("hide");
        			var relationContent = "";
        			for (var i = 0; i < relationLength; i++) {
        				var upRelation = upRelationList[i];
        				relationContent += '<div class="content-item">';
        				relationContent += 	'<div class="imgwrap">';
        				relationContent += 		'<a href="' + _path + '/p/' + upRelation.relationUp.id + '">';
        				relationContent += 			'<img src="' + upRelation.relationUp.avatar +'" width="100%" height="100%" alt="">';
        				relationContent += 		'</a>';
        				relationContent += 	'</div>';
        				relationContent += 	'<p><strong><a title="' + upRelation.relationUp.name + '" href="' + _path + '/p/' + upRelation.relationUp.id + '">' + upRelation.relationUp.name + '</a></strong></p>';
        				relationContent += '</div>';
        			}
        			$("#upRelationContent").html(relationContent);
        		}
        	}
        },
        error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
    });
}

/**
 * 初始化相关视频
 */
function initRelationVideo() {
	var upId = $("#upId").val();
	$.ajax({
        type : "POST",
        url :  _path + "/p/v/" + upId,
        dataType : "json",
        async : true,
        success : function(result) {
        	if (result && result.videoList) {
        		var videoList = result.videoList;
        		var videoLength = videoList.length;
        		if (videoLength > 0) {
        			$(".interrelatevideo").removeClass("hide");
        			var videoContent = "";
        			for (var i = 0; i < videoLength; i++) {
        				var video = videoList[i];
        				videoContent += '<div class="video-item">';
        				videoContent += 	'<div class="vimg-wrap">';
        				videoContent += 		'<div class="zhezhaobottom"></div>';
        				videoContent += 		'<a title="' + video.title + '" href="' + _path + '/video/' + video.shortId + '">' + ReferrerKiller.imageHtml(video.cover,_path + '/video/' + video.shortId) + '</a>';
        				videoContent += 		'<b>' + video.score + '分</b>'
        				videoContent += 	'</div>';
        				videoContent += 	'<p>' + video.title + '</p>';
        				videoContent += '</div>';
        			}
        			$(".videos").html(videoContent);
        		}
        	}
        },
        error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
    });
}

function loadRelationVideoList(_first) {
	var upId = $("#upId").val();
	var title = $("#title").val();
	if(_first != undefined && _first) {// 第一次点击，初始化
		first = _first;
		pageNum = 1;
	}
	
	var url = _path + "/p/v";
	var dataJson = {};
	dataJson.pageSize = "10";
	dataJson.nowPage = pageNum;// 页码
	pageNum = pageNum + 1;// 下一页
	
	var parameters = {};
	parameters.upId = upId;
	parameters.title = title;
	dataJson.parameters = parameters;
	
	ajaxMethod(url, {"gridPager":JSON.stringify(dataJson)}, "post", true, function(backData) {
		var content = "";
		var videoContent = "";
		var videoList = backData.list;
		if(videoList.length > 0) {
			var videoLength = videoList.length;
			$(".interrelatevideo").removeClass("hide");
			for (var i = 0; i < videoLength; i++) {
				var video = videoList[i];
				videoContent += '<div class="video-item">';
				videoContent += 	'<div class="vimg-wrap">';
				videoContent += 		'<a title="' + video.title + '" href="' + _path + '/video/' + video.shortId + '">' + ReferrerKiller.imageHtml(video.cover,_path + '/video/' + video.shortId)+ '</a>';
				videoContent += 		'<span class="totaltime">' + secToTime(video.duration) + '</span>';
				videoContent += 	'</div>';
				videoContent += 	'<div class="textwrap">';
				videoContent += 		'<a title="' + video.title + '" href="' + _path + '/video/' + video.shortId + '"><p>' + video.title + '</p></a>';
				videoContent += 		'<b>' + video.score + '分</b>';
				videoContent += 	'</div>';
				videoContent += '</div>';
				
			}
		}else{
			content += '';
		} 
		if(first) {
			$(".videos").html(videoContent);
		} else {
			$(".videos").append(videoContent);
		}
		
		var pageCount = backData.pageCount;
		if(videoContent.length > 0 && pageCount >= pageNum) {
			first = false;
			$("#videoMore").removeClass("hide");
			$("#videoNoMore").addClass("hide");
		} else {
			$("#videoMore").addClass("hide");
			$("#videoNoMore").removeClass("hide");
		}
		
		/*$(".c-informgroup").click(function(){
            $(this).toggleClass("addword");
        });*/
	})
}