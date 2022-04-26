package pt.ua.deti.tqs.covidinfoapi.sourceapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.DataFetchException;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.NoDataFoundException;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.Country;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.CountryCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.covid19api.Covid19CountryCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.covid19api.deserializers.Covid19CountryInfoDeserializer;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.repository.Covid19ApiRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class Covid19ApiService implements IExternalApiService {

    private final Covid19ApiRepository covid19ApiRepository;

    @Autowired
    public Covid19ApiService(Covid19ApiRepository covid19ApiRepository) {
        this.covid19ApiRepository = covid19ApiRepository;
    }

    @Override
    public CountryCovidInfo getCountryCovidInfo(Country country) {

        JsonObject generalCovidInfoJsonObj = covid19ApiRepository.getCountryCovidInfo(country);

        if (generalCovidInfoJsonObj == null)
            throw new NoDataFoundException("Unable to find data for country covid info.");

        try {

            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Covid19CountryCovidInfo.class, new Covid19CountryInfoDeserializer());
            mapper.registerModule(module);

            return mapper.readValue(generalCovidInfoJsonObj.toString(), Covid19CountryCovidInfo.class);

        } catch (JsonProcessingException | NullPointerException e) {
            throw new DataFetchException("Unable to parse data from upstream server.");
        }

    }

    @Override
    public List<Country> getAllCountries() {

        JsonArray allCountriesJsonArray = covid19ApiRepository.getAllCountries();
        List<Country> allCountries = new ArrayList<>();

        if (allCountriesJsonArray == null)
            throw new NoDataFoundException("Unable to find data.");

        allCountriesJsonArray.forEach(jsonElement -> allCountries.add(new Country(jsonElement.getAsString(), null)));
        return allCountries;

    }

}
