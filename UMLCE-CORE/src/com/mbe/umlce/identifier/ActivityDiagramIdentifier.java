package com.mbe.umlce.identifier;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;

import com.mbe.umlce.dataobject.Errors;
import com.mbe.umlce.dataobject.Keywords;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.uml.UMLModelLoader;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class ActivityDiagramIdentifier implements Serializable,Identifier{
	private static final long serialVersionUID = 1L;
	private MaxentTagger tagger;

	public Result identifyMistakes(ModelFile modelFile)  throws Exception {

		ArrayList<Errors> mistakes = new ArrayList<Errors>();

		String taggerPath = this.getClass().getClassLoader().getResource("")
				.getPath()
				+ "/taggers/left3words-wsj-0-18.tagger";

		UMLModelLoader umlModel = new UMLModelLoader();
		EList<PackageableElement> packageableElements;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = modelFile.getModel().read(buffer)) > -1) {
			baos.write(buffer, 0, len);
		}
		baos.flush();
		modelFile.setModel(new BufferedInputStream(new ByteArrayInputStream(
				baos.toByteArray())));
		Model _model = umlModel.loadModel(new ModelFile(
				new BufferedInputStream(new ByteArrayInputStream(baos
						.toByteArray()))));

		if (_model == null) {
			Package _package = umlModel.loadPackage(modelFile);
			packageableElements = _package.getPackagedElements();
		} else {
			packageableElements = _model.getPackagedElements();
		}

		for (PackageableElement element : packageableElements) {

			if (element.eClass() == UMLPackage.Literals.ACTIVITY) {

				Activity activity = (Activity) element;
				EList<ActivityNode> nodes = activity.getNodes();
				int initial = 0;
				boolean fin = false;
				boolean action = false;

				for (ActivityNode an : nodes) {

					for (ActivityEdge ae : an.getIncomings()) {
						if (!ae.getTarget().equals(an)) {
							Errors err = new Errors(Keywords.Error,
									"Incoming Edge", "Activity Diagram", an
											.eClass().getName(),
									"Incoming Edge does not belong to this Node");
							mistakes.add(err);
						}
					}
					for (ActivityEdge ae : an.getOutgoings()) {
						if (!ae.getSource().equals(an)) {
							Errors err = new Errors(Keywords.Error,
									"Outgoing Edge", "Activity Diagram", an
											.eClass().getName(),
									"Outgoing Edge does not belong to this Node");
							mistakes.add(err);
						}
					}

					if (an.eClass() == UMLPackage.Literals.INITIAL_NODE) {
						if (initial == 0)
							initial = 1;
						else
							initial = initial + 1;
						if (an.getIncomings().size() > 0) {
							Errors err = new Errors(Keywords.Error,
									"Incoming Edges", "Activity Diagram",
									"InitialNode",
									"Must be No Incoming Edge towards InitialNode");
							mistakes.add(err);
						}
						if (an.getOutgoings().size() > 1
								|| an.getOutgoings().size() == 0) {
							Errors err = new Errors(Keywords.Error,
									"Outgoings", "Activity Diagram",
									"InitialNode",
									"Must be only one Outgoing Edge from InitialNode");
							mistakes.add(err);
						}
					}

					else if (an.eClass() == UMLPackage.Literals.ACTIVITY_FINAL_NODE) {
						fin = true;
						if (an.getOutgoings().size() > 0) {
							Errors err = new Errors(Keywords.Error,
									"Outgoings", "Activity Diagram",
									"ActivityFinalNode",
									"Must be no Outgoing Edge from ActivityFinalNode");
							mistakes.add(err);
						}
						if (an.getIncomings().size() != 1) {
							Errors err = new Errors(Keywords.Error,
									"Incomings", "Activity Diagram",
									"ActivityFinalNode",
									"Must be only one Incoming Edge towards ActivityFinalNode");
							mistakes.add(err);
						}
					}

					else if (an.eClass() == UMLPackage.Literals.OPAQUE_ACTION) {
						tagger = new MaxentTagger(taggerPath);
						String taggedName = tagger.tagString(an.getName());
						if (action == false) {
							action = true;
						}
						if (an.getName() == null) {
							Errors err = new Errors(Keywords.Warning,
									"Invalid Name", "Activity Diagram",
									"OpaqueAction", "Action name is NULL");
							mistakes.add(err);
						}
						if (!taggedName.contains("VB")) {
							Errors err = new Errors(Keywords.Warning,
									"Invalid Action", "Activity Diagram",
									"OpaqueAction",
									"Action name is not contains verb '"
											+ an.getLabel() + "'");
							mistakes.add(err);
						}
						if (an.getIncomings().size() == 0) {
							Errors err = new Errors(Keywords.Error,
									"No Incoming", "Activity Diagram",
									"OpaqueAction",
									"No Incoming Edge towards Action '"
											+ an.getLabel() + "'");
							mistakes.add(err);
						} else if (an.getIncomings().size() > 1) {
							Errors err = new Errors(
									Keywords.Error,
									"Multipule Incomings",
									"Activity Diagram",
									"OpaqueAction",
									"Multipule Incoming Edges towards Action '"
											+ an.getLabel()
											+ "' (Use MergeNode or JoinNode to combine)");
							mistakes.add(err);
						}
						if (an.getOutgoings().size() == 0) {
							Errors err = new Errors(Keywords.Error,
									"No Outgoing", "Activity Diagram",
									"OpaqueAction",
									"No Outgoing Edge from Action '"
											+ an.getLabel() + "'");
							mistakes.add(err);
						} else if (an.getOutgoings().size() > 1) {
							Errors err = new Errors(
									Keywords.Error,
									"Multipule Outgoing",
									"Activity Diagram",
									"OpaqueAction",
									"Multipule Outgoing Edges from Action '"
											+ an.getLabel()
											+ "' (Use DecisionNode or ForkNode to split)");
							mistakes.add(err);
						}
					}

					else if (an.eClass() == UMLPackage.Literals.DECISION_NODE) {
						if (an.getName() == null) {
							Errors err = new Errors(Keywords.Warning,
									"Invalid Name", "Activity Diagram",
									"DecisionNode", "DecisionNode name is NULL");
							mistakes.add(err);
						}
						if (an.getIncomings().size() == 0
								|| an.getIncomings().size() > 1) {
							Errors err = new Errors(Keywords.Error,
									"Incomings", "Activity Diagram",
									"DecisionNode",
									"Must be only one Incoming Edge towards DecisionNode '"
											+ an.getLabel() + "'");
							mistakes.add(err);
						}
						if (an.getOutgoings().size() < 2) {
							Errors err = new Errors(Keywords.Error, "Outgoing",
									"Activity Diagram", "DecisionNode",
									"Must be 2 or more Outgoing Edges from DecisionNode '"
											+ an.getLabel() + "'");
							mistakes.add(err);
						}
						if (an.getOutgoings().size() > 0) {
							for (ActivityEdge e : an.getOutgoings()) {
								if (e.getName() == null) {
									Errors err = new Errors(Keywords.Error,
											"Outgoing", "Activity Diagram",
											"DecisionNode",
											"No Condition on Outgoing Edges from DecisionNode");
									mistakes.add(err);
								}
							}
						}
					}

					else if (an.eClass() == UMLPackage.Literals.MERGE_NODE) {
						if (an.getName() == null) {
							Errors err = new Errors(Keywords.Warning,
									"Invalid Name", "Activity Diagram",
									"MergeNode", "MergeNode name is NULL");
							mistakes.add(err);
						}
						if (an.getIncomings().size() < 2) {
							Errors err = new Errors(Keywords.Error,
									"Incomings", "Activity Diagram",
									"MergeNode",
									"Must be 2 or more Incoming Edges towards MergeNode '"
											+ an.getLabel() + "'");
							mistakes.add(err);
						}
						if (an.getOutgoings().size() == 0
								|| an.getOutgoings().size() > 1) {
							Errors err = new Errors(Keywords.Error, "Outgoing",
									"Activity Diagram", "MergeNode",
									"Must be only one Outgoing Edge from MergeNode '"
											+ an.getLabel() + "'");
							mistakes.add(err);
						}
					}

					else if (an.eClass() == UMLPackage.Literals.JOIN_NODE) {
						if (an.getName() == null) {
							Errors err = new Errors(Keywords.Warning,
									"Invalid Name", "Activity Diagram",
									"JoinNode", "JoinNode name is NULL");
							mistakes.add(err);
						}
						if (an.getIncomings().size() < 2) {
							Errors err = new Errors(Keywords.Warning,
									"Incomings", "Activity Diagram",
									"JoinNode",
									"Must be 2 or more parallel Incoming Edges towards JoinNode '"
											+ an.getLabel() + "'");
							mistakes.add(err);
						}
						if (an.getOutgoings().size() == 0
								|| an.getOutgoings().size() > 1) {
							Errors err = new Errors(Keywords.Error, "Outgoing",
									"Activity Diagram", "JoinNode",
									"Must be only one Outgoing Edge from JoinNode '"
											+ an.getLabel() + "'");
							mistakes.add(err);
						}

						boolean val = checkJoinConcurrentFlow(an);
						if (val == false) {
							Errors err = new Errors(
									Keywords.Error,
									"JoinNode Not Concurrent",
									"Activity Diagram",
									"JoinNode",
									"JoinNode '"
											+ an.getLabel()
											+ "' is Not Concurrent use ForkNode before JoinNode");
							mistakes.add(err);
						}
					}

					else if (an.eClass() == UMLPackage.Literals.FORK_NODE) {
						if (an.getName() == null) {
							Errors err = new Errors(Keywords.Warning,
									"Invalid Name", "Activity Diagram",
									"ForkNode", "ForkNode name is NULL");
							mistakes.add(err);
						}
						if (an.getIncomings().size() == 0
								|| an.getIncomings().size() > 1) {
							Errors err = new Errors(Keywords.Error,
									"Incomings", "Activity Diagram",
									"ForkNode",
									"Must be only one Incoming Edge towards ForkNode '"
											+ an.getLabel() + "'");
							mistakes.add(err);
						}
						if (an.getOutgoings().size() < 2) {
							Errors err = new Errors(Keywords.Warning,
									"Outgoing", "Activity Diagram", "ForkNode",
									"Must be 2 or more [arallel Outgoing Edges from ForkNode '"
											+ an.getLabel() + "'");
							mistakes.add(err);
						}
						boolean val = checkForkConcurrentFlow(an);
						if (val == false) {
							Errors err = new Errors(
									Keywords.Error,
									"ForkNode Not Concurrent",
									"Activity Diagram",
									"ForkNode",
									"ForkNode '"
											+ an.getLabel()
											+ "' is Not Concurrent use JoinNode for Concurrent Procesing (never use MergeNode)");
							mistakes.add(err);
						}
					}
				}
				if (initial == 0) {
					Errors err = new Errors(Keywords.Error, "No InitialNode",
							"Activity Diagram", "InitialNode",
							"No Initial Node in activity diagram '"
									+ element.getLabel() + "'");
					mistakes.add(err);
				} else if (initial > 1) {
					Errors err = new Errors(Keywords.Error,
							"Multiple InitialNode", "Activity Diagram",
							"InitialNode",
							"Multiple Initial Node in activity diagram '"
									+ element.getLabel() + "'");
					mistakes.add(err);
				}
				if (fin == false) {
					Errors err = new Errors(Keywords.Error, "No FinalNode",
							"Activity Diagram", "ActivityFinalNode",
							"No ActivityFinal Node in activity diagram '"
									+ element.getLabel() + "'");
					mistakes.add(err);
				}
				if (action == false) {
					Errors err = new Errors(Keywords.Error, "No Action",
							"Activity Diagram", "OpaqueAction",
							"No OpaqueAction Node in activity diagram '"
									+ element.getLabel() + "'");
					mistakes.add(err);
				}
			}

		}
		Result result = new Result();
		result.setErrors(mistakes);
		return result;
	}

	private boolean checkJoinConcurrentFlow(ActivityNode an) {
		if (an.getIncomings().size() > 1) {
			if (an.getIncomings().get(0).getSource().eClass() == UMLPackage.Literals.FORK_NODE) {
				return true;
			} else if (an.getIncomings().get(0).getSource().eClass() == UMLPackage.Literals.INITIAL_NODE) {
				return false;
			} else {
				return checkForkConcurrentFlow(an.getIncomings().get(0)
						.getSource());
			}
		} else {
			return true;
		}
	}

	private boolean checkForkConcurrentFlow(ActivityNode an) {
		if (an.getOutgoings().size() > 1) {
			if (an.getOutgoings().get(0).getTarget().eClass() == UMLPackage.Literals.JOIN_NODE) {
				return true;
			} else if (an.getOutgoings().get(0).getTarget().eClass() == UMLPackage.Literals.ACTIVITY_FINAL_NODE) {
				return false;
			} else {
				return checkForkConcurrentFlow(an.getOutgoings().get(0)
						.getTarget());
			}
		} else {
			return true;
		}
	}

}
