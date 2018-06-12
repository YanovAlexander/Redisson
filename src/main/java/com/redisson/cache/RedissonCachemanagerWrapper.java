package com.redisson.cache;

import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;

import javax.inject.Inject;
import java.util.Objects;

public class RedissonCachemanagerWrapper implements CacheManager {
    private RedissonClient client;

    @Inject
    public RedissonCachemanagerWrapper(RedissonClient client) {
        Objects.requireNonNull(client, "client is null");

        this.client = client;
    }

    @Override
    public Cache getCache() {
        return new RedissonWrapper(client);
    }

    @Override
    public void clearCache() {
        RKeys keys = client.getKeys();

        keys.getKeys().forEach(key ->
                client.getBucket(key)
                        .delete());
    }
}
