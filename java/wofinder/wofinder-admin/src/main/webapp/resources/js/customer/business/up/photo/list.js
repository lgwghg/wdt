var dtGridColumns_photo = [
	{
	    id : 'upId',
	    title : '人物id',
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
	    id : 'upSecondId',
	    title : '人物二级信息id',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	return '<span title="'+value+'">'+value+'</span>';
	        }else{
	        	return '<span title="'+value+'">'+value+'</span>';
	        }
	    }
	},
	{
	    id : 'photoName',
	    title : '相册名称',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	return '<span title="'+value+'">'+value+'</span>';
	        }else{
	        	return '<span title="'+value+'"></span>';
	        }
	    }
	},
	{
	    id : 'photo',
	    title : '图片地址',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	return '<img src="' + value + '"  width="30" height="30" />';
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'isMain',
	    title : '是否主图',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	if (value == 1) {
	        		return "主图";
	        	} else if (value == 0) {
	        		return "非主图";
	        	}
	        }
	    	return "";
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
	        		return "有效";
	        	} else if (value == 0) {
	        		return "有效";
	        	}
	        }
	    	return "";
	    }
	},
	{
	    id : 'createId',
	    title : '创建者',
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
	    title : '创建时间',
	    type : 'date',
	    format : 'yyyy-MM-dd hh:mm:ss',
	    otype : 'time_stamp_ms',
	    oformat : 'yyyy-MM-dd hh:mm:ss',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	   
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
	    title : '更新时间',
	    type : 'date',
	    format : 'yyyy-MM-dd hh:mm:ss',
	    otype : 'time_stamp_ms',
	    oformat : 'yyyy-MM-dd hh:mm:ss',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value==null || value=="" ){
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
	    	content+= '<shiro:hasPermission name="upPhoto:edit"><button type="button" data-toggle="modal" data-target="#formModal" href="/upPhoto/editUI.html?id=' + record.id + '" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button></shiro:hasPermission>';
	    	content+= '&nbsp;&nbsp;';
	    	content+= '<shiro:hasPermission name="upPhoto:deleteBatch"><button type="button" onclick="webside.wodota.delModelByOperation(\'/upPhoto/deleteBatch.html\',customSearch_photo,\''+record.id+'\')" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>&nbsp;删除</button></shiro:hasPermission>';
	    	return content;
	    }
	}
];

//动态设置jqGrid的rowNum
var pageSize = $("#pageSize_photo").val();
pageSize = pageSize == 0 || pageSize == "" ? sys.pageNum : pageSize;

var dtGridOption_photo = {
    lang : 'zh-cn',
    ajaxLoad : true,
    check : true,
    checkWidth :'37px',
    extraWidth : '37px',
    loadURL : sys.rootPath + '/upPhoto/list.html',
    columns : dtGridColumns_photo,
    gridContainer : 'dtGridContainer_photo',
    toolbarContainer : 'dtGridToolBarContainer_photo',
    tools : 'refresh|print|export[excel,csv,pdf,txt]',
    exportFileName : '信息',
    pageSize : pageSize,
    pageSizeLimit : [10, 20, 30]
};

var grid_photo = $.fn.dlshouwen.grid.init(dtGridOption_photo);
$(function() {
    if(null != $("#orderByColumn_photo").val() && '' != $("#orderByColumn_photo").val())
    {
    	grid_photo.sortParameter.columnId = $("#orderByColumn_photo").val();
    	grid_photo.sortParameter.sortType = $("#orderByType_photo").val();
    }
    grid_photo.parameters = new Object();
    grid_photo.parameters['upId'] = $("#upId").val();
    grid_photo.load();
    //$("#btnSearch_photo").click(customSearch_photo);
    
    //注册回车键事件
    document.onkeypress = function(e){
    var ev = document.all ? window.event : e;
        if(ev.keyCode==13) {
            customSearch_photo();
        }
    };
    
});

/**
 * 自定义查询
 * 这里不传入分页信息，防止删除记录后重新计算的页码比当前页码小而导致计算异常
 */
function customSearch_photo() {
	grid_photo.parameters = new Object();
	grid_photo.parameters['upId'] = $("#upId").val();
	//grid_photo.parameters['description'] = $("#searchKey_photo").val();
	grid_photo.refresh(true);
}
