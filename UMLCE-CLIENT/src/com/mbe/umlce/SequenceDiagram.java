package com.mbe.umlce;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.SSD;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.service.CheckerPortType;
import com.mbe.umlce.service.EvaluatorPortType;
import com.mbe.umlce.service.IdentifierPortType;
import com.mbe.umlce.service.MaperPortType;
import com.mbe.umlce.service.imp.Checker;
import com.mbe.umlce.service.imp.CheckerLocator;
import com.mbe.umlce.service.imp.Evaluator;
import com.mbe.umlce.service.imp.EvaluatorLocator;
import com.mbe.umlce.service.imp.Identifier;
import com.mbe.umlce.service.imp.IdentifierLocator;
import com.mbe.umlce.service.imp.Maper;
import com.mbe.umlce.service.imp.MaperLocator;

public class SequenceDiagram extends UMLCE implements Serializable {
	private static final long serialVersionUID = 927425714522019034L;

	public SequenceDiagram() {
	}

	@Override
	public Result identifyMistakes(ModelFile model) throws Exception {
		Identifier service = new IdentifierLocator();
		IdentifierPortType port = service.getIdentifierPort();
		String response = port.sequenceDiagramMistakes(IOUtils
				.toByteArray(model.getModel()));
		Result result = null;
		ObjectMapper mapper = new ObjectMapper();
		result = mapper.readValue(response, Result.class);
		return result;
	}

	@Override
	public Result checkPlagiarism(ModelFile model) throws Exception {
		Checker service = new CheckerLocator();
		CheckerPortType port = service.getCheckerPort();
		String response = port.checkSequenceDiagramPlagiarism(IOUtils
				.toByteArray(model.getModel()));
		Result result = null;
		ObjectMapper mapper = new ObjectMapper();
		result = mapper.readValue(response, Result.class);
		return result;
	}

	@Override
	public Result evaluateModel(ModelFile solution, ModelFile studentModel,
			ArrayList<EvaluationCriteria> markers, double totalMarks)
			throws Exception {
		Evaluator service = new EvaluatorLocator();
		EvaluatorPortType port = service.getEvaluatorPort();
		String response = port.evaluateSequenceDiagram(
				IOUtils.toByteArray(solution.getModel()),
				IOUtils.toByteArray(studentModel.getModel()),
				markers.toArray(new EvaluationCriteria[markers.size()]),
				totalMarks);
		Result result = null;
		ObjectMapper mapper = new ObjectMapper();
		result = mapper.readValue(response, Result.class);
		return result;
	}

	@Override
	public String[] getMistakesList() throws Exception {
		Evaluator service = new EvaluatorLocator();
		EvaluatorPortType port = service.getEvaluatorPort();
		String response = port.getSequenceDiagramMistakeList();
		String[] model = null;

		ObjectMapper mapper = new ObjectMapper();
		model = mapper.readValue(response, String[].class);
		return model;
	}

	public SSD getRefModelDetails(ModelFile model1) throws Exception {
		Evaluator service = new EvaluatorLocator();
		EvaluatorPortType port = service.getEvaluatorPort();
		String response = port.getSequenceDiagramDetail(IOUtils
				.toByteArray(model1.getModel()));
		SSD model = null;
		ObjectMapper mapper = new ObjectMapper();
		model = mapper.readValue(response, SSD.class);
		return model;
	}

	@Override
	public Result codeMapping(ModelFile model, ModelFile code) throws Exception {
		Maper maper = new MaperLocator();
		MaperPortType port = maper.getMaperPort();
		String response = port.mapSequenceToCode(
				IOUtils.toByteArray(model.getModel()),
				IOUtils.toByteArray(code.getModel()));
		Result result = null;
		ObjectMapper mapper = new ObjectMapper();
		result = mapper.readValue(response, Result.class);
		return result;

	}

}
