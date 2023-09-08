package hr.algebra.dal.dao;

import hr.algebra.dal.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country,Integer> {
    //jpa gives us all the basic CRUD operations that we need
}
