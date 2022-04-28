package pt.ua.deti.tqs.covidinfoapi.cache.implementations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.vaccovid.VacCovidWorldCovidInfo;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WorldCovidInfoSingleCacheTest {

    @Spy
    private WorldCovidInfoSingleCache worldCovidInfoSingleCache;

    @Mock
    private VacCovidWorldCovidInfo covidWorldCovidInfoMock;

    @Test
    void getCachedValue() {

        assertThat(worldCovidInfoSingleCache.getCachedValue()).isEqualTo(null);
        worldCovidInfoSingleCache.setCachedValue(covidWorldCovidInfoMock);
        assertThat(worldCovidInfoSingleCache.getCachedValue()).isEqualTo(covidWorldCovidInfoMock);

    }

    @Test
    void setCachedValue() {

        assertThat(worldCovidInfoSingleCache.getCachedValue()).isEqualTo(null);
        worldCovidInfoSingleCache.setCachedValue(covidWorldCovidInfoMock);
        assertThat(worldCovidInfoSingleCache.getCachedValue()).isEqualTo(covidWorldCovidInfoMock);

    }

    @Test
    void isValid() {

        assertThat(worldCovidInfoSingleCache.isValid()).isFalse();

        worldCovidInfoSingleCache.setCachedValue(covidWorldCovidInfoMock, Date.from(Instant.now()));

        assertThat(worldCovidInfoSingleCache.isValid()).isTrue();

        worldCovidInfoSingleCache.setCachedValue(covidWorldCovidInfoMock, new Date(Date.from(Instant.now()).getTime() - WorldCovidInfoSingleCache.getCACHE_TTL() - 1));

        assertThat(worldCovidInfoSingleCache.isValid()).isFalse();

    }

}