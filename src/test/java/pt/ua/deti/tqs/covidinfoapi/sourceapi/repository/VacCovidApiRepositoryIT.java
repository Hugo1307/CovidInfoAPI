package pt.ua.deti.tqs.covidinfoapi.sourceapi.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.DataFetchException;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.NoDataFoundException;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.VacCovidAPI;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.Country;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class VacCovidApiRepositoryIT {

    private VacCovidApiRepository vacCovidApiRepository;

    private VacCovidAPI vacCovidAPI;
    private Country country;

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        vacCovidApiRepository = new VacCovidApiRepository(restTemplate);
        vacCovidAPI = new VacCovidAPI("https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com/api", "dbd92461c3msh03b2a93b4b69037p122734jsn4e1f7a225e85");
        country = new Country("Portugal", "prt");
    }

    @Test
    void getApiInstance() {
        assertEquals(vacCovidApiRepository.getApiInstance(), vacCovidAPI);
    }

    @Test
    void getCountryCovidInfo() {

        JsonObject result = vacCovidApiRepository.getCountryCovidInfo(country);

        assertThatJson(result)
                .isNotNull()
                .isObject()
                .isNotEmpty()
                .containsKeys("id", "rank", "Country", "Continent", "TwoLetterSymbol",
                        "ThreeLetterSymbol", "Infection_Risk", "Case_Fatality_Rate",
                        "Test_Percentage", "Recovery_Proporation", "TotalCases",
                        "NewCases", "TotalDeaths", "NewDeaths", "TotalRecovered",
                        "NewRecovered", "ActiveCases", "TotalTests", "Population",
                        "Deaths_1M_pop", "Serious_Critical", "Tests_1M_Pop");

        assertThatJson(result)
                .isNotNull()
                .isObject()
                .isNotEmpty()
                .containsEntry("Country", country.getName())
                .containsEntry("Continent", "Europe")
                .containsEntry("ThreeLetterSymbol", country.getCode());

    }

    @Test
    void getWorldCovidInfo() {

        JsonObject result = vacCovidApiRepository.getWorldCovidInfo();

        assertThatJson(result)
                .isNotNull()
                .isObject()
                .isNotEmpty()
                .containsKeys("id", "rank", "Country", "Continent", "TwoLetterSymbol",
                        "ThreeLetterSymbol", "Infection_Risk", "Case_Fatality_Rate",
                        "Test_Percentage", "Recovery_Proporation", "TotalCases",
                        "NewCases", "TotalDeaths", "NewDeaths", "TotalRecovered",
                        "NewRecovered", "ActiveCases", "TotalTests", "Population",
                        "Deaths_1M_pop", "Serious_Critical", "Tests_1M_Pop");

        assertThatJson(result)
                .isNotNull()
                .isObject()
                .isNotEmpty()
                .containsEntry("Country", "World")
                .containsEntry("Continent", "All");

    }

    @Test
    void getAllCountries() {

        JsonArray result = vacCovidApiRepository.getAllCountries();

        assertThatJson(result)
                .isNotNull()
                .isArray()
                .contains(
                        json("{\"Country\":\"Austria\", \"ThreeLetterSymbol\":\"aut\"}"),
                        json("{\"Country\":\"Angola\", \"ThreeLetterSymbol\":\"ago\"}")
                );

    }

    @Test
    void getCountryHistoryData() {

        JsonArray result = vacCovidApiRepository.getCountryHistoryData(country);

        assertThatJson(result)
                .isNotNull()
                .isArray()
                .first()
                .isObject()
                .containsKeys("Country", "date", "total_cases", "new_cases", "total_deaths", "new_deaths", "total_tests", "new_tests");

    }

}