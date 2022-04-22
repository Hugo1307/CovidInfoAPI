package pt.ua.deti.tqs.covidinfoapi.cache.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.CountryCovidInfo;

import java.time.Instant;
import java.util.Date;

@Getter
@AllArgsConstructor
public class CachedCountryCovidInfo implements ISingleCache<CountryCovidInfo> {

    private final static int CACHE_TTL = 5*60*1000;

    private CountryCovidInfo countryCovidInfo;
    private Date timestamp;

    public CountryCovidInfo getCachedValue() {
        return countryCovidInfo;
    }

    public void setCachedValue(CountryCovidInfo countryCovidInfo) {
        this.countryCovidInfo = countryCovidInfo;
        this.timestamp = Date.from(Instant.now());
    }

    public boolean isValid() {
        if (countryCovidInfo == null)
            return false;
        return Date.from(Instant.now()).getTime() - timestamp.getTime() <= CACHE_TTL;
    }


}
