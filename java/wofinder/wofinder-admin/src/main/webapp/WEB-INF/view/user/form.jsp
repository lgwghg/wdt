<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${ ctx}/resources/js/customer/user/form.js"></script>

<div class="page-header">
	<h1>
		<c:if test="${ empty entity }">
		新增用户
		</c:if>
		<c:if test="${!empty entity}">
		编辑用户
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="userForm" name="userForm" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" name="id" id="id" value="${entity.id}">
			</c:if>
			
			<c:choose>
				<c:when test="${ empty entity }">
					<div class="form-group">
						<label class="control-label col-sm-1 no-padding-right" for="nickName">昵称</label>
						<div class="col-sm-10">
							<div class="clearfix">
								<input <c:if test="${!empty entity}">disabled="disabled"</c:if> class="form-control" name="nickName" type="text" 
					           		value="${ entity.nickName }" placeholder="昵称..."/>
							</div>
						</div>
						<div>
				        	<span class="help-inline">*</span>
				        </div>
				    </div>
				    <div class="form-group">
						<label class="control-label col-sm-1 no-padding-right" for="mobile">手机号</label>
						<div class="col-sm-10">
							<div class="clearfix">
								<input <c:if test="${!empty entity}">disabled="disabled"</c:if> class="form-control" name="mobile" type="text" 
					           		value="${ entity.mobile }" placeholder="手机号..."/>
							</div>
						</div>
						<div>
				        	<span class="help-inline">*</span>
				        </div>
				    </div>
				    <div class="form-group">
						<label class="control-label col-sm-1 no-padding-right" for="email">邮箱</label>
						<div class="col-sm-10">
							<div class="clearfix">
								<input <c:if test="${!empty entity}">disabled="disabled"</c:if> class="form-control" name="email" type="text" 
					           		value="${ entity.email }" placeholder="邮箱..."/>
							</div>
						</div>
						<div>
				        	<span class="help-inline">*</span>
				        </div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1 no-padding-right" for="password">密码</label>
						<div class="col-sm-10">
							<div class="clearfix">
								<input class="form-control" name="password" id="password" type="password" 
				           			placeholder="密码..."/>
							</div>
						</div>
						<div>
				        	<span class="help-inline">*</span>
				        </div>
					</div>
				    <div class="form-group">
				      	<label class="control-label col-sm-1 no-padding-right" for="repassword">确认密码</label>
					    <div class="col-sm-10">
							<div class="clearfix">
								<input class="form-control" name="repassword" id="repassword" type="password"
									placeholder="确认密码..."/>
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
					</div>
				</c:when>
				<c:otherwise>
					<div class="form-group">
						<label class="control-label col-sm-1 no-padding-right" for="nickName">昵称</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input disabled="disabled" class="form-control" name="nickName" type="text" 
					           		value="${ entity.nickName }" placeholder="昵称..."/>
							</div>
						</div>
						
						<label class="control-label col-sm-1 no-padding-right" for="mobile">手机号</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input disabled="disabled" class="form-control" name="mobile" type="text" 
					           		value="${ entity.mobile }" placeholder="手机号..."/>
							</div>
						</div>
				    
						<label class="control-label col-sm-1 no-padding-right" for="email">邮箱</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input disabled="disabled" class="form-control" name="email" type="text" 
					           		value="${ entity.email }" placeholder="邮箱..."/>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1 no-padding-right" for="steamKey">steam key</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input disabled="disabled" class="form-control" name="steamKey" type="text" value="${entity.steamKey }"  />
							</div>
						</div>
		
						<label class="control-label col-sm-1 no-padding-right" for="qqKey">QQ key</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input disabled="disabled" class="form-control" name="qqKey" id="qqKey" type="text" value="${entity.qqKey}" />
							</div>
						</div>
		
						<label class="control-label col-sm-1 no-padding-right" for="wechatKey">wechat key</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input disabled="disabled" class="form-control" name="wechatKey" id="wechatKey" type="text" value="${entity.wechatKey }" />
							</div>
						</div>
		
						<label class="control-label col-sm-1 no-padding-right" for="weiboKey">weibo key</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input disabled="disabled" class="form-control" name="weiboKey" id="weiboKey" type="text" value="${entity.weiboKey }" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1 no-padding-right" for="steamNick">steam 昵称</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input disabled="disabled" class="form-control" name="steamNick" id="steamNick" type="text" value="${entity.steamNick }"  />
							</div>
						</div>
		
						<label class="control-label col-sm-1 no-padding-right" for="qqNick">QQ 昵称</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input disabled="disabled" class="form-control" name="qqNick" id="qqNick" type="text" value="${entity.qqNick}" />
							</div>
						</div>
		
						<label class="control-label col-sm-1 no-padding-right" for="wechatNick">wechat 昵称</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input disabled="disabled" class="form-control" name="wechatNick" id="wechatNick" type="text" value="${entity.wechatNick }" />
							</div>
						</div>
		
						<label class="control-label col-sm-1 no-padding-right" for="weiboNick">weibo 昵称</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input disabled="disabled" class="form-control" name="weiboNick" id="weiboNick" type="text" value="${entity.weiboNick }" />
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label class="control-label col-sm-1 no-padding-right" for="photo">头像</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<img src="${entity.photo }" width="65" height="65"/>
							</div>
						</div>
						
						<label class="control-label col-sm-1 no-padding-right" for="sign">用户签名</label>
						<div class="col-sm-8">
							<div class="clearfix">
								<textarea disabled="disabled" class="form-control" rows="3" name="sign" placeholder="个性签名...">${entity.sign }</textarea>
							</div>
						</div>
					</div>
			
					<div class="form-group">
				      <label class="control-label col-sm-1 no-padding-right" for="status">状态</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					         <select name="status.value" class="form-control">
					   			<c:forEach items="${statusList }" var="dict">
					   				<option value="${dict.value }" <c:if test="${dict.value==entity.status.value }">selected="selected"</c:if>>${dict.label }</option>
					   			</c:forEach>
					   		</select>
					      </div>
				      </div>
				      
				      <label class="control-label col-sm-1 no-padding-right" for="locked">是否锁定</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					         <select name="locked.value" class="form-control">
					   			<c:forEach items="${lockedList }" var="dict">
					   				<option value="${dict.value }" <c:if test="${dict.value==entity.locked.value }">selected="selected"</c:if>>${dict.label }</option>
					   			</c:forEach>
					   		</select>
					      </div>
				      </div>
					</div>
				</c:otherwise>
			</c:choose>
		</form>
		<div class="hr hr-dotted"></div>
	</div>
</div>
<div class="center">
	<button type="button" onclick="javascript:$('#userForm').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	<button type="button" onclick="webside.common.loadPage('/userCtrl/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum }&rows=${page.pageSize }&sidx=${page.orderByColumn }&sord=${page.orderByType }</c:if>')" class="btn btn-primary btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>
