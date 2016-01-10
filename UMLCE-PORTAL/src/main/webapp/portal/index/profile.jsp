<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page-content">

	<!-- BEGIN PAGE HEADER-->


	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li><i class="fa fa-home"></i> <a href="index-2.html">Home</a> <i
				class="fa fa-angle-right"></i></li>
			<li><a href="#">User Profile</a></li>
		</ul>
	</div>
	<!-- END PAGE HEADER-->
	<!-- BEGIN PAGE CONTENT-->
	<div class="row profile">
		<div class="col-md-12">
			<!--BEGIN TABS-->
			<div class="tabbable tabbable-custom tabbable-noborder">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tab_1_1" data-toggle="tab">
							Overview </a></li>
					<li><a href="#tab_1_3" data-toggle="tab"> Account </a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="tab_1_1">
						<div class="row">
							<div class="col-md-3">
								<ul class="list-unstyled profile-nav">
									<li><img src="<c:url value='/portal/profile_pic'/>"
										class="img-responsive" alt="" /></li>
								</ul>
							</div>
							<div class="col-md-9">
								<div class="row">
									<div class="col-md-8 profile-info">
										<h1>${user.fname}&nbsp;${user.lname}</h1>


										<ul class="list-inline">
											<c:if test="${user.gender=='M'}">
												<li><i class="fa fa-male"></i> Male</li>
												<br></br>
											</c:if>
											<c:if test="${user.gender=='F'}">
												<li><i class="fa fa-female"></i> Female</li>
												<br></br>
											</c:if>
											<li><i class="fa fa-envelope"></i> ${user.email}</li>
											<br></br>
											<li><i class="fa fa-phone"></i> ${user.phone}</li>
											<br></br>
											<li><i class="fa fa-home"></i> ${user.address}</li>
											<br></br>
										</ul>
									</div>
									<!--end col-md-8-->
									<div class="col-md-4">
										<div class="portlet sale-summary">
											<div class="portlet-title">
												<div class="caption">Summary</div>
												<div class="tools">
													<a class="reload" href="javascript:;"> </a>
												</div>
											</div>
											<div class="portlet-body">
												<ul class="list-unstyled">
													<li><span class="sale-info"> ASSIGNMENTS <i
															class="fa fa-img-up"></i>
													</span> <span class="sale-num"> ${assignmentcount} </span></li>
													<li><span class="sale-info"> SUBMISSIONS <i
															class="fa fa-img-down"></i>
													</span> <span class="sale-num"> ${submissioncount} </span></li>
													<li><span class="sale-info"> AVERAGE MARKS </span> <span
														class="sale-num"> ${marks}% </span></li>
												</ul>
											</div>
										</div>
									</div>
									<!--end col-md-4-->
								</div>
								<!--end row-->

							</div>
						</div>
					</div>
					<!--tab_1_2-->
					<div class="tab-pane" id="tab_1_3">
						<div class="row profile-account">
							<div class="col-md-3">
								<ul class="ver-inline-menu tabbable margin-bottom-10">
									<li class="active"><a data-toggle="tab" href="#tab_1-1">
											<i class="fa fa-cog"></i> Personal info
									</a> <span class="after"> </span></li>
									<li><a data-toggle="tab" href="#tab_2-2"> <i
											class="fa fa-picture-o"></i> Change Avatar
									</a></li>
									<li><a data-toggle="tab" href="#tab_3-3"> <i
											class="fa fa-lock"></i> Change Password
									</a></li>
								</ul>
							</div>
							<div class="col-md-9">
								<div class="tab-content">
									<div id="tab_1-1" class="tab-pane active">
										<c:url value="/portal/profile#tab_1-1" var="loginUrl" />
										<form:form action="${loginUrl}" method="post"
											modelAttribute="userbean" class="horizontal-form">
											<div class="form-group">
												<form:hidden id="userName" path="username"
													value="${userdata.username}" class="form-control"
													placeholder="First Name" />
												<label class="control-label">First Name</label>
												<form:input type="text" id="firstName" path="fname"
													value="${userdata.fname}" class="form-control"
													placeholder="First Name" />
												<span class="help-block" style="color: red;"> <form:errors
														path="fname" />
												</span>
											</div>
											<div class="form-group">
												<label class="control-label">Last Name</label>
												<form:input type="text" id="lastName" path="lname"
													value="${userdata.lname}" class="form-control"
													placeholder="Last Name" />
												<span class="help-block" style="color: red;"> <form:errors
														path="lname" />
												</span>
											</div>
											<div class="form-group">
												<label class="control-label">Mobile Number</label>
												<form:input type="text" path="phoneno"
													value="${userdata.phone}" class="form-control"
													placeholder="0300-0000000" />
												<span class="help-block" style="color: red;"> <form:errors
														path="phoneno" />
												</span>
											</div>
											<div class="form-group">
												<label class="control-label">Email</label>
												<form:input type="email" id="email" path="email"
													value="${userdata.email}" class="form-control"
													placeholder="email" />
												<span class="help-block" style="color: red;"> <form:errors
														path="email" />
												</span>
											</div>
											<div class="form-group">
												<label class="control-label">Address</label>
												<form:input type="text" path="address"
													value="${userdata.address}" class="form-control" />
												<span class="help-block" style="color: red;"> <form:errors
														path="address" />
												</span>
											</div>
											<div class="margiv-top-10">
												<input type="submit" value="Save Changes" class="btn green" />
												<input type="reset" value="Cancel" class="btn default" />

											</div>
										</form:form>
									</div>
									<div id="tab_2-2" class="tab-pane">
										<c:url value="/portal/profile/changePicture#tab_2-2"
											var="picUrl" />
										<form:form action="${picUrl}" method="post"
											modelAttribute="passbean" class="horizontal-form"
											enctype="multipart/form-data">
											<div class="form-group">
												<div class="fileinput fileinput-new"
													data-provides="fileinput">
													<div class="fileinput-preview fileinput-exists thumbnail"
														style="max-width: 200px; max-height: 150px;"></div>
													<div>
														<span class="btn default btn-file"> <input
															type="file" class="fileinput-button" name="picture" />
														</span> <input type="reset" value="Remove" class="btn default" />
													</div>
												</div>
												<div class="clearfix margin-top-10">
													<span class="label label-danger"> NOTE! </span> <span>
														Attached image only .jpg,.png </span>
												</div>
											</div>
											<div class="margin-top-10">
												<input type="submit" value="Change Picture"
													class="btn green" /> <input type="reset" value="Cancel"
													class="btn default" />
											</div>

										</form:form>
									</div>
									<div id="tab_3-3" class="tab-pane">
										<c:url value="/portal/profile/changePassword#tab_3-3"
											var="passUrl" />
										<form:form action="${passUrl}" method="post"
											modelAttribute="passbean" class="horizontal-form">
											<div class="form-group">
												<label class="control-label">Current Password</label>
												<form:input type="password" path="oldpass"
													value="${passbean.oldpass}" class="form-control" />
												<span class="help-block" style="color: red;"> <form:errors
														path="oldpass" />
												</span>
											</div>
											<div class="form-group">
												<label class="control-label">New Password</label>
												<form:input type="password" path="newpass"
													value="${passbean.newpass}" class="form-control" />
												<span class="help-block" style="color: red;"> <form:errors
														path="newpass" />
												</span>
											</div>
											<div class="form-group">
												<label class="control-label">Re-type New Password</label>
												<form:input type="password" path="renewpass"
													value="${passbean.renewpass}" class="form-control" />
												<span class="help-block" style="color: red;"> <form:errors
														path="renewpass" />
												</span> <span class="help-block" style="color: red;">
													${fieldMatch} </span> <span class="help-block" style="color: red;">
													${currentPassword} </span>
											</div>
											<div class="margin-top-10">
												<input type="submit" value="Change Password"
													class="btn green" /> <input type="reset" value="Cancel"
													class="btn default" />
											</div>
										</form:form>
									</div>

								</div>
							</div>
							<!--end col-md-9-->
						</div>
					</div>

					<!--end tab-pane-->
				</div>
			</div>
			<!--END TABS-->
		</div>
	</div>
	<!-- END PAGE CONTENT-->

</div>

