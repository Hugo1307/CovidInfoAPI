package pt.ua.deti.tqs.covidinfoapi.cache;

import lombok.Data;
import lombok.Generated;
import lombok.ToString;

@Generated
@Data
@ToString
public class CacheDetails {

    public int countOfUsages;
    public int hits;
    public int misses;
    public int cacheUpdateCount;

}
