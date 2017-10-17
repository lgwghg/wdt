<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/submit/url/list.js"></script>

<div class="modal-header">
	<h4 class="modal-title">提交搜索关键字url列表</h4>
</div>

<div class="modal-body" style="height: 700px;">
	<div class="row" style="margin-top:5px;">
		<div class="col-xs-12 widget-container-col ui-sortable" style="min-height: 127px;">
			<div class="widget-box transparent ui-sortable-handle" style="opacity: 1; z-index: 0;">
				<div class="widget-header">
					<h4 class="widget-title lighter">提交搜索关键字url列表</h4>
					
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
						<input id="pageNum_u" type="hidden" value="${page.pageNum}">
						<input id="pageSize_u" type="hidden" value="${page.pageSize}">
						<input id="orderByColumn_u" type="hidden" value="${page.orderByColumn}">
						<input id="orderByType_u" type="hidden" value="${page.orderByType}">
						<div id="dtGridContainer_u" class="dlshouwen-grid-container"></div>
						<div id="dtGridToolBarContainer_u" class="dlshouwen-grid-toolbar-container"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal-footer" style="text-align: center;">
    <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal"><i class="fa fa-user-plus"></i>&nbsp;关闭</button>
</div>
