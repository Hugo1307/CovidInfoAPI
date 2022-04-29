package pt.ua.deti.tqs.covidinfoapi.sourceapi.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.DataFetchException;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.NoDataFoundException;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.ExternalAPI;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.VacCovidAPI;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.Country;

import java.util.Collections;

@Component
public class VacCovidApiRepository implements IExternalApiRepository {

    private final VacCovidAPI vacCovidAPI;

    private final RestTemplate restTemplate;

    @Autowired
    public VacCovidApiRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.vacCovidAPI = new VacCovidAPI("https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com/api", "dbd92461c3msh03b2a93b4b69037p122734jsn4e1f7a225e85");
    }

    @Override
    public ExternalAPI getApiInstance() {
        return vacCovidAPI;
    }

    @Override
    public JsonObject getCountryCovidInfo(Country country) {

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("X-RapidAPI-Key", vacCovidAPI.getApiKey());

        ResponseEntity<String> response = restTemplate.exchange(String.format(API_ENDPOINTS.COUNTRY_INFO.getUrl(), country.getName(), country.getCode()), HttpMethod.GET, new HttpEntity<>("body", headers), String.class);

        if (!response.hasBody() || response.getBody() == null || response.getStatusCode() != HttpStatus.OK)
            throw new DataFetchException("Unable to fetch country report. The server didn't report any results.");

        String responseBodyAsString = response.getBody();

        JsonArray jsonArray = JsonParser.parseString(responseBodyAsString).getAsJsonArray();

        if (jsonArray.isEmpty())
            throw new NoDataFoundException("Unable to find data for that country.");
        return jsonArray.get(0).getAsJsonObject();

    }

    public JsonObject getWorldCovidInfo() {

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("X-RapidAPI-Key", vacCovidAPI.getApiKey());

        ResponseEntity<String> response = restTemplate.exchange(API_ENDPOINTS.WORLD_INFO.getUrl(), HttpMethod.GET, new HttpEntity<>("body", headers), String.class);

        if (!response.hasBody() || response.getBody() == null || response.getStatusCode() != HttpStatus.OK)
            throw new DataFetchException("Unable to fetch world report. The server didn't report any results.");

        String responseBodyAsString = response.getBody();

        JsonArray jsonArray = JsonParser.parseString(responseBodyAsString).getAsJsonArray();

        if (jsonArray.isEmpty())
            throw new NoDataFoundException("Unable to find world's data.");
        return jsonArray.get(0).getAsJsonObject();

    }

    @Override
    public JsonArray getAllCountries() {

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("X-RapidAPI-Key", vacCovidAPI.getApiKey());

        ResponseEntity<String> response = restTemplate.exchange(API_ENDPOINTS.ALL_COUNTRIES.getUrl(), HttpMethod.GET, new HttpEntity<>("body", headers), String.class);

        if (!response.hasBody() || response.getBody() == null || response.getStatusCode() != HttpStatus.OK)
            throw new DataFetchException("Unable to fetch world report. The server didn't report any results.");

        String responseBodyAsString = response.getBody();
        return JsonParser.parseString(responseBodyAsString).getAsJsonArray();

    }

    public JsonArray getCountryHistoryData(Country country) {

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("X-RapidAPI-Key", vacCovidAPI.getApiKey());

        ResponseEntity<String> response = restTemplate.exchange(String.format(API_ENDPOINTS.COUNTRY_HISTORY_INFO.getUrl(), country.getCode()), HttpMethod.GET, new HttpEntity<>("body", headers), String.class);

        if (!response.hasBody() || response.getBody() == null || response.getStatusCode() != HttpStatus.OK)
            throw new DataFetchException("Unable to fetch world report. The server didn't report any results.");

        String responseBodyAsString = response.getBody();
        return JsonParser.parseString(responseBodyAsString).getAsJsonArray();

    }

    @Getter
    @RequiredArgsConstructor
    enum API_ENDPOINTS {
        BASE_URL("https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com/api"),
        COUNTRY_INFO(BASE_URL.getUrl() + "/npm-covid-data/country-report-iso-based/%s/%s"),
        WORLD_INFO(BASE_URL.getUrl() + "/npm-covid-data/world"),
        ALL_COUNTRIES(BASE_URL.getUrl() + "/npm-covid-data/countries-name-ordered"),
        COUNTRY_HISTORY_INFO(BASE_URL.getUrl() + "/covid-ovid-data/sixmonth/%s");

        private final String url;

    }

}
