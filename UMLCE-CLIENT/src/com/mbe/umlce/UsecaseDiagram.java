package com.mbe.umlce;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.UD;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.service.CheckerPortType;
import com.mbe.umlce.service.EvaluatorPortType;
import com.mbe.umlce.service.IdentifierPortType;
import com.mbe.umlce.service.imp.Checker;
import com.mbe.umlce.service.imp.CheckerLocator;
import com.mbe.umlce.service.imp.Evaluator;
import com.mbe.umlce.service.imp.EvaluatorLocator;
import com.mbe.umlce.service.imp.Identifier;
import com.mbe.umlce.service.imp.IdentifierLocator;

public class UsecaseDiagram extends UMLCE implements Serializable {

	private static final long serialVersionUID = 4155078191420082552L;

	public UsecaseDiagram() {
	}

	@Override
	public Result identifyMistakes(ModelFile model) throws Exception {
		Identifier service = new IdentifierLocator();
		IdentifierPortType port = service.getIdentifierPort();
		String response = port.usecaseDiagramMistakes(IOUtils.toByteArray(model
				.getModel()));
		Result result = null;
		ObjectMapper mapper = new ObjectMapper();
		result = mapper.readValue(response, Result.class);
		return result;
	}

	@Override
	public Result checkPlagiarism(ModelFile model) throws Exception {
		Checker service = new CheckerLocator();
		CheckerPortType port = service.getCheckerPort();
		String response = port.checkUsecaseDiagramPlagiarism(IOUtils
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
		String response = port.evaluateUsecaseDiagram(
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
		String response = port.getUsecaseMistakeList();
		String[] model = null;

		ObjectMapper mapper = new ObjectMapper();
		model = mapper.readValue(response, String[].class);
		return model;
	}

	public UD getRefModelDetails(ModelFile model1) throws Exception {
		Evaluator service = new EvaluatorLocator();
		EvaluatorPortType port = service.getEvaluatorPort();
		String response = port.getUsecaseDiagramDetail(IOUtils
				.toByteArray(model1.getModel()));
		UD model = null;
		ObjectMapper mapper = new ObjectMapper();
		model = mapper.readValue(response, UD.class);
		return model;
	}

	@Override
	public Result codeMapping(ModelFile model, ModelFile code) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
