package com.mbe.umlce.identifier;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.mbe.umlce.dataobject.Errors;
import com.mbe.umlce.dataobject.Keywords;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.uml.UMLModelLoader;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class DomainModelIdentifier implements Serializable,Identifier{
	private static final long serialVersionUID = 1L;
	private MaxentTagger tagger;

	public Result identifyMistakes(ModelFile modelFile) throws Exception {
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
			if (element.eClass() == UMLPackage.Literals.CLASS) {
				Class clas = (Class) element;
				String clasName = clas.getName();
				// if concept named a system
				if (clasName.equals("system") || clasName.equals("System"))
					mistakes.add(new Errors(Keywords.Error, "Invalid Concept",
							"Domain Model", clasName,
							"Concept cannot be named as system/System."));
				tagger = new MaxentTagger(taggerPath);
				String taggedName = tagger.tagString(clasName);
				// if class name is not noun then add it in mistakes list
				if (!taggedName.contains("NN") && !taggedName.contains("NNP")
						&& !taggedName.contains("NNS")
						&& !taggedName.contains("NNPS")) {
					mistakes.add(new Errors(Keywords.Error, "Invalid Concept",
							"Domain Model", clasName,
							"Concept(Class) name is not noun."));
				}

				if (clasName.contains(":"))
					mistakes.add(new Errors(Keywords.Error, "Invalid Concept",
							"Domain Model", clasName,
							"Concept(Class) name contains colon(:)"));
				// check for class relationship
				if (clas.getRelationships().isEmpty())
					mistakes.add(new Errors(Keywords.Error, "Invalid Concept",
							"Domain Model", clasName,
							"Concept(Class) having no Relationship."));
				// get class attributes
				EList<Property> attribute = clas.getOwnedAttributes();
				if (attribute.isEmpty()) {
					mistakes.add(new Errors(Keywords.Warning,
							"Missing Attributes", "Domain Model", clasName,
							"Concept(Class) having no attribute"));
				}
				int count_1 = 0, count_2 = 0;

				for (Property attr : attribute) {
					if (attr.getAssociation() != null) {
						count_2++;
					}
					// if attribute name is not verb then add it in mistakes
					// list
					if (attr.getAssociation() != null) {
						String taggedAttr = tagger.tagString(attr.getName());
						if (!taggedAttr.contains("NN")
								&& !taggedAttr.contains("NNP")
								&& !taggedAttr.contains("NNS")
								&& !taggedAttr.contains("NNPS"))
							mistakes.add(new Errors(Keywords.Error,
									"Invalid Attribute", "Domain Model",
									clasName + "->" + attr.getName(),
									"Attribute name is not noun."));
						// checking visibility of attribute
						if (attr.getVisibility().getName().equals("public")) {
							mistakes.add(new Errors(Keywords.Warning,
									"Attribute Visibility", "Domain Model",
									clasName + "->" + attr.getName(),
									"Attribute visibility is public in concept(class)"));
						}
						// checking static members
						if (attr.isStatic()) {
							mistakes.add(new Errors(Keywords.Warning,
									"Static Attribute", "Domain Model",
									clasName + "->" + attr.getName(),
									"Static attribute in concept(class)"));
						}
						// Missing data type of Attribute
						if (attr.getUpperValue() == null
								&& attr.getLowerValue() == null) {
							Type pt = attr.getType();
							if (pt == null) {
								mistakes.add(new Errors(Keywords.Warning,
										"Attribute data type", "Domain Model",
										clasName + "->" + attr.getName(),
										"Missing data type of attribute."));
							}
						}
					}
					++count_1;
				}
				if (count_1 == count_2) {
					mistakes.add(new Errors(Keywords.Warning,
							"Missing Attributes", "Domain Model", clasName,
							"Concept(Class) having no attribute"));
				}
				// operations
				List<org.eclipse.uml2.uml.Operation> operation = clas
						.getOwnedOperations();
				// Missing Operation(s) in Class
				if (!operation.isEmpty()) {
					mistakes.add(new Errors(Keywords.Warning, "Operation(s)",
							"Domain Model", clasName,
							"Concept having Operation(s)."));
				}
			} else if (element.eClass() == UMLPackage.Literals.ASSOCIATION) {
				if (element.getName() != null) {
					// association name
					if (element.getName().isEmpty()) {
						mistakes.add(new Errors(Keywords.Warning,
								"Association name", "Domain Model",
								"Association", "Missing association name."));
					}
				}
			}
		}
		Result result = new Result();
		result.setErrors(mistakes);
		return result;
	}

}
