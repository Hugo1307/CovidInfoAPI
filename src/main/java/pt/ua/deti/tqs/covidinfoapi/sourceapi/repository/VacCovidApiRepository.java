package pt.ua.deti.tqs.covidinfoapi.sourceapi.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

    public VacCovidApiRepository() {
        this.vacCovidAPI = new VacCovidAPI("https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com/api", "dbd92461c3msh03b2a93b4b69037p122734jsn4e1f7a225e85");
    }

    @Override
    public ExternalAPI getApiInstance() {
        return vacCovidAPI;
    }

    @Override
    public JsonObject getCountryCovidInfo(Country country) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("X-RapidAPI-Key", vacCovidAPI.getApiKey());

        ResponseEntity<String> response = restTemplate.exchange(vacCovidAPI.getBaseUrl() + String.format("/npm-covid-data/country-report-iso-based/%s/%s", country.getName(), country.getCode()), HttpMethod.GET, new HttpEntity<>("body", headers), String.class);

        if (!response.hasBody() || response.getBody() == null)
            throw new DataFetchException("Unable to fetch country report. The server didn't report any results.");

        String responseBodyAsString = response.getBody();

        JsonArray jsonArray = JsonParser.parseString(responseBodyAsString).getAsJsonArray();

        if (jsonArray.isEmpty())
            throw new NoDataFoundException("Unable to find data for that country.");
        return jsonArray.get(0).getAsJsonObject();

    }

    public JsonObject getWorldCovidInfo() {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("X-RapidAPI-Key", vacCovidAPI.getApiKey());

        ResponseEntity<String> response = restTemplate.exchange(vacCovidAPI.getBaseUrl() + "/npm-covid-data/world", HttpMethod.GET, new HttpEntity<>("body", headers), String.class);

        if (!response.hasBody() || response.getBody() == null)
            throw new DataFetchException("Unable to fetch world report. The server didn't report any results.");

        String responseBodyAsString = response.getBody();

        JsonArray jsonArray = JsonParser.parseString(responseBodyAsString).getAsJsonArray();

        if (jsonArray.isEmpty())
            throw new NoDataFoundException("Unable to find world's data.");
        return jsonArray.get(0).getAsJsonObject();

    }

    @Override
    public JsonArray getAllCountries() {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("X-RapidAPI-Key", vacCovidAPI.getApiKey());

        ResponseEntity<String> response = restTemplate.exchange(vacCovidAPI.getBaseUrl() + "/npm-covid-data/countries-name-ordered", HttpMethod.GET, new HttpEntity<>("body", headers), String.class);

        if (!response.hasBody() || response.getBody() == null)
            throw new DataFetchException("Unable to fetch world report. The server didn't report any results.");

        String responseBodyAsString = response.getBody();
        return JsonParser.parseString(responseBodyAsString).getAsJsonArray();

    }

}
