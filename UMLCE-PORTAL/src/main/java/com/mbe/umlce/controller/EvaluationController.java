package com.mbe.umlce.controller;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fyp.umce.ActivityDiagram;
import com.fyp.umce.ClassDiagram;
import com.fyp.umce.DomainModel;
import com.fyp.umce.SequenceDiagram;
import com.fyp.umce.StateMachineDiagram;
import com.fyp.umce.SystemSequenceDiagram;
import com.fyp.umce.UsecaseDiagram;
import com.fyp.umce.dataobject.AD;
import com.fyp.umce.dataobject.CD;
import com.fyp.umce.dataobject.ModelFile;
import com.fyp.umce.dataobject.SM;
import com.fyp.umce.dataobject.SSD;
import com.fyp.umce.dataobject.UD;
import com.fyp.umce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.beans.AssignmentBean;
import com.mbe.umlce.beans.EvaluationCritariaBean;
import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.EvaluationCritaria;
import com.mbe.umlce.service.AssignmentService;
import com.mbe.umlce.service.EvaluationCritariaService;

@Controller
public class EvaluationController {

	/*private static final Logger logger =
	 Logger.getLogger(EvaluationController.class);*/
	@Autowired
	AssignmentService assignmentService;
	@Autowired
	EvaluationCritariaService evaluationCritariaService;

	ClassDiagram classDiagram = new ClassDiagram();
	DomainModel domainModel = new DomainModel();
	ActivityDiagram activityDiagram = new ActivityDiagram();
	UsecaseDiagram usecaseDiagram = new UsecaseDiagram();
	SystemSequenceDiagram systemSequenceDiagram = new SystemSequenceDiagram();
	SequenceDiagram sequenceDiagram = new SequenceDiagram();
	StateMachineDiagram  stateMachine = new StateMachineDiagram();

	@RequestMapping(value = "/portal/assignment/evaluations", method = RequestMethod.GET)
	public String welcome() {
		return "redirect:/portal";
	}

	@RequestMapping(value = "/portal/assignment/evaluation", method = RequestMethod.GET)
	public ModelAndView create(Model model,
			@RequestParam(value = "assignment", required = false) String id) {
		Assignment assignment = assignmentService
				.findById(Integer.parseInt(id));
		try {
			if (assignment.getDiagram().equals("Activity Diagram")) {
				EvaluationCritariaBean evaluation = getActivityModel(assignment
						.getReferenceModel().getBinaryStream());
				evaluation.setAssignment(assignment);
				model.addAttribute("evaluationcritariabean", evaluation);
			} else if (assignment.getDiagram().equals("Class Diagram")) {
				EvaluationCritariaBean evaluation = getClassModel(assignment
						.getReferenceModel().getBinaryStream());
				evaluation.setAssignment(assignment);
				model.addAttribute("evaluationcritariabean", evaluation);
			} else if (assignment.getDiagram().equals("Sequence Diagram")) {
				EvaluationCritariaBean evaluation = getSequenceModel(assignment
						.getReferenceModel().getBinaryStream());
				evaluation.setAssignment(assignment);
				model.addAttribute("evaluationcritariabean", evaluation);
			} else if (assignment.getDiagram()
					.equals("System Sequence Diagram")) {
				EvaluationCritariaBean evaluation = getSystemSequenceModel(assignment
						.getReferenceModel().getBinaryStream());
				evaluation.setAssignment(assignment);
				model.addAttribute("evaluationcritariabean", evaluation);
			} else if (assignment.getDiagram().equals("Usecase Diagram")) {
				EvaluationCritariaBean evaluation = getUsecaseModel(assignment
						.getReferenceModel().getBinaryStream());
				evaluation.setAssignment(assignment);
				model.addAttribute("evaluationcritariabean", evaluation);
			} else if (assignment.getDiagram().equals("Domain Model")) {
				EvaluationCritariaBean evaluation = getDomainModel(assignment
						.getReferenceModel().getBinaryStream());
				evaluation.setAssignment(assignment);
				model.addAttribute("evaluationcritariabean", evaluation);
			}
			else if (assignment.getDiagram().equals("State Machine Diagram")){
				EvaluationCritariaBean evaluation = getStateMachineDiagram(assignment
						.getReferenceModel()
						.getBinaryStream());
				evaluation.setAssignment(assignment);
				model.addAttribute("evaluationcritariabean", evaluation);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("evaluationCritaria");
	}

	@RequestMapping(value = "/portal/assignment/evaluation", method = RequestMethod.POST)
	public ModelAndView createAssignment(
			@ModelAttribute("evaluationcritariabean") EvaluationCritariaBean evaluationbean,
			@RequestParam(value = "assignment", required = false) String page,
			ModelMap model, HttpSession session) {
		model.addAttribute("evaluation", evaluationbean);
		Assignment assignment = assignmentService.findById(evaluationbean
				.getAssignment().getId());
		ArrayList<EvaluationCriteria> critaria = new ArrayList<EvaluationCriteria>();
		if (assignment.getDiagram().equals("Activity Diagram")) {
			if (evaluationbean.getActions() != null)
				critaria.addAll(evaluationbean.getActions());
			if (evaluationbean.getDecisions() != null)
				critaria.addAll(evaluationbean.getDecisions());
			if (evaluationbean.getNodes() != null)
				critaria.addAll(evaluationbean.getNodes());
		} else if (assignment.getDiagram().equals("Class Diagram")) {
			if (evaluationbean.getClasses() != null)
				critaria.addAll(evaluationbean.getClasses());
		} else if (assignment.getDiagram().equals("Sequence Diagram")) {
			if (evaluationbean.getLifelines() != null)
				critaria.addAll(evaluationbean.getLifelines());
			if (evaluationbean.getOprations() != null)
				critaria.addAll(evaluationbean.getOprations());
		} else if (assignment.getDiagram().equals("System Sequence Diagram")) {

			if (evaluationbean.getLifelines() != null)
				critaria.addAll(evaluationbean.getLifelines());
			if (evaluationbean.getOprations() != null)
				critaria.addAll(evaluationbean.getOprations());
		} else if (assignment.getDiagram().equals("Usecase Diagram")) {
			if (evaluationbean.getActors() != null)
				critaria.addAll(evaluationbean.getActors());
			if (evaluationbean.getUsecases() != null)
				critaria.addAll(evaluationbean.getUsecases());
		} else if (assignment.getDiagram().equals("Domain Model")) {
			if (evaluationbean.getConcepts() != null)
				critaria.addAll(evaluationbean.getConcepts());
		}
		else if (assignment.getDiagram().equals("State Machine Diagram")){
			if (evaluationbean.getClasses() != null)
				critaria.addAll(evaluationbean.getClasses());
		}
		if (evaluationbean.getMistakes() != null)
			critaria.addAll(evaluationbean.getMistakes());
		assignmentService.updateTotalMarks(assignment.getId(),
				evaluationbean.getTotalMarks());
		for (EvaluationCriteria crit : critaria) {
			evaluationCritariaService.save(new EvaluationCritaria(crit
					.getType(), crit.getElementName(), crit.isEssential(), crit
					.getMarks(), evaluationbean.getAssignment()));
		}

		List<Assignment> assignments = assignmentService.findAll();
		model.addAttribute("assignmentList", assignments);
		model.addAttribute("assignmentbean", new AssignmentBean());
		return new ModelAndView("viewAssignments");
	}

	private EvaluationCritariaBean getActivityModel(InputStream streamSolution) {

		ArrayList<EvaluationCriteria> actions = new ArrayList<EvaluationCriteria>();
		ArrayList<EvaluationCriteria> decisions = new ArrayList<EvaluationCriteria>();
		ArrayList<EvaluationCriteria> nodes = new ArrayList<EvaluationCriteria>();
		ArrayList<EvaluationCriteria> mistakes = new ArrayList<EvaluationCriteria>();
		try {
			AD ad = activityDiagram.getRefModelDetails(new ModelFile(
					streamSolution));
			for (String action : ad.getOpaqueActions()) {
				actions.add(new EvaluationCriteria("Action", action, false, 0.0));
			}

			for (String decision : ad.getDecisions()) {
				decisions.add(new EvaluationCriteria("Decision", decision,
						false, 0.0));
			}

			for (String othernode : ad.getForks()) {
				nodes.add(new EvaluationCriteria("Node", othernode, false, 0.0));
			}
			for (String othernode : ad.getJoins()) {
				nodes.add(new EvaluationCriteria("Node", othernode, false, 0.0));
			}
			for (String othernode : ad.getMerges()) {
				nodes.add(new EvaluationCriteria("Node", othernode, false, 0.0));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		String[] mistakesData;
		try {
			mistakesData = activityDiagram.getMistakesList();
			for (int i = 0; i < mistakesData.length; i++) {
				mistakes.add(new EvaluationCriteria("Mistake", mistakesData[i],
						false, 0.0));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		EvaluationCritariaBean evaluation = new EvaluationCritariaBean();
		evaluation.setActions(actions);
		evaluation.setDecisions(decisions);
		evaluation.setNodes(nodes);
		evaluation.setMistakes(mistakes);
		return evaluation;
	}

	private EvaluationCritariaBean getUsecaseModel(InputStream streamSolution) {

		ArrayList<EvaluationCriteria> mistakes = new ArrayList<EvaluationCriteria>();
		ArrayList<EvaluationCriteria> actors = new ArrayList<EvaluationCriteria>();
		ArrayList<EvaluationCriteria> usecases = new ArrayList<EvaluationCriteria>();
		try {
			UD ud = usecaseDiagram.getRefModelDetails(new ModelFile(
					streamSolution));
			for (String actor : ud.getActors()) {
				actors.add(new EvaluationCriteria("Actor", actor, false, 0.0));
			}

			for (String usecase : ud.getUsecases()) {
				usecases.add(new EvaluationCriteria("Usecase", usecase, false,
						0.0));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String[] mistakesData = usecaseDiagram.getMistakesList();
			for (int i = 0; i < mistakesData.length; i++) {
				mistakes.add(new EvaluationCriteria("Mistake", mistakesData[i],
						false, 0.0));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		EvaluationCritariaBean evaluation = new EvaluationCritariaBean();
		evaluation.setActors(actors);
		evaluation.setUsecases(usecases);
		evaluation.setMistakes(mistakes);
		return evaluation;
	}

	private EvaluationCritariaBean getSequenceModel(InputStream streamSolution) {

		ArrayList<EvaluationCriteria> mistakes = new ArrayList<EvaluationCriteria>();
		ArrayList<EvaluationCriteria> lifelines = new ArrayList<EvaluationCriteria>();
		ArrayList<EvaluationCriteria> oprations = new ArrayList<EvaluationCriteria>();

		try {
			SSD sd = sequenceDiagram.getRefModelDetails(new ModelFile(
					streamSolution));
			for (String actor : sd.getLifelines()) {
				lifelines.add(new EvaluationCriteria("LifeLine", actor, false,
						0.0));
			}

			for (String systemSequence : sd.getOperations()) {
				oprations.add(new EvaluationCriteria("Operation",
						systemSequence, false, 0.0));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String[] mistakesData = systemSequenceDiagram.getMistakesList();
			for (int i = 0; i < mistakesData.length; i++) {
				mistakes.add(new EvaluationCriteria("Mistake", mistakesData[i],
						false, 0.0));
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		EvaluationCritariaBean evaluation = new EvaluationCritariaBean();
		evaluation.setLifelines(lifelines);
		evaluation.setOprations(oprations);
		evaluation.setMistakes(mistakes);
		return evaluation;
	}

	private EvaluationCritariaBean getSystemSequenceModel(
			InputStream streamSolution) {

		ArrayList<EvaluationCriteria> mistakes = new ArrayList<EvaluationCriteria>();
		ArrayList<EvaluationCriteria> lifelines = new ArrayList<EvaluationCriteria>();
		ArrayList<EvaluationCriteria> oprations = new ArrayList<EvaluationCriteria>();

		try {
			SSD sd = systemSequenceDiagram.getRefModelDetails(new ModelFile(
					streamSolution));
			for (String actor : sd.getLifelines()) {
				lifelines.add(new EvaluationCriteria("LifeLine", actor, false,
						0.0));
			}

			for (String systemSequence : sd.getOperations()) {
				oprations.add(new EvaluationCriteria("Operation",
						systemSequence, false, 0.0));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String[] mistakesData = systemSequenceDiagram.getMistakesList();
			for (int i = 0; i < mistakesData.length; i++) {
				mistakes.add(new EvaluationCriteria("Mistake", mistakesData[i],
						false, 0.0));
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EvaluationCritariaBean evaluation = new EvaluationCritariaBean();
		evaluation.setLifelines(lifelines);
		evaluation.setOprations(oprations);
		evaluation.setMistakes(mistakes);
		return evaluation;
	}

	private EvaluationCritariaBean getClassModel(InputStream streamSolution)
			throws Exception {
		ArrayList<EvaluationCriteria> mistakes = new ArrayList<EvaluationCriteria>();
		ArrayList<EvaluationCriteria> classes = new ArrayList<EvaluationCriteria>();

		try {
			for (CD cd : classDiagram.getRefModelDetails(new ModelFile(
					streamSolution))) {
				classes.add(new EvaluationCriteria("Class", cd.getClassName(),
						false, 0.0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String[] mistakesData = classDiagram.getMistakesList();
		for (int i = 0; i < mistakesData.length; i++) {
			mistakes.add(new EvaluationCriteria("Mistake", mistakesData[i],
					false, 0.0));
		}

		EvaluationCritariaBean evaluation = new EvaluationCritariaBean();
		evaluation.setClasses(classes);
		evaluation.setMistakes(mistakes);
		return evaluation;
	}

	private EvaluationCritariaBean getDomainModel(InputStream streamSolution)
			throws Exception {
		ArrayList<EvaluationCriteria> mistakes = new ArrayList<EvaluationCriteria>();
		ArrayList<EvaluationCriteria> concepts = new ArrayList<EvaluationCriteria>();
		try {
			for (CD cd : domainModel.getRefModelDetails(new ModelFile(
					streamSolution))) {
				concepts.add(new EvaluationCriteria("Concept", cd
						.getClassName(), false, 0.0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String[] mistakesData = domainModel.getMistakesList();
		for (int i = 0; i < mistakesData.length; i++) {
			mistakes.add(new EvaluationCriteria("Mistake", mistakesData[i],
					false, 0.0));
		}

		EvaluationCritariaBean evaluation = new EvaluationCritariaBean();
		evaluation.setConcepts(concepts);
		evaluation.setMistakes(mistakes);
		return evaluation;
	}
	
	private EvaluationCritariaBean getStateMachineDiagram(InputStream streamSolution) throws Exception
	{
		ArrayList<EvaluationCriteria> mistakes = new ArrayList<EvaluationCriteria>();
		ArrayList<EvaluationCriteria> classes = new ArrayList<EvaluationCriteria>();
		//System.out.println("ass" + stateMachine.getRefModelDetails(new ModelFile(streamSolution)).size());
		for (SM sm : stateMachine.getRefModelDetails(new ModelFile(streamSolution)))
		 {
			
			 classes.add(new EvaluationCriteria("State", sm.getName(),
						false, 0.0));
		 }
		
		String[] mistakesData = stateMachine.getMistakesList();
		for (int i = 0; i < mistakesData.length; i++) {
			mistakes.add(new EvaluationCriteria("Mistake", mistakesData[i],
					false, 0.0));
		}

		EvaluationCritariaBean evaluation = new EvaluationCritariaBean();
		evaluation.setClasses(classes);
		evaluation.setMistakes(mistakes);
		return evaluation;
		
	}
}
