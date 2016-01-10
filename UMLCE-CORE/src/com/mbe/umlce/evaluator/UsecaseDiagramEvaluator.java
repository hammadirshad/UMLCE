package com.mbe.umlce.evaluator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.mbe.umlce.dataobject.Errors;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.UD;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.EvaluationResult;
import com.mbe.umlce.dataobject.result.EvaluationResultError;
import com.mbe.umlce.dataobject.result.EvaluationResultErrorsDetail;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.reader.UsecaseDiagramReader;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class UsecaseDiagramEvaluator implements Serializable, Evaluator {
	private static final long serialVersionUID = 1L;

	public Result evaluateModel(ModelFile solution, ModelFile studentModel,
			Result mistakes, ArrayList<EvaluationCriteria> markers,
			double totalMarks) throws Exception {
		String wordNetpath = this.getClass().getClassLoader().getResource("")
				.getPath()
				+ "/WordNetDic";
		System.setProperty("wordnet.database.dir", wordNetpath);
		UsecaseDiagramReader reader = new UsecaseDiagramReader();
		UD ud = reader.getRefModelDetails(studentModel);
		EvaluationResult evaluationResult = new EvaluationResult();
		Double studentMarks = totalMarks;
		try {
			/*
			 * Evaluate student's model by comparing with reference model and
			 * mistakes that are essential.
			 */
			for (EvaluationCriteria marker : markers) {
				if (marker.getType().equals("Actor")) {
					for (String actor : ud.getActors()) {
						/*
						 * If actor name in student's model does not match
						 * exactly with the reference model's actor name, then
						 * look for its synonyms.
						 */
						if (!actor.toLowerCase().contains(
								marker.getElementName().toLowerCase())
								|| !actor.toLowerCase().equals(
										marker.getElementName().toLowerCase())) {
							boolean variationFound = false;
							WordNetDatabase database = WordNetDatabase
									.getFileInstance();
							Synset[] synsets = database.getSynsets(actor
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
								if (marker.isEssential() == true) {
									studentMarks = deductMarks(studentMarks,
											marker.isEssential(),
											marker.getMarks());
								}
								break;
							}
						} else {
							ud.getActors().remove(actor);
							break;
						}
					}
				}
			}

			for (EvaluationCriteria marker : markers) {
				if (marker.getType().equals("Usecase")) {
					for (String usecase : ud.getUsecases()) {
						/*
						 * If use case in student's model does not match exactly
						 * with the reference model's use case, then look for
						 * its synonyms.
						 */
						if (!usecase.toLowerCase().contains(
								marker.getElementName().toLowerCase())
								|| !usecase.toLowerCase().equals(
										marker.getElementName().toLowerCase())) {
							boolean variationFound = false;
							String[] ucWords = null;
							if (marker.getElementName().split(" ") != null) {
								ucWords = marker.getElementName().split(" ");
							}
							WordNetDatabase database = WordNetDatabase
									.getFileInstance();
							Synset[] synsets;
							if (ucWords != null)
								synsets = database.getSynsets(ucWords[0]
										.toLowerCase());
							else
								synsets = database.getSynsets(marker
										.getElementName().toLowerCase());
							for (int k = 0; k < synsets.length; k++) {
								String[] wordForms = synsets[k].getWordForms();
								for (int j = 0; j < wordForms.length; j++) {
									if (usecase.toLowerCase().equals(
											wordForms[j].toLowerCase())) {
										variationFound = true;
										break;
									}
								}
							}
							if (!variationFound) {
								if (marker.isEssential() == true) {
									studentMarks = deductMarks(studentMarks,
											marker.isEssential(),
											marker.getMarks());
								}
								break;
							}
						} else {
							ud.getUsecases().remove(usecase);
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

			/*
			 * Store marks of each student in ArrayList
			 */

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
		String[] mistakesData = { "Missing System Boundary",
				"Usecase name starts with non-verb",
				"Usecase occurs multiple times",
				"Use case having <<extend>> relationship",
				"Usecase having more than one <<include>> relationships",
				"Usecase having no Association with Actor",
				"Actor having no Association with any Usecase" };
		// "Use case having <<extend>> relationship","Usecase having more than one <<include>> relationships",

		return mistakesData;
	}
}
