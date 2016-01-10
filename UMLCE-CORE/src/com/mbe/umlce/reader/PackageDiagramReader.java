package com.mbe.umlce.reader;

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
import org.eclipse.uml2.uml.UMLPackage;

import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.PD;
import com.mbe.umlce.dataobject.classDiagram.ClassStructure;
import com.mbe.umlce.uml.UMLModelLoader;

public class PackageDiagramReader implements Serializable{

	private static final long serialVersionUID = 1L;

	public List<PD> getRefModelDetails(ModelFile file) throws Exception {
		List<PD> packages = new ArrayList<PD>();
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

		for (PackageableElement element : packageableElements) {

			if (element.eClass() == UMLPackage.Literals.PACKAGE) {
				Package _package = (Package) element;
				PD pd = new PD();
				pd.setPackageName(_package.getName());
				for (Package importPackage : _package.getImportedPackages()) {
					pd.addImport(importPackage.getName());
				}
				for (PackageableElement elecment2 : _package
						.getPackagedElements()) {
					if (elecment2.eClass() == UMLPackage.Literals.CLASS) {
						Class _class = (Class) elecment2;
						ClassStructure classStructure = new ClassStructure();
						classStructure.setClassName(_class.getName());
						classStructure.setPackage(pd.getPackageName());
						classStructure.setImports(pd.getImports());
						pd.addClass(classStructure);
					}

				}
				packages.add(pd);
			}
		}
		return packages;
	}
}