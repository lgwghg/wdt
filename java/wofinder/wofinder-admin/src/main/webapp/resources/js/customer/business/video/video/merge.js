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

var sameListSize = 0;
var gridArray = new Array();
var submit = false;

var video = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#merge-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
                	videoId_0 : {
						required : true,
						checkSameVideo : true
	                },
	                videoId_1 : {
						required : true,
						checkSameVideo : true
	                }
                },
                messages : {
                	videoId_0 : {
						required : "请选择视频"
	                },
	                videoId_1 : {
						required : "请选择视频"
	                }
                },
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
                	submit = false;
                	
                	var checkStationIds = [];
                	if(sameListSize > 0){
                		for(var i = 0;i<sameListSize;i++){
                			var rows = gridArray[i].getCheckedRecords();
                    	    if (rows.length < 1) {
                    	    	layer.msg("还存在没选择保留数据的列表，请选择", {
                    	            icon : 0
                    	        });
                    	    	return;
                    	    } else if (rows.length > 1){
                    	    	layer.msg("有选择了多行数据的列表，请修改", {
                    	            icon : 0
                    	        });
                    	    	return;
                    	    } else {
                    	    	checkStationIds.push(rows[0].id);
                    	    }
                		}
                	}
                	var videoIds = [];
                	$("input[name^=videoId]").each(function(index,element) {
                		videoIds.push($(element).val());
                	})
                    var id = $("#videoId_0").val();
                    var url = '/videoCtrl/merge.html';
                    var jumpUrl = '/videoCtrl/editUI.html?id='+id+"&page=1&rows=10&sidx=&sord=";
                    
                    //提交
                    var index;
                    var result;
                    $.ajax({
                        type : "POST",
                        url : sys.rootPath + url,
                        data : {
                        	videoIds : videoIds,
                        	checkStationIds : checkStationIds
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
                            	if(resultdata.code == -1){
                            		layer.msg(resultdata.message, {
                                        icon : 0
                                    });
                            		submit = true;
                            		var list = resultdata.sameList;
                            		sameListSize = list.length;
                            		for(var i = 0;i < list.length;i++){
                            			addListDiv(list[i].name, list[i].list, i);
                            		}
                            	}else{
                            		layer.msg(resultdata.message, {
                                        icon : 5
                                    });
                            	}
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
    video.form.validateForm();
    $('select').select2();
});

//新增视频
function addVideo(){
	var next = true;
	if(submit){
		layer.confirm('在存在相同站点数据列表的情况下，新增视频会清空显示的列表，确定吗？', {
			btn: ['确定','取消'] //按钮
		}, function(){
			$("#stationDiv").html("");
			sameListSize = 0;
			gridArray = new Array();
			layer.closeAll('dialog');
		}, function(){
			next = false;
		});
	}
	if(next){
		var length = $("#merge-form .form-group").length;
		var html = '';
		html += '<div class="form-group" id="video_'+(length)+'">';
		html += 	'<label class="control-label col-sm-1 no-padding-right" for="videoId_'+length+'">视频'+(length+1)+'</label>';
		html += 	'<div class="col-sm-10">';
		html += 		'<div class="clearfix">';
		html += 			'<input class="form-control" id="videoId_'+length+'" name="videoId_'+length+'" type="hidden"/>';
		html += 			'<input class="form-control" id="videoTitle_'+length+'" name="videoTitle_'+length+'" type="text" onfocus="webside.wodota.show(\'searchModal\',this)" href="'+sys.rootPath+'/videoCtrl/searchListUI.html" placeholder="视频'+(length+1)+'..."/>';
		html += 		'</div>';
		html += 	'</div>';
		html += 	'<div>';
		html += 		'<span class="help-inline">*';
		html +=				'<button type="button" onclick="delVideo('+length+')" class="btn btn-info btn-sm">';
		html +=					'<i class="fa fa-undo"></i>&nbsp;删除';
		html +=				'</button>';
		html += 		'</span>';
		html += 	'</div>';
		html += '</div>';
		$("#merge-form").find(".form-group:last").after(html);
		
		$("#videoId_" + length).rules("add",{required : true,checkSameVideo : true, messages: {required : "请选择视频"}});
	}
}

//删除视频
function delVideo(ind){
	$("#video_" + ind).remove();
	
	$("#merge-form .form-group").each(function(index,element) {
		if(index >= ind){
			$(element).attr("id","video_"+index);
			$(element).find("label").text("视频"+(index+1)).attr("for","videoId_"+index);;
			$(element).find("input[name^=videoId]").attr("name","videoId_"+index).attr("id","videoId_"+index).attr("aria-describedby","videoId_"+index+"-error");
			$(element).find("input[name^=videoTitle]").attr("name","videoTitle_"+index).attr("id","videoTitle_"+index).attr("placeholder","视频"+(index+1)+"...");
			$(element).find("div[id^=videoId_]").attr("id","videoId_"+index+"-error");
			$(element).find("button").attr("onclick","delVideo("+index+")");
		}
	})
}

//列表列
var gridColumns = [
	{id:'title', title:'标题', type:'string', columnClass:'text-center',
		resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null && value.length>10){
	        	return '<span title="'+value+'">'+value.substring(0,10)+'...</span>';
	        }else{
	        	return '<span title="'+value+'">'+value+'</span>';
	        }
	    }
	},
 	{id:'vid', title:'视频源id', type:'string', columnClass:'text-center'},
 	{id:'url', title:'视频源url', type:'string', columnClass:'text-center'},
 	{id:'cover', title:'封面', type:'string', columnClass:'text-center',
	 	resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null && value != undefined && value != ''){
	        	return '<img width="60" height="40" src="'+value+'" />';
	        }else{
	        	return '';
	        }
	    }
 	},
 	{id:'duration', title:'时长', type:'string', columnClass:'text-center'},
 	{id:'published', title:'发布时间', type:'string', columnClass:'text-center'},
 	{id:'viewCount', title:'播放量', type:'string', columnClass:'text-center'},
 	{id:'commentCount', title:'评论量', type:'string', columnClass:'text-center'},
 	{id:'downCount', title:'下载量', type:'string', columnClass:'text-center'},
 	{id:'favoriteCount', title:'收藏量', type:'string', columnClass:'text-center'},
 	{id:'up', title:'视频作者', type:'string', columnClass:'text-center',
 		resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(record.up!=null && record.up.name!=null){
	        	return record.up.name;
	        }else{
	        	return '';
	        }
	    }
 	}
];

//新增站点列表
function addListDiv(name,datas,index){
	var html = '';
	html += '<div class="row" style="margin-top:5px;">';
	html += 	'<div class="col-xs-12 widget-container-col ui-sortable">';
	html += 		'<div class="widget-box transparent ui-sortable-handle" style="opacity: 1; z-index: 0;">';
	html += 			'<div class="widget-header">';
	html += 				'<h4 class="widget-title lighter">'+name+'列表</h4>';
	html += 			'</div>';
	html += 			'<div class="widget-body" style="display: block;">';
	html += 				'<div class="widget-main padding-6 no-padding-left no-padding-right">';
	html += 					'<div id="dtGridContainer_'+index+'" class="dlshouwen-grid-container"></div>';
	html += 				'</div>';
	html += 			'</div>';
	html += 		'</div>';
	html += 	'</div>';
	html += '</div>';
	$("#stationDiv").append(html);

	var gridOption = {
	 	lang : 'zh-cn',
	 	ajaxLoad : false,
	 	check : true,
	 	checkWidth :'37px',
	 	exportFileName : name+'列表',
	 	datas : datas,
	 	columns : gridColumns,
	 	gridContainer : 'dtGridContainer_'+index,
	 	tools : '',
	 	pageSize : 10,
	 	pageSizeLimit : [10, 20, 50]
	};
	eval("var grid_" + index + "=" + "$.fn.dlshouwen.grid.init(gridOption);");
	eval("grid_" + index).load();
	gridArray[index] = eval("grid_" + index);
}
