package lru;

public interface LRUCache<T, R> {
    R get(T key);

    void put (T key, R value);
}
