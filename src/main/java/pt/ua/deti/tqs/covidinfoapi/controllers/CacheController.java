package pt.ua.deti.tqs.covidinfoapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.deti.tqs.covidinfoapi.cache.CacheDetails;

@RestController
@RequestMapping("/cache")
public class CacheController {

    private final CacheDetails cacheDetails;

    @Autowired
    public CacheController(CacheDetails cacheDetails) {
        this.cacheDetails = cacheDetails;
    }

    @GetMapping(value = "/details")
    ResponseEntity<?> getCacheDetails() {
        return new ResponseEntity<>(cacheDetails, HttpStatus.OK);
    }

}
