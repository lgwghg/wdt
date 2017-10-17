<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/video/value/form.js"></script>

<div class="modal-header">
	<h4 class="modal-title">
		<c:if test="${ empty entity }">
		新增视频属性值关联
		</c:if>
		<c:if test="${ !empty entity }">
		编辑视频属性值关联
		</c:if>
	</h4>
</div>

<div class="modal-body" >
	<div class="row" style="margin-top:5px;">
		<div class="col-xs-12">
			<form id="videovalue-form" name="videovalue-form" class="form-horizontal" role="form" method="post">
				<c:if test="${ !empty entity }">
					<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
					<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
					<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
					<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
					<input type="hidden" id="id" name="id" value="${entity.id}">
					<input type="hidden" name="video.id" value="${entity.video.id}">
				</c:if>
				<c:if test="${ empty entity }">
					<input type="hidden" name="video.id" value="${videoId}">
				</c:if>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="value">属性值</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <input name="value.name" class="form-control" type="text"
			           				value="${entity.value.name}" placeholder="请输入属性值..."/>
				      </div>
			      </div>
			      <div>
			         <span class="help-inline">*</span>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="status">状态</label>
			      <div class="col-sm-10">
				      <select name="status.value" class="form-control">
							<c:forEach items="${statusList }" var="dict">
			   					<option value="${dict.value }" <c:if test="${dict.value==entity.status.value }">selected="selected"</c:if>>${dict.label }</option>
			   				</c:forEach>
				   	  </select>
			      </div>
			      <div>
			         <span class="help-inline">*</span>
			      </div>
				</div>
			</form>
			
			<div class="hr hr-dotted"></div>
		</div>
	</div>
</div>

<div class="modal-footer" style="text-align: center;">
	<button type="button" onclick="javascript:$('#videovalue-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button type="button" class="btn btn-primary btn-sm" data-dismiss="modal"><i class="fa fa-undo"></i>&nbsp;返回</button>
</div>