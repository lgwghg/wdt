<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/video/station/form.js"></script>
<script>
$("#searchModal").on("hide.bs.modal", function() {  
	$(this).removeData("bs.modal");
	$("#searchModal-content").html("");
});  
</script>

<div class="modal-header">
	<h4 class="modal-title">
		<c:if test="${ empty entity }">
		新增视频站点
		</c:if>
		<c:if test="${ !empty entity }">
		编辑视频站点
		</c:if>
	</h4>
</div>

<div class="modal-body" >
	<div class="row" style="margin-top:5px;">
		<div class="col-xs-12">
			<form id="videostation-form" name="videostation-form" class="form-horizontal" role="form" method="post">
				<c:if test="${ !empty entity }">
					<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
					<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
					<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
					<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
					<input type="hidden" id="id" name="id" value="${entity.id}">
					<input type="hidden" name="video.id" value="${entity.video.id}">
				</c:if>
				<c:if test="${ empty entity }">
					<input type="hidden" name="video.id" value="${videoId}">
				</c:if>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="vid">视频源id</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="vid" type="text" 
				           		value="${ entity.vid }" placeholder="视频源id..."/>
				      </div>
			      </div>
			      <div>
			         <span class="help-inline">*</span>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="url">视频源url</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="url" type="text" 
				           		value="${ entity.url }" placeholder="视频源url..."/>
				      </div>
			      </div>
			      <div>
			         <span class="help-inline">*</span>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="flashUrl">视频播放url</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="flashUrl" type="text" 
				           		value="${ entity.flashUrl }" placeholder="视频播放url..."/>
				      </div>
			      </div>
			      <div>
			         <span class="help-inline">*</span>
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
			       <div>
			         <span class="help-inline">*</span>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="introduction">简介</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="introduction" type="text" 
				           		value="${ entity.introduction }" placeholder="简介..."/>
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
			      <div>
			         <span class="help-inline">*</span>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="duration">时长</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="duration" type="text" 
				           		value="${ entity.duration }" placeholder="时长..."/>
				      </div>
			      </div>
			      <div>
			         <span class="help-inline">*</span>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="category">类别</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="category" type="text" 
				           		value="${ entity.category }" placeholder="类别..."/>
				      </div>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="published">发布时间</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				      	 <input name="published" type="text" onfocus="this.blur()" maxlength="100" placeholder="发布时间..."
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true});" value="${ entity.published }" style="width: 100%"/>
				      </div>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="viewCount">播放量</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="viewCount" type="text" 
				           		value="${ entity.viewCount }" placeholder="播放量..."/>
				      </div>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="commentCount">评论量</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="commentCount" type="text" 
				           		value="${ entity.commentCount }" placeholder="评论量..."/>
				      </div>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="downCount">下载量</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="downCount" type="text" 
				           		value="${ entity.downCount }" placeholder="下载量..."/>
				      </div>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="favoriteCount">收藏量</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="favoriteCount" type="text" 
				           		value="${ entity.favoriteCount }" placeholder="收藏量..."/>
				      </div>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="up">视频作者</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				      	 <input class="form-control" id="upId" name="up.id" type="hidden" value="${ entity.up.id }"/>
				         <input class="form-control" id="upName" type="text" onfocus="webside.wodota.show('searchModal',this)" href="${ctx }/upCtrl/searchListUI.html"
				           		value="${ entity.up.name }" placeholder="视频作者..."/>
				      </div>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="station">所属站点</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <select name="station.value" class="form-control">
				   			<c:forEach items="${stationList }" var="dict">
				   				<option value="${dict.value }" <c:if test="${dict.value==entity.station.value }">selected="selected"</c:if>>${dict.label }</option>
				   			</c:forEach>
				   		</select>
				      </div>
			      </div>
			      <div>
			         <span class="help-inline">*</span>
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
</div>

<div class="modal-footer" style="text-align: center;">
	<button type="button" onclick="javascript:$('#videostation-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button type="button" class="btn btn-primary btn-sm" data-dismiss="modal"><i class="fa fa-undo"></i>&nbsp;返回</button>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="searchModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 1000px;">
        <div class="modal-content" id="searchModal-content">
        </div>
    </div>
</div>