<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${ctx}/resources/js/customer/business/crawl/singleVideo.js"></script>

<div class="page-header">
	<h1>
		单视频爬取
	</h1>
</div>

<div class="input-group" >
<!-- 	<label class="search">视频标题：<input id="title" type="text" placeholder="视频标题..."></label> -->
	<label class="search">视频ID：<input id="vid" type="text" style="width:300px" placeholder="视频ID..."></label>
	 <label class="search">站点：
		<select id="stationValue" style="width: 170px;">
   			<option value="">不限</option>
   			<c:forEach items="${stationList }" var="dict">
   				<option value="${dict.value }">${dict.label }</option>
   			</c:forEach>
   		</select>
	</label>
	<label>
	<button id="btnSearch" class="btn btn-primary btn-sm" type="button"> <i class="fa fa-search"></i> 搜索</button>
	<button id="addVideo" data-toggle="modal" data-target="#listModal" class="btn btn-success btn-sm" type="button"> <i class="fa fa-user-plus"></i> 新增</button>
	<button id="crawlVideo" class="btn btn-info btn-sm" type="button"> <i class="fa fa-user-plus"></i> 爬取</button>
	</label>
	<span class="input-group-btn">
	</span>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12 widget-container-col ui-sortable" style="min-height: 127px;">
		<div class="widget-box transparent ui-sortable-handle" style="opacity: 1; z-index: 0;">
			<div class="widget-header">
				
				<div class="widget-toolbar no-border">
					<a href="#" data-action="fullscreen" class="orange2"> 
						<i class="ace-icon fa fa-arrows-alt"></i>
					</a> 
					<a href="#" data-action="collapse" class="green"> 
						<i class="ace-icon fa fa-chevron-up"></i>
					</a>
				</div>
			</div>

			<div class="widget-body" style="display: block;">
				<div class="widget-main padding-6 no-padding-left no-padding-right">
					<input id="pageNum" type="hidden" value="${page.pageNum}">
					<input id="pageSize" type="hidden" value="${page.pageSize}">
					<input id="orderByColumn" type="hidden" value="${page.orderByColumn}">
					<input id="orderByType" type="hidden" value="${page.orderByType}">
					<div id="dtGridContainer" class="dlshouwen-grid-container"></div>
					<div id="dtGridToolBarContainer" class="dlshouwen-grid-toolbar-container"></div>
				</div>
			</div>
		</div>
	</div>
</div>
		<p>1、该页面用于单视频爬取数据以及更新数据。</p>
		<p>2、单视频可是WF视频，或单站点。</p>
		<p>3、没有选择站点即为WF视频。</p>
		<p>4、目前只支持B站、A站。</p>
		
<!-- 模态框（Modal） -->
<div class="modal fade" id="listModal" tabindex="-1" role="dialog" aria-labelledby="listModalLabel" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog" style="width: 700px;height: 125px;position:absolute;margin:0;top:50%;margin-top:-75px;left:50%;margin-left:-400px;">
        <div class="modal-content" id="listModal-content">
        	<div class="modal-body">
			 	<label class="search">站点：
					<select id="stationValue2" style="width: 170px;">
			   			<option value="">不限</option>
			   			<c:forEach items="${stationList }" var="dict">
			   				<option value="${dict.value }">${dict.label }</option>
			   			</c:forEach>
			   		</select>
				</label>
	        	<label class="search">视频ID：<input id="vid2" type="text" style="width:300px" placeholder="视频ID..."></label>
        	</div>
			
			<div style="margin:0 auto;margin-bottom:10px;display: table;">
				<button type="button" style="margin-right:5px;" class="btn btn-primary btn-sm" onclick="addVideo();">确定</button>
				<button type="button" class="btn btn-primary btn-sm" onclick="$('#listModal').modal('hide');">关闭</button>
			</div>
        </div>
    </div>
</div>
