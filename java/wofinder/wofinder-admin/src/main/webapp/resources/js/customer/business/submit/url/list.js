var dtGridColumns_u = [
	{
	    id : 'url',
	    title : 'url',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	}
];

//动态设置jqGrid的rowNum
var pageSize_u = $("#pageSize_u").val();
pageSize_u = pageSize_u == 0 || pageSize_u == "" ? sys.pageNum : pageSize_u;

var dtGridOption_u = {
    lang : 'zh-cn',
    ajaxLoad : true,
    checkWidth :'37px',
    extraWidth : '37px',
    loadURL : sys.rootPath + '/submitUrlCtrl/list.html',
    columns : dtGridColumns_u,
    gridContainer : 'dtGridContainer_u',
    toolbarContainer : 'dtGridToolBarContainer_u',
    tools : 'refresh',
    exportFileName : '提交搜索关键字url信息',
    pageSize : pageSize_u,
    pageSizeLimit : [10, 20, 30]
};

var grid_u = $.fn.dlshouwen.grid.init(dtGridOption_u);
$(function() {
	$('select').select2();
	
    if(null != $("#orderByColumn_u").val() && '' != $("#orderByColumn_u").val())
    {
        grid_u.sortParameter.columnId = $("#orderByColumn_u").val();
        grid_u.sortParameter.sortType = $("#orderByType_u").val();
    }
    grid_u.load();
    $("#btnSearch_u").click(customSearch_u);
    
    //注册回车键事件
    document.onkeypress = function(e){
    var ev = document.all ? window.event : e;
        if(ev.keyCode==13) {
            customSearch_u();
        }
    };
});

/**
 * 自定义查询
 * 这里不传入分页信息，防止删除记录后重新计算的页码比当前页码小而导致计算异常
 */
function customSearch_u() {
    grid_u.parameters = new Object();
    grid_u.refresh(true);
}
