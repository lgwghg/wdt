<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty userSwitchList }">
<c:forEach items="${userSwitchList }" var="uswitch" varStatus="status">
<li>
    <span class="one">${uswitch.switchDesc }</span>
 	<%-- <div class="on-off <c:if test="${uswitch.value ==1 }">open-x</c:if> <c:if test="${uswitch.value ==0 }">close-x</c:if>" switchType="${uswitch.switchType }" switchId = "${uswitch.id }" id="headerswitch_${uswitch.switchType }">
        <span></span>
    </div> --%>
    <div class="togglebutton">
      <label>
        <input type="checkbox" value="${uswitch.value}" <c:if test='${uswitch.value ==1 }'>checked="true"</c:if> switchType="${uswitch.switchType }" switchId = "${uswitch.id }" id="headerswitch_${uswitch.switchType }" />
      </label>
    </div>
</li>
</c:forEach>
</c:if>