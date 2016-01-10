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
			<li><a href="#">Results</a></li>
		</ul>

	</div>
	<div class="row">
		<div class="col-md-12">
			<div
				class="tabbable tabbable-custom tabbable-noborder tabbable-reversed">
				<div class="portlet box blue">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-gift"></i>Assignment Results
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
													<th>Submissions</th>
													<th>Max Marks</th>
													<th>Min Marks</th>
													<th>Average Marks</th>
													<th>Total Marks</th>
													<th>Above Average</th>
													<th>Below Average</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${evaluations}" var="evaluation">
													<tr>
														<td>${evaluation.id}</td>
														<td>${evaluation.name}</td>
														<td>${evaluation.count}</td>
														<td>${evaluation.maxmarks}</td>
														<td>${evaluation.minmarks}</td>
														<td>${evaluation.marks}</td>
														<td>${evaluation.total}</td>
														<td>${evaluation.aboveaverage}&nbsp;Students</td>
														<td>${evaluation.lessaverage}&nbsp;Students</td>
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