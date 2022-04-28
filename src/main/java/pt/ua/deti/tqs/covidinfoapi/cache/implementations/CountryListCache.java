package pt.ua.deti.tqs.covidinfoapi.cache.implementations;

import lombok.Generated;
import lombok.Getter;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.IMultipleCache;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.CachedCountriesList;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.ExternalAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CountryListCache implements IMultipleCache<ExternalAPI.AvailableAPI, CachedCountriesList> {

    @Generated
    @Getter
    private final Map<ExternalAPI.AvailableAPI, CachedCountriesList> storage = new HashMap<>();

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

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryListCache that = (CountryListCache) o;
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
        return "CountryListCache{" +
                "storage=" + storage +
                '}';
    }

}
