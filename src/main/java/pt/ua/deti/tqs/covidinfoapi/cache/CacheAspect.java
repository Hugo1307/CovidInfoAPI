package pt.ua.deti.tqs.covidinfoapi.cache;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class CacheAspect {

    private final CacheDetails cacheDetails;

    @Autowired
    public CacheAspect(CacheDetails cacheDetails) {
        this.cacheDetails = cacheDetails;
    }

    @Pointcut("execution(* CacheManager.getCachedValue(..))")
    public void cacheAccessed() {}

    @Pointcut("execution(* CacheManager.isValid(..))")
    public void cacheVerified() {}

    @Pointcut("execution(* CacheManager.updateCachedValue(..))")
    public void cacheUpdated() {}

    @After("cacheAccessed()")
    public void cacheAccessedAdvice(JoinPoint jp) {

        if (jp.getArgs().length < 2)
            log.info(String.format("Internal cache %s was accessed by %s.", jp.getArgs()[0].getClass().getSimpleName(), jp.getTarget().getClass().getSimpleName()));
        else
            log.info(String.format("Internal cache %s was accessed by %s with args «%s».", jp.getArgs()[1].getClass().getSimpleName(), jp.getTarget().getClass().getSimpleName(), jp.getArgs()[0]));

        cacheDetails.setCountOfUsages(cacheDetails.getCountOfUsages()+1);

    }

    @AfterReturning(pointcut = "cacheVerified()", returning = "isValid")
    public void cacheVerifiedAdvice(JoinPoint jp, Boolean isValid) {

        if (jp.getArgs().length < 2)
            log.info(String.format("Internal cache %s was verified by %s.", jp.getArgs()[0].getClass().getSimpleName(), jp.getTarget().getClass().getSimpleName()));
        else
            log.info(String.format("Internal cache %s was verified by %s with args «%s».", jp.getArgs()[1].getClass().getSimpleName(), jp.getTarget().getClass().getSimpleName(), jp.getArgs()[0]));

        if (isValid)
            cacheDetails.setHits(cacheDetails.getHits()+1);
        else
            cacheDetails.setMisses(cacheDetails.getMisses()+1);
    }

    @After("cacheUpdated()")
    public void cacheUpdatedAdvice(JoinPoint jp) {

        if (jp.getArgs().length < 2)
            log.info(String.format("Internal cache %s was updated by %s.", jp.getArgs()[0].getClass().getSimpleName(), jp.getTarget().getClass().getSimpleName()));
        else
            log.info(String.format("Internal cache %s was updated by %s with args «%s».", jp.getArgs()[1].getClass().getSimpleName(), jp.getTarget().getClass().getSimpleName(), jp.getArgs()[0]));

        cacheDetails.setCacheUpdateCount(cacheDetails.getCacheUpdateCount()+1);

    }

}
