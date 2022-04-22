package pt.ua.deti.tqs.covidinfoapi.sourceapi.services;

import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.Country;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.CountryCovidInfo;

import java.util.List;

public interface IExternalApiService {

    CountryCovidInfo getCountryCovidInfo(Country country);

    List<Country> getAllCountries();

}
