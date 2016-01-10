package com.mbe.umlce;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.mbe.umlce.comparator.ClassDiagramComparator;
import com.mbe.umlce.dataobject.CD;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.evaluator.ClassDiagramEvaluator;
import com.mbe.umlce.identifier.ClassDiagramIdentifier;
import com.mbe.umlce.maper.ClassDiagramMaper;
import com.mbe.umlce.reader.ClassDiagramReader;

public class ClassDiagramController extends UMLCE implements Serializable {

	private static final long serialVersionUID = -6166260740614000704L;
	private ClassDiagramEvaluator evaluator;
	private ClassDiagramIdentifier identifier;
	private ClassDiagramComparator comparator;
	private ClassDiagramReader reader;
	private ClassDiagramMaper maper;

	public ClassDiagramController() {
		evaluator = new ClassDiagramEvaluator();
		identifier = new ClassDiagramIdentifier();
		comparator = new ClassDiagramComparator();
		reader = new ClassDiagramReader();
		maper = new ClassDiagramMaper();
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

	@Override
	public Result codeMapping(ModelFile model, ModelFile code)
			throws Exception {
		return maper.mapClassToCode(model, code);
	}

	public ArrayList<CD> getRefModelDetails(ModelFile modelFile)
			throws Exception {
		return reader.getRefModelDetails(modelFile);
	}

}
