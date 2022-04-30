package pt.ua.deti.tqs.covidinfoapi.sourceapi.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.DataFetchException;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.NoDataFoundException;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.Covid19API;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.Country;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;

class Covid19ApiRepositoryIT {

    private Covid19ApiRepository covid19ApiRepository;
    private Covid19API covid19API;
    private Country country;
    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        covid19ApiRepository = new Covid19ApiRepository(restTemplate);
        covid19API = new Covid19API("https://covid-193.p.rapidapi.com", "dbd92461c3msh03b2a93b4b69037p122734jsn4e1f7a225e85");
        country = new Country("Portugal", "prt");
    }

    @Test
    void getApiInstance() {
        assertEquals(covid19ApiRepository.getApiInstance(), covid19API);
    }

    @Test
    void getCountryCovidInfo() {

        JsonObject result = covid19ApiRepository.getCountryCovidInfo(country);

        assertThatJson(result)
                .isObject()
                .containsEntry("continent", "Europe")
                .containsEntry("country", "Portugal");

        assertThatJson(result)
                .isObject()
                .containsKeys("population", "cases", "deaths", "tests", "day", "time");

        assertThatJson(result)
                .inPath("$.cases")
                .isObject()
                .containsKeys("new", "active", "critical", "recovered", "1M_pop", "total");

        assertThatJson(result)
                .inPath("$.deaths")
                .isObject()
                .containsKeys("new", "1M_pop", "total");

        assertThatJson(result)
                .inPath("$.tests")
                .isObject()
                .containsKeys("1M_pop", "total");

    }

    @Test
    void getAllCountries() {

        JsonArray result = covid19ApiRepository.getAllCountries();

        assertThatJson(result)
                .isArray()
                .contains("Austria", "Armenia", "Andorra", "Angola");

    }

}