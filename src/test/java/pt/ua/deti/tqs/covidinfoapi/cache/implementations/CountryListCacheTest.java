package pt.ua.deti.tqs.covidinfoapi.cache.implementations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.CachedCountriesList;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.ExternalAPI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CountryListCacheTest {

    @Spy
    private CountryListCache countryListCache;

    @Mock
    private CachedCountriesList cachedCountriesList;

    @Test
    void setValue() {

        countryListCache.setValue(ExternalAPI.AvailableAPI.VAC_COVID, cachedCountriesList);

        assertThat(countryListCache.getStorage().containsKey(ExternalAPI.AvailableAPI.VAC_COVID)).isTrue();
        assertThat(countryListCache.getStorage().get(ExternalAPI.AvailableAPI.VAC_COVID)).isEqualTo(cachedCountriesList);

    }

    @Test
    void removeKey() {

        countryListCache.getStorage().put(ExternalAPI.AvailableAPI.VAC_COVID, cachedCountriesList);

        assertThat(countryListCache.getStorage().containsKey(ExternalAPI.AvailableAPI.VAC_COVID)).isTrue();

        countryListCache.removeKey(ExternalAPI.AvailableAPI.VAC_COVID);

        assertThat(countryListCache.getStorage().containsKey(ExternalAPI.AvailableAPI.VAC_COVID)).isFalse();

    }

    @Test
    void getValue() {

        assertThat(countryListCache.getValue(ExternalAPI.AvailableAPI.VAC_COVID)).isEqualTo(null);

        countryListCache.getStorage().put(ExternalAPI.AvailableAPI.VAC_COVID, cachedCountriesList);

        assertThat(countryListCache.getValue(ExternalAPI.AvailableAPI.VAC_COVID)).isEqualTo(cachedCountriesList);

    }

    @Test
    void containsKey() {

        assertThat(countryListCache.containsKey(ExternalAPI.AvailableAPI.VAC_COVID)).isFalse();

        countryListCache.getStorage().put(ExternalAPI.AvailableAPI.VAC_COVID, cachedCountriesList);

        assertThat(countryListCache.containsKey(ExternalAPI.AvailableAPI.VAC_COVID)).isTrue();
        assertThat(countryListCache.containsKey(ExternalAPI.AvailableAPI.COVID_19)).isFalse();

    }

    @Test
    void isValid() {

        assertThat(countryListCache.isValid(ExternalAPI.AvailableAPI.VAC_COVID)).isFalse();

        doReturn(true).when(cachedCountriesList).isValid();
        countryListCache.getStorage().put(ExternalAPI.AvailableAPI.VAC_COVID, cachedCountriesList);
        assertThat(countryListCache.isValid(ExternalAPI.AvailableAPI.VAC_COVID)).isTrue();

        doReturn(false).when(cachedCountriesList).isValid();
        assertThat(countryListCache.isValid(ExternalAPI.AvailableAPI.VAC_COVID)).isFalse();

    }

}