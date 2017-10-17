<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/video/video/form.js"></script>
<script>
$("#searchModal").on("hide.bs.modal", function() {  
	$(this).removeData("bs.modal");
	$("#searchModal-content").html("");
});  
</script>

<div class="page-header">
	<h1>
		<c:if test="${ empty entity }">
		新增视频
		</c:if>
		<c:if test="${ !empty entity }">
		编辑视频
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="video-form" name="video-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" id="id" name="id" value="${entity.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="title">视频标题</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="title" type="text" 
			           		value="${ entity.title }" placeholder="视频标题..."/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="cover">视频封面</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="cover" type="text" 
			           		value="${ entity.cover }" placeholder="视频封面..."/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="duration">视频时长</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="duration" type="text" 
			           		value="${ entity.duration }" placeholder="视频时长，单位秒..."/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
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
				<label class="control-label col-sm-1 no-padding-right" for="game">所属游戏</label>
				<div class="col-sm-10">
					<input class="form-control" id="gameId" name="game.id" type="hidden" value="${ entity.game.id }"/>
			         <input class="form-control" id="gameName" type="text" onfocus="this.blur()" onclick="webside.wodota.show('searchModal',this)" href="${ctx }/sysGameCtrl/searchListUI.html"
			           		value="${ entity.game.name }" placeholder="游戏..."/>
				</div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="album">专辑</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" id="albumId" name="album.id" type="hidden" value="${ entity.album.id }"/>
			         <input class="form-control" id="albumName" type="text" onfocus="this.blur()" onclick="webside.wodota.show('searchModal',this)" href="${ctx }/videoAlbumCtrl/searchListUI.html"
			           		value="${ entity.album.name }" placeholder="专辑名称..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="albumIndex">当前集数</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="albumIndex" type="text" 
			           		value="${ entity.albumIndex }" placeholder="当前集数..."/>
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
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button type="button" onclick="javascript:$('#video-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button type="button" onclick="webside.common.loadPage('/videoCtrl/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
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