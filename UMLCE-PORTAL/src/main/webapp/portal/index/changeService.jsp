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
			<li><a href="#">Change Service</a></li>
		</ul>
	</div>
	<!-- END PAGE HEADER-->
	<!-- BEGIN PAGE CONTENT-->
	<div class="row profile">
		<div class="col-md-12">
			<!--BEGIN TABS-->
			<div class="tab-content">
				<div class="row profile-account">
					<div class="col-md-9">
						<div class="tab-content">
							<c:url value="/portal/service" var="passUrl" />
							<form:form action="${passUrl}" method="post"
								modelAttribute="servicebean" class="horizontal-form">
								<div class="form-group">
									<label class="control-label">Host Address</label>
									<form:input type="text" path="host" value="${servicebean.host}"
										class="form-control" />
									<span class="help-block" style="color: red;"> <form:errors
											path="host" />
									</span>
								</div>
								<div class="form-group">
									<label class="control-label">Port Number</label>
									<form:input type="text" path="port" value="${servicebean.port}"
										class="form-control" />
									<span class="help-block" style="color: red;"> <form:errors
											path="port" />
									</span>
								</div>
								<div class="form-group">
									<label class="control-label">Service Name</label>
									<form:input type="text" path="service"
										value="${servicebean.service}" class="form-control" />
									<span class="help-block" style="color: red;"> <form:errors
											path="service" />
									</span> <span class="help-block" style="color: red;"> ${syntax}
									</span>
								</div>
								<div class="margin-top-10">
									<input type="submit" value="Change Service" class="btn green" />
									<input type="reset" value="Cancel" class="btn default" />
								</div>
							</form:form>

						</div>
					</div>
					<!--end col-md-9-->
				</div>

				<!--end tab-pane-->
			</div>
			<!--END TABS-->
		</div>
	</div>
	<!-- END PAGE CONTENT-->

</div>

