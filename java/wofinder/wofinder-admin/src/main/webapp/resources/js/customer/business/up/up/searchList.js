var dtGridColumns_search = [
	{
	    id : 'name',
	    title : '名称',
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
	    id : 'introduction',
	    title : '简介',
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
	    id : 'avatar',
	    title : '头像',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null && value != undefined && value != ''){
	        	return '<img width="50" height="50" src="'+value+'" />';
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'popularityIndex',
	    title : '人气指数',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	},
	{
	    id : 'gameId',
	    title : '所属游戏',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(record.game!=null && record.game.name!=null){
	        	return record.game.name;
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
	},{
	    id : 'operation',
	    title : '操作',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var content = '';
	    		content+= '<button type="button" onclick="webside.wodota.selectForSearchModal(\'searchModal\',\''+record.id+'\',\''+record.name+'\')" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;选择</button>';
	    		content+= '&nbsp;&nbsp;';
	    	return content;
	    }
	}
];

//动态设置jqGrid的rowNum
var pageSize_search = $("#pageSize_search").val();
pageSize_search = pageSize_search == 0 || pageSize_search == "" ? sys.pageNum : pageSize_search;

var dtGridOption_search = {
    lang : 'zh-cn',
    ajaxLoad : true,
    checkWidth :'37px',
    extraWidth : '37px',
    loadURL : sys.rootPath + '/upCtrl/searchList.html',
    columns : dtGridColumns_search,
    gridContainer : 'dtGridContainer_search',
    toolbarContainer : 'dtGridToolBarContainer_search',
    tools : '',
    exportFileName : '视频作者信息',
    pageSize : pageSize_search,
    pageSizeLimit : [10, 20, 30]
};

var grid_search = $.fn.dlshouwen.grid.init(dtGridOption_search);
$(function() {
	$('select').select2();
	
    if(null != $("#orderByColumn_search").val() && '' != $("#orderByColumn_search").val())
    {
        grid_search.sortParameter.columnId = $("#orderByColumn_search").val();
        grid_search.sortParameter.sortType = $("#orderByType_search").val();
    }
    grid_search.load();
    $("#btnSearch_search").click(customSearch_search);
    
    //注册回车键事件
    document.onkeypress = function(e){
    var ev = document.all ? window.event : e;
        if(ev.keyCode==13) {
            customSearch_search();
        }
    };
});

/**
 * 自定义查询
 * 这里不传入分页信息，防止删除记录后重新计算的页码比当前页码小而导致计算异常
 */
function customSearch_search() {
    grid_search.parameters = new Object();
    grid_search.parameters['statusValue'] = $("#statusValue_search").val();
    grid_search.parameters['gameId'] = $("#gameId_search").val();
    grid_search.refresh(true);
}
