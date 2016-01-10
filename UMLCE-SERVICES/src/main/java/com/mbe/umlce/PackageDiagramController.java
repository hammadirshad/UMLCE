package com.mbe.umlce;

import java.util.ArrayList;

import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.maper.PackageDiagramMaper;

public class PackageDiagramController extends UMLCE {

	private PackageDiagramMaper maper = new PackageDiagramMaper();

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
		return maper.mapPackageToCode(model, code);
	}

}
