package com.mbe.umlce.evaluator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.mbe.umlce.dataobject.Errors;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.SM;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.EvaluationResult;
import com.mbe.umlce.dataobject.result.EvaluationResultError;
import com.mbe.umlce.dataobject.result.EvaluationResultErrorsDetail;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.reader.StateMachineReader;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class StateMachineEvaluator implements Serializable, Evaluator {
	private static final long serialVersionUID = 1L;

	public Result evaluateModel(ModelFile solution, ModelFile studentModel,
			Result mistakes, ArrayList<EvaluationCriteria> markers,
			double totalMarks) throws Exception {

		Double studentMarks = totalMarks;
		EvaluationResult evaluationResult = new EvaluationResult();
		String wordNetpath = this.getClass().getClassLoader().getResource("")
				.getPath()
				+ "/WordNetDic";
		System.setProperty("wordnet.database.dir", wordNetpath);
		HashMap<String, ArrayList<String>> mistakesResult = new HashMap<String, ArrayList<String>>();
		// StateMachineReader reader = new StateMachineReader();
		ArrayList<SM> studentDetails = new ArrayList<>();
		StateMachineReader reader=new StateMachineReader();
		studentDetails = reader.getRefModelDetails(studentModel);
		for (EvaluationCriteria marker : markers) {
			if (marker.getType().equals("State")) {
				/* Check the names of states with reference Model */
				String[] token = marker.getElementName().split(" ");
				String solName = token[0];
				for (SM smDetail : studentDetails) {
					String[] tokens = smDetail.getName().split(" ");
					String stdName = tokens[0];
					if (!stdName.toLowerCase().contains(solName.toLowerCase())
							|| !stdName.toLowerCase().equals(
									solName.toLowerCase())) {
						System.out.println("std : " + stdName);
						boolean variationFound = false;
						WordNetDatabase database = WordNetDatabase
								.getFileInstance();
						Synset[] synsets = database.getSynsets(stdName
								.toLowerCase());
						for (int k = 0; k < synsets.length; k++) {
							String[] wordForms = synsets[k].getWordForms();
							for (int j = 0; j < wordForms.length; j++) {
								if (wordForms[j].toLowerCase().equals(
										solName.toLowerCase())) {
									variationFound = true;
									System.out.println("sol : " + solName);
									break;
								}
							}
						}
						if (!variationFound) {
							if (marker.isEssential() == true) {
								studentMarks = deductMarks(studentMarks,
										marker.isEssential(), marker.getMarks());
							}
							break;
						}

					} else {
						studentDetails.remove(smDetail);
						break;
					}
				}
			}

			if (marker.getType().equals("Mistake")) {
				for (Errors mistake : mistakes.getErrors()) {

					if (mistake.getErrorDiscrption().contains(
							marker.getElementName())) {
						if (marker.isEssential() == true) {
							System.out.println("Yes  "
									+ marker.getElementName());
							studentMarks = deductMarks(studentMarks,
									marker.isEssential(), marker.getMarks());
							String error = mistake.getErrorName();
							if (mistakesResult.containsKey(error)) {
								mistakesResult.get(error).add(
										mistake.getElementName() + "%"
												+ mistake.getErrorDiscrption());
							} else {
								ArrayList<String> list = new ArrayList<>();
								list.add(mistake.getElementName() + "%"
										+ mistake.getErrorDiscrption());
								mistakesResult.put(error, list);
							}

						}
					}
				}

			}
		}
		Set<String> keySet = mistakesResult.keySet();
		for (String error : keySet) {

			int count = 0;
			EvaluationResultError resultError = new EvaluationResultError();
			for (String string : mistakesResult.get(error)) {
				String[] token = string.split("%");
				EvaluationResultErrorsDetail detail = new EvaluationResultErrorsDetail();
				detail.setElementName(token[0]);
				detail.setErrorDiscption(token[1]);
				resultError.addDetail(detail);
				count++;
			}

			resultError.setErrorName(error);
			resultError.setErrorCount(count);
			evaluationResult.addErrors(resultError);
		}

		evaluationResult.setTotalMarks(totalMarks);
		evaluationResult.setStudentMarks(studentMarks);

		Result result = new Result();
		result.setEvaluationResult(evaluationResult);
		System.out
				.println("r" + result.getEvaluationResult().getStudentMarks());
		// System.out.println("r"+result.getEvaluationResult().getErrors());
		return result;
	}

	public String[] getMistakesList() {
		String[] mistakesData = { "State name not starting with noun",
				"Guard Names Same at two Transitions ",
				"Two Guard Conditions Same on one State ",
				"No guard Condition ", "No Effect on Transition ",
				"No trigger at transition ", "No Connection with other states " };
		return mistakesData;
	}

	private double deductMarks(double studentMarks, boolean b, Double deduction) {
		if (b == true) {
			if (studentMarks > 0 && (studentMarks - deduction) >= 0) {
				if (deduction < 0)
					studentMarks += deduction;
				else
					studentMarks -= deduction;
			} else
				studentMarks = 0.0;
		}
		return studentMarks;
	}

}
