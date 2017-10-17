var dtGridColumns = [
	{
	    id : 'videoTitle',
	    title : '视频标题',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null && value.length>10){
	        	return '<a href="/video/' + record.videoShortId + '" target="_blank"><span title="'+value+'">'+value.substring(0,10)+'...</span></a>';
	        }else{
	        	return '<span title="'+value+'">'+value+'</span>';
	        }
	    }
	},
	{
	    id : 'valueName',
	    title : '标签名称',
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
	    id : 'informReason',
	    title : '举报原因',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	    		if (value == 0) {
	    			return '内容不相关';
	    		} else if (value == 1) {
	    			return '敏感信息';
	    		} else if (value == 2) {
	    			return '恶意攻击';
	    		} else if (value == 3) {
	    			return '剧透内容';
	    		} else if (value == 4) {
	    			return '恶意删除';
	    		}
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
	    	if(value!=null){
	    		if (value == 1) {
	    			return '有效';
	    		} else {
	    			return '无效';
	    		}
	        }else{
	        	return '';
	        }
	    }
	},{
	    id : 'informStatus',
	    title : '举报处理状态',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	    		if (value == 1) {
	    			return '举报未处理';
	    		} else if (value == 2){
	    			return '已处理';
	    		} else {
	    			return '已恢复';
	    		}
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'createName',
	    title : '举报人',
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
	    id : 'createTime',
	    title : '举报时间',
	    type : 'date',
	    format : 'yyyy-MM-dd hh:mm:ss',
	    otype : 'time_stamp_ms',
	    oformat : 'yyyy-MM-dd hh:mm:ss',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	},
	{
	    id : 'updateId',
	    title : '修改者',
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
	    id : 'updateTime',
	    title : '修改时间',
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
	    id : 'operation',
	    title : '操作',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var content = '';
	    	if(record.edit && record.informStatus == 1){
	    		content+= '<button type="button" onclick="dealWithInform(\''+record.id+'\', 1)" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;处理</button>';
	    		content+= '&nbsp;&nbsp;';
	    		content+= '<button type="button" onclick="dealWithInform(\''+record.id+'\', 0)" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;不处理</button>';
	    		content+= '&nbsp;&nbsp;';
	    	}
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
    check : true,
    checkWidth :'37px',
    extraWidth : '37px',
    loadURL : sys.rootPath + '/videoValueInform/list.html',
    columns : dtGridColumns,
    gridContainer : 'dtGridContainer',
    toolbarContainer : 'dtGridToolBarContainer',
    tools : 'refresh|print|export[excel,csv,pdf,txt]',
    exportFileName : '信息',
    pageSize : pageSize,
    pageSizeLimit : [10, 20, 30]
};

var grid = $.fn.dlshouwen.grid.init(dtGridOption);
$(function() {
    if(null != $("#orderByColumn").val() && '' != $("#orderByColumn").val())
    {
        grid.sortParameter.columnId = $("#orderByColumn").val();
        grid.sortParameter.sortType = $("#orderByType").val();
    }
    grid.parameters = new Object();
    grid.parameters['informReason'] = $("#informReason").val();
    grid.parameters['informStatus'] = $("#informStatus").val();
    grid.load();
    $("#btnSearch").click(customSearch);
    
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
    grid.parameters = new Object();
    grid.parameters['informReason'] = $("#informReason").val();
    grid.parameters['informStatus'] = $("#informStatus").val();
    grid.refresh(true);
}


function dealWithInform(id, isDeal) {
	var inform = "操作";
	if (isDeal == 1) {
		inform = "处理";
	} else {
		inform = "不处理";
	}
	layer.confirm('确认' + inform + '？', {
	  btn: ['确认', '取消'] //可以无限个按钮
	}, function(index, layero){
		layer.close(index);
		$.ajax({
			type : "POST",
			url : "/videoValueInform/edit.html",
			data : {
				id : id,
				isDeal : isDeal
			},
			success : function(result) {
				if (result.success) {
					alert(result.message);
					grid.refresh(true);
				}
			}
		});
	}, function(index){
		layer.close(index);
	});
	
}