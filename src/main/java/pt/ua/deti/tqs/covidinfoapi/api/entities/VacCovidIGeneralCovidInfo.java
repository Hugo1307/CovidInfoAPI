package pt.ua.deti.tqs.covidinfoapi.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class VacCovidIGeneralCovidInfo implements IGeneralCovidInfo {

    private UUID id;
    private int rank;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Infection_Risk")
    private double infectionRisk;

    @JsonProperty("Case_Fatality_Rate")
    private double caseFatalityRate;

    @JsonProperty("Test_Percentage")
    private double testPercentage;

    @JsonProperty("TotalCases")
    private int totalCases;

    @JsonProperty("NewCases")
    private int newCases;

    @JsonProperty("TotalDeaths")
    private int totalDeaths;

    @JsonProperty("NewDeaths")
    private int newDeaths;

    @JsonProperty("TotalRecovered")
    private int totalRecovered;

    @JsonProperty("NewRecovered")
    private int newRecovered;

    @JsonProperty("ActiveCases")
    private int activeCases;

    @JsonProperty("TotalTests")
    private int totalTests;

    @JsonProperty("Population")
    private int population;

    @JsonProperty("Deaths_1M_pop")
    private int deathsPerMillion;

    @JsonProperty("Tests_1M_Pop")
    private int testsPerMillion;

    @JsonProperty("TotCases_1M_Pop")
    private int casesPerMillion;

}
