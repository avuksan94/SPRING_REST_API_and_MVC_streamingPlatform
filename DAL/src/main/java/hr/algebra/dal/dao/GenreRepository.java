package hr.algebra.dal.dao;

import hr.algebra.dal.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    //jpa gives us all the basic CRUD operations that we need

    //Custom query
    @Query(value = "select * from Genre g where g.name like %:keyword%", nativeQuery = true)
    List<Genre> findByKeyword(@Param("keyword") String keyword);
}
