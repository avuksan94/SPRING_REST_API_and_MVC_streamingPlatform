package hr.algebra.dal.dao;

import hr.algebra.dal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //jpa gives us all the basic CRUD operations that we need
    @Transactional
    @Modifying
    @Query("DELETE FROM User WHERE username = ?1")
    void deleteByUserName(String username);
}
