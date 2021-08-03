package concurs.repo;


import concurs.model.Concurs;

public interface IRepoConcurs extends IRepository<Integer, Concurs> {
    int size();
    void update(Integer id, Concurs entity);
    Iterable<Concurs>findByCap(Integer cap);
}
