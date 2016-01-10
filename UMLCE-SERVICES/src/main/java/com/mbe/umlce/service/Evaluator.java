package com.mbe.umlce.service;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.mbe.umlce.dataobject.result.EvaluationCriteria;

@WebService(name = "EvaluatorPortType", targetNamespace = "http://service.umce.fyp.com")
public interface Evaluator {
	@WebMethod
	public String getClassDiagramMistakeList() throws Exception, java.lang.Exception;

	@WebMethod
	public String getClassDiagramDetail(byte[] file) throws Exception, java.lang.Exception;

	@WebMethod
	public String evaluateClassDiagram(byte[] solution, byte[] studentModel,
			ArrayList<EvaluationCriteria> markers, double totalMarks)
			throws Exception, java.lang.Exception;

	@WebMethod
	public String getDomainModelMistakeList() throws Exception, java.lang.Exception;

	@WebMethod
	public String getDomainModelDetail(byte[] file) throws Exception, java.lang.Exception;

	@WebMethod
	public String evaluateDomainModel(byte[] solution, byte[] studentModel,
			ArrayList<EvaluationCriteria> markers, double totalMarks)
			throws Exception, java.lang.Exception;

	
	public String getActivityDiagramMistakeList() throws Exception, java.lang.Exception;

	@WebMethod
	public String getActivityDiagramDetail(byte[] file) throws Exception, java.lang.Exception;

	@WebMethod
	public String evaluateActivityDiagram(byte[] solution, byte[] studentModel,
			ArrayList<EvaluationCriteria> markers, double totalMarks)
			throws Exception, java.lang.Exception;

	
	@WebMethod
	public String getUsecaseMistakeList() throws Exception, java.lang.Exception, java.lang.Exception;

	@WebMethod
	public String getUsecaseDiagramDetail(byte[] file) throws Exception, java.lang.Exception;

	@WebMethod
	public String evaluateUsecaseDiagram(byte[] solution, byte[] studentModel,
			ArrayList<EvaluationCriteria> markers, double totalMarks)
			throws Exception, java.lang.Exception;

	@WebMethod
	public String getSequenceDiagramMistakeList() throws Exception, java.lang.Exception;

	@WebMethod
	public String getSequenceDiagramDetail(byte[] file) throws Exception, java.lang.Exception;

	@WebMethod
	public String evaluateSequenceDiagram(byte[] solution, byte[] studentModel,
			ArrayList<EvaluationCriteria> markers, double totalMarks)
			throws Exception, java.lang.Exception;

	@WebMethod
	public String getSystemSequenceDiagramMistakeList() throws Exception, java.lang.Exception;

	@WebMethod
	public String getSystemSequenceDiagramDetail(byte[] file) throws Exception, java.lang.Exception;

	@WebMethod
	public String evaluateSystemSequenceDiagram(byte[] solution,
			byte[] studentModel, ArrayList<EvaluationCriteria> markers,
			double totalMarks) throws Exception, java.lang.Exception;

	@WebMethod
	public String getStateMachineMistakeList() throws Exception, java.lang.Exception;

	@WebMethod
	public String getStateMachineDetail(byte[] file) throws Exception, java.lang.Exception;

	@WebMethod
	public String evaluateStateMachine(byte[] solution,
			byte[] studentModel, ArrayList<EvaluationCriteria> markers,
			double totalMarks) throws Exception, java.lang.Exception;

}
