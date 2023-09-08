package hr.algebra.bll.service;

import hr.algebra.dal.entity.Image;

import java.util.List;

public interface ImageService {
    List<Image> findAll();
    Image findById(int id);
    Image save(Image image);
    void deleteById(int id);
}
