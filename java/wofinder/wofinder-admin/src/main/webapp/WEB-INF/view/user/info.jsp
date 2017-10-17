<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<link rel="stylesheet" href="${ctx }/resources/js/datepicker/css/bootstrap-datepicker3.standalone.min.css"/>
<script type="text/javascript" src="${ctx }/resources/js/datepicker/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/datepicker/locales/bootstrap-datepicker.zh-CN.min.js" charset="UTF-8"></script>
<script type="text/javascript">
$(document).ready(function() {
    webside.form.userInfo.initButton();
    webside.form.userInfo.initBirthday();
    webside.form.userInfo.validateUserInfoForm();
});
</script>
<style type="text/css">
.noform{
	padding:7px 0px 0px 12px;
	font-size: 14px;
}
</style>
<div class="page-header">
	<h1>
		个人资料
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form class="form-horizontal" role="form" method="post">
			   <div class="form-group">
					<button id="btnEdit" type="button" class="btn btn-success btn-sm">
					  	<i class="fa fa-user-plus"></i>&nbsp;编辑
					</button>
					<button id="btnAdd" type="button" onclick="javascript:$('#userInfoForm').submit();" class="btn btn-success btn-sm" style="display: none;">
					  	<i class="fa fa-user-plus"></i>&nbsp;保存
					</button>
					<button id="btnBack" type="button" onclick="javascript:top.location.href = 'admin'" class="btn btn-primary btn-sm">
						<i class="fa fa-undo"></i>&nbsp;返回
					</button>
				</div>
			   <div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right">昵称:</label>
			      <div class="col-sm-10 noform">
			      	${userEntity.nickName }
			      </div>
			   </div>
			   <div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right">手机号:</label>
			      <div class="col-sm-10 noform">
			      	${userEntity.mobile }
			      </div>
			   </div>
			   <div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right">所属角色:</label>
			      <div class="col-sm-10 noform">
			      	${userEntity.role.name}
			      </div>
			   </div> 
			   <%-- <div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right">用户描述:</label>
			      <div class="col-sm-10 noform">
			         <c:if test="${empty userEntity.description }">无</c:if>
			         <c:if test="${!empty userEntity.description }">${userEntity.description }</c:if>
			      </div>
			   </div>  --%>
		</form>
		<div class="hr hr-dotted"></div>
		<form id="userInfoForm" name="userInfoForm" class="form-horizontal" role="form" method="post">
			<input type="hidden" name="id" id = "id" value="${userEntity.id }">
			<input type="hidden" name="role.id" value="${userEntity.role.id }">
			<input type="hidden" name="mobile" value="${userEntity.mobile }">
			<div id="lableDiv">
				<div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right">邮箱:</label>
			      <div class="col-sm-10 noform">
			      	${userEntity.email }
			      </div>
			   </div> 
				<%-- <div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right">性别:</label>
			      <div class="col-sm-10 noform">
					<c:if test="${userEntity.userInfo.sex eq 1}">男</c:if>
					<c:if test="${userEntity.userInfo.sex eq 2}">女</c:if>
			      </div>
				</div> 
				<div class="form-group">
				      <label class="control-label col-sm-1 no-padding-right" for="birthday">出生日期:</label>
				      <div class="col-sm-10 noform">
				         <fmt:formatDate value="${userEntity.birthday }" pattern="yyyy-MM-dd"/>
			      	</div>
			    </div>  --%> 
			    <div class="form-group">
				      <label class="control-label col-sm-1 no-padding-right" for="sign">签名:</label>
				      <div class="col-sm-10 noform">
				         ${userEntity.sign }
			      	</div>
			    </div>
			   <%--  <div class="form-group">
				      <label class="control-label col-sm-1 no-padding-right" for="sign">自定义url:</label>
				      <div class="col-sm-10 noform">
				         ${userEntity.userUrl }
			      	</div>
			    </div> --%>
			</div>
			<div id="formDiv" style="display: none;">
				<div class="form-group">
				      <label class="control-label col-sm-1 no-padding-right">邮箱:</label>
				      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="email" id="email" type="text"
				          value="${userEntity.email }" placeholder="邮箱..."/>
				          </div>
			      	</div>
			    </div> 
			  <%--  <div class="form-group">
			      <label class="control-label col-sm-1 no-padding-right" for="userInfo.sex">性别</label>
			      <div class="col-sm-10">
			           <div class="radio">
							<label>
								<input name="userInfo.sex" type="radio" class="ace input-lg" value="1" <c:if test="${userEntity.userInfo.sex eq 1}">checked</c:if>>
								<span class="lbl bigger-110">男</span>
							</label>
							<label>
								<input name="userInfo.sex" type="radio" class="ace input-lg" value="2" <c:if test="${userEntity.userInfo.sex eq 2}">checked</c:if>>
								<span class="lbl bigger-110">女</span>
							</label>
						</div>
			      </div>
			   </div>
				<div class="form-group">
				      <label class="control-label col-sm-1 no-padding-right" for="birthday">出生日期</label>
				      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control date-picker" name="birthday" id="birthday" type="text"
				          value="<fmt:formatDate value="${userEntit.birthday }" pattern="yyyy-MM-dd"/>" placeholder="出生日期..."/>
			      		</div>
			      	</div>
			    </div>    --%>
			    <div class="form-group">
				      <label class="control-label col-sm-1 no-padding-right" for="sign">签名</label>
				      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="sign" id="sign" type="text"
				          value="${userEntity.sign }" placeholder="个性签名..."/>
				          </div>
			      	</div>
			    </div>
			   <%--  <div class="form-group">
				      <label class="control-label col-sm-1 no-padding-right" for="userUrl">自定义URL</label>
				      <div class="col-sm-10">
				      <div class="clearfix">
				         <input class="form-control" name="userUrl" id="userUrl" type="text"
				          value="${userEntity.userUrl }" placeholder="自定义url..."/>
				          </div>
			      	</div>
			    </div> --%>
			</div>   
		</form>
	</div>
</div>