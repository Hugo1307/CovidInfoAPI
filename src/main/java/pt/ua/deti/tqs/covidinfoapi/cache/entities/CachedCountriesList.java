package pt.ua.deti.tqs.covidinfoapi.cache.entities;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.Country;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class CachedCountriesList implements ISingleCache<List<Country>> {

    @Generated
    @Getter
    private final static int CACHE_TTL = 60*60*1000;

    @Generated
    @Getter
    private List<Country> countriesList;
    private Date timestamp;

    public CachedCountriesList() {
        this.countriesList = new ArrayList<>();
        this.timestamp = new Date(0L);
    }

    public List<Country> getCachedValue() {
        return countriesList;
    }

    public void setCachedValue(List<Country> countriesList) {
        this.countriesList = countriesList;
        this.timestamp = Date.from(Instant.now());
    }

    public void setCachedValue(List<Country> countriesList, Date timestamp) {
        this.countriesList = countriesList;
        this.timestamp = timestamp;
    }

    public boolean isValid() {
        if (countriesList == null)
            return false;
        return Date.from(Instant.now()).getTime() - timestamp.getTime() <= CACHE_TTL;
    }

}
