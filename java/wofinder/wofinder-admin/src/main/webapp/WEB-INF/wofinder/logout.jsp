<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="include/common.jsp" />
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="edge"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>wofinder-退出登录后</title>
    <link rel="stylesheet" href="${staticPrefix}/css/search-result.css?v=${version }" type="text/css">
    <link rel="stylesheet" href="${staticPrefix}/css/log-out.css?v=${version }" type="text/css">
</head>
<body>
<!--顶部-->
<jsp:include page="include/header.jsp" />
<!--顶部之外-->
<div class="main fix log-out-main">
    <div class="view1">
        <span class="one"></span>
        <span class="two">老铁，欢迎您来，欢迎您常来哦！</span>
    </div>
    <img src="${staticPrefix}/images/blitzcrank2.gif" class="view2">
    <div class="view3">
        <div><span>30</span>S&nbsp;后将您送达到首页</div>
        <button class="btn view3" type="button">不不不，我还要登录！</button>
    </div>
</div>
<jsp:include page="include/footer.jsp" />
<canvas id="drawNext">测试</canvas>
</body>
<script>
    $.material.init();
</script>
<script>
    $(function() {
        drawNext();
        //个人中心切换tab
        $(".grzx-content").on("click","li",function(){
            $(".grzx-content li").removeClass("checked");
            $(".grzx-x").addClass("none");
            $("." + $(this).attr("class") + "-content").removeClass("none").css("opacity","0").animate({
                "opacity":"1"
            },400);
            $(this).addClass("checked");
        })
        $(".log-out-main").on("click",".view3 button",function(){
            window.location.href = "/login";
        })
        setInterval(function(){
            var a = $(".log-out-main .view3 div span").html();
            if(a!= 1){
                $(".log-out-main .view3 div span").html(a-1);
            }else{
                $(".log-out-main .view3 div span").html(a-1);
                window.location.href = "/";
            }
        },1000)
    })
</script>
</html>
