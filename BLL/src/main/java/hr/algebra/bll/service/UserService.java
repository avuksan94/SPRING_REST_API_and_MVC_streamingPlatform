package hr.algebra.bll.service;

import hr.algebra.dal.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findByUsername(String username);
    User findByEmail(String email);
    User save(User user);
    void deleteByUsername(String username);
    List<User> getByKeyword(String keyword);
}
