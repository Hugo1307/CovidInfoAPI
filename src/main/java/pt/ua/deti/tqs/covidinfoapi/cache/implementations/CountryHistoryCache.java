package pt.ua.deti.tqs.covidinfoapi.cache.implementations;

import pt.ua.deti.tqs.covidinfoapi.cache.entities.CachedCountryHistoryInfo;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.IMultipleCache;

import java.util.HashMap;
import java.util.Map;

public class CountryHistoryCache implements IMultipleCache<String, CachedCountryHistoryInfo> {

    private final Map<String, CachedCountryHistoryInfo> storage = new HashMap<>();

    @Override
    public void setValue(String key, CachedCountryHistoryInfo value) {
        storage.put(key, value);
    }

    @Override
    public void removeKey(String key) {
        storage.remove(key);
    }

    @Override
    public CachedCountryHistoryInfo getValue(String key) {
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
