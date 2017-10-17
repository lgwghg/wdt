var dtGridColumns = [
	{
	    id : 'up',
	    title : '人物名称',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(record.up!=null && record.up.name!=null){
	        	return record.up.name;
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'title',
	    title : '标题',
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
	    id : 'viewCount',
	    title : '阅读量',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	},
	{
	    id : 'likeCount',
	    title : '点赞量',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
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
	    id : 'createUser',
	    title : '创建人',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    hideType : 'sm|xs|md|lg',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(record.createUser!=null && record.createUser.nickName!=null){
	        	return record.createUser.nickName;
	        }else{
	        	return '';
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
	    hideType : 'sm|xs|md|lg',
	}, 
	{
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
	    		var editUrl = webside.wodota.editModelForModal('/taskCommentCtrl/editUI.html',record.id);
	    		content+= '<button type="button" onclick="webside.wodota.editModelByOperation(\'/taskCtrl/editUI.html\',\''+record.id+'\')" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button>';
	    		content+= '&nbsp;&nbsp;';
	    	}
	    	if(record.taskComment){
	    		content+= '<button type="button" data-toggle="modal" data-target="#listModal" href="'+sys.rootPath+'/taskCommentCtrl/listUI.html?taskId='+record.id+'" class="btn btn-info btn-sm"><i class="fa fa-comment"></i>&nbsp;评论列表</button>';
	    		content+= '&nbsp;&nbsp;';
	    	}
	    	if(record.taskValue){
	    		content+= '<button type="button" data-toggle="modal" data-target="#listModal" href="'+sys.rootPath+'/taskValueCtrl/listUI.html?taskId='+record.id+'" class="btn btn-purple btn-sm"><i class="fa fa-tag"></i>&nbsp;属性值列表</button>';
	    		content+= '&nbsp;&nbsp;';
		    }
	    	if(record.del){
	    		content+= '<button type="button" onclick="webside.wodota.delModelByOperation(\'/taskCtrl/delete.html\',customSearch,\''+record.id+'\')" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>&nbsp;删除</button>';
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
    checkWidth :'37px',
    extraWidth : '37px',
    loadURL : sys.rootPath + '/taskCtrl/list.html',
    columns : dtGridColumns,
    gridContainer : 'dtGridContainer',
    toolbarContainer : 'dtGridToolBarContainer',
    tools : 'refresh',
    exportFileName : '事件信息',
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
    grid.parameters['upId'] = $("#upId").val();
    grid.parameters['title'] = $("#title").val();
    grid.parameters['statusValue'] = $("#statusValue").val();
    grid.refresh(true);
}
