package com.mbe.umlce;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.mbe.umlce.comparator.ActivityDiagramComparator;
import com.mbe.umlce.dataobject.AD;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.evaluator.ActivityDiagramEvaluator;
import com.mbe.umlce.identifier.ActivityDiagramIdentifier;
import com.mbe.umlce.reader.ActivityDiagramReader;

public class ActivityDiagramController extends UMLCE implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7608937557276848455L;
	private ActivityDiagramEvaluator evaluator;
	private ActivityDiagramIdentifier identifier;
	private ActivityDiagramComparator comparator;
	private ActivityDiagramReader reader;

	public ActivityDiagramController() {
		evaluator = new ActivityDiagramEvaluator();
		identifier = new ActivityDiagramIdentifier();
		comparator = new ActivityDiagramComparator();
		reader = new ActivityDiagramReader();
	}

	@Override
	public Result evaluateModel(ModelFile solutionModel,
			ModelFile studentModel, ArrayList<EvaluationCriteria> markers,
			double totalMarks) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = studentModel.getModel().read(buffer)) > -1) {
			baos.write(buffer, 0, len);
		}
		baos.flush();
		studentModel.setModel(new ByteArrayInputStream(baos.toByteArray()));
		InputStream studentModelDup = new ByteArrayInputStream(
				baos.toByteArray());
		Result mistakes = identifyMistakes(new ModelFile(studentModelDup));
		return evaluator.evaluateModel(solutionModel, studentModel, mistakes,
				markers, totalMarks);
	}

	@Override
	public Result identifyMistakes(ModelFile model) throws Exception {
		return identifier.identifyMistakes(model);
	}

	@Override
	public Result checkPlagiarism(ModelFile model) throws Exception {
		return comparator.checkPlagiarism(model);
	}

	@Override
	public String[] getMistakesList() {
		return evaluator.getMistakesList();
	}

	public AD getRefModelDetails(ModelFile modelFile) throws Exception {
		return reader.getModelDetails(modelFile);
	}

	@Override
	public Result codeMapping(ModelFile model, ModelFile code) throws Exception {
		return null;
	}

}
