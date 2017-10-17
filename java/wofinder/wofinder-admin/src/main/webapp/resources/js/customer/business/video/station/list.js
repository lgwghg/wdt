var dtGridColumns_s = [
	{
	    id : 'vid',
	    title : '视频源id',
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
	    id : 'url',
	    title : '视频源url',
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
	    id : 'flashUrl',
	    title : '视频播放url',
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
	        	return '<img width="60" height="40" src="'+value+'" />';
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
	    id : 'category',
	    title : '类别',
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
	    id : 'published',
	    title : '发布时间',
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
	    id : 'viewCount',
	    title : '播放量',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	},
	{
	    id : 'commentCount',
	    title : '评论量',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	},
	{
	    id : 'downCount',
	    title : '下载量',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	},
	{
	    id : 'favoriteCount',
	    title : '收藏量',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
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
	    id : 'station',
	    title : '所属站点',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(record.station!=null && record.station.label!=null){
	        	return '<span class="'+record.station.labelClass+'">'+record.station.label+'</span>';
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
	    hideType : 'sm|xs|md|lg',
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
	    		var editUrl = webside.wodota.editModelForModal('/videoStationCtrl/editUI.html',record.id);
	    		content+= '<button type="button" data-toggle="modal" data-target="#formModal" href="'+editUrl+'" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button>';
	    		content+= '&nbsp;&nbsp;';
	    	}
	    	if(record.del){
	    		content+= '<button type="button" onclick="webside.wodota.delModelByOperation(\'/videoStationCtrl/delete.html\',customSearch_s,\''+record.id+'\')" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>&nbsp;删除</button>';
	    	}
	    	return content;
	    }
	}
];

//动态设置jqGrid的rowNum
var pageSize_s = $("#pageSize_s").val();
pageSize_s = pageSize_s == 0 || pageSize_s == "" ? sys.pageNum : pageSize_s;

var dtGridOption_s = {
  lang : 'zh-cn',
  ajaxLoad : true,
  checkWidth :'37px',
  extraWidth : '37px',
  loadURL : sys.rootPath + '/videoStationCtrl/list.html',
  columns : dtGridColumns_s,
  gridContainer : 'dtGridContainer_s',
  toolbarContainer : 'dtGridToolBarContainer_s',
  tools : 'refresh',
  exportFileName : '视频站点信息',
  pageSize : pageSize_s,
  pageSizeLimit : [10, 20, 30]
};

var grid_s = $.fn.dlshouwen.grid.init(dtGridOption_s);
$(function() {
	$('select').select2();
	
  if(null != $("#orderByColumn_s").val() && '' != $("#orderByColumn_s").val())
  {
      grid_s.sortParameter.columnId = $("#orderByColumn_s").val();
      grid_s.sortParameter.sortType = $("#orderByType_s").val();
  }
  grid_s.parameters = new Object();
  grid_s.parameters['videoId'] = $("#videoId").val();
  grid_s.load();
  $("#btnSearch_s").click(customSearch_s);
  
  //注册回车键事件
  document.onkeypress = function(e){
  var ev = document.all ? window.event : e;
      if(ev.keyCode==13) {
          customSearch_s();
      }
  };
});

/**
* 自定义查询
* 这里不传入分页信息，防止删除记录后重新计算的页码比当前页码小而导致计算异常
*/
function customSearch_s() {
  grid_s.parameters = new Object();
  grid_s.parameters['videoId'] = $("#videoId").val();
  grid_s.parameters['statusValue'] = $("#statusValue_s").val();
  grid_s.parameters['stationValue'] = $("#stationValue_s").val();
  grid_s.refresh(true);
}
