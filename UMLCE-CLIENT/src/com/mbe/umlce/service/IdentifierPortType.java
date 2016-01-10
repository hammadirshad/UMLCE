package com.mbe.umlce.service;

import java.rmi.RemoteException;

public interface IdentifierPortType extends java.rmi.Remote {
	public String classDiagramMistakes(byte[] arg0) throws RemoteException,
			Exception;

	public String usecaseDiagramMistakes(byte[] arg0) throws RemoteException,
			Exception;

	public String systemSequenceDiagramMistakes(byte[] arg0)
			throws RemoteException, Exception;

	public String domainModelMistakes(byte[] arg0) throws RemoteException,
			Exception;

	public String activityDiagramMistakes(byte[] arg0) throws RemoteException,
			Exception;

	public String stateMachineMistakes(byte[] arg0) throws RemoteException,
			Exception;

	public String sequenceDiagramMistakes(byte[] arg0) throws RemoteException,
			Exception;
}
