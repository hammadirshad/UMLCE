<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li><i class="fa fa-home"></i> <a href="/portal">Home</a> <i
				class="fa fa-angle-right"></i></li>
			<li><a href="#">Calendar</a></li>
		</ul>
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

						<div class="col-md-12 col-sm-12">
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
<script>

	var Calendar = function() {
		return {
			
			//main function to initiate the module
			init : function() {
				Calendar.initCalendar();
			},

			initCalendar : function() {

				if (!jQuery().fullCalendar) {
					return;
				}

				var date = new Date();
				var d = date.getDate();
				var m = date.getMonth();
				var y = date.getFullYear();
				var calendarData=${calendarData};
				var h = {};

				if (Metronic.isRTL()) {
					if ($('#calendar').parents(".portlet").width() <= 720) {
						$('#calendar').addClass("mobile");
						h = {
							right : 'title, prev, next',
							center : '',
							left : 'agendaDay, agendaWeek, month, today'
						};
					} else {
						$('#calendar').removeClass("mobile");
						h = {
							right : 'title',
							center : '',
							left : 'agendaDay, agendaWeek, month, today, prev,next'
						};
					}
				} else {
					if ($('#calendar').parents(".portlet").width() <= 720) {
						$('#calendar').addClass("mobile");
						h = {
							left : 'title, prev, next',
							center : '',
							right : 'today,month,agendaWeek,agendaDay'
						};
					} else {
						$('#calendar').removeClass("mobile");
						h = {
							left : 'title',
							center : '',
							right : 'prev,next,today,month,agendaWeek,agendaDay'
						};
					}
				}

				$('#calendar').fullCalendar('destroy'); // destroy the calendar
				$('#calendar').fullCalendar(
						{ //re-initialize the calendar
							header : h,
							defaultView : 'month', // change default view with available options from http://arshaw.com/fullcalendar/docs/views/Available_Views/ 
							slotMinutes : 15,
							editable : true,
							droppable : true, // this allows things to be dropped onto the calendar !!!
							drop : function(date, allDay) { // this function is called when something is dropped

								// retrieve the dropped element's stored Event Object
								var originalEventObject = $(this).data(
										'eventObject');
								// we need to copy it, so that multiple events don't have a reference to the same object
								var copiedEventObject = $.extend({},
										originalEventObject);

								// assign it the date that was reported
								copiedEventObject.start = date;
								copiedEventObject.allDay = allDay;
								copiedEventObject.className = $(this).attr(
										"data-class");

								// render the event on the calendar
								// the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
								$('#calendar').fullCalendar('renderEvent',
										copiedEventObject, true);

								// is the "remove after drop" checkbox checked?
								if ($('#drop-remove').is(':checked')) {
									// if so, remove the element from the "Draggable Events" list
									$(this).remove();
								}
							},
							eventSources : [ {
								events :calendarData
							} ]
						});

			}

		};

	}();
	jQuery(document).ready(function() {

		Calendar.init();

	});
</script>