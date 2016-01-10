package com.mbe.umlce.reader;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;

import com.mbe.umlce.dataobject.AD;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.uml.UMLModelLoader;

public class ActivityDiagramReader {
	
	public AD getModelDetails(ModelFile modelFile) {
		AD adDetails = new AD();

		try {
			UMLModelLoader umlModel = new UMLModelLoader();
			EList<PackageableElement> packageableElements;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = modelFile.getModel().read(buffer)) > -1) {
				baos.write(buffer, 0, len);
			}
			baos.flush();
			modelFile.setModel(new BufferedInputStream(
					new ByteArrayInputStream(baos.toByteArray())));
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
					adDetails.setActivityName(activity.getName());
					for (ActivityNode an : activity.getNodes()) {
						if (an.eClass() == UMLPackage.Literals.OPAQUE_ACTION) {
							adDetails.addOpaqueActions(an.getName());
						} else if (an.eClass() == UMLPackage.Literals.JOIN_NODE) {
							adDetails.addJoins(an.getName());
						} else if (an.eClass() == UMLPackage.Literals.FORK_NODE) {
							adDetails.addForks(an.getName());
						} else if (an.eClass() == UMLPackage.Literals.MERGE_NODE) {
							adDetails.addMerges(an.getName());
						} else if (an.eClass() == UMLPackage.Literals.DECISION_NODE) {
							adDetails.addDecisions(an.getName());
						}
					}
					for (ActivityEdge ed : activity.getEdges()) {
						adDetails
								.addEdges(ed.getSource().getName() + ","
										+ ed.getName() + ","
										+ ed.getTarget().getName());
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adDetails;
	}

}
