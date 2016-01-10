package com.mbe.umlce.identifier;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.Vertex;

import com.mbe.umlce.dataobject.Errors;
import com.mbe.umlce.dataobject.Keywords;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.SM;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.dataobjects.stateMachine.Guard;
import com.mbe.umlce.uml.UMLModelLoader;

public class StateMachineIdentifier implements Serializable, Identifier {
	private static final long serialVersionUID = 1L;
	ArrayList<Errors> mistakes = new ArrayList<Errors>();

	public Result identifyMistakes(ModelFile modelFile) throws Exception {

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
			if (element.eClass() == UMLPackage.Literals.CLASS) {
				Class c = (Class) element;

				EList<Behavior> ownedBehaviors = c.getOwnedBehaviors();
				for (Behavior beh : ownedBehaviors) {
					if (beh.eClass() == UMLPackage.Literals.STATE_MACHINE) {
						// }
						// if (element.eClass() ==
						// UMLPackage.Literals.STATE_MACHINE) {

						StateMachine stateMachine = (StateMachine) beh;

						// System.out.println("Parent  :  "+parentClassName);

						EList<Region> regions = stateMachine.getRegions();
						for (Region reg : regions) {
							// System.out.println("Region : " + reg.getLabel());
							EList<Vertex> vertices = reg.getSubvertices();

							for (Vertex vertex : vertices) {
								SM smDetails = new SM();
								if (vertex.eClass() == UMLPackage.Literals.STATE) {
									// System.out.println("Vertex : "+vertex.getName());

									smDetails.setName(vertex.getLabel());

									// //
									String[] tokens = vertex.getLabel().split(
											" ");
									String taggedName = tokens[0];
									if (!taggedName.contains("NN")
											&& !taggedName.contains("NNP")
											&& !taggedName.contains("NNS")
											&& !taggedName.contains("NNPS")) {
										mistakes.add(new Errors(Keywords.Error,
												"Invalid State name",
												"State Machine Diagram",
												taggedName,
												"State name not starting with noun"));
										System.out.println("Noun Mistake : "
												+ taggedName);
									}
									// //

									identifyVertices(vertex);
									identifyGET(vertex);
									identifyVertexWithNoConnection(vertex);
									// transition = readVertices(vertex,
									// smDetails);
									// smDetails.setTransitions(transition);
									// stateMachineDetails.add(smDetails);

								}
							}

						}

					}
				}

			}
		}
		Result result = new Result();
		result.setErrors(mistakes);
		return result;
	}

	public void identifyVertices(Vertex vertex) {
		State state = (State) vertex;

		EList<Transition> outgoingTransitions = state.getOutgoings();

		for (Transition trans : outgoingTransitions) {
			// String transName = trans.getLabel();
			/* Check Guard Condition Errors */
			EList<Constraint> ownedRules = trans.getOwnedRules();
			Guard guard = new Guard();
			for (Constraint Rule : ownedRules) {
				// Constraint Rule = ownedRules.get(0);
				ValueSpecification Specifications = Rule.getSpecification();

				guard.setName(Rule.getLabel());
				OpaqueExpression expr = (OpaqueExpression) Specifications;

				String condition = expr.getBodies().toString();
				guard.setBody(removeSquareBrackets(condition));
			}
			checkGuardsWithOthers(outgoingTransitions, trans, guard);

			/* Guard Check Ends */
		}
	}

	public void checkGuardsWithOthers(EList<Transition> outgoingTransitions,
			Transition trans, Guard guard) {
		for (Transition transition : outgoingTransitions) {
			if (!transition.equals(trans)) {
				EList<Constraint> ownedRules = trans.getOwnedRules();
				for (Constraint Rule : ownedRules) {

					ValueSpecification Specifications = Rule.getSpecification();
					if (Rule.getLabel() != null) {
						if (guard.getName().equals(Rule.getLabel())) {
							mistakes.add(new Errors(Keywords.Warning,
									"Guard Condition Names",
									"State Machine Diagram", trans.getLabel(),
									"Guard Names Same at two Transitions "
											+ trans.getSource().getLabel()));

							// System.out.println("Two guard Names Same : "+transition.getSource().getLabel()+" : "+trans.getSource().getLabel());
						}
						OpaqueExpression expr = (OpaqueExpression) Specifications;

						String condition = removeSquareBrackets(expr
								.getBodies().toString());
						if (guard.getBody().equals(condition)) {
							Errors err = new Errors(Keywords.Error,
									"Guard Condition", "State Machine Diagram",
									transition.getLabel() + " and "
											+ trans.getLabel(),
									"Two Guard Conditions Same on one State "
											+ trans.getSource().getLabel());
							mistakes.add(err);
							// System.out.println("Keywords.Error," +
							// "Guard Condition "+
							// " State Machine Diagram "+transition.getLabel()+" and "+trans.getLabel()+" Two Guard Conditions Same on one State"+trans.getSource().getLabel());
						}
					}
				}
			}
		}
	}

	public static String removeSquareBrackets(String myString) {
		// print("remove braces : "+myString);
		if (myString.equals(""))
			return myString;
		return myString.substring(1, myString.length() - 1);
	}

	public void identifyGET(Vertex vertex) // GET: Guard, Effect, Trigger
	{
		State state = (State) vertex;

		EList<Transition> outgoingTransitions = state.getOutgoings();
		for (Transition transitin : outgoingTransitions) {
			// Check if there is guard Condition
			EList<Constraint> ownedRules = transitin.getOwnedRules();
			if (ownedRules.isEmpty()) {
				Errors err = new Errors(Keywords.Error, "Guard Condition",
						"State Machine Diagram", transitin.getLabel(),
						"No guard Condition ");
				mistakes.add(err);

				// System.out.println("Warning : No guard Condition at "+transitin.getLabel());
			}
			// Checks if there is effect
			if (transitin.getEffect() == null) {
				Errors err = new Errors(Keywords.Error, "Effect",
						"State Machine Diagram", transitin.getLabel(),
						"No Effect on Transition ");
				mistakes.add(err);

				// System.out.println("Warning : No Effect at "+transitin.getLabel());
			}

			// Checks if there is trigger
			if (transitin.getTriggers().isEmpty()) {
				Errors err = new Errors(Keywords.Error, "Guard Condition",
						"State Machine Diagram", transitin.getLabel(),
						"No trigger at transition ");
				mistakes.add(err);
				// System.out.println("Warning : No Trigger at "+transitin.getLabel());
			}
		}
	}

	public void identifyVertexWithNoConnection(Vertex vertex) {
		EList<Transition> inComings = vertex.getIncomings();
		EList<Transition> outGoings = vertex.getOutgoings();
		if (inComings.isEmpty() && outGoings.isEmpty()) {
			Errors err = new Errors(Keywords.Error, "Transition",
					"State Machine Diagram", vertex.getLabel(),
					"No Connection with other states ");
			mistakes.add(err);

			// System.out.println("State Have No connection with any other");
		}
	}
}
