<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
request.setAttribute("staticPrefix",  request.getContextPath()+"/resources/wofinder");
%>
<link rel="stylesheet" href="${staticPrefix }/css/cropper.min.css?v=${version }" type="text/css">
<script src="${staticPrefix }/js/cropper.min.js?v=${version }" type="text/javascript"></script>
<script src="${staticPrefix }/js/sitelogo.js?v=${version }" type="text/javascript"></script>
<script src="${staticPrefix}/js/jquery.form.js?v=${version }" type="text/javascript"></script>
<%-- 个人信息 --%>
<div class="content-foolpm">
        <div class="content-item">
            <div class="iconwrap">
                <span class="fool-pm">昵称 :</span>
                <div class="form-group label-floating">
               	   <input type="hidden" value="${userInfo.nickName }" id="oldNickName"/>
                   <input type="text" class="form-control" placeholder="请输入昵称" value="${userInfo.nickName }" id="nickName" onblur="checkNickName()">
                   <span class="help-block error" id="nickNameError"></span>
                </div>
            </div>
            <div class="btn-group">
                <button class="btn btn-success btn-block amend" type="button" onclick="nickNameBtn()" id="nickNameBtn">保存</button>
                <button class="btn btn-success btn-block cancel" type="button" onclick="nickNameCancelBtn()" id="nickNameCancelBtn">取消</button>
            </div>
        </div>
        <div class="content-item">
            <div class="iconwrap">
                <span class="fool-pm" style="margin-top: 58px;">头像 :</span>
                <div class="avatarwrap">
                <i></i>
                    <div class="imgzhezhao">
                        <div class="shch">
                    <strong><a href="#" data-toggle="modal"
                data-target="#avatar-modal"><i class="iconfont icon-wf_shangchuan"></i>上传头像</a></strong>
                        </div>
                    </div>
                        <img class="avatar" src="${staticPrefix }/images/default.png" alt="" width="100%" height="100%" id="user_pic">
                </div>
                <div id="photoHistory">
                <c:if test="${not empty usrePhotoList}">
                <c:forEach items="${usrePhotoList }" var="photo" varStatus="status">
                <div class="recteg <c:if test='${status.index eq 0 }'>active</c:if>" photoId="${photo.id }">
                    <img src="${photo.getPhoto_133() }" alt="" width="100%" height="100%">
                    <a title="删除" href="javascript:;" class="iconfont icon-wf_cuowushi cuowu"></a>
                </div>
                </c:forEach>
                </c:if>
                </div>
            </div>
            <div class="btn-group">
				<button class="btn btn-success btn-block amend" type="button" id="photoBtn" onclick="photoBtn()">保存</button>
                <button class="btn btn-success btn-block cancel" type="button" id="photoCancelBtn" onclick="photoCancelBtn()">取消</button>
            </div>
        </div>
        <div class="content-item">
            <div class="iconwrap">
                <span class="fool-pm">电话 :</span>
                <div class="form-group label-floating">
                   <input type="tel" class="form-control" placeholder="请输入电话号码" value="${userInfo.mobile }" disabled>
                </div>
            </div>
            <div class="btn-group">
				<button class="btn btn-success btn-block amend" type="button" id="mobileBtn">去修改</button>
                <!-- <button class="btn btn-success btn-block cancel" type="button">取消</button> -->
            </div>
        </div>
        <div class="content-item">
            <div class="iconwrap">
                <span class="fool-pm">生日 :</span>
                <input type="hidden" id="oldBirthday" value="${userInfo.userIncrement.birthday }" />
                <div class="form-group label-floating">
		           <input type="text" id="birthday" class="form-control" placeholder="请选择生日">
		        </div>
                <%-- <div class="select">
                 <div class="form-group">
                    <select class="form-control" name="year1" id="birthdayYear" >

                    </select>
                    <span class="help-block error" id="birthdayYearError"></span>
                  </div>
                  <div class="twrap"><span class="tyear">年</span></div>
                   <div class="form-group">
                    <select class="form-control" name="month1" id="birthdayMonth" >
  
                    </select>
                    <span class="help-block error" id="birthdayMonthError"></span>
                  </div>
                  <div class="twrap"><span class="tmonth">月</span></div>
                   <div class="form-group">
                    <select class="form-control" name="day1" id="birthdayDay" >
           
                    </select>
                    <span class="help-block error" id="birthdayDayError"></span>
                  </div>
                  <div class="twrap"><span class="tday">日</span></div>
            </div> --%>
            </div>
            <div class="btn-group">
				<button class="btn btn-success btn-block amend" type="button" id="birthdayBtn" onclick="birthdayBtn()">保存</button>
                <button class="btn btn-success btn-block cancel" type="button" id="birthdayCancelBtn" onclick="birthdayCancelBtn()">取消</button>
            </div>
        </div>
        <div class="content-item">
            <div class="iconwrap">
                <span class="fool-pm">性别 :</span>
                <input type="hidden" id="oldSex" value="${userInfo.userIncrement.sex }" />
                <div class="radiowrap">
                    <div class="radio">
                        <label>
                        <i class="iconfont icon-nan" title="男"></i>
                        <input type="radio" name="sex" value="1" <c:if test="${not empty userInfo.userIncrement.sex and userInfo.userIncrement.sex == 1}">checked</c:if> >  
                      </label>
                    </div>
                    <div class="radio" style="margin-top: 10px;">
                      <label>
                        <i class="iconfont icon-nv" title="女"></i>
                        <input type="radio" name="sex" value="0" <c:if test="${not empty userInfo.userIncrement.sex and userInfo.userIncrement.sex == 0}">checked</c:if> >
                      </label>
                    </div>
                </div>
            </div>
            <div class="btn-group">
                <button class="btn btn-success btn-block amend" type="button" id="sexBtn" onclick="sexBtn()">保存</button>
                <button class="btn btn-success btn-block cancel" type="button" id="sexCancelBtn" onclick="sexCancelBtn()">取消</button>
            </div>
        </div>
        <div class="content-item">
            <div class="iconwrap">
                <span class="fool-pm" style="margin-top: 0px;">兴趣 :</span>
                	<div class="counterwrap">
                      <div class="counter">
                          <c:if test="${not empty interestList }">
                          <%-- dark orange green blue pink sky--%>
                          <c:forEach items="${interestList }" var="interest" varStatus="status">
                          <span class="counter-item" interestId="${interest.id }">${interest.interest }
				            <i class="iconfont icon-wf_cuowushi"></i>
				          </span>
                          </c:forEach>
                          </c:if>
				          <%-- <c:set var="interestSize" value="${fn:length(interestList)}"> --%>
				          <%-- <div class="form-group label-floating add-detail hide">
				              <input type="text" class="form-control" placeholder="请输入..." id="interest" onBlur="checkInterest()">
				              <span class="help-block error" id="interestError"></span>
				          </div> --%>
        			  </div>
        			  <span class="add-item btn <%-- <c:if test="${not empty fn:length(interestList) and fn:length(interestList) gt 4}">hide</c:if> --%>"  
			          data-toggle="modal" data-target="#title-modal">
			            <i class="iconfont icon-wf_zhankai"></i>  
			          </span>
			      </div>
      		</div>
	      	<%-- <div class="btn-group" style="margin-top:0">
	        	<button class="btn btn-success btn-block amend interestbtn" type="button" id="interestBtn" onclick="interestBtn()">修改</button>
	        	<button class="btn btn-success btn-block cancel" type="button" id="interestCancelBtn" onclick="interestCancelBtn()">取消</button>
	      	</div> --%>
        </div>
    </div> 
    
    
        <!--picshanchuan-->
  <div class="modal fade" id="avatar-modal" aria-hidden="true"
    aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
  <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <form class="avatar-form" action="/avatar/upload"
          enctype="multipart/form-data" method="post" id="uploadImgForm">
          <div class="modal-header">
          	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="iconfont icon-wf_guanbi"></i></button>
            <h3 class="modal-title">图片选择</h3>
          </div>
          <div class="modal-body">
            <div class="avatar-body">
                    <div class="avatar-upload">
                        <input class="avatar-src" name="avatar_src" type="hidden">
	                  	<input class="avatar-data" name="avatar_data"  id="avatar_data" type="hidden">
	                  	<label class="btn avatar-label" for="avatarInput">点击上传文件</label>
	                  	<input type="file" id="avatarInput" multiple="" class="avatar-input" name="file"/>
                      </div>
              <div class="avatar">
                <div class="avatarone">
                  <div class="avatar-wrapper"></div>
                </div>
                <div class="avatartwo">
                  <div class="avatar-preview preview-lg"></div>
                  <div class="avatar-preview preview-md"></div>
                  <div class="avatar-preview preview-sm"></div>
                </div>
              </div>
              <div class="row avatar-btns">
                <div class="col-md-9">
                  <div class="btn-group">
                    <button class="btn"
                      data-method="rotate" data-option="-90" type="button"
                      title="Rotate -90 degrees">向左旋转</button>
                  </div>
                  <div class="btn-group">
                    <button class="btn"
                      data-method="rotate" data-option="90" type="button"
                      title="Rotate 90 degrees">向右旋转</button>
                  </div>
                </div>
                <div class="col-md-3">

                </div>
              </div>
              <div class="saveclose">
              <div class="btn-group">
                <a href="javascript:;" class="close" data-dismiss="modal">取消</a>            </div>  
                <div class="btn-group">
                  <button class="btn btn-success btn-block avatar-save"
                    type="button" onclick="uploadImg()">保存修改</button>
                </div>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
   </div> 
   
   
   <!--titleshanchuan-->
  <div class="modal fade" id="title-modal" aria-hidden="true"
    aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
  <div class="modal-dialog modal-lg">
      <div class="modal-content">
          <div class="modal-header">
          	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="iconfont icon-wf_guanbi"></i></button>
            <h3 class="modal-title">编辑兴趣标签</h3>
          </div>
          <div class="modal-body">
            <div class="form-group label-floating">
               <input type="text" class="form-control" placeholder="请输入兴趣标签" id="interest">
               <span class="help-block" style="color:red" id="interestError"></span>
            </div>
            <p class="tishititle">标签最多添加5个，按回车键即可自动生成标签</p>
            <div class="neirong">
              <c:if test="${not empty interestList }">
              <c:forEach items="${interestList }" var="interest" varStatus="status">
                <span class="counter-item" interestId="${interest.id }">${interest.interest }
   				<i class="iconfont icon-wf_cuowushi"></i>
        		</span>
              </c:forEach>
              </c:if>
              <!-- <span class="counter-item">英雄联盟
                <i class="iconfont icon-wf_cuowushi"></i>
              </span>
              <span class="counter-item">守望先锋
                <i class="iconfont icon-wf_cuowushi"></i>
              </span>
              <span class="counter-item">炉石传说
                <i class="iconfont icon-wf_cuowushi"></i>
              </span>
              <span class="counter-item">王者荣耀
                <i class="iconfont icon-wf_cuowushi"></i>
              </span>
              <span class="counter-item">魔兽争霸
                <i class="iconfont icon-wf_cuowushi"></i>
              </span> -->
            </div>
              <div class="saveclose">
              	<div class="btn-group">
                	<a href="javascript:;" class="close" data-dismiss="modal" id="interestCancelBtn" onclick="interestCancelBtn()">取消</a>
                </div>  
                <div class="btn-group">
                  <button class="btn btn-success btn-block avatar-save"
                    type="button" id="interestBtn" onclick="interestBtn()">保存修改</button>
                </div>
              </div>      
          </div>
      </div>
    </div>
   </div>