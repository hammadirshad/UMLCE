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
			<li><a href="#">Create</a></li>
		</ul>

	</div>
	<div class="row">
		<div class="col-md-12">
			<div
				class="tabbable tabbable-custom tabbable-noborder tabbable-reversed">
				<div class="portlet box blue">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-gift"></i>Create New Assignment
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
						<c:url value="/portal/assignment/create" var="loginUrl" />
						<form:form action="${loginUrl}" method="post"
							modelAttribute="assignmentbean" class="horizontal-form"
							enctype="multipart/form-data">

							<div class="form-body">
								<h3 class="form-section">Assignment Info</h3>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label">Assignment Title</label>
											<form:input type="text" id="tile" path="title"
												value="${assignmentbean.title}" class="form-control"
												placeholder="Assignment Title" />
											<span class="help-block" style="color: red;"> <form:errors
													path="title" /></span>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label">Submission Date</label>
											<div class="input-group date form_datetime">
												<form:input id="enddate" path="endDate"
													value="${assignmentbean.endDate}"
													class="form-control datepicker"
													placeholder="Submission Date" />
												<span class="input-group-btn">
													<button class="btn default date-set" type="button">
														<i class="fa fa-calendar"></i>
													</button>
												</span> <span class="help-block" style="color: red;"> <form:errors
														path="endDate" /></span>
											</div>


										</div>
									</div>
									<!--/span-->
								</div>
								<!--/row-->

								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label">Diagram Type</label>
											<form:select path="diagram" class="form-control">
												<form:options items="${diagramType}" />
											</form:select>
											<span class="help-block" style="color: red;"> <form:errors
													path="diagram" /></span>
										</div>
									</div>

									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label">Upload Attachments</label>
											<form:input path="files" type="file"
												class="btn white fileinput-button" name="files"
												multiple="multiple" />
										</div>
									</div>

								</div>
								<br></br>
								<div class="row">

									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-5">Check Mistakes</label>
											<form:checkbox path="mistakes"
												value="${assignmentbean.mistakes}" class="make-switch"
												data-on-text="ON" />
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-5">Check Plagarism</label>
											<form:checkbox path="plagarism"
												value="${assignmentbean.plagarism}" class="make-switch"
												data-on-text="ON" />
										</div>
									</div>
								</div>
								<br></br>

								<div class="row">

									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-5">Code Mapping</label>
											<form:checkbox path="codemapping"
												value="${assignmentbean.codemapping}" class="make-switch"
												data-on-text="ON" />
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-5">Upload Source
												Code</label>
											<form:input path="sourcecode" type="file"
												class="fileinput-button" name="sourcecode"
												multiple="multiple" />
											(.zip)
										</div>
									</div>
								</div>


								<div class="row">

									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-5">Evaluation</label>
											<form:checkbox path="evaluation"
												value="${assignmentbean.evaluation}" class="make-switch"
												data-on-text="ON" />
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-5">Upload
												Reference Model</label>
											<form:input type="file" class="fileinput-button"
												name="referencemodel" multiple="multiple"
												path="referencemodel" />
											(.uml)
										</div>
									</div>
								</div>



								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label">Description</label><br></br>
											<div class="col-md-12">
												<form:textarea name="summernote" id="summernote_1"
													path="description" />
												<span class="help-block" style="color: red;"> <form:errors
														path="description" /></span>
											</div>
										</div>
									</div>
								</div>
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