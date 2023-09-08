package hr.algebra.bll.service;

import hr.algebra.dal.dao.RoleRepository;
import hr.algebra.dal.entity.Role;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository _roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        _roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = _roleRepository.findAll();
        logger.info("Getting all roles: " + roles);
        return roles;
    }

    @Override
    public List<Role> findByUsername(String username) {
        List<Role> allDBRoles = _roleRepository.findAll();
        List<Role> userRoles =  new ArrayList<>();

        for (Role r : allDBRoles){
            if (r.getUsername().equals(username)){
                userRoles.add(r);
            }
        }

        return userRoles;
    }

    @Override
    @Transactional
    public Role save(Role role) {
        return _roleRepository.save(role);
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        _roleRepository.deleteByUserName(username);
    }
}
