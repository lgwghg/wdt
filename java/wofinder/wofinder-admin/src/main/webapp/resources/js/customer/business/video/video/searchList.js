var dtGridColumns_search = [
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
	    id : 'introduction',
	    title : '简介',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    hideType : 'sm|xs|md|lg',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null && value.length>10){
	        	return '<span title="'+value+'">'+value.substring(0,10)+'...</span>';
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
	    id : 'up',
	    title : '视频作者',
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
	    id : 'score',
	    title : '评分',
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
	    id : 'game',
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
	    id : 'album',
	    title : '专辑名称',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    hideType : 'sm|xs|md|lg',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(record.album!=null && record.album.name!=null){
	    		if(record.album.name.length>10){
		        	return '<span title="'+record.album.name+'">'+record.album.name.substring(0,10)+'...</span>';
		        }else{
		        	return '<span title="'+record.album.name+'">'+record.album.name+'</span>';
		        }
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'albumIndex',
	    title : '当前集数',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    hideType : 'sm|xs|md|lg',
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
	    	content+= '<button type="button" onclick="webside.wodota.selectForSearchModal(\'searchModal\',\''+record.id+'\',\''+record.title+'\')" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;选择</button>';
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
    loadURL : sys.rootPath + '/videoCtrl/searchList.html',
    columns : dtGridColumns_search,
    gridContainer : 'dtGridContainer_search',
    toolbarContainer : 'dtGridToolBarContainer_search',
    tools : '',
    exportFileName : '视频信息',
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
    grid_search.parameters = new Object();
    grid_search.parameters['noSearch'] = "1";
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
    grid_search.parameters['title'] = trim($("#title_search").val());
    grid_search.parameters['statusValue'] = $("#statusValue_search").val();
    grid_search.parameters['gameId'] = $("#gameId_search").val();
    grid_search.refresh(true);
}

//去左右空格
function trim(s){
	if(s == "" || s == undefined) {
		return "";
	}
  return s.replace(/(^\s*)|(\s*$)/g, "");
}