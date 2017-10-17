<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--浏览配置-->
<div class="browse-configuration" id="container">
    <div class="browse-configuration-inside">
        <ul class="browse-configuration-content">
        	<c:if test="${not empty userSwitchList }">
            <c:forEach items="${userSwitchList }" var="uswitch" varStatus="status">
            <li>
                <span class="one">${uswitch.switchDesc }</span>
                <%-- <div class="on-off <c:if test="${uswitch.value ==1 }">open-x</c:if> <c:if test="${uswitch.value ==0 }">close-x</c:if>" switchType="${uswitch.switchType }" switchId = "${uswitch.id }" id="switch_${uswitch.switchType }">
                    <span></span>
                </div> --%>
                <div class="togglebutton">
	              <label>
	                <input type="checkbox" value="${uswitch.value}" <c:if test='${uswitch.value ==1 }'>checked</c:if> switchType="${uswitch.switchType }" switchId = "${uswitch.id }" id="switch_${uswitch.switchType }" />
	              </label>
            	</div>
            </li>
            </c:forEach>
            </c:if>
            <!-- <li>
                <span class="one">开启系统提示</span>
                <div class="on-off open-x">
                    <span></span>
                </div>
            </li> -->
            <!-- <li>
                <span class="one">开启评论提示</span>
                <div class="on-off open-x">
                    <span></span>
                </div>
            </li>
            <li>
                <span class="one">开启我的评论高亮</span>
                <div class="on-off open-x">
                    <span></span>
                </div>
            </li> -->
        </ul>
    </div>
</div>