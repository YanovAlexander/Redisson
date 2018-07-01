package com.redisson.configs;


import com.google.inject.AbstractModule;
import com.mongodb.client.MongoDatabase;
import com.redisson.cache.*;
import com.redisson.result.ResultDao;
import com.redisson.result.ResultDaoImpl;
import org.redisson.api.RedissonClient;

public class DiConfig extends AbstractModule {
    @Override
    protected void configure() {
        bind(RedissonClient.class).toProvider(RedissonCacheManagerProvider.class);
        bind(MongoDatabase.class).toProvider(MongoDBProvider.class);
        bind(CacheManager.class).to(RedissonCachemanagerWrapper.class);
        bind(Cache.class).to(RedissonWrapper.class);
        bind(ResultDao.class).to(ResultDaoImpl.class);
    }
}

