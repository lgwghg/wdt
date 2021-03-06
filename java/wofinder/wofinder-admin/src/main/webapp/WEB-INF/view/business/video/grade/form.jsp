<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="${ ctx}/resources/js/select2/select2.min.css" />
<script type="text/javascript" src="${ ctx}/resources/js/select2/select2.min.js"></script>
<script type="text/javascript" src="${ ctx}/resources/js/select2/zh-CN.js"></script>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/videograde/form.js"></script>

<div class="page-header">
	<h1>
		<c:if test="${ empty entity }">
		新增视频评分
		</c:if>
		<c:if test="${ !empty entity }">
		编辑视频评分
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="videograde-form" name="videograde-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" id="id" name="id" value="${entity.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="id">评分</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="id" type="text" 
			           		value="${ entity.id }" placeholder="评分..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="userId">评分人</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="userId" type="text" 
			           		value="${ entity.userId }" placeholder="评分人..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="videoId">视频 t_video</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="videoId" type="text" 
			           		value="${ entity.videoId }" placeholder="视频 t_video..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="score">评分</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="score" type="text" 
			           		value="${ entity.score }" placeholder="评分..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="createTime">创建时间</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="createTime" type="text" 
			           		value="${ entity.createTime }" placeholder="创建时间..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="status">状态，1有效，0无效</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="status" type="text" 
			           		value="${ entity.status }" placeholder="状态，1有效，0无效..."/>
			      </div>
		      </div>
			</div>
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button type="button" onclick="javascript:$('#videograde-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button type="button" onclick="webside.common.loadPage('/videoGrade/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>