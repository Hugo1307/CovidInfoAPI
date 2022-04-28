package pt.ua.deti.tqs.covidinfoapi.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import pt.ua.deti.tqs.covidinfoapi.cache.CacheDetails;
import pt.ua.deti.tqs.covidinfoapi.cache.CacheInjector;
import pt.ua.deti.tqs.covidinfoapi.cache.CacheManager;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.CachedCountriesList;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.CachedCountryCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.ExternalAPI;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.Country;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.CountryCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.covid19api.Covid19CountryCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.vaccovid.VacCovidCountryCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.vaccovid.VacCovidWorldCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.services.Covid19ApiService;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.services.VacCovidApiService;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
class MainControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VacCovidApiService vacCovidApiService;

    @MockBean
    private Covid19ApiService covid19ApiService;

    @MockBean
    private CacheManager cacheManager;

    @MockBean
    private CacheInjector cacheInjector;

    @MockBean
    private CacheDetails cacheDetails;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void getCountryCovidInfo_noApiArg() throws Exception {

        Country country = new Country("Portugal", "prt");
        VacCovidCountryCovidInfo countryCovidInfo = new VacCovidCountryCovidInfo("Portugal", 10142721,3791744 , 0, 22162, 0, 3769582, 0,0, 29, 37.38, 0.58, 405.87, 41165855, 2185, 4058660, 373839);

        when(vacCovidApiService.getCountryCovidInfo(country)).thenReturn(countryCovidInfo);

        mvc.perform(MockMvcRequestBuilders.get("/api/country?countryName=Portugal&countryCode=prt").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void getCountryCovidInfo_withVacCovid() throws Exception {

        Country country = new Country("Portugal", "prt");
        VacCovidCountryCovidInfo countryCovidInfo = new VacCovidCountryCovidInfo("Portugal", 10142721,3791744 , 0, 22162, 0, 3769582, 0,0, 29, 37.38, 0.58, 405.87, 41165855, 2185, 4058660, 373839);

        when(vacCovidApiService.getCountryCovidInfo(country)).thenReturn(countryCovidInfo);

        mvc.perform(MockMvcRequestBuilders.get("/api/country?countryName=Portugal&countryCode=prt&api=VAC_COVID").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country", is("Portugal")))
                .andExpect(jsonPath("$.population", is(countryCovidInfo.getPopulation())))
                .andExpect(jsonPath("$.totalCases", is(countryCovidInfo.getTotalCases())))
                .andExpect(jsonPath("$.newCases", is(countryCovidInfo.getNewCases())))
                .andExpect(jsonPath("$.totalDeaths", is(countryCovidInfo.getTotalDeaths())))
                .andExpect(jsonPath("$.newDeaths", is(countryCovidInfo.getNewDeaths())))
                .andExpect(jsonPath("$.totalRecovered", is(countryCovidInfo.getTotalRecovered())))
                .andExpect(jsonPath("$.activeCases", is(countryCovidInfo.getActiveCases())))
                .andExpect(jsonPath("$.rank", is(countryCovidInfo.getRank())))
                .andExpect(jsonPath("$.infectionRisk", is(countryCovidInfo.getInfectionRisk())))
                .andExpect(jsonPath("$.caseFatalityRate", is(countryCovidInfo.getCaseFatalityRate())))
                .andExpect(jsonPath("$.testPercentage", is(countryCovidInfo.getTestPercentage())))
                .andExpect(jsonPath("$.totalTests", is(countryCovidInfo.getTotalTests())))
                .andExpect(jsonPath("$.deathsPerMillion", is(countryCovidInfo.getDeathsPerMillion())))
                .andExpect(jsonPath("$.testsPerMillion", is(countryCovidInfo.getTestsPerMillion())))
                .andExpect(jsonPath("$.casesPerMillion", is(countryCovidInfo.getCasesPerMillion())));

    }

    @Test
    void getCountryCovidInfo_withCovid19() throws Exception {

        Country country = new Country("Portugal", "prt");
        Covid19CountryCovidInfo countryCovidInfo = new Covid19CountryCovidInfo("Portugal", 3791744,0 , 22162, 0, 0, 0, 0,10142721);

        when(covid19ApiService.getCountryCovidInfo(country)).thenReturn(countryCovidInfo);

        mvc.perform(MockMvcRequestBuilders.get("/api/country?countryName=Portugal&countryCode=prt&api=COVID_19").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country", is("Portugal")))
                .andExpect(jsonPath("$.population", is(countryCovidInfo.getPopulation())))
                .andExpect(jsonPath("$.totalCases", is(countryCovidInfo.getTotalCases())))
                .andExpect(jsonPath("$.newCases", is(countryCovidInfo.getNewCases())))
                .andExpect(jsonPath("$.totalDeaths", is(countryCovidInfo.getTotalDeaths())))
                .andExpect(jsonPath("$.newDeaths", is(countryCovidInfo.getNewDeaths())))
                .andExpect(jsonPath("$.totalRecovered", is(countryCovidInfo.getTotalRecovered())))
                .andExpect(jsonPath("$.activeCases", is(countryCovidInfo.getActiveCases())));

    }

    @Test
    void getCountryCovidInfo_withCache() throws Exception {

        Country country = new Country("Portugal", "prt");
        CountryCovidInfo countryCovidInfo = new Covid19CountryCovidInfo("Portugal", 3791744,0 , 22162, 0, 0, 0, 0,10142721);
        CachedCountryCovidInfo cachedCountryCovidInfo = new CachedCountryCovidInfo(countryCovidInfo, Date.from(Instant.now()));

        when(cacheManager.isValid(country.getName(), cacheInjector.getCountryCovidInfoCache())).thenReturn(true);
        when(cacheManager.getCachedValue(country.getName(), cacheInjector.getCountryCovidInfoCache())).thenReturn(cachedCountryCovidInfo);

        mvc.perform(MockMvcRequestBuilders.get("/api/country?countryName=Portugal&countryCode=prt").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country", is("Portugal")))
                .andExpect(jsonPath("$.population", is(countryCovidInfo.getPopulation())))
                .andExpect(jsonPath("$.totalCases", is(countryCovidInfo.getTotalCases())))
                .andExpect(jsonPath("$.newCases", is(countryCovidInfo.getNewCases())))
                .andExpect(jsonPath("$.totalDeaths", is(countryCovidInfo.getTotalDeaths())))
                .andExpect(jsonPath("$.newDeaths", is(countryCovidInfo.getNewDeaths())))
                .andExpect(jsonPath("$.totalRecovered", is(countryCovidInfo.getTotalRecovered())))
                .andExpect(jsonPath("$.activeCases", is(countryCovidInfo.getActiveCases())));

    }

    @Test
    void getWorldCovidInfo() throws Exception {

        VacCovidWorldCovidInfo vacCovidWorldCovidInfo = new VacCovidWorldCovidInfo(511847955, 421883, 6254688, 1572, 465536886, 549832, 40056381, 802.4, 1.22);

        when(cacheManager.isValid(cacheInjector.getWorldCovidInfoCache())).thenReturn(false);
        when(vacCovidApiService.getWorldCovidInfo()).thenReturn(vacCovidWorldCovidInfo);

        mvc.perform(MockMvcRequestBuilders.get("/api/world").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCases", is(vacCovidWorldCovidInfo.getTotalCases())))
                .andExpect(jsonPath("$.newCases", is(vacCovidWorldCovidInfo.getNewCases())))
                .andExpect(jsonPath("$.totalDeaths", is(vacCovidWorldCovidInfo.getTotalDeaths())))
                .andExpect(jsonPath("$.newDeaths", is(vacCovidWorldCovidInfo.getNewDeaths())))
                .andExpect(jsonPath("$.totalRecovered", is(vacCovidWorldCovidInfo.getTotalRecovered())))
                .andExpect(jsonPath("$.activeCases", is(vacCovidWorldCovidInfo.getActiveCases())))
                .andExpect(jsonPath("$.deathsPerMillion", is(vacCovidWorldCovidInfo.getDeathsPerMillion())))
                .andExpect(jsonPath("$.caseFatalityRate", is(vacCovidWorldCovidInfo.getCaseFatalityRate())));

    }

    @Test
    void getWorldCovidInfo_withCache() throws Exception {

        VacCovidWorldCovidInfo vacCovidWorldCovidInfo = new VacCovidWorldCovidInfo(511847955, 421883, 6254688, 1572, 465536886, 549832, 40056381, 802.4, 1.22);

        when(cacheManager.isValid(cacheInjector.getWorldCovidInfoCache())).thenReturn(true);
        when(cacheManager.getCachedValue(cacheInjector.getWorldCovidInfoCache())).thenReturn(vacCovidWorldCovidInfo);

        mvc.perform(MockMvcRequestBuilders.get("/api/world").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCases", is(vacCovidWorldCovidInfo.getTotalCases())))
                .andExpect(jsonPath("$.newCases", is(vacCovidWorldCovidInfo.getNewCases())))
                .andExpect(jsonPath("$.totalDeaths", is(vacCovidWorldCovidInfo.getTotalDeaths())))
                .andExpect(jsonPath("$.newDeaths", is(vacCovidWorldCovidInfo.getNewDeaths())))
                .andExpect(jsonPath("$.totalRecovered", is(vacCovidWorldCovidInfo.getTotalRecovered())))
                .andExpect(jsonPath("$.activeCases", is(vacCovidWorldCovidInfo.getActiveCases())))
                .andExpect(jsonPath("$.deathsPerMillion", is(vacCovidWorldCovidInfo.getDeathsPerMillion())))
                .andExpect(jsonPath("$.caseFatalityRate", is(vacCovidWorldCovidInfo.getCaseFatalityRate())));

    }

    @Test
    void getAllCountries_noApi() throws Exception {

        List<Country> countries = List.of(new Country("Afghanistan", "afg"), new Country("Albania", "alb"));

        when(vacCovidApiService.getAllCountries()).thenReturn(countries);
        when(cacheManager.isValid(cacheInjector.getWorldCovidInfoCache())).thenReturn(false);

        mvc.perform(MockMvcRequestBuilders.get("/api/countries").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(countries.get(0).getName())))
                .andExpect(jsonPath("$[0].code", is(countries.get(0).getCode())))
                .andExpect(jsonPath("$[1].name", is(countries.get(1).getName())))
                .andExpect(jsonPath("$[1].code", is(countries.get(1).getCode())));

    }

    @Test
    void getAllCountries_VacCovidApi() throws Exception {

        List<Country> countries = List.of(new Country("Afghanistan", "afg"), new Country("Albania", "alb"));

        when(vacCovidApiService.getAllCountries()).thenReturn(countries);
        when(cacheManager.isValid(cacheInjector.getWorldCovidInfoCache())).thenReturn(false);

        mvc.perform(MockMvcRequestBuilders.get("/api/countries?api=VAC_COVID").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(countries.get(0).getName())))
                .andExpect(jsonPath("$[0].code", is(countries.get(0).getCode())))
                .andExpect(jsonPath("$[1].name", is(countries.get(1).getName())))
                .andExpect(jsonPath("$[1].code", is(countries.get(1).getCode())));

    }

    @Test
    void getAllCountries_Covid19Api() throws Exception {

        List<Country> countries = List.of(new Country("Afghanistan", null), new Country("Albania", null));

        when(covid19ApiService.getAllCountries()).thenReturn(countries);
        when(cacheManager.isValid(cacheInjector.getWorldCovidInfoCache())).thenReturn(false);

        mvc.perform(MockMvcRequestBuilders.get("/api/countries?api=COVID_19").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(countries.get(0).getName())))
                .andExpect(jsonPath("$[0].code", is(countries.get(0).getCode())))
                .andExpect(jsonPath("$[1].name", is(countries.get(1).getName())))
                .andExpect(jsonPath("$[1].code", is(countries.get(1).getCode())));

    }

    @Test
    void getAllCountries_withCache() throws Exception {

        List<Country> countries = List.of(new Country("Afghanistan", "afg"), new Country("Albania", "alb"));
        CachedCountriesList cachedCountriesList = new CachedCountriesList(countries, Date.from(Instant.now()));

        when(cacheManager.isValid(ExternalAPI.AvailableAPI.VAC_COVID, cacheInjector.getCountryListCache())).thenReturn(true);
        when(cacheManager.getCachedValue(ExternalAPI.AvailableAPI.VAC_COVID, cacheInjector.getCountryListCache())).thenReturn(cachedCountriesList);

        mvc.perform(MockMvcRequestBuilders.get("/api/countries").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(countries.get(0).getName())))
                .andExpect(jsonPath("$[0].code", is(countries.get(0).getCode())))
                .andExpect(jsonPath("$[1].name", is(countries.get(1).getName())))
                .andExpect(jsonPath("$[1].code", is(countries.get(1).getCode())));

    }

}