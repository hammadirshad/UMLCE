package com.mbe.umlce.controller;

import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.AbstractJasperReportsView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsHtmlView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsXlsView;

import com.mbe.umlce.beans.AssignmentEvaluationReportBean;
import com.mbe.umlce.beans.ClassMistakeReport;
import com.mbe.umlce.beans.MistakeReportBean;
import com.mbe.umlce.beans.UserEvaluationReportBean;
import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.User;
import com.mbe.umlce.reports.AssignmentReportingService;
import com.mbe.umlce.reports.ClassReportingService;
import com.mbe.umlce.reports.UserReportingService;
import com.mbe.umlce.service.AssignmentService;
import com.mbe.umlce.service.UserService;

@Controller
public class ReportingController {
	private static final Logger logger = Logger
			.getLogger(ReportingController.class);
	@Autowired
	AssignmentService assignmentService;
	@Autowired
	UserService userService;
	@Autowired
	ClassReportingService classReportingService;
	@Autowired
	UserReportingService userReportingService;
	@Autowired
	AssignmentReportingService assignmentReportingService;
	@Autowired
	ApplicationContext appContext;

	@RequestMapping(value = "/portal/assignment/Reports", method = RequestMethod.GET)
	public ModelAndView welcome() {
		return new ModelAndView("calendarPage");
	}

	@RequestMapping(value = "/portal/assignment/UserReports", method = RequestMethod.GET)
	public ModelAndView getUserList(Model model) {

		List<User> userList = userService.findAll();
		model.addAttribute("userList", userList);
		return new ModelAndView("userReports");
	}

	@RequestMapping(value = "/portal/assignment/AssignmentReports", method = RequestMethod.GET)
	public ModelAndView getAssignmentList(Model model) {

		List<Assignment> assignmentList = assignmentService.findAll();
		model.addAttribute("assignmentList", assignmentList);
		return new ModelAndView("assignmentReports");
	}

	@RequestMapping(value = "/portal/assignment/classReports/mistakes", method = RequestMethod.GET)
	public ModelAndView getClassMistakesReport(Model model) {
		List<MistakeReportBean> mistakesreport = classReportingService
				.getClassMistakesReport();
		model.addAttribute("type", "Class Report");
		model.addAttribute("mistakes", mistakesreport);
		model.addAttribute("graph",
				"/portal/assignment/classReports/mistakesgraph");
		model.addAttribute("report",
				"/portal/assignment/classReports/mistakesdownload?");
		return new ModelAndView("Reports");
	}

	@RequestMapping(value = "/portal/assignment/classReports/mistakesgraph", method = RequestMethod.GET)
	public void getClassMistakesReportGraph(HttpServletResponse response) {
		final DefaultCategoryDataset dataset = classReportingService
				.getClassMistakesReportGraph();
		JFreeChart barChart = ChartFactory.createBarChart("Class Mistakes",
				"Mistakes", "Count", dataset, PlotOrientation.VERTICAL, true,
				true, false);
		
		CategoryPlot plot = barChart.getCategoryPlot();

		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.BLACK);
		List<MistakeReportBean> mistakebeans = classReportingService
				.getClassMistakesReport();

		
		for (MistakeReportBean reportBean : mistakebeans) {
			CategoryMarker marker = new CategoryMarker(reportBean.getElementName());
		
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

	@RequestMapping(value = "/portal/assignment/classReports/mistakesdownload", method = RequestMethod.GET)
	public ModelAndView getClassMistakesReportDownload(
			@RequestParam(value = "type", required = false) String type) {
		List<MistakeReportBean> mistakesreport = classReportingService
				.getClassMistakesReport();
		ClassMistakeReport classMistakeReport = new ClassMistakeReport();
		classMistakeReport.setMistakes(mistakesreport);
		AbstractJasperReportsView reportView;
		if (type.equals("pdf")) {
			reportView = new JasperReportsPdfView();
		} else if (type.equals("excel")) {
			reportView = new JasperReportsXlsView();
		} else {
			reportView = new JasperReportsHtmlView();
		}
		reportView.setUrl("classpath:report/mistakeReport.jrxml");
		reportView.setApplicationContext(appContext);
		reportView.setReportDataKey("datasource");
		reportView.addStaticAttribute(
				"datasource",
				new JRBeanCollectionDataSource(Arrays
						.asList(classMistakeReport)));
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		return new ModelAndView(reportView, parameterMap);
	}

	@RequestMapping(value = "/portal/assignment/UserReports/mistakes", method = RequestMethod.GET)
	public ModelAndView getUserMistakesReport(Model model,
			@RequestParam("user") String username) {
		List<MistakeReportBean> mistakesreport = userReportingService
				.getUserMistakesReport(username);
		model.addAttribute("type", "User Report");
		model.addAttribute("mistakes", mistakesreport);
		model.addAttribute("graph",
				"/portal/assignment/UserReports/mistakesgraph?user=" + username);
		model.addAttribute("report",
				"/portal/assignment/UserReports/mistakesdownload?user=" + username+"&");
		return new ModelAndView("Reports");
	}

	@RequestMapping(value = "/portal/assignment/UserReports/mistakesgraph", method = RequestMethod.GET)
	public void getUserMistakesReportGraph(
			@RequestParam("user") String username, HttpServletResponse response) {
		final DefaultCategoryDataset dataset = userReportingService
				.getUserMistakesReportGraph(username);
		JFreeChart barChart = ChartFactory.createBarChart("User Mistakes",
				"Mistakes", "Count", dataset, PlotOrientation.VERTICAL, true,
				true, false);
		
		CategoryPlot plot = barChart.getCategoryPlot();

		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.BLACK);
		List<MistakeReportBean> mistakebeans = userReportingService
				.getUserMistakesReport(username);

		
		for (MistakeReportBean reportBean : mistakebeans) {
			CategoryMarker marker = new CategoryMarker(reportBean.getElementName());
		
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

	@RequestMapping(value = "/portal/assignment/UserReports/mistakesdownload", method = RequestMethod.GET)
	public ModelAndView getUserMistakesReportDownload(
			@RequestParam("user") String username,
			@RequestParam(value = "type", required = false) String type) {
		List<MistakeReportBean> mistakesreport = userReportingService
				.getUserMistakesReport(username);
		ClassMistakeReport classMistakeReport = new ClassMistakeReport();
		classMistakeReport.setMistakes(mistakesreport);
		AbstractJasperReportsView reportView;
		if (type.equals("pdf")) {
			reportView = new JasperReportsPdfView();
		} else if (type.equals("excel")) {
			reportView = new JasperReportsXlsView();
		} else {
			reportView = new JasperReportsHtmlView();
		}
		reportView.setUrl("classpath:report/mistakeReport.jrxml");
		reportView.setApplicationContext(appContext);
		reportView.setReportDataKey("datasource");
		reportView.addStaticAttribute(
				"datasource",
				new JRBeanCollectionDataSource(Arrays
						.asList(classMistakeReport)));
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		return new ModelAndView(reportView, parameterMap);
	}

	@RequestMapping(value = "/portal/assignment/AssignmentReports/mistakes", method = RequestMethod.GET)
	public ModelAndView getAssignmentMistakesReport(Model model,
			@RequestParam("assignment") String id) {
		List<MistakeReportBean> mistakesreport = assignmentReportingService
				.getAssignmentMistakesReport(id);
		model.addAttribute("type", "Assignment Report");
		model.addAttribute("mistakes", mistakesreport);
		model.addAttribute("graph",
				"/portal/assignment/AssignmentReports/mistakesgraph?assignment="
						+ id);
		model.addAttribute("report",
				"/portal/assignment/AssignmentReports/mistakesdownload?assignment=" + id+"&");
		return new ModelAndView("Reports");
	}

	@RequestMapping(value = "/portal/assignment/AssignmentReports/mistakesgraph", method = RequestMethod.GET)
	public void getAssignmentMistakesReportGraph(
			@RequestParam("assignment") String id, HttpServletResponse response) {
		final DefaultCategoryDataset dataset = assignmentReportingService
				.getAssignmentMistakesReportGraph(id);
		JFreeChart barChart = ChartFactory.createBarChart(
				"Assignment Mistakes", "Mistakes", "Count", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		
		CategoryPlot plot = barChart.getCategoryPlot();

		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.BLACK);
		List<MistakeReportBean> mistakebeans = assignmentReportingService
				.getAssignmentMistakesReport(id);

		
		for (MistakeReportBean reportBean : mistakebeans) {
			CategoryMarker marker = new CategoryMarker(reportBean.getElementName());
		
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

	
	@RequestMapping(value = "/portal/assignment/AssignmentReports/mistakesdownload", method = RequestMethod.GET)
	public ModelAndView getAssignmentMistakesReportDownload(
			@RequestParam("assignment") String id,
			@RequestParam(value = "type", required = false) String type) {
		List<MistakeReportBean> mistakesreport = assignmentReportingService
				.getAssignmentMistakesReport(id);
		ClassMistakeReport classMistakeReport = new ClassMistakeReport();
		classMistakeReport.setMistakes(mistakesreport);
		AbstractJasperReportsView reportView;
		if (type.equals("pdf")) {
			reportView = new JasperReportsPdfView();
		} else if (type.equals("excel")) {
			reportView = new JasperReportsXlsView();
		} else {
			reportView = new JasperReportsHtmlView();
		}
		reportView.setUrl("classpath:report/mistakeReport.jrxml");
		reportView.setApplicationContext(appContext);
		reportView.setReportDataKey("datasource");
		reportView.addStaticAttribute(
				"datasource",
				new JRBeanCollectionDataSource(Arrays
						.asList(classMistakeReport)));
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		return new ModelAndView(reportView, parameterMap);
	}
	
	
	@RequestMapping(value = "/portal/assignment/AssignmentReports/evaluation", method = RequestMethod.GET)
	public ModelAndView getAssignmentEvaluationReport(Model model) {
		List<AssignmentEvaluationReportBean> reportBeans = assignmentReportingService
				.getAssignmentEvaluationReport();
		model.addAttribute("evaluations", reportBeans);
		model.addAttribute("graph",
				"/portal/assignment/AssignmentReports/evaluationgraph");
		return new ModelAndView("evaluationAssignmentReports");
	}

	@RequestMapping(value = "/portal/assignment/AssignmentReports/evaluationgraph", method = RequestMethod.GET)
	public void getAssignmentEvaluationReportGraph(HttpServletResponse response) {
		final DefaultCategoryDataset dataset = assignmentReportingService
				.getAssignmentEvaluationReportGraph();
		JFreeChart barChart = ChartFactory.createBarChart("Assignment Result",
				"Total Submissions", "Avrage Marks Percentage", dataset,
				PlotOrientation.VERTICAL, true, true, false);
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

	@RequestMapping(value = "/portal/assignment/UserReports/evaluation", method = RequestMethod.GET)
	public ModelAndView getUserEvaluationReport(Model model) {
		List<UserEvaluationReportBean> reportBeans = userReportingService
				.getUserEvaluationReport();
		model.addAttribute("evaluations", reportBeans);
		return new ModelAndView("evaluationUserReports");
	}

}