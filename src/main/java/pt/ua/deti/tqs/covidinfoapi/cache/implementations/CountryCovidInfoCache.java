package pt.ua.deti.tqs.covidinfoapi.cache.implementations;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.IMultipleCache;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.CachedCountryCovidInfo;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
@ToString
public class CountryCovidInfoCache implements IMultipleCache<String, CachedCountryCovidInfo> {

    private static final Map<String, CachedCountryCovidInfo> storage = new HashMap<>();

    @Override
    public void setValue(String key, CachedCountryCovidInfo value) {
        storage.put(key, value);
    }

    @Override
    public void removeKey(String key) {
        storage.remove(key);
    }

    @Override
    public CachedCountryCovidInfo getValue(String key) {
        return storage.get(key);
    }

    @Override
    public boolean containsKey(String key) {
        return storage.containsKey(key);
    }

    @Override
    public boolean isValid(String key) {
        return containsKey(key) && storage.get(key).isValid();
    }

}
