package pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.vaccovid;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.Date;

@Generated
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@AllArgsConstructor
@NoArgsConstructor
public class VacCovidCountryHistoryData {

    @JsonAlias("Country")
    private String country;

    @JsonAlias("date")
    private Date date;

    @JsonAlias("total_cases")
    private int totalCases;

    @JsonAlias("new_deaths")
    private int newDeaths;

    @JsonAlias("total_tests")
    private int totalTests;

    @JsonAlias("new_tests")
    private int newTests;

}
