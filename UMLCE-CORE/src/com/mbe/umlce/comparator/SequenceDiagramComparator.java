package com.mbe.umlce.comparator;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
import com.mbe.umlce.dataobject.result.Plagiarism;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.uml.UMLModelLoader;

public class SequenceDiagramComparator implements Serializable,Comparator {
	private static final long serialVersionUID = 1L;

	/*
	 * This function checks plagiarism among student's SDs.
	 */

	public Result checkPlagiarism(String[] files, byte[][] model)
			throws Exception {
		Result result = null;
		ArrayList<String> studentsName = new ArrayList<String>();
		ArrayList<SSD> studentModelDetails = new ArrayList<SSD>();
		if (model != null) {
			int i = 0;
			SSD details = new SSD();
			for (String name : files) {
				InputStream file = new BufferedInputStream(
						new ByteArrayInputStream(model[i]));
				details = getRefModelDetails(new ModelFile(file));
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
		ArrayList<SSD> studentModelDetails = new ArrayList<SSD>();

		if (model != null) {
			ZipInputStream directory = new ZipInputStream(
					new BufferedInputStream(model.getModel()));
			ZipEntry entry;

			// if (codeFiles != null) {
			// reading each file in specified directory
			while ((entry = directory.getNextEntry()) != null) {
				if (!entry.isDirectory()) {
					SSD details = new SSD();
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
					details = getRefModelDetails(new ModelFile(file));
					studentModelDetails.add(details);
					studentsName.add(entry.getName().replace(".uml", " ")
							.trim());
				}
			}
			result = plagiarism(studentModelDetails, studentsName);
		}
		return result;
	}

	private Result plagiarism(ArrayList<SSD> studentModelDetails,
			ArrayList<String> studentsName) {
		ArrayList<Plagiarism> plagReport = new ArrayList<Plagiarism>();
		
		int[] maxCount = getMaxCount(studentModelDetails);
		float plagInLifelines = (float) (18.0 / maxCount[0]);
		float plagInOperations = (float) (40.0 / maxCount[1]);
		float plagInMessages = (float) (30.0 / maxCount[2]);
		float plagInColbName = 2.0f;
		/*
		 * Plagiarism checking is started.
		 */
		for (int i = 0; i < studentModelDetails.size(); i++) {
			/*
			 * Get first student's details
			 */
			String collabName_s1 = studentModelDetails.get(i)
					.getCollabrationName();
			ArrayList<String> lifelines_s1 = studentModelDetails.get(i)
					.getLifelines();
			ArrayList<String> operations_s1 = studentModelDetails.get(i)
					.getOperations();
			ArrayList<String> messages_s1 = studentModelDetails.get(i)
					.getMessages();
			/*
			 * Get second student's details and compare to check similarity
			 */
			for (int j = i + 1; j < studentModelDetails.size(); j++) {
				float totalPlagiarism = 0.0f;
				String collabName_s2 = studentModelDetails.get(j)
						.getCollabrationName();
				ArrayList<String> lifelines_s2 = studentModelDetails.get(j)
						.getLifelines();
				ArrayList<String> operations_s2 = studentModelDetails.get(j)
						.getOperations();
				ArrayList<String> messages_s2 = studentModelDetails.get(j)
						.getMessages();

				/*
				 * comparing collaboration names
				 */
				if (collabName_s1 != null) {
					if (collabName_s1.equalsIgnoreCase(collabName_s2)) {
						totalPlagiarism += plagInColbName;
					}
				}
				/*
				 * comparing lifelines
				 */
				totalPlagiarism += compareLists(lifelines_s1, lifelines_s2,
						plagInLifelines);
				/*
				 * comparing operation names
				 */
				totalPlagiarism += compareLists(operations_s1, operations_s2,
						plagInOperations);
				/*
				 * comparing reply messages
				 */
				totalPlagiarism += compareLists(messages_s1, messages_s2,
						plagInMessages);
				/*
				 * Add to plagiarism report
				 */
				if (totalPlagiarism > 30) {
					if (totalPlagiarism > 100) {
						float difference = totalPlagiarism - 100;
						plagReport.add(new Plagiarism(studentsName.get(i),
								studentsName.get(j),
								(totalPlagiarism - difference)));
					} else
						plagReport.add(new Plagiarism(studentsName.get(i),
								studentsName.get(j), totalPlagiarism));
				}
			}
		}

		Result result = new Result();
		result.setPlagiarism(plagReport);
		return result;
	}

	/*
	 * This function receives two arraylists and plagiarism criteria , compare
	 * them and return plagiarism on the bases of similarity.
	 */
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

	/*
	 * This function will input path of model files and return object of SD
	 * model details.
	 */
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

	/*
	 * This function finds maximum number of actors and usecases and return
	 * array containing results.
	 */
	private int[] getMaxCount(ArrayList<SSD> sdList) {
		int[] Max = new int[3];
		int llCount = 1, opCount = 1, msgCount = 1;
		for (SSD sd : sdList) {
			if (sd.getLifelines().size() > llCount) {
				llCount = sd.getLifelines().size();
			}
			if (sd.getMessages().size() > msgCount) {
				msgCount = sd.getMessages().size();
			}
			if (sd.getOperations().size() > opCount) {
				msgCount = sd.getOperations().size();
			}
		}
		Max[0] = llCount;
		Max[1] = opCount;
		Max[2] = msgCount;
		return Max;
	}

}
