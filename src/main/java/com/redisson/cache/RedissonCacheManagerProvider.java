package com.redisson.cache;

import com.google.inject.Provider;
import org.apache.log4j.Logger;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class RedissonCacheManagerProvider implements Provider<RedissonClient> {
    private static final Logger LOG = Logger.getLogger(RedissonCacheManagerProvider.class);
    private static RedissonClient client = null;

    public synchronized static void init(String fileName) {
        LOG.debug(String.format("init: Params: configFileName=%s", fileName));
        Objects.requireNonNull(fileName, "configFileName is null");

        if (client != null) {
            return;
        }

        Config config;
        try {
            config = Config.fromJSON(new File(fileName));
        } catch (IOException e) {
            LOG.error("init: configFileName is not valid");
            throw new IllegalArgumentException("config is not valid");
        }

        client = Redisson.create(config);
    }

    @Override
    public RedissonClient get() {
        return client;
    }

    public static void destroy() {
        client.shutdown();
    }
}
