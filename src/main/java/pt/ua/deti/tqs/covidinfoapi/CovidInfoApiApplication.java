package pt.ua.deti.tqs.covidinfoapi;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import pt.ua.deti.tqs.covidinfoapi.cache.CacheDetails;
import pt.ua.deti.tqs.covidinfoapi.cache.implementations.CountryListCache;
import pt.ua.deti.tqs.covidinfoapi.cache.implementations.CountryCovidInfoCache;
import pt.ua.deti.tqs.covidinfoapi.cache.implementations.WorldCovidInfoSingleCache;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class CovidInfoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CovidInfoApiApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
    @Bean
    public WorldCovidInfoSingleCache worldCovidInfoCache() {
        return new WorldCovidInfoSingleCache();
    }
    @Bean
    public CountryCovidInfoCache countryCovidInfoCacheManager() {
        return new CountryCovidInfoCache();
    }
    @Bean
    public CountryListCache countryListCache() {
        return new CountryListCache();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CacheDetails cacheDetails() {
        return new CacheDetails();
    }

}
