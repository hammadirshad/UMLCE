<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<meta charset="utf-8" />
<title><spring:message code='login.title' /></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&amp;subset=all"
	rel="stylesheet" type="text/css">
<link
	href="<c:url value='/resources/global/plugins/font-awesome/css/font-awesome.min.css' />"
	rel="stylesheet" type="text/css">
<link
	href="<c:url value='/resources/global/plugins/simple-line-icons/simple-line-icons.min.css' />"
	rel="stylesheet" type="text/css">
<link
	href="<c:url value='/resources/global/plugins/bootstrap/css/bootstrap.min.css' />"
	rel="stylesheet" type="text/css">
<link
	href="<c:url value='/resources/global/plugins/uniform/css/uniform.default.css' />"
	rel="stylesheet" type="text/css">
<link
	href="<c:url value='/resources/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css' />"
	rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link
	href="<c:url value='/resources/global/plugins/select2/select2.css' />"
	rel="stylesheet" type="text/css" />
<link href="<c:url value='/resources/css/login-soft.css' />"
	rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN THEME STYLES -->
<link href="<c:url value='/resources/global/css/components.css' />"
	rel="stylesheet" type="text/css" />
<link href="<c:url value='/resources/global/css/plugins.css' />"
	rel="stylesheet" type="text/css" />
<link href="<c:url value='/resources/css/layout.css' />"
	rel="stylesheet" type="text/css" />
<link id="style_color"
	href="<c:url value='/resources/css/themes/default.css' />"
	rel="stylesheet" type="text/css" />
<link href="<c:url value='/resources/css/custom.css' />"
	rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->

<body class="login" ng-app="">
	<!-- BEGIN LOGO -->
	<div class="logo">
		<a href="index-2.html"> <img
			src="<c:url value='/resources/img/logo-big.png' />" alt="" />
		</a>
	</div>
	<!-- END LOGO -->
	<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
	<div class="menu-toggler sidebar-toggler"></div>
	<!-- END SIDEBAR TOGGLER BUTTON -->
	<!-- BEGIN LOGIN -->
	<div class="content">
		<!-- BEGIN LOGIN FORM -->
		<form class="login-form" method="post"
			action="j_spring_security_check">
			<div ng-controller="loginController">
				<h3 class="form-title">
					<spring:message code="login.header" />
				</h3>



				<div class="alert alert-danger"
					ng-class="{'': displayLoginError == true, 'display-hide': displayLoginError == false}">
					<button class="close" data-close="alert"></button>
					<span><spring:message code="login.error" /></span>
				</div>


				<div class="form-group">
					<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
					<label class="control-label visible-ie8 visible-ie9">Username</label>
					<div class="input-icon">
						<i class="fa fa-user"></i> <input name="j_username"
							id="j_username" type="text"
							class="form-control placeholder-no-fix"
							class="form-control placeholder-no-fix"
							placeholder="<spring:message code='login.username' />"><br />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label visible-ie8 visible-ie9">Password</label>
					<div class="input-icon">
						<i class="fa fa-lock"></i> <input name="j_password"
							id="j_password" type="password"
							class="form-control placeholder-no-fix" autocomplete="off"
							placeholder="<spring:message code='login.password' />">
					</div>
				</div>
				<div class="form-actions">
					<label class="checkbox"> <input type="checkbox"
						name="remember" value="1" /> Remember me
					</label>

					<button type="submit" name="submit" class="btn blue pull-right">
						<spring:message code="login.signIn" />
					</button>
				</div>
				<div class="login-options">
					<h4>Or login with</h4>
					<ul class="social-icons">
						<li><a class="facebook" data-original-title="facebook"
							href="#"> </a></li>
						<li><a class="twitter" data-original-title="Twitter" href="#">
						</a></li>
						<li><a class="googleplus" data-original-title="Goole Plus"
							href="#"> </a></li>
						<li><a class="linkedin" data-original-title="Linkedin"
							href="#"> </a></li>
					</ul>
				</div>
			</div>
		</form>
		<!-- END LOGIN FORM -->
		<!-- END REGISTRATION FORM -->
	</div>
	<!-- END LOGIN -->
	<!-- BEGIN COPYRIGHT -->
	<div class="copyright">2015 &copy; Fonix Developers. All Rights
		Reserved.</div>
	<!-- END COPYRIGHT -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
<script src="<c:url value='/resources/global/plugins/respond.min.js' />"></script>
<script src="<c:url value='/resources/global/plugins/excanvas.min.js' />"></script> 
<![endif]-->
	<script
		src="<c:url value='/resources/global/plugins/jquery-1.11.0.min.js' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/global/plugins/jquery-migrate-1.2.1.min.js' />"
		type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script
		src="<c:url value='/resources/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/global/plugins/bootstrap/js/bootstrap.min.js' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/global/plugins/jquery.blockui.min.js' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/global/plugins/jquery.cokie.min.js' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/global/plugins/uniform/jquery.uniform.min.js' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js' />"
		type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script
		src="<c:url value='/resources/global/plugins/jquery-validation/js/jquery.validate.min.js' />"
		type="text/javascript"></script>
	<script
		src="<c:url value='/resources/global/plugins/backstretch/jquery.backstretch.min.js' />"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/global/plugins/select2/select2.min.js' />"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="<c:url value='/resources/global/scripts/metronic.js' />"
		type="text/javascript"></script>
	<script src="<c:url value='/resources/scripts/layout.js' />"
		type="text/javascript"></script>
	<script src="<c:url value='/resources/scripts/demo.js' />"
		type="text/javascript"></script>
	<script src="<c:url value='/resources/scripts/login-soft.js' />"
		type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script>
		jQuery(document).ready(
				function() {
					Metronic.init(); // init metronic core components
					Layout.init(); // init current layout
					Demo.init(); // init demo features
					Login.init();
					// init background slide images
					$.backstretch([
							"<c:url value='/resources/media/bg/1.jpg' />",
							"<c:url value='/resources/media/bg/2.jpg' />",
							"<c:url value='/resources/media/bg/3.jpg' />",
							"<c:url value='/resources/media/bg/4.jpg' />" ], {
						fade : 1000,
						duration : 8000
					});
				});
	</script>
	<script src="<c:url value='/resources/js/angular.min.js' />"></script>
	<script src="<c:url value='/resources/js/pages/login.js' />"></script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>