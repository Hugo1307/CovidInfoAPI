package pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.vaccovid;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.CountryCovidInfo;

@Generated
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class VacCovidCountryCovidInfo extends CountryCovidInfo {

    public VacCovidCountryCovidInfo(String country, int population, int totalCases, int newCases, int totalDeaths, int newDeaths, int totalRecovered, int newRecovered, int activeCases, int rank, double infectionRisk, double caseFatalityRate, double testPercentage, int totalTests, int deathsPerMillion, int testsPerMillion, int casesPerMillion) {
        super(country, population, totalCases, newCases, totalDeaths, newDeaths, totalRecovered, newRecovered, activeCases);
        this.rank = rank;
        this.infectionRisk = infectionRisk;
        this.caseFatalityRate = caseFatalityRate;
        this.testPercentage = testPercentage;
        this.totalTests = totalTests;
        this.deathsPerMillion = deathsPerMillion;
        this.testsPerMillion = testsPerMillion;
        this.casesPerMillion = casesPerMillion;
    }

    private int rank;

    @JsonAlias("Infection_Risk")
    private double infectionRisk;

    @JsonAlias("Case_Fatality_Rate")
    private double caseFatalityRate;

    @JsonAlias("Test_Percentage")
    private double testPercentage;

    @JsonAlias("TotalTests")
    private int totalTests;

    @JsonAlias("Deaths_1M_pop")
    private int deathsPerMillion;

    @JsonAlias("Tests_1M_Pop")
    private int testsPerMillion;

    @JsonAlias("TotCases_1M_Pop")
    private int casesPerMillion;

}
