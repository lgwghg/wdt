<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../include/common.jsp" />
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="edge"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>个人中心</title>
    <link rel="stylesheet" href="${staticPrefix }/css/jquery.dropdown.css?v=${version }" type="text/css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="${staticPrefix }/css/bootstrap-material-datetimepicker.css?v=${version }" type="text/css">
    <link rel="stylesheet" href="${staticPrefix }/css/search-result.css?v=${version }" type="text/css">
    <link rel="stylesheet" href="${staticPrefix }/css/personal-center.css?v=${version }" type="text/css">
    <link rel="stylesheet" href="${staticPrefix }/css/pc-contain.css?v=${version }" type="text/css">
    <script src="/resources/js/common/dateformat.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix }/js/jquery.dropdown.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix }/js/common/jqPaginator.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix }/js/moment.min.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix }/js/bootstrap-material-datetimepicker.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix }/js/business/mycenter/personal.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix }/js/business/mycenter/welcome.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix }/js/business/mycenter/loginAndSecurity.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix }/js/business/mycenter/helpCenter.js?v=${version }" type="text/javascript"></script>
	<%-- <script src="${staticPrefix }/js/YMDClass.js?v=${version }" type="text/javascript"></script> --%>
	<script src="${staticPrefix}/js/common/gt.js?v=${version }" type="text/javascript"></script>
	<script src="${staticPrefix}/js/common/captcha.js?v=${version }" type="text/javascript"></script>
	<script src="${staticPrefix}/js/business/mycenter/accountSet.js?v=${version }" type="text/javascript"></script>
	<script src="${staticPrefix}/js/business/mycenter/integralTask.js?v=${version }" type="text/javascript"></script>
	<script src="${staticPrefix}/js/business/mycenter/pwdEdit.js?v=${version }" type="text/javascript"></script>
	<script src="${staticPrefix}/js/business/mycenter/safeQuestion.js?v=${version }" type="text/javascript"></script>
</head>
<body>
    <!--顶部-->
	<jsp:include page="../include/header.jsp" />
    <div class="personal-center-main">
        <div class="personal-center-main-inside">
        	<%-- 左侧导航 --%>
            <jsp:include page="centerLeft.jsp" />
            <div class="personal-center-main-right">
                <div class="personal-center-main-right-inside">
                    <%-- <p class="title">欢迎</p> --%>
                    <div class="title-outside">
	                    <i class="iconfont icon-wf_liebiao iconfont-x dropdown-button" title="菜单"></i>
	                    <p class="title">欢迎</p>
	                </div>
                    <div class="personal-center-main-right-main">
                        <div class="personal-center-main-right-main-inside" id="centerMain">
                            <!--欢迎-->
                            <%-- <jsp:include page="welcome.jsp" /> --%>
                            <%-- <!--登录和安全-->
                            <jsp:include page="loginAndSecurity.jsp" />
                			<!--设备-->
		                    <jsp:include page="loginDevice.jsp" /> 
						    <!--personal-->
						    <jsp:include page="personal.jsp" />
						    <!--message-->
						    <jsp:include page="message.jsp" />
						    <!--评分-->  
						    <jsp:include page="commentGrade.jsp" />
						    <!--help-->
						    <jsp:include page="help.jsp" /> --%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--<canvas id="drawNext"></canvas>-->
</body>
<script>
    $.material.init();
</script>
<script>
    //wodotabtn
    /* $(function(){
        var flag;
        $(".bindbtn").click(function(){
            if(flag){
                $(this).addClass("unlash").removeClass("lash").html("点击解绑账号");
                flag = false;
                console.log(flag);
            }else{
                $(this).addClass("lash").removeClass("unlash").html("点击绑定账号");
                flag = true;
            }
        });
    }); */
    //personal
    $(function(){
        $(".select select").dropdown();
        $(".recteg").on("click",function(){
            $(this).addClass("active").siblings().removeClass("active");
        }).on("mouseover",function(){
            $(this).find(".cuowu").show();
        }).on("mouseout",function(){
            $(this).find(".cuowu").hide();
        }).on("click",".cuowu",function(){
            event.stopPropagation();
            $(this).parent(".recteg").remove();
        }); 
        $(".avatarwrap").hover(function(){
            $(this).find(".imgzhezhao").toggle();
        }); 
        $(".counter-item").on("click",".icon-wf_cuowushi",function(){
            $(this).parent(".counter-item").remove();
        });
    });
    //massage
        $(function(){
            $(".listone li").click(function(){
                $(this).find(".dot").css("opacity","0");
                $(this).addClass("lifoucus");
            });
            $(".pagination li").click(function(){
                $(this).find("a").addClass("choose").parent().siblings().find("a").removeClass("choose");
            });
        });
</script>
</html>
