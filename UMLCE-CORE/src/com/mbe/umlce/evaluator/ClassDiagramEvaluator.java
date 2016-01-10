package com.mbe.umlce.evaluator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.mbe.umlce.dataobject.CD;
import com.mbe.umlce.dataobject.Errors;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.EvaluationResult;
import com.mbe.umlce.dataobject.result.EvaluationResultError;
import com.mbe.umlce.dataobject.result.EvaluationResultErrorsDetail;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.reader.ClassDiagramReader;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class ClassDiagramEvaluator implements Serializable,Evaluator{
	private static final long serialVersionUID = 1L;

	public Result evaluateModel(ModelFile solution, ModelFile studentModel,
			Result mistakes, ArrayList<EvaluationCriteria> markers,
			double totalMarks) throws Exception {
		ClassDiagramReader reader = new ClassDiagramReader();
		ArrayList<CD> refModelDetails = reader.getRefModelDetails(solution);
		ArrayList<CD> stModelDetails = reader.getRefModelDetails(studentModel);
		String wordNetpath = this.getClass().getClassLoader().getResource("")
				.getPath()
				+ "/WordNetDic";
		// set the path of wordnet database dictionary.
		System.setProperty("wordnet.database.dir", wordNetpath);
		EvaluationResult evaluationResult = new EvaluationResult();
		Double studentMarks = totalMarks;
		try {
			/*
			 * Compare student's class diagram with evaluator's reference
			 * solution.
			 */
			if (!refModelDetails.isEmpty() && !stModelDetails.isEmpty()) {

				for (CD stDetails : stModelDetails) {
					for (CD refDetails : refModelDetails) {
						boolean variationFound = false;
						String stClass = stDetails.getClassName().toLowerCase();
						String refClass = refDetails.getClassName()
								.toLowerCase();
						if (!stClass.equals(refClass)) {

							WordNetDatabase database = WordNetDatabase
									.getFileInstance();
							Synset[] synsets = database.getSynsets(refClass
									.toLowerCase());
							for (int k = 0; k < synsets.length; k++) {

								String[] wordForms = synsets[k].getWordForms();
								for (int j = 0; j < wordForms.length; j++) {

									if (wordForms[j].toLowerCase().equals(
											stClass.toLowerCase())) {

										variationFound = true;
										break;
									}
								}
							}
							if (!variationFound) {

								for (EvaluationCriteria marker : markers) {
									if (marker.getType().equals("Class")
											&& marker.getElementName().equals(
													refClass)
											&& marker.isEssential() == true) {
										studentMarks = deductMarks(
												studentMarks,
												marker.isEssential(),
												marker.getMarks());
									}
								}
								break;
							}
						}
						if (!variationFound) {
							for (EvaluationCriteria marker : markers) {
								if (marker.getType().equals("Class")
										&& marker.getElementName().equals(
												refClass)
										&& marker.isEssential() == true) {
									studentMarks = deductMarks(studentMarks,
											marker.isEssential(),
											marker.getMarks());
								}
							}
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
		String[] mistakesData = { "Class name is not noun",
				"Class name contains colon(:)", "Class having no relationship",
				"Class having no attribute", "Attribute name is not noun",
				"Attribute visibility is public", "Static attribute in class",
				"Missing attribute datatype in class",
				"Class having no association name",
				"Class having no opertaion " };

		return mistakesData;
	}

}
