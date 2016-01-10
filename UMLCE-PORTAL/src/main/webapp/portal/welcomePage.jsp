<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="page-content">

	<!-- BEGIN PAGE HEADER-->

	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li><i class="fa fa-home"></i> <h:link outcome="/index.xhtml">Home</h:link>
				<i class="fa fa-angle-right"></i></li>
			<li><a href="#">UMLCE</a> <i class="fa fa-angle-right"></i></li>
			<li><a href="#">Dash Board</a></li>
		</ul>

	</div>
	<!-- END PAGE HEADER-->
	<!-- BEGIN PAGE CONTENT-->
	<div class="tiles col-md-12">
		<a data-toggle="modal" href="#mistakes">
			<div class="tile double bg-grey-cascade">
				<div class="tile-body">
					<h4>Mistakes</h4>
					<ul>
						<li>Class Diagram</li>
						<li>Domain Model</li>
						<li>Sequence Diagram</li>
						<li>Usecase Diagram</li>
					</ul>
				</div>
				<div class="tile-object">
					<div class="number">4</div>
				</div>
			</div>
		</a> <a data-toggle="modal" href="#evaluation">
			<div class="tile double bg-green-turquoise">
				<div class="tile-body">
					<h4>Evaluation</h4>
					<ul>
						<li>Class Diagram</li>
						<li>Domain Model</li>
						<li>Sequence Diagram</li>
						<li>Usecase Diagram</li>
					</ul>
				</div>
				<div class="tile-object">
					<div class="number">4</div>
				</div>
			</div>
		</a> <a data-toggle="modal" href="#plagiarism">
			<div class="tile double bg-blue-steel">
				<div class="tile-body">
					<h4>Plagiarism</h4>
					<ul>
						<li>Class Diagram</li>
						<li>Domain Model</li>
						<li>Sequence Diagram</li>
						<li>Usecase Diagram</li>
					</ul>
				</div>
				<div class="tile-object">
					<div class="number">4</div>
				</div>
			</div>
		</a> <a data-toggle="modal" href="#codemapping">
			<div class="tile double bg-red-sunglo">
				<div class="tile-body">
					<h4>Code Mapping</h4>
					<ul>
						<li>Class Diagram</li>
					</ul>
				</div>
				<div class="tile-object">
					<div class="number">1</div>
				</div>
			</div>
		</a>
	</div>
	<!-- END PAGE CONTENT-->

	<!-- responsive -->
	<div id="mistakes" class="modal fade" tabindex="-1" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content col-md-8">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="modal-title">Mistakes</h4>
				</div>
				<div class="modal-body">
					<div class="scroller" style="height: 300px" data-always-visible="1"
						data-rail-visible1="1">
						<div class="row">
							<div class="tiles col-md-12 col-md-offset-1">
								<h:link outcome="/Identifier/CDMistakes.xhtml">
									<div class="tile bg-blue-hoki">
										<div class="tile-body">
											<i class="fa fa-warning"></i>
										</div>
										<div class="tile-object">
											<div class="name">Class Diagram</div>
										</div>
									</div>
								</h:link>
								<h:link outcome="/Identifier/SDMistakes.xhtml">
									<div class="tile bg-blue-hoki">
										<div class="tile-body">
											<i class="fa fa-warning"></i>
										</div>
										<div class="tile-object">
											<div class="name">Sequence Diagram</div>
										</div>
									</div>
								</h:link>
								<h:link outcome="/Identifier/DMMistakes.xhtml">
									<div class="tile bg-blue-hoki">
										<div class="tile-body">
											<i class="fa fa-warning"></i>
										</div>
										<div class="tile-object">
											<div class="name">Domain Model</div>
										</div>
									</div>
								</h:link>
								<h:link outcome="/Identifier/UCDMistakes.xhtml">
									<div class="tile bg-blue-hoki">
										<div class="tile-body">
											<i class="fa fa-warning"></i>
										</div>
										<div class="tile-object">
											<div class="name">Usecase Diagram</div>
										</div>
									</div>
								</h:link>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>

	<div id="plagiarism" class="modal fade" tabindex="-1"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content col-md-8">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="modal-title">Plagiarism</h4>
				</div>
				<div class="modal-body">
					<div class="scroller" style="height: 300px" data-always-visible="1"
						data-rail-visible1="1">
						<div class="row">
							<div class="tiles col-md-12 col-md-offset-1">
								<h:link outcome="/Comparator/CDPlagiarismChecker.xhtml">
									<div class="tile bg-blue-hoki">
										<div class="tile-body">
											<i class="fa fa-search"></i>
										</div>
										<div class="tile-object">
											<div class="name">Class Diagram</div>
										</div>
									</div>
								</h:link>
								<h:link outcome="/Comparator/SDPlagiarismChecker.xhtml">
									<div class="tile bg-blue-hoki">
										<div class="tile-body">
											<i class="fa fa-search"></i>
										</div>
										<div class="tile-object">
											<div class="name">Sequence Diagram</div>
										</div>
									</div>
								</h:link>
								<h:link outcome="/Comparator/DMPlagiarismChecker.xhtml">
									<div class="tile bg-blue-hoki">
										<div class="tile-body">
											<i class="fa fa-search"></i>
										</div>
										<div class="tile-object">
											<div class="name">Domain Model</div>
										</div>
									</div>
								</h:link>
								<h:link outcome="/Comparator/UCPlagiarismChecker.xhtml">
									<div class="tile bg-blue-hoki">
										<div class="tile-body">
											<i class="fa fa-search"></i>
										</div>
										<div class="tile-object">
											<div class="name">Usecase Diagram</div>
										</div>
									</div>
								</h:link>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>

	<div id="evaluation" class="modal fade" tabindex="-1"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content col-md-8">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="modal-title">Evaluation</h4>
				</div>
				<div class="modal-body">
					<div class="scroller" style="height: 300px" data-always-visible="1"
						data-rail-visible1="1">
						<div class="row">
							<div class="tiles col-md-12 col-md-offset-1">
								<h:link outcome="/Evaluator/CDEvaluation.xhtml">
									<div class="tile bg-blue-hoki">
										<div class="tile-body">
											<i class="fa fa-bug"></i>
										</div>
										<div class="tile-object">
											<div class="name">Class Diagram</div>
										</div>
									</div>
								</h:link>
								<h:link outcome="/Evaluator/SDEvaluation.xhtml">
									<div class="tile bg-blue-hoki">
										<div class="tile-body">
											<i class="fa fa-bug"></i>
										</div>
										<div class="tile-object">
											<div class="name">Sequence Diagram</div>
										</div>
									</div>
								</h:link>
								<h:link outcome="/Evaluator/DMEvaluation.xhtml">
									<div class="tile bg-blue-hoki">
										<div class="tile-body">
											<i class="fa fa-bug"></i>
										</div>
										<div class="tile-object">
											<div class="name">Domain Model</div>
										</div>
									</div>
								</h:link>
								<h:link outcome="/Evaluator/UDEvaluation.xhtml">
									<div class="tile bg-blue-hoki">
										<div class="tile-body">
											<i class="fa fa-bug"></i>
										</div>
										<div class="tile-object">
											<div class="name">Usecase Diagram</div>
										</div>
									</div>
								</h:link>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>

	<div id="codemapping" class="modal fade" tabindex="-1"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content col-md-8">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="modal-title">Code Mapping</h4>
				</div>
				<div class="modal-body">
					<div class="scroller" style="height: 300px" data-always-visible="1"
						data-rail-visible1="1">
						<div class="row">
							<div class="tiles col-md-12 col-md-offset-1">
								<h:link outcome="/Comparator/codeToDiagramMaper.xhtml">
									<div class="tile bg-blue-hoki">
										<div class="tile-body">
											<i class="fa fa-gears"></i>
										</div>
										<div class="tile-object">
											<div class="name">Class Diagram</div>
										</div>
									</div>
								</h:link>

							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</div>

