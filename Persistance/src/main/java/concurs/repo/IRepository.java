package concurs.repo;

public interface IRepository<ID, T> {
    T findOne(ID id);
    Iterable<T> findAll();
}