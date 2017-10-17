var dtGridColumns_c = [
	{
	    id : 'content',
	    title : '评论内容',
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
	    id : 'parentComment',
	    title : '父级评论',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(record.parentComment!=null && record.parentComment.content!=null){
	    		if(record.parentComment.content.length>10){
	    			return '<span title="'+record.parentComment.content+'">'+record.parentComment.content.substring(0,10)+'...</span>';
	    		}else{
	    			return '<span title="'+record.parentComment.content+'">'+record.parentComment.content+'</span>';
	    		}
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'likeNum',
	    title : '点赞数量',
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
	    		content+= '<button type="button" data-toggle="modal" data-target="#formModal" href="'+editUrl+'" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button>';
	    		content+= '&nbsp;&nbsp;';
	    	}
	    	if(record.del){
	    		content+= '<button type="button" onclick="webside.wodota.delModelByOperation(\'/taskCommentCtrl/delete.html\',customSearch_c,\''+record.id+'\')" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>&nbsp;删除</button>';
	    	}
	    	return content;
	    }
	}
];

//动态设置jqGrid的rowNum
var pageSize_c = $("#pageSize_c").val();
pageSize_c = pageSize_c == 0 || pageSize_c == "" ? sys.pageNum : pageSize_c;

var dtGridOption_c = {
    lang : 'zh-cn',
    ajaxLoad : true,
    checkWidth :'37px',
    extraWidth : '37px',
    loadURL : sys.rootPath + '/taskCommentCtrl/list.html',
    columns : dtGridColumns_c,
    gridContainer : 'dtGridContainer_c',
    toolbarContainer : 'dtGridToolBarContainer_c',
    tools : 'refresh',
    exportFileName : '事件评论信息',
    pageSize : pageSize_c,
    pageSizeLimit : [10, 20, 30]
};

var grid_c =$.fn.dlshouwen.grid.init(dtGridOption_c);
$(function() {
	$('select').select2();
	
    if(null != $("#orderByColumn_c").val() && '' != $("#orderByColumn_c").val())
    {
        grid_c.sortParameter.columnId = $("#orderByColumn_c").val();
        grid_c.sortParameter.sortType = $("#orderByType_c").val();
    }
    grid_c.parameters = new Object();
    grid_c.parameters['taskId'] = $("#taskId").val();
    grid_c.load();
    $("#btnSearch_c").click(customSearch_c);
    
    //注册回车键事件
    document.onkeypress = function(e){
    var ev = document.all ? window.event : e;
        if(ev.keyCode==13) {
            customSearch_c();
        }
    };
});

/**
 * 自定义查询
 * 这里不传入分页信息，防止删除记录后重新计算的页码比当前页码小而导致计算异常
 */
function customSearch_c() {
    grid_c.parameters = new Object();
    grid_c.parameters['content'] = $("#content_c").val();
	grid_c.parameters['statusValue'] = $("#statusValue_c").val();
    grid_c.refresh(true);
}
