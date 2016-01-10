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
			<li><a href="#">Evaluation</a></li>
		</ul>

	</div>
	<div class="row">
		<div class="col-md-12">
			<div
				class="tabbable tabbable-custom tabbable-noborder tabbable-reversed">
				<div class="portlet box blue">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-gift"></i>Assignment Evaluation
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
						<c:url value="/portal/assignment/evaluation" var="loginUrl">
						</c:url>
						<form:form action="${loginUrl}" method="post"
							modelAttribute="evaluationcritariabean" class="horizontal-form"
							enctype="multipart/form-data">
							<div class="form-body">
								<form:hidden path="assignment.id"
									value="${evaluationcritariabean.assignment.id}" />
								<c:if
									test="${evaluationcritariabean.assignment.diagram.equals('Activity Diagram')}">
									<div class="tabbable-custom nav-justified">
										<ul class="nav nav-tabs nav-justified">
											<li class="active"><a href="#tab_1_1_1"
												data-toggle="tab"> Actions </a></li>
											<li><a href="#tab_1_1_2" data-toggle="tab">
													Decisions </a></li>
											<li><a href="#tab_1_1_4" data-toggle="tab"> Other
													Nodes </a></li>
											<li><a href="#tab_1_1_3" data-toggle="tab"> Syntax
													and semantic Mistakes </a></li>
										</ul>
										<div class="tab-content">
											<div class="tab-pane active" id="tab_1_1_1">

												<div class="table-scrollable table-scrollable-borderless">

													<table class="table table-hover">
														<thead>
															<tr>
																<th>Action Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.actions}"
																var="action" varStatus="status">
																<tr>
																	<td>${action.elementName}<form:hidden
																			path="actions[${status.index}].elementName"
																			value="${action.elementName}" /> <form:hidden
																			path="actions[${status.index}].type"
																			value="${action.type}" />
																	</td>
																	<td><form:checkbox
																			path="actions[${status.index}].essential"
																			value="${action.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="tile"
																			path="actions[${status.index}].marks"
																			value="${action.marks}" class="form-control"
																			placeholder="Marks" /></td>
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
													<table class="table table-hover">
														<thead>
															<tr>
																<th>Action Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.decisions}"
																var="decision" varStatus="status">
																<tr>
																	<td>${decision.elementName}<form:hidden
																			path="decisions[${status.index}].elementName"
																			value="${decision.elementName}" /> <form:hidden
																			path="decisions[${status.index}].type"
																			value="${decision.type}" />
																	</td>
																	<td><form:checkbox
																			path="decisions[${status.index}].essential"
																			value="${decision.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="dmarks"
																			path="decisions[${status.index}].marks"
																			value="${decision.marks}" class="form-control"
																			placeholder="Marks" /></td>
																</tr>

															</c:forEach>
														</tbody>
													</table>

												</div>
												<br />
												<hr />
											</div>
											<div class="tab-pane" id="tab_1_1_4">

												<div class="table-scrollable table-scrollable-borderless">
													<table class="table table-hover">
														<thead>
															<tr>
																<th>Action Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.nodes}"
																var="node" varStatus="status">
																<tr>
																	<td>${node.elementName}<form:hidden
																			path="nodes[${status.index}].elementName"
																			value="${node.elementName}" /> <form:hidden
																			path="nodes[${status.index}].type"
																			value="${node.type}" />
																	</td>
																	<td><form:checkbox
																			path="nodes[${status.index}].essential"
																			value="${node.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="nmarks"
																			path="nodes[${status.index}].marks"
																			value="${node.marks}" class="form-control"
																			placeholder="Marks" /></td>
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
																<th>Action Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.mistakes}"
																var="mistake" varStatus="status">
																<tr>
																	<td>${mistake.elementName}<form:hidden
																			path="mistakes[${status.index}].elementName"
																			value="${mistake.elementName}" /> <form:hidden
																			path="mistakes[${status.index}].type"
																			value="${mistake.type}" />
																	</td>
																	<td><form:checkbox
																			path="mistakes[${status.index}].essential"
																			value="${mistake.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="nmarks"
																			path="mistakes[${status.index}].marks"
																			value="${mistake.marks}" class="form-control"
																			placeholder="Marks" /></td>
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
								</c:if>





								<c:if
									test="${evaluationcritariabean.assignment.diagram.equals('Domain Model')}">

									<div class="tabbable-custom nav-justified">
										<ul class="nav nav-tabs nav-justified">
											<li class="active"><a href="#tab_1_1_1"
												data-toggle="tab"> Concepts </a></li>
											<li><a href="#tab_1_1_2" data-toggle="tab"> Mistakes
											</a></li>
										</ul>
										<div class="tab-content">
											<div class="tab-pane active" id="tab_1_1_1">

												<div class="table-scrollable table-scrollable-borderless">

													<table class="table table-hover">
														<thead>
															<tr>
																<th>Concept Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.concepts}"
																var="concept" varStatus="status">
																<tr>
																	<td>${concept.elementName}<form:hidden
																			path="concepts[${status.index}].elementName"
																			value="${concept.elementName}" /> <form:hidden
																			path="concepts[${status.index}].type"
																			value="${concept.type}" />
																	</td>
																	<td><form:checkbox
																			path="concepts[${status.index}].essential"
																			value="${concept.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="tile"
																			path="concepts[${status.index}].marks"
																			value="${concept.marks}" class="form-control"
																			placeholder="Marks" /></td>
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
													<table class="table table-hover">
														<thead>
															<tr>
																<th>Mistake Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.mistakes}"
																var="mistake" varStatus="status">
																<tr>
																	<td>${mistake.elementName}<form:hidden
																			path="mistakes[${status.index}].elementName"
																			value="${mistake.elementName}" /> <form:hidden
																			path="mistakes[${status.index}].type"
																			value="${mistake.type}" />
																	</td>
																	<td><form:checkbox
																			path="mistakes[${status.index}].essential"
																			value="${mistake.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="nmarks"
																			path="mistakes[${status.index}].marks"
																			value="${mistake.marks}" class="form-control"
																			placeholder="Marks" /></td>
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

								</c:if>

								<c:if
									test="${evaluationcritariabean.assignment.diagram.equals('Sequence Diagram')}">


									<div class="tabbable-custom nav-justified">
										<ul class="nav nav-tabs nav-justified">
											<li class="active"><a href="#tab_1_1_1"
												data-toggle="tab"> Lifelines </a></li>
											<li><a href="#tab_1_1_2" data-toggle="tab">
													Operations </a></li>
											<li><a href="#tab_1_1_3" data-toggle="tab"> Syntax
													and semantic Mistakes </a></li>
										</ul>
										<div class="tab-content">
											<div class="tab-pane" id="tab_1_1_1">

												<div class="table-scrollable table-scrollable-borderless">
													<table class="table table-hover">
														<thead>
															<tr>
																<th>Lifeline Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.lifelines}"
																var="lifeline" varStatus="status">
																<tr>
																	<td>${lifeline.elementName}<form:hidden
																			path="lifelines[${status.index}].elementName"
																			value="${lifeline.elementName}" /> <form:hidden
																			path="lifelines[${status.index}].type"
																			value="${lifeline.type}" />
																	</td>
																	<td><form:checkbox
																			path="lifelines[${status.index}].essential"
																			value="${lifeline.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="dmarks"
																			path="lifelines[${status.index}].marks"
																			value="${lifeline.marks}" class="form-control"
																			placeholder="Marks" /></td>
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
													<table class="table table-hover">
														<thead>
															<tr>
																<th>Operation Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.oprations}"
																var="operation" varStatus="status">
																<tr>
																	<td>${operation.elementName}<form:hidden
																			path="oprations[${status.index}].elementName"
																			value="${operation.elementName}" /> <form:hidden
																			path="oprations[${status.index}].type"
																			value="${operation.type}" />
																	</td>
																	<td><form:checkbox
																			path="operations[${status.index}].essential"
																			value="${operation.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="nmarks"
																			path="operations[${status.index}].marks"
																			value="${operation.marks}" class="form-control"
																			placeholder="Marks" /></td>
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
																<th>Mistake Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.mistakes}"
																var="mistake" varStatus="status">
																<tr>
																	<td>${mistake.elementName}<form:hidden
																			path="mistakes[${status.index}].elementName"
																			value="${mistake.elementName}" /> <form:hidden
																			path="mistakes[${status.index}].type"
																			value="${mistake.type}" />
																	</td>
																	<td><form:checkbox
																			path="mistakes[${status.index}].essential"
																			value="${mistake.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="nmarks"
																			path="mistakes[${status.index}].marks"
																			value="${mistake.marks}" class="form-control"
																			placeholder="Marks" /></td>
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


								</c:if>

								<c:if
									test="${evaluationcritariabean.assignment.diagram.equals('System Sequence Diagram')}">

									<div class="tabbable-custom nav-justified">
										<ul class="nav nav-tabs nav-justified">
											<li class="active"><a href="#tab_1_1_1"
												data-toggle="tab"> Lifelines </a></li>
											<li><a href="#tab_1_1_2" data-toggle="tab">
													Operations </a></li>
											<li><a href="#tab_1_1_3" data-toggle="tab"> Syntax
													and semantic Mistakes </a></li>
										</ul>
										<div class="tab-content">
											<div class="tab-pane" id="tab_1_1_1">

												<div class="table-scrollable table-scrollable-borderless">
													<table class="table table-hover">
														<thead>
															<tr>
																<th>Lifeline Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.lifelines}"
																var="lifeline" varStatus="status">
																<tr>
																	<td>${lifeline.elementName}<form:hidden
																			path="lifelines[${status.index}].elementName"
																			value="${lifeline.elementName}" /> <form:hidden
																			path="lifelines[${status.index}].type"
																			value="${lifeline.type}" />
																	</td>
																	<td><form:checkbox
																			path="lifelines[${status.index}].essential"
																			value="${lifeline.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="dmarks"
																			path="lifelines[${status.index}].marks"
																			value="${lifeline.marks}" class="form-control"
																			placeholder="Marks" /></td>
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
													<table class="table table-hover">
														<thead>
															<tr>
																<th>Operation Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.oprations}"
																var="operation" varStatus="status">
																<tr>
																	<td>${operation.elementName}<form:hidden
																			path="oprations[${status.index}].elementName"
																			value="${operation.elementName}" /> <form:hidden
																			path="oprations[${status.index}].type"
																			value="${operation.type}" />
																	</td>
																	<td><form:checkbox
																			path="operations[${status.index}].essential"
																			value="${operation.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="nmarks"
																			path="operations[${status.index}].marks"
																			value="${operation.marks}" class="form-control"
																			placeholder="Marks" /></td>
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
																<th>Mistake Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.mistakes}"
																var="mistake" varStatus="status">
																<tr>
																	<td>${mistake.elementName}<form:hidden
																			path="mistakes[${status.index}].elementName"
																			value="${mistake.elementName}" /> <form:hidden
																			path="mistakes[${status.index}].type"
																			value="${mistake.type}" />
																	</td>
																	<td><form:checkbox
																			path="mistakes[${status.index}].essential"
																			value="${mistake.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="nmarks"
																			path="mistakes[${status.index}].marks"
																			value="${mistake.marks}" class="form-control"
																			placeholder="Marks" /></td>
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

								</c:if>

								<c:if
									test="${evaluationcritariabean.assignment.diagram.equals('Usecase Diagram')}">

									<div class="tabbable-custom nav-justified">
										<ul class="nav nav-tabs nav-justified">
											<li class="active"><a href="#tab_1_1_1"
												data-toggle="tab"> Actors </a></li>
											<li><a href="#tab_1_1_2" data-toggle="tab"> Usecases
											</a></li>
											<li><a href="#tab_1_1_3" data-toggle="tab"> Syntax
													and semantic Mistakes </a></li>
										</ul>
										<div class="tab-content">
											<div class="tab-pane" id="tab_1_1_1">

												<div class="table-scrollable table-scrollable-borderless">
													<table class="table table-hover">
														<thead>
															<tr>
																<th>Actor Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.actors}"
																var="actor" varStatus="status">
																<tr>
																	<td>${actor.elementName}<form:hidden
																			path="actors[${status.index}].elementName"
																			value="${actor.elementName}" /> <form:hidden
																			path="actors[${status.index}].type"
																			value="${actor.type}" />
																	</td>
																	<td><form:checkbox
																			path="actors[${status.index}].essential"
																			value="${actor.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="dmarks"
																			path="actors[${status.index}].marks"
																			value="${actor.marks}" class="form-control"
																			placeholder="Marks" /></td>
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
													<table class="table table-hover">
														<thead>
															<tr>
																<th>Operation Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.usecases}"
																var="usecase" varStatus="status">
																<tr>
																	<td>${usecase.elementName}<form:hidden
																			path="usecases[${status.index}].elementName"
																			value="${usecase.elementName}" /> <form:hidden
																			path="usecases[${status.index}].type"
																			value="${usecase.type}" />
																	</td>
																	<td><form:checkbox
																			path="usecases[${status.index}].essential"
																			value="${usecase.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="nmarks"
																			path="usecases[${status.index}].marks"
																			value="${usecase.marks}" class="form-control"
																			placeholder="Marks" /></td>
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
																<th>Mistake Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.mistakes}"
																var="mistake" varStatus="status">
																<tr>
																	<td>${mistake.elementName}<form:hidden
																			path="mistakes[${status.index}].elementName"
																			value="${mistake.elementName}" /> <form:hidden
																			path="mistakes[${status.index}].type"
																			value="${mistake.type}" />
																	</td>
																	<td><form:checkbox
																			path="mistakes[${status.index}].essential"
																			value="${mistake.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="nmarks"
																			path="mistakes[${status.index}].marks"
																			value="${mistake.marks}" class="form-control"
																			placeholder="Marks" /></td>
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

								</c:if>
								<c:if
									test="${evaluationcritariabean.assignment.diagram.equals('Class Diagram')}">

									<div class="tabbable-custom nav-justified">
										<ul class="nav nav-tabs nav-justified">
											<li class="active"><a href="#tab_1_1_1"
												data-toggle="tab"> Classes </a></li>
											<li><a href="#tab_1_1_2" data-toggle="tab"> Mistakes
											</a></li>
										</ul>
										<div class="tab-content">
											<div class="tab-pane active" id="tab_1_1_1">

												<div class="table-scrollable table-scrollable-borderless">

													<table class="table table-hover">
														<thead>
															<tr>
																<th>Class Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.classes}"
																var="classe" varStatus="status">
																<tr>
																	<td>${classe.elementName}<form:hidden
																			path="classes[${status.index}].elementName"
																			value="${classe.elementName}" /> <form:hidden
																			path="classes[${status.index}].type"
																			value="${classe.type}" />
																	</td>
																	<td><form:checkbox
																			path="classes[${status.index}].essential"
																			value="${classe.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="nmarks"
																			path="classes[${status.index}].marks"
																			value="${classe.marks}" class="form-control"
																			placeholder="Marks" /></td>
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
													<table class="table table-hover">
														<thead>
															<tr>
																<th>Action Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.mistakes}"
																var="mistake" varStatus="status">
																<tr>
																	<td>${mistake.elementName}<form:hidden
																			path="mistakes[${status.index}].elementName"
																			value="${mistake.elementName}" /> <form:hidden
																			path="mistakes[${status.index}].type"
																			value="${mistake.type}" />
																	</td>
																	<td><form:checkbox
																			path="mistakes[${status.index}].essential"
																			value="${mistake.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="nmarks"
																			path="mistakes[${status.index}].marks"
																			value="${mistake.marks}" class="form-control"
																			placeholder="Marks" /></td>
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


								</c:if>
								<c:if
									test="${evaluationcritariabean.assignment.diagram.equals('State Machine Diagram')}">

									<div class="tabbable-custom nav-justified">
										<ul class="nav nav-tabs nav-justified">
											<li class="active"><a href="#tab_1_1_1"
												data-toggle="tab"> States </a></li>
											<li><a href="#tab_1_1_2" data-toggle="tab"> Mistakes
											</a></li>
										</ul>
										<div class="tab-content">
											<div class="tab-pane active" id="tab_1_1_1">

												<div class="table-scrollable table-scrollable-borderless">

													<table class="table table-hover">
														<thead>
															<tr>
																<th>Class Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.classes}"
																var="classe" varStatus="status">
																<tr>
																	<td>${classe.elementName}<form:hidden
																			path="classes[${status.index}].elementName"
																			value="${classe.elementName}" /> <form:hidden
																			path="classes[${status.index}].type"
																			value="${classe.type}" />
																	</td>
																	<td><form:checkbox
																			path="classes[${status.index}].essential"
																			value="${classe.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="nmarks"
																			path="classes[${status.index}].marks"
																			value="${classe.marks}" class="form-control"
																			placeholder="Marks" /></td>
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
													<table class="table table-hover">
														<thead>
															<tr>
																<th>Action Name</th>
																<th>Essential/Non-Essential</th>
																<th>Marks To Deduct</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${evaluationcritariabean.mistakes}"
																var="mistake" varStatus="status">
																<tr>
																	<td>${mistake.elementName}<form:hidden
																			path="mistakes[${status.index}].elementName"
																			value="${mistake.elementName}" /> <form:hidden
																			path="mistakes[${status.index}].type"
																			value="${mistake.type}" />
																	</td>
																	<td><form:checkbox
																			path="mistakes[${status.index}].essential"
																			value="${mistake.essential}" class="make-switch"
																			data-on-text="ON" /></td>
																	<td><form:input type="text" id="nmarks"
																			path="mistakes[${status.index}].marks"
																			value="${mistake.marks}" class="form-control"
																			placeholder="Marks" /></td>
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


								</c:if>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-5">Total Marks</label>
										<form:input type="text" id="nmarks" path="totalMarks"
											value="${evaluationcritariabean.totalMarks}"
											class="form-control" placeholder="Marks" />
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