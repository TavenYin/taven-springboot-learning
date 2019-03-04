package com.gitee.taven.service;

import com.gitee.taven.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AppService {

    void mybatisInsert(List<User> userList);

    void jdbcBatch(List<User> userList);

    void jdbcBatch2(List<User> userList);
}
