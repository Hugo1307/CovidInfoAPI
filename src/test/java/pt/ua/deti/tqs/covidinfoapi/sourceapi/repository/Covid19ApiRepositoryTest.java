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
import pt.ua.deti.tqs.covidinfoapi.sourceapi.Covid19API;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.Country;

import static org.junit.jupiter.api.Assertions.*;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class Covid19ApiRepositoryTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private Covid19ApiRepository covid19ApiRepository;

    private Covid19API covid19API;
    private Country country;
    private Country nonExistentCountry;

    @BeforeEach
    void setUp() {
        covid19API = new Covid19API("https://covid-193.p.rapidapi.com", "dbd92461c3msh03b2a93b4b69037p122734jsn4e1f7a225e85");
        country = new Country("Portugal", "prt");
        nonExistentCountry = new Country("Aaushd", "yyy");
    }

    @Test
    void getApiInstance() {
        assertEquals(covid19ApiRepository.getApiInstance(), covid19API);
    }

    @Test
    void getCountryCovidInfo() {

        doReturn(new ResponseEntity<>("{\n" +
                "    \"get\": \"statistics\",\n" +
                "    \"parameters\": {\n" +
                "        \"country\": \"Portugal\"\n" +
                "    },\n" +
                "    \"errors\": [],\n" +
                "    \"results\": 1,\n" +
                "    \"response\": [\n" +
                "        {\n" +
                "            \"continent\": \"Europe\",\n" +
                "            \"country\": \"Portugal\",\n" +
                "            \"population\": 10142964,\n" +
                "            \"cases\": {\n" +
                "                \"new\": null,\n" +
                "                \"active\": null,\n" +
                "                \"critical\": 61,\n" +
                "                \"recovered\": null,\n" +
                "                \"1M_pop\": \"373830\",\n" +
                "                \"total\": 3791744\n" +
                "            },\n" +
                "            \"deaths\": {\n" +
                "                \"new\": null,\n" +
                "                \"1M_pop\": \"2185\",\n" +
                "                \"total\": 22162\n" +
                "            },\n" +
                "            \"tests\": {\n" +
                "                \"1M_pop\": \"4046001\",\n" +
                "                \"total\": 41038444\n" +
                "            },\n" +
                "            \"day\": \"2022-04-25\",\n" +
                "            \"time\": \"2022-04-25T23:45:03+00:00\"\n" +
                "        }\n" +
                "    ]\n" +
                "}", HttpStatus.OK))
                .when(restTemplate).exchange(eq(Covid19ApiRepository.API_ENDPOINTS.COUNTRY_INFO.getUrl()), eq(HttpMethod.GET), any(), eq(String.class), anyMap());

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
    void getCountryCovidInfo_nonExistentCountry() {

        doReturn(new ResponseEntity<>("{\n" +
                "    \"get\": \"statistics\",\n" +
                "    \"parameters\": {\n" +
                "        \"country\": \"Aaushd\"\n" +
                "    },\n" +
                "    \"errors\": [],\n" +
                "    \"results\": 0,\n" +
                "    \"response\": []\n" +
                "}", HttpStatus.OK))
                .when(restTemplate).exchange(eq(Covid19ApiRepository.API_ENDPOINTS.COUNTRY_INFO.getUrl()), eq(HttpMethod.GET), any(), eq(String.class), anyMap());

        assertThrows(NoDataFoundException.class, () -> covid19ApiRepository.getCountryCovidInfo(nonExistentCountry));
    }

    @Test
    void getCountryCovidInfo_nullCountry() {

        doReturn(new ResponseEntity<>("{\n" +
                "    \"get\": \"statistics\",\n" +
                "    \"parameters\": {\n" +
                "        \"country\": \"Aaushd\"\n" +
                "    },\n" +
                "    \"errors\": [ahsd],\n" +
                "    \"results\": 0,\n" +
                "    \"response\": []\n" +
                "}", HttpStatus.OK))
                .when(restTemplate).exchange(eq(Covid19ApiRepository.API_ENDPOINTS.COUNTRY_INFO.getUrl()), eq(HttpMethod.GET), any(), eq(String.class), anyMap());

        assertThrows(NoDataFoundException.class, () -> covid19ApiRepository.getCountryCovidInfo(nonExistentCountry));
    }

    @Test
    void getCountryCovidInfo_no200Code() {

        doReturn(new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR))
                .when(restTemplate).exchange(eq(Covid19ApiRepository.API_ENDPOINTS.COUNTRY_INFO.getUrl()), eq(HttpMethod.GET), any(), eq(String.class), anyMap());

        assertThrows(DataFetchException.class, () -> covid19ApiRepository.getCountryCovidInfo(nonExistentCountry));
    }

    @Test
    void getCountryCovidInfo_nullBody() {

        doReturn(new ResponseEntity<>(null, HttpStatus.OK))
                .when(restTemplate).exchange(eq(Covid19ApiRepository.API_ENDPOINTS.COUNTRY_INFO.getUrl()), eq(HttpMethod.GET), any(), eq(String.class), anyMap());

        assertThrows(DataFetchException.class, () -> covid19ApiRepository.getCountryCovidInfo(nonExistentCountry));
    }

    @Test
    void getAllCountries() {

        doReturn(new ResponseEntity<>("{\n" +
                "    \"get\": \"countries\",\n" +
                "    \"parameters\": [],\n" +
                "    \"errors\": [],\n" +
                "    \"results\": 233,\n" +
                "    \"response\": [\n" +
                "        \"Afghanistan\",\n" +
                "        \"Albania\",\n" +
                "        \"Algeria\",\n" +
                "        \"Andorra\",\n" +
                "        \"Angola\",\n" +
                "        \"Anguilla\",\n" +
                "        \"Antigua-and-Barbuda\",\n" +
                "        \"Argentina\",\n" +
                "        \"Armenia\",\n" +
                "        \"Aruba\",\n" +
                "        \"Australia\",\n" +
                "        \"Austria\",\n" +
                "        \"Azerbaijan\"\n" +
                "    ]\n" +
                "}", HttpStatus.OK))
                .when(restTemplate).exchange(eq(Covid19ApiRepository.API_ENDPOINTS.ALL_COUNTRIES.getUrl()), eq(HttpMethod.GET), any(), eq(String.class));

        JsonArray result = covid19ApiRepository.getAllCountries();

        assertThatJson(result)
                .isArray()
                .contains("Austria", "Armenia", "Andorra", "Angola");

    }

    @Test
    void getAllCountries_no200Code() {

        doReturn(new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR))
                .when(restTemplate).exchange(eq(Covid19ApiRepository.API_ENDPOINTS.ALL_COUNTRIES.getUrl()), eq(HttpMethod.GET), any(), eq(String.class));

        assertThrows(DataFetchException.class, () -> covid19ApiRepository.getAllCountries());

    }

    @Test
    void getAllCountries_nullBody() {

        doReturn(new ResponseEntity<>(null, HttpStatus.OK))
                .when(restTemplate).exchange(eq(Covid19ApiRepository.API_ENDPOINTS.ALL_COUNTRIES.getUrl()), eq(HttpMethod.GET), any(), eq(String.class));

        assertThrows(DataFetchException.class, () -> covid19ApiRepository.getAllCountries());

    }

    @Test
    void getAllCountries_noCountries() {

        doReturn(new ResponseEntity<>("{\n" +
                "    \"get\": \"countries\",\n" +
                "    \"parameters\": [],\n" +
                "    \"errors\": [],\n" +
                "    \"results\": 233,\n" +
                "    \"response\": []\n" +
                "}", HttpStatus.OK))
                .when(restTemplate).exchange(eq(Covid19ApiRepository.API_ENDPOINTS.ALL_COUNTRIES.getUrl()), eq(HttpMethod.GET), any(), eq(String.class));

        assertThrows(DataFetchException.class, () -> covid19ApiRepository.getAllCountries());

    }

}