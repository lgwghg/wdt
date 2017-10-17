var dtGridColumns = [
	{
	    id : 'name',
	    title : '名称',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null && value.length>10){
	        	return '<span title="'+value+'"><a href="/p/' + record.id + '" target="_blank">'+value.substring(0,10)+'...</a></span>';
	        }else{
	        	return '<span title="'+value+'"><a href="/p/' + record.id + '" target="_blank">'+value+'</a></span>';
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
	    headerClass : 'dlshouwen-grid-header',
	    hideType : 'sm|xs|md|lg'
	},
	{
	    id : 'isSearch',
	    title : '是否可以搜索',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(record.isSearch!=null && record.isSearch.label!=null){
	        	return '<span class="'+record.isSearch.labelClass+'">'+record.isSearch.label+'</span>';
	        }else{
	        	return '';
	        }
	    }
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
	}, {
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
	}, {
	    id : 'createTime',
	    title : '创建时间',
	    type : 'date',
	    format : 'yyyy-MM-dd hh:mm:ss',
	    otype : 'time_stamp_ms',
	    oformat : 'yyyy-MM-dd hh:mm:ss',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    hideType : 'sm|xs|md|lg'
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
	    		content+= '<button type="button" onclick="webside.wodota.editModelByOperation(\'/upCtrl/editUI.html\',\''+record.id+'\')" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button>';
	    		content+= '&nbsp;&nbsp;';
	    	}
	    	if(record.del){
	    		content+= '<button type="button" onclick="webside.wodota.delModelByOperation(\'/upCtrl/delete.html\',customSearch,\''+record.id+'\')" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>&nbsp;删除</button>';
	    		content+= '&nbsp;&nbsp;';
	    	}
	    	if(record.upName){
	    		content+= '<button type="button" data-toggle="modal" data-target="#listModal" href="'+sys.rootPath+'/upNameCtrl/listUI.html?upId='+record.id+'" class="btn btn-info btn-sm"><i class="fa fa-group"></i>&nbsp;名称列表</button>';
	    		content+= '&nbsp;&nbsp;';
	    	}
	    	if(record.upStation){
	    		content+= '<button type="button" data-toggle="modal" data-target="#listModal" href="'+sys.rootPath+'/upStationCtrl/listUI.html?upId='+record.id+'" class="btn btn-warning btn-sm"><i class="fa fa-th-large"></i>&nbsp;站点列表</button>';
	    		content+= '&nbsp;&nbsp;';
		    }
	    	if(record.upValue){
	    		content+= '<button type="button" data-toggle="modal" data-target="#listModal" href="'+sys.rootPath+'/upValueCtrl/listUI.html?upId='+record.id+'" class="btn btn-purple btn-sm"><i class="fa fa-tag"></i>&nbsp;属性值列表</button>';
	    		content+= '&nbsp;&nbsp;';
		    }
	    	if(record.upSecondLevel){
	    		content+= '<button type="button" data-toggle="modal" data-target="#listModal" href="'+sys.rootPath+'/upSecondLevel/listUI.html?upId='+record.id+'" class="btn btn-pink btn-sm"><i class="fa fa-list"></i>&nbsp;人物二级信息列表</button>';
	    		content+= '&nbsp;&nbsp;';
		    }
	    	if(record.upRelation){
	    		content+= '<button type="button" data-toggle="modal" data-target="#listModal" href="'+sys.rootPath+'/upRelation/listUI.html?upId='+record.id+'" class="btn btn-yellow btn-sm"><i class="fa fa-group"></i>&nbsp;相关人物列表</button>';
	    		content+= '&nbsp;&nbsp;';
		    }
	    	if(record.upPhoto){
	    		content+= '<button type="button" data-toggle="modal" data-target="#listModal" href="'+sys.rootPath+'/upPhoto/listUI.html?upId='+record.id+'" class="btn btn-light btn-sm"><i class="fa fa-camera"></i>&nbsp;相册图片列表</button>';
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
    checkWidth :'37px',
    extraWidth : '37px',
    loadURL : sys.rootPath + '/upCtrl/list.html',
    columns : dtGridColumns,
    gridContainer : 'dtGridContainer',
    toolbarContainer : 'dtGridToolBarContainer',
    tools : 'refresh',
    exportFileName : '视频作者信息',
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
    grid.parameters['statusValue'] = $("#statusValue").val();
    grid.parameters['gameId'] = $("#gameId").val();
    grid.parameters['isSearchValue'] = $("#isSearchValue").val();
    grid.refresh(true);
}
