package com.mbe.umlce.evaluator;

import java.util.ArrayList;

import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.Result;

public interface Evaluator {
	public Result evaluateModel(ModelFile solution, ModelFile studentModel,
			Result mistakes, ArrayList<EvaluationCriteria> markers,
			double totalMarks) throws Exception ;
}
