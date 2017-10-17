<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="edge"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>WoDotA为您找到“${wd }”的相关结果约${count }个${seoConfigMap['1'].title }</title>
    <meta name="keywords" content="${wd }">
    <meta name="description" content="WoDotA为您找到“${wd }”的相关结果约${count }个">
    <jsp:include page="include/common.jsp"/>
    <jsp:include page="include/socket.jsp"/>
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/search-result.css?v=${version }">
    <script type="text/javascript" src="${staticPrefix }/js/business/search.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/business/comment.js?v=${version }"></script>
	<script type="text/javascript" src="${staticPrefix }/js/pinying.js?v=${version }"></script>
	<script src="${staticPrefix}/js/common/gt.js?v=${version }" type="text/javascript"></script>
	<%-- 统计分析 --%>
	<jsp:include page="include/analytics.jsp"/>
</head>
<body class="o-h">
    <!-- 顶部 -->
    <jsp:include page="include/header.jsp"/>
    <!--顶部之外-->
    <div class="main fix">
	    <div class="search-result-main">
            <!--搜素结果数量-->
            <div class="search-result-main-number">WoDotA为您找到“<h1>${wd }</h1>”的相关结果约${count }个</div>
            <input value="${current }" id="current" type="hidden"/>
	        <!--搜索结果-->
	        <div class="search-result-main-content">
	            <!--left-->
	            <ul class="search-content-left">
                    <input type="hidden" value="${user.photo_70 }" id="userPhoto_65"/>
                    <li id="loadMore" class="page-turning-before">
                        <img src="${staticPrefix }/images/loadding-02.gif">
                    </li>
                    
                    <li class="page-turning hide">
                    	<div class="page-turning-inside">
	                   		<!--翻页-->
	                        <div class="left" id="left">
	                        	<div class="fy">
	                                <i class="iconfont icon-wf_zuo_qiehuan"></i>
	                                <span>上一页</span>
	                            </div>	
	                            <div class="zm">W</div>
	                        </div>
	                        <ul class="middle" id="middle-page"></ul>
	                        <div class="right" id="right">
	                            <div class="zm">F</div>
	                            <div class="fy">
	                                <i class="iconfont icon-wf_you_qiehuan"></i>
	                                <span>下一页</span>
	                            </div>	
	                        </div>
	                    </div>
                    </li>
		                    
	                <li class="no-find hide">
	                	<!--没有找到-->
	                    <p class="title">您想要的内容，暂时还没有，不过您可以提交内容给我们。</p>
	                    <label class="label-x" style="margin-top: 50px;">关键字</label>
	                    <div class="form-group label-floating phonenum">
	                        <input type="text" id="sub_keyword" class="form-control text_input" value="${wd }" onblur="checkSubmitKeyword()">
	                        <span class="help-block gjz-error"></span>
	                    </div>
	                    <label class="label-x" style="margin-top: 36px;">URL<span>(请输入索引URL链接,以http或者https开头)</span></label>
	                    <div class="form-group label-floating form-group-x">
	                        <input type="text" name="submitUrlList" class="form-control text_input" placeholder="http://" onblur="checkSubmitUrl(this)">
	                        <span class="help-block">请输入链接地址</span>
	                        <span class="subtract images-x images-x-content">-</span>
	                    </div>
	                    <div class="add-outside">
	                        <span class="add iconfont-x images-x images-x-content">+</span>
	                        <span class="text">点击增加，最多可增加4个URL链接</span>
	                    </div>
	                    <div class="main"><div class="yanzheng" id="gtcaptcha"></div></div>
	                    <button class="btn" id="submitKeywordBtn" disabled="true">提交</button>
	                </li>
                    
	            </ul>
                <!--right无搜索结果预加载-->
                <div class="search-content-right">
                    <!--右侧title预加载-->
                    <div class="title-x title-x-yjz">
                        <span class="one"></span>
                        <h3 class="two"></h3>
                    </div>
                    <div class="team-two team-two-yjz">
                        <div class="team-two-inside">
                            <a href="javascript:;" class="images-x images-x-content"></a>
                            <a href="javascript:;" class="title">
                                <div class="one"></div>
                                <div class="two"></div>
                            </a>
                        </div>
                        <div class="team-two-inside">
                            <a href="javascript:;" class="images-x images-x-content"></a>
                            <a href="javascript:;" class="title">
                                <div class="one"></div>
                                <div class="two"></div>
                            </a>
                        </div>
                        <div class="team-two-inside">
                            <a href="javascript:;" class="images-x images-x-content"></a>
                            <a href="javascript:;" class="title">
                                <div class="one"></div>
                                <div class="two"></div>
                            </a>
                        </div>
                        <div class="team-two-inside">
                            <a href="javascript:;" class="images-x images-x-content"></a>
                            <a href="javascript:;" class="title">
                                <div class="one"></div>
                                <div class="two"></div>
                            </a>
                        </div>
                        <div class="team-two-inside">
                            <a href="javascript:;" class="images-x images-x-content"></a>
                            <a href="javascript:;" class="title">
                                <div class="one"></div>
                                <div class="two"></div>
                            </a>
                        </div>
                        <div class="team-two-inside">
                            <a href="javascript:;" class="images-x images-x-content"></a>
                            <a href="javascript:;" class="title">
                                <div class="one"></div>
                                <div class="two"></div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="include/footer.jsp" />
	    <canvas id="drawNext"></canvas>
</body>
<script>
    $.material.init();
</script>
</html>