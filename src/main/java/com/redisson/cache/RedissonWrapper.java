package com.redisson.cache;

import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;

import javax.inject.Inject;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RedissonWrapper implements Cache {
    private RedissonClient client;

    @Inject
    public RedissonWrapper(RedissonClient client) {
        Objects.requireNonNull(client, "client is null");

        this.client = client;
    }

    @Override
    public Object get(String key) {
        RBucket<Object> bucket = client.getBucket(key);
        return bucket.get();
    }

    @Override
    public void put(String key, Object value) {
        RBucket<Object> bucket = client.getBucket(key);
        bucket.set(value);
    }

    @Override
    public void put(String key, Object value, int timeToIdleAndLiveSeconds) {
        RBucket<Object> bucket = client.getBucket(key);
        bucket.set(value, timeToIdleAndLiveSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean remove(String key) {
        RBucket<Object> bucket = client.getBucket(key);
        return bucket.delete();
    }

    @Override
    public boolean removeByKeyPrefix(String keyPrefix) {
        RKeys keys = client.getKeys();
        keys.deleteByPattern(keyPrefix + "*");
        return true;
    }
}
