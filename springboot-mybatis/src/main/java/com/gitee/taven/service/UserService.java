package com.gitee.taven.service;

import com.gitee.taven.entity.User;
import com.gitee.taven.entity.UserExample;

public interface UserService {
    User selectOneByExample(UserExample example);

    void select();

    void selectList();

    void batchMode();
}
