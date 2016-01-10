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
			<li><a href="#">Users List</a></li>
		</ul>

	</div>
	<div class="row">
		<div class="col-md-12">
			<div
				class="tabbable tabbable-custom tabbable-noborder tabbable-reversed">
				<div class="portlet box blue">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-gift"></i>Created Assignment
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
						<c:url value="/portal/assignment/view" var="loginUrl" />
						<form:form action="${loginUrl}" method="post"
							modelAttribute="assignmentbean" class="horizontal-form">
							<div class="form-body">
								<h3 class="form-section">User List</h3>
								<div class="portlet-body">
									<div class="table-scrollable">
										<table class="table table-hover">
											<thead>
												<tr>
													<th>Username</th>
													<th>First Name</th>
													<th>Last Name</th>
													<th>Gender</th>
													<th>Email</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${userList}" var="user">
													<tr>
														<td>${user.username}</td>
														<td>${user.fname}</td>
														<td>${user.lname}</td>
														<td><c:if test="${user.gender.equals('M')}">
											 Male</c:if> <c:if test="${user.gender.equals('F')}">
											 Female</c:if></td>
														<td>${user.email}</td>
													</tr>



												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>

							</div>
						</form:form>
						<!-- END FORM-->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>