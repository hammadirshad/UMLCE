package com.mbe.umlce.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.AbstractJasperReportsView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsHtmlView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsXlsView;

import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.PieChart;
import com.googlecode.charts4j.Slice;
import com.mbe.umlce.beans.AssignmentResult;
import com.mbe.umlce.beans.EvaluationMistakeBean;
import com.mbe.umlce.model.AssignmentSubmission;
import com.mbe.umlce.model.EvalErrorDetail;
import com.mbe.umlce.model.EvaluationError;
import com.mbe.umlce.model.MappingMistake;
import com.mbe.umlce.model.Mistake;
import com.mbe.umlce.model.Plagarism;
import com.mbe.umlce.reports.AssignmentReportingService;
import com.mbe.umlce.service.AssignmentSubmissionService;
import com.mbe.umlce.service.EvalErrorDetailService;
import com.mbe.umlce.service.EvaluationErrorService;
import com.mbe.umlce.service.MappingMistakeService;
import com.mbe.umlce.service.MistakeService;
import com.mbe.umlce.service.PlagarismService;

@Controller
public class ResultsController {

	@Autowired
	AssignmentSubmissionService assignmentSubmissionService;
	@Autowired
	MistakeService mistakeService;
	@Autowired
	PlagarismService plagarismService;
	@Autowired
	EvaluationErrorService evaluationService;
	@Autowired
	EvalErrorDetailService evalErrorDetailService;
	@Autowired
	MappingMistakeService mappingMistakeService;
	@Autowired
	ApplicationContext appContext;
	@Autowired
	AssignmentReportingService reportingService;
	private static final Logger logger = Logger
			.getLogger(ResultsController.class);

	@RequestMapping(value = "/portal/submissions/results", method = RequestMethod.GET)
	public String welcome() {
		return "redirect:/portal";
	}

	@RequestMapping(value = "/portal/submissions/results/submissionresultsdownload", method = RequestMethod.GET)
	public ModelAndView submissionResultReport(
			@RequestParam(value = "submission", required = false) String id,
			@RequestParam(value = "type", required = false) String type) {
		AssignmentResult result = reportingService.submissionResultReport(id);
		AbstractJasperReportsView reportView;
		if (type.equals("pdf")) {
			reportView = new JasperReportsPdfView();
		} else if (type.equals("excel")) {
			reportView = new JasperReportsXlsView();
		} else {
			reportView = new JasperReportsHtmlView();
		}
		reportView.setUrl("classpath:report/AssignmentResult.jrxml");
		reportView.setApplicationContext(appContext);
		reportView.setReportDataKey("datasource");
		reportView.addStaticAttribute("datasource",
				new JRBeanCollectionDataSource(Arrays.asList(result)));
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		return new ModelAndView(reportView, parameterMap);
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/portal/submissions/results/submissionresults", method = RequestMethod.GET)
	public ModelAndView submissionResult(
			@RequestParam(value = "submission", required = false) String id,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		AssignmentSubmission submission = assignmentSubmissionService
				.findById(Integer.parseInt(id));

		model.addAttribute("title", submission.getAssignment().getTitle());
		model.addAttribute("submitionDate", submission.getSubmissionDate()
				.toGMTString());
		model.addAttribute("type", submission.getAssignment().getDiagram());
		model.addAttribute("id", submission.getId());

		if (submission.getAssignment().isMistakes() == true) {
			List<Mistake> mistakes = mistakeService
					.findByAssignmentSub(submission);
			model.addAttribute("mistakes", mistakes);
		}

		if (submission.getAssignment().isPlagarism() == true) {
			List<Plagarism> plagarisms = plagarismService
					.findByStudent1AndAssignment(submission.getOwner(),
							submission.getAssignment());
			List<Plagarism> plagarisms1 = plagarismService
					.findByStudent2AndAssignment(submission.getOwner(),
							submission.getAssignment());
			plagarisms.addAll(plagarisms1);
			model.addAttribute("plagarisms", plagarisms);
		}

		if (submission.getAssignment().isEvaluation() == true) {
			List<EvaluationError> evaluationerror = evaluationService
					.findBySubmission(submission);
			ArrayList<EvaluationMistakeBean> evalmistakes = new ArrayList<EvaluationMistakeBean>();
			for (EvaluationError eval : evaluationerror) {
				List<EvalErrorDetail> errordetail = evalErrorDetailService
						.findByError(eval);
				EvaluationMistakeBean evalmistakebean = new EvaluationMistakeBean(
						eval.getId(), eval.getErrorName(), eval.getCount(),
						errordetail);
				evalmistakes.add(evalmistakebean);
			}
			model.addAttribute("evalmistakes", evalmistakes);
		}
		if (submission.getAssignment().isCodemapping() == true) {
			List<MappingMistake> mistakes = mappingMistakeService
					.findByAssignmentSub(submission);
			model.addAttribute("mappingmistakes", mistakes);
		}
		model.addAttribute("submission", submission);
		return new ModelAndView("submissionResult");
	}

	@RequestMapping(value = "/portal/submissions/results/graph", method = RequestMethod.GET)
	public ModelAndView drawPieChart(ModelMap model) {
		Slice s1 = Slice.newSlice(10, Color.newColor("CACACA"), "Windows",
				"Windows");
		Slice s2 = Slice.newSlice(90, Color.newColor("951800"), "Mac", "Mac");
		PieChart pieChart = GCharts.newPieChart(s1, s2);
		pieChart.setTitle("Test Chart");
		pieChart.setThreeD(true);
		pieChart.setSize(300, 300);
		model.addAttribute("pieChart", pieChart.toURLForHTML());
		return new ModelAndView("chartsResult");
	}

}