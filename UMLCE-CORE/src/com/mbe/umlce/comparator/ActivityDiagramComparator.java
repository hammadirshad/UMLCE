package com.mbe.umlce.comparator;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
import com.mbe.umlce.dataobject.result.Plagiarism;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.uml.UMLModelLoader;

public class ActivityDiagramComparator implements Serializable,Comparator {
	private static final long serialVersionUID = 1L;

	/*
	 * This function checks plagiarism among student's SDs.
	 */

	public Result checkPlagiarism(String[] files, byte[][] model)
			throws IOException {
		Result result = null;
		ArrayList<String> studentsName = new ArrayList<String>();
		ArrayList<AD> studentModelDetails = new ArrayList<AD>();
		if (model != null) {
			int i = 0;
			AD details = new AD();
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

	public Result checkPlagiarism(ModelFile modelFile) throws Exception {
		Result result = null;

		ArrayList<String> studentsName = new ArrayList<String>();
		ArrayList<AD> studentModelDetails = new ArrayList<AD>();
		if (modelFile != null) {
			ZipInputStream directory = new ZipInputStream(
					new BufferedInputStream(modelFile.getModel()));
			ZipEntry entry;
			// if (codeFiles != null) {
			while ((entry = directory.getNextEntry()) != null) {
				if (!entry.isDirectory()) {
					AD details = new AD();
					// make list of students model object details

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

	private Result plagiarism(ArrayList<AD> studentModelDetails,
			ArrayList<String> studentsName) {
		ArrayList<Plagiarism> plagReport = new ArrayList<Plagiarism>();

		int[] maxCount = getMaxCount(studentModelDetails);
		float[] plagvalues = getPlagirism(maxCount);

		for (int i = 0; i < studentModelDetails.size(); i++) {
			for (int j = i + 1; j < studentModelDetails.size(); j++) {
				float totalPlagiarism = 0;
				if (studentModelDetails
						.get(i)
						.getActivityName()
						.equalsIgnoreCase(
								studentModelDetails.get(j).getActivityName())) {
					totalPlagiarism = totalPlagiarism + plagvalues[6];
				}

				totalPlagiarism = totalPlagiarism
						+ compareLists(studentModelDetails.get(i)
								.getDecisions(), studentModelDetails.get(j)
								.getDecisions(), plagvalues[0]);
				totalPlagiarism = totalPlagiarism
						+ compareLists(studentModelDetails.get(i).getMerges(),
								studentModelDetails.get(j).getMerges(),
								plagvalues[1]);
				totalPlagiarism = totalPlagiarism
						+ compareLists(studentModelDetails.get(i).getForks(),
								studentModelDetails.get(j).getForks(),
								plagvalues[2]);
				totalPlagiarism = totalPlagiarism
						+ compareLists(studentModelDetails.get(i).getJoins(),
								studentModelDetails.get(j).getJoins(),
								plagvalues[3]);
				totalPlagiarism = totalPlagiarism
						+ compareLists(studentModelDetails.get(i)
								.getOpaqueActions(), studentModelDetails.get(j)
								.getOpaqueActions(), plagvalues[4]);
				totalPlagiarism = totalPlagiarism
						+ compareLists(studentModelDetails.get(i).getEdges(),
								studentModelDetails.get(j).getEdges(),
								plagvalues[5]);
				DecimalFormat df = new DecimalFormat("###.#");
				plagReport.add(new Plagiarism(studentsName.get(i), studentsName
						.get(j), Float.parseFloat(df
						.format((double) totalPlagiarism))));
			}

		}
		Result result = new Result();
		result.setPlagiarism(plagReport);
		return result;
	}

	private float[] getPlagirism(int[] maxCount) {

		float org = 0;
		float[] plagPercentage = { 10, 10, 5, 5, 40, 25, 5 };
		for (int count = 0; count < 7; count++) {
			if (maxCount[count] != 0) {
				org = org + plagPercentage[count];
			}
		}
		for (int count = 0; count < 7; count++) {
			if (maxCount[count] != 0) {
				float temp = plagPercentage[count];
				plagPercentage[count] = (((temp) / org) * 100)
						/ maxCount[count];
			} else {
				plagPercentage[count] = 0;
			}
		}
		return plagPercentage;
	}

	/*
	 * This function will input path of model files and return object of AD
	 * model details.
	 */
	private AD getModelDetails(ModelFile modelFile) {
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

	private int[] getMaxCount(ArrayList<AD> adList) {
		int[] Max = new int[7];
		for (int count = 0; count < 6; count++) {
			Max[count] = 0;
		}
		for (AD ad : adList) {
			if (ad.getDecisions().size() > Max[0]) {
				Max[0] = ad.getDecisions().size();
			}
			if (ad.getMerges().size() > Max[1]) {
				Max[1] = ad.getMerges().size();
			}
			if (ad.getForks().size() > Max[2]) {
				Max[2] = ad.getForks().size();
			}
			if (ad.getJoins().size() > Max[3]) {
				Max[3] = ad.getJoins().size();
			}
			if (ad.getOpaqueActions().size() > Max[4]) {
				Max[4] = ad.getOpaqueActions().size();
			}
			if (ad.getEdges().size() > Max[5]) {
				Max[5] = ad.getEdges().size();
			}
			if (ad.getActivityName() != null) {
				Max[6] = 1;
			}
		}
		return Max;
	}

	private float compareLists(ArrayList<String> list_s1,
			ArrayList<String> list_s2, float plag) {
		float totalPlag = 0.0f;
		if (list_s1.size() >= list_s2.size()) {
			for (String s1 : list_s1) {
				for (String s2 : list_s2) {
					if (s1 != null && s2 != null)
						if (s2.equalsIgnoreCase(s1)) {
							totalPlag += plag;
						}
				}
			}
		} else {
			for (String s2 : list_s2) {
				for (String s1 : list_s1) {
					if (s1 != null && s2 != null)
						if (s1.equalsIgnoreCase(s2)) {
							totalPlag += plag;
						}
				}
			}
		}
		return totalPlag;
	}

}
