package hr.algebra.bll.service;

import hr.algebra.dal.dao.CountryRepository;
import hr.algebra.dal.entity.Country;
import hr.algebra.utils.notFoundErrors.CustomNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService{
    private final CountryRepository _countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        _countryRepository = countryRepository;
    }

    @Override
    public List<Country> findAll() {
        return _countryRepository.findAll();
    }

    @Override
    public Country findById(int id) {
        Optional<Country> countryOptional;
        countryOptional = _countryRepository.findById(id);

        if (countryOptional.isEmpty()){
            throw new CustomNotFoundException(("Country id not found - " + id));
        }

        return countryOptional.get();
    }

    @Override
    @Transactional
    public Country save(Country country) {
        return _countryRepository.save(country);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        _countryRepository.deleteById(id);
    }

    @Override
    public List<Country> getByKeyword(String keyword) {
        return _countryRepository.findByKeyword(keyword);
    }
}
