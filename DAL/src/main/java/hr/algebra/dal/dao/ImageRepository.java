package hr.algebra.dal.dao;

import hr.algebra.dal.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    //jpa gives us all the basic CRUD operations that we need
}
