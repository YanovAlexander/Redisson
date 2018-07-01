package com.redisson.result;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResultDaoImpl implements ResultDao {
    private static final Logger LOG = Logger.getLogger(ResultDaoImpl.class);
    private static final String COLLECTION_NAME = "result";
    private MongoDatabase database;

    @Inject
    public ResultDaoImpl(MongoDatabase database) {
        Objects.requireNonNull(database, "database is null");

        if (this.database != null) {
            return;
        }

        this.database = database;
    }

    @Override
    public void save(ResultData data) {
        LOG.debug(String.format("save: Params: homeTeam=%s, awayTeam=%s, group=%s, result=%s",
                data.getHomeTeam(), data.getAwayTeam(),
                data.getGroup(), data.getResult()));
        Objects.requireNonNull(data, "data is null");

        try {
            MongoCollection<ResultData> collection = database.getCollection(COLLECTION_NAME, ResultData.class);
            collection.insertOne(data);
        } catch (Exception e) {
            LOG.error(String.format("save: Error Params: homeTeam=%s, awayTeam=%s, group=%s, result=%s",
                    data.getHomeTeam(), data.getAwayTeam(), data.getGroup(), data.getResult()), e);
            throw new InternalServerErrorException("save error");
        }
    }

    @Override
    public List<ResultData> getAll() {
        LOG.debug("getAll method.");
        List<ResultData> resultData = new ArrayList();

        try {
            MongoCollection<ResultData> collection = database.getCollection(COLLECTION_NAME, ResultData.class);
//            FindIterable<ResultData> resultData1 = collection.find(ResultData.class);
            MongoCursor<ResultData> iterator = collection.find().iterator();
//            resultData1.iterator().forEachRemaining(System.out::println
//            );


            while (iterator.hasNext()) {

                ResultData next = iterator.next();

                if (next != null) {
                    resultData.add(next);
                }
            }

        } catch (Exception e) {
            LOG.error("getAll: Error ", e);
        }

        return resultData;
    }
}
