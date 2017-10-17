<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/task/task/form.js"></script>
<script>
$("#searchModal").on("hide.bs.modal", function() {  
	$(this).removeData("bs.modal");
	$("#searchModal-content").html("");
}); 
</script>

<div class="page-header">
	<h1>
		<c:if test="${ empty entity }">
		新增事件
		</c:if>
		<c:if test="${ !empty entity }">
		编辑事件
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="task-form" name="task-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" id="id" name="id" value="${entity.id}">
			</c:if>
			
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="upId">人物名称</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	<input id="upId" value="${entity.up.id}" name="up.id" type="hidden"/>
					<input id="upName" class="form-control" type="text"
							value="${entity.up.name}" placeholder="人物名称..." onfocus="webside.wodota.show('searchModal',this)" href="${ctx }/upCtrl/searchListUI.html">
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="title">标题</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="title" type="text" 
			           		value="${ entity.title }" placeholder="标题..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="content">内容</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	<script id="content" name="content" type="text/plain" style="width:100%;height:256px;">${ entity.content }</script>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="viewCount">阅读量</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="viewCount" type="text" 
			           		value="<c:if test="${empty entity }">0</c:if><c:if test="${ !empty entity }">${entity.viewCount }</c:if>" placeholder="阅读数量..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="likeCount">点赞量</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="likeCount" type="text" 
			           		value="<c:if test="${empty entity }">0</c:if><c:if test="${ !empty entity }">${entity.likeCount }</c:if>" placeholder="点赞数量..."/>
			      </div>
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
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button type="button" onclick="javascript:$('#task-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button type="button" onclick="webside.common.loadPage('/taskCtrl/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="searchModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 1000px;">
        <div class="modal-content" id="searchModal-content">
        </div>
    </div>
</div>