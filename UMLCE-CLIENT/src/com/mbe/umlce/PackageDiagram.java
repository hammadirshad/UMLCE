package com.mbe.umlce;

import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.service.MaperPortType;
import com.mbe.umlce.service.imp.Maper;
import com.mbe.umlce.service.imp.MaperLocator;

public class PackageDiagram extends UMLCE {

	@Override
	public Result evaluateModel(ModelFile solutionModel,
			ModelFile studentModel, ArrayList<EvaluationCriteria> markers,
			double totalMarks) throws Exception {
		return null;
	}

	@Override
	public Result identifyMistakes(ModelFile model) throws Exception {
		return null;
	}

	@Override
	public Result checkPlagiarism(ModelFile model) throws Exception {
		return null;
	}

	@Override
	public String[] getMistakesList() {
		return null;
	}

	@Override
	public Result codeMapping(ModelFile model, ModelFile code) throws Exception {
		Maper maper = new MaperLocator();
		MaperPortType port = maper.getMaperPort();
		String response = port.mapPackageToCode(
				IOUtils.toByteArray(model.getModel()),
				IOUtils.toByteArray(code.getModel()));
		Result result = null;
		ObjectMapper mapper = new ObjectMapper();
		result = mapper.readValue(response, Result.class);
		return result;

	}

}
