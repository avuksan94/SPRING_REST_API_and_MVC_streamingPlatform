package hr.algebra.dal.dao;

import hr.algebra.dal.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    //jpa gives us all the basic CRUD operations that we need
}
