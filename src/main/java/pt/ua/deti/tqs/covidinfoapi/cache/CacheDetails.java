package pt.ua.deti.tqs.covidinfoapi.cache;

import lombok.Data;

@Data
public class CacheDetails {

    public int countOfRequests;
    public int hits;
    public int misses;
    public int cacheUpdateCount;

}
