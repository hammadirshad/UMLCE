package com.mbe.umlce.controller;

import java.awt.Color;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryMarker;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.Layer;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mbe.umlce.beans.AssignmentPlagarismReportBean;
import com.mbe.umlce.beans.UserPlagarismReportBean;
import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.AssignmentSubmission;
import com.mbe.umlce.model.User;
import com.mbe.umlce.reports.AssignmentReportingService;
import com.mbe.umlce.reports.ClassReportingService;
import com.mbe.umlce.reports.UserReportingService;
import com.mbe.umlce.service.AssignmentService;
import com.mbe.umlce.service.AssignmentSubmissionService;
import com.mbe.umlce.service.PlagarismService;
import com.mbe.umlce.service.UserService;

@Controller
public class PlagarismReportingController {
	private static final Logger logger = Logger
			.getLogger(PlagarismReportingController.class);

	@Autowired
	AssignmentService assignmentService;
	@Autowired
	AssignmentSubmissionService submissionService;
	@Autowired
	UserService userService;
	@Autowired
	ClassReportingService classReportingService;
	@Autowired
	UserReportingService userReportingService;
	@Autowired
	PlagarismService plagarismService;
	@Autowired
	AssignmentReportingService assignmentReportingService;

	@RequestMapping(value = "/portal/assignment/AssignmentReports/plagarism", method = RequestMethod.GET)
	public ModelAndView getAssignmentPlagarismReport(Model model) {
		List<AssignmentPlagarismReportBean> plagarismbeans = assignmentReportingService
				.getAssignmentPlagarismReport();
		model.addAttribute("plagarisms", plagarismbeans);
		model.addAttribute("graph",
				"/portal/assignment/AssignmentReports/plagarismgraph");
		return new ModelAndView("plagarismAssignmentReports");
	}

	@RequestMapping(value = "/portal/assignment/AssignmentReports/plagarismgraph", method = RequestMethod.GET)
	public void getAssignmentPlagarismReportGraph(HttpServletResponse response) {
		final DefaultCategoryDataset dataset = assignmentReportingService
				.getAssignmentPlagarismReportGraph();
		JFreeChart barChart = ChartFactory.createBarChart("Plagarism Report",
				"Assignment Title", "Student Count", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		
		CategoryPlot plot = barChart.getCategoryPlot();

		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.BLACK);
		List<AssignmentPlagarismReportBean> plagarismbeans = assignmentReportingService
				.getAssignmentPlagarismReport();

		
		for (AssignmentPlagarismReportBean reportBean : plagarismbeans) {
			CategoryMarker marker = new CategoryMarker(reportBean.getName());
		
				marker.setPaint(Color.LIGHT_GRAY);
				marker.setOutlinePaint(Color.LIGHT_GRAY);
			
			marker.setAlpha(0.5f);
			marker.setLabelAnchor(RectangleAnchor.TOP);
			marker.setLabelTextAnchor(TextAnchor.TOP_CENTER);
			marker.setLabelOffsetType(LengthAdjustmentType.CONTRACT);
			plot.addDomainMarker(marker, Layer.BACKGROUND);
		}
		
		int width = 1280;
		int height = 720;
		try {
			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(response.getOutputStream(),
					barChart, width, height);
			response.getOutputStream().close();
		} catch (IOException e) {
			logger.error(e);
		}
	}

	@RequestMapping(value = "/portal/assignment/UserReports/plagarism", method = RequestMethod.GET)
	public ModelAndView getUserPlagarismReport(Model model) {
		List<UserPlagarismReportBean> plagarismbeans = userReportingService
				.getUserPlagarismReport();
		model.addAttribute("plagarisms", plagarismbeans);
		model.addAttribute("graph",
				"/portal/assignment/UserReports/plagarismgraph");
		return new ModelAndView("plagarismUserReports");
	}

	@RequestMapping(value = "/portal/assignment/UserReports/plagarismgraph", method = RequestMethod.GET)
	public void getUserPlagarismReportGraph(HttpServletResponse response) {
		final DefaultCategoryDataset dataset = userReportingService
				.getUserPlagarismReportGraph();
		JFreeChart barChart = ChartFactory.createBarChart("Plagarism Report",
				"Assignment Title", "Assignment Count", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		CategoryPlot plot = barChart.getCategoryPlot();

		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.BLACK);
		List<UserPlagarismReportBean> plagarismbeans = userReportingService
				.getUserPlagarismReport();

		
		for (UserPlagarismReportBean reportBean : plagarismbeans) {
			CategoryMarker marker = new CategoryMarker(reportBean.getName());
		
				marker.setPaint(Color.LIGHT_GRAY);
				marker.setOutlinePaint(Color.LIGHT_GRAY);
			
			marker.setAlpha(0.5f);
			marker.setLabelAnchor(RectangleAnchor.TOP);
			marker.setLabelTextAnchor(TextAnchor.TOP_CENTER);
			marker.setLabelOffsetType(LengthAdjustmentType.CONTRACT);
			plot.addDomainMarker(marker, Layer.BACKGROUND);
		}


		int width = 1280;
		int height = 720;
		try {
			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(response.getOutputStream(),
					barChart, width, height);
			response.getOutputStream().close();
		} catch (IOException e) {
			logger.error(e);
		}
	}

	@RequestMapping(value = "/portal/assignment/AssignmentReports/userplagarism", method = RequestMethod.GET)
	public ModelAndView getAssignmentUserEvaluationReport(Model model,
			@RequestParam("assignment") String id,
			@RequestParam("plagarism") String plag) {
		Assignment assignment = assignmentService
				.findById(Integer.parseInt(id));
		List<AssignmentSubmission> submissions = submissionService
				.findByAssignment(assignment);
		List<AssignmentSubmission> submissions1 = new ArrayList<AssignmentSubmission>();
		if (plag.equals("less50")) {
			for (AssignmentSubmission submission : submissions) {
				if (submission.getPlagarism() < 50) {
					submissions1.add(submission);
				}
			}
		} else if (plag.equals("above50")) {
			for (AssignmentSubmission submission : submissions) {
				if (submission.getPlagarism() >= 50
						&& submission.getPlagarism() < 80) {
					submissions1.add(submission);
				}
			}
		} else if (plag.equals("above80")) {
			for (AssignmentSubmission submission : submissions) {
				if (submission.getPlagarism() >= 80) {
					submissions1.add(submission);
				}
			}
		} else if (plag.equals("aboveaverage")) {
			double average = 0;
			for (AssignmentSubmission submission1 : submissions) {
				average = average + submission1.getPlagarism();
			}
			average = average / submissions.size();
			for (AssignmentSubmission submission : submissions) {
				if (submission.getPlagarism() >= average) {
					submissions1.add(submission);
				}
			}
		}

		DecimalFormat twoDForm = new DecimalFormat("##.##");
		for (AssignmentSubmission submission : submissions1) {
			submission.setPlagarism(Double.valueOf(twoDForm.format(submission
					.getPlagarism())));
		}
		Collections.sort(submissions1, new Comparator<AssignmentSubmission>() {
			public int compare(AssignmentSubmission m1, AssignmentSubmission m2) {
				return m2.getSubmissionDate().compareTo(m1.getSubmissionDate());
			}
		});
		model.addAttribute("submissions", submissions1);
		return new ModelAndView("plagarismAssignmentUserReports");
	}

	@RequestMapping(value = "/portal/assignment/UserReports/userplagarism", method = RequestMethod.GET)
	public ModelAndView getUserAssignmentEvaluationReport(Model model,
			@RequestParam("user") String username,
			@RequestParam("plagarism") String plag) {
		User user = userService.findByUsername(username);
		List<AssignmentSubmission> submissions = submissionService
				.findByOwner(user);
		List<AssignmentSubmission> submissions1 = new ArrayList<AssignmentSubmission>();
		if (plag.equals("less50")) {
			for (AssignmentSubmission submission : submissions) {
				if (submission.getPlagarism() < 50) {
					submissions1.add(submission);
				}
			}
		} else if (plag.equals("above50")) {
			for (AssignmentSubmission submission : submissions) {
				if (submission.getPlagarism() >= 50
						&& submission.getPlagarism() < 80) {
					submissions1.add(submission);
				}
			}
		} else if (plag.equals("above80")) {
			for (AssignmentSubmission submission : submissions) {
				if (submission.getPlagarism() >= 80) {
					submissions1.add(submission);
				}
			}
		} else if (plag.equals("aboveaverage")) {
			for (AssignmentSubmission submission : submissions) {
				double average = 0;
				List<AssignmentSubmission> submissions2 = submissionService
						.findByAssignment(submission.getAssignment());
				for (AssignmentSubmission submission2 : submissions1) {
					average = average + submission2.getPlagarism();
				}
				average = average / submissions2.size();
				if (submission.getPlagarism() >= average) {
					submissions1.add(submission);
				}
			}
		}
		DecimalFormat twoDForm = new DecimalFormat("##.##");
		for (AssignmentSubmission submission : submissions1) {
			submission.setPlagarism(Double.valueOf(twoDForm.format(submission
					.getPlagarism())));
		}
		Collections.sort(submissions1, new Comparator<AssignmentSubmission>() {
			public int compare(AssignmentSubmission m1, AssignmentSubmission m2) {
				return m2.getSubmissionDate().compareTo(m1.getSubmissionDate());
			}
		});
		model.addAttribute("submissions", submissions1);
		return new ModelAndView("plagarismAssignmentUserReports");
	}

}