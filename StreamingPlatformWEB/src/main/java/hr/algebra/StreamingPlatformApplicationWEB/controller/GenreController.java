package hr.algebra.StreamingPlatformApplicationWEB.controller;

import hr.algebra.bll.service.GenreServiceImpl;
import hr.algebra.dal.entity.Genre;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("genres")
public class GenreController {
    private GenreServiceImpl _genreService;

    public GenreController(GenreServiceImpl _genreService) {
        this._genreService = _genreService;
    }

    @GetMapping("/list")
    public String listGenres(Model theModel) {

        List<Genre> genres = _genreService.findAll();

        // add to the spring model
        theModel.addAttribute("genres", genres);

        return "genres/list-genres";
    }

    @GetMapping("/listSearch")
    public String searchGenres(Genre Genre, Model model, String keyword) {
        List<Genre> genres;
        if(keyword != null) {
            genres = _genreService.getByKeyword(keyword);
        }else {
            genres = _genreService.findAll();
        }
        model.addAttribute("genres", genres);

        return "genres/list-genres";
    }

    @GetMapping("/showFormForAddGenre")
    public String showFormForAddGenre(Model theModel){

        //create the model attribute to bind form data
        Genre genre =  new Genre();
        theModel.addAttribute("genre", genre);

        return "genres/genre-form";
    }

    @GetMapping("/showFormForUpdateGenre")
    public String showFormForUpdateGenre(@RequestParam("genreId") int theId, Model theModel){
        //get the genre from the service
        Genre genre = _genreService.findById(theId);

        theModel.addAttribute("genre", genre);

        //send over to our form
        return "genres/genre-form";
    }

    @PostMapping("/save")
    public String saveGenre(@Valid @ModelAttribute("genre") Genre genre, BindingResult bindingResult, Model model) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return "genres/genre-form";
        }

        List<Genre> allDBGenres = _genreService.findAll();

        Optional<Genre> result = allDBGenres.stream()
                .filter(genreRes -> genre.getName().equals(genreRes.getName()))
                .findFirst();

        if(result.isPresent()){
            model.addAttribute("errorMessage", "Genre with that name already exists!");
            return "genres/genre-form";
        }

        _genreService.save(genre);

        return "redirect:/genres/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("genreId") int theId){
        //delete the genre
        _genreService.deleteById(theId);

        //redirect to the /genres/list
        return "redirect:/genres/list";
    }
}
