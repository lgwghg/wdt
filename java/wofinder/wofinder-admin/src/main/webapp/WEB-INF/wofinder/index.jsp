<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="edge"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>${seoConfigMap['2'].title }</title>
    <meta name="keywords" content="${seoConfigMap['2'].keywords }">
    <meta name="description" content="${seoConfigMap['2'].description }">
    <jsp:include page="include/common.jsp"/>
    <jsp:include page="include/socket.jsp"/>
    <link rel="stylesheet" href="${staticPrefix }/css/swiper.min.css?v=${version }" type="text/css">
    <script src="${staticPrefix }/js/swiper.min.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix }/js/business/index.js?v=${version }" type="text/javascript"></script>
    <%-- 统计分析 --%>
	<jsp:include page="include/analytics.jsp"/>
</head>
<body class="homepage-body">
    <div class="load-stop">
    	<!--背景视频-->
        <div class="homepage-bg">
            <!--<video loop autoplay id="homepage-bg-video">
                <source src="images/bg.mp4" type="video/mp4" />
            </video>-->
            <div class="homepage-bg-outside">
                <img src="${staticPrefix }/images/bg.jpg" id="homepage-bg-video">
                <div></div>
            </div>
            <!--底部-->
            <div class="homepage-bottom">© CopyRight 2012-2017, WODOTA.COM, Inc.All Rights Reserved.  鄂ICP备16018142号-2</div>
        </div>
        <!--顶部-->
        <ul class="homepage-top">
            <%-- <li class="homepage-top-li homepage-top-li-one">
                <i class="iconfont icon-wf_shouye iconfont-x" title="首页"><span></span></i>
            </li>
            <li class="homepage-top-li homepage-top-li-four dropdown">
                <i class="iconfont icon-wf_menu iconfont-x dropdown-button" title="菜单"><span></span></i>
                <div class="dropdown-triangle-bd"></div>
                <div class="dropdown-triangle"></div>
                <ul class="dropdown-x menu-content none">
                    <li>
                        <i class="iconfont icon-wf_tijiaojilu"></i>
                        <p>提交记录</p>
                    </li>
                    <li class="menu-content-new">
                        <i class="iconfont icon-wf_bianjicitiao"></i>
                        <p>编辑词条</p>
                    </li>
                    <li>
                        <i class="iconfont icon-wf_paihang"></i>
                        <p>排行榜</p>
                    </li>
                </ul>
            </li> --%>
            
            <li class="homepage-top-li homepage-top-li-three dropdown">
                <div class="header iconfont-x dropdown-button">
                    <i class="iconfont icon-wf_touxiang <c:if test='${not empty user and not empty user.photo }'>hide</c:if>" title="个人中心"></i>
                    <div class="images-x images-x-content <c:if test='${empty user or empty user.photo }'>hide</c:if>">
                        <img src="${user.photo_24 }" class="header-inside">
                    </div>
                </div>
                <c:if test='${not empty user and not empty user.id}'>
                <%-- <span class="new-message">1.4K</span> --%>
                <div class="dropdown-triangle-bd"></div>
                <div class="dropdown-triangle"></div>
                <div class="dropdown-x grzx none">
                    <!-- <ul class="grzx-content">
                        <li class="grzx-message checked">
                            <span class="view1">消息</span>
                            <span class="view2">14</span>
                        </li>
                        <li class="grzx-setting">
                            <span class="view1">我</span>
                            <span class="view2"></span>
                        </li>
                    </ul> -->
                    <%-- 
                    <ul class="grzx-x grzx-message-content none">
                       <li class="no-see">
                           <strong>
                               <a href="javascript:;">
                                   <i class="iconfont icon-wf_shezhi-copy"></i>
                                   <p class="view1">你成功完成了个人资料你成功完成了个人资料</p>
                                   <div class="view2"></div>
                               </a>
                           </strong>
                       </li>
                        <li class="no-see">
                            <strong>
                                <a href="javascript:;">
                                    <i class="iconfont icon-wf_shezhi-copy"></i>
                                    <p class="view1">你成功完成了个人资料你成功完成了个人资料</p>
                                    <div class="view2"></div>
                                </a>
                            </strong>
                        </li>
                        <li class="no-see">
                            <strong>
                                <a href="javascript:;">
                                    <i class="iconfont icon-wf_shezhi-copy"></i>
                                    <p class="view1">你成功完成了个人资料你成功完成了个人资料</p>
                                    <div class="view2"></div>
                                </a>
                            </strong>
                        </li>
                        <li class="no-see">
                            <strong>
                                <a href="javascript:;">
                                    <i class="iconfont icon-wf_pinglun1"></i>
                                    <p class="view1"><span class="mb"></span><img src="${staticPrefix }/images/message.png"><span>有8条回复</span></p>
                                    <div class="view2"></div>
                                </a>
                            </strong>
                        </li>
                        <li>
                            <strong>
                                <a href="javascript:;">
                                    <i class="iconfont icon-wf_pinglun1"></i>
                                    <p class="view1"><span class="mb"></span><img src="${staticPrefix }/images/message.png"><span>有8条回复</span></p>
                                    <div class="view2"></div>
                                </a>
                            </strong>
                        </li>
                        <li>
                            <strong>
                                <a href="javascript:;">
                                    <i class="iconfont icon-wf_shezhi-copy"></i>
                                    <p class="view1">你成功完成了个人资料你成功完成了个人资料</p>
                                    <div class="view2"></div>
                                </a>
                            </strong>
                        </li>
                        <li>
                            <strong>
                                <a href="javascript:;">
                                    <i class="iconfont icon-wf_pinglun1"></i>
                                    <p class="view1"><span class="mb"></span><img src="${staticPrefix }/images/message.png"><span>有10W条回复</span></p>
                                    <div class="view2"></div>
                                </a>
                            </strong>
                        </li>
                        <li>
                            <strong>
                                <a href="javascript:;">
                                    <i class="iconfont icon-wf_pinglun1"></i>
                                    <p class="view1"><span class="mb"></span><img src="${staticPrefix }/images/message.png"><span>有1.5K条回复</span></p>
                                    <div class="view2"></div>
                                </a>
                            </strong>
                        </li>
                        <li>
                            <strong>
                                <a href="javascript:;">
                                    <i class="iconfont icon-wf_shezhi-copy"></i>
                                    <p class="view1">你成功完成了个人资料你成功完成了个人资料</p>
                                    <div class="view2"></div>
                                </a>
                            </strong>
                        </li>
                        <li>
                            <strong>
                                <a href="javascript:;">
                                    <i class="iconfont icon-wf_pinglun1"></i>
                                    <p class="view1"><span class="mb"></span><img src="${staticPrefix }/images/message.png"><span>有999条回复</span></p>
                                    <div class="view2"></div>
                                </a>
                            </strong>
                        </li>
                        <p class="load-more">
                            <strong>
                                <a href="javascript:;">
                                    更多
                                </a>
                            </strong>
                        </p>
                    </ul> --%>
                    <div class="grzx-x grzx-setting-content ">
                        <div class="grzx-view1">
                            <div class="images-x images-x-content grzx-view1-header">
                                <img src="${user.getPhoto_70() }">
                            </div>
                            <div class="grzx-view1-ms">
                                <p class="one">
                                	<span class="second">${user.nickName }</span>
                                    <a class="iconfont icon-wf_tuichu" href="/logout"></a>
                                    <a class="iconfont icon-wf_shezhi-copy" href="/my"></a>
                                </p>
                                <p class="two">
                                    <i class="iconfont icon-wf_xunzhang"></i>
                                    <span class="first">Lv.1</span>
                                    <span class="thirdly">220</span>
                                    <span class="second">110/</span>
                                </p>
                                <p class="three">
                                    <span></span>
                                </p>
                            </div>
                        </div>
                        <div class="grzx-view2">
                            <div class="one">
                                <p class="first">
                                    <i class="iconfont icon-wf_qiandao"></i>
                                    <span class="data" id="signCalender">12</span>
                                    <span class="title" id="signIntegral">签到</span>
                                </p>
                                <p class="second">每日签到获得5经验值</p>
                            </div>
                            <button class="btn btn-primary btn-raised" type="button" id="signButton">签到</button>
                        </div>
                        <ul class="grzx-view3" id="headerBrowseSet">
                            <%-- <li>
                                <span class="one">开启竞价广告</span>
                                <div class="on-off open-x">
                                    <span></span>
                                </div>
                            </li>
                            <li>
                                <span class="one">开启系统提示</span>
                                <div class="on-off close-x">
                                    <span></span>
                                </div>
                            </li>
                            <li>
                                <span class="one">开启信息弹幕</span>
                                <div class="on-off close-x">
                                    <span></span>
                                </div>
                            </li>
                            <li>
                                <span class="one">开启评论提示</span>
                                <div class="on-off close-x">
                                    <span></span>
                                </div>
                            </li>
                            <li>
                                <span class="one">开启我的评论高亮</span>
                                <div class="on-off close-x">
                                    <span></span>
                                </div>
                            </li> --%>
                        </ul>
                    </div>
                </div>
            	</c:if>
            </li>
            <%-- 
            <li class="homepage-top-li homepage-top-li-two dropdown">
                <i class="iconfont icon-wf_lishijilu iconfont-x dropdown-button" title="历史记录"><span></span></i>
                <div class="dropdown-triangle-bd"></div>
                <div class="dropdown-triangle"></div>
                <div class="dropdown-x history-content none">
                    <ul class="history-main has-history">
                        <li>
                            <i class="iconfont icon-wf_bofang1"></i>
                            <strong class="title"><a href="javascript:;">梁博个人干起来了...</a></strong>
                            <span class="time">1分钟前</span>
                        </li>
                        <li>
                            <i class="iconfont icon-wf_touxiang"></i>
                            <strong class="title"><a href="javascript:;">鱼嘴滑舌39集</a></strong>
                            <span class="time">10分钟前</span>
                        </li>
                        <li>
                            <i class="iconfont icon-wf_bofang1"></i>
                            <strong class="title"><a href="javascript:;">只是一个视频标题...</a></strong>
                            <span class="time">20分钟前</span>
                        </li>
                        <li>
                            <i class="iconfont icon-wf_bofang1"></i>
                            <strong class="title"><a href="javascript:;">梁博个人干起来了...</a></strong>
                            <span class="time">30分钟前</span>
                        </li>
                        <li>
                            <i class="iconfont icon-wf_bofang1"></i>
                            <strong class="title"><a href="javascript:;">鱼嘴滑舌39集</a></strong>
                            <span class="time">40分钟前</span>
                        </li>
                        <li>
                            <i class="iconfont icon-wf_bofang1"></i>
                            <strong class="title"><a href="javascript:;">只是一个视频标题...</a></strong>
                            <span class="time">50分钟前</span>
                        </li>
                        <li>
                            <i class="iconfont icon-wf_bofang1"></i>
                            <strong class="title"><a href="javascript:;">梁博个人干起来了...</a></strong>
                            <span class="time">1小时前</span>
                        </li>
                        <li>
                            <i class="iconfont icon-wf_touxiang"></i>
                            <strong class="title"><a href="javascript:;">鱼嘴滑舌39集</a></strong>
                            <span class="time">2小时前</span>
                        </li>
                    </ul>
                    <p class="delete-history">
                        <span class="delete-history-btn">
                            <i class="iconfont icon-lajixiang"></i>
                            <span>清除记录</span>
                        </span>
                    </p>
                </div>
            </li> --%>
        </ul>
        <!--顶部之外部分-->
        <div class="homepage-main">
            <!--搜索框-->
            <div class="homepage-main-search">
                <h1 class="homepage-main-search-logo-outside">
                    <img src="${staticPrefix }/images/w-w.png" class="homepage-main-search-logo">
                </h1>
                <div class="form-group label-floating">
                    <label class="control-label">请输入UP主名称或视频关键词</label>
                    <input type="text" class="form-control text_input" id="wd" onkeydown="searchByKey(event)" onkeyup="suByKey()">
                    <div class="search-record" id="search-record"></div>
                </div>
                <div class="btn btn-primary btn-raised" onclick="search()">找一下</div>
                <%-- <div class="checkbox">
                    <label>
                        <input type="checkbox" id="clearCache">
                    </label>
                    <span class="title">更新缓存</span>
                </div> --%>
            </div>
            <!--弹幕模块-->
            <div class="homepage-main-dm" id="homepage-main-dm">
            
            </div>
            <!--推荐视频模块-->
            <div class="recommend-video" id="recommend-video">
                <div class="swiper-container swiper-no-swiping">
                    <div class="swiper-wrapper">
                        <div class="swiper-slide">
                            <div class="recommend-video-details yjz-recommend-video-details">
                                <img src="${staticPrefix }/images/u18.gif">
                            </div>
                            <div class="recommend-video-details yjz-recommend-video-details">
                                <img src="${staticPrefix }/images/u18.gif">
                            </div>
                            <div class="recommend-video-details yjz-recommend-video-details">
                                <img src="${staticPrefix }/images/u18.gif">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">
    //material
    $.material.init();
</script>
<script type="text/javascript">
    $(function() {
    	showHeaderBrowseSet();
    	$(".images-x-content").append("<div class='images-x-hover'></div>");
        
        //预加载
        setTimeout(function(){
            $(".load-start").addClass("hide");
        },1000)
        //清除历史记录
        $(".delete-history-btn").on("click",function(){
            $(".homepage-top-li .history-content .history-main").html("暂时没有历史记录").removeClass("has-history").addClass("no-history");
            $(".homepage-top-li .history-content .has-history li").remove();
        })
        //个人中心消除新消息
        $(".grzx-message-content li").on("click",function(){
            $(this).removeClass("no-see");
        })
        //个人中心切换tab
        $(".grzx-content").on("click","li",function(){
            $(".grzx-content li").removeClass("checked");
            $(".grzx-x").addClass("none");
            $("." + $(this).attr("class") + "-content").removeClass("none").css("opacity","0").animate({
                "opacity":"1"
            },400);
            $(this).addClass("checked");
        })
        //推荐视频列表鼠标经过效果
        var timer2;
        $(".recommend-video-details").mouseenter(function(){
            var a = $(this).find(".view1 div");
            timer2 = setTimeout(function(){
                a.animate({"opacity":"0"},99);
            },99)
        }).mouseleave(function(){
            clearTimeout(timer2);
            $(this).find(".view1 div").animate({"opacity":"0.1"},99);
        })
		//搜索框获取焦点变化背景
        $(".form-group.label-floating input").focus(function() {
        	showHistory();
            $(".homepage-bg .homepage-bg-outside div").animate({"opacity":"0.9"},666);
        }).blur(function(){
            $(".homepage-bg .homepage-bg-outside div").animate({"opacity":"0.8"},666);
        })
    });
    //背景视频响应式
    var v = document.getElementById("homepage-bg-video");
    window.onload = function () {
        var w=window.innerWidth;
        var h=window.innerHeight;
        var vw = v.width;
        var vh = v.height;
        if(h>973){
            if(w/h > vw/vh){
                v.style.height = "auto";
                v.style.width = "100%";
            }
            else{
                v.style.height = "100%";
                v.style.width = "auto";
            }
        }else{
            if(w/973 > vw/vh){
                v.style.height = "auto";
                v.style.width = "100%";
            }
            else{
                v.style.height = "100%";
                v.style.width = "auto";
            }
        }
    };
    window.onresize = function(){
        var w=window.innerWidth;
        var h=window.innerHeight;
        var vw = v.width;
        var vh = v.height;
        if(h>973){
            if(w/h > vw/vh){
                v.style.height = "auto";
                v.style.width = "100%";
            }
            else{
                v.style.height = "100%";
                v.style.width = "auto";
            }
        }else{
            if(w/973 > vw/vh){
                v.style.height = "auto";
                v.style.width = "100%";
            }
            else{
                v.style.height = "100%";
                v.style.width = "auto";
            }
        }
    }
</script>
</html>
