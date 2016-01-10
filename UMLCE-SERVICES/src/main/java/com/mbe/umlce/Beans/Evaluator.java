package com.mbe.umlce.Beans;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

import com.mbe.umlce.ActivityDiagramController;
import com.mbe.umlce.ClassDiagramController;
import com.mbe.umlce.DomainModelController;
import com.mbe.umlce.SequenceDiagramController;
import com.mbe.umlce.StateMachineDiagramController;
import com.mbe.umlce.SystemSequenceDiagramController;
import com.mbe.umlce.UsecaseDiagramController;
import com.mbe.umlce.dataobject.AD;
import com.mbe.umlce.dataobject.CD;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.SM;
import com.mbe.umlce.dataobject.SSD;
import com.mbe.umlce.dataobject.UD;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.EvaluationResult;
import com.mbe.umlce.dataobject.result.EvaluationResultError;
import com.mbe.umlce.dataobject.result.EvaluationResultErrorsDetail;

@ManagedBean
@SessionScoped
public class Evaluator implements Serializable {

	private static final long serialVersionUID = -467371840515138740L;
	private ArrayList<EvaluationResultErrorsDetail> resultdetails = new ArrayList<EvaluationResultErrorsDetail>();
	private transient Part modelDiagram;
	private transient Part solutionDiagram;
	private ArrayList<EvaluationCriteria> mistakes = new ArrayList<EvaluationCriteria>();
	private ArrayList<EvaluationCriteria> classes = new ArrayList<EvaluationCriteria>();
	private ArrayList<EvaluationCriteria> concepts = new ArrayList<EvaluationCriteria>();
	private ArrayList<EvaluationCriteria> lifelines = new ArrayList<EvaluationCriteria>();
	private ArrayList<EvaluationCriteria> oprations = new ArrayList<EvaluationCriteria>();
	private ArrayList<EvaluationCriteria> actors = new ArrayList<EvaluationCriteria>();
	private ArrayList<EvaluationCriteria> usecases = new ArrayList<EvaluationCriteria>();
	private ArrayList<EvaluationCriteria> actions = new ArrayList<EvaluationCriteria>();
	private ArrayList<EvaluationCriteria> decisions = new ArrayList<EvaluationCriteria>();
	private ArrayList<EvaluationCriteria> nodes = new ArrayList<EvaluationCriteria>();

	ClassDiagramController classDiagram = new ClassDiagramController();
	DomainModelController domainModel = new DomainModelController();
	UsecaseDiagramController usecaseDiagram = new UsecaseDiagramController();
	ActivityDiagramController activityDiagram = new ActivityDiagramController();
	SystemSequenceDiagramController systemSequenceDiagram = new SystemSequenceDiagramController();
	SequenceDiagramController sequenceDiagram = new SequenceDiagramController();
	StateMachineDiagramController stateMachine = new StateMachineDiagramController();

	private String displayPanel = "none";
	private String resultPanel = "none";
	private String loadModel = "";
	private double totalMarks;
	private double studentMarks;

	private ArrayList<EvaluationResultError> evaluationResult = new ArrayList<EvaluationResultError>();

	private String diagramPanel = "none";
	private String solutionPanel = "none";
	private double diagramFileSize = 0;
	private double solutionfileSize = 0;

	public void onPageLoad(ComponentSystemEvent event) {
		if (FacesContext.getCurrentInstance().getPartialViewContext()
				.isAjaxRequest()) {
			return;
		}

		classes = new ArrayList<EvaluationCriteria>();
		concepts = new ArrayList<EvaluationCriteria>();
		lifelines = new ArrayList<EvaluationCriteria>();
		oprations = new ArrayList<EvaluationCriteria>();
		actors = new ArrayList<EvaluationCriteria>();
		usecases = new ArrayList<EvaluationCriteria>();

		displayPanel = "none";
		resultPanel = "none";
		loadModel = "";
		modelDiagram = null;
		solutionDiagram = null;
		diagramPanel = "none";
		solutionPanel = "none";
		diagramFileSize = 0;
		solutionfileSize = 0;

	}

	public String cencelDiagram() {
		diagramPanel = "none";
		modelDiagram = null;
		diagramFileSize = 0;
		return "true";
	}

	public String cencelSolution() {
		solutionPanel = "none";
		solutionDiagram = null;
		solutionfileSize = 0;
		return "true";
	}

	public void diagramValueChange() {
		diagramPanel = "";
	}

	public void solutionValueChange() {
		solutionPanel = "";
	}

	public void diagramValidator(FacesContext context,
			UIComponent componentToValidate, Object value)
			throws ValidatorException {
		Part Diagram = (Part) value;
		String temp = getFilename(Diagram);
		diagramFileSize = Diagram.getSize() / 1000;
		String type = temp.substring(temp.length() - 3);
		if (!type.equals("uml")) {
			FacesMessage message = new FacesMessage("File type " + type
					+ " not allowed");
			modelDiagram = Diagram;
			diagramPanel = "";
			context.addMessage(componentToValidate.getId(), message);
			throw new ValidatorException(message);
		}
	}

	public void solutionalidator(FacesContext context,
			UIComponent componentToValidate, Object value)
			throws ValidatorException {
		Part Diagram = (Part) value;
		String temp = getFilename(Diagram);
		solutionfileSize = Diagram.getSize() / 1000;
		String type = temp.substring(temp.length() - 3);
		if (!type.equals("uml")) {
			FacesMessage message = new FacesMessage("File type " + type
					+ " not allowed");
			solutionDiagram = Diagram;
			solutionPanel = "";
			context.addMessage(componentToValidate.getId(), message);
			throw new ValidatorException(message);
		}
	}

	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1)
						.substring(filename.lastIndexOf('\\') + 1);

			}
		}
		return null;
	}

	public String getClassModel() {

		displayPanel = "";
		loadModel = "none";
		try {
			InputStream streamSolution = solutionDiagram.getInputStream();
			for (CD cd : classDiagram.getRefModelDetails(new ModelFile(
					streamSolution))) {
				classes.add(new EvaluationCriteria("Class", cd.getClassName(),
						false, 0.0));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}

		String[] mistakesData = classDiagram.getMistakesList();
		for (int i = 0; i < mistakesData.length; i++) {
			mistakes.add(new EvaluationCriteria("Mistake", mistakesData[i],
					false, 0.0));
		}

		return "true";
	}

	public String evaluateClassDiagram() {
		displayPanel = "none";
		resultPanel = "";
		InputStream streamSolution = null;
		InputStream streamModel = null;
		try {
			streamSolution = solutionDiagram.getInputStream();
			streamModel = modelDiagram.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}

		ArrayList<EvaluationCriteria> markers = getClasses();
		for (EvaluationCriteria marker : getMistakes()) {
			markers.add(marker);
		}
		try {
			EvaluationResult result = classDiagram.evaluateModel(
					new ModelFile(streamSolution), new ModelFile(streamModel),
					markers, totalMarks).getEvaluationResult();
			totalMarks = result.getTotalMarks();
			studentMarks = result.getStudentMarks();
			evaluationResult = result.getErrors();

		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}

		return "true";
	}

	public String getDomainModel() {
		displayPanel = "";
		loadModel = "none";

		try {
			InputStream streamSolution = solutionDiagram.getInputStream();
			for (CD cd : domainModel.getRefModelDetails(new ModelFile(
					streamSolution))) {
				concepts.add(new EvaluationCriteria("Concept", cd
						.getClassName(), false, 0.0));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}

		String[] mistakesData = domainModel.getMistakesList();
		for (int i = 0; i < mistakesData.length; i++) {
			mistakes.add(new EvaluationCriteria("Mistake", mistakesData[i],
					false, 0.0));
		}

		return "true";
	}

	public String evaluateDomainModel() {
		displayPanel = "none";
		resultPanel = "";
		InputStream streamSolution = null;
		InputStream streamModel = null;
		try {
			streamSolution = solutionDiagram.getInputStream();
			streamModel = modelDiagram.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}

		ArrayList<EvaluationCriteria> markers = getConcepts();
		for (EvaluationCriteria marker : getMistakes()) {
			markers.add(marker);
		}

		try {

			EvaluationResult result = domainModel.evaluateModel(
					new ModelFile(streamSolution), new ModelFile(streamModel),
					markers, totalMarks).getEvaluationResult();
			totalMarks = result.getTotalMarks();
			studentMarks = result.getStudentMarks();
			evaluationResult = result.getErrors();
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}

		return "true";
	}

	public String getUsecaseModel() {
		displayPanel = "";
		loadModel = "none";

		try {
			InputStream streamSolution = solutionDiagram.getInputStream();
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
			return "false";
		}

		String[] mistakesData = usecaseDiagram.getMistakesList();
		for (int i = 0; i < mistakesData.length; i++) {
			mistakes.add(new EvaluationCriteria("Mistake", mistakesData[i],
					false, 0.0));
		}

		return "true";
	}

	public String evaluateUsecaseDiagram() {
		displayPanel = "none";
		resultPanel = "";
		InputStream streamSolution = null;
		InputStream streamModel = null;
		try {
			streamSolution = solutionDiagram.getInputStream();
			streamModel = modelDiagram.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}

		ArrayList<EvaluationCriteria> markers = getActors();
		for (EvaluationCriteria marker : getUsecases()) {
			markers.add(marker);
		}
		for (EvaluationCriteria marker : getMistakes()) {
			markers.add(marker);
		}

		try {

			EvaluationResult result = usecaseDiagram.evaluateModel(
					new ModelFile(streamSolution), new ModelFile(streamModel),
					markers, totalMarks).getEvaluationResult();
			totalMarks = result.getTotalMarks();
			studentMarks = result.getStudentMarks();
			evaluationResult = result.getErrors();
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}

		return "true";
	}

	public String getSequenceModel() {
		displayPanel = "";
		loadModel = "none";

		try {
			InputStream streamSolution = solutionDiagram.getInputStream();
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
			return "false";
		}

		String[] mistakesData = systemSequenceDiagram.getMistakesList();
		for (int i = 0; i < mistakesData.length; i++) {
			mistakes.add(new EvaluationCriteria("Mistake", mistakesData[i],
					false, 0.0));
		}

		return "true";
	}

	public String evaluateSequenceDiagram() {
		displayPanel = "none";
		resultPanel = "";
		InputStream streamSolution = null;
		InputStream streamModel = null;
		try {
			streamSolution = solutionDiagram.getInputStream();
			streamModel = modelDiagram.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}

		ArrayList<EvaluationCriteria> markers = getLifelines();
		for (EvaluationCriteria marker : getOprations()) {
			markers.add(marker);
		}
		for (EvaluationCriteria marker : getMistakes()) {
			markers.add(marker);
		}

		try {

			EvaluationResult result = sequenceDiagram.evaluateModel(
					new ModelFile(streamSolution), new ModelFile(streamModel),
					markers, totalMarks).getEvaluationResult();
			totalMarks = result.getTotalMarks();
			studentMarks = result.getStudentMarks();
			evaluationResult = result.getErrors();
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}

		return "true";
	}

	public String getSystemSequenceModel() {
		displayPanel = "";
		loadModel = "none";

		try {
			InputStream streamSolution = solutionDiagram.getInputStream();
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
			return "false";
		}

		String[] mistakesData = systemSequenceDiagram.getMistakesList();
		for (int i = 0; i < mistakesData.length; i++) {
			mistakes.add(new EvaluationCriteria("Mistake", mistakesData[i],
					false, 0.0));
		}

		return "true";
	}

	public String evaluateSystemSequenceDiagram() {
		displayPanel = "none";
		resultPanel = "";
		InputStream streamSolution = null;
		InputStream streamModel = null;
		try {
			streamSolution = solutionDiagram.getInputStream();
			streamModel = modelDiagram.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}

		ArrayList<EvaluationCriteria> markers = getLifelines();
		for (EvaluationCriteria marker : getOprations()) {
			markers.add(marker);
		}
		for (EvaluationCriteria marker : getMistakes()) {
			markers.add(marker);
		}

		try {

			EvaluationResult result = systemSequenceDiagram.evaluateModel(
					new ModelFile(streamSolution), new ModelFile(streamModel),
					markers, totalMarks).getEvaluationResult();
			totalMarks = result.getTotalMarks();
			studentMarks = result.getStudentMarks();
			evaluationResult = result.getErrors();
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}

		return "true";
	}
	
	public String getActivityModel() {
		displayPanel = "";
		loadModel = "none";

		try {
			InputStream streamSolution = solutionDiagram.getInputStream();
			AD ad = activityDiagram.getRefModelDetails(new ModelFile(
					streamSolution));
			for (String action : ad.getOpaqueActions()) {
				actions.add(new EvaluationCriteria("Action", action, false, 0.0));
			}

			for (String decision : ad.getDecisions()) {
				decisions.add(new EvaluationCriteria("Decision", decision, false,
						0.0));
			}
			for (String othernode : ad.getForks()) {
				nodes.add(new EvaluationCriteria("Node", othernode, false,
						0.0));
			}
			for (String othernode : ad.getJoins()) {
				nodes.add(new EvaluationCriteria("Node", othernode, false,
						0.0));
			}
			for (String othernode : ad.getMerges()) {
				nodes.add(new EvaluationCriteria("Node", othernode, false,
						0.0));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}

		String[] mistakesData = activityDiagram.getMistakesList();
		for (int i = 0; i < mistakesData.length; i++) {
			mistakes.add(new EvaluationCriteria("Mistake", mistakesData[i],
					false, 0.0));
		}

		return "true";
	}
	
	public String evaluateActivityDiagram() {
		displayPanel = "none";
		resultPanel = "";
		InputStream streamSolution = null;
		InputStream streamModel = null;
		try {
			streamSolution = solutionDiagram.getInputStream();
			streamModel = modelDiagram.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}

		ArrayList<EvaluationCriteria> markers = getActions();
		for (EvaluationCriteria marker : getDecisions()) {
			markers.add(marker);
		}
		for (EvaluationCriteria marker : getMistakes()) {
			markers.add(marker);
		}
		for (EvaluationCriteria marker : getNodes()) {
			markers.add(marker);
		}

		try {

			EvaluationResult result = activityDiagram.evaluateModel(
					new ModelFile(streamSolution), new ModelFile(streamModel),
					markers, totalMarks).getEvaluationResult();
			totalMarks = result.getTotalMarks();
			studentMarks = result.getStudentMarks();
			evaluationResult = result.getErrors();
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}

		return "true";
	}
	
	public String getStateMachineModel() {
		displayPanel = "";
		loadModel = "none";
		
		
		try {
			InputStream streamSolution = solutionDiagram.getInputStream();
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
		}
		catch (Exception e) {
			e.printStackTrace();
			return "false";
		}

		return "true";
	}
	
	public String evaluateStateMachineDiagram()
	{
		
		displayPanel = "none";
		resultPanel = "";
		
		InputStream streamSolution = null;
		InputStream streamModel = null;
		try {
			streamSolution = solutionDiagram.getInputStream();
			streamModel = modelDiagram.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
		ArrayList<EvaluationCriteria> markers = getActions();
		for (EvaluationCriteria marker : getMistakes()) {
			markers.add(marker);
		}
		for (EvaluationCriteria marker : getClasses()) {
			markers.add(marker);
		}
		
		try {

			EvaluationResult result = stateMachine.evaluateModel(
					new ModelFile(streamSolution), new ModelFile(streamModel),
					markers, totalMarks).getEvaluationResult();
			totalMarks = result.getTotalMarks();
			System.out.println("Marks : "+ totalMarks);
			studentMarks = result.getStudentMarks();
			evaluationResult = result.getErrors();
			System.out.println("Result : "+evaluationResult.get(0).getErrorName());
			System.out.println("Result 1: "+evaluationResult.get(0).getErrorCount());
			System.out.println("Result 2: "+evaluationResult.get(0).getDetail().get(0).getElementName());
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
		
		return "true";
	}

	public ArrayList<EvaluationResultErrorsDetail> getResultdetails() {
		return resultdetails;
	}

	public void setResultdetails(
			ArrayList<EvaluationResultErrorsDetail> resultdetails) {
		this.resultdetails = resultdetails;
	}

	public Part getModelDiagram() {
		return modelDiagram;
	}

	public void setModelDiagram(Part modelDiagram) {
		this.modelDiagram = modelDiagram;
	}

	public Part getSolutionDiagram() {
		return solutionDiagram;
	}

	public void setSolutionDiagram(Part solutionDiagram) {
		this.solutionDiagram = solutionDiagram;
	}

	public ArrayList<EvaluationCriteria> getMistakes() {
		return mistakes;
	}

	public void setMistakes(ArrayList<EvaluationCriteria> mistakes) {
		this.mistakes = mistakes;
	}

	public ArrayList<EvaluationCriteria> getClasses() {
		return classes;
	}

	public void setClasses(ArrayList<EvaluationCriteria> classes) {
		this.classes = classes;
	}

	public ArrayList<EvaluationCriteria> getConcepts() {
		return concepts;
	}

	public void setConcepts(ArrayList<EvaluationCriteria> concepts) {
		this.concepts = concepts;
	}

	public ArrayList<EvaluationCriteria> getLifelines() {
		return lifelines;
	}

	public void setLifelines(ArrayList<EvaluationCriteria> lifelines) {
		this.lifelines = lifelines;
	}

	public ArrayList<EvaluationCriteria> getOprations() {
		return oprations;
	}

	public void setOprations(ArrayList<EvaluationCriteria> oprations) {
		this.oprations = oprations;
	}

	public ArrayList<EvaluationCriteria> getActors() {
		return actors;
	}

	public void setActors(ArrayList<EvaluationCriteria> actors) {
		this.actors = actors;
	}

	public ArrayList<EvaluationCriteria> getUsecases() {
		return usecases;
	}

	public void setUsecases(ArrayList<EvaluationCriteria> usecases) {
		this.usecases = usecases;
	}

	public String getDisplayPanel() {
		return displayPanel;
	}

	public void setDisplayPanel(String displayPanel) {
		this.displayPanel = displayPanel;
	}

	public String getResultPanel() {
		return resultPanel;
	}

	public void setResultPanel(String resultPanel) {
		this.resultPanel = resultPanel;
	}

	public String getLoadModel() {
		return loadModel;
	}

	public void setLoadModel(String loadModel) {
		this.loadModel = loadModel;
	}

	public double getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(double totalMarks) {
		this.totalMarks = totalMarks;
	}

	public double getStudentMarks() {
		return studentMarks;
	}

	public void setStudentMarks(double studentMarks) {
		this.studentMarks = studentMarks;
	}

	public ArrayList<EvaluationResultError> getEvaluationResult() {
		return evaluationResult;
	}

	public void setEvaluationResult(
			ArrayList<EvaluationResultError> evaluationResult) {
		this.evaluationResult = evaluationResult;
	}

	public String getDiagramPanel() {
		return diagramPanel;
	}

	public void setDiagramPanel(String diagramPanel) {
		this.diagramPanel = diagramPanel;
	}

	public String getSolutionPanel() {
		return solutionPanel;
	}

	public void setSolutionPanel(String solutionPanel) {
		this.solutionPanel = solutionPanel;
	}

	public double getDiagramFileSize() {
		return diagramFileSize;
	}

	public void setDiagramFileSize(double diagramFileSize) {
		this.diagramFileSize = diagramFileSize;
	}

	public double getSolutionfileSize() {
		return solutionfileSize;
	}

	public void setSolutionfileSize(double solutionfileSize) {
		this.solutionfileSize = solutionfileSize;
	}
	
	
	public ArrayList<EvaluationResultError> setGetEvaluationResult(
			ArrayList<EvaluationResultError> evaluationResult) {
		return this.evaluationResult = evaluationResult;
	}

	public ArrayList<EvaluationCriteria> getActions() {
		return actions;
	}

	public void setActions(ArrayList<EvaluationCriteria> actions) {
		this.actions = actions;
	}

	public ArrayList<EvaluationCriteria> getDecisions() {
		return decisions;
	}

	public void setDecisions(ArrayList<EvaluationCriteria> decisions) {
		this.decisions = decisions;
	}

	public ArrayList<EvaluationCriteria> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<EvaluationCriteria> nodes) {
		this.nodes = nodes;
	}
	
	

}
