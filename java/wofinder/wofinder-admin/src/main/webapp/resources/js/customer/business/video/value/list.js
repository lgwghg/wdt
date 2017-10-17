var dtGridColumns_v = [
	{
	    id : 'value',
	    title : '属性值',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(record.value!=null && record.value.name!=null){
	        	return record.value.name;
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
	}, {
	    id : 'createUser',
	    title : '创建人',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(record.createUser!=null && record.createUser.nickName!=null){
	        	return record.createUser.nickName;
	        }else{
	        	return '';
	        }
	    }
	}, {
	    id : 'createTime',
	    title : '创建时间',
	    type : 'date',
	    format : 'yyyy-MM-dd hh:mm:ss',
	    otype : 'time_stamp_ms',
	    oformat : 'yyyy-MM-dd hh:mm:ss',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	}, {
	    id : 'updateUser',
	    title : '修改人',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    hideType : 'sm|xs|md|lg',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(record.updateUser!=null && record.updateUser.nickName!=null){
	        	return record.updateUser.nickName;
	        }else{
	        	return '';
	        }
	    }
	}, {
	    id : 'updateTime',
	    title : '更新时间',
	    type : 'date',
	    format : 'yyyy-MM-dd hh:mm:ss',
	    otype : 'time_stamp_ms',
	    oformat : 'yyyy-MM-dd hh:mm:ss',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    hideType : 'sm|xs|md|lg',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	        if (value == null || value == '') {
	            return '';
	        } else {
	            return getSmpFormatDateByLong(parseInt(value),true);
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
	    	if(record.edit){
	    		var editUrl = webside.wodota.editModelForModal('/videoValueCtrl/editUI.html',record.id);
	    		content+= '<button type="button" data-toggle="modal" data-target="#formModal" href="'+editUrl+'" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button>';
	    		content+= '&nbsp;&nbsp;';
	    	}
	    	if(record.del){
	    		content+= '<button type="button" onclick="webside.wodota.delModelByOperation(\'/videoValueCtrl/delete.html\',customSearch_v,\''+record.id+'\')" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>&nbsp;删除</button>';
	    	}
	    	return content;
	    }
	}
];

//动态设置jqGrid的rowNum
var pageSize_v = $("#pageSize_v").val();
pageSize_v = pageSize_v == 0 || pageSize_v == "" ? sys.pageNum : pageSize_v;

var dtGridOption_v = {
	    lang : 'zh-cn',
	    ajaxLoad : true,
	    checkWidth :'37px',
	    extraWidth : '37px',
	    loadURL : sys.rootPath + '/videoValueCtrl/list.html',
	    columns : dtGridColumns_v,
	    gridContainer : 'dtGridContainer_v',
	    toolbarContainer : 'dtGridToolBarContainer_v',
	    tools : 'refresh',
	    exportFileName : '视频属性值关联信息',
	    pageSize : pageSize_v,
	    pageSizeLimit : [10, 20, 30]
	};

var grid_v = $.fn.dlshouwen.grid.init(dtGridOption_v);
$(function() {
	$('select').select2();
	
    if(null != $("#orderByColumn_v").val() && '' != $("#orderByColumn_v").val())
    {
        grid_v.sortParameter.columnId = $("#orderByColumn_v").val();
        grid_v.sortParameter.sortType = $("#orderByType_v").val();
    }
    grid_v.parameters = new Object();
    grid_v.parameters['videoId'] = $("#videoId").val();
    grid_v.load();
    $("#btnSearch_v").click(customSearch_v);
    
    //注册回车键事件
    document.onkeypress = function(e){
    var ev = document.all ? window.event : e;
        if(ev.keyCode==13) {
            customSearch_v();
        }
    };
});

/**
 * 自定义查询
 * 这里不传入分页信息，防止删除记录后重新计算的页码比当前页码小而导致计算异常
 */
function customSearch_v() {
    grid_v.parameters = new Object();
    grid_v.parameters['videoId'] = $("#videoId").val();
    grid_v.parameters['statusValue'] = $("#statusValue_v").val();
    grid_v.parameters['valueId'] = $("#valueId_v").val();
    grid_v.refresh(true);
}
