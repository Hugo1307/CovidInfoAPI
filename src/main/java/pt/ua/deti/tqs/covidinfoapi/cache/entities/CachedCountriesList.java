package pt.ua.deti.tqs.covidinfoapi.cache.entities;

import lombok.AllArgsConstructor;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.Country;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class CachedCountriesList implements ISingleCache<List<Country>> {

    private final static int CACHE_TTL = 60*60*1000;

    private List<Country> countriesList;
    private Date timestamp;

    public List<Country> getCachedValue() {
        return countriesList;
    }

    public void setCachedValue(List<Country> countriesList) {
        this.countriesList = countriesList;
        this.timestamp = Date.from(Instant.now());
    }

    public boolean isValid() {
        if (countriesList == null)
            return false;
        return Date.from(Instant.now()).getTime() - timestamp.getTime() <= CACHE_TTL;
    }

}
