package com.mbe.umlce;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.mbe.umlce.comparator.SystemSequenceDiagramComparator;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.SSD;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.evaluator.SystemSequenceDiagramEvaluator;
import com.mbe.umlce.identifier.SystemSequenceDiagramIdentifier;
import com.mbe.umlce.reader.SystemSequenceDiagramReader;

public class SystemSequenceDiagramController extends UMLCE implements Serializable {
	private static final long serialVersionUID = 927425714522019034L;
	private SystemSequenceDiagramEvaluator evaluator;
	private SystemSequenceDiagramIdentifier identifier;
	private SystemSequenceDiagramComparator comparator;
	private SystemSequenceDiagramReader reader;

	public SystemSequenceDiagramController() {
		evaluator = new SystemSequenceDiagramEvaluator();
		identifier = new SystemSequenceDiagramIdentifier();
		comparator = new SystemSequenceDiagramComparator();
		reader = new SystemSequenceDiagramReader();
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

	public SSD getRefModelDetails(ModelFile modelFile) throws Exception {
		return reader.getRefModelDetails(modelFile);
	}

	@Override
	public Result codeMapping(ModelFile model, ModelFile code) throws Exception {
		return null;
	}

}
