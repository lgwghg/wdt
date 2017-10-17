<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="${ ctx}/resources/js/select2/select2.min.css" />
<script type="text/javascript" src="${ ctx}/resources/js/select2/select2.min.js"></script>
<script type="text/javascript" src="${ ctx}/resources/js/select2/zh-CN.js"></script>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/user/message/form.js"></script>

<div class="page-header">
	<h1>
		<c:if test="${ empty entity }">
		新增用户消息
		</c:if>
		<c:if test="${ !empty entity }">
		编辑用户消息
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="usermessage-form" name="usermessage-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" id="id" name="id" value="${entity.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="userId">用户ID</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="userId" type="text" 
			           		value="${ entity.userId }" placeholder="用户ID..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="businessType">业务类型</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="businessType" type="text" 
			           		value="${ entity.businessType }" placeholder="业务类型 1:评论..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="businessId">业务ID</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="businessId" type="text" 
			           		value="${ entity.businessId }" placeholder="业务ID..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="content">消息描述</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="content" type="text" 
			           		value="${ entity.content }" placeholder="消息描述..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="state">阅读状态 </label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="state" type="text" 
			           		value="${ entity.state }" placeholder="阅读状态 1：已读，0：未读..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="isDeleted">是否删除</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="isDeleted" type="text" 
			           		value="${ entity.isDeleted }" placeholder="默认0     1：已删除   0：正常..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="createTime">消息创建时间</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="createTime" type="text" 
			           		value="${ entity.createTime }" placeholder="消息创建时间..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="updateTime">更新已读状态时，更新时间</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="updateTime" type="text" 
			           		value="${ entity.updateTime }" placeholder="更新已读状态时，更新时间..."/>
			      </div>
		      </div>
			</div>
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button type="button" onclick="javascript:$('#usermessage-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button type="button" onclick="webside.common.loadPage('/userMessage/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>