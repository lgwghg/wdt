<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/common/upload.jsp"%>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/up/up/form.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.all.min.js"> </script>

<script>
$("#searchModal").on("hide.bs.modal", function() {  
	$(this).removeData("bs.modal");
	$("#searchModal-content").html("");
}); 
$(function(){
    var ue = UE.getEditor("introduction");
});
</script>

<div class="page-header">
	<h1>
		<c:if test="${ empty entity }">
		新增视频作者
		</c:if>
		<c:if test="${ !empty entity }">
		编辑视频作者
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="up-form" name="up-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" name="id" id="id" value="${entity.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="name" type="text" 
			           		value="${ entity.name }" placeholder="名称..."/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="introduction">简介</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <script id="introduction" name="introduction" type="text/plain" style="width:100%;height:256px;">${ entity.introduction }</script>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="avatar">头像</label>
		      <%-- <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="avatar" type="text" 
			           		value="${ entity.avatar }" placeholder="头像..."/>
			      </div>
		      </div> --%>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input name="avatar" id="avatar" type="hidden" class="form-control"
		         		value="${entity.avatar }" placeholder="头像..."/>
			         <div id="avatar_view_div" style="width:120px; height:120px">  
				         <img src="${ctx}/resources/images/nopic.gif" style="width:120px; height:120px" id="avatar_view">  
				    </div>  
					<div id="avatar_upload"></div>
		      	</div>
			    <div>
		        	<span class="help-inline">图片尺寸90*90</span>
		      	</div>
		    </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="popularityIndex">人气指数</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="popularityIndex" type="text" 
			           		value="${ entity.popularityIndex }" placeholder="人气指数..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="isSearch">是否可以搜索</label>
		      <div class="col-sm-10">
		      	  <div class="clearfix">
			         <select name="isSearch.value" class="form-control">
			   			<c:forEach items="${isSearchList }" var="dict">
			   				<option value="${dict.value }" <c:if test="${dict.value==entity.isSearch.value }">selected="selected"</c:if>>${dict.label }</option>
			   			</c:forEach>
			   		</select>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="game">所属游戏</label>
		      <div class="col-sm-10">
		      	  <div class="clearfix">
			         <input class="form-control" id="gameId" name="game.id" type="hidden" value="${ entity.game.id }"/>
			         <input class="form-control" id="gameName" type="text" onfocus="webside.wodota.show('searchModal',this)" href="${ctx }/sysGameCtrl/searchListUI.html"
			           		value="${ entity.game.name }" placeholder="游戏..."/>
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
	<button type="button" onclick="javascript:$('#up-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button type="button" onclick="webside.common.loadPage('/upCtrl/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
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