package com.github.taven.service;

public interface DemoService {

    void insertDB(boolean rollback);

    void insertRedis(boolean rollback);
}
