package pt.ua.deti.tqs.covidinfoapi.sourceapi.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.DataFetchException;
import pt.ua.deti.tqs.covidinfoapi.exception.implementations.NoDataFoundException;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.Covid19API;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.ExternalAPI;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.Country;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class Covid19ApiRepository implements IExternalApiRepository{

    private final Covid19API covid19API;

    public Covid19ApiRepository() {
        this.covid19API = new Covid19API("https://covid-193.p.rapidapi.com", "dbd92461c3msh03b2a93b4b69037p122734jsn4e1f7a225e85");
    }

    @Override
    public ExternalAPI getApiInstance() {
        return covid19API;
    }

    @Override
    public JsonObject getCountryCovidInfo(Country country) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> params = new HashMap<>();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("X-RapidAPI-Key", covid19API.getApiKey());

        params.put("country", country.getName());

        ResponseEntity<String> response = restTemplate.exchange(covid19API.getBaseUrl() + "/statistics?country={country}", HttpMethod.GET, new HttpEntity<>("body", headers), String.class, params);

        if (!response.hasBody() || response.getBody() == null)
            throw new DataFetchException("Unable to fetch country report. The server didn't report any results.");

        String responseBodyAsString = response.getBody();

        JsonArray responseJsonArray = JsonParser.parseString(responseBodyAsString).getAsJsonObject()
                .get("response").getAsJsonArray();

        if (responseJsonArray.size() == 0)
            throw new NoDataFoundException("Unable to find data for that country.");

        JsonObject responseJsonObj = responseJsonArray.get(0).getAsJsonObject();

        if (responseJsonObj == null || responseJsonObj.isJsonNull())
            throw new NoDataFoundException("Unable to find data for that country.");
        return responseJsonObj;

    }

    @Override
    public JsonArray getAllCountries() {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("X-RapidAPI-Key", covid19API.getApiKey());

        ResponseEntity<String> response = restTemplate.exchange(covid19API.getBaseUrl() + "/countries", HttpMethod.GET, new HttpEntity<>("body", headers), String.class);

        if (!response.hasBody() || response.getBody() == null)
            throw new DataFetchException("Unable to fetch country report. The server didn't report any results.");

        String responseBodyAsString = response.getBody();

        JsonArray responseJsonArray = JsonParser.parseString(responseBodyAsString).getAsJsonObject()
                .get("response").getAsJsonArray();

        if (responseJsonArray == null || responseJsonArray.isJsonNull())
            throw new NoDataFoundException("Unable to find data for that country.");
        return responseJsonArray;
    }

}
