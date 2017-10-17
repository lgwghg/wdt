<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/video/video/merge.js"></script>
<script>
$("#searchModal").on("hide.bs.modal", function() {  
	$(this).removeData("bs.modal");
	$("#searchModal-content").html("");
});  
</script>

<div class="page-header">
	<h1>
		合并视频
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="merge-form" name="merge-form" class="form-horizontal" role="form" method="post">
			<div class="form-group" id="video_0">
		      <label class="control-label col-sm-1 no-padding-right" for="videoId_0"><span style="color: red">（主）</span>视频1</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	 <input class="form-control" id="videoId_0" name="videoId_0" type="hidden"/>
			         <input class="form-control" id="videoTitle_0" name="videoTitle_0" type="text" onfocus="webside.wodota.show('searchModal',this)" href="${ctx }/videoCtrl/searchListUI.html"
			           		placeholder="视频1..."/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group" id="video_1">
		      <label class="control-label col-sm-1 no-padding-right" for="videoId_1">视频2</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	 <input class="form-control" id="videoId_1" name="videoId_1" type="hidden"/>
			         <input class="form-control" id="videoTitle_1" name="videoTitle_1" type="text" onfocus="webside.wodota.show('searchModal',this)" href="${ctx }/videoCtrl/searchListUI.html"
			           		placeholder="视频2..."/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
		</form>
		<div id="stationDiv">
			
		</div>
		<p>注意：带有红字主标识的视频为主要参考标准，数据以该记录为主，其余记录为辅。</p>
		<p>1、专辑、所属游戏、状态、其它基本属性均以主视频为主；评分取值最高的。</p>
		<p>2、评论：保留去重后的所有评论。</p>
		<p>3、属性值：保留去重后的所有属性值。</p>
		<p>4、站点：如存在站点冲突，将会在当前页给出选择，每个站点只允许存在一条记录。</p>
		<button type="button" onclick="addVideo()" class="btn btn-info btn-sm">
			<i class="fa fa-undo"></i>&nbsp;新增视频
		</button>
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button type="button" onclick="javascript:$('#merge-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	合并
	</button>
	
	<button type="button" onclick="javascript:top.location.href = 'admin'" class="btn btn-info btn-sm">
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