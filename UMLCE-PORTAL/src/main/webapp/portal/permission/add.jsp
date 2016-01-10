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
			<li><a href="#">Permissions</a> <i class="fa fa-angle-right"></i></li>
			<li><a href="#">Add Permissions</a></li>
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
						<c:url value="/portal/permission/add" var="loginUrl" />
						<form:form action="${loginUrl}" method="post"
							modelAttribute="permissionbean" class="horizontal-form">
							<div class="form-body">
								<h3 class="form-section">Permission Info</h3>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label">Permission Name</label>
											<form:select path="id" class="form-control">
												<form:options items="${permissionList}" itemLabel="name"
													itemValue="id" />
											</form:select>

										</div>
									</div>
									<!--/span-->
								</div>
								<!--/row-->
								<h3 class="form-section">Permision Levels</h3>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-2">View</label>
											<form:checkbox path="levels" value="view" class="make-switch"
												data-on-text="ON" />
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-2">Submit</label>
											<form:checkbox path="levels" value="submit"
												class="make-switch" data-on-text="ON" />
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-2">Add</label>
											<form:checkbox path="levels" value="add" class="make-switch"
												data-on-text="ON" />
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-2">Delete</label>
											<form:checkbox path="levels" value="delete"
												class="make-switch" data-on-text="ON" />
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="row">

									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-2">Update</label>
											<form:checkbox path="levels" value="update"
												class="make-switch" data-on-text="ON" />
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-2">Grade</label>
											<form:checkbox path="levels" value="grade"
												class="make-switch" data-on-text="ON" />
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