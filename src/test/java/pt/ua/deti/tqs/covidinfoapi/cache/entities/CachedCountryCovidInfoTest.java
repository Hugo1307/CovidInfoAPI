package pt.ua.deti.tqs.covidinfoapi.cache.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.CountryCovidInfo;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CachedCountryCovidInfoTest {
    @Spy
    private CachedCountryCovidInfo cachedCountryCovidInfo;

    @Mock
    private CountryCovidInfo countryCovidInfoMock;

    @Test
    void getCachedValue() {

        cachedCountryCovidInfo.setCachedValue(countryCovidInfoMock);
        assertThat(cachedCountryCovidInfo.getCachedValue()).isEqualTo(countryCovidInfoMock);

    }

    @Test
    void setCachedValue() {

        assertThat(cachedCountryCovidInfo.getCachedValue()).isNull();
        cachedCountryCovidInfo.setCachedValue(countryCovidInfoMock);
        assertThat(cachedCountryCovidInfo.getCachedValue()).isEqualTo(countryCovidInfoMock);

    }

    @Test
    void isValid() {

        assertThat(cachedCountryCovidInfo.isValid()).isFalse();

        cachedCountryCovidInfo.setCachedValue(countryCovidInfoMock, Date.from(Instant.now()));

        assertThat(cachedCountryCovidInfo.isValid()).isTrue();

        cachedCountryCovidInfo.setCachedValue(countryCovidInfoMock, new Date(Date.from(Instant.now()).getTime() - CachedCountriesList.getCACHE_TTL() - 1));

        assertThat(cachedCountryCovidInfo.isValid()).isFalse();

    }

}