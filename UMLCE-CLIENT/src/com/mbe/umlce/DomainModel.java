package com.mbe.umlce;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import com.mbe.umlce.dataobject.CD;
import com.mbe.umlce.dataobject.ModelFile;
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

public class DomainModel extends UMLCE implements Serializable {

	private static final long serialVersionUID = 2577717352843419465L;

	public DomainModel() {
	}

	@Override
	public Result evaluateModel(ModelFile solution, ModelFile studentModel,
			ArrayList<EvaluationCriteria> markers, double totalMarks)
			throws Exception {
		Evaluator service = new EvaluatorLocator();
		EvaluatorPortType port = service.getEvaluatorPort();
		String response = port.evaluateDomainModel(
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
	public Result identifyMistakes(ModelFile model) throws Exception {
		Identifier service = new IdentifierLocator();
		IdentifierPortType port = service.getIdentifierPort();
		String response = port.domainModelMistakes(IOUtils.toByteArray(model
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
		String response = port.checkDomainModelPlagiarism(IOUtils
				.toByteArray(model.getModel()));
		Result result = null;
		ObjectMapper mapper = new ObjectMapper();
		result = mapper.readValue(response, Result.class);
		return result;
	}

	@Override
	public String[] getMistakesList() throws Exception {
		Evaluator service = new EvaluatorLocator();
		EvaluatorPortType port = service.getEvaluatorPort();
		String response = port.getDomainModelMistakeList();
		String[] model = null;
		ObjectMapper mapper = new ObjectMapper();
		model = mapper.readValue(response, String[].class);
		return model;
	}

	@SuppressWarnings("deprecation")
	public ArrayList<CD> getRefModelDetails(ModelFile model1) throws Exception {
		Evaluator service = new EvaluatorLocator();
		EvaluatorPortType port = service.getEvaluatorPort();
		String response = port.getDomainModelDetail(IOUtils.toByteArray(model1
				.getModel()));
		ArrayList<CD> model = null;
		ObjectMapper mapper = new ObjectMapper();
		model = mapper.readValue(response,
				TypeFactory.collectionType(ArrayList.class, CD.class));
		return model;
	}

	@Override
	public Result codeMapping(ModelFile model, ModelFile code) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
