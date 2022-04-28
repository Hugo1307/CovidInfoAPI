package pt.ua.deti.tqs.covidinfoapi;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
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
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public WorldCovidInfoSingleCache worldCovidInfoCache() {
        return new WorldCovidInfoSingleCache();
    }
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CountryCovidInfoCache countryCovidInfoCacheManager() {
        return new CountryCovidInfoCache();
    }
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CountryListCache countryListCache() {
        return new CountryListCache();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CacheDetails cacheDetails() {
        return new CacheDetails();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST","PUT", "DELETE");
            }
        };

    }

}
