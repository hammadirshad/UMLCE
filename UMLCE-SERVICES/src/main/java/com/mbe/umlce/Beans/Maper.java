package com.mbe.umlce.Beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.Part;

import com.mbe.umlce.ClassDiagramController;
import com.mbe.umlce.PackageDiagramController;
import com.mbe.umlce.SequenceDiagramController;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.MappingErrors;

@ManagedBean
public class Maper implements Serializable {

	private static final long serialVersionUID = 5875674852782082716L;

	private Part diagram;
	private Part codeZip;
	private ArrayList<MappingErrors> mistakes;
	private ClassDiagramController classDiagram = new ClassDiagramController();
	private SequenceDiagramController sequenceDiagram = new SequenceDiagramController();
	private PackageDiagramController packageDiagram=new PackageDiagramController();

	public void onPageLoad(ComponentSystemEvent event) {
		if (FacesContext.getCurrentInstance().getPartialViewContext()
				.isAjaxRequest()) {
			return;
		}

	}

	public String mapClassToSrcCode() throws Exception {
		mistakes = classDiagram.codeMapping(
				new ModelFile(diagram.getInputStream()),
				new ModelFile(codeZip.getInputStream())).getMappingErrors();
		System.out.println(mistakes);
		return "true";
		
	}
	
	public String mapSequenceToSrcCode() throws Exception {
		mistakes = sequenceDiagram.codeMapping(
				new ModelFile(diagram.getInputStream()),
				new ModelFile(codeZip.getInputStream())).getMappingErrors();
		System.out.println(mistakes);
		return "true";
	}
	
	public String mapPackageToSrcCode() throws Exception {
		mistakes = packageDiagram.codeMapping(
				new ModelFile(diagram.getInputStream()),
				new ModelFile(codeZip.getInputStream())).getMappingErrors();
		System.out.println(mistakes);
		return "true";
	}
	
	public Part getDiagram() {
		return diagram;
	}

	public void setDiagram(Part diagram) {
		this.diagram = diagram;
	}

	public Part getCodeZip() {
		return codeZip;
	}

	public void setCodeZip(Part codeZip) {
		this.codeZip = codeZip;
	}

	public ArrayList<MappingErrors> getMistakes() {
		return mistakes;
	}

	public void setMistakes(ArrayList<MappingErrors> mistakes) {
		this.mistakes = mistakes;
	}

}
