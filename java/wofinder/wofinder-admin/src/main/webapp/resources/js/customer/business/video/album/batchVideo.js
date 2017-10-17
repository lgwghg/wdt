jQuery.validator.addMethod("checkSameVideo", function(value, ele, param) {
	var ind = 0;
	var videoIdArray=new Array();
	$("input[name^=videoId]").each(function(index,element) {
		videoIdArray[index] = $(element).val()+","+index;
		if($(ele).attr("name") == $(element).attr("name")){
			ind = index;
		}
	})
	
	var nary = videoIdArray.sort();
	
	for(var i = 0;i < nary.length;i++){
		var videoId = nary[i].split(",");
		if(videoId[0] ==''){
			continue;
		}
		if (ind != videoId[1] && videoId[0]==value){
			return false;
		}
	}
	return true;
}, "视频存在重复的，请修改或者删除！");

jQuery.validator.addMethod("checkSameIndex", function(value, ele, param) {
	var ind = 0;
	var videoIndexArray=new Array();
	$("input[name^=videoIndex]").each(function(index,element) {
		videoIndexArray[index] = $(element).val()+","+index;
		if($(ele).val() == $(element).val()){
			ind = index;
		}
	})
	
	var nary = videoIndexArray.sort();
	
	for(var i = 0;i < nary.length;i++){
		var videoIndex = nary[i].split(",");
		if(videoIndex[0] ==''){
			continue;
		}
		if (ind != videoIndex[1] && videoIndex[0]==value){
			return false;
		}
	}
	return true;
}, "所属专辑集数存在相同的，请修改！");

var deleteVideoIdArray = [];

var batchVideo = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#batchVideo-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                highlight : function(e) {
                    $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
                },
                success : function(e) {
                    $(e).closest('.form-group').removeClass('has-error').addClass('has-success');
                    $(e).remove();
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
                },
                submitHandler : function(form) {
                	if($("#batchVideo-form .video_div").length == 0){
                		layer.msg("还没有视频呢，添加一个吧", {
            	            icon : 0
            	        });
            	    	return;
                	}
                	var datas = [];
                	$("input[name^=videoId]").each(function(index,element) {
                		var map = '{"id":"'+$(element).val()+'","index":'+$("#videoIndex_"+index).val()+'}';
                		datas.push(map);
                	})
                    var albumId = $("#albumId").val();
                    var url = '/videoAlbumCtrl/batchVideo.html';
                    var jumpUrl = '/videoAlbumCtrl/listUI.html?id='+albumId+"&page=" + $("#pageNum").val() + "&rows=" + $("#pageSize").val() + "&sidx=" + $("#orderByColumn").val() + "&sord=" + $("#orderByType").val();
                    
                    //提交
                    var index;
                    var result;
                    $.ajax({
                        type : "POST",
                        url : sys.rootPath + url,
                        data : {
                        	albumId : albumId,
                        	datas : datas,
                        	delDatas : deleteVideoIdArray
                        },
                        dataType : "json",
                        beforeSend : function() {
                            index = layer.load();
                        },
                        success : function(resultdata) {
                            layer.close(index);
                            if (resultdata.success) {
                                layer.msg(resultdata.message, {
                                    icon : 1
                                });
                                webside.common.loadPage(jumpUrl);
                            } else {
                            	layer.msg(resultdata.message, {
                                    icon : 5
                                });
                            }
                        },
                        error : function(data, errorMsg) {
                            layer.close(index);
                            layer.msg(data.responseText, {
                                icon : 2
                            });
                        }
                    });
                }
            });
		}
	}
};

$(function() 
{
	batchVideo.form.validateForm();
    $('select').select2();
    
    $("#batchVideo-form .video_div").each(function(index,element) {
		$("#videoId_" + index).rules("add",{required : true,checkSameVideo : true, messages: {required : "请选择视频"}});
		$("#videoIndex_" + index).rules("add",{required : true,digits : true,min : 1,checkSameIndex : true, messages: {required : "请填写所属专辑集数",digits : "所属专辑集数只能是大于0的整数",min : "所属专辑集数只能是大于0的整数"}});
	})
	
	if($("#batchVideo-form .video_div").length == 0){
		addVideo();
	}
});

//新增视频
function addVideo(){
	var length = $("#batchVideo-form .video_div").length;
	var html = '';
	html += '<div id="video_'+(length)+'" class="video_div">'
	html += 	'<div class="form-group">';
	html += 		'<label class="control-label col-sm-1 no-padding-right" for="videoId_'+(length)+'">视频'+(length+1)+'</label>';
	html += 		'<div class="col-sm-10">';
	html += 			'<div class="clearfix">';
	html += 				'<input class="form-control" id="videoId_'+length+'" name="videoId_'+length+'" type="hidden"/>';
	html += 				'<input class="form-control" id="videoTitle_'+length+'" name="videoTitle_'+length+'" type="text" onfocus="webside.wodota.show(\'searchModal\',this)" href="'+sys.rootPath+'/videoCtrl/searchListUI.html" placeholder="视频'+(length+1)+'..."/>';
	html += 			'</div>';
	html += 		'</div>';
	html += 		'<div>';
	html += 			'<span class="help-inline">*';
	html +=					'<button type="button" onclick="delVideo('+length+')" class="btn btn-info btn-sm">';
	html +=						'<i class="fa fa-undo"></i>&nbsp;删除';
	html +=					'</button>';
	html += 			'</span>';
	html += 		'</div>';
	html +=		'</div>';
	html += 	'<div class="form-group">';
	html += 		'<label class="control-label col-sm-1 no-padding-right" for="videoIndex_'+(length)+'">所属专辑集数</label>';
	html += 		'<div class="col-sm-10">';
	html += 			'<div class="clearfix">';
	html += 				'<input class="form-control" id="videoIndex_'+length+'" name="videoIndex_'+length+'" type="text" placeholder="所属专辑集数..."/>';
	html += 			'</div>';
	html += 		'</div>';
	html += 		'<div>';
	html += 			'<span class="help-inline">*</span>';
	html += 		'</div>';
	html += 	'</div>';
	html += '</div>';
	
	$("#batchVideo-form").append(html);
	
	$("#videoId_" + length).rules("add",{required : true,checkSameVideo : true, messages: {required : "请选择视频"}});
	$("#videoIndex_" + length).rules("add",{required : true,digits : true,min : 1,checkSameIndex : true, messages: {required : "请填写所属专辑集数",digits : "所属专辑集数只能是大于0的整数",min : "所属专辑集数只能是大于0的整数"}});
}

//删除视频
function delVideo(ind){
	deleteVideoIdArray.push($("#videoId_"+ind).val());
	$("#video_" + ind).remove();
	
	$("#batchVideo-form .video_div").each(function(index,element) {
		if(index >= ind){
			$(element).attr("id","video_"+index);
			var firstDiv = $(element).children(".form-group:first");
			var lastDiv = $(element).children(".form-group:last");
			$(firstDiv).find("label").text("视频"+(index+1)).attr("for","videoId_"+index);
			$(firstDiv).find("input[name^=videoId]").attr("name","videoId_"+index).attr("id","videoId_"+index).attr("aria-describedby","videoId_"+index+"-error");
			$(firstDiv).find("input[name^=videoTitle]").attr("name","videoTitle_"+index).attr("id","videoTitle_"+index).attr("placeholder","视频"+(index+1)+"...");
			$(lastDiv).find("input[name^=videoIndex]").attr("name","videoIndex_"+index).attr("id","videoIndex_"+index).attr("placeholder","所属专辑集数"+(index+1)+"...");
			$(firstDiv).find("div[id^=videoId_]").attr("id","videoId_"+index+"-error");
			$(lastDiv).find("div[id^=videoIndex_]").attr("id","videoIndex_"+index+"-error");
			$(lastDiv).find("label").attr("for","videoIndex_"+index);
			$(firstDiv).find("button").attr("onclick","delVideo("+index+")");
		}
	})
}
