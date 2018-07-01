package com.redisson.result;

import com.redisson.configs.SL;
import org.apache.log4j.Logger;

import java.util.Objects;

public class Results {
    private static final Logger LOG = Logger.getLogger(Results.class);

    public static void save(ResultDto resultDto) {
        LOG.debug(String.format("save: Params: awayTeam=%s, homeTeam=%s, result=%s, group=%s",
                resultDto.getAwayTeam(), resultDto.getHomeTeam(),
                resultDto.getResult(), resultDto.getGroup()));
        Objects.requireNonNull(resultDto, "resultDto is null");

        try {
            SL.get(ResultDao.class).save(ResultMapper.mapResultDtoToData(resultDto));
        } catch (Exception e) {
            LOG.error(String.format("save: Error, params: awayTeam=%s, homeTeam=%s, result=%s, group=%s",
                                    resultDto.getAwayTeam(), resultDto.getHomeTeam(),
                                    resultDto.getResult(), resultDto.getGroup()), e);
            throw new RuntimeException("save error");
        }
    }


}
