package pt.ua.deti.tqs.covidinfoapi.cache;

import lombok.Generated;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ua.deti.tqs.covidinfoapi.cache.implementations.CountryCovidInfoCache;
import pt.ua.deti.tqs.covidinfoapi.cache.implementations.CountryListCache;
import pt.ua.deti.tqs.covidinfoapi.cache.implementations.WorldCovidInfoSingleCache;

@Component
@Generated
@Getter
public class CacheInjector {

    protected final CountryListCache countryListCache;
    private final CountryCovidInfoCache countryCovidInfoCache;
    private final WorldCovidInfoSingleCache worldCovidInfoCache;

    @Autowired
    public CacheInjector(CountryListCache countryListCache, CountryCovidInfoCache countryCovidInfoCache, WorldCovidInfoSingleCache worldCovidInfoCache) {
        this.countryListCache = countryListCache;
        this.countryCovidInfoCache = countryCovidInfoCache;
        this.worldCovidInfoCache = worldCovidInfoCache;
    }

}
