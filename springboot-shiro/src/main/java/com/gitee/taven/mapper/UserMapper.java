package com.gitee.taven.mapper;

import com.gitee.taven.domain.User;

public interface UserMapper {

    User getUserByUsername(String username);

}
