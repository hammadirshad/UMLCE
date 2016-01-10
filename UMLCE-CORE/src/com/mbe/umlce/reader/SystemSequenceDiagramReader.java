package com.mbe.umlce.reader;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.impl.CollaborationImpl;
import org.eclipse.uml2.uml.internal.impl.InteractionImpl;

import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.SSD;
import com.mbe.umlce.uml.UMLModelLoader;

public class SystemSequenceDiagramReader implements Serializable{
	private static final long serialVersionUID = 1L;

	public SSD getRefModelDetails(ModelFile modelFile) throws Exception {
		SSD ssd = null;

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

			if (element.eClass() == UMLPackage.Literals.COLLABORATION) {
				CollaborationImpl collaborationImpl = (CollaborationImpl) element;


				if (collaborationImpl.getOwnedBehaviors() != null) {
					for (Element element2 : collaborationImpl
							.getOwnedBehaviors()) {
						if (element2.eClass() == UMLPackage.Literals.INTERACTION) {
							InteractionImpl interactionImpl = (InteractionImpl) element2;
							ssd = interactionReader(interactionImpl);
						}
					}
				}
			}

			if (element.eClass() == UMLPackage.Literals.INTERACTION) {
				InteractionImpl interactionImpl = (InteractionImpl) element;
				ssd = interactionReader(interactionImpl);

			}

		}
		
		return ssd;
	}

	private SSD interactionReader(InteractionImpl interactionImpl) {
		ArrayList<String> operations = new ArrayList<String>();
		ArrayList<String> lifelines = new ArrayList<String>();
		SSD ssd = new SSD();
		// LifLines
		for (Lifeline lifeline : interactionImpl.getLifelines()) {
			lifelines.add(lifeline.getName());
		}

		// Messages
		for (Message message : interactionImpl.getMessages()) {
			operations.add(message.getName());
		}
		return ssd;

	}

	
}
