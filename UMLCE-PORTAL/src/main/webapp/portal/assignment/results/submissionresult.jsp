<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page-content">

	<!-- BEGIN PAGE HEADER-->

	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li><i class="fa fa-home"></i> <a href="/portal">Home</a> <i
				class="fa fa-angle-right"></i></li>
			<li><a href="#">Assignment</a> <i class="fa fa-angle-right"></i></li>
			<li><a href="#">Result</a></li>
		</ul>

	</div>
	<div class="row">
		<div class="col-md-12">
			<div
				class="tabbable tabbable-custom tabbable-noborder tabbable-reversed">
				<div class="portlet box blue">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-gift"></i>Result
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
						<c:url value="portal/submissions/results/submissionresults"
							var="loginUrl">
						</c:url>
						<div class="form-body">
							<h3 class="form-section">Assignment Info</h3>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label">Assignment Title : </label> <label
											class="control-label">${title}</label>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label">Submission Date : </label> <label
											class="control-label">${submitionDate}</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label">Diagram Type : </label> <label
											class="control-label">${type}</label>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<c:url
											value="/portal/submissions/results/submissionresultsdownload?submission=${id}&type=html"
											var="htmlUrl">
										</c:url>
										<c:url
											value="/portal/submissions/results/submissionresultsdownload?submission=${id}&type=pdf"
											var="pdfUrl">
										</c:url>
										<c:url
											value="/portal/submissions/results/submissionresultsdownload?submission=${id}&type=excel"
											var="excelUrl">
										</c:url>
										<label class="control-label">Download : </label> <a
											href="${pdfUrl}"><i class="fa fa-file-pdf-o"></i> PDF</a> <a
											href="${excelUrl} }"><i class="fa fa-file-excel-o"></i>
											EXCEL</a> <a href="${htmlUrl}"><i class="fa fa-html5"></i>
											HTML</a>
									</div>
								</div>
							</div>
							<!--/span-->
						</div>
						<div class="tabbable-custom nav-justified">
							<ul class="nav nav-tabs nav-justified">
								<c:if test="${submission.assignment.mistakes==true}">
									<li><a href="#tab_1_1_1" data-toggle="tab"> Mistakes </a></li>
								</c:if>
								<c:if test="${submission.assignment.evaluation==true}">
									<li><a href="#tab_1_1_2" data-toggle="tab"> Evaluation
											Results </a></li>
								</c:if>
								<c:if test="${submission.assignment.codemapping==true}">
									<li><a href="#tab_1_1_4" data-toggle="tab"> Code
											Mapping </a></li>
								</c:if>
								<c:if test="${submission.assignment.plagarism==true}">
									<li><a href="#tab_1_1_3" data-toggle="tab"> Plagarism
											Results </a></li>
								</c:if>
							</ul>
							<div class="tab-content">

								<div class="tab-pane active" id="tab_1_1_1">

									<div class="table-scrollable table-scrollable-borderless">

										<table class="table table-hover">
											<thead>
												<tr>
													<th>Mistake Type</th>
													<th>Model Type</th>
													<th>Mistake</th>
													<th>Element</th>
													<th>Description</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${mistakes}" var="mistake"
													varStatus="status">
													<tr>
														<td>${mistake.type}</td>
														<td>${mistake.modelType}</td>
														<td>${mistake.errorName}</td>
														<td>${mistake.elementName}</td>
														<td>${mistake.errorDescription}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<br />
									<hr />
								</div>

								<div class="tab-pane" id="tab_1_1_2">

									<div class="table-scrollable table-scrollable-borderless">
										<div class="row list-separated col-md-offset-1">
											<div class="col-md-3 col-sm-3 col-xs-6">
												<div class="font-grey-mint font-sm">
													<b>Total Marks:</b>
												</div>
												<div class="uppercase font-hg font-red-flamingo">
													<span>${submission.assignment.totalMarks}</span>
												</div>
											</div>
											<div class="col-md-3 col-sm-3 col-xs-6">
												<div class="font-grey-mint font-sm">
													<b>Student Marks: </b>
												</div>
												<div class="uppercase font-hg theme-font">
													<span>${submission.marks}</span>
												</div>
											</div>

										</div>
										<br />
										<hr />
										<c:forEach items="${evalmistakes}" var="details">
											<div class="alert alert-danger col-md-6">
												<b>Mistake Name: </b> <span>${details.errorName}</span> <b>Mistake
													Count:</b> <span>${details.count}</span>
											</div>
											<br />

											<table class="table table-hover">
												<thead>
													<tr>
														<th>Element Name</th>
														<th>Error Description</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${details.errordetail}" var="mistake"
														varStatus="status">
														<tr>
															<td>${mistake.elementName}</td>
															<td>${mistake.description}</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
											<br />
										</c:forEach>

									</div>
									<br />
									<hr />
								</div>
								<div class="tab-pane" id="tab_1_1_4">

									<div class="table-scrollable table-scrollable-borderless">

										<table class="table table-hover">
											<thead>
												<tr>
													<th>Class Diagram Attribute</th>
													<th>Mapping Code Mistake</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${mappingmistakes}" var="mistake"
													varStatus="status">
													<tr>
														<td>${mistake.elementName}</td>
														<td>${mistake.errorDescription}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>

									</div>
									<br />
									<hr />
								</div>
								<div class="tab-pane" id="tab_1_1_3">

									<div class="table-scrollable table-scrollable-borderless">
										<table class="table table-hover">
											<thead>
												<tr>
													<th>Student1</th>
													<th>Student2</th>
													<th>Plagarism Persentage</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${plagarisms}" var="plagarism"
													varStatus="status">
													<tr>
														<td>${plagarism.student1.fname}&nbsp;${plagarism.student1.lname}</td>
														<td>${plagarism.student2.fname}&nbsp;${plagarism.student2.lname}</td>
														<td>${plagarism.plagPersentage}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>

									</div>
									<br />
									<hr />
								</div>
							</div>
						</div>
					</div>
					<!-- END FORM-->
				</div>
			</div>
		</div>
	</div>
</div>
</div>