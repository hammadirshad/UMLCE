package com.mbe.umlce.reports;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.log4j.Logger;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbe.umlce.beans.AssignmentEvaluationReportBean;
import com.mbe.umlce.beans.AssignmentPlagarismReportBean;
import com.mbe.umlce.beans.AssignmentResult;
import com.mbe.umlce.beans.EvaluationMistakeBean;
import com.mbe.umlce.beans.MistakeReportBean;
import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.AssignmentSubmission;
import com.mbe.umlce.model.EvalErrorDetail;
import com.mbe.umlce.model.EvaluationError;
import com.mbe.umlce.model.MappingMistake;
import com.mbe.umlce.model.Mistake;
import com.mbe.umlce.model.Plagarism;
import com.mbe.umlce.service.AssignmentResourceService;
import com.mbe.umlce.service.AssignmentService;
import com.mbe.umlce.service.AssignmentSubmissionService;
import com.mbe.umlce.service.EvalErrorDetailService;
import com.mbe.umlce.service.EvaluationErrorService;
import com.mbe.umlce.service.MappingMistakeService;
import com.mbe.umlce.service.MistakeService;
import com.mbe.umlce.service.PlagarismService;
import com.mbe.umlce.service.SubmissionResourceService;

@Service
public class AssignmentReportingService {
	private static final Logger logger = Logger
			.getLogger(AssignmentReportingService.class);
	@Autowired
	AssignmentService assignmentService;
	@Autowired
	AssignmentResourceService assignmentResourceService;
	@Autowired
	AssignmentSubmissionService assignmentSubmissionService;
	@Autowired
	SubmissionResourceService submissionResourceService;
	@Autowired
	MistakeService mistakeService;
	@Autowired
	MappingMistakeService mappingMistakeService;
	EvalErrorDetailService evalErrorDetailService;
	@Autowired
	PlagarismService plagarismService;
	@Autowired
	EvaluationErrorService evaluationService;
	@Autowired
	AssignmentSubmissionService submissionService;

	@SuppressWarnings("deprecation")
	public AssignmentResult submissionResultReport(String id) {
		AssignmentSubmission submission = assignmentSubmissionService
				.findById(Integer.parseInt(id));

		AssignmentResult result = new AssignmentResult();
		result.setAssignmentTitle(submission.getAssignment().getTitle());
		result.setSubmitionDate(submission.getSubmissionDate().toGMTString());
		result.setAssignmentType(submission.getAssignment().getDiagram());
		result.setName(submission.getOwner().getFname() + " "
				+ submission.getOwner().getLname());
		result.setRollNo(submission.getOwner().getUsername());

		if (submission.getAssignment().isMistakes() == true) {
			List<Mistake> mistakes = mistakeService
					.findByAssignmentSub(submission);
			result.setMistakes(mistakes);
		}

		if (submission.getAssignment().isPlagarism() == true) {
			List<Plagarism> plagarisms = plagarismService
					.findByStudent1AndAssignment(submission.getOwner(),
							submission.getAssignment());
			List<Plagarism> plagarisms2 = plagarismService
					.findByStudent2AndAssignment(submission.getOwner(),
							submission.getAssignment());
			plagarisms.addAll(plagarisms2);
			List<com.mbe.umlce.beans.Plagarism> plagarisms1 = new ArrayList<com.mbe.umlce.beans.Plagarism>();
			for (Plagarism p : plagarisms) {
				plagarisms1.add(new com.mbe.umlce.beans.Plagarism(p
						.getStudent1().getFname()
						+ " "
						+ p.getStudent1().getLname(), p.getStudent2()
						.getFname() + " " + p.getStudent2().getLname(), p
						.getPlagPersentage()));
			}
			logger.info(plagarisms1);
			result.setPlagarisms(plagarisms1);
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
			com.mbe.umlce.beans.EvaluationResult evaluationResult = new com.mbe.umlce.beans.EvaluationResult();
			evaluationResult.setErrors(evalmistakes);
			evaluationResult.setTotalMarks(submission.getAssignment()
					.getTotalMarks());
			evaluationResult.setStudentMarks(submission.getMarks());
			result.setEvaluationResult(Arrays.asList(evaluationResult));
		}

		if (submission.getAssignment().isCodemapping() == true) {
			List<MappingMistake> mistakes = mappingMistakeService
					.findByAssignmentSub(submission);
			result.setMappingMistakes(mistakes);
		}

		try {

			JasperCompileManager.compileReport(JRXmlLoader.load(this.getClass()
					.getResourceAsStream("report/Mistakes.jrxml")));
			JasperCompileManager.compileReport(JRXmlLoader.load(this.getClass()
					.getResourceAsStream("CodeMapping.jrxml")));
		} catch (JRException e) {
			logger.error(e);
		}
		return result;
	}

	public List<MistakeReportBean> getAssignmentMistakesReport(String id) {
		Assignment assignment = assignmentService
				.findById(Integer.parseInt(id));
		List<Mistake> mistakes = mistakeService.findByAssignment(assignment);
		List<MistakeReportBean> mistakesreport = new ArrayList<MistakeReportBean>();
		HashMap<String, MistakeReportBean> mistakesreport1 = new HashMap<String, MistakeReportBean>();
		String key = null;
		for (Mistake mistake : mistakes) {
			key = mistake.getErrorName() + "," + mistake.getType() + ","
					+ mistake.getModelType() + "," + mistake.getElementName();
			if (mistakesreport1.containsKey(key)) {
				mistakesreport1.get(key).setCount(
						mistakesreport1.get(key).getCount() + 1);
			} else {
				MistakeReportBean mistakereportbean = new MistakeReportBean(
						mistake.getErrorName(), mistake.getType(),
						mistake.getModelType(), mistake.getElementName(), 1);
				mistakesreport1.put(key, mistakereportbean);
			}
		}
		mistakesreport = new ArrayList<MistakeReportBean>();
		for (Entry<String, MistakeReportBean> entry : mistakesreport1
				.entrySet()) {
			mistakesreport.add(entry.getValue());
		}
		Collections.sort(mistakesreport, new Comparator<MistakeReportBean>() {
			public int compare(MistakeReportBean m1, MistakeReportBean m2) {
				return m2.getCount().compareTo(m1.getCount());
			}
		});

		return mistakesreport;
	}

	public DefaultCategoryDataset getAssignmentMistakesReportGraph(String id) {
		List<MistakeReportBean> mistakesreport = getAssignmentMistakesReport(id);
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (MistakeReportBean mistakeReportBean : mistakesreport) {
			dataset.addValue(mistakeReportBean.getCount(),
					mistakeReportBean.getErrorName(),
					mistakeReportBean.getElementName());
		}
		return dataset;
	}

	public List<AssignmentEvaluationReportBean> getAssignmentEvaluationReport() {
		List<Assignment> assignments = assignmentService.findAll();
		List<AssignmentEvaluationReportBean> reportBeans = new ArrayList<AssignmentEvaluationReportBean>();
		for (Assignment assignment : assignments) {
			if (!assignment.isEvaluation() && assignment.getTitle() == null) {
				continue;
			}
			List<AssignmentSubmission> submissions = assignmentSubmissionService
					.findByAssignment(assignment);
			if (submissions.size() <= 0) {
				continue;
			}
			double min = 100000, max = 0, marks = 0;
			for (AssignmentSubmission submission : submissions) {
				if (submission.getMarks() > max) {
					max = submission.getMarks();
				}
				if (submission.getMarks() < min) {
					min = submission.getMarks();
				}
				marks = marks + submission.getMarks();
			}
			int above=0,less=0;
			marks=marks / submissions.size();
			double standarddaviation = 0;
			for (AssignmentSubmission submission : submissions) {
					standarddaviation=standarddaviation+(submission.getMarks()-marks)*(submission.getMarks()-marks);
				}
			standarddaviation=standarddaviation/submissions.size();
			standarddaviation=Math.sqrt(standarddaviation);
			for (AssignmentSubmission submission : submissions) {
				if(submission.getMarks()>=marks)
				{
					above++;
				}
				if(submission.getMarks()<(marks-standarddaviation))
				{
					less++;
				}
			}
			reportBeans
					.add(new AssignmentEvaluationReportBean(assignment.getId(),
							assignment.getTitle(), submissions.size(), max,
							min, marks / submissions.size(), assignment
									.getTotalMarks(), above, less));
		}
		Collections.sort(reportBeans,
				new Comparator<AssignmentEvaluationReportBean>() {
					public int compare(AssignmentEvaluationReportBean m1,
							AssignmentEvaluationReportBean m2) {
						return m2.getMarks().compareTo(m1.getMarks());
					}
				});
		return reportBeans;
	}

	public DefaultCategoryDataset getAssignmentEvaluationReportGraph() {
		List<AssignmentEvaluationReportBean> evaluationReport = getAssignmentEvaluationReport();
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (AssignmentEvaluationReportBean evaluationReportBean : evaluationReport) {
			double percentage = 0.0;
			try {
				if (evaluationReportBean.getTotal() > 0) {
					percentage = evaluationReportBean.getMarks()
							/ evaluationReportBean.getTotal();
					percentage = percentage * 100;
				}
			} catch (Exception e) {
				logger.error(e);
			}
			dataset.addValue(percentage, evaluationReportBean.getName(),
					evaluationReportBean.getCount() + "");
		}
		return dataset;
	}

	public List<AssignmentPlagarismReportBean> getAssignmentPlagarismReport() {
		List<Assignment> assignments = assignmentService.findAll();
		List<AssignmentPlagarismReportBean> plagarismbeans = new ArrayList<AssignmentPlagarismReportBean>();
		for (Assignment assignment : assignments) {
			if (!assignment.isPlagarism() && assignment.getTitle() == null) {
				continue;
			}
			
			if((new Date()).compareTo(assignment.getEndDate()) < 0){
				continue;
			}
			
			List<AssignmentSubmission> submissions = submissionService
					.findByAssignment(assignment);
			
			if(submissions.size()<=0){
				continue;
			}
			
			
			int above80 = 0, above50 = 0, aboveaverage = 0, less50 = 0;
			double average = 0;
			for (AssignmentSubmission submission : submissions) {
				average = average + submission.getPlagarism();
			}
			average = average / submissions.size();
			for (AssignmentSubmission submission : submissions) {
				if (submission.getPlagarism() >= 80)
					above80++;
				else if (submission.getPlagarism() >= 50)
					above50++;
				else if (submission.getPlagarism() < 50)
					less50++;
				if (submission.getPlagarism() > average)
					aboveaverage++;
			}
			AssignmentPlagarismReportBean assignmentbean = new AssignmentPlagarismReportBean(
					assignment.getId(), assignment.getTitle(),
					submissions.size(), assignment.getEndDate(), above80,
					above50, aboveaverage, less50);
			plagarismbeans.add(assignmentbean);
		}

		Collections.sort(plagarismbeans,
				new Comparator<AssignmentPlagarismReportBean>() {
					public int compare(AssignmentPlagarismReportBean m1,
							AssignmentPlagarismReportBean m2) {
						return m2.getSubmissiondate().compareTo(
								m1.getSubmissiondate());
					}
				});
		return plagarismbeans;
	}

	public DefaultCategoryDataset getAssignmentPlagarismReportGraph() {
		List<AssignmentPlagarismReportBean> plagarismReportBeans = getAssignmentPlagarismReport();
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (AssignmentPlagarismReportBean reportBean : plagarismReportBeans) {
			dataset.addValue(reportBean.getAbove50(), "Above 50",
					reportBean.getName());
			dataset.addValue(reportBean.getAbove80(), "Above 80",
					reportBean.getName());
			dataset.addValue(reportBean.getLess50(), "Below 50",
					reportBean.getName());
			dataset.addValue(reportBean.getAboveaverage(), "Above Avrage",
					reportBean.getName());
		}
		return dataset;
	}

}
