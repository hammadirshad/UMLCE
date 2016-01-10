package com.mbe.umlce.service;

import java.rmi.RemoteException;

public interface CheckerPortType extends java.rmi.Remote {
	public String checkActiityDiagramPlagiarism(byte[] arg0)
			throws RemoteException, Exception;

	public String stateMachinelagiarism(byte[] arg0) throws RemoteException,
			Exception;

	public String checkSequenceDiagramPlagiarism(byte[] arg0)
			throws RemoteException, Exception;

	public String checkDomainModelPlagiarism(byte[] arg0)
			throws RemoteException, Exception;

	public String checkUsecaseDiagramPlagiarism(byte[] arg0)
			throws RemoteException, Exception;

	public String checkClassDiagramPlagiarism(byte[] arg0)
			throws RemoteException, Exception;

	public String checkSystemSequenceDiagramPlagiarism(byte[] arg0)
			throws RemoteException, Exception;
}
