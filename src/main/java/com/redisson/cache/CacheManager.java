package com.redisson.cache;

public interface CacheManager {
    Cache getCache();
    void clearCache();
}

