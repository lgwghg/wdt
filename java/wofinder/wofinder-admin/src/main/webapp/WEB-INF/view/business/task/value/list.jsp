<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/task/value/list.js"></script>
<script>
$("#formModal").on("hide.bs.modal", function() {  
	formClose = true;
	$(this).removeData("bs.modal");
	$("#formModal-content").html("");
});  
</script>

<div class="modal-header">
	<h4 class="modal-title">事件属性值关联列表</h4>
</div>

<div class="modal-body" style="height: 700px;">
	<div class="page-header">
		<shiro:hasPermission name="taskValue:addUI">
			<button type="button" data-toggle="modal" data-target="#formModal" href="${ctx }/taskValueCtrl/addUI.html?taskId=${taskId}" class="btn btn-primary btn-sm">
			  	<i class="fa fa-user-plus"></i>&nbsp;添加
			</button>
		</shiro:hasPermission>
	</div>
	
	<div class="input-group" >
		<label class="search">状态：
			<select id="statusValue_v" style="width: 170px;">
	   			<option value="">不限</option>
	   			<c:forEach items="${statusList }" var="dict">
	   				<option value="${dict.value }">${dict.label }</option>
	   			</c:forEach>
	   		</select>
		</label>
		<span class="input-group-btn">
			<button id="btnSearch_v" class="btn btn-primary btn-sm" type="button"> <i class="fa fa-search"></i> 搜索</button>
		</span>
	</div>
	
	<div class="row" style="margin-top:5px;">
		<div class="col-xs-12 widget-container-col ui-sortable" style="min-height: 127px;">
			<div class="widget-box transparent ui-sortable-handle" style="opacity: 1; z-index: 0;">
				<div class="widget-header">
					<h4 class="widget-title lighter">事件属性值关联列表</h4>
					
					<div class="widget-toolbar no-border">
						<a href="#" data-action="fullscreen" class="orange2"> 
							<i class="ace-icon fa fa-arrows-alt"></i>
						</a> 
						<a href="#" data-action="collapse" class="green"> 
							<i class="ace-icon fa fa-chevron-up"></i>
						</a>
					</div>
				</div>
	
				<div class="widget-body" style="display: block;">
					<div class="widget-main padding-6 no-padding-left no-padding-right">
						<input id="pageNum_v" type="hidden" value="${page.pageNum}">
						<input id="pageSize_v" type="hidden" value="${page.pageSize}">
						<input id="orderByColumn_v" type="hidden" value="${page.orderByColumn}">
						<input id="orderByType_v" type="hidden" value="${page.orderByType}">
						<input id="taskId" type="hidden" value="${taskId }">
						<div id="dtGridContainer_v" class="dlshouwen-grid-container"></div>
						<div id="dtGridToolBarContainer_v" class="dlshouwen-grid-toolbar-container"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal-footer" style="text-align: center;">
    <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal"><i class="fa fa-user-plus"></i>&nbsp;关闭</button>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="formModal" tabindex="-1" role="dialog" aria-labelledby="formModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 1000px;">
        <div class="modal-content" id="formModal-content">
        </div>
    </div>
</div>
