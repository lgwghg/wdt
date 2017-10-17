<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/common/upload.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/business/up/photo/form.js"></script>

<script type="text/javascript">
	$(function() 
	{
	    upPhoto.form.validateForm();
	    //var ue = UE.getEditor("photo");
	});
</script>

<div class="modal-header">
	<h4 class="modal-title">
		<c:if test="${ empty entity.id }">
		新增
		</c:if>
		<c:if test="${ !empty entity.id }">
		编辑
		</c:if>
	</h4>
</div>

<div class="modal-body">
	<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="upphoto-form" name="upphoto-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" name="id" id="id" value="${entity.id}">
				<input type="hidden" name="upId" id="upId" value="${entity.upId}">
			</c:if>
			<c:if test="${ empty entity }">
				<input type="hidden" name="upId" id="upId" value="${upId}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="upSecondId">二级标题</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <select class="form-control" name="upSecondId" id="upSecondId" type="text"  value="${ entity.upSecondId }">
			         	<option value="">--请选择--</option>
			         	<c:forEach items="${secondLevelList }" var="secondLevel">
			         		<option value="${secondLevel.id }" <c:if test="${not empty entity.upSecondId && entity.upSecondId == secondLevel.id }">selected = true</c:if>>${secondLevel.title }</option>
			         	</c:forEach>
			         </select>
			      </div>
		      </div>
			</div>
			<%-- <div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="photoName">相册名称</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="photoName" id="photoName" type="text" 
			           		value="${ entity.photoName }" placeholder="相册名称..."/>
			      </div>
		      </div>
			</div> --%>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="photo">图片地址</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input name="photo" id="photo" type="hidden" class="form-control"
		         		value="${entity.photo }" placeholder="图片地址..."/>
			         <div id="photo_view_div" style="width:120px; height:120px">  
				         <img src="${ctx}/resources/images/nopic.gif" style="width:120px; height:120px" id="photo_view">  
				    </div>  
					<div id="photo_upload"></div>
		      	</div>
			    <div>
		        	<span class="help-inline"></span>
		      	</div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="isMain">是否主图</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <select class="form-control" name="isMain" id="isMain" type="text" 
			           		value="${ entity.isMain }">
			         	<option value="0" <c:if test="${not empty entity.isMain && entity.isMain == 0}">selected=true</c:if>>非主图</option>
			         	<option value="1" <c:if test="${not empty entity.isMain && entity.isMain == 1}">selected=true</c:if>>主图</option>
			         </select>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="isMain">是否有效</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <select class="form-control" name="status" id="status" type="text" value="${ entity.status }">
			         	<option value="1" <c:if test="${not empty entity.status && entity.status == 1}">selected=true</c:if>>有效</option>
			         	<option value="0" <c:if test="${not empty entity.status && entity.status == 0}">selected=true</c:if>>无效</option>
			         </select>
			      </div>
		      </div>
			</div>
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
	</div>
</div>

<div class="modal-footer" style="text-align: center;">
	<button id="btnAdd" type="button" onclick="javascript:$('#upphoto-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button id="btn" type="button" data-dismiss="modal" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>