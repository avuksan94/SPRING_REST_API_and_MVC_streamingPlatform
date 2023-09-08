package hr.algebra.dal.dao;

import hr.algebra.dal.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Role r WHERE r.roleId.username = ?1")
    void deleteByUserName(String username);
}
