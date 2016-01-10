package com.mbe.umlce;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.mbe.umlce.comparator.DomainModelComparator;
import com.mbe.umlce.dataobject.CD;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.evaluator.DomainModelEvaluator;
import com.mbe.umlce.identifier.DomainModelIdentifier;
import com.mbe.umlce.reader.DomainModelReader;

public class DomainModelController extends UMLCE implements Serializable {

	private static final long serialVersionUID = 2577717352843419465L;
	private DomainModelEvaluator evaluator;
	private DomainModelIdentifier identifier;
	private DomainModelComparator comparator;
	private DomainModelReader reader;

	public DomainModelController() {
		evaluator = new DomainModelEvaluator();
		identifier = new DomainModelIdentifier();
		comparator = new DomainModelComparator();
		reader = new DomainModelReader();
	}

	@Override
	public Result evaluateModel(ModelFile solution, ModelFile studentModel,
			ArrayList<EvaluationCriteria> markers, double totalMarks)
			throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = studentModel.getModel().read(buffer)) > -1) {
			baos.write(buffer, 0, len);
		}
		baos.flush();
		studentModel.setModel(new ByteArrayInputStream(baos.toByteArray()));
		;
		InputStream studentModelDup = new ByteArrayInputStream(
				baos.toByteArray());
		Result mistakes = identifyMistakes(new ModelFile(studentModelDup));
		return evaluator.evaluateModel(solution, studentModel, mistakes,
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

	public ArrayList<CD> getRefModelDetails(ModelFile modelFile)
			throws Exception {

		return reader.getRefModelDetails(modelFile);
	}

	@Override
	public Result codeMapping(ModelFile model, ModelFile code) throws Exception {
		return null;
	}

}
