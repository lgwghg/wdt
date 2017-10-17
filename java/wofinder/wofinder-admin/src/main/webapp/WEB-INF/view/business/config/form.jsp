<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/business/config/form.js"></script>

<div class="page-header">
	<h1>
		<c:if test="${ empty entity }">
		新增配置
		</c:if>
		<c:if test="${ !empty entity }">
		编辑配置
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="sysconfig-form" name="sysconfig-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" name="id" id="id" value="${entity.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="configGroup">配置组</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="configGroup" id="configGroup" type="text" 
			           		value="${ entity.configGroup }" placeholder="配置组..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="configKey">配置键</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="configKey" id="configKey" type="text" 
			           		value="${ entity.configKey }" placeholder="配置键..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="configValue">配置值</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <textarea class="form-control" name="configValue" id="configValue" placeholder="配置值..." height="200">${ entity.configValue }</textarea>
			      </div>
		      </div>
			</div>
			
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button id="btnAdd" type="button" onclick="javascript:$('#sysconfig-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button id="btn" type="button" onclick="webside.common.loadPage('/configCtrl/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>