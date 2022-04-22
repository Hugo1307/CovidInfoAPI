package pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.covid19api.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.covid19api.Covid19CountryCovidInfo;

import java.io.IOException;

public class Covid19CountryInfoDeserializer extends StdDeserializer<Covid19CountryCovidInfo> {

    public Covid19CountryInfoDeserializer() {
        this(null);
    }

    protected Covid19CountryInfoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Covid19CountryCovidInfo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String country = node.get("country").asText();
        int population = node.get("population").asInt();

        int totalCases = node.get("cases").get("total").isNull() ? 0 : node.get("cases").get("total").asInt();
        int newCases = node.get("cases").get("new").isNull() ? 0 : node.get("cases").get("new").asInt();
        int activeCases = node.get("cases").get("active").isNull() ? 0 : node.get("cases").get("active").asInt();
        int totalRecovered = node.get("cases").get("recovered").isNull() ? 0 : node.get("cases").get("recovered").asInt();

        int totalDeaths = node.get("deaths").get("total").isNull() ? 0 : node.get("deaths").get("total").asInt();
        int newDeaths = node.get("deaths").get("new").isNull() ? 0 : node.get("deaths").get("new").asInt();

        return new Covid19CountryCovidInfo(country, totalCases, newCases, totalDeaths, newDeaths, totalRecovered, 0, activeCases, population);

    }

}
