package concurs.services;

import concurs.model.Participant;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IConcursObserver extends Remote {
    void participantInscris(Participant participant) throws ConcursException, RemoteException;
}
