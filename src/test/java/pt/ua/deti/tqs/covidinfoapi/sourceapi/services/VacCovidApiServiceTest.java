package pt.ua.deti.tqs.covidinfoapi.sourceapi.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import pt.ua.deti.tqs.covidinfoapi.sourceapi.repository.VacCovidApiRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class VacCovidApiServiceTest {

    @Mock
    private VacCovidApiRepository vacCovidApiRepository;

    @InjectMocks
    private VacCovidApiService covidApiService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getCountryCovidInfo() {

        Country country = new Country("Portugal", "prt");

        JsonObject jsonObject = JsonParser.parseString("{\n" +
                "        \"id\": \"9fb86a08-d197-4fcb-b08a-bebbe5f6c642\",\n" +
                "        \"rank\": 29,\n" +
                "        \"Country\": \"Portugal\",\n" +
                "        \"Continent\": \"Europe\",\n" +
                "        \"TwoLetterSymbol\": \"pt\",\n" +
                "        \"ThreeLetterSymbol\": \"prt\",\n" +
                "        \"Infection_Risk\": 37.38,\n" +
                "        \"Case_Fatality_Rate\": 0.58,\n" +
                "        \"Test_Percentage\": 405.87,\n" +
                "        \"Recovery_Proporation\": 0,\n" +
                "        \"TotalCases\": 3791744,\n" +
                "        \"NewCases\": 0,\n" +
                "        \"TotalDeaths\": 22162,\n" +
                "        \"NewDeaths\": 0,\n" +
                "        \"TotalRecovered\": \"3769582\",\n" +
                "        \"NewRecovered\": 0,\n" +
                "        \"ActiveCases\": 0,\n" +
                "        \"TotalTests\": \"41165855\",\n" +
                "        \"Population\": \"10142721\",\n" +
                "        \"one_Caseevery_X_ppl\": 3,\n" +
                "        \"one_Deathevery_X_ppl\": 458,\n" +
                "        \"one_Testevery_X_ppl\": 0,\n" +
                "        \"Deaths_1M_pop\": 2185,\n" +
                "        \"Serious_Critical\": 61,\n" +
                "        \"Tests_1M_Pop\": 4058660,\n" +
                "        \"TotCases_1M_Pop\": 373839\n" +
                "}").getAsJsonObject();

        doReturn(jsonObject).when(vacCovidApiRepository).getCountryCovidInfo(country);

        assertThat(covidApiService.getCountryCovidInfo(country))
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("country", "Portugal")
                .hasFieldOrPropertyWithValue("totalCases", 3791744)
                .hasFieldOrPropertyWithValue("newCases", 0)
                .hasFieldOrPropertyWithValue("totalDeaths", 22162)
                .hasFieldOrPropertyWithValue("newDeaths", 0)
                .hasFieldOrPropertyWithValue("totalRecovered", 3769582)
                .hasFieldOrPropertyWithValue("newRecovered", 0)
                .hasFieldOrPropertyWithValue("activeCases", 0)
                .hasFieldOrPropertyWithValue("population", 10142721);

    }

    @Test
    void getCountryCovidInfo_nullData() {

        Country country = new Country("Portugal", "prt");

        doReturn(null).when(vacCovidApiRepository).getCountryCovidInfo(country);
        assertThrows(NoDataFoundException.class, () -> covidApiService.getCountryCovidInfo(country));

    }

    @Test
    void getAllCountries() {

        JsonArray jsonArray = JsonParser.parseString("[\n" +
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
                "    }\n" +
                "]").getAsJsonArray();

        doReturn(jsonArray).when(vacCovidApiRepository).getAllCountries();

        assertThat(covidApiService.getAllCountries())
                .isNotNull()
                .doesNotContainNull()
                .contains(new Country("Afghanistan", "afg"), new Country("Algeria", "dza"));

    }

    @Test
    void getAllCountries_nullData() {

        doReturn(null).when(vacCovidApiRepository).getAllCountries();
        assertThrows(NoDataFoundException.class, () -> covidApiService.getAllCountries());

    }

    @Test
    void getWorldCovidInfo() {

        JsonObject jsonObject = JsonParser.parseString(" {\n" +
                "        \"id\": \"40f44943-8413-4a31-a4df-deee8111f85f\",\n" +
                "        \"rank\": 0,\n" +
                "        \"Country\": \"World\",\n" +
                "        \"Continent\": \"All\",\n" +
                "        \"TwoLetterSymbol\": null,\n" +
                "        \"ThreeLetterSymbol\": null,\n" +
                "        \"Infection_Risk\": 0,\n" +
                "        \"Case_Fatality_Rate\": 1.22,\n" +
                "        \"Test_Percentage\": 0,\n" +
                "        \"Recovery_Proporation\": 90.97,\n" +
                "        \"TotalCases\": 511672523,\n" +
                "        \"NewCases\": 247281,\n" +
                "        \"TotalDeaths\": 6254294,\n" +
                "        \"NewDeaths\": 1178,\n" +
                "        \"TotalRecovered\": \"465477398\",\n" +
                "        \"NewRecovered\": 490344,\n" +
                "        \"ActiveCases\": 39940831,\n" +
                "        \"TotalTests\": \"0\",\n" +
                "        \"Population\": \"0\",\n" +
                "        \"one_Caseevery_X_ppl\": 0,\n" +
                "        \"one_Deathevery_X_ppl\": 0,\n" +
                "        \"one_Testevery_X_ppl\": 0,\n" +
                "        \"Deaths_1M_pop\": 802.4,\n" +
                "        \"Serious_Critical\": 42112,\n" +
                "        \"Tests_1M_Pop\": 0,\n" +
                "        \"TotCases_1M_Pop\": 65643\n" +
                "    }").getAsJsonObject();

        doReturn(jsonObject).when(vacCovidApiRepository).getWorldCovidInfo();

        assertThat(covidApiService.getWorldCovidInfo())
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("totalCases", 511672523)
                .hasFieldOrPropertyWithValue("newCases", 247281)
                .hasFieldOrPropertyWithValue("totalDeaths", 6254294)
                .hasFieldOrPropertyWithValue("newDeaths", 1178)
                .hasFieldOrPropertyWithValue("totalRecovered", 465477398)
                .hasFieldOrPropertyWithValue("newRecovered", 490344)
                .hasFieldOrPropertyWithValue("activeCases", 39940831)
                .hasFieldOrPropertyWithValue("deathsPerMillion", 802.4)
                .hasFieldOrPropertyWithValue("caseFatalityRate", 1.22);

    }

    @Test
    void getWorldCovidInfo_nullData() {

        doReturn(null).when(vacCovidApiRepository).getWorldCovidInfo();
        assertThrows(NoDataFoundException.class, () -> covidApiService.getWorldCovidInfo());

    }

}