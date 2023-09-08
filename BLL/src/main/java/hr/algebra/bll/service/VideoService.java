package hr.algebra.bll.service;

import hr.algebra.dal.entity.Video;

import java.util.List;

public interface VideoService {
    List<Video> findAll();
    Video findById(int id);
    Video save(Video video);
    void deleteById(int id);
}
