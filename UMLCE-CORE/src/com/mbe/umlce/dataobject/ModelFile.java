package com.mbe.umlce.dataobject;

import java.io.InputStream;

public class ModelFile {
	private InputStream model;

	public ModelFile() {
		super();
	}

	public ModelFile(InputStream model) {
		super();
		this.model = model;
	}

	public InputStream getModel() {
		return model;
	}

	public void setModel(InputStream model) {
		this.model = model;
	}

}
