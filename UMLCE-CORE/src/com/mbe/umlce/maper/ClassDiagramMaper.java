package com.mbe.umlce.maper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

import com.mbe.umlce.dataobject.Keywords;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.MappingErrors;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.uml.UMLModelLoader;

public class ClassDiagramMaper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7136117133231485301L;
	private Hashtable<String, ArrayList<String>> generalClassesMap = new Hashtable<String, ArrayList<String>>();

	public Result mapClassToCode(ModelFile modelFile, ModelFile code)
			throws Exception {

		String taggerPath = this.getClass().getClassLoader().getResource("")
				.getPath()
				+ "/taggers/left3words-wsj-0-18.tagger";
		System.out.println(taggerPath);

		ArrayList<MappingErrors> results = new ArrayList<MappingErrors>();
		ArrayList<String> modelClasses = new ArrayList<String>();
		Hashtable<String, ArrayList<String>> genTable = new Hashtable<String, ArrayList<String>>();

		ArrayList<String> clasNames = null;

		// read java code
		Hashtable<String, Hashtable<String, ArrayList<String>>> codeDetails = readDirFiles(code);

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

				if (clasName.contains("{abstract}")) {
					clasName = clasName.substring(0, clasName.indexOf("{"))
							.trim();

				}

				if (!codeDetails.containsKey(clasName.toLowerCase())) {
					results.add(new MappingErrors("Class [" + clasName + "]",
							"Absent in source code"));
				}

				if (codeDetails.containsKey(clasName.toLowerCase())) {
					Hashtable<String, ArrayList<String>> items = codeDetails
							.get(clasName.toLowerCase());

					ArrayList<String> privateItem = items.get(Keywords.Private);
					ArrayList<String> publicItem = items.get(Keywords.Public);
					ArrayList<String> protectedItem = items
							.get(Keywords.Protected);

					// attributes

					EList<Property> attributes = clas.getOwnedAttributes();
					if (!attributes.isEmpty() && (!privateItem.isEmpty())
							|| !publicItem.isEmpty()
							|| !protectedItem.isEmpty()) {

						for (Property attr : attributes) {

							if (privateItem.contains(attr.getName())) {

								if (!attr.getVisibility().toString()
										.equals(Keywords.Private)) {
									results.add(new MappingErrors("Attribute ["
											+ attr.getName() + "] in Class ["
											+ clasName + "]",
											"Visibility Type Not Match."));
								}
							} else if (publicItem.contains(attr.getName())) {

								if (!attr.getVisibility().toString()
										.equals(Keywords.Public)) {
									results.add(new MappingErrors("Attribute ["
											+ attr.getName() + "] in Class ["
											+ clasName + "]",
											"Visibility Type Not Match."));
								}

							} else if (protectedItem.contains(attr.getName())) {

								if (!attr.getVisibility().toString()
										.equals(Keywords.Protected)) {
									results.add(new MappingErrors("Attribute ["
											+ attr.getName() + "] in Class ["
											+ clasName + "]",
											"Visibility Type Not Match."));
								}

							} else if (attr.getAssociation() == null) {
								results.add(new MappingErrors("Attribute ["
										+ attr.getName() + "] in Class ["
										+ clasName + "]",
										"Absent in source code."));
							}

						}

					}

					// operations
					List<org.eclipse.uml2.uml.Operation> operations = clas
							.getOwnedOperations();

					if (!operations.isEmpty() && (!privateItem.isEmpty())
							|| !publicItem.isEmpty()
							|| !protectedItem.isEmpty()) {
						for (org.eclipse.uml2.uml.Operation op : operations) {
							if (privateItem.contains(op.getName())) {

								if (!op.getVisibility().toString()
										.equals(Keywords.Private)) {
									results.add(new MappingErrors("Operation ["
											+ op.getName() + "] in Class ["
											+ clasName + "]",
											"Visibility Type Not Match."));
								}
							} else if (publicItem.contains(op.getName())) {

								if (!op.getVisibility().toString()
										.equals(Keywords.Public)) {
									results.add(new MappingErrors("Operation ["
											+ op.getName() + "] in Class ["
											+ clasName + "]",
											"Visibility Type Not Match."));
								}

							} else if (protectedItem.contains(op.getName())) {

								if (!op.getVisibility().toString()
										.equals(Keywords.Protected)) {
									results.add(new MappingErrors("Operation ["
											+ op.getName() + "] in Class ["
											+ clasName + "]",
											"Visibility Type Not Match."));
								}

							} else {
								results.add(new MappingErrors("Operation ["
										+ op.getName() + "] in Class ["
										+ clasName + "]",
										"Absent in source code."));
							}
						}
					}
				}
				modelClasses.add(clasName.toLowerCase());
				/*
				 * Make model generalizations hashtable.
				 */
				EList<Generalization> generalizations = clas
						.getGeneralizations();
				if (!generalizations.isEmpty()) {
					for (Generalization gen : generalizations) {

						String name = gen.getGeneral().getName();
						if (name.contains(Keywords.Abstract)) {

							String word = name.substring(0, name.indexOf("{"));
							name = word.trim();

						}

						if (!genTable.containsKey(name)) {
							clasNames = new ArrayList<String>();
							clasNames.add(clasName.toLowerCase());
							genTable.put(name, clasNames);
						}

						else {
							clasNames.add(clasName.toLowerCase());
							genTable.put(name, clasNames);
						}

					}
				}
			}
		}
		/*
		 * Check whether generalization (inheritance) shown in class diagram is
		 * implemented in source code or not.
		 */

		Enumeration<String> clasKeys = genTable.keys();
		while (clasKeys.hasMoreElements()) {
			String key = clasKeys.nextElement();
			if (generalClassesMap.containsKey(key)) {

				ArrayList<String> modelClassValues = genTable.get(key);
				ArrayList<String> codeClassValues = generalClassesMap.get(key);
				for (String value : modelClassValues) {
					if (!codeClassValues.contains(value)) {
						results.add(new MappingErrors(value.toUpperCase()
								+ " Generalized from " + key + "",
								"Not generalized in source code"));
					}
				}

			}
		}
		/*
		 * Reverse Inspection i.e. classes,attributes and operations in code but
		 * not in class diagram
		 */
		Set<String> codeKeys = codeDetails.keySet();
		for (String key : codeKeys) {
			if (!modelClasses.contains(key)) {
				results.add(new MappingErrors("Absent in class diagram",
						"Class [" + key + "]"));
			}
		}
		Result result = new Result();
		result.setMappingErrors(results);
		return result;
	}

	public Hashtable<String, Hashtable<String, ArrayList<String>>> readDirFiles(
			ModelFile dirPath) throws Exception {

		Hashtable<String, Hashtable<String, ArrayList<String>>> codeDetails = new Hashtable<String, Hashtable<String, ArrayList<String>>>();

		ZipInputStream directory = new ZipInputStream(new BufferedInputStream(
				dirPath.getModel()));
		// File[] codeFiles = directory.listFiles();
		ArrayList<String> clasNames = null;
		// if (codeFiles != null) {
		ZipEntry entry;
		// reading each file in specified directory
		while ((entry = directory.getNextEntry()) != null) {
			String clasName = null;

			Hashtable<String, ArrayList<String>> items = new Hashtable<String, ArrayList<String>>();
			ArrayList<String> privateItem = new ArrayList<String>();
			ArrayList<String> publicItem = new ArrayList<String>();
			ArrayList<String> protectedItem = new ArrayList<String>();

			if (!entry.isDirectory() && entry.getName().endsWith(".java")) {
				String line = null;
				InputStreamReader fileReader = new InputStreamReader(directory);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				while ((line = bufferedReader.readLine()) != null) {
					// extracting class names from Java class file
					if (line.contains("class") && !line.contains("//")
							&& line.contains(Keywords.Public)
							&& !line.contains(";") && !line.contains("\"")) {

						if (line.contains("extends") && line.contains("{")) {
							clasName = line.substring(
									line.indexOf("class") + 5,
									line.indexOf("extends")).trim();
							String values[] = line.split(" ");
							String clas2Name = values[values.length - 2].trim();
							if (!generalClassesMap.containsKey(clas2Name)) {
								clasNames = new ArrayList<String>();
								clasNames.add(clasName.toLowerCase());
								generalClassesMap.put(clas2Name, clasNames);
							} else {
								clasNames.add(clasName.toLowerCase());
								generalClassesMap.put(clas2Name, clasNames);
							}

						} else if (line.contains("extends")
								&& !line.contains("{")) {
							clasName = line.substring(
									line.indexOf("class") + 5,
									line.indexOf("extends")).trim();
							String values[] = line.split(" ");
							String clas2Name = values[values.length - 1].trim();
							if (!generalClassesMap.containsKey(clas2Name)) {
								clasNames = new ArrayList<String>();
								clasNames.add(clasName.toLowerCase());
								generalClassesMap.put(clas2Name, clasNames);
							} else {
								clasNames.add(clasName.toLowerCase());
								generalClassesMap.put(clas2Name, clasNames);
							}
						} else if (line.contains("implements")) {
							clasName = line.substring(
									line.indexOf("class") + 5,
									line.indexOf("implements")).trim();
						} else if (line.contains("{")) {
							clasName = line.substring(
									line.indexOf("class") + 5,
									line.indexOf("{")).trim();
						} else if (!line.contains("{")
								&& !line.contains("extends")
								&& !line.contains("implements")) {
							String values[] = line.split(" ");
							clasName = values[values.length - 1].trim();
						}
					}
					// extracting class names from C++/C# class file
					else if (line.contains("class") && !line.contains("//")
							&& !line.contains(";") && !line.contains("\"")) {
						if (line.contains(":") && line.contains("{")) {
							clasName = line.substring(
									line.indexOf("class") + 5,
									line.indexOf(":")).trim();
							String values[] = line.split(" ");
							String clas2Name = values[values.length - 2].trim();
							if (!generalClassesMap.containsKey(clas2Name)) {
								clasNames = new ArrayList<String>();
								clasNames.add(clasName.toLowerCase());
								generalClassesMap.put(clas2Name, clasNames);
							} else {
								clasNames.add(clasName.toLowerCase());
								generalClassesMap.put(clas2Name, clasNames);
							}
						} else if (line.contains(":") && !line.contains("{")) {
							clasName = line.substring(
									line.indexOf("class") + 5,
									line.indexOf(":")).trim();
							String values[] = line.split(" ");
							String clas2Name = values[values.length - 1].trim();
							if (!generalClassesMap.containsKey(clas2Name)) {
								clasNames = new ArrayList<String>();
								clasNames.add(clasName.toLowerCase());
								generalClassesMap.put(clas2Name, clasNames);
							} else {
								clasNames.add(clasName.toLowerCase());
								generalClassesMap.put(clas2Name, clasNames);
							}
						} else if (line.contains("{")) {
							clasName = line.substring(
									line.indexOf("class") + 5,
									line.indexOf("{")).trim();
						} else if (!line.contains("{")
								&& !line.contains("extends")
								&& !line.contains("implements")) {
							String values[] = line.split(" ");
							clasName = values[values.length - 1].trim();
						}
					}
					// extracting public operation names
					if (line.contains(Keywords.Public) && line.contains("(")
							&& line.contains(")") && !line.contains("if(")
							&& !line.contains(";")) {
						String[] words = line.substring(
								line.indexOf(Keywords.Public),
								line.indexOf('(')).split(" ");
						publicItem.add(words[words.length - 1]);
					} else if (line.contains(Keywords.Public)
							&& line.contains("(") && line.contains(")")
							&& !line.contains("if(") && !line.contains("\"")
							&& line.contains(";") && !line.contains("new")) {
						String[] words = line.substring(
								line.indexOf(Keywords.Public),
								line.indexOf('(')).split(" ");
						publicItem.add(words[words.length - 1]);
					}

					// extracting protected operation names
					if (line.contains(Keywords.Protected) && line.contains("(")
							&& line.contains(")") && !line.contains("if(")
							&& !line.contains(";")) {
						String[] words = line.substring(
								line.indexOf(Keywords.Protected),
								line.indexOf('(')).split(" ");
						protectedItem.add(words[words.length - 1]);
					} else if (line.contains(Keywords.Protected)
							&& line.contains("(") && line.contains(")")
							&& !line.contains("if(") && line.contains(";")
							&& !line.contains("\"") && !line.contains("new")) {
						String[] words = line.substring(
								line.indexOf(Keywords.Protected),
								line.indexOf('(')).split(" ");
						protectedItem.add(words[words.length - 1]);
					}

					// extracting attribute names
					// for private attributes
					if (line.contains(Keywords.Private) && line.contains(";")
							&& !line.contains("if") && !line.contains("\"")) {
						if (!line.contains("=") && !line.contains(",")
								&& !line.contains("(") && !line.contains(")")) {
							String[] words = line.split(" ");
							privateItem.add(words[words.length - 1].replace(
									';', ' ').trim());
						} else if (line.contains("=") && line.contains("(")
								&& line.contains(")")) {
							String[] words = line.substring(
									line.indexOf(Keywords.Private),
									line.indexOf("=")).split(" ");
							privateItem.add(words[words.length - 1]);
						} else if (line.contains("=")) {
							String[] words = line.substring(
									line.indexOf(Keywords.Private),
									line.indexOf("=")).split(" ");
							privateItem.add(words[words.length - 1]);
						}
					}
					// for public attributes
					else if (line.contains(Keywords.Public)
							&& line.contains(";") && !line.contains("if")
							&& !line.contains("\"")) {
						if (!line.contains("=") && !line.contains(",")
								&& !line.contains("(") && !line.contains(")")) {
							String[] words = line.split(" ");
							publicItem.add(words[words.length - 1].replace(';',
									' ').trim());
						} else if (line.contains("=") && line.contains("(")
								&& line.contains(")")) {
							String[] words = line.substring(
									line.indexOf(Keywords.Public),
									line.indexOf("=")).split(" ");
							publicItem.add(words[words.length - 1]);
						} else if (line.contains("=")) {
							String[] words = line.substring(
									line.indexOf(Keywords.Public),
									line.indexOf("=")).split(" ");
							publicItem.add(words[words.length - 1]);
						}
					}
					// for protected attributes
					else if (line.contains(Keywords.Protected)
							&& line.contains(";") && !line.contains("if")
							&& !line.contains("\"")) {
						if (!line.contains("=") && !line.contains(",")
								&& !line.contains("(") && !line.contains(")")) {
							String[] words = line.split(" ");
							protectedItem.add(words[words.length - 1].replace(
									';', ' ').trim());
						} else if (line.contains("=") && line.contains("(")
								&& line.contains(")")) {
							String[] words = line.substring(
									line.indexOf(Keywords.Protected),
									line.indexOf("=")).split(" ");
							protectedItem.add(words[words.length - 1]);
						} else if (line.contains("=")) {
							String[] words = line.substring(
									line.indexOf(Keywords.Protected),
									line.indexOf("=")).split(" ");
							protectedItem.add(words[words.length - 1]);
						}
					}
				}

				items.put(Keywords.Private, privateItem);
				items.put(Keywords.Public, publicItem);
				items.put(Keywords.Protected, protectedItem);
			}
			if (clasName != null)
				codeDetails.put(clasName.toLowerCase(), items);
		}

		return codeDetails;
	}

}
