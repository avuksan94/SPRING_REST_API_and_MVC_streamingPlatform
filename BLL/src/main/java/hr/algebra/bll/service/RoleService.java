package hr.algebra.bll.service;

import hr.algebra.dal.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    List<Role> findByUsername(String username);
    Role save(Role role);
    void deleteByUsername(String username);
}
