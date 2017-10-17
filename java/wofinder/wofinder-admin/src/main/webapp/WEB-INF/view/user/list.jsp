<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/customer/user/list.js"></script>

<div class="page-header">
	<shiro:hasPermission name="user:addUI">
		<button id="btnAdd" type="button" onclick="webside.common.addModel('/userCtrl/addUI.html')" class="btn btn-primary btn-sm">
		  	<i class="fa fa-user-plus"></i>&nbsp;添加
		</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="user:editUI">
		<button id="btnEdit" type="button" onclick="webside.common.editModel('/userCtrl/editUI.html')" class="btn btn-success btn-sm">
			 <i class="fa fa-pencil-square-o"></i>&nbsp;编辑
		</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="user:deleteBatch ">
		<button id="btnDel" type="button" onclick="webside.common.delModel('/userCtrl/deleteBatch.html', customSearch)" class="btn btn-danger btn-sm">
			<i class="fa fa-trash-o"></i>&nbsp;删除
		</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="user:resetPassword">
		<button id="btnDel" type="button" onclick="resetPWDModel()" class="btn btn-warning btn-sm">
			<i class="fa fa-unlock-alt"></i>&nbsp;重置密码
		</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="user:resetPassword">
		<button id="btnDel" type="button" onclick="lockModel('/userCtrl/lock.html')" class="btn btn-info btn-sm">
			<i class="fa fa-lock"></i>&nbsp;锁定
		</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="user:resetPassword">
		<button id="btnDel" type="button" onclick="lockModel('/userCtrl/unlock.html')" class="btn btn-success btn-sm">
			<i class="fa fa-unlock"></i>&nbsp;解锁
		</button>
	</shiro:hasPermission>
</div>

<div class="input-group" >
	<label class="search">昵称|手机号|邮箱：<input id="userNick" type="text" placeholder="昵称|手机号|邮箱..."></label>
	<label class="search">注册时间：
		<input id="beginCreateTime" type="text" onfocus="this.blur()" maxlength="20" placeholder="开始时间"
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
		<input id="endCreateTime" type="text" onfocus="this.blur()" maxlength="20" placeholder="结束时间"
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
	</label>
	<label class="search">状态：
		<select id="statusValue" style="width: 170px;">
   			<option value="">不限</option>
   			<c:forEach items="${statusList }" var="dict">
   				<option value="${dict.value }">${dict.label }</option>
   			</c:forEach>
   		</select>
	</label>
	<label class="search">是否锁定：
		<select id="lockedValue" style="width: 170px;">
   			<option value="">不限</option>
   			<c:forEach items="${lockedList }" var="dict">
   				<option value="${dict.value }">${dict.label }</option>
   			</c:forEach>
   		</select>
	</label>
	<span class="input-group-btn">
		<button id="btnSearch" class="btn btn-primary btn-sm" type="button"> <i class="fa fa-search"></i> 搜索</button>
	</span>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12 widget-container-col ui-sortable" style="min-height: 127px;">
		<div class="widget-box transparent ui-sortable-handle" style="opacity: 1; z-index: 0;">
			<div class="widget-header">
				<h4 class="widget-title lighter">用户列表</h4>
				
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

