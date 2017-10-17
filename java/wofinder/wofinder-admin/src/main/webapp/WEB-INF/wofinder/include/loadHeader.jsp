<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!--顶部-->
<ul class="homepage-top search-result-top">
    <!--搜索框-->
    <li class="homepage-top-li homepage-main-search">
        <h2 class="homepage-main-search-logo-outside"><a href="${path }/index" class="iconfont icon-logoico homepage-main-search-logo"></a></h2>
        <div class="form-group label-floating">
            <input type="text" class="form-control text_input" placeholder="请输入UP主名称或视频关键词">
            <div class="search-record"></div>
        </div>
        <div class="btn-outside">
            <div class="btn btn-primary btn-raised">找一下</div>
        </div>
        <%-- <div class="checkbox">
            <label>
                <input type="checkbox">
            </label>
            <span class="title">更新缓存</span>
        </div> --%>
    </li>
    <%-- <li class="homepage-top-li homepage-top-li-four">
        <i class="iconfont icon-wf_liebiao iconfont-x" title="菜单"><span></span></i>
        <div class="dropdown-triangle-bd"></div>
        <div class="dropdown-triangle"></div>
    </li> --%>
    <li class="homepage-top-li homepage-top-li-three">
        <i class="iconfont icon-wf_touxiang iconfont-x" title="个人中心"><span></span></i>
        <div class="dropdown-triangle-bd"></div>
        <div class="dropdown-triangle"></div>
    </li>
    <%-- <li class="homepage-top-li homepage-top-li-two">
        <i class="iconfont icon-wf_lishijilu iconfont-x" title="历史记录"><span></span></i>
        <div class="dropdown-triangle-bd"></div>
        <div class="dropdown-triangle"></div>
    </li> --%>
</ul>
