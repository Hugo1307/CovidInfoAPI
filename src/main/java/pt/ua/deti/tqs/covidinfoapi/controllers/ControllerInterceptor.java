package pt.ua.deti.tqs.covidinfoapi.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Log4j2
public class ControllerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        HttpServletRequest requestCacheWrapperObject = new ContentCachingRequestWrapper(request);
        requestCacheWrapperObject.getParameterMap();

        log.info(String.format("Received request for %s %s.", request.getMethod(), request.getRequestURI()));

        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        HttpServletRequest requestCacheWrapperObject = new ContentCachingRequestWrapper(request);
        requestCacheWrapperObject.getParameterMap();

        if (ex == null)
            log.info(String.format("Completed request for %s %s.", request.getMethod(), request.getRequestURI()));
        else
            log.info(String.format("Completed request for %s %s with exception %s.", request.getMethod(), request.getRequestURI(), ex.getMessage()));

    }

}
