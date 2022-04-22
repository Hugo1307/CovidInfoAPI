package pt.ua.deti.tqs.covidinfoapi.cache.implementations;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.ISingleCache;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.vaccovid.VacCovidWorldCovidInfo;

import java.time.Instant;
import java.util.Date;

@EqualsAndHashCode
@ToString
public class WorldCovidInfoSingleCache implements ISingleCache<VacCovidWorldCovidInfo> {

    private final static int CACHE_TTL = 15*60*1000;

    private VacCovidWorldCovidInfo worldCovidInfo;
    private Date timestamp;

    public VacCovidWorldCovidInfo getCachedValue() {
        return worldCovidInfo;
    }

    public void setCachedValue(VacCovidWorldCovidInfo vacCovidWorldCovidInfo) {
        this.worldCovidInfo = vacCovidWorldCovidInfo;
        this.timestamp = Date.from(Instant.now());
    }

    public boolean isValid() {
        if (worldCovidInfo == null)
            return false;
        return Date.from(Instant.now()).getTime() - timestamp.getTime() <= CACHE_TTL;
    }

}
