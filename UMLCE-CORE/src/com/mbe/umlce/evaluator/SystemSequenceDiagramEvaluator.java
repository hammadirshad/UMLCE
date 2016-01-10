package com.mbe.umlce.evaluator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mbe.umlce.dataobject.Errors;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.SSD;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.EvaluationResult;
import com.mbe.umlce.dataobject.result.EvaluationResultError;
import com.mbe.umlce.dataobject.result.EvaluationResultErrorsDetail;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.reader.SystemSequenceDiagramReader;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class SystemSequenceDiagramEvaluator implements Serializable, Evaluator {
	private static final long serialVersionUID = 1L;
	public Result evaluateModel(ModelFile solution, ModelFile studentModel,
			Result mistakes, ArrayList<EvaluationCriteria> markers,
			double totalMarks) throws Exception {
		String wordNetpath = this.getClass().getClassLoader().getResource("")
				.getPath()
				+ "/WordNetDic";
		System.setProperty("wordnet.database.dir", wordNetpath);
		SystemSequenceDiagramReader reader = new SystemSequenceDiagramReader();
		SSD solutionDetails = reader.getRefModelDetails(solution);
		EvaluationResult evaluationResult = new EvaluationResult();
		Double studentMarks = totalMarks;
		try {
			/*
			 * Evaluate student's model by comparing with reference model and
			 * mistakes that are essential.
			 */
			for (EvaluationCriteria marker : markers) {
				if (marker.getType().equals("LifeLine")) {

					for (String lifeline : solutionDetails.getLifelines()) {
						/*
						 * If lifeline/class name in student's model does not
						 * match exactly with the reference model's
						 * lifelines/class name, then look for its synonyms.
						 */
						if (!lifeline.toLowerCase().contains(
								marker.getElementName().toLowerCase())
								|| !lifeline.toLowerCase().equals(
										marker.getElementName().toLowerCase())) {
							boolean variationFound = false;
							WordNetDatabase database = WordNetDatabase
									.getFileInstance();
							Synset[] synsets = database.getSynsets(lifeline
									.toLowerCase());
							for (int k = 0; k < synsets.length; k++) {
								String[] wordForms = synsets[k].getWordForms();
								for (int j = 0; j < wordForms.length; j++) {
									if (wordForms[j].toLowerCase().equals(
											marker.getElementName()
													.toLowerCase())) {
										variationFound = true;
										break;
									}
								}
							}
							if (!variationFound) {
								studentMarks = deductMarks(studentMarks,
										marker.isEssential(), marker.getMarks());
								break;
							}
						} else {
							solutionDetails.getLifelines().remove(lifeline);
							break;
						}
					}
				}
			}
			/*
			 * Comparing operations and their sequence of student model with
			 * evaluators' model.
			 */
			for (EvaluationCriteria marker : markers) {
				if (marker.getType().equals("Operation")) {
					for (String operation : solutionDetails.getOperations()) {
						boolean found = false;
						// looking for exact match
						if (!found
								&& !operation.toLowerCase().equals(
										marker.getElementName().toLowerCase())) {
							found = true;
						}
						/*
						 * Find occurrence of reference operation in student's
						 * operation
						 */
						else if (!found
								&& marker.getElementName().contains("(")) {
							String[] values = marker.getElementName().split(
									"\\(");
							Pattern pattern = Pattern.compile(values[0]);
							Matcher matcher = pattern.matcher(operation);
							while (matcher.find()) {
								found = true;
								break;
							}
						}
						if (!found)
							studentMarks = deductMarks(studentMarks,
									marker.isEssential(), marker.getMarks());
						else {
							solutionDetails.getOperations().remove(operation);
							break;
						}
					}
				}
			}
			/*
			 * Evaluate student's model by checking syntax as well as semantic
			 * mistakes and deduct marks of those mistakes that are
			 * essential(Specified by evaluator).
			 */
			HashMap<String, ArrayList<String>> mistakesResult = new HashMap<String, ArrayList<String>>();

			for (EvaluationCriteria marker : markers) {

				if (marker.getType().equals("Mistake")) {
					for (Errors mistake : mistakes.getErrors()) {

						if (mistake.getErrorDiscrption().contains(
								marker.getElementName())) {
							if (marker.isEssential() == true) {
								studentMarks = deductMarks(studentMarks,
										marker.isEssential(), marker.getMarks());

								String error = mistake.getErrorName();

								// for errors
								if (mistake.getType().equals("Error")) {

									if (mistakesResult.containsKey(error)) {
										mistakesResult
												.get(error)
												.add(mistake.getElementName()
														+ "%"
														+ mistake
																.getErrorDiscrption());
									} else {
										ArrayList<String> list = new ArrayList<>();
										list.add(mistake.getElementName() + "%"
												+ mistake.getErrorDiscrption());
										mistakesResult.put(error, list);
									}
								}

								// for warnings
								if (mistake.getType().equals("Warning")) {

									if (mistakesResult.containsKey(error)) {
										mistakesResult
												.get(error)
												.add(mistake.getElementName()
														+ "%"
														+ mistake
																.getErrorDiscrption());
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

		} catch (Exception e) {
			e.printStackTrace();
		}
		Result result = new Result();
		result.setEvaluationResult(evaluationResult);
		return result;
	}

	/*
	 * This function deducts marks of student based on criteria
	 */
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

	public String[] getMistakesList() {
		String[] mistakesData = { "Should not like operation",
				"Operation is like return message",
				"Sequence message cannot be separated by space",
				"Sequence message with Empyy name",
				"Multiple Sequence messages with the same name." };

		return mistakesData;
	}
}
