package pt.ua.deti.tqs.covidinfoapi.cache.implementations;

import lombok.Generated;
import lombok.Getter;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.IMultipleCache;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.CachedCountryCovidInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CountryCovidInfoCache implements IMultipleCache<String, CachedCountryCovidInfo> {

    @Generated
    @Getter
    private final Map<String, CachedCountryCovidInfo> storage = new HashMap<>();

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

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryCovidInfoCache that = (CountryCovidInfoCache) o;
        return Objects.equals(storage, that.storage);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(storage);
    }

    @Generated
    @Override
    public String toString() {
        return "CountryCovidInfoCache{" +
                "storage=" + storage +
                '}';
    }

}
