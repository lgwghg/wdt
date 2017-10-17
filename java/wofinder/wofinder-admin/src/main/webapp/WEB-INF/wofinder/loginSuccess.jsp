<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="edge"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>wofinder-登录成功</title>
    <jsp:include page="include/common.jsp"/>
    <link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_skrm5y3cugn6zuxr.css">
    <link rel="stylesheet" href="${staticPrefix }/css/log-out.css" type="text/css">
</head>
<body>
<!--顶部-->
<jsp:include page="include/header.jsp" />
<!--顶部之外-->
<div class="main fix log-out-main">
    <div class="view1">
        <span class="one"></span>
        <span class="two"><(｀^´)>你又来啦！</span>
    </div>
    <img src="${staticPrefix }/images/dlcgh.png" class="view2">
    <div class="view3">
        <div><span>10</span>S后将您送达到首页</div>
        <button class="btn view3">不等，直接去上一页！</button>
    </div>
</div>
<input type="hidden" id="returnUrl" value="${returnUrl }"/>
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
        	var returnUrl = $("#returnUrl").val();
        	if (returnUrl == null || returnUrl == '') {
        		returnUrl = "/index";
        	}
            window.location.href = returnUrl;
        })
        setInterval(function(){
            var a = $(".log-out-main .view3 div span").html();
            if(a!= 1){
                $(".log-out-main .view3 div span").html(a-1);
            }else{
                $(".log-out-main .view3 div span").html(a-1);
                var returnUrl = $("#returnUrl").val();
            	if (returnUrl == null || returnUrl == '') {
            		returnUrl = "/index";
            	}
                window.location.href = returnUrl;
            }
        },1000)
        //小尺寸时点击展开搜索
        $(".search-result-top").on("click",".homepage-main-search-kz",function(){
            if($(".homepage-top-li-two").hasClass("hide") == true){
                $(".search-result-top .homepage-main-search").css("display","");
            }
            else{
                $(".search-result-top .homepage-main-search").css({"display":"block","opacity":"0"}).animate({
                    "opacity":"1"
                },488);
            }
            $(".homepage-top-li-two,.homepage-top-li-three,.homepage-top-li-four").toggleClass("hide");
        })
    })
</script>
</html>