package com.mbe.umlce.reports;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fyp.umce.ActivityDiagram;
import com.fyp.umce.ClassDiagram;
import com.fyp.umce.DomainModel;
import com.fyp.umce.PackageDiagram;
import com.fyp.umce.SequenceDiagram;
import com.fyp.umce.StateMachineDiagram;
import com.fyp.umce.SystemSequenceDiagram;
import com.fyp.umce.UsecaseDiagram;
import com.fyp.umce.dataobject.Errors;
import com.fyp.umce.dataobject.ModelFile;
import com.fyp.umce.dataobject.result.EvaluationCriteria;
import com.fyp.umce.dataobject.result.EvaluationResult;
import com.fyp.umce.dataobject.result.EvaluationResultError;
import com.fyp.umce.dataobject.result.EvaluationResultErrorsDetail;
import com.fyp.umce.dataobject.result.MappingErrors;
import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.AssignmentSubmission;
import com.mbe.umlce.model.EvalErrorDetail;
import com.mbe.umlce.model.EvaluationCritaria;
import com.mbe.umlce.model.EvaluationError;
import com.mbe.umlce.model.MappingMistake;
import com.mbe.umlce.model.Mistake;
import com.mbe.umlce.service.AssignmentResourceService;
import com.mbe.umlce.service.AssignmentService;
import com.mbe.umlce.service.AssignmentSubmissionService;
import com.mbe.umlce.service.EvalErrorDetailService;
import com.mbe.umlce.service.EvaluationCritariaService;
import com.mbe.umlce.service.EvaluationErrorService;
import com.mbe.umlce.service.MappingMistakeService;
import com.mbe.umlce.service.MistakeService;
import com.mbe.umlce.service.PlagarismFinder;
import com.mbe.umlce.service.PlagarismService;
import com.mbe.umlce.service.SubmissionResourceService;

@Service
public class ResultService {
	private static final Logger logger = Logger.getLogger(ResultService.class);
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
	@Autowired
	EvaluationCritariaService evaluationCriteriaService;
	@Autowired
	EvaluationErrorService evaluationErrorService;
	@Autowired
	EvalErrorDetailService evalErrorDetailService;
	@Autowired
	PlagarismService plagarismService;
	@Autowired
	EvaluationErrorService evaluationService;
	private ClassDiagram classIdentifier = new ClassDiagram();
	private UsecaseDiagram usecaseIdentifier = new UsecaseDiagram();
	private DomainModel domainIdentifier = new DomainModel();
	private SystemSequenceDiagram systemSequenceIdentifier = new SystemSequenceDiagram();
	private SequenceDiagram sequenceIdentifier = new SequenceDiagram();
	private ActivityDiagram activityIdentifier = new ActivityDiagram();
	private PackageDiagram packageIdentifier = new PackageDiagram();
	private StateMachineDiagram stateIdentifier = new StateMachineDiagram();

	public void performResults(AssignmentSubmission assignmentSubmission,
			MultipartFile model) {
		if (assignmentSubmission.getAssignment().isMistakes()) {
			try {
				ArrayList<Errors> mistakes = checkMistakes(
						assignmentSubmission.getAssignment(),
						model.getInputStream());
				for (Errors error : mistakes) {
					Mistake errors = new Mistake(error.getErrorName(),
							error.getType(), error.getModelType(),
							error.getErrorDiscrption(), error.getElementName(),
							assignmentSubmission.getOwner(),
							assignmentSubmission.getAssignment(),
							assignmentSubmission);
					mistakeService.save(errors);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (assignmentSubmission.getAssignment().isCodemapping()) {
			try {
				ArrayList<MappingErrors> mistakes = codeMapping(
						assignmentSubmission.getAssignment(),
						model.getInputStream());
				if (mistakes != null)
					for (MappingErrors error : mistakes) {
						MappingMistake mistake = new MappingMistake(
								error.getErrorDiscrption(),
								error.getElementName(),
								assignmentSubmission.getOwner(),
								assignmentSubmission.getAssignment(),
								assignmentSubmission);
						mappingMistakeService.save(mistake);
					}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (assignmentSubmission.getAssignment().isEvaluation()) {
			try {
				logger.info(assignmentSubmission.getAssignment().getDiagram());
				if (evaluationCriteriaService == null)
					logger.info(assignmentSubmission.getAssignment().getId());
				List<EvaluationCritaria> critaria = evaluationCriteriaService
						.findByAssignment(assignmentSubmission.getAssignment());

				ArrayList<EvaluationCriteria> markers = new ArrayList<EvaluationCriteria>();
				for (EvaluationCritaria cri : critaria) {
					EvaluationCriteria eval = new com.fyp.umce.dataobject.result.EvaluationCriteria(
							cri.getType(), cri.getElementName(),
							cri.isEssential(), cri.getMarks());
					markers.add(eval);
				}
				EvaluationResult result = evaluateAssignment(
						assignmentSubmission.getAssignment(), markers,
						model.getInputStream(), assignmentSubmission
								.getAssignment().getReferenceModel()
								.getBinaryStream());
				assignmentSubmissionService.updateMarks(
						assignmentSubmission.getId(), result.getStudentMarks());
				for (EvaluationResultError error : result.getErrors()) {
					EvaluationError errors = new EvaluationError(
							error.getErrorName(), error.getErrorCount(),
							assignmentSubmission);
					errors = evaluationErrorService.save(errors);
					for (EvaluationResultErrorsDetail detail : error
							.getDetail()) {
						EvalErrorDetail errorDetail = new EvalErrorDetail(
								detail.getElementName(),
								detail.getErrorDiscption(), errors);
						logger.info(errorDetail.getDescription() + "--"
								+ errorDetail.getElementName());
						evalErrorDetailService.save(errorDetail);
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private ArrayList<Errors> checkMistakes(Assignment assignment,
			InputStream model) {
		ArrayList<Errors> mistakes = null;
		try {
			switch (assignment.getDiagram()) {
			case "Class Diagram":
				mistakes = classIdentifier.identifyMistakes(
						new ModelFile(model)).getErrors();
				break;
			case "Domain Model":
				mistakes = domainIdentifier.identifyMistakes(
						new ModelFile(model)).getErrors();
				break;
			case "Activity Diagram":
				mistakes = activityIdentifier.identifyMistakes(
						new ModelFile(model)).getErrors();
				break;
			case "Sequence Diagram":
				mistakes = sequenceIdentifier.identifyMistakes(
						new ModelFile(model)).getErrors();
				break;
			case "System Sequence Diagram":
				mistakes = systemSequenceIdentifier.identifyMistakes(
						new ModelFile(model)).getErrors();
				break;
			case "Usecase Diagram":
				mistakes = usecaseIdentifier.identifyMistakes(
						new ModelFile(model)).getErrors();
				break;
			case "State Machine Diagram":
				mistakes = stateIdentifier.identifyMistakes(
						new ModelFile(model)).getErrors();
				break;	

			}
			return mistakes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private EvaluationResult evaluateAssignment(
			Assignment assignment,
			ArrayList<com.fyp.umce.dataobject.result.EvaluationCriteria> markers,
			InputStream model, InputStream solution) {
		EvaluationResult result = null;
		try {
			switch (assignment.getDiagram()) {
			case "Class Diagram":
				result = classIdentifier.evaluateModel(new ModelFile(solution),
						new ModelFile(model), markers,
						assignment.getTotalMarks()).getEvaluationResult();
				break;
			case "Domain Model":
				result = domainIdentifier.evaluateModel(
						new ModelFile(solution), new ModelFile(model), markers,
						assignment.getTotalMarks()).getEvaluationResult();
				break;
			case "Activity Diagram":
				result = activityIdentifier.evaluateModel(
						new ModelFile(solution), new ModelFile(model), markers,
						assignment.getTotalMarks()).getEvaluationResult();
				break;
			case "Sequence Diagram":
				result = sequenceIdentifier.evaluateModel(
						new ModelFile(solution), new ModelFile(model), markers,
						assignment.getTotalMarks()).getEvaluationResult();
				break;
			case "System Sequence Diagram":
				result = systemSequenceIdentifier.evaluateModel(
						new ModelFile(solution), new ModelFile(model), markers,
						assignment.getTotalMarks()).getEvaluationResult();
				break;
			case "Usecase Diagram":
				result = usecaseIdentifier.evaluateModel(
						new ModelFile(solution), new ModelFile(model), markers,
						assignment.getTotalMarks()).getEvaluationResult();
				break;
			case "State Machine Diagram":
				result = stateIdentifier.evaluateModel(
						new ModelFile(solution), new ModelFile(model), markers,
						assignment.getTotalMarks()).getEvaluationResult();
				break;	

			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<MappingErrors> codeMapping(Assignment assignment,
			InputStream model) {
		ArrayList<MappingErrors> mistakes = null;
		try {
			switch (assignment.getDiagram()) {
			case "Class Diagram":
				mistakes = classIdentifier.codeMapping(
						new ModelFile(model),
						new ModelFile(assignment.getSourceCode()
								.getBinaryStream())).getMappingErrors();
				break;
			case "Sequence Diagram":
				mistakes = sequenceIdentifier.codeMapping(
						new ModelFile(model),
						new ModelFile(assignment.getSourceCode()
								.getBinaryStream())).getMappingErrors();
				break;
			case "Package Diagram":
				mistakes = packageIdentifier.codeMapping(
						new ModelFile(model),
						new ModelFile(assignment.getSourceCode()
								.getBinaryStream())).getMappingErrors();
				break;

			}
			return mistakes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void plagtriger(Date date, int id) {
		JobDetail job = JobBuilder.newJob(PlagarismFinder.class)
				.withIdentity(id+"_Job_"+date.toString(), "group1").usingJobData("id", id)
				.build();

		Trigger trigger = TriggerBuilder.newTrigger().startAt(date)
				.withIdentity(id+"_trigger_"+date.toString(), "group1")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()).build();

		// schedule it
		Scheduler scheduler;
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

}
