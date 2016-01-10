<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page-content">

	<!-- BEGIN PAGE HEADER-->

	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li><i class="fa fa-home"></i> <a href="/portal">Home</a> <i
				class="fa fa-angle-right"></i></li>
			<li><a href="#">Assignment</a> <i class="fa fa-angle-right"></i></li>
			<li><a href="#">Plagarism</a></li>
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
								<h3 class="form-section">Assignment List</h3>
								<div class="portlet-body">
									<div class="table-scrollable">
										<table class="table table-hover">
											<thead>
												<tr>
													<th>ID</th>
													<th>Title</th>
													<th>End Date</th>
													<th>Submissions</th>
													<th>Plagarism < 50%</th>
													<th>Plagarism > 50%</th>
													<th>Plagarism > 80%</th>
													<th>Plagarism > Average</th>

												</tr>
											</thead>
											<tbody>
												<c:forEach items="${plagarisms}" var="plagarism">
													<tr>
														<td>${plagarism.id}</td>
														<td>${plagarism.name}</td>
														<td>${plagarism.submissiondate}</td>
														<td>${plagarism.submissions}</td>
														<td><c:if test="${plagarism.less50>0}">
																<a
																	href="userplagarism?assignment=${plagarism.id}&plagarism=less50">
															</c:if> ${plagarism.less50}&nbsp;Students</td>
														<td><c:if test="${plagarism.above50>0}">
																<a
																	href="userplagarism?assignment=${plagarism.id}&plagarism=above50">
															</c:if> ${plagarism.above50}&nbsp;Students</td>
														<td><c:if test="${plagarism.above80>0}">
																<a
																	href="userplagarism?assignment=${plagarism.id}&plagarism=above80">
															</c:if> ${plagarism.above80}&nbsp;Students</td>
														<td><c:if test="${plagarism.aboveaverage>0}">
																<a
																	href="userplagarism?assignment=${plagarism.id}&plagarism=aboveaverage">
															</c:if> ${plagarism.aboveaverage}&nbsp;Students</td>
													</tr>



												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
								<h3 class="form-section">Graph View</h3>
								<div class="row">
									<div class="col-md-12">
										<c:url value="${graph}" var="barChart" />
										<img src="${barChart}" class="img-responsive" />
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