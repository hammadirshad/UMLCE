package com.mbe.umlce.identifier;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.impl.PropertyImpl;
import org.eclipse.uml2.uml.internal.impl.UseCaseImpl;

import com.mbe.umlce.dataobject.Errors;
import com.mbe.umlce.dataobject.Keywords;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.uml.UMLModelLoader;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class UsecaseDiagramIdentifier implements Serializable,Identifier{
	private static final long serialVersionUID = 1L;

	private MaxentTagger tagger;

	public Result identifyMistakes(ModelFile modelFile) throws Exception {

		ArrayList<String> ucNames = new ArrayList<String>();
		ArrayList<String> associations = new ArrayList<String>();
		ArrayList<String> actors = new ArrayList<String>();
		boolean found = false;
		int count = 0;
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
			if (element.eClass() == UMLPackage.Literals.COMPONENT) {
				EList<Element> ownedElems = element.getOwnedElements();
				for (Element elem : ownedElems) {
					if (elem.getClass().getName()
							.contains(Keywords.UseCaseImpl)) {
						UseCaseImpl uc = (UseCaseImpl) elem;
						tagger = new MaxentTagger(taggerPath);
						String taggedName = tagger.tagString(uc.getName());
						// if Usecase name is not noun then add it in mistakes
						// list
						if (!taggedName.contains("VB")
								&& !taggedName.contains("VBD")
								&& !taggedName.contains("VBG")
								&& !taggedName.contains("VBGN")
								&& !taggedName.contains("VBP")
								&& !taggedName.contains("VBZ"))
							mistakes.add(new Errors(Keywords.Error,
									"Invalid UC", "Usecase Diagram", uc
											.getName(),
									"Usecase name starts with non-verb"));
						// check whether one UC name occurs multiple times
						if (ucNames.contains(uc.getName()))
							mistakes.add(new Errors(Keywords.Error,
									"Duplicate UCs", "Usecase Diagram", uc
											.getName(),
									"Usecase occurs multiple times."));
						ucNames.add(uc.getName());
						// extends in use case
						if (!uc.getExtends().isEmpty())
							mistakes.add(new Errors(Keywords.Warning,
									"Extend Relationship", "Usecase Diagram",
									uc.getName(),
									"Use case having <<extend>> relationship."));
						// for two or more includes
						if (uc.getIncludes().size() >= 2)
							mistakes.add(new Errors(Keywords.Warning,
									"Multiple includes", "Usecase Diagram", uc
											.getName(),
									"Usecase having more than one <<include>> relationships."));
						// use case having no relationship
						if (uc.getAssociations().isEmpty())
							mistakes.add(new Errors(Keywords.Error,
									"Invalid UC", "Usecase Diagram ", uc
											.getName(),
									"Usecase having no Association with Actor."));
					}
				}
			}
			// if system boundary is missing then read individual use case
			// elements
			else if (element.eClass() == UMLPackage.Literals.USE_CASE) {
				if (!found) {
					mistakes.add(new Errors(Keywords.Error,
							"Missing System Boundary", "Usecase Diagram",
							"System", "Use cases should be in system boundary."));
					found = true;
				}
				tagger = new MaxentTagger(taggerPath);
				String taggedName = tagger.tagString(element.getName());
				// if Usecase name is not noun then add it in mistakes list
				if (!taggedName.contains("VB") && !taggedName.contains("VBD")
						&& !taggedName.contains("VBG")
						&& !taggedName.contains("VBGN")
						&& !taggedName.contains("VBP")
						&& !taggedName.contains("VBZ"))
					mistakes.add(new Errors(Keywords.Error, "Invalid UC",
							"Usecase Diagram", element.getName(),
							"Usecase name starts with non-verb"));
				// check whether one UC name occurs multiple times
				if (ucNames.contains(element.getName()))
					mistakes.add(new Errors(Keywords.Error, "Duplicate UCs",
							"Usecase Diagram", element.getName(),
							"Usecase occurs multiple times."));
				ucNames.add(element.getName());
				if (!element.getOwnedElements().isEmpty()) {
					EList<Element> ucElems = element.getOwnedElements();
					for (Element elem : ucElems) {
						// count include relationships
						if (elem.getClass().getName()
								.contains(Keywords.IncludeImpl))
							++count;
						else if (elem.getClass().getName()
								.contains(Keywords.ExtendImpl))
							mistakes.add(new Errors(Keywords.Warning,
									"Extend Relationship", "Usecase Diagram",
									element.getName(),
									"Use case having <<extend>> relationship."));
						// if include relationship exists more than one time
						// then add it to mistakes
						if (count >= 2)
							mistakes.add(new Errors(Keywords.Warning,
									"Multiple includes", "Usecase Diagram",
									element.getName(),
									"Usecase having more than one <<include>> relationships."));
					}
				}
			} else if (element.eClass() == UMLPackage.Literals.ACTOR) {
				actors.add(element.getName());
			} else if (element.eClass() == UMLPackage.Literals.ASSOCIATION) {
				EList<org.eclipse.uml2.uml.Element> ownedElems = element
						.getOwnedElements();
				for (org.eclipse.uml2.uml.Element elem : ownedElems) {
					PropertyImpl pi = (PropertyImpl) elem;
					associations.add(pi.getName());
				}
			}
		}
		// check if actor has no relationship with any usecase
		for (int i = 0; i < actors.size(); i++) {
			if (!associations.contains(actors.get(i).toLowerCase()))
				mistakes.add(new Errors(Keywords.Error, "Invalid Actor",
						"Usecase Diagram", actors.get(i),
						"Actor having no Association with any Usecase."));
		}
		Result result = new Result();
		result.setErrors(mistakes);
		return result;
	}

}
