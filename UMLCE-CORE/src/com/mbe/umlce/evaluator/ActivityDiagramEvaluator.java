package com.mbe.umlce.evaluator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.mbe.umlce.dataobject.AD;
import com.mbe.umlce.dataobject.Errors;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.EvaluationCriteria;
import com.mbe.umlce.dataobject.result.EvaluationResult;
import com.mbe.umlce.dataobject.result.EvaluationResultError;
import com.mbe.umlce.dataobject.result.EvaluationResultErrorsDetail;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.reader.ActivityDiagramReader;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class ActivityDiagramEvaluator implements Serializable,Evaluator{
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
		try {
			/*
			 * Evaluate student's model by checking syntax as well as semantic
			 * mistakes and deduct marks of those mistakes that are
			 * essential(Specified by evaluator).
			 */
			HashMap<String, ArrayList<String>> mistakesResult = new HashMap<String, ArrayList<String>>();
			ActivityDiagramReader reader = new ActivityDiagramReader();
			AD ad = reader.getModelDetails(studentModel);
			for (EvaluationCriteria marker : markers) {
				
				if (marker.getType().equals("Action")) {
					
					for (String action : ad.getOpaqueActions()) {
						System.out.println(marker.getElementName().toLowerCase()+","+ad.getOpaqueActions().size());
						/*
						 * If action name in student's model does not match
						 * exactly with the reference model's actor name, then
						 * look for its synonyms.
						 */
						//System.out.println(marker.getElementName().toLowerCase()+","+action.toLowerCase());
						if (!action.toLowerCase().contains(
								marker.getElementName().toLowerCase())
								|| !action.toLowerCase().equals(
										marker.getElementName().toLowerCase())) {
							boolean variationFound = false;
							WordNetDatabase database = WordNetDatabase
									.getFileInstance();
							Synset[] synsets = database.getSynsets(action
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
							ad.getOpaqueActions().remove(action);
							break;
						}
					}
				}
				
				if (marker.getType().equals("Decision")) {
					for (String decision : ad.getDecisions()) {
						/*
						 * If actor name in student's model does not match
						 * exactly with the reference model's actor name, then
						 * look for its synonyms.
						 */
						if(decision!=null)
							if(marker.getElementName()!=null)
						if (!decision.toLowerCase().contains(
								marker.getElementName().toLowerCase())
								|| !decision.toLowerCase().equals(
										marker.getElementName().toLowerCase())) {
							boolean variationFound = false;
							WordNetDatabase database = WordNetDatabase
									.getFileInstance();
							Synset[] synsets = database.getSynsets(decision
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
							ad.getDecisions().remove(decision);
							break;
						}
					}
				}
				
				
				if (marker.getType().equals("Node")) {
					for (String node : ad.getForks()) {
						/*
						 * If actor name in student's model does not match
						 * exactly with the reference model's actor name, then
						 * look for its synonyms.
						 */
						if(node!=null)
							if(marker.getElementName()!=null)
						if (!node.toLowerCase().contains(
								marker.getElementName().toLowerCase())
								|| !node.toLowerCase().equals(
										marker.getElementName().toLowerCase())) {
							boolean variationFound = false;
							WordNetDatabase database = WordNetDatabase
									.getFileInstance();
							Synset[] synsets = database.getSynsets(node
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
							ad.getForks().remove(node);
							break;
						}
					}
					
					for (String node : ad.getJoins()) {
						/*
						 * If actor name in student's model does not match
						 * exactly with the reference model's actor name, then
						 * look for its synonyms.
						 */
						if(node!=null)
							if(marker.getElementName()!=null)
						if (!node.toLowerCase().contains(
								marker.getElementName().toLowerCase())
								|| !node.toLowerCase().equals(
										marker.getElementName().toLowerCase())) {
							boolean variationFound = false;
							WordNetDatabase database = WordNetDatabase
									.getFileInstance();
							Synset[] synsets = database.getSynsets(node
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
							ad.getJoins().remove(node);
							break;
						}
					}
					
					for (String node : ad.getMerges()) {
						/*
						 * If actor name in student's model does not match
						 * exactly with the reference model's actor name, then
						 * look for its synonyms.
						 */
						if(node!=null)
							if(marker.getElementName()!=null)
						if (!node.toLowerCase().contains(
								marker.getElementName().toLowerCase())
								|| !node.toLowerCase().equals(
										marker.getElementName().toLowerCase())) {
							boolean variationFound = false;
							WordNetDatabase database = WordNetDatabase
									.getFileInstance();
							Synset[] synsets = database.getSynsets(node
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
							ad.getMerges().remove(node);
							break;
						}
					}
					
				}
				
				
			/*	if (marker.getType().equals("Action")) {
					for (String action : ad.getOpaqueActions()) {
						/*
						 * If actor name in student's model does not match
						 * exactly with the reference model's actor name, then
						 * look for its synonyms.
						 */
						/*if (!action.toLowerCase().contains(
								marker.getElementName().toLowerCase())
								|| !action.toLowerCase().equals(
										marker.getElementName().toLowerCase())) {
							boolean variationFound = false;
							WordNetDatabase database = WordNetDatabase
									.getFileInstance();
							Synset[] synsets = database.getSynsets(action
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
							ad.getOpaqueActions().remove(action);
							break;
						}
					}
				}*/
				
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
		String[] mistakesData = {
				"Incoming Edge does not belong to this Node",
				"Outgoing Edge does not belong to this Node",
				"Must be No Incoming Edge towards InitialNode",
				"Must be only one Outgoing Edge from InitialNode",
				"Must be no Outgoing Edge from ActivityFinalNode",
				"Must be only one Incoming Edge towards ActivityFinalNode",
				"Action name is NULL",
				"Action name is not contains verb",
				"No Incoming Edge towards Action",
				"Multipule Incoming Edges towards Action",
				"No Outgoing Edge from Action",
				"Multipule Outgoing Edges from Action",
				"Must be only one Incoming Edge towards DecisionNode",
				"Must be 2 or more Outgoing Edges from DecisionNode",
				"No Condition on Outgoing Edges from DecisionNode",
				"MergeNode name is NULL",
				"Must be 2 or more Incoming Edges towards MergeNode",
				"JoinNode name is NULL",
				"Must be 2 or more parallel Incoming Edges towards JoinNode",
				"Not Concurrent use ForkNode before JoinNode",
				"ForkNode name is NULL",
				"Must be only one Incoming Edge towards ForkNode",
				"Must be 2 or more [arallel Outgoing Edges from ForkNode",
				"Not Concurrent use JoinNode for Concurrent Procesing (never use MergeNode",
				"No Initial Node in activity diagram",
				"Multiple Initial Node in activity diagram",
				"No ActivityFinal Node in activity diagram",
				"No OpaqueAction Node in activity diagram" };

		return mistakesData;
	}
}
