package pt.ua.deti.tqs.covidinfoapi.cache.entities;

public interface IMultipleCache<K, V> {

    void setValue(K key, V value);

    void removeKey(K key);

    V getValue(K key);

    boolean containsKey(K key);

    boolean isValid(K key);

}
