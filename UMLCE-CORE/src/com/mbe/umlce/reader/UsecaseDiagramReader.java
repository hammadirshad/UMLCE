package com.mbe.umlce.reader;

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

import com.mbe.umlce.dataobject.Keywords;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.UD;
import com.mbe.umlce.uml.UMLModelLoader;

public class UsecaseDiagramReader implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * This function inputs a reference model and return a hashtable containing
	 * use case model details (i.e. actors, usecase name, associations)
	 */
	public UD getRefModelDetails(ModelFile modelFile) throws Exception {
		UD ud = new UD();
		ArrayList<String> actors = new ArrayList<String>();
		ArrayList<String> usecases = new ArrayList<String>();
		ArrayList<String> associations = new ArrayList<String>();
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
			if (element.eClass() == UMLPackage.Literals.ACTOR)
				actors.add(element.getName());
			else if (element.eClass() == UMLPackage.Literals.USE_CASE)
				usecases.add(element.getName());
			if (element.eClass() == UMLPackage.Literals.COMPONENT) {
				EList<Element> ownedElems = element.getOwnedElements();
				for (Element elem : ownedElems) {
					if (elem.getClass().getName()
							.contains(Keywords.UseCaseImpl)) {
						UseCaseImpl uc = (UseCaseImpl) elem;
						usecases.add(uc.getName());
					}
				}

			} else if (element.eClass() == UMLPackage.Literals.ASSOCIATION) {
				EList<org.eclipse.uml2.uml.Element> ownedElems = element
						.getOwnedElements();
				for (org.eclipse.uml2.uml.Element elem : ownedElems) {
					PropertyImpl pi = (PropertyImpl) elem;
					associations.add(pi.getName());
				}

			}
		}
		ud.setActors(actors);
		ud.setUsecases(usecases);
		ud.setAssociations(associations);
		return ud;
	}

}