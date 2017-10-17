var dtGridColumns = [{
    id : 'nickName',
    title : '昵称',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'mobile',
    title : '手机号',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'email',
    title : '邮箱',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'photo',
    title : '头像',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
    	if(value!=null && value!=''){
        	return '<img width="65" height="65" src="'+value+'"/>';
        }else{
        	return '';
        }
    }
}, {
    id : 'sign',
    title : '签名',
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
}, {
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
    id : 'locked',
    title : '是否锁定',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
    	if(record.locked!=null && record.locked.label!=null){
        	return '<span class="'+record.locked.labelClass+'">'+record.locked.label+'</span>';
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
}];

//动态设置jqGrid的rowNum
var pageSize = $("#pageSize").val();
pageSize = pageSize == 0 || pageSize == "" ? sys.pageNum : pageSize;

var dtGridOption = {
    lang : 'zh-cn',
    ajaxLoad : true,
    check : true,
    checkWidth :'37px',
    extraWidth : '37px',
    loadURL : sys.rootPath + '/userCtrl/list.html',
    columns : dtGridColumns,
    gridContainer : 'dtGridContainer',
    toolbarContainer : 'dtGridToolBarContainer',
    tools : 'refresh',
    exportFileName : '用户信息',
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
    grid.parameters['nickName'] = $("#userNick").val();
    grid.parameters['beginCreateTime'] = $("#beginCreateTime").val();
    grid.parameters['endCreateTime'] = $("#endCreateTime").val();
    grid.parameters['statusValue'] = $("#statusValue").val();
    grid.parameters['lockedValue'] = $("#lockedValue").val();
    grid.refresh(true);
}

/**
 *重置密码
 */
function resetPWDModel() {
    var rows = grid.getCheckedRecords();
    if (rows.length == 1) {
        var index;
        $.ajax({
            type : "POST",
            url : sys.rootPath + '/userCtrl/resetPassword/',
            data : {
                "id" : rows[0].id,
                "accountName" : rows[0].accountName,
                "userName" : rows[0].userName
            },
            dataType : "json",
            beforeSend : function()
            {
                index = layer.load();
            },
            success : function(resultdata) {
                layer.close(index);
                if (resultdata.success) {
                    layer.msg(resultdata.message, {
                        icon : 1
                    });
                } else {
                    layer.msg(resultdata.message, {
                        icon : 5
                    });
                }
            },
            error : function(data, errorMsg) {
                layer.close(index);
                layer.msg(data.responseText, {title:"",icon: 2});
            }
        });
    } else {
        layer.msg("你没有选择行或选择了多行数据", {
            icon : 0
        });
    }
}

/**
 *锁定
 */
function lockModel(url) {
    var rows = grid.getCheckedRecords();
    if (rows.length == 1) {
    	if (url == '/userCtrl/lock.html' || url == '/userCtrl/unlock.html') {
	        if (rows[0].role.key == 'administrator') {
	            layer.msg('该用户为超级管理员,不能锁定或解锁!', {
	                icon : 0
	            });
	            return false;
	        }
	    }
        var index;
        $.ajax({
            type : "POST",
            url : sys.rootPath + url,
            data : {
                "id" : rows[0].id
            },
            dataType : "json",
            beforeSend : function()
            {
                index = layer.load();
            },
            success : function(resultdata) {
                layer.close(index);
                if (resultdata.success) {
                    layer.msg(resultdata.message, {
                        icon : 1
                    });
                    webside.common.loadPage("/userCtrl/listUI.html");
                } else {
                    layer.msg(resultdata.message, {
                        icon : 5
                    });
                }
            },
            error : function(data, errorMsg) {
                layer.close(index);
                layer.msg(data.responseText, {title:"",icon: 2});
            }
        });
    } else {
        layer.msg("你没有选择行或选择了多行数据", {
            icon : 0
        });
    }
}
