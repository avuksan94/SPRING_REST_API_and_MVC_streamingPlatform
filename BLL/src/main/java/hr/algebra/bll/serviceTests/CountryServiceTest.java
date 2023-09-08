package hr.algebra.bll.serviceTests;

import hr.algebra.bll.service.CountryServiceImpl;
import hr.algebra.dal.dao.CountryRepository;
import hr.algebra.dal.entity.Country;
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
import static org.mockito.Mockito.*;

/*
@Mock annotation is used to create a mock implementation of the Repository.
@InjectMocks is used to inject mock dependencies into the service you're testing

https://www.javaguides.net/2022/03/spring-boot-unit-testing-service-layer.html
*/

@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {
    @Mock
    private CountryRepository _countryRepository;
    @InjectMocks
    private CountryServiceImpl _countryService;

    @Test
    public void testFindAll() {
        when(_countryRepository.findAll()).thenReturn(Arrays.asList(new Country(), new Country()));
        List<Country> countries = _countryService.findAll();

        assertEquals(2, countries.size());
    }

    @Test
    public void testFindByIdFound() {
        int id = 1;
        when(_countryRepository.findById(id)).thenReturn(Optional.of(new Country()));
        Country country = _countryService.findById(id);

        assertNotNull(country);
    }

    @Test
    public void testSave() {
        Country country = new Country("JM","Jamaica");
        when(_countryRepository.save(any(Country.class))).thenReturn(country);

        Country savedCountry = _countryService.save(new Country());

        assertNotNull(savedCountry);
    }

    @Test
    public void testDeleteById() {
        int id = 1;

        _countryService.deleteById(id);

        verify(_countryRepository, times(1)).deleteById(id);
    }

}
