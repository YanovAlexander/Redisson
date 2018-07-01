package com.redisson.configs;

import com.google.inject.Provider;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDBProvider implements Provider<MongoDatabase> {
    private static final Logger LOG = Logger.getLogger(MongoDBProvider.class);
    private static final String HOST = "localhost";
    private static final Integer PORT = 27017;
    private static final String DB_NAME = "result";

    private static MongoClient client = null;

    public synchronized static void init() {
        if (client != null) {
            return;
        }

        LOG.info(String.format("init: mongo client init with host=%s, port=%d", HOST, PORT));
        client = new MongoClient(HOST, PORT);
    }

    @Override
    public MongoDatabase get() {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        return client.getDatabase(DB_NAME).withCodecRegistry(pojoCodecRegistry);
    }
}
