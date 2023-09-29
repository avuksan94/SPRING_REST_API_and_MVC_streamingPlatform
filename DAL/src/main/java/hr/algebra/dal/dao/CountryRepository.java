package hr.algebra.dal.dao;

import hr.algebra.dal.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country,Integer> {
    //jpa gives us all the basic CRUD operations that we need

    //Custom query
    @Query(value = "select * from Country c where c.name like %:keyword%", nativeQuery = true)
    List<Country> findByKeyword(@Param("keyword") String keyword);
}
