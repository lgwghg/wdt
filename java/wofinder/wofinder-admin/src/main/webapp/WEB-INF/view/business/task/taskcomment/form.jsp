<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/task/taskcomment/form.js"></script>
<script>
$("#searchModal").on("hide.bs.modal", function() {  
	$(this).removeData("bs.modal");
	$("#searchModal-content").html("");
});  
</script>
<div class="page-header">
	<h1>
		<c:if test="${ empty entity }">
		新增事件评论
		</c:if>
		<c:if test="${ !empty entity }">
		编辑事件评论
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="taskcomment-form" name="taskcomment-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" id="id" name="id" value="${entity.id}">
				<input type="hidden" name="task.id" value="${entity.task.id}">
			</c:if>
			<c:if test="${ empty entity }">
				<input type="hidden" name="task.id" value="${taskId}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="content">评论内容</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="content" type="text" 
			           		value="${ entity.content }" placeholder="评论内容..."/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="parentComment">父级评论id</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="parentComment.id" type="text" 
			           		value="${ entity.parentComment.id }" placeholder="父级评论id..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="likeNum">点赞数量</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="likeNum" type="text" 
			           		value="<c:if test="${empty entity }">0</c:if><c:if test="${ !empty entity }">${entity.likeNum }</c:if>" placeholder="点赞数量..."/>
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
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button type="button" onclick="javascript:$('#taskcomment-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button type="buttn" class="btn btn-primary btn-sm" data-dismiss="modal"><i class="fa fa-undo"></i>&nbsp;返回</button>
</div>
