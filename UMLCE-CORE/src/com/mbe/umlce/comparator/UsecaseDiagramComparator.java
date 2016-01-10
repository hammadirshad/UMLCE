package com.mbe.umlce.comparator;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.impl.UseCaseImpl;

import com.mbe.umlce.dataobject.Keywords;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.UD;
import com.mbe.umlce.dataobject.result.Plagiarism;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.uml.UMLModelLoader;

public class UsecaseDiagramComparator implements Serializable,Comparator {
	private static final long serialVersionUID = 1L;
	/*
	 * This function checks plagiarism among student's SDs.
	 */

	public Result checkPlagiarism(ModelFile modelFile) throws IOException {
		ArrayList<Plagiarism> plagReport = new ArrayList<Plagiarism>();
		ArrayList<String> studentsName = new ArrayList<String>();
		ArrayList<UD> studentModelDetails = new ArrayList<UD>();
		if (modelFile != null) {
			ZipInputStream directory = new ZipInputStream(
					new BufferedInputStream(modelFile.getModel()));
			ZipEntry entry;

			// if (codeFiles != null) {
			// reading each file in specified directory
			while ((entry = directory.getNextEntry()) != null) {
				if (!entry.isDirectory()) {
					UD details = new UD();
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
		}
		int[] maxUcsActors = getAvgActorsUsecases(studentModelDetails);
		float plagInActors = 0;
		float plagInUsecases = 0;
		float plagInExtends = 0;
		float plagInIncludes = 0;
		float plagInSysName = 2.0f;

		if (maxUcsActors[0] > 0)
			plagInActors = (float) (40.0 / maxUcsActors[0]);
		if (maxUcsActors[1] > 0)
			plagInUsecases = (float) (40.0 / maxUcsActors[1]);
		if (maxUcsActors[2] > 0)
			plagInExtends = (float) (8.0 / maxUcsActors[2]);
		if (maxUcsActors[3] > 0)
			plagInIncludes = (float) (10.0 / maxUcsActors[3]);
		/*
		 * Plagiarism checking is started.
		 */
		for (int i = 0; i < studentModelDetails.size(); i++) {
			/*
			 * Get first student's details
			 */
			String systemName_s1 = studentModelDetails.get(i).getSystemName();
			int actorsCount_s1 = studentModelDetails.get(i).getActorsCount();
			int usecasesCount_s1 = studentModelDetails.get(i)
					.getUsecasesCount();
			ArrayList<String> actors_s1 = studentModelDetails.get(i)
					.getActors();
			ArrayList<String> usecases_s1 = studentModelDetails.get(i)
					.getUsecases();
			/*
			 * Get second student's details and compare to check similarity
			 */
			for (int j = i + 1; j < studentModelDetails.size(); j++) {
				float totalPlagiarism = 0.0f;
				String systemName_s2 = studentModelDetails.get(j)
						.getSystemName();
				int actorsCount_s2 = studentModelDetails.get(j)
						.getActorsCount();
				int usecasesCount_s2 = studentModelDetails.get(j)
						.getUsecasesCount();
				ArrayList<String> actors_s2 = studentModelDetails.get(j)
						.getActors();
				ArrayList<String> usecases_s2 = studentModelDetails.get(j)
						.getUsecases();
				/*
				 * comparing system names
				 */
				if (systemName_s1 != null) {
					if (systemName_s1.equalsIgnoreCase(systemName_s2)) {
						totalPlagiarism += plagInSysName;
					}
				}
				/*
				 * comparing actor names
				 */
				if (actorsCount_s1 >= actorsCount_s2) {
					for (String actor1 : actors_s1) {
						for (String actor2 : actors_s2) {
							if (actor2.equalsIgnoreCase(actor1)) {
								totalPlagiarism += plagInActors;
							}
						}
					}
				} else {
					for (String actor2 : actors_s2) {
						for (String actor1 : actors_s1) {
							if (actor1.equalsIgnoreCase(actor2)) {
								totalPlagiarism += plagInActors;
							}
						}
					}
				}
				/*
				 * comparing use case names
				 */
				if (usecasesCount_s1 >= usecasesCount_s2) {
					for (String uc1 : usecases_s1) {
						for (String uc2 : usecases_s2) {
							if (uc2.equalsIgnoreCase(uc1)) {
								totalPlagiarism += plagInUsecases;
							}
						}
					}
				} else {
					for (String uc2 : usecases_s2) {
						for (String uc1 : usecases_s1) {
							if (uc1.equalsIgnoreCase(uc2)) {
								totalPlagiarism += plagInUsecases;
							}
						}
					}
				}
				/*
				 * Check include relationships
				 */
				if (studentModelDetails.get(i).getIncludesCount() == studentModelDetails
						.get(j).getIncludesCount())
					totalPlagiarism += plagInIncludes;

				/*
				 * Check extend relationships
				 */
				if (studentModelDetails.get(i).getExtendsCount() == studentModelDetails
						.get(j).getExtendsCount())
					totalPlagiarism += plagInExtends;
				/*
				 * Add to plagiarism report
				 */
				if (totalPlagiarism > 50) {
					if (totalPlagiarism > 100) {
						float difference = totalPlagiarism - 100;
						plagReport.add(new Plagiarism(studentsName.get(i),
								studentsName.get(j),
								(totalPlagiarism - difference)));
						System.out.println(totalPlagiarism + "," + difference);
					} else {
						plagReport.add(new Plagiarism(studentsName.get(i),
								studentsName.get(j), totalPlagiarism));
						System.out.println(totalPlagiarism);
					}
				}
			}
		}
		Result result = new Result();
		result.setPlagiarism(plagReport);
		return result;
	}

	/*
	 * This function finds maximum number of actors and usecases and return
	 * array containing results.
	 */
	private int[] getAvgActorsUsecases(ArrayList<UD> ucsList) {
		int[] Avg = new int[4];
		float actorCount = 0, ucsCount = 0, exCount = 0, inCount = 0;
		for (UD uc : ucsList) {
			if (uc.getActorsCount() > actorCount)
				actorCount = uc.getActorsCount();
			if (uc.getUsecasesCount() > ucsCount)
				ucsCount = uc.getUsecasesCount();
			if (uc.getExtendsCount() > exCount)
				exCount = uc.getExtendsCount();
			if (uc.getIncludesCount() > inCount)
				inCount = uc.getIncludesCount();
		}
		System.out.println();
		Avg[0] = (int) Math.ceil(actorCount / 2);
		Avg[1] = (int) Math.ceil(ucsCount / 2);
		Avg[2] = (int) Math.ceil(exCount / 2);
		Avg[3] = (int) Math.ceil(inCount / 2);
		return Avg;
	}

	/*
	 * This function will input path of model files and return object of UC
	 * model details.
	 */
	private UD getModelDetails(ModelFile file) {
		UD ucDetails = new UD();
		UMLModelLoader umlModel = new UMLModelLoader();
		int actorsCount = 0, ucsCount = 0, extendsCount = 0, includesCount = 0;
		try {
			Model model = umlModel.loadModel(file);
			EList<PackageableElement> members = model.getPackagedElements();
			for (PackageableElement element : members) {
				if (element.eClass() == UMLPackage.Literals.ACTOR) {
					ucDetails.addActor(element.getName());
					actorsCount++;
				} else if (element.eClass() == UMLPackage.Literals.USE_CASE) {
					ucDetails.addUsecase(element.getName());
					ucsCount++;
					if (!element.getOwnedElements().isEmpty()) {
						EList<Element> ucElems = element.getOwnedElements();
						for (Element elem : ucElems) {
							// count include relationships
							if (elem.getClass().getName()
									.contains(Keywords.IncludeImpl))
								includesCount++;
							// count extend relationships
							else if (elem.getClass().getName()
									.contains(Keywords.ExtendImpl))
								extendsCount++;
						}
					}
				}
				if (element.eClass() == UMLPackage.Literals.COMPONENT) {
					ucDetails.setSystemName(element.getName());
					EList<Element> ownedElems = element.getOwnedElements();
					for (Element elem : ownedElems) {
						if (elem.getClass().getName()
								.contains(Keywords.UseCaseImpl)) {
							UseCaseImpl uc = (UseCaseImpl) elem;
							ucDetails.addUsecase(uc.getName());
							ucsCount++;
							// count extend relationships
							if (!uc.getExtends().isEmpty())
								extendsCount++;
							// count include relationships
							if (uc.getIncludes().size() >= 2)
								includesCount++;
						}
					}
				}
			}
			ucDetails.setActorsCount(actorsCount);
			ucDetails.setUsecasesCount(ucsCount);
			ucDetails.setExtendsCount(extendsCount);
			ucDetails.setIncludesCount(includesCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ucDetails;
	}

}
