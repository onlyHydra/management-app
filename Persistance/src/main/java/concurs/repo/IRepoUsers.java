package concurs.repo;

import concurs.model.User;

public interface IRepoUsers {
    User findOne(String username, String password);
    Iterable<User> findAll();
}
