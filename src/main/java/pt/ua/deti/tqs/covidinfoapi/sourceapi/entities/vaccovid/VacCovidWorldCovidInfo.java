package pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.vaccovid;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Generated
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@AllArgsConstructor
@NoArgsConstructor
public class VacCovidWorldCovidInfo {

    @JsonAlias("TotalCases")
    private int totalCases;

    @JsonAlias("NewCases")
    private int newCases;

    @JsonAlias("TotalDeaths")
    private int totalDeaths;

    @JsonAlias("NewDeaths")
    private int newDeaths;

    @JsonAlias("TotalRecovered")
    private int totalRecovered;

    @JsonAlias("NewRecovered")
    private int newRecovered;

    @JsonAlias("ActiveCases")
    private int activeCases;

    @JsonAlias("Deaths_1M_pop")
    private double deathsPerMillion;

    @JsonAlias("Case_Fatality_Rate")
    private double caseFatalityRate;

}
