package hr.algebra.bll.service;

import hr.algebra.dal.entity.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();
    Genre findById(int id);
    Genre save(Genre genre);
    void deleteById(int id);
    List<Genre> getByKeyword(String keyword);
}
