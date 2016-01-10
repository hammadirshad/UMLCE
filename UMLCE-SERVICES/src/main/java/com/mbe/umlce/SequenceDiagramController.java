package com.mbe.umlce;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.mbe.umlce.comparator.SequenceDiagramComparator;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.SSD;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.evaluator.SequenceDiagramEvaluator;
import com.mbe.umlce.identifier.SequenceDiagramIdentifier;
import com.mbe.umlce.maper.SequenceDiagramMaper;
import com.mbe.umlce.reader.SystemSequenceDiagramReader;

public class SequenceDiagramController extends UMLCE implements Serializable {
	private static final long serialVersionUID = 927425714522019034L;
	private SequenceDiagramEvaluator evaluator;
	private SequenceDiagramIdentifier identifier;
	private SequenceDiagramComparator comparator;
	private SystemSequenceDiagramReader reader;
	private SequenceDiagramMaper maper;

	public SequenceDiagramController() {
		evaluator = new SequenceDiagramEvaluator();
		identifier = new SequenceDiagramIdentifier();
		comparator = new SequenceDiagramComparator();
		reader = new SystemSequenceDiagramReader();
		maper=new SequenceDiagramMaper();
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
		return maper.mapSequenceToCode(model, code);
	}

}
