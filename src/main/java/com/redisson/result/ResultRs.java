package com.redisson.result;

import com.redisson.cache.Cache;
import com.redisson.cache.CacheManager;
import com.redisson.configs.SL;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("/result")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ResultRs {
    private static final Logger LOG = Logger.getLogger(ResultRs.class);

    @POST
    @Path("/save")
    public Response save(ResultDto resultDto) {
        LOG.debug(String.format("saveScore: Params: homeTeam=%s, awayTeam=%s, result=%s, group=%s",
                resultDto.getHomeTeam(), resultDto.getAwayTeam(), resultDto.getResult(), resultDto.getGroup()));
        Objects.requireNonNull(resultDto, "resultDto is null");

        try {
            Results.save(resultDto);
        } catch (Exception e) {
            LOG.error(String.format("save: Error, params: homeTeam=%s, awayTeam=%s, result=%s, group=%s",
                    resultDto.getHomeTeam(), resultDto.getAwayTeam(), resultDto.getResult(), resultDto.getGroup()));
        }

        return Response.ok().build();
    }

    @GET
    @Path("/get-all")
    public Response getAllResults() {
        LOG.debug("getAllResults method.");
        List<Result> allResults = null;

        try {
            allResults = ResultCached.getAllResults(SL.get(ResultDao.class), SL.get(CacheManager.class).getCache());
        } catch (Exception e) {
            LOG.error("getAllResults: Error ", e);
        }

        return Response.ok().entity(allResults.stream().map(ResultMapper::mapResultToDto).collect(Collectors.toList())).build();
    }
}
