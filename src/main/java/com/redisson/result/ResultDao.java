package com.redisson.result;

import java.util.List;

public interface ResultDao {

    void save(ResultData data);

    List<ResultData> getAll();
}
