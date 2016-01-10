package com.mbe.umlce.service;

import java.rmi.RemoteException;

import com.mbe.umlce.service.Exception;

public interface EvaluatorPortType extends java.rmi.Remote {
	public String getSystemSequenceDiagramDetail(byte[] arg0)
			throws RemoteException, Exception;

	public String getStateMachineDetail(byte[] arg0) throws RemoteException,
			Exception;

	public String getActivityDiagramMistakeList() throws RemoteException,
			Exception;

	public String getDomainModelMistakeList() throws RemoteException, Exception;

	public String getDomainModelDetail(byte[] arg0) throws RemoteException,
			Exception;

	public String getUsecaseDiagramDetail(byte[] arg0) throws RemoteException,
			Exception;

	public String evaluateStateMachine(byte[] arg0, byte[] arg1,
			com.mbe.umlce.dataobject.result.EvaluationCriteria[] arg2,
			double arg3) throws RemoteException, Exception;

	public String getUsecaseMistakeList() throws RemoteException, Exception;

	public String getSequenceDiagramMistakeList() throws RemoteException,
			Exception;

	public String getStateMachineMistakeList() throws RemoteException,
			Exception;

	public String getActivityDiagramDetail(byte[] arg0) throws RemoteException,
			Exception;

	public String getSequenceDiagramDetail(byte[] arg0) throws RemoteException,
			Exception;

	public String getClassDiagramMistakeList() throws RemoteException,
			Exception;

	public String getClassDiagramDetail(byte[] arg0) throws RemoteException,
			Exception;

	public String evaluateClassDiagram(byte[] arg0, byte[] arg1,
			com.mbe.umlce.dataobject.result.EvaluationCriteria[] arg2,
			double arg3) throws RemoteException, Exception;

	public String evaluateSequenceDiagram(byte[] arg0, byte[] arg1,
			com.mbe.umlce.dataobject.result.EvaluationCriteria[] arg2,
			double arg3) throws RemoteException, Exception;

	public String evaluateSystemSequenceDiagram(byte[] arg0, byte[] arg1,
			com.mbe.umlce.dataobject.result.EvaluationCriteria[] arg2,
			double arg3) throws RemoteException, Exception;

	public String evaluateDomainModel(byte[] arg0, byte[] arg1,
			com.mbe.umlce.dataobject.result.EvaluationCriteria[] arg2,
			double arg3) throws RemoteException, Exception;

	public String evaluateUsecaseDiagram(byte[] arg0, byte[] arg1,
			com.mbe.umlce.dataobject.result.EvaluationCriteria[] arg2,
			double arg3) throws RemoteException, Exception;

	public String evaluateActivityDiagram(byte[] arg0, byte[] arg1,
      com.mbe.umlce.dataobject.result.EvaluationCriteria[] arg2,
			double arg3) throws RemoteException, Exception;

	public String getSystemSequenceDiagramMistakeList() throws RemoteException,
			Exception;
}
