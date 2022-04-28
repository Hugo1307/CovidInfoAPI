package pt.ua.deti.tqs.covidinfoapi.cache;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.Import;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.ExternalAPI;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.vaccovid.VacCovidWorldCovidInfo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith({OutputCaptureExtension.class, MockitoExtension.class})
class CacheAspectTest {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CacheInjector cacheInjector;

    @Mock
    private VacCovidWorldCovidInfo worldCovidInfo;

    @Test
    void cacheAccessedAdvice(CapturedOutput capturedOutput) {

        cacheManager.getCachedValue(cacheInjector.getWorldCovidInfoCache());
        assertThat(capturedOutput).contains("Internal cache WorldCovidInfoSingleCache was accessed by CacheManager.");

    }

    @Test
    void cacheAccessedAdvice_multipleCache(CapturedOutput capturedOutput) {

        cacheManager.getCachedValue(ExternalAPI.AvailableAPI.VAC_COVID, cacheInjector.getCountryListCache());
        assertThat(capturedOutput).contains("Internal cache CountryListCache was accessed by CacheManager with args");

    }

    @Test
    void cacheVerifiedAdvice(CapturedOutput capturedOutput) {

        cacheManager.isValid(cacheInjector.getWorldCovidInfoCache());
        assertThat(capturedOutput).contains("Internal cache WorldCovidInfoSingleCache was verified by CacheManager.");

    }

    @Test
    void cacheVerifiedAdvice_multipleCache(CapturedOutput capturedOutput) {

        cacheManager.isValid(ExternalAPI.AvailableAPI.VAC_COVID, cacheInjector.getCountryListCache());
        assertThat(capturedOutput).contains("Internal cache CountryListCache was verified by CacheManager with args");

    }

    @Test
    void cacheUpdatedAdvice(CapturedOutput capturedOutput) {

        cacheManager.updateCachedValue(worldCovidInfo, cacheInjector.getWorldCovidInfoCache());
        assertThat(capturedOutput).contains("Internal cache WorldCovidInfoSingleCache was updated by CacheManager with args");

    }

}