package pt.ua.deti.tqs.covidinfoapi.sourceapi.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.ExternalAPI;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.Country;

public interface IExternalApiRepository {

    ExternalAPI getApiInstance();

    JsonObject getCountryCovidInfo(Country country);

    JsonArray getAllCountries();

}
