<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${ ctx}/resources/js/customer/business/up/station/form.js"></script>

<div class="modal-header">
	<h4 class="modal-title">
		<c:if test="${ empty entity }">
		新增视频作者站点
		</c:if>
		<c:if test="${ !empty entity }">
		编辑视频作者站点
		</c:if>
	</h4>
</div>

<div class="modal-body" >
	<div class="row" style="margin-top:5px;">
		<div class="col-xs-12">
			<form id="upstation-form" name="upstation-form" class="form-horizontal" role="form" method="post">
				<c:if test="${ !empty entity }">
					<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
					<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
					<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
					<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
					<input type="hidden" name="id" id="id" value="${entity.id}">
					<input type="hidden" name="up.id" value="${entity.up.id}">
				</c:if>
				<c:if test="${ empty entity }">
					<input type="hidden" name="up.id" value="${upId}">
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
			      <label class="control-label col-sm-1 no-padding-right" for="homeUrl">个人主页</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="homeUrl" type="text" 
				           		value="${ entity.homeUrl }" placeholder="个人主页..."/>
				      </div>
			      </div>
			      <div>
			         <span class="help-inline">*</span>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="upIntroduction">简介</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="upIntroduction" type="text" 
				           		value="${ entity.upIntroduction }" placeholder="简介..."/>
				      </div>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="upAvatar">头像</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="upAvatar" type="text" 
				           		value="${ entity.upAvatar }" placeholder="头像..."/>
				      </div>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="upVideoCount">视频数量</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="upVideoCount" type="text" 
				           		value="${ entity.upVideoCount }" placeholder="视频数量..."/>
				      </div>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="upFansCount">粉丝数量</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="upFansCount" type="text" 
				           		value="${ entity.upFansCount }" placeholder="粉丝数量..."/>
				      </div>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="upFriendCount">关注数量</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="upFriendCount" type="text" 
				           		value="${ entity.upFriendCount }" placeholder="关注数量..."/>
				      </div>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="upPlayCount">播放数量</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="upPlayCount" type="text" 
				           		value="${ entity.upPlayCount }" placeholder="播放数量..."/>
				      </div>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="station">所属站点</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <select name="station.value" id="stationValue" class="form-control">
				         	<option value="">无</option>
				   			<c:forEach items="${stationList }" var="dict">
				   				<option value="${dict.value }" <c:if test="${dict.value==entity.station.value }">selected="selected"</c:if>>${dict.label }</option>
				   			</c:forEach>
				   		</select>
				      </div>
			      </div>
				</div>
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="thirdParty">所属第三方</label>
			      <div class="col-sm-10">
				      <div class="clearfix">
				         <select name="thirdParty.value" id="thirdPartyValue" class="form-control">
				         	<option value="">无</option>
				   			<c:forEach items="${thirdPartyList }" var="dict">
				   				<option value="${dict.value }" <c:if test="${dict.value==entity.thirdParty.value }">selected="selected"</c:if>>${dict.label }</option>
				   			</c:forEach>
				   		</select>
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
</div>

<div class="modal-footer" style="text-align: center;">
	<button type="button" onclick="javascript:$('#upstation-form').submit();" class="btn btn-success btn-sm">
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