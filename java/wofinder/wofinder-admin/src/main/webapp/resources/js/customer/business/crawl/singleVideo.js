var dtGridColumns = [
	{
	    id : 'title',
	    title : '标题',
	    type : 'string',
	    width: "300px",
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null && value.length>20){
	        	return '<span title="'+value+'">'+value.substring(0,20)+'...</span>';
	        }else{
	        	return '<span title="'+value+'">'+value+'</span>';
	        }
	    }
	},
	{
	    id : 'cover',
	    title : '封面',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null && value != undefined && value != ''){
	        	return '<img width="70" height="50" src="'+value+'" />';
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'duration',
	    title : '时长',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null && value.length>10){
	        	return '<span title="'+value+'">'+value.substring(0,10)+'...</span>';
	        }else{
	        	return '<span title="'+value+'">'+value+'</span>';
	        }
	    }
	},
	{
	    id : 'station',
	    title : '所属站点',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value != null){
	        	return '<span class="iconfont '+value.description+'">'+value.label+'</span>';
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'status',
	    title : '状态',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(record.status!=null && record.status.label!=null){
	        	return '<span class="'+record.status.labelClass+'">'+record.status.label+'</span>';
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'operation',
	    title : '操作',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var content = '';
	    	content+= '<button type="button" onclick="syncVideo(\''+record.vid+'\',\''+record.station.value+'\')" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;同步</button>';
	    	content+= '&nbsp;&nbsp;';
	    	return content;
	    }
	}
];

//动态设置jqGrid的rowNum
var pageSize = $("#pageSize").val();
pageSize = pageSize == 0 || pageSize == "" ? sys.pageNum : pageSize;

var dtGridOption = {
    lang : 'zh-cn',
    ajaxLoad : true,
    checkWidth :'37px',
    extraWidth : '37px',
    loadURL : sys.rootPath + '/crawlerCtrl/singleVideo.html',
    columns : dtGridColumns,
    gridContainer : 'dtGridContainer',
    toolbarContainer : 'dtGridToolBarContainer',
    tools : 'refresh',
    exportFileName : '视频信息',
    pageSize : pageSize,
    pageSizeLimit : [10, 20, 30]
};

var grid = $.fn.dlshouwen.grid.init(dtGridOption);
$(function() {
	$('select').select2();
	
    if(null != $("#orderByColumn").val() && '' != $("#orderByColumn").val())
    {
        grid.sortParameter.columnId = $("#orderByColumn").val();
        grid.sortParameter.sortType = $("#orderByType").val();
    }
    grid.parameters = new Object();
    grid.parameters['vid'] = -1;
    grid.load();
    $("#btnSearch").click(customSearch);
    $("#crawlVideo").click(crawlVideo);
    
    //注册回车键事件
    document.onkeypress = function(e){
    var ev = document.all ? window.event : e;
        if(ev.keyCode==13) {
            customSearch();
        }
    };
});

/**
 * 自定义查询
 * 这里不传入分页信息，防止删除记录后重新计算的页码比当前页码小而导致计算异常
 */
function customSearch() {
	var vid = $("#vid").val();
	if(vid == "" || vid == undefined) {
		alert("请输入要查询的视频ID");
		return;
	}
	var videoId = "";
	var stationValue = $("#stationValue").val();
	if(stationValue == "" || stationValue == undefined) {
		videoId = vid;
		vid = "";
	}
    grid.parameters = new Object();
    grid.parameters['title'] = $("#title").val();
    grid.parameters['vid'] = vid;
    grid.parameters['videoId'] = videoId;
    grid.parameters['stationValue'] = stationValue;
    grid.refresh(true);
}

function syncVideo(id, stationId) {
	$.ajax({
		url: sys.rootPath + "/crawl/crawlId",
		data: {"id":id, "stationId":stationId},
		dataType: "json",
		type: "post",
		success: function(backData) {
			if(backData.success) {
				alert("新增成功");
				$('#listModal').modal('hide');
			} else {
				alert(backData.msg);
			}
		}
	});
}

function addVideo() {
	var stationValue = $("#stationValue2").val();
	if(stationValue == "" || stationValue == undefined) {
		alert("请选择要查询的站点");
		return;
	}
	var vid = $("#vid2").val();
	if(vid == "" || vid == undefined) {
		alert("请输入要查询的视频ID");
		return;
	}
	syncVideo(vid, stationValue);
}

function crawlVideo() {
	var stationValue = $("#stationValue").val();
	if(stationValue == "" || stationValue == undefined) {
		alert("请选择要爬取的站点");
		return;
	}
	var text = $("#stationValue").find("option:selected").text();
	if(!confirm("是否确定爬取_"+text+"？")) {
		return;
	}
	$.ajax({
		url: sys.rootPath + "/crawl/crawlStation",
		data: {"stationId":stationValue},
		dataType: "json",
		type: "post",
		success: function(backData) {
			if(backData.success) {
				alert("爬取成功");
			} else {
				alert(backData.msg);
			}
		}
	});
}





