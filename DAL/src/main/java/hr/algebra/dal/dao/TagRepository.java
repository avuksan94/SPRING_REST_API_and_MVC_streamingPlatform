package hr.algebra.dal.dao;

import hr.algebra.dal.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    //jpa gives us all the basic CRUD operations that we need

    //Custom query
    @Query(value = "select * from Tag t where t.name like %:keyword%", nativeQuery = true)
    List<Tag> findByKeyword(@Param("keyword") String keyword);
}
