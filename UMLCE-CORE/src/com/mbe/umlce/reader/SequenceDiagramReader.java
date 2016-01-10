package com.mbe.umlce.reader;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Gate;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.impl.BehaviorExecutionSpecificationImpl;
import org.eclipse.uml2.uml.internal.impl.CollaborationImpl;
import org.eclipse.uml2.uml.internal.impl.InteractionImpl;
import org.eclipse.uml2.uml.internal.impl.MessageOccurrenceSpecificationImpl;
import org.eclipse.uml2.uml.internal.impl.OpaqueExpressionImpl;
import org.eclipse.uml2.uml.internal.impl.PrimitiveTypeImpl;

import com.mbe.umlce.dataobject.Keywords;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.SD;
import com.mbe.umlce.dataobject.classDiagram.ClassAttribute;
import com.mbe.umlce.dataobject.classDiagram.ClassMethod;
import com.mbe.umlce.dataobject.classDiagram.ClassStructure;
import com.mbe.umlce.dataobject.sequenceDiagram.SequenceAttribute;
import com.mbe.umlce.dataobject.sequenceDiagram.SequenceBehavior;
import com.mbe.umlce.dataobject.sequenceDiagram.SequenceCombinedFragment;
import com.mbe.umlce.dataobject.sequenceDiagram.SequenceGate;
import com.mbe.umlce.dataobject.sequenceDiagram.SequenceLifeline;
import com.mbe.umlce.dataobject.sequenceDiagram.SequenceMessage;
import com.mbe.umlce.uml.UMLModelLoader;

public class SequenceDiagramReader implements Serializable {
	private static final long serialVersionUID = 1L;

	public SD getRefModelDetails(ModelFile file) throws Exception {
		UMLModelLoader umlModel = new UMLModelLoader();
		EList<PackageableElement> packageableElements;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = file.getModel().read(buffer)) > -1) {
			baos.write(buffer, 0, len);
		}
		baos.flush();
		file.setModel(new BufferedInputStream(new ByteArrayInputStream(baos
				.toByteArray())));
		Model _model = umlModel.loadModel(new ModelFile(
				new BufferedInputStream(new ByteArrayInputStream(baos
						.toByteArray()))));

		if (_model == null) {
			Package _package = umlModel.loadPackage(file);
			packageableElements = _package.getPackagedElements();
		} else {
			packageableElements = _model.getPackagedElements();
		}

		SD sd = new SD();
		for (PackageableElement element : packageableElements) {

			if (element.eClass() == UMLPackage.Literals.CLASS) {
				Class umlClass = (Class) element;
				ClassStructure structure = new ClassStructure();
				structure.setClassName(umlClass.getName());
				structure.setClassMethods(readOperations(umlClass
						.getOperations()));
				structure.setClassAttributes(readAttributes(umlClass
						.getAllAttributes()));
				sd.getClasses().add(structure);
			}
			if (element.eClass() == UMLPackage.Literals.COLLABORATION) {
				CollaborationImpl collaborationImpl = (CollaborationImpl) element;

				for (Property property : collaborationImpl.getAttributes()) {
					SequenceAttribute attribute = new SequenceAttribute(
							property.getName(), property.getType().getName());
					sd.addAttribute(attribute);
				}

				if (collaborationImpl.getOwnedBehaviors() != null) {
					for (Element element2 : collaborationImpl
							.getOwnedBehaviors()) {
						if (element2.eClass() == UMLPackage.Literals.INTERACTION) {
							InteractionImpl interactionImpl = (InteractionImpl) element2;
							SD read = interactionReader(interactionImpl);
							sd.setLifelines(read.getLifelines());
							sd.setMessages(read.getMessages());
							sd.setGates(read.getGates());
							sd.setBehaviors(read.getBehaviors());
							sd.setFragments(read.getFragments());
						}
					}
				}
			}

			if (element.eClass() == UMLPackage.Literals.INTERACTION) {
				InteractionImpl interactionImpl = (InteractionImpl) element;
				SD read = interactionReader(interactionImpl);
				sd.setLifelines(read.getLifelines());
				sd.setMessages(read.getMessages());
				sd.setGates(read.getGates());
				sd.setBehaviors(read.getBehaviors());
				sd.setFragments(read.getFragments());
			}

		}

		return sd;
	}

	/**
	 * UML Interactions Reader read interaction messages, lifeLines,Formal
	 * gates,MessageOccurrence,CombinedFragment,BehaviorExecution
	 * 
	 * @param InteractionImplementation
	 * @return Class object that have list of messages, lifeLines,Formal
	 *         gates,MessageOccurrence,CombinedFragment,BehaviorExecution
	 */

	private SD interactionReader(InteractionImpl interactionImpl) {
		SD sd = new SD();

		// reading behaviors
		for (InteractionFragment interactionFragment : interactionImpl
				.getFragments()) {
			if (interactionFragment instanceof BehaviorExecutionSpecificationImpl) {
				BehaviorExecutionSpecificationImpl fragment = (BehaviorExecutionSpecificationImpl) interactionFragment;
				sd.addBehavior(behaviorReader(fragment));
			}
		}

		// reading combine fragments
		for (InteractionFragment interactionFragment : interactionImpl
				.getFragments()) {
			if (interactionFragment instanceof CombinedFragment) {
				CombinedFragment combinedFragment = (CombinedFragment) interactionFragment;
				sd.addFragment(fragmentReader(combinedFragment));
			}
		}

		// Adding Behavior Calls
		for (SequenceBehavior behavior : sd.getBehaviors()) {
			addBehaviorDetail(behavior, interactionImpl.getFragments());
		}

		// FormalGates
		for (Gate gate : interactionImpl.getFormalGates()) {
			sd.addGate(gateReader(gate));
		}

		// LifLines
		for (Lifeline lifeline : interactionImpl.getLifelines()) {
			sd.addLifeline(lifelineReader(lifeline));
		}

		// Messages
		for (Message message : interactionImpl.getMessages()) {
			sd.addMessage(messageReader(message));
		}

		return sd;

	}

	private void addBehaviorDetail(SequenceBehavior behavior,
			EList<InteractionFragment> fragments) {
		ArrayList<String> calles = new ArrayList<String>();
		// MessageOccurrence
		for (InteractionFragment interactionFragment : fragments) {
			if (interactionFragment instanceof MessageOccurrenceSpecificationImpl) {
				MessageOccurrenceSpecificationImpl fragment = (MessageOccurrenceSpecificationImpl) interactionFragment;
				SequenceLifeline lifeline = lifelineReader(fragment
						.getCovereds().get(0));

				if (behavior.getLifeline().getLifelineName()
						.equals(lifeline.getLifelineName())) {

					SequenceMessage message = messageReader(fragment
							.getMessage());
					if (message != null) {

						calles.add(message.getMessageName());

						// Break Loop if found Synchronized Message Reply
						if (behavior.getFinish() != null
								&& message.getMessageType() != null) {
							if (behavior.getFinish().getMessageName()
									.equals(message.getMessageName())
									&& message.getMessageType().equals(
											Keywords.Reply)) {
								break;
							}
						}

						// All Messages After Start Message
						if (!behavior.getStart().getMessageName()
								.equals(message.getMessageName())) {

							// If Message Type is not Reply
							if (message.getMessageType() != null) {
								if (!message.getMessageType().equals(
										Keywords.Reply)) {

									if (message.getReciver() != null) {
										// Message Next Behavior Covered
										// Lifeline
										if (!message
												.getReciver()
												.getLifelineName()
												.equals(lifeline
														.getLifelineName())) {

											if (calles.contains(behavior
													.getStart()
													.getMessageName())) {
												behavior.addCall(message);
											}
										}
									}
								}
							}
						}
					}
				}
			} else if (interactionFragment instanceof CombinedFragment) {
				CombinedFragment combinedFragment = (CombinedFragment) interactionFragment;
				SequenceCombinedFragment fragment = fragmentReader(combinedFragment);
				if (fragment != null) {
					for (SequenceLifeline lifeline : fragment
							.getSequenceLifelines()) {
						if (lifeline.getLifelineName().equals(
								behavior.getLifeline().getLifelineName())) {
							behavior.addFragment(fragment);
						}
					}
				}
			}

		}

	}

	/**
	 * fragmentReader read fragment type,condition,messages
	 * 
	 * @param CombinedFragment
	 * @return SequenceCombinedFragment
	 */

	private SequenceCombinedFragment fragmentReader(
			CombinedFragment combinedFragment) {

		SequenceCombinedFragment sequenceCombinedFragment = new SequenceCombinedFragment();
		for (Lifeline lifeline : combinedFragment.getCovereds()) {
			SequenceLifeline fragmentLifeline = lifelineReader(lifeline);
			sequenceCombinedFragment.addSequenceLifeline(fragmentLifeline);

		}

		sequenceCombinedFragment.setOperation(combinedFragment
				.getInteractionOperator().getName());
		for (InteractionOperand interactionOperand : combinedFragment
				.getOperands()) {
			OpaqueExpressionImpl opaqueExpressionImpl = (OpaqueExpressionImpl) interactionOperand
					.getGuard().getSpecification();
			sequenceCombinedFragment.setCondition(opaqueExpressionImpl
					.getBodies().get(0));

			for (InteractionFragment Operandfragment : interactionOperand
					.getFragments()) {
				if (Operandfragment instanceof MessageOccurrenceSpecificationImpl) {
					MessageOccurrenceSpecificationImpl fragment = (MessageOccurrenceSpecificationImpl) Operandfragment;

					SequenceMessage message = messageReader(fragment
							.getMessage());

					if (!message.getSender().getLifelineName().equals("")) {
						sequenceCombinedFragment.addCall(message);
					}
				}
			}

		}

		return sequenceCombinedFragment;
	}

	/**
	 * behaviorReader read BehaviorFragment Lifeline and start and finsh event
	 * 
	 * @param BehaviorExecutionSpecificationImpl
	 * @return SequenceBehavior
	 */
	private SequenceBehavior behaviorReader(
			BehaviorExecutionSpecificationImpl fragment) {
		SequenceBehavior behavior = new SequenceBehavior();
		for (Lifeline lifeline : fragment.getCovereds()) {
			behavior.setLifeline(lifelineReader(lifeline));
		}
		// Behavior Start Occurrence
		if (fragment.getStart() instanceof MessageOccurrenceSpecification) {
			MessageOccurrenceSpecification specification = (MessageOccurrenceSpecification) fragment
					.getStart();
			behavior.setStart(messageReader(specification.getMessage()));
		}
		// Behavior Finish Occurrence
		if (fragment.getFinish() instanceof MessageOccurrenceSpecification) {
			MessageOccurrenceSpecification specification = (MessageOccurrenceSpecification) fragment
					.getFinish();
			behavior.setFinish(messageReader(specification.getMessage()));
		}
		return behavior;
	}

	/**
	 * gate reader read sequenceGate from sequenceDiagram
	 * 
	 * @param Gate
	 * @return SequenceGate
	 */
	private SequenceGate gateReader(Gate gate) {
		SequenceGate sequenceGate = new SequenceGate();
		sequenceGate.setGateMessage(gate.getMessage().getName());
		MessageEnd interactionFragment = gate.getMessage().getReceiveEvent();

		if (interactionFragment instanceof MessageOccurrenceSpecificationImpl) {
			MessageOccurrenceSpecificationImpl fragment = (MessageOccurrenceSpecificationImpl) interactionFragment;
			for (Lifeline lifeline : fragment.getCovereds()) {
				SequenceLifeline sequenceLifeline = new SequenceLifeline();
				sequenceLifeline.setLifelineName(lifeline.getName());
				sequenceLifeline.setRepresents(lifeline.getRepresents()
						.getName());
				sequenceGate.setGateLifeline(sequenceLifeline);
			}
		}
		return sequenceGate;
	}

	/**
	 * lifelineReader read lifeline name representation from Lifeline
	 * 
	 * @param Lifeline
	 * @return SequenceLifeline
	 */
	private SequenceLifeline lifelineReader(Lifeline lifeline) {
		SequenceLifeline sequenceLifeline = new SequenceLifeline();
		sequenceLifeline.setLifelineName(lifeline.getName());
		sequenceLifeline.setRepresents(lifeline.getRepresents().getName());
		return sequenceLifeline;
	}

	/**
	 * messageReader read sequence message and get message Name, Type, Sender
	 * and Reciver lifeLines
	 * 
	 * @param Message
	 * @return SequenceMessage
	 */
	private SequenceMessage messageReader(Message message) {
		SequenceMessage sequenceMessage = new SequenceMessage();
		if (message != null) {
			if (!message.getName().isEmpty()) {
				if (!message.getName().contains("(")
						&& !message.getName().contains(")")) {
					if (!message.getName().contains(" ")) {
						sequenceMessage.setMessageName(message.getName());
						sequenceMessage.setMessageType(message.getMessageSort()
								.toString());
						// Send Event
						if (message.getSendEvent() instanceof MessageOccurrenceSpecification) {
							MessageOccurrenceSpecification specification = (MessageOccurrenceSpecification) message
									.getSendEvent();
							for (Lifeline lifeline : specification
									.getCovereds()) {
								SequenceLifeline sequenceLifeline = new SequenceLifeline();
								sequenceLifeline.setLifelineName(lifeline
										.getName());
								sequenceLifeline.setRepresents(lifeline
										.getRepresents().getName());
								sequenceMessage.setSender(sequenceLifeline);
							}
						}

						// Received Event
						if (message.getReceiveEvent() instanceof MessageOccurrenceSpecification) {
							MessageOccurrenceSpecification specification = (MessageOccurrenceSpecification) message
									.getReceiveEvent();
							for (Lifeline lifeline : specification
									.getCovereds()) {
								SequenceLifeline sequenceLifeline = new SequenceLifeline();
								sequenceLifeline.setLifelineName(lifeline
										.getName());
								sequenceLifeline.setRepresents(lifeline
										.getRepresents().getName());
								sequenceMessage.setReciver(sequenceLifeline);

							}
						}

					}
				}
			}
		}
		return sequenceMessage;
	}

	private ArrayList<ClassMethod> readOperations(EList<Operation> eList) {
		ArrayList<String> opps = new ArrayList<String>();
		ArrayList<ClassMethod> structure = new ArrayList<ClassMethod>();
		if (!eList.isEmpty()) {
			for (org.eclipse.uml2.uml.Operation oper : eList) {

				ClassMethod operation = new ClassMethod();
				operation.setmethodName(oper.getName());
				operation.setmethodAccessibility(oper.getVisibility()
						.toString());

				EList<Parameter> parameters = oper.getOwnedParameters();

				if (!parameters.isEmpty()) {
					for (Parameter parameter : parameters) {
						ClassAttribute attr1 = new ClassAttribute();

						/*
						 * System.out.println(parameter.getName()+"  "+(
						 * parameter.getDirection() == null?"Return":"void"
						 * )+" "+parameter.getDirection());
						 */

						attr1.setDirection(parameter.getDirection().toString());
						if (parameter.getType() instanceof org.eclipse.uml2.uml.internal.impl.PrimitiveTypeImpl) {
							attr1.setAttributeName(parameter.getName()
									.toString());
							PrimitiveTypeImpl prim = (PrimitiveTypeImpl) (parameter
									.getType());
							attr1.setAttributeType(prim.eProxyURI().fragment());

						} else if (parameter.getType() instanceof org.eclipse.uml2.uml.internal.impl.InterfaceImpl) {

							attr1.setAttributeName(parameter.getName());
							attr1.setAttributeType("List<E>");
						}
						if (parameter.getDirection() == null) {
							operation.setmethodReturnType(attr1
									.getAttributeType());
						} else {
							operation.setmethodReturnType("void");
						}
						operation.getmethodParameters().add(attr1);
					}
				}

				if (!opps.contains(operation.getmethodName())) {
					opps.add(operation.getmethodName());
					structure.add(operation);
				}
			}
		}
		return structure;
	}

	private ArrayList<ClassAttribute> readAttributes(EList<Property> attributes) {
		ArrayList<ClassAttribute> structure = new ArrayList<ClassAttribute>();
		if (!attributes.isEmpty()) {
			for (Property attribute : attributes) {
				ClassAttribute attr = new ClassAttribute();
				if (attribute.getType() instanceof org.eclipse.uml2.uml.internal.impl.PrimitiveTypeImpl) {
					attr.setAttributeName(attribute.getName().toString());
					attr.setAttributeVisibility(attribute.getVisibility()
							.toString());
					PrimitiveTypeImpl prim = (PrimitiveTypeImpl) (attribute
							.getType());
					attr.setAttributeType(prim.eProxyURI().fragment());
					structure.add(attr);
				} else if (attribute.getType() instanceof org.eclipse.uml2.uml.internal.impl.InterfaceImpl) {
					/*
					 * InterfaceImpl prim = (InterfaceImpl) (attribute
					 * .getType()); attr.setType(prim.eProxyURI().query());
					 * structure.getAttributes().add(attr);
					 */
				}

			}
		}
		return structure;
	}
}
