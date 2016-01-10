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
			<li><a href="#">Roles</a> <i class="fa fa-angle-right"></i></li>
			<li><a href="#">Add New Role</a></li>
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
						<c:url value="/portal/role/create" var="loginUrl" />
						<form:form action="${loginUrl}" method="post"
							modelAttribute="rolebean" class="horizontal-form">
							<div class="form-body">
								<h3 class="form-section">Role Info</h3>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label">Role Name</label>
											<form:input type="text" id="name" path="name"
												value="${rolebean.name}" class="form-control"
												placeholder="Role Name" />
										</div>
									</div>
									<!--/span-->
								</div>
								<!--/row-->
								<h3 class="form-section">Permisions</h3>
								<div class="row">

									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-2">Submit</label>
											<form:select path="permissions" class="form-control"
												multiple="true">
												<form:options items="${permissionList}" itemLabel="name"
													itemValue="id" />
											</form:select>
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