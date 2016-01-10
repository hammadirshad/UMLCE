package com.mbe.umlce;

import java.util.ArrayList;

import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.Result;

public abstract class UMLCE {
	public abstract Result evaluateModel(ModelFile solutionModel,
			ModelFile studentModel, ArrayList<EvaluationCriteria> markers,
			double totalMarks) throws Exception;

	public abstract Result identifyMistakes(ModelFile model) throws Exception;

	public abstract Result checkPlagiarism(ModelFile model) throws Exception;

	public abstract String[] getMistakesList() throws Exception;

	public abstract Result codeMapping(ModelFile model, ModelFile code)
			throws Exception;

}