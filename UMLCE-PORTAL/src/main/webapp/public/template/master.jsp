<%@page import="com.umce.fyp.security.Authentication"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->


<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code='login.title' /></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<meta content="" name="description" />
<meta content="" name="Fonix Developers" />
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700 subset=all"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/resources/global/plugins/font-awesome/css/font-awesome.min.css'/>"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/resources/global/plugins/simple-line-icons/simple-line-icons.min.css'/>"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/resources/global/plugins/bootstrap/css/bootstrap.min.css'/>"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/resources/global/plugins/uniform/css/uniform.default.css'/>"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/resources/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css'/>"
	rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="<c:url value='/resources/global/css/components.css'/>"
	rel="stylesheet" type="text/css" />
<link href="<c:url value='/resources/global/css/plugins.css'/>"
	rel="stylesheet" type="text/css" />
<link href="<c:url value='/resources/css/layout.css'/>" rel="stylesheet"
	type="text/css" />
<link id="style_color"
	href="<c:url value='/resources/css/themes/default.css'/>"
	rel="stylesheet" type="text/css" />
<link href="<c:url value='/resources/css/custom.css'/>" rel="stylesheet"
	type="text/css" />

<link
	href="<c:url value='global/plugins/bootstrap-fileinput/bootstrap-fileinput.css'/>"
	rel="stylesheet" type="text/css" />
<link href="<c:url value='/resources/css/profile.css'/>"
	rel="stylesheet" type="text/css" />

<link
	href="<c:url value='/resources/global/plugins/bootstrap-summernote/summernote.css'/>"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value='/resources/global/plugins/fullcalendar/fullcalendar/fullcalendar.css'/>"
	rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/global/plugins/clockface/css/clockface.css'/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/global/plugins/bootstrap-datepicker/css/datepicker3.css'/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css'/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/global/plugins/bootstrap-colorpicker/css/colorpicker.css'/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/global/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css'/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/global/plugins/bootstrap-datetimepicker/css/datetimepicker.css'/>" />
<!-- END THEME STYLES -->

<script
	src="<c:url value='/resources/global/plugins/jquery-1.11.0.min.js'/>"
	type="text/javascript"></script>


<link rel="shortcut icon" href="favicon.ico" />
</head>
<h:body
	class="page-boxed page-header-fixed page-container-bg-solid page-sidebar-closed-hide-logo page-sidebar-closed-hide-logo page-fontawesome">

	<div class="page-header navbar navbar-fixed-top">
		<!-- BEGIN HEADER INNER -->
		<div class="page-header-inner container">
			<!-- BEGIN LOGO -->
			<div class="page-logo">
				<a href="<c:url value='/portal' />"> <img
					src="<c:url value='/resources/img/log.png' />" alt="logo"
					class="logo-default" />
				</a>
				<div class="menu-toggler sidebar-toggler">
					<!-- DOC: Remove the above "hide" to enable the sidebar toggler button on header -->
				</div>
			</div>
			<!-- END LOGO -->
			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
			<a href="javascript:;" class="menu-toggler responsive-toggler"
				data-toggle="collapse" data-target=".navbar-collapse"> </a>
			<!-- END RESPONSIVE MENU TOGGLER -->
			<!-- BEGIN PAGE TOP -->
			<div class="page-top">
				<!-- BEGIN HEADER SEARCH BOX -->
				<!-- DOC: Apply "search-form-expanded" right after the "search-form" class to have half expanded search box -->
				<form class="search-form search-form-expanded" action=""
					method="get">
					<div class="input-group">
						<input type="text" class="form-control" placeholder="Search..."
							name="query" /> <span class="input-group-btn"> <a
							href="javascript:;" class="btn submit"><i
								class="icon-magnifier"></i></a>
						</span>
					</div>
				</form>
				<!-- END HEADER SEARCH BOX -->
				<div class="top-menu">
					<ul class="nav navbar-nav pull-right">
						<!-- BEGIN NOTIFICATION DROPDOWN -->
						<!-- 				<li class="dropdown dropdown-extended dropdown-notification"
							id="header_notification_bar"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"
							data-hover="dropdown" data-close-others="true"> <i
								class="icon-bell"></i> <span class="badge badge-danger">
									7 </span>
						</a>
							<ul class="dropdown-menu">
								<li>
									<p>You have 14 new notifications</p>
								</li>
								<li>
									<div class="slimScrollDiv"
										style="position: relative; overflow: hidden; width: auto; height: 250px;">
										<ul class="dropdown-menu-list scroller"
											style="overflow: hidden; width: auto; height: 250px;"
											data-initialized="1">
											<li><a href="#"> <span
													class="label label-sm label-icon label-success"> <i
														class="fa fa-plus"></i>
												</span> New user registered. <span class="time"> Just now </span>
											</a></li>
										</ul>
										<div class="slimScrollBar"
											style="width: 7px; position: absolute; top: 0px; opacity: 0.4; border-radius: 7px; z-index: 99; right: 1px; display: block; background: rgb(187, 187, 187);"></div>
										<div class="slimScrollRail"
											style="width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; opacity: 0.2; z-index: 90; right: 1px; background: rgb(234, 234, 234);"></div>
									</div>
								</li>
								<li class="external"><a href="#"> See all notifications
										<i class="icon-arrow-right"></i>
								</a></li>
							</ul></li> -->
						<!-- END NOTIFICATION DROPDOWN -->
						<!-- BEGIN TODO DROPDOWN -->
						<%-- 				<li class="dropdown dropdown-extended dropdown-tasks"
							id="header_task_bar"><a
							href="<c:url value='/portal/calendar' />" class="dropdown-toggle">
								<i class="icon-calendar"></i> --%>
						</a>
						<!-- END TODO DROPDOWN -->
						<!-- BEGIN QUICK SIDEBAR TOGGLER -->
						<li class="dropdown dropdown-quick-sidebar-toggler hide"><a
							href="javascript:;" class="dropdown-toggle"> <i
								class="icon-logout"></i>
						</a></li>
						<!-- END QUICK SIDEBAR TOGGLER -->
						<!-- BEGIN USER LOGIN DROPDOWN -->
						<li class="dropdown dropdown-user"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"
							data-hover="dropdown" data-close-others="true"> <img alt=""
								class="img-circle" src="<c:url value='/portal/profile_pic' />" />
								<span class="username username-hide-on-mobile">
									(${user.lname}) </span> <i class="fa fa-angle-down"></i>
						</a>
							<ul class="dropdown-menu">
								<li><a href="<c:url value='/portal/profile' />"> <i
										class="icon-user"></i> My Profile
								</a></li>
								<li><a href="<c:url value='/portal/calendar' />"> <i
										class="icon-calendar"></i> My Calendar
								</a></li>
								<%
									if (Authentication.hasRole(session, "Admin")) {
								%>
								<li><a href="<c:url value='/portal/service' />"> <i
										class="icon-calendar"></i> Change Service
								</a></li>
								<%
									}
								%>
								<li><a href="<c:url value='/logout' />"> <i
										class="icon-key"></i> <spring:message code="header.logout" />
								</a></li>
							</ul></li>
						<!-- END USER LOGIN DROPDOWN -->
					</ul>
				</div>
			</div>
			<!-- END PAGE TOP -->
		</div>
		<!-- END HEADER INNER -->
	</div>
	<!-- END HEADER -->
	<div class="clearfix"></div>
	<div class="container">
		<!-- BEGIN CONTAINER -->
		<div class="page-container">
			<!-- BEGIN SIDEBAR -->
			<div class="page-sidebar navbar-collapse collapse">
				<!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
				<!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
				<!-- BEGIN SIDEBAR MENU1 -->
				<ul class="page-sidebar-menu page-sidebar-menu-compact"
					data-auto-scroll="true" data-slide-speed="200">
					<li class="start active open"><a
						href="<c:url value='/portal' />"> <i class="fa fa-home"></i> <span
							class="title">Dashboard</span>
					</a></li>


					<%
						if (Authentication.hasRole(session, "Admin")) {
					%>
					<li><a href="javascript:;"> <i class="fa fa-cogs"></i> <span
							class="title">User</span> <span class="arrow "></span></a>
						<ul class="sub-menu">
							<li><a href="/umlce-portal/portal/user/add"> <i
									class="icon-briefcase"></i> Add Roles
							</a></li>
							<li><a href="/umlce-portal/portal/user/create"> <i
									class="icon-briefcase"></i> Create User
							</a></li>
							<li><a href="/umlce-portal/portal/user/list"> <i
									class="icon-briefcase"></i> User List
							</a></li>
						</ul></li>
					<%
						}
					%>
					<%
						if (Authentication.hasRole(session, "Admin")) {
					%>
					<li><a href="javascript:;"> <i class="fa fa-cogs"></i> <span
							class="title">Roles</span> <span class="arrow "></span></a>
						<ul class="sub-menu">
							<li><a href="/umlce-portal/portal/role/add"> <i
									class="icon-briefcase"></i> Add Permissions
							</a></li>
							<li><a href="/umlce-portal/portal/role/create"> <i
									class="icon-briefcase"></i> Create Role
							</a></li>
						</ul></li>
					<%
						}
					%>
					<%
						if (Authentication.hasRole(session, "Admin")) {
					%>
					<li><a href="javascript:;"> <i class="fa fa-cogs"></i> <span
							class="title">Permissions</span> <span class="arrow "></span></a>
						<ul class="sub-menu">
							<li><a href="/umlce-portal/portal/permission/add"> <i
									class="icon-briefcase"></i> Add Permission Levels
							</a></li>
							<li><a href="/umlce-portal/portal/permission/create"> <i
									class="icon-briefcase"></i> Create Permission
							</a></li>
						</ul></li>
					<%
						}
					%>
					<%
						if (Authentication.hasRole(session, "Teacher")
									|| Authentication.hasRole(session, "Student")) {
					%>
					<li><a href="javascript:;"> <i class="fa fa-cogs"></i> <span
							class="title">Assignments</span> <span class="arrow "></span></a>
						<ul class="sub-menu">

							<%
								if (Authentication.hasRole(session, "Teacher")) {
							%>
							<li><a href="/umlce-portal/portal/assignment/create"> <i
									class="icon-briefcase"></i> Create Assignment
							</a></li>
							<%
								}
							%>
							<%
								if (Authentication.hasRole(session, "Student")) {
							%>
							<li><a href="/umlce-portal/portal/assignment/view"> <i
									class="icon-briefcase"></i> View Assignments
							</a></li>
							<%
								}
							%>
							<%
								if (Authentication.hasRole(session, "Teacher")) {
							%>
							<li><a href="/umlce-portal/portal/assignment/my_assignments">
									<i class="icon-briefcase"></i> My Assignments
							</a></li>
							<%
								}
							%>
						</ul></li>

					<%
						}
					%>
					<%
						if (Authentication.hasRole(session, "Teacher")) {
					%>
					<li><a href="javascript:;"> <i class="fa fa-cogs"></i> <span
							class="title">Class Reports</span> <span class="arrow "></span></a>
						<ul class="sub-menu">
							<li><a
								href="/umlce-portal/portal/assignment/classReports/mistakes">
									<i class="icon-briefcase"></i> Mistakes Report
							</a></li>
						</ul></li>

					<li><a href="javascript:;"> <i class="fa fa-cogs"></i> <span
							class="title">User Reports</span> <span class="arrow "></span></a>
						<ul class="sub-menu">
							<li><a href="/umlce-portal/portal/assignment/UserReports">
									<i class="icon-briefcase"></i> Mistakes Report
							</a></li>
							<li><a
								href="/umlce-portal/portal/assignment/UserReports/evaluation">
									<i class="icon-briefcase"></i> Evaluation Report
							</a></li>
							<li><a
								href="/umlce-portal/portal/assignment/UserReports/plagarism">
									<i class="icon-briefcase"></i> Plagarism Report
							</a></li>
						</ul></li>

					<li><a href="javascript:;"> <i class="fa fa-cogs"></i> <span
							class="title">Assignment Reports</span> <span class="arrow "></span></a>
						<ul class="sub-menu">
							<li><a
								href="/umlce-portal/portal/assignment/AssignmentReports"> <i
									class="icon-briefcase"></i> Mistakes Report
							</a></li>
							<li><a
								href="/umlce-portal/portal/assignment/AssignmentReports/evaluation">
									<i class="icon-briefcase"></i> Evaluation Report
							</a></li>
							<li><a
								href="/umlce-portal/portal/assignment/AssignmentReports/plagarism">
									<i class="icon-briefcase"></i> Plagarism Report
							</a></li>
						</ul></li>
					<%
						}
					%>

				</ul>
				<!-- END SIDEBAR MENU1 -->
			</div>
			<!-- END SIDEBAR -->
			<!-- BEGIN CONTENT -->
			<div class="page-content-wrapper">

				<tiles:insertAttribute name="body" />
			</div>
			<!-- END CONTENT -->
			<!-- BEGIN QUICK SIDEBAR -->
			<!--Cooming Soon...-->
			<!-- END QUICK SIDEBAR -->
		</div>
		<!-- END CONTAINER -->
		<!-- BEGIN FOOTER -->
		<div class="page-footer">
			<div class="copyright">2015 &copy; Fonix Developers. All Rights
				Reserved.</div>
			<div class="scroll-to-top">
				<i class="icon-arrow-up"></i>
			</div>
		</div>
		<!-- END FOOTER -->
	</div>


	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
<script src="<c:url value='/resources/global/plugins/respond.min.js'/>"></script>
<script src="<c:url value='/resources/global/plugins/excanvas.min.js'/>"></script> 
<![endif]-->
	<script
		src="<c:url value='/resources/global/plugins/jquery-migrate-1.2.1.min.js'/>"
		type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script
		src="<c:url value='/resources/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/global/plugins/bootstrap/js/bootstrap.min.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/global/plugins/jquery.blockui.min.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/global/plugins/jquery.cokie.min.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/global/plugins/uniform/jquery.uniform.min.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js'/>"
		type="text/javascript"></script>

	<!-- END CORE PLUGINS -->

	<script type="text/javascript"
		src="<c:url value='/resources/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js'/>"></script>


	<script type="text/javascript"
		src="<c:url value='/resources/global/plugins/clockface/js/clockface.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/global/plugins/bootstrap-daterangepicker/moment.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/global/plugins/bootstrap-daterangepicker/daterangepicker.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/global/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js'/>"></script>


	<script type="text/javascript"
		src="<c:url value='/resources/scripts/components-editors.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/global/plugins/bootstrap-summernote/summernote.min.js'/>"></script>



	<script
		src="<c:url value='/resources/global/plugins/fullcalendar/fullcalendar/fullcalendar.min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/resources/global/scripts/metronic.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/resources/scripts/layout.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/resources/scripts/demo.js'/>"
		type="text/javascript"></script>

	<script src="<c:url value='/resources/scripts/components-pickers.js'/>"
		type="text/javascript"></script>


	<script
		src="<c:url value='/resources/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js'/>"
		type="text/javascript"></script>

	<!-- END JAVASCRIPTS -->
	<script>
		jQuery(document).ready(function() {
			// initiate layout and plugins
			Metronic.init(); // init metronic core components
			Layout.init(); // init current layout
			Demo.init(); // init demo features
			ComponentsPickers.init();
			ComponentsEditors.init();
			/* 			Calendar.init(); */

		});
	</script>
</h:body>
</html>