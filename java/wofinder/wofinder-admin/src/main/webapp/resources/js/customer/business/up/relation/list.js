var dtGridColumns_relation = [
	
	/*{
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
	},*/
	{
	    id : 'relationUpId',
	    title : '相关人物',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var relationUp = record.relationUp;
	    	if (relationUp != null) {
	    		if(relationUp.name!=null && relationUp.name.length>10){
	    			return '<span title="'+relationUp.name+'">'+relationUp.name.substring(0,10)+'...</span>';
	    		}else{
	    			return '<span title="'+relationUp.name+'">'+relationUp.name+'</span>';
	    		}
	    		
	    	}
	    }
	},
	{
	    id : 'relationDesc',
	    title : '关系描述',
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
	/*{
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
	},*/
	{
	    id : 'operation',
	    title : '操作',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var content = '';
	    	//content+= '<shiro:hasPermission name="upRelation:edit"><button type="button" data-toggle="modal" data-target="#formModal" href="/upRelation/editUI.html?id=' + record.id + '"  class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button></shiro:hasPermission>';
	    	//content+= '&nbsp;&nbsp;';
	    	content+= '<shiro:hasPermission name="upRelation:deleteBatch"><button type="button" onclick="webside.wodota.delModelByOperation(\'/upRelation/deleteBatch.html\',customSearch_relation,\''+record.id+'\')" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>&nbsp;删除</button></shiro:hasPermission>';
	    	return content;
	    }
	}
];

//动态设置jqGrid的rowNum
var pageSize = $("#pageSize_relation").val();
pageSize = pageSize == 0 || pageSize == "" ? sys.pageNum : pageSize;

var dtGridOption_relation = {
    lang : 'zh-cn',
    ajaxLoad : true,
    check : true,
    checkWidth :'37px',
    extraWidth : '37px',
    loadURL : sys.rootPath + '/upRelation/list.html',
    columns : dtGridColumns_relation,
    gridContainer : 'dtGridContainer_relation',
    toolbarContainer : 'dtGridToolBarContainer_relation',
    tools : 'refresh|export[excel]',
    exportFileName : '信息',
    pageSize : pageSize,
    pageSizeLimit : [10, 20, 30]
};

var grid_relation = $.fn.dlshouwen.grid.init(dtGridOption_relation);
$(function() {
    if(null != $("#orderByColumn_relation").val() && '' != $("#orderByColumn_relation").val())
    {
        grid_relation.sortParameter.columnId = $("#orderByColumn_relation").val();
        grid_relation.sortParameter.sortType = $("#orderByType_relation").val();
    }
    grid_relation.parameters = new Object();
    grid_relation.parameters['upId'] = $("#upId").val();
    grid_relation.load();
    //$("#btnSearch_relation").click(customSearch_relation);
    
    //注册回车键事件
    document.onkeypress = function(e){
    var ev = document.all ? window.event : e;
        if(ev.keyCode==13) {
            customSearch_relation();
        }
    };
    
});

/**
 * 自定义查询
 * 这里不传入分页信息，防止删除记录后重新计算的页码比当前页码小而导致计算异常
 */
function customSearch_relation() {
    grid_relation.parameters = new Object();
    grid_relation.parameters['upId'] = $("#upId").val();
    //grid_relation.parameters['description'] = $("#searchKey_relation").val();
    grid_relation.refresh(true);
}
