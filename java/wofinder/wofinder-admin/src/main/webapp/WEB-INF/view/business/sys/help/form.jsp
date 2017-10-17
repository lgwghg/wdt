<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="${ ctx}/resources/js/select2/select2.min.css" />
<script type="text/javascript" src="${ ctx}/resources/js/select2/select2.min.js"></script>
<script type="text/javascript" src="${ ctx}/resources/js/select2/zh-CN.js"></script>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/sys/help/form.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript">
	$(function()  {
		sysHelp.form.validateForm();
	    var ue = UE.getEditor("content");
	});
</script>
<div class="page-header">
	<h1>
		<c:if test="${ empty entity }">
		新增系统帮助
		</c:if>
		<c:if test="${ !empty entity }">
		编辑系统帮助
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="syshelp-form" name="syshelp-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" id="id" name="id" value="${entity.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="title">标题</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="title" id="title" type="text" 
			           		value="${ entity.title }" placeholder="标题..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="code">编码</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="code" id="code" type="text" 
			           		value="${ entity.code }" placeholder="编码..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="content">通知内容</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	 <script id="content" name="content" type="text/plain" style="width:100%;height:256px;">${ entity.content }</script>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="type">公告类型</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	 <select class="form-control" name="type" id="type" style="width: 100%">
			   			<option value="1" ${entity.status eq 1?"selected":""}>系统帮助</option>
			   			<option value="2" ${entity.status eq 2?"selected":""}>用户反馈</option>
					 </select>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="sequence">序列</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="sequence" id="sequence" type="text" 
			           		value="${ entity.sequence }" placeholder="序列..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="status">状态 </label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	 <select class="form-control" name="status" id="status" style="width: 100%">
		   				<option value="1" ${entity.status eq 1?"selected":""}>有效</option>
		   				<option value="0" ${entity.status eq 0?"selected":""}>无效</option>
					 </select>
			      </div>
		      </div>
			</div>
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button type="button" onclick="javascript:$('#syshelp-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button type="button" onclick="webside.common.loadPage('/helpCtrl/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>