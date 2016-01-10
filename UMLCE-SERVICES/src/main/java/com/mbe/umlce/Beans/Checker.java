package com.mbe.umlce.Beans;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

import com.mbe.umlce.ActivityDiagramController;
import com.mbe.umlce.ClassDiagramController;
import com.mbe.umlce.DomainModelController;
import com.mbe.umlce.SequenceDiagramController;
import com.mbe.umlce.StateMachineDiagramController;
import com.mbe.umlce.SystemSequenceDiagramController;
import com.mbe.umlce.UsecaseDiagramController;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.Plagiarism;

@ManagedBean
public class Checker implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8293740521968014616L;

	
	private Part diagram;
	private ArrayList<Plagiarism> mistakes;
	private ClassDiagramController classChecker = new ClassDiagramController();
	private UsecaseDiagramController usecaseChecker = new UsecaseDiagramController();
	private ActivityDiagramController activityChecker = new ActivityDiagramController();
	private DomainModelController domainChecker = new DomainModelController();
	private SequenceDiagramController sequenceChecker = new SequenceDiagramController();
	private SystemSequenceDiagramController systemSequenceChecker = new SystemSequenceDiagramController();
	private StateMachineDiagramController stateMachineDiagramChecker = new StateMachineDiagramController();
	private String diagramPanel = "none";
	private double fileSize = 0;

	public void onPageLoad(ComponentSystemEvent event) {
		if (FacesContext.getCurrentInstance().getPartialViewContext()
				.isAjaxRequest()) {
			return;
		}

	}

	public String cencelDiagram() {
		diagramPanel = "none";
		diagram = null;
		fileSize = 0;
		return "true";
	}

	public void diagramValueChange() {
		diagramPanel = "";
	}

	public void diagramValidator(FacesContext context,
			UIComponent componentToValidate, Object value)
			throws ValidatorException {
		Part Diagram = (Part) value;
		String temp = getFilename(Diagram);
		fileSize = Diagram.getSize() / 1000;
		String type = temp.substring(temp.length() - 3);
		if (!type.equals("zip")) {
			FacesMessage message = new FacesMessage("File type " + type
					+ " not allowed");
			diagram = Diagram;
			diagramPanel = "";
			context.addMessage(componentToValidate.getId(), message);
			throw new ValidatorException(message);
		}
	}

	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1)
						.substring(filename.lastIndexOf('\\') + 1);

			}
		}
		return null;
	}

	public String checkClassDiagramPlagiarism() throws Exception {
		InputStream stream = diagram.getInputStream();
		mistakes = classChecker.checkPlagiarism(new ModelFile(stream))
				.getPlagiarism();
		return "true";
	}

	public String checkUsecaseDiagramPlagiarism() throws Exception {
		InputStream stream = diagram.getInputStream();
		mistakes = usecaseChecker.checkPlagiarism(new ModelFile(stream))
				.getPlagiarism();
		return "true";

	}

	public String checkActivityDiagramPlagiarism() throws Exception {
		InputStream stream = diagram.getInputStream();
		mistakes = activityChecker.checkPlagiarism(new ModelFile(stream))
				.getPlagiarism();
		return "true";

	}

	public String checkDomainModelPlagiarism() throws Exception {
		InputStream stream = diagram.getInputStream();
		mistakes = domainChecker.checkPlagiarism(new ModelFile(stream))
				.getPlagiarism();
		return "true";

	}

	public String checkSequenceDiagramPlagiarism() throws Exception {
		InputStream stream = diagram.getInputStream();
		mistakes = sequenceChecker.checkPlagiarism(new ModelFile(stream))
				.getPlagiarism();
		return "true";

	}

	public String checkSystemSequenceDiagramPlagiarism() throws Exception {
		InputStream stream = diagram.getInputStream();
		mistakes = systemSequenceChecker.checkPlagiarism(new ModelFile(stream))
				.getPlagiarism();
		return "true";

	}
	
	public String checkStateMachineDiagramPlagiarism() throws Exception {
		InputStream stream = diagram.getInputStream();
		mistakes = stateMachineDiagramChecker.checkPlagiarism(new ModelFile(stream)).getPlagiarism();
		return "true";

	}
	
	

	public Part getDiagram() {
		return diagram;
	}

	public void setDiagram(Part diagram) {
		this.diagram = diagram;
	}

	public ArrayList<Plagiarism> getMistakes() {
		return mistakes;
	}

	public void setMistakes(ArrayList<Plagiarism> mistakes) {
		this.mistakes = mistakes;
	}

	public String getDiagramPanel() {
		return diagramPanel;
	}

	public void setDiagramPanel(String diagramPanel) {
		this.diagramPanel = diagramPanel;
	}

	public double getFileSize() {
		return fileSize;
	}

	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}

}
