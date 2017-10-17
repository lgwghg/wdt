<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/common/upload.jsp"%>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/video/recommend/form.js"></script>
<script>
$("#searchModal").on("hide.bs.modal", function() {  
	$(this).removeData("bs.modal");
	$("#searchModal-content").html("");
});  
</script>

<div class="page-header">
	<h1>
		<c:if test="${ empty entity }">
		新增首页推荐视频
		</c:if>
		<c:if test="${ !empty entity }">
		编辑首页推荐视频
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="videorecommend-form" name="videorecommend-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" id="id" name="id" value="${entity.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="video">视频</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	<input class="form-control" id="videoId" name="video.id" type="hidden" value="${ entity.video.id }"/>
			        <input class="form-control" id="videoTitle" type="text" onfocus="webside.wodota.show('searchModal',this)" href="${ctx }/videoCtrl/searchListUI.html"
			           		value="${ entity.video.title }" placeholder="视频标题..."/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="url">展示视频地址</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="url" id="url" type="hidden"
			           		value="${ entity.url }" placeholder="展示视频地址..."/>
			         <video src="${ entity.url }" type="video/mp4" controls="controls" width="600" height="400" id="video"></video>
					<div id="video_upload"></div>
			      </div>
		      </div>
		      <!-- <div>
		         <span class="help-inline">*</span>
		      </div> -->
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="startTime">开始展示时间</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	 <input name="startTime" type="text" onfocus="this.blur()" maxlength="100" placeholder="开始展示时间..."
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true});" value="${ entity.startTime }" style="width: 100%"/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="endTime">结束展示时间</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	 <input name="endTime" type="text" onfocus="this.blur()" maxlength="40" placeholder="结束展示时间..."
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true});" value="${entity.endTime }" style="width: 100%"/>
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
	<button type="button" onclick="javascript:$('#videorecommend-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button type="button" onclick="webside.common.loadPage('/videoRecommendCtrl/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
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