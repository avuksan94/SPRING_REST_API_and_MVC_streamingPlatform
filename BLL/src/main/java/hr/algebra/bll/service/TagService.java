package hr.algebra.bll.service;


import hr.algebra.dal.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findAll();
    Tag findById(int id);
    Tag save(Tag tag);
    void deleteById(int id);
    Tag findByName(String name);
    Tag findOrCreate(String name);
    List<Tag> getByKeyword(String keyword);
}
