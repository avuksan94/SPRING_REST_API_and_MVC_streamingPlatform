package hr.algebra.bll.service;

import hr.algebra.dal.entity.Country;

import java.util.List;

public interface CountryService {
    List<Country> findAll();
    Country findById(int id);
    Country save(Country country);
    void deleteById(int id);
}
