package com.mbe.umlce.service.Imp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;

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
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.service.Checker;

@WebService(serviceName = "Checker", portName = "CheckerPort", endpointInterface = "com.fyp.umce.service.Checker", targetNamespace = "http://imp.service.umce.fyp.com")
@MTOM
public class CheckerService implements Checker,Serializable {

	private static final long serialVersionUID = 1L;
	private ClassDiagramController classChecker = new ClassDiagramController();
	private UsecaseDiagramController usecaseChecker = new UsecaseDiagramController();
	private DomainModelController domainChecker = new DomainModelController();
	private SequenceDiagramController sequenceChecker = new SequenceDiagramController();
	private SystemSequenceDiagramController systemSequenceChecker = new SystemSequenceDiagramController();
	private ActivityDiagramController activityChecker = new ActivityDiagramController();
	private StateMachineDiagramController stateMachineDiagram=new StateMachineDiagramController();

	@Override
	public String checkClassDiagramPlagiarism(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		Result plagiarism = classChecker.checkPlagiarism(new ModelFile(stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(plagiarism);
	}

	@Override
	public String checkUsecaseDiagramPlagiarism(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		Result plagiarism = usecaseChecker
				.checkPlagiarism(new ModelFile(stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(plagiarism);
	}

	@Override
	public String checkSequenceDiagramPlagiarism(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		Result plagiarism = sequenceChecker.checkPlagiarism(new ModelFile(
				stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(plagiarism);
	}

	@Override
	public String checkSystemSequenceDiagramPlagiarism(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		Result plagiarism = systemSequenceChecker.checkPlagiarism(new ModelFile(
				stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(plagiarism);
	}

	@Override
	public String checkDomainModelPlagiarism(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		Result plagiarism = domainChecker
				.checkPlagiarism(new ModelFile(stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(plagiarism);
	}

	@Override
	public String checkActiityDiagramPlagiarism(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		Result plagiarism = activityChecker
				.checkPlagiarism(new ModelFile(stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(plagiarism);
	}

	@Override
	public String StateMachinelagiarism(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		Result plagiarism = stateMachineDiagram
				.checkPlagiarism(new ModelFile(stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(plagiarism);
	}

}
