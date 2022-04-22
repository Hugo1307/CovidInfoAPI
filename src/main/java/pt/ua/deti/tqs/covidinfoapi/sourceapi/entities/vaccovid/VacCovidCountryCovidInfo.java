package pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.vaccovid;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.CountryCovidInfo;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@EqualsAndHashCode(callSuper = true)
public class VacCovidCountryCovidInfo extends CountryCovidInfo {

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
