package com.redisson.cache;

public interface Cache {
    Object get(String key);
    void put(String key, Object value);
    void put(String key, Object value, int timeToIdleAndLiveSeconds);
    boolean remove(String key);
    boolean removeByKeyPrefix(String keyPrefix);

}

