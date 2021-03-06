package com.mbe.umlce.uml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.mapping.ecore2xml.Ecore2XMLPackage;
import org.eclipse.emf.mapping.ecore2xml.util.Ecore2XMLResource;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UML212UMLResource;
import org.eclipse.uml2.uml.resource.UML22UMLResource;
import org.eclipse.uml2.uml.resource.UMLResource;

import com.mbe.umlce.dataobject.ModelFile;

public class UMLModelLoader {
	private final ResourceSet RESOURCE_SET;

	public UMLModelLoader() {
		RESOURCE_SET = new ResourceSetImpl();
	}

	/*
	 * This function converts input path to uri and return uri.
	 */
	public String getFileURI(String path) throws Exception {
		File f = new File(path);
		String uri = f.toURI().toString();
		return uri;
	}

	/*
	 * This function inputs uri, load model and return that model.
	 */
	/**
	 * @param uri
	 * @return
	 */

	public Model loadModel(ModelFile file) throws Exception {

		registerPackages(RESOURCE_SET);
		registerResourceFactories();
		String relPath = null;
		try {
			relPath = new File(".").getCanonicalPath();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		URIConverter.URI_MAP
				.put(URI.createURI("platform:/plugin/org.eclipse.uml2.uml/"),
						URI.createURI("jar:file:"
								+ relPath
								+ "/lib/org.eclipse.uml2.uml_3.1.2.v201010261927.jar!/"));
		Resource resource = null;

		Map<String, Object> options = new HashMap<String, Object>();
		options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		options.put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");

		resource = RESOURCE_SET.createResource(URI.createFileURI(System
				.currentTimeMillis() + ".xmi"));
		resource.load(file.getModel(), options);

		Model _model = (Model) EcoreUtil.getObjectByType(
				resource.getContents(), UMLPackage.Literals.MODEL);
		return _model;
	}
	public Package loadPackage(ModelFile file) throws Exception {

		registerPackages(RESOURCE_SET);
		registerResourceFactories();
		String relPath = null;
		try {
			relPath = new File(".").getCanonicalPath();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		URIConverter.URI_MAP
				.put(URI.createURI("platform:/plugin/org.eclipse.uml2.uml/"),
						URI.createURI("jar:file:"
								+ relPath
								+ "/lib/org.eclipse.uml2.uml_3.1.2.v201010261927.jar!/"));
		Resource resource = null;

		Map<String, Object> options = new HashMap<String, Object>();
		options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		options.put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");

		resource = RESOURCE_SET.createResource(URI.createFileURI(System
				.currentTimeMillis() + ".xmi"));
		resource.load(file.getModel(), options);

		Package _package = (Package) EcoreUtil.getObjectByType(
				resource.getContents(), UMLPackage.Literals.PACKAGE);
		return _package;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void registerResourceFactories() {
		Map extensionFactoryMap = Resource.Factory.Registry.INSTANCE
				.getExtensionToFactoryMap();
		extensionFactoryMap.put(UMLResource.FILE_EXTENSION,
				UMLResource.Factory.INSTANCE);
		extensionFactoryMap.put(Ecore2XMLResource.FILE_EXTENSION,
				Ecore2XMLResource.Factory.INSTANCE);
		extensionFactoryMap.put(UML22UMLResource.FILE_EXTENSION,
				UML22UMLResource.Factory.INSTANCE);
		extensionFactoryMap.put(UMLResource.FILE_EXTENSION,
				UML22UMLResource.Factory.INSTANCE);
		extensionFactoryMap.put(UMLResource.FILE_EXTENSION,
				UML22UMLResource.Factory.INSTANCE);
		extensionFactoryMap.put("xml", UMLResource.Factory.INSTANCE);
		extensionFactoryMap.put("xmi", new XMIResourceFactoryImpl());

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void registerPackages(ResourceSet resourceSet) {
		Map packageRegistry = resourceSet.getPackageRegistry();
		packageRegistry.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		packageRegistry.put(Ecore2XMLPackage.eNS_URI,
				Ecore2XMLPackage.eINSTANCE);
		packageRegistry.put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
		packageRegistry.put(UML212UMLResource.UML_METAMODEL_NS_URI,
				UMLPackage.eINSTANCE);
		packageRegistry.put("http://www.eclipse.org/uml2/2.0.0/UML",
				UMLPackage.eINSTANCE);

	}

	@SuppressWarnings("unused")
	private void registerPathmaps(URI uri) {
		URIConverter.URI_MAP.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP),
				uri.appendSegment("libraries").appendSegment(""));
		URIConverter.URI_MAP.put(URI.createURI(UMLResource.METAMODELS_PATHMAP),
				uri.appendSegment("metamodels").appendSegment(""));
		URIConverter.URI_MAP.put(URI.createURI(UMLResource.PROFILES_PATHMAP),
				uri.appendSegment("profiles").appendSegment(""));
	}
}
