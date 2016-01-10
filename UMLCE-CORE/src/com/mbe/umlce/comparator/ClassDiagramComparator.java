package com.mbe.umlce.comparator;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Relationship;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.impl.ClassImpl;

import com.mbe.umlce.dataobject.CD;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.Plagiarism;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.uml.UMLModelLoader;

public class ClassDiagramComparator implements Serializable,Comparator {
	private static final long serialVersionUID = 1L;

	/*
	 * This function checks plagiarism among student's SDs.
	 */

	public Result checkPlagiarism(String[] files, byte[][] model)
			throws IOException {
		Result result = null;
		ArrayList<String> studentsName = new ArrayList<String>();
		ArrayList<ArrayList<CD>> studentModelDetails = new ArrayList<ArrayList<CD>>();
		if (model != null) {
			int i = 0;
			ArrayList<CD> details = new ArrayList<CD>();
			for (String name : files) {
				InputStream file = new BufferedInputStream(
						new ByteArrayInputStream(model[i]));
				details = getModelDetails(new ModelFile(file));
				studentModelDetails.add(details);
				studentsName.add(name);
			}
			result = plagiarism(studentModelDetails, studentsName);
		}
		return result;
	}

	public Result checkPlagiarism(ModelFile model) throws Exception {
		Result result = null;
		ArrayList<String> studentsName = new ArrayList<String>();
		ArrayList<ArrayList<CD>> studentModelDetails = new ArrayList<ArrayList<CD>>();

		if (model != null) {
			ZipInputStream directory = new ZipInputStream(
					new BufferedInputStream(model.getModel()));
			ZipEntry entry;

			// if (codeFiles != null) {
			// reading each file in specified directory
			while ((entry = directory.getNextEntry()) != null) {
				if (!entry.isDirectory()) {
					ArrayList<CD> details = new ArrayList<CD>();
					// make list of students model object details

					System.out.println(entry.getName());
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int len;
					while ((len = directory.read(buffer)) > -1) {
						baos.write(buffer, 0, len);
					}
					baos.flush();
					InputStream file = new BufferedInputStream(
							new ByteArrayInputStream(baos.toByteArray()));

					details = getModelDetails(new ModelFile(file));
					studentModelDetails.add(details);
					studentsName.add(entry.getName().replace(".uml", " ")
							.trim());
				}
			}
			result = plagiarism(studentModelDetails, studentsName);
		}
		return result;
	}

	private Result plagiarism(ArrayList<ArrayList<CD>> studentModelDetails,
			ArrayList<String> studentsName) {
		ArrayList<Plagiarism> plagReport = new ArrayList<Plagiarism>();

		int[] maxCounts = getMaxCount(studentModelDetails);
		/*
		 * Plagiarism checking is started.
		 */
		for (int i = 0; i < studentModelDetails.size(); i++) {
			/*
			 * Get first student's details
			 */
			ArrayList<CD> student_1 = studentModelDetails.get(i);
			/*
			 * Get second student's details and compare to check similarity
			 */
			for (int j = i + 1; j < studentModelDetails.size(); j++) {
				float totalPalgiarism = 0.0f;
				ArrayList<CD> student_2 = studentModelDetails.get(j);
				if (student_1.size() >= student_2.size()) {
					for (CD s1 : student_1) {
						for (CD s2 : student_2) {
							if (s1.getClassName().equalsIgnoreCase(
									s2.getClassName())) {
								if (s1 != null && s1 != null
										&& maxCounts != null)
									totalPalgiarism += compareAttributesOperations(
											s1, s2, maxCounts);
							}
						}
					}
				} else {
					for (CD s2 : student_2) {
						for (CD s1 : student_1) {
							if (s2.getClassName().equalsIgnoreCase(
									s1.getClassName())) {
								totalPalgiarism += compareAttributesOperations(
										s1, s2, maxCounts);
							}
						}
					}
				}
				/*
				 * Add to plagiarism report
				 */
				if (totalPalgiarism > 40) {
					if (totalPalgiarism > 100) {
						float difference = totalPalgiarism - 100;
						plagReport.add(new Plagiarism(studentsName.get(i),
								studentsName.get(j),
								(totalPalgiarism - difference)));
					} else
						plagReport.add(new Plagiarism(studentsName.get(i),
								studentsName.get(j), totalPalgiarism));
				}
			}
		}

		// }
		Result result = new Result();
		result.setPlagiarism(plagReport);
		return result;
	}

	/*
	 * This function reads model, store its details in ArrayList and return.
	 */
	private ArrayList<CD> getModelDetails(ModelFile file) {
		ArrayList<CD> allClasses = new ArrayList<CD>();
		UMLModelLoader umlModel = new UMLModelLoader();
		try {

			Model model = umlModel.loadModel(file);
			EList<PackageableElement> members = model.getPackagedElements();
			for (PackageableElement element : members) {
				if (element.eClass() == UMLPackage.Literals.CLASS) {
					CD details = new CD();
					Class clas = (Class) element;
					String clasName = clas.getName();
					// add class name
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
					allClasses.add(details);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return allClasses;
	}

	/*
	 * This function determines maximum number of classes, attributes and
	 * operations.
	 */
	private int[] getMaxCount(ArrayList<ArrayList<CD>> studentModels) {
		int[] maxCount = new int[3];
		int classCount = 1, attrCount = 1, operCount = 1;
		for (ArrayList<CD> detailList : studentModels) {
			if (detailList.size() > classCount)
				classCount = detailList.size();
			for (CD detail : detailList) {
				if (detail.getAttributes().size() > attrCount)
					attrCount = detail.getAttributes().size();
				if (detail.getOperations().size() > operCount)
					operCount = detail.getOperations().size();
			}
		}
		maxCount[0] = classCount;
		maxCount[1] = attrCount;
		maxCount[2] = operCount;
		return maxCount;
	}

	/*
	 * This function will compare class attributes and operations of two
	 * students and return similarity percentage.
	 */
	private float compareAttributesOperations(CD s1, CD s2, int[] maxCounts) {
		ArrayList<String> attributes_s1 = s1.getAttributes();
		ArrayList<String> operations_s1 = s1.getOperations();
		ArrayList<String> attributes_s2 = s2.getAttributes();
		ArrayList<String> operations_s2 = s2.getOperations();
		float totalPalgiarism = 0.0f;
		float plagInClass = (float) (35.0 / maxCounts[0]);
		float plagInAttributes = (float) (32.0 / maxCounts[1]);
		float plagInOperations = (float) (33.0 / maxCounts[2]);
		totalPalgiarism += plagInClass;
		/*
		 * compare class attributes
		 */
		if (attributes_s2.size() > 0)
			if (attributes_s1.size() >= attributes_s2.size()) {
				for (String attr_1 : attributes_s1) {
					for (String attr_2 : attributes_s2) {
						if (attr_1 != null && attr_2 != null)
							try {

								if (attr_1.equalsIgnoreCase(attr_2)) {
									totalPalgiarism += plagInAttributes;
								}
							} catch (Exception e) {
								System.out.println(attr_1 + "," + attr_2);
								System.out.println("" + e);
							}

					}
				}
			} else {
				for (String attr_2 : attributes_s2) {
					for (String attr_1 : attributes_s1) {
						if (attr_2.equalsIgnoreCase(attr_1)) {
							totalPalgiarism += plagInAttributes;
						}
					}
				}
			}
		/*
		 * compare class operations
		 */
		if (operations_s1.size() >= operations_s2.size()) {
			for (String oper_1 : operations_s1) {
				for (String oper_2 : operations_s2) {
					if (oper_1.equalsIgnoreCase(oper_2)) {
						totalPalgiarism += plagInOperations;
					}
				}
			}
		} else {
			for (String oper_2 : operations_s2) {
				for (String oper_1 : operations_s1) {
					if (oper_2.equalsIgnoreCase(oper_1)) {
						totalPalgiarism += plagInOperations;
					}
				}
			}
		}
		return totalPalgiarism;
	}

}
