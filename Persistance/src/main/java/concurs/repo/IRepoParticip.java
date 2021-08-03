package concurs.repo;

import concurs.model.Participant;

public interface IRepoParticip extends IRepository<Integer, Participant> {
    int size();
    void save(Participant entity);
    void delete(Integer id);
    void update(Integer id, Participant entity);
    Iterable<Participant> findByTeam(String name);
}
