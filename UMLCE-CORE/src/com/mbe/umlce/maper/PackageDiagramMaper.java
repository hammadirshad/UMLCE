package com.mbe.umlce.maper;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.PD;
import com.mbe.umlce.dataobject.classDiagram.ClassStructure;
import com.mbe.umlce.dataobject.result.MappingErrors;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.reader.PackageDiagramReader;

public class PackageDiagramMaper {

	PackageDiagramReader reader = new PackageDiagramReader();

	public static void main(String[] args) throws Exception {
		PackageDiagramMaper diagramIdentifier = new PackageDiagramMaper();
		FileInputStream fileInputStream = new FileInputStream(
				"C:\\Users\\Muhammad\\Desktop\\FYP\\Model\\Package.uml");

		FileInputStream fileInputStream2 = new FileInputStream(
				"C:\\Users\\Muhammad\\Desktop\\FYP\\Model\\sequenceMappingCode.zip");

		diagramIdentifier.mapPackageToCode(new ModelFile(fileInputStream),
				new ModelFile(fileInputStream2));
	}

	public Result mapPackageToCode(ModelFile model, ModelFile code)
			throws Exception {

		List<PD> packages = reader.getRefModelDetails(model);
		ZipEntry entry;

		ArrayList<MappingErrors> results = new ArrayList<MappingErrors>();
		CompilationUnit cu;
		for (PD pd : packages) {

			System.out
					.println("\n Package Name: " + pd.getPackageName() + "\n");
			for (ClassStructure classStructure : pd.getClasses()) {
				boolean found = false;
				System.out.println("Class Name: "
						+ classStructure.getClassName());
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len;
				while ((len = code.getModel().read(buffer)) > -1) {
					baos.write(buffer, 0, len);
				}
				baos.flush();
				code.setModel(new BufferedInputStream(new ByteArrayInputStream(
						baos.toByteArray())));
				ZipInputStream directory = new ZipInputStream(
						new BufferedInputStream(new ByteArrayInputStream(
								baos.toByteArray())));
				while ((entry = directory.getNextEntry()) != null) {
					if (!entry.isDirectory()
							&& entry.getName().endsWith(".java")) {
						if (entry.getName()
								.substring(0, entry.getName().length() - 5)
								.equals(classStructure.getClassName())) {
							found = true;
							try {
								cu = JavaParser.parse(directory);
								if (!(cu.getPackage().getName().toString())
										.equals(classStructure.getPackage())) {

									results.add(new MappingErrors("Package ["
											+ classStructure.get_package()
											+ "] in Class ["
											+ classStructure.getClassName()
											+ "]", "Package Name Not Match"));

									System.out.println("Class ["
											+ classStructure.getClassName()
											+ "]" + "Package Name Not Match");
								}

								for (String _import : classStructure
										.getImports()) {

									boolean importFound = false;
									for (ImportDeclaration importDeclaration : cu
											.getImports()) {
										if (_import.equals(importDeclaration
												.getName().toString())) {
											importFound = true;
											break;
										}
									}
									if (!importFound) {
										results.add(new MappingErrors(
												"Import ["
														+ _import
														+ "] Class ["
														+ classStructure
																.getClassName()
														+ "]",
												"Import Not Found"));

										System.out.println("Import [" + _import
												+ "]" + "Import Not Found");
									}
								}

								break;
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					}
				}
				if (!found) {
					results.add(new MappingErrors("Class ["
							+ classStructure.getClassName() + "]",
							"Class not Found"));
					System.out.println("Class ["
							+ classStructure.getClassName() + "]"
							+ " Class not Found");
				}
			}

		}

		Result result = new Result();
		result.setMappingErrors(results);
		return result;
	}
}
