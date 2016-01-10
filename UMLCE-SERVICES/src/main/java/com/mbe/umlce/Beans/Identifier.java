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
import com.mbe.umlce.dataobject.Errors;
import com.mbe.umlce.dataobject.ModelFile;

@ManagedBean
public class Identifier implements Serializable {

	private static final long serialVersionUID = 8101408561036784885L;
	private Part diagram;
	private String diagramType;
	private ArrayList<Errors> mistakes;
	private ClassDiagramController classIdentifier = new ClassDiagramController();
	private UsecaseDiagramController usecaseIdentifier = new UsecaseDiagramController();
	private DomainModelController domainIdentifier = new DomainModelController();
	private SystemSequenceDiagramController systemSequenceIdentifier=new SystemSequenceDiagramController();
	private SequenceDiagramController sequenceIdentifier = new SequenceDiagramController();
	private ActivityDiagramController activityIdentifier = new ActivityDiagramController();
	private StateMachineDiagramController stateIdentifier = new StateMachineDiagramController();
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
		if (!type.equals("uml")) {
			FacesMessage message = new FacesMessage("File type " + type
					+ " not allowed");
			diagram = Diagram;
			diagramPanel = "";
			context.addMessage(componentToValidate.getId(), message);
			throw new ValidatorException(message);
		}
	}

	public String checkClassDiagramMistakes() throws Exception {
		InputStream stream = diagram.getInputStream();
		mistakes = classIdentifier.identifyMistakes(new ModelFile(stream))
				.getErrors();
		return "true";
	}

	public String checkUsecaseDiagramMistakes() throws Exception {
		InputStream stream = diagram.getInputStream();
		mistakes = usecaseIdentifier.identifyMistakes(new ModelFile(stream))
				.getErrors();
		return "true";

	}

	public String checkDomainModelMistakes() throws Exception {
		InputStream stream = diagram.getInputStream();
		mistakes = domainIdentifier.identifyMistakes(new ModelFile(stream))
				.getErrors();
		return "true";

	}

	public String checkSystemSequenceDiagramMistakes() throws Exception {
		InputStream stream = diagram.getInputStream();
		mistakes = systemSequenceIdentifier.identifyMistakes(new ModelFile(stream))
				.getErrors();
		return "true";

	}
	
	public String checkSequenceDiagramMistakes() throws Exception {
		InputStream stream = diagram.getInputStream();
		mistakes = sequenceIdentifier.identifyMistakes(new ModelFile(stream))
				.getErrors();
		return "true";

	}

	public String checkActivityDiagramMistakes() throws Exception {
		InputStream stream = diagram.getInputStream();
		mistakes = activityIdentifier.identifyMistakes(new ModelFile(stream))
				.getErrors();
		return "true";

	}
	
	public String checkStateMachineDiagramMistakes() throws Exception {
		InputStream stream = diagram.getInputStream();
		ModelFile file = new ModelFile(stream);
		mistakes = stateIdentifier.identifyMistakes(file)
				.getErrors();
		
		return "true";

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

	public Part getDiagram() {
		return diagram;
	}

	public void setDiagram(Part diagram) {
		this.diagram = diagram;
	}

	public ArrayList<Errors> getMistakes() {
		return mistakes;
	}

	public void setMistakes(ArrayList<Errors> mistakes) {
		this.mistakes = mistakes;
	}

	public String getDiagramType() {
		return diagramType;
	}

	public void setDiagramType(String diagramType) {
		this.diagramType = diagramType;
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

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

}