package pt.ua.deti.tqs.covidinfoapi.cache;

import lombok.Generated;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.ISingleCache;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.IMultipleCache;
import pt.ua.deti.tqs.covidinfoapi.cache.implementations.CountryListCache;

@Component
@Getter
@Generated
public class CacheManager {

    private final CountryListCache countryListCache;

    @Autowired
    public CacheManager(CountryListCache countryListCache) {
        this.countryListCache = countryListCache;
    }

    public <T> T getCachedValue(ISingleCache<T> cache) {
        return cache.getCachedValue();
    }

    public <K, V> V getCachedValue(K k, IMultipleCache<K, V> multipleCache) {
        return multipleCache.getValue(k);
    }

    public <T> boolean isValid(ISingleCache<T> cache) {
        return cache.isValid();
    }

    public <K, V> boolean isValid(K k, IMultipleCache<K, V> cache) {
        return cache.isValid(k);
    }

    public <T> void updateCachedValue(T t, ISingleCache<T> cache) {
        cache.setCachedValue(t);
    }

    public <K, V> void updateCachedValue(K k, V v, IMultipleCache<K, V> multipleCache) {
        multipleCache.setValue(k, v);
    }

}
