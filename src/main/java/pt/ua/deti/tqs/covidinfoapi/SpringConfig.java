package pt.ua.deti.tqs.covidinfoapi;

import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pt.ua.deti.tqs.covidinfoapi.controllers.ControllerInterceptor;

@Configuration
public class SpringConfig implements WebMvcConfigurer {

    private final ControllerInterceptor controllerInterceptor;

    @Generated
    @Autowired
    public SpringConfig(ControllerInterceptor controllerInterceptor) {
        this.controllerInterceptor = controllerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(controllerInterceptor).addPathPatterns("/**");
    }

}
