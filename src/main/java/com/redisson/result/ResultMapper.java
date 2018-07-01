package com.redisson.result;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import java.util.Objects;

public class ResultMapper {
    private static final Logger LOG = Logger.getLogger(ResultMapper.class);

    public static ResultData mapResultDtoToData (ResultDto dto) {
        LOG.debug(String.format("mapResultDtoToData: Params: id=%s, homeTeam=%s, awayTeam=%s, result=%s, group=%s",
                dto.getId(), dto.getHomeTeam(), dto.getAwayTeam(), dto.getResult(), dto.getGroup()));
        Objects.requireNonNull(dto, "dto is null");

        return new ResultData(new ObjectId(), dto.getHomeTeam(), dto.getAwayTeam(), dto.getResult(), dto.getGroup());
    }

    public static Result mapResultDataToResult (ResultData data) {
        LOG.debug(String.format("mapResultDataToResult: Params: id=%s, homeTeam=%s, awayTeam=%s, result=%s, group=%s",
                data.getId(), data.getHomeTeam(), data.getAwayTeam(), data.getResult(), data.getGroup()));
        Objects.requireNonNull(data, "data is null");

        return new Result(data.getId(), data.getHomeTeam(), data.getAwayTeam(), data.getResult(), data.getGroup());
    }

    public static ResultDto mapResultToDto (Result data) {
        LOG.debug(String.format("mapResultDataToResult: Params: id=%s, homeTeam=%s, awayTeam=%s, result=%s, group=%s",
                data.getId(), data.getHomeTeam(), data.getAwayTeam(), data.getResult(), data.getGroup()));
        Objects.requireNonNull(data, "data is null");

        return new ResultDto(data.getId(), data.getHomeTeam(), data.getAwayTeam(), data.getResult(), data.getGroup());
    }
}
