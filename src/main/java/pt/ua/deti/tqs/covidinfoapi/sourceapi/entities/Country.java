package pt.ua.deti.tqs.covidinfoapi.sourceapi.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Generated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {

    @JsonAlias("Country")
    private String name;

    @JsonAlias("ThreeLetterSymbol")
    private String code;

}
