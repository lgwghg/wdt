<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/seoconfig/form.js"></script>

<div class="page-header">
	<h1>
		<c:if test="${ empty entity }">
		新增SEO配置
		</c:if>
		<c:if test="${ !empty entity }">
		编辑SEO配置
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="seoconfig-form" name="seoconfig-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" id="id" name="id" value="${entity.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="keywords">keywords</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="keywords" type="text" 
			           		value="${ entity.keywords }" placeholder="keywords..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="description">description</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="description" type="text" 
			           		value="${ entity.description }" placeholder="description..."/>
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
		      <label class="control-label col-sm-1 no-padding-right" for="type">类别</label>
		      <div class="col-sm-10">
			      <select name="type.value" class="form-control">
						<c:forEach items="${typeList }" var="dict">
		   					<option value="${dict.value }" <c:if test="${dict.value==entity.type.value }">selected="selected"</c:if>>${dict.label }</option>
		   				</c:forEach>
			   	  </select>
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

<div class="center">
	<button type="button" onclick="javascript:$('#seoconfig-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button type="button" onclick="webside.common.loadPage('/seoConfigCtrl/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>