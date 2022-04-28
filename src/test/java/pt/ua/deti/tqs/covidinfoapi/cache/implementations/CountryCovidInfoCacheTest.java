package pt.ua.deti.tqs.covidinfoapi.cache.implementations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.CachedCountryCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.CountryCovidInfo;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.covid19api.Covid19CountryCovidInfo;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CountryCovidInfoCacheTest {

    @Spy
    private CountryCovidInfoCache countryCovidInfoCache;

    @Mock
    private CachedCountryCovidInfo cachedCountryCovidInfoMock;

    @Test
    void setValue() {

        countryCovidInfoCache.setValue("Test", cachedCountryCovidInfoMock);

        assertThat(countryCovidInfoCache.getStorage().containsKey("Test")).isTrue();
        assertThat(countryCovidInfoCache.getStorage().get("Test")).isEqualTo(cachedCountryCovidInfoMock);

    }

    @Test
    void removeKey() {

        countryCovidInfoCache.getStorage().put("Test", cachedCountryCovidInfoMock);
        assertThat(countryCovidInfoCache.getStorage().containsKey("Test")).isTrue();

        countryCovidInfoCache.removeKey("Test");
        assertThat(countryCovidInfoCache.getStorage().containsKey("Test")).isFalse();

    }

    @Test
    void getValue() {

        assertThat(countryCovidInfoCache.getValue("Test")).isEqualTo(null);

        countryCovidInfoCache.getStorage().put("Test", cachedCountryCovidInfoMock);
        assertThat(countryCovidInfoCache.getValue("Test")).isEqualTo(cachedCountryCovidInfoMock);

    }

    @Test
    void containsKey() {

        assertThat(countryCovidInfoCache.containsKey("Test")).isFalse();

        countryCovidInfoCache.getStorage().put("Test", cachedCountryCovidInfoMock);

        assertThat(countryCovidInfoCache.containsKey("Test")).isTrue();

    }

    @Test
    void isValid() {

        assertThat(countryCovidInfoCache.isValid("Test")).isFalse();

        doReturn(true).when(cachedCountryCovidInfoMock).isValid();

        countryCovidInfoCache.getStorage().put("Test", cachedCountryCovidInfoMock);

        assertThat(countryCovidInfoCache.isValid("Test")).isTrue();

        doReturn(false).when(cachedCountryCovidInfoMock).isValid();

        assertThat(countryCovidInfoCache.isValid("Test")).isFalse();

    }

}