package com.gitee.taven.service;

import com.gitee.taven.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

public interface AppService {

    void clearData();

    void mybatisInsert(List<User> userList);

    void mybatisBatch(List<User> userList);

    void jdbcBatch(List<User> userList);

    void nativeJdbcBatch(List<User> userList) throws SQLException;
}
