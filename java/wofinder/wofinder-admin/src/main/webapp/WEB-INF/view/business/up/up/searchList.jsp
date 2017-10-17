<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/up/up/searchList.js"></script>

<div class="modal-header">
	<h4 class="modal-title">视频作者列表</h4>
</div>

<div class="modal-body" style="height: 600px;">
	<div class="input-group" >
		<label class="search">游戏：
			<select id="gameId_search" style="width: 170px;">
	   			<option value="">不限</option>
	   			<c:forEach items="${gameList }" var="game">
	   				<option value="${game.id }">${game.name }</option>
	   			</c:forEach>
	   		</select>
		</label>
		<label class="search">状态：
			<select id="statusValue_search" style="width: 170px;">
	   			<option value="">不限</option>
	   			<c:forEach items="${statusList }" var="dict">
	   				<option value="${dict.value }">${dict.label }</option>
	   			</c:forEach>
	   		</select>
		</label>
	     <span class="input-group-btn">
	         <button id="btnSearch_search" class="btn btn-primary btn-sm" type="button"> <i class="fa fa-search"></i> 搜索</button>
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
						<input id="pageNum_search" type="hidden" value="${page.pageNum}">
						<input id="pageSize_search" type="hidden" value="${page.pageSize}">
						<input id="orderByColumn_search" type="hidden" value="${page.orderByColumn}">
						<input id="orderByType_search" type="hidden" value="${page.orderByType}">
						<input id="hidId" type="hidden" value="${hidId}">
						<input id="showId" type="hidden" value="${showId}">
						<div id="dtGridContainer_search" class="dlshouwen-grid-container"></div>
						<div id="dtGridToolBarContainer_search" class="dlshouwen-grid-toolbar-container"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal-footer" style="text-align: center;">
	<button type="button" class="btn btn-warning btn-sm" onclick="webside.wodota.clearForSearchModal('searchModal')"><i class="fa fa-reply"></i>&nbsp;置空</button>
	<button type="button" class="btn btn-primary btn-sm" data-dismiss="modal"><i class="fa fa-undo"></i>&nbsp;返回</button>
</div>