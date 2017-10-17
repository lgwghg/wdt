<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${ ctx}/resources/js/customer/dict/form.js"></script>

<div class="page-header">
	<h1>
		<c:if test="${ empty entity }">
		新增字典
		</c:if>
		<c:if test="${ !empty entity }">
		编辑字典
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="dictForm" name="dictForm" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" id="id" name="id" value="${entity.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="type">字典类型</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input <c:if test="${!empty entity}">disabled="disabled"</c:if> class="form-control" name="type" type="text" 
			           		value="${ entity.type }" placeholder="字典类型..."/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="label">标签名</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="label" type="text" 
			           		value="${ entity.label }" placeholder="标签名..."/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="value">数据值</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="value" type="text" 
			           		value="${ entity.value }" placeholder="数据值..."/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="sort">排序</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="sort" type="text" 
			           		value="${ entity.sort }" placeholder="排序..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="labelClass">文字列表样式</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="labelClass" type="text" 
			           		value="<c:if test="${!empty entity }">${entity.labelClass }</c:if><c:if test="${empty entity }">label label-sm label-success arrowed arrowed-righ</c:if>" placeholder="文字列表样式..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="description">描述</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="description" type="text" 
			           		value="${ entity.description }" placeholder="描述..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="status">状态</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <select name="status.value" class="form-control">
			   			<c:forEach items="${statusList }" var="dict">
			   				<option value="${dict.value }" <c:if test="${dict.value==entity.status.value }">selected="selected"</c:if>>${dict.label }</option>
			   			</c:forEach>
			   		</select>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button type="button" onclick="javascript:$('#dictForm').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button type="button" onclick="webside.common.loadPage('/dictCtrl/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>