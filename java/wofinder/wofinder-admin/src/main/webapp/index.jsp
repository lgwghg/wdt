<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-cn" xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="common/common.jsp"%>
<script src="${ctx}/resources/js/bootstrap/bootstrap.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
$(function() {
    webside.index.initHomePage();
    webside.index.initNavigation();
    webside.index.initScrollBar();
    webside.index.menu.initDropdownMenuStyle();
    webside.index.menu.initMenuEvent();
    
});
</script>
</head>

<body class="no-skin">
	<!-- #section:basics/navbar.layout -->
	<div id="navbar" class="navbar navbar-default">
		<script type="text/javascript">
			try {
				ace.settings.check('navbar', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="navbar-container" id="navbar-container">
			<!-- #section:basics/sidebar.mobile.toggle -->
			<button type="button" class="navbar-toggle menu-toggler pull-left"
				id="menu-toggler" data-target="#sidebar">
				<span class="sr-only"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
			</button>

			<!-- /section:basics/sidebar.mobile.toggle -->
			<div class="navbar-header pull-left">
				<!-- #section:basics/navbar.layout.brand -->
				<a href="#" class="navbar-brand" style="padding: 7px 0px;">
				 	<small>
				 		<img src="${ctx }/resources/images/logo_no_title.png" height="32px" />
				 		wofinder后台管理系统
					</small>
				</a>

				<!-- /section:basics/navbar.layout.brand -->

				<!-- #section:basics/navbar.toggle -->

				<!-- /section:basics/navbar.toggle -->
			</div>

			<!-- #section:basics/navbar.dropdown -->
			<div class="navbar-buttons navbar-header pull-right" role="navigation">
				<ul class="nav ace-nav">
					<!-- #section:basics/navbar.user_menu -->
					<li class="light-blue"><a data-toggle="dropdown" href="#"
						class="dropdown-toggle">
							<c:if test="${!empty userEntity && !empty userEntity.photo}">
								<img class="nav-user-photo" src="${userEntity.photo_40}"
									alt="头像" style="width:40px;height:40px;" />
							</c:if> <c:if test="${empty userEntity || empty userEntity.photo}">
								<img class="nav-user-photo"
									src="${ctx}/resources/images/nopic.gif" alt="头像"
									style="width:40px;height:40px;" />
							</c:if> <span class="user-info"> <small>欢迎</small>
								${userEntity.nickName }
						</span> <i class="ace-icon fa fa-caret-down"></i>
					</a>
						<ul
							class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
							<%-- <li><a href="javascript:void(0)"
								nav-menu="修改密码,/userCtrl/passwordUI.html?id=${userEntity.id }&mobile=${userEntity.mobile}&nickName=${userEntity.nickName}">
									<i class="ace-icon fa fa-cog"></i> 修改密码
							</a></li> --%>
							<li><a href="javascript:void(0)"
								nav-menu="个人资料,/userCtrl/infoUI.html?id=${userEntity.id }"> <i
									class="ace-icon fa fa-user"></i> 个人信息
							</a></li>
							<li class="divider"></li>
							<li><a href="${ctx}/logout"> <i
									class="ace-icon fa fa-power-off"></i> 退出
							</a></li>
						</ul></li>

					<!-- /section:basics/navbar.user_menu -->
				</ul>
			</div>

			<!-- /section:basics/navbar.dropdown -->
		</div>
		<!-- /.navbar-container -->
	</div>

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<!-- #section:basics/sidebar -->
		<div id="sidebar" class="sidebar responsive">
			<script type="text/javascript">
				try {
					ace.settings.check('sidebar', 'fixed')
				} 
				catch (e) { }
			</script>
			
			<!-- /.sidebar-shortcuts -->
			<ul class="nav nav-list">
				<!-- 最多支持四级菜单 -->
				<c:forEach var="resource" items="${list}" varStatus="s">
					<!-- 一级 -->
					<li level="level1" class=""><a href="javascript:void(0)"
						<c:if test="${fn:length(resource.sourceUrl) gt 0 }"> nav-menu="${resource.name },${resource.sourceUrl }"</c:if>
						<c:if test="${fn:length(resource.children) gt 0 }"> class="dropdown-toggle"</c:if>>
							<i class="menu-icon fa ${resource.icon }"></i> <span
							class="menu-text">${resource.name } </span> <b
							class="arrow<c:if test="${fn:length(resource.children) gt 0 }"> fa fa-angle-down</c:if>"></b>
					</a> <b class="arrow"></b> <c:if
							test="${fn:length(resource.children) gt 0 }">
							<ul class="submenu">
								<!-- 二级 -->
								<c:forEach var="firstChildrens" items="${resource.children}"
									varStatus="idx1">
									<li level="level2" class=""><a href="javascript:void(0)"
										<c:if test="${fn:length(firstChildrens.sourceUrl) gt 0 }">nav-menu="${resource.name },${firstChildrens.name },${firstChildrens.sourceUrl }"</c:if>
										<c:if test="${fn:length(firstChildrens.children) gt 0 }"> class="dropdown-toggle"</c:if>>
											<i class="menu-icon fa fa-caret-right"></i>
											${firstChildrens.name } <c:if
												test="${fn:length(firstChildrens.children) gt 0 }">
												<b class="arrow  fa fa-angle-down"></b>
											</c:if>
									</a> <b class="arrow"></b> <c:if
											test="${fn:length(firstChildrens.children) gt 0 }">
											<ul class="submenu">
												<!-- 三级 -->
												<c:forEach var="secondChildrens"
													items="${firstChildrens.children}" varStatus="idx2">
													<li level="level3" class=""><a
														href="javascript:void(0)"
														<c:if test="${fn:length(secondChildrens.sourceUrl) gt 0 }">nav-menu="${resource.name },${firstChildrens.name },${secondChildrens.name },${secondChildrens.sourceUrl }"</c:if>
														<c:if test="${fn:length(secondChildrens.children) gt 0 }"> class="dropdown-toggle"</c:if>>
															<i
															class="menu-icon <c:if test="${fn:length(secondChildrens.icon) gt 0 }">${secondChildrens.icon } green</c:if><c:if test="${fn:length(secondChildrens.icon) le 0 }">fa fa-caret-right</c:if>"></i>
															${secondChildrens.name } <c:if
																test="${fn:length(secondChildrens.children) gt 0 }">
																<b class="arrow  fa fa-angle-down"></b>
															</c:if>
													</a> <b class="arrow"></b> <c:if
															test="${fn:length(secondChildrens.children) gt 0 }">
															<ul class="submenu">
																<!-- 四级 -->
																<c:forEach var="thridChildrens"
																	items="${secondChildrens.children}" varStatus="idx3">
																	<li level="level4" class=""><a
																		href="javascript:void(0)"
																		<c:if test="${fn:length(thridChildrens.sourceUrl) gt 0 }">nav-menu="${resource.name },${firstChildrens.name },${secondChildrens.name },${thridChildrens.name },${thridChildrens.sourceUrl }"</c:if>
																		<c:if test="${fn:length(thridChildrens.children) gt 0 }"> class="dropdown-toggle"</c:if>>
																			<i
																			class="menu-icon <c:if test="${fn:length(thridChildrens.icon) gt 0 }">${thridChildrens.icon } orange</c:if><c:if test="${fn:length(thridChildrens.icon) le 0 }">fa fa-caret-right</c:if>"></i>
																			${thridChildrens.name }
																	</a> <b class="arrow"></b></li>
																</c:forEach>
															</ul>
														</c:if></li>
												</c:forEach>
											</ul>
										</c:if></li>
								</c:forEach>
							</ul>
						</c:if></li>
				</c:forEach>

			</ul>
			<!-- /.nav-list -->

			<!-- #section:basics/sidebar.layout.minimize -->
			<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
				<i class="ace-icon fa fa-angle-double-left"
					data-icon1="ace-icon fa fa-angle-double-left"
					data-icon2="ace-icon fa fa-angle-double-right"></i>
			</div>

			<!-- /section:basics/sidebar.layout.minimize -->
			<script type="text/javascript">
				try {
					ace.settings.check('sidebar', 'collapsed')
				} 
				catch (e) { }
			</script>
		</div>

		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<!-- #section:basics/content.breadcrumbs -->
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try {
							ace.settings.check('breadcrumbs', 'fixed')
						} catch (e) {
						}
					</script>

					<ul class="breadcrumb">

					</ul>
					<!-- /.breadcrumb -->
				</div>

				<!-- #section:settings.box -->
				<div class="ace-settings-container" id="ace-settings-container"
					style="display: none;">
					<div class="btn btn-app btn-xs btn-warning ace-settings-btn"
						id="ace-settings-btn">
						<i class="ace-icon fa fa-cog bigger-130"></i>
					</div>

					<div class="ace-settings-box clearfix" id="ace-settings-box">
						<div class="pull-left width-50">
							<!-- #section:settings.skins -->
							<div class="ace-settings-item">
								<div class="pull-left">
									<select id="skin-colorpicker" class="hide">
										<option data-skin="no-skin" value="#438EB9">#438EB9</option>
										<option data-skin="skin-1" value="#222A2D">#222A2D</option>
										<option data-skin="skin-2" value="#C6487E">#C6487E</option>
										<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
									</select>
								</div>
								<span>&nbsp; Choose Skin</span>
							</div>

							<!-- /section:settings.skins -->

							<!-- #section:settings.navbar -->
							<div class="ace-settings-item">
								<input type="checkbox" class="ace ace-checkbox-2"
									id="ace-settings-navbar" /> <label class="lbl"
									for="ace-settings-navbar"> Fixed Navbar</label>
							</div>

							<!-- /section:settings.navbar -->

							<!-- #section:settings.sidebar -->
							<div class="ace-settings-item">
								<input type="checkbox" class="ace ace-checkbox-2"
									id="ace-settings-sidebar" /> <label class="lbl"
									for="ace-settings-sidebar"> Fixed Sidebar</label>
							</div>

							<!-- /section:settings.sidebar -->

							<!-- #section:settings.breadcrumbs -->
							<div class="ace-settings-item">
								<input type="checkbox" class="ace ace-checkbox-2"
									id="ace-settings-breadcrumbs" /> <label class="lbl"
									for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>
							</div>

							<!-- /section:settings.breadcrumbs -->

							<!-- #section:settings.rtl -->
							<div class="ace-settings-item">
								<input type="checkbox" class="ace ace-checkbox-2"
									id="ace-settings-rtl" /> <label class="lbl"
									for="ace-settings-rtl"> Right To Left (rtl)</label>
							</div>

							<!-- /section:settings.rtl -->

							<!-- #section:settings.container -->
							<div class="ace-settings-item">
								<input type="checkbox" class="ace ace-checkbox-2"
									id="ace-settings-add-container" /> <label class="lbl"
									for="ace-settings-add-container"> Inside <b>.container</b>
								</label>
							</div>

							<!-- /section:settings.container -->
						</div>
						<!-- /.pull-left -->

						<div class="pull-left width-50">
							<!-- #section:basics/sidebar.options -->
							<div class="ace-settings-item">
								<input type="checkbox" class="ace ace-checkbox-2"
									id="ace-settings-hover" /> <label class="lbl"
									for="ace-settings-hover"> Submenu on Hover</label>
							</div>

							<div class="ace-settings-item">
								<input type="checkbox" class="ace ace-checkbox-2"
									id="ace-settings-compact" /> <label class="lbl"
									for="ace-settings-compact"> Compact Sidebar</label>
							</div>

							<div class="ace-settings-item">
								<input type="checkbox" class="ace ace-checkbox-2"
									id="ace-settings-highlight" /> <label class="lbl"
									for="ace-settings-highlight"> Alt. Active Item</label>
							</div>

							<!-- /section:basics/sidebar.options -->
						</div>
						<!-- /.pull-left -->
					</div>
					<!-- /.ace-settings-box -->
				</div>
				<!-- /.ace-settings-container -->

				<!-- /section:settings.box -->

				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content"></div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->

		<div class="footer">
			<div class="footer-inner">
				<!-- #section:basics/footer -->
				<div class="footer-content">
					<span class="bigger-120"> <span class="blue bolder">WoDota</span>
						Application &copy; 2017
					</span>
				</div>

				<!-- /section:basics/footer -->
			</div>
		</div>

		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only fa-2x"></i>
		</a>
	</div>
	<!-- /.main-container -->

	<!-- ace scripts -->
	<script type="text/javascript" src="${ctx}/resources/js/ace/ace.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/ace/ace-extra.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/ace/ace.sidebar.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/ace/ace.sidebar-scroll-1.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/ace/ace.widget-box.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/ace/ace.settings.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/ace/ace.settings-skin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/ace/elements.scroller.js"></script>

	<!-- jquery scripts -->
	<script type="text/javascript"
		src="${ctx}/resources/js/jquery/jquery.easypiechart.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/jquery/jquery.sparkline.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/jquery/jquery.flot.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/jquery/jquery.flot.pie.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/jquery/jquery.flot.resize.js"></script>
</body>
</html>
