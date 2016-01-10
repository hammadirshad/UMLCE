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
			<li><a href="#">${type}</a> <i class="fa fa-angle-right"></i></li>
			<li><a href="#">Mistakes</a></li>
		</ul>

	</div>
	<div class="row">
		<div class="col-md-12">
			<div
				class="tabbable tabbable-custom tabbable-noborder tabbable-reversed">
				<div class="portlet box blue">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-gift"></i>Class Mistakes
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
						<c:url value="/portal/assignment/classReports/mistakes"
							var="loginUrl" />
						<form:form action="${loginUrl}" method="post"
							modelAttribute="assignmentbean" class="horizontal-form">
							<div class="form-body">

								<h3 class="form-section">Report Info</h3>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<c:url value="${report}type=html" var="htmlUrl">
											</c:url>
											<c:url value="${report}type=pdf" var="pdfUrl">
											</c:url>
											<c:url value="${report}type=excel" var="excelUrl">
											</c:url>
											<label class="control-label">Download : </label> <a
												href="${pdfUrl}"><i class="fa fa-file-pdf-o"></i> PDF</a> <a
												href="${excelUrl}"><i class="fa fa-file-excel-o"></i>
												EXCEL</a> <a href="${htmlUrl}"><i class="fa fa-html5"></i>
												HTML</a>
										</div>
									</div>
								</div>
								<h3 class="form-section">Mistakes List</h3>
								<div class="portlet-body">
									<div class="table-scrollable">
										<table class="table table-hover">
											<thead>
												<tr>
													<th>Name</th>
													<th>Type</th>
													<th>Model Type</th>
													<th>Element Name</th>
													<th>Count</th>
													<th></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${mistakes}" var="mistake">
													<tr>
														<td>${mistake.errorName}</td>
														<td>${mistake.type}</td>
														<td>${mistake.modelType}</td>
														<td>${mistake.elementName}</td>
														<td>${mistake.count}</td>
														<td></td>
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