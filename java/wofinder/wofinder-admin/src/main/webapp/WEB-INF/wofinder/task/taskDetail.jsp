<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="edge"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>${task.title}${seoConfigMap['1'].title }</title>
    <jsp:include page="../include/common.jsp" />
    <jsp:include page="../include/socket.jsp"/>
    <link rel="stylesheet" href="${staticPrefix }/css/taskdetails.css?v=${version }" type="text/css">
    <link rel="stylesheet" href="${staticPrefix }/css/incident-details.css?v=${version }" type="text/css">
    <script type="text/javascript" src="${staticPrefix }/js/business/taskDetail.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/business/taskComment.js?v=${version }"></script>
</head>
<body>
<!-- 头部 -->
<jsp:include page="../include/header.jsp"/>
<!--顶部之外-->
<input type="hidden" value="${task.id}" id="taskId">
<input type="hidden" value="${task.up.id}" id="upId">
<input type="hidden" value="${task.likeCount}" id="likeCount_${task.id}">
<input type="hidden" value="${task.likeStatus }" id="taskLikeStatus_${task.id}">
<div class="main fix">
    <div class="search-result-main video-details-main">
        <!--搜索结果-->
        <div class="search-result-main-content">
            <!--left-->
            <ul class="search-content-left">
                <li class="incident-content" id="task_li" value="${task.id }">
                    <div class="title-x">
                        <span class="one"></span>
                        <h3 class="two">
                            ${task.title}
                        </h3>
                    </div>
                    <p class="time-read-prize">
                        <span class="one">${task.createTime}</span>
                        <span class="two" id="taskLikeCount_${task.id}_title">${task.likeCount}</span>
                        <span class="three">赞</span>
                        <span class="four">${task.viewCount}</span>
                        <span class="five">阅读</span>
                    </p>
                    <div class="incident-content-details">
                        <div class="one">
                            ${task.content}
                        </div>
                        <div class="two">
                            <div class="prize">
                                <i class="iconfont icon-zan <c:if test="${task.likeStatus==1}">red</c:if>" onclick="addTaskLike('${task.id }',this)"></i>
                                <span id="taskLikeCount_${task.id}">${task.likeCount}</span>
                            </div>
                            <div class="thirdly" id="taskTagList">
                                
                            </div>
                            <div class="share">
                                <span class="one">分享：</span>
                                <div class="two">
                                    <i class="iconfont iconfont-x icon-qq" onclick="share('1','${ip}task/${task.id }','${task.title}')"></i>
	                                <i class="iconfont iconfont-x icon-weibo" onclick="share('2','${ip}task/${task.id }','${task.title}')"></i>
                                </div>
                            </div>
                            <!--评论已展开-->
	                        <div class="comment-yes" id="comment">
							    <ul class="comment-inside">
						            
							    </ul>
							</div>
                            <input type="hidden" value="${userPhoto_65 }" id="userPhoto_65"/>
							<div class="comment-fb-outside">
							    <div class="header images-x images-x-content">
							        <img id="myPhoto65" src="${userPhoto_65 }">
							    </div>
							    <div class="hf-x hide">
							        <span>回复：</span>
							        <span class="name"></span>
							    </div>
							    <input type="text" id="comment-content" class="comment-fb" placeholder="添加评论...">
							    <i class="iconfont icon-wf_huiche iconfont-x"></i>
							</div>
                        </div>
                    </div>
                </li>
            </ul>
            <!--right-->
            <div class="search-content-right video-details-right event">
                <div class="title-x">
                    <span class="one"></span>
                    <h3 class="two"> 相关事件</h3>
                    <span class="three" onclick="getRelatedTask();">换一批</span>
                </div>
                <div class="event-content-outside">
                    <ul class="event-content" id="relatedTask"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<!--底部-->
<jsp:include page="../include/footer.jsp"/>
<!--页面加载动画canvas-->
<canvas id="drawNext">测试</canvas>
</body>
<script>
    $(function () {
        drawNext();
    })
</script>
</html>