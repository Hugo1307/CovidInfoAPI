package pt.ua.deti.tqs.covidinfoapi.cache.entities;


public interface ISingleCache<T> {

    T getCachedValue();

    void setCachedValue(T t);

    boolean isValid();

}
