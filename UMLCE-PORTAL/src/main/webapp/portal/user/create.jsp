<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page-content">

	<!-- BEGIN PAGE HEADER-->

	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li><i class="fa fa-home"></i> <a href="/index.xhtml">Home</a> <i
				class="fa fa-angle-right"></i></li>
			<li><a href="#">Users</a> <i class="fa fa-angle-right"></i></li>
			<li><a href="#">Add Users</a></li>
		</ul>

	</div>
	<div class="row">
		<div class="col-md-12">
			<div
				class="tabbable tabbable-custom tabbable-noborder tabbable-reversed">
				<div class="portlet box blue">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-gift"></i>Form Sample
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"> </a> <a
								href="#portlet-config" data-toggle="modal" class="config"> </a>
							<a href="javascript:;" class="reload"> </a> <a
								href="javascript:;" class="remove"> </a>
						</div>
					</div>
					<div class="portlet-body form">
						<!-- BEGIN FORM-->
						<c:url value="/portal/user/create" var="loginUrl" />
						<form:form action="${loginUrl}" method="post"
							modelAttribute="userbean" class="horizontal-form">
							<div class="form-body">
								<h3 class="form-section">Person Info</h3>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label">First Name</label>
											<form:input type="text" id="firstName" path="fname"
												value="${userbean.fname}" class="form-control"
												placeholder="First Name" />
											<span class="help-block" style="color: red;"> <form:errors
													path="fname" />
											</span>
										</div>
									</div>
									<!--/span-->
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label">Last Name</label>
											<form:input type="text" id="lastName" path="lname"
												value="${userbean.lname}" class="form-control"
												placeholder="Last Name" />
											<span class="help-block" style="color: red;"> <form:errors
													path="lname" />
											</span>
										</div>
									</div>
									<!--/span-->
								</div>
								<!--/row-->
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label">Gender</label>
											<form:select path="gender" class="form-control">
												<form:options items="${genderList}" />
											</form:select>
											<span class="help-block" style="color: red;"> <form:errors
													path="gender" />
											</span>
										</div>
									</div>
									<!--/span-->
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label">Phone No.</label>
											<form:input type="text" path="phoneno"
												value="${userbean.phoneno}" class="form-control"
												placeholder="0300-0000000" />
											<span class="help-block" style="color: red;"> <form:errors
													path="phoneno" />
											</span>
										</div>
									</div>
									<!--/span-->
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label">Roles</label>
											<form:select path="roles" class="form-control"
												multiple="true">
												<form:options items="${roleList}" itemLabel="name"
													itemValue="id" />
											</form:select>
											<span class="help-block" style="color: red;"> <form:errors
													path="roles" />
											</span>
										</div>
									</div>

								</div>
								<!--/row-->
								<div class="form-group">
									<label>Address</label>
									<form:input type="text" path="address"
										value="${userbean.address}" class="form-control" />
									<span class="help-block" style="color: red;"> <form:errors
											path="address" />
									</span>
								</div>
								<!--/row-->
								<h3 class="form-section">Account Info</h3>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label">Username</label>
											<form:input type="text" id="username" path="username"
												value="${userbean.username}" class="form-control"
												placeholder="username" />
											<span class="help-block" style="color: red;"> <form:errors
													path="username" />
											</span>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label">Email</label>
											<form:input type="email" id="email" path="email"
												value="${userbean.email}" class="form-control"
												placeholder="email" />
											<span class="help-block" style="color: red;"> <form:errors
													path="email" />
											</span>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label">Password</label>
											<form:input type="password" id="password" path="password"
												value="${userbean.password}" class="form-control"
												placeholder="Password" />
											<span class="help-block" style="color: red;"> <form:errors
													path="password" />
											</span>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label">Confirm Password</label>
											<form:input type="password" id="cpassword" path="confirmpass"
												value="${userbean.confirmpass}" class="form-control"
												placeholder="Confirm Password" />
											<span class="help-block" style="color: red;"> <form:errors
													path="confirmpass" />
											</span> <span class="help-block" style="color: red;">
												${fieldMatch} </span>
										</div>
									</div>
								</div>
								<!--/span-->

							</div>
							<div class="form-actions right">
								<input type="reset" value="Cancel" class="btn default" /> <input
									type="submit" value="Create" class="btn blue" />
							</div>
						</form:form>
						<!-- END FORM-->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
