package com.mbe.umlce;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.mbe.umlce.comparator.UsecaseDiagramComparator;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.UD;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.evaluator.UsecaseDiagramEvaluator;
import com.mbe.umlce.identifier.UsecaseDiagramIdentifier;
import com.mbe.umlce.reader.UsecaseDiagramReader;

public class UsecaseDiagramController extends UMLCE implements Serializable {

	private static final long serialVersionUID = 4155078191420082552L;
	private UsecaseDiagramEvaluator evaluator;
	private UsecaseDiagramIdentifier identifier;
	private UsecaseDiagramComparator comparator;
	private UsecaseDiagramReader reader;

	public UsecaseDiagramController() {
		evaluator = new UsecaseDiagramEvaluator();
		identifier = new UsecaseDiagramIdentifier();
		comparator = new UsecaseDiagramComparator();
		reader = new UsecaseDiagramReader();
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
	public String[] getMistakesList() {
		return evaluator.getMistakesList();
	}

	public UD getRefModelDetails(ModelFile model) throws Exception {
		return reader.getRefModelDetails(model);
	}

	@Override
	public Result codeMapping(ModelFile model, ModelFile code) throws Exception {
		return null;
	}

}
