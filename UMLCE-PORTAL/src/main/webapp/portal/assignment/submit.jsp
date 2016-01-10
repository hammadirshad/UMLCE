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
			<li><a href="#">Submission</a></li>
		</ul>

	</div>
	<div class="row">
		<div class="col-md-12">
			<div
				class="tabbable tabbable-custom tabbable-noborder tabbable-reversed">
				<div class="portlet box blue">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-gift"></i>Submit Assignment
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
						<c:url
							value="/portal/assignment/submit?query=${assignmentbean.id}"
							var="loginUrl" />
						<form:form action="${loginUrl}" method="post"
							modelAttribute="submissionbean" class="horizontal-form"
							enctype="multipart/form-data">
							<form:input type="hidden" id="query" path="query"
								value="${assignmentbean.id}" />
							<span class="help-block" style="color: red;"> <form:errors
									path="query" /></span>
							<div class="form-body">
								<div class="row">
									<div class="col-md-8">
										<h3>
											Assignment Title: ${assignmentbean.title}&nbsp;
											<c:if test="${status==0}">
												<span class="label label-sm label-warning">
													Unsubmited </span>
											</c:if>
											<c:if test="${status==1}">
												<span class="label label-sm label-success"> Submited
												</span>
											</c:if>
											<c:if test="${status==2}">
												<span class="label label-sm label-danger"> Expired </span>
											</c:if>
										</h3>
										Diagram Type: ${assignmentbean.diagram}<br />
										<h4>Start Date: ${assignmentbean.startDate}</h4>
										<h4>Submission Date: ${assignmentbean.endDate}</h4>

										<strong>Owner:
											${assignmentbean.owner.fname}&nbsp${assignmentbean.owner.lname}</strong><br />
									</div>
									<h4>Attachments</h4>
									<ul>
										<c:forEach items="${resource}" var="file">
											<li><a href="download?query=${file.id}">${file.sourceName}</a></li>
										</c:forEach>
									</ul>

								</div>
								<div class="portlet light">
									<div class="portlet-title">
										<div class="caption">Description</div>
										<div class="tools"></div>
									</div>
									<div class="portlet-body">
										<div class="row">
											<div class="col-md-6">${assignmentbean.description}</div>
										</div>
									</div>
								</div>
								<c:if test="${submit==0}">
									<div class="col-md-11">
										<form:textarea name="summernote" id="summernote_1"
											path="description" />

									</div>
									<div class="row"></div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<br></br> <label class="control-label">Upload
													Attachments</label>
												<form:input path="files" type="file"
													class="btn white fileinput-button" name="files"
													multiple="multiple" />
												<span class="help-block" style="color: red;"> <form:errors
														path="files" /></span>
											</div>
										</div>

									</div>
								</c:if>

								<c:if test="${submit==1}">
									<div class="portlet light">
										<div class="portlet-title">
											<div class="caption">Submission Details</div>
											<div class="tools"></div>
										</div>
										<div class="portlet-body">
											<div class="row">
												<div class="col-md-6">

													${assignmentsubmission.description}</div>
											</div>
										</div>

									</div>
									<div class="row"></div>

									<div class="row">
										<div class="col-md-4">
											<h4>Submission Attachments</h4>
											<ul>
												<c:forEach items="${submissionresource}" var="file">
													<li><a
														href="submission/download?submission=${file.id}">${file.fileName}</a></li>
												</c:forEach>
											</ul>
										</div>
									</div>
								</c:if>

							</div>
							<c:if test="${submit==0}">
								<div class="form-actions right">
									<input type="reset" value="Cancel" class="btn default" /> <input
										type="submit" value="Create" class="btn blue" />
								</div>
							</c:if>
						</form:form>
						<!-- END FORM-->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>