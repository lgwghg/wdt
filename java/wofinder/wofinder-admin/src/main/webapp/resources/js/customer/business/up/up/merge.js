jQuery.validator.addMethod("checkSameUp", function(value, ele, param) {
	var ind = 0;
	var upIdArray=new Array();
	$("input[name^=upId]").each(function(index,element) {
		upIdArray[index] = $(element).val()+","+index;
		if($(ele).attr("name") == $(element).attr("name")){
			ind = index;
		}
	})
	
	var nary = upIdArray.sort();
	
	for(var i = 0;i < nary.length;i++){
		var upId = nary[i].split(",");
		if(upId[0] ==''){
			continue;
		}
		if (ind != upId[1] && upId[0]==value){
			return false;
		}
	}
	return true;
}, "视频作者存在重复的，请修改或者删除！");

var sameListSize = 0;
var gridArray = new Array();
var submit = false;

var up = 
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
                	upId_0 : {
						required : true,
						checkSameUp : true
	                },
	                upId_1 : {
						required : true,
						checkSameUp : true
	                }
                },
                messages : {
                	upId_0 : {
						required : "请选择视频作者"
	                },
	                upId_1 : {
						required : "请选择视频作者"
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
                	var upIds = [];
                	$("input[name^=upId]").each(function(index,element) {
                		upIds.push($(element).val());
                	})
                    var id = $("#upId_0").val();
                    var url = '/upCtrl/merge.html';
                    var jumpUrl = '/upCtrl/editUI.html?id='+id+"&page=1&rows=10&sidx=&sord=";
                    
                    //提交
                    var index;
                    var result;
                    $.ajax({
                        type : "POST",
                        url : sys.rootPath + url,
                        data : {
                        	upIds : upIds,
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
    up.form.validateForm();
    $('select').select2();
});

//新增作者
function addUp(){
	var next = true;
	if(submit){
		layer.confirm('在存在相同站点数据列表的情况下，新增视频作者会清空显示的列表，确定吗？', {
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
		html += '<div class="form-group" id="up_'+(length)+'">';
		html += 	'<label class="control-label col-sm-1 no-padding-right" for="upId_'+length+'">视频作者'+(length+1)+'</label>';
		html += 	'<div class="col-sm-10">';
		html += 		'<div class="clearfix">';
		html += 			'<input class="form-control" id="upId_'+length+'" name="upId_'+length+'" type="hidden"/>';
		html += 			'<input class="form-control" id="upName_'+length+'" name="upName_'+length+'" type="text" onfocus="webside.wodota.show(\'searchModal\',this)" href="'+sys.rootPath+'/upCtrl/searchListUI.html" placeholder="视频作者'+(length+1)+'..."/>';
		html += 		'</div>';
		html += 	'</div>';
		html += 	'<div>';
		html += 		'<span class="help-inline">*';
		html +=				'<button type="button" onclick="delUp('+length+')" class="btn btn-info btn-sm">';
		html +=					'<i class="fa fa-undo"></i>&nbsp;删除';
		html +=				'</button>';
		html += 		'</span>';
		html += 	'</div>';
		html += '</div>';
		$("#merge-form").find(".form-group:last").after(html);
		
		$("#upId_" + length).rules("add",{required : true,checkSameUp : true, messages: {required : "请选择视频作者"}});
	}
}

//删除作者
function delUp(ind){
	$("#up_" + ind).remove();
	
	$("#merge-form .form-group").each(function(index,element) {
		if(index >= ind){
			$(element).attr("id","up_"+index);
			$(element).find("label").text("视频作者"+(index+1)).attr("for","upId_"+index);;
			$(element).find("input[name^=upId]").attr("name","upId_"+index).attr("id","upId_"+index).attr("aria-describedby","upId_"+index+"-error");
			$(element).find("input[name^=upName]").attr("name","upName_"+index).attr("id","upName_"+index).attr("placeholder","视频作者"+(index+1)+"...");
			$(element).find("div[id^=upId_]").attr("id","upId_"+index+"-error");
			$(element).find("button").attr("onclick","delUp("+index+")");
		}
	})
}

//列表列
var gridColumns = [
 	{id:'name', title:'名称', type:'string', columnClass:'text-center'},
 	{id:'homeUrl', title:'个人主页', type:'string', columnClass:'text-center'},
 	{id:'upAvatar', title:'头像', type:'string', columnClass:'text-center',
	 	resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null && value != undefined && value != ''){
	        	return '<img width="50" height="50" src="'+value+'" />';
	        }else{
	        	return '';
	        }
	    }
 	},
 	{id:'upVideoCount', title:'视频数量', type:'string', columnClass:'text-center'},
 	{id:'upFansCount', title:'粉丝数量', type:'string', columnClass:'text-center'},
 	{id:'upFriendCount', title:'关注数量', type:'string', columnClass:'text-center'},
 	{id:'upPlayCount', title:'播放数量', type:'string', columnClass:'text-center'}
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
