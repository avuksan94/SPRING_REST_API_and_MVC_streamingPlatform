package hr.algebra.streamingPlatform.rest;

import hr.algebra.bll.service.CountryServiceImpl;
import hr.algebra.dal.entity.Country;
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
public class CountryController {
    @Autowired
    private CountryServiceImpl _countryService;

    public CountryController(CountryServiceImpl countryService) {
        _countryService = countryService;
    }

    @Async
    @GetMapping("/countries")
    public CompletableFuture<List<Country>> findAll(){
        return CompletableFuture.completedFuture(_countryService.findAll());
    }

    @Async
    @GetMapping("/countries/{countryId}")
    public CompletableFuture<Country> getCountry(@PathVariable int countryId){
        Country country = _countryService.findById(countryId);

        return CompletableFuture.completedFuture(country);
    }

    @Async
    @PostMapping("/countries")
    public CompletableFuture<Country> addCountry(@Valid @RequestBody Country country){

        List<Country> allDBCountries = _countryService.findAll();

        //this is firstOrDefault in c#
        Optional<Country> result = allDBCountries.stream()
                .filter(countryRes -> country.getName().equals(countryRes.getName()))
                .findFirst();

        if(result.isPresent()){
            throw new CustomAlreadyExistsException("Country with that name already exists: " + ' ' + country.getName());
        }

        country.setId(0);
        return CompletableFuture.completedFuture(_countryService.save(country));
    }

    @Async
    @PutMapping("/countries/{countryId}")
    public CompletableFuture<Country> updateCountry(@PathVariable int countryId, @Valid @RequestBody Country country){
        Country existingCountry = _countryService.findById(countryId);

        if (existingCountry == null) {
            throw new CustomNotFoundException("Country not found for id: " + countryId);
        }

        existingCountry.setCode(country.getCode());
        existingCountry.setName(country.getName());

        return CompletableFuture.completedFuture(_countryService.save(existingCountry));
    }

    @Async
    @DeleteMapping("/countries/{countryId}")
    public CompletableFuture<String> deleteCountry(@PathVariable int countryId){
        Country countryToDelete = _countryService.findById(countryId);
        if (countryToDelete == null){
            throw new CustomNotFoundException(("Country id not found - " + countryId));
        }
        _countryService.deleteById(countryId);

        return CompletableFuture.completedFuture("Deleted country with ID: " + countryId);
    }

}
