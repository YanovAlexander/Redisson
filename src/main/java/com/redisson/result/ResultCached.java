package com.redisson.result;

import com.redisson.cache.Cache;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResultCached {
    private static final Logger LOG = Logger.getLogger(ResultCached.class);
    private static final String ALL_RESULTS_KEY = "all_results";

    public static List<Result> getAllResults(ResultDao dao, Cache cache) {
        LOG.debug("getAllResults method.");
        Objects.requireNonNull(dao, "dao is null");
        Objects.requireNonNull(cache, "cache is null");

        List<ResultData> results = (List<ResultData>) cache.get(ALL_RESULTS_KEY);

        if (results == null) {
           results = dao.getAll();
        }

        if (results == null) {
            LOG.warn("getAllResults: results is null");
            throw new IllegalArgumentException("results not exists");
        }

        cache.put(ALL_RESULTS_KEY, results);

        return results.stream()
                .map(ResultMapper::mapResultDataToResult)
                .collect(Collectors.toList());
    }
}
