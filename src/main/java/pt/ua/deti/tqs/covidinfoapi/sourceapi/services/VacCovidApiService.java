package pt.ua.deti.tqs.covidinfoapi.sourceapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.DataFetchException;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.NoDataFoundException;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.Country;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.CountryCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.vaccovid.VacCovidCountryCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.vaccovid.VacCovidWorldCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.repository.VacCovidApiRepository;

import java.util.List;

@Service
public class VacCovidApiService implements IExternalApiService {

    private final VacCovidApiRepository vacCovidApiRepository;

    @Autowired
    public VacCovidApiService(VacCovidApiRepository vacCovidApiRepository) {
        this.vacCovidApiRepository = vacCovidApiRepository;
    }

    @Override
    @NonNull
    public CountryCovidInfo getCountryCovidInfo(Country country) {

        JsonObject generalCovidInfoJsonObj = vacCovidApiRepository.getCountryCovidInfo(country);

        if (generalCovidInfoJsonObj == null)
            throw new NoDataFoundException("Unable to find data for country covid info.");

        try {
            return new ObjectMapper().readValue(generalCovidInfoJsonObj.toString(), VacCovidCountryCovidInfo.class);
        } catch (JsonProcessingException e) {
           throw new DataFetchException("Unable to parse data from upstream server.");
        }

    }

    @Override
    public List<Country> getAllCountries() {

        JsonArray allCountriesJsonArray = vacCovidApiRepository.getAllCountries();

        if (allCountriesJsonArray == null)
            throw new NoDataFoundException("Unable to find data for list of countries.");

        try {
            return new ObjectMapper().readValue(allCountriesJsonArray.toString(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new DataFetchException("Unable to parse data from upstream server.");
        }

    }

    @NonNull
    public VacCovidWorldCovidInfo getWorldCovidInfo() {

        JsonObject generalCovidInfoJsonObj = vacCovidApiRepository.getWorldCovidInfo();

        if (generalCovidInfoJsonObj == null)
            throw new NoDataFoundException("Unable to find data for worldwide covid info.");

        try {
            return new ObjectMapper().readValue(generalCovidInfoJsonObj.toString(), VacCovidWorldCovidInfo.class);
        } catch (JsonProcessingException e) {
            throw new DataFetchException("Unable to parse data from upstream server.");
        }

    }

}
