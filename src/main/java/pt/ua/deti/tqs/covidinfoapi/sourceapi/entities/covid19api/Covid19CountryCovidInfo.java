package pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.covid19api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.CountryCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.covid19api.deserializers.Covid19CountryInfoDeserializer;

@Generated
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonDeserialize(using = Covid19CountryInfoDeserializer.class)
public class Covid19CountryCovidInfo extends CountryCovidInfo {

    public Covid19CountryCovidInfo(String country, int totalCases, int newCases, int totalDeaths, int newDeaths, int totalRecovered, int newRecovered, int activeCases, int population) {
        super(country, totalCases, newCases, totalDeaths, newDeaths, totalRecovered, newRecovered, activeCases, population);
    }

}
