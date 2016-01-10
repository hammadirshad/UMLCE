package com.mbe.umlce.service.Imp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import org.codehaus.jackson.map.ObjectMapper;

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
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.reader.StateMachineReader;
import com.mbe.umlce.service.Evaluator;

@WebService(serviceName = "Evaluator", portName = "EvaluatorPort", endpointInterface = "com.fyp.umce.service.Evaluator", targetNamespace = "http://imp.service.umce.fyp.com")
@MTOM
public class EvaluatorService implements Evaluator,Serializable {

	private static final long serialVersionUID = 1L;
	private ClassDiagramController classDiagram = new ClassDiagramController();
	private UsecaseDiagramController usecaseDiagram = new UsecaseDiagramController();
	private ActivityDiagramController activityDiagram = new ActivityDiagramController();
	private DomainModelController domainModel = new DomainModelController();
	private SystemSequenceDiagramController systemSequenceDiagram = new SystemSequenceDiagramController();
	private SequenceDiagramController sequenceDiagram = new SequenceDiagramController();
	private StateMachineDiagramController stateMachineDiagram=new StateMachineDiagramController();
	
	@Override
	public String getClassDiagramMistakeList() throws Exception {
		String[] mistakesList = classDiagram.getMistakesList();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(mistakesList);
	}

	@Override
	public String getClassDiagramDetail(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		ArrayList<CD> cd = classDiagram
				.getRefModelDetails(new ModelFile(stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(cd);
	}

	@Override
	public String evaluateClassDiagram(byte[] solution, byte[] studentModel,
			ArrayList<EvaluationCriteria> markers, double totalMarks)
			throws Exception {
		Result result = classDiagram.evaluateModel(new ModelFile(
				new ByteArrayInputStream(solution)), new ModelFile(
				new ByteArrayInputStream(studentModel)), markers, totalMarks);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}

	@Override
	public String getDomainModelMistakeList() throws Exception {
		String[] mistakesList = domainModel.getMistakesList();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(mistakesList);
	}

	@Override
	public String getDomainModelDetail(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		ArrayList<CD> cd = domainModel
				.getRefModelDetails(new ModelFile(stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(cd);
	}

	@Override
	public String evaluateDomainModel(byte[] solution, byte[] studentModel,
			ArrayList<EvaluationCriteria> markers, double totalMarks)
			throws Exception {
		Result result = domainModel.evaluateModel(new ModelFile(
				new ByteArrayInputStream(solution)), new ModelFile(
				new ByteArrayInputStream(studentModel)), markers, totalMarks);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}

	@Override
	public String getUsecaseMistakeList() throws Exception {
		String[] mistakesList = usecaseDiagram.getMistakesList();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(mistakesList);
	}

	@Override
	public String getUsecaseDiagramDetail(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		UD ud = usecaseDiagram.getRefModelDetails(new ModelFile(stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(ud);
	}

	@Override
	public String evaluateUsecaseDiagram(byte[] solution, byte[] studentModel,
			ArrayList<EvaluationCriteria> markers, double totalMarks)
			throws Exception {
		Result result = usecaseDiagram.evaluateModel(new ModelFile(
				new ByteArrayInputStream(solution)), new ModelFile(
				new ByteArrayInputStream(studentModel)), markers, totalMarks);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}

	@Override 
	public String getSequenceDiagramMistakeList() throws Exception {
		String[] mistakesList = sequenceDiagram.getMistakesList();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(mistakesList);
	}

	@Override
	public String getSequenceDiagramDetail(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		SSD sd = sequenceDiagram.getRefModelDetails(new ModelFile(stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(sd);
	}

	@Override
	public String evaluateSequenceDiagram(byte[] solution, byte[] studentModel,
			ArrayList<EvaluationCriteria> markers, double totalMarks)
			throws Exception {
		Result result = sequenceDiagram.evaluateModel(new ModelFile(
				new ByteArrayInputStream(solution)), new ModelFile(
				new ByteArrayInputStream(studentModel)), markers, totalMarks);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}

	@Override
	public String getSystemSequenceDiagramMistakeList() throws Exception {
		String[] mistakesList = systemSequenceDiagram.getMistakesList();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(mistakesList);
	}

	@Override
	public String getSystemSequenceDiagramDetail(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		SSD sd = systemSequenceDiagram.getRefModelDetails(new ModelFile(stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(sd);
	}

	@Override
	public String evaluateSystemSequenceDiagram(byte[] solution,
			byte[] studentModel, ArrayList<EvaluationCriteria> markers,
			double totalMarks) throws Exception {
		Result result = systemSequenceDiagram.evaluateModel(new ModelFile(
				new ByteArrayInputStream(solution)), new ModelFile(
				new ByteArrayInputStream(studentModel)), markers, totalMarks);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}

	@Override
	public String getActivityDiagramMistakeList() throws Exception {
		String[] mistakesList = activityDiagram.getMistakesList();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(mistakesList);
	}

	@Override
	public String getActivityDiagramDetail(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		AD ad = activityDiagram.getRefModelDetails(new ModelFile(stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(ad);
	}

	@Override
	public String evaluateActivityDiagram(byte[] solution, byte[] studentModel,
			ArrayList<EvaluationCriteria> markers, double totalMarks)
			throws Exception {
		Result result = activityDiagram.evaluateModel(new ModelFile(
				new ByteArrayInputStream(solution)), new ModelFile(
				new ByteArrayInputStream(studentModel)), markers, totalMarks);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}

	@Override
	public String getStateMachineMistakeList() throws Exception {
		String[] mistakesList = stateMachineDiagram.getMistakesList();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(mistakesList);

	}

	@Override
	public String getStateMachineDetail(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		ArrayList<SM> sm =stateMachineDiagram.getRefModelDetails(new ModelFile(stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(sm);
	}

	@Override
	public String evaluateStateMachine(byte[] solution, byte[] studentModel,
			ArrayList<EvaluationCriteria> markers, double totalMarks)
			throws Exception {
		Result result = stateMachineDiagram.evaluateModel(new ModelFile(
				new ByteArrayInputStream(solution)), new ModelFile(
				new ByteArrayInputStream(studentModel)), markers, totalMarks);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}

}
