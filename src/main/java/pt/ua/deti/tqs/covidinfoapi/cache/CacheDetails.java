package pt.ua.deti.tqs.covidinfoapi.cache;

import lombok.Data;
import lombok.Generated;

@Generated
@Data
public class CacheDetails {

    public int countOfRequests;
    public int hits;
    public int misses;
    public int cacheUpdateCount;

}
