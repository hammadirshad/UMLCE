<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li><i class="fa fa-home"></i> <a href="index-2.html">Home</a> <i
				class="fa fa-angle-right"></i></li>
			<li><a href="#">Pages</a> <i class="fa fa-angle-right"></i></li>
			<li><a href="#">Calendar</a></li>
		</ul>
		<div class="page-toolbar">
			<div class="btn-group pull-right">
				<button type="button"
					class="btn btn-fit-height grey-salt dropdown-toggle"
					data-toggle="dropdown" data-hover="dropdown" data-delay="1000"
					data-close-others="true">
					Actions <i class="fa fa-angle-down"></i>
				</button>
				<ul class="dropdown-menu pull-right" role="menu">
					<li><a href="#">Action</a></li>
					<li><a href="#">Another action</a></li>
					<li><a href="#">Something else here</a></li>
					<li class="divider"></li>
					<li><a href="#">Separated link</a></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- END PAGE HEADER-->
	<!-- BEGIN PAGE CONTENT-->
	<div class="row">
		<div class="col-md-12">
			<div class="portlet box green-meadow calendar">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-gift"></i>Calendar
					</div>
				</div>
				<div class="portlet-body">
					<div class="row">
						<div class="col-md-3 col-sm-12">
							<!-- BEGIN DRAGGABLE EVENTS PORTLET-->
							<h3 class="event-form-title">Draggable Events</h3>
							<div id="external-events">
								<form class="inline-form">
									<input type="text" value="" class="form-control"
										placeholder="Event Title..." id="event_title" /><br /> <a
										href="javascript:;" id="event_add" class="btn default">
										Add Event </a>
								</form>
								<hr />
								<div id="event_box"></div>
								<label for="drop-remove"> <input type="checkbox"
									id="drop-remove" />remove after drop
								</label>
								<hr class="visible-xs" />
							</div>
							<!-- END DRAGGABLE EVENTS PORTLET-->
						</div>
						<div class="col-md-9 col-sm-12">
							<div id="calendar" class="has-toolbar"></div>
						</div>
					</div>
					<!-- END CALENDAR PORTLET-->
				</div>
			</div>
		</div>
	</div>
	<!-- END PAGE CONTENT-->
</div>
