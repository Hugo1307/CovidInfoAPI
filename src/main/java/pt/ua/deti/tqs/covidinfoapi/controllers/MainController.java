package pt.ua.deti.tqs.covidinfoapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.deti.tqs.covidinfoapi.cache.CacheDetails;
import pt.ua.deti.tqs.covidinfoapi.cache.CacheInjector;
import pt.ua.deti.tqs.covidinfoapi.cache.CacheManager;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.CachedCountryCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.CachedCountriesList;
import pt.ua.deti.tqs.covidinfoapi.cache.implementations.CountryCovidInfoCache;
import pt.ua.deti.tqs.covidinfoapi.cache.implementations.WorldCovidInfoSingleCache;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.ExternalAPI;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.Country;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.CountryCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.vaccovid.VacCovidWorldCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.services.Covid19ApiService;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.services.IExternalApiService;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.services.VacCovidApiService;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MainController {

    private final VacCovidApiService vacCovidAPIService;
    private final Covid19ApiService covid19ApiService;
    private final CacheManager cacheManager;
    private final CacheInjector cacheInjector;
    private final CacheDetails cacheDetails;

    @Autowired
    public MainController(VacCovidApiService vacCovidAPIService, Covid19ApiService covid19ApiService, WorldCovidInfoSingleCache worldCovidInfoCache, CountryCovidInfoCache countryCovidInfoCache, CacheManager cacheManager, CacheInjector cacheInjector, CacheDetails cacheDetails) {
        this.vacCovidAPIService = vacCovidAPIService;
        this.covid19ApiService = covid19ApiService;
        this.cacheInjector = cacheInjector;
        this.cacheManager = cacheManager;
        this.cacheDetails = cacheDetails;
    }

    @RequestMapping(value = "/country")
    ResponseEntity<?> getCountryCovidInfo(@RequestBody Country country, @RequestParam Optional<ExternalAPI.AvailableAPI> api) {

        IExternalApiService apiService;
        CountryCovidInfoCache countryCovidInfoCache = cacheInjector.getCountryCovidInfoCache();

        if (api.isPresent())
            apiService = api.get() == ExternalAPI.AvailableAPI.VAC_COVID ? vacCovidAPIService : covid19ApiService;
        else
            apiService = vacCovidAPIService;

        if (cacheManager.isValid(country.getName(), countryCovidInfoCache))
            return new ResponseEntity<>(cacheManager.getCachedValue(country.getName(), countryCovidInfoCache).getCachedValue(), HttpStatus.OK);

        CountryCovidInfo generalCovidInfo = apiService.getCountryCovidInfo(country);
        cacheManager.updateCachedValue(country.getName(), new CachedCountryCovidInfo(generalCovidInfo, Date.from(Instant.now())), countryCovidInfoCache);

        return new ResponseEntity<>(generalCovidInfo, HttpStatus.OK);

    }

    @RequestMapping(value = "/world")
    ResponseEntity<?> getWorldCovidInfo() {

        WorldCovidInfoSingleCache worldCovidInfoCache = cacheInjector.getWorldCovidInfoCache();

        if (cacheManager.isValid(worldCovidInfoCache))
            return new ResponseEntity<>(cacheManager.getCachedValue(worldCovidInfoCache), HttpStatus.OK);

        VacCovidWorldCovidInfo worldCovidInfo = vacCovidAPIService.getWorldCovidInfo();
        cacheManager.updateCachedValue(worldCovidInfo, worldCovidInfoCache);

        return new ResponseEntity<>(worldCovidInfo, HttpStatus.OK);

    }

    @RequestMapping(value = "/countries")
    ResponseEntity<?> getAllCountries(@RequestParam Optional<ExternalAPI.AvailableAPI> api) {

        IExternalApiService apiService;

        if (api.isPresent())
            apiService = api.get() == ExternalAPI.AvailableAPI.VAC_COVID ? vacCovidAPIService : covid19ApiService;
        else
            apiService = vacCovidAPIService;

        ExternalAPI.AvailableAPI availableAPI = api.orElse(ExternalAPI.AvailableAPI.VAC_COVID);

        if (cacheManager.isValid(availableAPI, cacheManager.getCountryListCache()))
            return new ResponseEntity<>(cacheManager.getCachedValue(availableAPI, cacheManager.getCountryListCache()).getCachedValue(), HttpStatus.OK);

        List<Country> allCountries = apiService.getAllCountries();

        cacheManager.updateCachedValue(availableAPI, new CachedCountriesList(allCountries, Date.from(Instant.now())), cacheManager.getCountryListCache());
        return new ResponseEntity<>(allCountries, HttpStatus.OK);

    }

    @RequestMapping(value = "/cache/details")
    ResponseEntity<?> getCacheDetails() {
        return new ResponseEntity<>(cacheDetails, HttpStatus.OK);
    }

}
