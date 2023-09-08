package hr.algebra.bll.serviceTests;

import hr.algebra.bll.service.GenreServiceImpl;
import hr.algebra.dal.dao.GenreRepository;
import hr.algebra.dal.entity.Genre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {
    @Mock
    private GenreRepository _genreRepository;
    @InjectMocks
    private GenreServiceImpl _genreService;

    @Test
    public void testFindAll() {
        when(_genreRepository.findAll()).thenReturn(Arrays.asList(new Genre(), new Genre()));
        List<Genre> genres = _genreService.findAll();

        assertEquals(2, genres.size());
    }

    @Test
    public void testFindByIdFound() {
        int id = 1;
        when(_genreRepository.findById(id)).thenReturn(Optional.of(new Genre()));
        Genre genre = _genreService.findById(id);

        assertNotNull(genre);
    }

    @Test
    public void testSave() {
        Genre genre = new Genre("Test", "Test desc 1");
        when(_genreRepository.save(any(Genre.class))).thenReturn(genre);

        Genre savedGenre = _genreService.save(new Genre());

        assertNotNull(savedGenre);
    }

    @Test
    public void testDeleteById() {
        int id = 1;

        _genreService.deleteById(id);

        verify(_genreRepository, times(1)).deleteById(id);
    }
}
