package com.mbe.umlce.reader;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Relationship;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.impl.ClassImpl;

import com.mbe.umlce.dataobject.CD;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.uml.UMLModelLoader;

public class DomainModelReader implements Serializable{

	private static final long serialVersionUID = 1L;
	private static ArrayList<CD> refModelDetails;

	/*
	 * This function inputs a reference model and return a ArrayList containing
	 * object of Class Model details.
	 */
	public ArrayList<CD> getRefModelDetails(ModelFile modelFile)
			throws Exception {
		refModelDetails = new ArrayList<CD>();
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
				CD details = new CD();

				Class clas = (Class) element;
				String clasName = clas.getName();
				details.setClassName(clasName);
				// get class attributes
				EList<Property> attributes = clas.getOwnedAttributes();
				if (!attributes.isEmpty()) {
					for (Property attr : attributes) {
						details.addAttribute(attr.getName());
					}
				}
				// get class operations
				List<org.eclipse.uml2.uml.Operation> operations = clas
						.getOwnedOperations();
				if (!operations.isEmpty()) {
					for (org.eclipse.uml2.uml.Operation oper : operations) {
						details.addOperation(oper.getName());
					}
				}
				// get relationships
				EList<Relationship> relationships = clas.getRelationships();
				for (Relationship rel : relationships) {
					EList<Element> relElems = rel.getRelatedElements();
					for (Element elem : relElems) {
						ClassImpl relName = (ClassImpl) elem;
						if (!relName.getName().equals(clas.getName()))
							details.addRelationships(relName.getName());
					}
				}
				refModelDetails.add(details);
			}
		}
		return refModelDetails;
	}

}
