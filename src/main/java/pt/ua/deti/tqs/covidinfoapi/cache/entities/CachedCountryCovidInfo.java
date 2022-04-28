package pt.ua.deti.tqs.covidinfoapi.cache.entities;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.CountryCovidInfo;

import java.time.Instant;
import java.util.Date;

@AllArgsConstructor
public class CachedCountryCovidInfo implements ISingleCache<CountryCovidInfo> {

    @Generated
    @Getter
    private final static int CACHE_TTL = 5*60*1000;

    private CountryCovidInfo countryCovidInfo;
    private Date timestamp;

    public CachedCountryCovidInfo() {
        this.countryCovidInfo = null;
        this.timestamp = new Date(0L);
    }

    public CountryCovidInfo getCachedValue() {
        return countryCovidInfo;
    }

    public void setCachedValue(CountryCovidInfo countryCovidInfo) {
        this.countryCovidInfo = countryCovidInfo;
        this.timestamp = Date.from(Instant.now());
    }

    public void setCachedValue(CountryCovidInfo countryCovidInfo, Date timestamp) {
        this.countryCovidInfo = countryCovidInfo;
        this.timestamp = timestamp;
    }

    public boolean isValid() {
        if (countryCovidInfo == null)
            return false;
        return Date.from(Instant.now()).getTime() - timestamp.getTime() <= CACHE_TTL;
    }

}
