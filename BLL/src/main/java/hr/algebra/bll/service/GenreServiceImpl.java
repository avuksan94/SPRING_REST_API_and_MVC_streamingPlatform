package hr.algebra.bll.service;

import hr.algebra.dal.dao.GenreRepository;
import hr.algebra.dal.entity.Genre;
import hr.algebra.utils.notFoundErrors.CustomNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService{
    private final GenreRepository _genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        _genreRepository = genreRepository;
    }

    @Override
    public List<Genre> findAll() {
        return _genreRepository.findAll();
    }

    @Override
    public Genre findById(int id) {
        Optional<Genre> genreOptional;
        genreOptional = _genreRepository.findById(id);

        if (genreOptional.isEmpty()){
            throw new CustomNotFoundException(("Genre id not found - " + id));
        }

        return genreOptional.get();
    }

    @Override
    @Transactional
    public Genre save(Genre genre) {
        return _genreRepository.save(genre);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        _genreRepository.deleteById(id);
    }

    @Override
    public List<Genre> getByKeyword(String keyword) {
        return _genreRepository.findByKeyword(keyword);
    }
}
