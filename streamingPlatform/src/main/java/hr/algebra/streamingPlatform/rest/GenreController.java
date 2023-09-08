package hr.algebra.streamingPlatform.rest;

import hr.algebra.bll.service.GenreServiceImpl;
import hr.algebra.dal.entity.Genre;
import hr.algebra.utils.alreadyExistsErrors.CustomAlreadyExistsException;
import hr.algebra.utils.notFoundErrors.CustomNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class GenreController {
    @Autowired
    private GenreServiceImpl _genreService;

    public GenreController(GenreServiceImpl genreService) {
        _genreService = genreService;
    }

    @Async
    @GetMapping("/genres")
    public CompletableFuture<List<Genre>> findAll(){
        return CompletableFuture.completedFuture(_genreService.findAll());
    }

    @Async
    @GetMapping("/genres/{genreId}")
    public CompletableFuture<Genre> getGenre(@PathVariable int genreId){
        Genre genre = _genreService.findById(genreId);

        return CompletableFuture.completedFuture(genre);
    }

    @Async
    @PostMapping("/genres")
    public CompletableFuture<Genre> addGenre(@Valid @RequestBody Genre genre){

        List<Genre> allDBGenres = _genreService.findAll();

        //this is firstOrDefault in c#
        Optional<Genre> result = allDBGenres.stream()
                .filter(genreRes -> genre.getName().equals(genreRes.getName()))
                .findFirst();

        if(result.isPresent()){
            throw new CustomAlreadyExistsException("Genre with that name already exists: " + ' ' + genre.getName());
        }

        genre.setId(0);
        return CompletableFuture.completedFuture(_genreService.save(genre));
    }

    @Async
    @PutMapping("/genres/{genreId}")
    public CompletableFuture<Genre> updateGenre(@PathVariable int genreId,@Valid @RequestBody Genre genre){
        Genre existingGenre = _genreService.findById(genreId);

        if (existingGenre == null) {
            throw new CustomNotFoundException("Genre not found for id: " + genreId);
        }

        existingGenre.setName(genre.getName());
        existingGenre.setDescription(genre.getDescription());

        return CompletableFuture.completedFuture(_genreService.save(existingGenre));
    }

    @Async
    @DeleteMapping("/genres/{genreId}")
    public CompletableFuture<String> deleteGenre(@PathVariable int genreId){
        Genre genreToDelete = _genreService.findById(genreId);
        if (genreToDelete == null){
            throw new CustomNotFoundException(("Genre id not found - " + genreId));
        }
        _genreService.deleteById(genreId);

        return CompletableFuture.completedFuture("Deleted genre with ID: " + genreId);
    }
}
