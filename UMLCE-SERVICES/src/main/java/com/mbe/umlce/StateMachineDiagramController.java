package com.mbe.umlce;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.mbe.umlce.comparator.StateMachineComparator;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.SM;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.evaluator.StateMachineEvaluator;
import com.mbe.umlce.identifier.StateMachineIdentifier;
import com.mbe.umlce.reader.StateMachineReader;

public class StateMachineDiagramController extends UMLCE implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StateMachineIdentifier identifier ;
	private StateMachineEvaluator evaluator ;
	private StateMachineComparator comparator;
	private StateMachineReader reader;
	
	
	public StateMachineDiagramController() {
		super();
		identifier = new StateMachineIdentifier();
		evaluator = new StateMachineEvaluator();
		comparator = new StateMachineComparator();
		reader=new StateMachineReader();
	}



	public StateMachineDiagramController(StateMachineIdentifier identifier) {
		super();
		this.identifier = identifier;
	}



	@Override
	public Result identifyMistakes(ModelFile model) throws Exception {
		return identifier.identifyMistakes(model);
		
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
	public Result checkPlagiarism(ModelFile model) throws Exception {
		return comparator.checkPlagiarism(model);
	}



	@Override
	public String[] getMistakesList() {
		return evaluator.getMistakesList();
	}



	@Override
	public Result codeMapping(ModelFile model, ModelFile code) throws Exception {
		return null;
	}



	public ArrayList<SM> getRefModelDetails(ModelFile modelFile) throws Exception {
		
		return reader.getRefModelDetails(modelFile);
	}

}
