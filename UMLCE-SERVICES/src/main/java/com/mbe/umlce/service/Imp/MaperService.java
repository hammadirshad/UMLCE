package com.mbe.umlce.service.Imp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;

import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import org.codehaus.jackson.map.ObjectMapper;

import com.mbe.umlce.ClassDiagramController;
import com.mbe.umlce.PackageDiagramController;
import com.mbe.umlce.SequenceDiagramController;
import com.mbe.umlce.dataobject.ModelFile;
import com.mbe.umlce.dataobject.result.Result;
import com.mbe.umlce.service.Maper;

@WebService(serviceName = "Maper", portName = "MaperPort", endpointInterface = "com.fyp.umce.service.Maper", targetNamespace = "http://imp.service.umce.fyp.com")
@MTOM
public class MaperService implements Maper, Serializable {

	private static final long serialVersionUID = 1L;
	ClassDiagramController classDiagram = new ClassDiagramController();
	SequenceDiagramController sequenceDiagram = new SequenceDiagramController();
	PackageDiagramController packageDiagram = new PackageDiagramController();

	@Override
	public String mapClassToCode(byte[] diagram, byte[] codeZip)
			throws Exception {

		InputStream file = new ByteArrayInputStream(diagram);
		InputStream codeDir = new ByteArrayInputStream(codeZip);
		Result mistakes = classDiagram.codeMapping(new ModelFile(file),
				new ModelFile(codeDir));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(mistakes);
	}

	@Override
	public String mapSequenceToCode(byte[] diagram, byte[] codeZip)
			throws Exception {

		InputStream file = new ByteArrayInputStream(diagram);
		InputStream codeDir = new ByteArrayInputStream(codeZip);
		Result mistakes = sequenceDiagram.codeMapping(new ModelFile(file),
				new ModelFile(codeDir));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(mistakes);
	}

	@Override
	public String mapPackageToCode(byte[] diagram, byte[] codeZip)
			throws Exception {

		InputStream file = new ByteArrayInputStream(diagram);
		InputStream codeDir = new ByteArrayInputStream(codeZip);
		Result mistakes = packageDiagram.codeMapping(new ModelFile(file),
				new ModelFile(codeDir));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(mistakes);
	}

}
