package pt.ua.deti.tqs.covidinfoapi.sourceapi.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.DataFetchException;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.NoDataFoundException;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.Country;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.repository.Covid19ApiRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class Covid19ApiServiceTest {

    @Mock
    private Covid19ApiRepository covid19ApiRepository;

    @InjectMocks
    private Covid19ApiService covid19ApiService;

    @Test
    void getCountryCovidInfo() {

        Country country = new Country("Portugal", "prt");

        JsonObject jsonObject = JsonParser.parseString("{\n" +
                "            \"continent\": \"Europe\",\n" +
                "            \"country\": \"Portugal\",\n" +
                "            \"population\": 10142883,\n" +
                "            \"cases\": {\n" +
                "                \"new\": null,\n" +
                "                \"active\": null,\n" +
                "                \"critical\": 61,\n" +
                "                \"recovered\": null,\n" +
                "                \"1M_pop\": \"373833\",\n" +
                "                \"total\": 3791744\n" +
                "            },\n" +
                "            \"deaths\": {\n" +
                "                \"new\": null,\n" +
                "                \"1M_pop\": \"2185\",\n" +
                "                \"total\": 22162\n" +
                "            },\n" +
                "            \"tests\": {\n" +
                "                \"1M_pop\": \"4046033\",\n" +
                "                \"total\": 41038444\n" +
                "            },\n" +
                "            \"day\": \"2022-04-26\",\n" +
                "            \"time\": \"2022-04-26T00:15:03+00:00\"\n" +
                "}").getAsJsonObject();

        doReturn(jsonObject).when(covid19ApiRepository).getCountryCovidInfo(country);

        assertThat(covid19ApiService.getCountryCovidInfo(country))
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("country", "Portugal")
                .hasFieldOrPropertyWithValue("totalCases", 3791744)
                .hasFieldOrPropertyWithValue("newCases", 0)
                .hasFieldOrPropertyWithValue("totalDeaths", 22162)
                .hasFieldOrPropertyWithValue("newDeaths", 0)
                .hasFieldOrPropertyWithValue("totalRecovered", 0)
                .hasFieldOrPropertyWithValue("newRecovered", 0)
                .hasFieldOrPropertyWithValue("activeCases", 0)
                .hasFieldOrPropertyWithValue("population", 10142883);

    }

    // This test missed the "cases" json object.
    @Test
    void getCountryCovidInfo_wrongDataFormat() {

        Country country = new Country("Portugal", "prt");

        JsonObject jsonObject = JsonParser.parseString("{\n" +
                "            \"continent\": \"Europe\",\n" +
                "            \"country\": \"Portugal\",\n" +
                "            \"population\": 10142883,\n" +
                "            \"deaths\": {\n" +
                "                \"new\": null,\n" +
                "                \"1M_pop\": \"2185\",\n" +
                "                \"total\": 22162\n" +
                "            },\n" +
                "            \"tests\": {\n" +
                "                \"1M_pop\": \"4046033\",\n" +
                "                \"total\": 41038444\n" +
                "            },\n" +
                "            \"day\": \"2022-04-26\",\n" +
                "            \"time\": \"2022-04-26T00:15:03+00:00\"\n" +
                "}").getAsJsonObject();

        doReturn(jsonObject).when(covid19ApiRepository).getCountryCovidInfo(country);

        assertThrows(DataFetchException.class, () -> covid19ApiService.getCountryCovidInfo(country));

    }

    @Test
    void getCountryCovidInfo_nullData() {

        Country country = new Country("Portugal", "prt");

        doReturn(null).when(covid19ApiRepository).getCountryCovidInfo(country);
        assertThrows(NoDataFoundException.class, () -> covid19ApiService.getCountryCovidInfo(country));

    }

    @Test
    void getAllCountries() {

        JsonArray jsonArray = JsonParser.parseString("[\n" +
                "        \"Afghanistan\",\n" +
                "        \"Albania\",\n" +
                "        \"Algeria\",\n" +
                "        \"Andorra\",\n" +
                "        \"Angola\" \n"  +
                "    ]").getAsJsonArray();

        doReturn(jsonArray).when(covid19ApiRepository).getAllCountries();

        assertThat(covid19ApiService.getAllCountries()).hasSize(5);

        assertThat(covid19ApiService.getAllCountries()).extracting(Country::getName).doesNotContainNull();

        assertThat(covid19ApiService.getAllCountries()).extracting(Country::getCode).containsOnlyNulls();

        assertThat(covid19ApiService.getAllCountries()).extracting(Country::getName).containsExactly("Afghanistan", "Albania", "Algeria", "Andorra", "Angola");

    }

    @Test
    void getAllCountries_nullData() {

        doReturn(null).when(covid19ApiRepository).getAllCountries();
        assertThrows(NoDataFoundException.class, () -> covid19ApiService.getAllCountries());

    }

}