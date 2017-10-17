<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/video/album/batchVideo.js"></script>
<script>
$("#searchModal").on("hide.bs.modal", function() {  
	$(this).removeData("bs.modal");
	$("#searchModal-content").html("");
});  
</script>

<div class="page-header">
	<h1>
		批量操作专辑下的视频
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="batchVideo-form" name="batchVideo-form" class="form-horizontal" role="form" method="post">
			<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
			<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
			<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
			<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
			<input type="hidden" id="albumId" name="albumId" value="${albumId}">
			<c:forEach items="${videoList }" var="video" varStatus="ind">
				<div id="video_${ind.index }" class="video_div">
					<div class="form-group">
				      <label class="control-label col-sm-1 no-padding-right" for="videoId_${ind.index }">视频${ind.index+1 }</label>
				      <div class="col-sm-10">
					      <div class="clearfix">
					      	 <input class="form-control" id="videoId_${ind.index }" name="videoId_${ind.index }" value="${video.id }" type="hidden"/>
					         <input class="form-control" id="videoTitle_${ind.index }" name="videoTitle_${ind.index }" value="${video.title }" type="text" onfocus="webside.wodota.show('searchModal',this)" href="${ctx }/videoCtrl/searchListUI.html"
					           		placeholder="视频${ind.index+1 }..."/>
					      </div>
				      </div>
				      <div>
				         <span class="help-inline">*
					         <button type="button" onclick="delVideo(${ind.index})" class="btn btn-info btn-sm">
								<i class="fa fa-undo"></i>&nbsp;删除
							</button>
						</span>
				      </div>
					</div>
					
					<div class="form-group">
					  <label class="control-label col-sm-1 no-padding-right" for="videoIndex_${ind.index }">所属专辑集数</label>
				      <div class="col-sm-10">
					      <div class="clearfix">
					         <input class="form-control" id="videoIndex_${ind.index }" name="videoIndex_${ind.index }" value="${video.albumIndex }" type="text"
					           		placeholder="所属专辑集数..."/>
					      </div>
				      </div>
				      <div>
				         <span class="help-inline">*</span>
				      </div>
					</div>
				</div>
			</c:forEach>
		</form>
		<button type="button" onclick="addVideo()" class="btn btn-info btn-sm">
			<i class="fa fa-undo"></i>&nbsp;新增视频
		</button>
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button type="button" onclick="javascript:$('#batchVideo-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	保存
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