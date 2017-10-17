<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--积分与任务-->
<div class="integration-task" id="container">
    <div class="integration-task-inside">
        <div class="task-x team1">
            <div class="title-a">完善信息:</div>
            <div class="number">
                <span class="over">0${userInfoCompleteNum }/</span>
                <span class="all">06</span>
            </div>
            <c:if test="${not empty completedUserIntegral }">
            <ul class="graph">
                <li class="ywc">
                    <span class="top">账号信息</span>
                    <div class="bottom">
                        <div class="line"></div>
                        <div class="roundness"></div>
                    </div>
                </li>
                <li class="ywc">
                    <span class="top">设置昵称</span>
                    <div class="bottom">
                        <div class="line"></div>
                        <div class="roundness"></div>
                    </div>
                </li>
                <li class="ywc">
                    <span class="top">设置性别</span>
                    <div class="bottom">
                        <div class="line"></div>
                        <div class="roundness"></div>
                    </div>
                </li>
                <li class="ywc">
                    <span class="top">设置生日</span>
                    <div class="bottom">
                        <div class="line"></div>
                        <div class="roundness"></div>
                    </div>
                </li>
                <li class="ywc">
                    <span class="top">添加兴趣</span>
                    <div class="bottom">
                        <div class="line"></div>
                        <div class="roundness"></div>
                    </div>
                </li>
                <li class="ywc">
                    <span class="top">上传头像</span>
                    <div class="bottom">
                        <div class="line"></div>
                        <div class="roundness"></div>
                    </div>
                </li>
                <li class="last ywc">
                    <div class="bottom">
                        <div class="line"></div>
                    </div>
                </li>
            </ul>
            <div class="button ywc">
                <button class="btn btn-primary btn-raised" disabled>已完成</button>
                <span style="display:block;">+${completedUserIntegral.integral }分</span>
            </div>
           	</c:if>
           	<%-- 未完成 --%>
           	<c:if test="${empty completedUserIntegral }">
           	<ul class="graph">
           	<c:if test="${not empty completeUser }">
           		<c:forEach items="${completeUser }" var="complete">
           		<li class="ywc">
                    <span class="top">${complete }</span>
                    <div class="bottom">
                        <div class="line"></div>
                        <div class="roundness"></div>
                    </div>
                </li>
           		</c:forEach>
           	</c:if>
           	
           	<c:if test="${not empty  uncompleteUser }">
           		<c:forEach items="${uncompleteUser }" var="uncomplete">
           		<li>
                    <span class="top">${uncomplete }</span>
                    <div class="bottom">
                        <div class="line"></div>
                        <div class="roundness"></div>
                    </div>
                </li>
           		</c:forEach>
           	</c:if>
	           	<li class="last <c:if test='${userInfoCompleteNum eq 6 }'>ywc</c:if>">
	                <div class="bottom">
	                    <div class="line"></div>
	                </div>
	            </li>
           	</ul>
           	<c:if test="${userInfoCompleteNum lt 6 }">
           	<div class="button wwc">
                <button class="btn btn-primary btn-raised" disabled>未完成</button>
                <span>+6分</span>
            </div>
           	</c:if>
           	<c:if test="${userInfoCompleteNum eq 6 }">
           	<div class="button wc">
                <button class="btn btn-primary btn-raised" id="userInfoComplete" onclick="completeTask('userInfoComplete',0)">完成</button>
                <span>+6分</span>
            </div>
           	</c:if>
           	</c:if>
           	
            
        </div>
        <div class="task-x team2">
            <div class="title-a">每日十评:</div>
            <div class="number">
                <span class="over"><c:if test="${gradeNum lt 10 }">0${gradeNum }</c:if><c:if test="${gradeNum ge 10 }">10</c:if>/</span>
                <span class="all">10</span>
            </div>
            <div class="line1">
            	<c:if test="${gradeNum ge 10 }">
                <div class="line2" style="width: 100%"></div>
                </c:if>
                <c:if test="${gradeNum lt 10 }">
                <div class="line2" style="width: ${(gradeNum/10)*100 }%"></div>
                </c:if>
            </div>
            <c:if test="${not empty gradeIntegral }">
            <div class="button ywc">
                <button class="btn btn-primary btn-raised" disabled>已完成</button>
                <span style="display:block;">+${gradeIntegral.integral }分</span>
            </div>
            </c:if>
            <c:if test="${empty gradeIntegral }">
            	<c:if test="${gradeNum ge 10 }">
            	<div class="button wc">
	                <button class="btn btn-primary btn-raised" id="gradeComplete" onclick="completeTask('gradeComplete',1)">完成</button>
	                <span>+6分</span>
	            </div>
            	</c:if>
            	<c:if test="${gradeNum lt 10 }">
            	<div class="button wwc">
	                <button class="btn btn-primary btn-raised" disabled>未完成</button>
	                <span>+6分</span>
	            </div>
            	</c:if>
            </c:if>
        </div>
        <div class="task-x team3">
            <div class="title-a">每日十贴:</div>
            <div class="number">
                <span class="over"><c:if test="${commentNum lt 10 }">0${commentNum }</c:if><c:if test="${commentNum ge 10 }">10</c:if>/</span>
                <span class="all">10</span>
            </div>
            <div class="line1">
            	<c:if test="${commentNum ge 10 }">
            	<div class="line2" style="width: 100%"></div>
				</c:if>
				<c:if test="${commentNum lt 10 }">
                <div class="line2" style="width: ${(commentNum/10)*100 }%"></div>
				</c:if>
            </div>
            <c:if test="${not empty commentIntegral }">
            <div class="button ywc">
                <button class="btn btn-primary btn-raised" disabled>已完成</button>
                <span style="display:block;">+${commentIntegral.integral }分</span>
            </div>
            </c:if>
            <c:if test="${empty commentIntegral }">
            	<c:if test="${commentNum ge 10 }">
            	<div class="button wc">
	                <button class="btn btn-primary btn-raised" id="commentComplete" onclick="completeTask('commentComplete',2)">完成</button>
	                <span>+10分</span>
	            </div>
            	</c:if>
            	<c:if test="${commentNum lt 10 }">
            	<div class="button wwc">
	                <button class="btn btn-primary btn-raised" disabled>未完成</button>
	                <span></span>
	            </div>
            	</c:if>
            </c:if>
        </div>
    </div>
</div>