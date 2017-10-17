<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/search-result.css?v=${version }">
<script type="text/javascript" src="${staticPrefix }/js/business/header.js?v=${version }"></script>
<%-- <ul class="homepage-top search-result-top">
    <!--搜索框-->
    <li class="homepage-top-li homepage-main-search">
        <h2 class="homepage-main-search-logo-outside">
            <a href="${path }/index" class="homepage-main-search-logo"><img src="${staticPrefix }/images/w-b.png"></a>
        </h2>
        <div class="form-group label-floating">
            <input type="text" class="form-control text_input" placeholder="请输入UP主名称或视频关键词" id="wd" value="${wd}" onkeydown="searchByKey(event)" onkeyup="suByKey()">
            <div class="search-record" id="search-record"></div>
        </div>
        <div class="btn-outside">
            <div class="btn btn-primary btn-raised" onclick="search()">找一下</div>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox" id="clearCache">
            </label>
            <span class="title">更新缓存</span>
        </div>
    </li>
    <li class="homepage-top-li homepage-top-li-four dropdown">
        <!-- <i class="iconfont icon-wf_menu iconfont-x dropdown-button" title="菜单"><span></span></i> -->
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
    </li>
    <li class="homepage-top-li homepage-top-li-three dropdown">
        <div class="header iconfont-x dropdown-button">
            <i class="iconfont icon-wf_touxiang <c:if test='${not empty user and not empty user.photo_24 }'>hide</c:if>" title="个人中心"></i>
            <div class="images-x images-x-content <c:if test='${empty user or empty user.photo_24 }'>hide</c:if>">
                <img src="${user.photo_24 }" class="header-inside" id="headerUserPhoto_24">
            </div>
        </div>
        <span class="new-message">1.4K</span>
        <div class="dropdown-triangle-bd"></div>
        <div class="dropdown-triangle"></div>
        <div class="dropdown-x grzx none">
            <ul class="grzx-content">
                <li class="grzx-message checked">
                    <span class="view1">消息</span>
                    <span class="view2">14</span>
                </li>
                <li class="grzx-setting">
                    <span class="view1">我</span>
                    <span class="view2">14</span>
                </li>
            </ul>
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
                            <p class="view1"><span class="mb"></span><img src="images/message.png"><span>有8条回复</span></p>
                            <div class="view2"></div>
                        </a>
                    </strong>
                </li>
                <li>
                    <strong>
                        <a href="javascript:;">
                            <i class="iconfont icon-wf_pinglun1"></i>
                            <p class="view1"><span class="mb"></span><img src="images/message.png"><span>有8条回复</span></p>
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
                            <p class="view1"><span class="mb"></span><img src="images/message.png"><span>有10W条回复</span></p>
                            <div class="view2"></div>
                        </a>
                    </strong>
                </li>
                <li>
                    <strong>
                        <a href="javascript:;">
                            <i class="iconfont icon-wf_pinglun1"></i>
                            <p class="view1"><span class="mb"></span><img src="images/message.png"><span>有1.5K条回复</span></p>
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
                            <p class="view1"><span class="mb"></span><img src="images/message.png"><span>有999条回复</span></p>
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
            </ul>
            <div class="grzx-x grzx-setting-content">
                <div class="grzx-view1">
                    <div class="images-x images-x-content grzx-view1-header">
                        <img src="${user.getPhoto_70() }" id="headerUserPhoto_70">
                    </div>
                    <div class="grzx-view1-ms">
                        <p class="one">
                            <!-- <span class="first">账号：</span> -->
                            <span class="second" id="headerUserNick">${user.nickName }</span>
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
                        <p class="second">每日签到获得5积分</p>
                    </div>
                    <button class="btn btn-primary btn-raised" id="signButton">签到</button>
                </div>
                <ul class="grzx-view3" id="headerBrowseSet">
                    <li>
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
                    </li>
                </ul>
            </div>
        </div>
    </li>
    
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
    </li>
</ul>
 --%>

<ul class="homepage-top search-result-top">
    <!--LOGO-->
    <h2 class="homepage-main-search-logo-outside">
        <a href="/" class="homepage-main-search-logo">
            <img src="${staticPrefix }/images/w-b.png" class="one">
            <img src="${staticPrefix }/images/w-b-b.png" class="two">
        </a>
    </h2>
    <!--搜索框-->
    <li class="homepage-top-li homepage-main-search-kz" title="点击搜索">
        <i class="iconfont icon-wf_sousuo iconfont-x" title="点击搜索"></i>
    </li>
    <li class="homepage-top-li homepage-main-search">
        <div class="form-group label-floating">
            <input type="text" class="form-control text_input" placeholder="请输入UP主名称或视频关键词" id="wd" value="${wd}" onkeydown="searchByKey(event)" onkeyup="suByKey()" onfocus="showHistory()">
            <div class="search-record" id="search-record"></div>
        </div>
        <div class="btn-outside">
            <div class="btn btn-primary btn-raised" onclick="search()">找一下</div>
        </div>
    </li>
    <%-- <li class="homepage-top-li homepage-top-li-four dropdown">
        <i class="iconfont icon-wf_liebiao iconfont-x dropdown-button" title="菜单"></i>
        <div class="dropdown-triangle-bd"></div>
        <div class="dropdown-triangle"></div>
        <div class="load-schedule">
            <div class="one"></div>
        </div>
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
            <i class="iconfont icon-wf_touxiang <c:if test='${not empty user and not empty user.photo_24 }'>hide</c:if>" title="个人中心"></i>
            <div class="images-x images-x-content <c:if test='${empty user or empty user.photo_24 }'>hide</c:if>">
                <img src="${user.photo_24 }" class="header-inside" id="headerUserPhoto_24">
            </div>
        </div>
        <%-- <span class="new-message">1.4K</span> --%>
        <div class="dropdown-triangle-bd"></div>
        <div class="dropdown-triangle"></div>
        <div class="dropdown-x grzx none">
            <%-- <ul class="grzx-content">
                <li class="grzx-message checked">
                    <span class="view1">消息</span>
                    <span class="view2">14</span>
                </li>
                <li class="grzx-setting">
                    <span class="view1">我</span>
                    <span class="view2">14</span>
                </li>
            </ul>
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
                            <p class="view1"><span class="mb"></span><img src="images/message.png"><span>有8条回复</span></p>
                            <div class="view2"></div>
                        </a>
                    </strong>
                </li>
                <li>
                    <strong>
                        <a href="javascript:;">
                            <i class="iconfont icon-wf_pinglun1"></i>
                            <p class="view1"><span class="mb"></span><img src="images/message.png"><span>有8条回复</span></p>
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
                            <p class="view1"><span class="mb"></span><img src="images/message.png"><span>有10W条回复</span></p>
                            <div class="view2"></div>
                        </a>
                    </strong>
                </li>
                <li>
                    <strong>
                        <a href="javascript:;">
                            <i class="iconfont icon-wf_pinglun1"></i>
                            <p class="view1"><span class="mb"></span><img src="images/message.png"><span>有1.5K条回复</span></p>
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
                            <p class="view1"><span class="mb"></span><img src="images/message.png"><span>有999条回复</span></p>
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
            <div class="grzx-x grzx-setting-content">
                <div class="grzx-view1">
                    <div class="images-x images-x-content grzx-view1-header">
                        <img src="${user.getPhoto_70() }" id="headerUserPhoto_70">
                    </div>
                    <div class="grzx-view1-ms">
                        <p class="one">
                            <!-- <span class="first">账号：</span> -->
                            <span class="second" id="headerUserNick">${user.nickName }</span>
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
                        <p class="second">每日签到获得5积分</p>
                    </div>
                    <button class="btn btn-primary btn-raised" id="signButton">签到</button>
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
    </li>
    <%-- <li class="homepage-top-li homepage-top-li-two dropdown">
        <i class="iconfont icon-wf_lishijilu iconfont-x dropdown-button" title="历史记录"></i>
        <div class="dropdown-triangle-bd"></div>
        <div class="dropdown-triangle"></div>
        <div class="load-schedule">
            <div class="one"></div>
        </div>
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


<script>
    $.material.init();
  	$(function() {
  		//个人中心切换tab
  	    $(".grzx-content").on("click","li",function(){
  	        $(".grzx-content li").removeClass("checked");
  	        $(".grzx-x").addClass("none");
  	        $("." + $(this).attr("class") + "-content").removeClass("none").css("opacity","0").animate({
  	            "opacity":"1"
  	        },400);
  	        $(this).addClass("checked");
  	    })
  	    
  		//小尺寸时点击展开搜索
        $(".search-result-top").on("click",".homepage-main-search-kz",function(){
            if($(".homepage-top-li-three").hasClass("hide") == true){
                $(".search-result-top .homepage-main-search").css("display","");
            }
            else{
                $(".search-result-top .homepage-main-search").css({"display":"block","opacity":"0"}).animate({
                    "opacity":"1"
                },488);
            }
            $(".homepage-top-li-two,.homepage-top-li-three,.homepage-top-li-four").toggleClass("hide");
        })
    
  	    showHeaderBrowseSet();
  	});
  
</script>