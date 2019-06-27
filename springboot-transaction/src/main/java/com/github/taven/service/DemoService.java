package com.github.taven.service;

public interface DemoService {

    void insertDB(boolean rollback);

    void insertRedis(boolean rollback);

    void insertDBAndRedis(boolean rollback);

    Object cache(String uuid);

    void removeCache(boolean rollback, String uuid);

    void removeCacheAfter(boolean rollback, String uuid);
}
