package pt.ua.deti.tqs.covidinfoapi.cache.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ua.deti.tqs.covidinfoapi.cache.implementations.WorldCovidInfoSingleCache;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.Country;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CachedCountriesListTest {

    @Spy
    private CachedCountriesList cachedCountriesList;

    @Mock
    private Country country;

    @Test
    void getCachedValue() {

        assertThat(cachedCountriesList.getCachedValue()).isNotNull().hasSize(0);

        cachedCountriesList.getCountriesList().add(country);

        assertThat(cachedCountriesList.getCachedValue()).hasSize(1);
        assertThat(cachedCountriesList.getCachedValue()).contains(country);

    }

    @Test
    void setCachedValue() {

        assertThat(cachedCountriesList.getCachedValue()).isNotNull().hasSize(0);

        cachedCountriesList.setCachedValue(List.of(country, country));

        assertThat(cachedCountriesList.getCachedValue()).hasSize(2);
        assertThat(cachedCountriesList.getCachedValue()).contains(country);

    }

    @Test
    void isValid() {

        assertThat(cachedCountriesList.isValid()).isFalse();

        cachedCountriesList.setCachedValue(List.of(country), Date.from(Instant.now()));

        assertThat(cachedCountriesList.isValid()).isTrue();

        cachedCountriesList.setCachedValue(List.of(country), new Date(Date.from(Instant.now()).getTime() - CachedCountriesList.getCACHE_TTL() - 1));

        assertThat(cachedCountriesList.isValid()).isFalse();

    }

}