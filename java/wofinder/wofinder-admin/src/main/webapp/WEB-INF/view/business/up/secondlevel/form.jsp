<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/business/up/secondlevel/form.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript">
	$(function() 
	{
	    upSecondLevel.form.validateForm();
	    var ue = UE.getEditor("content");
	});
</script>

<div class="modal-header">
	<h4 class="modal-title">
		<c:if test="${ empty entity }">
		新增
		</c:if>
		<c:if test="${ !empty entity }">
		编辑
		</c:if>
	</h4>
</div>

<div class="modal-body">
	<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="upsecondlevel-form" name="upsecondlevel-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity && !empty entity.id}">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" name="id" id="id" value="${entity.id}">
				<input type="hidden" name="upId" id="upId" value="${entity.upId}">
			</c:if>
			<c:if test="${ empty entity }">
				<input type="hidden" name="upId" id="upId" value="${upId}">
			</c:if>
			<%-- <div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="parentId">父</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="parentId" id="parentId" type="text" 
			           		value="${ entity.parentId }" placeholder="父id..."/>
			      </div>
		      </div>
			</div> --%>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="titleType">标题</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <select class="form-control" name="titleType" id="titleType" type="text"  value="${ entity.titleType }">
			         	<c:if test="${not empty dictList }">		
				         	<c:forEach items="${dictList }" var="dict">
				         		<option value="${dict.value }" <c:if test="${not empty entity.titleType && entity.titleType == dict.value }">selected=true</c:if>>${dict.label }</option>
				         	</c:forEach>
				         </c:if>
			         </select>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="content">内容</label>
		      <div class="col-sm-10">
			      <%-- <div class="clearfix">
			         <input class="form-control" name="content" id="content" type="text" 
			           		value="${ entity.content }" placeholder="内容..."/>
			         <textarea class="form-control" name="content" id="content" type="text" rows="10">${ entity.content }</textarea>
			      </div> --%>
			      <div class="clearfix">
			      	<script id="content" name="content" type="text/plain" style="width:100%;height:256px;">${ entity.content }</script>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="sort">排序</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="sort" id="sort" type="text" 
			           		value="${ entity.sort }" placeholder="排序..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="isMain">是否有效</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <select class="form-control" name="status" id="status" type="text" value="${ entity.status }">
			         	<option value="1" <c:if test="${not empty entity.status && entity.status == 1}">selected=true</c:if>>有效</option>
			         	<option value="0" <c:if test="${not empty entity.status && entity.status == 0}">selected=true</c:if>>无效</option>
			         </select>
			      </div>
		      </div>
			</div>
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
	</div>
</div>

<div class="modal-footer" style="text-align: center;">
	<button id="btnAdd" type="button" onclick="javascript:$('#upsecondlevel-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button id="btn" type="button" data-dismiss="modal" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>