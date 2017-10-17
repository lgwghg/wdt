<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/video/album/form.js"></script>

<div class="page-header">
	<h1>
		<c:if test="${ empty entity }">
		新增视频专辑
		</c:if>
		<c:if test="${ !empty entity }">
		编辑视频专辑
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="videoalbum-form" name="videoalbum-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" id="id" name="id" value="${entity.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="name">合集名称</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="name" type="text" 
			           		value="${ entity.name }" placeholder="合集名称..."/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="author">合集作者</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="author" type="text" 
			           		value="${ entity.author }" placeholder="合集作者..."/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="introduction">合集简介</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="introduction" type="text" 
			           		value="${ entity.introduction }" placeholder="合集简介..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="updateRemarks">更新时间备注</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="updateRemarks" type="text" 
			           		value="${ entity.updateRemarks }" placeholder="更新时间备注..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="cover">封面</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="cover" type="text" 
			           		value="${ entity.cover }" placeholder="封面..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="parentName">父级合集</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" id="parentId" name="parentAlbum.id" type="hidden" value="${ entity.parentAlbum.id }"/>
			         <input class="form-control" id="parentName" type="text" onfocus="this.blur()" onclick="webside.wodota.show('searchModal',this)" href="${ctx }/videoAlbumCtrl/searchListUI.html"
			           		value="${ entity.parentAlbum.name }" placeholder="父级合集名称..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="homeId">第三方ID</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="homeId" type="text" 
			           		value="${ entity.homeId }" placeholder="第三方ID..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="homeUrl">第三方链接</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="homeUrl" type="text" 
			           		value="${ entity.homeUrl }" placeholder="第三方链接..."/>
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
		      <label class="control-label col-sm-1 no-padding-right" for="inde">排序</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="inde" type="text" 
			           		value="${ entity.inde }" placeholder="排序..."/>
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
	<button type="button" onclick="javascript:$('#videoalbum-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button type="button" onclick="webside.common.loadPage('/videoAlbumCtrl/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
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