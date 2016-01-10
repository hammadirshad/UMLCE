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
import com.mbe.umlce.service.Identifier;

@WebService(serviceName = "Identifier", portName = "IdentifierPort", endpointInterface = "com.fyp.umce.service.Identifier", targetNamespace = "http://imp.service.umce.fyp.com")
@MTOM
public class IdentifierService implements Identifier, Serializable {

	private static final long serialVersionUID = 1L;
	private ClassDiagramController classDiagram = new ClassDiagramController();
	private UsecaseDiagramController usecaseDiagram = new UsecaseDiagramController();
	private DomainModelController domainModel = new DomainModelController();
	private SequenceDiagramController sequenceDiagram = new SequenceDiagramController();
	private SystemSequenceDiagramController systemSequenceDiagram = new SystemSequenceDiagramController();
	private ActivityDiagramController activityDiagram = new ActivityDiagramController();
	private StateMachineDiagramController stateMachineDiagram = new StateMachineDiagramController();

	@Override
	public String classDiagramMistakes(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		Result mistakes = classDiagram.identifyMistakes(new ModelFile(stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(mistakes);
	}

	@Override
	public String UsecaseDiagramMistakes(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		Result mistakes = usecaseDiagram
				.identifyMistakes(new ModelFile(stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(mistakes);
	}

	@Override
	public String SequenceDiagramMistakes(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		Result mistakes = sequenceDiagram
				.identifyMistakes(new ModelFile(stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(mistakes);
	}

	@Override
	public String DomainModelMistakes(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		Result mistakes = domainModel.identifyMistakes(new ModelFile(stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(mistakes);
	}

	@Override
	public String SystemSequenceDiagramMistakes(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		Result mistakes = systemSequenceDiagram.identifyMistakes(new ModelFile(
				stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(mistakes);
	}

	@Override
	public String ActivityDiagramMistakes(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		Result mistakes = activityDiagram
				.identifyMistakes(new ModelFile(stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(mistakes);
	}

	@Override
	public String StateMachineMistakes(byte[] file) throws Exception {
		InputStream stream = new ByteArrayInputStream(file);
		Result mistakes = stateMachineDiagram
				.identifyMistakes(new ModelFile(stream));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(mistakes);
	}

}