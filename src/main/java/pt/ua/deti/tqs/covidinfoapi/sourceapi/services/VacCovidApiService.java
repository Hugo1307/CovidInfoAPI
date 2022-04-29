package pt.ua.deti.tqs.covidinfoapi.sourceapi.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.NoDataFoundException;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.Country;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.CountryCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.vaccovid.VacCovidCountryCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.vaccovid.VacCovidCountryHistoryData;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.vaccovid.VacCovidWorldCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.repository.VacCovidApiRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacCovidApiService implements IExternalApiService {

    private final VacCovidApiRepository vacCovidApiRepository;

    @Autowired
    public VacCovidApiService(VacCovidApiRepository vacCovidApiRepository) {
        this.vacCovidApiRepository = vacCovidApiRepository;
    }

    @SneakyThrows
    @Override
    @NonNull
    public CountryCovidInfo getCountryCovidInfo(Country country) {

        JsonObject generalCovidInfoJsonObj = vacCovidApiRepository.getCountryCovidInfo(country);

        if (generalCovidInfoJsonObj == null)
            throw new NoDataFoundException("Unable to find data for country covid info.");

        return new ObjectMapper().readValue(generalCovidInfoJsonObj.toString(), VacCovidCountryCovidInfo.class);

    }

    @SneakyThrows
    @Override
    public List<Country> getAllCountries() {

        JsonArray allCountriesJsonArray = vacCovidApiRepository.getAllCountries();

        if (allCountriesJsonArray == null)
            throw new NoDataFoundException("Unable to find data for list of countries.");

        return new ObjectMapper().readValue(allCountriesJsonArray.toString(), new TypeReference<>() {});

    }

    @NonNull
    @SneakyThrows
    public VacCovidWorldCovidInfo getWorldCovidInfo() {

        JsonObject generalCovidInfoJsonObj = vacCovidApiRepository.getWorldCovidInfo();

        if (generalCovidInfoJsonObj == null)
            throw new NoDataFoundException("Unable to find data for worldwide covid info.");

        return new ObjectMapper().readValue(generalCovidInfoJsonObj.toString(), VacCovidWorldCovidInfo.class);

    }

    @SneakyThrows
    public List<VacCovidCountryHistoryData> getCountryHistory(Country country) {

        JsonArray countryHistoryJsonArray = vacCovidApiRepository.getCountryHistoryData(country);

        if (countryHistoryJsonArray == null)
            throw new NoDataFoundException("Unable to find data for country history covid info.");

        return new ObjectMapper().readValue(countryHistoryJsonArray.toString(), new TypeReference<>() {});

    }

    @SneakyThrows
    public List<VacCovidCountryHistoryData> getCountryHistory(Country country, Date date) {

        JsonArray countryHistoryJsonArray = vacCovidApiRepository.getCountryHistoryData(country);

        if (countryHistoryJsonArray == null)
            throw new NoDataFoundException("Unable to find data for country history covid info.");

        List<VacCovidCountryHistoryData> covidCountryHistoryData = new ObjectMapper().readValue(countryHistoryJsonArray.toString(), new TypeReference<>() {});
        return covidCountryHistoryData
                .stream()
                .filter(vacCovidCountryHistoryData -> Math.abs(vacCovidCountryHistoryData.getDate().getTime() - date.getTime()) < 24*60*60*1000)
                .collect(Collectors.toList());
    }

}
