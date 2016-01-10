package com.mbe.umlce.identifier;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.impl.CollaborationImpl;
import org.eclipse.uml2.uml.internal.impl.InteractionImpl;

import com.mbe.umlce.dataobject.Errors;
import com.mbe.umlce.dataobject.Keywords;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.uml.UMLModelLoader;

public class SequenceDiagramIdentifier implements Serializable,Identifier{
	private static final long serialVersionUID = 1L;

	private ArrayList<Errors> mistakes = new ArrayList<Errors>();
	private ArrayList<String> operations = new ArrayList<String>();

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
				Class umlClass = (Class) element;
				if (umlClass.getAllOperations().isEmpty()) {
					mistakes.add(new Errors(Keywords.Error,
							"Missing Operation", "Sequence Diagram", umlClass
									.getName(), "Class have no Operation."));
				}
			}

			/* Diagram Type is Collaboration For RSA */
			if (element.eClass() == UMLPackage.Literals.COLLABORATION) {
				CollaborationImpl collaborationImpl = (CollaborationImpl) element;

				if (collaborationImpl.getOwnedBehaviors() != null) {
					for (Element element2 : collaborationImpl
							.getOwnedBehaviors()) {
						/* Collaboration Behaviors is Interaction for RSA */
						if (element2.eClass() == UMLPackage.Literals.INTERACTION) {
							readInteractions(element2);
						}
					}
				} else {
					/* Collaboration Elements For Papyrus */
					readCollabrations(element);
				}

			}

			/* Diagram Type is Interaction For Papyrus */
			if (element.eClass() == UMLPackage.Literals.INTERACTION) {
				readInteractions(element);
			}

		}
		Result result = new Result();
		result.setErrors(mistakes);
		return result;
	}

	private void readCollabrations(Element elements) {
		for (Element element : elements.getOwnedElements()) {
			if (element instanceof Message) {
				Message message = (Message) element;
				readMessage(message);
			} else if (element instanceof Lifeline) {
				Lifeline lifeline = (Lifeline) element;
				readLifeline(lifeline);
			}
		}
	}

	private void readInteractions(Element element) {

		InteractionImpl interactionImpl = (InteractionImpl) element;

		for (Lifeline lifeline : interactionImpl.getLifelines()) {
			readLifeline(lifeline);
		}

		for (Message message : interactionImpl.getMessages()) {
			readMessage(message);
		}

	}

	private void readLifeline(Lifeline lifeline) {
		if (lifeline.getName().contains(":")) {
			mistakes.add(new Errors(Keywords.Error, "Incorrect Lifeline Name",
					"Sequence Diagram", lifeline.getName(),
					"Lifeline Name contain :"));
		}
	}

	private void readMessage(Message message) {
		// for invalid return message
		if (message.getMessageSort().getName().equals(Keywords.Reply)) {
			if (message.getName().contains("(")
					&& message.getName().contains(")"))
				mistakes.add(new Errors(Keywords.Error,
						"Invalid Return Message", "Sequence Diagram", message
								.getName(), "Should not like operation."));
		}

		// for invalid operation call
		if (!message.getMessageSort().getName().equals(Keywords.Reply)
				&& !message.getName().isEmpty()
				&& !message.getMessageSort().getName().equals("createMessage")) {
			if (!message.getName().contains("(")
					&& !message.getName().contains(")"))
				mistakes.add(new Errors(Keywords.Error, "Invalid operation",
						"Sequence Diagram", message.getName(),
						"Operation is like return message."));
		}
		// Incorrect function name separated with space
		if (message.getName().contains(" ")) {
			mistakes.add(new Errors(Keywords.Error, "Invalid operation",
					"Sequence Diagram", message.getName(),
					"Operation name cannot be separated by space."));
		}
		// Operation call without operation name
		if (message.getName().isEmpty()) {
			mistakes.add(new Errors(Keywords.Error, "Invalid operation",
					"Sequence Diagram ", "",
					"Operation call without operation name."));
		}
		// check object creation level
		if (message.getName().toLowerCase().contains("create")
				&& message.getMessageSort().getName().equals("asynchCall")) {
			mistakes.add(new Errors(Keywords.Error,
					"Incorrect object creation", "Sequence Diagram", message
							.getName(),
					"Object creation level is same as lifeline(Class)."));
		}
		// check for duplicate operations
		if (operations.contains(message.getName())
				&& !message.getMessageSort().getName().equals(Keywords.Reply)
				&& !message.getMessageSort().getName()
						.equals(Keywords.CreateMessage)
				&& !message.getMessageSort().getName()
						.equals(Keywords.AsynchSignal)) {

			mistakes.add(new Errors(Keywords.Error, "Duplicate operations",
					"Sequence Diagram", message.getName(),
					"Multiple operations with the same name."));
		}
		if (!message.getMessageSort().getName().equals(Keywords.Reply)
				&& !message.getMessageSort().getName()
						.equals(Keywords.CreateMessage)
				&& !message.getMessageSort().getName()
						.equals(Keywords.AsynchSignal)) {
			System.out.println(message.getName() + " "
					+ message.getMessageSort());

			operations.add(message.getName());
		}
	}

}
