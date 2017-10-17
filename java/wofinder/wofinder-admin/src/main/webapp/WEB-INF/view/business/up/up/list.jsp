<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/up/up/list.js"></script>
<script>
var formClose = false;
$("#listModal").on("hidden.bs.modal", function() {  
	if(!formClose){
		$(this).removeData("bs.modal");
		$("#listModal-content").html("");
	}else{
		formClose = false;
	}
});  

$("#searchModal").on("hide.bs.modal", function() {  
	$(this).removeData("bs.modal");
	$("#searchModal-content").html("");
});  
</script>
<div class="page-header">
	<shiro:hasPermission name="up:addUI">
		<button type="button" onclick="webside.common.addModel('/upCtrl/addUI.html')" class="btn btn-primary btn-sm">
		  	<i class="fa fa-user-plus"></i>&nbsp;添加
		</button>
	</shiro:hasPermission>
</div>

<div class="input-group" >
	 <label class="search">状态：
		<select id="statusValue" style="width: 170px;">
   			<option value="">不限</option>
   			<c:forEach items="${statusList }" var="dict">
   				<option value="${dict.value }">${dict.label }</option>
   			</c:forEach>
   		</select>
	</label>
	<label class="search">游戏：
		<input id="gameId" type="hidden"/>
		<input id="gameName" type="text" onfocus="webside.wodota.show('searchModal',this)" href="${ctx }/sysGameCtrl/searchListUI.html"
			placeholder="游戏名称..."/>
	</label>
	 <label class="search">是否可以搜索：
		<select id="isSearchValue" style="width: 170px;">
   			<option value="">不限</option>
   			<c:forEach items="${isSearchList }" var="dict">
   				<option value="${dict.value }">${dict.label }</option>
   			</c:forEach>
   		</select>
	</label>
     <span class="input-group-btn">
         <button id="btnSearch" class="btn btn-primary btn-sm" type="button"> <i class="fa fa-search"></i> 搜索</button>
     </span>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12 widget-container-col ui-sortable" style="min-height: 127px;">
		<div class="widget-box transparent ui-sortable-handle" style="opacity: 1; z-index: 0;">
			<div class="widget-header">
				<h4 class="widget-title lighter">视频作者列表</h4>
				
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
					<input id="pageNum" type="hidden" value="${page.pageNum}">
					<input id="pageSize" type="hidden" value="${page.pageSize}">
					<input id="orderByColumn" type="hidden" value="${page.orderByColumn}">
					<input id="orderByType" type="hidden" value="${page.orderByType}">
					<div id="dtGridContainer" class="dlshouwen-grid-container"></div>
					<div id="dtGridToolBarContainer" class="dlshouwen-grid-toolbar-container"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="listModal" tabindex="-1" role="dialog" aria-labelledby="listModalLabel" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog" style="width: 100%;">
        <div class="modal-content" id="listModal-content"></div>
    </div>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="searchModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 1000px;">
        <div class="modal-content" id="searchModal-content">
        </div>
    </div>
</div>