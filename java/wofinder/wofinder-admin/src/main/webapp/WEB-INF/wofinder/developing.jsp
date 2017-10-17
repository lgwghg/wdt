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
    <title>正在开发中${seoConfigMap['1'].title }</title>
    <meta name="keywords" content="${seoConfigMap['1'].keywords }">
    <meta name="description" content="${seoConfigMap['1'].description }">
    <link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_77ahtsee99jurf6r.css?v=${version }">
    <link rel="stylesheet" href="${staticPrefix}/css/exploitation-now.css?v=${version }" type="text/css">
    <script src="${staticPrefix}/js/common/gt.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix}/js/business/developing.js?v=${version }" type="text/javascript"></script>
</head>
<body>
	
    <!--顶部-->
    <jsp:include page="include/header.jsp" />
    <!--顶部之外-->
    <div class="main fix exploitation-main">
        <p class="text-center one">功能开发中...</p>
        <p class="text-center two">我们与你一起期待</p>
        <img src="${staticPrefix}/images/8sgif.gif" class="three">
        <p class="text-center four">有什么想跟我们说的，尽管讲：</p>
        <div class="form-group label-floating">
            <label for="feedBack" class="control-label">写下要说的话</label>
            <textarea class="form-control" id="feedBack" maxlength="100"></textarea>
            <div class="number">
                <span class="first">0</span>
                <span class="second">/100</span>
            </div>
            <div class="yanzheng" id="gtcaptcha"></div>
        </div>
        <button class="btn five" onclick="" id="feedbackBtn" disabled="true">提交</button>
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
        //输入框限制长度
        $('.exploitation-main .form-group .form-control').on('input propertychange', function() {
            var a = $(this).val().length;
            if(a<=200){
                $(this).siblings(".number").find(".first").html(a);
            }
            else{
                var b = $(this).val().substr(0,200);
                $(this).val(b);
                $(this).siblings(".number").find(".first").html("200");
            }
        });
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