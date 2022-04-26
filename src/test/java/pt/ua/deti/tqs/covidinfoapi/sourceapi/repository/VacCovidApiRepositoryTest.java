package pt.ua.deti.tqs.covidinfoapi.sourceapi.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.DataFetchException;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.NoDataFoundException;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.VacCovidAPI;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.Country;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.junit.jupiter.api.Assertions.*;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacCovidApiRepositoryTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private VacCovidApiRepository vacCovidApiRepository;

    private VacCovidAPI vacCovidAPI;
    private Country country;
    private Country nonExistentCountry;

    @BeforeEach
    void setUp() {
        vacCovidAPI = new VacCovidAPI("https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com/api", "dbd92461c3msh03b2a93b4b69037p122734jsn4e1f7a225e85");
        country = new Country("Portugal", "prt");
        nonExistentCountry = new Country("Aaushd", "yyy");

    }

    @Test
    void getApiInstance() {
        assertEquals(vacCovidApiRepository.getApiInstance(), vacCovidAPI);
    }

    @Test
    void getCountryCovidInfo() {

        doReturn(new ResponseEntity<>("[\n" +
                "    {\n" +
                "        \"id\": \"9fb86a08-d197-4fcb-b08a-bebbe5f6c642\",\n" +
                "        \"rank\": 29,\n" +
                "        \"Country\": \"Portugal\",\n" +
                "        \"Continent\": \"Europe\",\n" +
                "        \"TwoLetterSymbol\": \"pt\",\n" +
                "        \"ThreeLetterSymbol\": \"prt\",\n" +
                "        \"Infection_Risk\": 37.38,\n" +
                "        \"Case_Fatality_Rate\": 0.58,\n" +
                "        \"Test_Percentage\": 404.6,\n" +
                "        \"Recovery_Proporation\": 0,\n" +
                "        \"TotalCases\": 3791744,\n" +
                "        \"NewCases\": 0,\n" +
                "        \"TotalDeaths\": 22162,\n" +
                "        \"NewDeaths\": 0,\n" +
                "        \"TotalRecovered\": \"3769582\",\n" +
                "        \"NewRecovered\": 0,\n" +
                "        \"ActiveCases\": 0,\n" +
                "        \"TotalTests\": \"41038444\",\n" +
                "        \"Population\": \"10142964\",\n" +
                "        \"one_Caseevery_X_ppl\": 3,\n" +
                "        \"one_Deathevery_X_ppl\": 458,\n" +
                "        \"one_Testevery_X_ppl\": 0,\n" +
                "        \"Deaths_1M_pop\": 2185,\n" +
                "        \"Serious_Critical\": 61,\n" +
                "        \"Tests_1M_Pop\": 4046001,\n" +
                "        \"TotCases_1M_Pop\": 373830\n" +
                "    }\n" +
                "]", HttpStatus.OK))
                .when(restTemplate).exchange(eq(String.format(VacCovidApiRepository.API_ENDPOINTS.COUNTRY_INFO.getUrl(), country.getName(), country.getCode())), eq(HttpMethod.GET), any(), eq(String.class));

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

        verify(restTemplate, times(1)).exchange(eq(String.format(VacCovidApiRepository.API_ENDPOINTS.COUNTRY_INFO.getUrl(), country.getName(), country.getCode())), eq(HttpMethod.GET), any(), eq(String.class));

    }

    @Test
    void getCountryCovidInfo_emptyBody() {

        doReturn(new ResponseEntity<>(null, HttpStatus.OK))
                .when(restTemplate).exchange(eq(String.format(VacCovidApiRepository.API_ENDPOINTS.COUNTRY_INFO.getUrl(), country.getName(), country.getCode())), eq(HttpMethod.GET), any(), eq(String.class));

        assertThrows(DataFetchException.class, () -> vacCovidApiRepository.getCountryCovidInfo(country));

        verify(restTemplate, times(1)).exchange(eq(String.format(VacCovidApiRepository.API_ENDPOINTS.COUNTRY_INFO.getUrl(), country.getName(), country.getCode())), eq(HttpMethod.GET), any(), eq(String.class));

    }

    @Test
    void getCountryCovidInfo_non200Code() {

        doReturn(new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR))
                .when(restTemplate).exchange(eq(String.format(VacCovidApiRepository.API_ENDPOINTS.COUNTRY_INFO.getUrl(), country.getName(), country.getCode())), eq(HttpMethod.GET), any(), eq(String.class));

        assertThrows(DataFetchException.class, () -> vacCovidApiRepository.getCountryCovidInfo(country));

        verify(restTemplate, times(1)).exchange(eq(String.format(VacCovidApiRepository.API_ENDPOINTS.COUNTRY_INFO.getUrl(), country.getName(), country.getCode())), eq(HttpMethod.GET), any(), eq(String.class));

    }

    @Test
    void getCountryCovidInfo_nonExistentCountry() {

        doReturn(new ResponseEntity<>("[]", HttpStatus.OK))
                .when(restTemplate).exchange(eq(String.format(VacCovidApiRepository.API_ENDPOINTS.COUNTRY_INFO.getUrl(), nonExistentCountry.getName(), nonExistentCountry.getCode())), eq(HttpMethod.GET), any(), eq(String.class));

        assertThrows(NoDataFoundException.class, () -> vacCovidApiRepository.getCountryCovidInfo(nonExistentCountry));

        verify(restTemplate, times(1)).exchange(eq(String.format(VacCovidApiRepository.API_ENDPOINTS.COUNTRY_INFO.getUrl(), nonExistentCountry.getName(), nonExistentCountry.getCode())), eq(HttpMethod.GET), any(), eq(String.class));

    }


    @Test
    void getWorldCovidInfo() {

        doReturn(new ResponseEntity<>("[\n" +
                "    {\n" +
                "        \"id\": \"40f44943-8413-4a31-a4df-deee8111f85f\",\n" +
                "        \"rank\": 0,\n" +
                "        \"Country\": \"World\",\n" +
                "        \"Continent\": \"All\",\n" +
                "        \"TwoLetterSymbol\": null,\n" +
                "        \"ThreeLetterSymbol\": null,\n" +
                "        \"Infection_Risk\": 0,\n" +
                "        \"Case_Fatality_Rate\": 1.22,\n" +
                "        \"Test_Percentage\": 0,\n" +
                "        \"Recovery_Proporation\": 90.79,\n" +
                "        \"TotalCases\": 509830607,\n" +
                "        \"NewCases\": 344133,\n" +
                "        \"TotalDeaths\": 6244529,\n" +
                "        \"NewDeaths\": 1552,\n" +
                "        \"TotalRecovered\": \"462887767\",\n" +
                "        \"NewRecovered\": 838563,\n" +
                "        \"ActiveCases\": 40698311,\n" +
                "        \"TotalTests\": \"0\",\n" +
                "        \"Population\": \"0\",\n" +
                "        \"one_Caseevery_X_ppl\": 0,\n" +
                "        \"one_Deathevery_X_ppl\": 0,\n" +
                "        \"one_Testevery_X_ppl\": 0,\n" +
                "        \"Deaths_1M_pop\": 801.1,\n" +
                "        \"Serious_Critical\": 42412,\n" +
                "        \"Tests_1M_Pop\": 0,\n" +
                "        \"TotCases_1M_Pop\": 65407\n" +
                "    }\n" +
                "]", HttpStatus.OK))
                .when(restTemplate).exchange(eq(VacCovidApiRepository.API_ENDPOINTS.WORLD_INFO.getUrl()), eq(HttpMethod.GET), any(), eq(String.class));

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

        verify(restTemplate, times(1)).exchange(eq(VacCovidApiRepository.API_ENDPOINTS.WORLD_INFO.getUrl()), eq(HttpMethod.GET), any(), eq(String.class));

    }

    @Test
    void getWorldCovidInfo_emptyBody() {

        doReturn(new ResponseEntity<>(null, HttpStatus.OK))
                .when(restTemplate).exchange(eq(VacCovidApiRepository.API_ENDPOINTS.WORLD_INFO.getUrl()), eq(HttpMethod.GET), any(), eq(String.class));

        assertThrows(DataFetchException.class, () -> vacCovidApiRepository.getWorldCovidInfo());

        verify(restTemplate, times(1)).exchange(eq(VacCovidApiRepository.API_ENDPOINTS.WORLD_INFO.getUrl()), eq(HttpMethod.GET), any(), eq(String.class));

    }

    @Test
    void getWorldCovidInfo_non200Code() {

        doReturn(new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR))
                .when(restTemplate).exchange(eq(VacCovidApiRepository.API_ENDPOINTS.WORLD_INFO.getUrl()), eq(HttpMethod.GET), any(), eq(String.class));

        assertThrows(DataFetchException.class, () -> vacCovidApiRepository.getWorldCovidInfo());

        verify(restTemplate, times(1)).exchange(eq(VacCovidApiRepository.API_ENDPOINTS.WORLD_INFO.getUrl()), eq(HttpMethod.GET), any(), eq(String.class));

    }

    @Test
    void getAllCountries() {

        when(restTemplate.exchange(eq(VacCovidApiRepository.API_ENDPOINTS.ALL_COUNTRIES.getUrl()), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>("[\n" +
                        "    {\n" +
                        "        \"Country\": \"Afghanistan\",\n" +
                        "        \"ThreeLetterSymbol\": \"afg\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"Country\": \"Albania\",\n" +
                        "        \"ThreeLetterSymbol\": \"alb\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"Country\": \"Algeria\",\n" +
                        "        \"ThreeLetterSymbol\": \"dza\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"Country\": \"Andorra\",\n" +
                        "        \"ThreeLetterSymbol\": \"and\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"Country\": \"Angola\",\n" +
                        "        \"ThreeLetterSymbol\": \"ago\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"Country\": \"Anguilla\",\n" +
                        "        \"ThreeLetterSymbol\": \"aia\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"Country\": \"Antigua and Barbuda\",\n" +
                        "        \"ThreeLetterSymbol\": \"atg\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"Country\": \"Argentina\",\n" +
                        "        \"ThreeLetterSymbol\": \"arg\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"Country\": \"Armenia\",\n" +
                        "        \"ThreeLetterSymbol\": \"arm\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"Country\": \"Aruba\",\n" +
                        "        \"ThreeLetterSymbol\": \"abw\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"Country\": \"Australia\",\n" +
                        "        \"ThreeLetterSymbol\": \"aus\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"Country\": \"Austria\",\n" +
                        "        \"ThreeLetterSymbol\": \"aut\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"Country\": \"Azerbaijan\",\n" +
                        "        \"ThreeLetterSymbol\": \"aze\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"Country\": \"Bahamas\",\n" +
                        "        \"ThreeLetterSymbol\": \"bhs\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"Country\": \"Bahrain\",\n" +
                        "        \"ThreeLetterSymbol\": \"bhr\"\n" +
                        "    }" +
                        "]", HttpStatus.OK));

        JsonArray result = vacCovidApiRepository.getAllCountries();

        assertThatJson(result)
                .isNotNull()
                .isArray()
                .contains(
                    json("{\"Country\":\"Austria\", \"ThreeLetterSymbol\":\"aut\"}"),
                    json("{\"Country\":\"Angola\", \"ThreeLetterSymbol\":\"ago\"}")
                );

        verify(restTemplate, times(1)).exchange(eq(VacCovidApiRepository.API_ENDPOINTS.ALL_COUNTRIES.getUrl()), eq(HttpMethod.GET), any(), eq(String.class));

    }

    @Test
    void getAllCountries_no200Code() {

        doReturn(new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR))
                .when(restTemplate).exchange(eq(VacCovidApiRepository.API_ENDPOINTS.ALL_COUNTRIES.getUrl()), eq(HttpMethod.GET), any(), eq(String.class));

        assertThrows(DataFetchException.class, () -> vacCovidApiRepository.getAllCountries());

        verify(restTemplate, times(1)).exchange(eq(VacCovidApiRepository.API_ENDPOINTS.ALL_COUNTRIES.getUrl()), eq(HttpMethod.GET), any(), eq(String.class));

    }

    @Test
    void getAllCountries_nullBody() {

        doReturn(new ResponseEntity<>(null, HttpStatus.OK))
                .when(restTemplate).exchange(eq(VacCovidApiRepository.API_ENDPOINTS.ALL_COUNTRIES.getUrl()), eq(HttpMethod.GET), any(), eq(String.class));

        assertThrows(DataFetchException.class, () -> vacCovidApiRepository.getAllCountries());

        verify(restTemplate, times(1)).exchange(eq(VacCovidApiRepository.API_ENDPOINTS.ALL_COUNTRIES.getUrl()), eq(HttpMethod.GET), any(), eq(String.class));

    }

}