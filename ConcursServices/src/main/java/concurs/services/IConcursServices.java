package concurs.services;

import concurs.model.Concurs;
import concurs.model.Inscris;
import concurs.model.Participant;
import concurs.model.User;

public interface IConcursServices {
    void login(User user, IConcursObserver obs) throws ConcursException;
    void logout(User user, IConcursObserver obs) throws ConcursException;
    Concurs[] getConcursByCap(Integer cap) throws ConcursException;
    Participant[] getParticipantByTeam(String team) throws ConcursException;
    Participant[] getAllParticip() throws ConcursException;
    Concurs[] getAllConcurs() throws ConcursException;
    void inscriere(Inscris inscris) throws ConcursException;
}
