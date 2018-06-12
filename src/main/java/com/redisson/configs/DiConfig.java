package com.redisson.configs;


import com.google.inject.AbstractModule;
import com.redisson.cache.*;
import org.redisson.api.RedissonClient;

public class DiConfig extends AbstractModule {
    @Override
    protected void configure() {
        bind(RedissonClient.class).toProvider(RedissonCacheManagerProvider.class);
        bind(CacheManager.class).to(RedissonCachemanagerWrapper.class);
        bind(Cache.class).to(RedissonWrapper.class);
    }
}

