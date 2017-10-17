<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="edge"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>${video.title}${seoConfigMap['1'].title }</title>
	<meta name="MobileOptimized" content="320">
	<meta name="format-detection" content="telephone=no" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta name="keywords" content="<c:if test="${not empty video.videoTag && video.videoTag!=''}">${video.videoTag}-${video.stationName}-${seoConfigMap['1'].title }</c:if><c:if test="${empty video.videoTag || video.videoTag==''}">${video.title}-${video.stationName}-${seoConfigMap['1'].title }</c:if>">
	<meta name="description" itemprop="description" content="<c:if test="${not empty video.introduction && video.introduction!=''}">${video.introduction}</c:if><c:if test="${empty video.introduction || video.introduction==''}">${video.title}</c:if>">
	<meta name="author" content="${video.upName }"/>
	<!-- 社交 -->
	<meta property="og:type" content="video"/>  
	<meta property="og:title"content="${video.title}${seoConfigMap['1'].title }"/>
	<meta property="og:image" content="${video.cover }"/>
	<meta property="og:url" content="http://wodota.com/video/${video.shortId }"/>
	<meta property="og:description" content="<c:if test="${not empty video.introduction && video.introduction!=''}">${video.introduction}</c:if><c:if test="${empty video.introduction || video.introduction==''}">${video.title}</c:if>">
	<meta property="og:site_name" content="WoDotA">  
	<meta property="og:width" content="672" />  
	<meta property="og:height" content="536" />
	<meta property="og:locale" content="zh-CN">
	
	<!-- google+ -->
	<meta itemprop="name" content="${video.title}${seoConfigMap['1'].title }"/>
	<meta itemprop="description" content="<c:if test="${not empty video.introduction && video.introduction!=''}">${video.introduction}</c:if><c:if test="${empty video.introduction || video.introduction==''}">${video.title}</c:if>"/>
	<meta itemprop="image" content="${video.cover }"/>
	<meta itemprop="thumbnailUrl" content="${video.cover }"/>
	<meta itemprop="url" content="http://wodota.com/video/${video.shortId }"/>
	<meta itemprop="uploadDate" content="${video.createTime }" />
	<meta itemprop="datePublished" content="${video.createTime }" />
	<meta itemprop="inLanguage" content="zh-CN"/>
	<meta itemprop="contentLocation" content="中国"/>
	
	<!-- twitter -->
	<meta name="twitter:title" content="${video.title}${seoConfigMap['1'].title }">
	<meta name="twitter:description" content="<c:if test="${not empty video.introduction && video.introduction!=''}">${video.introduction}</c:if><c:if test="${empty video.introduction || video.introduction==''}">${video.title}</c:if>">
	<meta name="twitter:card" content="summary">  
	<meta name="twitter:domain" content="wodota.com"/>
	<meta name="twitter:url" content="http://wodota.com/video/${video.shortId }"/>
	<meta name="twitter:image" content="${video.cover }"/>
	<meta name="twitter:creator" content="${video.upName }">
	<meta name="twitter:site" content="WoDotA"> 
	
    <jsp:include page="../include/common.jsp"/>
    <jsp:include page="../include/socket.jsp"/>
    <script type="text/javascript" src="${staticPrefix }/js/business/videoDetails.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/business/videovalue/videoValue.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/business/comment.js?v=${version }"></script>
	<script type="text/javascript" src="${staticPrefix }/js/pinying.js?v=${version }"></script>
	<script type="text/javascript" src="${staticPrefix }/js/qrcode/jquery.qrcode.js?v=${version }"></script>
	<script type="text/javascript" src="${staticPrefix }/js/qrcode/jquery.utf.js?v=${version }"></script>
	<%-- 统计分析 --%>
	<jsp:include page="../include/analytics.jsp"/>
  </head>
  
  <body>
	<!-- 头部 -->
	<jsp:include page="../include/header.jsp"/>
    <!--顶部之外-->
    <div class="main fix">
        <div class="search-result-main video-details-main">
        	<input type="hidden" id="gto" value="${gto}">
			<input type="hidden" id="category" value="${video.category}">
			<input type="hidden" id="stationId" value="${video.videoStatinId }"/>
			<input type="hidden" id="videoId" value="${video.videoId }"/>
			<input type="hidden" id="videoShortId" value="${video.shortId }"/>
			<input type="hidden" id="albumId" value="${video.albumId }"/>
            <!--搜索结果-->
            <div class="search-result-main-content">
                <!--left-->
                <ul class="search-content-left">
                    <!--具体视频内容-->
	                <li class="video video-yjz compilations" id="video_li_${video.videoId }" value="${video.videoId }">
	                	<div id="secondVideo" class="hide">
	                		<!-- 隐藏域 -->
							<input type="hidden" value="${video.videoId }" id="video_${video.videoId }">
		                    <!--标题-->
		                    <div class="title-x">
		                        <span class="one"></span>
		                        <h1 class="two" id="title">${video.title}</h1>
		                        <c:if test="${!videoIsRedisCache }">
			                        <button id="videoRedis_btn" class="three" onclick="setIdToRedis('${videoIdsToRedis}')">
			                            <i class="iconfont icon-wf_zhongzhi"></i>
			                            <span>点击更新缓存</span>
			                        </button>
		                        </c:if>
		                    </div>
		                    <!--视频点击后播放-->
		                    <div class="unfold-after unfold-after-x" id="videoDiv">
		                    	<c:choose>
		                    		<c:when test="${video.station eq 1 or video.station eq 2 }">
		                    			<iframe src="${video.flashUrl }" width="672" height="536" align="middle">
		                    			</iframe>
		                    		</c:when>
		                    		<c:otherwise>
				                    	<embed width="672" height="536" allownetworking="all" allowscriptaccess="always" 
				                    		src="${video.flashUrl }" quality="high" bgcolor="#000" wmode="window"
											allowfullscreen="true" allowFullScreenInteractive="true" type="application/x-shockwave-flash"
											pluginspage="//www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" />
		                    		</c:otherwise>
		                    	</c:choose>
		                    </div>
		                    <!--切换视频来源-->
		                    <div class="video-source">
		                        <div class="left" title="播放量">
		                            <i class="iconfont icon-wf_bofang"></i>
		                            <span class="two" id="viewCount">${video.viewCount }</span>
		                        </div>
		                        <div class="middle">
		                            <i class="iconfont icon-wf_zuo_qiehuan"></i>
		                            <div class="video-source-content">
		                                <ul>
		                                    <li id="stationList_li">
		                                    </li>
		                                </ul>
		                            </div>
		                            <i class="iconfont icon-wf_you_qiehuan"></i>
		                        </div>
		                        <div class="right">
		                        	<span class="grade-number">${video.score }分</span>
		                            <span>评论：</span>
		                            <strong><a id="videoCommentCount">${video.videoCommentCount }</a></strong>
		                        </div>
		                    </div>
		                    <!--视频点击后简介模块-->
		                    <div class="grade unfold-after-x">
		                        <div class="grade-x">
		                            <div class="left">
		                            	<div class="one"></div>
		                            	<div class="two" title="${video.introduction }" id="introduction">
			                                ${video.introduction }
		                            	</div>
		                            </div>
		                            <div class="right">
		                            	<input type="hidden" value="${video.myScore }" id="score" placeholder="我的评分">
		                                <div class="grade-number-x">
		                                    <ul id="scoreClass">
		                                        <li class="item1">
		                                            <i class="iconfont icon-wf_weipingfen"></i>
		                                            <i class="iconfont icon-wf_pingfen1"></i>
		                                            <i class="iconfont icon-wf_pingfen"></i>
		                                        </li>
		                                        <li class="item2">
		                                            <i class="iconfont icon-wf_weipingfen"></i>
		                                            <i class="iconfont icon-wf_pingfen1"></i>
		                                            <i class="iconfont icon-wf_pingfen"></i>
		                                        </li>
		                                        <li class="item3">
		                                            <i class="iconfont icon-wf_weipingfen"></i>
		                                            <i class="iconfont icon-wf_pingfen1"></i>
		                                            <i class="iconfont icon-wf_pingfen"></i>
		                                        </li>
		                                        <li class="item4">
		                                            <i class="iconfont icon-wf_weipingfen"></i>
		                                            <i class="iconfont icon-wf_pingfen1"></i>
		                                            <i class="iconfont icon-wf_pingfen"></i>
		                                        </li>
		                                        <li class="item5">
		                                            <i class="iconfont icon-wf_weipingfen"></i>
		                                            <i class="iconfont icon-wf_pingfen1"></i>
		                                            <i class="iconfont icon-wf_pingfen"></i>
		                                        </li>
		                                    </ul>
		                                    <div class="two">
		                                        <div class="item1-one"></div>
		                                        <div class="item1-two"></div>
		                                        <div class="item2-one"></div>
		                                        <div class="item2-two"></div>
		                                        <div class="item3-one"></div>
		                                        <div class="item3-two"></div>
		                                        <div class="item4-one"></div>
		                                        <div class="item4-two"></div>
		                                        <div class="item5-one"></div>
		                                        <div class="item5-two"></div>
		                                    </div>
		                                    <span class="three"></span>
		                                </div>
		                            </div>
		                            <div class="author">
		                                <span>作者:</span>
		                                <span id="upName" title="${video.upName }">${video.upName }</span>
		                            </div>
		                        </div>
			                    <div class="label-x">
		                            <!-- <div class="label-x-inside">
		                                <i class="iconfont icon-wf_biaoqiantou"></i>
		                                <span class="label-x-inside-content">卢本伟牛逼</span>
		                            </div>
		                            -->
		                            <div class="label-x-inside add">
		                                <i class="iconfont icon-wf_zhankai"></i>
		                            </div>
		                            <div class="form-group label-floating is-empty hide add-icon">
		                                <input type="text" class="form-control text_input" id="videoValueName" placeholder="按回车键完成输入" />
		                                <i class="iconfont icon-x"></i>
		                            </div>
		                            <div class="fix" style="clear: both;display: block"></div>
		                        </div>
		                        <div class="see-label-history">
		                            <span><a target="_blank" href="${path }/videoValue/log/${video.shortId}">查看标签修改记录</a></span>
		                        </div>
		                    </div>
	                        
	                        <!-- 合辑 -->
	                        <div class="episode-details">
		                        <div class="episode-details-menu" style="display: none;">
		                            <div class="one-outside">
		                                <ul class="one" id="album_ul">
		                                    
		                                </ul>
		                            </div>
		                            <div class="two" id="changeAlbum" style="display: none;">
		                                <i class="iconfont icon-wf_zuo_qiehuan no-click"></i>
		                                <i class="iconfont icon-wf_you_qiehuan"></i>
		                            </div>
		                        </div>
		                        <div class="episode-details-bottom" style="display: none;">
		                            <div class="control-left" style="display: none;">
		                                <i class="iconfont icon-wf_zuo_qiehuan"></i>
		                            </div>
		                            <ul class="videos" id="video_ul">
		                                
		                            </ul>
		                            <div class="control-right" style="display: none;">
		                                <i class="iconfont icon-wf_you_qiehuan"></i>
		                            </div>
		                        </div>
		                    </div>
	                	</div>
	                    
	                    <div id="firstVideo" class="">
	                    	<div class="title-x title-x-yjz">
	                            <span class="one"></span>
	                            <h1 class="two"></h1>
	                        </div>
	                        <!--视频点击后播放-->
	                        <div class="unfold-after unfold-after-x">
	                        </div>
	                        <!--切换视频来源-->
	                        <div class="video-source">
	                            <div class="left">
	                            </div>
	                            <div class="middle">
	                            </div>
	                            <div class="right">
	                            </div>
	                        </div>
	                        <!--视频点击后简介模块-->
	                        <div class="grade unfold-after-x">
	                            <div class="grade-x">
	                                <div class="left">
	                                </div>
	                                <div class="right">
	                                </div>
	                            </div>
	                        </div>
		                    	
	                    </div>
	                    
	                    <!--评论展开后分享模块-->
	                    <div id="secondComment" class="comment-x hide">
	                        <div class="share">
	                            <span class="one">分享：</span>
	                            <div class="two">
	                                <i class="iconfont iconfont-x icon-qq" onclick="share('1','${ip}video/${video.shortId }','${video.title}','${video.introduction }','${video.cover }')"></i>
<!--  	                                <i class="iconfont iconfont-x icon-weixin"> -->
<!--  	                                	<div class="ewm"><div id="showEWMDiv" class="ewm-fx"></div><div class="sys-fx">微信扫一扫分享</div></div> -->
<!--  	                                </i> -->
	                                <i class="iconfont iconfont-x icon-weibo" onclick="share('2','${ip}video/${video.shortId }','${video.title}','${video.introduction }','${video.cover }')"></i>
	                            </div>
	                            <strong class="three">
	                                <a id="url" target="_helf" href="${video.url }">视频来源</a>
	                            </strong>
	                        </div>
	                        <!--评论已展开-->
	                        <div class="comment-yes" id="comment">
							    <ul class="comment-inside">
						            
							    </ul>
							</div>
							<input type="hidden" value="${userPhoto_65 }" id="userPhoto_65"/>
							<div class="comment-fb-outside">
							    <div class="header images-x images-x-content">
							        <img id="myPhoto65_${video.videoId }" src="${userPhoto_65 }">
							        <span id="myZM_${video.videoId }" class="hide">Z</span>
							    </div>
							    <div class="hf-x hide">
							        <span>回复：</span>
							        <span class="name"></span>
							    </div>
							    <input type="text" id="comment-content_${video.videoId }" class="comment-fb" placeholder="添加评论...">
							    <i class="iconfont icon-wf_huiche iconfont-x"></i>
							</div>
                        </div>
	                    
	                    <div id="firstComment" class="comment-x comment-x-yjz">
	                   	 	<!--评论展开后分享模块-->
                            <div class="share">
                                <span class="one"></span>
                                <div class="two">
                                </div>
                            </div>
	                    
                            <!--评论已展开-->
                            <div class="comment-yes">
                                <ul class="comment-inside">
                                    <li>
                                        <!--一级对话-->
                                        <div class="comment-content comment-one">
                                            <div class="header images-x images-x-content">
                                            </div>
                                            <div class="right">
                                                <div class="top">
                                                    <span class="name"></span>
                                                    <span class="praise-number"></span>
                                                    <span class="time"></span>
                                                    <div class="praise praise-yes hide">
                                                        <i class="iconfont icon-wf_dianzan"></i>
                                                    </div>
                                                </div>
                                                <div class="bottom">
                                                </div>
                                            </div>
                                        </div>
                                        <!--二级对话-->
                                        <div class="comment-content comment-two">
                                            <div class="header images-x images-x-content">
                                            </div>
                                            <div class="right">
                                                <div class="top">
                                                    <span class="name"></span>
                                                    <span class="praise-number"></span>
                                                    <span class="time"></span>
                                                    <div class="praise praise-yes hide">
                                                        <i class="iconfont icon-wf_dianzan"></i>
                                                    </div>
                                                </div>
                                                <div class="bottom">
                                                </div>
                                            </div>
                                        </div>
                                        <!--三级对话-->
                                        <div class="comment-content comment-three">
                                            <div class="header images-x images-x-content">
                                            </div>
                                            <div class="right">
                                                <div class="top">
                                                    <span class="name"></span>
                                                    <span class="praise-number"></span>
                                                    <span class="time"></span>
                                                    <div class="praise praise-yes hide">
                                                        <i class="iconfont icon-wf_dianzan"></i>
                                                    </div>
                                                </div>
                                                <div class="bottom">
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                    <li>
                                        <!--一级对话-->
                                        <div class="comment-content comment-one">
                                            <div class="header images-x images-x-content">
                                            </div>
                                            <div class="right">
                                                <div class="top">
                                                    <span class="name"></span>
                                                    <span class="praise-number"></span>
                                                    <span class="time"></span>
                                                    <div class="praise praise-yes hide">
                                                        <i class="iconfont icon-wf_dianzan"></i>
                                                    </div>
                                                </div>
                                                <div class="bottom">
                                                </div>
                                            </div>
                                        </div>
                                        <!--二级对话-->
                                        <div class="comment-content comment-two">
                                            <div class="header images-x images-x-content">
                                            </div>
                                            <div class="right">
                                                <div class="top">
                                                    <span class="name"></span>
                                                    <span class="praise-number"></span>
                                                    <span class="time"></span>
                                                    <div class="praise praise-yes hide">
                                                        <i class="iconfont icon-wf_dianzan"></i>
                                                    </div>
                                                </div>
                                                <div class="bottom">
                                                </div>
                                            </div>
                                        </div>
                                        <!--三级对话-->
                                        <div class="comment-content comment-three">
                                            <div class="header images-x images-x-content">
                                            </div>
                                            <div class="right">
                                                <div class="top">
                                                    <span class="name"></span>
                                                    <span class="praise-number"></span>
                                                    <span class="time"></span>
                                                    <div class="praise praise-yes hide">
                                                        <i class="iconfont icon-wf_dianzan"></i>
                                                    </div>
                                                </div>
                                                <div class="bottom">
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                    <li>
                                        <!--一级对话-->
                                        <div class="comment-content comment-one">
                                            <div class="header images-x images-x-content">
                                            </div>
                                            <div class="right">
                                                <div class="top">
                                                    <span class="name"></span>
                                                    <span class="praise-number"></span>
                                                    <span class="time"></span>
                                                    <div class="praise praise-yes hide">
                                                        <i class="iconfont icon-wf_dianzan"></i>
                                                    </div>
                                                </div>
                                                <div class="bottom">
                                                </div>
                                            </div>
                                        </div>
                                        <!--二级对话-->
                                        <div class="comment-content comment-two">
                                            <div class="header images-x images-x-content">
                                            </div>
                                            <div class="right">
                                                <div class="top">
                                                    <span class="name"></span>
                                                    <span class="praise-number"></span>
                                                    <span class="time"></span>
                                                    <div class="praise praise-yes hide">
                                                        <i class="iconfont icon-wf_dianzan"></i>
                                                    </div>
                                                </div>
                                                <div class="bottom">
                                                </div>
                                            </div>
                                        </div>
                                        <!--三级对话-->
                                        <div class="comment-content comment-three">
                                            <div class="header images-x images-x-content">
                                            </div>
                                            <div class="right">
                                                <div class="top">
                                                    <span class="name"></span>
                                                    <span class="praise-number"></span>
                                                    <span class="time"></span>
                                                    <div class="praise praise-yes hide">
                                                        <i class="iconfont icon-wf_dianzan"></i>
                                                    </div>
                                                </div>
                                                <div class="bottom">
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                            <div class="comment-fb-outside">
                                <div class="header images-x images-x-content">
                                </div>
                                <input type="text" class="comment-fb">
                            </div>
                        </div>
	                </li>
                </ul>
                <!--right-->
	            <div id="secondRelate" class="search-content-right video-details-right hide">
	                <div class="title-x">
	                    <span class="one"></span>
	                    <h3 class="two">相关视频</h3>
	                    <span class="three" onclick="getRelatedVideo();">换一批</span>
	                </div>
		            <ul class="unfold-before-outside" id="relatedVideo">
		                    <!--视频封面和介绍-->
		                    
		            </ul>
	            </div>
                <!--right预加载-->
                <div id="firstRelate" class="search-content-right video-details-right">
                    <div class="title-x title-x-yjz">
                        <span class="one"></span>
                        <h3 class="two"></h3>
                        <span class="three"></span>
                    </div>
                    <ul class="unfold-before-outside unfold-before-outside-yjz">
                        <!--视频封面和介绍-->
                        <li class="unfold-before">
                            <div class="images-x images-x-content">
                            </div>
                            <div class="two">
                                <p>
                                </p>
                                <span></span>
                            </div>
                        </li>
                        <!--视频封面和介绍-->
                        <li class="unfold-before">
                            <div class="images-x images-x-content">
                            </div>
                            <div class="two">
                                <p>
                                </p>
                                <span></span>
                            </div>
                        </li>
                        <!--视频封面和介绍-->
                        <li class="unfold-before">
                            <div class="images-x images-x-content">
                            </div>
                            <div class="two">
                                <p>
                                </p>
                                <span></span>
                            </div>
                        </li>
                        <!--视频封面和介绍-->
                        <li class="unfold-before">
                            <div class="images-x images-x-content">
                            </div>
                            <div class="two">
                                <p>
                                </p>
                                <span></span>
                            </div>
                        </li>
                        <!--视频封面和介绍-->
                        <li class="unfold-before">
                            <div class="images-x images-x-content">
                            </div>
                            <div class="two">
                                <p>
                                </p>
                                <span></span>
                            </div>
                        </li>
                        <!--视频封面和介绍-->
                        <li class="unfold-before">
                            <div class="images-x images-x-content">
                            </div>
                            <div class="two">
                                <p>
                                </p>
                                <span></span>
                            </div>
                        </li>
                        <!--视频封面和介绍-->
                        <li class="unfold-before">
                            <div class="images-x images-x-content">
                            </div>
                            <div class="two">
                                <p>
                                </p>
                                <span></span>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!--底部-->
    <jsp:include page="../include/footer.jsp"/>
    <!--页面加载动画canvas-->
    <canvas id="drawNext">测试</canvas>
	<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/video-details.css?v=${version }">
	
	<div class="label-praise hide" id="labelSetting">
	    <div class="team-one">
	        <div class="top">
	            <span class="top-title">卢本伟牛逼</span>
	            <div class="delete-report">
	                <button class="delete">删除</button>
	                <button class="report informBtn">举报</button>
	            </div>
	        </div>
	        <div class="bottom">
	            <i class="iconfont icon-wf_dianzan"></i>
	            <span class="one">赞</span>
	            <span class="two">
	                <span class="first">2</span>
	                <span class="second hide">+1</span>
	            </span>
	        </div>
	    </div>
	    <div class="team-two hide">
	        <div class="top">
	            <span class="top-title">请选择举报理由</span>
	            <i class="iconfont icon-x"></i>
	        </div>
	        <div class="form-group">
	            <div class="radio">
	                <label><input type="radio" name="informReason" value="0" checked="">内容不相关</label>
	            </div>
	            <div class="radio">
	                <label><input type="radio" name="informReason" value="1">敏感信息</label>
	            </div>
	            <div class="radio">
	                <label><input type="radio" name="informReason" value="2">恶意攻击</label>
	            </div>
	            <div class="radio">
	                <label><input type="radio" name="informReason" value="3">剧透内容</label>
	            </div>
	        </div>
	        <button class="btn yes">确认</button>
	    </div>
	    <div class="team-three hide">
	        <div class="top">确定要删除该标签吗？</div>
	        <div class="bottom">
	            <button class="btn yes">确认</button>
	            <button class="no">取消</button>
	        </div>
	    </div>
	</div>
  </body>
</html>
