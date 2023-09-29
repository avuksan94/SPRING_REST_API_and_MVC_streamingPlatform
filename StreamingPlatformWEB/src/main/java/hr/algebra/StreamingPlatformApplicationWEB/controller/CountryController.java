package hr.algebra.StreamingPlatformApplicationWEB.controller;

import hr.algebra.bll.service.CountryServiceImpl;
import hr.algebra.dal.entity.Country;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("countries")
public class CountryController {
    private final CountryServiceImpl _countryService;

    public CountryController(CountryServiceImpl countryService) {
        _countryService = countryService;
    }

    @GetMapping("/list")
    public String listCountries(Model theModel) {
        List<Country> countries = _countryService.findAll();

        // add to the spring model
        theModel.addAttribute("countries", countries);

        return "countries/list-countries";
    }

    @GetMapping("/listSearch")
    public String searchCountries(Country Country, Model model, String keyword) {
        List<Country> countries;
        if(keyword != null) {
            countries = _countryService.getByKeyword(keyword);
        }else {
            countries = _countryService.findAll();
        }
        model.addAttribute("countries", countries);

        return "countries/list-countries";
    }

    @GetMapping("/showFormForAddCountry")
    public String showFormForAddCountry(Model theModel){

        //create the model attribute to bind form data
        Country country =  new Country();
        theModel.addAttribute("country", country);

        return "countries/country-form";
    }

    @GetMapping("/showFormForUpdateCountry")
    public String showFormForUpdateCountry(@RequestParam("countryId") int theId, Model theModel){
        //get the country from the service
        Country country = _countryService.findById(theId);

        theModel.addAttribute("country", country);

        //send over to our form
        return "countries/country-form";
    }

    @PostMapping("/save")
    public String saveCountry(@Valid @ModelAttribute("country") Country country, BindingResult bindingResult, Model model) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return "countries/country-form";
        }

        List<Country> allDBCountries = _countryService.findAll();

        Optional<Country> result = allDBCountries.stream()
                .filter(countryRes -> country.getName().equals(countryRes.getName()))
                .findFirst();

        // Check if the country name is already used by another country
        if (result.isPresent() && result.get().getId() != country.getId()) {
            model.addAttribute("errorMessage", "Country with that name already exists!");
            return "countries/country-form";
        }

        _countryService.save(country);

        return "redirect:/countries/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("countryId") int theId){
        //delete the country
        _countryService.deleteById(theId);

        //redirect to the /genres/list
        return "redirect:/countries/list";
    }

}
