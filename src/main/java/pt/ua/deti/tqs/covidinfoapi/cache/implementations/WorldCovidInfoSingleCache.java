package pt.ua.deti.tqs.covidinfoapi.cache.implementations;

import lombok.Generated;
import lombok.Getter;
import pt.ua.deti.tqs.covidinfoapi.cache.entities.ISingleCache;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.vaccovid.VacCovidWorldCovidInfo;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

public class WorldCovidInfoSingleCache implements ISingleCache<VacCovidWorldCovidInfo> {

    @Generated
    @Getter
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

    public void setCachedValue(VacCovidWorldCovidInfo vacCovidWorldCovidInfo, Date timestamp) {
        this.worldCovidInfo = vacCovidWorldCovidInfo;
        this.timestamp = timestamp;
    }

    public boolean isValid() {
        if (worldCovidInfo == null)
            return false;
        System.out.println(Date.from(Instant.now()).getTime() - timestamp.getTime());
        return Date.from(Instant.now()).getTime() - timestamp.getTime() <= CACHE_TTL;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorldCovidInfoSingleCache that = (WorldCovidInfoSingleCache) o;
        return Objects.equals(worldCovidInfo, that.worldCovidInfo) && Objects.equals(timestamp, that.timestamp);
    }

    @Generated
    @Override
    public String toString() {
        return "WorldCovidInfoSingleCache{" +
                "worldCovidInfo=" + worldCovidInfo +
                ", timestamp=" + timestamp +
                '}';
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(worldCovidInfo, timestamp);
    }

}
