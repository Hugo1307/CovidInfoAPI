package pt.ua.deti.tqs.covidinfoapi.cache.implementations;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.IMultipleCache;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.CachedCountriesList;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.ExternalAPI;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
@ToString
public class CountryListCache implements IMultipleCache<ExternalAPI.AvailableAPI, CachedCountriesList> {

    private static final Map<ExternalAPI.AvailableAPI, CachedCountriesList> storage = new HashMap<>();

    @Override
    public void setValue(ExternalAPI.AvailableAPI key, CachedCountriesList value) {
        storage.put(key, value);
    }

    @Override
    public void removeKey(ExternalAPI.AvailableAPI key) {
        storage.remove(key);
    }

    @Override
    public CachedCountriesList getValue(ExternalAPI.AvailableAPI key) {
        return storage.get(key);
    }

    @Override
    public boolean containsKey(ExternalAPI.AvailableAPI key) {
        return storage.containsKey(key);
    }

    @Override
    public boolean isValid(ExternalAPI.AvailableAPI key) {
        return containsKey(key) && storage.get(key).isValid();
    }
}
