<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../include/common.jsp" />
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="edge"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<title>taskdetails${seoConfigMap['1'].title }</title>
	<meta name="keywords" content="${seoConfigMap['1'].keywords }">
    <meta name="description" content="${seoConfigMap['1'].description }">
    <link rel="stylesheet" href="${staticPrefix}/css/swiper.min.css?v=${version }" type="text/css">
    <link rel="stylesheet" href="${staticPrefix}/css/taskdetails.css?v=${version }" type="text/css">
    <script src="${staticPrefix}/js/swiper.min.js?v=${version }" type="text/javascript"></script>
    <script src="${staticPrefix}/js/business/upDetail.js?v=${version }" type="text/javascript"></script>
</head>
<body>
	<!--顶部-->
	<jsp:include page="../include/header.jsp" />
	<input type="hidden" value="${id }" id="upId"/>
	<input type="hidden" value="${upVo.name }" id="title"/>
	<div class="main fix">
		<div class="loadingwrap">
        <p class="search-result-main-number">WoDotA为您找到相关结果约 100,000,000个</p>
            <div class="loading">
                <div class="loadingone">
                    <div class="lefttop"></div>
                    <div class="righttop"></div>
                    <div class="titlewrapF">
                        <span></span>
                        <span></span>
                        <span></span>
                    </div>
                    <div class="content">
                        <div class="contentL"></div>
                        <div class="contentR">
                            <div></div>
                            <div></div>
                            <span></span><span></span>
                        </div>
                    </div>
                </div>
                <div class="loadingtwo">
                    <div class="righttop"></div>
                    <div class="titlewrap">
                        <span></span><span></span>
                    </div>
                    <div class="content">
                        <div class="content-item">
                            <div class="top"></div>
                            <div class="bottom"></div>
                        </div>
                        <div class="content-item">
                            <div class="top"></div>
                            <div class="bottom"></div>
                        </div>
                        <div class="content-item">
                            <div class="top"></div>
                            <div class="bottom"></div>
                        </div>
                        <div class="content-item">
                            <div class="top"></div>
                            <div class="bottom"></div>
                        </div>
                        <div class="content-item">
                            <div class="top"></div>
                            <div class="bottom"></div>
                        </div>
                        <div class="content-item">
                            <div class="top"></div>
                            <div class="bottom"></div>
                        </div>
                        <div class="content-item">
                            <div class="top"></div>
                            <div class="bottom"></div>
                        </div>
                    </div>
                </div>
                <div class="loadingthree">
                    <div class="righttop"></div>
                    <div class="titlewrap">
                        <span></span><span></span>
                    </div>
                    <div class="content">
                        <div></div>
                        <div></div>
                    </div>
                </div>
                <div class="loadingfour">
                    <div class="righttop"></div>
                    <div class="titlewrap">
                        <span></span><span></span>
                    </div>
                    <div class="content">
                        <div class="contentL">
                            <div></div>
                            <div></div>
                        </div>
                        <div class="contentR"></div>
                    </div>
                </div>
                <div class="loadingfive">
                    <div class="righttop"></div>
                    <div class="titlewrap">
                        <span></span><span></span>
                    </div>
                    <div class="content">
                        <div></div>
                        <div></div>
                    </div>
                </div>
            </div>
            <div class="loadingside">
                <div class="loadingsix">
                    <div class="righttop"></div>
                    <div class="titlewrap">
                        <span></span><span></span>
                    </div>
                    <div class="content">
                        <div class="content-item">
                            <div class="top"></div>
                            <div class="bottom"></div>
                        </div>
                        <div class="content-item">
                            <div class="top"></div>
                            <div class="bottom"></div>
                        </div>
                        <div class="content-item">
                            <div class="top"></div>
                            <div class="bottom"></div>
                        </div>
                        <div class="content-item">
                            <div class="top"></div>
                            <div class="bottom"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
		<div class="wrap">
		<p class="search-result-main-number">WoDotA为您找到相关结果约 100,000,000个</p>
		<div class="substance">
		<div class="gamestar">
				<i class="iconfont icon-wf_renwutouxiang"></i>
				<!-- <a href="#" class="iconfont icon-wf_bianji"></a> -->
				<div class="scorewrap">
					<h1>${upVo.name }</h1>
					<%-- <div class="pingfen">
						<span class="hotstar"><i class="iconfont icon-wf_pingfen"></i>综合好评 : ${upVo.popularityIndex }分</span><span class="wolist"><i class="iconfont icon-wf_renqizhishushi"></i>WO排名 : ${upVo.rank }</span>
					</div> --%>
				</div>
				<div class="firstwrap">
					<c:if test="${not empty upVo.mainPhoto }">
						<div class="avatar" data-toggle="modal" data-target="#myModal" >
							<div class="imgwrap">
								<img src="${upVo.mainPhoto }" alt="" width="100%" height="100%">
							</div>
						</div>
						
						<div id="upPhotoDiv" class="hide">
							<div class="swiper-container gallery-left">
						        <div class="swiper-wrapper">
						        	<c:forEach items="${upVo.upPhotoList }" var="upPhoto" varStatus="index">
							            <div class="swiper-slide"><img src="${upPhoto.photo }" alt="" ></div>
						        	</c:forEach>
						            <%-- <div class="swiper-slide"><img src="${staticPrefix}/images/about2.png" alt=""></div>
						            <div class="swiper-slide"><img src="${staticPrefix}/images/about3.png" alt=""></div>
						            <div class="swiper-slide"><img src="${staticPrefix}/images/about4.png" alt=""></div>
						            <div class="swiper-slide"><img src="${staticPrefix}/images/fm-one.png" alt=""></div>
						            <div class="swiper-slide"><img src="${staticPrefix}/images/lbw3.png" alt=""></div> --%>
						        </div>
						    </div>
						    <div class="swiper-button-next"><i class="iconfont icon-wf_you_qiehuan"></i></div>
						    <div class="swiper-button-prev"><i class="iconfont icon-wf_zuo_qiehuan"></i></div>
						    <div class="haha">
							    <div class="swiper-container gallery-thumbs">
							        <div class="swiper-wrapper">
							        	<c:forEach items="${upVo.upPhotoList }" var="upPhoto">
							            <div class="swiper-slide"><img src="${upPhoto.photo }" alt="" width="100%" height="100%"></div>
							        	</c:forEach>
							            <%-- <div class="swiper-slide"><img src="${staticPrefix}/images/about2.png" alt="" width="100%" height="100%"></div>
							            <div class="swiper-slide"><img src="${staticPrefix}/images/about3.png" alt="" width="100%" height="100%"></div>
							            <div class="swiper-slide"><img src="${staticPrefix}/images/about4.png" alt="" width="100%" height="100%"></div>
							            <div class="swiper-slide"><img src="${staticPrefix}/images/fm-one.png" alt="" width="100%" height="100%"></div>
							            <div class="swiper-slide"><img src="${staticPrefix}/images/lbw3.png" alt="" width="100%" height="100%"></div> --%>
							        </div>
						    	</div>
						    	<div class="button-bottom"><i class="iconfont icon-wf_yincangxianshi1"></i></div>
								<div class="button-top"><i class="iconfont icon-wf_yincangxianshi"></i></div>
						 	</div>
						</div>
					</c:if>
					<div class="description">
						<div class="textwrap">
							<%-- <p>卢本伟 ，ID：Wh1t3zZ，前皇族电子竞技俱乐部英雄联盟分部中单，在随即获得S3全球决赛亚军后退役。曾获2011TGA成都区冠军、2011TGA总决赛冠军、2011WCG中国区冠军、2013S3全球总决赛中国区冠军、2013S3全球总决赛亚军等荣誉。</p>
							<p>现经常出没于TGA和七煌担任英雄联盟赛事解说，平时也会在斗鱼进行游戏直播，关注度高达百万。美服S1 RANK前10(最好成绩排名第6)，是进入美服天梯排名前10的第一个中国人。善用刺客型中单，同时是国服第一小鱼人。在美服及国服都享有很高的知名度，经常在香港LOL论坛发布心得技术帖，个人能力极强。</p>
							<p>2015年1月8日，white与草莓，卷毛，微笑，lucky在斗鱼TV进行五排，翻开了他在斗鱼TV做直播的新篇章。</p>
							<p>2016年8月11日，卢本伟在新浪微博上宣布收购IMAY战队。</p>
							<p>2016年12月，卢本伟在直播中声明已退出IM战队的股份。</p> --%>
							${upVo.introduction }
						</div>
						<div class="pag hide">
							<ul id="upTag"><%-- 人物标签信息 --%>
								<!-- <li><i class="iconfont icon-wf_biaoqiantou"></i><span>职业选手</span></li>
								<li><i class="iconfont icon-wf_biaoqiantou"></i><span>主播</span></li> -->
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="interrelatestar hide" id="upRelationDiv">
				<!-- <a href="#" class="iconfont icon-wf_bianji"></a> -->
				<div class="title">
					<h3>相关人物</h3>
				</div>
				<div class="content" id="upRelationContent"><%-- 相关人物信息 --%>
				</div>
			</div>
			<div id="upSecondLevelDIV"><%-- 人物二级信息 --%>
			</div>
		</div>
		<div class="side">
			<div class="interrelatevideo">
				<%-- <a href="#" class="iconfont icon-wf_bianji"></a> --%>
				<div class="title">
					<h3>相关视频</h3>
				</div>
				<div class="videos">
				</div>
				<div class="more hide" id="videoMore" onClick="loadRelationVideoList()">加载更多</div>
				<div class="more hide" id="videoNoMore">没有更多数据</div>
			</div>
		</div>
		</div>
	</div>
	<jsp:include page="../include/footer.jsp" />
	<!--model-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: auto; position: static;  margin-left: 0; margin-top: 0;">
            <div class="modal-header">
                <div class="close" data-dismiss="modal" aria-hidden="true"><i class="iconfont icon-wf_cuowu"></i></div>
            </div>
            <div class="modal-body">
				 <%-- <div class="swiper-container gallery-left">
			        <div class="swiper-wrapper">
			            <div class="swiper-slide"><img src="${staticPrefix}/images/about1.png" alt=""></div>
			            <div class="swiper-slide"><img src="${staticPrefix}/images/about2.png" alt=""></div>
			            <div class="swiper-slide"><img src="${staticPrefix}/images/about3.png" alt=""></div>
			            <div class="swiper-slide"><img src="${staticPrefix}/images/about4.png" alt=""></div>
			            <div class="swiper-slide"><img src="${staticPrefix}/images/fm-one.png" alt=""></div>
			            <div class="swiper-slide"><img src="${staticPrefix}/images/lbw3.png" alt=""></div>
			        </div>
			    </div>
			    <div class="swiper-button-next"><i class="iconfont icon-wf_you_qiehuan"></i></div>
			    <div class="swiper-button-prev"><i class="iconfont icon-wf_zuo_qiehuan"></i></div>
			    <div class="haha">
				    <div class="swiper-container gallery-thumbs">
				        <div class="swiper-wrapper">
				            <div class="swiper-slide"><img src="${staticPrefix}/images/about1.png" alt="" width="100%" height="100%"></div>
				            <div class="swiper-slide"><img src="${staticPrefix}/images/about2.png" alt="" width="100%" height="100%"></div>
				            <div class="swiper-slide"><img src="${staticPrefix}/images/about3.png" alt="" width="100%" height="100%"></div>
				            <div class="swiper-slide"><img src="${staticPrefix}/images/about4.png" alt="" width="100%" height="100%"></div>
				            <div class="swiper-slide"><img src="${staticPrefix}/images/fm-one.png" alt="" width="100%" height="100%"></div>
				            <div class="swiper-slide"><img src="${staticPrefix}/images/lbw3.png" alt="" width="100%" height="100%"></div>
				        </div>
			    	</div>
			    	<div class="button-bottom"><i class="iconfont icon-wf_yincangxianshi1"></i></div>
					<div class="button-top"><i class="iconfont icon-wf_yincangxianshi"></i></div>
			 	</div> --%>
            </div>
            
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
<script>
    $.material.init();
        //个人中心切换tab
    $(".grzx-content").on("click","li",function(){
        $(".grzx-content li").removeClass("checked");
        $(".grzx-x").addClass("none");
        $("." + $(this).attr("class") + "-content").removeClass("none").css("opacity","0").animate({
            "opacity":"1"
        },400);
        $(this).addClass("checked");
    });
    window.onload = function(){
        var t = setTimeout(function(){
            $(".loadingwrap").hide();
            $(".wrap").show();
        },500)
    }
    function swiper(){
	    var galleryLeft = new Swiper('.gallery-left', {
	        nextButton: '.swiper-button-next',
	        prevButton: '.swiper-button-prev',
	        slidesPerView: 1,
	        paginationClickable: true,
	        spaceBetween: 10,
	        mousewheelControl: true
	    });
	    var galleryThumbs = new Swiper('.gallery-thumbs', {
	     	nextButton: '.button-bottom',
	        prevButton: '.button-top',
	        direction: 'vertical',
	        spaceBetween: 10,
	        slidesPerView: 'auto',
	        centeredSlides: true,
	        touchRatio: 0.2,
	        slideToClickedSlide: true
	    });
	    galleryLeft.params.control = galleryThumbs;
	    galleryThumbs.params.control = galleryLeft;
    };
$(function() {
    $('.main').width($(window).width());
    $(window).resize(function(){
    	$('.main').width($(window).width());
    	});
    $(".avatar").click(function() {
    	$(".modal-body").html($("#upPhotoDiv").html());
	});
    $('#myModal').on('shown.bs.modal',
    function() {
        swiper();
    })
   
});
</script>
</html>