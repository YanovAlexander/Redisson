package com.redisson.score;

import com.redisson.cache.Cache;
import com.redisson.configs.SL;
import org.apache.log4j.Logger;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/score")
public class ScoreRs {
    private static final Logger LOG = Logger.getLogger(ScoreRs.class);

    @POST
    @Path("/save")
    public Response saveScore (ScoreDto scoreDto) {
        LOG.debug(String.format("saveScore: Params: homeTeam=%s, awayTeam=%s, result=%s, group=%s",
                scoreDto.getHomeTeam(), scoreDto.getAwayTeam(), scoreDto.getResult(), scoreDto.getGroup()));

        SL.get(Cache.class).put("redis", "testValue");

        return Response.ok().build();
    }

    @GET
    @Path("/readFromRedis")
    public Response getFromRedis() {
        String redis = (String) SL.get(Cache.class).get("redis");

        return Response.ok().entity(redis).build();
    }
}
