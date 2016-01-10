package com.mbe.umlce.service;

import java.rmi.RemoteException;

public interface MaperPortType extends java.rmi.Remote {
	public String mapPackageToCode(byte[] arg0, byte[] arg1)
			throws RemoteException, Exception;

	public String mapClassToCode(byte[] arg0, byte[] arg1)
			throws RemoteException, Exception;

	public String mapSequenceToCode(byte[] arg0, byte[] arg1)
			throws RemoteException, Exception;
}
