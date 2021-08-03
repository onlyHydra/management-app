package concurs.server;

import concurs.model.Concurs;
import concurs.model.Inscris;
import concurs.model.Participant;
import concurs.model.User;
import concurs.repo.IRepoConcurs;
import concurs.repo.IRepoParticip;
import concurs.repo.IRepoUsers;
import concurs.services.ConcursException;
import concurs.services.IConcursObserver;
import concurs.services.IConcursServices;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceImpl implements IConcursServices {

    private IRepoParticip repoParticip;
    private IRepoConcurs repoConcurs;
    private IRepoUsers repoUsers;
    private Map<String, IConcursObserver> loggedClients;

    public ServiceImpl(IRepoParticip repoParticip, IRepoConcurs repoConcurs, IRepoUsers repoUsers) {
        this.repoParticip = repoParticip;
        this.repoConcurs = repoConcurs;
        this.repoUsers = repoUsers;
        loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(User user, IConcursObserver obs) throws ConcursException {
        User userR = repoUsers.findOne(user.getUsername(), user.getPassword());
        if(userR != null){
            if(loggedClients.get(userR.getUsername()) != null){
                throw new ConcursException("Utilizator deja conectat");
            }
            loggedClients.put(userR.getUsername(), obs);
        }else {
            throw new ConcursException("Autentificare esuata. Verificati userul si parola");
        }
    }

    @Override
    public synchronized void logout(User user, IConcursObserver obs) throws ConcursException {
        IConcursObserver client = loggedClients.remove(user.getUsername());
        if(client == null){
            throw new ConcursException("Utilizatorul nu este logat");
        }
    }

    @Override
    public synchronized Concurs[] getConcursByCap(Integer cap) throws ConcursException {
        ArrayList<Concurs> result = (ArrayList<Concurs>)repoConcurs.findByCap(cap);
        Concurs[] list = new Concurs[result.size()];
        for (int i = 0; i<result.size(); i++){
            list[i] = result.get(i);
        }
        return list;
    }

    @Override
    public synchronized Participant[] getParticipantByTeam(String team) throws ConcursException{
        ArrayList<Participant> participants = (ArrayList<Participant>)repoParticip.findByTeam(team);
        Participant[] list = new Participant[participants.size()];
        for(int i=0; i<participants.size(); i++){
            list[i] = participants.get(i);
        }
        return list;
    }

    @Override
    public synchronized Participant[] getAllParticip() throws ConcursException{
        ArrayList<Participant> participants = (ArrayList<Participant>)repoParticip.findAll();
        Participant[] list = new Participant[participants.size()];
        for(int i=0; i<participants.size(); i++){
            list[i] = participants.get(i);
        }
        return list;
    }

    @Override
    public synchronized Concurs[] getAllConcurs() throws ConcursException{
        ArrayList<Concurs> result = (ArrayList<Concurs>)repoConcurs.findAll();
        Concurs[] list = new Concurs[result.size()];
        for (int i = 0; i<result.size(); i++){
            list[i] = result.get(i);
        }
        return list;
    }

    @Override
    public synchronized void inscriere(Inscris inscris) throws ConcursException{
        Concurs concurs = inscris.getConcurs();
        Participant participant = inscris.getParticipant();
        if(repoConcurs.findOne(concurs.getId()) != null){
            Concurs con = repoConcurs.findOne(concurs.getId());
            con.setNumar(con.getNumar() + 1);
            repoConcurs.update(con.getId(), con);
            Integer idMax = 0;
            for(Participant p:repoParticip.findAll()){
                if(p.getId() > idMax) idMax = p.getId();
            }
            Participant p = new Participant(idMax+1, participant.getNume(), participant.getEchipa(), participant.getCapacitate(), participant.getIdCursa());
            repoParticip.save(p);
            notifyAllUsers(p);
        }else throw new ConcursException("Eroare inscriere");
    }

    private final int defaultThreadCount = 5;
    private void notifyAllUsers(Participant participant){
        Iterable<User> users = repoUsers.findAll();

        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadCount);
        for(User user:users){
            IConcursObserver client = loggedClients.get(user.getUsername());
            if(client != null){
                executor.execute(()->{
                    try {
                        System.out.println("Notifying: "+user.getUsername());
                        try {
                            client.participantInscris(participant);
                        } catch (RemoteException e) {
                            System.out.println("Remote exception " + e.getMessage());
                        }
                    }catch (ConcursException ex){
                        System.out.println("Error in notifying user " + user.getUsername());
                    }
                });
            }
        }
        executor.shutdown();
    }
}
